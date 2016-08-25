package com.app.model;

import java.io.Serializable;

import com.app.util.BaseObject;

public class TUser extends BaseObject<TUser> implements Serializable{
	public static final int USER_STATUS_WORK = 1;
	public static final int USER_STATUS_LOCKED = 2;
	
    private Long id;

    private Long deptid;

    private String name;

    private String navids;

    private String psw;

    private Long stationid;

    private Integer status;

    private String username;
    private String deptname;
    private String stationname;
    public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}

	private String saveCookie;
    public String getSaveCookie() {
		return saveCookie;
	}

	public void setSaveCookie(String saveCookie) {
		this.saveCookie = saveCookie;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptid() {
        return deptid;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNavids() {
        return navids;
    }

    public void setNavids(String navids) {
        this.navids = navids == null ? null : navids.trim();
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw == null ? null : psw.trim();
    }

    public Long getStationid() {
        return stationid;
    }

    public void setStationid(Long stationid) {
        this.stationid = stationid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
}