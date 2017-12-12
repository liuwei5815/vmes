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
import com.xy.cms.entity.Test;
import com.xy.cms.service.ConfigService;
import com.xy.cms.service.TestService;


/**
 * 
 * @author Administrator
 */
public class TestServiceImpl extends BaseDAO implements TestService{

	public QueryResult queryTestPage(Map param) throws BusinessException {
		try{
			QueryResult result = null;
			BaseQEntity qEntity = (BaseQEntity) param.get("qEntity"); 
			StringBuffer sql = new StringBuffer();
			Map<String, Object> m = new HashMap<String, Object>();
			sql.append("from Test");
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




	public void saveTest(Test test) throws BusinessException{
		try {
			if(test == null){
				throw new BusinessException("参数错误");
			}
			this.save(test);
		} catch (Exception e) {
			throw new BusinessException("添加信息异常："+e.getMessage(),e);
		}
	}

	

	public Test getTest(Integer id) throws BusinessException{
		Test banner = (Test) this.get(Test.class,id);
		return banner;
	}

	public void upateTest(Test test) throws BusinessException{
		try {
			this.update(test);
		} catch (DataAccessException e) {
			throw new BusinessException("修改异常："+e.getMessage(),e);
		}
	}

	@Override
	public Map getAllTest() throws BusinessException {
		return CachePool.config;
	}




	@Override
	public void removeTest(Integer id) throws BusinessException {
		Test test = this.getTest(id);
		
		if(test == null)
			throw new BusinessException("查询对象为空");
		
		this.delete(test);
	}
	
}