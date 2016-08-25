package com.app.dto;

import java.io.Serializable;

public class Auth implements Serializable{
	private String groupId;
	private String groupName;
	private String resourceId;
	private String resourceName;
	public Auth(String groupId, String groupName, String resourceId,
			String resourceName) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.resourceId = resourceId;
		this.resourceName = resourceName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	
	

	
	
}
