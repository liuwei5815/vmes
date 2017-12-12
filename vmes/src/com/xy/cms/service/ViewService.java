package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.View;

public interface ViewService {
	QueryResult queryTables(Map<String,Object> map) throws BusinessException;
	List getTablesInfo();
	void saveView(View view,String ruleStr_def,String ruleStr_dt,String columnStr,String orderStr,String flag,String viewName_old,Long viewId)throws BusinessException;
    Map getViewInfoById(Long id);
    void delViewById(Long id);
    void saveViewNew(View view,String ruleStr_def,String ruleStr_dt,String columnStr,String orderStr,String flag,String viewName_old,Long viewId)throws BusinessException;
}
