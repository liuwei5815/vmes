package com.xy.cms.service.imp;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.SpringUtil;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.TableColumns;
import com.xy.cms.entity.Tables;
import com.xy.cms.entity.View;
import com.xy.cms.service.ViewService;

public class ViewServiceImpl extends BaseDAO implements ViewService{

	@Override
	public QueryResult queryTables(Map<String, Object> map)throws BusinessException {
		QueryResult result = null;
		Map m = new HashMap();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		View view = (View) map.get("view");
		String beginDate=(String) map.get("beginDate");
		String endDate=(String) map.get("endDate");
		StringBuffer hql=new StringBuffer("from View view where 1=1");
		if(view!=null){
			if(CommonFunction.isNotNull(view.getName())){
				hql.append(" and (view.name like :name)");
				m.put("name", "%"+view.getName()+"%");
			}
			if(CommonFunction.isNotNull(beginDate)){
				hql.append(" and view.createTime >=:beginDate");
				m.put("beginDate", beginDate);
			}
			if(CommonFunction.isNotNull(endDate)){
				hql.append(" and view.createTime <=:endDate ");
				m.put("endDate", endDate+" 23:59:59");
			}
		}
		hql.append(" order by view.createTime desc");
		result = this.getPageQueryResult(hql.toString(), m, qEntity);
		return result;
	}

	@Override
	public List getTablesInfo() {
		String hql="from Tables";
		return this.find(hql);
	}

