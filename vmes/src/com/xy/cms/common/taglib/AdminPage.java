package com.xy.cms.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class AdminPage extends TagSupport {

	private static final long serialVersionUID = 1001L;
	private Integer totalCount;
	private Integer currPage;//当前页
	private Integer totalPage;//共几页
	private String action;//跳转路径
	private Integer pageSize;
	
	private static int noteSum=6;//显示的分页条的数
	
	public int doStartTag() throws JspTagException {
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		try {
			String url = action.substring(0,action.lastIndexOf("."));
			String suf = action.substring(action.lastIndexOf("."));
			StringBuffer out = new StringBuffer();
			
			out.append("<div class=\"page\">\r\n");
			out.append("<ul class=\"cl\">");
			if(currPage==0){
				currPage=1;
			}
			if(currPage > 1){
				out.append("<li class=\"pre\"><a href=\"javascript:void(0);\" onclick=\"pageSub("+(currPage-1)+")\"><i class=\"icon-prev\"></i></a></li>");
			}else{
				out.append("<li class=\"next\" style=\"display:none;\"><a href=\"javascript:void(0);\"><i class=\"icon-next\"></i></a></li>");
			}
			if(totalPage==0){
				totalPage=1;
			}
			/*==*/
			int begin=1;
			int end =1;
			if(totalPage<=noteSum){
				
				end=totalPage;
				appendItems(out,begin,end,currPage,totalPage);
			}
			else{
				int middleNode = noteSum/2;//先确定最中间的数(最后一个不计算 则6中间应该是3);
				if(currPage>middleNode){
				begin=currPage-middleNode+1;
				}
				end = begin+noteSum-1;
				if(end > totalPage){
					end=totalPage;
					begin=totalPage-noteSum+1;
				}
				appendItems(out,begin,end,currPage,totalPage);
			}
			
			/*==*/
			if(currPage.equals(totalPage)){
				out.append("<li style=\"display:none;\" class=\"next\"><a href=\"javascript:void(0);\"><i class=\"icon-next\"></i></a></li>");
			}else{
				out.append("<li class=\"next\"><a href=\"javascript:void(0);\" onclick=\"pageSub("+(currPage+1)+")\"><i class=\"icon-next\"></i></a></li>");
			}
			out.append("<script type=\"text/javascript\">");
			out.append("function pageSub(currPage){");
			out.append("location.href=\""+url+"_\"+currPage+\""+suf+"\"");
			out.append("}</script>");
			
			out.append("<li><a href=\"javascript:void(0);\"><input id=\"page_text\" readonly=\"readonly\" type=\"text\" value="+currPage+" class=\"ptve\" /><span>/"+totalPage+"页</span></a></li>");
			out.append("</ul>");
			out.append("</div>\r\n");
			
			pageContext.getOut().write(out.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	private  void appendItems(StringBuffer out,long start, long end, long currentPage,long totalCountPage) {
		for(long i=start;i<=end;i++){
			if(i==currentPage){
				out.append("<li><a href=\"javascript:void(0);\" class=\"on\">"+i+"</a></li>");
			}else{
				out.append("<li><a href=\"javascript:void(0);\" onclick=\"pageSub("+i+")\">"+i+"</a></li>");
			}
		}
		if(end < totalCountPage){
			if(end == totalCountPage-1){
				out.append("<li><a href=\"javascript:void(0);\">...</a></li>");
			}else{
				out.append("<li><a href=\"javascript:void(0);\">...</a></li>");
				out.append("<li><a href=\"javascript:void(0);\" onclick=\"pageSub("+totalCountPage+")\">"+totalCountPage+"</a></li>");
			}
		}
	}
	
	private  void appendItems(StringBuffer out,long start, long end, long currentPage,String url,String suf) {
		for(long i=start;i<=end;i++){
			if(i==currentPage){
				out.append("<li><a href=\"javascript:void(0);\" class=\"on\">"+i+"</a></li>");
			}else{
				out.append("<li><a href=\"javascript:void(0);\" onclick=\"pageSub("+i+")\">"+i+"</a></li>");
			}
		}
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
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public static int getNoteSum() {
		return noteSum;
	}
	public static void setNoteSum(int noteSum) {
		AdminPage.noteSum = noteSum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public void setAction(String action) {
		this.action = action;
	}
}
