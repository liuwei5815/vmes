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
import com.xy.cms.entity.Test_lw;
import com.xy.cms.service.TestService;
import com.xy.cms.service.TestlwService;

public class TestlwAction  extends BaseAction{
	private TestlwService testlwService;
	
	private Test_lw test_lw;
	
	public String init(){
		return "init";
	}
	
	public String list(){
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				String name=request.getParameter("name");
				if(CommonFunction.isNotNull(name)){
					pageMap.put("name", name);
				}
				return testlwService.queryTestlwPage(pageMap);
			}
		});
		return "list";
	}
	
	public String preEdit(){
		String id = request.getParameter("id");
		try {
			if(CommonFunction.isNull(id))
				throw new BusinessException("id不能为空");
			test_lw = testlwService.getTestlw(Long.parseLong(id));
		} catch (BusinessException e) {
			logger.error(e.getMessage(),e);
		}
		return "edit";
	}
	
	public String edit(){

		try {			
			test_lw.setUdate(new Date());
			testlwService.upateTestlw(test_lw);
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
			if(CommonFunction.isNull(test_lw)){
				throw new BusinessException("参数错误");
			}
			testlwService.saveTestlw(test_lw);	
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
			testlwService.removeTestlw(Long.parseLong(id));
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
	
	public TestlwService getTestlwService() {
		return testlwService;
	}

	public void setTestlwService(TestlwService testlwService) {
		this.testlwService = testlwService;
	}
	
	public Test_lw getTest_lw() {
		return test_lw;
	}

	public void setTest_lw(Test_lw test_lw) {
		this.test_lw = test_lw;
	}
}
