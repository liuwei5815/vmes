package com.xy.admx.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
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
	 * @deprecated
	 */
	public static boolean isMobile(String mobile) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	
	/** 
     * 大陆号码或香港号码均可 
     */  
    public static boolean isPhoneLegal(String str) {  
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);  
    }  
  
    /** 
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
     * 此方法中前三位格式有： 
     * 13+任意数 
     * 15+除4的任意数 
     * 18+除1和4的任意数 
     * 17+除9的任意数 
     * 147 
     */  
    public static boolean isChinaPhoneLegal(String str) {  
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(14[0-9]))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    }  
  
    /** 
     * 香港手机号码8位数，5|6|8|9开头+7位任意数 
     */  
    public static boolean isHKPhoneLegal(String str) {  
        String regExp = "^(5|6|8|9)\\d{7}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    }  

    /**
     * 验证是否为email
     * @param str
     * @return
     */
	public static boolean isEmail(String str){
		String regExp = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
		Pattern p = Pattern.compile(regExp);  
	    Matcher m = p.matcher(str);  
	    return m.matches();  
	}
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	public static List<String> findAll(String str,String reg){
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = 	pattern.matcher(str);
		List<String> result = new ArrayList<String>();
		while(matcher.find()){
			result.add(matcher.group());
		}
		return result;
	}

	/**
	 * 是否由字母和数字构成
	 * 不限制长度
	 * @param str
	 * @return
	 */
	public static boolean isLetterAndNum(String str){
		String regExp = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)[0-9A-Za-z]*$";
		Pattern p = Pattern.compile(regExp);  
	    Matcher m = p.matcher(str);  
	    return m.matches();  
	}
	/**
	 * 验证字符串长度，字符串包含（汉字或英文字母），字符不能包括\:*?"<>｜
	 * @param name
	 * @param length
	 * @return
	 */
	public static boolean checkName(String str,int length){
		if(str.length()>length)
			return false;
		String reg ="^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?![u4E00-u9FA5]+$)[0-9A-Za-z\u4E00-\u9FA5]*$";
		Pattern p = Pattern.compile(reg);  
	    Matcher m = p.matcher(str);  
	    return m.matches();  
	}
	
	public static void main(String[] arg) {
//		System.out.println(leftFill("2", '0', 10));
//		System.out.println(isNumeric("123.3"));
//		System.out.println(isMobile("18086009097"));
		System.out.println(isEmail("ds@dd.com"));
		System.out.println(isLetterAndNum("ddd2ddddddddddddddddddddddddddddddddd"));
	}
}
