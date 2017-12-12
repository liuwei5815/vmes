package com.xy.cms.service;

import java.util.List;

import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.OrdersDetail;

public interface OrdersDetailService {
	
	/**
	 * 通过OrdersId查询OrdersDetail以及产品
	 * @param OrdersId 订单id
	 * */
	public List queryOrdersDetailByOid(Long ordersId)throws BusinessException;
	
	/**
	 * 通过销售明细id得到销售明细
	 * @param ordersDetailId 销售明细主键id
	 * @return
	 * */
	public OrdersDetail getOrdersDetailById(Long ordersDetailId);
}
