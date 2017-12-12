package com.xy.admx.common;

import org.springframework.stereotype.Component;


@Component
public class CacheUtils {
	
//	public static final long EXPIRES_IN = 7200*1000;
//	
//	private static MemCachedClient memCachedClient;
//	
//	/**
//	 * 根据key删除缓存中的记录
//	 * @param key
//	 * @return
//	 */
//	public static boolean remove(String key){
//		return memCachedClient.delete(key);
//	}
//	
//	/**
//	 * 根据token获取公司信息
//	 * @param token
//	 * @return
//	 */
//	public static TokenCache getCache(String token){
//		return (TokenCache) memCachedClient.get(token);
//	}
//	
//	
//	public static boolean setCache(String token,TokenCache tokenCache,Long expires_in){
//		return memCachedClient.set(token, tokenCache,new Date(System.currentTimeMillis()+expires_in));
//	}
//	
//	public static boolean setCache(String token,TokenCache tokenCache){
//		return setCache(token, tokenCache,EXPIRES_IN);
//	}
//	
//	/**
//	 * 根据accessid+secret获取token
//	 * @param devInfo
//	 * @return
//	 */
//	public static String getToken(String devInfo){
//		return (String) memCachedClient.get(devInfo);
//	}
//	
//	public static boolean setToken(String devInfo,String token,Long expires_in){
//		return memCachedClient.set(devInfo,token,new Date(System.currentTimeMillis()+expires_in));
//	}
//	
//	public static boolean setToken(String devInfo,String token){
//		return memCachedClient.set(devInfo,token,new Date(System.currentTimeMillis()+EXPIRES_IN));
//	}
//	
//	public MemCachedClient getMemCachedClient() {
//		return memCachedClient;
//	}
//
//	@Resource
//	public void setMemCachedClient(MemCachedClient memCachedClient) {
//		this.memCachedClient = memCachedClient;
//	}
	
}
