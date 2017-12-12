package com.xy.admx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xy.admx.common.MD5;
import com.xy.admx.common.exception.BusinessException;
import com.xy.admx.core.helper.AppUserHelper;
import com.xy.admx.core.service.base.BaseServiceImpl;
import com.xy.admx.service.AppUserService;
import com.xy.apisql.db.TableColumnsService;
import com.xy.apisql.db.TablesService;
import com.xy.cms.entity.AppModular;
import com.xy.cms.entity.AppUser;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Employee;

@Service("appUserService")
public class AppUserServiceImpl extends BaseServiceImpl implements AppUserService {
	@Resource
	private TablesService tablesService;

	@Resource
	private TableColumnsService tableColumnsService;

	@Override
	public AppUser queryAppUserByAccountAndPwd(String account, String pwd) {
		StringBuilder sql = new StringBuilder();
		sql.append("from AppUser where ")
				.append(AppUserHelper.ACCOUNT_COLUMN.getFildName()).append("=:account").append(" and ")
				.append(AppUserHelper.PASSWROD_COLUMN.getFildName()).append("=:pwd");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("account", account);
		param.put("pwd", MD5.MD5(pwd));
		List list = this.getList(sql.toString(), param);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return (AppUser)list.get(0);
	}

	@Override
	public List<Department> getEmpDepts(Long empId) {
		String hql =  "from Department d where exists(select 1 from EmployeeDeptart ed where ed.employeeId=:empId and ed.departId=d.id)";
		Map param = new HashMap();
		param.put("empId", empId);
		return this.getList(hql,param);
	}
	
	public List<AppModular> queryAppRolePower(Long roleId){
		String hql = "from AppModular m where exists(select 1 from AppRolePower p where p.appRoleId=:roleid and p.appModularId=m.id) order by orderby";
		Map param = new HashMap();
		param.put("roleid", roleId);
		return this.getList(hql,param);
	}

	@Override
	public void changePwd(AppUser user, String oldPwd, String newPwd) throws BusinessException {
		AppUser entity = this.get(AppUser.class, user.getId());
		if(!MD5.MD5(oldPwd).equals(entity.getPassword())){
			throw new BusinessException("原密码输入错误");
		}
		entity.setPassword(MD5.MD5(newPwd));
		this.update(entity);
	}

}
