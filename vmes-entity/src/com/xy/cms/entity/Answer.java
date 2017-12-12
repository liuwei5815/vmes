package com.xy.cms.entity;

import java.util.Date;

public class Answer implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 问题ID
	 */
	private Long quesId;

	/**
	 * 回答内容
	 */
	private String replyContent;

	/**
	 * 回答者
	 */
	private String replyPerson;

	/**
	 * 回答时间
	 */
	private Date replyTime;

	public Answer() {
		super();
	}

	public Answer(Long id, Long quesId, String replyContent, String replyPerson, Date replyTime) {
		super();
		this.id = id;
		this.quesId = quesId;
		this.replyContent = replyContent;
		this.replyPerson = replyPerson;
		this.replyTime = replyTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuesId() {
		return quesId;
	}

	public void setQuesId(Long quesId) {
		this.quesId = quesId;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyPerson() {
		return replyPerson;
	}

	public void setReplyPerson(String replyPerson) {
		this.replyPerson = replyPerson;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", quesId=" + quesId + ", replyContent=" + replyContent + ", replyPerson="
				+ replyPerson + ", replyTime=" + replyTime + "]";
	}
	
	
	

}
