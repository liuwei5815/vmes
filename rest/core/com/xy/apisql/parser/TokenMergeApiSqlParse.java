package com.xy.apisql.parser;

import java.util.Iterator;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.xy.apisql.base.BaseApiSqlParse;
import com.xy.apisql.common.ApiSqlContext;
import com.xy.apisql.common.ApiToken;
@Order(6)
@Service
public class TokenMergeApiSqlParse implements BaseApiSqlParse{

	@Override
	public void chain(ApiSqlContext apiSqlContext) {
		Iterator<ApiToken> apiTokens= apiSqlContext.getApiTokens().iterator();
		Iterator<String>  separators =apiSqlContext.getSeparators().iterator();
		StringBuilder apiSqlBuilder =new StringBuilder();
		while(apiTokens.hasNext()){
			apiSqlBuilder.append(apiTokens.next().getToken()).append(separators.hasNext()?separators.next():"");
		}
		apiSqlContext.setApiSql(apiSqlBuilder.toString());
		
	}

}
