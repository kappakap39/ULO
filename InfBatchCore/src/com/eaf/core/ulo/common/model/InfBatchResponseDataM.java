package com.eaf.core.ulo.common.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class InfBatchResponseDataM implements Serializable,Cloneable{
	public InfBatchResponseDataM(){
		super();
	}		
	private String batchId;
	private String resultCode;
	private String resultDesc;
	private int totalTask = 0;
	private int totalSuccess = 0;
	private int totalFail = 0;
	private int totalValid = 0;
	private int totalWarning = 0;
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public int getTotalTask() {
		return totalTask;
	}
	public void setTotalTask(int totalTask) {
		this.totalTask = totalTask;
	}
	public int getTotalSuccess() {
		return totalSuccess;
	}
	public void setTotalSuccess(int totalSuccess) {
		this.totalSuccess = totalSuccess;
	}
	public int getTotalFail() {
		return totalFail;
	}
	public void setTotalFail(int totalFail) {
		this.totalFail = totalFail;
	}
	public int getTotalValid() {
		return totalValid;
	}
	public void setTotalValid(int totalValid) {
		this.totalValid = totalValid;
	}
	public int getTotalWarning() {
		return totalWarning;
	}
	public void setTotalWarning(int totalWarning) {
		this.totalWarning = totalWarning;
	}
}
