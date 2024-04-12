package com.eaf.orig.ulo.model.doc;

import java.io.Serializable;
import java.sql.Date;

public class CreateDocSetRequest implements Serializable, Cloneable{
	private String FuncNm;
	private String RqUID;
	private Date RqDt;
	private String RqAppId;
	private String UserId;
	private String TerminalId;
	private String UserLangPref;
	private String CorrID;
	
	private String SetID;
	private String FormQRCode;
	private String FormatTypeNo;
	private String ITPurpose;
	private String FormLocation;
	private String ProductTypeNo;
	private String FormTypeNo;
	private String FormVersionNo;
	private String IPAddress;
	private String EventFlag;
	private String Priority;
	private String WebScanUser;
	private String DocumentCreationDate;
	private String DocumentChronicleID;
	private String DocumentType;
	private String DocumentName;
	private String DocumentFormat;
	
	public String getFuncNm() {
		return FuncNm;
	}
	public void setFuncNm(String funcNm) {
		FuncNm = funcNm;
	}
	public String getRqUID() {
		return RqUID;
	}
	public void setRqUID(String rqUID) {
		RqUID = rqUID;
	}
	public Date getRqDt() {
		return RqDt;
	}
	public void setRqDt(Date rqDt) {
		RqDt = rqDt;
	}
	public String getRqAppId() {
		return RqAppId;
	}
	public void setRqAppId(String rqAppId) {
		RqAppId = rqAppId;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getTerminalId() {
		return TerminalId;
	}
	public void setTerminalId(String terminalId) {
		TerminalId = terminalId;
	}
	public String getUserLangPref() {
		return UserLangPref;
	}
	public void setUserLangPref(String userLangPref) {
		UserLangPref = userLangPref;
	}
	public String getCorrID() {
		return CorrID;
	}
	public void setCorrID(String corrID) {
		CorrID = corrID;
	}
	public String getSetID() {
		return SetID;
	}
	public void setSetID(String setID) {
		SetID = setID;
	}
	public String getFormQRCode() {
		return FormQRCode;
	}
	public void setFormQRCode(String formQRCode) {
		FormQRCode = formQRCode;
	}
	public String getFormatTypeNo() {
		return FormatTypeNo;
	}
	public void setFormatTypeNo(String formatTypeNo) {
		FormatTypeNo = formatTypeNo;
	}
	public String getITPurpose() {
		return ITPurpose;
	}
	public void setITPurpose(String iTPurpose) {
		ITPurpose = iTPurpose;
	}
	public String getFormLocation() {
		return FormLocation;
	}
	public void setFormLocation(String formLocation) {
		FormLocation = formLocation;
	}
	public String getProductTypeNo() {
		return ProductTypeNo;
	}
	public void setProductTypeNo(String productTypeNo) {
		ProductTypeNo = productTypeNo;
	}
	public String getFormTypeNo() {
		return FormTypeNo;
	}
	public void setFormTypeNo(String formTypeNo) {
		FormTypeNo = formTypeNo;
	}
	public String getFormVersionNo() {
		return FormVersionNo;
	}
	public void setFormVersionNo(String formVersionNo) {
		FormVersionNo = formVersionNo;
	}
	public String getIPAddress() {
		return IPAddress;
	}
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	public String getEventFlag() {
		return EventFlag;
	}
	public void setEventFlag(String eventFlag) {
		EventFlag = eventFlag;
	}
	public String getPriority() {
		return Priority;
	}
	public void setPriority(String priority) {
		Priority = priority;
	}
	public String getWebScanUser() {
		return WebScanUser;
	}
	public void setWebScanUser(String webScanUser) {
		WebScanUser = webScanUser;
	}
	public String getDocumentCreationDate() {
		return DocumentCreationDate;
	}
	public void setDocumentCreationDate(String documentCreationDate) {
		DocumentCreationDate = documentCreationDate;
	}
	public String getDocumentChronicleID() {
		return DocumentChronicleID;
	}
	public void setDocumentChronicleID(String documentChronicleID) {
		DocumentChronicleID = documentChronicleID;
	}
	public String getDocumentType() {
		return DocumentType;
	}
	public void setDocumentType(String documentType) {
		DocumentType = documentType;
	}
	public String getDocumentName() {
		return DocumentName;
	}
	public void setDocumentName(String documentName) {
		DocumentName = documentName;
	}
	public String getDocumentFormat() {
		return DocumentFormat;
	}
	public void setDocumentFormat(String documentFormat) {
		DocumentFormat = documentFormat;
	}

}
