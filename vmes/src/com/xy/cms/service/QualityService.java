package com.xy.cms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.view.TreeView;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.Menu;
import com.xy.cms.entity.MenuConfig;
import com.xy.cms.entity.TableColumnType;
import com.xy.cms.entity.TableColumns;
import com.xy.cms.entity.TableRelationship;
import com.xy.cms.entity.Tables;
import com.xy.cms.entity.View;
import com.xy.cms.entity.ViewSearch;

public interface QualityService {
	/**
 	 * 根据部门id查询所有的报工
 	 * @param DepId 组织机构id
 	 * @return
 	 * */
 	public QueryResult queryEmployeeByDepId(Map pageMap,Long Depid,String start,String end)throws BusinessException;
	

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
	List<Map> queryDeptEmpAchievement(Long deptId,String beginDate,String endDate,boolean isAsc);
	/**
	 * 查询人员生产合格率 
	 */
	Map<String,Object> queryEmpAchievement(Long empId,String beginDate,String endDate,boolean isAsc);
	
	/**
	 * 查询人员生产合格率 
	 */
	List<Map> queryEmpsAchievement(String[] empId,String beginDate,String endDate,boolean isAsc);
	 	 
}
