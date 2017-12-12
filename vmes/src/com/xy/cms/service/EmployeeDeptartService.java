package com.xy.cms.service;

import java.util.List;

import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.EmployeeDeptart;

public interface EmployeeDeptartService {
	
	/**
	 * 通过employee_id得到EmployeeDeptart
	 * @param empId 员工id
	 * @return
	 */
	public EmployeeDeptart getEmployeeDeptartByEid(Long empId)throws BusinessException;

	
}
