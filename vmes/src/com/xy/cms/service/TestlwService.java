package com.xy.cms.service;

import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Config;
import com.xy.cms.entity.Test;
import com.xy.cms.entity.Test_lw;

public interface TestlwService {
	/**
	 * 获取所有缓存中的配置
	 * @return
	 * @throws BusinessException
	 */
	Map getAllTestlw() throws BusinessException;
	
	/**
	 * 分页查询信息
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	QueryResult queryTestlwPage(Map param) throws BusinessException;
	

	/**
	 * 保存信息
	 * @param user
	 * @throws BusinessException
	 */
	public void saveTestlw(Test_lw test) throws BusinessException;
	

	
	/**
	 * 根据ID查询信息
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Test_lw getTestlw(long id) throws BusinessException;
	
	/**
	 * 更新信息 
	 * @throws BusinessException
	 */
	public void upateTestlw(Test_lw test) throws BusinessException; 
	
	/**
	 * 根据ID删除信息
	 * @param id
	 * @throws BusinessException
	 */
	public void removeTestlw(long id) throws BusinessException;
}
