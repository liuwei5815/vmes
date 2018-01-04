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
import com.xy.cms.entity.CustomerType;
import com.xy.cms.service.CustomerTypeService;

public class CustomerTypeAction extends BaseAction {
	private CustomerTypeService customerTypeService;
	
	private CustomerType customerType;
	
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
				return customerTypeService.queryCustomerTypePage(pageMap);
			}
		});
		return "list";
	}
	
	public String query() throws Exception{
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				String cusName = request.getParameter("name");
				pageMap.put("cusName", cusName);
				return customerTypeService.queryCustomerTypePage(pageMap);
			}
		});
		return "list";
	}
	
	public String preEdit(){
		String typeName = request.getParameter("typeName");
		try {
			if(CommonFunction.isNull(typeName))
				throw new BusinessException("客户类型不能为空");
			customerType = customerTypeService.getCustomerType(typeName);
		} catch (BusinessException e) {
			logger.error(e.getMessage(),e);
		}
		return "edit";
	}
	
	public String edit(){

		try {			
			customerTypeService.upateCustomerType(customerType);
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
			if(CommonFunction.isNull(customerType)){
				throw new BusinessException("参数错误");
			}
			customerTypeService.saveCustomerType(customerType);	
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
			customerTypeService.removeCustomerType(id);
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
	
	public CustomerTypeService getCustomerTypeService() {
		return customerTypeService;
	}

	public void setCustomerTypeService(CustomerTypeService customerTypeService) {
		this.customerTypeService = customerTypeService;
	}
	
	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
}
