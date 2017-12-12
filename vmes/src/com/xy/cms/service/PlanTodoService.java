package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Produceplan;
import com.xy.cms.entity.ProduceplanTodo;


public interface PlanTodoService {
	QueryResult queryAllPlan(Map<String, Object> map) throws BusinessException;
//	/**
//	 * 刪除表數據
//	 * @param id
//	 * @throws BusinessException
//	 */
	public void del(Long id) throws BusinessException;
//	/**
//	 * 保存一条记录
//	 */
	public void savePlan(ProduceplanTodo planTodo) throws BusinessException;
//	/**
//	 * 查询计划
//	 */
	public List<Produceplan> getPlan();
//	/**
//	 * 查询一条记录
//	 */
	public ProduceplanTodo queryPlanById(Long id);
//	/**
//	 * 查询打印的List
//	 */
	public List<Object[]> queryPrintPlan(Long id) throws BusinessException;
	/**
	 * 查询一条生产记录
	 */
	public Produceplan queryPlan(Long id) throws BusinessException;
	
	
	List<ProduceplanTodo> queryProduceplanTodoByProduceplanIds(Long[] produceplanIds);
	List<ProduceplanTodo> queryProduceplanTodoByProduceplanId(Long produceplanId);
	/**
	 * 根据数量和生产计划id批量生成派工单
	 * @param count
	 * @param produceplanId
	 */
	void addProductplans(Integer count,Long produceplanId) throws Exception;
	
	/***
	 * 调整该工序计划
	 */
	void changeWorkPlan(Long planId,List<ProduceplanTodo> todos);
	
	
	/***
	 * 
	 */
	List<Object[]> queryPlanAndToDoByProductTodos(Long[] products);
}
