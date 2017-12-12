package com.xy.cms.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.xy.cms.common.exception.BusinessException;

public class ConfigBean {
	
	private static Map<String,String> map = new HashMap<String,String>();
	
	private static final Logger logger = Logger.getLogger(ConfigBean.class);
	
	/**
	 * 初始化ConfigBean
	 * @throws BussinessException
	 */
	public static void init() throws BusinessException{
		String home = Environment.getHome();
		if ((home == null) || (home.equals(""))) {
			throw new BusinessException("home is null or blank!");
		}
		try {
			String str4 = home + "/WEB-INF/config/config.xml";
			Dom4jUtil xmlUtil = new Dom4jUtil();
			Document doc = xmlUtil.getDocument(str4);
			List<Element> tagList = doc.selectNodes("//tag");
			String name = null;
			String value = null;
			for(Element tagEle : tagList){
				name = tagEle.attributeValue("name");
				value = tagEle.attributeValue("value");
				putBeans(name, value);
			}
			logger.info("Load config.xml successful!");
		} catch (Exception localException) {
			throw new BusinessException("缓存config.xml文件中内容时发生错误!" , localException);
		}
	}

	public static String getStringValue(String key) {
		return map.get(key);
	}

	public static int getIntValue(String key) {
		if(key == null || key.equals("")){
			return -1;
		}
		return Integer.valueOf(map.get(key));
	}

	public static void putBeans(String key, String value) {
		map.put(key, value);
	}

	public static void destroy() {
		map.clear();
		map = null;
		logger.info("ConfigBean destroy sucessfull!");
	}
}