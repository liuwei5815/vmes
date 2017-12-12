package com.xy.cms.common.taglib;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class TreeTag extends TagSupport{

	private String key;     // 配置key
	private String rootName;
	private String list;
	private String type;
	private String imagePath;
	private String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public int doStartTag() throws JspTagException {
		return SKIP_BODY;
	}
		
	@Override
	public int doEndTag() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
			String contextPath = request.getContextPath();
			StringBuffer out = new StringBuffer();
			printTree(out, contextPath);
			pageContext.getOut().write(out.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	public void printTree(StringBuffer out, String contextPath) {
		Object k = this.pageContext.getRequest().getAttribute(this.list);
		List list = (List)this.pageContext.getRequest().getAttribute(this.list);
		
		if(list==null){
			return;
		} 
		
		out.append("<SCRIPT type=\"text/javascript\">\r\n");
		out.append("webFXTreeConfig.setImagePath(\"");
		out.append(contextPath);
		out.append(imagePath);
		out.append("\");\r\n");
		out.append("var ");
		out.append(key);
		out.append(" = new WebFXTree('");
		out.append(rootName);
		out.append("'");
		
		if(action!=null && action.trim().length()>0) {
			out.append(",\"");
			out.append(action);
			out.append("\"");
		}
		
		out.append(");\r\n");
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Tree node = (Tree) iterator.next();
			out.append("var ");
			out.append(key); 
			out.append(node.getId());
			//节点封装------------
			if("checkbox".equals(type)){
				out.append(" = new WebFXCheckBoxTreeItem(");
			} else {
				out.append(" = new WebFXTreeItem(");
			}
			
			if(node.getText() != null) {
				out.append("\"");
				out.append(node.getText());
				out.append("\"");
			} else {
				out.append("\"\"");
			}
			out.append(",");
			
			if("checkbox".equals(type)) {//如果是checkbox树则第二个参数是checkbox的值而不是action
				if(node.getValue() != null) {
					out.append("\"");
					out.append(node.getValue());
					out.append("\"");
				} else {
					out.append("\"\"");
				}
			}else {
				if(node.getAction() != null) {
					out.append("\"");
					out.append(node.getAction());
					out.append("\"");
				} else {
					out.append("\"\"");
				}
			}
			
			out.append(",");
			
			if(node.getParentId() != null) {
				out.append(key);
				out.append(node.getParentId());
			
			} else {
				out.append(key);
			}
			
			if(node.getIcon() != null) {
				out.append(",\"");
				out.append(node.getIcon());
				out.append("\"");
			}else {
				out.append(",\"\"");
			}
			
			if(node.getOpenIcon() != null) {
				out.append(",\"");
				out.append(node.getOpenIcon());
				out.append("\"");
			}else {
				out.append(",\"\"");
			}
			
			out.append(", ");
			out.append(node.isChecked());
			
			out.append(", ");
			out.append(node.isDisabled());
			
			out.append(");\r\n");
			
			out.append(key);
			out.append(node.getId());
			out.append(".setId(\"");
			out.append(node.getId());
			out.append("\");\r\n");
			//结束节点封装----------
			
			//设置节点文本颜色
			if(node.getColor() != null && !"".equals(node.getColor()) && !"null".equals(node.getColor())) {
				out.append(key + node.getId());
				out.append(".setColor(\"" + node.getColor() + "\");");
			}
			
			//设置父节点的文本颜色
			if(node.getPColor() != null && !"".equals(node.getPColor()) && !"null".equals(node.getPColor())) {
				out.append("try{");
				out.append(key + node.getId() + ".parentNode");
				out.append(".setColor(\"" + node.getPColor() + "\");");
				out.append("}catch(e){}");
				out.append("\r\n");
				
				out.append("try{");
				out.append(key + node.getId() + ".parentNode.parentNode");
				out.append(".setColor(\"" + node.getPColor() + "\");");
				out.append("}catch(e){}");
				out.append("\r\n");
				
				out.append("try{");
				out.append(key + node.getId() + ".parentNode.parentNode.parentNode");
				out.append(".setColor(\"" + node.getPColor() + "\");");
				out.append("}catch(e){}");
				out.append("\r\n");
			}
			
			if("checkbox".equals(type)) {//如果是checkbox要单独设置他的action
				if(node.getAction() != null && !"".equals(node.getAction().trim()) && !"null".equalsIgnoreCase(node.getAction())) {
					out.append(key + node.getId());
					out.append(".action=\"" + node.getAction() + "\";");
				}
			}
			//设置节点请求的target
			if(node.getTarget() != null && !"".equals(node.getTarget().trim()) && !"null".equalsIgnoreCase(node.getTarget())) {
				out.append(key + node.getId());
				out.append(".target=\"" + node.getTarget() + "\";");
			}
			
		}
		out.append(key + ".setColor(\"" + "black" + "\");");
		out.append("\r\n");
		out.append("document.write(");
		out.append(key);
		out.append(");\r\n");
		out.append("</SCRIPT>\r\n");
	}
	
}
