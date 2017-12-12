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
import com.xy.cms.entity.Material;
import com.xy.cms.entity.MaterialType;
import com.xy.cms.entity.Product;
import com.xy.cms.entity.ProductUint;
import com.xy.cms.service.MaterielTypeService;


/**
 * 
 * 物料类型管理Action
 * */
public class MaterielTypeAction extends BaseAction{
	private MaterielTypeService materielTypeService;
	private MaterialType materialType;
	private File file;
	private String fileFileName;
	
	/**
	 * 初始化
	 * */
	public String init(){
		//查询所有的物料类型
		List<MaterialType> materialTypeList = materielTypeService.quertMaterielTypeAll();
		List<JsTree> treeList=new ArrayList<JsTree>();
		for (MaterialType materialType : materialTypeList) {
			JsTree root=new JsTree();
			root.setText(materialType.getName());
			root.setId(materialType.getId().toString());
			root.setParent((materialType.getPid()==null || materialType.getPid()==0)?"#":materialType.getPid().toString());
			treeList.add(root);
		}
		String treeJson=JSON.toJSONString(treeList);
		request.setAttribute("tree",StringEscapeUtils.escapeHtml4(treeJson));
		return "init";
	}
	
	/**
	 * 预添加物料类型
	 * */
	public String preAdd(){
		return "add";
	}
	
