package com.xy.cms.bean;
/**
 * ModelAction自动注入属性时需要的辅助bean
 * @author Administrator
 *
 */
public class ModelBean {
		private String value;//字段value;
		private Long dataType;//字段类型
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public Long getDataType() {
			return dataType;
		}
		public void setDataType(Long dataType) {
			this.dataType = dataType;
		}
}
