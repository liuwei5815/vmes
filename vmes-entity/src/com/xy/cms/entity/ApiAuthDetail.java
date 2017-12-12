package com.xy.cms.entity;

public class ApiAuthDetail {
	private Long id;
	private ApiAuth apiAuth;
	private Integer type;
	private Long value;
	
	/**
	 * 类型
	 * @author dg
	 *
	 */
	public enum Type {
		// 1:账号，2:角色
		account(1, "账号"), role(2, "角色");
		private String msg;
		private int code;

		private Type(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getMsg() {
			return msg;
		}

		public int getCode() {
			return code;
		}

	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ApiAuth getApiAuth() {
		return apiAuth;
	}
	public void setApiAuth(ApiAuth apiAuth) {
		this.apiAuth = apiAuth;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	
	
}