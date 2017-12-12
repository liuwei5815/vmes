package com.xy.cms.service;

import java.util.List;

import com.xy.cms.entity.EqiupmentType;
import com.xy.cms.entity.MaterialType;

public interface EquipmentTypeService {
	/**
	 * 查询所有的设备类型
	 * */
	public List<EqiupmentType> quertEqiupmentTypeAll();
	
	/**
	 * 根据名称以及上级id查询是否重名
	 * @param type 名称
	 * @param pid 上级id
	 * @return
	 * */
	public boolean validateRepeatName(String type,Long pid);
	
	/**
	 * 
	 * 添加设备类型
	 * */
	public void saveEqiupmentType(EqiupmentType eqiupmentType);
	
	/**
	 * 
	 * 通过设备类型id查询设备类型信息
	 * @param id
	 * @return
	 * */
	public EqiupmentType queryEqiupmentTypeById(Long id);
	
	/**
	 * 编辑设备类型
	 * */
	public void updateEqiupmentType(EqiupmentType eqiupmentType);
	
	/**
	 * 删除设备类型
	 * */
	public void deleteEqiupmentType(EqiupmentType eqiupmentType);
	
	/**
	 * 根据设备类型名称以及上级id查询设备类型
	 * @param name
	 * @param pid
	 * @return
	 * */
	public EqiupmentType queryEqiupmentType(String type, Long pid);
}
