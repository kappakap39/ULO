package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class AttachmentDataM implements Serializable,Cloneable{
	public AttachmentDataM(){
		super();
	}
	private String applicationGroupId;	//ORIG_ATTACHMENT_HISTORY.APPLICATION_GROUP_ID(VARCHAR2)
	private String attachId;	//ORIG_ATTACHMENT_HISTORY.ATTACH_ID(VARCHAR2)
	private String fileName;	//ORIG_ATTACHMENT_HISTORY.FILE_NAME(VARCHAR2)
	private String fileType;	//ORIG_ATTACHMENT_HISTORY.FILE_TYPE(VARCHAR2)
	private String filePath;	//ORIG_ATTACHMENT_HISTORY.FILE_PATH(VARCHAR2)
	private String mimeType;	//ORIG_ATTACHMENT_HISTORY.MIME_TYPE(VARCHAR2)
	private BigDecimal fileSize;	//ORIG_ATTACHMENT_HISTORY.FILE_SIZE(NUMBER)
	private String createBy;	//ORIG_ATTACHMENT_HISTORY.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_ATTACHMENT_HISTORY.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_ATTACHMENT_HISTORY.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_ATTACHMENT_HISTORY.UPDATE_DATE(DATE)
	private String fileTypeOth;	//ORIG_ATTACHMENT_HISTORY.FILE_TYPE_OTH(VARCHAR2)
	private String refId;	//ORIG_ATTACHMENT_HISTORY.REF_ID(VARCHAR2)
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public BigDecimal getFileSize() {
		return fileSize;
	}
	public void setFileSize(BigDecimal fileSize) {
		this.fileSize = fileSize;
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
	public String getFileTypeOth() {
		return fileTypeOth;
	}
	public void setFileTypeOth(String fileTypeOth) {
		this.fileTypeOth = fileTypeOth;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}	
	
}
