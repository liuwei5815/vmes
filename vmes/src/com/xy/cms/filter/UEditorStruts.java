package com.xy.cms.filter;
/**
 * 自定义struts2的拦截器 解决ueditor上传图片报'未找到上传文件'
 * 
 */


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;
public class UEditorStruts extends StrutsPrepareAndExecuteFilter {
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String url = request.getRequestURI(); 
		if (!url.endsWith("pwd.jsp")&&url.contains("/jsp/")) { 
			chain.doFilter(req, res); 
		}else{ 
	
			super.doFilter(req, res, chain); 
		}

	}
}
