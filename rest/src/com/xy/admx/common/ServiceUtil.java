package com.xy.admx.common;

import org.springframework.context.ApplicationContext;

public class ServiceUtil {

	private static ApplicationContext act = null;


	public static ApplicationContext getAct() {
		return act;
	}

	public static void setAct(ApplicationContext act) {
		ServiceUtil.act = act;
	}

	public static Object getService(String beanName) {
		return act.getBean(beanName);
	}
}
