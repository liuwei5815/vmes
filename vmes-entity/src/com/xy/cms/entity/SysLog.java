package com.xy.cms.entity;

import java.util.Date;

/**
 * 
 * @author Administrator
 *
 */
public class SysLog implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 操作描述
	 */
	private String content;
	
	/**
	 * 用户ID，引用admin表主键
	 */
	private Long adminId;
	
	/**
	 * 操作人账号
	 */
	private String userAccount;
	
	/**
	 * 操作时间
	 */
	private Date time;
	
	/**
	 * 操作人IP
	 */
	private String ip;
	
	/**
	 * 请求URL
	 */
	private String url;
	
	

	public SysLog() {}

	public SysLog(Integer id, String content, Long adminId, String userAccount, Date time, String ip, String url,
			String username) {
		super();
		this.id = id;
		this.content = content;
		this.adminId = adminId;
		this.userAccount = userAccount;
		this.time = time;
		this.ip = ip;
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
