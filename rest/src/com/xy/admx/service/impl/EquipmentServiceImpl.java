package com.xy.admx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.xy.admx.common.CommonFunction;
import com.xy.admx.common.DateUtil;
import com.xy.admx.common.SysConfig;
import com.xy.admx.core.service.base.BaseServiceImpl;
import com.xy.admx.rest.EquipmentStatusRest.Status;
import com.xy.admx.service.EquipmentService;
import com.xy.cms.entity.Eqiupment;
import com.xy.cms.entity.EquipmentParam;
import com.xy.cms.entity.EquipmentParamData;

@Service
public class EquipmentServiceImpl extends BaseServiceImpl implements EquipmentService {

	@Override
	public List<Eqiupment> getEquipmentByDeptId(Long id) {
		/*String hql="from Eqiupment e where e.dept=:id";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		return (Eqiupment)this.getUniqueResult(hql,map);*/
		String hql="from Eqiupment e where e.dept in(:id)";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		return this.getList(hql, map);
		
	}

	@Override
	public void saveEqData(List<EquipmentParamData> list) {
		if (CommonFunction.isNotNull(list)) {
			for (EquipmentParamData equipmentParamData : list) {
				this.save(equipmentParamData);
			}
		}
	}

	@Override
	public void saveEqParam(List<EquipmentParam> list) {
		String hql="delete from EquipmentParam";
		this.execute(hql);
		if (CommonFunction.isNotNull(list)) {
			for (EquipmentParam equipmentParam : list) {
				this.save(equipmentParam);
			}
		}
		
	}

	@Override
	public Map<String, Object> queryEquipmentRuningState() {
		StringBuilder sql =new StringBuilder();
		sql.append("SELECT COUNT(1) AS count,COUNT(CASE WHEN d.id IS NULL OR d.paramter3=0  THEN 1 ELSE NULL END) AS off,");
		sql.append("COUNT(CASE WHEN paramter5!=0 THEN 1 ELSE NULL END) AS exception,");
		sql.append("COUNT(CASE WHEN paramter5=0 AND paramter4>0 THEN 1 ELSE NULL END) AS run,");
		sql.append("COUNT(CASE WHEN paramter5=0 AND paramter4=0 AND  paramter3>0 THEN 1 ELSE NULL END) AS wait ");
		sql.append(" FROM `equipment` eq LEFT JOIN ");
		sql.append(" (SELECT a.* FROM `equipment_parameter_data` a,(");
		sql.append(" SELECT equpment_id AS equpment_id ,MAX(receive_time) AS receive_time FROM `equipment_parameter_data` da where da.receive_time>=:time  GROUP BY equpment_id  ");
		sql.append(" ) b WHERE a.`equpment_id`=b.equpment_id AND a.`receive_time`=b.receive_time");
		sql.append(" )d ON eq.`id`=d.equpment_id");
		Map<String,Object> values= new HashMap<>();
		//values.put("time", new Date(DateUtil.getBeforeMinute(15).getTime()));
		values.put("time","2017-08-14");
		return this.getFirstResultBySqlToMap(sql.toString(), values);
	}

	@Override
	public List<Map<String, Object>> queryEquipmentListByStatus(Status status) {
		StringBuilder sql = new StringBuilder();
		sql.append("select  equip.`id`,equip.`name`,equip.`equipment_code`,equip.`model` from equipment equip left join (select a.* from equipment_parameter_data a,");
		sql.append(" (select equpment_id as equpment_id,max(receive_time) as receive_time   from equipment_parameter_data   where `receive_time`>:time");
		sql.append(" GROUP BY equpment_id");
		sql.append(" )d WHERE a.equpment_id=d.equpment_id AND a.receive_time=d.receive_time) a");
		sql.append(" on a.equpment_id=equip.`id` where 1=1");
	
		if(status.equals(Status.wait)){
			sql.append(" AND  paramter5=0 AND paramter4=0 AND  paramter3>0");
		}
		else if(status.equals(Status.run)){
			sql.append(" AND  paramter5=0 AND paramter4>0 ");
		}
		else if(status.equals(Status.exception)){
			sql.append(" AND paramter5!=0  ");
		}
		else if(status.equals(Status.off)){
			sql.append(" AND (a.id is null OR paramter3=0 )");
		}
		Map<String,Object> values= new HashMap<>();
		//values.put("time", new Date(DateUtil.getBeforeMinute(15).getTime()));
		values.put("time","2017-08-14");
		return this.getListBySQLToMap(sql.toString(), values);
	}

