package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.SessionBean;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.Menu;

public interface LoginService {

	Admin doLogin(Admin admin) throws BusinessException;
	
	Admin doLogin(String acount,String pwd);
	
	List<Menu> getLeftMenu() throws BusinessException;
	
	void editLastDate(Admin admin) throws BusinessException;
	
	void updatePWD(String pwd, Long id) throws BusinessException;
	
	void updateConfig(String confName,String confValue) throws BusinessException;
	
	public Map getLeftMenuMap(SessionBean bean) throws BusinessException;
}
