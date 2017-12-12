package com.xy.cms.service.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;

import com.xy.cms.bean.ViewSearchBean;
import com.xy.cms.common.CacheFun;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.Environment;
import com.xy.cms.common.MD5;
import com.xy.cms.common.PseudoSqlUtil;
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

public class PerformanceServiceImpl extends BaseDAO implements PerformanceService {


	@Override
	public QueryResult queryEmployeeByDepId(Map pageMap,Long Depid,String start,String end) {
		Map par=new HashMap();
		QueryResult result = null;
		BaseQEntity qEntity = (BaseQEntity) pageMap.get("qEntity");
		StringBuffer hql= new StringBuffer(" ");
		hql.append(" SELECT sd.name AS deptName,se.name AS empName,se.serial_no AS empCode, ");
		hql.append(" plan.plan_code,plan.product_name, ");
		hql.append(" todo.PROCESS,todo.todo_code,todo.taskname, ");
		hql.append(" claim.plannum,claim.qualified_num,claim.disqualified_num ");
		hql.append(" FROM  sys_employee_deptart  AS sed JOIN sys_employee AS se ON sed.employee_id=se.id  ");
		hql.append(" JOIN sys_department AS sd ON sed.depart_id=sd.id ");
		hql.append(" JOIN produceplan_todo_claim AS claim ON claim.executor_emp_id=se.id ");
		hql.append(" JOIN produceplan_todo AS todo ON claim.todo_id=todo.id ");
		hql.append(" JOIN  produceplan as plan on plan.id=todo.produceplan_id ");
		
		if(Depid != null && Depid !=0){
			hql.append(" where sd.id=:Depid");
			par.put("Depid", Depid);
		}
		if(StringUtils.isNotBlank(start)){
			hql.append(" and claim.finish_time>=:begindate ");
			par.put("begindate",start);
		}
		if(StringUtils.isNotBlank(end)){
			hql.append(" and claim.finish_time<=:enddate ");
			par.put("enddate", end);
		}
		//员工号
		if(CommonFunction.isNotNull(pageMap.get("serialNo"))){
			hql.append(" and  se.serial_no like:serialNo ");
			par.put("serialNo", "%"+pageMap.get("serialNo")+"%");
		}
		//员工名称
		if(CommonFunction.isNotNull(pageMap.get("name"))){
			hql.append(" and  se.name like:name ");
			par.put("name", "%"+pageMap.get("name")+"%");
		}
		//产品名称
		if(CommonFunction.isNotNull(pageMap.get("productName"))){
			hql.append(" and  plan.product_name like:productName ");
			par.put("productName", "%"+pageMap.get("productName")+"%");
		}
		//工序
		if(CommonFunction.isNotNull(pageMap.get("process"))){
			hql.append(" and  todo.PROCESS like:process ");
			par.put("process", "%"+pageMap.get("process")+"%");
		}
		//生产计划编号
		if(CommonFunction.isNotNull(pageMap.get("planCode"))){
			hql.append(" and  plan.plan_code like:planCode ");
			par.put("planCode", "%"+pageMap.get("planCode")+"%");
		}
		//派工单号
		if(CommonFunction.isNotNull(pageMap.get("plantodoCode"))){
			hql.append(" and  todo.todo_code like:plantodoCode ");
			par.put("plantodoCode", "%"+pageMap.get("plantodoCode")+"%");
		}
		System.out.println(hql.toString());
		result=this.getPageQueryResultSQL(hql.toString(), par, qEntity);
		return result;
	}

