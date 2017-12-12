package com.xy.cms.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 二维码生成工厂
 * @author xiaojun
 *
 */
public class QrCodeFactoty {
	/***
	 * 根据派工单code值生成二维码
	 * @throws FileNotFoundException 
	 */
	public static void createTodoQrCode(String fileName,String planTodo) {
		
		File file=new File(Environment.getWebAppsHome()+CacheFun.getConfig("qrcode_img")+fileName+"."+QRcode.CODE_FORMAT);
		file.getParentFile().mkdirs();
		try{
			QRcode.create(new FileOutputStream(file), planTodo);
		}
		catch(FileNotFoundException e){
			throw new RuntimeException(e);
		}
	}
}
