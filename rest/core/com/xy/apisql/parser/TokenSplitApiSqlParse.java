package com.xy.apisql.parser;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.xy.apisql.base.BaseApiSqlParse;
import com.xy.apisql.common.ApiSqlContext;
import com.xy.apisql.common.ApiToken;

@Order(1)
@Service("tokenSplitApiSqlParse")
public class TokenSplitApiSqlParse implements BaseApiSqlParse {

	@Override
	public void chain(ApiSqlContext apiSqlContext) {
		ApiTokenFormat tokenFormat = new ApiTokenFormat();
		tokenFormat.spilt(apiSqlContext.getApiSql());
		apiSqlContext.setApiTokens(tokenFormat.getApiTokens());

		apiSqlContext.setSeparators(tokenFormat.getSeparators());
	}

	private class ApiTokenFormat {
		private LinkedList<ApiToken> apiTokens = new LinkedList<>();
		private LinkedList<String> separators = new LinkedList<>();
		private int begin = 0;
		private int sn = 1;

		public void spilt(String apiSql) {
			String HQL_SEPARATORS = "[\\s\\(\\)=,]";
			Pattern pattern = Pattern.compile(HQL_SEPARATORS);
			Matcher matcher = pattern.matcher(apiSql);
			LinkedList<Integer> linkedList = new LinkedList<>();
			linkedList.add(0);
			elementApiToken(apiSql, sn, linkedList, matcher);

			System.out.println(begin + ": end" + apiSql.length());
		}

		private void elementApiToken(String apiSql, Integer id, LinkedList<Integer> parents, Matcher matcher) {
			while (matcher.find()) {
				String separator = matcher.group();
				ApiToken apiToken = new ApiToken();
				apiToken.setId(id);
				apiToken.setParents(parents);
				apiToken.setToken(apiSql.substring(begin, matcher.start()));
				apiTokens.add(apiToken);
				begin = matcher.end();
				separators.add(separator);
				if (separator.equals("(")) {
					LinkedList<Integer> new_parents = new LinkedList<>(parents);
					new_parents.add(id);
					elementApiToken(apiSql, ++sn, new_parents, matcher);
					continue;
				} else if (separator.equals(")")) {
					return;
				}

			}
			if (begin < apiSql.length()) {
				ApiToken apiToken = new ApiToken();
				apiToken.setId(id);
				apiToken.setParents(parents);
				apiToken.setToken(apiSql.substring(begin, apiSql.length()));
				apiTokens.add(apiToken);
			}
		}

		public LinkedList<ApiToken> getApiTokens() {
			return apiTokens;
		}

		public void setApiTokens(LinkedList<ApiToken> apiTokens) {
			this.apiTokens = apiTokens;
		}

		public LinkedList<String> getSeparators() {
			return separators;
		}

		public void setSeparators(LinkedList<String> separators) {
			this.separators = separators;
		}

	}

}
