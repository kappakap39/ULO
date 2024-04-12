package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class ApplicationImageSplitDataM implements Serializable,Cloneable{
	public ApplicationImageSplitDataM(){
		super();
	}
	private String appImgId;	//ORIG_APPLICATION_IMG_SPLIT.APP_IMG_ID(VARCHAR2)
	private String imgPageId;	//ORIG_APPLICATION_IMG_SPLIT.IMG_PAGE_ID(VARCHAR2)
	private String imageId;	//ORIG_APPLICATION_IMG_SPLIT.IMG_ID(VARCHAR2)
	private int seq;	//ORIG_APPLICATION_IMG_SPLIT.SEQ(NUMBER)
	private String docTypeGroup; //ORIG_APPLICATION_IMG_SPLIT.DOCUMENT_CATEGORY(VARCHAR2)
	private String docType;	//ORIG_APPLICATION_IMG_SPLIT.DOC_TYPE(VARCHAR2)
	private int docTypeSeq;	//ORIG_APPLICATION_IMG_SPLIT.DOC_TYPE_SEQ(NUMBER)
	private String expiryFlag;	//ORIG_APPLICATION_IMG_SPLIT.EXPIRY_FLAG(VARCHAR2)
	private String createBy;	//ORIG_APPLICATION_IMG_SPLIT.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_APPLICATION_IMG_SPLIT.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_APPLICATION_IMG_SPLIT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_APPLICATION_IMG_SPLIT.UPDATE_DATE(DATE)
	private String personalId;	//ORIG_APPLICATION_IMG_SPLIT.PERSONAL_ID(VARCHAR2)
	private String applicantTypeIM; //APPLICANT_TYPE_IM
	private String fixedZoneFlag; //FIXED_ZONE_FLAG
	
	public String getAppImgId() {
		return appImgId;
	}
	public void setAppImgId(String appImgId) {
		this.appImgId = appImgId;
	}
	public String getImgPageId() {
		return imgPageId;
	}
	public void setImgPageId(String imgPageId) {
		this.imgPageId = imgPageId;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getDocTypeGroup() {
		return docTypeGroup;
	}
	public void setDocTypeGroup(String docTypeGroup) {
		this.docTypeGroup = docTypeGroup;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public int getDocTypeSeq() {
		return docTypeSeq;
	}
	public void setDocTypeSeq(int docTypeSeq) {
		this.docTypeSeq = docTypeSeq;
	}
	public String getExpiryFlag() {
		return expiryFlag;
	}
	public void setExpiryFlag(String expiryFlag) {
		this.expiryFlag = expiryFlag;
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
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getApplicantTypeIM() {
		return applicantTypeIM;
	}
	public void setApplicantTypeIM(String applicantTypeIM) {
		this.applicantTypeIM = applicantTypeIM;
	}
	public String getFixedZoneFlag() {
		return fixedZoneFlag;
	}
	public void setFixedZoneFlag(String fixedZoneFlag) {
		this.fixedZoneFlag = fixedZoneFlag;
	}
}
