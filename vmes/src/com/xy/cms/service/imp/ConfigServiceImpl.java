package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.xy.cms.common.CachePool;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Config;
import com.xy.cms.service.ConfigService;


/**
 * 
 * @author Administrator
 */
public class ConfigServiceImpl extends BaseDAO implements ConfigService{

	public QueryResult queryConfigPage(Map param) throws BusinessException {
		try{
			QueryResult result = null;
			BaseQEntity qEntity = (BaseQEntity) param.get("qEntity"); 
			StringBuffer hql = new StringBuffer();
			Map<String, Object> m = new HashMap<String, Object>();
			hql.append("from Config"); 
			result = this.getPageQueryResult(hql.toString(), m, qEntity);
			return result;
		}
		catch (Exception e) {
			throw new BusinessException("分页查询列表出现异常  Error:"+e.getMessage());
		}
	}




	public void saveConfig(Config config) throws BusinessException{
		try {
			if(config == null){
				throw new BusinessException("参数错误");
			}
			this.save(config);
		} catch (Exception e) {
			throw new BusinessException("添加信息异常："+e.getMessage(),e);
		}
	}

	

	public Config getConfig(Integer id) throws BusinessException{
		Config banner = (Config) this.get(Config.class,id);
		return banner;
	}

	public void upateConfig(Config config) throws BusinessException{
		try {
			config.setConfvalue(config.getConfvalue().replaceAll("\\s*|\t|\r|\n", ""));
			this.update(config);
		} catch (DataAccessException e) {
			throw new BusinessException("修改异常："+e.getMessage(),e);
		}
	}

	@Override
	public Map getAllConfig() throws BusinessException {
		return CachePool.config;
	}




	@Override
	public void removeConfig(Integer id) throws BusinessException {
		Config config = this.getConfig(id);
		
		if(config == null)
			throw new BusinessException("查询对象为空");
		
		this.delete(config);
	}
	
}