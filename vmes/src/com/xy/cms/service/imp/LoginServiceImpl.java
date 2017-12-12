package com.xy.cms.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.xy.cms.bean.MenuBean;
import com.xy.cms.common.MD5;
import com.xy.cms.common.SessionBean;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.Menu;
import com.xy.cms.service.LoginService;
import com.xy.cms.common.CommonFunction;

public class LoginServiceImpl extends BaseDAO implements LoginService {
	@Override
	public Admin doLogin(String acount, String pwd){
		String hql = " from Admin ad where ad.account=:account and ad.pwd=:pwd";
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("account",acount);
		param.put("pwd",pwd);
		Admin result = null;
		List list = this.getList(hql, param);
		if(list!=null && list.size()>0){
			result = new Admin();
			result =(Admin) list.get(0);
			result.setUpdateDate(new Date());
		}
		return result;
	}

	public Admin doLogin(Admin admin) throws BusinessException {
		String hql = " from Admin ad where ad.account=:account and ad.pwd=:pwd";
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("account", admin.getAccount());
		param.put("pwd",MD5.MD5(admin.getPwd()));
		Admin result = null;
		List list = this.getList(hql, param);
		if(list!=null && list.size()>0){
			result = new Admin();
			result =(Admin) list.get(0);
			result.setUpdateDate(new Date());
		}
		return result;
	}

	public List<Menu> getLeftMenu() throws BusinessException {
		String hql = " from Menu mn where mn.state=1 and mn.isMenu=0";
		List<Menu>  list = this.find(hql);
		return list;
	}
	
	public Map getLeftMenuMap(SessionBean bean) throws BusinessException {
		StringBuilder hql = new StringBuilder("from Menu a where state=1  and exists(select 1 from Power b where a.id=b.menuId and b.roleId= :roleId) order by level,orderby ");
		try{
			Map listMap = new HashMap();
			Map param = new HashMap();
			param.put("roleId", bean.getAdmin().getRoleId());
			List<Menu> list = this.getList(hql.toString(), param);
			List<MenuBean> parentList = new ArrayList<MenuBean>();
			Map<Long,MenuBean> menuMap = new HashMap<Long, MenuBean>();
			Map cdMap = new HashMap();
			for(Menu m : list){
				if(Integer.parseInt(m.getIsMenu()) == 0){
					MenuBean menuBean = new MenuBean();
					BeanUtils.copyProperties(m, menuBean);
					
					if(m.getLevel()==1){
						menuMap.put(m.getId(),menuBean);
						parentList.add(menuBean);
					}
					else{
						
						MenuBean parentMenu = menuMap.get(m.getSupiorId());
						if(parentMenu==null)
							continue;
						menuMap.put(m.getId(),menuBean);
						if(parentMenu.getChildNode()==null){
							parentMenu.setChildNode(new ArrayList<MenuBean>());
						}
						parentMenu.getChildNode().add(menuBean);
					}
					
				}
				else{
					cdMap.put(m.getUrl(), m);
				}
				
			}
			
			listMap.put("handle",cdMap);
			listMap.put("menuList", parentList);
			return listMap;
		}catch (Exception e) {
			throw new BusinessException("获取菜单权限发生异常",e);
		}
	}

	public void editLastDate(Admin admin) throws BusinessException {
	    Admin newAdmin = (Admin)this.get(Admin.class, admin.getId());
	    newAdmin.setUpdateDate(new Date());
	    this.update(newAdmin);
	}

	public void updatePWD(String pwd, Long id) throws BusinessException {
		Admin newAdmin = (Admin)this.get(Admin.class, id);
		newAdmin.setPwd(pwd);
		this.update(newAdmin);
	}

	public void updateConfig(String confName, String confValue)
			throws BusinessException {
		if(CommonFunction.isNull(confName)){
			throw new BusinessException("参数错误,confName不能为空");
		}
		if(CommonFunction.isNull(confValue)){
			throw new BusinessException("参数错误,confValue不能为空");
		}
		String hql = "update Config set confValue='" + confValue + "' where confName='" + confName + "'";
		this.execute(hql);
	}


}
