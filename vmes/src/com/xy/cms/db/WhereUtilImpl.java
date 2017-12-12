package com.xy.cms.db;

import com.xy.cms.db.bean.Where;

public class WhereUtilImpl implements WhereUtil {

	@Override
	public String getWhere(Where where) {
		StringBuilder $ = new StringBuilder();
		$.append(where.getName());
		$.append(getGx(where));
		$.append(":").append(where.getName());
		return $.toString();
	}

	private String getGx(Where where) {
		if (where.isEQ()) {
			return "=";
		}
		if (where.isGT()) {
			return ">";
		}
		if (where.isGEQ()) {
			return ">=";
		}
		if (where.isLT()) {
			return "<";
		}
		if (where.isLEQ()) {
			return "<=";
		}
		if (where.isNEQ()) {
			return "like";
		}
		if (where.isBEGIN()) {
			return "";
		}
		if (where.isNCS()) {
			return "not like ";
		}
		return "==";
	}

}
