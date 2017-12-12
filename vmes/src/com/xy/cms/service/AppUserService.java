package com.xy.cms.service;

import java.util.List;

import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.AppUser;

public interface AppUserService {
	/**
	 * 根据empId查询appUser
	 * @param empId
	 * @return
	 * */
	public AppUser getAppUserByEmpId(Long empId)throws BusinessException;
	
	/**
	 * 更新AppUser
	 * */
	public void updateAppUser(AppUser appUser)throws BusinessException;
	
	/**
	 * 删除AppUser
	 * */
	public void deleteAppUser(AppUser appUser)throws BusinessException;
	
	/**
	 * 添加一个AppUser
	 * */
	public void addAppUser(AppUser appUser)throws BusinessException;
	/**
	 * 查询所有appUser用户
	 */
	public List<AppUser> getAppUser()throws BusinessException;
}
