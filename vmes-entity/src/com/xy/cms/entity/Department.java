package com.xy.cms.entity;

import java.util.Date;
import java.util.Set;

/**
 * 
 * 缁勭粐鏈烘瀯
 * */
public class Department implements java.io.Serializable{
	/**
	 *涓婚敭id
	 */
	private Long id;
	
	/**
	 *鐖剁骇閮ㄩ棬id
	 */
	private Long pid;
	
	/**
	 *閮ㄩ棬鍚嶇О
	 */
	private String name;
	
	/**
	 * 浣跨敤鐘舵�� 1锛氭甯� 0锛氬仠鐢�
	 */
	private Short status;
	
	/**
	 * 鍒涘缓浜�
	 * */
	private Long createby;
	
	/**
	 * 娣诲姞鏃堕棿
	 * */
	private Date addDate;
	
	/**
	 * 淇敼鏃堕棿
	 */
	private Date updateDate;
	
	/**
	 * 鎺掑簭瀛楁  
	 * */
	private Integer orderby;

	/**
	 * 閮ㄩ棬绾у埆
	 * @return
	 */
	private Integer level;
	
	
	
	
	private String path;//部门路径(中间使用“-”链接)
	
	private String number;//部门编码
	
	private String longnumber;//部门长编码
	
	private String type;  //类型:生产部门、行政部门、职能部门、公司
	
	
	

	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getLongnumber() {
		return longnumber;
	}

	public void setLongnumber(String longnumber) {
		this.longnumber = longnumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
