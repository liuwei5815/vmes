package com.xy.cms.bean;

import java.util.List;

import com.xy.cms.entity.TableColumns;
import com.xy.cms.entity.Tables;

public class TableBean {
	private Long pkValue;
	private Tables table;
	private List<TableColumns> list;
    private List listData;
    private short type;//对应关系  一对多、一对一
    private List listFormCol;
	public Tables getTable() {
		return table;
	}

	public void setTable(Tables table) {
		this.table = table;
	}

	public List<TableColumns> getList() {
		return list;
	}

	public void setList(List<TableColumns> list) {
		this.list = list;
	}

	public List getListData() {
		return listData;
	}

	public void setListData(List listData) {
		this.listData = listData;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public List getListFormCol() {
		return listFormCol;
	}

	public void setListFormCol(List listFormCol) {
		this.listFormCol = listFormCol;
	}

	public Long getPkValue() {
		return pkValue;
	}

	public void setPkValue(Long pkValue) {
		this.pkValue = pkValue;
	}
    
}
