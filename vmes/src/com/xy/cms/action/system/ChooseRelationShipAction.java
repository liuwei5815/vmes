package com.xy.cms.action.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.TabExpander;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.xy.cms.bean.JsTree;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.common.view.TreeView;
import com.xy.cms.entity.TableColumns;
import com.xy.cms.entity.TableRelationship;
import com.xy.cms.entity.Tables;
import com.xy.cms.service.ModelService;
import com.xy.cms.service.TableColumnsService;
import com.xy.cms.service.TableRelationshipService;
import com.xy.cms.service.TablesService;
import com.xy.cms.service.imp.TableColumnsServiceImpl;

public class ChooseRelationShipAction extends BaseAction {
	private ModelService modelService;
	private TableRelationshipService tableRelationshipService;
	private TablesService tablesService;
	private TableColumnsService tableColumnsService;
	private TableRelationship ship;
	private List list;
	private Long shipId;
	private String pkVal;//外键值
	private Long pkId;
	
	public String init(){
	
		try{
			if(CommonFunction.isNull(shipId))
				throw new BusinessException("参数错误");
			ship = 	tableRelationshipService.getRelationById(shipId);
			TableColumns showColumn = tableColumnsService.getTableColumnsById(ship.getTargetShowColumnId());
			if(showColumn==null)
				throw new BusinessException("找不到目标表的字段");
			request.setAttribute("showColumn", showColumn);
		}
		catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		TableRelationship treeShip = tableRelationshipService.getTreeRelationship(ship.getTargetTableId());
		if(treeShip!=null){
			return treeView(treeShip);
		}
		return "init";
	}
	
	private String treeView(TableRelationship treeShip){
		List<TreeView>  treeViews = modelService.getTreeList(treeShip);
		if(!CollectionUtils.isEmpty(treeViews)){
			List<JsTree> jsTrees = new ArrayList();
			for (TreeView treeView : treeViews) {
				JsTree jsTree = new JsTree();
				jsTree.setId(treeView.getId().toString());
				jsTree.setParent((treeView.getParentId()==null || treeView.getParentId()==0)?"#":treeView.getParentId().toString());
				jsTree.setText(treeView.getName());
				jsTrees.add(jsTree);
			}

			String treeJson = JSON.toJSONString(jsTrees);
			request.setAttribute("tree",StringEscapeUtils.escapeHtml4(treeJson));
		}
		return "tree";
	}
	
	
	/**
	 * 主外键关系选择列表
	 * @return
	 */
	public String query(){
	
			this.queryTemplate(new BaseActionQueryPageCallBack() {
				
				@Override
				public QueryResult execute(Map pageMap) throws BusinessException {
					if(CommonFunction.isNull(shipId))
						throw new BusinessException("参数错误");
					TableRelationship ship = 	tableRelationshipService.getRelationById(shipId);
					if(ship==null)
						throw new BusinessException("找不到该主外键关系");
					Tables targetTable = tablesService.getTableById(ship.getTargetTableId());//主表
					if(targetTable==null)
						throw new BusinessException("找不到目标表");
					TableColumns pkColumn = tableColumnsService.getTableColumnsById(ship.getTargetColumnId());
					TableColumns showColumn = tableColumnsService.getTableColumnsById(ship.getTargetShowColumnId());
					if(pkColumn==null || showColumn ==null)
						throw new BusinessException("找不到目标表的字段");
					//主键表作为外键
					if(CommonFunction.isNotNull(pkId)){
						if(CommonFunction.isNull(pkVal))
							throw new BusinessException("参数错误！找不到关联值");
						TableColumns columns = tableColumnsService.getTableColumnsById(pkId);
						
						pageMap.put("pkName",columns.getName());
						pageMap.put("pkVal", pkVal);
					}
					pageMap.put("name",request.getParameter("showName"));
					request.setAttribute("showName", showColumn.getNameCn());
					return modelService.queryShip(pageMap, targetTable.getName(),pkColumn.getName(),showColumn.getName());
				}
			});
		return "list";
	}
	
	public Long getShipId() {
		return shipId;
	}

	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}

	public ModelService getModelService() {
		return modelService;
	}
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}
	public TableRelationshipService getTableRelationshipService() {
		return tableRelationshipService;
	}
	public void setTableRelationshipService(
			TableRelationshipService tableRelationshipService) {
		this.tableRelationshipService = tableRelationshipService;
	}
	public TablesService getTablesService() {
		return tablesService;
	}
	public void setTablesService(TablesService tablesService) {
		this.tablesService = tablesService;
	}
	public TableColumnsService getTableColumnsService() {
		return tableColumnsService;
	}
	public void setTableColumnsService(TableColumnsService tableColumnsService) {
		this.tableColumnsService = tableColumnsService;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}

	public TableRelationship getShip() {
		return ship;
	}

	public void setShip(TableRelationship ship) {
		this.ship = ship;
	}




	public String getPkVal() {
		return pkVal;
	}




	public void setPkVal(String pkVal) {
		this.pkVal = pkVal;
	}




	public Long getPkId() {
		return pkId;
	}




	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}
	
	
}
