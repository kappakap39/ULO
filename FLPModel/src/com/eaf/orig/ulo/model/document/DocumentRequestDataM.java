package com.eaf.orig.ulo.model.document;

import java.io.Serializable;

public class DocumentRequestDataM implements Serializable,Cloneable{
	public DocumentRequestDataM(){
		super();
	}
	private String receivedFlag;
	private int seq;
	private String applicantType;
	private String documentCode;
	private String personalID;
	private String docTypeId;
	public String getReceivedFlag() {
		return receivedFlag;
	}
	public void setReceivedFlag(String receivedFlag) {
		this.receivedFlag = receivedFlag;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public String getPersonalID() {
		return personalID;
	}
	public void setPersonalID(String personalID) {
		this.personalID = personalID;
	}
	public String getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(String docTypeId) {
		this.docTypeId = docTypeId;
	}	
}
