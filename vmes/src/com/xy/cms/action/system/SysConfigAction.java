package com.xy.cms.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Config;
import com.xy.cms.service.ConfigService;

public class SysConfigAction extends BaseAction{
	private ConfigService configService;
	
	private Config config;
	
	public String init(){
		return "init";
	}
	
	public String list(){
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				return configService.queryConfigPage(pageMap);
			}
		});
		return "list";
	}
	
	public String preEdit(){
		String id = request.getParameter("id");
		try {
			if(CommonFunction.isNull(id))
				throw new BusinessException("id不能为空");
			config = configService.getConfig(Integer.parseInt(id));
		} catch (BusinessException e) {
			logger.error(e.getMessage(),e);
		}
		return "edit";
	}
	
	public String edit(){
		System.out.println(config.getDes());
		try {
			configService.upateConfig(config);
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
			configService.saveConfig(config);
			this.message="操作成功";
			request.setAttribute("successflag","1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
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
			configService.removeConfig(Integer.parseInt(id));
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
	
	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
	
	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
}
