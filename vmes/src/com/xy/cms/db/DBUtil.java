package com.xy.cms.db;

import java.util.List;

import com.xy.cms.db.bean.Column;
import com.xy.cms.db.bean.OrderBy;
import com.xy.cms.db.bean.Where;

public abstract class DBUtil {
	protected WhereUtil whereUtil;

	public abstract String createDelSql(String name, List<Where> wheres);

	public abstract String createInsertSql(String name, List<Column> columns);

	public abstract String createUpdateSql(String name, List<Column> columns,
			List<Where> wheres);

	public abstract String createSelectSql(String name, List<Column> columns,
			List<Where> wheres, List<OrderBy> orderbys);

	public WhereUtil getWhereUtil() {
		return whereUtil;
	}

	public void setWhereUtil(WhereUtil whereUtil) {
		this.whereUtil = whereUtil;
	}

}
