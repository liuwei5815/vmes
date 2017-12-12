package com.xy.admx.core.common;

import java.util.Collection;
import java.util.List;

public class CollectionUtil {
	/***
	 * 判断该集合是否为空的
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection){
		if(collection==null){
			return true;
		}
		else if(collection.size()==0){
			return true;
		}
		return false;
	}
	/***
	 * 判断该集合是否为空的
	 * @param collection
	 * @return
	 */
	public static <T> boolean contains(Collection<T> collection,T o){
		if(collection==null || o==null){
			return false;
		}
		return collection.contains(o);
	}
	/***
	 * 得到list第一个元素
	 * @param list
	 * @return
	 */
	public static <T> T getFirstElement(List<T> list){
		if(isEmpty(list)){
			return null;
		}
		return list.get(0);
	}
	
	
	
	
	
}
