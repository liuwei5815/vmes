package com.xy.cms.bean;

import java.util.Date;

import com.xy.cms.entity.Admin;

/**
 * @author 武汉夏宇信息
 */
public class AdminBean extends Admin implements java.io.Serializable{

	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
