package com.xy.apisql.common;

import java.util.List;
import java.util.Map;

import com.xy.cms.entity.Api;
import com.xy.cms.entity.AppUser;

/***
 * ApiSql
 * 
 * @author xiaojun
 *
 */
public class ApiSqlContext {

	// 当前正在处理的api
	private Api currentApi;

	private String apiSql;

	private Map<String, Object> namedParam;

	private List<ApiToken> apiTokens;

	private List<String> separators;

	private Map<Integer, Map<String, Long>> tableAlias;

	private Map<String, String> request;

	private AppUser sessionUser;

	public List<ApiToken> getApiTokens() {
		return apiTokens;
	}

	public void setApiTokens(List<ApiToken> apiTokens) {
		this.apiTokens = apiTokens;
	}

	public String getApiSql() {
		return apiSql;
	}

	public void setApiSql(String apiSql) {
		this.apiSql = apiSql;
	}

	public Map<Integer, Map<String, Long>> getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(Map<Integer, Map<String, Long>> tableAlias) {
		this.tableAlias = tableAlias;
	}

	public List<String> getSeparators() {
		return separators;
	}

	public void setSeparators(List<String> separators) {
		this.separators = separators;
	}

	public Map<String, String> getRequest() {
		return request;
	}

	public void setRequest(Map<String, String> request) {
		this.request = request;
	}

	public Map<String, Object> getNamedParam() {
		return namedParam;
	}

	public void setNamedParam(Map<String, Object> namedParam) {
		this.namedParam = namedParam;
	}

	public AppUser getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(AppUser sessionUser) {
		this.sessionUser = sessionUser;
	}

	public Api getCurrentApi() {
		return currentApi;
	}

	public void setCurrentApi(Api currentApi) {
		this.currentApi = currentApi;
	}

}
