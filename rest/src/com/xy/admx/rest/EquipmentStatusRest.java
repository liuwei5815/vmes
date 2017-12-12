package com.xy.admx.rest;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xy.admx.common.exception.BusinessException;
import com.xy.admx.rest.base.BaseRest;
import com.xy.admx.rest.base.SuccessResponse;
import com.xy.admx.service.EquipmentService;
import com.xy.cms.entity.Eqiupment;

/**
 * 
 * @author xiaojun
 *
 */
@RestController
@RequestMapping("/equipstatus/")
public class EquipmentStatusRest extends BaseRest {
	
	@Resource
	private EquipmentService equipmentService;
	
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
	
	@RequestMapping(value="runing",method=RequestMethod.GET)
	@ResponseBody
	public SuccessResponse running(){
		return new SuccessResponse(equipmentService.queryEquipmentRuningState());
	}
	

	
	@RequestMapping(value="list/{status}",method=RequestMethod.GET)
	@ResponseBody
	public SuccessResponse list(@PathVariable("status") Integer code) throws BusinessException{
		Status status = Status.getStatus(code);
		if(status==null){
			throw new BusinessException("参数错误");
		}
		return new SuccessResponse(equipmentService.queryEquipmentListByStatus(status));
	}		

	
	@RequestMapping(value="detail/{equipmentId}",method=RequestMethod.GET)
	@ResponseBody
	public SuccessResponse detail(@PathVariable("equipmentId") Long equipmentId) throws BusinessException{
		if(equipmentId==null){
			throw new BusinessException("参数错误");
		}
		Eqiupment eqiupment = equipmentService.get(Eqiupment.class,equipmentId);
		if(eqiupment==null){
			throw new BusinessException("找不到该设备");
		}
		
		return new SuccessResponse(equipmentService.queryEquipmentDetail(equipmentId));
	}	
	

	
	
}
