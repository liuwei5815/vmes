package com.xy.cms.entity;

public class ProductUint {
	/**
	 * 主键id
	 * */
	private Long id;
	
	/**
	 * 产品单位名称
	 * */
	private String name;
	/**
	 * 父级id
	 * @return
	 */
	private Long pid;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
	
}
