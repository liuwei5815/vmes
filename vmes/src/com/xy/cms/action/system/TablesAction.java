package com.xy.cms.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.Constants;
import com.xy.cms.common.SessionBean;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.entity.TableColumnType;
import com.xy.cms.entity.TableColumns;
import com.xy.cms.entity.TableRelationship;
import com.xy.cms.entity.Tables;
import com.xy.cms.service.TableColumnTypeService;
import com.xy.cms.service.TablesService;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;

public class TablesAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Tables tables;
	
	private TableColumns tableColumns;

	private TableColumnTypeService columnTypeService;
	
	private TablesService tablesService;
	private Long tableId;
	private List listCol;
	private List listRelation;
	private String menuName;
	private Long supiorId;
	private Long relationId;
	private TableRelationship ship;
	private Map tableColListMap;
	private String paramStr;
	
	public String init(){
		return "init";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryTable(){
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				pageMap.put("table", tables);
				pageMap.put("beginDate", request.getParameter("beginDate"));
				pageMap.put("endDate", request.getParameter("endDate"));
				return tablesService.queryTables(pageMap);
			}
		});
		return "list";
	}
	
	public String preAdd() throws Exception{
		List<TableColumnType> tList = columnTypeService.getColumnType();
		request.setAttribute("columnTypeList",tList);
		return "add";
	}
	
	public String add() throws Exception{
		
		//try {
			String par_str=request.getParameter("param_str");
			tablesService.addTables(tables, par_str);
			this.message="操作成功";
			request.setAttribute("successflag","1");
		/*}
		catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}catch(Exception e){
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
			
		}*/
		
		return "add";
	}
	
	public String menuCreate(){
		//一级菜单
		list=tablesService.getFirstLevelMenu();
		return "menu_create";
	}
	
	public String preEdit()throws Exception{
		String tabId=request.getParameter("tableId");
		if(tabId.equals("") || tabId==null){
			throw new BusinessException("参数错误"+tabId);
		}
		this.tables=tablesService.getTableById(Long.parseLong(tabId));
		this.list=tablesService.getCoListById(Long.parseLong(tabId));
		List<TableColumnType> tList = columnTypeService.getColumnType();
		request.setAttribute("columnTypeList",tList);
		request.setAttribute("t_count", tablesService.checkIfExist(Long.parseLong(tabId)));
		request.setAttribute("tableId", tabId);
		return "edit";
	}
	
	public String createMenu() {
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();		
			if (CommonFunction.isNull(tableId)) {
				throw new BusinessException("参数错误");
			}
			if (CommonFunction.isNull(menuName)) {
				throw new BusinessException("参数错误");
			}
			if (CommonFunction.isNull(supiorId)) {
				throw new BusinessException("参数错误");
			}
			SessionBean bean = (SessionBean) this.getSession().get(Constants.SESSION_BEAN);
			tablesService.create(menuName,tableId,supiorId,bean);
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
	
	public String edit()throws Exception{
		//try {
			tablesService.editTables(tables,request.getParameter("tabOldName"));
			
			tablesService.saveCol(tableId, paramStr);
			this.message="操作成功";
			request.setAttribute("successflag","1");
		/*} 
		catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}catch (Exception e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}*/
		
		return "edit";
	}
	
	/*public void saveCol(){
		String tabId=request.getParameter("tableId");
		String par_str=request.getParameter("par_str");
		PrintWriter writer=null;
		Map map=new HashMap();
		try {
			writer=response.getWriter();
			tablesService.saveCol(Long.parseLong(tabId), par_str);
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
	}*/
	
	@SuppressWarnings("unchecked")
	public void updateRelation(){
		PrintWriter writer=null;
		Map map=new HashMap();
		try {
			if(ship == null){
				throw new BusinessException("参数错误");
			}
			writer=response.getWriter();
			tablesService.updateRelation(ship);
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
	
	@SuppressWarnings("unchecked")
	public void delCol(){
		String tabId=request.getParameter("tableId");
		String colId=request.getParameter("colId");
		String colName=request.getParameter("colName");
		
		String[] ids = colId.split(",");
		String[] names = colName.split(",");
		
		PrintWriter writer=null;
		Map map=new HashMap();
		try {
			writer=response.getWriter();
			System.out.println(ids.length);
			for(int i=0;i<ids.length;i++){
				tablesService.delCol(Long.parseLong(tabId), Long.parseLong(ids[i]), names[i]);
			}
			map.put("code", 1);
			map.put("msg", "删除成功");
		}catch (BusinessException e) {
			map.put("code", 0);
			map.put("msg", "删除失败"+e.getMessage());
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
	
	public String preAddRelation(){
		String tableId=request.getParameter("tableId");
		this.tables=tablesService.getTableById(Long.parseLong(tableId));
		this.list=tablesService.getTableListByTabId(Long.parseLong(tableId),1);
		this.listCol=tablesService.getTableListByTabId(Long.parseLong(tableId),2);
		this.listRelation=tablesService.listRelation(Long.parseLong(tableId));
		this.tableColListMap=tablesService.getTableColumnListMap();
		
		return "addRelation";
	}
	
	public void addRelation(){
		PrintWriter writer=null;
		Map map=new HashMap();
		try {
			writer=response.getWriter();
			Long shipId=tablesService.saveRelation(ship);
			listCol=tablesService.getTableListByTabId(ship.getSourceTableId(),2);
			tableColListMap=tablesService.getTableColumnListMap();
			list=tablesService.getTableListByTabId(ship.getSourceTableId(),1);
			map.put("code", 1);
			map.put("shipId", shipId);
			map.put("listCol", listCol);
			map.put("tableColListMap", tableColListMap);
			map.put("list", list);
			map.put("msg", "保存成功");
		} catch (BusinessException e) {
			map.put("code", 0);
			map.put("msg", "保存失败："+e.getMessage());
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
	
	public String preEditRelation(){
		String tableId=request.getParameter("tableId");
		this.list=tablesService.getTableListByTabId(Long.parseLong(tableId),1);//table外键表
		this.listCol=tablesService.getTableListByTabId(Long.parseLong(tableId),2);//column外键字段
		this.listRelation=tablesService.listRelation(Long.parseLong(tableId));//主外键关系
		
		return "editRelation";
	}
	
	@SuppressWarnings("unchecked")
	public void editRelation(){
		PrintWriter writer=null;
		Map map=new HashMap();
		String parStr=request.getParameter("parStr");
		try {
			writer=response.getWriter();
			tablesService.editRelation(parStr);
			map.put("code", 1);
			map.put("msg", "保存成功");
		}catch (BusinessException e) {
			map.put("code", 0);
			map.put("msg", "保存失败："+e.getMessage());
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
	
	public void delRelation(){
		PrintWriter writer=null;
		Map map=new HashMap();
		try {
			if(CommonFunction.isNull(tableId)){
				throw new BusinessException("参数异常");
			}
			if(CommonFunction.isNull(relationId)){
				throw new BusinessException("参数异常");
			}
			writer=response.getWriter();
			tablesService.delRelation(tableId,relationId);
			map.put("code", 1);
			map.put("msg", "保存成功");
		}catch (BusinessException e) {
			map.put("code", 0);
			map.put("msg", "保存失败："+e.getMessage());
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
	
	public void loadColByTabId(){
		try {
			String tableId=request.getParameter("tableId");
			if(!"".equals(tableId)){
				Map map=new HashMap();
				List listColumn=tablesService.getTableListByTabId(Long.parseLong(tableId),2);
				int checkreTableNum=tablesService.checkReTableNum(Long.parseLong(tableId));
				PrintWriter print=response.getWriter();
				map.put("listColumn", listColumn);
				map.put("checkreTableNum", checkreTableNum);
				print.write(new Gson().toJson(map));
				print.close();
			}
		}catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public String delMenu() {
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			if (CommonFunction.isNull(tableId)) {
				throw new BusinessException("参数错误");
			}
			tablesService.delMenu(tableId);
			
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
	
	public String delTableById() {
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			if (CommonFunction.isNull(tables.getId())) {
				throw new BusinessException("参数错误");
			}
			tablesService.delTableById(tables.getId());
			
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
	
	public Tables getTables() {
		return tables;
	}

	public void setTables(Tables tables) {
		this.tables = tables;
	}

	public TableColumns getTableColumns() {
		return tableColumns;
	}

	public void setTableColumns(TableColumns tableColumns) {
		this.tableColumns = tableColumns;
	}

	public void setColumnTypeService(TableColumnTypeService columnTypeService) {
		this.columnTypeService = columnTypeService;
	}

	public Map getTableColListMap() {
		return tableColListMap;
	}

	public void setTableColListMap(Map tableColListMap) {
		this.tableColListMap = tableColListMap;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}


	public void setTablesService(TablesService tablesService) {
		this.tablesService = tablesService;
	}

	public List getListCol() {
		return listCol;
	}

	public void setListCol(List listCol) {
		this.listCol = listCol;
	}

	public TableRelationship getShip() {
		return ship;
	}


	public String getParamStr() {
		return paramStr;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}

	public Long getSupiorId() {
		return supiorId;
	}

	public void setSupiorId(Long supiorId) {
		this.supiorId = supiorId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public void setShip(TableRelationship ship) {
		this.ship = ship;
	}

	public List getListRelation() {
		return listRelation;
	}

	public void setListRelation(List listRelation) {
		this.listRelation = listRelation;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
    
}
