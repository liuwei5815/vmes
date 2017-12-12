package com.xy.cms.entity;

/**
 * 产品表
 * */
public class Product {
	/**
	 * 主键id
	 * */
	private Long id;
	
	/**
	 * 产品编号
	 * */
	private String productCode;
	/**
	 * 用户产品编号
	 * */
	private String userProductCode;
	
	/**
	 * 产品名称
	 * */
	private String name;
	
	/**
	 * 产品介绍
	 * */
	private String dsc;
	
	/**
	 * 产品分类
	 * */
	private String type;
	
	/**
	 * 产品状态 0删除 1正常
	 * */
	private short status;
	
	/**
	 * 规格型号
	 * */
	private String typespec;
	
	/**
	 * 单位
	 * */
	private String unit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDsc() {
		return dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getTypespec() {
		return typespec;
	}

	public void setTypespec(String typespec) {
		this.typespec = typespec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUserProductCode() {
		return userProductCode;
	}

	public void setUserProductCode(String userProductCode) {
		this.userProductCode = userProductCode;
	}
	
	
	
}
