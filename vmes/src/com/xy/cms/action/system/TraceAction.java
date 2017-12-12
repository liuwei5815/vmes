package com.xy.cms.action.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Customer;
import com.xy.cms.entity.Orders;
import com.xy.cms.entity.OrdersDetail;
import com.xy.cms.entity.Produceplan;
import com.xy.cms.entity.ProduceplanTodo;
import com.xy.cms.entity.ProduceplanTodoClaim;
import com.xy.cms.entity.TodoClaimEquip;
import com.xy.cms.entity.TodoClaimMaterial;
import com.xy.cms.service.CustomerService;
import com.xy.cms.service.OrdersService;
import com.xy.cms.service.PlanTodoService;
import com.xy.cms.service.ProduceplanService;
import com.xy.cms.service.ProduceplanTodoClaimService;
import com.xy.cms.service.TraceService;
/**
 * 产品追溯
 * @author Administrator
 *
 */
public class TraceAction extends BaseAction{
	private ProduceplanService produceplanService;
	private PlanTodoService planTodoService;
	private ProduceplanTodoClaimService produceplanTodoClaimService;
	private TraceService traceService;
	private OrdersService ordersService;
	private CustomerService customerService;
	/**
	 * 初始化init页面
	 * */
	public String init(){
		String str = "";
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    Calendar lastDate = Calendar.getInstance();
	    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
	    str=sdf.format(lastDate.getTime());
	    String beginDate= str;
	    String endDate=DateUtil.format2Short(new Date());
		request.setAttribute("orderStartDate", beginDate);
		request.setAttribute("orderEndDate", endDate);
		request.setAttribute("planStartDate", beginDate);
		request.setAttribute("planEndDate", endDate);
		return "init";
	}
	