	@Override
	public void saveView(View view, String ruleStr_def,String ruleStr_dt, String columnStr,
			String orderStr,String flag,String viewName_old,Long viewId)throws BusinessException {
		try {
			if(flag!=null && flag.equals("edit")){
				String del_viewSql="delete from sys_view where id="+viewId;
				String del_vListSql="delete from sys_view_list where view_id="+viewId;
				String del_vOrderSql="delete from sys_view_order where view_id="+viewId;
				String del_vSearchSql="delete from sys_view_search where view_id="+viewId;
				this.executeSQL(del_viewSql);
				this.executeSQL(del_vListSql);
				this.executeSQL(del_vOrderSql);
				this.executeSQL(del_vSearchSql);
				this.executeSQL("drop view "+viewName_old);
			}
			
			this.save(view);
			//保存显示列
			if(CommonFunction.isNotNull(columnStr)){
				String[] columnSz=columnStr.split(",");
				if(columnSz!=null && columnSz.length>0){
					for(int i=0;i<columnSz.length;i++){
						String sql_column="insert into sys_view_list(view_id,columns_id) values("+view.getId()+","+Long.parseLong(columnSz[i])+")";
					    this.executeSQL(sql_column);
					}
				}
			}
			
			//保存排序
			if(CommonFunction.isNotNull(orderStr)){
				String[] orderSz=orderStr.split(",");
				if(orderSz!=null && orderSz.length>0){
					for(int i=0;i<orderSz.length;i++){
						String[] orderStrSz=orderSz[i].split("#");
						if(orderStrSz!=null && orderStrSz.length>0){
							String columnId=orderStrSz[0];
							String type=orderStrSz[1];
						    if(!columnId.equals("0")){
						    	String sql_order="insert into sys_view_order(view_id,columns_id,type) values("+view.getId()+","+Long.parseLong(columnId)+","+Integer.parseInt(type)+")";
						    	this.executeSQL(sql_order);
						    }
						}
					}
				}
			}
			
			//保存查找条件(默认)
			if(CommonFunction.isNotNull(ruleStr_def)){
				//con_value+"#"+column_id+"#"+rule+"#"+rule_val;
				String[] ruleSz=ruleStr_def.split(",");
				if(ruleSz!=null && ruleSz.length>0){
					for(int i=0;i<ruleSz.length;i++){
						String[] suleStrSz=ruleSz[i].split("#");
						if(suleStrSz!=null && suleStrSz.length>0){
							String con_value=suleStrSz[0];
							String columnId=suleStrSz[1];
							String rule=suleStrSz[2];
							String rule_value=suleStrSz[3];
						    if(!columnId.equals("0")){
						    	String sql_rule="insert into sys_view_search(view_id,type,colunms_id,rule,rule_value,state,condition_value) values("+view.getId()+",2,"+Long.parseLong(columnId)+",'"+rule+"','"+rule_value+"',1,"+Integer.parseInt(con_value)+")";
						    	this.executeSQL(sql_rule);
						    }
						}
					}
				}
			}
			
			//保存查找条件(动态)
			if(CommonFunction.isNotNull(ruleStr_dt)){
				//con_value+"#"+column_id+"#"+rule+"#"+rule_val;
				String[] ruleSz=ruleStr_dt.split(",");
				if(ruleSz!=null && ruleSz.length>0){
					for(int i=0;i<ruleSz.length;i++){
						String[] suleStrSz=ruleSz[i].split("#");
						if(suleStrSz!=null && suleStrSz.length>0){
							String con_value=suleStrSz[0];
							String columnId=suleStrSz[1];
							String rule=suleStrSz[2];
							String rule_value=suleStrSz[3];
						    if(!columnId.equals("0")){
						    	String sql_rule="insert into sys_view_search(view_id,type,colunms_id,rule,rule_value,state,condition_value) values("+view.getId()+",1,"+Long.parseLong(columnId)+",'"+rule+"','"+rule_value+"',1,"+Integer.parseInt(con_value)+")";
						    	this.executeSQL(sql_rule);
						    }
						}
					}
				}
			}
			
			//生成视图
			//查询列
			StringBuilder createViewSql=new StringBuilder("create view "+view.getName()+" as ");
			String sql_column="select (select col.name from sys_table_columns col where col.id=v.columns_id),v.id from sys_view_list v where v.view_id="+view.getId();
			List listCol=this.findBySQL(sql_column);
			if(listCol.size()>0){
				String colName="";
				for(int i=0;i<listCol.size();i++){
					Object[] obj=(Object[]) listCol.get(i);
					colName+=","+obj[0].toString();
				}
				colName=colName.substring(1);
				Tables table=(Tables) this.get(Tables.class, view.getTableId());
				createViewSql.append("select "+colName+" from "+table.getName()+"");
			}
			//查询默认条件
			StringBuilder sql_condition=new StringBuilder("select (select col.name from sys_table_columns col where col.id=vs.colunms_id),");
			sql_condition.append(" vs.rule,vs.rule_value ");
			sql_condition.append(" from sys_view_search vs where vs.view_id="+view.getId()+" and vs.state=1 and vs.type=2");
			List listCondition=this.findBySQL(sql_condition.toString());
			if(listCondition.size()>0){
				String operStr=" where ";
				for(int i=0;i<listCondition.size();i++){
					Object[] obj=(Object[]) listCondition.get(i);
					String colName=obj[0].toString();
					String rule=obj[1].toString();
					String rule_value=obj[2].toString();
					if(rule.equals("1")){
						operStr+=colName+"='"+rule_value+"' and ";
					}else if(rule.equals("2")){
						operStr+=colName+">'"+rule_value+"' and ";
					}else if(rule.equals("3")){
						operStr+=colName+">='"+rule_value+"' and ";
					}else if(rule.equals("4")){
						operStr+=colName+"<'"+rule_value+"' and ";
					}else if(rule.equals("5")){
						operStr+=colName+"<='"+rule_value+"' and ";
					}else if(rule.equals("6")){
						operStr+=colName+"!='"+rule_value+"' and ";
					}else if(rule.equals("7")){
						operStr+=colName+" like '%"+rule_value+"%' and ";
					}else if(rule.equals("8")){
						operStr+=colName+" like'"+rule_value+"%' and ";
					}else if(rule.equals("9")){
						operStr+=colName+" not like'%"+rule_value+"%' and ";
					}
				}
				operStr+=" 1=1 ";
				createViewSql.append(operStr);
				
				//高级条件代号暂时只规定1、2、3、4、5
				List conditionList=new ArrayList();
				for(int i=1;i<=5;i++){
					conditionList.add(i);
				}
				
				String searchRole=this.findBySQL("select sys_search_role from view where id="+view.getId()).get(0).toString();
				if(searchRole!=null && !"".equals(searchRole)){
					createViewSql.append(" and (");
					for(int i=0;i<conditionList.size();i++){
						int index = searchRole.indexOf(""+conditionList.get(i)+"");
						if(index>0){
							  String conditionSql="select (select col.name from sys_table_columns col where col.id=colunms_id),rule,rule_value from sys_view_search where view_id="+view.getId()+" and type=2 and condition_value="+conditionList.get(i);
							  List list_search=this.findBySQL(conditionSql);
							  Object[] obj=(Object[]) list_search.get(0);
							  String colName=obj[0].toString();
							  String rule=obj[1].toString();
							  String rule_value=obj[2].toString();
							  String ss="";
							  if(rule.equals("1")){
								  ss=colName+"='"+rule_value+"'";
								}else if(rule.equals("2")){
									ss=colName+">'"+rule_value+"'";
								}else if(rule.equals("3")){
									ss=colName+">='"+rule_value+"'";
								}else if(rule.equals("4")){
									ss=colName+"<'"+rule_value+"'";
								}else if(rule.equals("5")){
									ss=colName+"<='"+rule_value+"'";
								}else if(rule.equals("6")){
									ss=colName+"!='"+rule_value+"'";
								}else if(rule.equals("7")){
									ss=colName+" like '%"+rule_value+"%'";
								}else if(rule.equals("8")){
									ss=colName+" like '"+rule_value+"%'";
								}else if(rule.equals("9")){
									ss=colName+" not like '%"+rule_value+"%'";
								}
							  
							  String where = searchRole.substring(0,index);
							  createViewSql.append(where);
							  createViewSql.append(ss);
							  searchRole= searchRole.substring(index+1);
						   }
					}
					createViewSql.append(searchRole+") ");
				}
				
				String orderSql="select (select col.name from sys_table_columns col where col.id=columns_id),type from sys_view_order where view_id="+view.getId();
				List listOrder=this.findBySQL(orderSql);
				String str_order="";
				if(listOrder.size()>0){
					createViewSql.append(" order by ");
					for(int i=0;i<listOrder.size();i++){
						Object[] obj=(Object[]) listOrder.get(i);
						String colName=obj[0].toString();
						short type=Short.parseShort(obj[1].toString());
						String px="";
						if(type==1){
							px="asc";
						}else if(type==2){
							px="desc";
						}
						str_order+=","+colName+" "+px;
					}
					str_order=str_order.substring(1);
					createViewSql.append(str_order);
					Connection conn=(Connection) SpringUtil.getConnectionFromSpring("dataSource");
					Statement statement=conn.createStatement();
					statement.executeUpdate(createViewSql.toString());
					conn.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

@SuppressWarnings({ "rawtypes", "unchecked" })
@Override
public Map getViewInfoById(Long id) {
	View view=(View) this.get(View.class, id);
	Tables table=(Tables) this.get(Tables.class, view.getTableId());
	//查询已选列信息
	List list_col_selected=this.findBySQL("select vl.columns_id ,(select col.name from sys_table_columns col where col.id=vl.columns_id),(select col.name_cn from sys_table_columns col where col.id=vl.columns_id) from sys_view_list vl where vl.view_id="+id);
	//查询未选列信息
	List list_col_not=this.findBySQL("select col.id,col.name,col.name_cn from sys_table_columns col where col.id not in(select vl.columns_id from sys_view_list vl where vl.view_id="+id+") and col.table_id="+table.getId());
	//查询排序信息
	List list_order=this.findBySQL("select vr.columns_id,(select col.name from sys_table_columns col where col.id=vr.columns_id),(select col.name_cn from sys_table_columns col where col.id=vr.columns_id),vr.type from sys_view_order vr where vr.view_id="+id);
	//查询默认搜索条件
	List list_search_def=this.findBySQL("select vs.colunms_id,(select col.name from sys_table_columns col where col.id=vs.colunms_id),(select col.name_cn from sys_table_columns col where col.id=vs.colunms_id),vs.rule,vs.rule_value,vs.condition_value from sys_view_search vs where vs.state=1 and vs.type=2 and vs.view_id="+id);
	//查询动态搜索条件
	List list_search_dt= this.findBySQL("select vs.colunms_id,(select col.name from sys_table_columns col where col.id=vs.colunms_id),(select col.name_cn from sys_table_columns col where col.id=vs.colunms_id),vs.rule,vs.rule_value,vs.condition_value from sys_view_search vs where vs.state=1 and vs.type=1 and vs.view_id="+id);
	//查询所有列信息
	List list_ColAll=this.findBySQL("select col.id,col.name,col.name_cn from sys_table_columns col where col.table_id="+table.getId());
	Map map=new HashMap();
	map.put("view", view);
	map.put("table", table);
	map.put("list_col_selected", list_col_selected);
	map.put("list_col_not", list_col_not);
	map.put("list_colAll", list_ColAll);
	map.put("list_order", list_order);
	map.put("list_search_def", list_search_def);
	map.put("list_search_dt", list_search_dt);
	return map;
}

@Override
public void delViewById(Long id) {
	View view=(View) this.get(View.class, id);
	this.executeSQL("drop view "+view.getName());
	String del_viewSql="delete from sys_view where id="+id;
	String del_vListSql="delete from sys_view_list where view_id="+id;
	String del_vOrderSql="delete from sys_view_order where view_id="+id;
	String del_vSearchSql="delete from sys_view_search where view_id="+id;
	this.executeSQL(del_viewSql);
	this.executeSQL(del_vListSql);
	this.executeSQL(del_vOrderSql);
	this.executeSQL(del_vSearchSql);
	
}

@Override
public void saveViewNew(View view, String ruleStr_def,String ruleStr_dt, String columnStr, String orderStr,
		String flag,String viewName_old, Long viewId) throws BusinessException{
	try {
		if(flag!=null && flag.equals("edit")){
			String del_viewSql="delete from sys_view where id="+viewId;
			String del_vListSql="delete from sys_view_list where view_id="+viewId;
			String del_vOrderSql="delete from sys_view_order where view_id="+viewId;
			String del_vSearchSql="delete from sys_view_search where view_id="+viewId;
			this.executeSQL(del_viewSql);
			this.executeSQL(del_vListSql);
			this.executeSQL(del_vOrderSql);
			this.executeSQL(del_vSearchSql);
			this.executeSQL("drop view "+viewName_old);
		}
		
		this.save(view);
		//保存显示列
		if(CommonFunction.isNotNull(columnStr)){
			String[] columnSz=columnStr.split(",");
			if(columnSz!=null && columnSz.length>0){
				for(int i=0;i<columnSz.length;i++){
					String tabId=columnSz[i].split("#")[0];
					String other=columnSz[i].split("#")[1];
					String colId=columnSz[i].split("#")[2];
					if(other.equals("t")){
						String sql_column="insert into sys_view_list(view_id,columns_id,table_id) values("+view.getId()+","+colId+","+tabId+")";
					    this.executeSQL(sql_column);
					}else{
						String sql_column="insert into sys_view_list(view_id,target_table_id,target_column_id) values("+view.getId()+","+tabId+","+colId+")";
					    this.executeSQL(sql_column);
					}
				}
			}
		}
		
		//保存排序
		//columnId+"#"+order_type+"#"+tabId+"#"+other
		if(CommonFunction.isNotNull(orderStr)){
			String[] orderSz=orderStr.split(",");
			if(orderSz!=null && orderSz.length>0){
				for(int i=0;i<orderSz.length;i++){
					String[] orderStrSz=orderSz[i].split("#");
					if(orderStrSz!=null && orderStrSz.length>0){
						String columnId=orderStrSz[0];
						String type=orderStrSz[1];
						String tabId=orderStrSz[2];
						String other=orderStrSz[3];
					    if(!columnId.equals("0")){
					    	if(other.equals("t")){
					    		String sql_order="insert into sys_view_order(view_id,columns_id,type,table_id) values("+view.getId()+","+Long.parseLong(columnId)+","+Integer.parseInt(type)+","+Long.parseLong(tabId)+")";
						    	this.executeSQL(sql_order);
					    	}else{
					    		String sql_order="insert into sys_view_order(view_id,type,target_table_id,target_column_id) values("+view.getId()+","+Integer.parseInt(type)+","+Long.parseLong(tabId)+","+Long.parseLong(columnId)+")";
						    	this.executeSQL(sql_order);
					    	}
					    	
					    }
					}
				}
			}
		}
		
		//保存查找条件(默认)
		//con_value+"#"+column_id+"#"+rule+"#"+rule_val+"#"+type+"#"+tabId+"#"+otherName;
		if(CommonFunction.isNotNull(ruleStr_def)){
			String[] ruleSz=ruleStr_def.split(",");
			if(ruleSz!=null && ruleSz.length>0){
				for(int i=0;i<ruleSz.length;i++){
					String[] suleStrSz=ruleSz[i].split("#");
					if(suleStrSz!=null && suleStrSz.length>0){
						String con_value=suleStrSz[0];
						String columnId=suleStrSz[1];
						String rule=suleStrSz[2];
						String rule_value=suleStrSz[3];
						String tabId=suleStrSz[5];
						String otherName=suleStrSz[6];
					    if(!columnId.equals("0")){
					    	/*String sql_rule="insert into view_search(view_id,type,colunms_id,rule,rule_value,state,condition_value) values("+view.getId()+",2,"+Long.parseLong(columnId)+",'"+rule+"','"+rule_value+"',1,"+Integer.parseInt(con_value)+")";
					    	this.executeSQL(sql_rule);*/
					    	if(otherName.equals("t")){
					    		String sql_rule="insert into sys_view_search(view_id,type,colunms_id,rule,rule_value,state,condition_value,table_id) values("+view.getId()+",2,"+Long.parseLong(columnId)+",'"+rule+"','"+rule_value+"',1,"+Integer.parseInt(con_value)+","+Long.parseLong(tabId)+")";
						    	this.executeSQL(sql_rule);
					    	}else{
					    		String sql_rule="insert into sys_view_search(view_id,type,rule,rule_value,state,condition_value,target_table_id,target_column_id) values("+view.getId()+",2,'"+rule+"','"+rule_value+"',1,"+Integer.parseInt(con_value)+","+Long.parseLong(tabId)+","+Long.parseLong(columnId)+")";
						    	this.executeSQL(sql_rule);
					    	}
					    }
					}
				}
			}
		}
		
		//保存查找条件(动态)
		//con_value+"#"+column_id+"#"+rule+"#"+rule_val+"#"+type+"#"+tabId+"#"+otherName;
		if(CommonFunction.isNotNull(ruleStr_dt)){
			String[] ruleSz=ruleStr_dt.split(",");
			if(ruleSz!=null && ruleSz.length>0){
				for(int i=0;i<ruleSz.length;i++){
					String[] suleStrSz=ruleSz[i].split("#");
					if(suleStrSz!=null && suleStrSz.length>0){
						String con_value=suleStrSz[0];
						String columnId=suleStrSz[1];
						String rule=suleStrSz[2];
						String rule_value=suleStrSz[3];
						String tabId=suleStrSz[5];
						String otherName=suleStrSz[6];
					    if(!columnId.equals("0")){
					    	if(otherName.equals("t")){
					    		String sql_rule="insert into sys_view_search(view_id,type,colunms_id,rule,rule_value,state,condition_value,table_id) values("+view.getId()+",1,"+Long.parseLong(columnId)+",'"+rule+"','"+rule_value+"',1,"+Integer.parseInt(con_value)+","+Long.parseLong(tabId)+")";
						    	this.executeSQL(sql_rule);
					    	}else{
					    		String sql_rule="insert into sys_view_search(view_id,type,rule,rule_value,state,condition_value,target_table_id,target_column_id) values("+view.getId()+",1,'"+rule+"','"+rule_value+"',1,"+Integer.parseInt(con_value)+","+Long.parseLong(tabId)+","+Long.parseLong(columnId)+")";
						    	this.executeSQL(sql_rule);
					    	}
					    }
					}
				}
			}
		}
		
		//生成视图
		//查询列
		
		StringBuilder createViewSql=new StringBuilder("create view "+view.getName()+" as ");
		
		StringBuilder sql_column_pk=new StringBuilder("");//查询所有主键表字段
		sql_column_pk.append("select vl.columns_id,(select col.name from sys_table_columns col where col.id=vl.columns_id),");
		sql_column_pk.append("vl.table_id,(select tab.name from sys_tables tab where tab.id=vl.table_id) ");
		sql_column_pk.append("from sys_view_list vl where vl.columns_id!='' and vl.view_id="+view.getId());
		List listColPk=this.findBySQL(sql_column_pk.toString());
		
		StringBuilder sql_column_fk=new StringBuilder("");//查询所有外键表字段
		sql_column_fk.append("select vl.target_column_id,(select col.name from sys_table_columns col where col.id=vl.target_column_id),");
		sql_column_fk.append("vl.target_table_id,(select tab.name from sys_tables tab where tab.id=vl.target_table_id )");
		sql_column_fk.append(" from sys_view_list vl where vl.target_column_id!='' and vl.view_id="+view.getId());
		List listColfk=this.findBySQL(sql_column_fk.toString());
		
		String colName_info="";
		if(listColPk.size()>0){
			for(int i=0;i<listColPk.size();i++){
				Object[] obj=(Object[]) listColPk.get(i);
				colName_info+=","+obj[3].toString()+"."+obj[1].toString()+" as "+obj[3].toString()+"__"+obj[1].toString();
			}
			colName_info=colName_info.substring(1);
		}
		
		if(listColfk.size()>0){
			for(int i=0;i<listColfk.size();i++){
				Object[] obj=(Object[]) listColfk.get(i);
				colName_info+=","+obj[3].toString()+"."+obj[1].toString()+" as "+obj[3].toString()+"__"+obj[1].toString();
			}
			if(listColPk.size()==0){
				colName_info=colName_info.substring(1);
			}
		}
		
		//查询动态条件的字段
		String sql_dt_1="select vs.colunms_id,vs.table_id from sys_view_search vs where vs.view_id="+view.getId()+" and vs.state=1 and vs.type=1 and vs.colunms_id!='' and vs.colunms_id not in(select vl.columns_id from sys_view_list vl where vl.view_id="+view.getId()+" and vl.columns_id!='')";
		String sql_dt_2="select vs.target_column_id,vs.target_table_id from sys_view_search vs where vs.view_id="+view.getId()+" and vs.state=1 and vs.type=1 and vs.target_column_id!='' and vs.colunms_id not in(select vl.target_column_id from sys_view_list vl where vl.view_id="+view.getId()+" and vl.target_column_id!='')";
		
		List listDt1=this.findBySQL(sql_dt_1);
		List listDt2=this.findBySQL(sql_dt_2);
		
		if(listDt1.size()>0){
			for(int i=0;i<listDt1.size();i++){
				Object[] obj=(Object[]) listDt1.get(i);
				Tables table=(Tables) this.get(Tables.class, Long.parseLong(obj[1].toString()));
				TableColumns col=(TableColumns) this.get(Tables.class, Long.parseLong(obj[0].toString()));
				colName_info+=","+table.getName()+"."+col.getName()+" as "+table.getName()+"__"+col.getName();
			}
		}
		
		if(listDt2.size()>0){
			for(int i=0;i<listDt2.size();i++){
				Object[] obj=(Object[]) listDt2.get(i);
				Tables table=(Tables) this.get(Tables.class, Long.parseLong(obj[1].toString()));
				TableColumns col=(TableColumns) this.get(Tables.class, Long.parseLong(obj[0].toString()));
				colName_info+=","+table.getName()+"."+col.getName()+" as "+table.getName()+"__"+col.getName();
			}
		}
		
		createViewSql.append("select "+colName_info+" from ");
		String tableStr="select table_id,target_table_id from sys_view where id="+view.getId();
		List listTab=this.findBySQL(tableStr);
		String tabStr="";
		for(int i=0;i<listTab.size();i++){
			Object[] obj=(Object[]) listTab.get(i);
			Object tabId=obj[0];
			Object target_tabId=obj[1];
			Tables tabPk=(Tables) this.get(Tables.class, Long.parseLong(tabId.toString()));
			String s="";
			if(target_tabId!=null && !"".equals(target_tabId.toString())){
				String[] col_fks=target_tabId.toString().split(",");
				if(col_fks.length>0){
					for(int j=0;j<col_fks.length;j++){
						Tables tabFk=(Tables) this.get(Tables.class, Long.parseLong(col_fks[j]));
						s+=","+tabFk.getName()+" "+tabFk.getName();
					}
					
				}
			}
			tabStr+=","+tabPk.getName()+" "+tabPk.getName()+s;
		}
		tabStr=tabStr.substring(1);
		createViewSql.append(tabStr+" where 1=1 ");
		//查询关联的字段
		String sql_reCol="select table_id from sys_view where id="+view.getId();
		Long tableId=Long.parseLong(this.findBySQL(sql_reCol).get(0).toString());
		String shipSql="select target_table_id,target_column_id,source_table_id,source_column_id from sys_table_relationship where target_table_id="+tableId;
		if(this.findBySQL(shipSql).size()>0){
			Object[] obj=(Object[]) this.findBySQL(shipSql).get(0);
			Long target_table_id=Long.parseLong(obj[0].toString());//主键表Id
			Long target_column_id=Long.parseLong(obj[1].toString());//主键字段Id
			Long source_table_id=Long.parseLong(obj[2].toString());//外键表Id
			Long source_column_id=Long.parseLong(obj[3].toString());//外键字段Id
			
			Tables target_table=(Tables) this.get(Tables.class, target_table_id);
			TableColumns target_column=(TableColumns) this.get(TableColumns.class, target_column_id);
			Tables source_table=(Tables) this.get(Tables.class, source_table_id);
			TableColumns source_column=(TableColumns) this.get(TableColumns.class, source_column_id);
			createViewSql.append(" and "+target_table.getName()+"."+target_column.getName()+"="+source_table.getName()+"."+source_column.getName());
		}
		
		
		//查询默认条件
		StringBuilder sql_condition=new StringBuilder("select (select col.name from sys_table_columns col where col.id=vs.colunms_id),");
		sql_condition.append(" vs.rule,vs.rule_value,vs.table_id,(select col.name from sys_table_columns col where col.id=vs.target_column_id),vs.target_table_id");
		sql_condition.append(" from sys_view_search vs where vs.view_id="+view.getId()+" and vs.state=1 and vs.type=2 ");
		List listCondition=this.findBySQL(sql_condition.toString());
		if(listCondition.size()>0){
				String operStr=" and ";
				for(int i=0;i<listCondition.size();i++){
					Object[] obj=(Object[]) listCondition.get(i);
					String colName="";
					if(obj[0]!=null && !"".equals(obj[0].toString())){
						colName=obj[0].toString();
					}
					if(obj[4]!=null && !"".equals(obj[4].toString())){
						colName=obj[4].toString();
					}
					
					String rule=obj[1].toString();
					String rule_value=obj[2].toString();
					Tables table=null;
					if(obj[3]!=null && !"".equals(obj[3].toString())){
						table=(Tables) this.get(Tables.class, Long.parseLong(obj[3].toString()));
					}
					if(obj[5]!=null && !"".equals(obj[5].toString())){
						table=(Tables) this.get(Tables.class, Long.parseLong(obj[5].toString()));
					}
					
					String tableName=table.getName();
					if(rule.equals("1")){
						operStr+=tableName+"."+colName+"='"+rule_value+"' and ";
					}else if(rule.equals("2")){
						operStr+=tableName+"."+colName+">'"+rule_value+"' and ";
					}else if(rule.equals("3")){
						operStr+=tableName+"."+colName+">='"+rule_value+"' and ";
					}else if(rule.equals("4")){
						operStr+=tableName+"."+colName+"<'"+rule_value+"' and ";
					}else if(rule.equals("5")){
						operStr+=tableName+"."+colName+"<='"+rule_value+"' and ";
					}else if(rule.equals("6")){
						operStr+=tableName+"."+colName+"!='"+rule_value+"' and ";
					}else if(rule.equals("7")){
						operStr+=tableName+"."+colName+" like '%"+rule_value+"%' and ";
					}else if(rule.equals("8")){
						operStr+=tableName+"."+colName+" like'"+rule_value+"%' and ";
					}else if(rule.equals("9")){
						operStr+=tableName+"."+colName+" not like'%"+rule_value+"%' and ";
					}
				}
				operStr+=" 1=1 ";
				createViewSql.append(operStr);
			}
			
			//高级条件代号暂时只规定1、2、3、4、5
			List conditionList=new ArrayList();
			for(int i=1;i<=5;i++){
				conditionList.add(i);
			}
			
			String searchRole=this.findBySQL("select search_role from sys_view where id="+view.getId()).get(0).toString();
			if(searchRole!=null && !"".equals(searchRole)){
				createViewSql.append(" and (");
				for(int i=0;i<conditionList.size();i++){
					int index = searchRole.indexOf(""+conditionList.get(i)+"");
					if(index>0){
						  String conditionSql="select (select col.name from sys_table_columns col where col.id=colunms_id),rule,rule_value,table_id,";
						  conditionSql+="(select col.name from sys_table_columns col where col.id=target_column_id),target_table_id";
						  conditionSql+=" from sys_view_search where view_id="+view.getId()+" and type=2 and condition_value="+conditionList.get(i);
						  List list_search=this.findBySQL(conditionSql);
						  Object[] obj=(Object[]) list_search.get(0);
						  String colName="";
						  if(obj[0]!=null && !"".equals(obj[0].toString())){
							  colName=obj[0].toString();
						  }
						  if(obj[4]!=null && !"".equals(obj[4].toString())){
							  colName=obj[4].toString();
						  }
						  
						  Tables table=null;
						  String rule=obj[1].toString();
						  String rule_value=obj[2].toString();
						  if(obj[3]!=null && !"".equals(obj[3].toString())){
							  table=(Tables) this.get(Tables.class, Long.parseLong(obj[3].toString()));
						  }
						  if(obj[5]!=null && !"".equals(obj[5].toString())){
							  table=(Tables) this.get(Tables.class, Long.parseLong(obj[5].toString()));
						  }
						  
						  String tableName=table.getName();
						  String ss="";
						  if(rule.equals("1")){
							    ss=tableName+"."+colName+"='"+rule_value+"'";
							}else if(rule.equals("2")){
								ss=tableName+"."+colName+">'"+rule_value+"'";
							}else if(rule.equals("3")){
								ss=tableName+"."+tableName+"."+colName+">='"+rule_value+"'";
							}else if(rule.equals("4")){
								ss=tableName+"."+colName+"<'"+rule_value+"'";
							}else if(rule.equals("5")){
								ss=tableName+"."+colName+"<='"+rule_value+"'";
							}else if(rule.equals("6")){
								ss=tableName+"."+colName+"!='"+rule_value+"'";
							}else if(rule.equals("7")){
								ss=tableName+"."+colName+" like '%"+rule_value+"%'";
							}else if(rule.equals("8")){
								ss=tableName+"."+colName+" like '"+rule_value+"%'";
							}else if(rule.equals("9")){
								ss=tableName+"."+colName+" not like '%"+rule_value+"%'";
							}
						  
						  String where = searchRole.substring(0,index);
						  createViewSql.append(where);
						  createViewSql.append(ss);
						  searchRole= searchRole.substring(index+1);
					   }
				}
				createViewSql.append(searchRole+") ");
			}
			
			//排序
			//主表字段排序
			String orderSql="select (select col.name from sys_table_columns col where col.id=columns_id),type,(select col.name from sys_table_columns col where col.id=target_column_id),target_table_id,table_id from sys_view_order where view_id="+view.getId()+"";
			List listOrder=this.findBySQL(orderSql);
			String str_order="";
			if(listOrder.size()>0){
				createViewSql.append(" order by ");
				for(int i=0;i<listOrder.size();i++){
					Object[] obj=(Object[]) listOrder.get(i);
					String colName="";
					if(obj[0]!=null && !"".equals(obj[0].toString())){
						colName=obj[0].toString();
					}
					if(obj[2]!=null && !"".equals(obj[2].toString())){
						colName=obj[2].toString();
					}
					Tables table=null;
					if(obj[3]!=null && !"".equals(obj[3].toString())){
						table=(Tables) this.get(Tables.class, Long.parseLong(obj[3].toString()));
					}
					if(obj[4]!=null && !"".equals(obj[4].toString())){
						table=(Tables) this.get(Tables.class, Long.parseLong(obj[4].toString()));
					}
					String tableName=table.getName();
					short type=Short.parseShort(obj[1].toString());
					String px="";
					if(type==1){
						px="asc";
					}else if(type==2){
						px="desc";
					}
					str_order+=","+tableName+"."+colName+" "+px;
				}
			}
			str_order=CommonFunction.isNotNull(str_order)?str_order.substring(1):"";
			createViewSql.append(str_order);
			Connection conn=(Connection) SpringUtil.getConnectionFromSpring("dataSource");
			Statement statement=conn.createStatement();
			statement.executeUpdate(createViewSql.toString());
			conn.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}
public static void main(String[] args) {
	String[] a = { "0", "1", "2", "3", "4", "5", "6" };
	String[] b = { "1", "3", "5" };
	List<String> la = new ArrayList(Arrays.asList(a));
	List<String> lb = new ArrayList(Arrays.asList(b));
	la.removeAll(lb);
	for(int i=0;i<la.size();i++){
		System.out.println(la.get(i));
	}
}
}
