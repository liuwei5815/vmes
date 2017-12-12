package com.xy.cms.service.imp;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Tables;
import com.xy.cms.service.IndexService;

public class IndexServiceImpl extends BaseDAO implements IndexService {
	 
	public QueryResult loadTableData(Long tableId, int rows, int page, Map<String, Object> map) {
		return loadTableData(tableId, rows, page, map,null,null);
	}

	public QueryResult loadTableData(String tableName, int rows, int page, Map<String, Object> map) {
		return loadTableData(tableName, rows, page, map,null,null);
	}
	
	public QueryResult loadTableData(String tableName, int rows,int page,Map<String, Object> map,
			List<Map<String,String>> orderList,List<String> columns) {
		return this.getListBySqlTableData(tableName,rows,page,map,orderList,columns);
	}
	
	public QueryResult loadTableData(Long tableId, int rows, int page,
			Map<String, Object> map, List<Map<String, String>> orderList,
			List<String> columns) {
		Tables table = (Tables)this.get(Tables.class, tableId);
		return loadTableData(table.getName(), rows, page, map,orderList,columns);
	}
	
	public Map loadTableData(String tableName, int rows,String primaryKeyField, String parentIdField,
			List<String> columns) {
		return this.getListBySqlTableData(tableName,rows,primaryKeyField,parentIdField,columns);
	}
	
}
