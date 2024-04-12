package com.eaf.orig.ulo.model.ia;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class ImageDocumentRequest implements Serializable,Cloneable{
	public ImageDocumentRequest(){
		super();
		kbankHeader = new KBankHeader();
		documentSegments = new ArrayList<DocumentSegment>();
	}
	private KBankHeader kbankHeader;
	private String qr1;
	private String qr2;
	private String rcCode;
	private String branchCode;
	private String channel;
	private String qrApplyType;
	private Date scanDate;
	private ArrayList<DocumentSegment> documentSegments;
	
	public KBankHeader getKbankHeader() {
		return kbankHeader;
	}
	public void setKbankHeader(KBankHeader kbankHeader) {
		this.kbankHeader = kbankHeader;
	}
	public String getQr1() {
		return qr1;
	}
	public void setQr1(String qr1) {
		this.qr1 = qr1;
	}
	public String getQr2() {
		return qr2;
	}
	public void setQr2(String qr2) {
		this.qr2 = qr2;
	}
	public String getRcCode() {
		return rcCode;
	}
	public void setRcCode(String rcCode) {
		this.rcCode = rcCode;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getQrApplyType() {
		return qrApplyType;
	}
	public void setQrApplyType(String qrApplyType) {
		this.qrApplyType = qrApplyType;
	}	
	public Date getScanDate() {
		return scanDate;
	}
	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}
	public ArrayList<DocumentSegment> getDocumentSegments() {
		return documentSegments;
	}
	public void setDocumentSegments(ArrayList<DocumentSegment> documentSegments) {
		this.documentSegments = documentSegments;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImageDocumentRequest [kbankHeader=");
		builder.append(kbankHeader);
		builder.append(", qr1=");
		builder.append(qr1);
		builder.append(", qr2=");
		builder.append(qr2);
		builder.append(", rcCode=");
		builder.append(rcCode);
		builder.append(", branchCode=");
		builder.append(branchCode);
		builder.append(", channel=");
		builder.append(channel);
		builder.append(", qrApplyType=");
		builder.append(qrApplyType);
		builder.append(", scanDate=");
		builder.append(scanDate);
		builder.append(", documentSegments=");
		builder.append(documentSegments);
		builder.append("]");
		return builder.toString();
	}	
}
