package com.xy.admx.service;

import java.util.Map;

import com.xy.admx.common.base.PageQEntity;
import com.xy.admx.common.exception.BusinessException;
import com.xy.admx.core.service.base.BaseService;

public interface OrdersService extends BaseService {

	/**
	 * 分页查询所有订单
	 * @param pageEntity
	 * @throws BusinessException
	 */
	public void queryOrdersPage(PageQEntity pageEntity,Map param) throws Exception;
}
