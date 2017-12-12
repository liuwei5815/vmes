package com.xy.cms.bean;

import java.util.List;

import com.xy.cms.entity.TableColumns;
import com.xy.cms.entity.TableRelationship;
import com.xy.cms.entity.Tables;

/**
 * 主表录入从表显示list辅助bean
 * 
 * @author xiaojun
 *
 */
public class AddFkViewBean {
	private TableRelationship ship;// 主外键对应关系

	private PkTableBean pkTable;

	public class PkTableBean extends Tables {
		private List<TableColumns> addColumns;// 添加列表显示字段
		private List<TableColumns> listColumns;// 查询列表显示字段

		public List<TableColumns> getAddColumns() {
			return addColumns;
		}

		public void setAddColumns(List<TableColumns> addColumns) {
			this.addColumns = addColumns;
		}

		public List<TableColumns> getListColumns() {
			return listColumns;
		}

		public void setListColumns(List<TableColumns> listColumns) {
			this.listColumns = listColumns;
		}

	}

	public TableRelationship getShip() {
		return ship;
	}

	public void setShip(TableRelationship ship) {
		this.ship = ship;
	}

	public PkTableBean getPkTable() {
		return pkTable;
	}

	public void setPkTable(PkTableBean pkTable) {
		this.pkTable = pkTable;
	}

}
