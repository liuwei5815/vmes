package com.xy.cms.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.Constants;
import com.xy.cms.common.SessionBean;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.taglib.Tree;
import com.xy.cms.entity.Menu;
import com.xy.cms.entity.MenuConfig;
import com.xy.cms.service.MenuService;

public class MenuAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Menu menu;
	private MenuConfig menuConfig;

	private MenuService menuService;

	private Long id;
	
	private List<Menu> listParent;
	
	private Long roleId;
	
	// 初始化
	public String init() {
		return "init";
	}

	// 获得树形结构
	public String menuTree() {
		List<Tree> listTree = menuService.getMenuTree();
		request.setAttribute("listTree", listTree);
		return "tree";
	}

	// 分页显示menu
	public String query() {
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				pageMap.put("menu", menu);
				return menuService.queryMenu(pageMap);
			}
		});
		/*try {
			BaseQEntity baseQEntity = new BaseQEntity();
			if (this.getCurrPage() == null || this.getCurrPage().trim().equals(""))
				this.setCurrPage("1");
			baseQEntity.setCurrPage(Integer.parseInt(this.getCurrPage().trim()));
			if (this.getPerPageRows() != null && !"".equals(this.getPerPageRows().trim())) {
				baseQEntity.setPerPageRows(Integer.parseInt(this.getPerPageRows().trim()));
			}
			pageMap = new HashMap();
			pageMap.put("qEntity", baseQEntity);
			pageMap.put("menu", menu);
			QueryResult rs = menuService.queryMenu(pageMap);
			if (rs != null) {
				this.list = rs.getList();
				this.setTotalCount(rs.getTotalCount());
				this.setTotalPage(rs.getTotalPage());

			} else {
				this.list = null;
				this.setCurrPage("0");
				this.setTotalPage("0");
				this.setTotalCount("0");
			}
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}*/
		listParent = menuService.list();
		return "list";
	}

	// 获得所有menu
	public String getAll() {
		list = menuService.list();
		return "list";
	}

	// 预添加
	public String preAdd() {
		getAll();
		return "add";
	}

	// 添加
	public String add() {
		try {
			if (menu == null) {
				throw new BusinessException("Illegal access");
			}
			String width=request.getParameter("width");
			String height=request.getParameter("height");
			menu.setLevel((short) (menu.getSupiorId() == 0 ? 1 : 2));
			menuService.saveMenu(menu);
			if(CommonFunction.isNotNull(width) && CommonFunction.isNotNull(height)){
				menuService.saveMenuConfig(Long.parseLong(width), Long.parseLong(height), menu.getId());
			}
			this.menu = null;
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}

		return "add";
	}

	// 预更新
	public String preEdit() {
		try {
			if (CommonFunction.isNull(menu) || CommonFunction.isNull(menu.getId())) {
				throw new BusinessException("Illegal access");
			}
			menu = menuService.queryById(menu.getId());
			menuConfig=menuService.getConfigByMenuId(menu.getId());
			getAll();
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "edit";
	}

	// 更新
	public String edit() {
		try {
			if (menu == null || CommonFunction.isNull(menu.getId()))
				throw new BusinessException("参数错误");
			
			SessionBean sessionBean=(SessionBean) session.get(Constants.SESSION_BEAN);
			menuService.updateMenu(menu,sessionBean.getAdmin().getRoleId());
			menuService.updateMenuConfig(menuConfig.layerWidth, menuConfig.layerHeight, menu.getId());
			this.menu = null;
			this.message = "修改成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "edit";
	}

	// 删除
	public String del(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out = response.getWriter();
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("非法访问");
			}
			if(menuService.del(id)) {
				json.put("successflag", "1");
				json.put("code",0);
			} else {
				json.put("successflag", "5");
			}			
		}catch (BusinessException e) {		
			json.put("code",1);
			json.put("msg", e.getMessage());
			logger.error(e.getMessage(), e);			
		} catch (Exception e) {
			json.put("code", 1);
			json.put("msg","服务器出现异常" );			
			logger.error(e.getMessage(), e);
		}finally{

			if(out!=null){
				Gson gson = new Gson();
				out.print(gson.toJson(json));
				out.close();
			}					
		}
		
		return NONE;
	}
	
	
	// get and set...
	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Menu> getListParent() {
		return listParent;
	}

	public void setListParent(List<Menu> listParent) {
		this.listParent = listParent;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public MenuConfig getMenuConfig() {
		return menuConfig;
	}

	public void setMenuConfig(MenuConfig menuConfig) {
		this.menuConfig = menuConfig;
	}

	

}
