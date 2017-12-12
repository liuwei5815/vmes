package com.xy.cms.entity;

import java.util.List;

public class Menu {

	public Long id;

	public String name;

	public Short level;

	public Long supiorId;

	public String url;

	public String icon;

	public Short state;

	protected List<Menu> childNodes;

	public String isMenu;
	public Integer orderby;
	
	private Boolean isSuperMenu;

	public String getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
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

	public Short getLevel() {
		return level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	public Long getSupiorId() {
		return supiorId;
	}

	public void setSupiorId(Long supiorId) {
		this.supiorId = supiorId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public List<Menu> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<Menu> childNodes) {
		this.childNodes = childNodes;
	}

	public Boolean getIsSuperMenu() {
		return isSuperMenu;
	}

	public void setIsSuperMenu(Boolean isSuperMenu) {
		this.isSuperMenu = isSuperMenu;
	}

	
	
}
