package com.xy.admx.rest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xy.admx.common.RequestUtil;
import com.xy.admx.common.exception.RestException;
import com.xy.admx.common.wx.SessionHandler;
import com.xy.admx.rest.base.BaseRest;
import com.xy.admx.rest.base.ResponseCode;
import com.xy.admx.rest.base.SuccessResponse;
import com.xy.admx.service.ApiService;
import com.xy.cms.entity.Api;
import com.xy.cms.entity.AppUser;
import com.xy.cms.entity.ProduceplanTodoClaim;

/**
 * 员工报工
 * 
 * @author dg
 *
 */
@RestController
@RequestMapping("/todo")
public class TodoRest extends BaseRest {

	private @Resource ApiService apiService;
	
	/**
	 * 扫码派工单开始任务
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/claim/add",method = RequestMethod.POST)
	public SuccessResponse addClaim(HttpServletRequest request, @RequestHeader(required = false) String session_token,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows)
			throws Exception {
		Map requests = null;
		String requestBody = RequestUtil.getRequetBody(request);
		if (StringUtils.isBlank(requestBody)) {
			requests = new HashMap<>();
		} else {
			requests = JSONObject.parseObject(requestBody, Map.class);
		}
		
		AppUser appUser = SessionHandler.getAppUser(session_token);
		//报工单关联设备信息
		Api getTodoApi = apiService.getApi("/gettodobyqrcode", "POST");
		//检查报工中是否已添加该设备
		Api addClaimApi = apiService.getApi("/claim/add", "POST");
		
		SuccessResponse getTodoRes = apiService.executeApi(requests, getTodoApi, appUser, page, rows);
		if(getTodoRes.getCode().equals(ResponseCode.SUCCESS.getCode())){
			JSONObject res = JSON.parseObject(JSON.toJSONString(getTodoRes.getBody()));
			if(res.getJSONArray("list") != null && res.getJSONArray("list").size() == 0){
				throw new RestException(ResponseCode.BUSSINESS_EXCEPTION,"没有找到派工单信息");
			}else{
				JSONObject todoJson = (JSONObject)(res.getJSONArray("list").get(0));
				if(StringUtils.isBlank(todoJson.getString("process"))){
					Api completeTodoApi = apiService.getApi("/completeTodo", "POST");
					SuccessResponse completeTodoApiRes = apiService.executeApi(requests, completeTodoApi, appUser, page, rows);
					if(completeTodoApiRes.getCode().equals(ResponseCode.SUCCESS.getCode())){
						//将关联的生产计置为进行中
						Api udpateStateApi = apiService.getApi("/plan/udpateState", "POST");
						apiService.executeApi(requests, udpateStateApi, appUser, page, rows);
					}
					
				}
			}
		}
		return apiService.executeApi(requests, addClaimApi, appUser, page, rows);
	}

	/**
	 * 添加相关设备
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/addequip",method = RequestMethod.POST)
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
		//报工单关联设备信息
		Api addEquipApi = apiService.getApi("/claim/addequip", "POST");
		//检查报工中是否已添加该设备
		Api existsEquip = apiService.getApi("/cliam/existsequip", "POST");
		
		SuccessResponse existsEquipRes = apiService.executeApi(requests, existsEquip, appUser, page, rows);
		if(existsEquipRes.getCode().equals(ResponseCode.SUCCESS.getCode())){
			JSONObject res = JSON.parseObject(JSON.toJSONString(existsEquipRes.getBody()));
			System.out.println(res.getJSONArray("list"));
			if(res.getJSONArray("list") != null && res.getJSONArray("list").size() > 0){
				throw new RestException(ResponseCode.BUSSINESS_EXCEPTION,"已经关联了该设备不能重复添加");
			}
		}
		return apiService.executeApi(requests, addEquipApi, appUser, page, rows);
	}
	
	/**
	 * 添加相关物料
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/addmaterial",method = RequestMethod.POST)
	public SuccessResponse addeMaterial(HttpServletRequest request, @RequestHeader(required = false) String session_token,
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
		//报工单关联物料
		
	
		String addApiUrl = "/claim/addmaterial";
		//检查报工中是否已添加该物料
		if(requests.get("batchno")!=null){
		
			String existsmaterialUrl = "/cliam/existsmaterial";
			Api existsApi = apiService.getApi(existsmaterialUrl, "POST");
			SuccessResponse existsRes = apiService.executeApi(requests, existsApi, appUser, page, rows);
			if(existsRes.getCode().equals(ResponseCode.SUCCESS.getCode())){
				JSONObject res = JSON.parseObject(JSON.toJSONString(existsRes.getBody()));
				System.out.println(res.getJSONArray("list"));
				if(res.getJSONArray("list") != null && res.getJSONArray("list").size() > 0){
					throw new RestException(ResponseCode.BUSSINESS_EXCEPTION,"已经关联了该物料不能重复添加");
				}
			}
		}
		else{
			addApiUrl+="Nobatchno";
		}

		Api addApi = apiService.getApi(addApiUrl, "POST");
	
		return apiService.executeApi(requests, addApi, appUser, page, rows);
	}
	
	/**
	 * 报工
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/claim",method = RequestMethod.POST)
	public SuccessResponse claim(HttpServletRequest request, @RequestHeader(required = false) String session_token,
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
		//报工单关联物料
		Api claimApi = apiService.getApi("/claim", "POST");
		//检查报工中是否已添加该物料
		Api getstateApi = apiService.getApi("/claim/getstate", "POST");
		
		SuccessResponse getClaimStateRes = apiService.executeApi(requests, getstateApi, appUser, page, rows);
		if(getClaimStateRes.getCode().equals(ResponseCode.SUCCESS.getCode())){
			JSONObject res = JSON.parseObject(JSON.toJSONString(getClaimStateRes.getBody()));
			System.out.println(res.getJSONArray("list"));
			if(res.getJSONArray("list") != null && res.getJSONArray("list").size() > 0){
				int state = ((JSONObject)(res.getJSONArray("list").get(0))).getIntValue("state");
				
				if(ProduceplanTodoClaim.State.ongoing.getCode() == state){
					//报工
					SuccessResponse succ = apiService.executeApi(requests, claimApi, appUser, page, rows);
					
					//检查报工中是否已添加该物料
					Api getTodoApi = apiService.getApi("/todo/getbyclaimid", "POST");
					SuccessResponse getTodoRes = apiService.executeApi(requests, getTodoApi, appUser, page, rows);
					if(getTodoRes.getCode().equals(ResponseCode.SUCCESS.getCode())){
						JSONObject getTodoResBody = JSON.parseObject(JSON.toJSONString(getTodoRes.getBody()));
						if(getTodoResBody.getJSONArray("list") != null && getTodoResBody.getJSONArray("list").size() > 0){
							JSONObject todoJson = (JSONObject)getTodoResBody.getJSONArray("list").get(0);
							int todo_plan_num = todoJson.getIntValue("todo_plan_num");
							//更新派工单中合合数量/不合格数量
							Api sumqualifiednumApi = apiService.getApi("/todo/sumqualifiednum", "POST");
							requests.put("todoid", todoJson.getLong("id"));
							SuccessResponse sumRes = apiService.executeApi(requests, sumqualifiednumApi, appUser, page, rows);
							if(sumRes.getCode().equals(ResponseCode.SUCCESS.getCode())){
								JSONObject sumResBody = JSON.parseObject(JSON.toJSONString(sumRes.getBody()));
								if(sumResBody.getJSONArray("list") != null && sumResBody.getJSONArray("list").size() > 0){
									int sumqualifiednum = ((JSONObject)(sumResBody.getJSONArray("list").get(0))).getIntValue("sumqualifiednum");
									if(sumqualifiednum >= todo_plan_num){//如果合格数已>=计划的，将派工单标为已完成
										//更新为已完成
										Api updateStateAndNumApi = apiService.getApi("/todo/finish", "POST");
										apiService.executeApi(requests, updateStateAndNumApi, appUser, page, rows);
										//如果所有的派工单都变为已完成了，将生产计划置为已完成
										
										//如果所有生产计划都变为已完成了,将销售订单置为已完成
									}else{
										//公统计数量(合格数、不合格数)
										Api updateNumApi = apiService.getApi("/todo/updateNums", "POST");
										apiService.executeApi(requests, updateNumApi, appUser, page, rows);
									}
								}
							}
						}
					}
					return succ;
				}else if(ProduceplanTodoClaim.State.finished.getCode() == state){
					throw new RestException(ResponseCode.BUSSINESS_EXCEPTION,"该任务已完成不能再报工");
				}else if(ProduceplanTodoClaim.State.canceled.getCode() == state){
					throw new RestException(ResponseCode.BUSSINESS_EXCEPTION,"该任务已取消不能再报工");
				}
			}else{
				throw new RestException(ResponseCode.BUSSINESS_EXCEPTION,"报工信息不存在");
			}
		}
		return getClaimStateRes;
	}
	
	/**
	 * 消取报工
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/cancel",method = RequestMethod.POST)
	public SuccessResponse cancel(HttpServletRequest request, @RequestHeader(required = false) String session_token,
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
		//报工单关联物料
		Api cancelApi = apiService.getApi("/claim/cancel", "POST");
		//检查报工中是否已添加该物料
		Api getstateApi = apiService.getApi("/claim/getstate", "POST");
		
		SuccessResponse getstateRes = apiService.executeApi(requests, getstateApi, appUser, page, rows);
		if(getstateRes.getCode().equals(ResponseCode.SUCCESS.getCode())){
			JSONObject res = JSON.parseObject(JSON.toJSONString(getstateRes.getBody()));
			System.out.println(res.getJSONArray("list"));
			if(res.getJSONArray("list") != null && res.getJSONArray("list").size() > 0){
				int state = ((JSONObject)(res.getJSONArray("list").get(0))).getIntValue("state");
				if(ProduceplanTodoClaim.State.ongoing.getCode() == state){
					return apiService.executeApi(requests, cancelApi, appUser, page, rows);
				}else if(ProduceplanTodoClaim.State.finished.getCode() == state){
					throw new RestException(ResponseCode.BUSSINESS_EXCEPTION,"该任务已完成不能再报工");
				}else if(ProduceplanTodoClaim.State.canceled.getCode() == state){
					throw new RestException(ResponseCode.BUSSINESS_EXCEPTION,"该任务已取消不能再报工");
				}else{
					throw new RestException(ResponseCode.BUSSINESS_EXCEPTION,"任务状态不正确");
				}
			}else{
				throw new RestException(ResponseCode.BUSSINESS_EXCEPTION,"报工信息不存在");
			}
		}
		return getstateRes;
	}
}
