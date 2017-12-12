package com.xy.admx.common;

import java.util.Map;

import com.xy.cms.entity.CodeList;



public class CacheFun {


	/**
	 * 获得指定类型的代码在数据库中对应的文本
	 * 
	 * @param codeType
	 *            java.lang.String - 代码类型
	 * @param code
	 *            java.lang.String - 代码值
	 * @return String 代码对应的文本说明
	 */
	public static String getCodeText(String codeType, String code) {
	
		if (codeType == null || code == null)
			return null;
		Map map = (Map) CachePool.codeListCache.get(codeType);
		if (map == null)
			return null;
		return (String) map.get(code);
	}
	
	/**
	 * 获取content对应的值
	 * @param codeType
	 * @param content
	 * @return
	 */
	public static String getCodeValue(String codeType, String content) {
		if (codeType == null || content == null)
			return null;
		Map map = (Map) CachePool.CodeListCache2.get(codeType);
		if (map == null)
			return null;
		return (String) map.get(content);
	}
	
	public static CodeList getCodeEntity(String codeType,String code){
		if (codeType == null || code == null)
			return null;
		String content = getCodeText(codeType, code);
		CodeList entity = new CodeList();
		entity.setCode(code);
//		entity.setCodeDesc(content);
		entity.setContents(content);
		entity.setTypeId(codeType);
		return entity;
	}

	/**
	 * 
	 * 功能：获得代码表中代码及其初始值，并组装成HtmlOption数组
	 * 
	 * @param codeType
	 *            java.lang.String 代码表类型
	 * @return HtmlOption[] HtmlOption数组
	 */
	public static Map getCodeList(String codeType) {
		return (Map) CachePool.codeListCache.get(codeType);
	}
	

	/*public static Menu getMenu(String id) {
		return (Menu) CachePool.menuCache.get(id);
	}*/
	
	/**
	 * 动态更新缓存codelist
	 * @param entity
	 */
	public static void addCodeList(CodeList entity){
		Map codeMap = (Map)CachePool.codeListCache.get(entity.getTypeId());
		codeMap.put(entity.getCode(), entity.getContents());
	}
	
	/**
	 * 动态更新缓存codelist
	 * @param entity
	 */
	public static void updateCodeList(CodeList entity){
		Map codeMap = (Map)CachePool.codeListCache.get(entity.getTypeId());
		codeMap.remove(entity.getCode());
		codeMap.put(entity.getCode(), entity.getContents());
	}
	
	/**
	 * 动态更新缓存codelist
	 * @param entity
	 */
	public static void removeCodeList(CodeList entity){
		Map codeMap = (Map)CachePool.codeListCache.get(entity.getTypeId());
		codeMap.remove(entity.getCode());
	}
	
	public static void removeCodeList(String codeType,String code){
		Map codeMap = (Map)CachePool.codeListCache.get(codeType);
		codeMap.remove(code);
	}
	
	/**
	 * 获取系统配置
	 * @param confName
	 * @return
	 */
	public static String getConfig(String confName){
		return CachePool.config.get(confName);
	}
	
	/**
	 * 更新缓存系统配置
	 * @param newvalue
	 */
	public static void updateConfig(String confName,String confValue){
		CachePool.config.put(confName, confValue);
	}
	
	
	/**
	 * 得到图片文件夹路径（媒体关注、工程聚焦、前沿视窗）
	 * @param type 1:媒体关注 2:工程聚焦  3:前沿视窗 4:首页缩略图
	 */
	public static String getPicParentFilePath(String type){
		StringBuffer s = new StringBuffer(CachePool.config.get("picPath"));
		if(type.equals("1")){
			s.append(CachePool.config.get("mediaPicPath"));
		}
		else if(type.equals("2")){
			s.append(CachePool.config.get("projectPicPath"));
		}
		else if(type.equals("3")){
			s.append(CachePool.config.get("frontPicPath"));
		}	
		else if(type.equals("4")){
			s.append(CachePool.config.get("pagePicPath"));
		}	
		else if(type.equals("5")){
			s.append(CachePool.config.get("culturePicPath"));
		}	
		return s.toString();
	}
	

	
	
	public static String stripHtml(String content) {
		// <p>段落替换为换行
		content = content.replaceAll("<p .*?>", "");
		// <br><br/>替换为换行
		content = content.replaceAll("<br//s*/?>", "");
		// 去掉其它的<>之间的东西
		content = content.replaceAll("<[^>].*?>", "");
		content = content.replaceAll("&nbsp;", "");
		
		// 还原HTML
		// content = HTMLDecoder.decode(content);
		if(content.length()>50){
			content =content.substring(0,50);
		}
		return content;
	}
	
	public static String replaceJs(String content){
		return content.replaceAll("\n|\r", "");
	}
}
