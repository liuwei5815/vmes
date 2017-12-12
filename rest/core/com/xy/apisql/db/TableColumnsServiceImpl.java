package com.xy.apisql.db;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xy.admx.core.helper.AppUserHelper;
import com.xy.admx.core.service.base.BaseServiceImpl;
import com.xy.cms.entity.TableColumns;
@Service
public class TableColumnsServiceImpl extends BaseServiceImpl implements TableColumnsService {
	@Override
	public TableColumns getTableColumnsById(Long id) {
		return this.get(TableColumns.class,id);
	}

	@Override
	public boolean existOpenId(Long tableId) {
		StringBuilder hql = new StringBuilder("select count(*) from TableColumns t where t.name=:name and t.tableId=:tableId");
		Map<String,Object> pageMap =new HashMap<>();
		pageMap.put("name",AppUserHelper.OPENID_COLUMN.getFildName());
		pageMap.put("tableId",tableId);
		return Long.parseLong(this.getUniqueResult(hql.toString(),pageMap).toString())>0;
	}

	@Override
	public TableColumns getOpenId(Long tableId) {
		StringBuilder hql = new StringBuilder(" from TableColumns t where t.name=:name and t.tableId=:tableId");
		Map<String,Object> pageMap =new HashMap<>();
		pageMap.put("name",AppUserHelper.OPENID_COLUMN.getFildName());
		pageMap.put("tableId",tableId);
		return (TableColumns) this.getFirstResult(hql.toString(),pageMap);
	}

	@Override
	public TableColumns getTableColumnsByTableAndNameCn(Long tableId, String nameCn) {
		StringBuilder hql = new StringBuilder("from TableColumns t where t.nameCn=:nameCn and t.tableId=:tableId");
		Map<String,Object> pageMap =new HashMap<>();
		pageMap.put("nameCn",nameCn);
		pageMap.put("tableId",tableId);
		return (TableColumns) this.getFirstResult(hql.toString(), pageMap);
	}
}
