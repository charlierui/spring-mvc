package com.app.model;

import java.io.Serializable;

public class TDictype  implements Serializable{
    private Long id;

    private String bak;

    private Integer sortnum;

    private String text;

    private String value;
	private String iconCls;

    public String getIconCls() {
		 return "icon-bullet-green";
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak == null ? null : bak.trim();
    }

    public Integer getSortnum() {
        return sortnum;
    }

    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
}