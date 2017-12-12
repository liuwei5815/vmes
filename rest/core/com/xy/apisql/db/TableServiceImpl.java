package com.xy.apisql.db;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xy.admx.core.service.base.BaseServiceImpl;
import com.xy.cms.entity.Tables;
@Service
public class TableServiceImpl extends BaseServiceImpl implements TablesService{

	@Override
	public Tables getTableByNameCn(String name) {
		String hql =" from Tables where  nameCn=:nameCn";
		Map<String,Object> pageMap =new HashMap<>();
		pageMap.put("nameCn",name) ;
		return (Tables) this.getFirstResult(hql, pageMap);
	}

	@Override
	public Tables getTable(Long id) {
		return this.get(Tables.class, id);
	}

}
