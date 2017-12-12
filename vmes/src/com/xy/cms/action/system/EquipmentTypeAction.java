package com.xy.cms.action.system;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.xy.cms.bean.JsTree;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.FileUtil;
import com.xy.cms.common.ImportExecl;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.EqiupmentType;
import com.xy.cms.entity.MaterialType;
import com.xy.cms.service.EquipmentTypeService;


/**
 * 
 * 设备类型管理Action
 * */
public class EquipmentTypeAction extends BaseAction{
	private EquipmentTypeService equipmentTypeService;
	private EqiupmentType equipmentType;
	private File file;
	private String fileFileName;
	
	/**
	 * 初始化
	 * */
	public String init(){
		//查询所有的设备类型
		List<EqiupmentType> eqiupmentTypeTypeList = equipmentTypeService.quertEqiupmentTypeAll();
		List<JsTree> treeList=new ArrayList<JsTree>();
		for (EqiupmentType eqiupmentType : eqiupmentTypeTypeList) {
			JsTree root=new JsTree();
			root.setText(eqiupmentType.getType());
			root.setId(eqiupmentType.getId().toString());
			root.setParent((eqiupmentType.getPid()==null || eqiupmentType.getPid()==0)?"#":eqiupmentType.getPid().toString());
			treeList.add(root);
		}
		String treeJson=JSON.toJSONString(treeList);
		request.setAttribute("tree",StringEscapeUtils.escapeHtml4(treeJson));
		return "init";
	}
	
