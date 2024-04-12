package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;

public class JobCodeDataM implements Serializable,Cloneable {
 private  String jobCode;
 private  String optional1;
 private  String optional2;
 private  String optional3;
 private  String optional4;
 private  String optional5;
 private  String pattern;
 private String fixEmail;
 private String notificationType;
 private String sendTo;
 private String ccTo;
 private String priority;
 
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
public String getNotificationType() {
	return notificationType;
}
public void setNotificationType(String notificationType) {
	this.notificationType = notificationType;
}
public String getSendTo() {
	return sendTo;
}
public void setSendTo(String sendTo) {
	this.sendTo = sendTo;
}
public String getCcTo() {
	return ccTo;
}
public void setCcTo(String ccTo) {
	this.ccTo = ccTo;
}
public String getPriority() {
	return priority;
}
public void setPriority(String priority) {
	this.priority = priority;
}
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("JobCodeDataM [jobCode=");
	builder.append(jobCode);
	builder.append(", optional1=");
	builder.append(optional1);
	builder.append(", optional2=");
	builder.append(optional2);
	builder.append(", optional3=");
	builder.append(optional3);
	builder.append(", optional4=");
	builder.append(optional4);
	builder.append(", optional5=");
	builder.append(optional5);
	builder.append(", pattern=");
	builder.append(pattern);
	builder.append(", fixEmail=");
	builder.append(fixEmail);
	builder.append(", notificationType=");
	builder.append(notificationType);
	builder.append(", sendTo=");
	builder.append(sendTo);
	builder.append(", ccTo=");
	builder.append(ccTo);
	builder.append(", priority=");
	builder.append(priority);
	builder.append("]");
	return builder.toString();
}
}
