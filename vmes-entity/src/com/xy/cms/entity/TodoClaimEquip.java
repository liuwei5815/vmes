package com.xy.cms.entity;

import java.io.Serializable;
import java.util.Date;

public class TodoClaimEquip implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private Long claimId;
	private Long equipId;
	private String equipCode;
	private String equipName;
	private String equipModel;
	private Date addDate;
	private Long createby;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}
	public Long getEquipId() {
		return equipId;
	}
	public void setEquipId(Long equipId) {
		this.equipId = equipId;
	}
	public String getEquipCode() {
		return equipCode;
	}
	public void setEquipCode(String equipCode) {
		this.equipCode = equipCode;
	}
	public String getEquipName() {
		return equipName;
	}
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}
	public String getEquipModel() {
		return equipModel;
	}
	public void setEquipModel(String equipModel) {
		this.equipModel = equipModel;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public Long getCreateby() {
		return createby;
	}
	public void setCreateby(Long createby) {
		this.createby = createby;
	}
}
