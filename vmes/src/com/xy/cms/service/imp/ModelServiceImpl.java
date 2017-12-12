package com.xy.cms.service.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;

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
import com.xy.cms.service.SequenceService;
import com.xy.cms.service.TableColumnsService;

public class ModelServiceImpl extends BaseDAO implements ModelService {

	private TableColumnsService tableColumnsService;

	private SequenceService sequenceService;

	public List<TableColumnType> getTableColumnTypes() {
		//字段类型
		String hql="from TableColumnType";
		List<TableColumnType> types=this.getList(hql, null);
		
		return types;
	}

	public QueryResult query(Map pageMap,List<TableColumns> columnsList) throws BusinessException {
		QueryResult result = null;

		Map m = new HashMap();
		BaseQEntity qEntity = (BaseQEntity) pageMap.get("qEntity");
		Long tableId= (Long) pageMap.get("tableId");
		Tables table=getTablesById(tableId);

		StringBuffer sb=new StringBuffer(); 
		//有外键，则有关联表
		StringBuffer fromTable=new StringBuffer();
		//有外键，则有关联表关系
		StringBuffer whereTable=new StringBuffer();
		sb.append("select ");

		TableRelationship tship=null;
		TableColumns fkColumns=null;
		int sure=0;
		Tables fk_table=null;
		TableColumns fk_col=null;
		if(columnsList != null && columnsList.size() > 0){
			for (int i = 0; i < columnsList.size(); i++) {
				TableColumns col=columnsList.get(i);
				//先判断字段有无外键
				tship=queryTableRelationship(tableId,col.getId());
				if(tship != null){
					sure++;
					//关联表
					fk_table=getTablesById(tship.getTargetTableId());
					//关联栏目
					fk_col=getTableColumnsById(tship.getTargetColumnId());
					fromTable.append(","+fk_table.getName().trim()+" fktable_"+sure);
					whereTable.append(" and "+table.getName().trim()+"."+col.getName()+"=fktable_"+sure+"."+fk_col.getName());
					//外键显示栏目
					fkColumns=getTableColumnsById(tship.getTargetShowColumnId());
					if(i == 0){
						sb.append("fktable_"+sure+"."+fkColumns.getName().trim());
					}else{
						sb.append(",fktable_"+sure+"."+fkColumns.getName().trim());
					}
				}else{
					if(i == 0){
						sb.append(table.getName().trim()+"."+col.getName().trim());
					}else{
						sb.append(","+table.getName().trim()+"."+col.getName().trim());
					}
				}

			}
		}else{
			sb.append("*");
		}
		sb.append(" from "+table.getName().trim()+" "+table.getName().trim());
		if(fromTable != null){
			sb.append(fromTable);
			sb.append(" where 1=1 ");
			sb.append(whereTable);
		}

		if(columnsList != null && columnsList.size() > 0){
			for (int i = 0; i < columnsList.size(); i++) {
				TableColumns col=columnsList.get(i);
				if(col.isPk()){
					sb.append(" order by "+table.getName().trim()+"."+col.getName()+" desc");
				}
			}
		}

		result = this.getPageQueryResultSQL(sb.toString(), null, qEntity);

		return result;
	}

	public TableRelationship queryTableRelationship(Long tableId,Long columnId){
		Map par=new HashMap();
		par.put("tableId", tableId);
		par.put("columnId", columnId);
		String hql="from TableRelationship where sourceTableId=:tableId and sourceColumnId=:columnId";
		List list=this.getList(hql, par);
		TableRelationship ship=null;
		if(list != null && list.size() > 0){
			ship=(TableRelationship) list.get(0);
		}
		return ship;
	}

	public List<TableRelationship> queryMainTableRelationship(Long tableId,Long columnId){
		Map par=new HashMap();
		par.put("tableId", tableId);
		par.put("columnId", columnId);
		String hql="from TableRelationship where targetTableId=:tableId and targetColumnId=:columnId";
		List list=this.getList(hql, par);
		return list;
	}

