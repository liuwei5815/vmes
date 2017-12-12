package com.xy.cms.bean;

import java.io.Serializable;

public class JsTree implements Serializable {

	// required
	private String id;

	// required
	private String parent;

	// node text
	private String text;
	
	/**
	 *  state       : {
		    opened    : boolean  // is the node open
		    disabled  : boolean  // is the node disabled
		    selected  : boolean  // is the node selected
	  	}
	 */
	private State state;
	
	public JsTree(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public static class State{
		private boolean opened;
		
		private boolean disabled ;
		
		private boolean selected ;
		
		public State(boolean opened){
			this.opened = opened;
		}
		
		public State(){}
		
		public State(boolean opened, boolean disabled, boolean selected) {
			super();
			this.opened = opened;
			this.disabled = disabled;
			this.selected = selected;
		}

		public boolean isOpened() {
			return opened;
		}

		public void setOpened(boolean opened) {
			this.opened = opened;
		}

		public boolean isDisabled() {
			return disabled;
		}

		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}
		
		
	}

}
