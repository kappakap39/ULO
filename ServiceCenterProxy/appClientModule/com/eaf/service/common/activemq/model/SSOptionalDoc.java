package com.eaf.service.common.activemq.model;

import java.io.Serializable;

public class SSOptionalDoc implements Serializable {

	private static final long serialVersionUID = -3520784957432136844L;
	
	private String FLPRefId;
	private String DocNameEng;
	private String DocNameThai;
	private String DocTypeCode;
	
	public String getFLPRefId() {
		return FLPRefId;
	}
	public void setFLPRefId(String fLPRefId) {
		FLPRefId = fLPRefId;
	}
	public String getDocNameEng() {
		return DocNameEng;
	}
	public void setDocNameEng(String docNameEng) {
		DocNameEng = docNameEng;
	}
	public String getDocNameThai() {
		return DocNameThai;
	}
	public void setDocNameThai(String docNameThai) {
		DocNameThai = docNameThai;
	}
	public String getDocTypeCode() {
		return DocTypeCode;
	}
	public void setDocTypeCode(String docTypeCode) {
		this.DocTypeCode = docTypeCode;
	}	
	
}
