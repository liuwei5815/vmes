package com.xy.cms.action.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.xy.cms.bean.AdminBean;
import com.xy.cms.common.CacheFun;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.DateUtil;
import com.xy.cms.common.Environment;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.base.BaseActionQueryPageCallBack;
import com.xy.cms.common.base.BaseQEntity;
import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.Role;
import com.xy.cms.service.AdminService;
import com.xy.cms.service.RoleService;

public class AdminAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Admin admin;
	private List<AdminBean> adminList;
	private AdminService adminService;
	private RoleService roleService;
	
	public String init() throws Exception {
		queryRole();
		return "init";
	}
	
	public String query() {
		this.queryTemplate(new BaseActionQueryPageCallBack() {
			
			@Override
			public QueryResult execute(Map pageMap) throws BusinessException {
				pageMap.put("admin", admin);
				return adminService.queryAdminPage(pageMap);
			}
		});
		adminList = new ArrayList<AdminBean>();
		if (this.list != null) {
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i);
				AdminBean ab = new AdminBean();
				ab.setId(Long.valueOf(obj[0].toString()));
				ab.setAccount(obj[1].toString());
				ab.setAddDate(DateUtil.str2Date(obj[2].toString(), "yyyy-MM-dd"));
				ab.setRoleName(obj[3].toString());
				adminList.add(ab);
			}
		} else {
			adminList = null;
		}
		return "query";
	}
	
	public String preEdit() {
		try {
			if (CommonFunction.isNull(admin)
					|| CommonFunction.isNull(admin.getId())) {
				throw new BusinessException("参数错误");
			}
			queryRole();
			admin = adminService.getAdmin(Long.valueOf(admin.getId()));
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return "edit";
	}

	public String edit() throws ParseException {
		try {
			if (admin == null || admin.getId() == null) {
				throw new BusinessException("Illegal access");
			}
			admin.setUpdateDate(new Date());
			adminService.upateAdmin(admin);
			this.message ="Admin updated";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return preEdit();
	}
	
	public String del() throws Exception {
		String adminId = request.getParameter("id");
		try {
			if (!CommonFunction.isNotNull(adminId)) {
				throw new BusinessException("admin id is null");
			}

			adminService.del(Long.valueOf(adminId));
			this.message = "Delete success";
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		this.admin = null;
		return query();
	}
	
	public String batchDel() {
		try {
			if (checks == null) {
				throw new BusinessException(
						"Please select at least one user !");
			}
			adminService.batchDel(checks);
			this.message = "Delete success";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return query();
	}

	public String preAdd() {
		queryRole();
		return "add";
	}
	
	public String add() {
		try {
			if (admin == null) {
				throw new BusinessException("Illegal access");
			}
			admin.setAddDate(new Date());
			adminService.saveAdmin(admin);
			this.admin = null;
			this.message = "创建用户名成功!";
			request.setAttribute("successflag", "1");
		} catch (BusinessException e) {
			this.message = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return preAdd();
	}
	
	public String viewImg(){
		OutputStream output = null;
		InputStream imageIn = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try{
			String imgname = request.getParameter("email");
			File image = null;
			if(StringUtils.isNotBlank(imgname)){
				String filePath = CacheFun.getConfig("admin_img_path") + File.separator + imgname+ ".jpg";
				image = new File(filePath);
				if(!image.exists()){
					image = null;
				}
			}
			if(image == null){
				image = new File(Environment.getHome() + "/image/defaultUserPic.png");
			}
			response.setContentType("image/png");
			output = response.getOutputStream();// 得到输出流 
			imageIn = new FileInputStream(image);// 文件流  
            bis = new BufferedInputStream(imageIn);// 输入缓冲流  
            bos = new BufferedOutputStream(output);// 输出缓冲流  
            byte data[] = new byte[1024];// 缓冲字节数  
            int size = 0;  
            size = bis.read(data);  
            while (size != -1) {  
                bos.write(data, 0, size);  
                size = bis.read(data);  
            }  
            bis.close();  
            bos.flush();// 清空输出缓冲流  
            bos.close();  
		}catch (IOException e){
			PrintWriter toClient;
			try {
				toClient = response.getWriter();
				response.setContentType("text/html;charset=UTF-8");
				toClient.write("Can't open this image");
				toClient.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally{
			 try {
				bos.flush();// 清空输出缓冲流
				bos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
		return null;
	}
	
	/**
	 * 查询角色信息
	 */
	public void queryRole(){
		try {
			List<Role> list = roleService.getAllRole();
			request.setAttribute("rList",list);
		} catch (BusinessException e) { 
			logger.error("查询角色信息异常:"+e.getMessage());
		}
		
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<AdminBean> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<AdminBean> adminList) {
		this.adminList = adminList;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

}
