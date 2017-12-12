package com.xy.apisql.parser;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.xy.apisql.base.BaseApiSqlParse;
import com.xy.apisql.common.ApiSqlConstans;
import com.xy.apisql.common.ApiSqlContext;
import com.xy.apisql.common.ApiSqlUtil;
import com.xy.apisql.common.ApiToken;
import com.xy.apisql.db.TableColumnsService;
import com.xy.apisql.db.TablesService;
import com.xy.apisql.exception.ApiSqlException;
import com.xy.cms.entity.TableColumns;
@Order(3)
@Service("tableColumnsApiSqlParse")
public class TableColumnsApiSqlParse implements BaseApiSqlParse  {
	@Resource
	private TableColumnsService tableColumnsService;
	@Resource
	private TablesService tablesService;
	@Override
	public void chain(ApiSqlContext apiSqlContext) {
		List<ApiToken> tokens = apiSqlContext.getApiTokens();
		String apiSql = apiSqlContext.getApiSql();
		for(int i=0;i<tokens.size();i++){
			ApiToken apiToken=tokens.get(i);
			String[] columns=apiToken.getToken().split("\\.");
			if(ApiSqlUtil.isColumnsToken(apiToken.getToken())){
				String tableName=columns[0];
				String columnsName = columns[1];
				Long tableId= getTableId(apiToken, tableName, apiSqlContext);
				if(tableId==null){
					throw new ApiSqlException("not find table ["+tableName+"]");
				}
				TableColumns tableColumns = tableColumnsService.getTableColumnsByTableAndNameCn(tableId, columnsName);
				if(tableColumns==null){
					throw new ApiSqlException(MessageFormat.format(" not find column {0}",apiToken ));
				}
				if(tableName.startsWith(ApiSqlConstans.TABLENAME_PREFIX)){
					tableName=tablesService.getTable(tableId).getName();
				}
				
				apiToken.setToken(tableName+"."+tableColumns.getName());
			}
		}
		
		apiSqlContext.setApiSql(apiSql);
	}
	
	private Long getTableId(ApiToken apiToken,String tableName,ApiSqlContext apiSqlContext){
		LinkedList<Integer> parents= apiToken.getParents();
		Map<Integer,Map<String,Long>> tableAlias=apiSqlContext.getTableAlias();
		Map<String,Long> _map =tableAlias.get(apiToken.getId());
		if(_map!=null && _map.get(tableName)!=null){
			return _map.get(tableName);
		}
		else{
			for (Integer integer : parents) {
				Map<String,Long> _current  = tableAlias.get(integer);
				if(_current!=null && _current.get(tableName)!=null){
					return _current.get(tableName);
				}
			}
			return null;
		}

		
	}

	

}