	/**
	 * 预添加设备类型
	 * */
	public String preAdd(){
		return "add";
	}
	
	
	/**
	 * 添加设备类型
	 * 
	 * */
	public String add(){
		try {
			if(CommonFunction.isNull(equipmentType.getType())){
				throw new BusinessException("设备类型名称不能为空");
			}
			if(equipmentTypeService.validateRepeatName(equipmentType.getType(), equipmentType.getPid().longValue())){
				throw new BusinessException("同一级下面不得创建相同名称的设备类型");
			}
			equipmentTypeService.saveEqiupmentType(equipmentType);
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "add";
	}
	
	/**
	 * 添加设备类型的时候，查询所有设备类型的树
	 * */
	public String tree(){
		//查询所有的设备类型
		List<EqiupmentType> eqiupmentTypeTypeList = equipmentTypeService.quertEqiupmentTypeAll();
		List<JsTree> treeList=new ArrayList<JsTree>();
		for (EqiupmentType eqiupmentType : eqiupmentTypeTypeList) {
			JsTree root=new JsTree();
			root.setText(eqiupmentType.getType());
			root.setId(eqiupmentType.getId().toString());
			root.setParent((eqiupmentType.getPid()==null || eqiupmentType.getPid()==0)?"#":eqiupmentType.getPid().toString());
			treeList.add(root);
		}
		String treeJson=JSON.toJSONString(treeList);
		request.setAttribute("tree",StringEscapeUtils.escapeHtml4(treeJson));
		return "tree";
	}
	
	
	/**
	 * 预修改物料类型
	 * */
	public String preEdit(){
		try {
			String id=request.getParameter("id");
			if(CommonFunction.isNull(id)){
				throw new BusinessException("设备类型Id为空");
			}
			EqiupmentType eqiupmentType = equipmentTypeService.queryEqiupmentTypeById(Long.parseLong(id));
			request.setAttribute("eqiupmentType", eqiupmentType);
			if(eqiupmentType.getPid() != null){
				EqiupmentType pEqiupmentType = equipmentTypeService.queryEqiupmentTypeById(eqiupmentType.getPid().longValue());
				request.setAttribute("pEqiupmentType", pEqiupmentType);
			}
			
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "edit";
	}
	
	/**
	 * 修改物料类型
	 * */
	public String edit(){
		try {
			if(CommonFunction.isNull(equipmentType.getType())){
				throw new BusinessException("设备类型名称不能为空");
			}
			if(CommonFunction.isNull(equipmentType.getId())){
				throw new BusinessException("设备类型id为空");
			}
			if(equipmentTypeService.validateRepeatName(equipmentType.getType(), equipmentType.getPid().longValue())){
				throw new BusinessException("同一级下面不得创建相同名称的设备类型");
			}
			equipmentTypeService.updateEqiupmentType(equipmentType);
			this.message = "修改成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "edit";
	}
	
	
	/**
	 * 删除物料类型
	 * */
	public String delete(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//设备类型id
			String id=request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			EqiupmentType eqiupmentType = equipmentTypeService.queryEqiupmentTypeById(Long.parseLong(id));
			if(CommonFunction.isNull(eqiupmentType)){
				throw new BusinessException("该物料产品已被删除");
			}else{
				equipmentTypeService.deleteEqiupmentType(eqiupmentType);
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
	
	/**
	 * 预批量导入设备类型
	 * */
	public String preImportEquipmentType(){
		return "import";
	}
	
	/**
	 * 批量导入设备类型
	 * */
	public String importEquipmentType(){
		try {
			String path = ServletActionContext.getServletContext().getRealPath("/excels");
			File upload = new File(path+File.separator+fileFileName);
			FileUtil.copy(file, upload);
			ImportExecl poi = new ImportExecl(); 
			List<List<String>> list = poi.read(path+File.separator+fileFileName);
			Long matId=null;
			MaterialType materialType = new MaterialType();
			if (list != null) {
				for(int i=1;i<list.size();i++){
					//取到每行的数据
					List<String> newList = list.get(i);
					//通过名字和上级id查询一级类型
					EqiupmentType oneEqiupmentType= equipmentTypeService.queryEqiupmentType(newList.get(0).trim(),0L);
					if(CommonFunction.isNotNull(oneEqiupmentType)){//数据一级类型存在
						//判断二级
						if(CommonFunction.isNotNull(newList.get(1).trim())){//表格中二级类型存在
							EqiupmentType twoEqiupmentType= equipmentTypeService.queryEqiupmentType(newList.get(1).trim(),oneEqiupmentType.getId());
							//判断数据库中是否存在
							if(CommonFunction.isNotNull(twoEqiupmentType)){//数据库二级类型存在
								if(CommonFunction.isNotNull(newList.get(2).trim())){//表格库三级存在
									EqiupmentType threeEqiupmentType= equipmentTypeService.queryEqiupmentType(newList.get(2).trim(),twoEqiupmentType.getId());
									if(CommonFunction.isNull(threeEqiupmentType)){
										EqiupmentType threeEqiupmentTypeS= new EqiupmentType();
										threeEqiupmentTypeS.setType(newList.get(2).trim());
										threeEqiupmentTypeS.setPid(twoEqiupmentType.getId().intValue());
										equipmentTypeService.saveEqiupmentType(threeEqiupmentTypeS);
									}
								}
							}else{//数据库二级不存在
								EqiupmentType twoEqiupmentTypeS= new EqiupmentType();
								twoEqiupmentTypeS.setType(newList.get(1).trim());
								twoEqiupmentTypeS.setPid(oneEqiupmentType.getId().intValue());
								equipmentTypeService.saveEqiupmentType(twoEqiupmentTypeS);
								if(CommonFunction.isNotNull(newList.get(2).trim())){//表格库三级存在
									EqiupmentType threeMaterialType= equipmentTypeService.queryEqiupmentType(newList.get(2).trim(),twoEqiupmentTypeS.getId());
									if(CommonFunction.isNull(threeMaterialType)){
										EqiupmentType threeEqiupmentTypeS= new EqiupmentType();
										threeEqiupmentTypeS.setType(newList.get(2).trim());
										threeEqiupmentTypeS.setPid(twoEqiupmentTypeS.getId().intValue());
										equipmentTypeService.saveEqiupmentType(threeEqiupmentTypeS);
									}
								}
							}
						}
					}else{//数据一级类型不存在
						EqiupmentType oneEqiupmentTypeS= new EqiupmentType();
						oneEqiupmentTypeS.setType(newList.get(0).trim());
						oneEqiupmentTypeS.setPid(0);
						//添加
						equipmentTypeService.saveEqiupmentType(oneEqiupmentTypeS);
						//判断二级
						if(CommonFunction.isNotNull(newList.get(1).trim())){//表格二级存在
							EqiupmentType twoEqiupmentType= equipmentTypeService.queryEqiupmentType(newList.get(1).trim(),oneEqiupmentTypeS.getId());
							if(CommonFunction.isNotNull(twoEqiupmentType)){//数据库二级存在
								if(CommonFunction.isNotNull(newList.get(2).trim())){//表格库三级存在
									EqiupmentType threeEqiupmentType= equipmentTypeService.queryEqiupmentType(newList.get(2).trim(),twoEqiupmentType.getId());
									if(CommonFunction.isNull(threeEqiupmentType)){
										EqiupmentType threeEqiupmentTypeS= new EqiupmentType();
										threeEqiupmentTypeS.setType(newList.get(2).trim());
										threeEqiupmentTypeS.setPid(twoEqiupmentType.getId().intValue());
										equipmentTypeService.saveEqiupmentType(threeEqiupmentTypeS);
									}
								}
							}else{//数据库二级不存在
								EqiupmentType twoEqiupmentTypeS = new EqiupmentType();
								twoEqiupmentTypeS.setType(newList.get(1).trim());
								twoEqiupmentTypeS.setPid(oneEqiupmentTypeS.getId().intValue());
								equipmentTypeService.saveEqiupmentType(twoEqiupmentTypeS);
								if(CommonFunction.isNotNull(newList.get(2).trim())){//表格库三级存在
									EqiupmentType threeEqiupmentType= equipmentTypeService.queryEqiupmentType(newList.get(2).trim(),twoEqiupmentTypeS.getId());
									if(CommonFunction.isNull(threeEqiupmentType)){
										EqiupmentType threeEqiupmentTypeS= new EqiupmentType();
										threeEqiupmentTypeS.setType(newList.get(2).trim());
										threeEqiupmentTypeS.setPid(twoEqiupmentTypeS.getId().intValue());
										equipmentTypeService.saveEqiupmentType(threeEqiupmentTypeS);
									}
								}
							}
						}
					}
				}
			}
			this.message = "保存成功";
			request.setAttribute("successflag", "1");
		} catch (IOException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		
		return "import";
	}
	

	public EquipmentTypeService getEquipmentTypeService() {
		return equipmentTypeService;
	}

	public void setEquipmentTypeService(EquipmentTypeService equipmentTypeService) {
		this.equipmentTypeService = equipmentTypeService;
	}

	public EqiupmentType getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(EqiupmentType equipmentType) {
		this.equipmentType = equipmentType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	
	
}

