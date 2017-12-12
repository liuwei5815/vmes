package com.xy.admx.common.exception;

public class NotEnoughRightsException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 13424242L;
	
	public NotEnoughRightsException(String message) {
		super(message);
	}
	
	public NotEnoughRightsException(String message,Throwable t) {
		super(message,t);
	}
}
