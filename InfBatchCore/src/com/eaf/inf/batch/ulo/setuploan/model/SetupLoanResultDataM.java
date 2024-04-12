package com.eaf.inf.batch.ulo.setuploan.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SetupLoanResultDataM implements Serializable, Cloneable {
	private String	applicationGroupNo;
	private String	result;
	private String	accountNo;
	private String	openDate;
	private String  errMsg;
	
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getOpenDate() {
		return openDate;
	}
	public Timestamp getOpenDateAsTimestamp() {
		if (null == openDate)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Timestamp tms = null;
		try {
			tms = new Timestamp(df.parse(openDate).getTime());
		}
		catch (Exception e) {};
		return tms;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
}
