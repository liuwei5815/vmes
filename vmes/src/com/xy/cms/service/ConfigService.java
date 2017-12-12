package com.xy.cms.service;

import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Config;

public interface ConfigService {
	
	/**
	 * 获取所有缓存中的配置
	 * @return
	 * @throws BusinessException
	 */
	Map getAllConfig() throws BusinessException;
	
	/**
	 * 分页查询信息
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	QueryResult queryConfigPage(Map param) throws BusinessException;
	

	/**
	 * 保存信息
	 * @param user
	 * @throws BusinessException
	 */
	public void saveConfig(Config config) throws BusinessException;
	

	
	/**
	 * 根据ID查询信息
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Config getConfig(Integer id) throws BusinessException;
	
	/**
	 * 更新信息 
	 * @throws BusinessException
	 */
	public void upateConfig(Config config) throws BusinessException; 
	
	/**
	 * 根据ID删除信息
	 * @param id
	 * @throws BusinessException
	 */
	public void removeConfig(Integer id) throws BusinessException;
	
}
