package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RejectLetterLogDataM implements Serializable, Cloneable{
	public RejectLetterLogDataM(){
		super();
	}
	private ArrayList<String> aplicationRecordIds;
	private String letterNo;
	private String letterType;
	private String language;
	private String applicationGroupId;
	private String sendTo;
	private String interfaceCode;
	private String interfaceStatus;
	private int lifeCycle;
	private String subInterfaceCode;
	private String templateId;
	public ArrayList<String> getAplicationRecordIds() {
		return aplicationRecordIds;
	}
	public void setAplicationRecordIds(ArrayList<String> aplicationRecordIds) {
		this.aplicationRecordIds = aplicationRecordIds;
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
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
	public String getInterfaceStatus() {
		return interfaceStatus;
	}
	public void setInterfaceStatus(String interfaceStatus) {
		this.interfaceStatus = interfaceStatus;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	public String getSubInterfaceCode() {
		return subInterfaceCode;
	}
	public void setSubInterfaceCode(String subInterfaceCode) {
		this.subInterfaceCode = subInterfaceCode;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
}
