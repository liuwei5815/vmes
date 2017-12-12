package com.xy.admx.core.sql;

/**
 * 字段
 * 
 * @author dg
 *
 */
public class Column {

	// 字段名
	private String fildName;

	// 字段类型
	private String dataType;

	// 字段长度
	private Integer len;

	// 默认值
	private String defaultV;
	
	// 是否允许为空?
	public Boolean notNull;
	
	// 注释
	private String comment;
	
	
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getLen() {
		return len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public String getDefaultV() {
		return defaultV;
	}

	public String getFildName() {
		return fildName;
	}

	public void setFildName(String fildName) {
		this.fildName = fildName;
	}

	public void setDefaultV(String defaultV) {
		this.defaultV = defaultV;
	}

	public Boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(Boolean notNull) {
		this.notNull = notNull;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

//	public Integer getDataCode() {
//		return dataCode;
//	}
//
//	public void setDataCode(Integer dataCode) {
//		this.dataCode = dataCode;
//	}
}
