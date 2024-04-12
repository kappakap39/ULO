package com.ava.dynamic.exception;

public class DashboardException  extends RuntimeException {
	private static final long serialVersionUID = 1769047510848906789L;
	private String errCode;
	private String errMsg;
	
	public DashboardException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}
