package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.entity.TableRelationship;


public interface TableRelationshipService {
		/**
		 * tableId作为从表查询出选择主表
		 * @param tableId
		 * @return TableRelationship
		 */
		List<TableRelationship> getRelationBySourceTableId(Long tableId);
		
		/**
		 * tableId作为主表需要添加从表
		 * @param tableId
		 * @return TableRelationship
		 */
		List<TableRelationship> getRelationByTargetTabelId(Long tableId);
		
		
		/**
		 * 根据主键id得到TableRelationship
		 * @param id
		 * @return TableRelationship
		 */
		TableRelationship getRelationById(Long id);
		/**
		 * 根据tableId得到所有该tableId下所有主键表的主外键对应关系
		 * @param tableId 
		 * @return  Map<Long,TableRelationship>  Key:ship Value:TableRelationship
		 */
		Map<Long,TableRelationship> getRelationMapByTableId(Long tableId);
		
		TableRelationship getRelation(Long sourceTableId,Long targetTableId);
		
		
		/**
		 * 根据主表得到该表的树关系
		 * 判断依据：系统当中业务对象关联当前业务对象则认为他是树
		 * @param mainTable
		 * @return
		 */
		TableRelationship getTreeRelationship(Long mainTable);
		
}
