package com.xy.apisql.db;

import com.xy.cms.entity.TableColumns;

public interface TableColumnsService {
	
	public TableColumns getTableColumnsById(Long id);

	public boolean existOpenId(Long tableId);
	
	public TableColumns getOpenId(Long tableId);

	public TableColumns getTableColumnsByTableAndNameCn(Long tableId, String nameCn);
}
