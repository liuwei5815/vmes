package com.xy.cms.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.xy.cms.common.exception.BusinessException;
import com.opensymphony.xwork2.ActionContext;

public class CommonFunction {
	private static Logger logger =Logger.getLogger(CommonFunction.class);
	public static String getPageRows() {
//		Map session = ActionContext.getContext().getSession();
//		String height = (String) session.get("screenHeight");
//		String width = (String) session.get("screenWidth");
//		String rows = "10";
//		return rows;
		Map session = ActionContext.getContext().getSession();
		String height = (String)session.get("screenHeight");
		String width = (String)session.get("screenWidth");
		String rows="2";
		
		if ((width != null && width.equals(Constants.SCREEN_WIDTH_1024)) && (height!=null && height.equals(Constants.SCREEN_HEIGHT_768))){
			rows=Constants.PAGE_PER_RECORD10;
		}else if ((width !=null && width.equals(Constants.SCREEN_WIDTH_1280)) && (height!=null && height.equals(Constants.SCREEN_HEIGHT_800))){
			rows=Constants.PAGE_PER_RECORD11;
		}else if ((width !=null && width.equals(Constants.SCREEN_WIDTH_800)) && (height!=null && height.equals(Constants.SCREEN_HEIGHT_600))){
			rows=Constants.PAGE_PER_RECORD5;
		}else if ((width !=null && width.equals(Constants.SCREEN_WIDTH_960)) && (height!=null && height.equals(Constants.SCREEN_HEIGHT_600))){
			rows=Constants.PAGE_PER_RECORD6;
		}else if ((width !=null && width.equals(Constants.SCREEN_WIDTH_1088)) && (height!=null && height.equals(Constants.SCREEN_HEIGHT_612))){
			rows=Constants.PAGE_PER_RECORD6;
		}else if ((width !=null && width.equals(Constants.SCREEN_WIDTH_1152)) && (height!=null && height.equals(Constants.SCREEN_HEIGHT_864))){
			rows=Constants.PAGE_PER_RECORD14;
		}else if ((width !=null && width.equals(Constants.SCREEN_WIDTH_1280)) && (height!=null && height.equals(Constants.SCREEN_HEIGHT_720))){
			rows=Constants.PAGE_PER_RECORD8;
		}else if ((width !=null && width.equals(Constants.SCREEN_WIDTH_1280)) && (height!=null && height.equals(Constants.SCREEN_HEIGHT_768))){
			rows=Constants.PAGE_PER_RECORD10;
		}else if ((width !=null && width.equals(Constants.SCREEN_WIDTH_1280)) && (height!=null && height.equals(Constants.SCREEN_HEIGHT_960))){
			rows=Constants.PAGE_PER_RECORD18;
		}else if ((width !=null && width.equals(Constants.SCREEN_WIDTH_1280)) && (height!=null && height.equals(Constants.SCREEN_HEIGHT_1024))){
			rows=Constants.PAGE_PER_RECORD20;
		}else{
			rows=Constants.PAGE_PER_RECORD10;
		}
		return rows;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNotNull(Object o) {
		if (o == null) {
			return false;
		} else if (o instanceof String && "".equals(((String) o).trim())) {
			return false;
		} 
		else if(o instanceof List && ((List)o).size()==0){
			return false;
		}
		else {
			return true;
		}
	}
	public static boolean isNull(Object o) {
		if (o == null) {
			return true;
		} else if (o instanceof String && "".equals(((String) o).trim())) {
			return true;
		}
		else if(o instanceof List && ((List)o).size()==0){
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * 从页面的pageMap中取数据
	 * 
	 * @param o
	 * @return
	 */
	public static String getStringValueFromMap(Map map, String key) {
		Object o = map.get(key);
		if (o instanceof String[]) {
			return ((String[]) map.get(key))[0];
		} else if (o instanceof String) {
			return (String) o;
		} else {
			if (o != null) {
				return o.toString();
			} else {
				return null;
			}
		}
	}

	// *********************************lht*****************************************************
	public static final String DEFAULT_DATE_SAMPLE_FORMAT = "yyyy-MM-dd";

	public static final String DEFAULT_SAMPLE_FORMAT = "yyyy-MM-dd";

	public static final String DEFAULT_TIME_SAMPLE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final java.text.SimpleDateFormat DATE_FORMATTER = new java.text.SimpleDateFormat(
			DEFAULT_DATE_SAMPLE_FORMAT);

	private static final java.text.SimpleDateFormat TIME_FORMATTER = new java.text.SimpleDateFormat(
			DEFAULT_TIME_SAMPLE_FORMAT);

	private static final java.text.SimpleDateFormat DEFAULT_FORMATTER = new java.text.SimpleDateFormat(
			DEFAULT_SAMPLE_FORMAT);

	/**
	 * 获得日期类型的时间戳字符串
	 * 
	 * @param date
	 *            java.util.Date - 日期对象
	 * @return String - 包含指定日期的标准时间戳格式字符串
	 */
	public static String getTimeSampleString(java.util.Date date) {
		String s = null;
		if (date != null)
			s = TIME_FORMATTER.format(date);
		return s;
	}

	/**
	 * 获得日期类型的日期戳字符串
	 * 
	 * @param date
	 *            java.util.Date - 日期对象
	 * @return String - 包含指定日期的标准时间戳格式字符串
	 */
	public static String getDateSampleString(java.util.Date date) {
		if (date == null)
			return null;
		return DATE_FORMATTER.format(date);
	}

	/**
	 * 获得日期类型的日期戳字符串
	 * 
	 * @param date
	 *            java.util.Date - 日期对象
	 * @return String - 包含指定日期的标准时间戳格式字符串
	 */
	public static String getSampleString(java.util.Date date) {
		if (date == null)
			return null;
		return DEFAULT_FORMATTER.format(date);
	}

	public static java.sql.Date getDateToDate(Date date) {
		String temp = null;
		temp = getSampleString(date);
		if (temp == null || temp.trim().length() < 10) {
			return null;
		}
		String year, month, day;
		year = temp.substring(0, 4);
		month = temp.substring(5, 7);
		day = temp.substring(8);
		StringBuffer str = new StringBuffer();
		str.append(year);
		str.append("-");
		str.append(month);
		str.append("-");
		str.append(day);
		java.sql.Date date1 = java.sql.Date.valueOf(str.toString());
		return date1;
	}

	public static Date getSampleDate(String date) {
		Date d = new Date();
		if (date == null)
			return null;
		try {
			d = DEFAULT_FORMATTER.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 获得日期类型的日期戳字符串
	 * 
	 * @param date
	 *            java.util.Date - 日期对象
	 * @param format
	 *            java.lang.String - 指定的日期格式
	 * @return String - 包含指定日期的标准时间戳格式字符串
	 */
	public static String getDateSampleString(java.util.Date date, String format) {
		if (date == null)
			return null;
		if (format == null)
			return getDateSampleString(date);
		java.text.SimpleDateFormat dateFormater = new java.text.SimpleDateFormat(
				format);
		return dateFormater.format(date);
	}

	// *********************************lht*****************************************************
	public static java.util.Date setDateTime(java.util.Date date, int h, int m,
			int s) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.set(year, month, date, hourOfDay, minute, second)
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), h, m, s);
		return calendar.getTime();
	}

	public static Date getNextDate(Date edate) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(edate);
		gc.add(5, 1);
		// System.out.println(gc.getTime());
		return gc.getTime();
	}

	public static String getWeek(Date edate) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(edate);
		String[] arr = { "日", "一", "二", "三", "四", "五", "六" };
		int day = gc.get(Calendar.DAY_OF_WEEK) - 1;
		String week = "星期" + arr[day];
		return week;
	}
	
