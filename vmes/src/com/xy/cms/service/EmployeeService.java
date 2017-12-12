package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.AppUser;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.EmployeeDeptart;

public interface EmployeeService {

	/**
	 * 分页查询成员信息
	 * */
	QueryResult queryEmployeePage(Map param) throws BusinessException;
	
	/**
	 * 添加员工
	 * @param employee
	 * @param departmentId 部门id
	 * */
	public void addEmployee(Admin admin,AppUser appUser,Employee employee,Long departmentId)throws BusinessException;

	/**
	 * 通过员工id得到与员工
	 * */
	public Employee getEmployeeById(Long id);
	
	/**
	 * 保存编辑后的员工
	 * */
	public void updateEmployee(Employee employee,Long deptId)throws BusinessException;
	
	/**
	 * 删除员工
	 * @param employee
	 * */
	public void delEmployee(Employee employee)throws BusinessException;
	
	/**
	 * 判断员工号是否重复
	 * @param employeeSerialNo 
	 * @return
	 * */
	public boolean provingEmployeeSerialNo(String employeeSerialNo);
	
	/**
	 * 批量导入员工
	 * @param List<Employee>
	 * */
	/*public void importEmpoyee(List<Employee> employeeList,Long departmentId);*/
	public void importEmpoyee(List<Map<Long,Employee>> employeeList);
	
	/**
	 * 批量删除员工
	 * @param id 要删除的主键id的数组集合
	 * */
	public void batchDeleteEmployee(String[] id)throws BusinessException;

	/**
	 * 查询员工列表
	 */
	public List<Employee> getAllEmp();
	/**
	 * 通过员工id找对应关系
	 */
	public EmployeeDeptart getEdById(Long id);
	
	/**
	 * 得到未添加账号的所有员工
	 * */
	public QueryResult queryNoAccountEmployee(Map map);
	
	/**
	 * 通过员工号获取员工
	 */
	public Employee getEmployeeBySerialNo(String employeeSerialNo);
}
