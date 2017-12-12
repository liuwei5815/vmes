package com.xy.cms.entity;

import java.util.Date;

/**
 * 省市表
 * */
public class Region {
	/**
	 * 主键id
	 * */
	private Long id;
	
	/**
	 * 名称
	 * */
	private String name;
	
	/**
	 * 类型,'p'代表省,'c'代表市,'a'代表区  
	 * */
	private String type;
	
	/**
	 * 父级id
	 * */
	private Long Parentid;
	
	/**
	 * 添加时间
	 * */
	private Date addDate;
	
	/**
	 * 修改时间
	 * */
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getParentid() {
		return Parentid;
	}

	public void setParentid(Long parentid) {
		Parentid = parentid;
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
	
	
	
}
