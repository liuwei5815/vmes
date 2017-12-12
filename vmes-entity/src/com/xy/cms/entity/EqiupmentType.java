package com.xy.cms.entity;

import java.sql.Date;
import java.sql.Timestamp;
/**
 * 设备类型
 * @author Administrator
 *
 */
public class EqiupmentType {
	private Long id;//序号
	private String type; 
	private String etType;
	private String img; 
	private String equipTypeCode; 
	private Integer pid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEtType() {
		return etType;
	}
	public void setEtType(String etType) {
		this.etType = etType;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getEquipTypeCode() {
		return equipTypeCode;
	}
	public void setEquipTypeCode(String equipTypeCode) {
		this.equipTypeCode = equipTypeCode;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	} 
	
	 
}
