package com.xy.cms.entity;

import java.io.Serializable;

public class AppRolePower implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long appRoleId;
	
	private Long appModularId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAppRoleId() {
		return appRoleId;
	}

	public void setAppRoleId(Long appRoleId) {
		this.appRoleId = appRoleId;
	}

	public Long getAppModularId() {
		return appModularId;
	}

	public void setAppModularId(Long appModularId) {
		this.appModularId = appModularId;
	}
	
}
