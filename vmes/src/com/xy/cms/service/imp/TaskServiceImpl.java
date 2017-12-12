package com.xy.cms.service.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.scheduling.TaskScheduler;

import com.xy.cms.bean.ViewSearchBean;
import com.xy.cms.common.CacheFun;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.Environment;
import com.xy.cms.common.MD5;
import com.xy.cms.common.QRcode;
import com.xy.cms.common.SysConfig;
import com.xy.cms.common.UploadFileUtils;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.opt.QrCodeOptUtil;
import com.xy.cms.common.view.TreeView;
import com.xy.cms.entity.Menu;
import com.xy.cms.entity.MenuConfig;
import com.xy.cms.entity.TableColumnType;
import com.xy.cms.entity.TableColumns;
import com.xy.cms.entity.TableColumns.DataType;
import com.xy.cms.entity.TableRelationship;
import com.xy.cms.entity.Tables;
import com.xy.cms.entity.View;
import com.xy.cms.entity.ViewOrder;
import com.xy.cms.entity.ViewSearch;
import com.xy.cms.service.ModelService;
import com.xy.cms.service.PerformanceService;
import com.xy.cms.service.SequenceService;
import com.xy.cms.service.TableColumnsService;
import com.xy.cms.service.TaskService;

public class TaskServiceImpl extends BaseDAO implements TaskService {


	@Override
	public QueryResult queryEmployeeByDepId(Map pageMap,Long Depid,String start,String end) {
		Map par=new HashMap();
		QueryResult result = null;
		BaseQEntity qEntity = (BaseQEntity) pageMap.get("qEntity");
		StringBuffer sql= new StringBuffer(" ");
		sql.append(" SELECT se.id,sd.name AS deptName, se.name AS empName,se.serial_no AS empCode,pt.process AS process,plan.product_name AS productName,pt.taskname, ");
		sql.append(" SUM(claim.qualified_num) AS quaSum,SUM(claim.disqualified_num) AS disSum,SUM(claim.plannum) AS planSum ,ROUND(SUM(claim.qualified_num)*100/SUM(claim.plannum),2) AS wcl ");
		sql.append(" FROM sys_employee_deptart  AS sed  ");
		sql.append(" JOIN sys_employee AS se ON sed.employee_id=se.id ");
		sql.append(" JOIN sys_department AS sd ON sed.depart_id=sd.id ");
		sql.append(" JOIN produceplan_todo_claim AS claim ON claim.executor_emp_id=se.id ");
		sql.append(" JOIN produceplan_todo AS pt ON pt.id=claim.todo_id ");
		sql.append(" JOIN produceplan AS plan ON plan.id=pt.produceplan_id WHERE 1=1 ");
		if(Depid != null && Depid !=0){
			sql.append(" AND sed.depart_id=:Depid");
			par.put("Depid", Depid);
		}
		//员工号
		String serialNo = (String) pageMap.get("serialNo");
		if (!StringUtils.isBlank(serialNo)) {
			sql.append(" AND se.serial_no LIKE :serialNo");
			par.put("serialNo", "%" + serialNo + "%");
		}
		//员工名称
		String name = (String) pageMap.get("name");
		if (!StringUtils.isBlank(name)) {
			sql.append(" AND se.name LIKE :name");
			par.put("name", "%" + name + "%");
		}
		//报工开始时间
		String claimStart = (String) pageMap.get("claimStart");
		if (!StringUtils.isBlank(claimStart)) {
			sql.append(" AND claim.add_date >= :claimStart");
			par.put("claimStart", claimStart);
		}
		//报工结束时间
		String claimEnd = (String) pageMap.get("claimEnd");
		if (!StringUtils.isBlank(claimEnd)) {
			sql.append(" AND claim.finish_time <= :claimEnd");
			par.put("claimEnd", claimEnd);
		}
		//产品名称
		if (CommonFunction.isNotNull(pageMap.get("productName"))) {
			sql.append(" AND plan.product_name LIKE :productName");
			par.put("productName", "%" + pageMap.get("productName") + "%");
		}
		//工序名称
		if (CommonFunction.isNotNull(pageMap.get("process"))) {
			sql.append(" AND pt.process LIKE :process");
			par.put("process", "%" + pageMap.get("process") + "%");
		}
		sql.append(" GROUP BY claim.executor_emp_id,deptName, empName,empCode,process,productName,taskname ");
		result=this.getPageQueryResultSQLToMap(sql.toString(), par, qEntity);
		return result;
	}
	
