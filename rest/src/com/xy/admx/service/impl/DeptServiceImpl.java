package com.xy.admx.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xy.admx.core.service.base.BaseServiceImpl;
import com.xy.admx.service.DeptService;
import com.xy.cms.entity.Department;
@Service
public class DeptServiceImpl extends BaseServiceImpl implements DeptService{

	@Override
	public List<Department> getNormalDepartment() {
		String hql = " from Department d where d.status=1 and d.level<=2 order by pid,orderby ";
		return this.getList(hql);
	}

}
