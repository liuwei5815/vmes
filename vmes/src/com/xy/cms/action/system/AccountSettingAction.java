package com.xy.cms.action.system;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.xy.cms.common.CacheFun;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Config;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.License;
import com.xy.cms.service.ConfigService;
import com.xy.cms.service.LicenseService;

public class AccountSettingAction extends BaseAction{
	private ConfigService configService;
	private Config config;
	private LicenseService licenseService;
	private License license;
	
	/**
	 * 初始化页面
	 */
	public String init() throws Exception {
		request.setAttribute("config", configService.getConfig(6));
		License license = licenseService.getLicById(1L);
		request.setAttribute("license", license);
		System.out.println(license.getWebNum());
		return "init";
	}
	
	public ConfigService getConfigService() {
		return configService;
	}
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
	public String save(){
		try {
			if(CommonFunction.isNull(license.getExpiredDate())){
				throw new BusinessException("软件有效期不能为空");
			}
			//保存数量
			config=configService.getConfig(6);
			config.setConfvalue(Integer.toString(license.getWebNum()+license.getAppNum()));
			configService.upateConfig(config);
			//
			License lic=licenseService.getLicById(1L);
			if(CommonFunction.isNotNull(lic)){
				//更新
				lic.setExpiredDate(license.getExpiredDate());
				lic.setViableAcct(Integer.toString(license.getWebNum()+license.getAppNum()));
				lic.setAppNum(license.getAppNum());
				lic.setWebNum(license.getWebNum());
				licenseService.update(lic);
			}else{
				//保存
				lic.setExpiredDate(license.getExpiredDate());
				lic.setViableAcct(Integer.toString(license.getWebNum()+license.getAppNum()));
				lic.setAppNum(license.getAppNum());
				lic.setWebNum(license.getWebNum());
				licenseService.save(lic);
			}
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "init";
		
		/*PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();
			//保存数量
			config=configService.getConfig(6);
			config.setConfvalue(request.getParameter("num"));
			configService.upateConfig(config);
			//更新缓存
			CacheFun.updateConfig("account_num", request.getParameter("num"));
			//保存控制选项
			String dd=request.getParameter("dd");
			String ac=request.getParameter("acctCtrl");
			System.out.println(dd + ", " + ac);
			JSONArray acAr=JSONArray.parseArray(ac);
			StringBuffer actr=new StringBuffer();
			for (Object object : acAr) {
				actr.append(object);
			} 
			License lic=licenseService.getLicById(1L);
			if(CommonFunction.isNotNull(lic)){
				//更新
				lic.setExpiredDate(DateUtil.str2Date(dd, "yyyy-MM-dd"));
				lic.setAcctCtrl(actr.toString());
				licenseService.update(lic);
			}else{
				//保存
				license.setExpiredDate(DateUtil.str2Date(request.getParameter("dd"), "yyyy-MM-dd"));
				license.setAcctCtrl(request.getParameter("acctCtrl"));
				licenseService.save(license);
			}
			json.put("successflag", "1");
			json.put("code",0);
		} catch (Exception e) {
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
		return NONE;*/
	}
	public LicenseService getLicenseService() {
		return licenseService;
	}
	public void setLicenseService(LicenseService licenseService) {
		this.licenseService = licenseService;
	}
	public License getLicense() {
		return license;
	}
	public void setLicense(License license) {
		this.license = license;
	}
}
