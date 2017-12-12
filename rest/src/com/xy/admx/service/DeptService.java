package com.xy.admx.service;

import java.util.List;

import com.xy.cms.entity.Department;

public interface DeptService {
	/**
	 * 查询所有正常使用的部门
	 * @return
	 * */
	public List<Department> getNormalDepartment();

}
