package com.app.dto;

import java.util.List;

public class TreeJson {
	private long id;
	private String text;
	private String state;
	private String iconCls;
	private List children;
	private TreeAttributes attributes;

	
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
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public TreeAttributes getAttributes() {
		return attributes;
	}
	public void setAttributes(TreeAttributes attributes) {
		this.attributes = attributes;
	}
	
}
