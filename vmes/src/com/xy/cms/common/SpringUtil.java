package com.xy.cms.common;

import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringUtil {
	public static Object getSpringBean(String beanId){
		ServletContext context = ServletActionContext.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		Object obj = wac.getBean(beanId);
		return obj;
	}
	public static Connection getConnectionFromSpring(String beanId) throws Exception{
		DataSource dbSource = (DataSource) getSpringBean(beanId); //配置文件里的beanid
		Connection con = dbSource.getConnection();
		return con;
	}
}
