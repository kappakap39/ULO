package com.eaf.orig.ulo.model.ia;

import java.io.Serializable;

public class DocumentSegment implements Serializable,Cloneable{
	public DocumentSegment(){
		super();
	}
	private int seq;
	private String imageId;
	private int docTypeSeq;
	private String docTypeCode;
	private String docTypeGroup;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public int getDocTypeSeq() {
		return docTypeSeq;
	}
	public void setDocTypeSeq(int docTypeSeq) {
		this.docTypeSeq = docTypeSeq;
	}
	public String getDocTypeCode() {
		return docTypeCode;
	}
	public void setDocTypeCode(String docTypeCode) {
		this.docTypeCode = docTypeCode;
	}
	public String getDocTypeGroup() {
		return docTypeGroup;
	}
	public void setDocTypeGroup(String docTypeGroup) {
		this.docTypeGroup = docTypeGroup;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DocumentSegment [seq=");
		builder.append(seq);
		builder.append(", imageId=");
		builder.append(imageId);
		builder.append(", docTypeSeq=");
		builder.append(docTypeSeq);
		builder.append(", docTypeCode=");
		builder.append(docTypeCode);
		builder.append(", docTypeGroup=");
		builder.append(docTypeGroup);
		builder.append("]");
		return builder.toString();
	}	
}
