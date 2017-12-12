package com.xy.apisql.base;

import com.xy.apisql.common.ApiSqlContext;

/**
 * 
 * @author xiaojun
 *
 */
public interface BaseApiSqlParse {
	/**
	 * 
	 * @param apiSql
	 * @param param
	 * @return
	 */
	public void chain(ApiSqlContext apiSqlContext);
}