	/**
	 * 根据条件时间返回上月时间
	 * 
	 * @param time
	 * @return
	 */
	public static Date lastMonth(Date time) {
		String temp = getSampleString(time);
		if (temp == null || temp.trim().length() < 10) {
			return null;
		}
		String year, month;
		year = temp.substring(0, 4);
		month = temp.substring(5, 7);
		if (Integer.valueOf(month) == 1) {
			year = String.valueOf((Integer.valueOf(year) - 1));
			month = "12";
		} else {
			month = String.valueOf(Integer.valueOf(month) - 1);
		}
		StringBuffer str = new StringBuffer();
		str.append(year);
		str.append("-");
		str.append(month);
		str.append("-");
		str.append("01");
		java.sql.Date date = java.sql.Date.valueOf(str.toString());
		return date;
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
	 * 从Cookies中获取参数
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
	
	public static String formatNumber(String pattern, double paramDouble) {
		DecimalFormat localDecimalFormat = new DecimalFormat(pattern);
		return localDecimalFormat.format(paramDouble);
	}

	public static String formatNumber(String pattern, long paramLong) {
		DecimalFormat localDecimalFormat = new DecimalFormat(pattern);
		return localDecimalFormat.format(paramLong);
	}
	
	/**
	 * 打印堆栈异常
	 * 
	 * @param t
	 * @return
	 */
	public static String getExceptionStackTrace(Throwable t) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			if (t != null) {
				sw = new StringWriter();
				pw = new PrintWriter(sw);
				t.printStackTrace(pw);
				return sw.toString();
			}
		} finally {
			try {
				if(pw != null){
					pw.close();
				}
				if(sw != null){
					sw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	/**
	 * 打印堆栈异常
	 * 
	 * @param t
	 * @return
	 */
	public static void logException(Throwable t) {
		logger.error(t.getMessage(),t);
	}
	
	
	public static int calAgeByIdCard(String idCard) throws BusinessException{
		int len = idCard.length();// 身份证编码长度
		if (len < 15) {
			throw new BusinessException("不是有效身份证编码");
		} else{// 身份证编码大于15位
			if ((len != 15) && (len != 18)){// 判断编码位数等于15或18
				throw new BusinessException("不是有效身份证编码");
			} else {
				if (len == 15){// 15位身份证
					Calendar currDate = Calendar.getInstance();
					int year1 = currDate.get(Calendar.YEAR);// 取得当前年份
					int month1 = currDate.get(Calendar.MONTH)+1;// 取得当前月份
					if (month1 > Integer.valueOf((idCard.substring(8, 10))))// 判断当前月分与编码中的月份大小
						return (year1 - Integer.valueOf("19" + idCard.substring(6, 8)));
					else
						return (year1 - Integer.valueOf("19" + idCard.substring(6, 8)) - 1);
				}
				if (len == 18){// 18位身份证
					Calendar currDate = Calendar.getInstance();
					int year1 = currDate.get(Calendar.YEAR);// 取得当前年份
					int month1 = currDate.get(Calendar.MONTH)+1;// 取得当前月份
					System.out.println(idCard.substring(10, 12));
					if (month1 > Integer.valueOf(idCard.substring(10, 12)))// 判断当前月分与编码中的月份大小
						return (year1 - Integer.valueOf(idCard.substring(6, 10)));
					else
						return (year1 - Integer.valueOf(idCard.substring(6, 10)) - 1);
				}
			}
		}
		return 0;
	}
	
	/**
	 * 根据身份证计算出性别
	 * @param idCard
	 * @return
	 * @throws BusinessException
	 */
	public static String calSexByIdCard(String idCard) throws BusinessException{
		int len = idCard.length();// 身份证编码长度
		if (len < 15) {
			throw new BusinessException("不是有效身份证编码");
		} else{// 身份证编码大于15位
			if ((len != 15) && (len != 18)){// 判断编码位数等于15或18
				throw new BusinessException("不是有效身份证编码");
			} else {
				if (len == 15){// 15位身份证
					Integer s = Integer.valueOf(idCard.substring(len - 1));
					if (( s % 2) != 0)// 判断顺序码是否是奇数
						return "男";
					else
						return "女";
				}
				if (len == 18){// 18位身份证
					Integer s = Integer.valueOf(idCard.substring(len - 2, len - 1));
					if ((s % 2) != 0)// 判断顺序码是否是奇数
						return "男";
					else
						return "女";
				}
			}
		}
		return "";
	}
	
	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	public static void validateIDCard(String IDStr) throws BusinessException {
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4","3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7","9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			throw new BusinessException("身份证号码长度应该为15位或18位");
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			throw new BusinessException("身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字");
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			throw new BusinessException("身份证生日无效");
		}
		GregorianCalendar gc = new GregorianCalendar();
		if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
				|| (gc.getTime().getTime() - DateUtil.str2Date(strYear + "-" + strMonth + "-" + strDay, "yyyy-MM-dd").getTime()) < 0) {
			throw new BusinessException("身份证生日不在有效范围");
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			throw new BusinessException("身份证月份无效");
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			throw new BusinessException("身份证日期无效");
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			throw new BusinessException("身份证地区编码错误");
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		/**int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equalsIgnoreCase(IDStr) == false) {
				throw new BusinessException("身份证无效，不是合法的身份证号码");
			}
		} **/
		// =====================(end)=====================
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 格式yyyy-MM-dd
	 * @param dateStr
	 * @return
	 */
	public static boolean isDate(String dateStr){
		Pattern date = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		return date.matcher(dateStr).matches();
	}
	
	/**
	 * 判断是不是一个时间范围
	 * 格式如：2014/01/01-2015/12/31
	 * @param dateRangeStr
	 * @return
	 */
	public static boolean isDateRange(String dateRangeStr){
		Pattern date = Pattern.compile("[0-9]{4}/[0-9]{2}/[0-9]{2}-[0-9]{4}/[0-9]{2}/[0-9]{2}");
		return date.matcher(dateRangeStr).matches();
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		List l = null;
		System.out.println(isNull(l));
	}
	
	
	public static String replaceJs(String content){

		return content.replaceAll("\n|\r", "");
	}
	
	public static String getStrValue(String key){
		return SysConfig.getStrValue(key);
	}
	
	public static String getImageValue(String key,String img,String type){
		String picUrl=SysConfig.getStrValue(key);
		int index = img.lastIndexOf("/");
		String begin = img.substring(0, index+1);
		String end = type+"_"+img.substring(index+1, img.length());
		img = picUrl+begin+end;
		return img;
	}
	
	public static String getImageValue(String img,String type){
		int index = img.lastIndexOf("/");
		String begin = img.substring(0, index+1);
		String end = type+"_"+img.substring(index+1, img.length());
		img =begin+end;
		return img;
	}
}
