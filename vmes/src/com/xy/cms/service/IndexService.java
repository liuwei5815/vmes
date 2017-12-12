package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;

public interface IndexService {

	  /**
	   * 根据不同表id 查询相应数据列表信息
	   * @param tableId 表id 
	   * @param rows 显示行数
	   * @param map 查询条件 
	   * @return
	   */
	QueryResult loadTableData(Long tableId,int rows,int page,Map<String,Object> map);
	  
	  /**
	   * 根据不同表名 查询相应数据列表信息
	   * @param tableName 表名 
	   * @param rows 显示行数
	   * @param map 查询条件 
	   * @return
	   */
	QueryResult loadTableData(String tableName,int rows,int page,Map<String,Object> map);
	 
	  /**
	   * 根据不同表id 查询相应数据列表信息
	   * @param tableId 表id 
	   * @param rows 显示行数
	   * @param map 查询条件
	   * @param orderList 排序字段集合
	   * @return
	   */
	  //List loadTableData(Long tableId,int rows,Map<String,Object> map,List<Map<String,String>> orderList,List<String> columns);
	  /**
	   * 
	   * @param tableId 表id 
	   * @param rows 显示行数
	   * @param page 当前页数
	   * @param map 查询条件
	   * @param orderList 排序字段集合
	   * @param columns
	   * @return
	   */
	QueryResult loadTableData(Long tableId,int rows,int page,Map<String,Object> map,List<Map<String,String>> orderList,List<String> columns);
	  
	  /**
	   * 根据不同表名 查询相应数据列表信息
	   * @param tableName 表名 
	   * @param rows 显示行数
	   * @param map 查询条件
	   * @param orderList 排序字段集合
	   * @return
	   */
	QueryResult loadTableData(String tableName,int rows,int page,Map<String,Object> map, List<Map<String,String>> orderList,List<String> columns);
	/**
	 * 根据不同表名
	 * 根据表数据的子父级关系查询数据
	 * @param tableName 表名
	 * @param rows 显示行数
	 * @param parentIdField 父节点id字段
	 * @param columns 查询字段集合
	 * @return
	 */
	Map loadTableData(String tableName,int rows,String primaryKeyField,String parentIdField,List<String> columns);
	
}
