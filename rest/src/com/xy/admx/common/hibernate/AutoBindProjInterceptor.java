package com.xy.admx.common.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;

public class AutoBindProjInterceptor extends EmptyInterceptor {
	
	private static Logger logger = Logger.getLogger(AutoBindProjInterceptor.class);
	
	@Override
	public String onPrepareStatement(String sql) {
		if(sql.indexOf("{projid}") > -1 && ContextHolder.getProjId() != null){
			logger.info("old sql:" + sql);
			sql = sql.replace("{projid}", ContextHolder.getProjId().toString());
			logger.info(sql);
		}
		return super.onPrepareStatement(sql);
	}
}
