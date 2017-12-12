package com.xy.cms.utils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import com.xy.cms.common.RandomUtil;

public class UploadFileUtils {
	/**
	 * 根据年月生成对应的目录 如2015-10 则生成 2015/10
	 * 
	 * @return
	 */
	public static String getPathByYearAndMonth() {
		StringBuilder path = new StringBuilder();
		Calendar now = Calendar.getInstance();
		path.append(now.get(Calendar.YEAR)).append(File.separator).append(now.get(Calendar.MONTH) + 1);
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

}
