package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class FollowDocHistoryDataM implements Serializable,Cloneable{
	public FollowDocHistoryDataM(){
		super();
	}
	private String applicationGroupId;//ORIG_FOLLOW_DOC_HISTORY.APPLICATION_GROUP_ID
	private String  docReason;//ORIG_FOLLOW_DOC_HISTORY.DOC_REASON
	private String documentCode;//ORIG_FOLLOW_DOC_HISTORY.DOCUMENT_CODE
	private int  followupSeq;//ORIG_FOLLOW_DOC_HISTORY.FOLLOWUP_SEQ
	private Timestamp createDate;//ORIG_FOLLOW_DOC_HISTORY.CREATE_DATE
	private String createBy;//ORIG_FOLLOW_DOC_HISTORY.CREATE_BY
	private Timestamp updateDate;//ORIG_FOLLOW_DOC_HISTORY.UPDATE_DATE
	private String updateBy;//ORIG_FOLLOW_DOC_HISTORY.UPDATE_BY
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getDocReason() {
		return docReason;
	}
	public void setDocReason(String docReason) {
		this.docReason = docReason;
	}
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public int getFollowupSeq() {
		return followupSeq;
	}
	public void setFollowupSeq(int followupSeq) {
		this.followupSeq = followupSeq;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
}
