package com.xy.admx.rest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xy.admx.common.CommonFunction;
import com.xy.admx.rest.base.BaseRest;
import com.xy.admx.rest.base.SuccessResponse;
import com.xy.admx.service.AchievementService;
import com.xy.cms.entity.Department;
@RestController
@RequestMapping("/achievements")
public class AchievementsRest extends BaseRest {
	@Resource
	private AchievementService achievementService;
	/**
	 * 单部门 任务完成率
	 * @param deptId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dept/{deptId}",method=RequestMethod.GET)
	public SuccessResponse dept(@PathVariable("deptId") Long deptId, String beginDate,String endDate){
		return new SuccessResponse(achievementService.queryDeptAchievement(deptId, beginDate, endDate));
	}
	
	/**
	 * 单个部门前10名或者后10名任务完成率
	 * */
	@ResponseBody
	@RequestMapping(value="/deptemp/{deptId}",method=RequestMethod.GET)
	public SuccessResponse deptEmp(@PathVariable("deptId") Long deptId,String beginDate,String endDate,Integer orderBy){
		return new SuccessResponse(achievementService.queryDeptEmpAchievement(deptId, beginDate, endDate, orderBy==1?true:false));
	}
	/**
	 * 单部门 生产合格率
	 * @param deptId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deptQlt/{deptId}",method=RequestMethod.GET)
	public SuccessResponse deptQlt(@PathVariable("deptId") Long deptId, String beginDate,String endDate){
		beginDate=beginDate+" 00:00:00";
		endDate=endDate+" 23:59:59";
		return new SuccessResponse(achievementService.queryDeptQltAchievement(deptId, beginDate, endDate));
	}
	@ResponseBody
	@RequestMapping(value="/deptQltEmp/{deptId}",method=RequestMethod.GET)
	public SuccessResponse deptQltEmp(@PathVariable("deptId") Long deptId,String beginDate,String endDate,Integer orderBy){
		return new SuccessResponse(achievementService.queryDeptQltEmpAchievement(deptId, beginDate, endDate,orderBy==1?true:false));
	}
	/**
	 * 单部门 生产效率
	 * @param deptId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deptEfc/{deptId}",method=RequestMethod.GET)
	public SuccessResponse deptEfc(@PathVariable("deptId") Long deptId, String beginDate,String endDate){
		return new SuccessResponse(achievementService.queryDeptEfcAchievement(deptId, beginDate, endDate));
	}
	@ResponseBody
	@RequestMapping(value="/deptEfcEmp/{deptId}",method=RequestMethod.GET)
	public SuccessResponse deptEfcEmp(@PathVariable("deptId") Long deptId,String beginDate,String endDate,Integer orderBy){
		return new SuccessResponse(achievementService.queryDeptEfcEmpAchievement(deptId, beginDate, endDate, orderBy==1?true:false));
	}
	/**
	 * 企业各部门任务完成率
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/task",method=RequestMethod.POST)
	public SuccessResponse queryAllDeptTaskAchievements(@RequestBody(required=true) Map<String,Object> map){
		String beginDate=null;
		String endDate=null;
		if(!map.isEmpty()){
			  beginDate = map.get("beginDate").toString()+" 00:00:00";
		      endDate = map.get("endDate").toString()+" 23:59:59";
		}else{
			String str = "";
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    Calendar lastDate = Calendar.getInstance();
		    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
		    str=sdf.format(lastDate.getTime());
		    beginDate= str+" 00:00:00";
		    endDate=com.xy.admx.common.DateUtil.format2Short(new Date())+" 23:59:59";
		}
		return new SuccessResponse(achievementService.queryAllDeptTaskAchievement(beginDate,endDate));
	}
	/**
	 * 企业各部门生产合格率
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/quality",method=RequestMethod.POST)
	public SuccessResponse queryAllQualifiedAchievements(@RequestBody(required=true) Map<String,Object> map){
		String beginDate=null;
		String endDate=null;
		if(!map.isEmpty()){
			  beginDate = map.get("beginDate").toString()+" 00:00:00";
		      endDate = map.get("endDate").toString()+" 23:59:59";
		}else{
			String str = "";
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    Calendar lastDate = Calendar.getInstance();
		    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
		    str=sdf.format(lastDate.getTime());
		    beginDate= str+" 00:00:00";
		    endDate=com.xy.admx.common.DateUtil.format2Short(new Date())+" 23:59:59";
		}
		return new SuccessResponse(achievementService.queryAllQualifiedAchievement(beginDate,endDate));
	}
	/**
	 * 企业各部门生产效率
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/efc",method=RequestMethod.POST)
	public SuccessResponse queryAllAchievements(@RequestBody(required=true) Map<String,Object> map){
		String beginDate=null;
		String endDate=null;
		if(!map.isEmpty()){
			  beginDate = map.get("beginDate").toString()+" 00:00:00";
		      endDate = map.get("endDate").toString()+" 23:59:59";
		}else{
			String str = "";
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    Calendar lastDate = Calendar.getInstance();
		    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
		    str=sdf.format(lastDate.getTime());
		    beginDate= str+" 00:00:00";
		    endDate=com.xy.admx.common.DateUtil.format2Short(new Date())+" 23:59:59";
		}
		return new SuccessResponse(achievementService.queryAllEfficiencyAchievement(beginDate,endDate));
	}
	/**
	 * 任务完成率
	 * 
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/task/{executor}",method=RequestMethod.GET)
	public SuccessResponse query(@PathVariable("executor") Long executor, String beginDate,String endDate)throws Exception {
		Map<String,Object> paramMap = achievementService.queryEmployeeAchievement(executor, new Date(),  new Date(), true);
		return new SuccessResponse(paramMap);
	}	
	
	/**
	 * 查询所有部门
	 */
	@ResponseBody
	@RequestMapping(value="/queryDept",method=RequestMethod.GET)
	public SuccessResponse queryDept()throws Exception {
		List<Map<String,Object>> paramMap = achievementService.getNormalDepartment();
		return new SuccessResponse(paramMap);
	}
	/**
	 * 查询所有员工
	 */
	@ResponseBody
	@RequestMapping(value="/queryEmp",method=RequestMethod.GET)
	public SuccessResponse queryEmp()throws Exception {
		List<Map<String,Object>> paramMap = achievementService.getEmp();
		return new SuccessResponse(paramMap);
	}
	
	
	/**
	 * 查询单个员工在某个时间段内的任务完成率饼图
	 * @param employeeId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * */
	@ResponseBody
	@RequestMapping(value="/queryEmpTaskCompletionRatePie/{employeeId}",method=RequestMethod.GET)
	public SuccessResponse empTaskCompletionRatePie(@PathVariable("employeeId") Long employeeId, String beginDate,String endDate){
		return new SuccessResponse(achievementService.queryEmpTaskCompletionRatePie(employeeId, beginDate, endDate));
	}
	
