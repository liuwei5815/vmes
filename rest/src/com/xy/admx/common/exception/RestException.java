package com.xy.admx.common.exception;

import com.xy.admx.rest.base.ResponseCode;

public class RestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 132L;
	private String message;
	private String code;

	public RestException(ResponseCode responseCode) {
		this.code = responseCode.getCode();
		this.message = responseCode.getError();
	}

	public RestException(ResponseCode responseCode, String message) {
		this(responseCode, message, null);
	}

	public RestException(ResponseCode responseCode, String message, Throwable t) {
		super(t);
		this.code = responseCode.getCode();
		this.message = message;
	}
	public RestException(String responseCode, String message) {
		this.code = responseCode;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}

}
