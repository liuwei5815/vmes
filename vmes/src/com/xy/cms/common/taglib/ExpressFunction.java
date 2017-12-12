package com.xy.cms.common.taglib;

import java.util.Set;

public class ExpressFunction {
	
	public static boolean contains(Set<String> set , String s){
		if(set!=null && s!=null)
			return set.contains(s);
		else
			return false;
	}

}
