package com.xy.cms.service.imp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.MD5;
import com.xy.cms.common.SessionBean;
import com.xy.cms.common.SpringUtil;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.tableUtil.TableUtil;
import com.xy.cms.entity.Menu;
import com.xy.cms.entity.Power;
import com.xy.cms.entity.TableColumns;
import com.xy.cms.entity.TableRelationship;
import com.xy.cms.entity.Tables;
import com.xy.cms.service.TablesService;

public class TablesServiceImpl extends BaseDAO implements TablesService {
 
	public void addTables(Tables tables, String parStr)throws BusinessException {
		
		//表
		
			tables.setStatus(Short.valueOf("1"));
			tables.setAddDate(new Date());
			tables.setUpdateDate(tables.getAddDate());
			this.save(tables);
			Long tableId=tables.getId();
			//表相关的字段
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = formatter.format(new Date());
			String[] str=parStr.split(",");
			if(str!=null && str.length>0){
				for(int i=0;i<str.length;i++){
					String[] parArr=str[i].split("#");
					if(parArr!=null && parArr.length>0){
							int dataType=Integer.parseInt(parArr[1]);
							int notnull=Integer.parseInt(parArr[5]);
							int increment=Integer.parseInt(parArr[6]);
							int showinform=Integer.parseInt(parArr[7]);
							int showinlist=Integer.parseInt(parArr[8]);
							int orderBy=Integer.parseInt(parArr[10]);
							int prikey=Integer.parseInt(parArr[4]);
							String sql="insert into sys_table_columns(table_id,name,dataType,len,defaults,notnull,showinform,showinlist,des,orderby,status,add_date,primaryKey,increment,optvalue,name_cn)";
							sql+="values("+tableId+",'"+parArr[0]+"',"+dataType+",'"+parArr[2]+"','"+parArr[3]+"',"+notnull+","+showinform+","+showinlist+",'"+parArr[9]+"',"+orderBy+",1,'"+date+"',"+prikey+","+increment+",'"+parArr[11]+"','"+parArr[12]+"')";
						    this.executeSQL(sql);//插入数据
					}
				}
			}
			//删除已经存在的表
			this.executeSQL("DROP TABLE IF EXISTS "+tables.getName());
			//创建表
			String hql_col="from TableColumns where tableId="+tables.getId();
			List list=this.find(hql_col);
			String col_str="";
			for(int i=0;i<list.size();i++){
				TableColumns col=(TableColumns) list.get(i);
				String colName=col.getName();//列名
				Long dataType=col.getDataType();//数据类型
				String len=col.getLen();//字段长度
				String defaults=col.getDefaults();//默认值
				short notnull=col.getNotnull();//是否允许为空  1:不允许0:允许
				String comment=col.getDes();//备注
				short primaryKey=col.getPrimaryKey();//是否主键 0:不是 1:是
				short increment=col.getIncrement();//是否自增长 0:不是 1:是
				
				col_str+=colName;
				if(dataType==1 || dataType==17){//整型
					col_str+=" "+"bigint("+len+")";
					if(notnull==1){
						col_str+=" "+"NOT NULL";
						if(primaryKey==1){
							col_str+=" primary key";
							if(increment==1){
								col_str+=" AUTO_INCREMENT";
							}
						}else{
							if(CommonFunction.isNotNull(defaults)){
								col_str+=" DEFAULT '"+defaults+"'";
							}
						}
					}
				}else if(dataType==2 || dataType==7 || dataType==8 || dataType==9 || dataType==10 || dataType==12 || dataType==15 || dataType==18 || dataType == 19){//字符串
					col_str+=" "+"varchar("+len+")";
					if(notnull==1){
						col_str+=" not null";
						col_str+=" DEFAULT '"+defaults+"'";
					}
				}else if(dataType==3 || dataType==16){//日期类型   datetime
					col_str+=" "+"datetime";
					if(notnull==1){
						col_str+=" not null";
						if(CommonFunction.isNotNull(defaults)){
							col_str+=" DEFAULT now()";
						}
					}
				}else if(dataType==11){//日期类型  date
					col_str+=" "+"date";
					if(notnull==1){
						col_str+=" not null";
						
					}
				}else if(dataType==4){//大文本
					col_str+=" "+"text";
					if(notnull==1){
						col_str+=" not null";
					}
				}else if(dataType==5){//浮点型
					col_str+=" "+"double(16,2)";
					if(notnull==1){
						col_str+=" not null";
						if(CommonFunction.isNotNull(defaults)){
							col_str+=" DEFAULT '"+defaults+"'";
						}
					}
				}else if(dataType==6){//字符型
					col_str+=" "+"char("+len+")";
					if(notnull==1){
						col_str+=" not null";
						col_str+=" DEFAULT '"+defaults+"'";
					}
				}
				
				col_str+=" COMMENT '"+comment+"',";
			}
			col_str=col_str.substring(0,col_str.lastIndexOf(","));
			StringBuffer createTableSql=new StringBuffer("create table "+tables.getName()+"(");
			createTableSql.append(col_str);
			createTableSql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT");
			
			System.out.println("==================================="+createTableSql.toString());
			try {
				Connection conn = (Connection) SpringUtil.getConnectionFromSpring("dataSource");
				Statement statement=conn.createStatement();
				statement.executeUpdate(createTableSql.toString());
				conn.close();
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
			
	}

	public void editTables(Tables tables,String tabOldname)throws BusinessException {
		tables.setStatus((short) 1);
		tables.setUpdateDate(new Date());
        this.update(tables);
        this.executeSQL("alter table "+tabOldname+" rename to "+tables.getName()+"");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public QueryResult queryTables(Map<String, Object> map)
			throws BusinessException {
		QueryResult result = null;
		Map m = new HashMap();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		Tables table=(Tables) map.get("table");
		String beginDate=(String) map.get("beginDate");
		String endDate=(String) map.get("endDate");
		StringBuffer hql=new StringBuffer("from Tables tab where 1=1");
		if(table!=null){
			if(CommonFunction.isNotNull(table.getName())){
				hql.append(" and (tab.name like :name or tab.nameCn like :name)");
				m.put("name", "%"+table.getName()+"%");
			}
			/*if((beginDate!=null && !beginDate.equals("")) || (endDate!=null && !endDate.equals(""))){
				hql.append(" and tab.addDate between '"+beginDate+"' and '"+endDate+" 23:59:59'");
			}*/
			if(CommonFunction.isNotNull(beginDate)){
				hql.append(" and tab.addDate >=:beginDate");
				m.put("beginDate", beginDate);
			}
			if(CommonFunction.isNotNull(endDate)){
				hql.append(" and tab.addDate <=:endDate ");
				m.put("endDate", endDate+" 23:59:59");
			}
		}
		hql.append(" order by addDate desc");
		result = this.getPageQueryResult(hql.toString(), m, qEntity);
		return result;
	}

	@Override
	public List showColumnByTable(String tableName) {
		String sql="SHOW COLUMNS FROM sys_table_columns";
		List list=this.findBySQL(sql);
		return list;
	}

	@Override
	public Tables getTableById(Long tableId) {
		Tables tables=(Tables) this.get(Tables.class, tableId);
		return tables;
	}

	@Override
	public List getCoListById(Long tabledId) {
		String hql="from TableColumns where tableId="+tabledId;
		List list=this.find(hql);
		return list;
	}
	
	public void create(String name,Long tableId,Long supiorId,SessionBean bean) {
		
		Tables t=(Tables) this.get(Tables.class, tableId);
		Map paras=new HashMap();
		String sql="SELECT MAX(orderby) FROM sys_menu WHERE LEVEL=2 and supior_id=:supiorId";
		paras.put("supiorId", supiorId);
		List list=this.getListBySQL(sql, paras);
		Integer num ;
		Object obj=list.get(0);
		if(obj != null && obj != ""){
			int orderby=Integer.parseInt(obj.toString());
			num = ++orderby;
			
		}else{
			Menu m=(Menu) this.get(Menu.class, supiorId);
			num=m.getOrderby()+1;
		}
		Menu m=new Menu();
		m.setName(name);
		m.setLevel((short)2);
		m.setSupiorId(supiorId);
		m.setUrl("model!init.action?tableId="+tableId);
		m.setOrderby(num);
		m.setIcon(null);
		m.setState((short)1);
		m.setIsMenu("0");
		
		this.save(m);
		
		Power p=new Power();
		p.setMenuId(m.getId());
		p.setRoleId(bean.getAdmin().getRoleId());
		this.save(p);
		
		t.setMenuId(m.getId());
		this.update(t);
		
	}
	

	public void updateRelation(TableRelationship ship){
		this.update(ship);
	}
	
	@Override
	public void saveCol(Long tableId, String parStr) throws BusinessException {
	
		Tables tables=(Tables) this.get(Tables.class, tableId);
		String sql_countTab="select count(*) from "+tables.getName();
		int count_tab =Integer.parseInt(this.findBySQL(sql_countTab).get(0).toString());
			
		String[] parStrArr=parStr.split(",");
		String[] arr;
		for(int i=0;i<parStrArr.length;i++){
			String par_Strp=parStrArr[i];
			arr=par_Strp.split("#");
			
			if(arr!=null && arr.length>0){
				String columnId=arr[0];
				String name=arr[1];
				int dataType=Integer.parseInt(arr[2]);
				String len=arr[3];
				String defaults=arr[4];
				int prikey=Integer.parseInt(arr[5]);
				int notnull=Integer.parseInt(arr[6]);
				int zizeng=Integer.parseInt(arr[7]);
				int showinform=Integer.parseInt(arr[8]);
				int showinlist=Integer.parseInt(arr[9]);
				String des=arr[10]; 
				int orderBy=Integer.parseInt(arr[11]);
				String oldName=arr[12];
				String opValue=arr[13];
				String nameCN=arr[14];
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = formatter.format(new Date());
				if(CommonFunction.isNull(columnId)){//如果为空，则表示是新增的字段
					String sql_countCol="select count(*) from sys_table_columns where name='"+name+"' and table_id="+tableId;
					int count_col=Integer.parseInt(this.findBySQL(sql_countCol).get(0).toString());
					if(count_col>0){
						throw new BusinessException("该列已存在");
					}
					String sql="insert into sys_table_columns(table_id,name,dataType,len,defaults,notnull,showinform,showinlist,des,orderby,status,add_date,primaryKey,increment,optvalue,name_cn)";
				    sql+="values("+tableId+",'"+name+"',"+dataType+",'"+len+"','"+defaults+"',"+notnull+","+showinform+","+showinlist+",'"+des+"',"+orderBy+",1,'"+date+"',"+prikey+","+zizeng+",'"+opValue+"','"+nameCN+"')";
				    System.out.println("==============================="+sql);
				    this.executeSQL(sql);
				    //增加一列
				    String addOneColumnSql="alter table "+tables.getName()+" add column "+TableUtil.getStrResult(name, Long.parseLong(arr[2]), len, defaults, notnull, des, prikey, zizeng);
				    this.executeSQL(addOneColumnSql);
				}else{//反之，则是修改的
					/*if(count_tab>0){
						throw new BusinessException("该表已存在数据，不能修改");
					}*/
					String sql="update sys_table_columns set name='"+name+"',dataType="+dataType+",len='"+len+"',defaults='"+defaults+"',notnull="+notnull+",showinform="+showinform+",showinlist="+showinlist+",des='"+des+"',orderby="+orderBy+",update_date='"+date+"',primaryKey="+prikey+",increment="+zizeng+" ,optvalue='"+opValue+"',name_cn='"+nameCN+"' where id="+Integer.parseInt(columnId);
				    this.executeSQL(sql);
				    //修改列
				    String result=TableUtil.getStrResult(name, Long.parseLong(arr[2]), len, defaults, notnull, des, prikey, zizeng);
				    if(prikey==1){
				    	result=result.replace("primary key", "");
				    }
				    String editColumn="alter table "+tables.getName()+" change "+oldName+" "+result;
				    this.executeSQL(editColumn);
				}
			}
		}
	}

	@Override
	public void delCol(Long tableId, Long colId,String colName) throws BusinessException {
		Tables tables=(Tables) this.get(Tables.class, tableId);
		String countSql="select count("+colName+") from "+tables.getName();
		/*int count=Integer.parseInt(this.findBySQL(countSql).get(0).toString());
		if(count>0){
			throw new BusinessException("该列已存在数据，不能删除");
		}*/
		TableColumns tableColumns=(TableColumns) this.get(TableColumns.class, colId);
		this.delete(tableColumns);
		this.executeSQL("alter table "+tables.getName()+" drop "+colName);
	}

	@Override
	public int checkIfExist(Long tableId) {
		Tables tables=(Tables) this.get(Tables.class, tableId);
		String sql="SELECT COUNT(*) FROM information_schema.TABLES WHERE TABLE_NAME='"+tables.getName()+"'";
		int count=Integer.parseInt(this.findBySQL(sql).get(0).toString());
		return count;
	}

	@Override
	public List getTableListByTabId(Long tableId,int flag) {
		String hql="";
		if(flag==1){
			/*hql="from Tables where id!="+tableId;*/
			hql="from Tables where 1=1";
		}else if(flag==2){
			hql="from TableColumns where tableId="+tableId;
		}
		List list=this.find(hql);
		return list;
	}
	
	public Map getTableColumnListMap(){
		Map map=new HashMap();
		
		String hql="from TableColumns where tableId in (select DISTINCT s.targetTableId from TableRelationship s)";
		List list=this.getList(hql, null);
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				TableColumns col=(TableColumns) list.get(i);
				List colList=(List) map.get(col.getTableId());
				if(colList == null){
					colList=new ArrayList();
					map.put(col.getTableId(), colList);
				}
				colList.add(col);
			}
		}
		return map;
	}

