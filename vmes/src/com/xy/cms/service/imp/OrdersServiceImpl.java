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
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.Orders;
import com.xy.cms.entity.OrdersDetail;
import com.xy.cms.entity.Produceplan;
import com.xy.cms.entity.ProduceplanExceptionHandle;
import com.xy.cms.entity.ProduceplanTodo;
import com.xy.cms.entity.base.BaseProduceplanExceptionHandle;
import com.xy.cms.entity.base.BaseProduceplanExceptionHandle.Status;
import com.xy.cms.service.OrdersService;
import com.xy.cms.service.ProduceplanExceptionHandleService;
import com.xy.cms.service.ProduceplanService;
import com.xy.cms.service.SequenceService;

public class OrdersServiceImpl extends BaseDAO implements OrdersService{
	private ProduceplanService produceplanService;
	private ProduceplanExceptionHandleService produceplanExceptionHandleService;
	private SequenceService sequenceService;
	@Override
	public QueryResult queryAllOrders(Map<String, Object> map) throws BusinessException {
		QueryResult result = null;
		Map m = new HashMap();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		Orders orders = (Orders) map.get("orders");
		String code=(String)map.get("code");
		String state=(String)map.get("state");
		String name=(String)map.get("cusName");
		StringBuilder sql = new StringBuilder("select orders,customer,emp from Orders orders,Customer customer,Employee emp where orders.customerId=customer.id and orders.empId=emp.id ");
		if(CommonFunction.isNotNull(code)){
			sql.append(" and (orders.orderCode like :code or orders.orderCodeAuto like :code)");
			m.put("code", "%"+code+"%");
		}
		if(CommonFunction.isNotNull(state)){
			sql.append(" and  orders.state=:state ");
			m.put("state", state);
		}
		 
		if(CommonFunction.isNotNull(name)){
			sql.append(" and  customer.name like :name ");
			m.put("name",  "%"+name+"%");
		}
		sql.append(" order by conv(orders.state,10,2) asc");
		System.out.println(sql.toString());
		result=this.getPageQueryResult(sql.toString(), m, qEntity);
		return result;
	}

	@Override
	public List<String> queryCusCode() throws BusinessException {
		String sql="select id,name,customerCode from customer where 1=1";
		return this.findBySQL(sql);
	}

	@Override
	public void saveOrders(Orders orders,List<OrdersDetail> list) throws BusinessException {
		//自动编号
		String orderCode=sequenceService.getNewNoByTableColumns(551L);
		orders.setOrderCodeAuto(orderCode);
		this.save(orders);
		if(CommonFunction.isNotNull(list)){
			for (OrdersDetail ordersDetail : list) {
				ordersDetail.setOrderId(orders.getId());
				ordersDetail.setAddDate(new Date());
				ordersDetail.setState(OrdersDetail.OrdersDetailStatus.awaiting.getCode());
				this.save(ordersDetail);
			}
		}
	}

	@Override
	public void saveDetails(OrdersDetail detail) throws BusinessException {
		this.save(detail);
	}

	@Override
	public Orders getOrderByOrderCode(String orderCode)
			throws BusinessException {
		String hql ="from Orders o where o.orderCode=:orderCode ";
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("orderCode", orderCode);
		return (Orders) this.getQuery(hql, param);
	}

	@Override
	public List<String> getAllOrdersCode() throws BusinessException {
		String hql ="select o.orderCode from Orders o where 1=1 ";
		return this.find(hql);
	}
	
	@Override
	public List<Orders> getAllOrders() throws BusinessException {
		String hql ="from Orders o where 1=1 ";
		return this.find(hql);
	}

	@Override
	public Orders getOrderById(Long id) {
		return (Orders) this.get(Orders.class, id);
	}
	@Override
	public OrdersDetail getOrdersDetailById(Long id) throws BusinessException {
		return (OrdersDetail) this.get(OrdersDetail.class, id);
	}
	@Override
	public List<OrdersDetail> queryOrdersDetailByOrdersIds(Long[] ordersIds) {
		/*String hql = "select detail,product from OrdersDetail detail,Product product where detail.productId=product.id and detail.orderId in :ordersIds";*/
		String hql = "select detail from OrdersDetail detail where detail.orderId in :ordersIds order by conv(detail.state,10,2)";
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("ordersIds", ordersIds);
		return this.getList(hql, paramMap);
	}

