package com.xy.cms.entity;

import java.io.Serializable;

public class ApiAuth implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 321L;
	private Long id;
	private Api api;
	private Integer type;
	
	/**
	 * 类型
	 * @author dg
	 *
	 */
	public enum Type {
		// 1:公开，2:登录用户,3:指定用户或指定角色
		open(1, "公开"), login(2, "登录用户"), accounrandrole(3, "指定用户或指定角色");
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
	public Api getApi() {
		return api;
	}
	public void setApi(Api api) {
		this.api = api;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
