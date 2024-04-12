package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class NCBDocumentHistoryDataM implements Serializable,Cloneable{
	public NCBDocumentHistoryDataM(){
		super();
	}
	private String ncbDocHistoryId;	//ORIG_NCB_DOCUMENT_HISTORY.NCB_DOC_HISTORY_ID(VARCHAR2)
	private String personalId;	//ORIG_NCB_DOCUMENT_HISTORY.PERSONAL_ID(VARCHAR2)
	private String imgId;	//ORIG_NCB_DOCUMENT_HISTORY.IMG_ID(VARCHAR2)
	private String documentCode;	//ORIG_NCB_DOCUMENT_HISTORY.DOCUMENT_CODE(VARCHAR2)
	private String consentRefNo;	//ORIG_NCB_DOCUMENT_HISTORY.CONSENT_REF_NO(VARCHAR2)
	private Date consentGenDate;	//ORIG_NCB_DOCUMENT_HISTORY.CONSENT_GEN_DATE(VARCHAR2)
	private String createBy;	//ORIG_NCB_DOCUMENT_HISTORY.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_NCB_DOCUMENT_HISTORY.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_NCB_DOCUMENT_HISTORY.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_NCB_DOCUMENT_HISTORY.UPDATE_DATE(DATE)
	private String bureauRequiredFlag;// BUREAU_REQUIRED_FLAG
	
	public String getNcbDocHistoryId() {
		return ncbDocHistoryId;
	}
	public void setNcbDocHistoryId(String ncbDocHistoryId) {
		this.ncbDocHistoryId = ncbDocHistoryId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getImgId() {
		return imgId;
	}
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public String getConsentRefNo() {
		return consentRefNo;
	}
	public void setConsentRefNo(String consentRefNo) {
		this.consentRefNo = consentRefNo;
	}
	public Date getConsentGenDate() {
		return consentGenDate;
	}
	public void setConsentGenDate(Date consentGenDate) {
		this.consentGenDate = consentGenDate;
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
	public String getBureauRequiredFlag() {
		return bureauRequiredFlag;
	}
	public void setBureauRequiredFlag(String bureauRequiredFlag) {
		this.bureauRequiredFlag = bureauRequiredFlag;
	}	
}
