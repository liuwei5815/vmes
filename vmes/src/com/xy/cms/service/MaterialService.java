package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Company;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.Eqiupment;
import com.xy.cms.entity.EqiupmentType;
import com.xy.cms.entity.Material;
import com.xy.cms.entity.MaterialType;
import com.xy.cms.entity.Orders;
import com.xy.cms.entity.OrdersDetail;

public interface MaterialService {
	/**
	 * 初始化列表页
	 */
	QueryResult queryAllMaterial(Map<String, Object> map) throws BusinessException;
	
	/**
	 * 保存物料
	 * @return 
	 */
	void saveMaterial(Material ma) throws BusinessException;
	/**
	 * 查询物料
	 */
	public Material getMaterialById(Long id);
	/**
	 * 更新物料信息
	 */
	public void updateMaterial(Material ma)throws BusinessException;
	/**
	 * 删除物料信息
	 */
	public void delMaterial(Material ma)throws BusinessException;
	
	/**
	 * 查询物料类型名称
	 */
	public String queryMaType(Integer maId);
	/**
	 * 批量导入物料
	 * @param List<Material>
	 * */
	public void importMaterial(List<Material> materialList) throws BusinessException;
	/**
	 * 查询物料类型
	 */
	public MaterialType getMaTpyeByName(String name);
	/**
	 * 查询物料类型
	 */
	public MaterialType getMaTpyeByNameAndPid(String name,Long pid);
	/**
	 * 保存物料类型
	 */
	public Long addMaterialTypeWithId(MaterialType mt);
	
	
	/**
	 * 查询物料
	 */
	public List<Material> queryMaterialByIds(Long[] ids);

}
