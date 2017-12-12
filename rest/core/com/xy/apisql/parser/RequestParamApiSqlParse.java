package com.xy.apisql.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.xy.apisql.base.BaseApiSqlParse;
import com.xy.apisql.common.ApiSqlConstans;
import com.xy.apisql.common.ApiSqlContext;
import com.xy.apisql.common.ApiSqlUtil;
import com.xy.apisql.common.ApiToken;
import com.xy.apisql.exception.ApiSqlException;
@Order(4)
@Service("requestParamApiSqlParse")
public class RequestParamApiSqlParse implements BaseApiSqlParse {

	@Override
	public void chain(ApiSqlContext apiSqlContext) {
		List<ApiToken> apiTokens = apiSqlContext.getApiTokens();
		int requestNo=1;
		Map<String,Object> namedParam =new HashMap<String, Object>();
		for (ApiToken apiToken : apiTokens) {
			String token = apiToken.getToken();
			if(ApiSqlUtil.isRequestParamToken(token)){
				String requestParam = token.substring(ApiSqlConstans.REQUEST_PREFIX.length());
				String new_Token = "request_"+requestNo++;
				apiToken.setToken(":"+new_Token);
				Object value = apiSqlContext.getRequest().get(requestParam);
				if(value == null){
					throw new ApiSqlException("服务[" + apiSqlContext.getCurrentApi().getApi() + "]缺少参数" + requestParam);
				}
				namedParam.put(new_Token,value==null?"":value);
			}
		}
		apiSqlContext.setNamedParam(namedParam);
	}

}
