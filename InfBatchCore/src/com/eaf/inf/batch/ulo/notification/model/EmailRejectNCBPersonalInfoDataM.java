package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;

public class EmailRejectNCBPersonalInfoDataM implements Serializable, Cloneable{
	private String applicationGroupId;
	private String personalId;
	private String applicationNo;
	private String finalAppDecision;
	private String personalType;
	private String productName;
	private String reasonCode;
	private String saleChannel;
	private String recommendChannel;
	public String getApplicationGroupId(){
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId){
		this.applicationGroupId = applicationGroupId;
	}
	public String getPersonalId(){
		return personalId;
	}
	public void setPersonalId(String personalId){
		this.personalId = personalId;
	}
	public String getApplicationNo(){
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo){
		this.applicationNo = applicationNo;
	}
	public String getFinalAppDecision(){
		return finalAppDecision;
	}
	public void setFinalAppDecision(String finalAppDecision){
		this.finalAppDecision = finalAppDecision;
	}
	public String getPersonalType(){
		return personalType;
	}
	public void setPersonalType(String personalType){
		this.personalType = personalType;
	}
	public String getProductName(){
		return productName;
	}
	public void setProductName(String productName){
		this.productName = productName;
	}
	public String getReasonCode(){
		return reasonCode;
	}
	public void setReasonCode(String reasonCode){
		this.reasonCode = reasonCode;
	}
	public String getSaleChannel() {
		return saleChannel;
	}
	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}
	public String getRecommendChannel() {
		return recommendChannel;
	}
	public void setRecommendChannel(String recommendChannel) {
		this.recommendChannel = recommendChannel;
	}
}
