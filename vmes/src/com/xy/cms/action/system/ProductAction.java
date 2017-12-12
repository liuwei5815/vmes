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
import com.xy.cms.common.FileUtil;
import com.xy.cms.common.ImportExecl;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.Material;
import com.xy.cms.entity.MaterialType;
import com.xy.cms.entity.Orders;
import com.xy.cms.entity.Product;
import com.xy.cms.entity.ProductUint;
import com.xy.cms.service.OrdersService;
import com.xy.cms.service.ProductService;

public class ProductAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductService productService;
	private Product product;
	private File file;
	private String fileFileName;
	private ProductUint pu;
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
				//产品名称
				String productName = request.getParameter("productName");
				pageMap.put("productName", productName);
				//产品编号
				String productCode = request.getParameter("productCode");
				pageMap.put("productCode", productCode);
				//用户产品编号
				String userProductCode = request.getParameter("userProductCode");
				pageMap.put("userProductCode", userProductCode);
				return productService.queryProductPage(pageMap);
			}
		});
		return "list";
	}
	/**
	 * 预添加产品
	 * @return
	 */
	public String preAdd(){
		return "add";
	}
	/**
	 * 添加产品
	 * @return
	 */
	public String add(){
		try {
			if(CommonFunction.isNull(product)){
				throw new BusinessException("参数错误");
			}
			productService.saveProduct(product);	
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "add";
	}
	
	/**
	 * 预修改产品
	 * @return
	 */
	public String preEdit(){
		try {
			String id=request.getParameter("id");
			if(CommonFunction.isNull(id)){
				throw new BusinessException("产品Id为空");
			}
			Product product= productService.getProductById(Long.parseLong(id));
			request.setAttribute("product", product);
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		
		return "edit";
	}
	/**
	 * 修改产品信息
	 * @return
	 */
	public String edit(){
		try {
			if(CommonFunction.isNull(product)){
				throw new BusinessException("参数错误");
			}
			productService.updateProduct(product);
			this.message = "修改成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "edit";
	}
	/**
	 * 删除一个产品信息
	 * @return
	 */
	public String del(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到产品id
			String id=request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			Product eq=productService.getProductById(Long.parseLong(id));
			if(CommonFunction.isNull(eq)){
				throw new BusinessException("该产品已删除");
			}else{
				productService.delProduct(eq);
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
	 * 预批量导入产品
	 * */
	public String preImportProduct(){
		return "import";
	}
	/**
	 * 导入产品
	 * @return
	 */
	public String importProduct(){
		try {
			String path = ServletActionContext.getServletContext().getRealPath("/excels");
			File upload = new File(path+File.separator+fileFileName);
			FileUtil.copy(file, upload);
			ImportExecl poi = new ImportExecl(); 
			List<List<String>> list = poi.read(path+File.separator+fileFileName);
			if (list != null) {
				List<Product> productList = new ArrayList<Product>();
				for(int i=1;i<list.size();i++){
					List<String> newList = list.get(i);
					Product product = new Product();
					product.setUserProductCode(CommonFunction.isNotNull(newList.get(0).trim())?newList.get(0).trim():null);
					product.setName(CommonFunction.isNotNull(newList.get(1).trim())?newList.get(1).trim():null);//产品名称
					product.setTypespec(CommonFunction.isNotNull(newList.get(2).trim())?newList.get(2).trim():null);//规格型号
					product.setDsc(CommonFunction.isNotNull(newList.get(3).trim())?newList.get(3).trim():null);//产品备注
					productList.add(product);
				}
				/*employeeService.importEmpoyee();*/
				productService.importProduct(productList);
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
	/**
	 * 初始化单位类型
	 * @return
	 */
	public String queryUint(){
		queryUnit_tree();
		return "tree";
	}
	public String queryUints(){
		queryUnit_tree();
		return "tree_init";
	}
	private void queryUnit_tree() {
		List<ProductUint> proUnitList=productService.queryAllUnit();
		//初始化js树形结构
		List<JsTree> treeList=new ArrayList<JsTree>();
		for (ProductUint productUint : proUnitList) {
			JsTree root=new JsTree();
			root.setText(productUint.getName());
			root.setId(productUint.getId().toString());
			root.setParent((productUint.getPid()==null || productUint.getPid()==0)?"#":productUint.getPid().toString());
			treeList.add(root);
		}
		String treeJson=JSON.toJSONString(treeList);
		request.setAttribute("tree",StringEscapeUtils.escapeHtml4(treeJson));
	}
	/**
	 * 修改单位类型
	 * @return
	 * @throws BusinessException 
	 */
	public String preeditUnit() throws BusinessException{
		String id=request.getParameter("id");
		if(CommonFunction.isNotNull(id)){
			ProductUint pu=productService.getPuById(Long.parseLong(id));
			request.setAttribute("pu", pu);
			System.out.println( productService.getPuById(pu.getPid()));
			request.setAttribute("ppu", productService.getPuById(pu.getPid()));
		}
		return "editUnit";
	}
	/**
	 * 修改单位类型
	 * @return
	 */
	public String editUnit(){
		try {
			if(CommonFunction.isNull(pu)){
				throw new BusinessException("参数错误");
			}
			productService.editUnit(pu);
			this.message = "修改成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "editUnit";
	}
	/**
	 * 添加单位类型
	 */
	public String preAddUnit(){
		return "addUnit";
	} 
	/**
	 * 保存单位类型
	 * @return
	 */
	public String addUnit(){
		try {
			if(CommonFunction.isNull(pu)){
				throw new BusinessException("参数错误");
			}
			if(CommonFunction.isNull(pu.getPid())){
				pu.setPid(0L);
			}
			productService.saveUnit(pu);
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "addUnit";
	} 
	/**
	 * 快捷添加
	 * @return
	 */
	public String addUnitQu(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			String unitValue=request.getParameter("value");
			if (CommonFunction.isNull(unitValue)) {
				throw new BusinessException("参数错误");
			}
			List<ProductUint> list=productService.getUnitByPid(8L);
			for (ProductUint productUint : list) {
				if(productUint.getName().equals(unitValue)){
					throw new BusinessException("单位已存在");
				}
			}
			ProductUint pu=new ProductUint();
			pu.setName(unitValue);
			pu.setPid(8L);
			productService.saveUnit(pu);
			json.put("successflag", "1");
			json.put("code",0);
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
	 * 删除单位类型
	 * @return
	 */
	public String delUnit(){
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到产品id
			String id=request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			ProductUint productUnit=productService.getPuById(Long.parseLong(id));
			if(CommonFunction.isNull(productUnit)){
				throw new BusinessException("该类型已删除");
			}else{
				 List<ProductUint> list=productService.getUnitByPid(productUnit.getId());
				 if(CommonFunction.isNotNull(list)){
					 throw new BusinessException("还有子项未删除");
				 }
				productService.delUnit(productUnit);
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
	public ProductService getProductService() {
		return productService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
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
	public ProductUint getPu() {
		return pu;
	}
	public void setPu(ProductUint pu) {
		this.pu = pu;
	}
}
