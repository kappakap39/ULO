package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Reason implements Serializable,Cloneable{
	private String reasonCode;
	private int reasonRank = 999999;
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
}
