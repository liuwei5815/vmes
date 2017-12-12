package com.xy.admx.common.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * 设置项目绝对路径
 * @author xiaojun yang
 *
 */
public class XyFreeMarkerView extends FreeMarkerView{
		private static final String BASE_PATH = "base";	
		private static final String BASE_ADMIN_PATH="baseAdmin";

		 @Override
		 protected void exposeHelpers(Map<String, Object> model,
		            HttpServletRequest request) throws Exception {
		      model.put(BASE_PATH, request.getContextPath());
		      model.put(BASE_ADMIN_PATH, request.getContextPath()+"/admin");
		      super.exposeHelpers(model, request);
		 }
}
