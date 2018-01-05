package com.xy.cms.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Test;
import com.xy.cms.service.TestService;

public class TestAction  extends BaseAction{
	private TestService testService;
	
	private Test test;
	
	public String init(){
		return "init";
	}
	
	public String list(){
//		this.queryTemplate(new BaseActionQueryPageCallBack() {
//			@Override
//			public QueryResult execute(Map pageMap) throws BusinessException {
//				String name=request.getParameter("name");
//				if(CommonFunction.isNotNull(name)){
//					pageMap.put("name", name);
//				}
//				return testService.queryTestPage(pageMap);
//			}
//		});
		return "list";
	}
	
	public String preEdit(){
//		String id = request.getParameter("id");
//	    try {
//			if(CommonFunction.isNull(id))
//				throw new BusinessException("id不能为空");
//			test = testService.getTest(Integer.parseInt(id));
//		} catch (BusinessException e) {
//			logger.error(e.getMessage(),e);
//		}
		return "edit";
	}
	
	public String edit(){

		try {
			test.setUdate(new Date());
			testService.upateTest(test);
			this.message="操作成功";
			request.setAttribute("successflag","1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "edit";
	}
	
	public String preAdd(){
		return "add";
	}
	
	public String add(){
		try {
			if(CommonFunction.isNull(test)){
				throw new BusinessException("参数错误");
			}
			testService.saveTest(test);	
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "add";
	}
	
	public void delete(){
		String id = request.getParameter("id");
		Map param = new HashMap();
		PrintWriter writer= null;
		try {
			writer = response.getWriter();
			if(CommonFunction.isNull(id))
				throw new BusinessException("id不能为空");
			testService.removeTest(Integer.parseInt(id));
			param.put("code",1);
			param.put("msg","删除成功");
		} catch (BusinessException e) {
			param.put("code",0);
			param.put("msg","删除失败");
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			param.put("code",0);
			param.put("msg","删除失败");
			e.printStackTrace();
		} finally{
			if(writer!=null){
				writer.write(new Gson().toJson(param));
				writer.close();
			}
		}
	}
	
	public TestService getTestService() {
		return testService;
	}

	public void setTestService(TestService testService) {
		this.testService = testService;
	}
	
	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}
}
