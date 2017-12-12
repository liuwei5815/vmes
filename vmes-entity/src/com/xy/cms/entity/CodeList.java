package com.xy.cms.entity;

/**
 * 
 * @author 武汉夏宇信息
 *
 */
public class CodeList implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String typeId;
	private String contents;

	/** default constructor */
	public CodeList() {
	}

	/** full constructor */
	public CodeList(String code, String typeId, String contents) {
		this.code = code;
		this.typeId = typeId;
		this.contents = contents;
	}

	// Property accessors

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CodeList))
			return false;
		CodeList castOther = (CodeList) other;

		return ((this.getCode() == castOther.getCode()) || (this.getCode() != null
				&& castOther.getCode() != null && this.getCode().equals(
				castOther.getCode())))
				&& ((this.getTypeId() == castOther.getTypeId()) || (this
						.getTypeId() != null && castOther.getTypeId() != null && this
						.getTypeId().equals(castOther.getTypeId())))
				&& ((this.getContents() == castOther.getContents()) || (this
						.getContents() != null
						&& castOther.getContents() != null && this
						.getContents().equals(castOther.getContents())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCode() == null ? 0 : this.getCode().hashCode());
		result = 37 * result
				+ (getTypeId() == null ? 0 : this.getTypeId().hashCode());
		result = 37 * result
				+ (getContents() == null ? 0 : this.getContents().hashCode());
		return result;
	}

}