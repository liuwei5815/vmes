package com.xy.cms.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xy.cms.action.system.EquipmentAction.Status;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.PseudoSqlUtil;
import com.xy.cms.common.QrCodeFactoty;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Eqiupment;
import com.xy.cms.entity.EqiupmentType;
import com.xy.cms.entity.EquipmentParamData;
import com.xy.cms.entity.Orders;
import com.xy.cms.service.DepartmentService;
import com.xy.cms.service.EquipmentService;
import com.xy.cms.service.ModelService;
import com.xy.cms.service.SequenceService;

public class EquipmentServiceImpl extends BaseDAO implements EquipmentService{
	private SequenceService sequenceService;
	private DepartmentService departmentService;
	private ModelService modelService;
	@Override
	public QueryResult queryAllEquipment(Map<String, Object> map) throws BusinessException {
		QueryResult result = null;
		Map<String, Object> param = new HashMap<String, Object>();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		Orders orders = (Orders) map.get("orders");
		StringBuilder sql = new StringBuilder("select eq,et,de from Eqiupment eq,EqiupmentType et,Department de  where eq.equipmentType=et.id and eq.dept=de.id");
		//设备类型
		if (CommonFunction.isNotNull(map.get("eqType"))) {
			sql.append(" and et.type like :eqType");
			param.put("eqType", "%"+map.get("eqType")+"%");
		}
		//设备名称
		if(CommonFunction.isNotNull(map.get("eqName"))){
			sql.append(" and  eq.equipmentName like:eqName ");
			param.put("eqName", "%"+map.get("eqName")+"%");
		}
		//部门id
		if(CommonFunction.isNotNull(map.get("deptId")) && !map.get("deptId").equals("0")){
			Long[] ids=null;
			List idList=modelService.queryids(Long.parseLong((String) map.get("deptId")));
			ids=new Long[idList.size()];
			for (int i = 0; i < idList.size(); i++) {
				ids[i]=Long.parseLong(idList.get(i).toString());
			}
			param.put("ids",  ids);
			sql.append(" and  de.id in (:ids) ");
		}
		//用户设备编号
		if(CommonFunction.isNotNull(map.get("eqUserCode"))){
			sql.append(" and  eq.userEquipmentCode like:eqUserCode ");
			param.put("eqUserCode", "%"+map.get("eqUserCode")+"%");
		}
		//系统设备编号
		if(CommonFunction.isNotNull(map.get("eqCode"))){
			sql.append(" and  eq.equipmentCode like:eqCode ");
			param.put("eqCode", "%"+map.get("eqCode")+"%");
		}
		sql.append(" order by eq.buyDate desc");
		System.out.println(sql.toString());
		result=this.getPageQueryResult(sql.toString(), param, qEntity);
		return result;
	}