	@Override
	public QueryResult queryEmployeeTimeByDepId(Map pageMap, Long Depid, String start, String end,String time)
			throws BusinessException {
		Map par=new HashMap();
		QueryResult result = null;
		BaseQEntity qEntity = (BaseQEntity) pageMap.get("qEntity");
		StringBuffer hql= new StringBuffer(" ");
//		hql.append(" SELECT deptName,empName,empCode,ROUND(AVG(quatime),2) AS quatime FROM (");
		hql.append(" SELECT sd.name AS deptName,se.serial_no AS empCode,se.name AS empName, ");
		hql.append(" DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d') AS finishtime, ");
		hql.append(" ROUND(SUM( UNIX_TIMESTAMP( claim.finish_time ) - UNIX_TIMESTAMP( claim.add_date ) ) / 60, 0) AS workTime,");
		hql.append(" ROUND(SUM(UNIX_TIMESTAMP(claim.finish_time)-UNIX_TIMESTAMP(claim.add_date))*100/("+time+"*60*60),2) AS quatime ");
		hql.append(" FROM  sys_employee_deptart  AS sed JOIN sys_employee AS se ON sed.employee_id=se.id  ");
		hql.append(" JOIN sys_department AS sd ON sed.depart_id=sd.id ");
		hql.append(" JOIN produceplan_todo_claim AS claim ON claim.executor_emp_id=se.id ");
		hql.append(" JOIN produceplan_todo AS todo ON claim.todo_id=todo.id ");
		hql.append(" JOIN  produceplan as plan on plan.id=todo.produceplan_id ");
		if(Depid != null && Depid !=0){
			hql.append(" where sd.id=:Depid");
			par.put("Depid", Depid);
		}
		if(StringUtils.isNotBlank(start)){
			hql.append(" and claim.finish_time>=:begindate ");
			par.put("begindate",start);
		}
		if(StringUtils.isNotBlank(end)){
			hql.append(" and claim.finish_time<=:enddate ");
			par.put("enddate", end);
		}
		if(CommonFunction.isNotNull(pageMap.get("empCode"))){
			hql.append(" and  se.serial_no like:empCode ");
			par.put("empCode", "%"+pageMap.get("empCode")+"%");
		}
		if(CommonFunction.isNotNull(pageMap.get("empName"))){
			hql.append(" and  se.name like:empName ");
			par.put("empName", "%"+pageMap.get("empName")+"%");
		}
		hql.append(" GROUP BY DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d'),deptName, empName,empCode");
		hql.append(" ORDER BY DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d') DESC, worktime DESC");
		System.out.println(hql.toString());
		result=this.getPageQueryResultSQLToMap(hql.toString(), par, qEntity);
		return result;
	}

