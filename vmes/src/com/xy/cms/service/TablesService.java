package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.SessionBean;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Menu;
import com.xy.cms.entity.TableColumns;
import com.xy.cms.entity.TableRelationship;
import com.xy.cms.entity.Tables;

public interface TablesService {

	void addTables(Tables tables,String parStr) throws BusinessException;
	
	void editTables(Tables tables,String tabOldname) throws BusinessException;
	
	QueryResult queryTables(Map<String,Object> map) throws BusinessException;
	
	List showColumnByTable(String tableName);
	
	Tables getTableById(Long tableId);
	
	List getCoListById(Long tabledId);
	
	List getShowColList(Long tabledId);
	
    List getFormColList(Long tabledId);
	
	List listGetTableData(Long tabledId);
	
	public void create(String name,Long tableId,Long supiorId,SessionBean bean);
	
	void saveCol(Long tableId,String parStr)throws BusinessException;
	
	void delCol(Long tableId,Long colId,String colName)throws BusinessException;
	
	int checkIfExist(Long tableId);
	
	List getTableListByTabId(Long tableId,int flag);
	
	public Map getTableColumnListMap();
	
	Long saveRelation(TableRelationship ship)throws BusinessException;
	
	List listRelation(Long tableId);
	
	void editRelation(String paramStr)throws BusinessException;
	void delTableById(Long tableId);
	
	
	public void updateRelation(TableRelationship ship);
	
	public Menu getMenuById(Long id);
	
	public void delMenu(Long tableId) throws BusinessException;
	
	public List getFirstLevelMenu();
	
	public void delRelation(Long tableId,Long relationId);
	//查询该表有没有关联表
	public int checkReTableNum(Long tableId);
	//查询关联表信息
	public List reTableInfo(Long tableId);
	
	public List listGetTableData(Long tableId,Long pk,String pkValue);
	
	/**
	 * 根据主外键关系及主键值查找其外键的显示字段值
	 * @param shpId
	 * @param pkvalue
	 * @return
	 */
	public String getShowColumnIdByshipIdAndPkValue(Long shipId,String foreignKey);
}
