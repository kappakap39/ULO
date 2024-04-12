package com.eaf.inf.batch.ulo.notification.config.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NotificationConfig implements Serializable,Cloneable{
	public NotificationConfig(){
		
	}
	String notificationType;
	String sendTime;
	String sendTo;
	String jobCode;
	String optional1;
	String optional2;
	String optional3;
	String optional4;
	String optional5;
	String pattern;
	String fixEmail;
	String managerChannel;
	String applicationTemplate;
	String saleBranchCode;
	String recommendBranchCode;	
	String saleId;
	String recommendId;
	String notifyId;
	String priority;
	String subPriority;
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public String getOptional1() {
		return optional1;
	}
	public void setOptional1(String optional1) {
		this.optional1 = optional1;
	}
	public String getOptional2() {
		return optional2;
	}
	public void setOptional2(String optional2) {
		this.optional2 = optional2;
	}
	public String getOptional3() {
		return optional3;
	}
	public void setOptional3(String optional3) {
		this.optional3 = optional3;
	}
	public String getOptional4() {
		return optional4;
	}
	public void setOptional4(String optional4) {
		this.optional4 = optional4;
	}
	public String getOptional5() {
		return optional5;
	}
	public void setOptional5(String optional5) {
		this.optional5 = optional5;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getFixEmail() {
		return fixEmail;
	}
	public void setFixEmail(String fixEmail) {
		this.fixEmail = fixEmail;
	}
	public String getManagerChannel() {
		return managerChannel;
	}
	public void setManagerChannel(String managerChannel) {
		this.managerChannel = managerChannel;
	}
	public String getApplicationTemplate() {
		return applicationTemplate;
	}
	public void setApplicationTemplate(String applicationTemplate) {
		this.applicationTemplate = applicationTemplate;
	}
	public String getSaleBranchCode() {
		return saleBranchCode;
	}
	public void setSaleBranchCode(String saleBranchCode) {
		this.saleBranchCode = saleBranchCode;
	}
	public String getRecommendBranchCode() {
		return recommendBranchCode;
	}
	public void setRecommendBranchCode(String recommendBranchCode) {
		this.recommendBranchCode = recommendBranchCode;
	}
	public String getSaleId() {
		return saleId;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	public String getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}
	public String getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getSubPriority() {
		return subPriority;
	}
	public void setSubPriority(String subPriority) {
		this.subPriority = subPriority;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotificationConfig [");
		if (notificationType != null) {
			builder.append("notificationType=");
			builder.append(notificationType);
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
		if (jobCode != null) {
			builder.append("jobCode=");
			builder.append(jobCode);
			builder.append(", ");
		}
		if (optional1 != null) {
			builder.append("optional1=");
			builder.append(optional1);
			builder.append(", ");
		}
		if (optional2 != null) {
			builder.append("optional2=");
			builder.append(optional2);
			builder.append(", ");
		}
		if (optional3 != null) {
			builder.append("optional3=");
			builder.append(optional3);
			builder.append(", ");
		}
		if (optional4 != null) {
			builder.append("optional4=");
			builder.append(optional4);
			builder.append(", ");
		}
		if (optional5 != null) {
			builder.append("optional5=");
			builder.append(optional5);
			builder.append(", ");
		}
		if (pattern != null) {
			builder.append("pattern=");
			builder.append(pattern);
			builder.append(", ");
		}
		if (fixEmail != null) {
			builder.append("fixEmail=");
			builder.append(fixEmail);
			builder.append(", ");
		}
		if (managerChannel != null) {
			builder.append("managerChannel=");
			builder.append(managerChannel);
			builder.append(", ");
		}
		if (applicationTemplate != null) {
			builder.append("applicationTemplate=");
			builder.append(applicationTemplate);
			builder.append(", ");
		}
		if (saleBranchCode != null) {
			builder.append("saleBranchCode=");
			builder.append(saleBranchCode);
			builder.append(", ");
		}
		if (recommendBranchCode != null) {
			builder.append("recommendBranchCode=");
			builder.append(recommendBranchCode);
			builder.append(", ");
		}
		if (saleId != null) {
			builder.append("saleId=");
			builder.append(saleId);
			builder.append(", ");
		}
		if (recommendId != null) {
			builder.append("recommendId=");
			builder.append(recommendId);
			builder.append(", ");
		}
		if (notifyId != null) {
			builder.append("notifyId=");
			builder.append(notifyId);
		}
		builder.append("]");
		return builder.toString();
	}
	
}
