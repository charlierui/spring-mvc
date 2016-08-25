package com.app.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Navigation2 implements Serializable{
	private Long id;
	private String text;
	private String url;
	private Integer sortNum;
	private String  iconCls;
	private Long parentId;
	private List children=new ArrayList();
	private TreeAttributes attributes;
	
	public Navigation2(Long id, String text, String url, Integer sortNum,
			String iconCls, Long parentId) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.sortNum = sortNum;
		this.iconCls = iconCls;
		this.parentId = parentId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public List getChildren() {
		
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public TreeAttributes getAttributes() {		
		 attributes=new TreeAttributes();
		 if(url==null||url.equals("")||url.equals("#")){
			 attributes.setUrl("#");
		 }else{			 
			 attributes.setUrl(SystemOption.getInstance().getContextRoot()+url);
		 }
		return attributes;
	}
	public void setAttributes(TreeAttributes attributes) {
		this.attributes = attributes;
	}
	
	
}
