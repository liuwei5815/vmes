package com.xy.cms.service;

import java.util.List;

import com.xy.cms.entity.ProduceplanExceptionHandle;
import com.xy.cms.entity.base.BaseProduceplanExceptionHandle.Status;

public interface ProduceplanExceptionHandleService {
	
	/**
	 * 通过主键id得到ProduceplanExceptionHandle
	 * @param id 主键id
	 * @return
	 * */
	public ProduceplanExceptionHandle getPehById(Long id);
	/**
	 * 通过计划id得到ProduceplanExceptionHandle
	 * @param id 主键id
	 * @return
	 * */
	public ProduceplanExceptionHandle getPehByProId(Long id);
	
	
	public ProduceplanExceptionHandle getHandleByPlanIdAndState(Long planId,Status status);
	
	
	/***
	 * 根据计划id和状态批量得到计划异常处理流
	 * @param planId
	 * @param status
	 * @return
	 */
	public List<ProduceplanExceptionHandle> getHandleByPlanIdsAndState(Long[] planIds,Status status);
	
}
