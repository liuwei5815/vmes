package com.xy.cms.service;

import java.util.List;

import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.AppRole;
import com.xy.cms.entity.Role;

public interface AppRoleService {

	/**
	 * 查询全部角色信息
	 * @return
	 * @throws BusinessException
	 */
	public List<AppRole> getAllRole() throws BusinessException;
}
