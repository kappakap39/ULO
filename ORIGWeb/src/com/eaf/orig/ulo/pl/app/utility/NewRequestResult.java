package com.eaf.orig.ulo.pl.app.utility;

import com.eaf.orig.shared.util.OrigUtil;

public class NewRequestResult {
	
	protected String tranNo;
    protected String consentRefNo;
    protected String idNo;
    protected String statusCode;
    protected String statusDesc;
    
	public String getTranNo() {
		return tranNo;
	}
	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
	}
	public String getConsentRefNo() {
		return consentRefNo;
	}
	public void setConsentRefNo(String consentRefNo) {
		this.consentRefNo = consentRefNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
//	public void setDataToModel(org.tempuri.NewRequestResult newRequestResult){
//		if(!OrigUtil.isEmptyObject(newRequestResult)){
//			this.tranNo = newRequestResult.getTranNo();
//		    this.consentRefNo = newRequestResult.getConsentRefNo();
//		    this.idNo = newRequestResult.getIdNo();
//		    this.statusCode = newRequestResult.getStatusCode();
//		    this.statusDesc = newRequestResult.getStatusDesc();
//		}
//	}
	
	@Override
	public String toString() {
		StringBuilder strB = new StringBuilder();
		strB.append("NewRequestResult [tranNo=").append(tranNo).append(", consentRefNo=").append(consentRefNo);
		strB.append(", idNo=").append(idNo).append(", statusCode=").append(statusCode);
		strB.append(", statusDesc=").append(statusDesc).append("]");
		return strB.toString();
	}

}
