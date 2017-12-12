package com.xy.admx.common;

import org.springframework.ui.ModelMap;

public class ModelUtil {
	private final static Integer SUCCESS_CODE=1;
	private final static Integer ERROR_CODE=0;
	private final static String CODE_KEY="code";
	private final static String MSG_KEY="msg";
	public static void success(ModelMap model,String msg){
		success(model);
		model.put(MSG_KEY, msg);
	}
	public static void success(ModelMap model){
		model.put(CODE_KEY, SUCCESS_CODE);
	}
	public static void error(ModelMap model,String msg){
		error(model);
		model.put(MSG_KEY, msg);
	}
	public static void error(ModelMap model){
		model.put(CODE_KEY, ERROR_CODE);
	}
}
