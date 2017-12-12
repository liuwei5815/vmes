package com.xy.admx.common.exception;


/**
 * 关于数据库操作所抛出的异常
 * @author dinggang
 * @since 2010/04/20
 */
public class DBException extends Exception {
	
	private String message = null;
	public static void main(String[] args) {
		
	}
	public DBException(String message){
		super(message);
		this.message = message;
	}
	
	public DBException(String message,Throwable t){
		super(message,t);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}