	public QueryResult queryView(Map pageMap,List viewBeanList)
			throws BusinessException {

		QueryResult result = null;
		try {
			Long viewId=(Long) pageMap.get("viewId");
			Map m = new HashMap();
			m.put("viewId", viewId);
			BaseQEntity qEntity = (BaseQEntity) pageMap.get("qEntity");
			String colhql ="from TableColumns where id in (select columnId from ViewList where viewId=:viewId) or id in (select targetColId from ViewList where viewId=:viewId and targetColId!='')";
			List columnsList=this.getList(colhql, m);
			String orderHql="from ViewOrder where viewId=:viewId";
			List<ViewOrder> orderlist=this.getList(orderHql, m);

			//视图相关的表
			List<Tables> ts = new ArrayList<Tables>();
			Tables t = null;
			TableColumns tbc = null;
			for(ViewOrder vo : orderlist){
				if(vo.getTableId()!=null && vo.getTableId()>0){//主表id不为空
					t = this.getTablesById(vo.getTableId());
					ts.add(t);
					List<TableColumns> tlist = this.find("from TableColumns where primaryKey=1 and tableId="+t.getId());
					tbc = tlist.get(0);
				}
				if(vo.getTargetTabId() !=null && vo.getTargetTabId() >0){//子表id不为空
					ts.add(this.getTablesById(vo.getTargetTabId()));
				}
			}

			String searchHql="from ViewSearch where type=1 and state=1 and viewId=:viewId";
			List searchlist=this.getList(searchHql, m);
			View view=(View) this.get(View.class, viewId);
			StringBuilder sb=new StringBuilder();
			if(CommonFunction.isNotNull(t))
				sb.append(" select distinct " + t.getName() + "."+tbc.getName()+",");
			StringBuilder sb1= new StringBuilder();

			if(columnsList != null && columnsList.size() > 0){
				for (int i = 0; i < columnsList.size(); i++) {
					TableColumns col=(TableColumns) columnsList.get(i);
					Long tableId=col.getTableId();
					Tables table=(Tables) this.get(Tables.class, tableId);


					if(i == 0){
						sb.append(view.getName()+"."+table.getName()+"__"+col.getName().trim());
						if(table.getName().equals(t.getName()))
							sb1.append(" and "+view.getName()+"."+table.getName()+"__"+col.getName().trim()+"="+table.getName()+"."+col.getName());
					}else{
						sb.append(","+view.getName()+"."+table.getName()+"__"+col.getName().trim());
						if(table.getName().equals(t.getName()))
							sb1.append(" and "+view.getName()+"."+table.getName()+"__"+col.getName().trim()+"="+table.getName()+"."+col.getName());
					}
				}
			}else{
				sb.append("*");
			}

			sb.append(" from "+view.getName()+" as "+view.getName());
			for(Tables tb : ts){
				sb.append(","+tb.getName());
				if(ts.size() > 1){
					String hql = "from TableRelationship ship where ship.targetTableId=:tableId and ship.fs="+TableRelationship.ADD_FK;
					Map param = new HashMap();
					param.put("tableId", tb.getId());
					List<TableRelationship> shipList= this.getList(hql, param);
					if(shipList !=null && shipList.size()>0){
						Tables source_tb = this.getTablesById(shipList.get(0).getSourceTableId());
						TableColumns source_tc = this.getTableColumnsById(shipList.get(0).getSourceColumnId());
						TableColumns target_tc = this.getTableColumnsById(shipList.get(0).getTargetColumnId());

						sb1.append(" and " + tb.getName()+"."+target_tc.getName()+"="+source_tb.getName()+"."+source_tc.getName());
					}
				}
			}
			sb.append(" where 1=1 ").append(sb1);


			if(viewBeanList != null && viewBeanList.size() > 0){
				//有动态条件改变
				ViewSearchBean bean=null;
				for (int i = 0; i < viewBeanList.size(); i++) {
					bean=(ViewSearchBean) viewBeanList.get(i);
					//值不为空
					if(CommonFunction.isNotNull(bean.getRuleValue())){
						if(!TableColumns.rules.Wu.getValue().equals(bean.getRule())){
							sb.append(" and "+bean.getTableName()+"__"+bean.getColunmsName());

							if(TableColumns.rules.DengYu.getValue().equals(bean.getRule())){
								sb.append(" = '");
							}else if(TableColumns.rules.DaYu.getValue().equals(bean.getRule())){
								sb.append(" > '");
							}else if(TableColumns.rules.DaYuDengYu.getValue().equals(bean.getRule())){
								sb.append(" >= '");
							}else if(TableColumns.rules.XiaoYu.getValue().equals(bean.getRule())){
								sb.append(" < '");
							}else if(TableColumns.rules.XiaoYuDengYu.getValue().equals(bean.getRule())){
								sb.append(" <= '");
							}else if(TableColumns.rules.BuDengYu.getValue().equals(bean.getRule())){
								sb.append(" != '");
							}else if(TableColumns.rules.BaoHan.getValue().equals(bean.getRule())){
								sb.append(" like '");
							}else if(TableColumns.rules.QiShiZiFu.getValue().equals(bean.getRule())){
								sb.append(" like '");
							}else if(TableColumns.rules.BuBaoHan.getValue().equals(bean.getRule())){
								sb.append(" not like '");
							}

							if(bean.getColDataType() == 3){
								//年月日时分秒时间
								sb.append(bean.getRuleValue()+" 23:59:59'");
							}else if(bean.getColDataType() == 11){
								//年月日时间
								sb.append(bean.getRuleValue()+"'");
							}else{
								if(TableColumns.rules.BaoHan.getValue().equals(bean.getRule())){
									sb.append("%"+bean.getRuleValue().trim()+"%'");
								}else if(TableColumns.rules.QiShiZiFu.getValue().equals(bean.getRule())){
									sb.append(bean.getRuleValue().trim()+"%'");
								}else if(TableColumns.rules.BuBaoHan.getValue().equals(bean.getRule())){
									sb.append("%"+bean.getRuleValue().trim()+"%'");
								}else{
									sb.append(bean.getRuleValue().trim()+"'");
								}
							}
						}
					}
				}
			}else{
				//第一次查询视图，
				if(searchlist != null && searchlist.size() > 0){
					ViewSearch se=null;
					TableColumns tc=null;
					for (int i = 0; i < searchlist.size(); i++) {
						se=(ViewSearch) searchlist.get(i);
						Long colId=se.getColumnId();
						if(colId!=null){
							tc=getTableColumnsById(se.getColumnId());
							Tables table=(Tables) this.get(Tables.class, tc.getTableId());
							if(!TableColumns.rules.Wu.getValue().equals(se.getRule())){
								sb.append(" and "+table.getName()+"__"+tc.getName());

								if(TableColumns.rules.DengYu.getValue().equals(se.getRule())){
									sb.append(" = '");
								}else if(TableColumns.rules.DaYu.getValue().equals(se.getRule())){
									sb.append(" > '");
								}else if(TableColumns.rules.DaYuDengYu.getValue().equals(se.getRule())){
									sb.append(" >= '");
								}else if(TableColumns.rules.XiaoYu.getValue().equals(se.getRule())){
									sb.append(" < '");
								}else if(TableColumns.rules.XiaoYuDengYu.getValue().equals(se.getRule())){
									sb.append(" <= '");
								}else if(TableColumns.rules.BuDengYu.getValue().equals(se.getRule())){
									sb.append(" != '");
								}else if(TableColumns.rules.BaoHan.getValue().equals(se.getRule())){
									sb.append(" like '");
								}else if(TableColumns.rules.QiShiZiFu.getValue().equals(se.getRule())){
									sb.append(" like '");
								}else if(TableColumns.rules.BuBaoHan.getValue().equals(se.getRule())){
									sb.append(" not like '");
								}

								if(tc.getDataType() == 3){
									//年月日时分秒时间
									sb.append(se.getRuleValue()+" 23:59:59'");
								}else if(tc.getDataType() == 11){
									//年月日时间
									sb.append(se.getRuleValue()+"'");
								}else{
									if(TableColumns.rules.BaoHan.getValue().equals(se.getRule())){
										sb.append("%"+se.getRuleValue().trim()+"%'");
									}else if(TableColumns.rules.QiShiZiFu.getValue().equals(se.getRule())){
										sb.append(se.getRuleValue().trim()+"%'");
									}else if(TableColumns.rules.BuBaoHan.getValue().equals(se.getRule())){
										sb.append("%"+se.getRuleValue().trim()+"%'");
									}else{
										sb.append(se.getRuleValue().trim()+"'");
									}
								}

							}
						}

					}
				}
			}
			result = this.getPageQueryResultSQL(sb.toString(), null, qEntity);
		} catch (Exception e) {
			throw new BusinessException("查询视图记录出现异常");
		}

		return result;
	}

