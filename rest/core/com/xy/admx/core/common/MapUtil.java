package com.xy.admx.core.common;

import java.util.HashMap;
import java.util.Map;



public class MapUtil {
	public static void main(String[] args) {
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		map1.put("name", "1");
		map2.put("name","1");
		System.out.println(compareMapByKeySet(map1, map2));
	}
	/***
	 * 比较两个map所有元素是否相等
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static boolean compareMapByKeySet(Map<?,?> map1,Map<?,?> map2){
		if(map1==null && map2==null){
			return true;
		}
		if(map1==null || map2==null){
			return false;
		}
		if(map1.size()!=map2.size()){
			return false;
		}
		for(Object key:map1.keySet()){
			Object value1= map1.get(key);
			Object value2=map2.get(key);
			if(value1!=null && value2!=null){
				if(!value1.equals(value2)){
					return false;
				}																																						
			}
			else{
				if(value1!=value2){
					return false;
				}
			}
			
		}
		return true;
		
	}
	
}
