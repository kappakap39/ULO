package com.eaf.service.common.activemq.model;

import java.io.Serializable;

public class SSRequiredDoc implements Serializable {

	private static final long serialVersionUID = 5839464282943286448L;

	private String FLPRefId;
	private String DocNameEng;
	private String DocNameThai;
	private String ReturnReason;
	private String SubReturnReason;
	private String Description;
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

	public String getReturnReason() {
		return ReturnReason;
	}

	public void setReturnReason(String returnReason) {
		ReturnReason = returnReason;
	}

	public String getSubReturnReason() {
		return SubReturnReason;
	}

	public void setSubReturnReason(String subReturnReason) {
		SubReturnReason = subReturnReason;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getDocTypeCode() {
		return DocTypeCode;
	}

	public void setDocTypeCode(String docTypeCode) {
		this.DocTypeCode = docTypeCode;
	}

}
