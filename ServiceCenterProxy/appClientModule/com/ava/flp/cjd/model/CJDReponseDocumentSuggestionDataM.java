package com.ava.flp.cjd.model;

import java.io.Serializable;

public class CJDReponseDocumentSuggestionDataM implements Serializable, Cloneable{
	public CJDReponseDocumentSuggestionDataM(){
		super();
	}
	private String documentType;
	private String reason;
	private String remark;
	
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
