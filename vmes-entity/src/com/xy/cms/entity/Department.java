package com.xy.cms.entity;

import java.util.Date;
import java.util.Set;

/**
 * 
 * 组织机构
 * */
public class Department implements java.io.Serializable{
	/**
	 *主键id
	 */
	private Long id;
	
	/**
	 *父级部门id
	 */
	private Long pid;
	
	/**
	 *部门名称
	 */
	private String name;
	
	/**
	 * 使用状态 1：正常 0：停用
	 */
	private Short status;
	
	/**
	 * 创建人
	 * */
	private Long createby;
	
	/**
	 * 添加时间
	 * */
	private Date addDate;
	
	/**
	 * 修改时间
	 */
	private Date updateDate;
	
	/**
	 * 排序字段  
	 * */
	private Integer orderby;

	/**
	 * 部门级别
	 * @return
	 */
	private Integer level;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Long getCreateby() {
		return createby;
	}

	public void setCreateby(Long createby) {
		this.createby = createby;
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

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Department [name=" + name + "]";
	}
}
