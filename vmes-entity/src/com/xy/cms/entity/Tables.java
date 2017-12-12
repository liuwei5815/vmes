package com.xy.cms.entity;

import java.util.Date;

/**
 * @author 武汉夏宇信息
 */
public class Tables implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private String nameCn;
	
	private String des;
	
	private Long menuId;
	
	private Short status;
	
	private Date addDate;
	
	private Date updateDate;

	public Tables(){}
	
	public Tables(Long id, String name, String nameCn, String des, Long menuId,
			Short status, Date addDate, Date updateDate) {
		super();
		this.id = id;
		this.name = name;
		this.nameCn = nameCn;
		this.des = des;
		this.menuId = menuId;
		this.status = status;
		this.addDate = addDate;
		this.updateDate = updateDate;
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

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
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

	
}
