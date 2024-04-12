package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;

@SuppressWarnings("serial")
public class NotificationInfoDataM implements Serializable,Cloneable{
	public NotificationInfoDataM(){
		super();
	}
	public static final String DEFAULT_REJECT_REASON = "DEFAULT";
	public static final String COMPLIANCE_REJECT_REASON = "COMPLIANCE";
	private String applicationGroupId;
	private String applicationGroupNo;
	private int maxLifeCycle;
	private String applicationStatus;
	private String applyType;
	private String applicationType;
	private String applicationTemplate;
	private String sendingTime;
	private String saleId;
	private String saleRecommend;
	private String saleChanel;
	private String saleType;
	private boolean isSendToCash1hour;
	private boolean isSendToCompliance;
	private HashMap<String,ArrayList<JobCodeDataM>> jobCodes;
	private ArrayList<String> emailElement;
	private ArrayList<String> mobileElement;
	private ArrayList<String> sendTos;
	private HashMap<String,ArrayList<String>> rejectReasonCodes;
	private ArrayList<String> cashTransTypes;
	
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getSendingTime() {
		return sendingTime;
	}
	public void setSendingTime(String sendingTime) {
		this.sendingTime = sendingTime;
	}
	public String getSaleId() {
		return saleId;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	public String getSaleChanel() {
		return saleChanel;
	}
	public void setSaleChanel(String saleChanel) {
		this.saleChanel = saleChanel;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getApplicationTemplate() {
		return applicationTemplate;
	}
	public void setApplicationTemplate(String applicationTemplate) {
		this.applicationTemplate = applicationTemplate;
	}
	public ArrayList<String> getEmailElement() {
		return emailElement;
	}
	public void setEmailElement(ArrayList<String> emailElement) {
		this.emailElement = emailElement;
	}
	public ArrayList<String> getMobileElement() {
		return mobileElement;
	}
	public void setMobileElement(ArrayList<String> mobileElement) {
		this.mobileElement = mobileElement;
	}
	public HashMap<String, ArrayList<JobCodeDataM>> getJobCodes() {
		return jobCodes;
	}
	public void setJobCodes(HashMap<String, ArrayList<JobCodeDataM>> jobCodes) {
		this.jobCodes = jobCodes;
	}
	public ArrayList<String> getSendTos() {
		return sendTos;
	}
	public void setSendTos(ArrayList<String> sendTos) {
		this.sendTos = sendTos;
	}
	public boolean isSendToCash1hour() {
		return isSendToCash1hour;
	}
	public void setSendToCash1hour(boolean isSendToCash1hour) {
		this.isSendToCash1hour = isSendToCash1hour;
	}
	public boolean isSendToCompliance() {
		return isSendToCompliance;
	}
	public void setSendToCompliance(boolean isSendToCompliance) {
		this.isSendToCompliance = isSendToCompliance;
	}
	public void addSendTo(String sendTo) {
		if(null==sendTos){
			sendTos = new ArrayList<String>();
		}
		if(!sendTos.contains(sendTo)){
			sendTos.add(sendTo);
		}
	}		
	public int getMaxLifeCycle() {
		return maxLifeCycle;
	}
	public void setMaxLifeCycle(int maxLifeCycle) {
		this.maxLifeCycle = maxLifeCycle;
	}
	public String getSaleRecommend() {
		return saleRecommend;
	}
	public void setSaleRecommend(String saleRecommend) {
		this.saleRecommend = saleRecommend;
	}
	public HashMap<String, ArrayList<String>> getRejectReasonCodes() {
		return rejectReasonCodes;
	}
	public ArrayList<String> getRejectAllReasonCodes(){
		ArrayList<String> filterRejectReasons = new ArrayList<String>();
		if(null!=rejectReasonCodes){
			for(ArrayList<String> rejectReasons : rejectReasonCodes.values()){
				if(null!=rejectReasons){
					for(String rejectReason : rejectReasons){
						if(null!=rejectReason&&!filterRejectReasons.contains(rejectReason)){
							filterRejectReasons.add(rejectReason);
						}
					}
				}
			}
		}
		return filterRejectReasons;
	}
	public void setRejectReasonCodes(
			HashMap<String, ArrayList<String>> rejectReasonCodes) {
		this.rejectReasonCodes = rejectReasonCodes;
	}
	public String emailsToString() {
		StringBuilder emails = new StringBuilder("");
		if(null!=emailElement){
			String COMMA="";
			for(String email : emailElement){
				emails.append(COMMA+email);
				COMMA=",";
			}
		}
		return emails.toString(); 
	}
	public ArrayList<String> getCashTransTypes() {
		return cashTransTypes;
	}
	public void setCashTransTypes(ArrayList<String> cashTransTypes) {
		this.cashTransTypes = cashTransTypes;
	} 
	
	public void addCashTransType(String cashTransType) {
		if(null==cashTransTypes){
			cashTransTypes = new ArrayList<String>();
		}
		if(!cashTransTypes.contains(cashTransType)){
			cashTransTypes.add(cashTransType);	
		}
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public boolean isSendEmail(){
		String NOTIFICATION_TYPE_VALUE_EMAIL = InfBatchProperty.getInfBatchConfig("NOTIFICATION_TYPE_VALUE_EMAIL");
		String NOTIFICATION_SEND_TO_TYPE_COMPLIANCE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_COMPLIANCE");
		for(String key : this.jobCodes.keySet()){
    		ArrayList<JobCodeDataM> jobCodes = this.jobCodes.get(key);
    		if(!InfBatchUtil.empty(jobCodes)){
    			for(JobCodeDataM jobCode : jobCodes){
    				String notificationType = jobCode.getNotificationType();
    				String sendTo = jobCode.getSendTo();
    				if(NOTIFICATION_TYPE_VALUE_EMAIL.equals(notificationType) && !NOTIFICATION_SEND_TO_TYPE_COMPLIANCE.equals(sendTo)){
    					return true;
    				}
    			}
    		}
    	}
		return false;
	}
}
