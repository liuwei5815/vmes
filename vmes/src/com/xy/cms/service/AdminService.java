package com.xy.cms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xy.cms.common.base.QueryResult;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.AppUser;

public interface AdminService {
	/**
	 * do login
	 * @param admin
	 * @param isCreate
	 * @return
	 * @throws BusinessException
	 */
	public Admin login(Admin admin, boolean isCreate) throws BusinessException;

	/**
	 * 分页查询用户信息
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	QueryResult queryAdminPage(Map param) throws BusinessException;
	
	/**
	 * 保存用户信息
	 * @param AdminBean
	 * @throws BusinessException
	 */
	public void saveAdmin(Admin admin) throws BusinessException;
	
	/**
	 * 删除用户信息
	 * @param adminId
	 * @throws BusinessException
	 */
	public void del(Long adminId) throws BusinessException;
	
	/**
	 * 根据ID查询用户信息
	 * @param adminId
	 * @return
	 * @throws BusinessException
	 */
	public Admin getAdmin(Long adminId) throws BusinessException;
	
	/**
	 * 更新用户信息
	 * @param admin
	 * @throws BusinessException
	 */
	public void upateAdmin(Admin admin) throws BusinessException;
	
	/**
	 * 修改密码
	 * @param adminId
	 * @param newpwd
	 * @throws BusinessException
	 */
	public void updatePwd(Long adminId, String newpwd) throws BusinessException;
	
	
	public void batchDel(String[] ids) throws BusinessException ;
	
	/**
	 * 
	 * @param adminName
	 * @return
	 * @throws BusinessException 
	 */
	public void isExistAdmin(Admin admin) throws BusinessException;
	
	/**
	 * 退出登录
	 * @param request
	 * @throws BusinessException
	 */
	public void logout(HttpServletRequest request) throws BusinessException;
	/**
	
	/**
	 * get context in session
	 * @param session HttpSession
	 * @return
	 */
	public  Admin getAdmin(HttpSession session) throws BusinessException;
	
	/**
	 * get context in session of HttpServletRequest
	 * @param req HttpServletRequest
	 * @return
	 */
	public Admin getAdmin(HttpServletRequest req) throws BusinessException;

	/**
	 * get all Admins from DB
	 * @return
	 * @throws BusinessException
	 */
	public List<Admin> getAllAdmins() throws BusinessException;

	public Admin getAdminByAssId(Long assId,Long schId) throws BusinessException;

	/**
	 * 通过empId查询admin
	 * @param empId 员工id
	 * @return
	 * */
	public Admin getAdminByEmpId(Long empId) throws BusinessException;
	
	/**
	 * 更新admin以及appUser
	 * @param admin
	 * @param appUser
	 * */
	public void updateAdminAppUser(Admin admin,AppUser appUser);
	
	/**
	 * 添加admin
	 * */
	public void addAdmin(Admin admin)throws BusinessException;
}
