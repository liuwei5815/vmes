package com.xy.cms.action.system;

import com.xy.cms.common.CachePool;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionAjaxTemplate;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.AppRole;
import com.xy.cms.entity.AppRolePower;
import com.xy.cms.service.AppRolePowerService;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/***
 * 移动终端角色
 * @author xiaojun
 *
 */
public class AppRolePowerAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AppRolePowerService appRolePowerService;

	
	private Long id;
	
	private List<Long> appModularIds;
	
	private AppRole appRole;
	
	private List<AppRole> appRoleList;
	
	@Override
	public String init() throws Exception {
		return "init";
	}
	
	public String query() throws Exception {
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				pageMap.put("appRole", appRole);
				return appRolePowerService.queryAppRolesPage(pageMap);
			}
		});
		return "query";
		
	}
	
	public String preAdd() {
		return "add";
	}
	
	public String add() {
		try {
			if (appRole == null) {
				throw new BusinessException("Illegal access");
			} 
			appRolePowerService.addAppRole(appRole);
			this.appRole = null;
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "add";
	}
	
	public String preEdit() {
		try {
			if (CommonFunction.isNull(appRole)
					|| CommonFunction.isNull(appRole.getId())) {
				throw new BusinessException("Illegal access");
			}
			appRole = appRolePowerService.getAppRoleById(appRole.getId());
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "edit";
	}
	
	public String edit() {
		try {
			if (CommonFunction.isNull(appRole)|| CommonFunction.isNull(appRole.getId())) {
				throw new BusinessException("非法访问");
			}
			appRolePowerService.updateAppRole(appRole);
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
			if (CommonFunction.isNull(appRole.getId())) {
				throw new BusinessException("非法访问");
			}
			appRolePowerService.delAppRole(appRole);
			this.message = "删除成功";
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		this.appRole = null;
		return "init";
	}
	
	public String initPower(){
		String appRoleId = request.getParameter("appRole.id");
		id = Long.parseLong(appRoleId);
		System.out.println("id = " + id);
		appRole = appRolePowerService.getAppRoleById(id);
		List<AppRolePower> appModularPowers =appRolePowerService.queryAppRolePowerByAppRole(id);
		Set<Long> powerModularId= new HashSet<Long>();
		for (AppRolePower appRolePower : appModularPowers) {
			powerModularId.add(appRolePower.getAppModularId());
			System.out.println(appRolePower.getAppModularId());
		}
		request.setAttribute("powerModular", powerModularId);
		return "rolePower";
	}
	
	public void savePower(){
		
		this.ajaxTemplate(new BaseActionAjaxTemplate() {
			
			@Override
			public Object execute() throws Exception {
				if(id==null){
					throw new BusinessException("非法访问");
				}
				if(!CollectionUtils.isEmpty(appModularIds) && appModularIds.size()>CachePool.appModularList.size()){
					throw new BusinessException("恶意调用");
				}
				appRolePowerService.saveAppRolePower(appModularIds, id);
				return null;
			}
		});
	}
	
	
	
	

	public AppRolePowerService getAppRolePowerService() {
		return appRolePowerService;
	}

	public void setAppRolePowerService(AppRolePowerService appRolePowerService) {
		this.appRolePowerService = appRolePowerService;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getAppModularIds() {
		return appModularIds;
	}

	public void setAppModularIds(List<Long> appModularIds) {
	
		this.appModularIds = appModularIds;
	}

	public AppRole getAppRole() {
		return appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}
	
}
