package com.xy.admx.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xy.admx.common.ServiceUtil;
import com.xy.admx.service.ICacheService;

public class CommonFilter implements Filter {

	private static Logger logger = Logger.getLogger(CommonFilter.class);

	private static String targetEncoding = "UTF-8";
	@Resource
	private ICacheService cacheService;

	public void destroy() {
		cacheService.destroy();
		logger.debug("Service Stopped!");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		request.setCharacterEncoding(targetEncoding);
		response.setCharacterEncoding(targetEncoding);
		HttpServletRequest httpRequest = (javax.servlet.http.HttpServletRequest) request;
		HttpServletResponse httpResponse = (javax.servlet.http.HttpServletResponse) response;
		logger.info("---dofilter");
		chain.doFilter(request, response);
		
	}
	

	/***************************************************************************
	 * 初始化过滤器.
	 * 
	 * @param config
	 * @throws javax.servlet.ServletException
	 * 
	 **************************************************************************/
	public void init(FilterConfig config) throws ServletException {
		targetEncoding = config.getInitParameter("encoding");
		try {
			cacheService.init();
			ApplicationContext act = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
			ServiceUtil.setAct(act);
		} catch (Exception e) {
			logger.error("缓存数据出现异常 Error:"+e.getMessage());
		}
		logger.debug("targetEncode=" + targetEncoding);
	
	}

	
}
