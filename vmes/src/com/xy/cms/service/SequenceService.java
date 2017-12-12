package com.xy.cms.service;

import com.xy.cms.entity.Sequence;


public interface SequenceService {
	/**
	 * 累加并返回最新的编号
	 * @param colId 字段id
	 * @return
	 */
	public Sequence getLatestAndIncrement(Long colId);
	
	
	/***
	 * 根据字段id得到最新的字段编号
	 * @param tableColumns
	 * @return
	 */
	public String getNewNoByTableColumns(Long tableColumnsId);
	
}
