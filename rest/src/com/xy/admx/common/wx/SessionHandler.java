package com.xy.admx.common.wx;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.xy.admx.common.JedisPoolUtil;
import com.xy.admx.common.SysConfig;
import com.xy.cms.entity.AppUser;

/**
 * session 基类
 * @author dg
 *
 */
public class SessionHandler {
	
	protected String session_3rd;
	
	/**
	 * 生成session
	 * @return
	 */
	public SessionHandler generate3rdSession(){
		session_3rd = UUID.randomUUID().toString().replace("-", "");
		int expire = SysConfig.getIntValue("redis.session3rd.expire",7200000);
		JedisPoolUtil.putWithExpire(session_3rd, session_3rd, expire);
		return this;
	}
	
	/**
	 * 将用户信息存放进去
	 * @param userinfo
	 * @return
	 */
	public SessionHandler putUserInfo(AppUser userinfo){
		String userJson = JSONObject.toJSONString(userinfo);
		int expire = SysConfig.getIntValue("redis.session3rd.expire",7200000);
		JedisPoolUtil.putWithExpire(this.getSession_3rd() + "_user", userJson, expire);
		return this;
	}

	/**
	 * 从cache中获取用户信息
	 * @param sessionToken
	 * @return
	 */
	public static AppUser getAppUser(String sessionToken){
		if(StringUtils.isBlank(sessionToken)){
			return null;
		}
		return JSONObject.parseObject(JedisPoolUtil.get(sessionToken + "_user"), AppUser.class);
	}
	
	public String getSession_3rd() {
		return session_3rd;
	}

	
	public boolean isSessionExist(String id,String skey){
		return true;
	}
}
