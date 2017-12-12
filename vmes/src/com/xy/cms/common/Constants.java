package com.xy.cms.common;


/**
 * 应用程序中使用到的常�?已经要是不会变化的常�?
 * 用到�?��放一�?
 * @author Administrator CreateDate 2005/10/15 14:00
 */
public final class Constants {
	//分页显示行数
	public static final String PAGE_PER_RECORD = "10";
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
	
	//显示器分辩率的宽�?高度
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
	
	public static int MAX_RESULT_PER_PAGE = 10;  // 每页显示的最大行�?
	
    //数据库中数据状�?
	public static final Long STATE_VALID=1L;//有效
	public static final Long STATE_INVALID=0L;//无效
	/**
	 * 有效
	 */
	public static final String STATE_VALID_STRING="1";//有效
	public static final String STATE_INVALID_STRING="0";//无效
	
	//状�?
	public static final String STATE_UNAUDITED = "0"; //注销
	public static final String STATE_AUDITED = "1"; //有效
	
	public static final String UPLOAD_LINUX_PATH = "upload_linux_path";//add by hj
}
