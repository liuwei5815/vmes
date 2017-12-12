package com.xy.cms.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.xy.cms.bean.MenuBean;
import com.xy.cms.common.CacheFun;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.Constants;
import com.xy.cms.common.LoginUtil;
import com.xy.cms.common.MD5;
import com.xy.cms.common.SessionBean;
import com.xy.cms.common.StringUtil;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.License;
import com.xy.cms.entity.Menu;
import com.xy.cms.service.EmployeeService;
import com.xy.cms.service.LicenseService;
import com.xy.cms.service.LoginService;
import com.xy.cms.service.RoleService;

public class LoginAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Admin admin = null;

	private LoginService loginService;

	private RoleService roleService;

	private EmployeeService employeeService;
	private LicenseService licenseService;
	public List treeList;

	private String oldpwd;
	private String newpwd;
	private String verifypwd;


	public String toLogin() throws UnsupportedEncodingException{
		
		JSONObject accountInfo = LoginUtil.getAdminCookie(this.getRequest());
		
		if(accountInfo!=null){
			Admin admin = loginService.doLogin(accountInfo.getString("account"),accountInfo.getString("pwd"));
			if(admin!=null){
				try{
					loginAfter(admin);
					return "index";
				}
				catch(BusinessException e){
					logger.error(e.getMessage(), e);
			
				}
			}
		}

		return "login";
	}


	public String doLogin() throws Exception{
		try {
			if (CommonFunction.isNull(admin)) {
				throw new BusinessException("请先登录");
			} 
			if (CommonFunction.isNull(admin.getAccount())|| 
					admin.getAccount().equals("")) {
				throw new BusinessException("请输入帐户");
			}
			if (CommonFunction.isNull(admin.getPwd())|| 
					admin.getPwd().equals("")) {
				throw new BusinessException("请输入密码");
			}

			if (CommonFunction.isNull(admin.getAccount()) && 
					CommonFunction.isNull(admin.getPwd())) {
				throw new BusinessException("请输入帐户和密码");
			}
			Admin newAdmin = loginService.doLogin(admin);
			if (CommonFunction.isNull(newAdmin)) {
				throw new BusinessException("账户或密码错误");
			}
			rememberPassWord(newAdmin, response);

			loginAfter(newAdmin);

			return "index";

		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			this.message = "登录时发生服务器异常,Error:" + e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "login";
	}

	public void loginAfter(Admin newAdmin) throws BusinessException{
		if(1!=newAdmin.getRoleId()){
			//有效期控制开始/不控制admin
			License license=licenseService.getLicById(1L);
			if(CommonFunction.isNotNull(license)){
				String ac=license.getAcctCtrl();
				if("1".equals(String.valueOf(ac.charAt(ac.length()-1)))){
					//web账号控制
					if(new Date().getTime()>license.getExpiredDate().getTime()){
						throw new BusinessException("系统已过期");
					}
				} 
			}
			//有效期控制结束
		}
		SessionBean bean = new SessionBean();
		bean.setAdmin(newAdmin);
		bean.setUserId(Integer.valueOf(newAdmin.getId().toString()));
		bean.setUserCode(newAdmin.getAccount());
		bean.setUserName("admin");
		if(CommonFunction.isNotNull(newAdmin.getEmpId())){
			Employee emp=employeeService.getEmployeeById(newAdmin.getEmpId());
			if(CommonFunction.isNotNull(emp)){
				bean.setUserName(emp.getName());
			}
		}
		Map leftMenuMap = loginService.getLeftMenuMap(bean);
		List<Integer> permission = new ArrayList<Integer>();
		List<MenuBean> leftMenu = (List<MenuBean>) leftMenuMap.get("menuList");
		for(int i = 0 ; i < leftMenu.size() ;i++){
			MenuBean menu = (MenuBean)leftMenu.get(i);
			permission.add(Integer.valueOf(menu.getId().toString()));
		}
		bean.setPermission(permission);
		treeList =leftMenu;
		putSessionValue("rolePower",leftMenu);
		putSessionValue(Constants.SESSION_BEAN, bean);
	}



	private void rememberPassWord(Admin admin,HttpServletResponse response) throws UnsupportedEncodingException{

		String rememberPassWord =  request.getParameter("rememberPassWord");
		if(StringUtils.isNotBlank(rememberPassWord)){
			LoginUtil.setAdminCookie(admin, response);
		}
	}



	public String doLogOut() throws Exception{
		ActionContext.getContext().getSession().remove(Constants.SESSION_BEAN);// 清除所有session
		LoginUtil.clearAdminCookie(response);
		return "login";
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public String perupdatepwd() {
		return "pwd";
	}

	/**
	 * 修改密码
	 * @return
	 * @throws Exception
	 */
	public String uptPwd() throws Exception {
		try{
			SessionBean bean = (SessionBean) this.getSession().get(Constants.SESSION_BEAN);
			Admin tempInfo = bean.getAdmin();
			if (!MD5.MD5(oldpwd).equals(tempInfo.getPwd())) {
				throw new BusinessException("原密码错误!");
			}
			System.out.println(newpwd);
			System.out.println(verifypwd);
			if(!newpwd.equals(verifypwd)){
				throw new BusinessException("两次密码输入不一致!");
			}
			loginService.updatePWD(MD5.MD5(newpwd), tempInfo.getId());
			// 把新密码放入session中
			tempInfo.setPwd(MD5.MD5(newpwd));
			bean.setAdmin(tempInfo);
			putSessionValue(Constants.SESSION_BEAN, bean);
			this.message = "修改密码成功";
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			this.message = "修改密码时发生服务器异常,Error:" + e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "pwd";
	}

	/**
	 * 加载左边菜单
	 * @return
	 */
	public String leftmenu(){
		try{
			treeList = (List<Menu>)this.getSession().get("rolePower");
		}catch(Exception e){
			this.message = "查询左菜单时发生异常";
			logger.error(e.getMessage(),e);
		}
		return "leftmenu";
	}

	/**
	 * 更新框架的皮肤
	 * @return
	 */
	public String chgFrameSkin(){
		String skinName = request.getParameter("skinName");
		String themeColor = request.getParameter("themeColor");
		CacheFun.updateConfig("frame_skinname",skinName);
		CacheFun.updateConfig("frame_themecolor",themeColor);
		try {
			loginService.updateConfig("frame_skinname", skinName);
			loginService.updateConfig("frame_themecolor", themeColor);
			this.message = "皮肤切换成功，请退系统重新登录!";
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "frame_skin";
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List getTreeList() {
		return treeList;
	}

	public void setTreeList(List treeList) {
		this.treeList = treeList;
	}


	public String getOldpwd() {
		return oldpwd;
	}


	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}


	public String getNewpwd() {
		return newpwd;
	}


	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}


	public String getVerifypwd() {
		return verifypwd;
	}


	public void setVerifypwd(String verifypwd) {
		this.verifypwd = verifypwd;
	}


	public EmployeeService getEmployeeService() {
		return employeeService;
	}


	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	public LoginService getLoginService() {
		return loginService;
	}


	public RoleService getRoleService() {
		return roleService;
	}


	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public LicenseService getLicenseService() {
		return licenseService;
	}


	public void setLicenseService(LicenseService licenseService) {
		this.licenseService = licenseService;
	}


	public static void main(String[] args) {
		double x=1;
		for(int i=1;i<366;i++){
			x= x*1.0007;
		}
		System.out.println(x);
		String ac="000";
		System.out.println(ac.charAt(ac.length()-1));
		if("1".equals(String.valueOf(ac.charAt(ac.length()-1)))){
			//web账号控制
			System.out.println("控制");
		}else{
			System.out.println("不控制");
		}

	}


}
