package com.xy.cms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig; 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xy.cms.common.Constants;
import com.xy.cms.common.ServiceUtil;
import com.xy.cms.service.ICacheService;

public class CommonFilter implements Filter {

	private static Logger logger = Logger.getLogger(CommonFilter.class.getName());

	private static String targetEncoding = "UTF-8";

	private ICacheService cacheService;



	public void destroy() {
		logger.debug("Service Stopped!");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		request.setCharacterEncoding(targetEncoding);
		response.setCharacterEncoding(targetEncoding);

		HttpServletRequest httpRequest = (javax.servlet.http.HttpServletRequest) request;
		HttpServletResponse httpResponse = (javax.servlet.http.HttpServletResponse) response;
		String uri = httpRequest.getRequestURI();
		if(!uri.endsWith("/") && !uri.endsWith("index.action") &&!uri.endsWith("login.action")){
			if (!checkSession(httpRequest, httpResponse)) {
				((HttpServletResponse) response).sendRedirect(((HttpServletRequest)request).getContextPath() + "/login.jsp");
				return;
			}
		}

		chain.doFilter(request, response);
	}




	public boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession httpSession = request.getSession(false);
		String uri = request.getRequestURI();
		if (uri.indexOf("login") > 0 || uri.indexOf("index.jsp") > 0) {
			return true;
		}
		if (httpSession == null || httpSession.getAttribute(Constants.SESSION_BEAN) == null) {
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * 初始化过滤器.
	 * 
	 * @param config
	 * @throws javax.servlet.ServletException
	 * 
	 **************************************************************************/
	public void init(FilterConfig config) throws ServletException {
		try {
			cacheService.init();
			ApplicationContext act = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
			ServiceUtil.setAct(act);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.debug("缓存初始化失败");
		}

		targetEncoding = config.getInitParameter("encoding");
		logger.debug("targetEncode=" + targetEncoding);
	}

	public ICacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(ICacheService cacheService) {
		this.cacheService = cacheService;
	}

}
