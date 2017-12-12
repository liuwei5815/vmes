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
import com.xy.cms.service.AdminService;

public class AdminServiceImpl extends BaseDAO implements AdminService {

	@Override
	public Admin login(Admin admin, boolean isCreate) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	public QueryResult queryAdminPage(Map param) throws BusinessException {
		QueryResult result = null;
		BaseQEntity qEntity = (BaseQEntity) param.get("qEntity");
		Admin admin = (Admin)param.get("admin");
		
		StringBuffer hql = new StringBuffer("select u.id,u.account as uname,u.addDate,r.name as rname from Admin u,Role r where 1=1 ");
		Map<String, Object> m = new HashMap<String, Object>();
		if(CommonFunction.isNotNull(admin)){
			if(CommonFunction.isNotNull(admin.getAccount())){
				hql.append(" and u.account like :name");
			    m.put("name", "%"+admin.getAccount()+"%");
			}
			
			if(CommonFunction.isNotNull(admin.getRoleId())){
				hql.append(" and u.roleId=:roleId");
				m.put("roleId",admin.getRoleId());
			}
		}
		hql.append(" and u.roleId=r.id order by u.addDate desc");
		result = this.getPageQueryResult(hql.toString(), m, qEntity);
		return result;
	}

	public void saveAdmin(Admin admin) throws BusinessException {
		if(admin == null){
			throw new BusinessException("参数错误");
		}
		if(admin.getAccount() == null){
			throw new BusinessException("账号不能为空");
		}
		if(admin.getPwd() == null){
			throw new BusinessException("密码不能为空");
		}
		admin.setPwd(MD5.MD5(admin.getPwd()));
		this.save(admin);
	}

	public void del(Long adminId) throws BusinessException {
		Admin admin = new Admin();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("adminId", adminId);
		
		String hql = " delete from Admin where id=:adminId ";
		this.execute(hql, params);
		//删除user data
		//this.delete(user);
	}

	/*public Admin getAdmin(Long adminId) throws BusinessException {
		return (Admin)this.get(Admin.class,adminId);
	}*/
	public Admin getAdmin(Long adminId) throws BusinessException {
		String hql="from Admin where roleId=2";
		return (Admin)this.getUniqueResult(hql, null);
	}
	public void upateAdmin(Admin admin) throws BusinessException {
		try {
			this.update(admin);
		} catch (Exception e) {
			throw new BusinessException("用户管理员修改异常:"+e.getMessage());
		}
	}

	@Override
	public void updatePwd(Long adminId, String newpwd) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batchDel(String[] ids) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void isExistAdmin(Admin admin) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logout(HttpServletRequest request) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Admin getAdmin(HttpSession session) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admin getAdmin(HttpServletRequest req) throws BusinessException {
		return null;
	}

	@Override
	public List<Admin> getAllAdmins() throws BusinessException {
		String hql = " from Admin where empId is not null";
		return this.find(hql);
	}

	@Override
	public Admin getAdminByAssId(Long assId, Long schId)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admin getAdminByEmpId(Long empId) throws BusinessException {
		if(CommonFunction.isNull(empId)){
			throw new BusinessException("员工id不能为空");
		}
		String hql ="from Admin where empId=:empId";
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("empId", empId);
		return (Admin) this.getUniqueResult(hql, param);
	}

	@Override
	public void updateAdminAppUser(Admin admin, AppUser appUser) {
		if(CommonFunction.isNotNull(admin)){
			this.update(admin);
		}
		
		if(CommonFunction.isNotNull(appUser)){
			this.update(appUser);
		}
		
	}

	@Override
	public void addAdmin(Admin admin) throws BusinessException {
		this.save(admin);
	}
 
	
}
