package com.xy.cms.action.system;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.Lognormdist;


import com.google.gson.Gson;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Customer;
import com.xy.cms.entity.Orders;
import com.xy.cms.entity.OrdersDetail;
import com.xy.cms.entity.Produceplan;
import com.xy.cms.entity.ProduceplanExceptionHandle;
import com.xy.cms.entity.ProduceplanTodo;
import com.xy.cms.entity.Product;
import com.xy.cms.entity.base.BaseProduceplanExceptionHandle.Status;
import com.xy.cms.service.CustomerService;
import com.xy.cms.service.EmployeeService;
import com.xy.cms.service.OrdersDetailService;
import com.xy.cms.service.OrdersService;
import com.xy.cms.service.PlanTodoService;
import com.xy.cms.service.ProduceplanExceptionHandleService;
import com.xy.cms.service.ProduceplanService;
import com.xy.cms.service.ProductService;

public class ProduceplanAction extends BaseAction{
	private ProduceplanService produceplanService;
	private Produceplan produceplan;
	private ProductService productService;
	private EmployeeService employeeService;
	private OrdersService ordersService;
	private CustomerService customerService;
	private OrdersDetailService ordersDetailService;
	private List<Produceplan> ProduceplanList;
	private ProduceplanExceptionHandleService produceplanExceptionHandleService;
	private PlanTodoService planTodoService;
	
	/**
	 * 初始化init页面
	 * */
	public String init(){
		return "init";
	}
	
