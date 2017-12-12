package com.xy.cms.action.system;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.xy.cms.bean.JsTree;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.export.FreemarkerConfig;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.AppUser;
import com.xy.cms.entity.Company;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.Eqiupment;
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
import com.xy.cms.service.ModelService;
import com.xy.cms.service.PerformanceService;
import com.xy.cms.service.RoleService;
import com.xy.cms.service.SequenceService;
import com.xy.cms.utils.UploadFileUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PerformanceAction extends BaseAction{
	private DepartmentService departmentService;
	private CompanyService companyService;
	private PerformanceService performanceService;
	private EmployeeService employeeService;
	
	public String init(){
		init_tree();
		/*String str = "";
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    Calendar lastDate = Calendar.getInstance();
	    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
	    str=sdf.format(lastDate.getTime());
	    String beginDate= str;
	    String endDate=DateUtil.format2Short(new Date());
		request.setAttribute("startTime", beginDate);
		request.setAttribute("endTime", endDate);*/
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
		request.setAttribute("startTime", yesterday);
		String claimEnd =new SimpleDateFormat( "yyyy-MM-dd").format(DateUtil.endOfTodDay());
		request.setAttribute("endTime", claimEnd);
		return "init";
	}
	
	/**
	 * 根据部门id查询部门的报工
	 * */
	public String queryEmployee(){
		queryEmp();
		return "query";
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
	public String init_time(){
		init_tree();
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    Calendar lastDate = Calendar.getInstance();
	    lastDate.roll(Calendar.DATE, -1);//日期回滚一天
	    String beginDate = sdf.format(lastDate.getTime());
	    String endDate = DateUtil.format2Short(new Date());
		request.setAttribute("startTime", beginDate);
		request.setAttribute("endTime", endDate);
		request.setAttribute("allTime", endDate);
		return "init_time";
	}
	
	public String queryEmployeeTime(){
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				//部门主键id
				String departmentId = request.getParameter("departmentId");
				if(departmentId==null){
					departmentId = "0";
				}
				//员工号
				String empCode = request.getParameter("empCode");
				pageMap.put("empCode", empCode);
				//员工姓名
				String empName = request.getParameter("empName");
				pageMap.put("empName", empName);
				//报工开始时间
				String claimStart = request.getParameter("claimStart");
				if (CommonFunction.isNull(claimStart)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    Calendar lastDate = Calendar.getInstance();
				    lastDate.roll(Calendar.DATE, -1);//日期回滚一天
				    claimStart = sdf.format(lastDate.getTime());
				}
				claimStart = claimStart + " 00:00:00";
				pageMap.put("claimStart", claimStart);
				//报工结束时间
				String claimEnd = request.getParameter("claimEnd");
				if (CommonFunction.isNull(claimEnd)) {
				   claimEnd = DateUtil.format2Short(new Date());
				}
				claimEnd = claimEnd + " 23:59:59";		
				pageMap.put("claimEnd", claimEnd);
				//标准工作时间
				String workTime=request.getParameter("workTime");
				if(CommonFunction.isNull(workTime)){
					workTime="8";
				}
				request.setAttribute("departmentId",departmentId);
				return performanceService.queryEmployeeTimeByDepId(pageMap,Long.parseLong(departmentId),claimStart,claimEnd,workTime);
			}
		});
		return "query_time";
	}

	private void queryEmp() {
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {

				//部门主键id
				String departmentId = request.getParameter("departmentId");
				if(departmentId==null){
					departmentId = "0";
				}
				//员工号
				String serialNo = request.getParameter("empCode");
				pageMap.put("serialNo", serialNo);
				//员工名称
				String name = request.getParameter("employeeName");
				pageMap.put("name", name);
				//产品名称
				String productName=request.getParameter("productName");
				pageMap.put("productName", productName);
				//工序
				String process=request.getParameter("presName");
				pageMap.put("process", process);
				//生产计划编号
				String planCode=request.getParameter("planCode");
				pageMap.put("planCode", planCode);
				//派工单号
				String plantodoCode=request.getParameter("plantodoCode");
				pageMap.put("plantodoCode",plantodoCode);
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -1);
				String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
				//报工开始时间
				String claimStart = request.getParameter("claimStart");
				if(CommonFunction.isNull(claimStart)){
					claimStart = yesterday + " 00:00:00";
				}else{
					claimStart = claimStart + " 00:00:00";
				}
				pageMap.put("claimStart", claimStart);
				//报工结束时间
				String claimEnd = request.getParameter("claimEnd");
				if(CommonFunction.isNull(claimEnd)){
					claimEnd =new SimpleDateFormat( "yyyy-MM-dd").format( DateUtil.endOfTodDay())+" 23:59:59";
				}else{
					claimEnd=claimEnd+" 23:59:59";
				}
				request.setAttribute("departmentId",departmentId);
				/*request.setAttribute("startTime", claimStart);
				request.setAttribute("endTime", claimEnd);*/
				return performanceService.queryEmployeeByDepId(pageMap,Long.parseLong(departmentId),claimStart,claimEnd);
			}
		});
	}
	private void init_tree() {
		//查询正常使用的组织机构
		List<Department> departmentList = departmentService.getNormalDepartment();
		//查询公司的名称
		Company company =companyService.getCompanyById(1L);
		//初始化js树形结构
		List<JsTree> treeList = new ArrayList<JsTree>();
		if(CommonFunction.isNotNull(company)){
			JsTree root = new JsTree();
			root.setText(company.getName());
			root.setId("0");
			root.setParent("#");
			root.setState(new JsTree.State(true));
			treeList.add(root);
		}else{
			JsTree root = new JsTree();
			root.setText("请先添加企业基本信息");
			root.setId("0");
			root.setParent("#");
			root.setState(new JsTree.State(true));
			treeList.add(root);
		}
		
		for (Department dpt : departmentList) {
			JsTree jsTree = new JsTree();
			jsTree.setId(dpt.getId().toString());
			jsTree.setText(dpt.getName());
			jsTree.setParent(dpt.getPid().toString());
			treeList.add(jsTree);
		}
		String treeJson = JSON.toJSONString(treeList);
		request.setAttribute("orgtree", treeJson);
		request.setAttribute("comName", company==null?"请先添加企业基本信息":company.getName());
	}
	
	/**
	 * 查询单个或多个人员的有效工作时间占比（趋势图）
	 * @return
	 */
	public String queryEmpTime() {
		try {
			String empCode=request.getParameter("empCode");
			if(CommonFunction.isNull(empCode)){
				throw new BusinessException("未选择人员");
			} 
			//标准工作时间
			String workTime=request.getParameter("workTime");
			if(CommonFunction.isNull(workTime)){
				workTime="8";
			}
			//报工开始时间
			String claimStart = request.getParameter("claimStart");
			if (CommonFunction.isNull(claimStart)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    Calendar lastDate = Calendar.getInstance();
			    lastDate.roll(Calendar.DATE, -1);//日期回滚一天
			    claimStart = sdf.format(lastDate.getTime());
			}
			claimStart = claimStart + " 00:00:00";
			//报工结束时间
			String claimEnd = request.getParameter("claimEnd");
			if (CommonFunction.isNull(claimEnd)) {
			   claimEnd = DateUtil.format2Short(new Date());
			}
			claimEnd = claimEnd + " 23:59:59";
			List<String> empCodeList = Arrays.asList(empCode.split(","));
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			for(int i = 0; i < empCodeList.size(); i++){
				Map<String,String> newMap = new HashMap<String,String>();
				List<Map> faultMap = performanceService.queryEmployeeByCodeAndTime(empCodeList.get(i), workTime, claimStart, claimEnd);
				Employee employee = employeeService.getEmployeeBySerialNo(empCodeList.get(i));
				StringBuilder value = new StringBuilder();
				for(int j = 0; j < faultMap.size(); j++){
					value.append(faultMap.get(j).get("quatime") == null ? 0 : faultMap.get(j).get("quatime")).append("-");
					System.out.println("faultMap = " + faultMap.get(j).get("quatime"));
				}
				newMap.put(employee.getName(), (String) value.toString().subSequence(0, value.length() - 1));
				list.add(newMap);
			}
			request.setAttribute("list",list);
			//时间集合
			Date beginDate = DateUtil.format2String(claimStart);
			Date endDate = DateUtil.format2String(claimEnd);
			List<Date> dateList = DateUtil.getDatesBetweenTwoDate(beginDate,endDate);
			StringBuilder str = new StringBuilder();
			for(int i = 0;i < dateList.size(); i++){
				str.append(DateUtil.format2Long(dateList.get(i).getTime())).append("##");
			}
			request.setAttribute("dateList", str.toString().subSequence(0, str.length()-2));
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "empTime";
	}
	/**
	 * 查询单个人员的有效工作时间占比
	 * @return
	 */
	/*public String queryEmpTime(){
		try {
			String empCode=request.getParameter("empId");
			if(CommonFunction.isNull(empCode)){
				throw new BusinessException("未选择人员");
			} 
			//标准工作时间
			String workTime=request.getParameter("workTime");
			if(CommonFunction.isNull(workTime)){
				workTime="8";
			}
			String str = "";
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    Calendar lastDate = Calendar.getInstance();
		    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
		    str=sdf.format(lastDate.getTime());
		    String beginDate= str;
		    String endDate=DateUtil.format2Short(new Date());
			//报工开始时间
			String claimStart = request.getParameter("claimStart");
			//设置默认查询的开始时间为上个月0点
			if(CommonFunction.isNull(claimStart)){
				claimStart = beginDate + " 00:00:00";
			}else{
				claimStart = claimStart + " 00:00:00";
			}
			//报工结束时间
			String claimEnd = request.getParameter("claimEnd");
			//设置默认查询的结束时间为当前时间24点
			if (CommonFunction.isNull(claimEnd)) {
				claimEnd =endDate+" 23:59:59";
			} else {
				claimEnd = claimEnd + " 23:59:59";					
			}
			StringBuffer finishtime=new StringBuffer();
			StringBuffer quatime=new StringBuffer();
			String empName = null;
			List<Map> map=performanceService.queryEmployeeTimeByCode(empCode, workTime, claimStart, claimEnd);
			for (Map map2 : map) {
				finishtime.append(map2.get("finishtime")+",");
				quatime.append(map2.get("quatime")+",");
				empName = map2.get("empName") + "";
			}
			request.setAttribute("finishtime", finishtime);
			request.setAttribute("quatime", quatime);
			request.setAttribute("empName", empName);
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "empTime";
	}*/
	
	/**
	 * 查询多个人员的有效工作时间占比
	 * @return
	 */
	public String queryAllEmpTime(){
		try {
			String currentDate=request.getParameter("allTime");
			if(CommonFunction.isNull(currentDate)){
				currentDate=DateUtil.format("yyyy-MM-dd", new Date());
			}
			//标准工作时间
			String workTime=request.getParameter("workTime");
			if(CommonFunction.isNull(workTime)){
				workTime="8";
			}
			String empIds = request.getParameter("empIds");
			if (CommonFunction.isNull(empIds)) {
				throw new BusinessException("请选择人员");
			}
			StringBuffer deptName=new StringBuffer();
			StringBuffer empName=new StringBuffer();
			StringBuffer quaData=new StringBuffer();
			List<Map> map=performanceService.queryEmployeeTimeByCodes(empIds, currentDate, workTime);
			for (Map map2 : map) {
				deptName.append(map2.get("deptName")+",");
				empName.append(map2.get("empName")+",");
				quaData.append(map2.get("quatime")+",");
			}
			System.out.println(map);
			request.setAttribute("deptName", deptName);
			request.setAttribute("empName", empName);
			request.setAttribute("quaData", quaData);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "allempTime";
	}
	
	/**
	 * 通过员工号和时间查询该员工当天的派工单号及报工详情
	 * @return
	 */
	public String queryOrderDetail() {
		try {
			String empCode = request.getParameter("empCode");
			if (CommonFunction.isNull(empCode)) {
				throw new BusinessException("员工号为空");
			}
			String finishtime = request.getParameter("finishtime");
			if (CommonFunction.isNull(finishtime)) {
				throw new BusinessException("日期为空");
			}
			this.list = performanceService.queryOrderDetailByCodes(empCode, finishtime);
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "detail";
	}
	
	private FreemarkerConfig freemarkerConfig;
	
	public String export() throws TemplateException, IOException{
		try {
			String claimStart=request.getParameter("claimStart");
			String claimEnd=request.getParameter("claimEnd");
			String code=request.getParameter("code");
			String name=request.getParameter("name");
			String fileName="员工报工统计表";
			Template template = freemarkerConfig.getTemplate("/empPlan.xml");
			response.setHeader("Content-type", "application/vnd.ms-excel"); //
			response.setHeader("Content-Disposition","attachment; filename=" 
					+ new String(fileName.getBytes("gb2312"), "ISO8859-1")+".xls");
			List<Map> list=performanceService.queryEmployeeByTime(claimStart,claimEnd); 
			Map<String,Object> dataMap =new HashMap<String, Object>();
			dataMap.put("list", list);
			System.out.println(response.getWriter());
			template.process(dataMap, response.getWriter());
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return null;
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
	
	public PerformanceService getPerformanceService() {
		return performanceService;
	}

	public void setPerformanceService(PerformanceService performanceService) {
		this.performanceService = performanceService;
	}

	public FreemarkerConfig getFreemarkerConfig() {
		return freemarkerConfig;
	}

	public void setFreemarkerConfig(FreemarkerConfig freemarkerConfig) {
		this.freemarkerConfig = freemarkerConfig;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	
	
	
}
