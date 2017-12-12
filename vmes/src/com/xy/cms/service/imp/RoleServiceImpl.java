package com.xy.cms.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Role;
import com.xy.cms.service.RoleService;
import com.xy.cms.common.base.BaseQEntity;

public class RoleServiceImpl extends BaseDAO implements RoleService {

	public Role queryRoleById(String id) throws BusinessException {
		Role role = (Role)this.get(Role.class,Long.valueOf(id));
		return role;
	}

	public QueryResult queryRoleByPage(Map param) throws BusinessException {
		QueryResult result = null;
		try {
			BaseQEntity qEntity = (BaseQEntity) param.get("qEntity");
			Role role = (Role) param.get("role");
			Map pmap=new HashMap();
			StringBuffer hql =new StringBuffer(" from Role where 1=1 and isSuper=true");
			if(role != null){
				if(CommonFunction.isNotNull(role.getName())){
					hql.append(" and name like :name");
					pmap.put("name", "%"+role.getName()+"%");
				}
			}
			hql.append(" order by addDate desc");
			result = this.getPageQueryResult(hql.toString(), pmap, qEntity);
			return result;
		} catch (Exception e) {
			throw new BusinessException("查询角色信息异常："+e.getMessage());
		}
	}

	public void saveRole(Role role) throws BusinessException {
		try {
			role.setAddDate(DateUtil.convertSqlDate(new Date()));
			this.save(role);
		} catch (Exception e) {
			throw new BusinessException("添加角色信息异常:"+e.getMessage());
		}
	}

	public void del(String roleId) throws BusinessException {
		try {
			Role role = (Role)this.get(Role.class,Long.valueOf(roleId));
			this.delete(role);
		} catch (Exception e) { 
			throw new BusinessException("角色删除异常:"+e.getMessage());
		}	
	}

	public void upateRole(Role role) throws BusinessException {
		try {
			Role rl = (Role)this.get(Role.class,role.getId());
			rl.setName(role.getName());
			rl.setAddDate(new Date());
			this.update(rl);
		} catch (Exception e) {
			throw new BusinessException("角色修改异常:"+e.getMessage());
		}
	}

	public void batchDel(String[] ids) throws BusinessException {
	}

	public List<Role> getAllRole() throws BusinessException {
		try {
			return this.getAll(Role.class);
		} catch (DataAccessException e) {
			throw new BusinessException("查询角色信息异常:"+e.getMessage());
		}
	}

	@Override
	public List<Role> getAllRoleNoSuper() {
		/*String hql = "from Role where name!=:name";isSuper*/
		String hql="from Role where isSuper!=1";
		/*Map<String,Object> param = new HashMap<String, Object>();
		param.put("name", "超级管理员");*/
		return this.getList(hql, null);
	}
 
	
}
