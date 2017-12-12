package com.xy.cms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IDAO {
	
	public void save(Object object) throws Exception;

	public void update(Object object) throws Exception;

	public int updateOrDel(String hql, Map parMap) throws Exception;

	public void delete(Object object) throws Exception;

	public List getList(String hql, Map parMap) throws Exception;
	
	public Object get(Class clazz, Serializable id) throws Exception;
}
