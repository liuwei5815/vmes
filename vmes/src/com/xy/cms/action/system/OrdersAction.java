package com.xy.cms.action.system;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.xy.cms.bean.JsTree;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Customer;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.Orders;
import com.xy.cms.entity.OrdersDetail;
import com.xy.cms.entity.Produceplan;
import com.xy.cms.entity.ProduceplanExceptionHandle;
import com.xy.cms.entity.Product;
import com.xy.cms.entity.ProductUint;
import com.xy.cms.entity.base.BaseProduceplanExceptionHandle;
import com.xy.cms.service.CustomerService;
import com.xy.cms.service.EmployeeService;
import com.xy.cms.service.OrdersService;
import com.xy.cms.service.ProduceplanExceptionHandleService;
import com.xy.cms.service.ProduceplanService;
import com.xy.cms.service.ProductService;

public class OrdersAction extends BaseAction{
	private Orders orders;
	private List<OrdersDetail> ordersDetailList;
	private OrdersService ordersService;
	private ProductService productService;
	private CustomerService customerService;
	private ProduceplanExceptionHandleService produceplanExceptionHandleService;
	private ProduceplanService produceplanService;
	private EmployeeService employeeService;
	private Customer customer;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8270410754454437580L;

	/**
	 * 初始化
	 */
	public String init(){
		return "init";
	}

