package com.eaf.service.common.activemq.model;

public class SSFulfillmentInfoDefault implements SSFulfillmentInfo {

	private static final long serialVersionUID = 8268533203654011580L;

	private SSAttachDocListSegment AttachDocList;

	private SSRequiredDocListSegment RequiredDocList;

	private SSOptionalDocListSegment OptionalDocList;

	private String FLPAppRefNo;

	private SSContactPersonInfoSegment ContactPersonInfo;

	private SSCisSegment CisList;

	private String LPMNo;
	
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
	private String AttachmentFlag;
	private String DocumentCreationDate;
	private String DocumentChronicleID;
	private String DocumentType;
	private String DocumentName;
	private String DocumentFormat;

	

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

	public String getAttachmentFlag() {
		return AttachmentFlag;
	}

	public void setAttachmentFlag(String attachmentFlag) {
		AttachmentFlag = attachmentFlag;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SSAttachDocListSegment getAttachDocList() {
		return AttachDocList;
	}

	public void setAttachDocList(SSAttachDocListSegment attachDocList) {
		AttachDocList = attachDocList;
	}

	public SSRequiredDocListSegment getRequiredDocList() {
		return RequiredDocList;
	}

	public void setRequiredDocList(SSRequiredDocListSegment requiredDocList) {
		RequiredDocList = requiredDocList;
	}

	public SSOptionalDocListSegment getOptionalDocList() {
		return OptionalDocList;
	}

	public void setOptionalDocList(SSOptionalDocListSegment optionalDocList) {
		OptionalDocList = optionalDocList;
	}

	public String getFLPAppRefNo() {
		return FLPAppRefNo;
	}

	public void setFLPAppRefNo(String fLPAppRefNo) {
		FLPAppRefNo = fLPAppRefNo;
	}

	public SSContactPersonInfoSegment getContactPersonInfo() {
		return ContactPersonInfo;
	}

	public void setContactPersonInfo(SSContactPersonInfoSegment contactPersonInfo) {
		ContactPersonInfo = contactPersonInfo;
	}

	public SSCisSegment getCisList() {
		return CisList;
	}

	public void setCisList(SSCisSegment cisList) {
		CisList = cisList;
	}

	public String getLPMNo() {
		return LPMNo;
	}

	public void setLPMNo(String lPMNo) {
		LPMNo = lPMNo;
	}
}
