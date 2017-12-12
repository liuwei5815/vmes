package com.xy.cms.entity;

import java.io.Serializable;
import java.util.Date;

public class ProduceplanTodoClaim implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	
	private Long todoId;
	
	private Long executor;
	
	private Long disqualifiedNum;
	
	private Long qualifiedNum;
	
	private Date addDate;
	
	private Integer state;
	
	private Long plannum;
	
	private Date finishTime;
	
	//取消时间
	private Date cancelTime;
	
	/**
	 * 订单状态 0进行中 1已完成 -1已取消
	 * 
	 * @author dg
	 *
	 */
	public enum State {
		ongoing(0), finished(1), canceled(-1);
		private int code;

		private State(int code) {
			this.code = code;
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

	public Long getExecutor() {
		return executor;
	}

	public void setExecutor(Long executor) {
		this.executor = executor;
	}

	public Long getDisqualifiedNum() {
		return disqualifiedNum;
	}

	public void setDisqualifiedNum(Long disqualifiedNum) {
		this.disqualifiedNum = disqualifiedNum;
	}

	public Long getQualifiedNum() {
		return qualifiedNum;
	}

	public void setQualifiedNum(Long qualifiedNum) {
		this.qualifiedNum = qualifiedNum;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getPlannum() {
		return plannum;
	}

	public void setPlannum(Long plannum) {
		this.plannum = plannum;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Long getTodoId() {
		return todoId;
	}

	public void setTodoId(Long todoId) {
		this.todoId = todoId;
	}
	
	
	
	
}
