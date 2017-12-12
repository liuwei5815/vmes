package com.xy.cms.common;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class UploadFileUtils {
	/**
	 * 根据年月生成对应的目录 如2015-10 则生成 2015/10
	 * 
	 * @return
	 */
	public static String getPathByYearAndMonth() {
		StringBuilder path = new StringBuilder();
		Calendar now = Calendar.getInstance();
		path.append(now.get(Calendar.YEAR)).append("/").append(now.get(Calendar.MONTH) + 1);
		return path.toString();
	}

	/**
	 * 创建新的文件夹名称以系统毫秒数+3位随机数
	 * 
	 * @return
	 */
	public static String createNewFileName() {
		StringBuilder fileName = new StringBuilder();
		fileName.append(new Date().getTime());
		fileName.append(RandomUtil.randomStr(3));
		return fileName.toString();
	}

	/**
	 * 得到二维码上传路径并创建文件夹
	 * @return
	 */
	public static String getQRCodeFilePath(){
		StringBuilder filePath = new StringBuilder();
		filePath.append(Environment.getWebAppsHome()).append(File.separator).append(SysConfig.getStrValue("qrcode_img"));
		new File(filePath.toString()).mkdirs();
		return filePath.toString();
	}


}
