package com.xy.cms.service;

import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Config;
import com.xy.cms.entity.Test;
import com.xy.cms.entity.Test_wk;

public interface TestwkService {
	/**
	 * 获取所有缓存中的配置
	 * @return
	 * @throws BusinessException
	 */
	Map getAllTestwk() throws BusinessException;
	
	/**
	 * 分页查询信息
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	QueryResult queryTestwkPage(Map param) throws BusinessException;
	

	/**
	 * 保存信息
	 * @param user
	 * @throws BusinessException
	 */
	public void saveTestwk(Test_wk test) throws BusinessException;
	

	
	/**
	 * 根据ID查询信息
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Test_wk getTestwk(long id) throws BusinessException;
	
	/**
	 * 更新信息 
	 * @throws BusinessException
	 */
	public void upateTestwk(Test_wk test) throws BusinessException; 
	
	/**
	 * 根据ID删除信息
	 * @param id
	 * @throws BusinessException
	 */
	public void removeTestwk(long id) throws BusinessException;
}
