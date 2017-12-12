package com.xy.cms.entity;

/**
 * 员工与部门表
 * */
public class EmployeeDeptart {
	/**
	 * 主键id
	 * */
	private Long id;
	
	/**
	 * 员工id
	 * */
	private Long employeeId;
	
	/**
	 * 部门id
	 * */
	private Long departId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	
}
