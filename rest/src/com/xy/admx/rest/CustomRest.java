package com.xy.admx.rest;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xy.admx.common.wx.SessionHandler;
import com.xy.admx.rest.base.BaseRest;
import com.xy.admx.rest.base.SuccessResponse;
import com.xy.admx.service.ApiService;
import com.xy.cms.entity.Api;
import com.xy.cms.entity.AppUser;

/**
 * 用户自定义接口
 * 
 * @author dg
 *
 */
@RestController
@RequestMapping("/custom")
public class CustomRest extends BaseRest {
	private Logger logger = Logger.getLogger(CustomRest.class);

	@Resource
	private ApiService apiService;

	/**
	 * 自定义接口统一入口
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/**")
	@ResponseBody
	public SuccessResponse service(HttpServletRequest request, @RequestHeader(required = false) String session_token,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows) throws Exception {
		AppUser appUser = SessionHandler.getAppUser(session_token);
		Api invoke = (Api) request.getAttribute("invoke");
		return apiService.executeApi(request, invoke, appUser, page, rows);
	}

}
