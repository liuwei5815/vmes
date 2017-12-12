package com.xy.admx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.xy.admx.common.base.PageQEntity;
import com.xy.admx.core.service.base.BaseServiceImpl;
import com.xy.admx.service.OrdersService;


@Service
public class OrdersServiceImpl extends BaseServiceImpl  implements OrdersService{

	@Override
	public void queryOrdersPage(PageQEntity pageEntity, Map param) throws Exception {
		StringBuffer sql = new StringBuffer("select o.id,o.order_code,o.order_code_auto,o.order_date,o.delivery_date,c.name as customer_name");
		sql.append(" from orders o left join customer c on c.id=o.customer_id where 1=1 ");
		Map queryMap = new HashMap();
		if(param != null){
			if(param.get("state") != null){
				Integer state = (Integer)param.get("state");
				sql.append(" and o.state=:state ");
				queryMap.put("state", state);
			}
			if(param.get("deliverydateEnd") != null && StringUtils.isNotBlank(param.get("deliverydateEnd").toString())){
				String deliverydateEnd = param.get("deliverydateEnd").toString();
				sql.append(" and o.delivery_date <=:deliverydateEnd ");
				queryMap.put("deliverydateEnd", deliverydateEnd);
			}
			if(param.get("deliverydateStart") != null && StringUtils.isNotBlank(param.get("deliverydateStart").toString())){
				String deliverydateStart = param.get("deliverydateStart").toString();
				sql.append(" and o.delivery_date >=:deliverydateStart ");
				queryMap.put("deliverydateStart", deliverydateStart);
			}
			if(param.get("finishedEnd") != null && StringUtils.isNotBlank(param.get("finishedEnd").toString())){
				String finishedEnd = param.get("finishedEnd").toString();
				sql.append(" and o.finished_date <=:finishedEnd ");
				queryMap.put("finishedEnd", finishedEnd);
			}
			if(param.get("finishedStart") != null && StringUtils.isNotBlank(param.get("finishedStart").toString())){
				String finishedStart = param.get("finishedStart").toString();
				sql.append(" and o.finish_time >=:finishedStart ");
				queryMap.put("finishedStart", finishedStart);
			}
			if(param.get("canceledEnd") != null && StringUtils.isNotBlank(param.get("canceledEnd").toString())){
				String canceledEnd = param.get("canceledEnd").toString();
				sql.append(" and o.cancel_date <=:canceledEnd ");
				queryMap.put("canceledEnd", canceledEnd);
			}
			if(param.get("canceledStart") != null && StringUtils.isNotBlank(param.get("canceledStart").toString())){
				String canceledStart = param.get("canceledStart").toString();
				sql.append(" and o.cancel_date >=:canceledStart ");
				queryMap.put("canceledStart", canceledStart);
			}
			if(param.get("customer") != null && StringUtils.isNotBlank(param.get("customer").toString())){
				String customer = param.get("customer").toString();
				sql.append(" and c.name like :cname ");
				queryMap.put("cname", "%" + customer + "%");
			}
			if(param.get("prodname") != null && StringUtils.isNotBlank(param.get("prodname").toString())){
				String prodname = param.get("prodname").toString();
				sql.append("and exists (select 1 ");
				sql.append(" from orders_detail d left join product p on d.pro_id=p.id where d.order_id=o.id and p.name like :pname)");
				queryMap.put("pname", "%" + prodname + "%");
			}
		}
		sql.append("order by o.delivery_date asc ");
		this.getPageQueryResultSQLToMap(sql.toString(), queryMap, pageEntity);
		List list = pageEntity.getList();
		
		if(list == null){
			return ;
		}
		for(int i = 0 ; i < list.size() ;i++){
			Map map = (Map)list.get(i);
			Long orderId = Long.valueOf(map.get("id").toString());
			List<Map> details = queryOrdersDetailsByOrderId(orderId);
			map.put("details", details);
		}
		pageEntity.setList(list);
		
	}
	
	private List<Map> queryOrdersDetailsByOrderId(Long orderId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select d.id,d.pro_id,d.num,d.state,d.finish_time,d.deliveryd_date,d.add_date,p.name as prodName,p.product_code,p.typespec ");
		sql.append(" from orders_detail d left join product p on d.pro_id=p.id where d.order_id=:orderId  ");
		Map param = new HashMap();
		param.put("orderId", orderId);
		List list = this.getListBySQLToMap(sql.toString(),param);
		return list;
	}
}
