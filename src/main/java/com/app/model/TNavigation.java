package com.app.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.dto.SystemOption;
import com.app.dto.TreeAttributes;
import com.app.service.navigation.TNavigationInte;


public class TNavigation  implements Serializable{
	@Autowired
	private TNavigationInte navigationserv;
    private Long id;

    private String iconcls;

    private Long parentid;

    private Integer sortnum;

    private String text;

    private String url;
	private List children;
	private TreeAttributes attributes;

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

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls == null ? null : iconcls.trim();
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Integer getSortnum() {
        return sortnum;
    }
    
    public List getChildren() {
    	
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}