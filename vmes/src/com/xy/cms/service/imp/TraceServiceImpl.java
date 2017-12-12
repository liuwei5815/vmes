package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.service.TraceService;

public class TraceServiceImpl extends BaseDAO implements TraceService{

	@Override
	public QueryResult getAllProduceplanRelationOrdersDetail(Map pageMap) throws BusinessException {
		Map<String,Object> param=new HashMap<String,Object>();
		QueryResult result = new QueryResult();
		BaseQEntity qEntity = (BaseQEntity) pageMap.get("qEntity");
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from ( ");
		sql.append(" select cus.name AS cusName,detail.id AS detailId,detail.product_code,detail.product_name,detail.product_typespec,detail.num AS detailNum,detail.deliveryd_date,detail.state AS detailState,detail.product_unit");
		sql.append(" ,o.order_code_auto,o.order_code ");
		sql.append(" ,plan.id AS planId ,plan.manufacture_code,plan.plan_code,plan.num AS planNum,plan.qualified_num,plan.state AS planState ,plan.start_date AS planStartDate,plan.end_date AS planEndDte ");
		sql.append(" ,peh.state as pehState,peh.id AS pehId ");
		sql.append(" FROM orders_detail detail ");
		sql.append(" LEFT JOIN orders o ON o.id=detail.order_id ");
		sql.append(" LEFT JOIN produceplan plan ON plan.order_detail_id=detail.id  ");
		sql.append(" LEFT JOIN customer cus ON o.customer_id=cus.id ");
		sql.append(" LEFT JOIN produceplan_exception_handle peh ON peh.produceplan_id=plan.id where 1=1 ");
		sql.append(" and detail.deliveryd_date >=:orderStartDate AND detail.deliveryd_date<=:orderEndDate ");
		//订单日期
		param.put("orderStartDate",pageMap.get("orderStartDate"));
		param.put("orderEndDate",pageMap.get("orderEndDate"));
		//客户名称
		if(CommonFunction.isNotNull(pageMap.get("customerName"))){
			sql.append(" and cus.name like:cusName ");
			param.put("cusName","%"+pageMap.get("customerName")+"%");
		}
		//产品名称
		if(CommonFunction.isNotNull(pageMap.get("productName"))){
			sql.append(" and  detail.product_name like:productName ");
			param.put("productName", "%"+pageMap.get("productName")+"%");
		}
		//产品编号
		if(CommonFunction.isNotNull(pageMap.get("productCode"))){
			sql.append(" and  detail.product_code like:productCode ");
			param.put("productCode", "%"+pageMap.get("productCode")+"%");
		}
		//规格型号
		if(CommonFunction.isNotNull(pageMap.get("productTypespec"))){
			sql.append(" and  detail.product_typespec like:productTypespec ");
			param.put("productTypespec", "%"+pageMap.get("productTypespec")+"%");
		}
		//订单编号
		if(CommonFunction.isNotNull(pageMap.get("orderCode"))){
			sql.append(" and  (o.order_code like:orderCode or o.order_code_auto like:orderCode) ");
			param.put("orderCode", "%"+pageMap.get("orderCode")+"%");
		}
		//生产计划编号
		if(CommonFunction.isNotNull(pageMap.get("manufactureCode"))){
			sql.append(" and  (plan.manufacture_code like:manufactureCode or plan.plan_code like:manufactureCode)");
			param.put("manufactureCode", "%"+pageMap.get("manufactureCode")+"%");
		}
		//生产计划状态
		if(CommonFunction.isNotNull(pageMap.get("produceplanState"))){
			sql.append(" and  plan.state=:produceplanState ");
			param.put("produceplanState",pageMap.get("produceplanState"));
		}
		sql.append(" UNION ");
		sql.append(" SELECT cus.name AS cusName,detail.id AS detailId,detail.product_code,detail.product_name,detail.product_typespec,detail.num AS detailNum,detail.deliveryd_date,detail.state AS detailState,detail.product_unit ");
		sql.append(" ,o.order_code_auto,o.order_code ");
		sql.append(" ,plan.id AS planId ,plan.manufacture_code,plan.plan_code,plan.num AS planNum,plan.qualified_num,plan.state AS planState,plan.start_date AS planStartDate,plan.end_date AS planEndDte ");
		sql.append(" ,peh.state AS pehState,peh.id AS pehId ");
		sql.append(" FROM produceplan plan  ");
		sql.append(" LEFT JOIN orders_detail detail ON plan.order_detail_id=detail.id  ");
		sql.append(" LEFT JOIN orders o ON detail.order_id=o.id  ");
		sql.append(" LEFT JOIN customer cus ON o.customer_id=cus.id ");
		sql.append(" LEFT JOIN produceplan_exception_handle peh ON peh.produceplan_id=plan.id WHERE plan.order_detail_id IS NULL ");
		sql.append(" AND plan.start_date>=:planStartDate AND plan.start_date<=:planEndDate ");
		//生产时间
		param.put("planStartDate",pageMap.get("planStartDate"));
		param.put("planEndDate",pageMap.get("planEndDate"));
		//客户名称
		if(CommonFunction.isNotNull(pageMap.get("customerName"))){
			sql.append(" and cus.name like:cusName ");
			param.put("cusName","%"+pageMap.get("customerName")+"%");
		}
		//产品名称
		if(CommonFunction.isNotNull(pageMap.get("productName"))){
			sql.append(" and  detail.product_name like:productName ");
			param.put("productName", "%"+pageMap.get("productName")+"%");
		}
		//产品编号
		if(CommonFunction.isNotNull(pageMap.get("productCode"))){
			sql.append(" and  detail.product_code like:productCode ");
			param.put("productCode", "%"+pageMap.get("productCode")+"%");
		}
		//规格型号
		if(CommonFunction.isNotNull(pageMap.get("productTypespec"))){
			sql.append(" and  detail.product_typespec like:productTypespec ");
			param.put("productTypespec", "%"+pageMap.get("productTypespec")+"%");
		}
		//订单编号
		if(CommonFunction.isNotNull(pageMap.get("orderCode"))){
			sql.append(" and  (o.order_code like:orderCode or o.order_code_auto like:orderCode) ");
			param.put("orderCode", "%"+pageMap.get("orderCode")+"%");
		}
		//生产计划编号
		if(CommonFunction.isNotNull(pageMap.get("manufactureCode"))){
			sql.append(" and  (plan.manufacture_code like:manufactureCode or plan.plan_code like:manufactureCode)");
			param.put("manufactureCode", "%"+pageMap.get("manufactureCode")+"%");
		}
		//生产计划状态
		if(CommonFunction.isNotNull(pageMap.get("produceplanState"))){
			sql.append(" and  plan.state=:produceplanState ");
			param.put("produceplanState",pageMap.get("produceplanState"));
		}
		/*sql.append(" ) AS a where a.planState=3 ORDER BY CONV(a.planState,10,2),a.deliveryd_date  ");*/
		sql.append(" ) AS a where a.planState=3 ORDER BY a.deliveryd_date desc ");
		
		System.out.println("sss======"+sql.toString());
		result= this.getPageQueryResultSQLToMap(sql.toString(), param,qEntity);
		return result;
	}

}
