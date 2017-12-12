package com.xy.apisql.common;


public class ApiSqlUtil {
	/**
	 * 是否字段
	 * @return
	 */
	public static boolean isColumnsToken(String token){
		String[] columns=token.split("\\.");
		return columns.length>1 && !isValueToken(token);
	}  
	/**
	 * 是否是请求参数
	 * @param token
	 * @return
	 */
	public static boolean isRequestParamToken(String token){
		return token.startsWith(ApiSqlConstans.REQUEST_PREFIX);
	}
	/**
	 * 是否是系统内置变量
	 * @param token
	 * @return
	 */
	public static boolean isAdmxParamToken(String token){
		return token.startsWith(ApiSqlConstans.ADMX_PARAM_PREFIX);
	}
	/***
	 * 是否是指类型
	 * @param token
	 * @return
	 */
	public static boolean isValueToken(String token){
		return isAdmxParamToken(token) || isRequestParamToken(token);
	}

}
