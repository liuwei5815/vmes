package com.xy.admx.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xy.admx.bean.EmployeeBean;
import com.xy.admx.common.Constants;
import com.xy.admx.common.exception.BusinessException;
import com.xy.admx.common.exception.RestException;
import com.xy.admx.common.wx.SessionHandler;
import com.xy.admx.rest.base.BaseRest;
import com.xy.admx.rest.base.ResponseCode;
import com.xy.admx.rest.base.SuccessResponse;
import com.xy.admx.service.AppUserService;
import com.xy.apisql.db.TableColumnsService;
import com.xy.apisql.db.TablesService;
import com.xy.cms.entity.AppUser;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Employee;

@RestController
@RequestMapping("/auth")
public class AuthRest extends BaseRest {
	@Resource
	private AppUserService appUserService;
	@Resource
	private TablesService tabelsService;
	@Resource
	private TableColumnsService tableColumnsService;

	/**
	 * 普通的账号密码方式登录
	 * 
	 * @param request
	 * @param jsonObject
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/basic", method = RequestMethod.POST)
	public SuccessResponse login(HttpServletRequest request, @RequestBody JSONObject jsonObject) {

		String account = jsonObject.getString("account");
		if (StringUtils.isBlank(account)) {
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "缺少account参数");
		}
		String password = jsonObject.getString("password");
		if (StringUtils.isBlank(password)) {
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "缺少password参数");
		}
		AppUser appUser = appUserService.queryAppUserByAccountAndPwd(account, password);
		if (appUser == null) {
			throw new RestException(ResponseCode.BUSSINESS_EXCEPTION, "账号或密码错误");
		}
		appUser.setPassword(null);
		Employee emp = appUserService.get(Employee.class, appUser.getEmpId());
		if(emp == null){
			throw new RestException(ResponseCode.BUSSINESS_EXCEPTION, "员工信息不存在");
		}
		List<Department> depts = appUserService.getEmpDepts(appUser.getEmpId());
		StringBuffer detpsStr = new StringBuffer();
		for(int i = 0 ; i < depts.size() ;i++){
			if(i != 0 && i != depts.size() -1){
				detpsStr.append(depts.get(i).getName()).append(",");
			}else{
				detpsStr.append(depts.get(i).getName());
			}
		}
		EmployeeBean empBean = new EmployeeBean();
		BeanUtils.copyProperties(emp, empBean);
		empBean.setDepts(detpsStr.toString());
		
		if(appUser.getRoleId() != null){
			empBean.setPower(appUserService.queryAppRolePower(appUser.getRoleId()));
		}
		SessionHandler sessionHandler = new SessionHandler().generate3rdSession().putUserInfo(appUser);
		Map<String, Object> body = new HashMap<>();
		body.put(Constants.SESSION_TOKEN, sessionHandler.getSession_3rd());
		body.put("user", empBean);
		return new SuccessResponse(body);
	}
	

	/**
	 * 普通的账号密码方式登录
	 * 
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(path = "/chgpwd", method = RequestMethod.POST)
	public SuccessResponse changePwd(@RequestHeader("session_token") String sessionToken, HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BusinessException {
		String oldpwd = jsonObject.getString("oldpwd");
		if (StringUtils.isBlank(oldpwd)) {
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "缺少oldpwd参数");
		}
		String newpwd = jsonObject.getString("newpwd");
		if (StringUtils.isBlank(newpwd)) {
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "缺少newpwd参数");
		}
		AppUser user = SessionHandler.getAppUser(sessionToken);
		appUserService.changePwd(user, oldpwd, newpwd);
		return new SuccessResponse("");
	}

}
