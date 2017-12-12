package com.xy.cms.entity;

import java.io.Serializable;
import java.util.Date;

public class Api implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	private String action;
	private String api;
	private Tables mainTable;
	private Date addDate;
	private Date updateDate;
	private Long createby;
	private String apiSql;
	private String des;
	
	private ApiAuth apiAuth;

	public Api(){}
	public Api(Long id){
		this.id=id;
	}
	
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
	
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public Tables getMainTable() {
		return mainTable;
	}
	public void setMainTable(Tables mainTable) {
		this.mainTable = mainTable;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Long getCreateby() {
		return createby;
	}
	public void setCreateby(Long createby) {
		this.createby = createby;
	}
	
	public ApiAuth getApiAuth() {
		return apiAuth;
	}
	public void setApiAuth(ApiAuth apiAuth) {
		this.apiAuth = apiAuth;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getApiSql() {
		return apiSql;
	}
	public void setApiSql(String apiSql) {
		this.apiSql = apiSql;
	}

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	
	


}
