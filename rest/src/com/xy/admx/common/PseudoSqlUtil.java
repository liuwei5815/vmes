package com.xy.admx.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PseudoSqlUtil {

	/**
	 * 根据开始时间以及结束时间或者时间的伪类Sql
	 * */
	public static final String getTimeSlotPseudoSql(Date startDate,Date endDate) {
		StringBuffer str=new StringBuffer();
		List<Date> dateList = DateUtil.getDatesBetweenTwoDate(startDate,endDate);
		for(int i = 0;i<dateList.size();i++){
			str.append(" select '").append(DateUtil.format2Long(dateList.get(i).getTime())).append("' as day UNION");
		}
		str.delete(str.length()-5, str.length());
		return str.toString();
	}
	
	public static void main(String[] args) {
		 Date date = new Date();
		  Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DAY_OF_MONTH, +10);//+1今天的时间加一天
	        date = calendar.getTime();
		System.out.println(getTimeSlotPseudoSql(new Date(),date));
	}
	
	
	
	
}
