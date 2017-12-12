package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.AppUser;
import com.xy.cms.service.AppUserService;

public class AppUserServiceImpl extends BaseDAO implements AppUserService{

	@Override
	public AppUser getAppUserByEmpId(Long empId) throws BusinessException {
		if(CommonFunction.isNull(empId)){
			throw new BusinessException("员工id不能为空");
		}
		String hql ="from AppUser where empId=:empId";
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("empId", empId);
		return (AppUser) this.getUniqueResult(hql, param);
	}

	@Override
	public void updateAppUser(AppUser appUser) throws BusinessException {
		try {
			this.update(appUser);
		} catch (Exception e) {
			throw new BusinessException("用户管理员修改异常:"+e.getMessage());
		}
	}

	@Override
	public void deleteAppUser(AppUser appUser) throws BusinessException {
		try {
			this.delete(appUser);
		} catch (Exception e) {
			throw new BusinessException("用户管理员修改异常:"+e.getMessage());
		}
	}

	@Override
	public void addAppUser(AppUser appUser) throws BusinessException {
		this.save(appUser);
	}

	@Override
	public List<AppUser> getAppUser() throws BusinessException {
		// TODO Auto-generated method stub
		return this.getAll(AppUser.class);
	}

}
