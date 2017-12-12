package com.xy.cms.db.bean;

public class Column {
		private String name;//字段名
		private String alias;//字段别名
		public Column(String name){
			this(name, name);
		}
		public Column(String name,String alias){
			this.setName(name);
			this.setAlias(alias);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAlias() {
			return alias;
		}
		public void setAlias(String alias) {
			this.alias = alias;
		}
		
}
