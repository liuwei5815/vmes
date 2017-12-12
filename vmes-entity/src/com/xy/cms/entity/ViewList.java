package com.xy.cms.entity;

/**
 * @author 武汉夏宇信息
 */
public class ViewList implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	private Long id;
	
	private Long viewId;
	
	private Long columnId;
	
	private String showname;
	
	private String width;
	
	private Long tableId;
	
	private Long targetTabId;
	
	private Long targetColId;
	
	
	public ViewList(){}


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

	public String getShowname() {
		return showname;
	}

	public void setShowname(String showname) {
		this.showname = showname;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}


	public Long getColumnId() {
		return columnId;
	}


	public void setColumnId(Long columnId) {
		this.columnId = columnId;
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
