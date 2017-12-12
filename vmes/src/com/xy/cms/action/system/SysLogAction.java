package com.xy.cms.action.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.SysLog;
import com.xy.cms.service.SysLogService;

public class SysLogAction extends BaseAction {

	
	private static final long serialVersionUID = 1L;
	
	private SysLog sysLog;

	private SysLogService sysLogService;

	public String init() {
		return "init";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryLog() {
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				pageMap.put("sysLog", sysLog);
				pageMap.put("beginDate", request.getParameter("beginDate"));
				pageMap.put("endDate", request.getParameter("endDate"));
				return sysLogService.querySysLog(pageMap);
			}
		});
		return "list";
	}

	public SysLog getSysLog() {
		return sysLog;
	}

	public void setSysLog(SysLog sysLog) {
		this.sysLog = sysLog;
	}

	public SysLogService getSysLogService() {
		return sysLogService;
	}

	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}
	
	
}
