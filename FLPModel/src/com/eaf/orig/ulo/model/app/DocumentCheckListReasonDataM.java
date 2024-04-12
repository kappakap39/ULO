package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class DocumentCheckListReasonDataM implements Serializable,Cloneable{
	public DocumentCheckListReasonDataM(){
		super();
	}
	private String docCheckListId;	//ORIG_DOC_CHECK_LIST_REASON.DOC_CHECK_LIST_ID(VARCHAR2)
	private String docCheckListReasonId;	//ORIG_DOC_CHECK_LIST_REASON.DOC_CHECK_LIST_REASON_ID(VARCHAR2)
	private String docReason;	//ORIG_DOC_CHECK_LIST_REASON.DOC_REASON(VARCHAR2)
	private String generateFlag; //ORIG_DOC_CHECK_LIST_REASON.GENERATE_FLAG(VARCHAR2)
	private String createBy;	//ORIG_DOC_CHECK_LIST_REASON.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_DOC_CHECK_LIST_REASON.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_DOC_CHECK_LIST_REASON.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_DOC_CHECK_LIST_REASON.UPDATE_DATE(DATE)
	
	public String getDocCheckListId() {
		return docCheckListId;
	}
	public void setDocCheckListId(String docCheckListId) {
		this.docCheckListId = docCheckListId;
	}
	public String getDocCheckListReasonId() {
		return docCheckListReasonId;
	}
	public void setDocCheckListReasonId(String docCheckListReasonId) {
		this.docCheckListReasonId = docCheckListReasonId;
	}
	public String getDocReason() {
		return docReason;
	}
	public void setDocReason(String docReason) {
		this.docReason = docReason;
	}
	public String getGenerateFlag() {
		return generateFlag;
	}
	public void setGenerateFlag(String generateFlag) {
		this.generateFlag = generateFlag;
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
	
}
