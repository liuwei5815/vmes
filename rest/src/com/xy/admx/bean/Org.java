package com.xy.admx.bean;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

public class Org {
	private Integer id;
	private String name;
	private  Integer parentid;
	private Integer order;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public static void main(String[] args) {

		System.out.println(StringUtils.substringBefore("23123132121", "1"));
		
	}
	
}
