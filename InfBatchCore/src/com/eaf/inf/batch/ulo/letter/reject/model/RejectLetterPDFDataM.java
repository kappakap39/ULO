package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("serial")
public class RejectLetterPDFDataM  implements Serializable, Cloneable{
	public RejectLetterPDFDataM(){
		super();
	}
	private String applicationGroupId;
	private String applicationGroupNo;
	private String applicationType;
	private String reasonCode;
	private String letterType;
	private String letterNo;
	private String branchName;
	private String language;
	private String dateOfData;
	private String appTemplate;
	private String sendTo;
	private String saleId;
	private int lifeCycle;
	private String emailPriority;
	private String subEmailPriority;
	private ArrayList<String> applicationRecordIds;
	private ArrayList<RejectLetterPDFPersonalInfoDataM> personalInfos;
	private RejectLetterPDFPersonalInfoDataM rejectedPersonalInfo;
	private String functionType;
	public String getApplicationGroupId(){
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId){
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationGroupNo(){
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo){
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getApplicationType(){
		return applicationType;
	}
	public void setApplicationType(String applicationType){
		this.applicationType = applicationType;
	}
	public String getReasonCode(){
		return reasonCode;
	}
	public void setReasonCode(String reasonCode){
		this.reasonCode = reasonCode;
	}
	public String getLetterNo(){
		return letterNo;
	}
	public void setLetterNo(String letterNo){
		this.letterNo = letterNo;
	}
	public ArrayList<String> getApplicationRecordIds(){
		return applicationRecordIds;
	}
	public void setApplicationRecordIds(ArrayList<String> applicationRecordIds){
		this.applicationRecordIds = applicationRecordIds;
	}
	public String getLetterType(){
		return letterType;
	}
	public void setLetterType(String letterType){
		this.letterType = letterType;
	}
	public String getBranchName(){
		return branchName;
	}
	public void setBranchName(String branchName){
		this.branchName = branchName;
	}
	public String getLanguage(){
		return language;
	}
	public void setLanguage(String language){
		this.language = language;
	}
	public String getDateOfData(){
		return dateOfData;
	}
	public void setDateOfData(String dateOfData){
		this.dateOfData = dateOfData;
	}
	public String getTemplateId(){
		return appTemplate;
	}
	public void setTemplateId(String templateId){
		this.appTemplate = templateId;
	}
	public String getAppTemplate(){
		return appTemplate;
	}
	public void setAppTemplate(String appTemplate){
		this.appTemplate = appTemplate;
	}
	public String getSendTo(){
		return sendTo;
	}
	public void setSendTo(String sendTo){
		this.sendTo = sendTo;
	}
	public String getSaleId(){
		return saleId;
	}
	public void setSaleId(String saleId){
		this.saleId = saleId;
	}
	public int getLifeCycle(){
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle){
		this.lifeCycle = lifeCycle;
	}
	public String getEmailPriority(){
		return emailPriority;
	}
	public void setEmailPriority(String emailPriority){
		this.emailPriority = emailPriority;
	}
	public String getSubEmailPriority(){
		return subEmailPriority;
	}
	public void setSubEmailPriority(String subEmailPriority){
		this.subEmailPriority = subEmailPriority;
	}
	public ArrayList<RejectLetterPDFPersonalInfoDataM> getPersonalInfos() {
		return personalInfos;
	}
	public void setPersonalInfos(
			ArrayList<RejectLetterPDFPersonalInfoDataM> personalInfos) {
		this.personalInfos = personalInfos;
	}
	public RejectLetterPDFPersonalInfoDataM getRejectedPersonalInfo() {
		return rejectedPersonalInfo;
	}
	public void setRejectedPersonalInfo(
			RejectLetterPDFPersonalInfoDataM rejectedPersonalInfo) {
		this.rejectedPersonalInfo = rejectedPersonalInfo;
	}
	public List<String> findCashTransferTypePersonal(String personalId){
		List<String> cashTransferTypes = new ArrayList<String>();
		for(RejectLetterPDFPersonalInfoDataM personalInfo : this.personalInfos){
			if(personalInfo.equals(personalId)){
				String cashTransferType = personalInfo.getCashTransferType();
				cashTransferTypes.add(cashTransferType);
			}
		}
		return cashTransferTypes;
	}
	public String getFunctionType() {
		return functionType;
	}
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}
}