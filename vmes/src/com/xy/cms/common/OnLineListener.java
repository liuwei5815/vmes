package com.xy.cms.common;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;


public class OnLineListener implements HttpSessionListener {
	private static org.apache.log4j.Logger logger = Logger.getLogger(OnLineListener.class);
	
	private static ConcurrentHashMap<String, SessionBean> vec = new ConcurrentHashMap<String, SessionBean>();
	
	
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		logger.debug("session创建,sessionId:" + session.getId());
	} 

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		logger.debug("session消毁,sessionId:" + session.getId());
		SessionBean bean = (SessionBean)session.getAttribute(Constants.SESSION_BEAN);
		if(bean != null){
			vec.remove(bean.getUserName());
		}
	}
	
	/**
	 * 用户登录，添加用户信息到登录列表�?
	 * @param session
	 * @param userName
	 * @return
	 */
	public static void doLogin(String userName ,SessionBean bean){
		vec.put(userName, bean);
	}
	
	/**
	 * �?��用户是否在线
	 * @param userName
	 * @return
	 */
	public static boolean isOnLine(String userName){
		if(vec.get(userName) != null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * �?��，清除登录信�?
	 * @param userName
	 */
	public static void logOut(String userName){
		vec.remove(userName);
	}
	
	/**
	 * 获取在线用户列表
	 * @return
	 */
	public static ConcurrentHashMap<String, SessionBean> getOnLineMap(){
		return vec;
	}

	public static int getOnLineCount(){
		return vec.size();
	}
}
