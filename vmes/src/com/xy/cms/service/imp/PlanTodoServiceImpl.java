package com.xy.cms.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.xy.cms.common.QrCodeFactoty;
import com.xy.cms.common.RandomUtil;
import com.xy.cms.common.base.BaseDAO;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Produceplan;
import com.xy.cms.entity.ProduceplanExceptionHandle;
import com.xy.cms.entity.ProduceplanExceptionHandleHistory;
import com.xy.cms.entity.ProduceplanTodo;

import com.xy.cms.entity.SysLog;
import com.xy.cms.service.PlanTodoService;

import com.xy.cms.entity.base.BaseProduceplanExceptionHandle.Status;

import com.xy.cms.service.PlanTodoService;
import com.xy.cms.service.ProduceplanExceptionHandleService;
import com.xy.cms.service.ProduceplanService;
import com.xy.cms.service.SequenceService;

public class PlanTodoServiceImpl extends BaseDAO implements PlanTodoService{

	private SequenceService sequenceService;
	
	private ProduceplanExceptionHandleService produceplanExceptionHandleService;
	private ProduceplanService produceplanService;
	public QueryResult queryAllPlan(Map<String, Object> map) throws BusinessException {
		QueryResult result = null;
		Map m = new HashMap();
		BaseQEntity qEntity = (BaseQEntity) map.get("qEntity");
		String planCode = (String) map.get("planCode");
		String state = (String) map.get("planState");
		StringBuilder hql = new StringBuilder("select plan,pro from Produceplan plan,Product pro where plan.productId=pro.id ");
		if(!StringUtils.isBlank(planCode)){
			hql.append(" and (plan.planCode like :planCode or plan.manufactureCode like:planCode)");
			m.put("planCode", "%"+planCode+"%");
		}
		if(!StringUtils.isBlank(state)){
			hql.append(" and  plan.state=:state ");
			m.put("state", state);
		}
		/*if(StringUtils.isBlank(state) && StringUtils.isBlank(planCode)){
			hql.append(" and  plan.state=0 ");
		}*/
		hql.append(" order by conv(plan.state,10,2) asc,plan.id desc");
		result=this.getPageQueryResult(hql.toString(), m, qEntity);
		return result;
	}

	@Override
	public void del(Long id) throws BusinessException {
		this.delete(this.get(ProduceplanTodo.class, id));
	}

	@Override
	public void savePlan(ProduceplanTodo planTodo) throws BusinessException {
		try {
			if(planTodo == null){
				throw new BusinessException("参数错误");
			}
			String code=sequenceService.getNewNoByTableColumns(561L);
			planTodo.setCode(code);
			this.save(planTodo);
		} catch (Exception e) {
			throw new BusinessException("添加信息异常："+e.getMessage(),e);
		}
	}

	@Override
	public List<Produceplan> getPlan() {
		String hql="from Productplan where 1=1";
		return this.getList(hql, null);
	}
	@Override
	public List<Object[]> queryPrintPlan(Long id) throws BusinessException {
		
		StringBuilder sql = new StringBuilder("SELECT todo.id as tId,todo.`todo_code` ,p.*  FROM `produceplan` AS p INNER JOIN `produceplan_todo` AS todo ON p.`Id`=todo.`produceplan_id` WHERE todo.`id`=:id");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", id);
		List<Object[]> list=this.getListBySQL(sql.toString(), map);
		return list;
	}
	@Override
	public Produceplan queryPlan(Long id) throws BusinessException {
		return (Produceplan)this.get(Produceplan.class, id);
	}
	@Override
	public ProduceplanTodo queryPlanById(Long id) {
		return (ProduceplanTodo)this.get(ProduceplanTodo.class, id);
	}
	@Override
	public List<ProduceplanTodo> queryProduceplanTodoByProduceplanIds(
			Long[] produceplanIds) {
		String hql = " from ProduceplanTodo where produceplanId.produceplanId in :produceplanIds";
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("produceplanIds", produceplanIds);
		return this.getList(hql, paramMap);
	}



