package com.xy.cms.common;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xy.cms.common.exception.NotEnoughRightsException;
import com.xy.cms.entity.Authaction;
import com.xy.cms.entity.SysLog;
import com.xy.cms.service.IDAO;


/**
 * 管理员后台操作拦截器
 * 该方法用于 方法action调用的拦截。 主要用于日志的记录，调用方法的花费时间
 */
public class AdminAuthorizationInterceptor extends BaseInterceptor {

	@Override
	public boolean va() throws IOException {
		SessionBean sessionBean = (SessionBean)ActionContext.getContext().getSession().get(Constants.SESSION_BEAN);
		if(sessionBean == null){
			HttpServletRequest req=ServletActionContext.getRequest();
			req.setAttribute("msg","对不起,您还没有登录!");
			ServletActionContext.getResponse().sendRedirect(req.getContextPath()+"/login.jsp");
			return false;
		}
		return true;
		
	}
	
	

}
