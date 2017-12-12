package com.xy.cms.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.security.util.FieldUtils;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.StringUtil;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionAjaxTemplate;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Produceplan;
import com.xy.cms.entity.ProduceplanExceptionHandle;
import com.xy.cms.entity.ProduceplanTodo;
import com.xy.cms.entity.ProduceplanTodoClaim;
import com.xy.cms.entity.base.BaseProduceplanExceptionHandle.Status;
import com.xy.cms.service.PlanTodoService;
import com.xy.cms.service.ProduceplanExceptionHandleService;
import com.xy.cms.service.ProduceplanService;
import com.xy.cms.service.ProduceplanTodoClaimService;
import com.xy.cms.service.SequenceService;


public class DispatchOrderAction extends BaseAction{
	private ProduceplanTodo planTodo;

	private PlanTodoService planTodoService;

	private SequenceService sequenceService;

	private ProduceplanService produceplanService;

	private ProduceplanTodoClaimService produceplanTodoClaimService;

	private ProduceplanExceptionHandleService produceplanExceptionHandleService;

	private Long produceplanId;

	private List<ProduceplanTodo> planTodos;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8734673007112078960L;
	/**
	 * 初始化
	 */
	public String init(){
		return "init";
	}

	public String queryOrder() {

		this.queryTemplate(new BaseActionQueryPageCallBack() {
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				pageMap.put("planCode", request.getParameter("planCode"));
				pageMap.put("planState", request.getParameter("planState"));
				return planTodoService.queryAllPlan(pageMap);
			}
		});
		List<Object[]> resultList =this.getList();
		if(!CollectionUtils.isEmpty(resultList)){
			Long producePlan[] =new Long[resultList.size()];
			int i=0;
			for (Object[] object  : resultList) {
				producePlan[i++]=((Produceplan)object[0]).getId();
			}
			List<ProduceplanTodo> plans=planTodoService.queryProduceplanTodoByProduceplanIds(producePlan);
			if(!CollectionUtils.isEmpty(plans)){
				Map<Long,List<ProduceplanTodo>> productplanTodoMap  =new HashMap<Long, List<ProduceplanTodo>>();
				for (ProduceplanTodo productplanTodo : plans) {
					List<ProduceplanTodo> todos=productplanTodoMap.get(productplanTodo.getProduceplanId());
					if(todos==null){
						todos=new ArrayList<ProduceplanTodo>();
						productplanTodoMap.put(productplanTodo.getProduceplanId(),todos);
					}
					todos.add(productplanTodo);
				}
				request.setAttribute("planTodoMap", productplanTodoMap);
			}
			initExceptionHandle(producePlan);
		}