	@Override
	public Map<String, Object> queryEquipmentDetail(Long id) {
		Eqiupment eqiupment = this.get(Eqiupment.class, id);
		Map<String,Object> body = new HashMap<>();
		body.put("no",eqiupment.getEquipmentqrCode());
		body.put("name", eqiupment.getEquipmentName());
		body.put("img",StringUtils.isNotBlank(eqiupment.getEquipmentImg())?SysConfig.getStrValue("resource.file")+"/"+eqiupment.getEquipmentImg():null);
		body.put("model",eqiupment.getEquipmentModel());
		body.put("state",getEquipmentState(id).getCode());
		StringBuilder sql = new StringBuilder("SELECT MAX(receive_time) lastreceive,MAX(CASE WHEN paramter3>3 THEN receive_time END) AS lastontime");
		sql.append(" ,round(SUM(paramter4)/60,2) AS worktime,SUM(paramter1) AS total,SUM(paramter2) AS qualifiedSum,");
		sql.append(" SUM(CASE WHEN receive_time>=:begintime AND receive_time<=:endtime THEN paramter1 END ) AS todayTotal");
		sql.append("  FROM `equipment_parameter_data` where equpment_id=:id ");
		String nowDate = DateUtil.currentDate("yyyy-MM-dd");

		Map<String,Object> values= new HashMap<>();
		values.put("begintime", nowDate);
		values.put("endtime", nowDate+" 23:59:59");
		values.put("id", id);
		body.putAll((Map)this.getListBySQLToMap(sql.toString(), values).get(0));
		body.put("today", getEquipmentParamDataByDate(values));
		return body;
	}
	
	private Status getEquipmentState(Long id){
		StringBuilder hql = new StringBuilder("from EquipmentParamData where equmentId=:id  order by receiveTime desc ");
		Map<String,Object> values= new HashMap<>();
		values.put("id", id);
		EquipmentParamData equipmentParamData = (EquipmentParamData) this.getFirstResult(hql.toString(), values);
		/***
		 * 如果没有数据，或者最后采集时间在系统时间15分钟之前，那么则认为此设备是离线的状态
		 */
		if(equipmentParamData==null || equipmentParamData.getReceiveTime().before(DateUtil.getBeforeMinute(15))){
			return Status.off;
		}
		if(equipmentParamData.getParamterFive()>0){
			return Status.exception;
		}
		if(equipmentParamData.getParamterFour()>0){
			return Status.run;
		}
		return Status.wait;
		
		
		
		
		
	}
	
	
	
	
	private Map<String,Object> getEquipmentParamDataByDate(Map<String,Object> values){
		StringBuilder sql =new StringBuilder(" SELECT DATE_FORMAT(MIN(CASE WHEN paramter3>3 THEN receive_time END ),'%H:%i') AS begintime,");
		sql.append(" DATE_FORMAT(MAX(CASE WHEN paramter3>3 THEN receive_time END ),'%H:%i')");
		sql.append(" AS endtime,SUM(paramter3) AS ontime,SUM(paramter5) AS exceptiontime,SUM(paramter6*60-paramter3) AS offtime,");
		sql.append(" GROUP_CONCAT(CASE WHEN paramter5>5 THEN DATE_FORMAT(receive_time,'%H:%i') END) AS exceptiontimegroup,");
		sql.append(" SUM(paramter1) AS total,SUM(paramter2) AS qualifiedSum");
		sql.append(" FROM equipment_parameter_data where receive_time>=:begintime AND receive_time<=:endtime and equpment_id=:id ");
		return (Map<String, Object>) this.getListBySQLToMap(sql.toString(), values).get(0);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
