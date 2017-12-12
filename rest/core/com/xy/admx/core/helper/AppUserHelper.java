package com.xy.admx.core.helper;

import com.xy.admx.core.sql.Column;

public class AppUserHelper {
	public static Column ACCOUNT_COLUMN=new Column();//账号字段
	public static Column ROLE_COLUMN=new Column();//角色字段
	public static Column PASSWROD_COLUMN=new Column();//密码字段
	public static Column OPENID_COLUMN=new Column();//OpenId字段
	
	
	public static Column ROLE_NAME_COLUMN=new Column();//角色名称字段
	static{
		ACCOUNT_COLUMN.setNotNull(false);
		ACCOUNT_COLUMN.setDataType("varchar");
		ACCOUNT_COLUMN.setLen(50);
		ACCOUNT_COLUMN.setFildName("account");
		ACCOUNT_COLUMN.setComment("账号");
		PASSWROD_COLUMN.setNotNull(false);
		PASSWROD_COLUMN.setDataType("varchar");
		PASSWROD_COLUMN.setLen(50);
		PASSWROD_COLUMN.setFildName("password");
		PASSWROD_COLUMN.setComment("密码");
		ROLE_COLUMN.setNotNull(true);
		ROLE_COLUMN.setDataType("bigint");
		ROLE_COLUMN.setLen(12);
		ROLE_COLUMN.setFildName("role_id");
		ROLE_COLUMN.setComment("角色id");
		OPENID_COLUMN.setNotNull(false);
		OPENID_COLUMN.setDataType("varchar");
		OPENID_COLUMN.setLen(50);
		OPENID_COLUMN.setFildName("open_id");
		OPENID_COLUMN.setComment("微信openId");
		
		ROLE_NAME_COLUMN.setNotNull(true);
		ROLE_NAME_COLUMN.setDataType("varchar");
		ROLE_NAME_COLUMN.setLen(50);
		ROLE_NAME_COLUMN.setFildName("name");
		ROLE_NAME_COLUMN.setComment("名称");
		
		
	}
	
	
	
	/**
	 * 得到appUser表名	
	 * @return
	 */
	public static  String getAppUserTableName(Long projId){
		if(projId==null){
			throw new IllegalArgumentException("projId is not null");
		}
		return "zdy_"+projId+"_appuser";		
	}
	
	/**
	 * 得到appRole表名	
	 * @return
	 */
	public static  String getAppRoleTableName(Long projId){
		if(projId==null){
			throw new IllegalArgumentException("projId is not null");
		}
		return "zdy_"+projId+"_approle";		
	}

	
	
}
