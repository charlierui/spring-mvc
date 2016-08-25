package com.app.model;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.service.department.TdepartmentInte;
import com.app.util.BaseObject;


public class TDepartment extends BaseObject<TDepartment> implements Serializable{
	
	
    private Long id;

    private String bak;

    private String dname;

    private String dsortnum;

    private Long parentid;

    private Long zcgkid;
    private String text;
	
    
    public String getText() {
		return dname;
	}

	public void setText(String text) {
		this.text = text;
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

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname == null ? null : dname.trim();
    }

    public String getDsortnum() {
        return dsortnum;
    }

    public void setDsortnum(String dsortnum) {
        this.dsortnum = dsortnum == null ? null : dsortnum.trim();
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Long getZcgkid() {
        return zcgkid;
    }

    public void setZcgkid(Long zcgkid) {
        this.zcgkid = zcgkid;
    }
    
}