package com.xy.cms.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Orders;
import com.xy.cms.entity.OrdersDetail;
import com.xy.cms.entity.OrdersDetail.OrdersDetailStatus;
import com.xy.cms.entity.Produceplan;
import com.xy.cms.entity.ProduceplanExceptionHandle;
import com.xy.cms.entity.ProduceplanTodo;
import com.xy.cms.entity.Product;
import com.xy.cms.service.OrdersDetailService;
import com.xy.cms.service.OrdersService;
import com.xy.cms.service.ProduceplanExceptionHandleService;
import com.xy.cms.service.ProduceplanService;
import com.xy.cms.service.ProduceplanTodoClaimService;
import com.xy.cms.service.ProductService;
import com.xy.cms.service.SequenceService;

public class ProduceplanServiceImpl extends BaseDAO implements ProduceplanService{
	private SequenceService sequenceService;
	private OrdersDetailService ordersDetailService;
	private OrdersService ordersService;
	private ProductService productService;
	private ProduceplanExceptionHandleService produceplanExceptionHandleService;
	private ProduceplanTodoClaimService produceplanTodoClaimService;
	@Override
	public Produceplan getProduceplanById(Long id) {
		return (Produceplan) this.get(Produceplan.class, id);
	}
	
	/**
	 * 通过主键得到生成计划
	 * */
	public Object[] getProduceplanAndProductById(Long id){
		StringBuffer hql= new StringBuffer("select plan,product from Produceplan plan,Product product where plan.productId = product.id ");
		hql.append(" and plan.id=:planId");
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("planId", id);
		return (Object[]) this.getFirstResult(hql.toString(), param);
	}
	
	@Override
	public QueryResult queryAllProduceplan(Map pageMap)
			throws BusinessException {
		Map<String,Object> param = new HashMap<String, Object>();
		QueryResult result = null;
		BaseQEntity qEntity = (BaseQEntity) pageMap.get("qEntity");
		StringBuffer hql= new StringBuffer("select plan,product,e from Produceplan plan,Product product,Employee e where plan.productId = product.id ");
		result = this.getPageQueryResult(hql.toString(), param, qEntity);
		return result;
	}

	@Override
	public void addNoOrderProduceplan(Produceplan produceplan)throws BusinessException{
		Produceplan newProduceplan = new Produceplan();
		//生产计划编号(客户输入)
		newProduceplan.setManufactureCode(produceplan.getManufactureCode());
		//自动编号
		String planCode=sequenceService.getNewNoByTableColumns(577L);
		newProduceplan.setPlanCode(planCode);
		//计划执行开始时间
		newProduceplan.setStartDate(produceplan.getStartDate());
		//计划执行结束时间
		newProduceplan.setEndDate(produceplan.getEndDate());
		//产品信息id 
		newProduceplan.setProductId(produceplan.getProductId());
		//生产数量  
		newProduceplan.setNum(produceplan.getNum());
		//状态
		newProduceplan.setState(Produceplan.Status.watingtodo.getCode());
		//添加时间
		newProduceplan.setAddDate(new Date());
		
		//保存冗余的产品信息
		Product product = productService.getProductById(newProduceplan.getProductId());
		newProduceplan.setProductCode(product.getProductCode());
		newProduceplan.setProductName(product.getName());
		newProduceplan.setProductTypespec(product.getTypespec());
		newProduceplan.setProductUnit(product.getUnit());
		this.save(newProduceplan);
	}

	@Override
	public void editNoOrderProduceplan(Produceplan produceplan)
			throws BusinessException {
		//得到Produceplan
		Produceplan newProduceplan = getProduceplanById(produceplan.getId());
		//生产计划编号(客户输入)
		newProduceplan.setManufactureCode(produceplan.getManufactureCode());
		//结束时间
		newProduceplan.setEndDate(produceplan.getEndDate());
		//开始时间
		newProduceplan.setStartDate(produceplan.getStartDate());
		//修改时间
		newProduceplan.setUpdateDate(new Date());
		this.update(newProduceplan);
	}
	
