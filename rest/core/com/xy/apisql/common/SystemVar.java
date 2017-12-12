package com.xy.apisql.common;

public enum SystemVar {
	SESSION_USER_ID("{SessionUserId}"), SYSTEM_DATE("{SYSTEM_DATE}"),SESSION_EMP_ID("{SessionEmpId}");
	private String code;

	private SystemVar(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