	public void del(Long tableId,Long id) throws BusinessException {

		Map paramMap = new HashMap();
		paramMap.put("primaryKey", 1);
		List<TableColumns> tc = this.loadTableColumn(tableId, paramMap);

		List<TableRelationship> tr = this.queryMainTableRelationship(tableId, tc.get(0).getId());
		for(TableRelationship tableRelationship : tr){
			Tables t=this.getTablesById(tableRelationship.getSourceTableId());
			Map map=new HashMap();
			map.put("id", tableRelationship.getSourceColumnId());
			String hql="from TableColumns where id=:id order by orderby";
			List list=this.getList(hql, map);
			if(list != null && list.size() > 0){
				TableColumns col=null;
				for (int i = 0; i < list.size(); i++) {
					col=(TableColumns) list.get(i);
					this.executeSQL("delete from "+t.getName()+" where "+col.getName()+"="+id);
				}
			}
		}

		Tables t=this.getTablesById(tableId);
		Map map=new HashMap();
		map.put("tableId", tableId);
		String hql="from TableColumns where tableId=:tableId order by orderby";
		List list=this.getList(hql, map);
		if(list != null && list.size() > 0){
			TableColumns col=null;
			for (int i = 0; i < list.size(); i++) {
				col=(TableColumns) list.get(i);
				if(col.getPrimaryKey() == 1 && col.getIncrement() == 1){
					this.executeSQL("delete from "+t.getName()+" where "+col.getName()+"="+id);
				}
			}
		}
	}

