package com.xy.cms.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.DataAccessException;

import com.xy.cms.common.CachePool;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Config;
import com.xy.cms.entity.Test;
import com.xy.cms.entity.CustomerType;
import com.xy.cms.service.ConfigService;
import com.xy.cms.service.TestService;
import com.xy.cms.service.CustomerTypeService;


/**
 * 
 * @author Administrator
 */
public class CustomerTypeServiceImpl extends BaseDAO implements CustomerTypeService{
	

	public QueryResult queryCustomerTypePage(Map param) throws BusinessException {
		try{
			QueryResult result = null;
			BaseQEntity qEntity = (BaseQEntity) param.get("qEntity"); 
			StringBuffer sql = new StringBuffer();
			Map<String, Object> m = new HashMap<String, Object>();
			sql.append("from CustomerType");
			//名称
			if(CommonFunction.isNotNull(param.get("name"))){
				sql.append(" where name like:name ");
				m.put("name", "%"+param.get("name")+"%");
			}
			result = this.getPageQueryResult(sql.toString(), m, qEntity);
			return result;
		}
		catch (Exception e) {
			throw new BusinessException("分页查询列表出现异常  Error:"+e.getMessage());
		}
	}



	@Override
	public void saveCustomerType(CustomerType test) throws BusinessException{
		try {
			if(test == null){
				throw new BusinessException("参数错误");
			}
			String uuid = UUID.randomUUID().toString();
			test.setId(uuid);
			this.save(test);
		} catch (Exception e) {
			throw new BusinessException("添加信息异常："+e.getMessage(),e);
		}
	}

	
	@Override
	public CustomerType getCustomerType(long id) throws BusinessException{
		CustomerType banner = (CustomerType) this.get(CustomerType.class,id);
		return banner;
	}

	public void upateCustomerType(CustomerType test) throws BusinessException{
		try {
			this.update(test);
		} catch (DataAccessException e) {
			throw new BusinessException("修改异常："+e.getMessage(),e);
		}
	}

	@Override
	public Map getAllCustomerType() throws BusinessException {
		return CachePool.config;
	}




	@Override
	public void removeCustomerType(long id) throws BusinessException {
		CustomerType test = this.getCustomerType(id);
		
		if(test == null)
			throw new BusinessException("查询对象为空");
		
		this.delete(test);
	}









	
}