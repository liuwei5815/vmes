package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.OrdersDetail;
import com.xy.cms.service.OrdersDetailService;

public class OrdersDetailServiceImpl extends BaseDAO implements OrdersDetailService{

	@Override
	public List queryOrdersDetailByOid(Long ordersId)
			throws BusinessException {
		String hql = "select p.productCode,p.name,p.typespec,p.unit,d.num,d.deliveryDate,d.remarks,d.id from OrdersDetail d,Product p where d.orderId=:ordersId and d.productId=p.id";
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("ordersId",ordersId);
		return this.getList(hql, param);
	}

	@Override
	public OrdersDetail getOrdersDetailById(Long ordersDetailId){
		return (OrdersDetail) this.get(OrdersDetail.class, ordersDetailId);
	}

}
