package com.xy.cms.action.system;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.xy.cms.bean.JsTree;
import com.xy.cms.common.CacheFun;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.Environment;
import com.xy.cms.common.FileUtil;
import com.xy.cms.common.ImportExecl;
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
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.License;
import com.xy.cms.entity.Region;
import com.xy.cms.entity.Role;
import com.xy.cms.service.AdminService;
import com.xy.cms.service.AppRoleService;
import com.xy.cms.service.AppUserService;
import com.xy.cms.service.CompanyService;
import com.xy.cms.service.ConfigService;
import com.xy.cms.service.DepartmentService;
import com.xy.cms.service.EmployeeDeptartService;
import com.xy.cms.service.EmployeeService;
import com.xy.cms.service.LicenseService;
import com.xy.cms.service.ModelService;
import com.xy.cms.service.RoleService;
import com.xy.cms.service.SequenceService;
import com.xy.cms.utils.UploadFileUtils;

public class DepartmentAction extends BaseAction{
	private DepartmentService departmentService;
	private CompanyService companyService;
	private ModelService modelService;
	private Company company;
	private Department department;
	private Employee employee;
	private EmployeeService employeeService;
	private EmployeeDeptartService employeeDeptartService;
	private SequenceService sequenceService;
	private File image;
	private String imageFileName;
	private RoleService roleService;
	private Admin admin;
	private AppUser appUser;
	private AppRoleService appRoleService;
	private AdminService adminService;
	private AppUserService appUserService;
	private File file;
	private String fileFileName;
	private ConfigService configService;
	private LicenseService licenseService;
	
	public String init(){
		//查询正常使用的组织机构
		List<Department> departmentList = departmentService.getNormalDepartment();
		//查询公司的名称
//		Company company =companyService.getCompanyById(1L);
//		//初始化js树形结构
		List<JsTree> treeList = new ArrayList<JsTree>();
//		if(CommonFunction.isNotNull(company)){
//			JsTree root = new JsTree();
//			root.setText(company.getName());
//			root.setId("0");
//			root.setParent("#");
//			root.setState(new JsTree.State(true));
//			treeList.add(root);
//		}else{
//			JsTree root = new JsTree();
//			root.setText("请先添加企业基本信息");
//			root.setId("0");
//			root.setParent("#");
//			root.setState(new JsTree.State(true));
//			treeList.add(root);
//		}
		
		
		JsTree root = new JsTree();
		root.setText("组织架构树");
		root.setId("0");
		root.setParent("#");
		root.setState(new JsTree.State(true));
		treeList.add(root);
		
		for (Department dpt : departmentList) {
			JsTree jsTree = new JsTree();
			jsTree.setId(dpt.getId().toString());
			jsTree.setText(dpt.getName());
			jsTree.setParent(dpt.getPid().toString());
			treeList.add(jsTree);
		}
		String treeJson = JSON.toJSONString(treeList);
		request.setAttribute("orgtree", treeJson);
		request.setAttribute("comName", company==null?"组织架构树":company.getName());
		return "init";
	}
	
