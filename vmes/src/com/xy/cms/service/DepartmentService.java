package com.xy.cms.service;

import java.util.List;

import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Department;

/**
 * 组织机构Service
 * */
public interface DepartmentService {
	
	/**
	 * 查询所有正常使用的部门
	 * @return
	 * */
	public List<Department> getNormalDepartment();
	
	/**
	 * 添加部门
	 * @return
	 * */
	public void addDepartment(Department department)throws BusinessException;
	/**
	 * 添加部门后得到Id
	 * @return
	 * */
	public Long addDepartmentWithId(Department department)throws BusinessException;
	/**
	 * 通过主键id查询对应的部门
	 * @param id 主键id
	 * */
	public Department getDepartmentById(Long id)throws BusinessException;
	
	/**
	 * 更新部门信息
	 * @param department
	 * */
	public void updateDepartment(Department department)throws BusinessException;
	
	/**
	 * 通过id查询子部门
	 * @param id
	 * */
	public List<Department> getDepartmentByPid(Long pid)throws BusinessException;
	
	/**
	 * 删除部门
	 * @param department
	 * */
	public void delDepartment(Department department)throws BusinessException;
	
	/**
	 * 上移下移部门
	 * @param id 主键id
	 * @param sign 移动标记（上移还是下移）
	 * */
	public void upOrDownDepartment(Long id,String sign)throws BusinessException;
	
	/**
	 * 判断兄弟部门名称是否重复
	 * @param departmentName 部门名称
	 * @param pId 父级部门id
	 * */
	public boolean departmentName(String departmentName,Long pId);
	
	/**
	 * 根据名称找部门
	 */
	public Department getDepartmentByName(String name);/**
	 * 根据名称找二三级部门
	 */
	public Department getDepartmentByNameAndPid(String name,Long pid);
	/**
	 * 根据员工ID找部门
	 */
	public String getDepartmentByEmpId(Long eid);
}
