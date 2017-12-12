package com.xy.cms.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.xy.cms.entity.Admin;

public class SessionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 登录用户信息
	 */
	public Admin admin;

	// 权限
	private List<Integer> permission;

	// 操作人员 ID
	private Integer userId;

	private String userName;
	
	private String userCode;

	// 上次登录IP
	private String lastLoginIp;

	// 登录次数
	private long loginCount;

	/**
	 * 当前登录IP
	 */
	private String currLoginIp;

	// 上次登录时间
	private Date lastLoginTime;

	private Date currLoginTime;

	public Date getCurrLoginTime() {
		return currLoginTime;
	}

	public void setCurrLoginTime(Date currLoginTime) {
		this.currLoginTime = currLoginTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public List<Integer> getPermission() {
		return permission;
	}

	public void setPermission(List<Integer> permission) {
		this.permission = permission;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getCurrLoginIp() {
		return currLoginIp;
	}

	public void setCurrLoginIp(String currLoginIp) {
		this.currLoginIp = currLoginIp;
	}

	public long getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(long loginCount) {
		this.loginCount = loginCount;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
}
