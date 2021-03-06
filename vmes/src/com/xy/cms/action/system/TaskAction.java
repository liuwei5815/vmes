package com.xy.cms.action.system;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.xy.cms.bean.JsTree;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.AppUser;
import com.xy.cms.entity.Company;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Employee;
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
import com.xy.cms.service.TaskService;
import com.xy.cms.utils.UploadFileUtils;

public class TaskAction extends BaseAction{
	private DepartmentService departmentService;
	private CompanyService companyService;
	private TaskService taskService;
	
	public String init(){
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
		String str = "";
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    Calendar lastDate = Calendar.getInstance();
	    lastDate.roll(Calendar.DAY_OF_MONTH, -1);//日期回滚一个月
	    str=sdf.format(lastDate.getTime());
	    String beginDate= str;
	    String endDate=DateUtil.format2Short(new Date());
		request.setAttribute("startTime", beginDate);
		request.setAttribute("endTime", endDate);
		return "init";
	}
	
	/**
	 * 根据部门id查询部门的报工
	 * */
	public String queryEmployee(){
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				//部门主键id
				String departmentId = request.getParameter("departmentId");
				departmentId = departmentId!=null?departmentId:"0";
				//员工号
				String serialNo = request.getParameter("empCode");
				pageMap.put("serialNo", serialNo);
				//员工名称
				String name = request.getParameter("employeeName");
				pageMap.put("name", name);
				//产品名称
				String productName = request.getParameter("productName");
				pageMap.put("productName", productName);
				//工序名称
				String process = request.getParameter("process");
				pageMap.put("process", process);
				//处理时间
				String str = "";
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    Calendar lastDate = Calendar.getInstance();
			    lastDate.roll(Calendar.DAY_OF_MONTH, -1);//日期回滚一天			    
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
				pageMap.put("claimStart", claimStart);
				//报工结束时间
				String claimEnd = request.getParameter("claimEnd");
				//设置默认查询的结束时间为当前时间24点
				if (CommonFunction.isNull(claimEnd)) {
					claimEnd =endDate+" 23:59:59";
				} else {
					claimEnd = claimEnd + " 23:59:59";					
				}
				pageMap.put("claimEnd", claimEnd);
				request.setAttribute("departmentId",departmentId);
				return taskService.queryEmployeeByDepId(pageMap,Long.parseLong(departmentId),claimStart,claimEnd);
			}
		});
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
	/**
	 * 按部门查询任务完成率
	 * @return
	 */
	public String queryDeptTask(){
		try {
			String deptId=request.getParameter("deptId");
			if(CommonFunction.isNull(deptId)){
				throw new BusinessException("部门id为空");
			}
			String claimStart = request.getParameter("claimStart");
			String claimEnd = request.getParameter("claimEnd");
			if(CommonFunction.isNull(claimStart)){
				String str = "";
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    Calendar lastDate = Calendar.getInstance();
			    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
			    str=sdf.format(lastDate.getTime());
			    claimStart= str;
			    claimEnd=DateUtil.format2Short(new Date());
			}
			claimStart=claimStart+" 00:00:00 ";
			claimEnd=claimEnd+" 23:59:59 ";
			Map<String,Object> map=taskService.queryDeptAchievement(Long.parseLong(deptId), claimStart, claimEnd);
			StringBuffer nameStr=new StringBuffer();
			StringBuffer wclStr=new StringBuffer();
			List<Map> stafList=taskService.queryDeptEmpAchievement(Long.parseLong(deptId), claimStart, claimEnd, false);
			for (Map map2 : stafList) {
				nameStr.append(map2.get("name")+",");
				wclStr.append(map2.get("wcl")+",");
			}
			String staf=JSONArray.toJSONString(stafList);
			request.setAttribute("deptWcl", map);
			request.setAttribute("deptId", deptId);
			request.setAttribute("stafList", staf);
			request.setAttribute("nameStr",nameStr);
			request.setAttribute("wclStr",wclStr);
			request.setAttribute("successflag", "1");
			request.setAttribute("claimStart", claimStart);
			request.setAttribute("claimEnd", claimEnd);
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "dept";
	}
	
	/**
	 * 按部门查询任务完成率 前十后十 Ajax请求
	 * @return
	 */
	public String queryDeptTaskByAjax() {
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out = response.getWriter();
			String deptId=request.getParameter("deptId");
			String claimStart = request.getParameter("claimStart");
			String claimEnd = request.getParameter("claimEnd");
			if(CommonFunction.isNull(claimStart)){
				String str = "";
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    Calendar lastDate = Calendar.getInstance();
			    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
			    str=sdf.format(lastDate.getTime());
			    claimStart= str;
			    claimEnd=DateUtil.format2Short(new Date());
			}
			if(CommonFunction.isNull(deptId)){
				throw new BusinessException("部门id为空");
			}
			String orderby = request.getParameter("type");
			boolean flag = true;
			if (CommonFunction.isNotNull(orderby) && "desc".equals(orderby)) {
				flag = false;
			}
			Map<String,Object> map=taskService.queryDeptAchievement(Long.parseLong(deptId), claimStart, claimEnd);
			StringBuffer nameStr=new StringBuffer();
			StringBuffer wclStr=new StringBuffer();
			List<Map> stafList=taskService.queryDeptEmpAchievement(Long.parseLong(deptId), claimStart, claimEnd, flag);
			for (Map map2 : stafList) {
				nameStr.append(map2.get("name")+",");
				wclStr.append(map2.get("wcl")+",");
			}
			json.put("deptWcl", map);
			json.put("deptId", deptId);
			json.put("nameStr",nameStr);
			json.put("wclStr",wclStr);
			json.put("successflag", "1");
			json.put("code",0);
		} catch (BusinessException e) {
			json.put("code",1);
			json.put("msg", e.getMessage());
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			json.put("code", 1);
			json.put("msg","服务器出现异常" );			
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				Gson gson = new Gson();
				out.print(gson.toJson(json));
				out.close();
			}
		}
		return NONE;
	}
	/**
	 * 按人员查询任务完成率
	 * @return
	 */
	public String queryEmpTask(){
		String[] empId=request.getParameter("empId").split(",");
		String[] wcl=request.getParameter("wcl").split(",");
		double wclNum = 0 ;
		for(String w : wcl){
			wclNum=wclNum+Double.parseDouble(w);
		}
		request.setAttribute("wclStr",wclNum/empId.length);
		/*try {
			empId=request.getParameter("empId");
			empIds=request.getParameter("empIds");
			String claimStart = request.getParameter("claimStart");
			String claimEnd = request.getParameter("claimEnd");
			if(CommonFunction.isNull(claimStart)){
				String str = "";
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    Calendar lastDate = Calendar.getInstance();
			    lastDate.roll(Calendar.DAY_OF_MONTH, -1);//日期回滚一天
			    str=sdf.format(lastDate.getTime());
			    claimStart= str;
			    claimEnd=DateUtil.format2Short(new Date());
			}
			if(CommonFunction.isNull(empId)&&CommonFunction.isNull(empIds)){
				throw new BusinessException("人员id为空");
			}
			Map<String,Object> empTask=null;
			List<Map> empTasks=null;
			if(CommonFunction.isNotNull(empId)){
				empTask=taskService.queryEmpAchievement(Long.parseLong(empId),claimStart,claimEnd,true);
			}
			StringBuffer deptStr=new StringBuffer();
			StringBuffer empStr=new StringBuffer();
			StringBuffer wclStr=new StringBuffer();
			int quaNum = 0;//合格数
			int planNum = 0;//计划生产数
			if(CommonFunction.isNotNull(empIds)){
				String[] ids=empIds.split(",");
				empTasks=taskService.queryEmpsAchievement(ids,claimStart,claimEnd,true);
				for (Map map2 : empTasks) {
					deptStr.append(map2.get("deptName")+",");
					empStr.append(map2.get("empName")+",");
					quaNum += Integer.parseInt(map2.get("quaSum").toString());
					planNum += Integer.parseInt(map2.get("planSum").toString());
				}
				wclStr.append(quaNum * 100 / planNum);
			}
			request.setAttribute("empTask", empTask);
			request.setAttribute("empTasks", empTasks);
			request.setAttribute("deptStr", deptStr);
			request.setAttribute("empStr", empStr);
			request.setAttribute("wclStr", wclStr);
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		if(CommonFunction.isNotNull(empId)){
			return "emp";
		}*/
		return "emps";
	}
	
	/**
	 * 任务完成率柱状图
	 * 
	 * */
	public String queryEmpTaskHistogram(){
		String[] wcl=request.getParameter("wcl").split(",");
		StringBuffer wclHistogram = new StringBuffer();
		for(String w : wcl){
			wclHistogram.append(w+"-");
		}
		request.setAttribute("wclHistogram", wclHistogram.toString().subSequence(0, wclHistogram.length()-1));
		return "empTaskHistogram";
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

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	
	 
	
	
	
	
}
