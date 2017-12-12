package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.SysLog;

public interface SysLogService {

	List<SysLog> getAllRole() throws BusinessException;

	QueryResult querySysLog(Map<String, Object> map) throws BusinessException;

}