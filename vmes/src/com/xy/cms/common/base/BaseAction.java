package com.xy.cms.common.base;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.service.IWebPageItemsService;
import com.xy.cms.service.IndexService;


public class BaseAction extends ActionSupport implements
		ServletRequestAware,ServletResponseAware, SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1311L;
	// 当前页数
	public String currPage;
	// 总页数
	public String totalPage;
	// 跳转的页面
	public String goPage;
	// 总记录数
	public String totalCount;
	
	public String perPageRows;
	
	public List list;

	public String message = "";
	
	private IWebPageItemsService webPageItemsService;
	
	private IndexService indexService;
	
	protected static Logger logger = Logger.getLogger(BaseAction.class.getName());
	protected Map<String, Object> pageMap;// 用于接收表单数据
	public String[] checks;// 用于批量删除
	public Map session;
	public HttpServletRequest request;
	
	public HttpServletResponse response;

	public void setSession(Map session) {
		this.session = session;
	}

	public String init() throws Exception {
		return SUCCESS;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 得到session
	 * 
	 * @return
	 */
	public Map getSession() {
		return ActionContext.getContext().getSession();
	}

	/**
	 * 从session中取值
	 * 
	 * @param key
	 * @return
	 */
	public Object getSessionValue(String key) {
		return ActionContext.getContext().getSession().get(key);
	}

	/**
	 * set Session 中内容
	 * 
	 * @param key
	 * @param value
	 */
	public void putSessionValue(String key, Object value) {
		ActionContext.getContext().getSession().put(key, value);
	}

	/**
	 * 得到页面传的值参数
	 * 
	 * @param key
	 * @return
	 */
	public Object getPageValue(String key) {
		if (pageMap != null) {
			return pageMap.get(key);
		}
		return null;
	}

	/**
	 * 设置页面的值参数
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public void setPageValue(String key, Object value) {
		if (pageMap == null) {
			pageMap = new HashMap();
		}
		pageMap.put(key, value);
	}

	/**
	 * pageMap 该值一般是数组
	 * 
	 * @param key
	 * @return
	 */
	public String getStringValueFromMap(String key) {
		if (pageMap == null) {
			return null;
		}

		Object o = pageMap.get(key);
		if (o instanceof String[]) {
			return ((String[]) pageMap.get(key))[0];
		} else if (o instanceof String) {
			return (String) o;
		} else {
			if (o != null) {
				return o.toString();
			} else {
				return null;
			}
		}
	}
	public void ajaxTemplate(BaseActionAjaxTemplate ajaxTemplate){
		PrintWriter printWriter = null;
		Map map = new HashMap();
		try {
			printWriter=response.getWriter();
			Object o = ajaxTemplate.execute();
			map.put("code", 0);
			if(o!=null)
			map.put("body", o);
		}catch (BusinessException e) {
			map.put("code", 1);
			map.put("msg", e.getMessage());
			logger.error(e.getMessage(),e);
		}catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器出现异常");
			logger.error(e.getMessage(), e);
		} 
		if (printWriter != null) {
			Gson gson = new Gson();
			String json = gson.toJson(map);
			printWriter.write(json);
			printWriter.close();
		}
	}
	public void queryTemplate(BaseActionQueryPageCallBack callBack){
		try{
			BaseQEntity baseQEntity = new BaseQEntity();
			if (this.getCurrPage() == null || "".equals(this.getCurrPage().trim()) || "0".equals(this.getCurrPage())) {
				this.setCurrPage("1");
			}
			baseQEntity.setCurrPage(Integer.parseInt(this.getCurrPage().trim()));
			if (this.getPerPageRows() != null&& !"".equals(this.getPerPageRows().trim())) {
				baseQEntity.setPerPageRows(Integer.parseInt(this.getPerPageRows().trim()));
			}
			pageMap = new HashMap<String, Object>();
			pageMap.put("qEntity",baseQEntity);
			QueryResult rs= callBack.execute(pageMap);
			if (rs != null) {
				this.setCurrPage(rs.getCurrentPage());
				this.setTotalCount(rs.getTotalCount());
				this.setTotalPage(rs.getTotalPage());
				this.list = rs.getList();
			} else {
				this.setCurrPage("0");
				this.setTotalPage("0");
				this.setTotalCount("0");
				this.list = null;
			}
		}
		catch (BusinessException e) {
			this.message=e.getMessage();
			logger.error(e.getMessage(),e);
		}
	}
	public Map<String, Object> getPageMap() {
		return pageMap;
	}

	public void setPageMap(Map<String, Object> pageMap) {
		this.pageMap = pageMap;
	}

	public String[] getChecks() {
		return checks;
	}

	public void setChecks(String[] checks) {
		this.checks = checks;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getCurrPage() {
		return currPage;
	}

	public void setCurrPage(String currPage) {
		this.currPage = currPage;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public String getGoPage() {
		return goPage;
	}

	public void setGoPage(String goPage) {
		this.goPage = goPage;
	}

	public String getPerPageRows() {
		return perPageRows;
	}

	public void setPerPageRows(String perPageRows) {
		this.perPageRows = perPageRows;
	}

	public IWebPageItemsService getWebPageItemsService() {
		return webPageItemsService;
	}

	public void setWebPageItemsService(IWebPageItemsService webPageItemsService) {
		this.webPageItemsService = webPageItemsService;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public IndexService getIndexService() {
		return indexService;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	
	
}
