package com.xy.cms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Company;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Eqiupment;
import com.xy.cms.entity.EqiupmentType;
import com.xy.cms.entity.EquipmentParamData;
import com.xy.cms.entity.Material;
import com.xy.cms.entity.MaterialType;
import com.xy.cms.entity.Orders;
import com.xy.cms.entity.OrdersDetail;

public interface EquipmentService {
	/**
	 * 初始化列表页
	 */
	QueryResult queryAllEquipment(Map<String, Object> map) throws BusinessException;
	
	/**
	 * 保存设备
	 * @return 
	 */
	void saveEqiupment(Eqiupment eq) throws BusinessException;
	/**
	 * 查询设备
	 */
	public Eqiupment getEqiupmentById(Long id);
	/**
	 * 更新设备信息
	 */
	public void updateEqiupment(Eqiupment eq)throws BusinessException;
	/**
	 * 删除设备信息
	 */
	public void delEquipment(Eqiupment eq)throws BusinessException;
	/**
	 * 查询设备类型名称
	 */
	public String queryEqType(Integer eqId);
	/**
	 * 批量导入设备物料
	 * @param List<Material>
	 * */
	public void importEqiupment(List<Eqiupment> eqiupmentList) throws BusinessException;
	/**
	 * 根据设备类型名称查找设备类型
	 */
	public EqiupmentType getEqTpyeByName(String name); 

	/**
	 * 设备运行数据
	 */
	public QueryResult queryAllEquipmentOperInf(Map<String, Object> map) throws BusinessException;
	
	/**
	 * 单个设备24小时内的运行时间数据
	 * @param equipmentId 设备id
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * */
	public QueryResult queryRecord(Map map);
	
	/**
	 * 根据设备Id以及时间查询生产的数量
	 * */
	public Map queryNumberByIdAndDate(String equpmentId,String receiveTime);
	
	/**
	 * 根据日期以及设备id查询数据
	 * */
	public List<EquipmentParamData> queryEquipmentParamDataByDateAndId(Map map);

	/**
	 * 根据设备类型名称和父id查找设备类型
	 */
	public EqiupmentType getEqTpyeByNameAndPid(String name,Long pid); 
	/**
	 * 保存设备类型
	 */
	public Long addEqiupmentTypeWithId(EqiupmentType et);
	/**
	 * 设备有效利用率
	 */
	/**
	 * 单个设备有效利用率
	 */
	public Map queryEqiupmentEfcs(Map<String,Object> map);
	/**
	 * 单个设备故障率
	 */
	public Map queryEqiupmentDisEfcs(Map<String,Object> map);
	
	/**
	 * 
	 * 设备运行报表数据查询
	 * */
	public QueryResult queryOperInfor(Map<String, Object> map) throws BusinessException;
	
	
	/**
	 * 根据上级部门id查询该部门，以及该部门所有的设备id
	 * */
	public List<Eqiupment> queryEqiupmentId(Map<String,Object> map);
	
	/**
	 * 设备产能利用率
	 * */
	public Map queryEqiupmentCapacity(Map map);
	
	/**
	 * 设备空载率
	 * */
	public Map queryEqiupmentIdling(Map map);
	
	/**
	 * 设备故障率
	 * */
	public Map queryEqiupmentFault(Map map);
	
	/**
	 * 设备故障率组合图
	 * */
	public List<Map> queryEqiupmentCapacityGroup(Long id,Date beginDate,Date endDate)throws Exception;;
	
	/**
	 * 通过设备id查询到多个设备的运行时间之和
	 * */
	public Map queryWorktimeCapacity(Map map);
	/**
	 * 设备有效利用率组合图
	 */
	public List<Map> queryEqEfc(Long id,Date beginDate,Date endDate);
	/**
	 * 设备故障率组合图
	 */
	public List<Map> queryEqDisEfc(Map<String,Object> map);
	
	
	/**
	 * 根据上级部门id查询该部门，以及该部门所有的设备id
	 * */
	public List<Object[]> queryEqiupmentByIds(Long[] ids);
	
	/**
	 * 公用方法
	 * 设备有效利用率列表页
	 * 设备产能利用率列表页
	 * 
	 * **/
	public QueryResult queryList(Map map);
	
	/***
	 * 统计设备运行状态
	 */
	public Map getEquipmentStates(Long[] ids);
	/**
	 * 设备其他数据列表
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult queryOtherList(Map<String, Object> map) throws BusinessException;
	
}
