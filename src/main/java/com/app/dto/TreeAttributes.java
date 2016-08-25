package com.app.dto;

import java.io.Serializable;

public class TreeAttributes implements Serializable{
	private String url;
	private int type;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
