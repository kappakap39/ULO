package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class NotifyEODApplicationDataM implements Cloneable, Serializable{
	private String applicationGroupId;
	private String applicationGroupNo;
	private String appStatus;
	private String jobState;
	private String applicationTemplate;
	private String saleId;
	private String saleChannel;
	private String salesBranchCode;
	private String salesBranchName;
	private String recommendId;
	private String recommendChannel;
	private String recommendBranchCode;
	private String recommendBranchName;
	private int lifeCycle;
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
	public String getAppStatus(){
		return appStatus;
	}
	public void setAppStatus(String appStatus){
		this.appStatus = appStatus;
	}
	public String getJobState(){
		return jobState;
	}
	public void setJobState(String jobState){
		this.jobState = jobState;
	}
	public String getApplicationTemplate(){
		return applicationTemplate;
	}
	public void setApplicationTemplate(String applicationTemplate){
		this.applicationTemplate = applicationTemplate;
	}
	public String getSaleId(){
		return saleId;
	}
	public void setSaleId(String saleId){
		this.saleId = saleId;
	}
	public String getSaleChannel(){
		return saleChannel;
	}
	public void setSaleChannel(String saleChannel){
		this.saleChannel = saleChannel;
	}
	public String getSalesBranchCode(){
		return salesBranchCode;
	}
	public void setSalesBranchCode(String salesBranchCode){
		this.salesBranchCode = salesBranchCode;
	}
	public String getSalesBranchName(){
		return salesBranchName;
	}
	public void setSalesBranchName(String salesBranchName){
		this.salesBranchName = salesBranchName;
	}
	public String getRecommendId(){
		return recommendId;
	}
	public void setRecommendId(String recommendId){
		this.recommendId = recommendId;
	}
	public String getRecommendChannel(){
		return recommendChannel;
	}
	public void setRecommendChannel(String recommendChannel){
		this.recommendChannel = recommendChannel;
	}
	public String getRecommendBranchCode(){
		return recommendBranchCode;
	}
	public void setRecommendBranchCode(String recommendBranchCode){
		this.recommendBranchCode = recommendBranchCode;
	}
	public String getRecommendBranchName(){
		return recommendBranchName;
	}
	public void setRecommendBranchName(String recommendBranchName){
		this.recommendBranchName = recommendBranchName;
	}
	public int getLifeCycle(){
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle){
		this.lifeCycle = lifeCycle;
	}
}