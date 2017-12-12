package com.xy.cms.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.xy.cms.bean.JsTree;
import com.xy.cms.common.CacheFun;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.MD5;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.AppRole;
import com.xy.cms.entity.AppUser;
import com.xy.cms.entity.Company;
import com.xy.cms.entity.Customer;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.License;
import com.xy.cms.entity.Role;
import com.xy.cms.service.AccountService;
import com.xy.cms.service.AdminService;
import com.xy.cms.service.AppRoleService;
import com.xy.cms.service.AppUserService;
import com.xy.cms.service.EmployeeService;
import com.xy.cms.service.LicenseService;
import com.xy.cms.service.ModelService;
import com.xy.cms.service.RoleService;

public class AccountAction extends BaseAction{
	private AccountService accountService;
	private RoleService roleService;
	private AppRoleService appRoleService;
	private ModelService modelService;
	private AdminService adminService;
	private LicenseService licenseService;
	private AppUser appUser;
	private AppUserService appUserService;
	private EmployeeService employeeService;
	private Admin admin;
	
	/**
	 * 系统账号管理初始页面
	 * */
	public String init(){
		try {
			License license = licenseService.getLicById(1L);
			int webNum = adminService.getAllAdmins().size();//Web端已使用数量
			int appNum = appUserService.getAppUser().size();//终端已使用账号数量
			request.setAttribute("license", license);
			request.setAttribute("webNum", webNum);
			request.setAttribute("appNum", appNum);
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "init";
	}
	
	/**
	 * 系统账号管理列表页面
	 * */
	public String query() throws Exception{
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				String type=request.getParameter("type");
				//默认查询web类型账号
				if(CommonFunction.isNull(type)){
					type="web";
				}
				pageMap.put("type",type);
				String name=request.getParameter("cusName");
				pageMap.put("name", name);
				return accountService.queryAccountPage(pageMap);
			}
		});
		return "query";
	}
	
	/**
	 * 预添加账号
	 * */
	public String preAdd() {
		try {
			String type=request.getParameter("type");
			if(CommonFunction.isNull(type)){
				throw new BusinessException("账户类型为空");
			}
			if(type.equals("web")){
				//查询到管理员所有的角色信息
				List<Role> adminRoleList = roleService.getAllRoleNoSuper();
				request.setAttribute("roleList", adminRoleList);
				request.setAttribute("type", "Web端");
			}else{
				//查询到应用用户所有的角色信息
				List<AppRole> appRoleList = appRoleService.getAllRole();
				request.setAttribute("roleList", appRoleList);
				request.setAttribute("type", "终端");
			}
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		
		return "add";
	}
	
	
	/**
	 * 保存账号信息（Web或者App）
	 * @throws BusinessException 
	 */
	public String addAdminCount(){
		try {
			String type=request.getParameter("type");
			if(CommonFunction.isNull(type)){
				throw new BusinessException("未选择账号类型");
			}
			//员工id
			String empId=request.getParameter("empId");
			if(CommonFunction.isNull(empId)){
				throw new BusinessException("员工id为空");
			}
			//员工姓名
			String empName=request.getParameter("empName");
			//角色id
			String roleId=request.getParameter("roleId");
			if(CommonFunction.isNull(roleId)){
				throw new BusinessException("角色id为空");
			}
			//密码
			String pwd=request.getParameter("pwd");
			//查询公司剩余账号数量信息
			License license = licenseService.getLicById(1L);
			//通过人员id查询人员信息
			Employee employee = employeeService.getEmployeeById(Long.parseLong(empId));
			if(type.equals("web")){
				if(license.getWebNum()<1){
					throw new BusinessException("创建数量已达上限");
				}
				Admin newDdminS = new Admin();
				newDdminS.setRoleId(Long.parseLong(roleId));
				newDdminS.setAccount(employee.getSerialNo());
				if(CommonFunction.isNotNull(pwd)){
					newDdminS.setPwd(MD5.MD5(pwd));
				}else{
					newDdminS.setPwd(MD5.MD5("123456"));
				}
				newDdminS.setStatus(new Short("1"));
				newDdminS.setAddDate(new Date());
				newDdminS.setEmpId(Long.parseLong(empId));
				adminService.addAdmin(newDdminS);
			}else if(type.equals("app")){
				if(license.getAppNum()<1){
					throw new BusinessException("创建数量已达上限");
				}
				AppUser newAppUserS = new AppUser();
				newAppUserS.setRoleId(Long.parseLong(roleId));
				newAppUserS.setAccount(employee.getSerialNo());
				newAppUserS.setPassword(MD5.MD5(pwd));
				if(CommonFunction.isNotNull(pwd)){
					newAppUserS.setPassword(MD5.MD5(pwd));
				}else{
					newAppUserS.setPassword(MD5.MD5("123456"));
				}
				newAppUserS.setStatus(new Short("1"));
				newAppUserS.setAddDate(new Date());
				newAppUserS.setEmpId(Long.parseLong(empId));
				appUserService.addAppUser(newAppUserS);
			}
			this.message = "保存成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "add";
	}
	
	
	
	
	/**
	 * 查询所有员工（需要排除已经创建账号的员工）
	 * @return
	 * @throws Exception
	 */
	public String empInit(){
		String type = request.getParameter("type");
		session.put("types", type);
		return "empinit";
	}
	
	
	public String queryEmployee(){
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				String type = (String) session.get("types");
				pageMap.put("type", type);
				//部门主键id
				String departmentId = request.getParameter("departmentId");
				if(departmentId==null){
					departmentId = "0";
				}
				//员工号
				String serialNo = request.getParameter("employeeSerialNo");
				pageMap.put("serialNo", serialNo);
				//员工名称
				String name = request.getParameter("employeeName");
				pageMap.put("name", name);
				//员工电话
				String phoneNum = request.getParameter("employeePhoneNum");
				pageMap.put("phoneNum", phoneNum);
				//员工身份证
				String idcard = request.getParameter("employeeIdcard");
				pageMap.put("idcard", idcard);
				request.setAttribute("departmentId",departmentId);
				return employeeService.queryNoAccountEmployee(pageMap);
			}
		});
		return "list";
	}
	
	
	public String del() throws Exception { 
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到id
			String id=request.getParameter("id");
			String type=request.getParameter("type");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			if(type.equals("web")){//web端
				Admin admin=accountService.queryAdminById(Long.parseLong(id));
				if(CommonFunction.isNull(admin)){
					throw new BusinessException("该管理员已删除");
				}else{
					accountService.delAdmin(admin);
					json.put("successflag", "1");
					json.put("code",0);
				}
			}else{//移动端
				AppUser appUser=accountService.queryAppUserById(Long.parseLong(id));
				if(CommonFunction.isNull(appUser)){
					throw new BusinessException("该管理员已删除");
				}else{
					accountService.delAppUser(appUser);
					json.put("successflag", "1");
					json.put("code",0);
				}
			}
		} catch (BusinessException e) {		
			json.put("code",1);
			json.put("msg", e.getMessage());
			logger.error(e.getMessage(), e);			
		}catch (Exception e) {
			json.put("code", 1);
			json.put("msg","服务器出现异常" );			
			logger.error(e.getMessage(), e);
		}
		finally{

			if(out!=null){
				Gson gson = new Gson();
				out.print(gson.toJson(json));
				out.close();
			}					
		}			
		return NONE;
	}
	
	/**
	 * 预编辑员工(web端或终端)
	 * @return
	 */
	public String preEdit() {
		try {
			String id = request.getParameter("id");
			String type = request.getParameter("type");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			if (CommonFunction.isNull(type) || "web".equals(type)) {
				request.setAttribute("type", type);
				List rs = accountService.queryAdminAndNameById(Long.parseLong(id));
				if (rs != null && rs.size() > 0) {
					request.setAttribute("res", rs.get(0));
				}
			} else if ("app".equals(type)) {
				request.setAttribute("type", type);
				List rs = accountService.queryAppUserAndNameById(Long.parseLong(id));
				if (rs != null && rs.size() > 0) {
					request.setAttribute("res", rs.get(0));
				}
			} else {
				throw new BusinessException("参数错误");
			}
		}
		catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "edit";
	}
	
	/**
	 * 修改密码(web端或终端)
	 * @return
	 */
	public String updatePassword() {
		try {
			String id = request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			String pwd = request.getParameter("password");
			String type = request.getParameter("type");
			if (CommonFunction.isNull(type) || "web".equals(type)) {
				Admin newAdmin = new Admin();
				newAdmin.setId(Long.parseLong(id));
				if(CommonFunction.isNotNull(pwd)){
					newAdmin.setPwd(MD5.MD5(pwd));
				}else{
					newAdmin.setPwd(MD5.MD5("123456"));
				}
				accountService.updateAdmin(newAdmin);
			} else if ("app".equals(type)) {
				AppUser newAppUser = new AppUser();
				newAppUser.setId(Long.parseLong(id));
				if(CommonFunction.isNotNull(pwd)){
					newAppUser.setPassword(MD5.MD5(pwd));
				}else{
					newAppUser.setPassword(MD5.MD5("123456"));
				}
				accountService.updateAppUser(newAppUser);
			} else {
				throw new BusinessException("参数错误");
			}
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		} 
		return "edit";
	}
	
	public AccountService getAccountService() {
		return accountService;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public AppRoleService getAppRoleService() {
		return appRoleService;
	}
	public void setAppRoleService(AppRoleService appRoleService) {
		this.appRoleService = appRoleService;
	}
	public ModelService getModelService() {
		return modelService;
	}
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}
	public AdminService getAdminService() {
		return adminService;
	}
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	public LicenseService getLicenseService() {
		return licenseService;
	}
	public void setLicenseService(LicenseService licenseService) {
		this.licenseService = licenseService;
	}
	public AppUser getAppUser() {
		return appUser;
	}
	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
	public AppUserService getAppUserService() {
		return appUserService;
	}
	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
}
