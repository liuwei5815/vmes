package com.xy.apisql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xy.apisql.base.BaseApiSqlParse;
import com.xy.apisql.common.ApiSqlContext;
import com.xy.apisql.common.ApiSqlResult;
import com.xy.apisql.common.ApiSqlResult.ApiSqlType;
import com.xy.cms.entity.Api;
import com.xy.cms.entity.AppUser;

@Component("apiSqlParseFactory")
public class ApiSqlParseFactory{
	@Autowired
	private List<BaseApiSqlParse> baseApiSqlParses;
	
	public ApiSqlResult forMatApiSql(Api currApi,AppUser appUser,Map<String,String> request){
		ApiSqlContext apiSqlContext =new ApiSqlContext();
	
		apiSqlContext.setApiSql(currApi.getApiSql().trim().toLowerCase());
		apiSqlContext.setRequest(request);
		apiSqlContext.setSessionUser(appUser);
		apiSqlContext.setCurrentApi(currApi);
		for(BaseApiSqlParse baseApiSqlParse:baseApiSqlParses){
			baseApiSqlParse.chain(apiSqlContext);
		}
		ApiSqlResult sqlResult = new ApiSqlResult();
		sqlResult.setSql(apiSqlContext.getApiSql());
		sqlResult.setNamedParam(apiSqlContext.getNamedParam());
		String firestToken =apiSqlContext.getApiTokens().get(0).getToken();
		sqlResult.setApiSqlType(firestToken.equals("select")?ApiSqlType.SELECT:(firestToken.equals("update")?ApiSqlType.UPDATE:(firestToken.equals("delete")?ApiSqlType.DELETE:ApiSqlType.INSERT)));
		return sqlResult;
	}
	
	
	

	private  List<String> getApiTokens(String apiSql){
		String HQL_SEPARATORS = "[\\s\\(\\)=,]";
		String[] apiSqls =apiSql.split(HQL_SEPARATORS);
		return  new ArrayList<>(Arrays.asList(apiSqls));
	}

}
