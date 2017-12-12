package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.view.TreeView;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.Menu;
import com.xy.cms.entity.MenuConfig;
import com.xy.cms.entity.TableColumnType;
import com.xy.cms.entity.TableColumns;
import com.xy.cms.entity.TableRelationship;
import com.xy.cms.entity.Tables;
import com.xy.cms.entity.View;
import com.xy.cms.entity.ViewSearch;

public interface ModelService {
	 	public QueryResult query(Map pageMap,List<TableColumns> columnsList) throws BusinessException;
	 	
	 	public QueryResult queryView(Map pageMap,List viewBeanList)
				throws BusinessException;
	 	/**
	 	 * 加载表的字段  paramMap 参数
	 	 * 
	 	 * @param tableId
	 	 * @param paramMap key值:showinform、showinlist  primaryKey primaryKey特殊是or条件(单独存在没有任何意义)
	 	 * @return
	 	 */
	 	public List<TableColumns> loadTableColumn(Long tableId,Map paramMap);
	 	/**
	 	 * 查看相应视图加载表的字段
	 	 * @param viewId
	 	 * @return
	 	 */
	 	public List<TableColumns> loadViewTableColumn(Long viewId);
	 	/**
	 	 * 查询视图动态搜索条件
	 	 * @param viewId
	 	 * @return
	 	 */
	 	public List loadViewSearch(Long viewId);
	 	
	 	/**
	 	 * 查询视图动态搜索条件(改)
	 	 * @param viewId
	 	 * @return
	 	 */
	 	public List loadViewSearchNew(Long viewId);
	 	
	 	public Tables getTablesById(Long tableId);
	 	
	 	public List<TableColumnType> getTableColumnTypes();

	 	/**
	 	 * 保存表格数据
	 	 * @param tableId
	 	 * @param colunmnMap 
	 	 */
	 	public void saveTableDate(Long tableId,Map<String,String> colunmnMap,String adminId,String param) throws BusinessException;

	 	/**
	 	 * 删除表数据
	 	 * @param id
	 	 * @throws BusinessException
	 	 */
	 	public void del(Long tableId,Long id) throws BusinessException;
	 	
	 	/**
	 	 * 根据tableId查询其对应的主键id
	 	 * @param tableId
	 	 * @throws BusinessException
	 	 */
	 	public String loadTablePkByTableId(Long tableId) throws BusinessException;
	 	
	 	
	 	/**
	 	 * 根据tableId和其主键值来查询单条数据
	 	 * @param tableId
	 	 * @throws BusinessException
	 	 */
	 	public Map  loadTableDataByTableIdAndPk(Long tableId,String pkValue,List<TableColumns> colunmsList) throws BusinessException;
	 	
	 	
	 	/**
	 	 * 修改表单数据
	 	 */
	 	public void updateTableDataByParam(Long tableId,Map<String,String> colunmnMap,String adminId,String pkValue) throws BusinessException;
	 	
	 	public Menu getMenuById(Long id);
	 	/**
	 	 * 查询table的view
	 	 * @param tableId
	 	 * @return
	 	 */
	 	public List getTableViewNum(Long tableId);
	 	
	 	public List getViewSearch(Long viewId);
	 	
	 	public View getViewById(Long viewId);
	 	
	 	public TableColumns getTableColumnsById(Long colId);
	 	
	 	/**
	 	 * 分页查询主外键关系表中的主键信息
	 	 * @param pageMap 查询参数
	 	 * @param tableName 表名
	 	 * @param pkName 主键字段名
	 	 * @param showName 显示字段名
	 	 * @return
	 	 */
	 	public QueryResult queryShip(Map pageMap,String tableName,String pkName,String showName);
	 	
	 	public TableRelationship queryTableRelationship(Long tableId,Long columnId);

	 	/**
	 	 * 根据部门id查询所有的员工
	 	 * @param DepId 组织机构id
	 	 * @return
	 	 * */
	 	public QueryResult queryEmployeeByDepId(Map pageMap,Long[] ids)throws BusinessException;

	 	/**
	 	 * 查询所有的员工
	 	 * */
	 	public QueryResult queryEmployee(Map pageMap)throws BusinessException;

	 	
	 	/***
	 	 * 
	 	 */
	 	public List<TreeView> getTreeList(TableRelationship treeShip);

	 	/**
		 * 获取菜单弹框的配置
		 */
		public MenuConfig getMenuConfigByMenuId(Long menuId);
		/**
		 * queryids
		 */
		public List queryids(Long deptId);
		/**
		 * 根据id查询业务对象数据
		 * @param id
		 * @return
		 */
		public Map<String,Object> getTableDataById(Long tableId,Object pkValue);

}
