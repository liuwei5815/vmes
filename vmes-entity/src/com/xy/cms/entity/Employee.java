package com.xy.cms.entity;

import java.util.Date;

/**
 * 员工表
 */
public class Employee {
	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 员工名字
	 */
	private String name;

	/**
	 * 出生年月
	 */
	private String birthday;

	/**
	 * 性别
	 */
	private Short gender;

	/**
	 * 手机号码
	 */
	private String phoneNum;

	/**
	 * 职位
	 */
	private String jobtitle;

	/**
	 * 创建人id
	 */
	private Integer createby;

	/**
	 * 员工号
	 */
	private String serialNo;

	/**
	 * 添加时间
	 */
	private Date addDate;

	/**
	 * 修改时间
	 */
	private Date updateDate;

	/**
	 * 员工头像
	 */
	private String avatar;

	/**
	 * 身份证
	 */
	private String idcard;

	/**
	 * 角色
	 */
	private Long rid;

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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Short getGender() {
		return gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public Integer getCreateby() {
		return createby;
	}

	public void setCreateby(Integer createby) {
		this.createby = createby;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

}
