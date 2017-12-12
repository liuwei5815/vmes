package com.xy.cms.entity;

/**
 * 
 * @author ljx
 *
 */
public class Sequence {
	private Long id;
	
	private Long tableColumnsId;
	
	private Integer currentNumber;
	
	private Integer start;
	
	private Integer step;
	
	private Integer periodType;
	
	private Long lastResetTime;
	
	private Integer position;
	
	
	public static enum PeriodType{
		DAY(1),WEEK(2),YEAR(3);
		private int code;
		private PeriodType(int code){
			this.code=code;
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

	

	public Integer getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(Integer currentNumber) {
		this.currentNumber = currentNumber;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getPeriodType() {
		return periodType;
	}

	public void setPeriodType(Integer periodType) {
		this.periodType = periodType;
	}

	public Long getLastResetTime() {
		return lastResetTime;
	}

	public void setLastResetTime(Long lastResetTime) {
		this.lastResetTime = lastResetTime;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Long getTableColumnsId() {
		return tableColumnsId;
	}

	public void setTableColumnsId(Long tableColumnsId) {
		this.tableColumnsId = tableColumnsId;
	}
	
}