	@Override
	public void addProductplans(Integer count, Long produceplanId) throws Exception {
		for (int i = 0; i < count; i++) {
			Produceplan producePlan=produceplanService.getProduceplanById(produceplanId);
			producePlan.setState(Produceplan.Status.waitingproduce.getCode());//创建派工单 但还没有人报工时为待生产状态
			ProduceplanTodo planTodo=new ProduceplanTodo();
			planTodo.setProduceplanId(produceplanId);
			planTodo.setAddDate(new Date());
			String code=sequenceService.getNewNoByTableColumns(561L);
			planTodo.setCode(code);
			planTodo.setQrcode(code);
			this.save(planTodo);
			QrCodeFactoty.createTodoQrCode(planTodo.getCode(),planTodo.getQrcode());
		}

	}

	@Override
	public void changeWorkPlan(Long planId,List<ProduceplanTodo> todos){
		ProduceplanExceptionHandle exceptionHandle = produceplanExceptionHandleService.getHandleByPlanIdAndState(planId, Status.dispatch);
		if(exceptionHandle!=null){
			this.remove(exceptionHandle);
			ProduceplanExceptionHandleHistory handleHistory = new ProduceplanExceptionHandleHistory();
			BeanUtils.copyProperties(exceptionHandle, handleHistory);
			this.save(handleHistory);
		}
		//调整工序计划，需要更新二维码值及号码
		if(todos!=null){
			for (ProduceplanTodo produceplanTodo : todos) {
				ProduceplanTodo  oldProduceplanTodo = (ProduceplanTodo) this.get(ProduceplanTodo.class, produceplanTodo.getId());
				oldProduceplanTodo.setPlanNum(produceplanTodo.getPlanNum());
				oldProduceplanTodo.setState(produceplanTodo.getState());
				String newQrCode=null;
				while((newQrCode=createNewQrCodeConent(oldProduceplanTodo)).equals(oldProduceplanTodo.getCode()));
				oldProduceplanTodo.setQrcode(newQrCode);
				QrCodeFactoty.createTodoQrCode(oldProduceplanTodo.getCode(),oldProduceplanTodo.getQrcode());
			}
		}
	}
	/**
	 * 创建新的二维码内容
	 */
	private String createNewQrCodeConent(ProduceplanTodo planTodo){
		StringBuilder newQrCode= new StringBuilder();
		Random random = new Random();
		newQrCode.append(planTodo.getCode()).append(RandomUtil.randomStr(4));
		return newQrCode.toString();
	}
	
	@Override
	public List<Object[]> queryPlanAndToDoByProductTodos(Long[] plantodoIds) {
		String hql = " from ProduceplanTodo todo,Produceplan plan where plan.id=todo.produceplanId and todo.id in (:todoIds)";
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("todoIds", plantodoIds);
		
		return this.getList(hql, paramMap);
	}

	public ProduceplanExceptionHandleService getProduceplanExceptionHandleService() {
		return produceplanExceptionHandleService;
	}

	public void setProduceplanExceptionHandleService(
			ProduceplanExceptionHandleService produceplanExceptionHandleService) {
		this.produceplanExceptionHandleService = produceplanExceptionHandleService;
	}

	

	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public ProduceplanService getProduceplanService() {
		return produceplanService;
	}

	public void setProduceplanService(ProduceplanService produceplanService) {
		this.produceplanService = produceplanService;
	}

	@Override
	public List<ProduceplanTodo> queryProduceplanTodoByProduceplanId(Long produceplanId) {
		String hql = " from ProduceplanTodo where produceplanId.produceplanId =:produceplanId";
		Map<String,Object> paramMap =new HashMap<String, Object>();
		paramMap.put("produceplanId", produceplanId);
		return this.getList(hql, paramMap);
	}
	
	


}
