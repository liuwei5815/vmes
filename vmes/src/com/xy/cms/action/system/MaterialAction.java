package com.xy.cms.action.system;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.FileUtil;
import com.xy.cms.common.ImportExecl;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Material;
import com.xy.cms.entity.MaterialType;
import com.xy.cms.service.MaterialService;

public class MaterialAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MaterialService materialService;
	private Material material;
	private File file;
	private String fileFileName;
	/**
	 * 初始化
	 */
	public String init(){
		return "init";
	}
	/**
	 * 数据列表页
	 * @return
	 */
	public String query() {
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				//物料名称
				String maName = request.getParameter("maName");
				pageMap.put("maName", maName);
				//物料编号
				String maCode = request.getParameter("maCode");
				pageMap.put("maCode", maCode);
				//用户物料编号
				String maUserCode = request.getParameter("maUserCode");
				pageMap.put("maUserCode", maUserCode);
				return materialService.queryAllMaterial(pageMap);
			}
		});
		
		/*
		 * try {
			BaseQEntity baseQEntity = new BaseQEntity();
			if (this.getCurrPage() == null
					|| this.getCurrPage().trim().equals(""))
				this.setCurrPage("1");
			baseQEntity.setCurrPage(Integer.parseInt(this.getCurrPage().trim()));
			if (this.getPerPageRows() != null
					&& !"".equals(this.getPerPageRows().trim())) {
				baseQEntity.setPerPageRows(Integer.parseInt(this
						.getPerPageRows().trim()));
			}
			pageMap = new HashMap<String, Object>();
			pageMap.put("qEntity", baseQEntity);
			//鐗╂枡鍚嶇О
			String maName = request.getParameter("maName");
			pageMap.put("maName", maName);
			//鐗╂枡缂栧彿
			String maCode = request.getParameter("maCode");
			pageMap.put("maCode", maCode);
			//鐢ㄦ埛鐗╂枡缂栧彿
			String maUserCode = request.getParameter("maUserCode");
			pageMap.put("maUserCode", maUserCode);
			QueryResult queryResult = materialService.queryAllMaterial(pageMap);
			list = new ArrayList();
			if (queryResult != null) {
				this.setTotalCount(queryResult.getTotalCount());
				this.setTotalPage(queryResult.getTotalPage());
				list = queryResult.getList();
			} else {
				this.setCurrPage("0");
				this.setTotalPage("0");
				this.setTotalCount("0");
				list = null;
			}
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		 * */
		
		return "list";
	}
	/**
	 * 预添加物料
	 * @return
	 */
	public String preAdd(){
		return "add";
	}
	/**
	 * 添加物料
	 * @return
	 */
	public String add(){
		try {
			if(CommonFunction.isNull(material)){
				throw new BusinessException("参数错误");
			}
			materialService.saveMaterial(material);	
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "add";
	}
	
	/**
	 * 预修改物料
	 * @return
	 */
	public String preEdit(){
		try {
			String id=request.getParameter("id");
			
			if(CommonFunction.isNull(id)){
				throw new BusinessException("物料Id为空");
			}
			Material material= materialService.getMaterialById(Long.parseLong(id));
			request.setAttribute("material", material);
			if(CommonFunction.isNotNull(material.getMaterialType())){
				request.setAttribute("maTpye", materialService.queryMaType(Integer.valueOf(material.getMaterialType())));
			}
			
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		
		return "edit";
	}
	/**
	 * 修改物料信息
	 * @return
	 */
	public String edit(){
		try {
			if(CommonFunction.isNull(material)){
				throw new BusinessException("参数错误");
			}
			materialService.updateMaterial(material);
			this.message = "修改成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "edit";
	}
	/**
	 * 删除一个物料信息
	 * @return
	 */
	public String delEquipment(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到物料id
			String id=request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			Material ma=materialService.getMaterialById(Long.parseLong(id));
			if(CommonFunction.isNull(ma)){
				throw new BusinessException("该物料已删除");
			}else{
				materialService.delMaterial(ma);
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
	 * 预批量导入物料
	 * */
	public String preImportMaterial(){
		return "import";
	}
	/**
	 * 导入物料信息
	 * @return
	 */
	public String importMaterial(){
		try {
			String path = ServletActionContext.getServletContext().getRealPath("/excels");
			File upload = new File(path+File.separator+fileFileName);
			FileUtil.copy(file, upload);
			ImportExecl poi = new ImportExecl(); 
			List<List<String>> list = poi.read(path+File.separator+fileFileName);
			if (list != null) {
				List<Material> materialList = new ArrayList<Material>();
				for(int i=1;i<list.size();i++){
					List<String> newList = list.get(i);
					Material material = new Material();
					//用户物料编号
					material.setUsermaterialCode(CommonFunction.isNotNull(newList.get(0).trim())?newList.get(0).trim():null);
					//物料名称
					material.setMaterialName(CommonFunction.isNotNull(newList.get(1).trim())?newList.get(1).trim():null);
					//物料规格-型号
					material.setMaterialSpec(CommonFunction.isNotNull(newList.get(2).trim())?newList.get(2).trim():null);
					//物料类型 -级
					Long matId=null;
					if(CommonFunction.isNotNull(CommonFunction.isNotNull(newList.get(3).trim()))){//excel有
						MaterialType maType=materialService.getMaTpyeByName(newList.get(3).trim()); 
						if(CommonFunction.isNotNull(maType)){//一级存在
							matId=maType.getId();
							if(CommonFunction.isNotNull(newList.get(4).trim())){//excel有二级物料
								MaterialType mtSec=materialService.getMaTpyeByNameAndPid(newList.get(4).trim(), matId);
								if(CommonFunction.isNotNull(mtSec)){//二级物料
									matId=mtSec.getId();
									if(CommonFunction.isNotNull(newList.get(5).trim())){//excel三级物料
										MaterialType mtThr=materialService.getMaTpyeByNameAndPid(newList.get(5).trim(), matId);
										if(CommonFunction.isNotNull(mtThr)){
											matId=mtThr.getId();
										}else{
											MaterialType mt=new MaterialType();
											mt.setName(newList.get(5).trim());
											mt.setPid(matId.intValue());
											if(CommonFunction.isNotNull(newList.get(5).trim())){
												matId=materialService.addMaterialTypeWithId(mt);
											}
										}
									}
								}else{
									MaterialType mt=new MaterialType();
									mt.setName(newList.get(4).trim());
									mt.setPid(matId.intValue());
									matId=materialService.addMaterialTypeWithId(mt);
									if(CommonFunction.isNotNull(newList.get(5).trim())){//excel三级物料
										MaterialType mtThr=materialService.getMaTpyeByNameAndPid(newList.get(5).trim(), matId);
										if(CommonFunction.isNotNull(mtThr)){
											matId=mtThr.getId();
										}else{
											MaterialType newmt=new MaterialType();
											newmt.setName(newList.get(5).trim());
											newmt.setPid(matId.intValue());
											if(CommonFunction.isNotNull(newList.get(5).trim())){
												newList.get(5).trim();
											}
										}
									}
								}
							}
						}else{
							MaterialType matype=new MaterialType();
							matype.setName(newList.get(3).trim());
							matype.setPid(null);
							matId=materialService.addMaterialTypeWithId(matype);
							if(CommonFunction.isNotNull(newList.get(4).trim())){//excel有二级物料
								MaterialType mtSec=materialService.getMaTpyeByNameAndPid(newList.get(4).trim(), matId);
								if(CommonFunction.isNotNull(mtSec)){//二级物料
									matId=mtSec.getId();
									if(CommonFunction.isNotNull(newList.get(5).trim())){//excel三级物料
										MaterialType mtThr=materialService.getMaTpyeByNameAndPid(newList.get(5).trim(), matId);
										if(CommonFunction.isNotNull(mtThr)){
											matId=mtThr.getId();
										}else{
											MaterialType mt=new MaterialType();
											mt.setName(newList.get(5).trim());
											mt.setPid(matId.intValue());
											if(CommonFunction.isNotNull(newList.get(5).trim())){
												matId=materialService.addMaterialTypeWithId(mt);
											}
										}
									}
								}else{
									MaterialType mt=new MaterialType();
									mt.setName(newList.get(4).trim());
									mt.setPid(matId.intValue());
									matId=materialService.addMaterialTypeWithId(mt);
									if(CommonFunction.isNotNull(newList.get(5).trim())){//excel三级物料
										MaterialType mtThr=materialService.getMaTpyeByNameAndPid(newList.get(5).trim(), matId);
										if(CommonFunction.isNotNull(mtThr)){
											matId=mtThr.getId();
										}else{
											MaterialType newMt=new MaterialType();
											newMt.setName(newList.get(5).trim());
											newMt.setPid(matId.intValue());
											if(CommonFunction.isNotNull(newList.get(5).trim())){
												matId=materialService.addMaterialTypeWithId(newMt);
											}
										}
									}
								}
							}
						}
					}
					material.setMaterialType(String.valueOf(matId));
					//物料计量单位
					material.setMaterialUnit(CommonFunction.isNotNull(newList.get(6).trim())?newList.get(6).trim():null);
					materialList.add(material);
				}
				materialService.importMaterial(materialList);
			}
			this.message = "保存成功";
			request.setAttribute("successflag", "1");
		} catch (IOException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "import";
	}
	/***
	 * 物料信息打印
	 * @return
	 * @throws BusinessException 
	 */
	public String print() throws BusinessException{
		String ids =request.getParameter("ids");
		if(StringUtils.isEmpty(ids)){
			throw new BusinessException("请至少选择一项");
		}
		String[] id = ids.split(",");
		List<Material> list = materialService.queryMaterialByIds(com.xy.cms.common.Arrays.strToLong(id));
		request.setAttribute("printList", list);
		return "print";
	}
	
	
	
	public MaterialService getMaterialService() {
		return materialService;
	}
	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	
	
}
