package com.xy.cms.service;

import java.util.List;

import com.xy.cms.entity.MenuAction;

public interface MenuActionService {
	
	public List<MenuAction> getMenuActionByMenuId(Long menuId);
}
