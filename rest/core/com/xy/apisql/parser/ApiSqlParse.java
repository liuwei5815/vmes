package com.xy.apisql.parser;

import java.util.Map;

/**
 * 格式化ApiSql
 * @author xiaojun
 *
 */
public class ApiSqlParse {
	/**
	 * 格式化apiSql
	 * @param apiSql
	 * @param parseApi
	 * @return
	 */
	public static String parseApiSql(String apiSql,Map<String,Object> parseApi){
		
		
		return "";
	}
	public static void main(String[] args) {
		String apiSql = "select _user.姓名  as name , _user.年龄  as age,(select count(*) from @评论 where createby=_user.id)  as likes from @用户 as _user where _user.id=$param.id";
		String HQL_SEPARATORS = "[\\s\\(\\)=,]";
		String[] apiSqls =apiSql.split(HQL_SEPARATORS);
		for (String string : apiSqls) {
			System.out.println(string);
		}
		
	}
}
