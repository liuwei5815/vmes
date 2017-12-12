package com.xy.cms.entity;

import java.util.Date;

public class Eqiupment {
	private Long id;//序号
	private String equipmentCode;//系统设备编号
	private String userEquipmentCode;//用户设备编号
	private String equipmentName;//设备名称
	private String equipmentModel;//设备规格型号
	private Integer equipmentType;//设备类型
	private Date buyDate;//采购日期
	private String equipmentImg;//设备图片
	private String equipmentWorkTime;//设备工作时间
	private Long dept;//设备所属部门
	private String equipmentqrCode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getUserEquipmentCode() {
		return userEquipmentCode;
	}
	public void setUserEquipmentCode(String userEquipmentCode) {
		this.userEquipmentCode = userEquipmentCode;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getEquipmentModel() {
		return equipmentModel;
	}
	public void setEquipmentModel(String equipmentModel) {
		this.equipmentModel = equipmentModel;
	}
	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	public String getEquipmentImg() {
		return equipmentImg;
	}
	public void setEquipmentImg(String equipmentImg) {
		this.equipmentImg = equipmentImg;
	}
	public String getEquipmentWorkTime() {
		return equipmentWorkTime;
	}
	public void setEquipmentWorkTime(String equipmentWorkTime) {
		this.equipmentWorkTime = equipmentWorkTime;
	}
	public Long getDept() {
		return dept;
	}
	public void setDept(Long dept) {
		this.dept = dept;
	}
	public String getEquipmentqrCode() {
		return equipmentqrCode;
	}
	public void setEquipmentqrCode(String equipmentqrCode) {
		this.equipmentqrCode = equipmentqrCode;
	}
	
	
	 
}