	@Override
	public void cancelOrders(Orders orders) throws BusinessException {
		orders.setState(Integer.valueOf(Orders.State.canceled.getCode()));//取消订单
		List<OrdersDetail> OrdersDetailList=this.getOrderDetailByOrderId(orders.getId());
		if (CommonFunction.isNotNull(OrdersDetailList)) {
			for (OrdersDetail ordersDetail : OrdersDetailList) {
				ordersDetail.setState(Integer.valueOf(Orders.State.canceled.getCode()));//取消订单详情
				Produceplan produce=this.getProduceByOrderDetailId(ordersDetail.getId());
				if(CommonFunction.isNotNull(produce)){
					produce.setState(Produceplan.Status.no.getCode());//取消生成计划
					List<ProduceplanTodo> list=this.getTodoByProduceId(produce.getId());
					for (ProduceplanTodo produceplanTodo : list) {
						produceplanTodo.setState(String.valueOf(Orders.State.canceled.getCode()));//取消派工单
					}
				}
			}
		}
		
		
		//改变状态，先查所有的明细
		List<OrdersDetail> ordersDetailList = getDetailListByOrderId(orders.getId());
		if(CommonFunction.isNotNull(ordersDetailList)){
			//明细id集合
			Long[] str = new Long[ordersDetailList.size()];
			for(int i=0;i<ordersDetailList.size();i++){
				str[i] = ordersDetailList.get(i).getId();
			}
			//通过详情id查询所有的生产计划
			List<Produceplan> produceplanList = produceplanService.queryProduceplanByOrdersDetailId(str);
			for(Produceplan plan : produceplanList ){
				ProduceplanExceptionHandle p = produceplanExceptionHandleService.getPehByProId(plan.getId());
				if(CommonFunction.isNotNull(p)){
					p.setState(BaseProduceplanExceptionHandle.Status.produceplan.getCode());
					this.update(p);
				}else{
					ProduceplanExceptionHandle newP = new ProduceplanExceptionHandle();
					newP.setProduceplanId(plan.getId());
					newP.setState(BaseProduceplanExceptionHandle.Status.produceplan.getCode());
					this.save(newP);
				}
			}
		}
		
	}
	private List<OrdersDetail> getOrderDetailByOrderId(Long orderId){
		String hql="from OrdersDetail details where details.orderId=:orderId ";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("orderId", orderId);
		return this.getList(hql, map);
	}
	private Produceplan getProduceByOrderDetailId(Long orderDetailId){
		String hql="from Produceplan produce where produce.orderDetailId=:orderDetailId";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("orderDetailId", orderDetailId);
		return (Produceplan)this.getUniqueResult(hql, map);
	}
	private List<ProduceplanTodo> getTodoByProduceId(Long produceId){
		String hql="from ProduceplanTodo todo where todo.produceplanId=:produceId ";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("produceId", produceId);
		return this.getList(hql, map);
	}
	private Orders getOrdersByOrderDetailId(Long orderId){
		String hql="from Orders orders where orders.id=:orderId";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("orderId", orderId);
		return (Orders)this.getUniqueResult(hql, map);
	}
	@Override
	public void cancelOrderDetail(OrdersDetail ordersDetail) throws BusinessException {
		ordersDetail.setState(Integer.valueOf(OrdersDetail.OrdersDetailStatus.canceled.getCode()));
		Produceplan produce=this.getProduceByOrderDetailId(ordersDetail.getId());
		if(CommonFunction.isNotNull(produce)){
			produce.setState(Produceplan.Status.no.getCode());
			List<ProduceplanTodo> todos=this.getTodoByProduceId(produce.getId());
			for (ProduceplanTodo produceplanTodo : todos) {
				produceplanTodo.setState(String.valueOf(ProduceplanTodo.State.canceled.getCode()));
			}
		}
		Orders orders=this.getOrderById(ordersDetail.getOrderId());
		List<OrdersDetail> list=this.getDetailListByOrderId(ordersDetail.getOrderId());
		int i=0;
		 for (OrdersDetail ord : list) {//所有明细为取消 取消订单
			if(ord.getState().equals(OrdersDetail.OrdersDetailStatus.canceled.getCode())){
				i++;
			}
		} 
		if(list.size()==i){
			orders.setState(Orders.State.canceled.getCode());
			i=0;
		}
		
		//得到生成计划
		Produceplan produceplan = produceplanService.getProduceByOrderDetailId(ordersDetail.getId());
		//修改
		if(CommonFunction.isNotNull(produceplan)){
			ProduceplanExceptionHandle p = produceplanExceptionHandleService.getPehByProId(produceplan.getId());
			if(CommonFunction.isNotNull(p)){
				p.setState(BaseProduceplanExceptionHandle.Status.produceplan.getCode());
				this.update(p);
			}else{
				ProduceplanExceptionHandle newP = new ProduceplanExceptionHandle();
				newP.setProduceplanId(produceplan.getId());
				newP.setState(BaseProduceplanExceptionHandle.Status.produceplan.getCode());
				this.save(newP);
			}
		}
	}

