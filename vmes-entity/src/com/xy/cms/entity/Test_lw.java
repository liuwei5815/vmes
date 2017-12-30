package com.xy.cms.entity;

import java.util.Date;

public class Test_lw {

		private static final long serialVersionUID = 1L;
		
		/**
		 * id
		 */
		private Long id;
		
		/**
		 * 状态
		 */
		private Long state;
		
		/**
		 * 名称
		 */
		private String name;
		
		/**
		 * 编码
		 */
		private String number;
		
		/**
		 * 创建时间
		 */
		private Date cdate;
		
		/**
		 * 修改时间
		 */
		private Date udate;
		
		
		public Test_lw(){}
		
		public Test_lw(Long id, String name, Date cdate) {
			super();
			this.id = id;
			this.name = name;
			this.cdate = cdate;
		}
		

		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(Long id) {
			this.id = id;
		}

		/**
		 * @return the state
		 */
		public Long getState() {
			return state;
		}

		/**
		 * @param state the state to set
		 */
		public void setState(Long state) {
			this.state = state;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the number
		 */
		public String getNumber() {
			return number;
		}

		/**
		 * @param number the number to set
		 */
		public void setNumber(String number) {
			this.number = number;
		}

		/**
		 * @return the cdate
		 */
		public Date getCdate() {
			return cdate;
		}

		/**
		 * @param cdate the cdate to set
		 */
		public void setCdate(Date cdate) {
			this.cdate = cdate;
		}

		/**
		 * @return the udate
		 */
		public Date getUdate() {
			return udate;
		}

		/**
		 * @param udate the udate to set
		 */
		public void setUdate(Date udate) {
			this.udate = udate;
		}
		
		
		
}
