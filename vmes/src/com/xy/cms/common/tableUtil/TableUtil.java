package com.xy.cms.common.tableUtil;

import com.xy.cms.common.CommonFunction;

public class TableUtil {
   public static String getStrResult(String colName,Long dataType,String len,String defaults,int notnull,String comment,int primaryKey,int increment){
	   String col_str=colName;
	   if(dataType==1 || dataType==17){//整型
			col_str+=" "+"bigint("+len+")";
			if(notnull==1){
				col_str+=" "+"NOT NULL";
				if(primaryKey==1){
					col_str+=" primary key";
					if(increment==1){
						col_str+=" AUTO_INCREMENT";
					}
				}else{
					if(CommonFunction.isNotNull(defaults)){
						col_str+=" DEFAULT '"+defaults+"'";
					}
				}
			}
		}else if(dataType==2 || dataType==7 || dataType==8 || dataType==9 || dataType==10 || dataType==12 || dataType==15 || dataType==18 || dataType==19){//字符串
			col_str+=" "+"varchar("+len+")";
			if(notnull==1){
				col_str+=" not null";
				col_str+=" DEFAULT '"+defaults+"'";
			}
		}else if(dataType==3 || dataType==16){//日期类型  datetime
			col_str+=" "+"datetime";
			if(notnull==1){
				col_str+=" not null";
				if(CommonFunction.isNotNull(defaults)){
					col_str+=" DEFAULT now()";
				}
			}
		}else if(dataType==11){//日期类型  date
			col_str+=" "+"date";
			if(notnull==1){
				col_str+=" not null";
				if(CommonFunction.isNotNull(defaults)){
					//col_str+=" DEFAULT "+defaults+"";
				}
			}
		}else if(dataType==4){//大文本
			col_str+=" "+"text";
			if(notnull==1){
				col_str+=" not null";
			}
		}else if(dataType==5){//浮点型
			col_str+=" "+"double(16,2)";
			if(notnull==1){
				col_str+=" not null";
				if(CommonFunction.isNotNull(defaults)){
					col_str+=" DEFAULT '"+defaults+"'";
				}
			}
		}else if(dataType==6){//字符型
			col_str+=" "+"char("+len+")";
			if(notnull==1){
				col_str+=" not null";
				col_str+=" DEFAULT '"+defaults+"'";
			}
		}
		
		col_str+=" COMMENT '"+comment+"'";
	   return col_str;
   }
}
