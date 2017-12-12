package com.xy.cms.common.base;

import java.util.List;

/**
 * 
 * 查询时生成的结果
 * 
 **/
public class QueryResult {

	public List list; // 存在结果的list
	public String totalCount; // 总记录数
	private String totalPage; // 总页数
	private String currentPage;

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
}
