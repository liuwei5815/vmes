package com.xy.admx.common;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


/***
 * 
 * @author xiaojun
 *
 */
public class RequestUtil {
	private static String ACCEPT="Accept";
	private static String AJAX_PARAM="_ajax";
	public static final Long MIN_FILE_SIZE=1024*1024*2l;
	/**
	 * 判断请求是否是ajax请求
	 * ajax请求分为两种，上传时用from提交控制回调通过ajax_param来判断
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request){
		String accept = request.getHeader(ACCEPT);
		String ajaxParam = request.getParameter(AJAX_PARAM);
		return containsApplication(accept) || (StringUtils.isNotBlank(ajaxParam) && ajaxParam.equals("true"));
	}


	public static String getRequetBody(HttpServletRequest request) throws IOException{
		StringWriter writer = new StringWriter();
		FileCopyUtils.copy(request.getReader(),writer);
		return writer.toString();
	}
	private static boolean containsApplication(String accept){
		return (StringUtils.isNotBlank(accept) && accept.contains("application/json"));
	}
	/** 
	 * MultipartFile 转换成File 
	 *  
	 * @param multfile 原文件类型 
	 * @return File 
	 * @throws IOException 
	 */  
	public static  File multipartToFile(MultipartFile multfile) throws IOException {  
		CommonsMultipartFile cf = (CommonsMultipartFile)multfile;   
		//这个myfile是MultipartFile的  
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();  
		File file = fi.getStoreLocation();  
		//手动创建临时文件  
		if(file.length() < MIN_FILE_SIZE){  
			File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +   
					file.getName());  
			multfile.transferTo(tmpFile);  
			return tmpFile;  
		}  
		return file;  
	}  

}
