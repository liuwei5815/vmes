package com.xy.cms.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xy.cms.bean.MenuBean;
import com.xy.cms.common.CommonFunction;
import com.xy.cms.common.Constants;
import com.xy.cms.common.LoginUtil;
import com.xy.cms.common.SessionBean;
import com.xy.cms.common.base.BaseAction;
import com.xy.cms.common.exception.BusinessException;
import com.xy.cms.entity.Admin;
import com.xy.cms.entity.Employee;
import com.xy.cms.entity.License;
import com.xy.cms.service.LoginService;

/**
 * @author xiaojun
 *
 */
public class IndexAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;

	public String execute(){
		return SUCCESS;
	}



	
}