	/**
	 * 查询单个人员在某个时间段内的任务完成柱状图
	 * @param employeeId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * */
	@ResponseBody
	@RequestMapping(value="/queryEmpTaskCompletionRateHistogram/{employeeId}",method=RequestMethod.GET)
	public SuccessResponse queryEmpTaskCompletionRateHistogram(@PathVariable("employeeId") Long employeeId, String beginDate,String endDate){
		if(CommonFunction.isNull(beginDate)){
			String str = "";
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    Calendar lastDate = Calendar.getInstance();
		    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
		    str=sdf.format(lastDate.getTime());
		    endDate=com.xy.admx.common.DateUtil.format2Short(new Date());
		}
		return new SuccessResponse(achievementService.queryEmpTaskCompletionRateHistogram(employeeId, beginDate, endDate));
	}
	
	/**
	 * 查询单个员工在某个时间段内的生产合格率饼图
	 * @param employeeId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * */
	@ResponseBody
	@RequestMapping(value="/queryEmpQualifiedProductionPie/{employeeId}",method=RequestMethod.GET)
	public SuccessResponse queryEmpQualifiedProductionPie(@PathVariable("employeeId") Long employeeId, String beginDate,String endDate){
		return new SuccessResponse(achievementService.queryEmpQualifiedProductionPie(employeeId, beginDate, endDate));
	}
	
	/**
	 * 查询单个员工在某个时间段内的生产合格率柱状图
	 * @param employeeId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * */
	@ResponseBody
	@RequestMapping(value="/queryEmpQualifiedProductionHistogram/{employeeId}",method=RequestMethod.GET)
	public SuccessResponse queryEmpQualifiedProductionHistogram(@PathVariable("employeeId") Long employeeId, String beginDate,String endDate){
		if(CommonFunction.isNull(beginDate)){
			String str = "";
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    Calendar lastDate = Calendar.getInstance();
		    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
		    str=sdf.format(lastDate.getTime());
		    endDate=com.xy.admx.common.DateUtil.format2Short(new Date());
		}
		return new SuccessResponse(achievementService.queryEmpQualifiedProductionHistogram(employeeId, beginDate, endDate));
	}
	

	/**
	 * 查询单个员工在某个时间段内的生产效率饼图
	 * @param employeeId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * */
	@ResponseBody
	@RequestMapping(value="/empProductionEfficiency/{employeeId}",method=RequestMethod.GET)
	public SuccessResponse empProductionEfficiencyPie(@PathVariable("employeeId") Long employeeId, String beginDate,String endDate){
		return new SuccessResponse(achievementService.queryEmpEfcPie(employeeId, beginDate, endDate));
	}
	
	/**
	 * 查询单个人员在某个时间段内的生产效率柱状图
	 * @param employeeId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * */
	@ResponseBody
	@RequestMapping(value="/empProductionEfficiencyHistogram/{employeeId}",method=RequestMethod.GET)
	public SuccessResponse empProductionEfficiencyHistogram(@PathVariable("employeeId") Long employeeId, String beginDate,String endDate){
		if(CommonFunction.isNull(beginDate)){
			String str = "";
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    Calendar lastDate = Calendar.getInstance();
		    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
		    str=sdf.format(lastDate.getTime());
		    endDate=com.xy.admx.common.DateUtil.format2Short(new Date());
		}
		return new SuccessResponse(achievementService.queryEmpEfcHis(employeeId, beginDate, endDate));
	}
}
