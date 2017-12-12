package com.xy.cms.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspTagException; 
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 显示具体页码式分页标签
 * @author dg
 * 
 */
public class indexPageTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private Integer currPage;//当前页
	private Integer totalPage;//共几页
	private String action;//跳转路径
	
	public int doStartTag() throws JspTagException {
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspTagException {
		try {
			String url = action.substring(0,action.lastIndexOf("/"));
			String suf = action.substring(action.lastIndexOf("."));
			StringBuffer out = new StringBuffer();
			out.append("<div class=\"page\">\r\n");
			out.append("<ul>\r\n");
			/*if(currPage<=1){
				out.append("<li class=\"nu\"><a >上一页</a></li>\r\n");
				out.append("<li class=\"nu\"><a >首页</a></li>\r\n");
			}
			else{
				StringBuffer sb = new StringBuffer();
				sb.append(url).append("/").append(currPage-1).append(suf);
				out.append("<li ><a href=\""+sb.toString()+"\">上一页</a></li>\r\n");
				sb = new StringBuffer();
				sb.append(url).append("/").append(1).append(suf);
				out.append("<li ><a href=\""+sb.toString()+"\">首页</a></li>\r\n");
			}*/
			if(currPage>1){
				StringBuffer sb = new StringBuffer();
				sb.append(url).append("/").append(currPage-1).append(suf);
				out.append("<li ><a href=\""+sb.toString()+"\">上一页</a></li>\r\n");
				sb = new StringBuffer();
				sb.append(url).append("/").append(1).append(suf);
				out.append("<li ><a href=\""+sb.toString()+"\">首页</a></li>\r\n");
			}
			if(!currPage.equals(totalPage)){
				StringBuffer sb = new StringBuffer();
				sb.append(url).append("/").append(currPage+1).append(suf);
				out.append("<li ><a href=\""+sb.toString()+"\">下一页</a></li>\r\n");
				sb = new StringBuffer();
				sb.append(url).append("/").append(totalPage).append(suf);
				out.append("<li ><a href=\""+sb.toString()+"\">末页</a></li>\r\n");
			}
			/*
			if(currPage.equals(totalPage)){
				out.append("<li  class=\"nu\"><a>下一页</a></li>\r\n");
				out.append("<li class=\"nu\"><a >末页</a></li>\r\n");
			}
			else{
				StringBuffer sb = new StringBuffer();
				sb.append(url).append("/").append(currPage+1).append(suf);
				out.append("<li ><a href=\""+sb.toString()+"\">下一页</a></li>\r\n");
				sb = new StringBuffer();
				sb.append(url).append("/").append(totalPage).append(suf);
				out.append("<li ><a href=\""+sb.toString()+"\">末页</a></li>\r\n");
			}*/
			out.append("</ul>\r\n");
			pageContext.getOut().write(out.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	public Integer getCurrPage() {
		return currPage;
	}
	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	

	
}
