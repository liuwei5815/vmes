package com.xy.cms.common.export;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.ServletContextResource;

import de.innosystec.unrar.rarfile.EndArcHeader;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * Configuration实例，默认情况下一个应用只用有一个Configuration实例
 * @author xiaojun
 *
 */
public class FreemarkerConfig{
	
	private static final String UTF_8="utf-8";
	private Configuration configuration;
	private String encoding;
	private String templateLoaderPath;

	
	public Template getTemplate(String path){
		try{
			loadConfiguration();
			return configuration.getTemplate(path);
		}catch(Exception e){
			throw new IllegalStateException("not find file path:"+path,e);
		}
	}
	
	private void loadConfiguration(){
		if(configuration==null){
			synchronized (this) {
				if(configuration==null){
					configuration = new Configuration();
					configuration.setDefaultEncoding(StringUtils.isNotBlank(getEncoding())?getEncoding():UTF_8);
					configuration.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(),getTemplateLoaderPath());
				}
			}
		} 
	}
	
	public static void main(String[] args) {
		ApplicationContext applicationContext =new ClassPathXmlApplicationContext("applicationContext.xml");
		FreemarkerConfig config = (FreemarkerConfig) applicationContext.getBean("freemarkerConfig");
		Template template
		 = config.getTemplate("abc.txt");

	}
	
	
	
	

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getTemplateLoaderPath() {
		return templateLoaderPath;
	}

	public void setTemplateLoaderPath(String templateLoaderPath) {
		this.templateLoaderPath = templateLoaderPath;
	}

	
	
	
}
