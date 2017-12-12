package com.xy.admx.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xy.admx.common.RequestUtil;
import com.xy.admx.common.base.PageQEntity;
import com.xy.admx.common.exception.RestException;
import com.xy.admx.common.wx.SessionHandler;
import com.xy.admx.rest.base.BaseRest;
import com.xy.admx.rest.base.ResponseCode;
import com.xy.admx.rest.base.SuccessResponse;
import com.xy.admx.service.ApiService;
import com.xy.admx.service.PlanService;
import com.xy.cms.entity.Api;
import com.xy.cms.entity.AppUser;
import com.xy.cms.entity.Orders;
import com.xy.cms.entity.OrdersDetail.OrdersDetailStatus;

/**
 * 生产计划业务
 * @author dg
 *
 */
@RestController
@RequestMapping("/plan")
public class PlanRest extends BaseRest {

	@Resource
	private PlanService planService;
	private @Resource ApiService apiService;

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
		planService.queryPlanPage(pageEntity, requests);
		return new SuccessResponse(pageEntity);
	}
	
	/**
	 * 生产计划报工
	 * 
	 * 同时将销售订单明细状态更新为已完成
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/finish",method = RequestMethod.POST)
	public SuccessResponse addequip(HttpServletRequest request, @RequestHeader(required = false) String session_token,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows)
			throws Exception {
		AppUser appUser = SessionHandler.getAppUser(session_token);
		Map requests = null;
		String requestBody = RequestUtil.getRequetBody(request);
		if (StringUtils.isBlank(requestBody)) {
			requests = new HashMap<>();
		} else {
			requests = JSONObject.parseObject(requestBody, Map.class);
		}
		Api finishApi = apiService.getApi("/plan/finish", "POST");
		
		SuccessResponse finishApiRes = apiService.executeApi(requests, finishApi, appUser, page, rows);
		if(finishApiRes.getCode().equals(ResponseCode.SUCCESS.getCode())){
			Api ordersfinishApi = apiService.getApi("/orders/finish", "POST");
			apiService.executeApi(requests, ordersfinishApi, appUser, page, rows);
			
			//订单明细分三种状态，一种状态是全部标已完成，那么订单状态变成已完成，
			//第二种是全部变成已取消了，该订单的状态为已取消，
			//第三种是有些已完成，有些已取消，没有其它状态，那就显示为已完成。
			Long planid = Long.valueOf(requests.get("id").toString());
			Orders orders = planService.getOrderByPlanid(planid);
			if(!planService.hasNotProduce(orders.getId())){//如果都已经安排生产验证完成状态
				List states = planService.queryStatesOfOrderDetails(orders.getId());
				System.out.println(states.contains((short)OrdersDetailStatus.awaiting.getCode()));
				System.out.println(states.contains((short)OrdersDetailStatus.ongoing.getCode()));
				if(!states.contains((short)OrdersDetailStatus.awaiting.getCode()) && !states.contains((short)OrdersDetailStatus.ongoing.getCode())){
					if(!states.contains((short)OrdersDetailStatus.finished.getCode())){//如果不包含完成的标记为取消
						planService.updatePlanState(Orders.State.canceled.getCode(), orders.getId());
					}else{//标记为完成
						planService.updatePlanState(Orders.State.finished.getCode(), orders.getId());
					}
				}
			}
		}
		return finishApiRes;
	}
	
}