	@Override
	public List<Map> queryEmployeeTimeByCode(String code, String workTime, String startTime, String endTime) {
		StringBuffer hql=new StringBuffer();
//		hql.append(" SELECT deptName,empName,empCode,ROUND(AVG(quatime),2) AS quatime FROM (");
		hql.append(" SELECT sd.name AS deptName,se.name AS empName,se.serial_no AS empCode, ");
		hql.append(" ROUND(SUM(UNIX_TIMESTAMP(claim.finish_time)-UNIX_TIMESTAMP(claim.add_date))*100/("+workTime+"*60*60),2) AS quatime,DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d') AS finishtime");
		hql.append(" FROM  sys_employee_deptart  AS sed JOIN sys_employee AS se ON sed.employee_id=se.id ");
		hql.append(" JOIN sys_department AS sd ON sed.depart_id=sd.id ");
		hql.append(" JOIN produceplan_todo_claim AS claim ON claim.executor_emp_id=se.id ");
		hql.append(" JOIN produceplan_todo AS todo ON claim.todo_id=todo.id ");
		hql.append(" JOIN  produceplan AS plan ON plan.id=todo.produceplan_id ");
		Map<String,String> map=new HashMap<String,String>();
		hql.append(" WHERE se.serial_no=:code ");
		map.put("code", code);
		if(StringUtils.isNotBlank(startTime)){
			hql.append(" and claim.finish_time>=:begindate ");
			map.put("begindate",startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			hql.append(" and claim.finish_time<=:enddate ");
			map.put("enddate", endTime);
		}
		hql.append(" GROUP BY DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d'),deptName, empName,empCode ");
//		hql.append(" ) AS dayTime GROUP BY deptName,empName,empCode");
		return this.getListBySQLToMap(hql.toString(), map);
	}

	@Override
	public List<Map> queryAllEmployeeByTime(String time, String workTime) {
		StringBuffer hql=new StringBuffer();
//		hql.append(" SELECT deptName,empName,empCode,ROUND(AVG(quatime),2) AS quatime FROM (");
		hql.append(" SELECT sd.name AS deptName,se.name AS empName,se.serial_no AS empCode, ");
		hql.append(" ROUND(SUM(UNIX_TIMESTAMP(claim.finish_time)-UNIX_TIMESTAMP(claim.add_date))*100/("+workTime+"*60*60),2) AS quatime,DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d') AS finishtime");
		hql.append(" FROM  sys_employee_deptart  AS sed JOIN sys_employee AS se ON sed.employee_id=se.id ");
		hql.append(" JOIN sys_department AS sd ON sed.depart_id=sd.id ");
		hql.append(" JOIN produceplan_todo_claim AS claim ON claim.executor_emp_id=se.id ");
		hql.append(" JOIN produceplan_todo AS todo ON claim.todo_id=todo.id ");
		hql.append(" JOIN  produceplan AS plan ON plan.id=todo.produceplan_id ");
		hql.append(" WHERE DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d')=:time ");
		Map<String,String> map=new HashMap<String,String>();
		map.put("time", time);
		hql.append(" GROUP BY DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d'),deptName, empName,empCode ");
//		hql.append(" ) AS dayTime GROUP BY deptName,empName,empCode");
		return this.getListBySQLToMap(hql.toString(), map);
	}
	
	@Override
	public List<Map> queryEmployeeByCodeAndTime(String code, String workTime, String startTime, String endTime) {
		StringBuilder sql=new StringBuilder();
		sql.append(" SELECT * FROM (");
		Date beginDate = DateUtil.format2String(startTime);
		Date endDate = DateUtil.format2String(endTime);
		String pseudoSql = PseudoSqlUtil.getTimeSlotPseudoSql(beginDate,endDate);
		sql.append(pseudoSql);
		sql.append(" ) AS temp LEFT JOIN (");
		sql.append(" SELECT sd.name AS deptName,se.name AS empName,se.serial_no AS empCode, ");
		sql.append(" ROUND(SUM(UNIX_TIMESTAMP(claim.finish_time)-UNIX_TIMESTAMP(claim.add_date))*100/("+workTime+"*60*60),2) AS quatime,");
		sql.append(" DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d') AS finishtime");
		sql.append(" FROM  sys_employee_deptart AS sed, sys_employee AS se,");
		sql.append(" sys_department AS sd, produceplan_todo_claim AS claim,");
		sql.append(" produceplan_todo AS todo, produceplan AS plan");
		sql.append(" WHERE sed.employee_id=se.id AND sed.depart_id=sd.id");
		sql.append(" AND claim.executor_emp_id=se.id AND claim.todo_id=todo.id");
		sql.append(" AND plan.id=todo.produceplan_id");
		Map map=new HashMap();
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and claim.finish_time>=:begindate ");
			map.put("begindate",startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and claim.finish_time<=:enddate ");
			map.put("enddate", endTime);
		}
		sql.append(" AND se.serial_no = :empCode");
		map.put("empCode", code);
		sql.append(" GROUP BY DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d'),deptName, empName, empCode ");
		sql.append(" ) AS result ON finishtime = day");
		sql.append(" ORDER BY day, empCode");
		return this.getListBySQLToMap(sql.toString(), map);
	}
	
	@Override
	public List<Map> queryEmployeeTimeByCodes(String codes, String time,
			String workTime) {
		String[] ids = codes.split(",");
		StringBuffer hql=new StringBuffer();
//		hql.append(" SELECT deptName,empName,empCode,ROUND(AVG(quatime),2) AS quatime FROM (");
		hql.append(" SELECT sd.name AS deptName,se.name AS empName,se.serial_no AS empCode, ");
		hql.append(" ROUND(SUM(UNIX_TIMESTAMP(claim.finish_time)-UNIX_TIMESTAMP(claim.add_date))*100/("+workTime+"*60*60),2) AS quatime,DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d') AS finishtime");
		hql.append(" FROM  sys_employee_deptart  AS sed JOIN sys_employee AS se ON sed.employee_id=se.id ");
		hql.append(" JOIN sys_department AS sd ON sed.depart_id=sd.id ");
		hql.append(" JOIN produceplan_todo_claim AS claim ON claim.executor_emp_id=se.id ");
		hql.append(" JOIN produceplan_todo AS todo ON claim.todo_id=todo.id ");
		hql.append(" JOIN  produceplan AS plan ON plan.id=todo.produceplan_id ");
		hql.append(" WHERE DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d')=:time ");
		Map map=new HashMap();
		map.put("time", time);
		hql.append(" AND se.serial_no in :ids");
		map.put("ids", ids);
		hql.append(" GROUP BY DATE_FORMAT(claim.`finish_time`,'%Y-%m-%d'),deptName, empName,empCode ");
//		hql.append(" ) AS dayTime GROUP BY deptName,empName,empCode");
		return this.getListBySQLToMap(hql.toString(), map);
	}
	
	@Override
	public List<Map> queryOrderDetailByCodes(String code, String time) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT produceplan_todo.todo_code AS detailCode,");
		sql.append(" produceplan_todo_claim.todo_id AS todoId,");
		sql.append(" sys_employee.name AS name,");
		sql.append(" produceplan_todo_claim.add_date AS startTime,");
		sql.append(" produceplan_todo_claim.plannum AS planNum,");
		sql.append(" produceplan_todo_claim.qualified_num AS qualifiedNum,");
		sql.append(" produceplan_todo_claim.disqualified_num AS disqualifiedNum,");
		sql.append(" produceplan_todo_claim.finish_time AS finishTime,");
		sql.append(" produceplan_todo_claim.state AS state");
		sql.append(" FROM produceplan_todo, produceplan_todo_claim, sys_employee");
		sql.append(" WHERE produceplan_todo_claim.todo_id = produceplan_todo.id");
		sql.append(" AND sys_employee.serial_no = '" + code + "'");
		sql.append(" AND sys_employee.id = produceplan_todo_claim.executor_emp_id");
		sql.append(" AND DATE_FORMAT( produceplan_todo_claim.finish_time, '%Y-%m-%d' ) = '" + time + "'");
		return this.getListBySQLToMap(sql.toString(), null); 
	}