	/**
	 * 根据部门id查询部门的员工
	 * */
	public String queryEmployee(){
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
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
				//查询该部门下的所有子部门id
				Long[] ids=null;
				if(Long.parseLong(departmentId)!=0){
					List idList=modelService.queryids(Long.parseLong(departmentId));
					ids=new Long[idList.size()];
					for (int i = 0; i < idList.size(); i++) {
						ids[i]=Long.parseLong(idList.get(i).toString());
					}
				}
				request.setAttribute("departmentId",departmentId);
				return modelService.queryEmployeeByDepId(pageMap,ids);
			}
		});
		return "query";
	}
	public static void main(String[] args) {
		Long[] ids=new Long[7];
		ids[0]=1L;
		ids[1]=2L;
		System.out.println(ids);
	}
	/**
	 * 预修改公司信息
	 * */
	public String preEditCompany(){
		try {
			String id = request.getParameter("id");
			Long  companyId = null;
			if(CommonFunction.isNotNull(id)){
				companyId = Long.parseLong(id);
			}
			Company company =companyService.getCompanyById(companyId);
			request.setAttribute("company",company);
			if(CommonFunction.isNotNull(company)){
			/*	String regionProvince = companyService.getRegionById(company.getProvince()).getName();
				request.setAttribute("regionProvince",regionProvince);
				String regionCity = companyService.getRegionById(company.getCity()).getName();
				request.setAttribute("regionCity",regionCity);*/
				Region reg=companyService.getRegionById(company.getArea());
				if(reg.getParentid()==0){//省 直接返回
					request.setAttribute("region",reg);
				}else{//市
					request.setAttribute("region",reg);//市
					request.setAttribute("regionPro",companyService.getRegionById(reg.getParentid()));
				}
				Admin admin = adminService.getAdmin(2L);
				request.setAttribute("admin",admin);
			}
			
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editCompany";
	}
	
	/**
	 * 保存修改后的公司信息
	 * */
	public String editCompany(){
		try {
			if(CommonFunction.isNull(company.getName())){
				throw new BusinessException("请输入公司名称");
			}
			if(CommonFunction.isNull(company.getArea())){
				throw new BusinessException("请选择公司所在区域");
			}
			if(CommonFunction.isNull(company.getAddress())){
				throw new BusinessException("请输入公司地址");
			}
			if(CommonFunction.isNull(company.getContact())){
				throw new BusinessException("请输入公司联系人");
			}
			if(CommonFunction.isNull(company.getTel())){
				throw new BusinessException("请输入公司联系电话");
			}
			if(company.getId()==null){
				companyService.saveCompany(company,admin);
			}else{
				companyService.updateCompany(company,admin);
				this.message = "修改成功";
			}
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editCompany";
	}
	
	/**
	 * 预查询省市(查询出对应的省市，供客户选择)
	 * */
	public String queryProvinceCity(){
		//得到父级id
		String Parentid = request.getParameter("Parentid");
		request.setAttribute("Parentid",Parentid);
		//得到是选择的省还是市
		String nameCn = request.getParameter("nameCn");
		request.setAttribute("nameCn",nameCn);
		return "provinceCityInit";
	}
	/**
	 * 获取部门Tree
	 */
	public String deptTree(){
		//查询正常使用的组织机构
		List<Department> departmentList = departmentService.getNormalDepartment();
		//查询公司的名称
//		Company company =companyService.getCompanyById(1L);
		//初始化js树形结构
		List<JsTree> treeList = new ArrayList<JsTree>();
//		if(CommonFunction.isNotNull(company)){
//			JsTree root = new JsTree();
//			root.setText(company.getName());
//			root.setId("0");
//			root.setParent("#");
//			root.setState(new JsTree.State(true));
//			treeList.add(root);
//		 }else{
//			JsTree root = new JsTree();
//			root.setText("请先添加企业基本信息");
//			root.setId("0");
//			root.setParent("#");
//			root.setState(new JsTree.State(true));
//			treeList.add(root);
//		}
		
		JsTree root = new JsTree();
		root.setText("组织架构树");
		root.setId("0");
		root.setParent("#");
		root.setState(new JsTree.State(true));
		treeList.add(root);
		
		for (Department dpt : departmentList) {
			JsTree jsTree = new JsTree();
			jsTree.setId(dpt.getId().toString());
			jsTree.setText(dpt.getName());
			jsTree.setParent(dpt.getPid().toString());
			treeList.add(jsTree);
		}
		String treeJson = JSON.toJSONString(treeList);
		request.setAttribute("tree", treeJson);
		return "tree";
	}
	/**
	 * 查询省信息
	 * */
	public String queryProvince(){
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				String Parentid = request.getParameter("Parentid");
				request.setAttribute("Parentid",Parentid);
				//通过父级id查询省市
				return companyService.getRegion(pageMap, Long.parseLong(Parentid));
			}
		});
		return "provinceCity";
	}
	
	//上传图片
	public String uploadImage() {
		try {
			String srt = Environment.getWebAppsHome();
			String rootPath = CacheFun.getConfig("company_log");
			String picPath =srt+rootPath;
			String fileName = UploadFileUtils.createNewFileName() + imageFileName.substring(imageFileName.lastIndexOf("."));
			new File(picPath).mkdirs();// 创建新的文件名
			File newFile = new File(picPath+fileName);
			FileUtil.copy(image,newFile);
			//图片压缩
			String bwidth = request.getParameter("bg_width");
			int b_width = Integer.valueOf(CommonFunction.isNotNull(bwidth)?bwidth.trim():"0");
			if(b_width>0){
				String sourceImage = picPath + File.separator + fileName;
				String bheight = request.getParameter("bg_height");
				String bgImg = picPath + File.separator + "bg_"+fileName;
				int b_height = Integer.valueOf(CommonFunction.isNotNull(bheight.trim())?bheight.trim():"0");
				if(b_height>0){
					FileUtil.createSImg(sourceImage, bgImg, b_width, b_height);
				}
				String swidth = request.getParameter("s_width");
				int s_width = Integer.valueOf(CommonFunction.isNotNull(swidth.trim())?swidth.trim():"0");
				String sImg = picPath + File.separator + "s_"+fileName;
				if(s_width>0){
					String sheight = request.getParameter("s_height");
					int s_height = Integer.valueOf(CommonFunction.isNotNull(sheight.trim())?sheight.trim():"0");
					if(s_height>0){
						FileUtil.createSImg(sourceImage, sImg, s_width,s_height);
					}
				}else{
					FileUtil.createSImg(sourceImage, sImg, b_width/2,b_height/2);
				}
			}
			request.setAttribute("code", 1);
			request.setAttribute("fileName", (File.separator + fileName).replace("\\", "/"));
		}
		catch (Exception e) {
			this.message = "上传图片出现异常：服务器出现异常";
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	
	/**
	 * 预添加子部门
	 * */
	public String preAddDepartment(){
		//得到上级pid
		String pid = request.getParameter("pid");
		request.setAttribute("pid", pid);
		return "addDepartment";
	}
	
	/**
	 * 同级部门的名称不能重复
	 * */
	private String departmentName(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			String departmentName=request.getParameter("departmentName");
			String pId=request.getParameter("pId");
			if (CommonFunction.isNull(departmentName)) {
				throw new BusinessException("参数错误");
			}
			boolean department = departmentService .departmentName(departmentName, Long.parseLong(pId));
			//boolean employee = employeeService.provingEmployeeSerialNo(employeeSerialNo);
			if(department){
				json.put("successflag", "1");
				json.put("code",0);
			}else{
				this.message = "部门名称不能重复";
				request.setAttribute("successflag", "0");
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
	 * 添加子部门
	 * */
	public String addDepartment(){
		try {
			boolean dep = departmentService.departmentName(department.getName(),department.getPid());
			if(!dep){
				this.message = "部门名称不能重复";
				request.setAttribute("successflag", "0");
			}else{
				departmentService.addDepartment(department);
				this.message = "添加成功";
				request.setAttribute("successflag", "1");
			}
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "addDepartment";
	}
	
	/**
	 * 预编辑部门
	 * */
	public String preEditDepartment(){
		try {
			//得到部门信息
			String id = request.getParameter("id");
			Department department = departmentService.getDepartmentById(Long.parseLong(id));
			request.setAttribute("department", department);
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editDepartment";
	}
	
	/**
	 * 编辑部门信息
	 * */
	public String editDepartment(){
		try {
				Department newDepartment = departmentService.getDepartmentById(department.getId());
				boolean dep = departmentService .departmentName(department.getName(),newDepartment.getPid());
				if(!dep){
					this.message = "部门名称不能重复";
					request.setAttribute("successflag", "0");
				}else{
					newDepartment.setName(department.getName());
					newDepartment.setUpdateDate(new Date());
					departmentService.updateDepartment(newDepartment);
					this.message = "修改成功";
					request.setAttribute("successflag", "1");
				}
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editDepartment";
	}
	
	/**
	 * 删除部门
	 * */
	public String delDepartment(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到部门id
			String id=request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			//判断id下面有没有子部门
			List<Department> departmentList =  departmentService.getDepartmentByPid(Long.parseLong(id));
			if(CommonFunction.isNotNull(departmentList)){
				throw new BusinessException("还有子部门未删除");
			}
			Department department = departmentService.getDepartmentById(Long.parseLong(id));
			departmentService.delDepartment(department);
			employeeService.delEmployee(employee);
			json.put("successflag", "1");
			json.put("code",0);
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
	 * 上移下移部门
	 * */
	public String upOrDownDepartment(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到部门id
			String id=request.getParameter("id");
			//得到移动标记，上移还是下移
			String sign=request.getParameter("t");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			if (CommonFunction.isNull(sign)) {
				throw new BusinessException("参数错误");
			}
			departmentService.upOrDownDepartment(Long.parseLong(id), sign);
			json.put("successflag", "1");
			json.put("code",0);
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
	 * 预添加人员
	 * */
	public String preAddEmployee(){
		
		try {
			//得到部门id
			String departmentId = request.getParameter("departmentId");
			request.setAttribute("departmentId", departmentId);
			//查询到管理员所有的角色信息
			List<Role> adminRoleList = roleService.getAllRoleNoSuper();
			//查询到应用用户所有的角色信息
			List<AppRole> appRoleList = appRoleService.getAllRole();
			request.setAttribute("adminRoleList", adminRoleList);
			request.setAttribute("appRoleList", appRoleList);
			License license = licenseService.getLicById(1L);
			if(CommonFunction.isNotNull(license)){
				int webNum = adminService.getAllAdmins().size();//已有Web端管理员数目
				int appNum = appUserService.getAppUser().size();//已有移动端管理员数目
				request.setAttribute("license", license);
				request.setAttribute("webNum", webNum);
				request.setAttribute("appNum", appNum);
			}
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		
		return "addEmployee";
	}
	
	/**
	 * 添加时验证员工号是否唯一
	 * */
	public String provingEmployeeSerialNo(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			String employeeSerialNo=request.getParameter("employeeSerialNo");
			if (CommonFunction.isNull(employeeSerialNo)) {
				throw new BusinessException("参数错误");
			}
			boolean employee = employeeService.provingEmployeeSerialNo(employeeSerialNo);
			if(employee){
				json.put("successflag", "1");
				json.put("code",0);
			}else{
				this.message = "员工号已经存在";
				request.setAttribute("successflag", "0");
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
	 * 添加人员
	 * */
	public String addEmployee(){
		try {
			//得到部门id
			String departmentId = request.getParameter("departmentId");
			if(CommonFunction.isNull(employee.getSerialNo())){
				throw new BusinessException("请输入员工号");
			}
			if(CommonFunction.isNull(employee.getName())){
				throw new BusinessException("请输入员工名称");
			}
			if(CommonFunction.isNull(employee.getPhoneNum())){
				throw new BusinessException("请输入员工手机号码");
			}
			if(CommonFunction.isNull(employee.getIdcard())){
				throw new BusinessException("请输入员工身份证号码");
			}
			if (!checkMobile(employee.getPhoneNum())) {
				throw new BusinessException("请输入正确的员工手机号码");
			}
			if (!checkIdCard(employee.getIdcard())) {
				throw new BusinessException("请输入正确的员工身份证号码");
			}
			//得到是否选择创建管理员用户标记
			//创建用户之前判断用户数量
			
			License license = licenseService.getLicById(1L);
			String adminAccount = request.getParameter("adminAccount");
			if(CommonFunction.isNotNull(adminAccount) && adminAccount.equals("1")){
				if(CommonFunction.isNull(admin.getRoleId())){
					throw new BusinessException("请选择管理员用户角色");
				}
				if(license.getWebNum()<1){
					throw new BusinessException("创建数量已达上限");
				}
			}else{
				admin=null;
			}
			//得到是否选择创建应用用户标记
			String userAccount = request.getParameter("userAccount");
			if(CommonFunction.isNotNull(userAccount) && userAccount.equals("1")){
				if(CommonFunction.isNull(appUser.getRoleId())){
					throw new BusinessException("请选择应用用户角色");
				}
				if(license.getAppNum()<1){
					throw new BusinessException("创建数量已达上限");
				}
			}else{
				appUser=null;
			}
			//添加员工的时候，还要添加员工与部门关系
			employeeService.addEmployee(admin , appUser ,employee,Long.parseLong(departmentId));
			this.message = "保存成功";
			request.setAttribute("successflag", "1");
			request.setAttribute("useNum", license.getWebNum());
			request.setAttribute("appNum", license.getAppNum());
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "addEmployee";
	}
	
	   /** 
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港）） 
     * @param mobile 移动、联通、电信运营商的号码段 
     *<p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡） 
     *、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p> 
     *<p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p> 
     *<p>电信的号段：133、153、180（未启用）、189</p> 
     * @return 验证成功返回true，验证失败返回false 
     */  
    public static boolean checkMobile(String mobile) {  
        String regex = "^1[3458]\\d{9}$";  
        return Pattern.matches(regex,mobile);
    }  
	
	  /** 
     * 验证身份证号码 
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母 
     * @return 验证成功返回true，验证失败返回false 
     */  
    public static boolean checkIdCard(String idCard) {  
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex,idCard);  
    } 
	
	/**
	 * 预修改人员信息
	 * */
	public String preEditEmployee(){
		try {
			//得到员工id
			String id = request.getParameter("id");
			Employee employee = employeeService.getEmployeeById(Long.parseLong(id));
			request.setAttribute("employee", employee);
			//查询到管理员所有的角色信息
			List<Role> adminRoleList = roleService.getAllRoleNoSuper();
			//查询到应用用户所有的角色信息
			List<AppRole> appRoleList = appRoleService.getAllRole();
			request.setAttribute("adminRoleList", adminRoleList);
			request.setAttribute("appRoleList", appRoleList);
			//通过员工id查询admin
			Admin admin = adminService.getAdminByEmpId(Long.parseLong(id));
			request.setAttribute("admin", admin);
			//通过员工id查询appUser
			AppUser appUser = appUserService.getAppUserByEmpId(Long.parseLong(id));
			request.setAttribute("appUser", appUser);
			//查询所在部门
			request.setAttribute("dept", departmentService.getDepartmentByEmpId(employee.getId()));
			request.setAttribute("deptId",employeeDeptartService.getEmployeeDeptartByEid(employee.getId()).getDepartId());
		
			License license = licenseService.getLicById(1L);
			if(CommonFunction.isNotNull(license)){
				int webNum = adminService.getAllAdmins().size();//已有Web端管理员数目
				int appNum = appUserService.getAppUser().size();//已有移动端管理员数目
				request.setAttribute("license", license);
				request.setAttribute("webNum", webNum);
				request.setAttribute("appNum", appNum);
			}
		}catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editEmployee";
	}
	
	/**
	 * 修改人员基本信息
	 * */
	public String editEmployee(){
		try {
			if(CommonFunction.isNull(employee.getSerialNo())){
				throw new BusinessException("请输入员工号");
			}
			if(CommonFunction.isNull(employee.getName())){
				throw new BusinessException("请输入员工名称");
			}
			if(CommonFunction.isNull(employee.getPhoneNum())){
				throw new BusinessException("请输入员工手机号码");
			}
			if(CommonFunction.isNull(employee.getIdcard())){
				throw new BusinessException("请输入员工身份证号码");
			}
			if (!checkMobile(employee.getPhoneNum())) {
				throw new BusinessException("请输入正确的员工手机号码");
			}
			if (!checkIdCard(employee.getIdcard())) {
				throw new BusinessException("请输入正确的员工身份证号码");
			}
		/*	if(CommonFunction.isNull(employee.getRid())){
				throw new BusinessException("请选择角色");
			}*/
			String deptId=request.getParameter("employee.deptId");
			/*Department dept=departmentService.getDepartmentByName(name);*/
			Employee newEmployee = employeeService.getEmployeeById(employee.getId());
			newEmployee.setSerialNo(employee.getSerialNo());
			newEmployee.setName(employee.getName());
			newEmployee.setPhoneNum(employee.getPhoneNum());
			newEmployee.setIdcard(employee.getIdcard());
			newEmployee.setBirthday(employee.getBirthday());
			newEmployee.setGender(employee.getGender());
			newEmployee.setJobtitle(employee.getJobtitle());
			/*newEmployee.setRid(employee.getRid());*/
			newEmployee.setUpdateDate(new Date());
			employeeService.updateEmployee(newEmployee,Long.parseLong(deptId));
			//查询到管理员所有的角色信息
			List<Role> adminRoleList = roleService.getAllRoleNoSuper();
			//查询到应用用户所有的角色信息
			List<AppRole> appRoleList = appRoleService.getAllRole();
			request.setAttribute("adminRoleList", adminRoleList);
			request.setAttribute("appRoleList", appRoleList);
			//通过员工id查询admin
			Admin admin = adminService.getAdminByEmpId(newEmployee.getId());
			request.setAttribute("admin", admin);
			//通过员工id查询appUser
			AppUser appUser = appUserService.getAppUserByEmpId(newEmployee.getId());
			request.setAttribute("appUser", appUser);
			this.message = "保存成功";
			request.setAttribute("successflag", "1");
			int[] nums = getUserCount();
			request.setAttribute("useNum", nums[1]);
			request.setAttribute("appNum", nums[0]);
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editEmployee";
	}
	
	/**
	 * 修改Web账号信息
	 * */
	public String editAdmin(){
		try {
			//得到是否选择创建管理员用户标记
			String adminAccount = request.getParameter("adminAccount");
			//通过员工id查询admin
			Admin newDdmin = adminService.getAdminByEmpId(employee.getId());
			//查询到Employee
			Employee emp = employeeService.getEmployeeById(employee.getId());
			License license = licenseService.getLicById(1L);
			if(CommonFunction.isNotNull(adminAccount) && adminAccount.equals("1")){
				if(CommonFunction.isNull(admin.getRoleId())){
					throw new BusinessException("请选择管理员用户角色");
				}
				if(CommonFunction.isNotNull(newDdmin)){
					if(CommonFunction.isNotNull(admin.getPwd())){
						newDdmin.setPwd(MD5.MD5(admin.getPwd()));
					}
					newDdmin.setRoleId(admin.getRoleId());
					adminService.upateAdmin(newDdmin);
				}else{
					if(license.getWebNum()<1){
						throw new BusinessException("创建数量已达上限");
					}
					Admin newDdminS = new Admin();
					if(CommonFunction.isNotNull(admin.getPwd())){
						newDdminS.setPwd(MD5.MD5(admin.getPwd()));
					}else{
						newDdminS.setPwd(MD5.MD5("123456"));
					}
					newDdminS.setRoleId(admin.getRoleId());
					newDdminS.setAccount(emp.getSerialNo());
					newDdminS.setStatus(new Short("1"));
					newDdminS.setAddDate(new Date());
					newDdminS.setEmpId(emp.getId());
					adminService.addAdmin(newDdminS);
				}
			}else{
				adminService.del(newDdmin.getId());
			}
			this.message = "保存成功";
			request.setAttribute("successflag", "1");
			request.setAttribute("useNum", license.getWebNum());
			request.setAttribute("appNum", license.getAppNum());
		}catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editEmployee";
	}
	
	/**
	 * 修改App账号信息
	 * */
	public String editAppUser(){
		try {
			//通过员工id查询appUser
			AppUser newAppUser = appUserService.getAppUserByEmpId(employee.getId());
			//得到是否选择创建应用用户标记
			String userAccount = request.getParameter("userAccount");
			//查询到Employee
			Employee emp = employeeService.getEmployeeById(employee.getId());
			License license = licenseService.getLicById(1L);
			if(CommonFunction.isNotNull(userAccount) && userAccount.equals("1")){
				if(CommonFunction.isNull(appUser.getRoleId())){
					throw new BusinessException("请选择应用用户角色");
				}
				if(CommonFunction.isNotNull(newAppUser)){
					if(CommonFunction.isNotNull(appUser.getPassword())){
						newAppUser.setPassword(MD5.MD5(appUser.getPassword()));
					}
					newAppUser.setRoleId(appUser.getRoleId());
					appUserService.updateAppUser(newAppUser);
				}else{
					if(license.getAppNum()<1){
						throw new BusinessException("创建数量已达上限");
					}
					AppUser newAppUserS = new AppUser();
					if(CommonFunction.isNotNull(appUser.getPassword())){
						newAppUserS.setPassword(MD5.MD5(appUser.getPassword()));
					}else{
						newAppUserS.setPassword(MD5.MD5("123456"));
					}
					newAppUserS.setRoleId(appUser.getRoleId());
					newAppUserS.setAccount(emp.getSerialNo());
					newAppUserS.setStatus(new Short("1"));
					newAppUserS.setAddDate(new Date());
					newAppUserS.setEmpId(emp.getId());
					appUserService.addAppUser(newAppUserS);
				}
				
			}else{
				appUserService.deleteAppUser(newAppUser);
			}
			this.message = "保存成功";
			request.setAttribute("successflag", "1");
			request.setAttribute("useNum", license.getWebNum());
			request.setAttribute("appNum", license.getAppNum());
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "editEmployee";
	}
	
	/**
	 * 删除员工
	 * */
	public String delEmployee(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			String id=request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			Employee employee = employeeService.getEmployeeById(Long.parseLong(id));
			employeeService.delEmployee(employee);
			json.put("successflag", "1");
			json.put("code",0);
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
	 * 预批量导入员工
	 * */
	public String preImportEmpoyee(){
		String departmentId = request.getParameter("departmentId");
		request.setAttribute("departmentId", departmentId);
		return "import";
	}
	
	/**
	 * 获取移动端和Web端剩余账号数量
	 * @return nums[]
	 * 		nums[0] 移动端剩余账号数量
	 * 		nums[1] web端剩余账号数量
	 */
	private int[] getUserCount() {
		try {
			int[] nums = new int[2];
			License license = licenseService.getLicById(1L);
			String acctCtrl = license.getAcctCtrl();
			//计算数目
			
			int totalNum = Integer.valueOf(CacheFun.getConfig("account_num"));//总数目
			int adminNum = adminService.getAllAdmins().size();//已有Web管理员数目
			int appUserNum = appUserService.getAppUser().size();//已有移动端管理员数目
			int bit = Integer.parseInt(acctCtrl, 2);
			System.out.println("==========================================");
			System.out.println("totalNum = " + totalNum + ", adminNum = " + adminNum + ", appUserNum = " + appUserNum);
			System.out.println("==========================================");
			if (bit == 0) {
				nums[0] = -127;
				nums[1] = -127;
			} else if (bit == 1) {
				nums[0] = -127;
				nums[1] = totalNum - adminNum;
			} else if (bit == 2) {
				nums[0] = totalNum - appUserNum;
				nums[1] = -127;
			} else if (bit == 3) {
				nums[0] = totalNum - adminNum - appUserNum;
				nums[1] = nums[0];
			} else {
				throw new BusinessException("错误的控制参数");
			}
			System.out.println("==========================================");
			System.out.println("nums[0](移动端) = " + nums[0] + ", nums[1](Web端) = " + nums[1]);
			System.out.println("==========================================");
			return nums;
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return new int[]{-1, -1};
	}
	
	
	/**
	 * 
	 * */
	public String importEmpoyee(){
		try {
			String departmentId = request.getParameter("departmentId");
			String path = ServletActionContext.getServletContext().getRealPath("/excels");
			File upload = new File(path+File.separator+fileFileName);
			FileUtil.copy(file, upload);
			ImportExecl poi = new ImportExecl(); 
			List<List<String>> list = poi.read(path+File.separator+fileFileName);
			if (list != null) {
				List<Map<Long,Employee>> employeeList=new ArrayList<Map<Long,Employee>>();
				for(int i=1;i<list.size();i++){
					List<String> newList = list.get(i);
					Employee employee = new Employee();
					employee.setSerialNo(CommonFunction.isNotNull(newList.get(0).trim())?newList.get(0).trim():null);
					employee.setName(CommonFunction.isNotNull(newList.get(1).trim())?newList.get(1).trim():null);
					if(CommonFunction.isNotNull(newList.get(2).trim())){
						employee.setGender(newList.get(2).trim().equals("男")?(short)1:(short)2);
					}
					employee.setBirthday(CommonFunction.isNotNull(newList.get(3).trim())?newList.get(3).trim():null);
					employee.setJobtitle(CommonFunction.isNotNull(newList.get(4).trim())?newList.get(4).trim():null);
					employee.setIdcard(CommonFunction.isNotNull(newList.get(5).trim())?newList.get(5).trim():null);
					employee.setPhoneNum(CommonFunction.isNotNull(newList.get(6).trim())?newList.get(6).trim():null);
					//部门
					if(CommonFunction.isNotNull(newList.get(7).trim())){//excel有数据
						 Map<Long,Employee> map = new HashMap<Long,Employee>();//将部门和员工放入map
						//一级部门
						 Department department=departmentService.getDepartmentByName(newList.get(7).trim());
						 try{
							//存在 取id
							 Long deptId=null;
							 if(CommonFunction.isNotNull(department)){
								 deptId=department.getId();
								 //判断二级部门
								 if(CommonFunction.isNotNull(newList.get(8).trim())){//excel二级部门有数据
									 Department departmentSec=departmentService.getDepartmentByNameAndPid(newList.get(8).trim(),deptId);//取二级部门
									 if(CommonFunction.isNotNull(departmentSec)){
										 deptId=departmentSec.getId();//此时存二级部门id
										 if(CommonFunction.isNotNull(newList.get(9).trim())){//excel三级部门有数据
											 Department departmentThr=departmentService.getDepartmentByNameAndPid(newList.get(9).trim(),deptId);//取三级部门
											 if(CommonFunction.isNotNull(departmentThr)){
												 deptId=departmentThr.getId();
											 }else{
												 Department thrDepartment=new Department();//创建三级部门
												 thrDepartment.setPid(deptId);
												 thrDepartment.setName(newList.get(9).trim());
												 deptId=departmentService.addDepartmentWithId(thrDepartment);
											 }
										 }
									 }else{
										 Department subDepartment=new Department();//创建二级部门
										 subDepartment.setPid(deptId);
										 subDepartment.setName(newList.get(8).trim());
										 deptId=departmentService.addDepartmentWithId(subDepartment);
										 //三级部门
										 if(CommonFunction.isNotNull(newList.get(9).trim())){//excel三级部门有数据
											 Department departmentThr=departmentService.getDepartmentByNameAndPid(newList.get(9).trim(),deptId);//取三级部门
											 if(CommonFunction.isNotNull(departmentThr)){
												 deptId=departmentThr.getId();
											 }else{
												 Department thrDepartment=new Department();//创建三级部门
												 thrDepartment.setPid(deptId);
												 thrDepartment.setName(newList.get(9).trim());
												 deptId=departmentService.addDepartmentWithId(thrDepartment);
											 }
										 }
									 }
								 }
							 }else{
								//不存在 创建部门
								 department=new Department();
								 department.setPid(0L);//放入一级部门
								 department.setName(newList.get(7).trim());
								 deptId=departmentService.addDepartmentWithId(department);//新增部门 新增后得到id
								 //判断二级部门
								 if(CommonFunction.isNotNull(newList.get(8).trim())){//excel有数据
									 Department departmentSec=departmentService.getDepartmentByNameAndPid(newList.get(8).trim(),deptId);//取二级部门
									 if(CommonFunction.isNotNull(departmentSec)){
										 deptId=departmentSec.getId();//此时存二级部门id
										 if(CommonFunction.isNotNull(newList.get(9).trim())){//excel三级部门有数据
											 Department departmentThr=departmentService.getDepartmentByNameAndPid(newList.get(9).trim(),deptId);//取三级部门
											 if(CommonFunction.isNotNull(departmentThr)){
												 deptId=departmentThr.getId();
											 }else{
												 Department thrDepartment=new Department();//创建三级部门
												 thrDepartment.setPid(deptId);
												 thrDepartment.setName(newList.get(9).trim());
												 deptId=departmentService.addDepartmentWithId(thrDepartment);
											 }
										 }
									 }else{
										 Department subDepartment=new Department();
										 subDepartment.setPid(deptId);
										 subDepartment.setName(newList.get(8).trim());
										 deptId=departmentService.addDepartmentWithId(subDepartment);
										 if(CommonFunction.isNotNull(newList.get(9).trim())){//excel三级部门有数据
											 Department departmentThr=departmentService.getDepartmentByNameAndPid(newList.get(9).trim(),deptId);//取三级部门
											 if(CommonFunction.isNotNull(departmentThr)){
												 deptId=departmentThr.getId();
											 }else{
												 Department thrDepartment=new Department();//创建三级部门
												 thrDepartment.setPid(deptId);
												 thrDepartment.setName(newList.get(9).trim());
												 deptId=departmentService.addDepartmentWithId(thrDepartment);
											 }
										 }
									 }
								 }
							 }
							 map.put(deptId, employee);
							 employeeList.add(map);
						 }catch(BusinessException e){
							 message = e.getMessage();
							logger.error(e.getMessage(), e);
						 }
					}
				}
				employeeService.importEmpoyee(employeeList);
			}
			this.message = "保存成功";
			request.setAttribute("successflag", "1");
		} catch (IOException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "import";
	}
	/**
	 * 批量删除员工
	 * */
	public String batchDeleteEmployee(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			String employeeId=request.getParameter("employeeId");
			if (CommonFunction.isNull(employeeId)) {
				throw new BusinessException("参数错误");
			}
			String[] id = employeeId.split(",");
			employeeService.batchDeleteEmployee(id);
			this.message = "批量删除员工成功";
			json.put("successflag", "1");
			json.put("code",0);
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
	
	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public ModelService getModelService() {
		return modelService;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public EmployeeDeptartService getEmployeeDeptartService() {
		return employeeDeptartService;
	}

	public void setEmployeeDeptartService(EmployeeDeptartService employeeDeptartService) {
		this.employeeDeptartService = employeeDeptartService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public AppRoleService getAppRoleService() {
		return appRoleService;
	}

	public void setAppRoleService(AppRoleService appRoleService) {
		this.appRoleService = appRoleService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public AppUserService getAppUserService() {
		return appUserService;
	}

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public LicenseService getLicenseService() {
		return licenseService;
	}

	public void setLicenseService(LicenseService licenseService) {
		this.licenseService = licenseService;
	}
	
	
	
	
}