	/**
	 * 数据列表页面
	 * */
	public String query(){
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				//产品名称
				String productName = request.getParameter("productName");
				pageMap.put("productName", productName);
				//产品编号
				String productCode = request.getParameter("productCode");
				pageMap.put("productCode", productCode);
				//规格型号
				String productTypespec = request.getParameter("productTypespec");
				pageMap.put("productTypespec", productTypespec);
				//订单编号
				String orderCode = request.getParameter("orderCode");
				pageMap.put("orderCode", orderCode);
				//生产计划编号
				String manufactureCode = request.getParameter("manufactureCode");
				pageMap.put("manufactureCode", manufactureCode);
				//生产计划状态
				String produceplanState = request.getParameter("produceplanState");
				pageMap.put("produceplanState", produceplanState);
				//查询生产计划
				return produceplanService.getAllProduceplanRelationOrdersDetail(pageMap);
			}
		});
		return "query";
	}
	
	/**
	 * 查询产品Init
	 * */
	public String queryProductInit(){
		//去到产品信息页面
		return "queryProductInit";
	}
	
	/**
	 * 查询产品(用于添加无销售订单详情的生产计划时，选择产品)
	 * */
	public String queryProduct(){
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				//产品名称
				String productName = request.getParameter("productName");
				pageMap.put("productName", productName);
				//产品编号
				String productCode = request.getParameter("productCode");
				pageMap.put("productCode", productCode);
				//规格型号
				String productTypespec = request.getParameter("productTypespec");
				pageMap.put("productTypespec", productTypespec);
				//查询产品信息
				return productService.queryProductPage(pageMap);
			}
		});
		return "queryProduct";
	}
	
	
	/**
	 * 预添加无销售订单详情的生产计划(去到添加页面)
	 * */
	public String preAddNoOrder(){
		//查询所有的产品信息
		List<Product> productList = productService.queryProduct();
		request.setAttribute("productList", productList);
		//去到添加页面
		return "addNoOrder";
	}
	
	/**
	 * 添加无销售订单详情的生产计划(保存信息)
	 * */
	public String addNoOrder(){
		try {
			if(CommonFunction.isNull(produceplan.getProductId())){
				throw new BusinessException("请选择计划生产产品");
			}
			if(CommonFunction.isNull(produceplan.getNum())){
				throw new BusinessException("请填写计划生产数量");
			}
			if(CommonFunction.isNull(produceplan.getStartDate())){
				throw new BusinessException("请选择计划开始时间");
			}
			if(CommonFunction.isNull(produceplan.getStartDate())){
				throw new BusinessException("请选择计划结束时间");
			}
			if(produceplan.getStartDate().getTime()>produceplan.getEndDate().getTime()){
				throw new BusinessException("计划开始时间不能早于计划结束时间");
			}
			//保存
			produceplanService.addNoOrderProduceplan(produceplan);
			this.message = "保存成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "addNoOrder";
	}
	
	/**
	 *预编辑无销售订单详情的生产计划(只编辑生产计划编号、计划开始时间、计划结束时间)
	 */
	public String preEditNoOrder(){
		try {
			//得到生产计划主键id
			String id = request.getParameter("id");
			if(CommonFunction.isNull(id)){
				throw new BusinessException("参数错误");
			}
			//通过主键得到生产计划
			Produceplan produceplan = produceplanService.getProduceplanById(Long.parseLong(id));
			request.setAttribute("produceplan", produceplan);
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editNoOrder";
	}
	
	/**
	 * 编辑无销售订单详情的生成计划(保存)
	 * */
	public String editNoOrder(){
		try {
			if(CommonFunction.isNull(produceplan.getStartDate())){
				throw new BusinessException("请选择计划开始时间");
			}
			if(CommonFunction.isNull(produceplan.getStartDate())){
				throw new BusinessException("请选择计划结束时间");
			}
			produceplanService.editNoOrderProduceplan(produceplan);
			this.message = "编辑成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editNoOrder";
	}
	
	
	/**
	 * 预调整无销售订单详情的生产计划
	 * */
	public String preEditNoOrderNum(){
		try {
			//得到生成计划主键id
			String id = request.getParameter("id");
			if(CommonFunction.isNull(id)){
				throw new BusinessException("参数错误");
			}
			//得到是否报警的主键id
			String pehId = request.getParameter("pehId");
			request.setAttribute("pehId", pehId);
			//通过生产计划主键得到生产计划
			Produceplan produceplan = produceplanService.getProduceplanById(Long.parseLong(id));
			request.setAttribute("produceplan", produceplan);
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editNoOrderNum";
	}
	
	/**
	 * 调整无销售订单详情的生产计划(只调整数量，并且要改变销售订单的状态)
	 * */
	public String editNoOrderNum(){
		try {
			if(CommonFunction.isNull(produceplan.getNum())){
				throw new BusinessException("请填写调整后的数量");
			}
			//得到是否报警的主键id
			String pehId = request.getParameter("pehId");
			request.setAttribute("pehId", pehId);
			produceplanService.editNoOrderProduceplanNum(pehId,produceplan);
			this.message = "编辑成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editNoOrderNum";
	}
	
	/**
	 * 取消生产计划
	 * */
	public String cancelProduceplan(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到生成计划主键id
			String id = request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			//通过主键得到生成计划
			Produceplan produceplan = produceplanService.getProduceplanById(Long.parseLong(id));
			produceplanService.cancelProduceplan(produceplan);
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
	 * 预添加有销售订单详情的生产计划
	 * */
	public String preAddYesOrderOne(){
		try {
			//得到销售订单明细id
			String ordersDetailId = request.getParameter("id");
			if(CommonFunction.isNull(ordersDetailId)){
				throw new BusinessException("参数错误");
			}
			//查询销售订单明细
			OrdersDetail ordersDetail = ordersDetailService.getOrdersDetailById(Long.parseLong(ordersDetailId));
			request.setAttribute("ordersDetail", ordersDetail);
			//查询产品
			Product product = productService.getProductById(ordersDetail.getProductId());
			request.setAttribute("product", product);
			//查询订单
			Orders orders = ordersService.getOrderById(ordersDetail.getOrderId());
			request.setAttribute("orders", orders);
			//查询客户
			Customer cstomer = customerService.getCustomerById(orders.getCustomerId());
			request.setAttribute("cstomer", cstomer);
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "addYesOrderOne";
	}
	
	/**
	 * 添加有订单的生产计划(保存信息)
	 * */
	public String addYesOrderOne(){
		try {
			if(CommonFunction.isNull(produceplan.getProductId())){
				throw new BusinessException("请选择生产产品");
			}
			if(CommonFunction.isNull(produceplan.getNum())){
				throw new BusinessException("请填写生产数量");
			}
			if(CommonFunction.isNull(produceplan.getStartDate())){
				throw new BusinessException("请选择计划开始时间");
			}
			if(CommonFunction.isNull(produceplan.getStartDate())){
				throw new BusinessException("请选择计划结束时间");
			}
			if(produceplan.getStartDate().getTime()>produceplan.getEndDate().getTime()){
				throw new BusinessException("计划开始时间不能早于计划结束时间");
			}
			produceplanService.addYesOrderProduceplanOne(produceplan);
			this.message = "保存成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "addYesOrderOne";
	}
	
	/**
	 * 预编辑有销售订单的生产计划
	 * */
	public String preEditYesOrderOne(){
		try {
			//得到生产计划id
			String produceplanId = request.getParameter("id");
			if(CommonFunction.isNull(produceplanId)){
				throw new BusinessException("参数错误");
			}
			Object obj = produceplanService.editYesOrderProduceplanNumQuery(Long.parseLong(produceplanId));
			request.setAttribute("obj", obj);
			/*//通过主键得到生产计划
			Produceplan produceplan = produceplanService.getProduceplanById(Long.parseLong(produceplanId));
			request.setAttribute("produceplan", produceplan);
			//查询销售订单明细
			OrdersDetail ordersDetail = ordersDetailService.getOrdersDetailById(produceplan.getOrderDetailId());
			request.setAttribute("ordersDetail", ordersDetail);
			//查询订单
			Orders orders = ordersService.getOrderById(ordersDetail.getOrderId());
			request.setAttribute("orders", orders);
			//查询客户
			Customer cstomer = customerService.getCustomerById(orders.getCustomerId());
			request.setAttribute("cstomer", cstomer);*/
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editYesOrderOne";
	}
	
	/**
	 * 编辑有销售订单的生产计划(保存信息)
	 * */
	public String editYesOrderOne(){
		try {
			if(CommonFunction.isNull(produceplan.getStartDate())){
				throw new BusinessException("请选择计划开始时间");
			}
			if(CommonFunction.isNull(produceplan.getStartDate())){
				throw new BusinessException("请选择计划结束时间");
			}
			produceplanService.editYesOrderProduceplanOne(produceplan);
			this.message = "编辑成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editYesOrderOne";
	}
	
	
	/**
	 * 预编辑有销售订单的生产计划的数量
	 * */
	public String preEditYesOrderOneNum(){
		try {
			//得到生产计划id
			String produceplanId = request.getParameter("id");
			if(CommonFunction.isNull(produceplanId)){
				throw new BusinessException("参数错误");
			}
			//得到标记
			String pehId = request.getParameter("pehId");
			request.setAttribute("pehId", pehId);
			Object obj = produceplanService.editYesOrderProduceplanNumQuery(Long.parseLong(produceplanId));
			request.setAttribute("obj", obj);
			/*//通过主键得到生成计划
			Produceplan produceplan = produceplanService.getProduceplanById(Long.parseLong(produceplanId));
			request.setAttribute("produceplan", produceplan);
			//查询销售订单明细
			OrdersDetail ordersDetail = ordersDetailService.getOrdersDetailById(produceplan.getOrderDetailId());
			request.setAttribute("ordersDetail", ordersDetail);
			//查询产品
			Product product = productService.getProductById(ordersDetail.getProductId());
			request.setAttribute("product", product);
			//得到订单
			Orders orders = ordersService.getOrderById(ordersDetail.getOrderId());
			request.setAttribute("orders", orders);
			//查询客户
			Customer cstomer = customerService.getCustomerById(orders.getCustomerId());
			request.setAttribute("cstomer", cstomer);*/
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editYesOrderOneNum";
	}
	
	/**
	 * 编辑有销售订单的生产计划的数量(修改数量)
	 * */
	public String editYesOrderOneNum(){
		try {
			if(CommonFunction.isNull(produceplan.getNum())){
				throw new BusinessException("请填写生产计划数量");
			}
			//得到标记
			String pehId = request.getParameter("pehId");
			produceplanService.editYesOrderProduceplanNum(pehId,produceplan);
			this.message = "编辑成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editYesOrderOneNum";
	}
	
	/**
	 * 提示
	 * */
	public String prompt(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到生产计划主键id
			String id = request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			ProduceplanExceptionHandle exceptionHandle = produceplanExceptionHandleService.getHandleByPlanIdAndState(Long.parseLong(id), Status.dispatch);
			if(CommonFunction.isNotNull(exceptionHandle)){//不等于空,表示派工单还没有修改
				json.put("prompt", "1");
			}else{
				json.put("prompt", "0");
			}
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
	
	public ProduceplanService getProduceplanService() {
		return produceplanService;
	}

	public void setProduceplanService(ProduceplanService produceplanService) {
		this.produceplanService = produceplanService;
	}

	public Produceplan getProduceplan() {
		return produceplan;
	}

	public void setProduceplan(Produceplan produceplan) {
		this.produceplan = produceplan;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public OrdersDetailService getOrdersDetailService() {
		return ordersDetailService;
	}

	public void setOrdersDetailService(OrdersDetailService ordersDetailService) {
		this.ordersDetailService = ordersDetailService;
	}

	public List<Produceplan> getProduceplanList() {
		return ProduceplanList;
	}

	public void setProduceplanList(List<Produceplan> produceplanList) {
		ProduceplanList = produceplanList;
	}

	public ProduceplanExceptionHandleService getProduceplanExceptionHandleService() {
		return produceplanExceptionHandleService;
	}

	public void setProduceplanExceptionHandleService(
			ProduceplanExceptionHandleService produceplanExceptionHandleService) {
		this.produceplanExceptionHandleService = produceplanExceptionHandleService;
	}

	public PlanTodoService getPlanTodoService() {
		return planTodoService;
	}

	public void setPlanTodoService(PlanTodoService planTodoService) {
		this.planTodoService = planTodoService;
	}
	
	
	
	
}
