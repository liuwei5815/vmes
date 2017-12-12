package com.xy.cms.entity;

import java.util.Date;

public class EquipmentParamData {
	private Long id;//序号
	private Long equmentId;//设备id
	private Long paramterOne;//生产总量计数
	private Long paramterTwo;//合格品计数
	private Long paramterThree;//上电时间
	private Long paramterFour;//运行时间
	private Long paramterFive;//报警次数
	private Long paramterSix;//采集周期
	private Date receiveTime;//接收时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEqumentId() {
		return equmentId;
	}
	public void setEqumentId(Long equmentId) {
		this.equmentId = equmentId;
	}
	public Long getParamterOne() {
		return paramterOne;
	}
	public void setParamterOne(Long paramterOne) {
		this.paramterOne = paramterOne;
	}
	public Long getParamterTwo() {
		return paramterTwo;
	}
	public void setParamterTwo(Long paramterTwo) {
		this.paramterTwo = paramterTwo;
	}
	public Long getParamterThree() {
		return paramterThree;
	}
	public void setParamterThree(Long paramterThree) {
		this.paramterThree = paramterThree;
	}
	public Long getParamterFour() {
		return paramterFour;
	}
	public void setParamterFour(Long paramterFour) {
		this.paramterFour = paramterFour;
	}
	public Long getParamterFive() {
		return paramterFive;
	}
	public void setParamterFive(Long paramterFive) {
		this.paramterFive = paramterFive;
	}
	public Long getParamterSix() {
		return paramterSix;
	}
	public void setParamterSix(Long paramterSix) {
		this.paramterSix = paramterSix;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
}
