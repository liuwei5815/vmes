package com.xy.cms.service;

import java.util.Map;


/**
 * 下拉框
 * 
 * @author Administrator
 */
public interface IWebPageItemsService {
	
	/**
	 * 检查用户名是否存在
	 * @param userName
	 * @return
	 */
	public boolean isExistUser(String userName);
	

	/**
	 * 获得常量名称
	 * @param typeID
	 * @param code
	 * @return
	 */
	public String getCodeName(String typeID,String code);
	
	/**
	 * 根据code type 获取code list
	 * @param type
	 * @return
	 */
	public Map getCodeList(String type) ;
	
	/**
	 * 根据身份证号算出年龄
	 * @param idCard
	 * @return
	 */
	public int calAgeByIdCard(String idCard);
	

	
}
