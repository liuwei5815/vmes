package com.xy.cms.entity;

import java.util.Date;

/**
 * @author 武汉夏宇信息
 */
public class Admin implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	private Long id;
	
	/**
	 * 帐户
	 */
	private String account;
	
	/**
	 * 密码
	 */
	private String pwd;
	
	/**
	 * 使用状态 1：正常 0：停用
	 */
	private short status;
	
	/**
	 * 创建时间
	 */
	private Date addDate;
	
	/**
	 * 修改时间
	 */
	private Date updateDate;
	
	private Long roleId;
	
	/**
	 * 员工id
	 * */
	private Long empId;

	public Admin(){}
	
	public Admin(Long id, String account, String pwd, short status,
			Date addDate, Date updateDate,Long empId) {
		super();
		this.id = id;
		this.account = account;
		this.pwd = pwd;
		this.status = status;
		this.addDate = addDate;
		this.updateDate = updateDate;
		this.empId = empId;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	
	
}
