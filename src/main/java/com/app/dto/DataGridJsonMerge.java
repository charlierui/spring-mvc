package com.app.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataGridJsonMerge  implements Serializable{
	private long total=0;
	private List rows=new ArrayList();
	private String mergeStr;
	
	public String getMergeStr() {
		return mergeStr;
	}
	public void setMergeStr(String mergeStr) {
		this.mergeStr = mergeStr;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
