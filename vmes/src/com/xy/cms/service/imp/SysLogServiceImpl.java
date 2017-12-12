package com.xy.cms.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.SysLog;
import com.xy.cms.service.SysLogService;

public class SysLogServiceImpl extends BaseDAO implements SysLogService{

	/* (non-Javadoc)
	 * @see com.xy.cms.service.imp.SysLogService#getAllRole()
	 */
	@Override
	public List<SysLog> getAllRole() throws BusinessException {
		try {
			return this.getAll(SysLog.class);
		} catch (DataAccessException e) {
			throw new BusinessException("查询日志信息异常:"+e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.xy.cms.service.imp.SysLogService#queryTables(java.util.Map)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public QueryResult querySysLog(Map<String, Object> map)
			throws BusinessException {
		QueryResult result = null;
		Map m = new HashMap();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		SysLog sysLog = (SysLog) map.get("sysLog");
		String beginDate=(String) map.get("beginDate");
		String endDate=(String) map.get("endDate");
		StringBuffer hql=new StringBuffer("from SysLog log where 1=1");
		if(sysLog!=null){
			if(CommonFunction.isNotNull(sysLog.getUserAccount())){
				hql.append(" and log.userAccount like :userAccount");
				m.put("userAccount", "%"+sysLog.getUserAccount()+"%");
			}
			/*if((beginDate!=null && !beginDate.equals("")) || (endDate!=null && !endDate.equals(""))){
				hql.append(" and log.time between '"+beginDate+"' and '"+endDate+" 23:59:59'");
			}*/
			if(CommonFunction.isNotNull(beginDate)){
				hql.append(" and log.time >=:beginDate");
				m.put("beginDate", beginDate);
			}
			if(CommonFunction.isNotNull(endDate)){
				hql.append(" and log.time <=:endDate ");
				m.put("endDate", endDate+" 23:59:59");
			}
			
		}
		hql.append(" order by time desc");
		result = this.getPageQueryResult(hql.toString(), m, qEntity);
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(new Date().toString());
	}
}
