package com.xy.admx.common;

import com.alibaba.fastjson.JSONObject;
import com.xy.cms.entity.AppUser;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisPoolUtil {
	private static org.apache.log4j.Logger logger  =org.apache.log4j.Logger.getLogger(JedisPoolUtil.class);

	private static String server = SysConfig.getStrValue("redis.server");
	private static int port = SysConfig.getIntValue("redis.port",6379);
	private static String password = SysConfig.getStrValue("redis.password");
	private final static int DEFAULT_EXPIRE = 1800000;//毫秒,30分钟

	protected static JedisPool jedisPool;
	
	public static void createPool(){
		// 建立连接池配置参数
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(500);
        // 设置最大阻塞时间，记住是毫秒数milliseconds
        config.setMaxWaitMillis(1000);
        // 设置空间连接
        config.setMaxIdle(10);
		jedisPool = new JedisPool(config,server,port,2000,password);
	}

	protected static Jedis getJedis() {
		if(jedisPool == null){
			createPool();
		}
		Jedis jedis = jedisPool.getResource();
		return jedis;
	}

	protected static void release(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResourceObject(jedis);
		}
	}
	public static void putWithExpire(String key, String value){ 
		putWithExpire(key, value, DEFAULT_EXPIRE);
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @param expire	毫秒
	 */
	public static void putWithExpire(String key, String value,long expire){
		Jedis jedis = getJedis();
		try{
			jedis.set(key,  value);
			jedis.pexpire(key, expire);
		}finally{
			release(jedis);
		}
	}
	
	public static void putUnExpire(String key, String value){
		Jedis jedis = getJedis();
		try{
			jedis.set(key,value);
		}finally{
			release(jedis);
		}
	}
	
	public static String get(String key){
		Jedis jedis = getJedis();
		try{
			return jedis.get(key);
		}finally{
			release(jedis);
		}
	}
	
	public static void remove(String key){
		Jedis jedis = getJedis();
		try{
			jedis.del(key);
		}
		finally{
			release(jedis);
		}
	}
	public static boolean exists(String key){
		Jedis jedis = getJedis();
		try{
			return jedis.exists(key);
		}finally{
			release(jedis);
			
		}
	}
	
	public static String getAndResetExpire(String key,long expire){
		Jedis jedis = getJedis();
		try{
			jedis.pexpire(key, expire);
			return jedis.get(key);
		}finally{
			release(jedis);
		}
	}
	

	public static void del(String key){
		Jedis jedis = getJedis();
		try{
			jedis.del(key);
		}finally{
			release(jedis);	
		}
	}
	
	public static void main(String[] args) {
		AppUser user = JSONObject.parseObject(get("2fc048daa07143759d1a8aa50bb5fbf9_user"),AppUser.class);
		System.out.println(get("2fc048daa07143759d1a8aa50bb5fbf9_user"));
	}
	
	
}