	public List<TableColumns> loadTableColumn(Long tableId,Map paramMap) {
		StringBuilder hql =new StringBuilder("from TableColumns where tableId=:tableId  and (1=1  ");
		Map m = new HashMap();
		m.put("tableId", tableId);
		if(paramMap !=null){
			Object showinform = paramMap.get("showinform");
			Object showinlist = paramMap.get("showinlist");
			Object primaryKey = paramMap.get("primaryKey");
			if(CommonFunction.isNotNull(showinform)){
				hql.append(" and showinform=:showinform");
				m.put("showinform", showinform);
			}
			else if(CommonFunction.isNotNull(showinlist)){
				hql.append(" and showinlist = :showinlist");
				m.put("showinlist", showinlist);
			}
			if(CommonFunction.isNotNull(primaryKey)){
				hql.append(" or primaryKey =:primaryKey");
				m.put("primaryKey",primaryKey);

			}
		}
		hql.append(" ) ");
		hql.append("order by orderby,id");
		//m.put("showinform", TableColumns.SHOWINFORM);
		return this.getList(hql.toString(), m);
	}

	public List<TableColumns> loadViewTableColumn(Long viewId) {

		Map m = new HashMap();
		String hql ="from TableColumns where id in (select columnId from ViewList where viewId=:viewId and columnId!='') or id in (select targetColId from ViewList where viewId=:viewId and targetColId!='')";
		m.put("viewId", viewId);

		return this.getList(hql, m);
	}


	@Override
	public List loadViewSearchNew(Long viewId) {
		StringBuilder sql_condition=new StringBuilder("select (select col.name from sys_table_columns col where col.id=vs.colunms_id),(select col.name_cn from sys_table_columns col where col.id=vs.colunms_id),vs.colunms_id,");
		sql_condition.append(" vs.rule,vs.rule_value,vs.table_id,(select col.name from sys_table_columns col where col.id=vs.target_column_id),(select col.name_cn from sys_table_columns col where col.id=vs.target_column_id),vs.target_column_id,vs.target_table_id,");
		sql_condition.append("(select col.dataType from sys_table_columns col where col.id=vs.colunms_id),(select col.dataType from sys_table_columns col where col.id=vs.target_column_id),");
		sql_condition.append("(select col.optvalue from sys_table_columns col where col.id=vs.colunms_id),(select col.optvalue from sys_table_columns col where col.id=vs.target_column_id)");
		sql_condition.append(" from sys_view_search vs where vs.view_id="+viewId+" and vs.state=1 and vs.type=1 ");
		List sList=this.findBySQL(sql_condition.toString());
		List searchList=new ArrayList();
		if(sList != null && sList.size() > 0){
			for(int i=0;i<sList.size();i++){
				ViewSearchBean bean=new ViewSearchBean();
				Object[] obj=(Object[]) sList.get(i);
				if(obj[0]!=null && !"".equals(obj[0].toString())){
					bean.setColunmsId(Long.parseLong(obj[2].toString()));
					bean.setColunmsName(obj[0].toString());
					bean.setColunmsNameCn(obj[1].toString());
					bean.setColDataType(Long.parseLong(obj[10].toString()));
					bean.setOptValue(CommonFunction.isNull(obj[12])?"":obj[12].toString());
					Tables table=(Tables) this.get(Tables.class, Long.parseLong(obj[5].toString()));
					bean.setTableName(table.getName());
				}

				if(obj[6]!=null && !"".equals(obj[6].toString())){
					bean.setColunmsId(Long.parseLong(obj[8].toString()));
					bean.setColunmsName(obj[6].toString());
					bean.setColunmsNameCn(obj[7].toString());
					bean.setColDataType(Long.parseLong(obj[11].toString()));
					bean.setOptValue(CommonFunction.isNull(obj[13])?"":obj[13].toString());
					Tables table=(Tables) this.get(Tables.class, Long.parseLong(obj[9].toString()));
					bean.setTableName(table.getName());
				}
				bean.setRule(obj[3].toString());
				bean.setRuleValue(obj[4].toString());
				searchList.add(bean);
			}
		}
		return searchList;
	}

	public List loadViewSearch(Long viewId) {
		Map m = new HashMap();
		String sql ="SELECT v.id,v.colunms_id,tc.name,tc.name_cn,v.rule,v.rule_value,tc.dataType,tc.optvalue FROM sys_view_search v LEFT JOIN sys_table_columns tc ON v.colunms_id=tc.id WHERE v.view_id=:viewId and type=1 and state=1";
		m.put("viewId", viewId);
		List slist=this.getListBySQL(sql, m);
		List searchList=new ArrayList();
		if(slist != null && slist.size() > 0){
			ViewSearchBean bean=null;
			Object[] obj=null;
			for (int i = 0; i < slist.size(); i++) {
				obj=(Object[]) slist.get(i);
				bean=new ViewSearchBean();
				bean.setColunmsId(Long.parseLong(obj[1].toString()));
				bean.setColunmsName(obj[2].toString());
				bean.setColunmsNameCn(obj[3].toString());
				bean.setRule(obj[4].toString());
				bean.setRuleValue(obj[5].toString());
				bean.setColDataType(Long.parseLong(obj[6].toString()));
				bean.setOptValue(CommonFunction.isNull(obj[7])?"":obj[7].toString());
				searchList.add(bean);
			}
		}
		return searchList;
	}

