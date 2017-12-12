package com.xy.cms.bean;

import com.xy.cms.entity.TableRelationship;

/**
 * 当前表作为从表的关系
 * @author xiaojun
 *
 */
public class TargetShip extends TableRelationship {
		private TableRelationship sourceShip;//主表作为外键表

		public TableRelationship getSourceShip() {
			return sourceShip;
		}

		public void setSourceShip(TableRelationship sourceShip) {
			this.sourceShip = sourceShip;
		}
		
		
		
}
