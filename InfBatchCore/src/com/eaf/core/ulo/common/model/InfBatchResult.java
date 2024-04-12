package com.eaf.core.ulo.common.model;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class InfBatchResult implements Serializable,Cloneable{
	public InfBatchResult(){
		super();
	}
	private String batchId;
	private String uniqueId;
	private String resultCode;
	private String resultDesc;
	private Timestamp startTime;
	private Timestamp endTime;
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
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
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}	
}
