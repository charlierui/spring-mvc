package com.app.dto;

import java.io.Serializable;

public class Children implements Serializable{
	private long id;
	private String text;
	private String iconCls;
	
	
	public Children(long id, String text, String iconCls) {
		super();
		this.id = id;
		this.text = text;
		this.iconCls = iconCls;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
}
