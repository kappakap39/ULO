package com.eaf.inf.batch.ulo.notification.condition;

import java.io.Serializable;
import java.util.ArrayList;

import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;

@SuppressWarnings("serial")
public class NotificationEODDataM implements Serializable,Cloneable{
	public NotificationEODDataM(){
		
	}
	private String applicationGroupNo;
	private String applicationGroupId;
	private String notificationType;
	private String appStatus;
	private String jobState;
	private String saleChannel;
	private String applicationTemplate;
	private String saleId;
	private String sendTime;
	private String sendTo;
	private String recommend;
	private String fixEmail;
	private String branchName;
	private String branchCode;
	private String recommendChannel;
	private String managerChannel;
	private int lifeCycle = 1;
	private ArrayList<JobCodeDataM> jobCodes;
	
	public String getApplicationGroupNo(){
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo){
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getApplicationGroupId(){
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId){
		this.applicationGroupId = applicationGroupId;
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
	public String getSaleChannel(){
		return saleChannel;
	}
	public void setSaleChannel(String saleChannel){
		this.saleChannel = saleChannel;
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
	public String getSendTime(){
		return sendTime;
	}
	public void setSendTime(String sendTime){
		this.sendTime = sendTime;
	}
	public String getSendTo(){
		return sendTo;
	}
	public void setSendTo(String sendTo){
		this.sendTo = sendTo;
	}
	public String getRecommend(){
		return recommend;
	}
	public void setRecommend(String recommend){
		this.recommend = recommend;
	}
	public String getFixEmail(){
		return fixEmail;
	}
	public void setFixEmail(String fixEmail){
		this.fixEmail = fixEmail;
	}
	public String getNotificationType(){
		return notificationType;
	}
	public void setNotificationType(String notificationType){
		this.notificationType = notificationType;
	}
	public ArrayList<JobCodeDataM> getJobCodes(){
		return jobCodes;
	}
	public void setJobCodes(ArrayList<JobCodeDataM> jobCodes){
		this.jobCodes = jobCodes;
	}
	public void addJobCodes(JobCodeDataM jobCode){
		if(null==jobCodes){
			jobCodes = new ArrayList<JobCodeDataM>();
		}
		jobCodes.add(jobCode);
	}
	public String getBranchName(){
		return branchName;
	}
	public void setBranchName(String branchName){
		this.branchName = branchName;
	}
	public String getRecommendChannel(){
		return recommendChannel;
	}
	public void setRecommendChannel(String recommendChannel){
		this.recommendChannel = recommendChannel;
	}
	public String getManagerChannel() {
		return managerChannel;
	}
	public void setManagerChannel(String managerChannel) {
		this.managerChannel = managerChannel;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotificationEODDataM [");
		if (applicationGroupNo != null) {
			builder.append("applicationGroupNo=");
			builder.append(applicationGroupNo);
			builder.append(", ");
		}
		if (applicationGroupId != null) {
			builder.append("applicationGroupId=");
			builder.append(applicationGroupId);
			builder.append(", ");
		}
		if (notificationType != null) {
			builder.append("notificationType=");
			builder.append(notificationType);
			builder.append(", ");
		}
		if (appStatus != null) {
			builder.append("appStatus=");
			builder.append(appStatus);
			builder.append(", ");
		}
		if (jobState != null) {
			builder.append("jobState=");
			builder.append(jobState);
			builder.append(", ");
		}
		if (saleChannel != null) {
			builder.append("saleChannel=");
			builder.append(saleChannel);
			builder.append(", ");
		}
		if (applicationTemplate != null) {
			builder.append("applicationTemplate=");
			builder.append(applicationTemplate);
			builder.append(", ");
		}
		if (saleId != null) {
			builder.append("saleId=");
			builder.append(saleId);
			builder.append(", ");
		}
		if (sendTime != null) {
			builder.append("sendTime=");
			builder.append(sendTime);
			builder.append(", ");
		}
		if (sendTo != null) {
			builder.append("sendTo=");
			builder.append(sendTo);
			builder.append(", ");
		}
		if (recommend != null) {
			builder.append("recommend=");
			builder.append(recommend);
			builder.append(", ");
		}
		if (fixEmail != null) {
			builder.append("fixEmail=");
			builder.append(fixEmail);
			builder.append(", ");
		}
		if (branchName != null) {
			builder.append("branchName=");
			builder.append(branchName);
			builder.append(", ");
		}
		if (branchCode != null) {
			builder.append("branchCode=");
			builder.append(branchCode);
			builder.append(", ");
		}
		if (recommendChannel != null) {
			builder.append("recommendChannel=");
			builder.append(recommendChannel);
			builder.append(", ");
		}
		if (managerChannel != null) {
			builder.append("managerChannel=");
			builder.append(managerChannel);
			builder.append(", ");
		}
		builder.append("lifeCycle=");
		builder.append(lifeCycle);
		builder.append(", ");
		if (jobCodes != null) {
			builder.append("jobCodes=");
			builder.append(jobCodes);
		}
		builder.append("]");
		return builder.toString();
	}
}
