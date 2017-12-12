package com.xy.admx.common;

import java.util.Arrays;
import java.util.Random;

public class RandomUtil {

	// 用于生成随机数的字节数组
	private static final char pem_array[] = {'a', 'b', 'c', 'd', 'e', 'f','g','h','i','j'
		,'k','l','m','n','o','p','q','i','s','t','u','v','w','x','y','z','0','1','2','3'
		,'4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N'
		,'O','P','Q','I','S','T','U','V','W','X','Y','Z'};
	
	/**
	 * 生成随机数
	 * @param ch 		用于生成随机数的字节数组
	 * @param length	生成随机数的长度
	 * @return
	 */
	public static synchronized String randomStr(char[] ch, int length) {
		Random random = new Random();
		char c[] = new char[length];
		for (int i = 0; i < length; i++) {
			c[i] = ch[random.nextInt(ch.length)];
		}
		return String.valueOf(c);
	}
	
	/**
	 * 生成随机数
	 * @param length	生成随机数的长度
	 * @return
	 */
	public static String randomStr(int length) {
		return randomStr(pem_array,length);
	}
	
	public static void main(String[] args) {
		String str = randomStr(4);
		System.out.println(str);
	}
	public static String randomIntStr(int length){

		return randomStr(Arrays.copyOfRange(pem_array,26,36),length);
	}
}
