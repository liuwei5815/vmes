package com.xy.cms.utils;

import java.util.ArrayList;
import java.util.List;

/***
 * 字段帮助类
 * @author yl
 *
 */
public class ColumnUtils {
	   private final static  List<String> NUMBERS_TYPE;//数字类字段
	   private final static  List<String> CHARACTERS_TYPE;//字符类字段
	   static{
		   NUMBERS_TYPE = new ArrayList<String>();
		   NUMBERS_TYPE.add("bigint");
		   NUMBERS_TYPE.add("double");
		   CHARACTERS_TYPE = new ArrayList<String>();
		   CHARACTERS_TYPE.add("char");
		   CHARACTERS_TYPE.add("varchar");
		   CHARACTERS_TYPE.add("text");
	   }
	   public static boolean isNumberColunm(String type){
		   return NUMBERS_TYPE.contains(type);
	   }
	   public static boolean isCharacterColunm(String type){
		   return CHARACTERS_TYPE.contains(type);
	   }
	   
}
