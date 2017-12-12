package com.xy.cms.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TableColumns {
	public static final int STATE_NO = 0;
	public static final int STATE_YES = 1;

	public static final int ASC = 1;
	public static final int DESC = 2;

	public static enum rules {
		Wu("0"), DengYu("1"), DaYu("2"), DaYuDengYu("3"), XiaoYu("4"), XiaoYuDengYu("5"), BuDengYu("6"), BaoHan(
				"7"), QiShiZiFu("8"), BuBaoHan("9");
		private String value;

		private rules(String value) {
			this.value = value;

		}

		public String getValue() {
			return value;
		}

	};

	public static enum DefaultColumn {
		ADD_DATE("add_date"), UPDATE_DATE("update_date");
		private String name;

		private DefaultColumn(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public static enum DataType {
		AUTONUMBER(18l),QRCODE(19L);
		private Long code;

		private DataType(Long code) {
			this.code = code;
		}

		public Long getCode() {
			return code;
		}

	}

	private Long id;

	private Long tableId;

	private String name;
	private String nameCn;
	private Long dataType;

	private String len;

	private String defaults;

	private String optvalue;

	private Short notnull;

	private Short showinform;

	private Short showinlist;

	private String des;

	private Long orderby;

	private Short status;

	private Date addDate;

	private Date updateDate;

	private Short primaryKey;

	private Short increment;

	public TableColumns() {
		super();
	}

	public TableColumns(Long id, Long tableId, String name, Long dataType, String len, String defaults, Short notnull,
			Short showinform, Short showinlist, String des, Long orderby, Short status, Date addDate, Date updateDate) {
		super();
		this.id = id;
		this.tableId = tableId;
		this.name = name;
		this.dataType = dataType;
		this.len = len;
		this.defaults = defaults;
		this.notnull = notnull;
		this.showinform = showinform;
		this.showinlist = showinlist;
		this.des = des;
		this.orderby = orderby;
		this.status = status;
		this.addDate = addDate;
		this.updateDate = updateDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTableId() {
		return tableId;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDataType() {
		return dataType;
	}

	public void setDataType(Long dataType) {
		this.dataType = dataType;
	}

	public String getLen() {
		return len;
	}

	public void setLen(String len) {
		this.len = len;
	}

	public String getDefaults() {
		return defaults;
	}

	public void setDefaults(String defaults) {
		this.defaults = defaults;
	}

	public Short getNotnull() {
		return notnull;
	}

	public void setNotnull(Short notnull) {
		this.notnull = notnull;
	}

	public Short getShowinform() {
		return showinform;
	}

	public void setShowinform(Short showinform) {
		this.showinform = showinform;
	}

	public Short getShowinlist() {
		return showinlist;
	}

	public void setShowinlist(Short showinlist) {
		this.showinlist = showinlist;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Long getOrderby() {
		return orderby;
	}

	public void setOrderby(Long orderby) {
		this.orderby = orderby;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Short getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Short primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Short getIncrement() {
		return increment;
	}

	public void setIncrement(Short increment) {
		this.increment = increment;
	}

	public String getOptvalue() {
		return optvalue;
	}

	public void setOptvalue(String optvalue) {
		this.optvalue = optvalue;
	}

	/**
	 * 是否是主键
	 * 
	 * @return boolean
	 */
	public boolean isPk() {
		return this.primaryKey == STATE_YES;

	}

	/**
	 * 是否在列表中显示
	 * 
	 * @return
	 */
	public boolean isShowInList() {
		return this.showinlist == STATE_YES;
	}

	/**
	 * 是否在表单中显示
	 * 
	 * @return
	 */
	public boolean isShowInFrom() {
		return this.showinform == STATE_YES;
	}

	public List getOptvalueList() {
		if (optvalue == null || optvalue.trim().length() == 0) {
			return null;
		}
		return Arrays.asList(optvalue.split("、"));
	}

	public static void main(String[] args) {
		TableColumns columns = new TableColumns();
		columns.setDataType(10L);
		columns.setOptvalue("300*400、250*100");
		System.out.println(columns.getOptvalueList().get(0));
	}

}
