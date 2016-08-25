package com.app.util;



public class ReturnMSG  {

	/**
	 * 
	 */
	public String statuscode;// 状态编号
	public String msg;// 返回信息
	public Object result; // 结果
	
	
	

	public ReturnMSG() {

	}

	
	public ReturnMSG(String statuscode, String msg) {
		this.statuscode = statuscode;
		this.msg = msg;
	}

	public ReturnMSG(String statuscode, String msg, Object result) {
		super();
		this.statuscode = statuscode;
		this.msg = msg;
		this.result = result;
	}
	
	

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

	

}