	@Override
	public List<Map> queryEmployeeByTime(String start, String end) {
		Map par=new HashMap();
		StringBuffer hql= new StringBuffer(" ");
		hql.append("SELECT sd.name AS deptName,se.name AS empName,se.serial_no AS empCode, ");
		hql.append(" plan.plan_code,plan.product_name, ");
		hql.append(" todo.PROCESS,todo.todo_code,todo.taskname, ");
		hql.append(" claim.plannum,claim.qualified_num,claim.disqualified_num ");
		hql.append(" FROM  sys_employee_deptart  AS sed JOIN sys_employee AS se ON sed.employee_id=se.id  ");
		hql.append(" JOIN sys_department AS sd ON sed.depart_id=sd.id ");
		hql.append(" JOIN produceplan_todo_claim AS claim ON claim.executor_emp_id=se.id ");
		hql.append(" JOIN produceplan_todo AS todo ON claim.todo_id=todo.id ");
		hql.append(" JOIN  produceplan as plan on plan.id=todo.produceplan_id ");
		if(StringUtils.isNotBlank(start)){
			hql.append(" and claim.finish_time>=:begindate ");
			par.put("begindate",start);
		}
		if(StringUtils.isNotBlank(end)){
			hql.append(" and claim.finish_time<=:enddate ");
			par.put("enddate", end);
		}
		System.out.println(hql.toString());
		return this.getListBySQLToMap(hql.toString(), par);
	}
}