	@Override
	public void editNoOrderProduceplanNum(String pehId,Produceplan produceplan)
			throws BusinessException {
		//得到Produceplan
		Produceplan newProduceplan = getProduceplanById(produceplan.getId());
		//修改前的数量
		Long num = newProduceplan.getNum();
		//数量
		newProduceplan.setNum(produceplan.getNum());
		//修改时间
		newProduceplan.setUpdateDate(new Date());
		this.update(newProduceplan);
		//根据计划查找派工单
		if(CommonFunction.isNotNull(pehId)){
			//修改
			ProduceplanExceptionHandle produceplanExceptionHandle = produceplanExceptionHandleService.getPehById(Long.parseLong(pehId));
			produceplanExceptionHandle.setState(ProduceplanExceptionHandle.Status.dispatch.getCode());
			produceplanExceptionHandle.setOldPlanNum(num);
			produceplanExceptionHandle.setPlanNum(newProduceplan.getNum());
			this.update(produceplanExceptionHandle);
		}else{
			//新增
			ProduceplanExceptionHandle newProduceplanExceptionHandle = new ProduceplanExceptionHandle();
			newProduceplanExceptionHandle.setProduceplanId(newProduceplan.getId());
			newProduceplanExceptionHandle.setState(ProduceplanExceptionHandle.Status.dispatch.getCode());
			newProduceplanExceptionHandle.setOldPlanNum(num);
			newProduceplanExceptionHandle.setPlanNum(newProduceplan.getNum());
			this.save(newProduceplanExceptionHandle);
		}
	}
	
	@Override
	public void delNoOrderProduceplan(Produceplan produceplan)
			throws BusinessException {
		this.delete(produceplan);
	}

	@Override
	public Object queryProduceplanById(Long id) throws BusinessException {
		/*p1.startDate as startDate,p1.endDate as endDate,p1.productId as productId,p1.productId as productId,p1.state  as state ,p1.planCode as planCode,p2.id as p2id,*/
		String hql = "select p1,p2 where p1.id=:id and p1.productId=p2.id";
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		Object a = this.getQuery(hql, param);
		return this.getQuery(hql, param);
	}

	@Override
	public void addYesOrderProduceplan(List<Produceplan> produceplanList)
			throws BusinessException {
		for(Produceplan pro : produceplanList){
			this.save(pro);
		}
	}
	