	@Override
	public Map<String, Object> queryDeptAchievement(Long deptId,String beginDate,String endDate) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" select d.name as name,round(sum(claim.qualified_num)*100/sum(claim.plannum),2) as wcl  from produceplan_todo_claim as claim,sys_appuser as u,sys_employee as e,");
		sql.append("sys_employee_deptart ed,sys_department d");
		sql.append(" where claim.executor=u.id and u.emp_id=e.id and ed.employee_id=e.id and d.id=ed.depart_id and ed.depart_id=:deptId");
		sql.append(" AND claim.finish_time >=:beginDate AND claim.finish_time<=:endDate ");
		paramMap.put("deptId", deptId);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		System.out.println("sql:"+sql.toString());
		Map<String,Object> deptInfo = this.getFirstResultBySqlToMap(sql.toString(),paramMap);
		if(deptInfo.get("wcl") == null){
			deptInfo.put("wcl", 0.0);
		}
		return deptInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> queryDeptEmpAchievement(Long deptId,
			String beginDate, String endDate, boolean isAsc) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("deptId", deptId);
		StringBuilder sql = new StringBuilder();
		sql.append("  select e.name as name,COALESCE(round(sum(claim.qualified_num)*100/sum(claim.plannum),2),0) as wcl ");
		sql.append(" from sys_employee e left join produceplan_todo_claim claim on claim.executor_emp_id= e.id  ");
		sql.append(" where exists(select 1 from sys_employee_deptart ed where ed.employee_id=e.id and ed.depart_id=:deptId) ");
		//TODO:统计十人时 限制了时间数据就只能显示部分人
		if(StringUtils.isNotBlank(beginDate)){
			sql.append(" and finish_time >=:beginDate ");
			paramMap.put("beginDate", beginDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sql.append(" and finish_time <=:endDate ");
			paramMap.put("endDate", endDate);
		}
		sql.append(" group by e.id order by wcl ");
		if(!isAsc){
			sql.append(" desc");
		}
		sql.append(" limit 10");
		List<Map> empInfo=this.getListBySQLToMap(sql.toString(), paramMap);
		return empInfo;
	}

	@Override
	public Map<String, Object> queryEmpAchievement(Long empId, String beginDate, String endDate,
			boolean isAsc) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT se.id,sd.name AS deptName,se.name AS empName,se.serial_no AS empCode,  ");
		sql.append(" SUM(claim.qualified_num) AS quaSum,SUM(claim.plannum) AS planSum,ROUND((SUM(claim.qualified_num)/SUM(claim.plannum))*100,2) AS wcl ");
		sql.append(" FROM sys_employee_deptart  AS sed JOIN sys_employee AS se ON sed.employee_id=se.id JOIN sys_department AS sd ON sed.depart_id=sd.id ");
		sql.append(" JOIN produceplan_todo_claim AS claim ON claim.executor_emp_id=se.id WHERE se.id =:empId ");
		sql.append(" AND claim.add_date >=:beginDate AND claim.finish_time <=:endDate ");
		sql.append(" GROUP BY claim.executor_emp_id,deptName, empName,empCode ");
		paramMap.put("empId", empId);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		Map<String,Object>  empInfo=this.getFirstResultBySqlToMap(sql.toString(), paramMap);
		return empInfo;
	}

	@Override
	public List<Map> queryEmpsAchievement(String[] empId, String beginDate, String endDate, boolean isAsc) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT se.id,sd.name AS deptName,se.name AS empName,se.serial_no AS empCode,  ");
		sql.append(" SUM(claim.qualified_num) AS quaSum,SUM(claim.plannum) AS planSum,ROUND((SUM(claim.qualified_num)/SUM(claim.plannum))*100,2) AS wcl ");
		sql.append(" FROM sys_employee_deptart  AS sed JOIN sys_employee AS se ON sed.employee_id=se.id JOIN sys_department AS sd ON sed.depart_id=sd.id ");
		sql.append(" JOIN produceplan_todo_claim AS claim ON claim.executor_emp_id=se.id WHERE se.id in(:empId) ");
		sql.append(" AND claim.add_date >=:beginDate AND claim.finish_time <=:endDate ");
		sql.append(" GROUP BY claim.executor_emp_id,deptName, empName,empCode ");
		paramMap.put("empId", empId);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		return this.getListBySQLToMap(sql.toString(), paramMap);
		 
	}
	 
}
