package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RejectLetterDataM  implements Serializable, Cloneable{
	public RejectLetterDataM(){
		super();
	}
	private String applicationGroupId;
	private String applicationType;
	private String applicationTemplateId;
	private String emailPriority;
	private String paperPriority;
	private String subEmailPriority;
	private String subPaperPriority;
	private String sendTo;
	private int lifeCycle;	
	private String notificationtype;
	private String reasonCode;
	private String cashTransferType; 
	private String mobileNo;
	private String emailPrimary;
	private String saleChannel;
	private String salesId;
	private String saleFlag;
	private ArrayList<RejectLetterApplicationDataM> rejectLetterApplications;	
	
	public String getApplicationGroupId(){
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId){
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationType(){
		return applicationType;
	}
	public void setApplicationType(String applicationType){
		this.applicationType = applicationType;
	}
	public void addRejectLetterApplications(RejectLetterApplicationDataM rejectLetterApplicationDataM){
		if(null==rejectLetterApplications){
			rejectLetterApplications = new ArrayList<RejectLetterApplicationDataM>();
		}
		rejectLetterApplications.add(rejectLetterApplicationDataM);
	}
	
	public ArrayList<String> getProducts(){
		ArrayList<String> products = new ArrayList<String>();
		if(null!=rejectLetterApplications){
			for(RejectLetterApplicationDataM rejectLetterApplicationDataM : rejectLetterApplications){
				String product = rejectLetterApplicationDataM.getProduct();
				if(!products.contains(product) && null!=product){
					products.add(product);
				}
			}
		}
		return products;
	}
	public ArrayList<String> getCardCodes(){
		ArrayList<String> cardCodes = new ArrayList<String>();
		if(null!=rejectLetterApplications){
			for(RejectLetterApplicationDataM rejectLetterApplicationDataM : rejectLetterApplications){
				String cardCode = rejectLetterApplicationDataM.getCardCode();
				if(!cardCodes.contains(cardCode) && null!=cardCode){
					cardCodes.add(cardCode);
				}
			}
		}
		return cardCodes;
	}
	
	public ArrayList<String> getBusinessClassList(){
		ArrayList<String> businessClassList = new ArrayList<String>();
		if(null!=rejectLetterApplications){
			for(RejectLetterApplicationDataM rejectLetterApplicationDataM : rejectLetterApplications){
				String businessClass = rejectLetterApplicationDataM.getBusinessClassId();
				if(!businessClassList.contains(businessClass) && null!=businessClass){
					businessClassList.add(businessClass);
				}
			}
		}
		return businessClassList;
	}
	public String getApplicationTemplateId(){
		return applicationTemplateId;
	}
	public void setApplicationTemplateId(String applicationTemplateId){
		this.applicationTemplateId = applicationTemplateId;
	}
	public int getLifeCycle(){
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle){
		this.lifeCycle = lifeCycle;
	}
	public ArrayList<RejectLetterApplicationDataM> getRejectLetterApplications(){
		return rejectLetterApplications;
	}
	public void setRejectLetterApplications(
			ArrayList<RejectLetterApplicationDataM> rejectLetterApplications){
		this.rejectLetterApplications = rejectLetterApplications;
	}
	public void setSubEmailPriority(String subEmailPriority){
		this.subEmailPriority = subEmailPriority;
	}
	public String getEmailPriority(){
		return emailPriority;
	}
	public void setEmailPriority(String emailPriority){
		this.emailPriority = emailPriority;
	}
	public String getPaperPriority(){
		return paperPriority;
	}
	public void setPaperPriority(String paperPriority){
		this.paperPriority = paperPriority;
	}
	public String getSubPaperPriority(){
		return subPaperPriority;
	}
	public void setSubPaperPriority(String subPaperPriority){
		this.subPaperPriority = subPaperPriority;
	}
	public String getSubEmailPriority(){
		return subEmailPriority;
	}
	public String getSendTo(){
		return sendTo;
	}
	public void setSendTo(String sendTo){
		this.sendTo = sendTo;
	}
	public String getNotificationtype(){
		return notificationtype;
	}
	public void setNotificationtype(String notificationtype){
		this.notificationtype = notificationtype;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getCashTransferType() {
		return cashTransferType;
	}
	public void setCashTransferType(String cashTransferType) {
		this.cashTransferType = cashTransferType;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailPrimary() {
		return emailPrimary;
	}
	public void setEmailPrimary(String emailPrimary) {
		this.emailPrimary = emailPrimary;
	}
	public String getSaleChannel() {
		return saleChannel;
	}
	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}
	public String getSalesId() {
		return salesId;
	}
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	public String getSaleFlag() {
		return saleFlag;
	}
	public void setSaleFlag(String saleFlag) {
		this.saleFlag = saleFlag;
	}
}
