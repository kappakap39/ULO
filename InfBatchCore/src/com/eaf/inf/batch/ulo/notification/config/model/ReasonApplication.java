package com.eaf.inf.batch.ulo.notification.config.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ReasonApplication implements Serializable,Cloneable{
	public ReasonApplication(){
		
	}
	boolean foundReason = false;
	private String reasonCode;
	private int reasonRank;
	
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public int getReasonRank() {
		return reasonRank;
	}
	public void setReasonRank(int reasonRank) {
		this.reasonRank = reasonRank;
	}
	public boolean isFoundReason() {
		return foundReason;
	}
	public void setFoundReason(boolean foundReason) {
		this.foundReason = foundReason;
	}
}
