package com.xy.cms.action.system;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.xy.cms.bean.JsTree;
import com.xy.cms.common.CacheFun;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.FileUtil;
import com.xy.cms.common.ImportExecl;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Company;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Eqiupment;
import com.xy.cms.entity.EqiupmentType;
import com.xy.cms.entity.EquipmentParamData;
import com.xy.cms.service.CompanyService;
import com.xy.cms.service.DepartmentService;
import com.xy.cms.service.EquipmentService;
import com.xy.cms.service.EquipmentTypeService;
import com.xy.cms.utils.UploadFileUtils;

public class EquipmentAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EquipmentService equipmentService;
	private DepartmentService departmentService;
	private Eqiupment eqiupment;
	private File file;
	private String fileFileName;
	private File image;
	private String imageFileName;
	private CompanyService companyService;
	private EquipmentTypeService equipmentTypeService;
	/**
	 * 设备状态
	 */
	public static enum Status{
		wait(0),exception(1),run(2),off(3);
		private Integer code;
		private Status(Integer code){
			this.code=code;
		}
		public static Status getStatus(Integer code){
			if(code==null){
				return null;
			}
			Status[] status = values();
			for (Status status2 : status) {
				if(status2.getCode().equals(code)){
					return status2;
				}
			}
			return null;
		}
		public Integer getCode() {
			return code;
		}
	}
	/**
	 * 初始化
	 */
	public String init(){
		return "init";
	}
	
	
	/**
	 * 设备基本信息初始化
	 */
	public String baseinform_Init(){
		tree_init();
		return "baseinfor_init";
	}
	
	/**
	 * 公用方法
	 * 点击设备类型，查询所有的设备类型
	 * 
	 * */
	public String queryEquipmentType(){
		//查询所有的设备类型
		List<EqiupmentType> eqiupmentTypeTypeList = equipmentTypeService.quertEqiupmentTypeAll();
		List<JsTree> treeList=new ArrayList<JsTree>();
		for (EqiupmentType eqiupmentType : eqiupmentTypeTypeList) {
			JsTree root=new JsTree();
			root.setText(eqiupmentType.getType());
			root.setId(eqiupmentType.getId().toString());
			root.setParent((eqiupmentType.getPid()==null || eqiupmentType.getPid()==0)?"#":eqiupmentType.getPid().toString());
			treeList.add(root);
		}
		String treeJson=JSON.toJSONString(treeList);
		request.setAttribute("tree",StringEscapeUtils.escapeHtml4(treeJson));
		return "equipmentType";
	}
	
	/**
	 * 公用方法
	 * 点击部门名称，查询所有的部门
	 * 
	 * */
	public String queryDepartment(){
		//查询正常使用的组织机构
		List<Department> departmentList = departmentService.getNormalDepartment();
		//查询公司的名称
		Company company =companyService.getCompanyById(1L);
		//初始化js树形结构
		List<JsTree> treeList = new ArrayList<JsTree>();
		if(CommonFunction.isNotNull(company)){
			JsTree root = new JsTree();
			root.setText(company.getName());
			root.setId("0");
			root.setParent("#");
			root.setState(new JsTree.State(true));
			treeList.add(root);
		 }else{
			JsTree root = new JsTree();
			root.setText("请先添加企业基本信息");
			root.setId("0");
			root.setParent("#");
			root.setState(new JsTree.State(true));
			treeList.add(root);
		}
		for (Department dpt : departmentList) {
			JsTree jsTree = new JsTree();
			jsTree.setId(dpt.getId().toString());
			jsTree.setText(dpt.getName());
			jsTree.setParent(dpt.getPid().toString());
			treeList.add(jsTree);
		}
		String treeJson = JSON.toJSONString(treeList);
		request.setAttribute("tree", treeJson);
		return "department";
	}
	
	/**
	 * 设备运行数据初始化
	 */
	public String oparainforma_Init(){
		//tree_init();
		/*String str = "";
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    Calendar lastDate = Calendar.getInstance();
	    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
	    str=sdf.format(lastDate.getTime());
	    String beginDate= str;*/
	    String endDate=DateUtil.format2Short(new Date());
	    request.setAttribute("beginDate", endDate);
	    //request.setAttribute("endDate", endDate);
		return "oparainforma_Init";
	}
	
	/**
	 * 设备运行数据列表页
	 * @return
	 */
	public String operInfor_query() {
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				String eqName = request.getParameter("eqName");
				pageMap.put("eqName", eqName); 
				//所属部门(这里查询到的是部门的id)
				String deptId = request.getParameter("deptId");
				pageMap.put("deptId", deptId);
				//用户设备编号
				String eqUserCode = request.getParameter("eqUserCode");
				pageMap.put("eqUserCode", eqUserCode);
				//系统设备编号
				String eqCode = request.getParameter("eqCode");
				pageMap.put("eqCode", eqCode);
				//开始时间以及结束时间
				String beginDate=request.getParameter("beginDate");
				//String endDate=request.getParameter("endDate");
				//设备类型
				String eqType = request.getParameter("eqType");
				pageMap.put("eqType", eqType); 
				if(CommonFunction.isNull(beginDate)){
					/*String str = "";
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					Calendar lastDate = Calendar.getInstance();
					lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
					str=sdf.format(lastDate.getTime());*/
					beginDate=DateUtil.format2Short(new Date());
					//endDate=DateUtil.format2Short(new Date());
				}
				pageMap.put("beginDate", beginDate);
				 request.setAttribute("beginDate", beginDate);
				//pageMap.put("endDate", endDate);
				QueryResult queryResult = equipmentService.queryOperInfor(pageMap);
				return queryResult;
			}
		});
		return "operInfor_query";
	}
	
	/**
	 * 查询单个设备24小时运行数据初始化页面
	 * @return
	 */
	public String queryRecordInit(){
		String equipmentId=request.getParameter("equipmentId");
		session.put("equipmentId", equipmentId);
		return "recordInit";
	}
	/**
	 * 查询单个设备24小时运行数据
	 * @return
	 */
	public String queryRecordList(){
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				//设备id
				String equipmentId=(String) session.get("equipmentId");
				pageMap.put("equipmentId", equipmentId);
				//选项（24小时数据还是警报数据）
				String type = request.getParameter("type");
				if(CommonFunction.isNull(type)){
					type="1";
				}
				pageMap.put("type", type);
				//开始时间以及结束时间
				Date date= new Date();
				Calendar calendar = Calendar.getInstance();  
				calendar.setTime(date);  
				calendar.add(Calendar.DAY_OF_MONTH, -1);//系统时间的前一天
				date = calendar.getTime(); 
				String beginDate=DateUtil.format2Full(date);
				String endDate=DateUtil.format2Full(new Date());
				pageMap.put("beginDate", beginDate);
				pageMap.put("endDate", endDate);
				QueryResult queryResult = equipmentService.queryRecord(pageMap);
				return queryResult;
			}
		});
		return "recordList";
	}

	private void tree_init() {
		//查所有的部门
		//查询正常使用的组织机构
		List<Department> departmentList = departmentService.getNormalDepartment();
		//查询公司的名称
		Company company =companyService.getCompanyById(1L);
		//初始化js树形结构
		List<JsTree> treeList = new ArrayList<JsTree>();
		if(CommonFunction.isNotNull(company)){
			JsTree root = new JsTree();
			root.setText(company.getName());
			root.setId("0");
			root.setParent("#");
			root.setState(new JsTree.State(true));
			treeList.add(root);
		}else{
			JsTree root = new JsTree();
			root.setText("请先添加企业基本信息");
			root.setId("0");
			root.setParent("#");
			root.setState(new JsTree.State(true));
			treeList.add(root);
		}
		for (Department dpt : departmentList) {
			JsTree jsTree = new JsTree();
			jsTree.setId(dpt.getId().toString());
			jsTree.setText(dpt.getName());
			jsTree.setParent(dpt.getPid().toString());
			treeList.add(jsTree);
		}
		String treeJson = JSON.toJSONString(treeList);
		request.setAttribute("orgtree", treeJson);
	}
	
	
	
	/**
	 * 初始化
	 * **/
	public String oper_dialog_init(){
		//得到设备id
		/*String equmentId = request.getParameter("equmentId");
		session.put("equmentId", equmentId);
		request.setAttribute("date", DateUtil.format2Short(new Date()));*/
		return "oper_dialog_init";
	}
	
	/**
	 *
	 * 
	 * 设备运行数据环形图
	 * */
	public String oper_dialog(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到设备id
			String equmentId=request.getParameter("equmentId");
			if (CommonFunction.isNull(equmentId)) {
				throw new BusinessException("参数错误");
			}
			//得到要查询的时间
			String date=request.getParameter("date");
			//加工件数
			Map number = equipmentService.queryNumberByIdAndDate(equmentId, date);
			if(CommonFunction.isNotNull(number.get("number"))){
				json.put("number", number.get("number"));
			}else{
				json.put("number", 0);
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("equmentId", equmentId);
			map.put("date", date);
			List<EquipmentParamData> list = equipmentService.queryEquipmentParamDataByDateAndId(map);
			//开机与关机时间占比
			StringBuffer boot = new StringBuffer();
			//工作与其他时间占比
			StringBuffer work = new StringBuffer();
			if(list.size()>0){
				//开始前的占比
				boot.append(division1(DateUtil.format3String(date+" 00:00:00").getTime()/1000,list.get(0).getReceiveTime().getTime()/1000)+"-");
				work.append(division1(DateUtil.format3String(date+" 00:00:00").getTime()/1000,list.get(0).getReceiveTime().getTime()/1000)+"-");
				int bootopenValue = 0;//开机
				int bootshutValue = 0;//关机
				int workopenValue = 0;//运行
				int workshutValue = 0;//其他
				for(int i=0;i<list.size();i++){
					//开机时间
					if(list.get(i).getParamterSix()*60==list.get(i).getParamterThree()){
						if(bootshutValue!=0){
								boot.append(division2(bootshutValue+list.get(i).getParamterSix()*60-list.get(i).getParamterThree())+"-");
								bootshutValue= 0;
						}
						bootopenValue+=list.get(i).getParamterThree();//累计开机时间
					}else if(list.get(i).getParamterThree()==0){
						if(bootopenValue!=0){
							boot.append(division2(bootopenValue+list.get(i).getParamterThree())+"-");
							bootopenValue = 0;
						}
						bootshutValue+=list.get(i).getParamterSix()*60;
					}else{
						if(bootshutValue!=0){
							boot.append(division2(bootshutValue+list.get(i).getParamterSix()*60-list.get(i).getParamterThree())+"-");
							bootshutValue= 0;
						}
						boot.append(division2(bootopenValue+list.get(i).getParamterThree())+"-");
						if(i!=list.size()-1){
							if(list.get(i+1).getParamterThree()!=0){
								//关机时间
								boot.append(division2(bootshutValue+list.get(i).getParamterSix()*60-list.get(i).getParamterThree())+"-");
								bootshutValue= 0;
							}
						}
						bootopenValue = 0;
					}
					
					
					//工作时间
					if(list.get(i).getParamterSix()*60==list.get(i).getParamterFour()){
						if(workshutValue!=0){
							work.append(division2(workshutValue+list.get(i).getParamterSix()*60-list.get(i).getParamterFour())+"-");
								workshutValue= 0;
						}
						workopenValue+=list.get(i).getParamterFour();//累计开机时间
					}else if(list.get(i).getParamterFour()==0){
						if(workopenValue!=0){
							work.append(division2(workopenValue+list.get(i).getParamterFour())+"-");
							workopenValue = 0;
						}
						workshutValue+=list.get(i).getParamterSix()*60;
					}else{
						if(workshutValue!=0){
							work.append(division2(workshutValue+list.get(i).getParamterSix()*60-list.get(i).getParamterFour())+"-");
							workshutValue= 0;
						}
						work.append(division2(workopenValue+list.get(i).getParamterFour())+"-");
						if(i!=list.size()-1){
							if(list.get(i+1).getParamterFour()!=0){
								//关机时间
								work.append(division2(workshutValue+list.get(i).getParamterSix()*60-list.get(i).getParamterFour())+"-");
								workshutValue= 0;
							}
						}
						workopenValue = 0;
					}
				}
				//最后的占比
				boot.append(division1(list.get(list.size()-1).getReceiveTime().getTime()/1000,DateUtil.format3String(date+" 23:59:59").getTime()/1000));
				work.append(division1(list.get(list.size()-1).getReceiveTime().getTime()/1000,DateUtil.format3String(date+" 23:59:59").getTime()/1000));
			}

			json.put("boot", boot);
			json.put("work", work);
			json.put("successflag", "1");
			json.put("code",0);
		} catch (BusinessException e) {		
			json.put("code",1);
			json.put("msg", e.getMessage());
			logger.error(e.getMessage(), e);			
		}catch (Exception e) {
			json.put("code", 1);
			json.put("msg","服务器出现异常" );			
			logger.error(e.getMessage(), e);
		}
		finally{

			if(out!=null){
				Gson gson = new Gson();
				out.print(gson.toJson(json));
				out.close();
			}					
		}			
		return NONE;
	}
	
	/**
	 * 计算时间占比
	 * */
	public String division1(long a ,long b){
		 String result = "";
		 long c = 86400;
	     float num =(float)(b-a)/c*100;
	     DecimalFormat df = new DecimalFormat("0.00");
	     result = df.format(num);
	     return result;
	}
	
	public String division2(long a){
		 String result = "";
		 long b = 86400;
	     float num =(float)a/b*100;
	     DecimalFormat df = new DecimalFormat("0.00");
	     result = df.format(num);
	     return result;
	}
	
	/**
	 * 设备产能利用率数据初始化
	 */
	public String capainforma_Init(){
		tree_init();
		String str = "";
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    Calendar lastDate = Calendar.getInstance();
	    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
	    str=sdf.format(lastDate.getTime());
	    String beginDate= str;
	    String endDate=DateUtil.format2Short(new Date());
	    request.setAttribute("beginDate", beginDate);
	    request.setAttribute("endDate", endDate);
		return "capa_Init";
	}
	
	/**
	 * 设备产能利用率数据列表页
	 * @return
	 */
	public String capaInfor_query() {
		list();
		return "capaInfor_query";
	}
	
	
	/**
	 * 设备产能利用率对话框初始化--饼状图
	 */
	public String capainforma_dialog(){
		try {
			String equmentId=request.getParameter("eqId");
			String beginDate=request.getParameter("beginDate");
			String endDate=request.getParameter("endDate");
			if(CommonFunction.isNull(beginDate)){
				String str = "";
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    Calendar lastDate = Calendar.getInstance();
			    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
			    str=sdf.format(lastDate.getTime());
			    beginDate= str;
			    endDate=DateUtil.format2Short(new Date());
			}
			if(CommonFunction.isNull(equmentId)){
				throw new BusinessException("设备id为空");
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("equmentId", equmentId.split(","));
			map.put("beginDate", beginDate);
			map.put("endDate", endDate);
			//设备产能利用率
			Map capacityMap=equipmentService.queryEqiupmentCapacity(map);
			//设备空载率
			Map idlingMap=equipmentService.queryEqiupmentIdling(map);
			//故障率
			Map faultMap=equipmentService.queryEqiupmentFault(map);
			request.setAttribute("capacity", capacityMap.get("capacity"));
			request.setAttribute("idling", idlingMap.get("idling"));
			request.setAttribute("fault", faultMap.get("fault"));
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "capainforma_dialog";
	}
	
	/**
	 * 产能利用率对话框初始化--组合图
	 */
	public String capainforma_dialog2(){
		//根据多个设备id查询设备产能利用率(组合图)
		try {
			String equmentId=request.getParameter("eqId");
			String beginDate=request.getParameter("beginDate");
			String endDate=request.getParameter("endDate");
			if(CommonFunction.isNull(equmentId)){
				throw new BusinessException("设备id为空");
			}
			List<String> listPid = Arrays.asList(equmentId.split(","));
			Date beginDateS = null;
			Date endDateS = null;
			if(CommonFunction.isNotNull(beginDate)){
				beginDateS= DateUtil.format2String(beginDate);
				endDateS= DateUtil.format2String(endDate);
			}else{
				String str = "";
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    Calendar lastDate = Calendar.getInstance();
			    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
			    str=sdf.format(lastDate.getTime());
			    beginDateS= DateUtil.format2String(str);
			    endDateS=DateUtil.format2String(DateUtil.format2Short(new Date()));
			}
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			for(int i = 0;i<listPid.size();i++){
				Map<String,String> newMap = new HashMap<String,String>();
				List<Map> faultMap=equipmentService.queryEqiupmentCapacityGroup(Long.parseLong(listPid.get(i)),beginDateS,endDateS);
				Eqiupment eqiupment = equipmentService.getEqiupmentById(Long.parseLong(listPid.get(i)));
				StringBuffer value = new StringBuffer();
				for(int j = 0;j<faultMap.size();j++){
					value.append(faultMap.get(j).get("result")==null?0:faultMap.get(j).get("result")).append("-");
				}
				newMap.put(eqiupment.getEquipmentName(), (String) value.toString().subSequence(0, value.length()-1));
				list.add(newMap);
			}
			request.setAttribute("list",list);
			//时间集合
			List<Date> dateList = DateUtil.getDatesBetweenTwoDate(beginDateS,endDateS);
			StringBuffer str=new StringBuffer();
			for(int i = 0;i<dateList.size();i++){
				str.append(DateUtil.format2Long(dateList.get(i).getTime())).append("##");
			}
			request.setAttribute("dateList", str.toString().subSequence(0, str.length()-2));
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		
		return "capainforma_dialog2";
	}
	
	/**
	 * 设备其他数据初始化
	 */
	public String otherinforma_Init(){
		tree_init();
		String str = "";
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    Calendar lastDate = Calendar.getInstance();
	    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
	    str=sdf.format(lastDate.getTime());
	    String beginDate= str;
	    String endDate=DateUtil.format2Short(new Date());
	    request.setAttribute("beginDate", beginDate);
	    request.setAttribute("endDate", endDate);
		return "other_Init";
	}
	
	/**
	 *================= 设备有效利用率开始
	 */
	
	/**
	 * 设备有效利用率数据初始化
	 */
	public String efftinforma_Init(){
		tree_init();
		String str = "";
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    Calendar lastDate = Calendar.getInstance();
	    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
	    str=sdf.format(lastDate.getTime());
	    String beginDate= str;
	    String endDate=DateUtil.format2Short(new Date());
	    request.setAttribute("beginDate", beginDate);
	    request.setAttribute("endDate", endDate);
		return "efftinforma_Init";
	}
	/**
	 * 设备有效利用率数据列表页
	 * @return
	 */
	public String efftInfor_query() {
		list();
		return "efftInfor_query";
	}
	/**
	 * 设备有效利用率对话框初始化--饼状图
	 */
	public String efftinforma_dialog(){
		try {
			String equmentId=request.getParameter("eqId");
			String beginDate=request.getParameter("beginDate");
			String endDate=request.getParameter("endDate");
			if(CommonFunction.isNull(beginDate)){
				String str = "";
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    Calendar lastDate = Calendar.getInstance();
			    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
			    str=sdf.format(lastDate.getTime());
			    beginDate= str;
			    endDate=DateUtil.format2Short(new Date());
			}
			if(CommonFunction.isNull(equmentId)){
				throw new BusinessException("设备id为空");
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("equmentId", equmentId.split(","));
			map.put("beginDate", beginDate);
			map.put("endDate", endDate);
			//设备有效利用率
			Map efcData=equipmentService.queryEqiupmentEfcs(map);
			request.setAttribute("efcData", efcData);
			//设备故障率
			Map disEfcData=equipmentService.queryEqiupmentDisEfcs(map);
			request.setAttribute("disEfcData", disEfcData);
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "efftinforma_dialog";
	}
	/**
	 * 设备有效利用率对话框初始化--组合图
	 */
	public String efftinforma_histogram(){
		try {
			String equmentId=request.getParameter("eqId");
			String beginDate=request.getParameter("beginDate");
			String endDate=request.getParameter("endDate");
			if(CommonFunction.isNull(equmentId)){
				throw new BusinessException("设备id为空");
			}
			List<String> listPid = Arrays.asList(equmentId.split(","));
			Date beginDateS = null;
			Date endDateS = null;
			if(CommonFunction.isNotNull(beginDate)){
				beginDateS= DateUtil.format2String(beginDate);
				endDateS= DateUtil.format2String(endDate);
			}else{
				String str = "";
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    Calendar lastDate = Calendar.getInstance();
			    lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
			    str=sdf.format(lastDate.getTime());
			    beginDateS= DateUtil.format2String(str);
			    endDateS=DateUtil.format2String(DateUtil.format2Short(new Date()));
			}
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			for(int i = 0;i<listPid.size();i++){
				Map<String,String> newMap = new HashMap<String,String>();
				List<Map> faultMap=equipmentService.queryEqEfc(Long.parseLong(listPid.get(i)),beginDateS,endDateS);
				Eqiupment eqiupment = equipmentService.getEqiupmentById(Long.parseLong(listPid.get(i)));
				StringBuffer value = new StringBuffer();
				for(int j = 0;j<faultMap.size();j++){
					value.append(faultMap.get(j).get("efc")==null?0:faultMap.get(j).get("efc")).append("-");
				}
				newMap.put(eqiupment.getEquipmentName(), (String) value.toString().subSequence(0, value.length()-1));
				list.add(newMap);
			}
			request.setAttribute("list",list);
			//时间集合
			List<Date> dateList = DateUtil.getDatesBetweenTwoDate(beginDateS,endDateS);
			StringBuffer str=new StringBuffer();
			for(int i = 0;i<dateList.size();i++){
				str.append(DateUtil.format2Long(dateList.get(i).getTime())).append("##");
			}
			request.setAttribute("dateList", str.toString().subSequence(0, str.length()-2));
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "efftinforma_dialog2";
	}
	/**
	 *================= 设备有效利用率结束
	 */
	
	/**
	 * 数据列表页
	 * @return
	 */
	public String query() {
		base_query();
		return "list";
	}
	/**
	 * 基础数据列表页
	 */
	private void base_query() {
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				//设备类型（名称）
				String eqType = request.getParameter("equipType");
				pageMap.put("eqType", eqType);
				//设备名称
				String eqName = request.getParameter("eqName");
				pageMap.put("eqName", eqName); 
				//系统设备编号
				String eqCode = request.getParameter("eqCode");
				pageMap.put("eqCode", eqCode);
				//用户设备编号
				String eqUserCode = request.getParameter("eqUserCode");
				pageMap.put("eqUserCode", eqUserCode);
				//所属部门（名称）
				/*String deName = request.getParameter("deName");
				pageMap.put("deName", deName);*/
				//所属部门(这里查询到的是部门的id)
			    String deptId = request.getParameter("deptId");
				pageMap.put("deptId", deptId);	
				return equipmentService.queryAllEquipment(pageMap);
			}
		});
		if (CommonFunction.isNotNull(this.list)) {
			Long[] ids = new Long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Eqiupment eqiupment = (Eqiupment)((Object[])list.get(i))[0];
				ids[i] = eqiupment.getId();
			}
			if (ids.length > 0) {
				Map state = equipmentService.getEquipmentStates(ids);
				request.setAttribute("state", state);
			}
		}
	}
	
	/**
	 * 设备基本信息数据列表页
	 * @return
	 */
	public String baseInfor_query() {
		base_query();
		return "baseInfor_query";
	}
	
	/**
	 * 设备其他数据列表页
	 * @return
	 */
	public String otherInfor_query() {
		list();
		return "otherInfor_query";
	}
	
	
	/**
	 * 预添加设备
	 * @return
	 */
	public String preAdd(){
		return "add";
	}
	/**
	 * 添加设备
	 * @return
	 */
	public String add(){
		try {
			if(CommonFunction.isNull(eqiupment)){
				throw new BusinessException("参数错误");
			}
			equipmentService.saveEqiupment(eqiupment);	
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "add";
	}
	
	/**
	 * 预修改设备
	 * @return
	 */
	public String preEdit(){
		try {
			String id=request.getParameter("id");
			
			if(CommonFunction.isNull(id)){
				throw new BusinessException("订单Id为空");
			}
			Eqiupment eqiupment= equipmentService.getEqiupmentById(Long.parseLong(id));
			request.setAttribute("eqiupment", eqiupment);
			request.setAttribute("eqType", equipmentService.queryEqType(eqiupment.getEquipmentType()));
			request.setAttribute("dept", departmentService.getDepartmentById(eqiupment.getDept().longValue()));
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		
		return "edit";
	}
	/**
	 * 修改设备信息
	 * @return
	 */
	public String edit(){
		try {
			if(CommonFunction.isNull(eqiupment)){
				throw new BusinessException("参数错误");
			}
			equipmentService.updateEqiupment(eqiupment);
			this.message = "修改成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "edit";
	}
	/**
	 * 删除一个设备信息
	 * @return
	 */
	public String delEquipment(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到设备id
			String id=request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			Eqiupment eq=equipmentService.getEqiupmentById(Long.parseLong(id));
			if(CommonFunction.isNull(eq)){
				throw new BusinessException("该设备已删除");
			}else{
				equipmentService.delEquipment(eq);
				json.put("successflag", "1");
				json.put("code",0);
			}
		} catch (BusinessException e) {		
			json.put("code",1);
			json.put("msg", e.getMessage());
			logger.error(e.getMessage(), e);			
		}catch (Exception e) {
			json.put("code", 1);
			json.put("msg","服务器出现异常" );			
			logger.error(e.getMessage(), e);
		}
		finally{

			if(out!=null){
				Gson gson = new Gson();
				out.print(gson.toJson(json));
				out.close();
			}					
		}			
		return NONE;
	}
	/**
	 * 上传设备图片
	 */
	public String uploadImage() {
		try {
			String rootPath = CacheFun.getConfig("pic_path");
			String nowPath = UploadFileUtils.getPathByYearAndMonth();
			String picPath = rootPath + File.separator + nowPath;
			String fileName = UploadFileUtils.createNewFileName() + imageFileName.substring(imageFileName.lastIndexOf("."));
			new File(picPath).mkdirs();// 创建新的文件名
			File newFile = new File(picPath + File.separator + fileName);
			FileUtil.copy(image,newFile);
			/*BufferedImage sourceImg =ImageIO.read(new FileInputStream(newFile));
			int source_w = sourceImg.getWidth();
			int source_h = sourceImg.getHeight();*/
			//图片压缩
			String bwidth = request.getParameter("bg_width");
			int b_width = Integer.valueOf(CommonFunction.isNotNull(bwidth)?bwidth.trim():"0");
			if(b_width>0){
				String sourceImage = picPath + File.separator + fileName;
				String bheight = request.getParameter("bg_height");
				String bgImg = picPath + File.separator + "bg_"+fileName;
				int b_height = Integer.valueOf(CommonFunction.isNotNull(bheight.trim())?bheight.trim():"0");
				if(b_height>0){
					FileUtil.createSImg(sourceImage, bgImg, b_width, b_height);
				}
				String swidth = request.getParameter("s_width");
				int s_width = Integer.valueOf(CommonFunction.isNotNull(swidth.trim())?swidth.trim():"0");
				String sImg = picPath + File.separator + "s_"+fileName;
				if(s_width>0){
					String sheight = request.getParameter("s_height");
					int s_height = Integer.valueOf(CommonFunction.isNotNull(sheight.trim())?sheight.trim():"0");
					if(s_height>0){
						FileUtil.createSImg(sourceImage, sImg, s_width,s_height);
					}
				}else{
					FileUtil.createSImg(sourceImage, sImg, b_width/2,b_height/2);
				}
			}
			request.setAttribute("code", 1);
			request.setAttribute("fileName", (nowPath + File.separator + fileName).replace("\\", "/"));
		}
		catch (Exception e) {
			this.message = "上传图片出现异常：服务器出现异常";
			logger.error(e.getMessage(), e);
		}

		return SUCCESS;
	}
	/**
	 * 预批量导入设备
	 * */
	public String preImportEquipment(){
		return "import";
	}
	/**
	 * 导入设备信息
	 * @return
	 */
	public String importEquipment(){
		try {
			String path = ServletActionContext.getServletContext().getRealPath("/excels");
			File upload = new File(path+File.separator+fileFileName);
			FileUtil.copy(file, upload);
			ImportExecl poi = new ImportExecl(); 
			List<List<String>> list = poi.read(path+File.separator+fileFileName);
			if (list != null) {
				List<Eqiupment> eqiupmentList = new ArrayList<Eqiupment>();
				for(int i=1;i<list.size();i++){
					List<String> newList = list.get(i);
					Eqiupment eqiupment = new Eqiupment();
					//用户编号
					eqiupment.setUserEquipmentCode(CommonFunction.isNotNull(newList.get(0).trim())?newList.get(0).trim():null);
					//设备名称
					eqiupment.setEquipmentName(CommonFunction.isNotNull(newList.get(1).trim())?newList.get(1).trim():null);
					//规格-型号
					eqiupment.setEquipmentModel(CommonFunction.isNotNull(newList.get(2).trim())?newList.get(2).trim():null);
					//设备类型 -级
					Long etId=null;
					if(CommonFunction.isNotNull(newList.get(3).trim())){
						EqiupmentType et=equipmentService.getEqTpyeByName(newList.get(3).trim());
						if(CommonFunction.isNotNull(et)){//设备存在
							etId=et.getId();
							if(CommonFunction.isNotNull(newList.get(4).trim())){//二级设备
								EqiupmentType etSec=equipmentService.getEqTpyeByNameAndPid(newList.get(4).trim(), etId);
								if(CommonFunction.isNotNull(etSec)){//二级设备存在
									etId=etSec.getId();
									if(CommonFunction.isNotNull(newList.get(5))){
										EqiupmentType etThr =equipmentService.getEqTpyeByNameAndPid(newList.get(5), etId);
										if(CommonFunction.isNotNull(etThr)){
											etId=etThr.getId();
										}else{
											EqiupmentType subTh=new EqiupmentType();
											subTh.setType(newList.get(5).trim());
											subTh.setPid(etId.intValue());
										}
									}
								}else{//二级设备不存在
									EqiupmentType subEt=new EqiupmentType();
									subEt.setType(newList.get(4).trim());
									subEt.setPid(etId.intValue());
									etId=equipmentService.addEqiupmentTypeWithId(subEt);
									if(CommonFunction.isNotNull(newList.get(5))){
										EqiupmentType etThr =equipmentService.getEqTpyeByNameAndPid(newList.get(5), etId);
										if(CommonFunction.isNotNull(etThr)){
											etId=etThr.getId();
										}else{
											EqiupmentType subTh=new EqiupmentType();
											subTh.setEtType(newList.get(5).trim());
											subTh.setPid(etId.intValue());
										}
									}
								}
							}
						}else{//一级设备不存在
							et=new EqiupmentType();
							et.setType(newList.get(3).trim());
							et.setPid(null);
							etId=equipmentService.addEqiupmentTypeWithId(et);
							if(CommonFunction.isNotNull(newList.get(4).trim())){//二级设备
								EqiupmentType etSec=equipmentService.getEqTpyeByNameAndPid(newList.get(4).trim(), etId);
								if(CommonFunction.isNotNull(etSec)){//二级设备存在
									etId=etSec.getId();
									if(CommonFunction.isNotNull(newList.get(5))){
										EqiupmentType etThr =equipmentService.getEqTpyeByNameAndPid(newList.get(5), etId);
										if(CommonFunction.isNotNull(etThr)){
											etId=etThr.getId();
										}else{
											EqiupmentType subTh=new EqiupmentType();
											subTh.setType(newList.get(5).trim());
											subTh.setPid(etId.intValue());
											etId=equipmentService.addEqiupmentTypeWithId(subTh);
										}
									}
								}else{//二级设备不存在
									EqiupmentType subEt=new EqiupmentType();
									subEt.setType(newList.get(4).trim());
									subEt.setPid(etId.intValue());
									etId=equipmentService.addEqiupmentTypeWithId(subEt);
									if(CommonFunction.isNotNull(newList.get(5))){
										EqiupmentType etThr =equipmentService.getEqTpyeByNameAndPid(newList.get(5), etId);
										if(CommonFunction.isNotNull(etThr)){
											etId=etThr.getId();
										}else{
											EqiupmentType subTh=new EqiupmentType();
											subTh.setType(newList.get(5).trim());
											subTh.setPid(etId.intValue());
											etId=equipmentService.addEqiupmentTypeWithId(subTh);
										}
									}
								}
							}
						}
						
					}
					eqiupment.setEquipmentType(etId.intValue());
					//采购日期
					eqiupment.setBuyDate(DateUtil.str2Date(CommonFunction.isNotNull(newList.get(6).trim())?newList.get(6):null, "yyyy-MM-dd"));
					//设备所属部门
					Long deptId=null;
					if(CommonFunction.isNotNull(newList.get(7).trim())){
						Department dept=departmentService.getDepartmentByName(newList.get(7).trim());//一级部门
						if(CommonFunction.isNotNull(dept)){
							deptId=dept.getId();
							if(CommonFunction.isNotNull(newList.get(8).trim())){
								Department deptTwo=departmentService.getDepartmentByName(newList.get(8).trim());
								if(CommonFunction.isNotNull(deptTwo)){
									deptId=deptTwo.getId();
								}else{
									Department newDept=new Department();
									newDept.setPid(deptId);
									newDept.setName(newList.get(8).trim());
									deptId=departmentService.addDepartmentWithId(newDept);
								}
								
							}
						}else{
							Department deptSec=new Department();
							deptSec.setPid(0L);//放入一级部门
							deptSec.setName(newList.get(5).trim());
							deptId=departmentService.addDepartmentWithId(deptSec);//新增部门 新增后得到id
							if(CommonFunction.isNotNull(newList.get(8).trim())){
								Department deptTwo=departmentService.getDepartmentByName(newList.get(8).trim());
								if(CommonFunction.isNotNull(deptTwo)){
									deptId=deptTwo.getId();
								}else{
									Department newDept=new Department();
									newDept.setPid(deptId);
									newDept.setName(newList.get(8).trim());
									deptId=departmentService.addDepartmentWithId(newDept);
								}
							}
						}
					}
					eqiupment.setDept(deptId);
					eqiupment.setEquipmentWorkTime(CommonFunction.isNotNull(newList.get(9).trim())?newList.get(9).trim():null);
					eqiupmentList.add(eqiupment);
				}
				equipmentService.importEqiupment(eqiupmentList);
			}
			this.message = "保存成功";
			request.setAttribute("successflag", "1");
		} catch (IOException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "import";
	}
	/***
	 * 设备信息打印
	 * @return
	 * @throws BusinessException 
	 */
	public String print() throws BusinessException{
		String ids =request.getParameter("ids");
		if(StringUtils.isEmpty(ids)){
			throw new BusinessException("请至少选择一项");
		}
		String[] id = ids.split(",");
		List<Object[]> list = equipmentService.queryEqiupmentByIds(com.xy.cms.common.Arrays.strToLong(id));
		request.setAttribute("printList", list);
		return "print";
	}
	
	
	/**
	 * 公用方法
	 * 设备有效利用率列表页
	 * 设备产能利用率列表页
	 * 
	 * **/
	private void list() {
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				Map<String,Object> map = new HashMap<String, Object>();
				//设备类型
				String eqType = request.getParameter("equipType");
				pageMap.put("eqType", eqType);
				//设备名称
				String eqName = request.getParameter("eqName");
				pageMap.put("eqName", eqName); 
				//所属部门(这里查询到的是部门的id)
				String deptId = request.getParameter("deptId");
				pageMap.put("deptId", "0");
				//用户设备编号
				String eqUserCode = request.getParameter("eqUserCode");
				pageMap.put("eqUserCode", eqUserCode);
				//系统设备编号
				String eqCode = request.getParameter("eqCode");
				pageMap.put("eqCode", eqCode);
				//开始时间以及结束时间
				String beginDate=request.getParameter("beginDate");
				String endDate=request.getParameter("endDate");
				if(CommonFunction.isNull(beginDate)){
					String str = "";
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					Calendar lastDate = Calendar.getInstance();
					lastDate.roll(Calendar.MONTH, -1);//日期回滚一个月
					str=sdf.format(lastDate.getTime());
					beginDate= str;
					endDate=DateUtil.format2Short(new Date());
				}
				pageMap.put("beginDate", beginDate);
				pageMap.put("endDate", endDate);
				return equipmentService.queryList(pageMap);
			}
		});
		if (CommonFunction.isNotNull(this.list)) {
			Long[] ids = new Long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ids[i] = Long.parseLong(((Map)list.get(i)).get("id").toString());
			}
			if (ids.length > 0) {
				Map state = equipmentService.getEquipmentStates(ids);
				request.setAttribute("state", state);
			}
		}
	}
	
	public EquipmentService getEquipmentService() {
		return equipmentService;
	}
	public void setEquipmentService(EquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}
	public Eqiupment getEqiupment() {
		return eqiupment;
	}
	public void setEqiupment(Eqiupment eqiupment) {
		this.eqiupment = eqiupment;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public DepartmentService getDepartmentService() {
		return departmentService;
	}
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}


	public EquipmentTypeService getEquipmentTypeService() {
		return equipmentTypeService;
	}


	public void setEquipmentTypeService(EquipmentTypeService equipmentTypeService) {
		this.equipmentTypeService = equipmentTypeService;
	}

	
}
