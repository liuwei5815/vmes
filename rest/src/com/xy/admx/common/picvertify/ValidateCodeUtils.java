package com.xy.admx.common.picvertify;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.xy.admx.common.CachePool;
import com.xy.admx.common.CommonFunction;




public class ValidateCodeUtils {
	private static final int DEFAULT_WIDTH = 80;
	private static final int DEFAULT_HEIGHT = 34;
	/**
	 * 生成验证码，放入session中，并且响应
	 * @param key
	 * @throws IOException 
	 */
	public static void createValidateCode(String key,HttpServletResponse response) throws IOException{
		createValidateCode(key,response,DEFAULT_WIDTH,DEFAULT_HEIGHT);	
	}
	/**
	 * 生成验证码，放入CachePool中，并且响应
	 * @param key
	 * @throws IOException 
	 */
	public static void createValidateCode(String key,HttpServletResponse response,int width,int height) throws IOException{
		ValidateCode code = new ValidateCode(width, height);
		CachePool.picVertify.put(key, code.getCode());
		code.write(response.getOutputStream());
	}
	
	/**
	 * 判断验证码是否正确
	 * @param key
	 * @throws IOException 
	 */
	public static boolean compareCode(String key ,String values){
		boolean flag = false;
		if (CommonFunction.isNull(values)) {
			flag = false;
		}else {
			flag = CachePool.picVertify.get(key).equals(values);
			}
		
		return flag; 
	}
}  
