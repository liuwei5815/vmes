package com.xy.cms.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.taglib.Tree;
import com.xy.cms.entity.Menu;
import com.xy.cms.entity.MenuConfig;
import com.xy.cms.entity.Power;
import com.xy.cms.service.MenuService;

import com.xy.cms.common.CommonFunction;

public class MenuServiceImpl extends BaseDAO implements MenuService {
	// 获得树形结构
	@Override
	public List<Tree> getMenuTree() {

		List<Tree> treeList = new ArrayList<Tree>();
		String hql = "from Menu order by supiorId,orderby";
		List<Menu> list = this.find(hql);
		if (list != null && list.size() > 0) {
			for (Menu menu : list) {
				Tree tree = null;
				if (menu.getLevel() == 1) {
					tree = new Tree(menu.getId().toString(), "", menu.getName());
					tree.setAction("action(" + menu.getId() + ")");
				} else {
					tree = new Tree(menu.getId().toString(), menu.getSupiorId().toString(), menu.getName());
				}

				treeList.add(tree);
			}
		}
		return treeList;
	}

	// 删除
	@Override
	public boolean del(Long menuId) throws BusinessException {
		Menu menu = (Menu) this.get(Menu.class, Long.valueOf(menuId));
		if (menu.getLevel() == 1) {
			String hql = "from Menu where supiorId = " + menu.getId();
			List<Menu> list = this.find(hql);
			if (list != null && list.size() > 0) {
				return false;
			}
		}
		this.delete(menu);
		return true;
	}

	// 获得所有menu集合,作用于添加菜单时提供下拉框和列表页父级菜单
	@Override
	public List<Menu> list() {

		List<Menu> list = this.getAll(Menu.class);
		return list;
	}

	// 分页显示menu
	@Override
	public QueryResult queryMenu(Map<String, Object> map) throws BusinessException {
		QueryResult result = null;
		Map m = new HashMap();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		Menu menu = (Menu) map.get("menu");
		StringBuffer hql = new StringBuffer("from Menu menu where 1=1");
		if (menu != null) {
			if (CommonFunction.isNotNull(menu.getName())) {
				hql.append(" and menu.name like :name");
				m.put("name", "%" + menu.getName() + "%");
			}
			if (CommonFunction.isNotNull(menu.getSupiorId())) {
				hql.append(" and menu.supiorId = :supiorId");
				m.put("supiorId", menu.getSupiorId().toString());
			}
		}
		hql.append(" order by supiorId,orderby");
		result = this.getPageQueryResult(hql.toString(), m, qEntity);
		return result;
	}

	@Override
	public void saveMenu(Menu menu) throws BusinessException {
		if (CommonFunction.isNull(menu.getOrderby())) {
			menu = editNotOrderby(menu);
		} else {
			menu = editWithOrderby(menu);
		}
		this.save(menu);
	}

	@Override
	public void updateMenu(Menu menu, Long roleId) throws BusinessException {
		Menu oldMenu = (Menu) this.get(Menu.class, menu.getId());
		
		//如果父级是调整了
		if(!oldMenu.getSupiorId().equals(menu.getSupiorId())){
			String hql  = "select count(*) from Menu m  where m.supiorId=:id";
			Map m = new HashMap();
			m.put("id", menu.getId());
			Long sum = (Long) this.getUniqueResult(hql,m);
			if(sum>0)
				throw new BusinessException("该菜单下面有子菜单，暂不能调整其分类");
			Long parentId = menu.getSupiorId();
			Long menuId = oldMenu.getId();
			while(parentId!=0){
				StringBuilder  sql = new StringBuilder("select distinct role_id  from sys_power p ");
				sql.append("where  exists(select * from sys_power po where po.role_id = p.role_id and menu_id=:menuId)");
				sql.append(" and not exists(select * from sys_power po where po.role_id = p.role_id and menu_id=:parentId)");
				m.clear();
				m.put("menuId",menuId);
				m.put("parentId", parentId);
				List list = this.getListBySQL(sql.toString(), m);
				for (Object object : list) {
					Power p = new Power();
					p.setMenuId(parentId);
					p.setRoleId(Long.parseLong(object.toString()));
					this.save(p);
				}
				Menu parentMenu = (Menu) this.get(Menu.class, parentId);
				parentId = parentMenu==null?null:parentMenu.getSupiorId();
			}
			if (CommonFunction.isNull(menu.getOrderby())) {
				menu = editNotOrderby(menu);
			} else {
				menu = editWithOrderby(menu);
			}
			oldMenu.setName(menu.getName());
			oldMenu.setState(menu.getState());
			oldMenu.setIsMenu(menu.getIsMenu());
			oldMenu.setSupiorId(menu.getSupiorId());
			oldMenu.setOrderby(menu.getOrderby());
		}else {
			if (CommonFunction.isNull(menu.getOrderby())) {
				menu = editNotOrderby(menu);
			} else {
				menu = editWithOrderby(menu);
			}
			oldMenu.setOrderby(menu.getOrderby());
		}		
		
	}

