package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class RejectLetterInterfaceResponse implements Serializable, Cloneable{
	public RejectLetterInterfaceResponse() {
		super();
	}
	
	private String resultCode;
	private String resultDesc;
	private String contentT1;
	private String contentT2;
	private String contentPDF;
	private String interfaceCode;
	private String subInterfaceCode;
	private String letterNo;
	private String letterType;
	private String languageFlag;
	private Object interfaceObject;
	private RejectLetterSortingContent rejectLetterSortingContent;
	private RejectLetterLogDataM rejectLetterLogDataM;
	
	//KPL Additional
	private String contentT3;
	private int totalRecord;
	
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public String getContentT3() {
		return contentT3;
	}
	public void setContentT3(String contentT3) {
		this.contentT3 = contentT3;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	} 
	public Object getInterfaceObject() {
		return interfaceObject;
	}
	public void setInterfaceObject(Object interfaceObject) {
		this.interfaceObject = interfaceObject;
	}	
	public String getContentT1() {
		return contentT1;
	}
	public void setContentT1(String contentT1) {
		this.contentT1 = contentT1;
	}
	public String getContentT2() {
		return contentT2;
	}
	public void setContentT2(String contentT2) {
		this.contentT2 = contentT2;
	}
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public String getLetterType() {
		return letterType;
	}
	public void setLetterType(String letterType) {
		this.letterType = letterType;
	}
	public String getLanguageFlag() {
		return languageFlag;
	}
	public void setLanguageFlag(String languageFlag) {
		this.languageFlag = languageFlag;
	}
	public String getContentPDF() {
		return contentPDF;
	}
	public void setContentPDF(String contentPDF) {
		this.contentPDF = contentPDF;
	}
	public RejectLetterSortingContent getRejectLetterSortingContent() {
		return rejectLetterSortingContent;
	}
	public void setRejectLetterSortingContent(
			RejectLetterSortingContent rejectLetterSortingContent) {
		this.rejectLetterSortingContent = rejectLetterSortingContent;
	}
	public RejectLetterLogDataM getRejectLetterLogDataM() {
		return rejectLetterLogDataM;
	}
	public void setRejectLetterLogDataM(RejectLetterLogDataM rejectLetterLogDataM) {
		this.rejectLetterLogDataM = rejectLetterLogDataM;
	}
	public String getSubInterfaceCode() {
		return subInterfaceCode;
	}
	public void setSubInterfaceCode(String subInterfaceCode) {
		this.subInterfaceCode = subInterfaceCode;
	}
}