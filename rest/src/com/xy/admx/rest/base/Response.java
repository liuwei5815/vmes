package com.xy.admx.rest.base;

public class Response {

	private String code;// 此处本准备用枚举代替，但是jackson对枚举的序列化支持并不是特别好

	private String error;
	
	public Response(ResponseCode reponseCode){
		this.code = reponseCode.getCode();
		this.error = reponseCode.getError();
	}
	
	public Response(String code,String error) {
		this.code = code;
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public String getError() {
		return error;
	}
}
