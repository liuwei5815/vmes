package com.xy.cms.entity;

/**
 * 
 * @author 武汉夏宇信息
 *
 */
public class Config implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String confname;
	private String confvalue;
	private String des;

	/** default constructor */
	public Config() {
	}

	/** minimal constructor */
	public Config(String confname, String confvalue) {
		this.confname = confname;
		this.confvalue = confvalue;
	}

	/** full constructor */
	public Config(String confname, String confvalue, String des) {
		this.confname = confname;
		this.confvalue = confvalue;
		this.des = des;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConfname() {
		return this.confname;
	}

	public void setConfname(String confname) {
		this.confname = confname;
	}

	public String getConfvalue() {
		return this.confvalue;
	}

	public void setConfvalue(String confvalue) {
		this.confvalue = confvalue;
	}

	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}