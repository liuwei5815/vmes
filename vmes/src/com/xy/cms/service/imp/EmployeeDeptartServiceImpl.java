package com.xy.cms.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.EmployeeDeptart;
import com.xy.cms.service.EmployeeDeptartService;

public class EmployeeDeptartServiceImpl extends BaseDAO implements EmployeeDeptartService{

	@Override
	public EmployeeDeptart getEmployeeDeptartByEid(Long empId)
			throws BusinessException {
		String hql = "from EmployeeDeptart where employeeId=:empId";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("empId", empId);
		return (EmployeeDeptart) this.getUniqueResult(hql, map);
	}

}
