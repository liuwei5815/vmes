package com.xy.cms.bean;

import java.util.Arrays;
import java.util.List;

import com.xy.cms.common.CommonFunction;
import com.xy.cms.entity.TableColumns;

public class ViewSearchBean extends TableColumns {
		private Long colunmsId;
		private String colunmsName;
		private String colunmsNameCn;
		private String rule;
		private String ruleValue;
		private Long colDataType;
		private String optValue;
		private String tableName;
		
		public Long getColunmsId() {
			return colunmsId;
		}
		public void setColunmsId(Long colunmsId) {
			this.colunmsId = colunmsId;
		}
		public String getColunmsName() {
			return colunmsName;
		}
		public void setColunmsName(String colunmsName) {
			this.colunmsName = colunmsName;
		}
		public String getOptValue() {
			return optValue;
		}
		public void setOptValue(String optValue) {
			this.optValue = optValue;
		}
		public Long getColDataType() {
			return colDataType;
		}
		public void setColDataType(Long colDataType) {
			this.colDataType = colDataType;
		}
		public String getColunmsNameCn() {
			return colunmsNameCn;
		}
		public void setColunmsNameCn(String colunmsNameCn) {
			this.colunmsNameCn = colunmsNameCn;
		}
		public String getRule() {
			return rule;
		}
		public void setRule(String rule) {
			this.rule = rule;
		}
		public String getRuleValue() {
			return ruleValue;
		}
		public void setRuleValue(String ruleValue) {
			this.ruleValue = ruleValue;
		}
		
		public List getOptvalueList(){
			if(CommonFunction.isNull(optValue)){
				return null;
			}
			/*if(colDataType.equals(10L)){
				return  Arrays.asList(optValue.split("\\*"));
			}*/
			return Arrays.asList(optValue.split("、"));
		}
		public String getTableName() {
			return tableName;
		}
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		
}
