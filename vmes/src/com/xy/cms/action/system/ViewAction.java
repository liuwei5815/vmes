package com.xy.cms.action.system;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.View;
import com.xy.cms.service.TablesService;
import com.xy.cms.service.ViewService;

@SuppressWarnings("serial")
public class ViewAction extends BaseAction{
	private View view;
	private ViewService viewService;
	private TablesService tablesService;
	
	public String init(){
		return "init";
	}
	
    public String query(){
    	this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				pageMap.put("view", view);
				pageMap.put("beginDate", request.getParameter("beginDate"));
				pageMap.put("endDate", request.getParameter("endDate"));
				return viewService.queryTables(pageMap);
			}
		});
    	return "list";
    }
    
    public String addView(){
    	return "addView";
    }
    
    public String getTableInfo(){
    	this.list=viewService.getTablesInfo();
    	return "tableInfo";
    }
    
    public void saveView() throws Exception{
    	View v=new View();
    	String tableId=request.getParameter("tableId");
    	String searchRole=request.getParameter("searchRole");
    	String name=request.getParameter("name");
    	v.setTableId(Long.parseLong(tableId));
    	v.setSearchRole(searchRole);
    	v.setName(name);
    	v.setState((short)1);
		v.setCreateTime(new Date());
    	String ruleStr_def=request.getParameter("ruleStr_def");
    	String ruleStr_dt=request.getParameter("ruleStr_dt");
    	String columnStr=request.getParameter("columnStr");
    	String orderStr=request.getParameter("orderStr");
    	String viewName_old=request.getParameter("viewName_old");
    	String flag=request.getParameter("flag");
    	String viewId=request.getParameter("viewId");
    	if(viewId==null || "".equals(viewId)){
    		viewId="-1";
    	}
    	PrintWriter writer=null;
		Map map=new HashMap();
		try {
			writer=response.getWriter();
			viewService.saveView(v,ruleStr_def,ruleStr_dt,columnStr, orderStr,flag,viewName_old,Long.parseLong(viewId));
			map.put("code", 1);
			map.put("msg", "保存成功");
		}catch (BusinessException e) {
			map.put("code", 0);
			map.put("msg", e.getMessage());
			logger.error(e.getMessage());
		}finally{
			if(writer!=null){
				writer.write(new Gson().toJson(map));
				writer.close();
			}
		}
    }
    
    public void saveViewNew(){
    	View v=new View();
    	String tableIdStr=request.getParameter("tableId");//所有的需要查询的表字符串
    	String searchRole=request.getParameter("searchRole");//高级条件
    	String name=request.getParameter("name");//视图名称
    	String[] tableIdSz=tableIdStr.split(",");
    	Long tableId=0l;
    	String tabIdStr="";
    	if(tableIdSz!=null && tableIdSz.length>0){
    		for(int i=0;i<tableIdSz.length;i++){
    			String tableId_str=tableIdSz[i].split("#")[0];
    			String otherName=tableIdSz[i].split("#")[1];
    			if(otherName.equals("t")){
    				tableId=Long.parseLong(tableId_str);
    			}else{
    				tabIdStr+=","+tableId_str;
    			}
    		}
    	}
    	v.setTableId(tableId);
    	v.setSearchRole(searchRole);
    	v.setName(name);
    	v.setState((short)1);
		v.setCreateTime(new Date());
		if(tabIdStr.length()>0 && tabIdStr.indexOf(",")>=0){
			v.setTargetTabId(tabIdStr.substring(1));
		}
		
		
    	String ruleStr_def=request.getParameter("ruleStr_def");
    	String ruleStr_dt=request.getParameter("ruleStr_dt");
    	String columnStr=request.getParameter("columnStr");
    	String orderStr=request.getParameter("orderStr");
    	String viewName_old=request.getParameter("viewName_old");
    	String flag=request.getParameter("flag");
    	String viewId=request.getParameter("viewId");
    	if(viewId==null || "".equals(viewId)){
    		viewId="-1";
    	}
    	PrintWriter writer=null;
		Map map=new HashMap();
		try {
			writer=response.getWriter();
			viewService.saveViewNew(v,ruleStr_def,ruleStr_dt,columnStr, orderStr,flag,viewName_old,Long.parseLong(viewId));
			map.put("code", 1);
			map.put("msg", "保存成功");
		}catch (BusinessException e) {
			map.put("code", 0);
			map.put("msg", e.getMessage());
			logger.error(e.getMessage());
		}catch (Exception e) {
			map.put("code", 0);
			map.put("msg", "服务器异常");
			logger.error(e.getMessage());
		}finally{
			if(writer!=null){
				writer.write(new Gson().toJson(map));
				writer.close();
			}
		}
    }
    
    public String preEditView(){
    	String id=request.getParameter("id");
    	Map map=viewService.getViewInfoById(Long.parseLong(id));
    	request.setAttribute("map", map);
    	return "editView";
    }
    
    public String delView(){
    	Long id=Long.parseLong(request.getParameter("id"));
    	viewService.delViewById(id);
    	return null;
    }
    
    public String selectRetabInfo(){

			Long tableId=Long.parseLong(request.getParameter("tableId"));
			List listReTable=tablesService.reTableInfo(tableId);
			List listRe=new ArrayList();
			for(int i=0;i<listReTable.size();i++){
				Object[] obj=(Object[]) listReTable.get(i);
				Object[] ob=new Object[4];
				ob[0]=obj[0];
				ob[1]=obj[1];
				ob[2]=obj[2];
				ob[3]=tablesService.getTableListByTabId(Long.parseLong(ob[0].toString()), 2);
				listRe.add(ob);
			}
			request.setAttribute("listRe", listRe);
			return "tableInfoRe";
    }
    
	public View getView() {
		return view;
	}
	public void setView(View view) {
		this.view = view;
	}
	public ViewService getViewService() {
		return viewService;
	}
	public void setViewService(ViewService viewService) {
		this.viewService = viewService;
	}
	public TablesService getTablesService() {
		return tablesService;
	}

	public void setTablesService(TablesService tablesService) {
		this.tablesService = tablesService;
	}
}
