package com.xy.cms.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.Constants;
import com.xy.cms.common.SessionBean;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.taglib.Tree;
import com.xy.cms.entity.Role;
import com.xy.cms.service.PowerService;
import com.xy.cms.service.RoleService;

public class PowerAction extends BaseAction {

	private PowerService powerService;
	private	 RoleService roleService;
	private List<Role> roleList;
	private Role role;
	private String midStr;
	
	public String init(){
		
		return "init";
	}
	public String query() throws Exception{
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				pageMap.put("role", role);
				return powerService.queryRolesPage(pageMap);
			}
		});
		roleList = this.list;
		return "query";
	}
	public String preAdd(){
		return "add";
	}
	public String add(){
		
		return "add";
	}
	public String preEdit(){
		return "edit";
	}
	public String edit(){
		return "edit";
	}
	public String del() throws Exception { 
		String id=request.getParameter("id");
		try {
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("非法访问");
			}

			roleService.del(id);
			this.message = "删除成功";
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		this.role = null;
		return query();
	}
	public String rolepower(){
		try {
			if (CommonFunction.isNull(role)
					|| CommonFunction.isNull(role.getId())) {
				throw new BusinessException("非法访问");
			}
			role = powerService.getRole(role.getId());

		
			List<Tree> listTree = powerService.getMenuTree(role.getId());
			this.request.setAttribute("listTree", listTree);
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "rolepower";
	}
	
	public String ajaxSavePower() throws IOException {

		PrintWriter pw = response.getWriter();
		try {
			if (role == null || role.getId() == null) {
				pw.write("0");
			} else {
				List<Long> menuId = null;	
				
				if (midStr != null && midStr.length() > 0) {
					String[] ms = midStr.split(",");
					if (ms != null && ms.length > 0) {
						menuId = new ArrayList<Long>(ms.length);
						for (String id : ms) {
							if (id != null && id.trim().length() > 0) {
								menuId.add(Long.valueOf(id));
							}
						}
					}
				}
				try {
					powerService.updateRolePowerList(role.getId(),menuId);
					pw.write("1");
				} catch (Exception e) {
					pw.write("0");
				}
			}
		} catch (Exception e) {
			pw.write("0");
		}
		pw.flush();
		pw.close();
		return NONE;
	}
	
	public PowerService getPowerService() {
		return powerService;
	}
	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getMidStr() {
		return midStr;
	}
	public void setMidStr(String midStr) {
		this.midStr = midStr;
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
}
