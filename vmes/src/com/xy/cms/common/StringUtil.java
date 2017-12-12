package com.xy.cms.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StringUtil {

	private static String[] a_z = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public static String leftFill(String src, char fillChar, int strLength) {
		int strLen = src.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append(fillChar).append(src);// 左补0
				// sb.append(str).append("0");//右补0
				src = sb.toString();
				strLen = src.length();
			}
		}
		return src;
	}

	public static String getNextLetter(String currLetter){
		for(int i = 0 ; i < a_z.length ; i++){
			if(a_z[i].equals(currLetter)){
				return a_z[i+1];
			}
		}
		return "";
	}
	
	/**
	 * 设置Cookies
	 * @param request
	 * @param name
	 * @param value
	 * @param paramInt
	 * @throws UnsupportedEncodingException
	 */
	public static void setCookie(HttpServletResponse response,
			String name, String value, int maxAge)
			throws UnsupportedEncodingException {
		Cookie localCookie = new Cookie(name, URLEncoder.encode(value, "utf-8"));
		localCookie.setMaxAge(maxAge);
		localCookie.setPath("/");
		response.addCookie(localCookie);
	}
	

	/**
	 * 从Cookies中获取参�?
	 * @param paramHttpServletRequest
	 * @param paramString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getCookie(HttpServletRequest request, String name) throws UnsupportedEncodingException {
		Cookie[] cookies =  request.getCookies();
		if (cookies == null)
			return null;
		for (int i = 0; i < cookies.length; ++i){
			if (cookies[i].getName().equals(name)){
				return URLDecoder.decode(cookies[i].getValue(), "utf-8");
			}
		}
		return null;
	}
	
	/**
	 * 去掉字符串里面的html代码�?br>
	 * 要求数据要规范，比如大于小于号要配套,否则会被集体误杀�?
	 * 
	 * @param content
	 *            内容
	 * @return 去掉后的内容
	 */
	public static String stripHtml(String content) {
		// <p>段落替换为换�?
		content = content.replaceAll("<p .*?>", "/r/n");
		// <br><br/>替换为换�?
		content = content.replaceAll("<br//s*/?>", "/r/n");
		// 去掉其它�?>之间的东�?
		content = content.replaceAll("<[^>].*?>", "");
		// 还原HTML
		// content = HTMLDecoder.decode(content);
		return content;
	}
	
	/**
	 * 是否是手机号
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static void main(String[] arg) {
//		System.out.println(leftFill("2", '0', 10));
//		System.out.println(isNumeric("123.3"));
		System.out.println(isMobile("18086009097"));
	}
}
