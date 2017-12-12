package com.xy.cms.entity;

public class Power {

	public Long id;
	
	public Long menuId;
	
	public Long roleId;

	public Power(){}
	
	public Power(Long id, Long menuId, Long roleId) {
		super();
		this.id = id;
		this.menuId = menuId;
		this.roleId = roleId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	
}
