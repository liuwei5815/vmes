package com.xy.cms.service.imp;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.entity.Tables;
import com.xy.cms.service.PlaytourismService;

public class PlaytourismServiceImpl extends BaseDAO implements PlaytourismService{

	public QueryResult loadTableData(Long tableId, int rows,int page, Map<String, Object> map,
			List<Map<String, String>> orderList, List<String> columns) {
		Tables table = (Tables)this.get(Tables.class, tableId);
		return this.loadTableData(table.getName(),rows,page,map,orderList,columns);
	}

	public QueryResult loadTableData(String tableName, int rows,int page,Map<String, Object> map,
			List<Map<String,String>> orderList,List<String> columns) {
		return this.getListBySqlTableData(tableName,rows,page,map,orderList,columns);
	}
	
 
	public Map loadTableDataMap(Long tableId,Map<String, Object> map, List<Map<String, String>> orderList,
			List<String> columns,String viewsColName ) {
		Tables table = (Tables)this.get(Tables.class, tableId);
		return this.loadTableDataMap(table.getName(), map, orderList, columns,viewsColName);
	}
    
	public Map loadTableDataMap(String tableName,Map<String, Object> map, List<Map<String, String>> orderList,
			List<String> columns,String viewsColName) {
		return this.getMapBySqlTableData(tableName, map, orderList, columns,viewsColName);
	}
	
	public Map loadTableDataMap(String tableName,Map<String, Object> map, Map<String, Object> map2,List<Map<String, String>> orderList,
			List<String> columns,String viewsColName) {
		return this.getMapBySqlTableData(tableName, map, map2,orderList, columns,viewsColName);
	}
	
	
}
