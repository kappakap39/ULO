package com.eaf.service.module.model;

import java.io.Serializable;

public class CBS1215I01RequestDataM implements Serializable,Cloneable{
	public CBS1215I01RequestDataM(){
		super();
	}
	
	private String accountId;
	private String rententionSituationCode;
	private String rententionType;
	private String subAccountNumber;
	private int maxRow;
	private int startIndex;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getRententionSituationCode() {
		return rententionSituationCode;
	}
	public void setRententionSituationCode(String rententionSituationCode) {
		this.rententionSituationCode = rententionSituationCode;
	}
	public String getRententionType() {
		return rententionType;
	}
	public void setRententionType(String rententionType) {
		this.rententionType = rententionType;
	}
	public String getSubAccountNumber() {
		return subAccountNumber;
	}
	public void setSubAccountNumber(String subAccountNumber) {
		this.subAccountNumber = subAccountNumber;
	}
	public int getMaxRow() {
		return maxRow;
	}
	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
}
