package com.xy.cms.service;

import java.util.List;

import com.xy.cms.entity.MaterialType;

public interface MaterielTypeService {
	
	/**
	 * 查询所有的物料类型
	 * */
	public List<MaterialType> quertMaterielTypeAll();
	
	/**
	 * 
	 * 添加物料类型
	 * */
	public void saveMaterialType(MaterialType materialType);
	
	/**
	 * 
	 * 通过物料类型id查询物料信息
	 * @param id
	 * @return
	 * */
	public MaterialType queryMaterialTypeById(Long id);
	
	/**
	 * 编辑物料类型
	 * */
	public void updateMaterialType(MaterialType materialType);
	
	/**
	 * 删除物料类型
	 * */
	public void deleteMaterialType(MaterialType materialType);
	
	/**
	 * 根据名称以及pid来查询同一级下是否有重名
	 * @param name 名称
	 * @param pid 上级id
	 * @return 
	 * */
	public boolean validateRepeatName(String name,Long pid);
	
	/**
	 * 根据物料类型名称以及上级id查询物料类型
	 * @param name
	 * @param pid
	 * @return
	 * */
	public MaterialType queryMaterialType(String name, Long pid);
}
