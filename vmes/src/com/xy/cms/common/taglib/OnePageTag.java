package com.xy.cms.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 同一个页面的分页标签
 * 非iframe
 * @author dg
 * 
 */
public class OnePageTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * 页面form名称
	 */
	private String formName;
	/**
	 * 查询action
	 */
	private String action;
	
	public int doStartTag() throws JspTagException {
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		try {
			StringBuffer out = new StringBuffer();
			out.append("<script type=\"text/javascript\" language=\"javaScript\">\r\n");
			out.append("function turn(control){\r\n");
			out.append("var currpage=parseInt(document.getElementById(\"currPageLable\").innerHTML);\r\n");
			out.append("var totalcount=parseInt(document.getElementById(\"totalCountLable\").innerHTML);\r\n");
			out.append("var totalpage=parseInt(document.getElementById(\"totalPageLable\").innerHTML);\r\n");
			out.append("if(control == \"first\"){\r\n");
			out.append("if(currpage <= 1){\r\n");
			out.append("	alert(\"本页已经是首页\");\r\n");
			out.append("return ;\r\n");
			out.append("}\r\n");
			out.append("	currpage = 1;\r\n");
			out.append("	}\r\n");
			out.append("				if(control == \"previous\"){\r\n");
			out.append("if(currpage <= 1){\r\n");
			out.append("alert(\"本页已经是首页\");\r\n");
			out.append("return ;\r\n");
			out.append("}\r\n");
			out.append("currpage = currpage - 1;\r\n");
			out.append("}\r\n");

			out.append("if(control == \"next\"){\r\n");
			out.append("if(currpage >= totalpage){\r\n");
			out.append("alert(\"本页已经是末页\");\r\n");
			out.append("return ;\r\n");
			out.append("}\r\n");
			out.append("currpage = currpage + 1;\r\n");
			out.append("}\r\n");
			out.append("if(control == \"last\"){\r\n");
			out.append("if(currpage >= totalpage){\r\n");
			out.append("alert(\"本页已经是末页\");\r\n");
			out.append("return ;\r\n");
			out.append("}\r\n");
			out.append("currpage = totalpage;\r\n");
			out.append("}		\r\n");
			out.append("document.getElementById(\"currPage\").value = currpage;\r\n");
			String newFormName = (formName == null|| formName.trim().length() == 0 ? "forms[0]" : formName);
			if(!"".equals(action)){
				out.append("document.").append(newFormName).append(".action=\"").append(action).append("\";\r\n");
			}
			out.append("document."+newFormName+".submit();\r\n");
			out.append("}\r\n");
			
			out.append("function goToPage(){ \r\n");
			out.append("	var currpage=parseInt(document.getElementById('currPageLable').innerHTML); \r\n");
			out.append("	var totalcount=parseInt(document.getElementById('totalCountLable').innerHTML);\r\n");
			out.append("	var totalpage=parseInt(document.getElementById('totalPageLable').innerHTML);\r\n");
			out.append("	var goTo = document.getElementById('goTo').value; \r\n");
			out.append("	if(goTo == ''){\r\n");
			out.append("		alert('请输入要到达的页码');   \r\n");
			out.append("		document.getElementById('goTo').focus();\r\n");
			out.append("		return false;\r\n");
			out.append("	}\r\n");
			out.append("	if(isNaN(goTo)){\r\n");
			out.append("		alert('页码应为数字');\r\n");
			out.append("		document.getElementById('goTo').focus();\r\n");
			out.append("		return false;\r\n");
			out.append("	} \r\n");
			out.append("	if(parseInt(goTo) == currpage){\r\n");
			out.append("		alert('已是当前页,无需跳转');\r\n");
			out.append("		return false;\r\n");
			out.append("	}\r\n");
			out.append("	if(parseInt(goTo) < 1){\r\n");
			out.append("		alert('请正确输入页码!'); \r\n");
			out.append("		return false;\r\n");
			out.append("	}\r\n");
			out.append("	if(goTo > totalpage){\r\n");
			out.append("		alert('输入页码超过最大页数,无法跳转');\r\n");
			out.append("		return false;\r\n");
			out.append("	}\r\n");
			out.append("	document.getElementById('currPage').value = goTo;\r\n");
			if(!"".equals(action)){
				out.append("document.").append(newFormName).append(".action=\"").append(action).append("\";\r\n");
			}
			out.append("	document."+newFormName+".submit();\r\n");
			out.append("}\r\n");
			
			out.append("</script>\r\n");
			out.append("<form method=\"post\">");
			out.append("<div class=\"pages\">\r\n");
			out.append("<a href=\"javascript:void(0);\" onclick=\"turn('first');\">首 页</a>\r\n");
			out.append("<a href=\"javascript:void(0);\" onclick=\"turn('previous');\">上一页</a>\r\n");
			out.append("<a href=\"javascript:void(0);\" onclick=\"turn('next');\">下一页</a>\r\n");
			out.append("<a href=\"javascript:void(0);\" onclick=\"turn('last');\">尾页</a>\r\n");
			out.append("<span class=\"span_3\">共<label id=\"totalCountLable\">0</label>条  当前<label id=\"currPageLable\">0</label>/<label id=\"totalPageLable\">0</label>页</span> \r\n");
			out.append("<span class=\"span_3\">到第</span> \r\n");
			out.append("<input type=\"text\" name='goTo' id='goTo'/> \r\n");
			out.append("<span class=\"span_4\">页</span> \r\n");
			out.append("<button onclick='goToPage()'>确定</button> \r\n");
			out.append(" </div> \r\n");
			out.append("</form>");
			
			pageContext.getOut().write(out.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public void doInitBody() throws JspTagException {
	}

	public void setBodyContent(BodyContent bodyContent) {
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
