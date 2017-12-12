package com.xy.cms.common.opt;

import java.io.Serializable;

/**
 * 自动编码optval对应对象
 * @author ljx
 *
 */
public class AutoNumberOpt implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String prefix;//前缀
	
	private String timeStr;//时间字符串
	
	private String suffix;//后缀

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
}
