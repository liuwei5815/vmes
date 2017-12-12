package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.taglib.Tree;
import com.xy.cms.entity.Menu;
import com.xy.cms.entity.MenuConfig;

public interface MenuService {

	List<Tree> getMenuTree();

	boolean del(Long menuId) throws BusinessException;

	QueryResult queryMenu(Map<String, Object> map) throws BusinessException;

	void saveMenu(Menu menu) throws BusinessException;

	Menu queryById(Long id);

	List<Menu> list();

	/**
	 * 先判断是否进行了父菜单的更改，如果进行了更改，就需要考虑是否拥有目标一级菜单的权限，如果没有，就需要添加权限
	 * 再判断是否指定了orderby，调用相应的方法赋予orderby
	 * @param menu
	 * @throws BusinessException
	 */
	void updateMenu(Menu menu, Long roleId) throws BusinessException;
	
	void saveMenuConfig(Long width,Long height,Long menuId) throws BusinessException;
	void updateMenuConfig(Long width,Long height,Long menuId) throws BusinessException;
	
	MenuConfig getConfigByMenuId(Long menuId) throws BusinessException;

}