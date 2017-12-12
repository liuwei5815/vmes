package com.xy.cms.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xy.cms.entity.Authaction;

/**
 * 权限控制
 * @author dinggang
 * @since 2010/04/23
 */
public class Auth_Authaction {

	public static Map<String,Authaction> actionMap = new HashMap<String,Authaction>();
	
	private static final Logger logger = Logger.getLogger(Auth_Authaction.class);
	
	private static Auth_Authaction authaction = null;
	
	private Auth_Authaction(){
		
	}
	
	public static Auth_Authaction getInstance(){
		if(authaction == null){
			authaction = new Auth_Authaction();
		}
		return authaction;
	}
	
	/**
	 * 验证用户是否有权限执�?
	 * @param roleId
	 * @return
	 */
	/*public static boolean isAllow(SessionBean bean,String action){
		List<Integer> permission = bean.getPermission();
		Authaction authAction = actionMap.get(action);
		if(authAction == null){
			return false;
		}
		if(!authAction.getControl().equals("0")){
			String str = authAction.getMenuId().toString();
			Integer menuId = new Integer(str);
			if(permission.contains(menuId)){
				return true;
			}
		}else{
			return true;
		}
		return false;
	}*/
	
	/**
	 * 获取�?��记录日志操作的Action
	 * @param action
	 * @return
	 */
	public static Authaction getLogAuthaction(String action){
		Authaction authAction = actionMap.get(action);
		if(authAction != null && authAction.getIslog()==1){
			return authAction;
		}
		return null;
	}
	
	/**
	 * 获取操作描述信息
	 * @param action	操作
	 * @return
	 */
	public static String getDes(String action){
		Authaction authAction = actionMap.get(action);
		if(authAction!= null){
			return authAction.getDes();
		}
		return "";
	}
	
	public static void destroy() {
		actionMap.clear();
		actionMap = null;
		logger.info("Authaction destroy sucessfull!");
	}
}
