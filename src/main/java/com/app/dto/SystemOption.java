package com.app.dto;

import java.util.Date;

import com.app.util.PropertyManager;
import com.app.util.Tool;

public class SystemOption {
	
	private static SystemOption systemOption = null;
	
	private String systemTitle;
	private String defaultPsw ;
	private String contextRoot;
	private String filterurl;
	private String rwqh;


	public String getRwqh() {
		return rwqh;
	}


	public void setRwqh(String rwqh) {
		this.rwqh = rwqh;
	}


	public static SystemOption getInstance(){
		if(systemOption == null){
			init();
		}
		return systemOption;
	}
	
	
	public synchronized static void init(){
		if(systemOption == null){
			systemOption = new SystemOption();
		}
		
		PropertyManager pm = new PropertyManager("system.properties");
		systemOption.setSystemTitle(pm.get("systemTitle"));
		systemOption.setDefaultPsw(pm.get("defaultPsw"));
		systemOption.setContextRoot(pm.get("contextRoot"));
		systemOption.setFilterurl(pm.get("filterurl"));
		systemOption.setRwqh(pm.get("rwqh"));

	}
	

	public String getSystemTitle() {
		return systemTitle;
	}

	public void setSystemTitle(String systemTitle) {
		this.systemTitle = systemTitle;
	}


	public String getDefaultPsw() {
		return defaultPsw;
	}


	public void setDefaultPsw(String defaultPsw) {
		this.defaultPsw = defaultPsw;
	}


	public String getContextRoot() {
		return contextRoot;
	}


	public void setContextRoot(String contextRoot) {
		this.contextRoot = contextRoot;
	}


	public String getFilterurl() {
		return filterurl;
	}


	public void setFilterurl(String filterurl) {
		this.filterurl = filterurl;
	}


}
