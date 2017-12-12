package com.xy.cms.common;


public  class Base64 {

	private static final sun.misc.CharacterEncoder encoder = new sun.misc.BASE64Encoder();

	private static final sun.misc.CharacterDecoder decoder = new sun.misc.BASE64Decoder();

	// private static final Sring DEFAULT_CHARACTER_ENCODING = "GBK";
	/**
	 * 用BASE64算法加密字符�?
	 * 
	 */
	public final static String encrypt(String source, String encoding) throws Exception {
		String ciphertext = null;
		if (encoding == null)
			ciphertext = encoder.encode(source.getBytes());
		else
			ciphertext = encoder.encode(source.getBytes("encoding"));
		return ciphertext;
	}

	/**
	 * 用BASE64算法解密字符�?
	 * 
	 */
	public final static String decrypt(String ciphertext, String encoding) throws Exception {
	//	String source = null;
		if (encoding == null)
			ciphertext = new String(decoder.decodeBuffer(ciphertext));
		else
			ciphertext = new String(decoder.decodeBuffer(ciphertext), encoding);
		return ciphertext;
	}

	public static void main(String[] args) throws Exception {
//		String source = "1";
//		System.out.println("明文为：" + source);
//		String ciphertext = EncryptBase64.encrypt(source, null);
//		System.out.println("密文为：" + ciphertext);
//		String authHeader = "Basic c2hlemhpbUBjbi5pYm0uY29tOnNoZXpoaW1AY24uaWJtLmNvbQ==";
//		System.out.println("sss:" + authHeader.substring(6));
//		String authStr = Base64.decrypt(authHeader.substring(6),null);
//		System.out.println("====" + authStr);
//		String userID = "";
//		if (authStr.indexOf(":") > 1) {
//			String uid =
//				authStr.substring(0, authStr.indexOf(":"));
//			if (uid != null)
//				userID = uid;
//		}
//		
//		System.out.println("userID�? + userID);
		System.out.println(Base64.encrypt("yinwsh@cn.ibm.com:DALibm201406", null));
	}

	
}