	@Override
	public void editOrderDetailNum(Long detailId,Long num,Long oldNum) throws BusinessException {
		//得到ordetail
				OrdersDetail ordetail=this.getOrdersDetailById(detailId);
				//数量
				ordetail.setNum(num);
				this.update(ordetail);
				Produceplan producePlan=produceplanService.getProduceByOrderDetailId(detailId);
				if(CommonFunction.isNotNull(producePlan)){
					ProduceplanExceptionHandle produceplanExceptionHandle = produceplanExceptionHandleService.getPehByProId(producePlan.getId());
					if(CommonFunction.isNotNull(produceplanExceptionHandle)){
						//修改
						produceplanExceptionHandle.setOldOrderNum(oldNum);
						produceplanExceptionHandle.setOrderNum(num);
						produceplanExceptionHandle.setState(ProduceplanExceptionHandle.Status.produceplan.getCode());
						this.update(produceplanExceptionHandle);
					}else{
						//新增
						ProduceplanExceptionHandle newProduceplanExceptionHandle = new ProduceplanExceptionHandle();
						newProduceplanExceptionHandle.setOldOrderNum(oldNum);
						newProduceplanExceptionHandle.setOrderNum(num);
						newProduceplanExceptionHandle.setProduceplanId(producePlan.getId());
						newProduceplanExceptionHandle.setState(ProduceplanExceptionHandle.Status.produceplan.getCode());
						this.save(newProduceplanExceptionHandle);
					}
				}
	}

	public ProduceplanService getProduceplanService() {
		return produceplanService;
	}

	public void setProduceplanService(ProduceplanService produceplanService) {
		this.produceplanService = produceplanService;
	}

	public ProduceplanExceptionHandleService getProduceplanExceptionHandleService() {
		return produceplanExceptionHandleService;
	}

	public void setProduceplanExceptionHandleService(ProduceplanExceptionHandleService produceplanExceptionHandleService) {
		this.produceplanExceptionHandleService = produceplanExceptionHandleService;
	}

	@Override
	public List<OrdersDetail> getDetailListByOrderId(Long orderId) {
		String hql="from OrdersDetail detail where detail.orderId=:orderId";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("orderId", orderId);
		return this.getList(hql, map);
	}
	@Override
	public List<Employee> getEmp() {
		String hql="select emp from Orders as orders,Employee emp where orders.empId=emp.id";
		return this.getList(hql, null);
	}
	@Override
	public void editDetails(OrdersDetail ordersDetail) {
		this.update(ordersDetail);
		
	}

	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	
	
}
