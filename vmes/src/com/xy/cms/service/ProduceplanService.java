package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Produceplan;

public interface ProduceplanService {
	/**
	 * 通过主键得到生成计划
	 * @param id 生产计划主键id
	 * @return
	 * */
	public Produceplan getProduceplanById(Long id);
	
	/**
	 * 通过主键得到生成计划
	 * */
	public Object[] getProduceplanAndProductById(Long id);
	
	/**
	 * 查询所有的生成订单
	 * */
	public QueryResult queryAllProduceplan(Map pageMap)throws BusinessException;
	
	/**
	 * 保存无销售订单生产计划
	 * @param produceplan 要保存的生产计划
	 * */
	public void addNoOrderProduceplan(Produceplan produceplan)throws BusinessException;
	
	/**
	 * 编辑无销售订单生产计划
	 * */
	public void editNoOrderProduceplan(Produceplan produceplan)throws BusinessException;

	/**
	 * 调整无销售订单详情的生产计划数量
	 * @param pehId 警报表主键id
	 * @param produceplan 生产计划
	 * */
	public void editNoOrderProduceplanNum(String pehId,Produceplan produceplan)throws BusinessException;
	
	/**
	 * 删除无销售订单生产计划
	 * */
	public void delNoOrderProduceplan(Produceplan produceplan)throws BusinessException;

	/**
	 * 通过主键查询生产计划
	 * @param id 主键id
	 * */
	public Object queryProduceplanById(Long id)throws BusinessException;
	
	/**
	 * 保存有销售订单生产计划
	 * */
	public void addYesOrderProduceplan(List<Produceplan> produceplanList)throws BusinessException;

	/**
	 * 生产计划主页List数据查询
	 * @param pageMap 参数的map
	 * */
	public QueryResult getAllProduceplanRelationOrdersDetail(Map pageMap)throws BusinessException;

	/**
	 * 保存有销售订单的生成计划
	 * @param Produceplan
	 * */
	public void addYesOrderProduceplanOne(Produceplan produceplan)throws BusinessException;

	/**
	 * 取消生产计划
	 * */
	public void cancelProduceplan(Produceplan produceplan)throws BusinessException;

	/**
	 * 编辑有订单的生产计划
	 * @param produceplan 生产计划
	 * */
	public void editYesOrderProduceplanOne(Produceplan produceplan)throws BusinessException;

	/**
	 * 调整有销售订单生产计划数量
	 * */
	public void editYesOrderProduceplanNum(String pehId,Produceplan produceplan)throws BusinessException;
	/**
	 * 根据订单详情获取计划
	 */
	public Produceplan getProduceByOrderDetailId(Long detailId);
	
	/**
	 * 编辑有销售详情的生产计划时使用的查询
	 * @param produceplanId 生产订单主键id
	 * @retuen
	 * */
	public Object editYesOrderProduceplanNumQuery(Long produceplanId);

	/**
	 * 通过生产计划详情id查询所有的生产计划
	 * */
	public List<Produceplan> queryProduceplanByOrdersDetailId(Long[] id);
}
