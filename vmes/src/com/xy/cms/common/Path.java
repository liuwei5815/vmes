package com.xy.cms.common;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Path {

	private Path(){
	}
	private static Path instance = null;

	public static Path getInstance(){
		if(instance == null){
			instance = new Path();
		}
		return instance;
	}
	
	
	private static String $proj = null;
	private static String $webinf = null;
	private static String $classes = null;
	
	public String getClassesPath(){
		if($classes == null){
			$classes = this.getClass().getResource("").getPath();
			$classes = $classes.substring(0, $classes.lastIndexOf("/classes")) + "/classes";
		}
		return $classes;
	}
	
	
	public String getWEBINF(){
		if($webinf == null){
			$webinf = this.getClass().getResource("").getPath();
			$webinf = $webinf.substring(0, $webinf.lastIndexOf("/classes"));
		}
		return $webinf;
	}

	public String getProjectPath(){
		if($proj == null){
			$proj = this.getClass().getResource("").getPath();
			$proj = $proj.substring(0, $proj.lastIndexOf("/WEB-INF"));
		}
		return $proj;
	}
	
	public String getFileUploadPath(String model){
		return this.getPathFromXML($fileUploadSign, model);
	}
	
	public String getFileDownloadPath(String model){
		return this.getPathFromXML($fileDownloadSign, model);
	}
	
	public String getPathFromXML(String sign, String model){
		String result = "";
		try{
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File(this.getWEBINF() + $xmlFile));
			Element root = doc.getRootElement();
			
			Element node = null;
			Iterator<Element> itor = root.elementIterator();
			while(itor.hasNext()){
				node = itor.next();
				
				if(sign.equals(node.getName())){
					result = node.element(model).elementText($valueName);
					break;
				}
			}
		}catch(Exception ex){
			result = this.getDefaultPathForXML(sign, model);
			ex.printStackTrace();
		}
		return result;
	}
	private String getDefaultPathForXML(String sign, String model){
		String defaultPath = "";
		if($fileUploadSign.equals(sign)){
			if("batchCustomized".equals(model))
				defaultPath = "/upload/customized/xls/";
		}
		if($fileDownloadSign.equals(sign)){
			if("batchCustomized".equals(model))
				defaultPath = "/download/customized/report/";
			if("batchCustomizedTemplate".equals(model))
				defaultPath = "/download/template/鎵归噺璁㈣喘妯℃�?xls";
		}
		return defaultPath;
	}
	
	
	private static final String $xmlFile = "/config/pathConfg.xml";
	private static final String $fileUploadSign = "file-upload";
	private static final String $fileDownloadSign = "file-download";
	private static final String $valueName = "path";
}