	/**
	 * 添加物料类型
	 * 
	 * */
	public String add(){
		try {
			if(CommonFunction.isNull(materialType.getName())){
				throw new BusinessException("物料类型名称不能为空");
			}
			if(materielTypeService.validateRepeatName(materialType.getName(), materialType.getPid().longValue())){
				throw new BusinessException("同一级下面不得创建相同名称的物料类型");
			}
			materielTypeService.saveMaterialType(materialType);
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "add";
	}
	
	/**
	 * 添加物料类型的时候，查询所有物料类型的树
	 * */
	public String tree(){
		//查询所有的物料类型
		List<MaterialType> materialTypeList = materielTypeService.quertMaterielTypeAll();
		List<JsTree> treeList=new ArrayList<JsTree>();
		for (MaterialType materialType : materialTypeList) {
			JsTree root=new JsTree();
			root.setText(materialType.getName());
			root.setId(materialType.getId().toString());
			root.setParent((materialType.getPid()==null || materialType.getPid()==0)?"#":materialType.getPid().toString());
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
				throw new BusinessException("物料类型Id为空");
			}
			MaterialType materialType = materielTypeService.queryMaterialTypeById(Long.parseLong(id));
			request.setAttribute("materialType", materialType);
			if(materialType.getPid() != null){
				MaterialType pMaterialType = materielTypeService.queryMaterialTypeById(materialType.getPid().longValue());
				request.setAttribute("pMaterialType", pMaterialType);
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
			if(CommonFunction.isNull(materialType.getName())){
				throw new BusinessException("物料类型名称不能为空");
			}
			if(CommonFunction.isNull(materialType.getId())){
				throw new BusinessException("物料类型id为空");
			}
			if(materielTypeService.validateRepeatName(materialType.getName(), materialType.getPid().longValue())){
				throw new BusinessException("同一级下面不得创建相同名称的物料类型");
			}
			materielTypeService.updateMaterialType(materialType);
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
			//物料类型id
			String id=request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			MaterialType materialType = materielTypeService.queryMaterialTypeById(Long.parseLong(id));
			if(CommonFunction.isNull(materialType)){
				throw new BusinessException("该物料产品已被删除");
			}else{
				materielTypeService.deleteMaterialType(materialType);
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
	 * 预批量导入物料类型
	 * */
	public String preImportMaterialType(){
		return "import";
	}
	
	/**
	 * 批量导入物料类型
	 * */
	public String importMaterialType(){
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
					MaterialType oneMaterialType= materielTypeService.queryMaterialType(newList.get(0).trim(),0L);
					if(CommonFunction.isNotNull(oneMaterialType)){//数据一级类型存在
						//判断二级
						if(CommonFunction.isNotNull(newList.get(1).trim())){//表格中二级类型存在
							MaterialType twoMaterialType= materielTypeService.queryMaterialType(newList.get(1).trim(),oneMaterialType.getId());
							//判断数据库中是否存在
							if(CommonFunction.isNotNull(twoMaterialType)){//数据库二级类型存在
								if(CommonFunction.isNotNull(newList.get(2).trim())){//表格库三级存在
									MaterialType threeMaterialType= materielTypeService.queryMaterialType(newList.get(2).trim(),twoMaterialType.getId());
									if(CommonFunction.isNull(threeMaterialType)){
										MaterialType threeMaterialTypeS= new MaterialType();
										threeMaterialTypeS.setName(newList.get(2).trim());
										threeMaterialTypeS.setPid(twoMaterialType.getId().intValue());
										materielTypeService.saveMaterialType(threeMaterialTypeS);
									}
								}
							}else{//数据库二级不存在
								MaterialType twoMaterialTypeS= new MaterialType();
								twoMaterialTypeS.setName(newList.get(1).trim());
								twoMaterialTypeS.setPid(oneMaterialType.getId().intValue());
								materielTypeService.saveMaterialType(twoMaterialTypeS);
								if(CommonFunction.isNotNull(newList.get(2).trim())){//表格库三级存在
									MaterialType threeMaterialType= materielTypeService.queryMaterialType(newList.get(2).trim(),twoMaterialTypeS.getId());
									if(CommonFunction.isNull(threeMaterialType)){
										MaterialType threeMaterialTypeS= new MaterialType();
										threeMaterialTypeS.setName(newList.get(2).trim());
										threeMaterialTypeS.setPid(twoMaterialTypeS.getId().intValue());
										materielTypeService.saveMaterialType(threeMaterialTypeS);
									}
								}
							}
						}
					}else{//数据一级类型不存在
						MaterialType oneMaterialTypeS= new MaterialType();
						oneMaterialTypeS.setName(newList.get(0).trim());
						oneMaterialTypeS.setPid(0);
						//添加
						materielTypeService.saveMaterialType(oneMaterialTypeS);
						//判断二级
						if(CommonFunction.isNotNull(newList.get(1).trim())){//表格二级存在
							MaterialType twoMaterialType= materielTypeService.queryMaterialType(newList.get(1).trim(),oneMaterialTypeS.getId());
							if(CommonFunction.isNotNull(twoMaterialType)){//数据库二级存在
								if(CommonFunction.isNotNull(newList.get(2).trim())){//表格库三级存在
									MaterialType threeMaterialType= materielTypeService.queryMaterialType(newList.get(2).trim(),twoMaterialType.getId());
									if(CommonFunction.isNull(threeMaterialType)){
										MaterialType threeMaterialTypeS= new MaterialType();
										threeMaterialTypeS.setName(newList.get(2).trim());
										threeMaterialTypeS.setPid(twoMaterialType.getId().intValue());
										materielTypeService.saveMaterialType(threeMaterialTypeS);
									}
								}
							}else{//数据库二级不存在
								MaterialType twoMaterialTypeS = new MaterialType();
								twoMaterialTypeS.setName(newList.get(1).trim());
								twoMaterialTypeS.setPid(oneMaterialTypeS.getId().intValue());
								materielTypeService.saveMaterialType(twoMaterialTypeS);
								if(CommonFunction.isNotNull(newList.get(2).trim())){//表格库三级存在
									MaterialType threeMaterialType= materielTypeService.queryMaterialType(newList.get(2).trim(),twoMaterialTypeS.getId());
									if(CommonFunction.isNull(threeMaterialType)){
										MaterialType threeMaterialTypeS= new MaterialType();
										threeMaterialTypeS.setName(newList.get(2).trim());
										threeMaterialTypeS.setPid(twoMaterialTypeS.getId().intValue());
										materielTypeService.saveMaterialType(threeMaterialTypeS);
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


	public MaterielTypeService getMaterielTypeService() {
		return materielTypeService;
	}


	public void setMaterielTypeService(MaterielTypeService materielTypeService) {
		this.materielTypeService = materielTypeService;
	}

	public MaterialType getMaterialType() {
		return materialType;
	}

	public void setMaterialType(MaterialType materialType) {
		this.materialType = materialType;
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