	public List getViewSearch(Long viewId) {
		String hql="from ViewSearch where type=1 AND state=1 and viewId="+viewId;
		return this.getList(hql, null);
	}
	/**
	 * 添加后
	 */
	private void afretSave(Long tableId){
		Tables tables = (Tables) this.get(Tables.class, tableId);
		String sql ="select @@identity";
		Object o  = this.getUniqueResultSql(sql,null);
		Map<String,Object> body = this.getTableDataById(tableId, o);
		List<TableColumns>  codeColumns  = tableColumnsService.getTableColumnsByTIdAndType(tableId, DataType.QRCODE.getCode());
		StringBuilder updateCode = new StringBuilder();
		updateCode.append("update ").append(tables.getName()).append(" set ");
		Map<String,Object> paramMap = new HashMap<String, Object>();
		for (TableColumns tableColumns : codeColumns) {
			updateCode.append(tableColumns.getName()).append("=:").append(tableColumns.getId()).append(",");
			StringBuilder filePath =new StringBuilder();
			filePath.append(CacheFun.getConfig("qrcode_img")).append(File.separator).append(UploadFileUtils.createNewFileName()).append(".").append(QRcode.CODE_FORMAT);
			StringBuilder uploadFilePath = new StringBuilder();
			uploadFilePath.append(Environment.getWebAppsHome()).append(File.separator).
				append(filePath);
			QRcode.create(new File(uploadFilePath.toString()), QrCodeOptUtil.calCodeOpt(tableColumns.getOptvalue(), body));
			paramMap.put(tableColumns.getId().toString(),filePath.toString());
		}
		if(paramMap.size()>0){
			TableColumns primaryKey = tableColumnsService.gerPriayKeyColumnsByTableId(tableId);
			updateCode.deleteCharAt(updateCode.length()-1);
			updateCode.append(" where ");
			updateCode.append(primaryKey.getName()).append("=").append(":pkId");
			paramMap.put("pkId",o);
			this.executeSQL(updateCode.toString(), paramMap);
		}
		
		
	}
	
	
	@Override
	public void saveTableDate(Long tableId, Map<String,String> columnsMap,String adminId,String param) throws BusinessException {
		Tables tables = (Tables) this.get(Tables.class, tableId);
		if(tables==null){
			throw new BusinessException("表不存在");
		}
		Map<String,String> mapColumns = formatSqlParam(columnsMap, tableId, adminId);

		

		this.executeSQL(formatInsertSql(tables.getId(),tables.getName(), mapColumns),mapColumns);
		afretSave(tableId);
		
		String hql="from TableColumns where tableId="+tableId;
		List listCol=this.find(hql);
		String colName="";
		if(listCol.size()>0){
			for(int i=0;i<listCol.size();i++){
				TableColumns col=(TableColumns) listCol.get(i);
				if(col.getPrimaryKey().shortValue()==1){
					colName=col.getName();
				}
			}

			String sql_maxId="select max("+colName+") from "+tables.getName();
			Long id=Long.parseLong(this.findBySQL(sql_maxId).get(0).toString());//作为从表外键存储id

			if(CommonFunction.isNotNull(param)){
				String[] p1=param.split(",");
				if(CommonFunction.isNotNull(p1)){
					for(int i=0;i<p1.length;i++){
						if(CommonFunction.isNotNull(p1[i])){
							String[] p2=p1[i].split("#");
							Long tableId_fk=Long.parseLong(p2[0]);//外键表id
							Tables tab=(Tables) this.get(Tables.class, tableId_fk);
							String tabName=tab.getName();
							String colNameStr="";
							String valueStr="";
							String fk_col_name="";
							for(int j=1;j<p2.length;j++){
								String col_name=p2[j].split(":")[0];//列名
								String col_value=p2[j].split(":")[1];//参数值
								String col_id=p2[j].split(":")[2];
								//查询外键
								String fk_hql="from TableRelationship where sourceTableId="+tableId_fk+" and targetTableId="+tableId;
								List list=this.find(fk_hql);
								TableRelationship ship=(TableRelationship) list.get(0);
								Long columnId=ship.getSourceColumnId();
								TableColumns tCol=(TableColumns) this.get(TableColumns.class, columnId);
								if(tCol.getDataType().equals(DataType.AUTONUMBER.getCode())){
									break;	
								}
								fk_col_name=tCol.getName();
								colNameStr+=","+col_name;
								valueStr+=",'"+col_value+"'";
							}
							colNameStr=colNameStr.substring(1)+","+fk_col_name;
							valueStr=valueStr.substring(1)+",'"+id+"'";
							String saveSql="insert into "+tabName+" ("+colNameStr+") values("+valueStr+")";
							this.executeSQL(saveSql);
						}
					}
				}
			}
		}

	}