	//设备运行数据报表查询
	public  QueryResult queryAllEquipmentOperInf(Map<String, Object> map) throws BusinessException{
		
		QueryResult result = null;
		Map<String, Object> param = new HashMap<String, Object>();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		Orders orders = (Orders) map.get("orders");
		
		StringBuilder sql= new StringBuilder("select  e.id,equipment_code,usereq_code,e.name,model, equip_type,p.type,dept_id,t.name as department,buy_date,image,worktime,");
		sql.append(" SUM( IFNULL( d.paramter1, 0 ) ) AS scjlzs, ");//生产计数总量
		sql.append(" SUM( IFNULL( d.paramter2, 0 ) ) AS hgpjs, ");//合格品计数
		sql.append(" SUM( IFNULL( d.paramter3, 0 ) ) AS sdsj, ");//上电时间
		sql.append(" SUM( IFNULL( d.paramter4, 0 ) ) AS yxsj, ");//运行时间
		sql.append(" SUM( IFNULL( d.paramter5, 0 ) ) AS bjcs ");//报警次数
		sql.append(" from  equipment  e,sys_department t ,equipment_type p,equipment_parameter_data d ");
		sql.append(" where e.dept_id=t.id  and e.equip_type=p.id ");
		//设备名称
		if(CommonFunction.isNotNull(map.get("eqName"))){
			
			sql.append(" and  e.name like   '%"+map.get("eqName")+"%'  ");
			
		}
		//部门名称
		if(CommonFunction.isNotNull(map.get("deptName"))){
			
			sql.append(" and  t.name like  '%"+map.get("deptName")+"%' ");
			
		}
		//部门id
		if(CommonFunction.isNotNull(map.get("deptId"))){
			List idList=modelService.queryids(Long.parseLong((String) map.get("deptId")));
			sql.append(" and t.id in (");
			for (int i = 0; i < idList.size(); i++) {
				sql.append(idList.get(i).toString()+",");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
		}
		//用户设备编号
		if(CommonFunction.isNotNull(map.get("eqUserCode"))){
		
			sql.append(" and  e.usereq_code like  '%"+map.get("eqUserCode")+"%' ");
			
		}
		//系统设备编号
		if(CommonFunction.isNotNull(map.get("eqCode"))){
			
			  sql.append(" and  e.equipment_code like   '%"+map.get("eqCode")+"%' ");
			
		}
		sql.append(" group by e.id, equipment_code,usereq_code,e.name,model, equip_type,p.type,dept_id,department,buy_date,image,worktime ");
		sql.append(" order by e.buy_date desc");
		System.out.println(sql.toString());
		//result=this.getPageQueryResult(sql.toString(), param, qEntity);
		result=this.getPageQueryResultBySql(sql.toString(), qEntity);
		return result;
		
		
		
	}
	
	@Override
	public void saveEqiupment(Eqiupment eq) throws BusinessException {
		String equipmentCode=sequenceService.getNewNoByTableColumns(585L);
		eq.setEquipmentCode(equipmentCode);//自动编号
		eq.setEquipmentqrCode(equipmentCode);//二维码
		this.save(eq);
		QrCodeFactoty.createTodoQrCode(eq.getEquipmentCode(),eq.getEquipmentqrCode());
	}
	@Override
	public Eqiupment getEqiupmentById(Long id) {
		return (Eqiupment) this.get(Eqiupment.class, id);
	}
	
	@Override
	public void updateEqiupment(Eqiupment eq) throws BusinessException {
		if(CommonFunction.isNotNull(eq)){
			Eqiupment newEq =getEqiupmentById(eq.getId());
			newEq.setUserEquipmentCode(eq.getUserEquipmentCode());
			newEq.setEquipmentName(eq.getEquipmentName());
			newEq.setEquipmentModel(eq.getEquipmentModel());
			newEq.setEquipmentType(eq.getEquipmentType());
			newEq.setBuyDate(eq.getBuyDate());
			newEq.setEquipmentImg(eq.getEquipmentImg());
			newEq.setDept(eq.getDept());
			this.update(newEq);
		}
		
	}
	@Override
	public void importEqiupment(List<Eqiupment> eqiupmentList) throws BusinessException {
		for (Eqiupment eqiupment : eqiupmentList) {
			this.saveEqiupment(eqiupment);
		}
		
	}
	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	@Override
	public void delEquipment(Eqiupment eq) throws BusinessException {
		if(CommonFunction.isNull(eq)){
			throw new BusinessException("参数错误");
		}
		this.delete(eq);
		
	}

	@Override
	public String queryEqType(Integer eqId) {
		String sql="select eq.type from equipment_type as eq where eq.id=:eqId";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("eqId", eqId);
		return (String)this.getUniqueResultSql(sql, map);
	}

	@Override
	public EqiupmentType getEqTpyeByName(String name) {
		String hql="from EqiupmentType mt where mt.type=:name";
		Map<String, Object> m=new HashMap<String, Object>();
		m.put("name", name);
		return (EqiupmentType)this.getUniqueResult(hql, m);
	}

	@Override
	public EqiupmentType getEqTpyeByNameAndPid(String name, Long pid) {
		String hql="from EqiupmentType mt where mt.type=:name and mt.pid=:pid";
		Map<String, Object> m=new HashMap<String, Object>();
		m.put("name", name);
		m.put("pid", pid);
		return (EqiupmentType)this.getUniqueResult(hql, m);
	}

	@Override
	public Long addEqiupmentTypeWithId(EqiupmentType et) {
		if(CommonFunction.isNotNull(et)){
			this.save(et);
		}
		return et.getId();
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public ModelService getModelService() {
		return modelService;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	@Override
	public Map queryEqiupmentEfcs(Map<String, Object> map) {
		String sql=" SELECT SUM(paramter4)/SUM(paramter3)*100 AS efc FROM equipment_parameter_data d where d.receive_time>=CONCAT(:beginDate,' 00:00:00') and d.receive_time<=CONCAT(:endDate,' 23:59:59') and d.equpment_id in (:equmentId)";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("equmentId", map.get("equmentId"));
		param.put("beginDate", map.get("beginDate"));
		param.put("endDate", map.get("endDate"));
		return this.getFirstResultBySqlToMap(sql.toString(), param);
	}

	@Override
	public Map queryEqiupmentDisEfcs(Map<String, Object> map) {
		String sql=" SELECT SUM(paramter5)/SUM(paramter3)*100 as disEfc FROM equipment_parameter_data d where d.receive_time>=CONCAT(:beginDate,' 00:00:00') and d.receive_time<=CONCAT(:endDate,' 23:59:59') and d.equpment_id in (:equmentId) ";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("equmentId", map.get("equmentId"));
		param.put("beginDate", map.get("beginDate"));
		param.put("endDate", map.get("endDate"));
		return this.getFirstResultBySqlToMap(sql.toString(), param);
	}
	@Override
	public QueryResult queryOperInfor(Map<String, Object> map) throws BusinessException{
		QueryResult result = null;
		Map<String, Object> params = new HashMap<String, Object>();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		StringBuffer sql = new StringBuffer("  ");
		sql.append(" SELECT sj.*,eq.image AS image,eq.name AS name,eq.model AS model from ");
		sql.append(" ( select d.equpment_id  AS equmentId, SUM(d.paramter4) AS runtime ,MAX(d.receive_time) AS receivetime, SUM(d.paramter3) AS electrifytime  ");
		sql.append(" from equipment_parameter_data d WHERE 1=1  ");
		//开始时间
		if(CommonFunction.isNotNull(map.get("beginDate"))){
			sql.append(" and d.receive_time>=CONCAT(:beginDate,' 00:00:00') and d.receive_time<=CONCAT(:beginDate,' 23:59:59') ");
			params.put("beginDate", map.get("beginDate") );
		}
		/*//结束时间
		if(CommonFunction.isNotNull(map.get("endDate"))){
			sql.append(" and d.receive_time<=CONCAT(:endDate,' 23:59:59') ");
			params.put("endDate", map.get("endDate") );
		}*/
		//部门id
		if(CommonFunction.isNotNull(map.get("deptId"))){
			//通过部门id查询到所有的部门
			Long[] ids=null;
			List idList=modelService.queryids(Long.parseLong((String) map.get("deptId")));
			ids=new Long[idList.size()];
			for (int i = 0; i < idList.size(); i++) {
				ids[i]=Long.parseLong(idList.get(i).toString());
			}
			//再通过部门id查询下面有多少设备
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("ids", ids);
			//设备名称
			if(CommonFunction.isNotNull(map.get("eqName"))){
				m.put("eqName", map.get("eqName"));
			}
			//用户设备编号
			if(CommonFunction.isNotNull(map.get("eqUserCode"))){
				m.put("eqUserCode", map.get("eqUserCode"));
			}
			//系统设备编号
			if(CommonFunction.isNotNull(map.get("eqCode"))){
				m.put("eqCode", map.get("eqCode"));
			}
			//设备类型
			if(CommonFunction.isNotNull(map.get("eqType"))){
				m.put("eqType", map.get("eqType"));
			}
			List<Eqiupment> eqiupmentList = queryEqiupmentId(m);
			if(eqiupmentList.size()>0){
				Long[] eq_id=null;
				eq_id = new Long[eqiupmentList.size()];
				for (int j = 0; j < eqiupmentList.size(); j++) {
					eq_id[j]=Long.parseLong(eqiupmentList.get(j).getId().toString());
				}
				if(CommonFunction.isNotNull(eq_id)){
					sql.append(" and  d.equpment_id in (:eqId) ");
					params.put("eqId", eq_id );
				}
			}else{
				Long[] eq_id=new Long[1];
				eq_id[0] = 0L;
				sql.append(" and  d.equpment_id in (:eqId) ");
				params.put("eqId", eq_id );
			}
			
		}
		sql.append(" group by d.equpment_id ) AS sj");
		sql.append(" INNER JOIN equipment AS eq ON eq.id = sj.equmentId ");
		
		result=this.getPageQueryResultSQLToMap(sql.toString(), params, qEntity);
		return result;
	}

	@Override
	public List<Eqiupment> queryEqiupmentId(Map<String,Object> map) {
		StringBuffer hql = new StringBuffer(" from Eqiupment eq where eq.dept in :deptId ");
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("deptId", map.get("ids"));
		//设备名称
		if(CommonFunction.isNotNull(map.get("eqName"))){
			hql.append(" and  eq.equipmentName like:eqName ");
			param.put("eqName", "%"+map.get("eqName")+"%");
		}
		//用户设备编号
		if(CommonFunction.isNotNull(map.get("eqUserCode"))){
			hql.append(" and  eq.userEquipmentCode like:eqUserCode ");
			param.put("eqUserCode", "%"+map.get("eqUserCode")+"%");
		}
		//系统设备编号
		if(CommonFunction.isNotNull(map.get("eqCode"))){
			hql.append(" and  eq.equipmentCode like:eqCode ");
			param.put("eqCode", "%"+map.get("eqCode")+"%");
		}
		//设备类型
		if(CommonFunction.isNotNull(map.get("eqType"))){
			hql.append(" and  eq.equipmentType=:eqType ");
			param.put("eqType", map.get("eqType"));
		}
		return this.getList(hql.toString(), param);
	}
	

	@Override
	public Map queryEqiupmentCapacity(Map map) {
		StringBuffer sql = new StringBuffer("  ");
		sql.append(" SELECT ROUND( ");
		sql.append(" (SELECT SUM(d.paramter4)/3600 from equipment_parameter_data d where d.receive_time>=CONCAT(:beginDate,' 00:00:00') and d.receive_time<=CONCAT(:endDate,' 23:59:59') and d.equpment_id in (:equmentId) ) ");
		sql.append("  / ");
		sql.append("  (SELECT SUM(e.worktime) from equipment e WHERE e.id in (:equmentId) ");
		sql.append("  )*100,2) AS capacity ");
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("equmentId", map.get("equmentId"));
		param.put("beginDate", map.get("beginDate"));
		param.put("endDate", map.get("endDate"));
		return this.getFirstResultBySqlToMap(sql.toString(), param);
	}
	
	@Override
	public Map queryEqiupmentIdling(Map map) {
		StringBuffer sql = new StringBuffer("  ");
		sql.append(" SELECT ROUND( ");
		sql.append(" (SELECT SUM(d.paramter6)/60 from equipment_parameter_data d where d.receive_time>=CONCAT(:beginDate,' 00:00:00') and d.receive_time<=CONCAT(:endDate,' 23:59:59') and d.equpment_id in (:equmentId) and d.paramter4>0 and d.paramter1=0 ) ");
		sql.append("  / ");
		sql.append("  (SELECT SUM(e.worktime) from equipment e WHERE e.id in (:equmentId) ");
		sql.append("  )*100,2) AS idling ");
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("equmentId", map.get("equmentId"));
		param.put("beginDate", map.get("beginDate"));
		param.put("endDate", map.get("endDate"));
		return this.getFirstResultBySqlToMap(sql.toString(), param);
	}


	@Override
	public List<Object[]> queryEqiupmentByIds(Long[] ids) {
		
		StringBuilder hql = new StringBuilder("select e,d from Eqiupment e,Department d where e.dept=d.id and  e.id in :ids");
		Map<String,Object> values= new HashMap<String, Object>();
		values.put("ids", ids);
		return this.getList(hql.toString(), values);
	}


	@Override
	public Map queryEqiupmentFault(Map map) {
		StringBuffer sql = new StringBuffer("  ");
		sql.append(" SELECT ROUND( ");
		sql.append(" (SELECT SUM(d.paramter6)/60 from equipment_parameter_data d where d.receive_time>=CONCAT(:beginDate,' 00:00:00') and d.receive_time<=CONCAT(:endDate,' 23:59:59') and d.equpment_id in (:equmentId) and d.paramter5>0  ) ");
		sql.append("  / ");
		sql.append("  (SELECT SUM(e.worktime) from equipment e WHERE e.id in (:equmentId) ");
		sql.append("  )*100,2) AS fault ");
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("equmentId", map.get("equmentId"));
		param.put("beginDate", map.get("beginDate"));
		param.put("endDate", map.get("endDate"));
		return this.getFirstResultBySqlToMap(sql.toString(), param);
	}
	
	@Override
	public Map queryWorktimeCapacity(Map map) {
		String sql = " select SUM(e.worktime) as worktime equipment e WHERE e.id=:equmentId ";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("equmentId", map.get("equmentId"));
		return this.getFirstResultBySqlToMap(sql.toString(), param);
	}

	@Override
	public List<Map> queryEqEfc(Long id,Date beginDate,Date endDate) {
		String selfTimeContro = PseudoSqlUtil.getTimeSlotPseudoSql(beginDate,endDate);
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("id", id);
		StringBuffer sql = new StringBuffer("  ");
		sql.append(" SELECT  temp.day AS tempDay,ROUND(SUM(tcapacity.p4)/SUM(tcapacity.p3)*100,2) AS efc,tcapacity.equpment_id FROM ( ");
		sql.append(selfTimeContro); 
		sql.append(" ) AS temp LEFT JOIN  ");
		sql.append(" (SELECT d.receive_time AS t,d.paramter4 AS p4,d.paramter3 AS p3,d.equpment_id AS equpment_id  ");
		sql.append(" FROM `equipment_parameter_data` d WHERE d.equpment_id =:id ");
		sql.append(" ) tcapacity ON tcapacity.t>=CONCAT(temp.day,' 00:00:00') AND  tcapacity.t<=CONCAT(temp.day,' 23:59:59') GROUP BY day ");
		return this.getListBySQLToMap(sql.toString(), param);
	}

	@Override
	public List<Map> queryEqDisEfc(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> queryEqiupmentCapacityGroup(Long id,Date beginDate,Date endDate) throws Exception{
		String pseudoSql = PseudoSqlUtil.getTimeSlotPseudoSql(beginDate,endDate);
		Map<String,Object> param = new HashMap<String,Object>();
		
		StringBuffer sql=new StringBuffer();
		param.put("id", id);
		sql.append(" SELECT sj.tempDay,ROUND(sj.tempP4/e.worktime*100,1) AS result  FROM ");
		sql.append(" (SELECT  temp.day AS tempDay,SUM(capacity.p4)/3600 AS tempP4,capacity.equpment_id FROM ( ");
		//sql.append(" SELECT  ");
		sql.append(pseudoSql);
		sql.append(" ) AS temp  ");
		sql.append(" LEFT JOIN   ");
		sql.append(" (SELECT d.receive_time AS t,d.paramter4 AS p4,d.equpment_id FROM `equipment_parameter_data` d WHERE d.equpment_id =:id ");
		sql.append(" ) capacity ON capacity.t>=CONCAT(temp.day,' 00:00:00') AND  capacity.t<=CONCAT(temp.day,' 23:59:59') ");
		sql.append(" GROUP BY day) AS sj   ");
		sql.append(" LEFT JOIN `equipment` e ON e.id =sj.equpment_id   ");
		List<Map> list=this.getListBySQLToMap(sql.toString(),param);
		return list;
	}

	@Override
	public QueryResult queryList(Map map) {
		QueryResult result = null;
		Map<String, Object> params = new HashMap<String, Object>();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		StringBuffer sql = new StringBuffer();
		params.put("beginDate",map.get("beginDate"));
		params.put("endDate", map.get("endDate"));
		sql.append(" SELECT eq.*,eqType.type,de.name AS deName from  ");
		sql.append(" ( SELECT d.equpment_id AS eqId FROM equipment_parameter_data d WHERE d.receive_time>=CONCAT(:beginDate,' 00:00:00') AND d.receive_time<=CONCAT(:endDate,' 23:59:59')  ");
		/*//部门id
		if(CommonFunction.isNotNull(map.get("deptId"))){
			//通过部门id查询到所有的部门
			Long[] ids=null;
			List idList=modelService.queryids(Long.parseLong((String) map.get("deptId")));
			ids=new Long[idList.size()];
			for (int i = 0; i < idList.size(); i++) {
				ids[i]=Long.parseLong(idList.get(i).toString());
			}
			//再通过部门id查询下面有多少设备
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("ids", ids);
			//设备名称
			if(CommonFunction.isNotNull(map.get("eqName"))){
				m.put("eqName", map.get("eqName"));
			}
			//用户设备编号
			if(CommonFunction.isNotNull(map.get("eqUserCode"))){
				m.put("eqUserCode", map.get("eqUserCode"));
			}
			//系统设备编号
			if(CommonFunction.isNotNull(map.get("eqCode"))){
				m.put("eqCode", map.get("eqCode"));
			}
			List<Eqiupment> eqiupmentList = queryEqiupmentId(m);
			Long[] eq_id=null;
			eq_id = new Long[eqiupmentList.size()];
			for (int j = 0; j < eqiupmentList.size(); j++) {
				eq_id[j]=Long.parseLong(eqiupmentList.get(j).getId().toString());
			}
			if(CommonFunction.isNotNull(eq_id)){
				sql.append(" and  d.equpment_id in (:eqId) ");
				params.put("eqId", eq_id );
			}
		}*/
		sql.append(" GROUP BY d.equpment_id  ) AS sj");
		sql.append(" INNER JOIN  equipment eq ON eq.id=sj.eqId ");
		sql.append(" LEFT JOIN equipment_type eqType ON eqType.id=eq.equip_type ");
		sql.append(" LEFT JOIN sys_department de ON de.id=eq.dept_id WHERE 1=1");
		//设备类型
		if (CommonFunction.isNotNull(map.get("eqType"))) {
			sql.append(" AND eqType.type like :typeName");
			params.put("typeName", "%"+map.get("eqType")+"%");
		}
		//设备名称
		if(CommonFunction.isNotNull(map.get("eqName"))){
			sql.append(" AND eq.name like :eqName");
			params.put("eqName", "%"+map.get("eqName")+"%");
		}
		//用户设备编号
		if(CommonFunction.isNotNull(map.get("eqUserCode"))){
			sql.append(" AND eq.usereq_code like :eqUserCode");
			params.put("eqUserCode", "%"+map.get("eqUserCode")+"%");
		}
		//系统设备编号
		if(CommonFunction.isNotNull(map.get("eqCode"))){
			sql.append(" AND eq.equipment_code like :eqCode");
			params.put("eqCode", "%"+map.get("eqCode")+"%");
		}
		sql.append(" ORDER BY eq.equipment_code");
		result=this.getPageQueryResultSQLToMap(sql.toString(), params, qEntity);
		return result;
	}

	@Override
	public List<EquipmentParamData> queryEquipmentParamDataByDateAndId(Map map) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("date", map.get("date"));
		param.put("equmentId", map.get("equmentId"));
		String hql = " from EquipmentParamData d where d.equmentId=:equmentId and d.receiveTime>=CONCAT(:date,' 00:00:00') and d.receiveTime<=CONCAT(:date,' 23:59:59') ORDER BY d.receiveTime";
		return this.getList(hql, param);
	}

	@Override
	public Map queryNumberByIdAndDate(String equpmentId, String receiveTime) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("equpmentId", equpmentId);
		param.put("receiveTime", receiveTime);
		String sql = " select SUM(d.paramter1) AS number from equipment_parameter_data d WHERE d.equpment_id=:equpmentId AND d.receive_time>=CONCAT(:receiveTime,' 00:00:00') AND d.receive_time<=CONCAT(:receiveTime,' 23:59:59')";
		return this.getFirstResultBySqlToMap(sql, param);
	}

	@Override
	public QueryResult queryRecord(Map map) {
		QueryResult result = null;
		Map<String, Object> param = new HashMap<String, Object>();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		param.put("equipmentId", map.get("equipmentId"));
		param.put("beginDate", map.get("beginDate"));
		param.put("endDate", map.get("endDate"));
		String sql = null;
		if(map.get("type").equals("1")){
			sql = " select e.receive_time,e.paramter1 AS number,ROUND(e.paramter4/60,1) AS workMinute,ROUND(e.paramter4/3600,2) AS workHour,ROUND(e.paramter3/60,1) AS bootTime from equipment_parameter_data e where e.equpment_id =:equipmentId AND e.receive_time>=:beginDate AND e.receive_time<=:endDate ";
		}else{
			sql = " select e.receive_time,e.paramter1 AS number,ROUND(e.paramter4/60,1) AS workMinute,ROUND(e.paramter4/3600,2) AS workHour,ROUND(e.paramter3/60,1) AS bootTime from equipment_parameter_data e where e.equpment_id =:equipmentId AND e.receive_time>=:beginDate AND e.receive_time<=:endDate AND e.paramter5>0";
		}
		result=this.getPageQueryResultSQLToMap(sql.toString(), param, qEntity);
		return result;
	}
	
	/**
	 * 获取设备运行状态
	 */
	@Override
	public Map getEquipmentStates(Long[] ids){
		StringBuilder sql = new StringBuilder(" SELECT d.equpment_id, d.paramter4, d.paramter5, d.receive_time, e.buy_date");
		sql.append(" FROM equipment_parameter_data d, equipment e,");
		sql.append(" ( SELECT equpment_id AS c_id, MAX( `receive_time` ) AS c_time");
		sql.append(" FROM `equipment_parameter_data`");
		sql.append(" WHERE equpment_id IN :ids");
		sql.append(" GROUP BY equpment_id ) c");
		sql.append(" WHERE d.equpment_id = c.c_id AND d.receive_time = c.c_time AND d.equpment_id = e.id");
		sql.append(" ORDER BY e.buy_date DESC");
		Map<String,Object> values= new HashMap<String, Object>();
		values.put("ids", ids);
		List resultList = this.getListBySQLToMap(sql.toString(),values);
		List<Map> mapList = resultList;
		Map resultMap = new HashMap();
		for (Map map : mapList) {
			/*
			 * 如果没有数据，或者最后采集时间在系统时间15分钟之前，那么则认为此设备是离线的状态
			 */
			Date date = (Date) map.get("receive_time");
			int stateCode = 0;
			if (date.before(DateUtil.getBeforeMinute(15))) {
				stateCode = Status.off.getCode();
			} else if ((Integer)map.get("paramter5") > 0) {
				stateCode = Status.exception.getCode();
			} else if ((Integer)map.get("paramter4") > 0) {
				stateCode = Status.run.getCode();
			} else {
				stateCode = Status.wait.getCode();
			}
			resultMap.put(map.get("equpment_id").toString(), stateCode);
		}
		return resultMap;
	}

	@Override
	public QueryResult queryOtherList(Map<String, Object> map) throws BusinessException {
		QueryResult result = null;
		Map<String, Object> param = new HashMap<String, Object>();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		Orders orders = (Orders) map.get("orders");
		StringBuilder sql = new StringBuilder("select eq,et,de from Eqiupment eq,EqiupmentType et,Department de  where eq.equipmentType=et.id and eq.dept=de.id");
		//设备名称
		if(CommonFunction.isNotNull(map.get("eqName"))){
			sql.append(" and  eq.equipmentName like:eqName ");
			param.put("eqName", "%"+map.get("eqName")+"%");
		}
		//部门id
		if(CommonFunction.isNotNull(map.get("deptId")) && !map.get("deptId").equals("0")){
			Long[] ids=null;
			List idList=modelService.queryids(Long.parseLong((String) map.get("deptId")));
			ids=new Long[idList.size()];
			for (int i = 0; i < idList.size(); i++) {
				ids[i]=Long.parseLong(idList.get(i).toString());
			}
			param.put("ids",  ids);
			sql.append(" and  de.id in (:ids) ");
		}
		//用户设备编号
		if(CommonFunction.isNotNull(map.get("eqUserCode"))){
			sql.append(" and  eq.userEquipmentCode like:eqUserCode ");
			param.put("eqUserCode", "%"+map.get("eqUserCode")+"%");
		}
		//系统设备编号
		if(CommonFunction.isNotNull(map.get("eqCode"))){
			sql.append(" and  eq.equipmentCode like:eqCode ");
			param.put("eqCode", "%"+map.get("eqCode")+"%");
		}
		sql.append(" order by eq.equipment_code ");
		System.out.println(sql.toString());
		result=this.getPageQueryResult(sql.toString(), param, qEntity);
		return result;
	}

}
