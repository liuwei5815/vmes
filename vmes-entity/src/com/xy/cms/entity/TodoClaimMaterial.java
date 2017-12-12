package com.xy.cms.entity;

import java.io.Serializable;
import java.util.Date;

public class TodoClaimMaterial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private Long claimId;
	private Long materialId;
	private String materialCode;
	private String materialName;
	private String materialModel;
	private Long number;
	private String batchNumber;
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
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMaterialModel() {
		return materialModel;
	}
	public void setMaterialModel(String materialModel) {
		this.materialModel = materialModel;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
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