	@Override
	public Long saveRelation(TableRelationship ship) throws BusinessException{
		String hql_checkName="from TableRelationship where name='"+ship.getName()+"'";
		List list=this.find(hql_checkName);
		if(list.size()>0){
			throw new BusinessException("该外键名称已存在");
		}
		ship.setAddDate(new Date());
		this.save(ship);
		Tables tables_source=(Tables) this.get(Tables.class,ship.getSourceTableId());
		Tables tables_target=(Tables) this.get(Tables.class,ship.getTargetTableId());
		TableColumns col_source=(TableColumns) this.get(TableColumns.class, ship.getSourceColumnId());
		TableColumns col_target=(TableColumns) this.get(TableColumns.class, ship.getTargetColumnId());
		//添加数据库约束//V2:
		/*String sql="alter table "+tables_source.getName()+" add constraint "+ship.getName()+" foreign key("+col_source.getName()+") REFERENCES "+tables_target.getName()+"("+col_target.getName()+") ON DELETE CASCADE ON UPDATE CASCADE";*/
		/*this.executeSQL(sql);*/
		
		//如果是多对多关系，则创建第三张关系表
		String tableName=tables_source.getName()+"_"+tables_target.getName()+"_map";
		if(ship.getType()==3){
			this.executeSQL("DROP TABLE IF EXISTS "+tableName);
			StringBuilder sb=new StringBuilder("create table "+tableName+"(");
			sb.append("id bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键id',");
			sb.append("source_table_id bigint(12) NOT NULL COMMENT '外键表id',");
			sb.append("source_column_id bigint(12) NOT NULL COMMENT '外键字段id',");
			sb.append("target_table_id bigint(12) NOT NULL COMMENT '主键表id',");
			sb.append("target_column_id bigint(12) NOT NULL COMMENT '主键字段id',");
			sb.append("PRIMARY KEY (`id`)");
			sb.append(")ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");
			Connection conn=null;
			try {
				conn = (Connection) SpringUtil.getConnectionFromSpring("dataSource");
				Statement statement=conn.createStatement();
				statement.executeUpdate(sb.toString());
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ship.getId();
	}

	@Override
	public List listRelation(Long tableId) {
		StringBuffer sql=new StringBuffer();
		sql.append("select re.id,re.name,");
		sql.append("re.source_table_id,(select tab.name from sys_tables tab where tab.id=re.source_table_id),");
		sql.append("re.source_column_id,(select col.name from sys_table_columns col where col.id=re.source_column_id),");
		sql.append("re.target_table_id,(select tab.name from sys_tables tab where tab.id=re.target_table_id),");
		sql.append("re.target_column_id,(select col.name from sys_table_columns col where col.id=re.target_column_id),re.target_show_id,");
		sql.append("re.add_date,re.update_date,re.type,re.fs from sys_table_relationship re where re.source_table_id="+tableId);
		return this.findBySQL(sql.toString());
	}

	@Override
	public void editRelation(String paramStr) throws BusinessException{
		//relation_id+"#"+fk_name+"#"+fk_tabName+"#"+fk_id+"#"+pk_tabId+"#"+pk_colId
		String[] pstr=paramStr.split(",");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(new Date());
		if(pstr!=null && pstr.length>0){
			for(int i=0;i<pstr.length;i++){
				String[] parArr=pstr[i].split("#");
				if(parArr!=null && parArr.length>0){
					long relation_id=Long.parseLong(parArr[0]);
					String fk_name=parArr[1];
					String fk_tabName=parArr[2];
					long fk_colId=Long.parseLong(parArr[3]);
					long pk_tabId=Long.parseLong(parArr[4]);
					Tables tab=(Tables) this.get(Tables.class, pk_tabId);
					long pk_colId=Long.parseLong(parArr[5]);
					String fk_name_old=parArr[6];
					long show_colId=Long.parseLong(parArr[7]);
					short relationType=Short.parseShort(parArr[8]);
					short fs=Short.parseShort(parArr[9]);
					String sql="update sys_table_relationship set name='"+fk_name+"',source_column_id="+fk_colId+",target_table_id="+pk_tabId+",target_column_id="+pk_colId+",target_show_id="+show_colId+",update_date='"+date+"',type="+relationType+",fs="+fs+" where id="+relation_id+"";
				    this.executeSQL(sql);
				    //修改主外键时，只能先删除原来的，再新增现在的
				    //V2:
				    /*String alterSql_del="ALTER TABLE "+fk_tabName+" DROP FOREIGN KEY "+fk_name_old+"";
				    this.executeSQL(alterSql_del);*/
				    TableColumns col_fk=(TableColumns) this.get(TableColumns.class, fk_colId);
				    TableColumns col_pk=(TableColumns) this.get(TableColumns.class, pk_colId);
				    String alterSql_add="alter table "+fk_tabName+" add constraint "+fk_name+" foreign key("+col_fk.getName()+") REFERENCES "+tab.getName()+"("+col_pk.getName()+") ON DELETE CASCADE ON UPDATE CASCADE";
				    this.executeSQL(alterSql_add);
				}
			}
		}
	}
	
	public void delRelation(Long tableId,Long relationId){
		Tables t=(Tables) this.get(Tables.class, tableId);
		TableRelationship ship=(TableRelationship) this.get(TableRelationship.class, relationId);
		
		//先删除原来的外键
	   /* String alterSql_del="ALTER TABLE "+t.getName()+" DROP FOREIGN KEY "+ship.getName()+"";
	    this.executeSQL(alterSql_del);*/
		
	    //先删除关系记录
	    this.delete(ship);
	}

	public void delTableById(Long tableId) {
		Tables tab=(Tables) this.get(Tables.class, tableId);
		this.executeSQL("DROP TABLE IF EXISTS "+tab.getName());
		
		if(tab.getMenuId()!=null){
			Menu menu = this.getMenuById(tab.getMenuId());
			this.delete(menu);
		}
		this.executeSQL("delete from sys_table_columns where table_id="+tableId);
		this.executeSQL("delete from sys_table_relationship where source_table_id="+tableId+" or target_table_id="+tableId);
		this.executeSQL("delete from sys_view where table_id="+tableId+" or target_table_id="+tableId);
		this.executeSQL("delete from sys_view_list where table_id="+tableId+" or target_table_id="+tableId);
		this.executeSQL("delete from sys_view_order where table_id="+tableId+" or target_table_id="+tableId);
		this.executeSQL("delete from sys_view_search where table_id="+tableId+" or target_table_id="+tableId);
		this.executeSQL("delete from sys_tables where id="+tableId);
	}
	
	public Menu getMenuById(Long id){
		return (Menu) this.get(Menu.class, id);
	}
	
	public void delMenu(Long tableId) throws BusinessException {
		Tables table=(Tables) this.get(Tables.class, tableId);
		if(CommonFunction.isNull(table.getMenuId())){
			throw new BusinessException("数据异常");
		}
		Map params=new HashMap();
		params.put("menuId", table.getMenuId());
		//删除权限
		this.execute("delete from Power where menuId=:menuId", params);
		Menu m=(Menu) this.get(Menu.class, table.getMenuId());
		//删除菜单
		this.delete(m);
		//table情况menuId
		table.setMenuId(null);
	}
	
	public List getFirstLevelMenu() {
		String hql="from Menu where level=1 and state=1 order by orderby";
		List list=this.getList(hql, null);
		return list;
	}

	@Override
	public int checkReTableNum(Long tableId) {
		String sql="select count(*) from sys_table_relationship where target_table_id="+tableId;
		return Integer.parseInt(this.findBySQL(sql).get(0).toString());
	}

	@Override
	public List reTableInfo(Long tableId) {
		String sql="select tab.id,tab.name,tab.name_cn from sys_tables tab where tab.id in(select source_table_id from sys_table_relationship where target_table_id="+tableId+")";
		return this.findBySQL(sql);
	}
	
	
	public static String txt2String(File file){
		         String result = "";
		         try{
		             BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
		             String s = null;
		             while((s = br.readLine())!=null){//使用readLine方法，一次读一行
		                 result = result + "\n" +s;
		                 System.out.println(result);
		             }
		             br.close();    
		         }catch(Exception e){
		             e.printStackTrace();
		         }
		         return result;
		     }
	
	public static void main(String[] args) throws Exception {
		    String s=txt2String(new File("c://data.sql"));
			Connection conn=(Connection) SpringUtil.getConnectionFromSpring("dataSource");
			Statement statement=conn.createStatement();
			String[] ss=s.split(";");
			for(int i=0;i<ss.length;i++){
				statement.executeUpdate(ss[i]);
			}
			System.out.println("写入完毕");
		
		
	}

	@Override
	public List getShowColList(Long tabledId) {
		String hql="from TableColumns where showinlist=1 and tableId="+tabledId;
		List list=this.find(hql);
		return list;
	}
	
	@Override
	public List getFormColList(Long tabledId) {
		String hql="from TableColumns where showinform=1 and tableId="+tabledId;
		List list=this.find(hql);
		return list;
	}

	@Override
	public List listGetTableData(Long tabledId) {
		Tables table=(Tables) this.get(Tables.class, tabledId);
		String hql="from TableColumns where showinlist=1 and tableId="+tabledId;
		List<TableColumns> list=this.find(hql);
		String colName="";
		if(CommonFunction.isNotNull(list)){
			for(int i=0;i<list.size();i++){
				TableColumns col=list.get(i);
				colName+=","+col.getName();
			}
			colName=colName.substring(1);
			String sql="select "+colName+" from "+table.getName();
			List listData=this.findBySQL(sql);
			return listData;
		}
		return null;
	}
	
	@Override
	public List listGetTableData(Long tabledId,Long pkId,String pkValue) {
		String pkName = null;
		String temp = null;
		Tables table=(Tables) this.get(Tables.class, tabledId);
		String hql="from TableColumns where tableId="+tabledId;
		List<TableColumns> list=this.find(hql);
		String colName="";
		String imgName="";
		if(CommonFunction.isNotNull(list)){
			for(int i=0;i<list.size();i++){
				TableColumns col=list.get(i);
				if(col.getShowinlist() == 1){
					if(col.getDataType() == 10){
						imgName += ","+ col.getName();
					}else{
						colName+=","+col.getName();
					}
				}
				if(col.getId().equals(pkId)){
					pkName = col.getName();
				}
				if(col.getPrimaryKey() == 1){
					temp = col.getName() + " as id_" + MD5.MD5(colName);
				}
			}
			colName = temp+imgName+colName;
			String sql="select "+colName+" from "+table.getName() + " where " + pkName + "='" + pkValue + "'" ;
			System.out.println(sql);
			List<Object[]> listData=this.findBySQL(sql);
			
			if(CommonFunction.isNotNull(imgName)){
				for(int i =0;i<listData.size();i++){
					String path = listData.get(i)[1].toString();
					listData.get(i)[1] = path.substring(0, path.lastIndexOf("/")+1) + "s_"+path.substring(path.lastIndexOf("/")+1);
				}
			}
			return listData;
		}
		return null;
	}

	@Override
	public String getShowColumnIdByshipIdAndPkValue(Long shipId, String foreignKey) {
		TableRelationship ship = (TableRelationship) this.get(TableRelationship.class, shipId);
		Tables foreignTable = (Tables) this.get(Tables.class,ship.getTargetTableId());
		TableColumns foreignColumn =(TableColumns)this.get(TableColumns.class,ship.getTargetColumnId());
		TableColumns foreignShowColumn = (TableColumns)this.get(TableColumns.class,ship.getTargetShowColumnId());
		StringBuilder sql = new StringBuilder();
		sql.append(" select ").append(foreignShowColumn.getName())
			.append(" from ").append(foreignTable.getName())
			.append(" where ")
			.append(foreignColumn.getName()).append("=:id");
		Map<String,Object> map =new HashMap();
		map.put("id", foreignKey);
		Object foreignShowValue = this.getUniqueResultSql(sql.toString(), map);
		return foreignShowValue!=null?foreignShowValue.toString():null;
	}
}
