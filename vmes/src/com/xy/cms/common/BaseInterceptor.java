package com.xy.cms.common;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xy.cms.entity.Authaction;
import com.xy.cms.entity.SysLog;
import com.xy.cms.service.IDAO;

public abstract class BaseInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 12281L;
	private final Logger logger = Logger.getLogger(BaseInterceptor.class);
	private List excludeUrls;

	/**
	 * 业务日志记录到数据库
	 */
	private IDAO dao;
	/**
	 * 判断该url是不是不需要过滤
	 * @param url
	 * @return
	 */
	protected boolean exclude(String url) {
		if(excludeUrls!=null && excludeUrls.size()>0){
			if(excludeUrls.contains(url)){
				return true;
			}
		}
		return false;
	}
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		HttpServletRequest req=ServletActionContext.getRequest();

		String url = req.getRequestURI();// "/vas/login!init.action";
		String ip=req.getRemoteAddr();
		url = url.substring(0, url.indexOf("."));// /vas/login!init
		url = url.substring(url.lastIndexOf("/"));
		url = url.replace("/", "");// login!init
		
		if(!exclude(url)){
			if(!va()){
				return null;
			}
		}
		String value = null;
		long start = System.currentTimeMillis();
		value = invocation.invoke();
		Map sessionMap = invocation.getInvocationContext().getSession();
		logger.debug(url + " 操作花费时间=" + (System.currentTimeMillis() - start));
		saveLog(ip,url,sessionMap);
		return value;
		
	}
	public abstract boolean va() throws IOException;
	
	
	public List getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(String excludeUrls) {
		this.excludeUrls =	Arrays.asList(excludeUrls.split(","));
	}
	public IDAO getDao() {
		return dao;
	}
	public void setDao(IDAO dao) {
		this.dao = dao;
	}
	/**
	 * 该方法用于记录到数据库操作
	 */

	public void saveLog(String ip,String url,Map sessionMap) {
		try {
			SessionBean bean = (SessionBean) sessionMap.get(Constants.SESSION_BEAN);
			if(bean != null){
				SysLog log = new SysLog();
				log.setAdminId(Long.valueOf(bean.getUserId()));
				log.setUserAccount(bean.getAdmin().getAccount());
				Authaction auth = Auth_Authaction.getLogAuthaction(url);
				if (auth != null) {
					log.setContent(auth.getDes());
				}
				log.setUrl(url);
				log.setTime(new Date());
				log.setIp(ip);
				dao.save(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
