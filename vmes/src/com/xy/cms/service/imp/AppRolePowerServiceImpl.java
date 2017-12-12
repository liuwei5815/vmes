package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.util.CollectionUtils;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.AppRole;
import com.xy.cms.entity.AppRolePower;
import com.xy.cms.entity.Role;
import com.xy.cms.service.AppRolePowerService;

public class AppRolePowerServiceImpl extends BaseDAO implements AppRolePowerService {

	@Override
	public List<AppRolePower> queryAppRolePowerByAppRole(Long appRole) {
		String hql = " from AppRolePower where appRoleId=:appRole";
		Map<String,Object> paramMap = new HashMap();
		paramMap.put("appRole",appRole);
		return this.getList(hql, paramMap);
	}

	@Override
	public void saveAppRolePower(List<Long> modularIds,Long appRoleId) {
		String hql ="delete AppRolePower where appRoleId=:appRoleId";
		Map<String,Object> paramMap = new HashMap();
		paramMap.put("appRoleId",appRoleId);
		this.execute(hql, paramMap);
		if(!CollectionUtils.isEmpty(modularIds)){
			for (Long modularId : modularIds) {
				AppRolePower appRolePower =new AppRolePower();
				appRolePower.setAppRoleId(appRoleId);
				appRolePower.setAppModularId(modularId);
				this.save(appRolePower);
			}
		}

	}

	@Override
	public AppRole getAppRoleById(Long id) {

		return (AppRole) this.get(AppRole.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AppRole> getAllAppRole() {
		return this.getAll(AppRole.class);
	}
	
	public QueryResult queryAppRolesPage(Map<String, Object> map)
			throws BusinessException {
		try {
			QueryResult result = null;
			AppRole appRole = (AppRole) map.get("appRole");
			BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
			StringBuffer hql = new StringBuffer();
			Map queryTerm = new HashMap();
			hql.append("from AppRole where 1=1");
			if (appRole != null) {
				if (CommonFunction.isNotNull(appRole.getName())) {
					hql.append(" and name like :name");
					queryTerm.put("name", "%" + appRole.getName().trim() + "%");
				}
			}
			result = this
					.getPageQueryResult(hql.toString(), queryTerm, qEntity);
			return result;
		} catch (Exception e) {
			throw new BusinessException("分页查询角色发生异常,Error:" + e.getMessage());
		}
	}

	@Override
	public void addAppRole(AppRole appRole) throws BusinessException {
		this.saveOrUpdate(appRole);
	}
	
	@Override
	public void updateAppRole(AppRole appRole) throws BusinessException {
		this.update(appRole);
	}
	
	@Override
	public void delAppRole(AppRole appRole) throws BusinessException {
		this.delete(appRole);
	}
	

}
