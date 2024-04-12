package com.eaf.orig.ulo.model.pega;

import java.io.Serializable;

public class UpdateApprovalStatusData implements Serializable, Cloneable{
	private String caseId;
	private String isClose;
	private String isVetoEligible;
	private String sendToEDWS;
	
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getIsClose() {
		return isClose;
	}
	public void setIsClose(String isClose) {
		this.isClose = isClose;
	}
	public String getIsVetoEligible() {
		return isVetoEligible;
	}
	public void setIsVetoEligible(String isVetoEligible) {
		this.isVetoEligible = isVetoEligible;
	}
	public String getSendToEDWS() {
		return sendToEDWS;
	}
	public void setSendToEDWS(String sendToEDWS) {
		this.sendToEDWS = sendToEDWS;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateApprovalStatusData [caseId=");
		builder.append(caseId);
		builder.append(", isClose=");
		builder.append(isClose);
		builder.append(", isVetoEligible=");
		builder.append(isVetoEligible);
		builder.append(", sendToEDWS=");
		builder.append(sendToEDWS);
		builder.append("]");
		return builder.toString();
	}	
}
