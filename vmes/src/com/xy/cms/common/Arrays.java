package com.xy.cms.common;

public class Arrays {
	public static Long[] strToLong(String[] arrays){
		if(arrays==null){
			return null;
		}
		Long[] res = new Long[arrays.length];
		for(int i=0;i<arrays.length;i++){
			res[i]=Long.parseLong(arrays[i]);
		}
		return res;
	}
}
