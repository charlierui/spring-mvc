package com.app.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.service.department.TdepartmentInte;

public class TDepartment2 implements Serializable{
	
	
    private Long id;

    private String bak;

    private String dname;

    private String dsortnum;

    private Long parentid;

    private Long zcgkid;
	private List children=new ArrayList();
    
    private String text;
	
    
    

	public String getText() {
		return dname;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TDepartment2(Long id, String bak, String dname, String dsortnum, Long parentid, Long zcgkid,String text) {
		super();
		this.id = id;
		this.bak = bak;
		this.dname = dname;
		this.dsortnum = dsortnum;
		this.parentid = parentid;
		this.zcgkid = zcgkid;
		this.text=text;
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
	public List getChildren() {
		
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
}