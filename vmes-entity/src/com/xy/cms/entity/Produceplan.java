package com.xy.cms.entity;

import java.util.Date;

public class Produceplan {
	/**
	 * 主键id
	 * */
	private Long id;
	
	/**
	 * 系统编号(系统生成)
	 * */
	private String planCode;
	
	/**
	 * 生产计划编号(客户输入)
	 * */
	private String manufactureCode;
	/**
	 * 生产计划开始时间
	 * */
	private Date startDate;
	
	/**
	 * 生产计划结束时间
	 * */
	private Date endDate;
	
	/**
	 * 产品信息id
	 * */
	private Long productId;
	
	/**
	 * 生产数量
	 * */
	private Long num;
	
	/**
	 * 状态
	 * */
	private Integer state;
	
	/**
	 * 销售明细id
	 * */
	private Long orderDetailId;
	

	
	/**
	 * 实际完成时间
	 * */
	private Date finishTime;
	
	/**
	 * 添加时间
	 * */
	private Date addDate;
	
	/**
	 * 修改时间
	 * */
	private Date updateDate;
	
	/**
	 * 产品编号
	 * */
	private String productCode;
	
	/**
	 * 产品名称
	 * */
	private String productName;
	
	/**
	 * 规格型号
	 * */
	private String productTypespec;
	
	/**
	 * 单位
	 * */
	private String productUnit;
	
	public enum Status {
		// 0:待派工 1:待生产 2：生产中 3：已完成  -1:已取消  
		watingtodo(0,"待派工"),waitingproduce(1,"待生产"),conduct(2, "生产中"), ok(3, "已完成"), no(-1, "已取消");
		private String msg;
		private int code;

		private Status(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getMsg() {
			return msg;
		}

		public int getCode() {
			return code;
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}


	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getManufactureCode() {
		return manufactureCode;
	}

	public void setManufactureCode(String manufactureCode) {
		this.manufactureCode = manufactureCode;
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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductTypespec() {
		return productTypespec;
	}

	public void setProductTypespec(String productTypespec) {
		this.productTypespec = productTypespec;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}


	
	
	
}
