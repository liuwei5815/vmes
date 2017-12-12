package com.xy.cms.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

	/**
	 * 将Date格式化为指定字符串形�?
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static final String format(String pattern, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * 按指定格式输出当前日�?
	 * 
	 * @param pattern
	 * @return
	 */
	public static final String currentDate(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	/**
	 * 将日期格式化�?yyyy-MM-dd HH:mm:ss'
	 * 
	 * @param date
	 * @return
	 */
	public static final String format2Full(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 将日期格式化�?yyyy-MM-dd'
	 * 
	 * @param date
	 * @return
	 */
	public static final String format2Short(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	/**
	 * 将毫秒数格式化为'yyyy-MM-dd'
	 * 
	 * @param date
	 * @return
	 */
	public static final String format2Long(Long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(time);
	}
	
	/**
	 * 将字符串解析为Date型数�? 指定format格式
	 * 
	 * @param dateString
	 *            Date数据的字符串表示
	 * @return 解析得到的Date型数�?
	 */
	public final static Date str2Date(String dateString, String format){
		if (null == format || format.equals("")){
			format = "yyyy-MM-dd";
		}
		if (null == dateString || dateString.equals("")){
			return null;
		}
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			date = formatter.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 得到距离现在几分钟前的日期
	 * @param Day
	 * @return
	 */
	public static Date getBeforeMinute(int minute){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -minute);
		return calendar.getTime();
	}
	
	public static final String getTime() {
		return String.valueOf(new Date().getTime());
	}

	public static final String getSecond() {
		return getTime().substring(0, 10);
	}

	public static final String getStrCurrYear() {
		return format("yyyy", new Date());
	}

	public static final String getStrCurrMonth() {
		return format("MM", new Date());
	}

	public static final String getStrCurrDay() {
		return format("dd", new Date());
	}

	public static final String getStrCurrHour() {
		return format("HH", new Date());
	}

	public static final String getStrCurrMinute() {
		return format("mm", new Date());
	}

	public static final int getIntCurrYear() {
		return Integer.parseInt(getStrCurrYear());
	}

	public static final int getIntCurrMonth() {
		return Integer.parseInt(getStrCurrMonth());
	}

	public static final int getIntCurrDay() {
		return Integer.parseInt(getStrCurrDay());
	}

	public static final int getIntCurrHour() {
		return Integer.parseInt(getStrCurrHour());
	}

	public static final int getIntCurrMinute() {
		return Integer.parseInt(getStrCurrMinute());
	}

	public int getSeason() {
		int i = getIntCurrMonth();
		if ((1 <= i) && (i <= 3))
			return 1;
		if ((4 <= i) && (i <= 6))
			return 2;
		if ((7 <= i) && (i <= 9))
			return 3;
		return 4;
	}

	/**
	 * 将java.util.Date 转为 java.sql.Date
	 * @param dateTime
	 * @return
	 */
	public static java.sql.Date convertSqlDate(java.util.Date dateTime) {
		if (dateTime == null){
			return null;
		}else{
			return new java.sql.Date(dateTime.getTime());
		}
	}
	
	public static long getDayBetweenTwoDate(Date date1 ,Date date2){
        return (date1.getTime()-date2.getTime())/(24*60*60*1000);   
	}
	/**
	 * 得到服务器当前时�?yyyy-mm-dd-毫秒�?
	 * @param paramArrayOfString
	 */
	public static String getDateAndTime(){
		return format2Short(new Date())+"-"+getTime();
	}
	
	public static String getRandomNum(){
		Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	String str = sdf.format(date);
    	return str;
	}
	
	/**
	 * 通过开始时间和结束时间 得到中间的天数集合
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}
	
	
	//将yyyy-MM-dd格式的日期转为Date类型
	public static final Date format2String(String stringDate) {
	      SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd");  
	      try {  
	            Date date = lsdStrFormat.parse(stringDate);  
	            return date;
	      } catch (Exception e) {  
	            e.printStackTrace();  
	      }  
	      return null;
	}
	
	
	/**
	 * 计算两个时间段之间的秒数
	 * */
	public static final int second(Date beginDate, Date endDate) {
	    int  second = (int) ((endDate.getTime()-beginDate.getTime())/1000);
		return second;
	}
	
	//将yyyy-MM-dd格式的日期转为Date类型
	public static final Date format3String(String stringDate) {
		SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		try {  
            Date date = lsdStrFormat.parse(stringDate);  
            return date;
		} catch (Exception e) {  
            e.printStackTrace();  
		}  
		return null;
	}
	
	 /**
     * 当天的开始时间(毫秒数)
     * @return
     */
	public static long beginOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date date=calendar.getTime();
        return date.getTime();
    }
	
	 /**
     * 当天的结束时间(毫秒数)
     * @return
     */
	public static long endOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date date=calendar.getTime();
        return date.getTime();
    }
	
	
	public static void main(String[] paramArrayOfString) {
		//System.out.println(DateUtil.format3String("2017-09-08 00:00:00").getTime());
		//System.out.println(DateUtil.format3String("2017-09-08 01:00:00").getTime());
		System.out.println(beginOfTodDay());
//		System.out.println(convertSqlDate(new Date()));
//		System.out.println(new Date());
//		System.out.println(format("yyyyMMdd",new Date()));
//		System.out.println(DateUtil.format("yyyy-MM-dd", new Date()));
//		System.out.println(DateUtil.str2Date("9:40", "HH:mm").getTime());
		//System.out.println(DateUtil.format2String("2016-07-06"));
	}
}