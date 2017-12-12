package com.xy.admx.common;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;



public class CachePool {
	// 系统的长廊的配置
	public static Properties sysConfigCache = new Properties();

	public static ConcurrentHashMap<String, String> config = new ConcurrentHashMap<String, String>();

	// codelist的常�?
	public static Map codeListCache = new ConcurrentHashMap();
	
	// codelist的常�?
	public static Map CodeListCache2 = new ConcurrentHashMap();

	// 登录平台的菜�?
	public static Map menuCache = new ConcurrentHashMap();
	
	//图片验证码
	public static Map<String,Object> picVertify = new ConcurrentHashMap<>();
	
	/**
	 * 系统关闭清除缓存
	 */
	public static void destroy() {
		sysConfigCache.clear();
		codeListCache.clear();
		menuCache.clear();
		codeListCache.clear();
		config.clear();
		picVertify.clear();
	}
}
