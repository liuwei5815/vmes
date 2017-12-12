package com.xy.cms.entity;

import java.io.Serializable;


public class TableColumnType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String name;
	public String type;
	public Long code;
	
	public TableColumnType() {
		super();
	}
 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}
    
}
