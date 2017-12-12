package com.xy.cms.common.base;

import com.xy.cms.common.CommonFunction;

//当一个类想要实现分页效果的时候 将此类作为一个属性
public class BaseQEntity {

	// 总的记录数
	protected long totalRows = 0;

	// 当前页面
	protected int currPage = 1;

	// 总的页面数
	protected int totalPages = 0;

	// 根据显示器分辩率显示查询记录行数
	String rows = CommonFunction.getPageRows();
	// 每一页显示的记录数 (可以通过set方法来进行修改)
	protected int perPageRows = Integer.parseInt(rows);

	// 是不是实现分页
	protected boolean showPages = true;

	// 使用什么列来排序
	protected String orderby = "id";

	// 使用升序还是降序
	protected String asc = "asc";

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPerPageRows() {
		return perPageRows;
	}

	public void setPerPageRows(int perPageRows) {
		this.perPageRows = perPageRows;
	}

	public boolean isShowPages() {
		return showPages;
	}

	public void setShowPages(boolean showPages) {
		this.showPages = showPages;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
		// 计算出现在的总的页数
		this.totalPages = (int) Math.ceil((this.totalRows - 1) / this.perPageRows) + 1;
		if (this.currPage > this.totalPages) {
			this.currPage = 1;
		}
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
}
