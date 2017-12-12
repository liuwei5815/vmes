package com.xy.cms.common;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.xy.cms.entity.Admin;

public class LoginUtil {
	private static final String COOKIE_ACCOUNT="account";
	
	public static void setAdminCookie(Admin admin,HttpServletResponse response) throws UnsupportedEncodingException{
		JSONObject jsonObject  =new JSONObject();
		jsonObject.put("account",admin.getAccount());
		jsonObject.put("pwd",admin.getPwd());
		StringUtil.setCookie(response, COOKIE_ACCOUNT, jsonObject.toJSONString(), 30*60*60*24);
	}
	
	public static JSONObject getAdminCookie(HttpServletRequest request) throws UnsupportedEncodingException{
		String json = StringUtil.getCookie(request, COOKIE_ACCOUNT);
		if(StringUtils.isBlank(json)){
			return null;
		}
		return JSONObject.parseObject(json);
	}
	public static void clearAdminCookie(HttpServletResponse response) throws UnsupportedEncodingException{
	
		StringUtil.setCookie(response, COOKIE_ACCOUNT, "", -1);
	}
}
