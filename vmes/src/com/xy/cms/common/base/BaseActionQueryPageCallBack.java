package com.xy.cms.common.base;

import java.util.Map;

import com.xy.cms.common.exception.BusinessException;

public interface BaseActionQueryPageCallBack {
			public QueryResult execute(Map pageMap) throws BusinessException;
}
