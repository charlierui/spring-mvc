package com.app.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataGridJson implements Serializable{
	private long total=0;
	private List rows=new ArrayList();	
	
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
