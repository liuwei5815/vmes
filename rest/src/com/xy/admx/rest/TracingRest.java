package com.xy.admx.rest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xy.admx.common.CommonFunction;
import com.xy.admx.common.DateUtil;
import com.xy.admx.common.base.PageQEntity;
import com.xy.admx.common.exception.RestException;
import com.xy.admx.rest.base.BaseRest;
import com.xy.admx.rest.base.ResponseCode;
import com.xy.admx.rest.base.SuccessResponse;
import com.xy.admx.service.OrdersService;
import com.xy.admx.service.PlanService;
import com.xy.cms.entity.ProduceplanTodo;
import com.xy.cms.entity.ProduceplanTodoClaim;

/**
 * 追溯相关业务
 * 
 * @author dg
 *
 */
@RestController
@RequestMapping("/tracing")
public class TracingRest extends BaseRest {

	@Resource
	private OrdersService ordersService;
	
	private @Resource PlanService planService;

	/**
	 * 查询销售定单
	 * 
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public SuccessResponse query(HttpServletRequest request, @RequestBody Map requests, PageQEntity pageEntity)
			throws Exception {
		Map queryMap = new HashMap();
		if(!requests.containsKey("state")){
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "缺少state参数");
		}
		ordersService.queryOrdersPage(pageEntity, requests);
		return new SuccessResponse(pageEntity);
	}
	

	/**
	 * 根据销售订单
	 * 查询生产计划
	 * @param request
	 * @param odid 订单明细id
	 * @param jsonObject
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/plan", method = RequestMethod.POST)
	public SuccessResponse queryPlanBySalesId(HttpServletRequest request, @RequestBody Map requests, PageQEntity pageEntity)
			throws Exception {
		Map queryMap = new HashMap();
		if(requests.get("odid") == null){
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "odid参数缺失");
		}
		if(!StringUtils.isNumeric(requests.get("odid").toString())){
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "odid类型不正确");
		}
		planService.queryPlanPage(pageEntity, requests);
		return new SuccessResponse(pageEntity);
	}
	
	/**
	 * 根据生产计划
	 * 查询派工单
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/plan/todolist", method = RequestMethod.POST)
	public SuccessResponse queryTodoListByPlanId(HttpServletRequest request, @RequestBody Map requests, PageQEntity pageEntity)
			throws Exception {
		Map queryMap = new HashMap();
		if(requests.get("planid") == null){
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "planid参数缺失");
		}
		if(!StringUtils.isNumeric(requests.get("planid").toString())){
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "planid类型不正确");
		}
		planService.queryTodoPage(pageEntity, requests);
		return new SuccessResponse(pageEntity);
	}
	
	/**
	 * 根据生产计划
	 * 查询派工单详情
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/plan/claim", method = RequestMethod.POST)
	public SuccessResponse queryClaimByTodo(HttpServletRequest request, @RequestBody Map requests, PageQEntity pageEntity)
			throws Exception {
		Map queryMap = new HashMap();
		if(requests.get("todoid") == null){
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "todoid参数缺失");
		}
		if(!StringUtils.isNumeric(requests.get("planid").toString())){
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "todoid类型不正确");
		}
		planService.queryClaimPage(pageEntity, requests);
		return new SuccessResponse(pageEntity);
	}
	/**
	 * 根据派工单id查询派工详情
	 */
	@ResponseBody
	@RequestMapping(value="/claim/getbyid",method = RequestMethod.POST)
	public SuccessResponse queryCalimByTodoId(HttpServletRequest request, @RequestBody Map requests, PageQEntity pageEntity)throws Exception{
		Map queryMap = new HashMap();
		String claimid=(String)requests.get("claimid");
		if(CommonFunction.isNull(claimid)){
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "claimid参数缺失");
		}
		Long todoId = Long.parseLong(claimid);
		List<ProduceplanTodoClaim> todoClaims = planService.queryTodoClaimByTodoId(todoId);
		return new SuccessResponse(todoClaims);
	}
	/**
	 * 查询设备
	 */
	@ResponseBody
	@RequestMapping(value="/equipment",method=RequestMethod.POST)
	public SuccessResponse queryEquipment(HttpServletRequest request, @RequestBody Map requests)throws Exception{
		String equipmentId=(String)requests.get("equipmentId");
		if(requests.get("equipmentId") == null){
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "equipmentId参数缺失");
		}
		@SuppressWarnings("rawtypes")
		List<Map> list=planService.queryEquipmentDataById(Long.parseLong(equipmentId));
		if(CommonFunction.isNotNull(list)){
			for (Map map : list) {
				Timestamp  obj=(Timestamp )map.get("receivetime");
				if(CommonFunction.isNotNull(obj)){
					Date date = new Date();
					date=obj;
					Long now=new Date().getTime();
 					if((now-date.getTime())/(1000*60)>15){
 						map.put("isOnline", "false");
 					}else{
 						map.put("isOnline", "true");
 					}
				}
			}
		}
		return new SuccessResponse(list);
	}
}

