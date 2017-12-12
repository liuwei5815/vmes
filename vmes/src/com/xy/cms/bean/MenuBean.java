package com.xy.cms.bean;

import java.util.List;


/**
 * @author 武汉夏宇信息
 */
public class MenuBean implements java.io.Serializable{

	private Long id;
	private String name;
	private Long supiorId;
	private String url;
	private String icon;
    
    private List<MenuBean> childNode;

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

	public Long getSupiorId() {
		return supiorId;
	}

	public void setSupiorId(Long supiorId) {
		this.supiorId = supiorId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<MenuBean> getChildNode() {
		return childNode;
	}

	public void setChildNode(List<MenuBean> childNode) {
		this.childNode = childNode;
	}
	
}
