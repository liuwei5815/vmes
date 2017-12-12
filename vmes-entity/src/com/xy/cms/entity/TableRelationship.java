package com.xy.cms.entity;

import java.util.Date;

/**
 * @author 武汉夏宇信息
 */
public class TableRelationship implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int SELECT_PK=1;//在从表中选择主表
	
	public static final int ADD_FK=2;//在主表中添加从表
	
	private Long id;
 
	private String name;
	
	private Long sourceTableId;
	
	private Long sourceColumnId;
	
	private Long targetTableId;
	
	private Long targetColumnId;
	
	private Long targetShowColumnId;
	
	private Date addDate;
	
	private Date updateDate;
	
	private Short type;
	
	private Short fs;

	public TableRelationship(){}

	public TableRelationship(Long id, String name, Long sourceTableId,
			Long sourceColumnId, Long targetTableId, Long targetColumnId,
			Date addDate, Date updateDate) {
		super();
		this.id = id;
		this.name = name;
		this.sourceTableId = sourceTableId;
		this.sourceColumnId = sourceColumnId;
		this.targetTableId = targetTableId;
		this.targetColumnId = targetColumnId;
		this.addDate = addDate;
		this.updateDate = updateDate;
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

	public Long getTargetShowColumnId() {
		return targetShowColumnId;
	}

	public void setTargetShowColumnId(Long targetShowColumnId) {
		this.targetShowColumnId = targetShowColumnId;
	}

	public Long getSourceTableId() {
		return sourceTableId;
	}

	public void setSourceTableId(Long sourceTableId) {
		this.sourceTableId = sourceTableId;
	}

	public Long getSourceColumnId() {
		return sourceColumnId;
	}

	public void setSourceColumnId(Long sourceColumnId) {
		this.sourceColumnId = sourceColumnId;
	}

	public Long getTargetTableId() {
		return targetTableId;
	}

	public void setTargetTableId(Long targetTableId) {
		this.targetTableId = targetTableId;
	}

	public Long getTargetColumnId() {
		return targetColumnId;
	}

	public void setTargetColumnId(Long targetColumnId) {
		this.targetColumnId = targetColumnId;
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

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Short getFs() {
		return fs;
	}

	public void setFs(Short fs) {
		this.fs = fs;
	}
	
	
}
