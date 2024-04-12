package com.ava.flp.cjd.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;


public class CJDRequestBody implements Serializable, Cloneable{
	public CJDRequestBody(){
		super();
	}
	private String contractNo;
	private String result;
	private BigDecimal incomeAmount;
	private ArrayList<String> remarks;
	
	 
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public BigDecimal getIncomeAmount() {
		return incomeAmount;
	}
	public void setIncomeAmount(BigDecimal incomeAmount) {
		this.incomeAmount = incomeAmount;
	}
	public ArrayList<String> getRemarks() {
		return remarks;
	}
	public void setRemarks(ArrayList<String> remarks) {
		this.remarks = remarks;
	}
}
