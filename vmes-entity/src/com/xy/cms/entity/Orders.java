package com.xy.cms.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Orders {
	private Long id;
	private String orderCode;
	// 订单编号
	private Long customerId;
	private Long productId;
	private Timestamp orderDate;// 下单时间
	private Timestamp addDate;
	private Timestamp updateDate;
	private Integer state;
	private Date deliveryDate;// 交付时间
	private Timestamp finishTime;// 完成时间
	private Long empId;// 经手人
	private String orderCodeAuto;//自动编号
	
	/**
	 * 订单状态
	 * 0待排产 1进行中 2已完成 -1已取消
	 * @author dg
	 *
	 */
	public enum State{
		awaiting(0),ongoing(1),finished(2),canceled(-1);
		private int code;
		
		private State(int code){
			this.code = code;
		}

		public int getCode() {
			return code;
		}
		
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public Timestamp getAddDate() {
		return addDate;
	}

	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Timestamp getFinishTime() {
		return finishTime;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getOrderCodeAuto() {
		return orderCodeAuto;
	}

	public void setOrderCodeAuto(String orderCodeAuto) {
		this.orderCodeAuto = orderCodeAuto;
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
		Orders other = (Orders) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
