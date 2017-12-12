package com.xy.admx.rest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xy.admx.bean.EquipData;
import com.xy.admx.common.CommonFunction;
import com.xy.admx.common.DateUtil;
import com.xy.admx.common.exception.RestException;
import com.xy.admx.rest.base.BaseRest;
import com.xy.admx.rest.base.ResponseCode;
import com.xy.admx.rest.base.SuccessResponse;
import com.xy.admx.service.EquipmentService;
import com.xy.cms.entity.EquipmentParam;
import com.xy.cms.entity.EquipmentParamData;

@RestController
@RequestMapping("/equip")
public class EquipmentRest extends BaseRest {
	@Resource
	private EquipmentService equipmentService;
	
	@ResponseBody
	@RequestMapping(value="/getEquipmentByDeptId/{deptId}",method=RequestMethod.GET)
	public SuccessResponse dept(@PathVariable("deptId") Long deptId){
		if(CommonFunction.isNull(deptId)){
			throw new RestException(ResponseCode.ILLEGAL_PARAM, "缺少部门id参数");
		}
		return new SuccessResponse(equipmentService.getEquipmentByDeptId(deptId));
	}
	/**
	 * 上传设备参数
	 * @param request
	 * @param paramDatas
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadEquipmentData", method = RequestMethod.POST)
	public SuccessResponse uploadData(HttpServletRequest request, @RequestBody EquipData[] paramDatas) {
		System.out.println(paramDatas);
		if(CommonFunction.isNull(paramDatas)){
			throw new RestException(ResponseCode.ILLEGAL_PARAM,"请求参数错误");
		}
		for (EquipData equipmentParamData : paramDatas) {
			if(CommonFunction.isNull(equipmentParamData.getEqumentId())){
				throw new RestException(ResponseCode.ILLEGAL_PARAM,"设备id参数错误");
			}
			if(CommonFunction.isNull(equipmentParamData.getParamterId())){
				throw new RestException(ResponseCode.ILLEGAL_PARAM,"参数id错误");
			}
			if(CommonFunction.isNull(equipmentParamData.getParamterValue())){
				throw new RestException(ResponseCode.ILLEGAL_PARAM,"参数值错误");
			}
			if(!String.valueOf(equipmentParamData.getParamterValue()).matches("\\d{1,6}(.\\d{1,2})?")){
				throw new RestException(ResponseCode.ILLEGAL_PARAM,"参数值格式错误,最多八位数(六位整数加两位小数)");
			}
			if(CommonFunction.isNull(equipmentParamData.getReceiveTime())){
				throw new RestException(ResponseCode.ILLEGAL_PARAM,"接收时间参数错误");
			}
		}
		
		
		
		Map<String,EquipmentParamData> map=new LinkedHashMap<String,EquipmentParamData>();
		
		List<EquipmentParamData> paramDataList=new ArrayList<EquipmentParamData>();
		for (EquipData equipData : paramDatas) {
			EquipmentParamData equipmentParamData = map.get(equipData.getReceiveTime()+equipData.getEqumentId());
			if(equipmentParamData==null){
				equipmentParamData = new EquipmentParamData();
				equipmentParamData.setReceiveTime(DateUtil.str2Date(equipData.getReceiveTime(), "yyyy-MM-dd HH:mm:ss"));
				equipmentParamData.setEqumentId(equipData.getEqumentId());
				map.put(equipData.getReceiveTime()+equipData.getEqumentId(), equipmentParamData);
				paramDataList.add(equipmentParamData);
			}
			if(equipData.getParamterId()==1){
				equipmentParamData.setParamterOne(equipData.getParamterValue());
			}else if(equipData.getParamterId()==2){
				equipmentParamData.setParamterTwo(equipData.getParamterValue());
			}else if(equipData.getParamterId()==3){
				equipmentParamData.setParamterThree(equipData.getParamterValue());
			}else if(equipData.getParamterId()==4){
				equipmentParamData.setParamterFour(equipData.getParamterValue());
			}else if(equipData.getParamterId()==5){
				equipmentParamData.setParamterFive(equipData.getParamterValue());
			}else if(equipData.getParamterId()==6){
				equipmentParamData.setParamterSix(equipData.getParamterValue());
			}
		}
		
		equipmentService.saveEqData(paramDataList);
		return new SuccessResponse("");
	}
	/**
	 * 同步参数
	 * @param request
	 * @param paramDatas
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/syncParameters", method = RequestMethod.POST)
	public SuccessResponse syncParameters(HttpServletRequest request, @RequestBody EquipmentParam[] params) {
		if(CommonFunction.isNull(params)){
			throw new RestException(ResponseCode.ILLEGAL_PARAM,"请求参数错误");
		}
		for (EquipmentParam equipmentParam : params) {
			if(CommonFunction.isNull(equipmentParam.getParemtId())){
				throw new RestException(ResponseCode.ILLEGAL_PARAM,"参数id错误");
			}
			if(CommonFunction.isNull(equipmentParam.getParamterName())){
				throw new RestException(ResponseCode.ILLEGAL_PARAM,"参数名称错误");
			}
		 }
		List<EquipmentParam> paramList=new ArrayList<EquipmentParam>();
		for (EquipmentParam equipmentParam : params) {
			EquipmentParam ep=new EquipmentParam();
			ep.setParemtId(equipmentParam.getParemtId());
			ep.setParamterName(equipmentParam.getParamterName());
			paramList.add(ep);
		} 
		
		equipmentService.saveEqParam(paramList);
		return new SuccessResponse("");
	}

	
	
	
	
}
