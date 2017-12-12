/**
 * 
 */
package com.xy.admx.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 显示具体页码式分页标签
 * @author dg
 * 
 */
public class PageTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * 页面form名称
	 */
	private String formName;
	/**
	 * 查询action
	 */
	private String action;
	/**
	 * form的target
	 */
	private String target;
	
	//当前选择的每页显示条数
	private long perPageRows;

	
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
			out.append("	top.Dialog.alert(\"本页已经是首页\");\r\n");
			out.append("return ;\r\n");
			out.append("}\r\n");
			out.append("	currpage = 1;\r\n");
			out.append("	}\r\n");
			out.append("				if(control == \"previous\"){\r\n");
			out.append("if(currpage <= 1){\r\n");
			out.append("top.Dialog.alert(\"本页已经是首页\");\r\n");
			out.append("return ;\r\n");
			out.append("}\r\n");
			out.append("currpage = currpage - 1;\r\n");
			out.append("}\r\n");

			out.append("if(control == \"next\"){\r\n");
			out.append("if(currpage >= totalpage){\r\n");
			out.append("top.Dialog.alert(\"本页已经是末页\");\r\n");
			out.append("return ;\r\n");
			out.append("}\r\n");
			out.append("currpage = currpage + 1;\r\n");
			out.append("}\r\n");
			out.append("if(control == \"last\"){\r\n");
			out.append("if(currpage >= totalpage){\r\n");
			out.append("top.Dialog.alert(\"本页已经是末页\");\r\n");
			out.append("return ;\r\n");
			out.append("}\r\n");
			out.append("currpage = totalpage;\r\n");
			out.append("}		\r\n");
			out.append("if(control == \"refresh\"){\r\n");
			out.append("currpage = 1;\r\n");
			out.append("}		\r\n");
			out.append("document.getElementById(\"currPage\").value = currpage;\r\n");
			String newFormName = (formName == null|| formName.trim().length() == 0 ? "forms[0]" : formName);
			if(!"".equals(action)){
				out.append("document.").append(newFormName).append(".action=\"").append(action).append("\";\r\n");
			}
			if (target != null && target.trim().length() > 0) {
				out.append("document.").append(newFormName).append(".target=\"").append(target).append("\";\r\n");
			}
			out.append("document."+newFormName+".submit();\r\n");
			out.append("}\r\n");
			
			out.append("function goToPage(){ \r\n");
			out.append("	var currpage=parseInt(document.getElementById('currPageLable').innerHTML); \r\n");
			out.append("	var totalcount=parseInt(document.getElementById('totalCountLable').innerHTML);\r\n");
			out.append("	var totalpage=parseInt(document.getElementById('totalPageLable').innerHTML);\r\n");
			out.append("	var goTo = document.getElementById('goTo').value; \r\n");
			out.append("	if(goTo == ''){\r\n");
			out.append("		top.Dialog.alert('请输入要到达的页码');   \r\n");
			out.append("		document.getElementById('goTo').focus();\r\n");
			out.append("		return false;\r\n");
			out.append("	}\r\n");
			out.append("	if(isNaN(goTo)){\r\n");
			out.append("		top.Dialog.alert('页码应为数字');\r\n");
			out.append("		document.getElementById('goTo').focus();\r\n");
			out.append("		return false;\r\n");
			out.append("	} \r\n");
			out.append("	if(parseInt(goTo) == currpage){\r\n");
			out.append("		top.Dialog.alert('已是当前页,无需跳转');\r\n");
			out.append("		return false;\r\n");
			out.append("	}\r\n");
			out.append("	if(parseInt(goTo) < 1){\r\n");
			out.append("		top.Dialog.alert('请正确输入页码!'); \r\n");
			out.append("		return false;\r\n");
			out.append("	}\r\n");
			out.append("	if(goTo > totalpage){\r\n");
			out.append("		top.Dialog.alert('输入页码超过最大页数,无法跳转');\r\n");
			out.append("		return false;\r\n");
			out.append("	}\r\n");
			out.append("	document.getElementById('currPage').value = goTo;\r\n");
			if(!"".equals(action)){
				out.append("document.").append(newFormName).append(".action=\"").append(action).append("\";\r\n");
			}
			if (target != null && target.trim().length() > 0) {
				out.append("document.").append(newFormName).append(".target=\"").append(target).append("\";\r\n");
			}
			out.append("	document."+newFormName+".submit();\r\n");
			out.append("}\r\n");
			
			out.append("function changePerPageRows(){ \r\n");
			out.append("	var perPageRows = document.getElementById('changerow').value;\r\n");
			out.append("	document.getElementById('perPageRows').value = perPageRows;\r\n");
			if(!"".equals(action)){
				out.append("document.").append(newFormName).append(".action=\"").append(action).append("\";\r\n");
			}
			if (target != null && target.trim().length() > 0) {
				out.append("document.").append(newFormName).append(".target=\"").append(target).append("\";\r\n");
			}
			out.append("	document."+newFormName+".submit();\r\n");
			out.append("}\r\n");
			
			out.append("</script>\r\n");
			out.append("<div class='page_right'>");
			out.append("<ul>");
			out.append("<li>查询结果：共<span id=\"totalCountLable\">0</span>条记录  当前<span id=\"currPageLable\">0</span>/<span id=\"totalPageLable\">0</span> 页。</span></li>");
			out.append("<li><select class='list_number' autoWidth='true' id='changerow' onchange=\"changePerPageRows();\"><option value='10'>10</option><option value='20'>20</option><option value='30'>30</option><option value='100'>100</option><option value='150'>150</option></select></li>");
			out.append("");
			//首页
			out.append("<li><a href='javascript:void(0);'onclick=\"turn('first');\" ><img src='images/icon-bottom2.png' /></a>");
			//上一页
			out.append("<a href='javascript:void(0);' onclick=\"turn('previous');\"><img src='images/icon-next2.png' style='padding-left:15px;' /></a></li>");
			out.append("<li><input type=\"text\" name='goTo' id='goTo' class=\"num\"  onchange='goToPage()'></li>");
			//下一页
			out.append("<li><a href='javascript:void(0);' onclick=\"turn('next');\"><img src='images/icon-next.png' /></a>");
			//末页
			out.append("<a href='javascript:void(0);' onclick=\"turn('last');\"><img src='images/icon-bottom.png' style='padding-left:15px;' /></a></li>");
			out.append("<li><a href='javascript:void(0);' onclick=\"turn('refresh');\"><img src='images/icon-refresh.png' style='vertical-align:text-bottom;' /></a></li>");
			
			
			
		/*	out.append("<div style='height:35px;'>\r\n");
			out.append("<div class='float_left padding5'>;</div> \r\n");
			out.append("<div class='float_right padding5 paging'>\r\n");
			out.append("<div class='float_left padding_top4'>\r\n");
			
			out.append("<span class=''><a href='javascript:void(0);' onclick=\"turn('first');\">首页</a></span>\r\n");
			out.append("<span class=''><a href='javascript:void(0);' onclick=\"turn('previous');\">上一页</a></span>\r\n");
			out.append("<span><a href='javascript:void(0);' onclick=\"turn('next');\" >下一页</a></span>\r\n");
			out.append("<span><a href='javascript:void(0);' onclick=\"turn('last');\" >末页</a></span>	每页 \r\n");
			out.append("</div>\r\n");
			out.append("<div class='float_left padding_top3'><select autoWidth='true' id='changerow' onchange=\"changePerPageRows();\"><option value='10'>10</option><option value='30'>30</option><option value='50'>50</option><option value='100'>100</option><option value='150'>150</option></select></div>\r\n");
			out.append("<div class='float_left padding_top6'>条记录&nbsp;&nbsp;</div>\r\n");
			out.append("<div class='float_left padding_top3'><input style='width:40px' name='goTo' id='goTo'/></div>\r\n");
			out.append("<div class='float_left padding_top6'><span><a href='javascript:void(0)' onclick='goToPage()'>GO</a></span></div>\r\n");
			out.append("<div class='clear'></div>\r\n");
			out.append("</div>\r\n");
			out.append("<div class='clear'></div>\r\n");
			out.append("</div>\r\n");*/
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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public long getPerPageRows() {
		return perPageRows;
	}
	public void setPerPageRows(long perPageRows) {
		this.perPageRows = perPageRows;
	}

	
}
