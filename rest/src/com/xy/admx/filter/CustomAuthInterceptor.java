package com.xy.admx.filter;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xy.admx.common.Constants;
import com.xy.admx.common.exception.RestException;
import com.xy.admx.common.wx.SessionHandler;
import com.xy.admx.rest.base.ResponseCode;
import com.xy.admx.service.ApiAuthService;
import com.xy.admx.service.ApiService;
import com.xy.cms.entity.Api;
import com.xy.cms.entity.ApiAuth;
import com.xy.cms.entity.AppUser;

public class CustomAuthInterceptor implements HandlerInterceptor {
	

	@Resource
	private ApiAuthService apiAuthService;
	@Resource
	private ApiService apiService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {

	
		String uri = request.getRequestURI();
		int start = uri.indexOf("custom")+6;
		String path = uri.substring(start);
		String method = request.getMethod();
		List<Api> apis = apiService.getApi(path);
		Api invoke = null;
		if(org.springframework.util.CollectionUtils.isEmpty(apis))
			throw new RestException(ResponseCode.GENERAL_ERROR,"服务未找到,url=custom" + path + ",method=" + method);
		for(Api api : apis){
			if(api.getAction().toLowerCase().equals(method.toLowerCase())){
				invoke = api;
			}
		}
		if(invoke == null)
			throw new RestException(ResponseCode.ILLEGAL_METHOD,"访问方法错误");
		request.setAttribute("invoke", invoke);
		//如果该接口需要认证
		ApiAuth apiAuth = invoke.getApiAuth();
		if(apiAuth!=null){
			//如果
			Integer type= apiAuth.getType();
			if(type.equals(ApiAuth.Type.open.getCode())){
				return true;
			}
			String sessionToken =request.getHeader(Constants.SESSION_TOKEN);
			if(StringUtils.isBlank(sessionToken)){
				throw new RestException(ResponseCode.MISSING_SESSION_TOKEN);
			}
			
			// 认证从redis中取数据，判断该token是否合法
			AppUser appUser = SessionHandler.getAppUser(sessionToken);
			if(appUser==null){
				throw new RestException(ResponseCode.ILLEGAL_SESSION_TOKEN);
			}
			//如果此接口是
			if(type.equals(ApiAuth.Type.accounrandrole.getCode())){
				if(!apiAuthService.isAuth(appUser,invoke.getId())){
					throw new RestException(ResponseCode.NOT_AUTH);
				}
			}
		}
	
		return true;
	}


	

}
