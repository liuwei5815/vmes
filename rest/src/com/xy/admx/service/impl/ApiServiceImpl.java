package com.xy.admx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xy.admx.common.RequestUtil;
import com.xy.admx.common.base.PageQEntity;
import com.xy.admx.common.exception.RestException;
import com.xy.admx.core.service.base.BaseServiceImpl;
import com.xy.admx.rest.base.ResponseCode;
import com.xy.admx.rest.base.SuccessResponse;
import com.xy.admx.service.ApiService;
import com.xy.apisql.ApiSqlParseFactory;
import com.xy.apisql.common.ApiSqlResult;
import com.xy.apisql.common.ApiSqlResult.ApiSqlType;
import com.xy.cms.entity.Api;
import com.xy.cms.entity.AppUser;

@Service
public class ApiServiceImpl extends BaseServiceImpl implements ApiService {

	@Resource
	private ApiSqlParseFactory apiSqlParseFactory;

	@Override
	public SuccessResponse executeApi(HttpServletRequest request, Api api, AppUser appUser, Integer currentPage,
			Integer rows) throws Exception {
		String method = request.getMethod();
		Map requestParamsmap = null;
		switch (method) {
		case "GET": {
			requestParamsmap = request.getParameterMap();
			break;
		}
		case "POST": {
			String requestBody = RequestUtil.getRequetBody(request);
			if (StringUtils.isBlank(requestBody)) {
				requestParamsmap = new HashMap<>();
			} else {
				requestParamsmap = JSONObject.parseObject(requestBody, Map.class);
			}
		}
		}
		return executeApi(requestParamsmap, api, appUser, currentPage, rows);

	}

	public SuccessResponse executeApi(Map<String, String> request, Api api, AppUser appUser, Integer currentPage,
			Integer rows) {

		Long userId = appUser == null ? null : appUser.getId();
		if (api == null || api.getApiSql() == null) {
			throw new RestException(ResponseCode.SQL_EXCEPTION_MISSING_SQL);
		}
		ApiSqlResult apiSqlResult = apiSqlParseFactory.forMatApiSql(api, appUser, request);
		String apiSql = apiSqlResult.getSql();
		Map<String, Object> map = apiSqlResult.getNamedParam();

		if (apiSqlResult.getApiSqlType() == ApiSqlType.SELECT) {
			PageQEntity pageQEntity = new PageQEntity();
			pageQEntity.setCurrentPage(currentPage);
			pageQEntity.setPerPageRows(rows);
			pageQEntity.setList(this.getListBySQLToMap(apiSql, map));
			return new SuccessResponse(pageQEntity);
		}
		if (apiSqlResult.getApiSqlType() == ApiSqlType.UPDATE || apiSqlResult.getApiSqlType() == ApiSqlType.DELETE) {
			Map body = new HashMap<>();
			body.put("effect", this.executeSQL(apiSqlResult.getSql(), map));
			return new SuccessResponse(body);
		} else if (apiSqlResult.getApiSqlType() == ApiSqlType.INSERT) {
			int i = this.executeSQL(apiSqlResult.getSql(), map);
			Map body = new HashMap<>();
			List list = this.getListBySQL("select @@identity");
			if (list.size() > 0) {
				body.put("primarykey", list.get(0));
			} else {
				body.put("primarykeys", list);
			}
			return new SuccessResponse(body);
		}

		return null;
	}

	@Override
	public List<Api> getApi(String path) {
		StringBuilder hql = new StringBuilder(" from Api where api=:url");
		Map<String, Object> pageMap = new HashMap<>();
		pageMap.put("url", path);
		return this.getList(hql.toString(), pageMap);
	}

	public Api getApi(String path, String method) {
		List<Api> apis = getApi(path);
		if (org.springframework.util.CollectionUtils.isEmpty(apis))
			throw new RestException(ResponseCode.GENERAL_ERROR, "服务未找到");
		for (Api api : apis) {
			if (api.getAction().toLowerCase().equals(method.toLowerCase())) {
				return api;
			}
		}
		throw new RestException(ResponseCode.ILLEGAL_METHOD, "访问方法错误");
	}

}
