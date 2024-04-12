package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class DMNotificationDataM implements Serializable,Cloneable{
	public DMNotificationDataM(){
		super();
	}
	
	private String dmId;
	private String applicationGroupId;
	private int roundOfNotification;
	private String dmNotificationType;
	private String channelGroupCode;
	private String applyChanel;
	private Date corresPondLogCreateDate;
	private Date lastDecisionDate; 
	private Date actionDueDate; 
	private String dmRequestUser;
	private String userWebScan;
	private NotiCCDataM noticcDataM;
	private String templateName;
	private EmailTemplateDataM emailtemplatedataM;
	private String jobState;
	private String branchName;
	private int lifeCycle;
	
	public String getDmId() {
		return dmId;
	}
	public void setDmId(String dmId) {
		this.dmId = dmId;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public int getRoundOfNotification() {
		return roundOfNotification;
	}
	public void setRoundOfNotification(int roundOfNotification) {
		this.roundOfNotification = roundOfNotification;
	}
	public String getDmNotificationType() {
		return dmNotificationType;
	}
	public void setDmNotificationType(String dmNotificationType) {
		this.dmNotificationType = dmNotificationType;
	}
	public String getApplyChanel() {
		return applyChanel;
	}
	public void setApplyChanel(String applyChanel) {
		this.applyChanel = applyChanel;
	}
	public Date getCorresPondLogCreateDate() {
		return corresPondLogCreateDate;
	}
	public void setCorresPondLogCreateDate(Date corresPondLogCreateDate) {
		this.corresPondLogCreateDate = corresPondLogCreateDate;
	}
	public Date getLastDecisionDate() {
		return lastDecisionDate;
	}
	public void setLastDecisionDate(Date lastDecisionDate) {
		this.lastDecisionDate = lastDecisionDate;
	}
	public String getChannelGroupCode() {
		return channelGroupCode;
	}
	public void setChannelGroupCode(String channelGroupCode) {
		this.channelGroupCode = channelGroupCode;
	}
	public String getDmRequestUser() {
		return dmRequestUser;
	}
	public void setDmRequestUser(String dmRequestUser) {
		this.dmRequestUser = dmRequestUser;
	}
	public NotiCCDataM getNoticcDataM() {
		return noticcDataM;
	}
	public void setNoticcDataM(NotiCCDataM noticcDataM) {
		this.noticcDataM = noticcDataM;
	}
		public EmailTemplateDataM getEmailtemplatedataM() {
		return emailtemplatedataM;
	}
	public void setEmailtemplatedataM(EmailTemplateDataM emailtemplatedataM) {
		this.emailtemplatedataM = emailtemplatedataM;
	}
	public String getUserWebScan() {
		return userWebScan;
	}
	public void setUserWebScan(String userWebScan) {
		this.userWebScan = userWebScan;
	}
	public Date getActionDueDate() {
		return actionDueDate;
	}
	public void setActionDueDate(Date actionDueDate) {
		this.actionDueDate = actionDueDate;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getJobState() {
		return jobState;
	}
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	public String getBarnchName() {
		return branchName;
	}
	public void setBarnchName(String barnchName) {
		this.branchName = barnchName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
}

