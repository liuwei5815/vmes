package com.xy.cms.entity;

public class Material {
	private Long id;//序号
	private String materialCode;//物料编号
	private String usermaterialCode;//用户物料编号
	private String materialName;//物料名称
	private String materialSpec;//物料规格、型号
	private String materialType;//物料类型
	private String 	materialUnit;//计量单位
	private String materialqrCode;//物料二维码
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getUsermaterialCode() {
		return usermaterialCode;
	}
	public void setUsermaterialCode(String usermaterialCode) {
		this.usermaterialCode = usermaterialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMaterialSpec() {
		return materialSpec;
	}
	public void setMaterialSpec(String materialSpec) {
		this.materialSpec = materialSpec;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getMaterialUnit() {
		return materialUnit;
	}
	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}
	public String getMaterialqrCode() {
		return materialqrCode;
	}
	public void setMaterialqrCode(String materialqrCode) {
		this.materialqrCode = materialqrCode;
	}
	
	 
	
	
	

}
