package com.app.model;

public class Xinyizl {
    private String pkXinyi;

    private String xname;

    private Integer xsort;

    public String getPkXinyi() {
        return pkXinyi;
    }

    public void setPkXinyi(String pkXinyi) {
        this.pkXinyi = pkXinyi == null ? null : pkXinyi.trim();
    }

    public String getXname() {
        return xname;
    }

    public void setXname(String xname) {
        this.xname = xname == null ? null : xname.trim();
    }

    public Integer getXsort() {
        return xsort;
    }

    public void setXsort(Integer xsort) {
        this.xsort = xsort;
    }
}