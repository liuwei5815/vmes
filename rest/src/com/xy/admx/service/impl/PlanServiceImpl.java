package com.xy.admx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.xy.admx.common.base.PageQEntity;
import com.xy.admx.common.exception.BusinessException;
import com.xy.admx.core.service.base.BaseServiceImpl;
import com.xy.admx.service.PlanService;
import com.xy.cms.entity.Orders;
import com.xy.cms.entity.ProduceplanTodoClaim;


@Service
public class PlanServiceImpl extends BaseServiceImpl  implements PlanService{

	@Override
	public void queryPlanPage(PageQEntity pageEntity, Map param) throws Exception {
		StringBuffer sql = new StringBuffer("select plan.id,plan.plan_code,plan.start_date,plan.end_date,plan.num,DATE_FORMAT(plan.finish_time,'%Y-%m-%d') as finish_time, ");
		sql.append(" plan.qualified_num,p.name as prodname, p.typespec, p.product_code, ");
		sql.append(" (select sum(actual_num) from produceplan_todo where produceplan_id=plan.id ) as currnum");
		sql.append(" from produceplan plan left join product p on plan.product_id=p.id where 1=1 ");
		Map queryMap = new HashMap();
		if(param != null){
			if(param.get("state") != null){
				Integer state = (Integer)param.get("state");
				sql.append(" and plan.state=:state ");
				queryMap.put("state", state);
			}
			if(param.get("odid") != null){
				Integer odid = Integer.valueOf(param.get("odid").toString());
				sql.append(" and plan.order_detail_id=:odid ");
				queryMap.put("odid", odid);
			}
			if(param.get("finishedEnd") != null && StringUtils.isNotBlank(param.get("finishedEnd").toString())){
				String finishedEnd = param.get("finishedEnd").toString();
				sql.append(" and plan.end_date <=:finishedEnd ");
				queryMap.put("finishedEnd", finishedEnd);
			}
			if(param.get("finishedStart") != null && StringUtils.isNotBlank(param.get("finishedStart").toString())){
				String finishedStart = param.get("finishedStart").toString();
				sql.append(" and plan.end_date >=:finishedStart ");
				queryMap.put("finishedStart", finishedStart);
			}
			if(param.get("actulFinishEnd") != null && StringUtils.isNotBlank(param.get("actulFinishEnd").toString())){
				String actulFinishEnd = param.get("actulFinishEnd").toString();
				sql.append(" and plan.finish_date <=:actulFinishEnd ");
				queryMap.put("actulFinishEnd", actulFinishEnd);
			}
			if(param.get("actulFinishStart") != null && StringUtils.isNotBlank(param.get("actulFinishStart").toString())){
				String actulFinishStart = param.get("actulFinishStart").toString();
				sql.append(" and plan.finish_date >=:actulFinishStart ");
				queryMap.put("actulFinishStart", actulFinishStart);
			}
			if(param.get("canceledEnd") != null && StringUtils.isNotBlank(param.get("canceledEnd").toString())){
				String canceledEnd = param.get("canceledEnd").toString();
				sql.append(" and plan.cancel_time <=:canceledEnd ");
				queryMap.put("canceledEnd", canceledEnd);
			}
			if(param.get("canceledStart") != null && StringUtils.isNotBlank(param.get("canceledStart").toString())){
				String canceledStart = param.get("canceledStart").toString();
				sql.append(" and plan.cancel_time >=:canceledStart ");
				queryMap.put("canceledStart", canceledStart);
			}
			if(param.get("prodname") != null && StringUtils.isNotBlank(param.get("prodname").toString())){
				String prodname = param.get("prodname").toString();
				sql.append("and p.name like :pname)");
				queryMap.put("pname", "%" + prodname + "%");
			}
			if(param.get("customer") != null && StringUtils.isNotBlank(param.get("customer").toString())){
				String customer = param.get("customer").toString();
				sql.append("and exists (select 1 ");
				sql.append(" from orders_detail d ,orders o , customer c ");
				sql.append(" where plan.order_detail_id=d.id and o.id=d.order_id and o.customer_id=c.id and c.name like :cname) ");
				queryMap.put("cname", "%" + customer + "%");
			}
		}
		sql.append("order by plan.end_date asc ");
		this.getPageQueryResultSQLToMap(sql.toString(), queryMap, pageEntity);
		
	}

