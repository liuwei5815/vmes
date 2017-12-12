package com.xy.admx.service;

import java.util.List;
import java.util.Map;

import com.xy.admx.core.service.base.BaseService;
import com.xy.admx.rest.EquipmentStatusRest.Status;
import com.xy.cms.entity.Eqiupment;
import com.xy.cms.entity.EquipmentParam;
import com.xy.cms.entity.EquipmentParamData;


public interface EquipmentService extends BaseService {
	/**
	 * 根据部门id查询设备
	 */
	public List<Eqiupment> getEquipmentByDeptId(Long id);
	/**
	 * 保存上传数据
	 */
	public void saveEqData(List<EquipmentParamData> list);
	/**
	 * 保存参数
	 */
	public void saveEqParam(List<EquipmentParam> list);
	
	/***
	 * 统计设备运行状态
	 */
	public Map<String,Object> queryEquipmentRuningState();
	
	/**
	 * 根据状态查询设备列表
	 * @param status
	 * @return
	 */
	public List<Map<String,Object>> queryEquipmentListByStatus(Status status);
	
	
	public Map<String,Object> queryEquipmentDetail(Long id);
	
}
