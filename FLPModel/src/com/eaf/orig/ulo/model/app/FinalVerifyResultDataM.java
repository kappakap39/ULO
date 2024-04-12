package com.eaf.orig.ulo.model.app;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FinalVerifyResultDataM implements Serializable,Cloneable{
	public static enum VerifyId{VERIFY_CUSTOMER,VERIFY_HR,VERIFY_INCOME,VERIFY_WEB,VERIFY_PROJECT_CODE};
	private VerifyId verifyId;
	private String verifyResultCode;
	private String verifyResultDesc;	
	public VerifyId getVerifyId() {
		return verifyId;
	}
	public void setVerifyId(VerifyId verifyId) {
		this.verifyId = verifyId;
	}
	public String getVerifyResultCode() {
		return verifyResultCode;
	}
	public void setVerifyResultCode(String verifyResultCode) {
		this.verifyResultCode = verifyResultCode;
	}
	public String getVerifyResultDesc() {
		return verifyResultDesc;
	}
	public void setVerifyResultDesc(String verifyResultDesc) {
		this.verifyResultDesc = verifyResultDesc;
	}	
}
