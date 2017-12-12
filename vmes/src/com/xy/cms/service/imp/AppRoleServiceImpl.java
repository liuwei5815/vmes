package com.xy.cms.service.imp;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.AppRole;
import com.xy.cms.entity.Role;
import com.xy.cms.service.AppRoleService;

public class AppRoleServiceImpl  extends BaseDAO implements AppRoleService{

	@Override
	public List<AppRole> getAllRole() throws BusinessException {
		try {
			return this.getAll(AppRole.class);
		} catch (DataAccessException e) {
			throw new BusinessException("查询角色信息异常:"+e.getMessage());
		}
	}

}
