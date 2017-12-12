package com.xy.admx.service;

import java.util.List;

import com.xy.admx.common.exception.BusinessException;
import com.xy.admx.core.service.base.BaseService;
import com.xy.cms.entity.AppModular;
import com.xy.cms.entity.AppUser;
import com.xy.cms.entity.Department;

public interface AppUserService extends BaseService{

	AppUser queryAppUserByAccountAndPwd(final String account, final String pwd);

	/**
	 * 获取某个员工所有部门
	 * @param empId
	 * @return
	 */
	List<Department> getEmpDepts(Long empId);
	
	/**
	 * 修改密码
	 * @param user
	 * @param oldPwd
	 * @param newPwd
	 */
	void changePwd(AppUser user,String oldPwd,String newPwd)  throws BusinessException;
	
	/**
	 * 查询approle能使用的功能模块
	 * @param roleId
	 * @return
	 */
	public List<AppModular> queryAppRolePower(Long roleId);

}
