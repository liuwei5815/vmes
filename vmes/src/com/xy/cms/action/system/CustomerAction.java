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

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.FileUtil;
import com.xy.cms.common.ImportExecl;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Customer;
import com.xy.cms.entity.Department;
import com.xy.cms.entity.Eqiupment;
import com.xy.cms.entity.EqiupmentType;
import com.xy.cms.entity.Product;
import com.xy.cms.service.CustomerService;


public class CustomerAction extends BaseAction {
	private CustomerService customerService;
	private Customer customer;
	private File file;
	private String fileFileName;
	
	public String init(){
		
		return "init";
	}
	public String query() throws Exception{
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				String cusName = request.getParameter("cusName");
				pageMap.put("cusName", cusName);
				return customerService.queryCustomerPage(pageMap);
			}
		});
		return "list";
	}
	public String preAdd(){
		return "add";
	}
	public String add(){
		try {
			if(CommonFunction.isNull(customer)){
				throw new BusinessException("参数错误");
			}
			customer.setAddDate(new Timestamp(new Date().getTime()));
			customer.setState(0);
			customerService.saveCustomer(customer);
			this.message = "添加成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "add";
	}
	public String preEdit(){
		String id=request.getParameter("id");
		try {
			if(CommonFunction.isNull(id)){
				throw new BusinessException("参数错误");
			}
			request.setAttribute("customer", customerService.getCustomerById(Long.parseLong(id)));
		} catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
		return "edit";
	}
	public String edit(){
		try {
			if(CommonFunction.isNull(customer)){
				throw new BusinessException("参数错误");
			}
			customerService.upateCustomer(customer);
			this.message = "修改成功";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "edit";
	}
	public String del() throws Exception { 
		PrintWriter out = null;
		Map<String,Object> json = new HashMap<String, Object>();
		try {
			out =response.getWriter();	
			//得到id
			String id=request.getParameter("id");
			if (CommonFunction.isNull(id)) {
				throw new BusinessException("参数错误");
			}
			Customer cus=customerService.getCustomerById(Long.parseLong(id));
			if(CommonFunction.isNull(cus)){
				throw new BusinessException("该客户已删除");
			}else{
				customerService.del(cus);
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
	 * 批量导入客户信息
	 * @return
	 */
	
	public String preImportCustomer(){
		return "import";
	}
	/**
	 * 导入客户信息
	 * @return
	 */
	public String importCustomer(){
		try {
			String path = ServletActionContext.getServletContext().getRealPath("/excels");
			File upload = new File(path+File.separator+fileFileName);
			FileUtil.copy(file, upload);
			ImportExecl poi = new ImportExecl(); 
			List<List<String>> list = poi.read(path+File.separator+fileFileName);
			if (list != null) {
				List<Customer> customerList = new ArrayList<Customer>();
				for(int i=1;i<list.size();i++){
					List<String> newList = list.get(i);
					Customer customer = new Customer();
					customer.setCustomerCode(CommonFunction.isNotNull(newList.get(0).trim())?newList.get(0).trim():null); //客户编号
					customer.setName(CommonFunction.isNotNull(newList.get(1).trim())?newList.get(1).trim():null);//客户姓名
					customer.setContact(CommonFunction.isNotNull(newList.get(2).trim())?newList.get(2).trim():null);//客户地址
					customer.setTel(CommonFunction.isNotNull(newList.get(3).trim())?newList.get(3).trim():null);//客户联系方式
					customer.setAddDate(new Timestamp(new Date().getTime()));//导入时间
					customer.setState(0);
					customerList.add(customer);
				}
				/*employeeService.importEmpoyee();*/
				/*productService.importProduct(productList);*/
				customerService.importCustomer(customerList);
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
	
	public CustomerService getCustomerService() {
		return customerService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
