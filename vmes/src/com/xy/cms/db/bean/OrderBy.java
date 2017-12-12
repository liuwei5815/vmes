package com.xy.cms.db.bean;

import javax.persistence.criteria.Order;

public class OrderBy {
		private String name;//字段名
		private Integer order;//升降序
		private static Integer begin=1;
		private static Integer end=2;
		public static enum ORDER_BY{
			ASC(1),DESC(2);
			private int code;
			private ORDER_BY(int code){
				this.code=code;
			}
		}
		public OrderBy(String name){
			this(name,1);//默认升序
		}
		public OrderBy(String name,int order){
			this.setName(name);
			this.setOrder(order);
		}
		public boolean isDESC(){
			return  order==ORDER_BY.DESC.code;
		}
		public boolean isASC(){
			return  order==ORDER_BY.ASC.code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getOrder() {
			return order;
		}
		public void setOrder(Integer order) {
			if(order<begin || order>end){
				throw new IllegalArgumentException("order超过范湖,只能在"+begin+"到"+end+"之间");
			}
			this.order = order;
		}
		
}	
