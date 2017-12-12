package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Produceplan;
import com.xy.cms.entity.ProduceplanTodoClaim;
import com.xy.cms.entity.TodoClaimEquip;
import com.xy.cms.entity.TodoClaimMaterial;

public interface ProduceplanTodoClaimService {
	/**
	 * 根据派工单编号查询报工单情况
	 * @param id
	 * @return
	 */
	List<ProduceplanTodoClaim> queryTodoClaimByTodoId(Long todoId);


	/**
	 * 根据派工单编号批量查询报工单情况
	 * @param id
	 * @return
	 */
	List<Object[]> queryTodoClaimByTodoIds(Long[] todoId);
	/**
	 * 根据报工单查询相关物料
	 */
	List<TodoClaimEquip> queryEquipByToId(Long claimId);
	/**
	 * 根据报工单查询相关设备
	 */
	List<TodoClaimMaterial> queryMaByToId(Long claimId);
}
