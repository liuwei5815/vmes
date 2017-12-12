package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.AppRole;
import com.xy.cms.entity.AppRolePower;

public interface AppRolePowerService {
	/**
	 * 根据应用角色查找相关的权限
	 * @param appRole
	 * @return
	 */
	List<AppRolePower> queryAppRolePowerByAppRole(Long appRole);
	
	
	/***
	 * 保存应用角色权限信息
	 * @param modularId
	 */
	void saveAppRolePower(List<Long> modularIds,Long appRoleId);
	
	/**
	 * 得到appRole
	 * @param id
	 * @return
	 */
	AppRole getAppRoleById(Long id);
	
	/**
	 * 得到所有appRole
	 * @return
	 */
	List<AppRole> getAllAppRole();
	
	/**
	 * 分页查询appRole
	 * @param map
	 * @return
	 */
	QueryResult queryAppRolesPage(Map<String, Object> map) throws BusinessException;
	
	/**
	 * 添加appRole
	 * @param appRole
	 */
	void addAppRole(AppRole appRole) throws BusinessException;
	
	/**
	 * 更新appRole
	 * @param appRole
	 */
	void updateAppRole(AppRole appRole) throws BusinessException;
	
	/**
	 * 删除appRole
	 * @param appRole
	 */
	void delAppRole(AppRole appRole) throws BusinessException;
	
}
