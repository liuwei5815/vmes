package com.xy.apisql.common;

import java.util.Map;

public class ApiSqlResult {
	private String sql;
	private Map<String, Object> namedParam;
	private ApiSqlType apiSqlType;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Map<String, Object> getNamedParam() {
		return namedParam;
	}

	public void setNamedParam(Map<String, Object> namedParam) {
		this.namedParam = namedParam;
	}

	public ApiSqlType getApiSqlType() {
		return apiSqlType;
	}

	public void setApiSqlType(ApiSqlType apiSqlType) {
		this.apiSqlType = apiSqlType;
	}

	public static enum ApiSqlType {
		INSERT, DELETE, UPDATE, SELECT,
	}

}
