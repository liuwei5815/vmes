package com.xy.cms.service;

import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;

public interface TraceService {
	/**
	 * 生产计划主页List数据查询
	 * @param pageMap 参数的map
	 * */
	public QueryResult getAllProduceplanRelationOrdersDetail(Map pageMap)throws BusinessException;
}
