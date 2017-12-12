package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.taglib.Tree;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.Role;
import com.xy.cms.entity.TableColumns;
import com.xy.cms.entity.Tables;

public interface PowerService {
	
	public QueryResult queryRolesPage(Map<String, Object> map)
			throws BusinessException;
	
	public Role getRole(Long id) throws BusinessException;
	
	public List<Tree> getMenuTree(Long id);
	
	public void updateRolePowerList(Long roleId, List<Long> menuIdList);
}
