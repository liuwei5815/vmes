package com.xy.admx.common.hibernate;

import java.util.List;

import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.internal.util.StringHelper;

public class AutoBindProjNamingStrategy extends DefaultNamingStrategy {
	
	private List<String> include;// 需要动态映射的类名
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 321L;
	
	public static final AutoBindProjNamingStrategy INSTANCE = new AutoBindProjNamingStrategy();
	
	@Override
	public String classToTableName(String className) {
		System.out.println("---className:" + className);
		System.out.println("--:" + StringHelper.unqualify(className));
		if(include.contains(StringHelper.unqualify(className))){//包含
			String tname = "zdy_{projid}_" + super.classToTableName(className);
			tname = tname.toLowerCase();
			return tname;
		}
		return super.classToTableName(className);
	}
	
	public List<String> getInclude() {
		return include;
	}

	public void setInclude(List<String> include) {
		this.include = include;
	}
	
	public static void main(String[] args) {
		System.out.println("ADFF".toLowerCase());
	}
}
