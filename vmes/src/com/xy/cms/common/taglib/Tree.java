package com.xy.cms.common.taglib;


public class Tree implements java.io.Serializable {
	private String id;                //当前节点的ID
	private String parentId;          //当前节点的父ID
	private String text;              //节点的文本
	private String action;            //点击节点执行的动作
	private String icon;              //节点图片的路径
	private String openIcon;          //打开节点图标路径
	private String src;               //动态加载子节点的请求路径
	private boolean isHaveChild;      //当前节点是否存在子节点
	private int nodeType;                 //当前节点的类型，和配置文件中的索引位置对应
	private String value;				//CheckboxTree和RadioBox树选中节点的值
	private String target;				//action对应的target
	private int level;
	private boolean isChecked;
	private boolean isDisabled;
	private String color;				//节点文本颜色
	private String pColor;				//父节点文本颜色
	
	
	
	
	
	


	public String getPColor() {
		return pColor;
	}

	public void setPColor(String color) {
		pColor = color;
	}

	public Tree() {
		
	}
	
	public Tree(String id, String parentId, String text) {
		this.id = id;
		this.parentId = parentId;
		this.text = text;
	}
	
	
	
	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getOpenIcon() {
		return openIcon;
	}
	public void setOpenIcon(String openIcon) {
		this.openIcon = openIcon;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public boolean isHaveChild() {
		return isHaveChild;
	}
	public void setHaveChild(boolean isHaveChild) {
		this.isHaveChild = isHaveChild;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getNodeType() {
		return nodeType;
	}
	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}

