package com.xy.cms.entity;

import java.util.Date;

public class ProduceplanTodo {
	private Long id;

	private String processName;
	private Long planNum;
	private Long actualNum;
	private Long disqualifiedNum;
	private Date addDate;
	private String state;
	private Long produceplanId;
	private String code;
	private String taskName;
	private String qrcode;
	public ProduceplanTodo(){
		
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public Long getPlanNum() {
		return planNum;
	}
	public void setPlanNum(Long planNum) {
		this.planNum = planNum;
	}
	public Long getActualNum() {
		return actualNum;
	}
	public void setActualNum(Long actualNum) {
		this.actualNum = actualNum;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getProduceplanId() {
		return produceplanId;
	}
	public void setProduceplanId(Long produceplanId) {
		this.produceplanId = produceplanId;
	}
	public Long getDisqualifiedNum() {
		return disqualifiedNum;
	}
	public void setDisqualifiedNum(Long disqualifiedNum) {
		this.disqualifiedNum = disqualifiedNum;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	/**
	 * 订单状态
	 * 0待执行  1进行中 2已完成 -1已取消
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

}
