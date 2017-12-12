package com.xy.admx.service;

import java.util.List;
import java.util.Map;

import com.xy.admx.common.base.PageQEntity;
import com.xy.admx.common.exception.BusinessException;
import com.xy.admx.core.service.base.BaseService;
import com.xy.cms.entity.Orders;
import com.xy.cms.entity.ProduceplanTodoClaim;

public interface PlanService extends BaseService {

	/**
	 * 分页查询生产订划
	 * @param pageEntity
	 * @throws BusinessException
	 */
	public void queryPlanPage(PageQEntity pageEntity,Map param) throws Exception;
	
	/**
	 * 分页查询派工单
	 * @param pageEntity
	 * @throws BusinessException
	 */
	public void queryTodoPage(PageQEntity pageEntity,Map param) throws Exception;
	
	/**
	 * 分页查询报工单
	 * @param pageEntity
	 * @throws BusinessException
	 */
	public void queryClaimPage(PageQEntity pageEntity,Map param) throws Exception;
	
	/**
	 * 根据订单的id查订单明细下所有的状态值
	 * @param orderid
	 * @return
	 */
	public List queryStatesOfOrderDetails(Long orderid) ;
	
	/**
	 * 查询订单下是不是还有未完排生产的订单明细
	 * @param orderid
	 * @return
	 */
	public boolean hasNotProduce(Long orderid);
	
	/**
	 * 根据计划 id查订单
	 * @param planid
	 * @return
	 */
	public Orders getOrderByPlanid(Long planid);
	
	/**
	 * 更新订单状态
	 * @param state
	 * @param orderid
	 */
	public void updatePlanState(int state,Long orderid);
	
	/**
	 * 查询派工详情
	 */
	List<ProduceplanTodoClaim> queryTodoClaimByTodoId(Long todoId);
	/**
	 * 查询设备数据
	 */
	List<Map> queryEquipmentDataById(Long eqId);
}
