package com.xy.cms.entity;

import java.sql.Timestamp;
import java.util.Date;

public class OrdersDetail {
	private Long id;
	private Long orderId;
	private Long productId;
	private Integer state; 
	private Long num;
	private Date deliveryDate;//交货期
	private Timestamp finishTime;//完成期
	private String remarks;//备注
	private Date addDate;//添加时间
	
	private String productCode;
	private String productName;
	private String productTypespec;
	private String productUnit;
	public OrdersDetail() {
		// TODO Auto-generated constructor stub
	}
	public OrdersDetail(Long id, Long orderId, Long productId, Integer state, Long num, Date deliveryDate,
			Timestamp finishTime, String remarks, Date addDate) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.productId = productId;
		this.state = state;
		this.num = num;
		this.deliveryDate = deliveryDate;
		this.finishTime = finishTime;
		this.remarks = remarks;
		this.addDate = addDate;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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

	public enum OrdersDetailStatus {
		//0待排产 1生产中 2已完成 - 1已取消
		/*doing(0, "进行中"), done(1, "已完成"), cancel(-1, "已取消");*/
		awaiting(0,"待排产"),ongoing(1,"生产中"),finished(2,"已完成"),canceled(-1,"已取消");
		private String msg;
		private int code;

		private OrdersDetailStatus(int code, String msg) {
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdersDetail other = (OrdersDetail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	
}
