package com.xy.cms.db.bean;

public class Where {
	private String name;
	private Integer type;//

	private static int end = 9;
	private static int begin = 1;

	public static enum WHERE_TYPE {
		EQ(1), GT(2), GEQ(3), LT(4), LEQ(5), NEQ(6), CS(7), BEGIN(8), NCS(9);
		// 等于 大于 大于等于 小于 小于等于 不等于 包含 起始字符 不包含
		public int code;

		private WHERE_TYPE(int code) {
			this.code = code;
		}
	}

	public Where(String name) {
		this(name, 1);// 默认是等于
	}

	public Where(String name, Integer type) {
		setName(name);
		setType(type);
	}

	public boolean isEQ() {
		return type == WHERE_TYPE.EQ.code;
	}

	public boolean isGT() {
		return type == WHERE_TYPE.GT.code;
	}

	public boolean isGEQ() {
		return type == WHERE_TYPE.GEQ.code;
	}

	public boolean isLT() {
		return type == WHERE_TYPE.LT.code;
	}

	public boolean isLEQ() {
		return type == WHERE_TYPE.LEQ.code;
	}

	public boolean isNEQ() {
		return type == WHERE_TYPE.NEQ.code;
	}

	public boolean isCS() {
		return type == WHERE_TYPE.CS.code;
	}

	public boolean isNCS() {
		return type == WHERE_TYPE.NCS.code;
	}

	public boolean isBEGIN() {
		return type == WHERE_TYPE.BEGIN.code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		if (type < begin || type > end) {
			throw new IllegalArgumentException("type超过范湖,只能在" + begin + "到"
					+ end + "之间");
		}
		this.type = type;
	}

}
