package com.app.model;

import java.io.Serializable;

public class TStations  implements Serializable{
    private Long id;

    private String stationcode;

    private String stationname;

    private String stationremark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationcode() {
        return stationcode;
    }

    public void setStationcode(String stationcode) {
        this.stationcode = stationcode == null ? null : stationcode.trim();
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname == null ? null : stationname.trim();
    }

    public String getStationremark() {
        return stationremark;
    }

    public void setStationremark(String stationremark) {
        this.stationremark = stationremark == null ? null : stationremark.trim();
    }
}