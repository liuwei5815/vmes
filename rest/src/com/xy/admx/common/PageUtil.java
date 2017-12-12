package com.xy.admx.common;

public class PageUtil {
		private static Integer DEFAULT_PAGE_SIZE=10;
		private static Integer DEFAULT_CURRENT_PAGE=1;
		public static Integer getCurrentPage(Integer currentPage){
			return currentPage==null?DEFAULT_CURRENT_PAGE:currentPage;
		}
		public static Integer getPageSize(Integer pageSize){
			return pageSize==null?DEFAULT_PAGE_SIZE:pageSize;
		}
}
