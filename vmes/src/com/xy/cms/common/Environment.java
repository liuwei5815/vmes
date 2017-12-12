package com.xy.cms.common;

import org.apache.log4j.Logger;

public class Environment {
	private static String home = null;
	private static String webAppsHome=null;
	private static Integer databaseType = 1;
	private static String context = null;
	public static int DATABASE_MYSQL = 1;
	public static int DATABASE_SQLSERVER = 2;
	private static String lang = "cn";
	private static int _$3 = 0;
	private static int _$2 = 0;
	private static String _$1 = null;
	private static Logger logger = Logger.getLogger(Environment.class);

	public static String getHome() {
		return home;
	}

	public static void setHome(String paramString) {
		home = paramString;
	}
	public static void main(String[] args) {
		
	}
	public static void desctroy() {
		home = null;
		databaseType = null;
		context = null;
		logger.info("Environment destroy sucessfull!");
	}
	public static void setWebAppsHome(String webAppsHome){
		Environment.webAppsHome=webAppsHome;
	}
	public static String getWebAppsHome() {
		return webAppsHome;
	}
	
	public static String getContext() {
		return context;
	}

	public static void setContext(String context) {
		Environment.context = context;
	}

	public static void setDatabaseType(int dbType) {
		databaseType = dbType;
	}

	public static int getDatabaseType() {
		return databaseType;
	}

	public static void setLang(String language) {
		lang = language;
	}

	public static String getLang() {
		return lang;
	}

	public static String getLangResourceFileName() {
		return "lang_" + getLang() + ".pro";
	}

	public static String getAltMsgResourceFileName() {
		return "alt_msg_" + getLang() + ".pro";
	}

	public static void setLicenseAuthen() {
		_$3 = 1;
	}

	public static void setLicenseValidate() {
		_$2 = 1;
	}

	public static boolean isLicenseValidate() {
		return (_$2 == 1);
	}

	public static boolean isLicenseAuthen() {
		return (_$3 == 1);
	}

	public static void setLICENSE(String paramString) {
		_$1 = paramString;
	}

	public static String getLICENSE() {
		return _$1;
	}
}