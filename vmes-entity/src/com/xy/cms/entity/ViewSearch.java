package com.xy.cms.entity;


/**
 * @author 武汉夏宇信息
 */
public class ViewSearch implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long viewId;
	
	private Short type;
	
	private Long columnId;
	
	private String rule;
	
	private String ruleValue;
	
	private Short state;
	
	private int conditionValue;
	
	private Long tableId;
	
	private Long targetTabId;
	
	private Long targetColId;
	
	public ViewSearch(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public Long getViewId() {
		return viewId;
	}

	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public int getConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(int conditionValue) {
		this.conditionValue = conditionValue;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getTargetTabId() {
		return targetTabId;
	}

	public void setTargetTabId(Long targetTabId) {
		this.targetTabId = targetTabId;
	}

	public Long getTargetColId() {
		return targetColId;
	}

	public void setTargetColId(Long targetColId) {
		this.targetColId = targetColId;
	}
    
}
