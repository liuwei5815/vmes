package com.xy.cms.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.MD5;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.taglib.Tree;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.Power;
import com.xy.cms.entity.Role;
import com.xy.cms.service.AdminService;
import com.xy.cms.service.PowerService;
import com.xy.cms.common.base.BaseQEntity;

public class PowerServiceImpl extends BaseDAO implements PowerService {

	public QueryResult queryRolesPage(Map<String, Object> map)
			throws BusinessException {
		try {
			QueryResult result = null;
			Role role = (Role) map.get("role");
			BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
			StringBuffer hql = new StringBuffer();
			Map queryTerm = new HashMap();
			hql.append("from Role where 1=1 and isSuper=false");
			if (role != null) {
				if (CommonFunction.isNotNull(role.getName())) {
					hql.append(" and name like :name");
					queryTerm.put("name", "%" + role.getName().trim() + "%");
				}
			}
			result = this
					.getPageQueryResult(hql.toString(), queryTerm, qEntity);
			return result;
		} catch (Exception e) {
			throw new BusinessException("分页查询角色发生异常,Error:" + e.getMessage());
		}
	}
	
	public Role getRole(Long id) throws BusinessException {
		try {
			return (Role) this.get(Role.class, id);
		} catch (Exception e) {
			throw new BusinessException("查询角色失败，发生数据库异常,Error:"
					+ e.getMessage(), e);
		}
	}
	
	public void updateRolePowerList(Long roleId, List<Long> menuIdList) {
		this.execute("delete from Power p where p.roleId=" + roleId);
		if (menuIdList != null && menuIdList.size() > 0) {
			Power power = null;
			for (Long id : menuIdList) {
				power = new Power();
				power.setMenuId(id);
				power.setRoleId(roleId);
				this.save(power);
			}
		}
	}
	
	public List<Tree> getMenuTree(Long id) {
		List<Tree> treeList = new ArrayList<Tree>();
		List<Object[]> list = this
				.findBySQL("SELECT m.id a,m.name,m.level,m.supior_id,p.id b FROM sys_menu m LEFT JOIN sys_power p  ON m.id=p.menu_Id AND p.role_id=" + id + " WHERE state=1 and m.is_supermenu=0 ORDER BY m.level,m.orderby");
		if (list != null && list.size() > 0) {
			for (Object[] o : list) {
				Tree tree = null;
				if ((Short) o[2] == (short) 1) {
					tree = new Tree(o[0].toString(), "", o[1].toString());
				} else {
					tree = new Tree(o[0].toString(), o[3].toString(),
							o[1].toString());
				}
				tree.setValue(o[0].toString());
				if (o[4] != null && !o[4].equals("")) {
					tree.setChecked(true);
				} else {
					tree.setChecked(false);
				}

				treeList.add(tree);

			}

		}
		return treeList;
	}
}
