package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.MD5;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.AppUser;
import com.xy.cms.entity.Orders;
import com.xy.cms.service.AccountService;
import com.xy.cms.service.AdminService;

public class AccountServiceImpl extends BaseDAO implements AccountService {

	@Override
	public QueryResult queryAccountPage(Map<String, Object> map) throws BusinessException {
		QueryResult result = null;
		Map m = new HashMap();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		String type =(String) map.get("type");
		String name =(String) map.get("name");
		StringBuilder sql=new StringBuilder();
		if(type.equals("web")){//web
			sql.append(" select ad.id,ad.account,ad.addDate,emp.name from Admin as ad, Employee as emp where ad.roleId>2 and ad.empId=emp.id");
		}else{//app
			sql.append(" select ad.id,ad.account,ad.addDate,emp.name from AppUser as ad, Employee as emp where 1=1 and ad.empId=emp.id");
		}
		if(CommonFunction.isNotNull(name)){
			sql.append(" and ad.account like:name");
			m.put("name",  "%"+name+"%");
		}
		System.out.println(sql.toString());
		result=this.getPageQueryResult(sql.toString(), m, qEntity);
		return result;
	}

	@Override
	public Admin queryAdminById(Long id) {
		return (Admin)this.get(Admin.class, id);
	}

	@Override
	public AppUser queryAppUserById(Long id) {
		return (AppUser)this.get(AppUser.class, id); 
	}

	@Override
	public void delAdmin(Admin admin) {
		this.delete(admin);
		
	}

	@Override
	public void delAppUser(AppUser appUser) {
		this.delete(appUser);
		
	}

	@Override
	public List queryAdminAndNameById(Long id) {
		if (CommonFunction.isNotNull(id)) {
			String hql = "select ad.id,ad.account,emp.name from Admin as ad, Employee as emp where ad.id="+id+" and ad.empId=emp.id";
			return this.find(hql);
		}
		return null;
	}
	
	@Override
	public List queryAppUserAndNameById(Long id) {
		if (CommonFunction.isNotNull(id)) {
			String hql = "select ad.id,ad.account,emp.name from AppUser as ad, Employee as emp where ad.id="+id+" and ad.empId=emp.id";
			return this.find(hql);
		}
		return null;
	}
	
	@Override
	public void updateAdmin(Admin admin) {
		if (CommonFunction.isNotNull(admin)) {
			String sql = "update sys_admin set pwd = '"+admin.getPwd()+"' where id = "+admin.getId();
			this.executeSQL(sql);
		}
	}
	
	@Override
	public void updateAppUser(AppUser appUser) {
		if (CommonFunction.isNotNull(appUser)) {
			String sql = "update sys_appuser set password = '"+appUser.getPassword()+"' where id = "+appUser.getId();
			this.executeSQL(sql);
		}
	}
}