	/**
	 * 格式化insert 语句
	 * @param ColumnsMap
	 * @return
	 */
	private String formatInsertSql(Long tableId,String tableName,Map<String,String>  columnsMap){
		Set<String> keySet = columnsMap.keySet();
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("insert into ");
		insertSql.append(tableName);
		StringBuilder columns = new StringBuilder();
		StringBuilder values = new StringBuilder();
		List<TableColumns> tableColumns=tableColumnsService.getTableColumnsByTIdAndType(tableId, DataType.AUTONUMBER.getCode());
		for (TableColumns tableColumns2 : tableColumns) {
			if(CommonFunction.isNull(columnsMap.get(tableColumns2.getName()))){
				columnsMap.put(tableColumns2.getName(), sequenceService.getNewNoByTableColumns(tableColumns2.getId()));
			}
		}
		if(tableColumnsService.existsAddDate(tableId)){
			if(CommonFunction.isNull(columnsMap.get(TableColumns.DefaultColumn.ADD_DATE.getName()))){
				columnsMap.put(TableColumns.DefaultColumn.ADD_DATE.getName(), DateUtil.format2Full(new Date()));
			}
		}
	
		for (String key : keySet) {
			//String value = columnsMap.get(key);
			columns.append(key).append(",");
			values.append(":"+key).append(",");	
		}
		insertSql.append("(").append(columns.substring(0,columns.length()-1)).append(")");
		insertSql.append(" values");
		insertSql.append("(").append(values.substring(0,values.length()-1)).append(")");
		return insertSql.toString();
	}

	/**
	 * 格式化查询字段语句
	 * @param tableName
	 * @param colunmsList
	 * @param paramMap
	 * @return
	 */
	private String formatSelectBySql(String tableName,List<TableColumns> colunmsList,Map<String,String> paramMap){
		StringBuilder findSql = new StringBuilder();
		findSql.append(" select ");
		if(colunmsList!=null && colunmsList.size()>0){
			StringBuilder columns = new StringBuilder();
			for (TableColumns tableColumns : colunmsList) {
				columns.append(tableColumns.getName()).append(",");
			}
			findSql.append(columns.substring(0, columns.length()-1));
		}
		else{
			findSql.append(" * ");
		}
		findSql.append(" from ").append(tableName).append(" where 1=1 ");

		if(paramMap!=null){
			for(Map.Entry<String, String> entry:paramMap.entrySet()){
				findSql.append(" and ");
				findSql.append(entry.getKey());
				findSql.append(" =:").append(entry.getKey());
			}
		}
		return findSql.toString();
	}
	/***
	 * 格式化修改语句
	 * @param tableName
	 * @param colunmsList
	 * @param paramMap
	 * @return
	 */
	private String formatUpdateBySql(Long tableId,String tableName,Map<String,String> columnsMap,String pkKey,String pkValue){
		StringBuilder updateSql = new StringBuilder();
		updateSql.append(" update ");
		updateSql.append(tableName);
		updateSql.append(" set ");

		StringBuilder updateColunms = new StringBuilder();
		StringBuilder wherePk = new StringBuilder();
		wherePk.append(" where 1=1");
		if(tableColumnsService.existsUpdateDate(tableId)){
			if(CommonFunction.isNull(columnsMap.get(TableColumns.DefaultColumn.UPDATE_DATE.getName()))){
				columnsMap.put(TableColumns.DefaultColumn.UPDATE_DATE.getName(), DateUtil.format2Full(new Date()));
			}
		}
				
		for(Map.Entry<String,String> entry:columnsMap.entrySet()){
			String colunms = entry.getKey();
			updateColunms.append(colunms);
			updateColunms.append("=:");
			updateColunms.append(colunms);
			updateColunms.append(",");

		}
		
		wherePk.append(" and ");
		wherePk.append(pkKey);
		wherePk.append("=");
		wherePk.append(":");
		wherePk.append(pkKey);
		updateSql.append(updateColunms.substring(0,updateColunms.length()-1)) .append(wherePk);
		columnsMap.put(pkKey, pkValue);
		return updateSql.toString();
	}

	@Override
	public String loadTablePkByTableId(Long tableId) throws BusinessException {
		String hql = "select name from TableColumns s  where s.tableId =:tableId and s.primaryKey=:primaryKey";
		Map m = new HashMap();
		m.put("tableId",tableId);
		m.put("primaryKey",TableColumns.STATE_YES);
		return (String) this.getUniqueResult(hql, m);

	}

	@Override
	public Map loadTableDataByTableIdAndPk(Long tableId, String pkValue,List<TableColumns> colunmsList)
			throws BusinessException {
		Tables tables = (Tables) this.get(Tables.class, tableId);
		String pkKey = loadTablePkByTableId(tableId);
		if(CommonFunction.isNull(pkKey)){
			throw new BusinessException("该表没有主键,不能编辑");
		}
		Map m = new HashMap();
		m.put(pkKey, pkValue);
		List list = this.getListBySQLToMap(formatSelectBySql(tables.getName(), colunmsList,m),m);
		if(list ==null || list.size()==0)
			return null;
		return (Map) list.get(0);
	}

	@Override
	public Tables getTablesById(Long tableId) {

		return (Tables) this.get(Tables.class,tableId);
	}