	// 按ID查询
	@Override
	public Menu queryById(Long id) {

		Menu menu = (Menu) this.get(Menu.class, Long.valueOf(id));
		return menu;
	}

	private Menu editWithOrderby(Menu menu) {
		// 判断是否为一级菜单
		if (menu.getLevel() == 1) {
			// 获得所有orderby大于指定的orderby的集合
			String hql = "from Menu where level = 1 and orderby >=" + menu.getOrderby() + "and id !=" + menu.getId();
			List<Menu> parent = this.find(hql);
			// 判断是否存在大于此orderby的一级菜单，如果存在，将已有的一级菜单orderby+1,并将这些一级菜单下所有的二级菜单的orderby也更改
			if (parent != null && parent.size() > 0) {
				for (Menu newMenu : parent) {
					newMenu.setOrderby(newMenu.getOrderby() + 1);
					update(newMenu);
				}
			}
		} else {
			// 此时为二级菜单
			// 查询同级下并且orderby大于等于指定orderby的子菜单但是不能包含自己
			String hql = "from Menu where supiorId = " + menu.getSupiorId() + "and orderby >=" + menu.getOrderby()
					+ "and id !=" + menu.getId();
			List<Menu> list = this.find(hql);
			if (list != null && list.size() > 0) {
				for (Menu newMenu : list) {
					newMenu.setOrderby(newMenu.getOrderby() + 1);
					update(newMenu);
				}
			}
		}
		return menu;
	}

	private Menu editNotOrderby(Menu menu) {
		// 判断是否为一级菜单
		if (menu.getLevel() == 1) {
			String hql = "from Menu where level = 1 order by orderby desc";
			List<Menu> list = this.find(hql);
			// 判断此集合是否为空
			if (list != null && list.size() > 0) {
				Menu newMenu = list.get(0);
				// 判断最后位的一级菜单orderby是否大于等于9(+1后是否为两位数)，是，不用补0；否，需要补0
				menu.setOrderby(newMenu.getOrderby() + 1);
			} else {
				menu.setOrderby(1);
			}
		} else {
			// 此时为二级菜单
			String hql = "from Menu where supiorId = " + menu.getSupiorId() + "order by orderby desc";
			List<Menu> list = this.find(hql);
			// 判断此集合是否为空
			if (list != null && list.size() > 0) {
				Menu newMenu = list.get(0);
				// 判断其父级菜单的orderby是否大于等于9(+1后是否为两位数)：是，不用补0；否，需要补0
				menu.setOrderby(newMenu.getOrderby() + 1);
			} else {
				menu.setOrderby(1);
			}
		}
		return menu;
	}
	@Override
	public void saveMenuConfig(Long width, Long height,Long menuId) throws BusinessException {
		MenuConfig mc=new MenuConfig();
		mc.setLayerHeight(height);
		mc.setLayerWidth(width);
		mc.setMenuId(menuId);
		this.save(mc);
	}
	@Override
	public void updateMenuConfig(Long width, Long height, Long menuId) throws BusinessException {
		MenuConfig mc=getConfigByMenuId(menuId);
		if(CommonFunction.isNull(mc)){
			mc=new MenuConfig();
			mc.setMenuId(menuId);
		}
		mc.setLayerHeight(height);
		mc.setLayerWidth(width);
		this.save(mc);
	}
	@Override
	public MenuConfig getConfigByMenuId(Long menuId) throws BusinessException {
		String hql="from MenuConfig where menuId=:menuId";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("menuId", menuId);
		return (MenuConfig)this.getUniqueResult(hql, map);
	}
}
