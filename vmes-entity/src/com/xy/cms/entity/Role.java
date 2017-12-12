package com.xy.cms.entity;

import java.util.Date;

/**
 * @author 武汉夏宇信息
 */
public class Role implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	private Long id;
	
	/**
	 * 角色名称
	 */
	private String name;
	
	
	/**
	 * 创建时间
	 */
	private Date addDate;
	/**
	 * 是否是超管
	 */
	private Boolean isSuper;
	
	

	public Role(){}
	
	public Role(Long id, String name, Date addDate) {
		super();
		this.id = id;
		this.name = name;
		this.addDate = addDate;
	}

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

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Boolean getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(Boolean isSuper) {
		this.isSuper = isSuper;
	}
	
	
	

}
