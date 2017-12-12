package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Role;
public interface RoleService {

	/**
	 * 根据id 查询对应角色信息
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Role queryRoleById(String id) throws BusinessException;

	/**
	 * 分页查询角色信息
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult queryRoleByPage(Map param) throws BusinessException;
	
	/**
	 * 保存角色信息
	 * @param role
	 * @throws BusinessException
	 */
	public void saveRole(Role role) throws BusinessException;
	
	/**
	 * 删除角色信息
	 * @param userId
	 * @throws BusinessException
	 */
	public void del(String roleId) throws BusinessException;
	
	 
	
	/**
	 * 更新角色信息
	 * @param Role
	 * @throws BusinessException
	 */
	public void upateRole(Role Role) throws BusinessException;
	
	 
	
	/**
	 * 批量删除角色信息
	 * @param ids
	 * @throws BusinessException
	 */
	public void batchDel(String[] ids) throws BusinessException ;
	
	
	/**
	 * 查询全部角色信息
	 * @return
	 * @throws BusinessException
	 */
	public List<Role> getAllRole() throws BusinessException;
	
	/**
	 * 查询除超级管理员以外所有的角色
	 * @return
	 * */
	public List<Role> getAllRoleNoSuper() ;
}
