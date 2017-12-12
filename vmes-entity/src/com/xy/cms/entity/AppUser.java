package com.xy.cms.entity;

import java.util.Date;

/**
 * App应用账号表
 */
public class AppUser {
	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 员工id
	 */
	private Long empId;

	/**
	 * 添加时间
	 */
	private Date addDate;

	/**
	 * 使用状态 1：正常 0：停用
	 */
	private short status;

	/**
	 * 角色id
	 */
	private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
