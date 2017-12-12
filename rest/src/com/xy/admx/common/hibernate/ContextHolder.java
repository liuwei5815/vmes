package com.xy.admx.common.hibernate;

public class ContextHolder {
	private static final ThreadLocal<Object> holder = new ThreadLocal<Object>();

	public static void setProjId(Long projId) {
		holder.set(projId);
	}

	public static Long getProjId() {
		return (Long)holder.get();
	}

	public static void clear() {
		holder.remove();
	}
}