	/**
	 * 数据列表页面
	 * */
	public String query(){
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				String str = "";
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    Calendar lastDate = Calendar.getInstance();
			    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
			    str=sdf.format(lastDate.getTime());
			    String beginDate= str;
			    String endDate=DateUtil.format2Short(new Date());
				//订单时间
				String orderStartDate=request.getParameter("orderStartDate");
				String orderEndDate=request.getParameter("orderEndDate");
				if(CommonFunction.isNull(orderStartDate)){
					orderStartDate=beginDate;
				}
				if(CommonFunction.isNull(orderEndDate)){
					orderEndDate=endDate;
				}
				pageMap.put("orderStartDate", orderStartDate);
				pageMap.put("orderEndDate",orderEndDate);
				request.setAttribute("orderStartDate", orderStartDate);
				request.setAttribute("orderEndDate", orderEndDate);
				//生产时间
				String planStartDate=request.getParameter("planStartDate");
				String planEndDate=request.getParameter("planEndDate");
				if(CommonFunction.isNull(planStartDate)){
					planStartDate=beginDate;
				}
				if(CommonFunction.isNull(planEndDate)){
					planEndDate=endDate;
				}
				pageMap.put("planStartDate", planStartDate);
				pageMap.put("planEndDate",planEndDate);
				//生产计划编号
				String manufactureCode = request.getParameter("planCode");
				pageMap.put("manufactureCode", manufactureCode);
				//客户名称
				String cusName=request.getParameter("customerName");
				pageMap.put("customerName", cusName);
				//产品名称
				String productName = request.getParameter("productName");
				pageMap.put("productName", productName);
				//产品编号
				String productCode = request.getParameter("productCode");
				pageMap.put("productCode", productCode);
				//产品规格/型号
				String productTypespec = request.getParameter("productTypespec");
				pageMap.put("productTypespec", productTypespec);
				//查询生产计划
				return traceService.getAllProduceplanRelationOrdersDetail(pageMap);
			}
		});
		return "query";
	}
	/**
	 * 追溯订单
	 */
	public String queryOrder(){
		try {
			String orderId=request.getParameter("orderId");
			if(CommonFunction.isNull(orderId)){
				throw new BusinessException("销售订单id为空");
			}
			Long orderDeatilId = Long.parseLong(request.getParameter("orderId"));
			request.setAttribute("orderId", orderId);
			Produceplan plan=produceplanService.getProduceByOrderDetailId(orderDeatilId);
			if(CommonFunction.isNotNull(plan)){
				request.setAttribute("planId", plan.getId());
			}
			
			OrdersDetail detail=ordersService.getOrdersDetailById(orderDeatilId); 
			Orders order=ordersService.getOrderById(detail.getOrderId());
			Customer customer=customerService.getCustomerById(order.getCustomerId());
			request.setAttribute("cusName", customer.getName());
			request.setAttribute("orderCode", order.getOrderCodeAuto());
			request.setAttribute("detail", detail);
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "order";
	}
	/**
	 * 根据订单追溯计划
	 * @return
	 */
	public String queryPlan(){
		try {
			/*生产计划*/
			String planId=request.getParameter("planId");
			String orderId=request.getParameter("orderId");
			request.setAttribute("orderId", orderId);
			if(CommonFunction.isNull(planId)){
				throw new BusinessException("生产计划id为空");
			}
			Long todoId = Long.parseLong(request.getParameter("planId"));
			request.setAttribute("planId", todoId);
			Produceplan plan= produceplanService.getProduceplanById(todoId);
			request.setAttribute("plan", plan);
			/*销售订单*/
			if(CommonFunction.isNotNull(orderId)){
				Long orderDeatilId = Long.parseLong(orderId);
				OrdersDetail detail=ordersService.getOrdersDetailById(orderDeatilId); 
				Orders order=ordersService.getOrderById(detail.getOrderId());
				request.setAttribute("orderCode", order.getOrderCodeAuto());
				request.setAttribute("detail", detail);
				Customer customer=customerService.getCustomerById(order.getCustomerId());
				request.setAttribute("cusName", customer.getName());
			}
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "plan";
	}
	/**
	 * 根据计划追溯派工单
	 * @return
	 */
	public String queryDis() {
		String planId=request.getParameter("planId");
		String orderId=request.getParameter("orderId");
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				pageMap.put("planCode", request.getParameter("planCode"));
				return planTodoService.queryAllPlan(pageMap);
			}
		});
		List<Object[]> resultList =this.getList();
		
		if(!CollectionUtils.isEmpty(resultList)){
			Long producePlan[] =new Long[resultList.size()];
			int i=0;
			for (Object[] object  : resultList) {
				producePlan[i++]=((Produceplan)object[0]).getId();
			}
			List<ProduceplanTodo> plans=planTodoService.queryProduceplanTodoByProduceplanIds(producePlan);
			request.setAttribute("resultList", plans);
			if(!CollectionUtils.isEmpty(plans)){
				Map<Long,List<ProduceplanTodo>> productplanTodoMap  =new HashMap<Long, List<ProduceplanTodo>>();
				for (ProduceplanTodo productplanTodo : plans) {
					List<ProduceplanTodo> todos=productplanTodoMap.get(productplanTodo.getProduceplanId());
					if(todos==null){
						todos=new ArrayList<ProduceplanTodo>();
						productplanTodoMap.put(productplanTodo.getProduceplanId(),todos);
					}
					todos.add(productplanTodo);
				}
				request.setAttribute("planTodoMap", productplanTodoMap);
			}
		}
		request.setAttribute("planId", planId);
		request.setAttribute("orderId", orderId);
		request.setAttribute("planCode", request.getParameter("planCode"));
		try {
			/*生产计划*/
			Long todoId = Long.parseLong(request.getParameter("planId"));
			request.setAttribute("planId", todoId);
			Produceplan plan= produceplanService.getProduceplanById(todoId);
			request.setAttribute("plan", plan);
			/*销售订单*/
			if(CommonFunction.isNotNull(orderId)){
				Long orderDeatilId = Long.parseLong(orderId);
				OrdersDetail detail=ordersService.getOrdersDetailById(orderDeatilId); 
				Orders order=ordersService.getOrderById(detail.getOrderId());
				request.setAttribute("orderCode", order.getOrderCodeAuto());
				request.setAttribute("detail", detail);
				Customer customer=customerService.getCustomerById(order.getCustomerId());
				request.setAttribute("cusName", customer.getName());
			}
		} catch (Exception e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		
		return "plantodo";
	}
	/**
	 * 根据派工单追溯相关派工单详情
	 * @return
	 */
	public String claim(){
		try {
		String id=request.getParameter("todoId");
		if(CommonFunction.isNull(id)){
			throw new BusinessException("id为空");
		}
		Long todoId = Long.parseLong(id);
		ProduceplanTodo  produceplanTodo = planTodoService.queryPlanById(todoId);
		List<ProduceplanTodoClaim> todoClaims = produceplanTodoClaimService.queryTodoClaimByTodoId(todoId);

		request.setAttribute("produceplanTodo", produceplanTodo);
		request.setAttribute("todoClaims",todoClaims);
		request.setAttribute("todoId", todoId);
		
		
		String planId=request.getParameter("planId");
		String orderId=request.getParameter("orderId");
		request.setAttribute("planCode", request.getParameter("planCode"));
		request.setAttribute("planId", planId);
		request.setAttribute("orderId",orderId);
		
		/*生产计划*/
		Long plansId = Long.parseLong(request.getParameter("planId"));
		Produceplan plan= produceplanService.getProduceplanById(plansId);
		request.setAttribute("plan", plan);
		/*销售订单*/
		if(CommonFunction.isNotNull(orderId)){
			Long orderDeatilId = Long.parseLong(orderId);
			OrdersDetail detail=ordersService.getOrdersDetailById(orderDeatilId); 
			Orders order=ordersService.getOrderById(detail.getOrderId());
			request.setAttribute("orderCode", order.getOrderCodeAuto());
			request.setAttribute("detail", detail);	
			Customer customer=customerService.getCustomerById(order.getCustomerId());
			request.setAttribute("cusName", customer.getName());
		}
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		
		return "todoDetail";
	}
	/**
	 * 相关设备
	 * @return
	 */
	public String claimRelation(){
		queryClaimInfo();
		return "relationGos";
	}

	private void queryClaimInfo() {
		try {
			String id=request.getParameter("claimId");
			if(CommonFunction.isNull(id)){
				throw new BusinessException("id为空");
			}
			request.setAttribute("claimId", id);
			Long claimId= Long.parseLong(id);
			List<TodoClaimMaterial> maList=produceplanTodoClaimService.queryMaByToId(claimId);//相关物料
			List<TodoClaimEquip> eqList=produceplanTodoClaimService.queryEquipByToId(claimId);//相关设备
			
			request.setAttribute("maList", maList);
			request.setAttribute("eqList",eqList);
			
			String orderId=request.getParameter("orderId");
			String planId=request.getParameter("planId");
			request.setAttribute("planCode", request.getParameter("planCode"));
			request.setAttribute("planId",planId);
			request.setAttribute("todoId", request.getParameter("todoId"));
			request.setAttribute("orderId", orderId);
			/*派工单*/
			String toId=request.getParameter("todoId");
			Long todoId = Long.parseLong(toId);
			ProduceplanTodo  produceplanTodo = planTodoService.queryPlanById(todoId);
			List<ProduceplanTodoClaim> todoClaims = produceplanTodoClaimService.queryTodoClaimByTodoId(todoId);

			request.setAttribute("produceplanTodo", produceplanTodo);
			request.setAttribute("todoClaims",todoClaims);
			/*生产计划*/
			Long plansId = Long.parseLong(request.getParameter("planId"));
			Produceplan plan= produceplanService.getProduceplanById(plansId);
			request.setAttribute("plan", plan);
			/*销售订单*/
			if(CommonFunction.isNotNull(orderId)){
				Long orderDeatilId = Long.parseLong(orderId);
				OrdersDetail detail=ordersService.getOrdersDetailById(orderDeatilId); 
				Orders order=ordersService.getOrderById(detail.getOrderId());
				request.setAttribute("orderCode", order.getOrderCodeAuto());
				request.setAttribute("detail", detail);
				Customer customer=customerService.getCustomerById(order.getCustomerId());
				request.setAttribute("cusName", customer.getName());
			}
		} catch (Exception e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
	}
	/**
	 * 打印追溯结果
	 * @return
	 */
	public String queryPrintDis() throws BusinessException{
		queryClaimInfo(); 
		return "printDis";
	}
	public ProduceplanService getProduceplanService() {
		return produceplanService;
	}

	public void setProduceplanService(ProduceplanService produceplanService) {
		this.produceplanService = produceplanService;
	}

	public PlanTodoService getPlanTodoService() {
		return planTodoService;
	}

	public void setPlanTodoService(PlanTodoService planTodoService) {
		this.planTodoService = planTodoService;
	}

	public ProduceplanTodoClaimService getProduceplanTodoClaimService() {
		return produceplanTodoClaimService;
	}

	public void setProduceplanTodoClaimService(ProduceplanTodoClaimService produceplanTodoClaimService) {
		this.produceplanTodoClaimService = produceplanTodoClaimService;
	}

	public TraceService getTraceService() {
		return traceService;
	}

	public void setTraceService(TraceService traceService) {
		this.traceService = traceService;
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
	
}