	public void updateTableDataByParam(Long tableId,
			Map<String, String> colunmnMap,String adminId,String pkValue) throws BusinessException {
		Tables tables = 	this.getTablesById(tableId);
		String pk = loadTablePkByTableId(tableId);
		if(CommonFunction.isNull(pk))
			throw new BusinessException("表名错误");
		Map<String,String> mapColumns = formatSqlParam(colunmnMap, tableId, adminId);
		
		this.executeSQL(formatUpdateBySql(tableId,tables.getName(),mapColumns,pk,pkValue),mapColumns);
	}

	public Menu getMenuById(Long id){
		return (Menu) this.get(Menu.class, id);
	}

	public List getTableViewNum(Long tableId) {
		Map map=new HashMap();
		map.put("tableId", tableId);
		String hql="from View where tableId=:tableId and state=1";
		List list=this.getList(hql, map);
		return list;
	}
	public View getViewById(Long viewId) {
		return (View) this.get(View.class, viewId);
	}

	public TableColumns getTableColumnsById(Long colId) {
		return (TableColumns) this.get(TableColumns.class, colId);
	}

	@Override
	public QueryResult queryShip(Map pageMap, String tableName,
			String pkName, String showName) {
		BaseQEntity baseQEntity = (BaseQEntity) pageMap.get("qEntity");
		String name = (String) pageMap.get("name");
		String $_name = (String) pageMap.get("pkName");
		String $_val =(String)pageMap.get("pkVal");
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append(pkName);
		sql.append(" as pkName");
		sql.append(",");
		sql.append( showName);
		sql.append(" as showName");
		sql.append(" from ");
		sql.append(tableName);
		sql.append(" where 1=1 ");
		Map param = new HashMap();
		if(CommonFunction.isNotNull(name)){
			sql.append(" and ").append(showName).append(" like :showName");
			param.put("showName", "%"+name+"%");
		}
		if(CommonFunction.isNotNull($_name) && CommonFunction.isNotNull($_val)){
			sql.append(" and ").append($_name).append("=:pkVal");
			param.put("pkVal", $_val);
		}
		sql.append(" order by ");
		sql.append(pkName);
		sql.append(" desc");
		return this.getPageQueryResultSQLToMap(sql.toString(), param, baseQEntity);
	}





	/**
	 * 重新格式化添加或编辑字段值
	 * @param columnsMap
	 * @param tableId
	 * @return
	 */
	public Map<String,String> formatSqlParam(Map<String,String> columnsMap,Long tableId,String adminId){
		Map<String,String> mapColumns = new HashMap<String, String>();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tableId",tableId);
		String hql = " from TableColumns col where col.tableId=:tableId";
		List colList = this.getList(hql, map);
		if(colList!=null && colList.size()>0){
			for(int i=0;i<colList.size();i++){
				TableColumns col = (TableColumns) colList.get(i);
				if(col.getShowinform()==TableColumns.STATE_YES){//表单显示
					String value = columnsMap.get(col.getName());
					if(col.getDataType()==15){//密码
						if(CommonFunction.isNotNull(value)){
							mapColumns.put(col.getName(),MD5.MD5(value));
						}
					}else{
						mapColumns.put(col.getName(),value);
					}
				}else{
					String value = columnsMap.get(col.getName());
					if(col.getDataType()==16){//系统时间
						mapColumns.put(col.getName(),DateUtil.currentDate("yyyy-MM-dd HH:mm:ss"));
					}else if(col.getDataType()==17){//系统操作人id 
						mapColumns.put(col.getName(),adminId);	
					}else if(CommonFunction.isNotNull(col.getDefaults())&& col.getNotnull()==1){
						mapColumns.put(col.getName(), col.getDefaults());
					}
					

				}

			}
		}
		return mapColumns;
	}
	public Map<String,String> formatEditSqlParam(Map<String,String> columnsMap,Long tableId,String adminId){
		Map<String,String> mapColumns = new HashMap<String, String>();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tableId",tableId);
		String hql = " from TableColumns col where col.tableId=:tableId";
		List colList = this.getList(hql, map);
		if(colList!=null && colList.size()>0){
			for(int i=0;i<colList.size();i++){
				TableColumns col = (TableColumns) colList.get(i);
				if(col.getShowinform()==TableColumns.STATE_YES){//表单显示
					String value = columnsMap.get(col.getName());
					if(col.getDataType()==15){//密码
						if(CommonFunction.isNotNull(value)){
							mapColumns.put(col.getName(),MD5.MD5(value));
						}
					}else{
						mapColumns.put(col.getName(),value);
					}
				}
			}
		}
		return mapColumns;
	}
	public TableColumnsService getTableColumnsService() {
		return tableColumnsService;
	}

