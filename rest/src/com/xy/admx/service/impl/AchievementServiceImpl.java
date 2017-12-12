package com.xy.admx.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.xy.admx.common.CommonFunction;
import com.xy.admx.common.DateUtil;
import com.xy.admx.common.PseudoSqlUtil;
import com.xy.admx.core.service.base.BaseServiceImpl;
import com.xy.admx.service.AchievementService;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Employee;
@Service
public class AchievementServiceImpl extends BaseServiceImpl implements AchievementService {

	@Override
	public Map<String, Object> queryDeptAchievement(Long deptId,String beginDate,String endDate) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("deptId", deptId);
		StringBuilder sql = new StringBuilder();
		sql.append(" select d.name as name,round(sum(claim.qualified_num)*100/sum(claim.plannum),2) as wcl  from produceplan_todo_claim as claim,sys_appuser as u,sys_employee as e,");
		sql.append("sys_employee_deptart ed,sys_department d");
		sql.append(" where claim.executor=u.id and u.emp_id=e.id and ed.employee_id=e.id and d.id=ed.depart_id and ed.depart_id=:deptId");
		Map<String,Object> deptInfo = this.getFirstResultBySqlToMap(sql.toString(),paramMap);
		if(CommonFunction.isNotNull(deptInfo)){
			if(deptInfo.get("wcl") == null){
				deptInfo.put("wcl", 0.0);
			}
		}
		return deptInfo;
	}

	@Override
	public List<Map<String, Object>> queryDeptEmpAchievement(Long deptId,
			String beginDate, String endDate, boolean isAsc) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("deptId", deptId);
		StringBuilder sql = new StringBuilder();
		sql.append("  select e.name as name,COALESCE(round(sum(claim.qualified_num)*100/sum(claim.plannum),2),0) as wcl ");
		sql.append(" from sys_employee e left join produceplan_todo_claim claim on claim.executor_emp_id= e.id  ");
		sql.append(" where exists(select 1 from sys_employee_deptart ed where ed.employee_id=e.id and ed.depart_id=:deptId) ");
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
		List<Map<String,Object>> empInfo=this.getListBySQLToMap(sql.toString(), paramMap);
		return empInfo;
	}
	
	@Override
	public Map<String, Object> queryDeptQltAchievement(Long deptId, String beginDate, String endDate) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		StringBuilder sql=new StringBuilder();
		sql.append("select d.id,d.name,round(sum(claim.qualified_num*100)/sum(claim.qualified_num+claim.disqualified_num),2) as qltTask  ");
		sql.append(" from produceplan_todo_claim as claim,sys_appuser as u,sys_employee as e,sys_employee_deptart ed,sys_department d" );
		sql.append(" WHERE claim.executor=u.id AND u.emp_id=e.id AND ed.employee_id=e.id AND d.id=ed.depart_id and d.id=:deptId");
		paramMap.put("deptId", deptId);
		if(StringUtils.isNotBlank(beginDate)){
			sql.append(" and claim.finish_time >=:beginDate ");
			paramMap.put("beginDate", beginDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sql.append(" and claim.finish_time <=:endDate ");
			paramMap.put("endDate", endDate);
		}
		sql.append(" GROUP BY d.id");
		Map<String,Object> deptInfo = this.getFirstResultBySqlToMap(sql.toString(),paramMap);
		if(CommonFunction.isNotNull(deptInfo)){
			if(deptInfo.get("qltTask") == null){
				deptInfo.put("qltTask", 0.0);
			}
		}
		return deptInfo;
	}

	@Override
	public List<Map<String, Object>> queryDeptQltEmpAchievement(Long deptId, String beginDate, String endDate,
			boolean isAsc) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("deptId", deptId);
		StringBuilder sql = new StringBuilder();
		sql.append("  select e.name as name,COALESCE(round(sum(claim.qualified_num*100)/sum(claim.qualified_num+claim.disqualified_num),2),0) as qltTask ");
		sql.append(" from sys_employee e left join produceplan_todo_claim claim on claim.executor_emp_id= e.id  ");
		sql.append(" where exists(select 1 from sys_employee_deptart ed where ed.employee_id=e.id and ed.depart_id=:deptId) ");
		if(StringUtils.isNotBlank(beginDate)){
			sql.append(" and finish_time >=:beginDate ");
			paramMap.put("beginDate", beginDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sql.append(" and finish_time <=:endDate ");
			paramMap.put("endDate", endDate);
		}
		sql.append(" group by e.id order by qltTask ");
		if(!isAsc){
			sql.append(" desc");
		}
		sql.append(" limit 10");
		List<Map<String,Object>> empInfo=this.getListBySQLToMap(sql.toString(), paramMap);
		return empInfo;
	}
	@Override
	public Map<String, Object> queryAllDeptTaskAchievement(String beginDate, String endDate) {
		StringBuilder sql=new StringBuilder();
		Map<String,Object> paramMap =new HashMap<String, Object>();
		sql.append("select d.id,d.name,round(sum(claim.qualified_num*100)/sum(claim.plannum),2) as qltTask  ");
		sql.append(" from produceplan_todo_claim as claim,sys_appuser as u,sys_employee as e,sys_employee_deptart ed,sys_department d" );
		sql.append(" WHERE claim.executor=u.id AND u.emp_id=e.id AND ed.employee_id=e.id AND d.id=ed.depart_id ");
		if(StringUtils.isNotBlank(beginDate)){
			sql.append(" and claim.finish_time >=:beginDate ");
			paramMap.put("beginDate", beginDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sql.append(" and claim.finish_time <=:endDate ");
			paramMap.put("endDate", endDate);
		}
		sql.append(" GROUP BY d.id");
		Map<String,Object> body = new LinkedHashMap<>();
		List<Map<String,Object>> deptTask=this.getListBySQLToMap(sql.toString(), paramMap);
		body.put("deptTask", deptTask);
		return body;
	}
	
	@Override
	public Map<String, Object> queryEmployeeAchievement(Long employeeId,Date beginDate, Date endDate, boolean isAsc) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("employeeId", employeeId);
		StringBuilder sql = new StringBuilder();
		sql.append(" select e.name as name,round(sum(claim.qualified_num)*100/sum(claim.plannum),2) as wcl  from produceplan_todo_claim as claim,sys_employee as e,sys_appuser user ");
		sql.append(" where claim.executor=user.id and e.Id=user.emp_id and user.id=:employeeId ");/*and (claim.finish_time >=beginDate or claim.finish_time <=endDate)*/
		Map<String,Object> emplInfo = this.getFirstResultBySqlToMap(sql.toString(),paramMap);
		Map<String,Object> body = new LinkedHashMap<>();
		body.put("emplInfo",emplInfo);
		
		
		sql = new StringBuilder();
		sql.append(" select e.name as name,round(claim.qualified_num*100/claim.plannum,2) as wcl  from produceplan_todo_claim as claim,sys_employee as e,sys_appuser user ");
		sql.append(" where claim.executor=user.id and e.Id=user.emp_id and user.id=:employeeId ");/*and (claim.finish_time >=beginDate or claim.finish_time <=endDate)*/
		List<Map<String,Object>> empInfo=this.getListBySQLToMap(sql.toString(), paramMap);
		body.put("empInfo",empInfo);
		return body;
	}

	@Override
	public Map<String, Object> queryAllQualifiedAchievement(String beginDate,String endDate) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		StringBuilder sql=new StringBuilder();
		sql.append("select d.id,d.name,round(sum(claim.qualified_num*100)/sum(claim.qualified_num+claim.disqualified_num),2) as qltTask  ");
		sql.append(" from produceplan_todo_claim as claim,sys_appuser as u,sys_employee as e,sys_employee_deptart ed,sys_department d" );
		sql.append(" WHERE claim.executor=u.id AND u.emp_id=e.id AND ed.employee_id=e.id AND d.id=ed.depart_id ");
		if(StringUtils.isNotBlank(beginDate)){
			sql.append(" and claim.finish_time >=:beginDate ");
			paramMap.put("beginDate", beginDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sql.append(" and claim.finish_time <=:endDate ");
			paramMap.put("endDate", endDate);
		}
		sql.append(" GROUP BY d.id");
		Map<String,Object> body = new LinkedHashMap<>();
		List<Map<String,Object>> qualifiedTask=this.getListBySQLToMap(sql.toString(), paramMap);
		body.put("qualifiedTask", qualifiedTask);
		return body;
	}
	
	@Override
	public Map<String, Object> queryEmployeeQroduction(Long employeeId,Date beginDate, Date endDate, boolean isAsc) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("employeeId", employeeId);
		StringBuilder sql = new StringBuilder();
		sql.append(" select e.name as name,round(sum(claim.qualified_num)*100/sum(claim.plannum),2) as wcl  from produceplan_todo_claim as claim,sys_employee as e,sys_appuser user ");
		sql.append(" where claim.executor=user.id and e.Id=user.emp_id and user.id=:employeeId ");/*and (claim.finish_time >=beginDate or claim.finish_time <=endDate)*/
		Map<String,Object> emplInfo = this.getFirstResultBySqlToMap(sql.toString(),paramMap);
		Map<String,Object> body = new LinkedHashMap<>();
		body.put("emplInfo",emplInfo);
		sql = new StringBuilder();
		sql.append(" select e.name as name,round(claim.qualified_num*100/claim.plannum,2) as wcl  from produceplan_todo_claim as claim,sys_employee as e,sys_appuser user ");
		sql.append(" where claim.executor=user.id and e.Id=user.emp_id and user.id=:employeeId ");/*and (claim.finish_time >=beginDate or claim.finish_time <=endDate)*/
		List<Map<String,Object>> empInfo=this.getListBySQLToMap(sql.toString(), paramMap);
		body.put("empInfo",empInfo);
		return body;
	}

	@Override
	public Map<String, Object> queryAllEfficiencyAchievement(String beginDate,String endDate) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		StringBuilder sql=new StringBuilder();
		sql.append("select d.id,d.name,round(sum(claim.qualified_num)/sum(TIME_FORMAT(TIMEDIFF(claim.finish_time, claim.add_date),'%Hh%im')),2) as qltTask  ");
		sql.append(" from produceplan_todo_claim as claim,sys_appuser as u,sys_employee as e,sys_employee_deptart ed,sys_department d" );
		sql.append(" WHERE claim.executor=u.id AND u.emp_id=e.id AND ed.employee_id=e.id AND d.id=ed.depart_id ");
		if(StringUtils.isNotBlank(beginDate)){
			sql.append(" and claim.finish_time >=:beginDate ");
			paramMap.put("beginDate", beginDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sql.append(" and claim.finish_time <=:endDate ");
			paramMap.put("endDate", endDate);
		}
		sql.append(" GROUP BY d.id,d.name");
		Map<String,Object> body = new LinkedHashMap<>();
		List<Map<String,Object>> efficiencyTask=this.getListBySQLToMap(sql.toString(), paramMap);
		body.put("efficiencyTask", efficiencyTask);
		return body;
	}
	
	
	@Override
	public Map<String, Object> queryEmployeeQualified(Long employeeId,Date beginDate, Date endDate, boolean isAsc) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("employeeId", employeeId);
		StringBuilder sql = new StringBuilder();
		sql.append(" select e.name as name,round(sum(claim.qualified_num)*100/sum(claim.plannum),2) as wcl  from produceplan_todo_claim as claim,sys_employee as e,sys_appuser user ");
		sql.append(" where claim.executor=user.id and e.Id=user.emp_id and user.id=:employeeId ");/*and (claim.finish_time >=beginDate or claim.finish_time <=endDate)*/
		Map<String,Object> emplInfo = this.getFirstResultBySqlToMap(sql.toString(),paramMap);
		Map<String,Object> body = new LinkedHashMap<>();
		body.put("emplInfo",emplInfo);
		sql = new StringBuilder();
		sql.append(" select e.name as name,round(claim.qualified_num*100/claim.plannum,2) as wcl  from produceplan_todo_claim as claim,sys_employee as e,sys_appuser user ");
		sql.append(" where claim.executor=user.id and e.Id=user.emp_id and user.id=:employeeId ");/*and (claim.finish_time >=beginDate or claim.finish_time <=endDate)*/
		List<Map<String,Object>> empInfo=this.getListBySQLToMap(sql.toString(), paramMap);
		body.put("empInfo",empInfo);
		return body;
	}

	@Override
	public Map<String, Object> queryDeptEfcAchievement(Long deptId, String beginDate, String endDate) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("deptId", deptId);
		StringBuilder sql = new StringBuilder();
		sql.append(" select d.name as name,round(sum(claim.qualified_num)/sum(TIME_FORMAT(TIMEDIFF(claim.finish_time, claim.add_date),'%Hh%im')),2) as qltTask  from produceplan_todo_claim as claim,sys_appuser as u,sys_employee as e,");
		sql.append(" sys_employee_deptart ed,sys_department d");
		sql.append(" where claim.executor=u.id and u.emp_id=e.id and ed.employee_id=e.id and d.id=ed.depart_id and ed.depart_id=:deptId");
		Map<String,Object> deptInfo = this.getFirstResultBySqlToMap(sql.toString(),paramMap);
		if(CommonFunction.isNotNull(deptInfo)){
			if(deptInfo.get("wcl") == null){
				deptInfo.put("wcl", 0.0);
			}
		}
		return deptInfo;
	}

	@Override
	public List<Map<String, Object>> queryDeptEfcEmpAchievement(Long deptId, String beginDate, String endDate,
			boolean isAsc) {
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("deptId", deptId);
		StringBuilder sql = new StringBuilder();
		sql.append("  select e.name as name,COALESCE(round(sum(claim.qualified_num)/sum(TIME_FORMAT(TIMEDIFF(claim.finish_time, claim.add_date),'%Hh%im')),2),0)  as qltTask ");
		sql.append(" from sys_employee e left join produceplan_todo_claim claim on claim.executor_emp_id= e.id  ");
		sql.append(" where exists(select 1 from sys_employee_deptart ed where ed.employee_id=e.id and ed.depart_id=:deptId) ");
		if(StringUtils.isNotBlank(beginDate)){
			sql.append(" and finish_time >=:beginDate ");
			paramMap.put("beginDate", beginDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sql.append(" and finish_time <=:endDate ");
			paramMap.put("endDate", endDate);
		}
		sql.append(" group by e.id order by qltTask ");
		if(!isAsc){
			sql.append(" desc");
		}
		sql.append(" limit 10");
		List<Map<String,Object>> empInfo=this.getListBySQLToMap(sql.toString(), paramMap);
		return empInfo;
	}

	@Override
	public List<Map<String,Object>> getNormalDepartment() {
		String sql = "select d.id,d.name from sys_department d where status=1 order by d.id ";
		return this.getListBySQLToMap(sql, null);
	}

	@Override
	public List<Map<String,Object>> getEmp() {
		String sql=" select e.id,e.name from sys_employee e,sys_department de,sys_employee_deptart d  where d.employee_id=e.id and d.depart_id=de.id ";
		return this.getListBySQLToMap(sql, null);
	}

	@Override
	public Map<String, Object> queryEmpTaskCompletionRatePie(Long employeeId,String beginDate, String endDate) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("employeeId", employeeId);
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		String sql = " select ROUND((SUM(claim.qualified_num)/SUM(claim.plannum))*100,2) taskWcl,emp.name FROM produceplan_todo_claim claim,sys_employee emp WHERE claim.executor_emp_id=:employeeId AND claim.finish_time>=CONCAT(:beginDate,' 00:00:00') AND claim.finish_time<=CONCAT(:endDate,' 23:59:59') AND  emp.Id=:employeeId ";
		Map<String,Object> empTaskCompletionRate = this.getFirstResultBySqlToMap(sql.toString(),param);
		if(CommonFunction.isNotNull(empTaskCompletionRate)){
			if(empTaskCompletionRate.get("taskWcl") == null){
				empTaskCompletionRate.put("taskWcl", 0.00);
			}
		}
		return empTaskCompletionRate;
	}

	@Override
	public List<Map<String,Object>> queryEmpTaskCompletionRateHistogram(Long employeeId, String beginDate, String endDate) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("employeeId", employeeId);
		String pseudoSql = PseudoSqlUtil.getTimeSlotPseudoSql(DateUtil.format2String(beginDate),DateUtil.format2String(endDate));
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT pseudo.day,COALESCE(ROUND((SUM(val.qualified_num)/SUM(val.plannum))*100,2),0.00) AS empTaskCompletionRateHistogram from ( ");
		sql.append( pseudoSql );
		sql.append( " ) AS pseudo " );
		sql.append( " LEFT JOIN ( " );
		sql.append( " SELECT claim.qualified_num,claim.plannum,claim.finish_time  FROM produceplan_todo_claim claim WHERE claim.executor_emp_id=:employeeId  " );
		sql.append( " ) val ON val.finish_time>=CONCAT(pseudo.day,' 00:00:00') AND  val.finish_time<=CONCAT(pseudo.day,' 23:59:59')   " );
		sql.append( " GROUP BY DAY ORDER BY DAY " );
		List<Map<String,Object>> empTaskCompletionRateHistogram=this.getListBySQLToMap(sql.toString(), param);
		return empTaskCompletionRateHistogram;
	}

	@Override
	public Map<String, Object> queryEmpQualifiedProductionPie(Long employeeId,String beginDate, String endDate) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("employeeId", employeeId);
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		String sql = " select ROUND((SUM(claim.qualified_num)/SUM(claim.qualified_num+claim.disqualified_num))*100,2) as productionHgl,emp.name FROM produceplan_todo_claim claim,sys_employee emp WHERE claim.executor_emp_id=:employeeId AND claim.finish_time>=CONCAT(:beginDate,' 00:00:00') AND claim.finish_time<=CONCAT(:endDate,' 23:59:59') AND  emp.Id=:employeeId ";
		Map<String,Object> empTaskCompletionRate = this.getFirstResultBySqlToMap(sql.toString(),param);
		if(CommonFunction.isNotNull(empTaskCompletionRate)){
			if(empTaskCompletionRate.get("productionHgl") == null){
				empTaskCompletionRate.put("productionHgl", 0.00);
			}
		}
		return empTaskCompletionRate;
	}

	@Override
	public List<Map<String, Object>> queryEmpQualifiedProductionHistogram(Long employeeId, String beginDate, String endDate) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("employeeId", employeeId);
		String pseudoSql = PseudoSqlUtil.getTimeSlotPseudoSql(DateUtil.format2String(beginDate),DateUtil.format2String(endDate));
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT pseudo.day,COALESCE(ROUND((SUM(val.qualified_num)/SUM(val.qualified_num+val.disqualified_num))*100,2),0.00) AS empQualifiedProductionHistogram from ( ");
		sql.append( pseudoSql );
		sql.append( " ) AS pseudo " );
		sql.append( " LEFT JOIN ( " );
		sql.append( " SELECT claim.qualified_num,claim.plannum,claim.finish_time,claim.disqualified_num  FROM produceplan_todo_claim claim WHERE claim.executor_emp_id=:employeeId  " );
		sql.append( " ) val ON val.finish_time>=CONCAT(pseudo.day,' 00:00:00') AND  val.finish_time<=CONCAT(pseudo.day,' 23:59:59')   " );
		sql.append( " GROUP BY DAY ORDER BY DAY " );
		List<Map<String,Object>> empTaskCompletionRateHistogram=this.getListBySQLToMap(sql.toString(), param);
		return empTaskCompletionRateHistogram;
	}

	@Override
	public Map<String, Object> queryEmpEfcPie(Long employeeId, String beginDate, String endDate) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("employeeId", employeeId);
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		String sql = " select ROUND(SUM(claim.qualified_num)/SUM(TIMESTAMPDIFF( Second, claim.add_date, claim.finish_time)/3600),2) AS productionHgl,emp.name FROM produceplan_todo_claim claim,sys_employee emp WHERE claim.executor_emp_id=:employeeId AND claim.finish_time>=CONCAT(:beginDate,' 00:00:00') AND claim.finish_time<=CONCAT(:endDate,' 23:59:59') AND  emp.Id=:employeeId ";
		Map<String,Object> empEfcPie = this.getFirstResultBySqlToMap(sql.toString(),param);
		if(CommonFunction.isNotNull(empEfcPie)){
			if(empEfcPie.get("productionHgl") == null){
				empEfcPie.put("productionHgl", 0.00);
			}
		}
		return empEfcPie;
	}

	@Override
	public List<Map<String, Object>> queryEmpEfcHis(Long employeeId, String beginDate, String endDate) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("employeeId", employeeId);
		String pseudoSql = PseudoSqlUtil.getTimeSlotPseudoSql(DateUtil.format2String(beginDate),DateUtil.format2String(endDate));
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT pseudo.day,COALESCE(ROUND(SUM(val.qualified_num)/SUM(TIMESTAMPDIFF( Second, val.add_date, val.finish_time)/3600),2),0.00) AS empQualifiedProductionHistogram from ( ");
		sql.append( pseudoSql );
		sql.append( " ) AS pseudo " );
		sql.append( " LEFT JOIN ( " );
		sql.append( " SELECT claim.qualified_num,claim.finish_time,claim.add_date  FROM produceplan_todo_claim claim WHERE claim.executor_emp_id=:employeeId  " );
		sql.append( " ) val ON val.finish_time>=CONCAT(pseudo.day,' 00:00:00') AND  val.finish_time<=CONCAT(pseudo.day,' 23:59:59')   " );
		sql.append( " GROUP BY DAY ORDER BY DAY " );
		System.out.println("sql:"+sql.toString());
		List<Map<String,Object>> empEfceHistogram=this.getListBySQLToMap(sql.toString(), param);
		return empEfceHistogram;
	}
}
