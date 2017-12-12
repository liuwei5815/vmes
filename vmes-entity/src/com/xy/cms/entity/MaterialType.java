package com.xy.cms.entity;
/**
 * 物料类型
 * @author Administrator
 *
 */
public class MaterialType {
	private Long id;//序号
	private String name;//物料类型名称
	private String model;//物料型号
	private String img;//图片
	private Integer pid;//上级id
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	
}
