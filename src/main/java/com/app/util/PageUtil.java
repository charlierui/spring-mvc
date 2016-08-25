package com.app.util;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class PageUtil {
	
	private int rows;//数据集合
	
	private int total;//数据总数量
	
	private int page;//页数 第几页
	private int pageSize;
    
	private String orderStr;
	
	private String sort;
	
	private String order;
	
	private Object query;
	
	private List<?> data;
	
	@JsonIgnore
	public String getOrderStr() {
		orderStr = "";
		if (!StringUtils.isEmpty(order)) {
			String[] orders = order.split(",");
			String[] sorts = sort.split(",");
			for (int i = 0; i < sorts.length; i++) {
				orderStr += sorts[i] + " " + orders[i] + ", ";
			}
			orderStr = orderStr.endsWith(", ") ? orderStr.substring(0, orderStr.length() - 2) : orderStr;
		}
		return orderStr;
	}
	
//	public int getPageSiz() {
//		return pageSize;
//	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@JsonIgnore  
    public int getPageSize() {
        return pageSize;  
    }  
	
	public List<?> getRows(){
		return data;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@JsonIgnore
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@JsonIgnore
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@JsonIgnore
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@JsonIgnore
	public Object getQuery() {
		return query;
	}

	public void setQuery(Object query) {
		this.query = query;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "PageUtil [rows=" + rows + ", total=" + total + ", page=" + page + ", orderStr=" + orderStr + ", sort="
				+ sort + ", order=" + order + ", queryObj=" + query + ", data=" + data + "]";
	}

	


}

