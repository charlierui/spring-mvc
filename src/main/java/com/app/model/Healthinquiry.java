package com.app.model;

public class Healthinquiry {
    private String pkHealth;

    private String htitle;

    private String htype;

    private String haddtime;

    private String himage;

    private String hreftype;

    private String hcontentshref;

    private String hcontents;

    public String getPkHealth() {
        return pkHealth;
    }

    public void setPkHealth(String pkHealth) {
        this.pkHealth = pkHealth == null ? null : pkHealth.trim();
    }

    public String getHtitle() {
        return htitle;
    }

    public void setHtitle(String htitle) {
        this.htitle = htitle == null ? null : htitle.trim();
    }

    public String getHtype() {
        return htype;
    }

    public void setHtype(String htype) {
        this.htype = htype == null ? null : htype.trim();
    }

    public String getHaddtime() {
        return haddtime;
    }

    public void setHaddtime(String haddtime) {
        this.haddtime = haddtime == null ? null : haddtime.trim();
    }

    public String getHimage() {
        return himage;
    }

    public void setHimage(String himage) {
        this.himage = himage == null ? null : himage.trim();
    }

    public String getHreftype() {
        return hreftype;
    }

    public void setHreftype(String hreftype) {
        this.hreftype = hreftype == null ? null : hreftype.trim();
    }

    public String getHcontentshref() {
        return hcontentshref;
    }

    public void setHcontentshref(String hcontentshref) {
        this.hcontentshref = hcontentshref == null ? null : hcontentshref.trim();
    }

    public String getHcontents() {
        return hcontents;
    }

    public void setHcontents(String hcontents) {
        this.hcontents = hcontents == null ? null : hcontents.trim();
    }
}