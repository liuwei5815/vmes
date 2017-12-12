package com.xy.apisql.parser;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.xy.apisql.base.BaseApiSqlParse;
import com.xy.apisql.common.ApiSqlConstans;
import com.xy.apisql.common.ApiSqlContext;
import com.xy.apisql.common.ApiToken;
import com.xy.apisql.common.SqlKeyword;
import com.xy.apisql.db.TablesService;
import com.xy.apisql.exception.ApiSqlException;
import com.xy.cms.entity.Tables;

@Order(2)
@Service("tableNameApiSqlParse")
public class TableNameApiSqlParse implements BaseApiSqlParse  {
	
	@Resource
	private TablesService tablesService;	
	@Override
	public void chain(ApiSqlContext apiSqlContext) {
		String apiSql = apiSqlContext.getApiSql();
		FilterTableName filterTableName = new FilterTableName();
		filterTableName.filter(apiSqlContext.getApiTokens());
		List<ApiToken> tableName = filterTableName.getTableName();
		Map<ApiToken,String> tableNameAlias= filterTableName.getTableNameAlias();
		Map<Integer,Map<String,Long>> tableAliasMap=new HashMap<>();
		
		for(int i=0;i<tableName.size();i++){
			ApiToken apiToken = tableName.get(i);
			System.out.println(apiToken.getToken());
			Tables table = tablesService.getTableByNameCn(apiToken.getToken().substring(ApiSqlConstans.TABLENAME_PREFIX.length(),apiToken.getToken().length()));
			if(table==null){
				throw new ApiSqlException(" not find "+apiToken);
			}
			String tableAlias = tableNameAlias.get(apiToken);
			apiToken.setToken(table.getName());
			Integer id =apiToken.getId();
			Map<String,Long> map = tableAliasMap.get(id);
			if(map==null){
				map=new HashMap<>();
				tableAliasMap.put(id, map);
			}
			if(StringUtils.isNotBlank(tableAlias)){
				if(map.get(tableAlias)!=null){
					throw new ApiSqlException(MessageFormat.format("alias {0} repeat", tableAlias));
				}
				map.put(tableAlias,table.getId());
			}
			else{
				map.put(ApiSqlConstans.TABLENAME_PREFIX+table.getNameCn(),table.getId());
			}
			
			
		}
		apiSqlContext.setTableAlias(tableAliasMap);
		
	}
	private class FilterTableName{
		private List<ApiToken> tableName=new ArrayList<>();
		private Map<ApiToken,String> tableNameAlias= new HashMap<ApiToken, String>();
		public void filter(List<ApiToken> apiTokens){
			Iterator<ApiToken> iterator = apiTokens.iterator();
			while(iterator.hasNext()){
				ApiToken apiToken = iterator.next();
				if(apiToken.getToken().startsWith(ApiSqlConstans.TABLENAME_PREFIX)){
					if(!elementTableName(apiToken, iterator)){
						return;
					};
				}
			}
		}
		private boolean elementTableName(ApiToken apiToken,Iterator<ApiToken> iterator){
		
			//如果不是字段，则需要继续向下挖掘，找到别名
			if(apiToken.getToken().indexOf(".")==-1){
				tableName.add(apiToken);
				ApiToken nextToken = getNextNotEmptyToken(iterator);
				if(nextToken==null){
					return false;
				}
				if(nextToken.getToken().equals("as")){
					String tableAlias=getNextNotEmptyToken(iterator).getToken();
					tableNameAlias.put(apiToken, tableAlias);
					
				}
				else if(nextToken.getToken().startsWith(ApiSqlConstans.TABLENAME_PREFIX)){
					elementTableName(nextToken, iterator);
				}
				else if(!nextToken.equals(SqlKeyword.SELECT.getKey()) && !nextToken.equals(SqlKeyword.WHERE.getKey()) && !nextToken.equals(SqlKeyword.SET.getKey())){
					tableNameAlias.put(apiToken, nextToken.getToken());
				}
			}
			return true;
			
		}
		
		private ApiToken getNextNotEmptyToken(Iterator<ApiToken> tokenIterator){
			while(tokenIterator.hasNext()){
				ApiToken token = tokenIterator.next();
				if(StringUtils.isNotEmpty(token.getToken())){
					return token;
				}
			}
			return null;
			
		}
		public List<ApiToken> getTableName() {
			return tableName;
		}
		public void setTableName(List<ApiToken> tableName) {
			this.tableName = tableName;
		}
		public Map<ApiToken, String> getTableNameAlias() {
			return tableNameAlias;
		}
		public void setTableNameAlias(Map<ApiToken, String> tableNameAlias) {
			this.tableNameAlias = tableNameAlias;
		}
		
		
	}
	
	

	
	
	
	

}
