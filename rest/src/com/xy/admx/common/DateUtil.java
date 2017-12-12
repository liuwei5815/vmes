package com.xy.admx.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV;

public class DateUtil {

	/**
	 * 将Date格式化为指定字符串形式
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
	 * 按指定格式输出当前日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static final String currentDate(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	/**
	 * 将日期格式化为'yyyy-MM-dd HH:mm:ss'
	 * 
	 * @param date
	 * @return
	 */
	public static final String format2Full(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 将日期格式化为'yyyy-MM-dd'
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
	 * 将字符串解析为Date型数据, 指定format格式
	 * 
	 * @param dateString
	 *            Date数据的字符串表示
	 * @return 解析得到的Date型数据
	 */
	public final static Date str2Date(String dateString, String format) {
		if (null == format || format.equals("")) {
			format = "yyyy-MM-dd";
		}
		if (null == dateString || dateString.equals("")) {
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
	 * 计算员工的年龄
	 */
	public static final Integer getAge(Date date) {
		Calendar now = Calendar.getInstance();
		Calendar cs = Calendar.getInstance();
		cs.setTime(date);
		int now_year = now.get(Calendar.YEAR);
		int now_month = now.get(Calendar.MONTH);
		int now_day = now.get(Calendar.DATE);
		int cs_year = cs.get(Calendar.YEAR);
		int cs_month = cs.get(Calendar.MONTH);
		int cs_day = cs.get(Calendar.DATE);
		int age = now_year - cs_year;
		int less = 0;
		if (now_month < cs_month || now_month == cs_month && now_day < cs_day) {
			less = -1;
		}
		return age + less;
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
	 * 
	 * @param dateTime
	 * @return
	 */
	public static java.sql.Date convertSqlDate(java.util.Date dateTime) {
		if (dateTime == null) {
			return null;
		} else {
			return new java.sql.Date(dateTime.getTime());
		}
	}

	public static long getDayBetweenTwoDate(Date date1, Date date2) {
		return (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 得到服务器当前时间 yyyy-mm-dd-毫秒数
	 * 
	 * @param paramArrayOfString
	 */
	public static String getDateAndTime() {
		return format2Short(new Date()) + "-" + getTime();
	}
	
	/**
	 * 获得本周的周一和周日
	 * @return map.monday:周一  map.sunday:周日
	 */
	public static Map day() {
        return printWeekdays();
    }
 
    private static final int FIRST_DAY = Calendar.MONDAY;
 
    private static Map printWeekdays() {
        Calendar calendar = Calendar.getInstance();
        setToFirstDay(calendar);
        Map m = new HashMap();
        for (int i = 0; i < 7; i++) {
        	if(i==0)
        		m.put("monday", printDay(calendar));
        	else if(i==6)
        		m.put("sunday", printDay(calendar));
        	//	printDay(calendar);
            calendar.add(Calendar.DATE, 1);
        }
        return m;
    }
 
    private static void setToFirstDay(Calendar calendar) {
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            calendar.add(Calendar.DATE, -1);
        }
    }
 
    private static String printDay(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println(dateFormat.format(calendar.getTime()));
        return dateFormat.format(calendar.getTime());
    }	
	
    /**
     * 根据日期获得本周的起止时间
     * @param date
     * @return
     */
	public static DateRange getDateRangeByWeek(Date date){
		if(date==null)
			return null;
		Calendar current = Calendar.getInstance();
		current.setTime(date);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, current.get(Calendar.YEAR));
		c.set(Calendar.DAY_OF_WEEK, 2);//设置本周的第二天 即从周一开始算起
		c.set(Calendar.HOUR,0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date beginDate=new Date(c.getTime().getTime());
		c.set(Calendar.DAY_OF_YEAR,c.get(Calendar.DAY_OF_YEAR)+6);
		
		Date endDate =c.getTime();
		return new DateRange(beginDate,endDate);
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
	
	
	public static void main(String[] args) {
		DateRange range = getDateRangeByWeek(new Date());
		System.out.println(format2Full(range.getBeginDate()));
		System.out.println(format2Full(range.getEndDate()));
	}
	
	public static class DateRange{
		private Date beginDate;
		private Date endDate;
		public DateRange(Date beginDate,Date endDate){
			this.beginDate=beginDate;
			this.endDate=endDate;
		}
		public Date getBeginDate() {
			return beginDate;
		}
		public void setBeginDate(Date beginDate) {
			this.beginDate = beginDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		
		
	}

}