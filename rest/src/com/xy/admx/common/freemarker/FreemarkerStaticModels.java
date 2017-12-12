package com.xy.admx.common.freemarker;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import sun.util.logging.resources.logging;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;

public class FreemarkerStaticModels extends HashMap<Object, Object> {

	private static Logger logger = Logger.getLogger(FreemarkerStaticModels.class);
	public void setStaticModels(Properties properties) {
		if(properties!=null){
			Set<String> keys=properties.stringPropertyNames();
			for (String key : keys) {
				this.put(key, useStaticPackage(properties.getProperty(key)));
			}
		}
	}
	public static TemplateModel useStaticPackage(String packageName){
		try{
			//拿到静态Class的Model
			//对象包装(Freemarker)模板引擎在处理时，并不是直接使用我们提供的类型。它会将其转换为自己内部定义的类型，转换工作由ObjectWrapper去完成
			BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
			TemplateHashModel staticModels = wrapper.getStaticModels();
			TemplateModel fileStatics = staticModels.get(packageName);
			return fileStatics;
		}
		catch (Exception e){
			logger.error("注入 "+packageName+" 失败");
		}
		return null;
	}

}
