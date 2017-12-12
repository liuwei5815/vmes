package com.xy.cms.db;

import java.util.ArrayList;
import java.util.List;

import com.xy.cms.db.bean.Column;
import com.xy.cms.db.bean.OrderBy;
import com.xy.cms.db.bean.Where;

public class MySqlDbUtilImpl extends DBUtil {

	public String createDelSql(String name, List<Where> wheres) {
		if (name == null || name.trim().equals("")) {
			throw new IllegalArgumentException("name is null");
		}
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append(name);
		StringBuilder whereSql = new StringBuilder();
		if (wheres != null && wheres.size() >= 0) {
			whereSql.append(" where 1=1");
			for (Where w : wheres) {
				whereSql.append(" and ");
				whereSql.append(whereUtil.getWhere(w));
			}
		}
		sql.append(whereSql);
		return sql.toString();
	}

	public String createInsertSql(String name, List<Column> columns) {

		if (name == null || name.trim().equals("")) {
			throw new IllegalArgumentException("name is null");
		}
		if (columns == null || columns.size() == 0) {
			throw new IllegalArgumentException("columns is null or size==0");
		}
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ");
		sql.append(name);
		StringBuilder columnSql = new StringBuilder();
		StringBuilder valueSql = new StringBuilder();
		for (Column column : columns) {
			columnSql.append(column.getName());
			columnSql.append(",");
			valueSql.append(":").append(column.getName());
			valueSql.append(",");
		}
		sql.append("(").append(columnSql.substring(0, columnSql.length() - 1))
				.append(") ");
		sql.append("values(")
				.append(valueSql.substring(0, valueSql.length() - 1))
				.append(")");
		return sql.toString();
	}

	public String createUpdateSql(String name, List<Column> columns,
			List<Where> wheres) {
		if (name == null || name.trim().equals("")) {
			throw new IllegalArgumentException("name is null");
		}
		if (columns == null || columns.size() == 0) {
			throw new IllegalArgumentException("columns is null or size==0");
		}
		StringBuilder sql = new StringBuilder();
		sql.append("update ");
		sql.append(name);
		sql.append(" set ");
		StringBuilder columnSql = new StringBuilder();
		StringBuilder whereSql = new StringBuilder();
		for (Column column : columns) {
			columnSql.append(column.getName());
			columnSql.append(":").append(column.getName());
			columnSql.append(",");
		}

		if (wheres != null && wheres.size() >= 0) {
			whereSql.append(" where 1=1");
			for (Where w : wheres) {
				whereSql.append(" and ");
				whereSql.append(whereUtil.getWhere(w));

			}
		}
		sql.append(columnSql.subSequence(0, columnSql.length() - 1)).append(
				whereSql);
		return sql.toString();
	}

	public static void main(String[] args) {
		MySqlDbUtilImpl dbUtil = new MySqlDbUtilImpl();
		WhereUtil whereUtil = new WhereUtilImpl();
		dbUtil.setWhereUtil(whereUtil);
		List<Column> columns = new ArrayList<Column>();
		Column c = new Column("name");
		columns.add(c);
		List wheres = new ArrayList();
		Where w = new Where("name");
		wheres.add(w);
		System.out.println(dbUtil.createSelectSql("tableName", null, wheres,null));
		System.out.println(dbUtil.createInsertSql("tableName", columns));
		System.out.println(dbUtil.createUpdateSql("tableName", columns, wheres));
		System.out.println(dbUtil.createDelSql("tableName", wheres));
	}

	public String createSelectSql(String name, List<Column> columns,
			List<Where> wheres, List<OrderBy> orderbys) {
		if (name == null || name.trim().equals("")) {
			throw new IllegalArgumentException("name 不能为空");
		}
		StringBuilder sql = new StringBuilder();
		StringBuilder columnsSql = new StringBuilder();
		StringBuilder whereSql = new StringBuilder();
		StringBuilder orderSql = new StringBuilder();
		if (columns == null || columns.size() == 0)
			columnsSql.append(" * ");
		else {
			for (Column column : columns) {
				columnsSql.append(" ").append(column.getName()).append(" as ").append(column.getAlias()).append(",");
			}
			columnsSql = new StringBuilder(columnsSql.substring(0,columnsSql.length() - 1));
		}
		if (wheres != null && wheres.size() >= 0) {
			whereSql.append(" where 1=1");
			for (Where w : wheres) {
				whereSql.append(" and ");
				whereSql.append(whereUtil.getWhere(w));
			}
		}
		if (orderbys != null && orderbys.size() > 0) {
			orderSql.append(" order by");
			for (OrderBy w : orderbys) {
				orderSql.append(" ").append(w.getName());
				if (w.isASC()) {
					orderSql.append(" asc");
				} else {
					orderSql.append(" desc");
				}
				orderSql.append(",");
			}
			orderSql = new StringBuilder(orderSql.substring(0,orderSql.length() - 1));
		}
		sql.append("select ").append(columnsSql).append(" from ").append(name).append(whereSql).append(orderSql);
		return sql.toString();
	}

}
