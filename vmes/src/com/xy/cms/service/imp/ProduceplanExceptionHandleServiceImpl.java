package com.xy.cms.service.imp;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.entity.ProduceplanExceptionHandle;
import com.xy.cms.entity.base.BaseProduceplanExceptionHandle;
import com.xy.cms.entity.base.BaseProduceplanExceptionHandle.Status;
import com.xy.cms.service.ProduceplanExceptionHandleService;

public class ProduceplanExceptionHandleServiceImpl extends BaseDAO implements  ProduceplanExceptionHandleService{

	@Override
	public ProduceplanExceptionHandle getPehById(Long id) {
		return (ProduceplanExceptionHandle) this.get(ProduceplanExceptionHandle.class, id);
	}

	@Override
	public ProduceplanExceptionHandle getPehByProId(Long id) {
		String hql="from ProduceplanExceptionHandle peh where peh.produceplanId=:id";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		return (ProduceplanExceptionHandle)this.getUniqueResult(hql, map);
	}

	@Override
	public ProduceplanExceptionHandle getHandleByPlanIdAndState(Long planId,
			Status status) {
		String hql = "from ProduceplanExceptionHandle where produceplanId=:planId and state=:state";
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("planId", planId);
		paramMap.put("state", status.getCode());
		return (ProduceplanExceptionHandle) this.getFirstResult(hql, paramMap);
	}

	@Override
	public List<ProduceplanExceptionHandle> getHandleByPlanIdsAndState(Long[] planIds,
			Status status) {
		String hql = "from ProduceplanExceptionHandle where produceplanId in (:planId) and state=:state";
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("planId", planIds);
		paramMap.put("state", status.getCode());
		return this.getList(hql, paramMap);
	}


}
