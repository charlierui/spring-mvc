package com.app.model;

import java.io.Serializable;
import java.util.Date;

public class TLogbook  implements Serializable{
    private Long id;

    private Date ldate;

    private String description;

    private String ip;

    private String mark;

    private String name;

    private String requesturi;

    private String username;

    
    private String fromDateSch;
    private String toDateSch;

    
    
    

	public String getFromDateSch() {
		
		return fromDateSch;
	}

	public void setFromDateSch(String fromDateSch) {
		this.fromDateSch = fromDateSch;
	}

	public String getToDateSch() {
		
		return toDateSch;
	}

	public void setToDateSch(String toDateSch) {
		this.toDateSch = toDateSch;
	}

	public TLogbook() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TLogbook(Date ldate, String description, String ip, String mark, String name, String requesturi,
			String username) {
		super();
		this.ldate = ldate;
		this.description = description;
		this.ip = ip;
		this.mark = mark;
		this.name = name;
		this.requesturi = requesturi;
		this.username = username;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLdate() {
        return ldate;
    }

    public void setLdate(Date ldate) {
        this.ldate = ldate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRequesturi() {
        return requesturi;
    }

    public void setRequesturi(String requesturi) {
        this.requesturi = requesturi == null ? null : requesturi.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
}