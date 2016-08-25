package com.app.dto;

import java.io.Serializable;

public class TuserDto implements Serializable{
	private Long id;
    private String username;

    private Integer status;

    private String name;
    private String dname;
    private Integer stationid;
    private String stationname;
    private Long deptid;
    private String navids;

	public String getNavids() {
		return navids;
	}
	public void setNavids(String navids) {
		this.navids = navids;
	}
	public Long getId() {
		return id;
	}
	public Integer getStationid() {
		return stationid;
	}
	public void setStationid(Integer stationid) {
		this.stationid = stationid;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getStationname() {
		return stationname;
	}
	public void setStationname(String stationname) {
		this.stationname = stationname;
	}
	public Long getDeptid() {
		return deptid;
	}
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
    
    
    

}
