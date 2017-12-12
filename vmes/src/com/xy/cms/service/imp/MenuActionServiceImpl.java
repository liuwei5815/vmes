package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.entity.MenuAction;
import com.xy.cms.service.MenuActionService;

public class MenuActionServiceImpl extends BaseDAO implements MenuActionService{

	@Override
	public List<MenuAction> getMenuActionByMenuId(Long menuId) {
		String hql =" from MenuAction where menuId=:menuId";
		Map<String,Object> param = new HashMap();
		param.put("menuId", menuId);
		return this.getList(hql, param);
	}

}
