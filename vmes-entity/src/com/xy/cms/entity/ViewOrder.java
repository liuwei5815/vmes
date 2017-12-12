package com.xy.cms.entity;

public class ViewOrder {
    private Long id;
    private Long viewId;
    private Long columnId;
    private Short type;
    private Long tableId;
    private Long targetTabId;
    private Long targetColId;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getViewId() {
		return viewId;
	}
	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
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
