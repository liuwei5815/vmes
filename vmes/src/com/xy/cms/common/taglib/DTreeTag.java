package com.xy.cms.common.taglib;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class DTreeTag extends TagSupport{

	private String rootName;
	private String list;
	private String type;
	private String imagePath;
	private String action;
	private String target;

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
		
		out.append("<script type=\"text/javascript\">\r\n");
		out.append("var d = new dTree('d');");
		out.append("d.add(0,-1,'").append(rootName).append("');");
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Tree node = (Tree) iterator.next();
			out.append("d.add(\"").append(node.getId()).append("\",\"").append(node.getParentId()).append("\",\"").append(node.getText()).append("\",\"");
			out.append(node.getAction() == null ? "":node.getAction());
			out.append("\",\"").append(node.getText()).append("\",\"");
			if(node.getTarget() == null){
				if(this.getTarget() != null){
					out.append(this.getTarget());
				}
			}else{
				out.append(node.getTarget() == null ? "":node.getTarget());
			}
			out.append("\");\r\n");
		}	
		out.append("document.write(d);\r\n");
		out.append("</script>\r\n");
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
}