		return "list_new";
	}
	//加载异常处理
	private void initExceptionHandle(Long[] producePlans){
		List<ProduceplanExceptionHandle> produceplanExceptionHandles = produceplanExceptionHandleService.getHandleByPlanIdsAndState(producePlans, Status.dispatch);
		if(!CollectionUtils.isEmpty(produceplanExceptionHandles)){
			HashSet<Long> exceptionHandle= new HashSet<Long>();
			for (ProduceplanExceptionHandle produceplanExceptionHandle : produceplanExceptionHandles) {
				exceptionHandle.add(produceplanExceptionHandle.getProduceplanId());
			}
			request.setAttribute("exceptionHandle",exceptionHandle);

		}
	}


	/**
	 * 预添加 
	 * @return
	 */
	public String preAdd() {

		return "add";
	}
	/**
	 * 添加数据
	 * @return
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 * @throws WriterException 
	 */
	public String add() throws Exception{
		try {
			if(produceplanId==null){
				throw new BusinessException("非法访问");
			}
			Produceplan produceplan = produceplanService.getProduceplanById(produceplanId);
			if(produceplan==null){
				throw new BusinessException("非法访问");
			}
			String countStr = request.getParameter("count");
			if(!StringUtil.isNumeric(countStr)){
				throw new BusinessException("派工单数量必须为数字");
			}
			Integer count =	Integer.parseInt(countStr);
			if(!(count>=1 && count<=30)){
				throw new BusinessException("生成派工单数量必须在1-30之间");
			}
			planTodoService.addProductplans(count, produceplanId);
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return "add";
	}



	/**
	 * 编辑 计划数量 工序名称 
	 * @return
	 * @throws BusinessException 
	 */
	public String preEdit() throws BusinessException {
		//生产计划
		String id=request.getParameter("id");
		if(CommonFunction.isNull(id)){
			throw new BusinessException("参数错误");
		}
		ProduceplanTodo planTodo=planTodoService.queryPlanById(Long.parseLong(id));
		request.setAttribute("planTodo", planTodo);
		return "edit";
	}
	public String edit() throws FileNotFoundException{
		try {
			String id=request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			ProduceplanTodo planTodo=planTodoService.queryPlanById(Long.parseLong(id));

			String planNum=request.getParameter("planNum");
			if (CommonFunction.isNull(planNum)) {
				throw new BusinessException("计划完成数量为空");
			}
			String actualNum=request.getParameter("actualNum");
			if (CommonFunction.isNull(actualNum)) {
				throw new BusinessException("实际完成数量为空");
			}
			String processName=request.getParameter("processName");
			if (CommonFunction.isNull(processName)) {
				throw new BusinessException("实际完成数量为空");
			}
			planTodo.setPlanNum(Long.parseLong(planNum));
			planTodo.setActualNum(Long.parseLong(actualNum));
			planTodo.setProcessName(processName);
			planTodoService.savePlan(planTodo);
			this.message = "修改成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return "edit";
	}
	/**
	 * 删除
	 * @return
	 */
	public String del() {
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			String id=request.getParameter("currentId");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			planTodoService.del(Long.parseLong(id));
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
	 * 打印工单列表
	 * @return
	 * @throws BusinessException 
	 */
	public String queryPrintDis() throws BusinessException{
		String ids=request.getParameter("idstr");
		if(CommonFunction.isNull(ids)){
			throw new BusinessException("请选择派工单");
		}
		String[] idStr=ids.split(",");
		Long[] todoIds= new Long[idStr.length];
		for(int i=0;i<idStr.length;i++){
			todoIds[i]=Long.parseLong(idStr[i]);
		}
		List<Object[]> planObjs = planTodoService.queryPlanAndToDoByProductTodos(todoIds);
		initClaimMap(todoIds);
		request.setAttribute("planObjs", planObjs);
		return "printDis";
	}

	private void initClaimMap(Long[] todoIds){
		List<Object[]> claims = produceplanTodoClaimService.queryTodoClaimByTodoIds(todoIds);
		if(!CollectionUtils.isEmpty(claims)){
			Map<Long,List<Object[]>> claimsMap=new HashMap<Long,List<Object[]>>();
			for (Object[] objs : claims) {
				ProduceplanTodoClaim produceplanTodoClaim = (ProduceplanTodoClaim) objs[0];
				List<Object[]> todoClaims=claimsMap.get(produceplanTodoClaim.getTodoId());
				if(todoClaims==null){
					todoClaims=new ArrayList<Object[]>();
					claimsMap.put(produceplanTodoClaim.getTodoId(), todoClaims);
				}
				todoClaims.add(objs);
			}
			request.setAttribute("todoClaimsMap", claimsMap);
		}

	}



	public String claim(){
		Long todoId = Long.parseLong(request.getParameter("todoId"));
		ProduceplanTodo  produceplanTodo = planTodoService.queryPlanById(todoId);
		List<ProduceplanTodoClaim> todoClaims = produceplanTodoClaimService.queryTodoClaimByTodoId(todoId);

		request.setAttribute("produceplanTodo", produceplanTodo);
		request.setAttribute("todoClaims",todoClaims);
		return "claimDetail";
	}

	/**
	 * 调整计划工序
	 */
	public String changeWorkPlan(){
		Object[] objPlan  = produceplanService.getProduceplanAndProductById(produceplanId);
		Produceplan plan = (Produceplan) objPlan[0];
		List<ProduceplanTodo> produceplanTodos = planTodoService.queryProduceplanTodoByProduceplanIds(new Long[]{plan.getId()});
		if(!CollectionUtils.isEmpty(produceplanTodos)){
			long todoPlanNum = 0l;
			for (ProduceplanTodo produceplanTodo : produceplanTodos) {
				todoPlanNum+=produceplanTodo.getPlanNum()==null?0:produceplanTodo.getPlanNum();
			}
			request.setAttribute("todoPlanNum",todoPlanNum);
		}
		request.setAttribute("exceptionHandle", produceplanExceptionHandleService.getHandleByPlanIdAndState(produceplanId, Status.dispatch));
		request.setAttribute("todos",produceplanTodos);
		request.setAttribute("plan", objPlan);
		return "changeWorkPlan";
	}


	public void doChangeWorkPlan(){
		this.ajaxTemplate(new BaseActionAjaxTemplate() {

			@Override
			public Object execute() throws Exception {
				String planId = request.getParameter("planId");
				if(planTodos!=null){
					for(ProduceplanTodo todo:planTodos){
						if(todo.getId()==null){
							throw new BusinessException("非法访问");
						}
						if(todo.getPlanNum()==null){
							throw new BusinessException("请输入计划数量");
						}
						if(todo.getState()==null){
							throw new BusinessException("请输入状态");
						}
					}
				}
				planTodoService.changeWorkPlan(Long.parseLong(planId), planTodos);
				return null;
			}
		});
	}



	public ProduceplanTodo getPlanTodo() {

		return planTodo;
	}

	public void setPlanTodo(ProduceplanTodo planTodo) {
		this.planTodo = planTodo;
	}

	public PlanTodoService getPlanTodoService() {
		return planTodoService;
	}

	public void setPlanTodoService(PlanTodoService planTodoService) {
		this.planTodoService = planTodoService;
	}

	public ProduceplanService getProduceplanService() {
		return produceplanService;
	}

	public void setProduceplanService(ProduceplanService produceplanService) {
		this.produceplanService = produceplanService;
	}

	public Long getProduceplanId() {
		return produceplanId;
	}

	public void setProduceplanId(Long produceplanId) {
		this.produceplanId = produceplanId;
	}

	public ProduceplanTodoClaimService getProduceplanTodoClaimService() {
		return produceplanTodoClaimService;
	}

	public void setProduceplanTodoClaimService(
			ProduceplanTodoClaimService produceplanTodoClaimService) {
		this.produceplanTodoClaimService = produceplanTodoClaimService;
	}

	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public List<ProduceplanTodo> getPlanTodos() {
		return planTodos;
	}

	public void setPlanTodos(List<ProduceplanTodo> planTodos) {
		this.planTodos = planTodos;
	}

	public ProduceplanExceptionHandleService getProduceplanExceptionHandleService() {
		return produceplanExceptionHandleService;
	}

	public void setProduceplanExceptionHandleService(
			ProduceplanExceptionHandleService produceplanExceptionHandleService) {
		this.produceplanExceptionHandleService = produceplanExceptionHandleService;
	}




}

