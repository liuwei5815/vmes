package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.AppUser;

public interface AccountService {
	/**
	 *分页查询客户 
	 */
	public QueryResult queryAccountPage(Map<String, Object> map) throws BusinessException;
	
	/**
	 * 获取web端用户
	 */
	public Admin queryAdminById(Long id);
	/**
	 * 删除一个web用户
	 */
	public void delAdmin(Admin admin);
	/**
	 * 获取移动端用户
	 */
	public AppUser queryAppUserById(Long id);
	/**
	 * 删除一个app用户
	 */
	public void delAppUser(AppUser appUser);
	
	/**
	 * 通过web端用户id获取用户的账号和员工名
	 */
	public List queryAdminAndNameById(Long id);
	
	/**
	 * 通过终端用户id获取用户的账号和员工名
	 */
	public List queryAppUserAndNameById(Long id);
	
	/**
	 * 修改一个web端用户
	 */
	public void updateAdmin(Admin admin);
	
	/**
	 * 修改一个终端用户
	 */
	public void updateAppUser(AppUser appUser);
}
