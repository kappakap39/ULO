package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class DocumentCommentDataM implements Serializable, Cloneable {
	public DocumentCommentDataM() {
		super();
		dirty = false;
	}

	private String applicationGroupId; // ORIG_DOCUMENT_COMMENT.APPLICATION_GROUP_ID(VARCHAR2)
	private String docCommentId; // ORIG_DOCUMENT_COMMENT.DOC_COMMENT_ID(VARCHAR2)
	private String commentDesc; // ORIG_DOCUMENT_COMMENT.COMMENT_DESC(VARCHAR2)
	private String status; // ORIG_DOCUMENT_COMMENT.STATUS(VARCHAR2)
	private String createBy; // ORIG_DOCUMENT_COMMENT.CREATE_BY(VARCHAR2)
	private Timestamp createDate; // ORIG_DOCUMENT_COMMENT.CREATE_DATE(DATE)
	private String updateBy; // ORIG_DOCUMENT_COMMENT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate; // ORIG_DOCUMENT_COMMENT.UPDATE_DATE(DATE)
	private boolean dirty; // This flag use to allow to delete

	public String getApplicationGroupId() {
		return applicationGroupId;
	}

	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}

	public String getDocCommentId() {
		return docCommentId;
	}

	public void setDocCommentId(String docCommentId) {
		this.docCommentId = docCommentId;
	}

	public String getCommentDesc() {
		return commentDesc;
	}

	public void setCommentDesc(String commentDesc) {
		this.commentDesc = commentDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty() {
		this.dirty = true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DocumentCommentDataM [applicationGroupId=");
		builder.append(applicationGroupId);
		builder.append(", docCommentId=");
		builder.append(docCommentId);
		builder.append(", commentDesc=");
		builder.append(commentDesc);
		builder.append(", status=");
		builder.append(status);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", dirty=");
		builder.append(dirty);
		builder.append("]");
		return builder.toString();
	}


}