	public String queryOrder() {
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			@SuppressWarnings("unchecked")
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				String code=request.getParameter("orderCode");
				String state=request.getParameter("orderState");
				String cusName=request.getParameter("cusName");
				//订单编号
				if(CommonFunction.isNotNull(code)){
					pageMap.put("code", code);
				}
				//订单名称
				if(CommonFunction.isNotNull(state)){
					pageMap.put("state",state);
				}
				//客户名称
				if(CommonFunction.isNotNull(cusName)){
					 pageMap.put("cusName",cusName);
				}
				pageMap.put("orders", orders);
				return ordersService.queryAllOrders(pageMap);
			}
		});
		List<Orders[]> resultList =this.getList();
		if(!CollectionUtils.isEmpty(resultList)){
			Long orders[] =new Long[resultList.size()];
			int i=0;
			for (Object[] object : resultList) {
				orders[i++]=((Orders)object[0]).getId();
			}
			List<OrdersDetail> details=ordersService.queryOrdersDetailByOrdersIds(orders);
			if(!CollectionUtils.isEmpty(details)){
				Map<Long,List<OrdersDetail>> ordersDetailMap  =new HashMap<Long, List<OrdersDetail>>();
				for (OrdersDetail ordersDetail : details) {
					List<OrdersDetail> detail=ordersDetailMap.get(ordersDetail.getOrderId());
					if(detail==null){
						detail=new ArrayList<OrdersDetail>();
						ordersDetailMap.put(ordersDetail.getOrderId(),detail);
					}
					detail.add(ordersDetail);
				}
				request.setAttribute("ordersDetailMap", ordersDetailMap);
			}

		}
		return "list";
	}
	/**
	 * 预添加订单
	 * @return
	 */
	public String preAdd(){
		try {
			//获取所有客户编号
			List<String> cusList=ordersService.queryCusCode();
			request.setAttribute("cusList", cusList);
			//获取产品编号TODO
			List<Product> proList=productService.queryProduct();
			request.setAttribute("proList", proList);
			//获取产品单位
			/*List<Object> proList=productService.queryProducAndUnit();
			request.setAttribute("proList", proList);*/
			//获取企业员工
			List<Employee> empList=employeeService.getAllEmp();
			request.setAttribute("empList", empList);
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "add";
	}
	/**
	 * 添加订单
	 * @return
	 */
	public String add(){
		try {
			if(CommonFunction.isNull(orders)){
				throw new BusinessException("参数错误");
			}
		
			if(CommonFunction.isNull(orders.getOrderDate())){
				throw new BusinessException("下单日期为空");
			}
			if(CommonFunction.isNull(orders.getDeliveryDate())){
				throw new BusinessException("交付日期为空");
			}
			if(orders.getOrderDate().getTime()>orders.getDeliveryDate().getTime()){
				throw new BusinessException("交付日期不能早于下单日期");
			}
			if(CommonFunction.isNull(ordersDetailList)){
				throw new BusinessException("订单明细为空");
			}
			orders.setAddDate(new Timestamp(new Date().getTime()));
			orders.setState(Orders.State.awaiting.getCode());//待排产
			ordersService.saveOrders(orders,ordersDetailList);	
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "add";
	}
	/**
	 * 预添加订单详情
	 * @return
	 */
	public String preAddDetail(){
		try {
			String id=request.getParameter("id");
			
			if(CommonFunction.isNull(id)){
				throw new BusinessException("订单Id为空");
			}
			request.setAttribute("orders", ordersService.getOrderById(Long.parseLong(id)));
			if(CommonFunction.isNull(getProList(Long.parseLong(id)))){
				throw new BusinessException("没有更多产品了");
			}
			request.setAttribute("proList",getProList(Long.parseLong(id)) );
		}catch(BusinessException e){
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "addDeatil";
	}
	/**
	 * 获取销售详情已存在的产品
	 * @param orderId
	 * @return
	 * @throws BusinessException
	 */
	private List<Product> getProList(Long orderId) throws BusinessException{
		List<OrdersDetail> ordersDetail=ordersService.getDetailListByOrderId(orderId);
		List<Product> allProductList=productService.queryProduct();
		List<Product> proList = productService.getProductByOrderId(orderId);
		for (Product product : proList) {
			if(allProductList.contains(product)){
				allProductList.remove(product);
			}
		}
		return allProductList;
	}
	/**
	 * 添加详情
	 * @return
	 * @throws ParseException
	 */
	public String addDetail() throws ParseException{
		try {
			String orderId=request.getParameter("ordersDetail.orderId");
			String productId=request.getParameter("ordersDetail.productId");
			String num=request.getParameter("ordersDetail.num");
			String deliveryDate=request.getParameter("ordersDetail.deliveryDate");
			String remarks=request.getParameter("ordersDetail.remarks");
			String unit=request.getParameter("ordersDetail.productUnit");
			if(CommonFunction.isNull(orderId)){
				throw new BusinessException("订单id为空");
			}
			if(CommonFunction.isNull(productId)){
				throw new BusinessException("产品id为空");
			}
			if(CommonFunction.isNull(productId)){
				throw new BusinessException("数量为空");
			}
			if(CommonFunction.isNull(deliveryDate)){
				throw new BusinessException("交付时间为空");
			}
			Product product=productService.getProductById(Long.parseLong(productId));
			OrdersDetail detail=new OrdersDetail();
			detail.setOrderId(Long.parseLong(orderId));
			detail.setProductId(Long.parseLong(productId));
			detail.setNum(Long.parseLong(num));
			detail.setDeliveryDate(new SimpleDateFormat("yyyy-MM-dd").parse(deliveryDate));
			detail.setState(OrdersDetail.OrdersDetailStatus.awaiting.getCode());
			detail.setAddDate(new Date());
			detail.setProductCode(product.getProductCode());
			detail.setProductName(product.getName());
			detail.setProductTypespec(product.getTypespec());
			detail.setProductUnit(unit);
			if(CommonFunction.isNotNull(remarks)){
				detail.setRemarks(remarks);
			}
			ordersService.saveDetails(detail);
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		
		return "addDeatil";
	}
	
	/**
	 * 预修改订单
	 * @return
	 */
	public String preEditOrders(){
		try {
			String id=request.getParameter("id");
			
			if(CommonFunction.isNull(id)){
				throw new BusinessException("订单Id为空");
			}
			Orders orders=ordersService.getOrderById(Long.parseLong(id));
			//获取所有客户编号
			List<String> cusList=ordersService.queryCusCode();
			request.setAttribute("cusList", cusList);
			request.setAttribute("orders", orders);
			List<Employee> empList=employeeService.getAllEmp();
			request.setAttribute("empList", empList);
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		
		return "editOrders";
	}
	/**
	 * 编辑订单
	 * @return
	 */
	public String editOrders(){
		try {
			if(CommonFunction.isNull(orders)){
				throw new BusinessException("订单Id为空");
			}
			Orders newOrders=ordersService.getOrderById(orders.getId());
			newOrders.setOrderCode(orders.getOrderCode());
			newOrders.setOrderDate(orders.getOrderDate());
			//TODO:
		/*	newOrders.setBrokerage(orders.getBrokerage());*/
			newOrders.setEmpId(orders.getEmpId());
			newOrders.setCustomerId(orders.getCustomerId());
			newOrders.setUpdateDate(new Timestamp(new Date().getTime()));
			ordersService.saveOrders(newOrders, null);
			this.message = "修改成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "editOrders";
	}
	/**
	 * 预编辑订单详情
	 * @return
	 */
	public String preEditDetail(){
		try {
			String id=request.getParameter("id");
			
			if(CommonFunction.isNull(id)){
				throw new BusinessException("订单详情Id为空");
			}
			OrdersDetail ordersDetail=ordersService.getOrdersDetailById(Long.parseLong(id));
			request.setAttribute("product", productService.getProductById(ordersDetail.getProductId()));
			request.setAttribute("ordersDetail", ordersDetail);
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "editOrdersDetail";
	}
	/**
	 * 编辑订单详情
	 * @return
	 * @throws ParseException
	 */
	public String editDetail() throws ParseException{
		try {
			String detailId=request.getParameter("ordersDetail.id");
			String deliveryDate=request.getParameter("ordersDetail.deliveryDate");
			String remarks=request.getParameter("ordersDetail.remarks");
			String unit=request.getParameter("ordersDetail.productUnit");
			if(CommonFunction.isNull(detailId)){
				throw new BusinessException("详情id为空");
			}
			if(CommonFunction.isNull(deliveryDate)){
				throw new BusinessException("交付时间为空");
			}
			OrdersDetail oldOrdersDetail=ordersService.getOrdersDetailById(Long.parseLong(detailId));
			oldOrdersDetail.setDeliveryDate(new SimpleDateFormat("yyyy-MM-dd").parse(deliveryDate));
			if(CommonFunction.isNotNull(remarks)){
				oldOrdersDetail.setRemarks(remarks);
			}
			if(CommonFunction.isNotNull(unit)){
				oldOrdersDetail.setProductUnit(unit);
			}
			ordersService.editDetails(oldOrdersDetail);
			this.message = "修改成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "editOrdersDetail";
	}
	/**
	 * 调整订单数量
	 * @throws BusinessException 
	 */
	public String preEditDetailNum(){
		try {
			String oId=request.getParameter("oId");
			if(CommonFunction.isNull(oId)){
				throw new BusinessException("订单id为空");
			}
			Orders orders=ordersService.getOrderById(Long.parseLong(oId));
			request.setAttribute("orders", orders);
			//查询客户
			Customer cstomer = customerService.getCustomerById(orders.getCustomerId());
			request.setAttribute("cstomer", cstomer);
			//经手人
			Employee employee = employeeService.getEmployeeById(orders.getEmpId());
			request.setAttribute("employee", employee);
			
			String detailId=request.getParameter("id");
			if(CommonFunction.isNull(detailId)){
				throw new BusinessException("订单详情id为空");
			}
			OrdersDetail orderDetail=ordersService.getOrdersDetailById(Long.parseLong(detailId));
			request.setAttribute("orderDetail", orderDetail);
			//查询产品
			Product product = productService.getProductById(orderDetail.getProductId());
			request.setAttribute("product", product);
			//查询订单状态-计划状态-派工状态
			Produceplan producePlan=produceplanService.getProduceByOrderDetailId(Long.parseLong(detailId));
			if(CommonFunction.isNotNull(producePlan)){
				ProduceplanExceptionHandle peh=produceplanExceptionHandleService.getPehByProId(producePlan.getId());
				if(CommonFunction.isNotNull(peh)){
					if(peh.getState()==BaseProduceplanExceptionHandle.Status.produceplan.getCode()){
						request.setAttribute("peh", "proPehWaring");
					}else{
						request.setAttribute("peh", "disPehWaring");
					}
				}
			}
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "editDetailNum";
	}
	/**
	 * 保存订单数量
	 * @return
	 */
	public String editDetailNum(){
		try {
			String detailId=request.getParameter("detailId");
			if(CommonFunction.isNull(detailId)){
				throw new BusinessException("详情id为空");
			}
			OrdersDetail ordersDetail=ordersService.getOrdersDetailById(Long.parseLong(detailId));
			String num=request.getParameter("num");
			if(CommonFunction.isNull(detailId)){
				throw new BusinessException("数量不能为空");
			}
			
			ordersService.editOrderDetailNum(Long.parseLong(detailId),Long.parseLong(num),ordersDetail.getNum());
			this.message = "保存成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editDetailNum";
	}
	/**
	 * 取消销售订单
	 * @return
	 */
	public String cancleOrders(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到订单主键id
			String id = request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			//通过主键得到销售订单
			Orders orders = ordersService.getOrderById(Long.parseLong(id));
			ordersService.cancelOrders(orders);
			json.put("successflag", "1");
			json.put("code",0);
		} catch (BusinessException e) {		
			json.put("code",1);
			json.put("msg", e.getMessage());
			logger.error(e.getMessage(), e);			
		}catch (Exception e) {
			json.put("code", 1);
			json.put("msg","服务器出现异常" );			
			logger.error(e.getMessage(), e);
		}
		finally{

			if(out!=null){
				Gson gson = new Gson();
				out.print(gson.toJson(json));
				out.close();
			}					
		}	
		return NONE;
	}
	
	/**
	 * 取消销售订单详情
	 * @return
	 */
	public String cancleOrdersDetail(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到订单详情主键id
			String id = request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			//通过主键得到销售订单详情
			OrdersDetail ordersDetail = ordersService.getOrdersDetailById(Long.parseLong(id));
			ordersService.cancelOrderDetail(ordersDetail);
			json.put("successflag", "1");
			json.put("code",0);
		} catch (BusinessException e) {		
			json.put("code",1);
			json.put("msg", e.getMessage());
			logger.error(e.getMessage(), e);			
		}catch (Exception e) {
			json.put("code", 1);
			json.put("msg","服务器出现异常" );			
			logger.error(e.getMessage(), e);
		}
		finally{

			if(out!=null){
				Gson gson = new Gson();
				out.print(gson.toJson(json));
				out.close();
			}					
		}	
		return NONE;
	}
	/**
	 * 查询单位类型
	 * @return
	 */
	public String queryUint(){
		List<ProductUint> proUnitList=productService.queryAllUnit();
		//初始化js树形结构
		List<JsTree> treeList=new ArrayList<JsTree>();
		for (ProductUint productUint : proUnitList) {
			JsTree root=new JsTree();
			root.setText(productUint.getName());
			root.setId(productUint.getId().toString());
			root.setParent((productUint.getPid()==null || productUint.getPid()==0)?"#":productUint.getPid().toString());
			treeList.add(root);
		}
		String treeJson=JSON.toJSONString(treeList);
		request.setAttribute("tree",StringEscapeUtils.escapeHtml4(treeJson));
		return "tree";
	}
	
	/**
	 * 预添加客户
	 * @return
	 */
	public String preAddCustomer(){
		return "addCustomer";
	}
	
	/**
	 * 添加客户
	 * @return
	 */
	public String addCustomer(){
		try {
			if(CommonFunction.isNull(customer.getName())){
				throw new BusinessException("客户名称不能为空");
			}
			customer.setAddDate(new Timestamp(new Date().getTime()));
			customer.setState(0);
			customerService.saveCustomer(customer);
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "addCustomer";
	}
	
	/**
	 * 
	 * 查询所有的客户
	 * */
	public String customer(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			List<Customer> customerList = customerService.queryAllCustomer();
			json.put("successflag", "1");
			json.put("code",0);
			json.put("customerList",customerList);
		} catch (Exception e) {
			json.put("code", 1);
			json.put("msg","服务器出现异常" );			
			logger.error(e.getMessage(), e);
		}
		finally{
			if(out!=null){
				Gson gson = new Gson();
				out.print(gson.toJson(json));
				out.close();
			}					
		}	
		return NONE;
	}
	
	
	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public List<OrdersDetail> getOrdersDetailList() {
		return ordersDetailList;
	}

	public void setOrdersDetailList(List<OrdersDetail> ordersDetailList) {
		this.ordersDetailList = ordersDetailList;
	}

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public ProduceplanExceptionHandleService getProduceplanExceptionHandleService() {
		return produceplanExceptionHandleService;
	}

	public void setProduceplanExceptionHandleService(ProduceplanExceptionHandleService produceplanExceptionHandleService) {
		this.produceplanExceptionHandleService = produceplanExceptionHandleService;
	}

	public ProduceplanService getProduceplanService() {
		return produceplanService;
	}

	public void setProduceplanService(ProduceplanService produceplanService) {
		this.produceplanService = produceplanService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
}
