package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class RequiredDocDetailDataM implements Serializable,Cloneable{
	public RequiredDocDetailDataM(){
		super();
	}
	private String requiredDocDetailId;	//XRULES_REQUIRED_DOC_DETAIL.REQUIRED_DOC_DETAIL_ID(VARCHAR2)
	private String requiredDocId;	//XRULES_REQUIRED_DOC_DETAIL.REQUIRED_DOC_ID(VARCHAR2)
	private String documentCode;	//XRULES_REQUIRED_DOC_DETAIL.DOCUMENT_CODE(VARCHAR2)
	private String createBy;	//XRULES_REQUIRED_DOC_DETAIL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_REQUIRED_DOC_DETAIL.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_REQUIRED_DOC_DETAIL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_REQUIRED_DOC_DETAIL.UPDATE_DATE(DATE)
	private String mandatoryFlag; //XRULES_REQUIRED_DOC_DETAIL.MANDATORY_FLAG(VARCHAR2)
	private String receivedFlag;
	
	public String getRequiredDocDetailId() {
		return requiredDocDetailId;
	}
	public void setRequiredDocDetailId(String requiredDocDetailId) {
		this.requiredDocDetailId = requiredDocDetailId;
	}
	public String getRequiredDocId() {
		return requiredDocId;
	}
	public void setRequiredDocId(String requiredDocId) {
		this.requiredDocId = requiredDocId;
	}
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getMandatoryFlag() {
		return mandatoryFlag;
	}
	public void setMandatoryFlag(String mandatoryFlag) {
		this.mandatoryFlag = mandatoryFlag;
	}
	public String getReceivedFlag() {
		return receivedFlag;
	}
	public void setReceivedFlag(String receivedFlag) {
		this.receivedFlag = receivedFlag;
	} 		
}