	@Override
	public void queryTodoPage(PageQEntity pageEntity, Map param) throws Exception {
		if(param.get("planid") == null){
			throw new BusinessException("planid缺失");
		}
		if(!StringUtils.isNumeric(param.get("planid").toString())){
			throw new BusinessException("planid格式不正确," + param.get("planid"));
		}
		StringBuffer sql = new StringBuffer("select todo.todo_code,todo.id,todo.process,todo.plan_num,todo.taskname");
		sql.append(" from produceplan_todo todo where todo.produceplan_id=:planid ");
		Map queryMap = new HashMap();
		queryMap.put("planid", Long.valueOf(param.get("planid").toString()));
		sql.append("order by todo.id asc ");
		this.getPageQueryResultSQLToMap(sql.toString(), queryMap, pageEntity);
	}

	@Override
	public void queryClaimPage(PageQEntity pageEntity, Map param) throws Exception {
		if(param.get("todoid") == null){
			throw new BusinessException("todoid缺失");
		}
		if(!StringUtils.isNumeric(param.get("todoid").toString())){
			throw new BusinessException("todoid格式不正确," + param.get("todoid"));
		}
		StringBuffer sql = new StringBuffer("select c.id,c.executor,c.finish_date,c.state,c.disqualified_num,c.qualified_num,c.plannum,e.name as  executorname ");
		sql.append(" from produceplan_todo_claim c,sys_appuser u, sys_employee  e ");
		sql.append("  where c.executor=u.id and u.emp_id=e.id and c.todo_id=:todoid ");
		Map queryMap = new HashMap();
		queryMap.put("todoid", Long.valueOf(param.get("todoid").toString()));
		sql.append("order by c.id asc ");
		this.getPageQueryResultSQLToMap(sql.toString(), queryMap, pageEntity);
	}

	@Override
	public List queryStatesOfOrderDetails(Long orderid) {
		StringBuffer sql = new StringBuffer( "select distinct state from orders_detail a ");
		sql.append("where a.order_id=:orderid");
		Map values = new HashMap();
		values.put("orderid", orderid);
		return this.getListBySQL(sql.toString(),values);
	}

	@Override
	public boolean hasNotProduce(Long orderid) {
		StringBuffer sql = new StringBuffer( "select * from orders_detail a ");
		sql.append(" where not exists (select 1 from produceplan b where a.id=order_detail_id) ");
		sql.append(" and a.state != -1 and order_id=:orderid ");
		Map values = new HashMap();
		values.put("orderid", orderid);
		int cnt = this.getQueryCountSQL(sql.toString(), values);
		if(cnt > 0){
			return true;
		}
		return false;
	}

	@Override
	public Orders getOrderByPlanid(Long planid) {
		String hql = "from Orders a where a.id=(select orderId from OrdersDetail b where id=(select orderDetailId from Produceplan c where c.id=:planid)) ";
		Map values = new HashMap();
		values.put("planid", planid);
		return (Orders)this.getFirstResult(hql, values);
	}
	
	public void updatePlanState(int state,Long orderid){
		String hql = "update Orders set state=:state where id=:orderid";
		Map values = new HashMap();
		values.put("state", state);
		values.put("orderid", orderid);
		this.execute(hql, values);
	}

	@Override
	public List<ProduceplanTodoClaim> queryTodoClaimByTodoId(Long todoId) {
		String hql = "select claim as claim,e.name as name from ProduceplanTodoClaim claim,AppUser u,Employee e where e.id= u.empId and u.id =claim.executor  and  claim.todoId=:todoId";
		Map<String,Object> param = new HashMap();
		param.put("todoId", todoId);
		return this.getListToMap(hql, param);
	}

	@Override
	public List<Map> queryEquipmentDataById(Long eqId) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("  ");
	    sql.append(" SELECT sj.*,eq.equipment_code AS eqcode,eq.image AS image,eq.name AS NAME,eq.model AS model FROM ");
	    sql.append(" ( SELECT d.equpment_id  AS equmentId,SUM( d.`paramter1` ) AS number, ROUND(SUM(d.paramter4)/3600,2) AS runtime ,MAX(d.receive_time) AS receivetime,DATE_FORMAT(MAX(d.receive_time),'%Y-%m-%d') AS lastTime, ROUND(SUM(d.paramter3)/30,2) AS electrifytime,SUM( d.paramter5 ) AS waringtime ");
	    sql.append(" FROM equipment_parameter_data d WHERE d.equpment_id =:eqId GROUP BY d.equpment_id ) AS sj ");
	    sql.append(" INNER JOIN equipment AS eq ON eq.id = sj.equmentId");
	    params.put("eqId", eqId);
		return this.getListBySQLToMap(sql.toString(), params);
	}
	
}
