package com.xy.admx.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xy.admx.core.service.base.BaseService;
import com.xy.admx.rest.base.SuccessResponse;
import com.xy.cms.entity.Api;
import com.xy.cms.entity.AppUser;

public interface ApiService extends BaseService {

	public SuccessResponse executeApi(HttpServletRequest request, Api api, AppUser appUser, Integer currentPage,
			Integer rows) throws Exception;

	public SuccessResponse executeApi(Map<String, String> request, Api api, AppUser appUser, Integer currentPage,
			Integer rows);

	public List<Api> getApi(String path);

	/**
	 * 查询api
	 * 
	 * @param url
	 * @param method
	 *            POST/GET
	 * @return
	 */
	public Api getApi(String url, String method);
}
