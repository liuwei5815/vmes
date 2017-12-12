package com.xy.cms.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class CommonMethod {

	public static final String DEFAULT_DATE_SAMPLE_FORMAT = "yyyy/MM/dd";

	public static final String DEFAULT_TIME_SAMPLE_FORMAT = "yyyy/MM/dd HH:mm:ss";

	private static final java.text.SimpleDateFormat DATE_FORMATTER = new java.text.SimpleDateFormat(
			DEFAULT_DATE_SAMPLE_FORMAT);

	private static final java.text.SimpleDateFormat TIME_FORMATTER = new java.text.SimpleDateFormat(
			DEFAULT_TIME_SAMPLE_FORMAT);
	private static final java.text.SimpleDateFormat TIME_FORMATTER1 = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 获得日期类型的时间戳字符�?
	 * 
	 * @param date
	 *            java.util.Date - 日期对象
	 * @return String - 包含指定日期的标准时间戳格式字符�?
	 */
	public static String getTimeSampleString(java.util.Date date) {
		if (date == null)
			return null;
		return TIME_FORMATTER.format(date);
	}

	public static String getTimeSampleString1(java.util.Date date) {
		if (date == null)
			return null;
		return TIME_FORMATTER1.format(date);
	}

	/**
	 * 获得日期类型的日期戳字符�?
	 * 
	 * @param date
	 *            java.util.Date - 日期对象
	 * @return String - 包含指定日期的标准时间戳格式字符�?
	 */
	public static String getDateSampleString(java.util.Date date) {
		if (date == null)
			return null;
		return DATE_FORMATTER.format(date);
	}

	/**
	 * 获得日期类型的日期戳字符�?
	 * 
	 * @param date
	 *            java.util.Date - 日期对象
	 * @param format
	 *            java.lang.String - 指定的日期格�?
	 * @return String - 包含指定日期的标准时间戳格式字符�?
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

	/**
	 * 获得指定包含时间字符串格式对应的时间对象
	 * 
	 * @param dateTimeFormat
	 * @return java.sql.Timestamp
	 */
	public static Timestamp getTimestamp(String dateTimeFormat) {
		String year = null, month = null, date = null, hour = null, minute = null, second = null;
		if (dateTimeFormat == null)
			return null;
		if (dateTimeFormat.length() > 3)
			year = dateTimeFormat.substring(0, 4);

		if (dateTimeFormat.length() > 6)
			month = dateTimeFormat.substring(5, 7);

		if (dateTimeFormat.length() > 9)
			date = dateTimeFormat.substring(8, 10);

		if (dateTimeFormat.length() > 12)
			hour = dateTimeFormat.substring(11, 13);

		if (dateTimeFormat.length() > 15)
			minute = dateTimeFormat.substring(14, 16);

		if (dateTimeFormat.length() > 18)
			second = dateTimeFormat.substring(17);
		StringBuffer str = new StringBuffer();

		if (year != null)
			str.append(year);

		if (month != null) {
			str.append("-");
			str.append(month);
		}

		if (date != null) {
			str.append("-");
			str.append(date);
		}

		if (hour != null) {
			str.append(" ");
			str.append(hour);
		}

		if (minute != null) {
			str.append(":");
			str.append(minute);
		}

		if (second != null) {
			str.append(":");
			str.append(second);
		}

		if (year != null)
			return Timestamp.valueOf(str.toString());
		else
			return null;
	}

	/**
	 * 获得指定包含日期格式字符串对应的日期对象
	 * 
	 * @param dateFormat -
	 *            指定的包含日期的字符�?
	 * @return java.sql.Date
	 */
	public static java.sql.Date getDate(String dateFormat) {
		if (dateFormat == null || dateFormat.trim().length() < 10)
			return null;
		String year, month, date;
		year = dateFormat.substring(0, 4);
		month = dateFormat.substring(5, 7);
		date = dateFormat.substring(8);
		StringBuffer str = new StringBuffer();
		str.append(year);
		str.append("-");
		str.append(month);
		str.append("-");
		str.append(date);
		java.sql.Date date1 = java.sql.Date.valueOf(str.toString());
		return date1;
	}

	/**
	 * 根据编号类型，产生一个新的编�?Create Time 2005-11-14 19:01:52
	 * 
	 * @param type
	 *            java.lang.String - 指定的编号类�?
	 * @return java.lang.String - 新的编号
	 */
	public static String createNewId(String idType, boolean isDate) throws Exception{
		PersistenceManager per = new PersistenceManager();
		String id = null;
		String s = "0";
		try {
			java.sql.Connection connection = per.getConnection();
			java.sql.CallableStatement proc = connection
					.prepareCall("{call createprk.createno(?,?,?)}");
			proc.setString(1, idType);
			if (isDate)
				s = "1";
			proc.setString(2, s);
			proc.registerOutParameter(3, java.sql.Types.VARCHAR);
			proc.execute();
			id = proc.getString(3);
			proc.close();
		} finally {
			per.close();
		}
		return id;
	}

	/**
	 * 
	 * 将一个整数进行补零后以字符串的形式反�?Create Time 2005-11-15 10:13:14
	 * 
	 * @param LorR
	 *            java.lang.String - 补零的方�?L左补�?R右补�?
	 * @param i
	 *            int - 指定的整�?
	 * @param strlength
	 *            java.lang.String - 补齐的长�?
	 * @return java.lang.String -
	 */
	public static String zeroString(String LorR, int i, int strlength) {
		int j;
		int len;
		StringBuffer str = new StringBuffer();
		if (LorR.equals("R")) {
			str.append(Integer.toString(i));
		}
		len = Integer.toString(i).length();
		for (j = 0; j < strlength - len; j = j + 1) {
			str.append("0");
		}
		if (LorR.equals("L")) {
			str.append(Integer.toString(i));
		}
		return str.toString();
	}

	// */

	public static int getDayInMonth(int year, int month) {
		if (month < 1 || month > 12)
			throw new IllegalArgumentException("Illegal Month " + month);
		int[] dayInNormalYear = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
				30, 31 };
		int[] dayInLeapYear = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30,
				31 };
		int dayInMonth = 0;
		if (year % 4 == 0)
			dayInMonth = dayInLeapYear[month];
		else
			dayInMonth = dayInNormalYear[month];
		return dayInMonth;
	}

	/**
	 * @param date
	 * @return
	 */
	public static java.util.Date getMaxDateInMonth(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int days = getDayInMonth(calendar.get(Calendar.YEAR), calendar
				.get(Calendar.MONTH) + 1);
		calendar.set(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}

	/**
	 * 
	 * @param dateString
	 * @return
	 */
	public static java.util.Date getMaxDateInMonth(String dateString) {
		java.util.Date date = getDate(dateString);
		return getMaxDateInMonth(date);
	}

	public static java.sql.Date addDate(java.sql.Date oldDate) {
		java.util.Date newUtilDate = new java.util.Date(oldDate.getTime() + 24
				* 60 * 60 * 1000);
		java.sql.Date newSqlDate = getDate(getDateSampleString(newUtilDate));
		return newSqlDate;
	}

	public static String getStringValueFromMap(Map map, String key) {
		if (map == null) {
			return null;
		}

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
	
	 public static String getExceptionStackTrace(Throwable t){
		if(t != null){
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			return sw.toString();
		}
		return "";
	}
}
