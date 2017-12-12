package com.xy.admx.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xy.cms.entity.Department;
import com.xy.cms.entity.Employee;

public interface AchievementService {
	
	
	/**
	 * 查询部门完成情况
	 * @param deptId
	 * @param beginDate
	 * @param endDate
	 * @param isAsc
	 * @return
	 */
	Map<String,Object> queryDeptAchievement(Long deptId,String beginDate,String endDate);
	
	
	
	/**
	 * 查询部门员工完成情况
	 * @param deptId
	 * @param beginDate
	 * @param endDate
	 * @param isAsc
	 * @return
	 */
	List<Map<String,Object>> queryDeptEmpAchievement(Long deptId,String beginDate,String endDate,boolean isAsc);
	/**
	 * 查询单个部门生产合格率
	 * @param deptId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String,Object> queryDeptQltAchievement(Long deptId,String beginDate,String endDate);
	/**
	 * 查询单个部门人员生产合格率
	 * @param deptId
	 * @param beginDate
	 * @param endDate
	 * @param isAsc
	 * @return
	 */
	List<Map<String,Object>> queryDeptQltEmpAchievement(Long deptId,String beginDate,String endDate,boolean isAsc);
	/**
	 * 查询单个部门生产效率
	 * @param deptId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String,Object> queryDeptEfcAchievement(Long deptId,String beginDate,String endDate); 
	/**
	 * 查询单个部门人员生产效率
	 * @param deptId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<Map<String,Object>> queryDeptEfcEmpAchievement(Long deptId,String beginDate,String endDate,boolean isAsc);
	/**
	 * 查询所有部门任务完成率
	 */
	Map<String,Object> queryAllDeptTaskAchievement(String beginDate,String endDate);
	/**
	 * 查询所有部门生产合格率
	 */
	Map<String,Object> queryAllQualifiedAchievement(String beginDate,String endDate);
	/**
	 * 查询所有部门生产效率
	 */
	Map<String,Object> queryAllEfficiencyAchievement(String beginDate,String endDate);
	/**
	 * 查询员工任务完成率
	 * */
	Map<String,Object> queryEmployeeAchievement(Long employeeId,Date beginDate,Date endDate,boolean isAsc);

	/**
	 * 查询员工生产合格率
	 * */
	Map<String,Object> queryEmployeeQualified(Long employeeId,Date beginDate,Date endDate,boolean isAsc);
	
	/**
	 * 查询员工生产合格率
	 * */
	Map<String,Object> queryEmployeeQroduction(Long employeeId,Date beginDate,Date endDate,boolean isAsc);
	
	/**
	 * 查询所有部门
	 */
	List<Map<String,Object>> getNormalDepartment();
	/**
	 * 查询所有员工
	 */
	List<Map<String,Object>> getEmp();
	
	/**
	 * 查询单个员工在某个时间段内的任务完成率饼图
	 * @param employeeId 人员id
	 * @param beginDate	开始时间
	 * @param endDate	结束时间
	 * @return 
	 * */
	Map<String,Object> queryEmpTaskCompletionRatePie(Long employeeId,String beginDate,String endDate);
	
	
	/**
	 * 查询单个人员在某个时间段内的任务完成柱状图
	 * @param employeeId 人员id
	 * @param beginDate	开始时间
	 * @param endDate	结束时间
	 * @return 
	 * */
	List<Map<String,Object>> queryEmpTaskCompletionRateHistogram(Long employeeId,String beginDate,String endDate);

	/**
	 * 查询单个员工在某个时间段内的生产合格率饼图
	 * @param employeeId 人员id
	 * @param beginDate	开始时间
	 * @param endDate	结束时间
	 * @return 
	 * */
	Map<String,Object> queryEmpQualifiedProductionPie(Long employeeId,String beginDate,String endDate);
	
	/**
	 * 查询单个员工在某个时间段内的生产合格率柱状图
	 * @param employeeId 人员id
	 * @param beginDate	开始时间
	 * @param endDate	结束时间
	 * @return   
	 * */
	List<Map<String,Object>> queryEmpQualifiedProductionHistogram(Long employeeId,String beginDate,String endDate);
	/**
	 * 查询单个员工在某个时间段内的生产效率饼图
	 * @param employeeId 人员id
	 * @param beginDate	开始时间
	 * @param endDate	结束时间
	 * @return 
	 * */
	Map<String,Object> queryEmpEfcPie(Long employeeId,String beginDate,String endDate);
	/**
	 * 查询单个员工在某个时间段内的生产效率柱状图
	 * @param employeeId 人员id
	 * @param beginDate	开始时间
	 * @param endDate	结束时间
	 * @return 
	 * */
	List<Map<String,Object>> queryEmpEfcHis(Long employeeId,String beginDate,String endDate);
}
