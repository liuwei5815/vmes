package com.xy.cms.action.system;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.entity.Role;
import com.xy.cms.service.RoleService;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;

public class RoleAction extends BaseAction {

	private Role role;
	private RoleService roleService;
	
	public String init() throws Exception {
		return "init";
	}
	
	public String query() {
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				pageMap.put("role", role);
				return roleService.queryRoleByPage(pageMap);
			}
		});
		return "list";
	}
	
	public String preEdit() {
		try {
			if (CommonFunction.isNull(role)
					|| CommonFunction.isNull(role.getId())) {
				throw new BusinessException("Illegal access");
			}
			role = roleService.queryRoleById(String.valueOf(role.getId()));
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "edit";
	}

	public String edit() throws ParseException {
		try {
			if (CommonFunction.isNull(role)|| CommonFunction.isNull(role.getId())) {
				throw new BusinessException("非法访问");
			}
			roleService.upateRole(role);
			this.message ="修改成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "edit";
	}
	
	public String del() throws Exception { 
		try {
			if (CommonFunction.isNull(role.getId())) {
				throw new BusinessException("非法访问");
			}

			roleService.del(String.valueOf(role.getId()));
			this.message = "删除成功";
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		this.role = null;
		return query();
	}
	
	public String batchDel() {
		try {
			if (checks == null) {
				throw new BusinessException(
						"Please select at least one user !");
			}
			roleService.batchDel(checks);
			this.message = "删除成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return query();
	}

	public String preAdd() {
		return "add";
	}
	
	public String add() {
		try {
			if (role == null) {
				throw new BusinessException("Illegal access");
			} 
			roleService.saveRole(role);
			this.role = null;
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "add";
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
