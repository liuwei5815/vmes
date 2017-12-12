package com.xy.admx.common.base;

import java.util.List;

import com.xy.admx.common.Constants;

/**
 * 当一个类想要实现分页效果的时候 将此类作为一个属性
 * 
 * @author dg
 *
 */
public class PageQEntity<T> {

	// 总的记录数
	protected int totalCount = 0;

	// 当前页面
	protected int currentPage = 1;

	// 总的页面数
	protected int totalPage = 0;

	// 每一页显示的记录数 (可以通过set方法来进行修改)
	protected Integer perPageRows = Integer.parseInt(Constants.PAGE_PER_RECORD10);

	// 存在结果的list
	private List<T> list; 
	
	public Integer getPerPageRows() {
		return perPageRows;
	}

	public void setPerPageRows(Integer perPageRows) {
		this.perPageRows = perPageRows;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage() {
		this.totalPage = (int) Math.ceil((this.totalCount - 1) / this.perPageRows) + 1;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "分页显示：每页[" + getPerPageRows() + "]条记录，从第[" + ((getCurrentPage() - 1) * getPerPageRows() + 1)
				+ "]条记录开始，显示第[" + getCurrentPage() + "]页";
	}
}
