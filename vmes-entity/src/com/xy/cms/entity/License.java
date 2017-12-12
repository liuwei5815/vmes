package com.xy.cms.entity;

import java.util.Date;

/**
 * @author 武汉夏宇信息
 */
public class License implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	private Long id;
	
	/**
	 * 日期 
	 */
	private Date expiredDate;
	/**
	 * 账号数量
	 */
	private String viableAcct;
	/**
	 * 控制范围
	 */
	private String acctCtrl;
	
	/**
	 * Web端账号数
	 * */
	private Integer webNum;
	/**
	 * 移动端账号数
	 * */
	private Integer appNum;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getViableAcct() {
		return viableAcct;
	}
	public void setViableAcct(String viableAcct) {
		this.viableAcct = viableAcct;
	}
	public String getAcctCtrl() {
		return acctCtrl;
	}
	public void setAcctCtrl(String acctCtrl) {
		this.acctCtrl = acctCtrl;
	}
	public Integer getWebNum() {
		return webNum;
	}
	public void setWebNum(Integer webNum) {
		this.webNum = webNum;
	}
	public Integer getAppNum() {
		return appNum;
	}
	public void setAppNum(Integer appNum) {
		this.appNum = appNum;
	}
	
	
}
