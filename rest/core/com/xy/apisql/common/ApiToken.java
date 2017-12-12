package com.xy.apisql.common;

import java.util.LinkedList;

/***
 * ApiToken 拆分sql语句每个单词 id标示他为哪个嵌套查询 parentId标示他父级的嵌套查询 示例
 * sql:select 1,(select 1  from 2 ) from d 
 * 那么相对应的id 1 parentId：0 id =2 parentId:3
 * @author xiaojun
 *
 */
public class ApiToken {
	private Integer id;

	private LinkedList<Integer> parents;

	private String token;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LinkedList<Integer> getParents() {
		return parents;
	}

	public void setParents(LinkedList<Integer> parents) {
		this.parents = parents;
	}

	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "ApiToken [id=" + id + ", parents=" + parents + ", token=" + token + "]";
	}

	public void setToken(String token) {
		this.token = token;
	}

}
