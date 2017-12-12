package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.entity.TableColumns;
import com.xy.cms.service.TableColumnsService;

public class TableColumnsServiceImpl extends BaseDAO implements TableColumnsService {

	@Override
	public TableColumns getTableColumnsById(Long id) {
		
		return (TableColumns) this.get(TableColumns.class, id);
	}

	@Override
	public List<TableColumns> getTableColumnsByTIdAndType(Long tableId, Long dataType) {
		String hql =" from TableColumns where tableId=:tableId and dataType=:dataType ";
		Map<String,Long> pageMap = new HashMap();
		pageMap.put("tableId", tableId);
		pageMap.put("dataType", dataType);
		return this.getList(hql, pageMap);
	}

	@Override
	public boolean existsAddDate(Long tableId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from TableColumns where tableId=:tableId and name=:name");
		Map<String,Object> pageMap =new HashMap<String, Object>();
		pageMap.put("tableId", tableId);
		pageMap.put("name",TableColumns.DefaultColumn.ADD_DATE.getName());
		return ((Number)this.getUniqueResult(hql.toString(), pageMap)).intValue()>0;
	}

	@Override
	public boolean existsUpdateDate(Long tableId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from TableColumns where tableId=:tableId and name=:name");
		Map<String,Object> pageMap =new HashMap<String, Object>();
		pageMap.put("tableId", tableId);
		pageMap.put("name",TableColumns.DefaultColumn.UPDATE_DATE.getName());
		return ((Number)this.getUniqueResult(hql.toString(), pageMap)).intValue()>0;
	}

	@Override
	public List<TableColumns> queryTableColumnsByTableId(Long tableId) {
		String hql =" from TableColumns where tableId=:tableId";
		Map<String,Long> pageMap = new HashMap();
		pageMap.put("tableId", tableId);

		return this.getList(hql, pageMap);

	}

	@Override
	public TableColumns gerPriayKeyColumnsByTableId(Long tableId) {
		String hql =" from TableColumns where tableId=:tableId and primaryKey=1 ";
		Map<String,Long> pageMap = new HashMap();
		pageMap.put("tableId", tableId);

		return (TableColumns) this.getFirstResult(hql, pageMap);
	}

}
