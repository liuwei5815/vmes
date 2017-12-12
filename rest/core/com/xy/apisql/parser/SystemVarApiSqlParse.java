package com.xy.apisql.parser;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.xy.apisql.base.BaseApiSqlParse;
import com.xy.apisql.common.ApiSqlContext;
import com.xy.apisql.common.ApiToken;
import com.xy.apisql.common.SystemVar;

/**
 * 系统变量格式化
 * @author xiaojun
 *
 */
@Service
@Order(5)
public class SystemVarApiSqlParse implements BaseApiSqlParse {

	@Override
	public void chain(ApiSqlContext apiSqlContext) {
		List<ApiToken> tokens = apiSqlContext.getApiTokens();
		String apiSql = apiSqlContext.getApiSql();
		for(int i=0;i<tokens.size();i++){
			ApiToken systemToken=tokens.get(i);
			if(systemToken.getToken().equalsIgnoreCase(SystemVar.SESSION_USER_ID.getCode())){
				systemToken.setToken(":user_id");
				apiSqlContext.getNamedParam().put("user_id",apiSqlContext.getSessionUser().getId());
			}else if(systemToken.getToken().equalsIgnoreCase(SystemVar.SYSTEM_DATE.getCode())){
				systemToken.setToken(":curr_datetime");
				apiSqlContext.getNamedParam().put("curr_datetime",new Timestamp(new Date().getTime()));
			}else if(systemToken.getToken().equalsIgnoreCase(SystemVar.SESSION_EMP_ID.getCode())){
				systemToken.setToken(":emp_id");
				apiSqlContext.getNamedParam().put("emp_id",apiSqlContext.getSessionUser().getEmpId());
			}
			
		}
		
	}

}
