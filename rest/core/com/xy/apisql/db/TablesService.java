package com.xy.apisql.db;

import com.xy.cms.entity.Tables;

public interface TablesService {
	Tables getTableByNameCn(String name);
	
	
	Tables getTable(Long id);
}
