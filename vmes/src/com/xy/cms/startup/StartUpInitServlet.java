package com.xy.cms.startup;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.xy.cms.common.Auth_Authaction;
import com.xy.cms.common.CachePool;
import com.xy.cms.common.ConfigBean;
import com.xy.cms.common.Environment;
import com.xy.cms.common.MD5;
import com.schedule.init.LoadSchedule;

/**
 * 服务器起动时加载
 * @author dinggang
 */
 public class StartUpInitServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	 
   static final long serialVersionUID = 22323322331L;
   
   private Logger logger = Logger.getLogger(StartUpInitServlet.class);
   
   private ServletConfig servletConfig = null;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public StartUpInitServlet() {
		super();
	}   
	public static void main(String[] args) {
		System.out.println(MD5.MD5("123"));
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			
			servletConfig = config;
			//配置环境变量
			initPath();
			//缓存/WEB-INF/conf/config.xml中内容
			ConfigBean.init();
			//initFile();
			new LoadSchedule(Environment.getHome() + "/WEB-INF/config/asynchronized.xml");
			
			//new Thread(new AutoDispatchTaskThread()).start();
			logger.info("vmes 1.0V startup successful !");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 初始化log4j and homepath
	 */
	private void initPath(){
		Environment.setHome(getServletContext().getRealPath("/"));
		Environment.setContext(getServletContext().getContextPath() + "/");
		Environment.setWebAppsHome(new File(getServletContext().getRealPath("")).getParent());
		logger.info(" webapps path:"+Environment.getWebAppsHome());
	}

	
	@Override
	public void destroy() {
		super.destroy();
		ConfigBean.destroy();
		Auth_Authaction.destroy();
		Environment.desctroy();
		LoadSchedule.cleanAllScheduleTask();
	}   
}