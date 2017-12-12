package com.xy.cms.service;

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

public interface PerformanceService {
	 	/**
	 	 * 根据部门id查询所有的报工
	 	 * @param DepId 组织机构id
	 	 * @return
	 	 * */
	 	public QueryResult queryEmployeeByDepId(Map pageMap,Long Depid,String start,String end)throws BusinessException;
	 	
	 	public QueryResult queryEmployeeTimeByDepId(Map pageMap,Long Depid,String start,String end,String time)throws BusinessException;

	 	public List<Map> queryEmployeeTimeByCode(String code, String workTime, String startTime, String endTime);
	 	
	 	public List<Map> queryAllEmployeeByTime(String time, String workTime);
	 	
	 	/**
	 	 * 根据时间段和员工号查询一个的有效工作时间占比
	 	 * @param code		员工号
	 	 * @param workTime	工作时长
	 	 * @param startTime	开始时间
	 	 * @param endTime	结束时间
	 	 * @return
	 	 */
	 	public List<Map> queryEmployeeByCodeAndTime(String code, String workTime, String startTime, String endTime);
	 	/**
	 	 * 根据指定日期查询多个员工的有效工作时间占比
	 	 * @param codes 	员工号字符串，用","进行分割
	 	 * @param time		指定的日期
	 	 * @param workTime	工作时长
	 	 * @return
	 	 */
	 	public List<Map> queryEmployeeTimeByCodes(String codes, String time, String workTime);
	 	
	 	/**
	 	 * @param code 员工号
	 	 * @param time	指定日期
	 	 * @return		该员工在指定日期的派工单号及报工详情
	 	 */
	 	public List<Map> queryOrderDetailByCodes(String code, String time);
	 	
	 	public List<Map> queryEmployeeByTime(String start, String end);
}
