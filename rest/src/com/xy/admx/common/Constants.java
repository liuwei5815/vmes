package com.xy.admx.common;



/**
 * 应用程序中使用到的常量
 */
public final class Constants {
	//分页显示行数
	public static final String PAGE_PER_RECORD = "3";
	public static final String PAGE_PER_RECORD5 = "5";
	public static final String PAGE_PER_RECORD6 = "6";
	public static final String PAGE_PER_RECORD8 = "8";
	public static final String PAGE_PER_RECORD10 = "10";
	public static final String PAGE_PER_RECORD11 = "11";
	public static final String PAGE_PER_RECORD12 = "12";
	public static final String PAGE_PER_RECORD14 = "14";
	public static final String PAGE_PER_RECORD16 = "16";
	public static final String PAGE_PER_RECORD18 = "18";
	public static final String PAGE_PER_RECORD20 = "20";
	public static final String PAGE_PER_RECORD22 = "22";

	public static final String SCREEN_WIDTH_800 = "800";
	public static final String SCREEN_WIDTH_960 = "960";
	public static final String SCREEN_HEIGHT_600 = "600";
	public static final String SCREEN_WIDTH_1024 = "1024";
	public static final String SCREEN_HEIGHT_768 = "768";
	public static final String SCREEN_WIDTH_1088 = "1088";
	public static final String SCREEN_HEIGHT_612 = "612";
	public static final String SCREEN_WIDTH_1152 = "1152";
	public static final String SCREEN_HEIGHT_864 = "864";
	public static final String SCREEN_WIDTH_1280 = "1280";
	public static final String SCREEN_HEIGHT_720 = "720";
	public static final String SCREEN_HEIGHT_960 = "960";
	public static final String SCREEN_HEIGHT_1024 = "1024";
	public static final String SCREEN_HEIGHT_800 = "800";
	public static final String SESSION_BEAN = "sessionBean";// SessionBean
	public static final String COMPANY_BEAN = "companyBean";//CompanyBean
	//public final static String CURRENT_USER_ID = "userId";//存放当前用户的ID
	public final static String USER_BEAN = "userBean";
	
	public static int MAX_RESULT_PER_PAGE = 10;  // 每页显示的最大行数
	
	public static final String SESSION_MENU_KEYS="menus";
	
	
    //数据库中数据状态
	public static final Long STATE_VALID=1L;//有效
	public static final Long STATE_INVALID=0L;//无效
	/**
	 * 有效
	 */
	public static final String STATE_VALID_STRING="1";//有效
	public static final String STATE_INVALID_STRING="0";//无效
	
	//状态
	public static final String STATE_UNAUDITED = "0"; //注销
	public static final String STATE_AUDITED = "1"; //有效
	
	/***
	 * 系统静态变量
	 */
	public static final String USERID="{USERID}";//用户id
	
	public static final String USERDEPT="{USERDPET}";//用户部门
	
	public static final String USERROLE="{USERROLE}";//用户橘色
	
	public static final String SYSTEMDATE="{SYSTEMDATE}";//系统时间
	
	
	//动作标示

	
	
	
	//菜单类别
	/**
	 * 业务菜单
	 */
	public static final int MENU_TYPE_BUS=1;//业务菜案
	/**
	 * 链接菜单
	 */
	public static final int MENU_TYPE_LINK=2;//链接菜单
	/**
	 * 父菜单
	 */
	public static final int MENU_TYPE_PARENT=3;//父菜单
	//权限状态
	/**
	 * 完全控制
	 */
	public static final int POWER_POS_CONTROL=1;
	/**
	 * 非完全控制
	 */
	public static final int POWER_IMP_CONTROL=0;
	/**
	 * 没有权限
	 */
	public static final int POWER_NOT=2;
	
	
	/**
	 * 字段默认长度
	 */ 
	public static final int VARCHAR_DEFAULT = 80;
	
	public static final int STATUS_DEFAULT = 1;
	
	/**
	 * 文件后缀
	 */
	public static final String[] IMG_SUFFIX = {"jpg","jpeg","png"};//图片
	
	public static final String[] DOC_SUFFIX = {"doc","docx","md"};//文档
	
	public static final String[] REPORT_SUFFIX = {"xls","xlsx","csv"};//报表
	
	public static final String[] ZIP_SUFFIX = {"zip","gz","7z"};//压缩文件
	
	/**
	 * 账户默认密码
	 */
	public static final String DEFALT_PWD = "123456";
	
	/**
	 * 分隔符
	 */
	public static final String SPILIT_CODE = "##";
	
	public static final String ACCESS_TOKEN = "access_token";
	
	public static final String SESSION_TOKEN="session_token";
	
	public static final int TOKEN_CACHE_EXPIRES_IN=7200*1000;
	
}
