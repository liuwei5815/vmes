package com.xy.cms.entity;

import java.util.Date;

/**
 * @author 武汉夏宇信息
 */
public class View implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long tableId;
	private String searchRole;
	private String name;
	private String access;
	private Short state;
	private Date createTime;
	private String targetTabId;
	
	public View() {
		super();
	}
	public View(Long id, String name, String access, Short state) {
		super();
		this.id = id;
		this.name = name;
		this.access = access;
		this.state = state;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public String getSearchRole() {
		return searchRole;
	}
	public void setSearchRole(String searchRole) {
		this.searchRole = searchRole;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTargetTabId() {
		return targetTabId;
	}
	public void setTargetTabId(String targetTabId) {
		this.targetTabId = targetTabId;
	}
    
	
}
