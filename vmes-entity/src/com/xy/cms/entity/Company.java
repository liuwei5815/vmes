package com.xy.cms.entity;

import java.util.Date;

/**
 * 公司表
 * */
public class Company {
	/**
	 * 主键id
	 * */
	private Long id;
	
	/**
	 * 公司名称
	 * */
	private String name;
	
	/**
	 * 公司Logo
	 * */
	private String logo;
	
//	/**
//	 * 所在省
//	 * /
//	private Long province;
//	
//	/**
//	 * 所在市
//	 * /
//	private Long city; 
	/**
	 * 所在区域
	 */
	private Long area;
	/**
	 * 地址
	 * */
	private String address;
	
	/**
	 * 联系人
	 * */
	private String contact;
	
	/**
	 * 联系电话
	 * */
	private String tel;
	
	/**
	 * 创建时间
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	/*public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}
*/
	
	public String getAddress() {
		return address;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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
