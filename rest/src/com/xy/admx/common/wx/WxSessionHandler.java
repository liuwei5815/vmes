package com.xy.admx.common.wx;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xy.admx.common.HttpClient;
import com.xy.admx.common.JedisPoolUtil;
import com.xy.admx.common.SysConfig;
import com.xy.admx.common.exception.RestException;
import com.xy.admx.rest.base.ResponseCode;

@Component
public class WxSessionHandler extends SessionHandler{
	
	public static final String CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

	private String code;
	private String appid;
	private String appsecret;
	private String openid;
	private String wxSession_key;//微信返回的session_key
	
	public WxSessionHandler init(String appid,String appsecret,String jsCode){
		this.code = jsCode;
		this.appid = appid;
		this.appsecret = appsecret;
		return this;
	}
	
	public WxSessionHandler code2Session() throws RestException{
		Map<String,String> param = new HashMap<String,String>();
		param.put("js_code", code);
		param.put("appid", appid);
		param.put("secret", appsecret);
		param.put("grant_type", "authorization_code");
		try {
			String resstr = HttpClient.doGet(WxSessionHandler.CODE2SESSION_URL,null, param);
			JSONObject jsonObject = JSONObject.parseObject(resstr);
			if(jsonObject.containsKey("errcode")){//发生异常
				throw new RestException(jsonObject.getString("errcode"),jsonObject.getString("errmsg"));
			}else{
				openid = jsonObject.getString("openid");
				wxSession_key = jsonObject.getString("session_key");
			}
		} catch (RestException e) {
			throw e;
		} catch (Exception e) {
			throw new RestException(ResponseCode.WXAPI_CONECT_ERROR);
		}
		return this;
	}
	
	/**
	 * 生成session
	 * 以3rd_session为 key
	 * sessionkey_openid为value存到redis里
	 * @return
	 */
	@Override
	public WxSessionHandler generate3rdSession(){
		session_3rd = UUID.randomUUID().toString().replace("-", "");
		int expire = SysConfig.getIntValue("session3rd.expire",7200000);
		JedisPoolUtil.putWithExpire(session_3rd, wxSession_key+"_"+openid, expire);
		return this;
	}
	
	/**
	 * 获取微信open
	 * @param sessionToken
	 * @return
	 */
	public static String getOpenId(String sessionToken){
		if(StringUtils.isBlank(sessionToken)){
			return null;
		}
		String sessionKey_openid = JedisPoolUtil.get(sessionToken);
		if(StringUtils.isBlank(sessionKey_openid)){
			return null;
		}
		return sessionKey_openid.split("_")[1];
	}
	

	public String getOpenid() {
		return openid;
	}

	public String getWxSession_key() {
		return wxSession_key;
	}

	public boolean isSessionExist(String id,String skey){
		return true;
	}
}
