package com.app.model;

import java.io.Serializable;

public class TDicvalue  implements Serializable{
    private Long vid;

    private String bak;

    private String id;

    private String softcode;

    private Integer sortnum;

    private Integer status;

    private String text;

    private Long tid;
    private String status1;
    public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak == null ? null : bak.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSoftcode() {
        return softcode;
    }

    public void setSoftcode(String softcode) {
        this.softcode = softcode == null ? null : softcode.trim();
    }

    public Integer getSortnum() {
        return sortnum;
    }

    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }
}