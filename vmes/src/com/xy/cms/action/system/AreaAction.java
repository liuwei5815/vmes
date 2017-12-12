package com.xy.cms.action.system;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.xy.cms.bean.JsTree;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.FileUtil;
import com.xy.cms.common.ImportExecl;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Customer;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Eqiupment;
import com.xy.cms.entity.EqiupmentType;
import com.xy.cms.entity.MaterialType;
import com.xy.cms.entity.Product;
import com.xy.cms.entity.ProductUint;
import com.xy.cms.entity.Region;
import com.xy.cms.service.AreaService;
import com.xy.cms.service.CustomerService;


public class AreaAction extends BaseAction {
	private AreaService areaService;
	private Region region;
	public String init(){
		List<Region> regionList=areaService.queryAllRegion();
		//初始化js树形结构
		List<JsTree> treeList=new ArrayList<JsTree>();
		for (Region region : regionList) {
			JsTree root=new JsTree();
			root.setText(region.getName());
			root.setId(region.getId().toString());
			root.setParent((region.getParentid()==null || region.getParentid()==0)?"#":region.getParentid().toString());
			treeList.add(root);
		}
		String treeJson=JSON.toJSONString(treeList);
		request.setAttribute("tree",StringEscapeUtils.escapeHtml4(treeJson));
		return "init";
	}
	/**
	 * 添加行政区划
	 */
	public String preAddRegion(){
		return "add";
	} 
	/**
	 * 添加行政区划类型
	 * 
	 * */
	public String add(){
		try {
			if(CommonFunction.isNull(region.getName())){
				throw new BusinessException("行政区划类型名称不能为空");
			}
			if(CommonFunction.isNull(region.getParentid())){
				region.setParentid(0L);
			}
			areaService.saveRegion(region);
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "add";
	}
	
	/**
	 * 添加行政区划类型的时候，查询所有行政区划类型的树
	 * */
	public String tree(){
		//查询所有的行政区划类型
		List<Region> regionList=areaService.queryAllRegion();
		List<JsTree> treeList=new ArrayList<JsTree>();
		for (Region region : regionList) {
			JsTree root=new JsTree();
			root.setText(region.getName());
			root.setId(region.getId().toString());
			root.setParent((region.getParentid()==null || region.getParentid()==0)?"#":region.getParentid().toString());
			treeList.add(root);
		}
		String treeJson=JSON.toJSONString(treeList);
		request.setAttribute("tree",StringEscapeUtils.escapeHtml4(treeJson));
		return "tree";
	}
	
	/**
	 * 预修改行政区划类型
	 * */
	public String preEdit(){
		try {
			String id=request.getParameter("id");
			if(CommonFunction.isNull(id)){
				throw new BusinessException("行政区划类型Id为空");
			}
			Region region = areaService.queryRegionTypeById(Long.parseLong(id));
			request.setAttribute("region", region);
			Region fregion = areaService.queryRegionTypeById(region.getParentid());
			if(CommonFunction.isNull(fregion)){
				request.setAttribute("fregion", region);
			}else{
				
			}request.setAttribute("fregion", fregion);
			
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "edit";
	}
	
	/**
	 * 修改行政区划类型
	 * */
	public String edit(){
		try {
			if(CommonFunction.isNull(region.getName())){
				throw new BusinessException("行政区划类型名称不能为空");
			}
			if(CommonFunction.isNull(region.getId())){
				throw new BusinessException("行政区划类型id为空");
			}
			areaService.updateRegion(region);
			this.message = "修改成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "edit";
	}
	
	/**
	 * 删除行政区划类型
	 * */
	public String delete(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//行政区划类型id
			String id=request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			Region region = areaService.queryRegionTypeById(Long.parseLong(id));
			if(CommonFunction.isNull(region)){
				throw new BusinessException("该行政区划产品已被删除");
			}else{
				areaService.deleteRegion(region);
				json.put("successflag", "1");
				json.put("code",0);
			}
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
	public void queryRegion(){
	
	}
	public AreaService getAreaService() {
		return areaService;
	}
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	
}
