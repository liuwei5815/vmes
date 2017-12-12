package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;

public interface PlaytourismService extends IDAO{
	public QueryResult loadTableData(Long tableId,int rows,int page, Map<String,Object> map, 
			List<Map<String,String>> orderList,List<String> columns);
	
	public QueryResult loadTableData(String tableName, int rows,int page,Map<String, Object> map,
			List<Map<String,String>> orderList,List<String> columns);
	
	public Map loadTableDataMap(Long tableId, Map<String,Object> map, 
			List<Map<String,String>> orderList,List<String> columns,String viewsColName);
	
	public Map loadTableDataMap(String tableName, Map<String,Object> map, 
			List<Map<String,String>> orderList,List<String> columns,String viewsColName);
	
	public Map loadTableDataMap(String tableName, Map<String,Object> map, Map<String,Object> map2, 
			List<Map<String,String>> orderList,List<String> columns,String viewsColName);
	
	

}
