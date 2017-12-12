package com.xy.cms.common;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.xy.cms.entity.AppModular;
import com.xy.cms.entity.TableColumnType;

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
	
	//tableColumnType常量

	public static Map<Long,TableColumnType> columnTypeCache=new ConcurrentHashMap<Long,TableColumnType>();
	
	
	
	public static List<AppModular> appModularList =new CopyOnWriteArrayList<AppModular>();
	/**
	 * 系统关闭清除缓存
	 */
	public static void destroy() {
		sysConfigCache.clear();
		codeListCache.clear();
		menuCache.clear();
		codeListCache.clear();
		config.clear();
	}
}
