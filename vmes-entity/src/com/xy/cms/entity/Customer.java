package com.xy.cms.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer implements java.io.Serializable {

	/**
	 * 
	 */
	private static final Long serialVersionUID = 6268861942542577400L;

	/**
	 * 
	 */


	/**
	 * 主键id
	 * */
	private Long id;
	
	/**
	 * 客户编号
	 * */
	private String customerCode;

	/**
	 * 客户类型
	 */
    private String typeId;
    
	/**
	 * 部门id
	 */
    private Long departmentId;
    
    /**
     * 激活状态
     */
    private int activeState;
    
    /**
     * 状态
     */
    private String status;

	/**
	 * 客户名称
	 * */
	private String name;
	

	/**
	 * 客户电话
	 * */
	private String tel;

	private String contact;

	/**
	 * 状态
	 * */
	private Integer state;

	/**
	 * 添加时间
	 * */
	private Timestamp addDate;

	/**
	 *修改时间 
	 * */
	private Timestamp updateDate;


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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Timestamp getAddDate() {
		return addDate;
	}

	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	
    
    public void setTypeId(String typeId){
        this.typeId = typeId;
    }
    
    public String getTypeId(){
        return this.typeId;
    }
    
    public void setDepartmentId(Long departmentId){
        this.departmentId = departmentId;
    }
    
    public Long getDepartmentId(){
        return this.departmentId;
    }
    
    
    public void setActiveState(int activeState){
        this.activeState = activeState;
    }
    
    public int getActiveState(){
        return this.activeState;
    }
    


	
}
