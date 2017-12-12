package com.xy.apisql.common;


public enum SqlKeyword {
	SELECT("select"),FROM("from"),SET("set"),INNOR("innor"),JOIN("join"),WHERE("where"),AND("and"),OR("or"),like("like");
	private String key;
	private SqlKeyword(String key){
		this.key=key;
	}
	public String getKey() {
		return key;
	}
	public static SqlKeyword index(String key){
		SqlKeyword[] sqlKeywords =values();
		for (SqlKeyword sqlKeyword : sqlKeywords) {
			if(sqlKeyword.getKey().equals(key)){
				return sqlKeyword;
			}
		}
		return null;
		
	}
	
}
