package com.xy.admx.rest.base;

public class SuccessResponse extends Response {

	private Object body;
	
	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
	
	public SuccessResponse(Object body) {
		super(ResponseCode.SUCCESS);
		this.setBody(body);
	}
}
