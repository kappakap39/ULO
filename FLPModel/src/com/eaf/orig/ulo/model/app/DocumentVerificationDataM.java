package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class DocumentVerificationDataM implements Serializable,Cloneable{
	public DocumentVerificationDataM(){
		super();
	}
	private String documentVerId;	//XRULES_DOCUMENT_VERIFICATION.DOCUMENT_VER_ID(VARCHAR2)
	private String verResultId;	//XRULES_DOCUMENT_VERIFICATION.VER_RESULT_ID(VARCHAR2)
	private String followingType;	//XRULES_DOCUMENT_VERIFICATION.FOLLOWING_TYPE(VARCHAR2)
	private String verResult;	//XRULES_DOCUMENT_VERIFICATION.VER_RESULT(VARCHAR2)
	private String createBy;	//XRULES_DOCUMENT_VERIFICATION.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_DOCUMENT_VERIFICATION.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_DOCUMENT_VERIFICATION.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_DOCUMENT_VERIFICATION.UPDATE_DATE(DATE)
	
	public String getDocumentVerId() {
		return documentVerId;
	}
	public void setDocumentVerId(String documentVerId) {
		this.documentVerId = documentVerId;
	}
	public String getVerResultId() {
		return verResultId;
	}
	public void setVerResultId(String verResultId) {
		this.verResultId = verResultId;
	}
	public String getFollowingType() {
		return followingType;
	}
	public void setFollowingType(String followingType) {
		this.followingType = followingType;
	}
	public String getVerResult() {
		return verResult;
	}
	public void setVerResult(String verResult) {
		this.verResult = verResult;
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