	@Override
	public QueryResult getAllProduceplanRelationOrdersDetail(Map pageMap)throws BusinessException {
		Map<String,Object> param=new HashMap<String,Object>();
		QueryResult result = new QueryResult();
		BaseQEntity qEntity = (BaseQEntity) pageMap.get("qEntity");
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from ( ");
		sql.append(" select detail.id AS detailId,detail.product_code,detail.product_name,detail.product_typespec,detail.num AS detailNum,detail.deliveryd_date,detail.state AS detailState,detail.product_unit");
		sql.append(" ,o.order_code_auto,o.order_code ");
		sql.append(" ,plan.id AS planId ,plan.manufacture_code,plan.plan_code,plan.num AS planNum,plan.qualified_num,plan.state AS planState ,plan.start_date AS planStartDate,plan.end_date AS planEndDte ");
		sql.append(" ,peh.state as pehState,peh.id AS pehId ");
		sql.append(" FROM orders_detail detail ");
		sql.append(" LEFT JOIN orders o ON o.id=detail.order_id ");
		sql.append(" LEFT JOIN produceplan plan ON plan.order_detail_id=detail.id  ");
		sql.append(" LEFT JOIN produceplan_exception_handle peh ON peh.produceplan_id=plan.id where 1=1 ");
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
		sql.append(" SELECT detail.id AS detailId,detail.product_code,detail.product_name,detail.product_typespec,detail.num AS detailNum,detail.deliveryd_date,detail.state AS detailState,detail.product_unit ");
		sql.append(" ,o.order_code_auto,o.order_code ");
		sql.append(" ,plan.id AS planId ,plan.manufacture_code,plan.plan_code,plan.num AS planNum,plan.qualified_num,plan.state AS planState,plan.start_date AS planStartDate,plan.end_date AS planEndDte ");
		sql.append(" ,peh.state AS pehState,peh.id AS pehId ");
		sql.append(" FROM produceplan plan  ");
		sql.append(" LEFT JOIN orders_detail detail ON plan.order_detail_id=detail.id  ");
		sql.append(" LEFT JOIN orders o ON detail.order_id=o.id  ");
		sql.append(" LEFT JOIN produceplan_exception_handle peh ON peh.produceplan_id=plan.id WHERE plan.order_detail_id IS NULL ");
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
		sql.append(" ) AS a ORDER BY CONV(a.planState,10,2),a.deliveryd_date  ");
				/*sql.append(" UNION ");
				sql.append("select * from (select plan.id as planId,detail.id as detailId,ord.order_code,plan.product_code,plan.product_name,plan.product_typespec,plan.product_unit,detail.num as detailNum,detail.deliveryd_date,detail.remarks,plan.manufacture_code,plan.num as planNum,plan.start_date,plan.end_date,plan.state,peh.id,peh.state as pehState,plan.plan_code,ord.order_code_auto,detail.add_date AS ddate,plan.qualified_num from  produceplan plan LEFT JOIN orders_detail detail ON plan.order_detail_id=detail.id LEFT JOIN orders ord ON detail.order_id=ord.id LEFT JOIN produceplan_exception_handle peh ON peh.produceplan_id=plan.id where 1=1");
				//产品名称
				if(CommonFunction.isNotNull(pageMap.get("productName"))){
					sql.append(" and  plan.product_name like:productName ");
					par.put("productName", "%"+pageMap.get("productName")+"%");
				}
				//产品编号
				if(CommonFunction.isNotNull(pageMap.get("productCode"))){
					sql.append(" and  plan.product_code like:productCode ");
					par.put("productCode", "%"+pageMap.get("productCode")+"%");
				}
				//规格型号
				if(CommonFunction.isNotNull(pageMap.get("productTypespec"))){
					sql.append(" and  plan.product_typespec like:productTypespec ");
					par.put("productTypespec", "%"+pageMap.get("productTypespec")+"%");
				}
				//订单编号
				if(CommonFunction.isNotNull(pageMap.get("orderCode"))){
					sql.append(" and  ord.order_code like:orderCode ");
					par.put("orderCode", "%"+pageMap.get("orderCode")+"%");
				}
				//生产计划编号
				if(CommonFunction.isNotNull(pageMap.get("manufactureCode"))){
					sql.append(" and  (plan.manufacture_code like:manufactureCode or plan.plan_code like:manufactureCode)");
					par.put("manufactureCode", "%"+pageMap.get("manufactureCode")+"%");
				}
				//生产计划状态
				if(CommonFunction.isNotNull(pageMap.get("produceplanState"))){
					sql.append(" and  plan.state=:produceplanState ");
					par.put("produceplanState", pageMap.get("produceplanState"));
				}

		
		/*StringBuffer sql= new StringBuffer("select * from (select plan.id as planId,detail.id as detailId,ord.order_code,detail.product_code,detail.product_name,detail.product_typespec,detail.product_unit,detail.num as detailNum,detail.deliveryd_date,detail.remarks,plan.manufacture_code,plan.num as planNum,plan.start_date,plan.end_date,plan.state, peh.id,peh.state as pehState,plan.plan_code,ord.order_code_auto,detail.add_date as ddate,plan.qualified_num FROM orders_detail detail LEFT JOIN orders ord ON detail.order_id=ord.id LEFT JOIN produceplan plan ON plan.order_detail_id=detail.id LEFT JOIN produceplan_exception_handle peh ON peh.produceplan_id=plan.id where 1=1");
		//产品名称
		if(CommonFunction.isNotNull(pageMap.get("productName"))){
			sql.append(" and  detail.product_name like:productName ");
			par.put("productName", "%"+pageMap.get("productName")+"%");
		}
		//产品编号
		if(CommonFunction.isNotNull(pageMap.get("productCode"))){
			sql.append(" and  detail.product_code like:productCode ");
			par.put("productCode", "%"+pageMap.get("productCode")+"%");
		}
		//规格型号
		if(CommonFunction.isNotNull(pageMap.get("productTypespec"))){
			sql.append(" and  detail.product_typespec like:productTypespec ");
			par.put("productTypespec", "%"+pageMap.get("productTypespec")+"%");
		}
		//订单编号
		if(CommonFunction.isNotNull(pageMap.get("orderCode"))){
			sql.append(" and  (ord.order_code like:orderCode or ord.order_code_auto like:orderCode) ");
			par.put("orderCode", "%"+pageMap.get("orderCode")+"%");
		}
		//生产计划编号
		if(CommonFunction.isNotNull(pageMap.get("manufactureCode"))){
			sql.append(" and  (plan.manufacture_code like:manufactureCode or plan.plan_code like:manufactureCode)");
			par.put("manufactureCode", "%"+pageMap.get("manufactureCode")+"%");
		}
		//生产计划状态
		if(CommonFunction.isNotNull(pageMap.get("produceplanState"))){
			sql.append(" and  plan.state=:produceplanState ");
			par.put("produceplanState",pageMap.get("produceplanState"));
		}
		sql.append(" ORDER BY ddate DESC )t3 ");*/
		/*sql.append(" UNION ");
		sql.append("select * from (select plan.id as planId,detail.id as detailId,ord.order_code,plan.product_code,plan.product_name,plan.product_typespec,plan.product_unit,detail.num as detailNum,detail.deliveryd_date,detail.remarks,plan.manufacture_code,plan.num as planNum,plan.start_date,plan.end_date,plan.state,peh.id,peh.state as pehState,plan.plan_code,ord.order_code_auto,detail.add_date AS ddate,plan.qualified_num from  produceplan plan LEFT JOIN orders_detail detail ON plan.order_detail_id=detail.id LEFT JOIN orders ord ON detail.order_id=ord.id LEFT JOIN produceplan_exception_handle peh ON peh.produceplan_id=plan.id where 1=1");
		//产品名称
		if(CommonFunction.isNotNull(pageMap.get("productName"))){
			sql.append(" and  plan.product_name like:productName ");
			par.put("productName", "%"+pageMap.get("productName")+"%");
		}
		//产品编号
		if(CommonFunction.isNotNull(pageMap.get("productCode"))){
			sql.append(" and  plan.product_code like:productCode ");
			par.put("productCode", "%"+pageMap.get("productCode")+"%");
		}
		//规格型号
		if(CommonFunction.isNotNull(pageMap.get("productTypespec"))){
			sql.append(" and  plan.product_typespec like:productTypespec ");
			par.put("productTypespec", "%"+pageMap.get("productTypespec")+"%");
		}
		//订单编号
		if(CommonFunction.isNotNull(pageMap.get("orderCode"))){
			sql.append(" and  ord.order_code like:orderCode ");
			par.put("orderCode", "%"+pageMap.get("orderCode")+"%");
		}
		//生产计划编号
		if(CommonFunction.isNotNull(pageMap.get("manufactureCode"))){
			sql.append(" and  (plan.manufacture_code like:manufactureCode or plan.plan_code like:manufactureCode)");
			par.put("manufactureCode", "%"+pageMap.get("manufactureCode")+"%");
		}
		//生产计划状态
		if(CommonFunction.isNotNull(pageMap.get("produceplanState"))){
			sql.append(" and  plan.state=:produceplanState ");
			par.put("produceplanState", pageMap.get("produceplanState"));
		}
		sql.append(" ORDER BY plan.id DESC )t4 ");
		sql.append(" )t4)t5 order by conv(t5.state,10,2) asc, t5.ddate desc");*///先按照详情添加时间排序 需求变了在改 目前是要求按照界面上显示的交货日期排序 感觉不合理
		
		result= this.getPageQueryResultSQLToMap(sql.toString(), param,qEntity);
		return result;
		
	}

	@Override
	public void addYesOrderProduceplanOne(Produceplan produceplan)throws BusinessException {
		Produceplan newProduceplan = new Produceplan();
		//生产计划编号(客户输入)
		newProduceplan.setManufactureCode(produceplan.getManufactureCode());
		//自动编号
		String planCode=sequenceService.getNewNoByTableColumns(577L);
		newProduceplan.setPlanCode(planCode);
		//计划执行开始时间
		newProduceplan.setStartDate(produceplan.getStartDate());
		//计划执行结束时间
		newProduceplan.setEndDate(produceplan.getEndDate());
		//产品信息id 
		newProduceplan.setProductId(produceplan.getProductId());
		//生产数量  
		newProduceplan.setNum(produceplan.getNum());
		//状态
		newProduceplan.setState(Produceplan.Status.watingtodo.getCode());
		//销售订单详情id
		newProduceplan.setOrderDetailId(produceplan.getOrderDetailId());
		//录入时间
		newProduceplan.setAddDate(new Date());
		
		//保存冗余的产品信息
		Product product = productService.getProductById(newProduceplan.getProductId());
		newProduceplan.setProductCode(product.getProductCode());
		newProduceplan.setProductName(product.getName());
		newProduceplan.setProductTypespec(product.getTypespec());
		newProduceplan.setProductUnit(product.getUnit());
		this.save(newProduceplan);
		
		//把销售订单详情改为"进行中"
		OrdersDetail ordersDetail = ordersDetailService.getOrdersDetailById(newProduceplan.getOrderDetailId());
		ordersDetail.setState(OrdersDetail.OrdersDetailStatus.ongoing.getCode());
		this.update(ordersDetail);
		Orders orders = ordersService.getOrderById(ordersDetail.getOrderId());
		if(orders.getState().equals(0)){
			orders.setState(Orders.State.ongoing.getCode());
			this.update(orders);
		}
	}

	@Override
	public void cancelProduceplan(Produceplan produceplan)
			throws BusinessException {
		produceplan.setState(Produceplan.Status.no.getCode());
		produceplan.setUpdateDate(new Date());
		this.update(produceplan);
		
		//把销售订单详情改为"已取消"(需要先判断是否是无销售订单的生产计划)
		if(produceplan.getOrderDetailId() != null) {
			OrdersDetail ordersDetail = ordersDetailService.getOrdersDetailById(produceplan.getOrderDetailId());
			ordersDetail.setState(OrdersDetail.OrdersDetailStatus.canceled.getCode());
			this.update(ordersDetail);
			//刷新缓存
			this.getSessionFactory().getCurrentSession().flush();
			//更新销售订单的状态
			updateOrderState(ordersDetail);
		}
	}
	
	public void updateOrderState(OrdersDetail ordersDetail) {
		Long ordersDetailId = ordersDetail.getId();
		Orders orders = ordersService.getOrderById(ordersDetail.getOrderId());
		StringBuffer sql = new StringBuffer("SELECT orders_detail.state FROM orders_detail");
		sql.append(" WHERE orders_detail.order_id = ");
		sql.append(" (");
		sql.append(" SELECT orders_detail.order_id FROM orders_detail WHERE orders_detail.id = " + ordersDetailId);
		sql.append(" )");
		List list = this.findBySQL(sql.toString());
		System.out.println("==============================");
		
		int[] state = new int[]{0, 0, 0, 0};
		boolean isBreak = false;//是否提前退出循环
		for (Object object: list) {
			System.out.println(object);
			if (CommonFunction.isNotNull(object)) {
				String objString = object.toString();
				if (objString.equals("-1")) {
					state[0]++;
				} else if (objString.equals("0")) {//如果有待派工的，那么不需要任何改动
					state[1]++;
					isBreak = true;
					break;
				} else if (objString.equals("1")) {//如果有生产中的，那么不需要做任何改动
					state[2]++;
					isBreak = true;
					break;
				} else if (objString.equals("2")) {
					state[3]++;
				}
			}
		}
		if (!isBreak) {//如果没有待派工或生产中的
			if (state[3] > 0) {//如果存在已完成，那么更新为已完成
				orders.setState(Orders.State.finished.getCode());
			} else {//全部为取消状态，更新为已取消
				orders.setState(Orders.State.canceled.getCode());
			}
			this.update(orders);
		}
		System.out.println("==============================");
	}
	
	@Override
	public void editYesOrderProduceplanOne(Produceplan produceplan)
			throws BusinessException {
		//得到Produceplan
		Produceplan newProduceplan = getProduceplanById(produceplan.getId());
		//生产计划编号(客户输入)
		newProduceplan.setManufactureCode(produceplan.getManufactureCode());
		//结束时间
		newProduceplan.setEndDate(produceplan.getEndDate());
		//开始时间
		newProduceplan.setStartDate(produceplan.getStartDate());
		//修改时间
		newProduceplan.setUpdateDate(new Date());
		this.update(newProduceplan);
	}

	@Override
	public void editYesOrderProduceplanNum(String pehId,Produceplan produceplan)
			throws BusinessException {
		//得到Produceplan
		Produceplan newProduceplan = getProduceplanById(produceplan.getId());
		//修改前的数量
		Long num = newProduceplan.getNum();
		//数量
		newProduceplan.setNum(produceplan.getNum());
		//修改时间
		newProduceplan.setUpdateDate(new Date());
		this.update(newProduceplan);
		
		//TODO  这里修改为通过planId查询状态
		//ProduceplanExceptionHandle produceplanExceptionHandle = produceplanExceptionHandleService.getPehByProId(newProduceplan.getId());
		if(CommonFunction.isNotNull(pehId)){
			//修改
			ProduceplanExceptionHandle newProduceplanExceptionHandle = produceplanExceptionHandleService.getPehById(Long.parseLong(pehId));
			newProduceplanExceptionHandle.setState(ProduceplanExceptionHandle.Status.dispatch.getCode());
			newProduceplanExceptionHandle.setOldPlanNum(num);
			newProduceplanExceptionHandle.setPlanNum(newProduceplan.getNum());
			this.update(newProduceplanExceptionHandle);
		}else{
			//新增
			ProduceplanExceptionHandle newProduceplanExceptionHandle = new ProduceplanExceptionHandle();
			newProduceplanExceptionHandle.setProduceplanId(newProduceplan.getId());
			newProduceplanExceptionHandle.setState(ProduceplanExceptionHandle.Status.dispatch.getCode());
			newProduceplanExceptionHandle.setOldPlanNum(num);
			newProduceplanExceptionHandle.setPlanNum(newProduceplan.getNum());
			this.save(newProduceplanExceptionHandle);
		}
		
	}
	
	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public OrdersDetailService getOrdersDetailService() {
		return ordersDetailService;
	}

	public void setOrdersDetailService(OrdersDetailService ordersDetailService) {
		this.ordersDetailService = ordersDetailService;
	}
	

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}


