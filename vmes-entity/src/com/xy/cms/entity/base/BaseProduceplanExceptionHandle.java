package com.xy.cms.entity.base;

public abstract class BaseProduceplanExceptionHandle {
	
	/**
	 * 主键id
	 * */
	private Long id;
	
	/**
	 * 生产计划id
	 * */
	private Long produceplanId;
	
	/**
	 * 状态  状态 1:生产计划预警（触发条件，销售订单详情调整数量后） 2：派工单预警（生产计划调整完成后） 3：完结（此流程终结，销售订单）  
	 * */
	private Integer state;
	
	/**
	 * 修改前的订单数量
	 * */
	private Long oldOrderNum;
	
	/**
	 * 修改后的订单数量  
	 * */
	private Long orderNum;
	
	/**
	 * 修改前的计划数量 
	 * */
	private Long oldPlanNum;
	
	/**
	 * 修改后的计划数量
	 * */
	private Long planNum;
	
	

	public enum Status {
		// 状态 1:生产计划预警（触发条件，销售订单详情调整数量后） 2：派工单预警（生产计划调整完成后） 
		produceplan(1, "生产计划"), dispatch(2, "派工单");
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

	public Long getProduceplanId() {
		return produceplanId;
	}

	public void setProduceplanId(Long produceplanId) {
		this.produceplanId = produceplanId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getOldOrderNum() {
		return oldOrderNum;
	}

	public void setOldOrderNum(Long oldOrderNum) {
		this.oldOrderNum = oldOrderNum;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Long getOldPlanNum() {
		return oldPlanNum;
	}

	public void setOldPlanNum(Long oldPlanNum) {
		this.oldPlanNum = oldPlanNum;
	}

	public Long getPlanNum() {
		return planNum;
	}

	public void setPlanNum(Long planNum) {
		this.planNum = planNum;
	}

	
	
	
	
}
