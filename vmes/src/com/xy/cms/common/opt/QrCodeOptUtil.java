package com.xy.cms.common.opt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class QrCodeOptUtil {
	private static Pattern COLUMN_PATTER=Pattern.compile("\\{(\\d*)\\}");
	
	/**
	 * 解析qrCodeOpt
	 * @param qrCodeOpt
	 * @return
	 */
	public static String calCodeOpt(String qrCodeOpt,Map<String,Object> body){
		Matcher matcher = COLUMN_PATTER.matcher(qrCodeOpt);
		String result=qrCodeOpt;
		while(matcher.find()){
			String columnId = matcher.group(1);
			Object value = body.get(columnId);
			System.out.println(matcher.group());
			System.out.println(value);
			result=result.replace(matcher.group(),value==null?"":value.toString());
		}
		return result;
	}
	public static void main(String[] args) {
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("65","Z00001");
		System.out.println(
		calCodeOpt("{65}",body));
		String qrCodeOpt="{65}";
		

	}
	

	

}