	public void setTableColumnsService(TableColumnsService tableColumnsService) {
		this.tableColumnsService = tableColumnsService;
	}

	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	@Override
	public QueryResult queryEmployeeByDepId(Map pageMap,Long[] deptIds) {
		Map par=new HashMap();
		QueryResult result = null;
		BaseQEntity qEntity = (BaseQEntity) pageMap.get("qEntity");
		StringBuffer hql= new StringBuffer(" ");
		if(deptIds != null){
			hql.append("select e,depart from Employee e,Department depart,EmployeeDeptart edp where e.id in ( select ed.employeeId AS ids from  EmployeeDeptart ed where ed.departId in (:deptIds)) ");
			hql.append(" and edp.employeeId=e.id and edp.departId=depart.id "); 
			par.put("deptIds", deptIds);
		}else{
			hql.append("select e,de from Employee e,Department de,EmployeeDeptart d  where d.employeeId=e.id and d.departId=de.id ");
		}
		
		if(CommonFunction.isNotNull(pageMap.get("serialNo"))){
			hql.append(" and  e.serialNo like:serialNo ");
			par.put("serialNo", "%"+pageMap.get("serialNo")+"%");
		}
		if(CommonFunction.isNotNull(pageMap.get("name"))){
			hql.append(" and  e.name like :name");
			par.put("name", "%"+pageMap.get("name")+"%");
		}
		if(CommonFunction.isNotNull(pageMap.get("phoneNum"))){
			hql.append(" and  e.phoneNum like :phoneNum");
			par.put("phoneNum", "%"+pageMap.get("phoneNum")+"%");
		}
		if(CommonFunction.isNotNull(pageMap.get("idcard"))){
			hql.append(" and  e.idcard like :idcard");
			par.put("idcard", "%"+pageMap.get("idcard")+"%");
		}
		hql.append(" ORDER BY e.id");
		System.out.println("hql:"+hql);
		result = this.getPageQueryResult(hql.toString(), par, qEntity);
		return result;
	}

	@Override
	public QueryResult queryEmployee(Map pageMap) throws BusinessException {
		String hql = "select e from Employee e where 1=1";
		QueryResult result = null;
		BaseQEntity qEntity = (BaseQEntity) pageMap.get("qEntity");
		result = this.getPageQueryResult(hql.toString(), null, qEntity);
		return result;
	}


	@Override
	public List<TreeView> getTreeList(TableRelationship treeShip) {
		Tables mainTables = this.getTablesById(treeShip.getSourceTableId());
		TableColumns nameColumns = (TableColumns) this.get(TableColumns.class, treeShip.getTargetShowColumnId());
		TableColumns parentIdColumns = (TableColumns)this.get(TableColumns.class,treeShip.getSourceColumnId());
		TableColumns idColumns =(TableColumns)this.get(TableColumns.class,treeShip.getTargetColumnId());
		StringBuilder sql = new StringBuilder("select ");
		sql.append(idColumns.getName()).append(" as id,");
		sql.append(nameColumns.getName()).append(" as name,");
		sql.append(parentIdColumns.getName()).append(" as parentId");
		sql.append(" from ").append(mainTables.getName());
		SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		query.addScalar("id", StandardBasicTypes.LONG);
		query.addScalar("name", StandardBasicTypes.STRING);
		query.addScalar("parentId", StandardBasicTypes.LONG);
		return this.getListBySQLToBean(query, null, TreeView.class);
	}
	@Override
	public MenuConfig getMenuConfigByMenuId(Long menuId) {
		String hql="from MenuConfig where menuId=:menuId";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("menuId", menuId);
		return (MenuConfig)this.getUniqueResult(hql, map);
	}

	@Override
	public Map<String, Object> getTableDataById(Long tableId,Object pkValue) {
		Tables tables = (Tables) this.get(Tables.class, tableId);
		List<TableColumns> coList = tableColumnsService.queryTableColumnsByTableId(tableId);
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		String pkColumnName = "";
		for (TableColumns tableColumns : coList) {
			if(tableColumns.isPk()){
				pkColumnName=tableColumns.getName();
			}
			sql.append(tableColumns.getName()).append(" as '").append(tableColumns.getId()).append("',");
		}
		sql = sql.deleteCharAt(sql.length()-1);
		sql.append(" from ").append(tables.getName()).append(" where ");
		sql.append(pkColumnName).append(" = :pkId");
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("pkId",  pkValue.toString());
		List<Map<String,Object>> bodys = this.getListBySQLToMap(sql.toString(), param);
		if(bodys==null){
			return null;
		}
		return bodys.get(0);
	}

	@Override
	public List queryids(Long deptId) {
		String sql="SELECT id FROM `sys_department` WHERE pid=:deptId or id=:deptId "
				+ "UNION SELECT id FROM `sys_department` WHERE pid IN(SELECT id FROM `sys_department` WHERE pid=:deptId)"
				+ "UNION SELECT id FROM `sys_department` WHERE pid IN(SELECT id FROM `sys_department` WHERE pid "
				+ "IN(SELECT id FROM `sys_department` WHERE pid=:deptId))";
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("deptId", deptId);
		return this.getListBySQL(sql, param);
	}




}
