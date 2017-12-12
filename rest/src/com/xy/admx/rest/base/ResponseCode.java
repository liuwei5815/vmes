package com.xy.admx.rest.base;

public enum ResponseCode {
	
	SUCCESS("0",""),
	GENERAL_ERROR("-1","系统繁忙,请重试"),
	ILLEGAL_APPKEY("111","不合法的appkey"),
	ILLEGAL_ACCESS_ID("101","不合法的accessid或accessSecret"),
	ILLEGAL_ACCESS_TOKEN("102","不合法的access_token，请开发者认真比对access_token的有效性（如是否过期）"),
	ILLEGAL_PARAM("103","不合法的请求参数"),
	ILLEGAL_METHOD("104","不合法的请求方式"),
	ILLEGAL_BODY("105","不合法的请求包体,需要json格式"),
	ILLEGAL_SESSION_TOKEN("106","不合法的session_token"),
	MEDIATYPE_NOTSupported("106","不支持的请求类型,需要application/json"),
	BUSSINESS_EXCEPTION("107","业务检查性错误"),
	CUSTOM_API_ERROR("108","自定义服务配置错误"),
	SQL_EXCEPTION("109","SQL异常"),
	SQL_EXCEPTION_MISSING_SQL("110","API缺少SQL"),
	MISSING_ACCESS_ID("301","缺少accessid"),
	MISSING_ACCESS_TOKEN("302","缺少secret参数"),
	TOKEN_TIME_OUT("303","access_token超时，请检查access_token的有效期"),
	MISSING_APP_KEY("304","请求头缺少appkey"),
	MISSING_SESSION_TOKEN("304","缺少session_token参数"),
	GET_REQUIRED("401","需要GET请求"),
	POST_REQUIRED("402","需要POST请求"),
	HTTPS_REQUIRED("403","需要HTTPS请求"),
	WXAPI_CONECT_ERROR("405","微信服务器连接失败"),
	OUT_OF_LIMIT("501","接口调用超过限制"),
	FREQUENT_OPERATION("502","API调用太频繁，请稍候再试"),
	ILLEGAL_URL("109","不合法的域名"),
	NOT_AUTH("110","没有权限访问");
	
	private String code;
	
	private String error;
	
	private ResponseCode(String code,String error){
		this.code=code;
		this.error = error;
	}
	
	@Override
	public String toString() {
		return this.code;
	}

	public String getError() {
		return error;
	}

	public String getCode() {
		return code;
	}
}