	public ProduceplanExceptionHandleService getProduceplanExceptionHandleService() {
		return produceplanExceptionHandleService;
	}

	public void setProduceplanExceptionHandleService(
			ProduceplanExceptionHandleService produceplanExceptionHandleService) {
		this.produceplanExceptionHandleService = produceplanExceptionHandleService;
	}

	@Override
	public Produceplan getProduceByOrderDetailId(Long detailId) {
		String hql="from Produceplan produce where produce.orderDetailId=:detailId";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("detailId", detailId);
		return (Produceplan)this.getUniqueResult(hql, map);
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public Object editYesOrderProduceplanNumQuery(Long produceplanId) {/*plan.productCode,plan.productName,plan.productTypespec,plan.productUnit*/
		String hql = "select orders.orderCode,cus.customerCode,cus.name,detail.productCode,detail.productName,detail.productTypespec,detail.productUnit,detail.num,detail.deliveryDate,plan.manufactureCode,plan.num as planNum,plan.startDate,plan.endDate,plan.id as planId  from Produceplan plan,OrdersDetail detail,Orders orders,Customer cus where plan.id=:produceplanId and plan.orderDetailId=detail.id and detail.orderId=orders.id and orders.customerId=cus.id";
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("produceplanId", produceplanId);
		return this.getUniqueResult(hql, param);
	}

	@Override
	public List<Produceplan> queryProduceplanByOrdersDetailId(Long[] id) {
		String hql = "select p from Produceplan p where p.orderDetailId in :id ";
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("id", id);
		return this.getList(hql, paramMap);
	}
	
	
	
}
