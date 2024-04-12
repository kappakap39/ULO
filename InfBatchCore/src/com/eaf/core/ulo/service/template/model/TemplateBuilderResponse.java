package com.eaf.core.ulo.service.template.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class TemplateBuilderResponse implements Serializable,Cloneable{
	public TemplateBuilderResponse(){
		super();
	}
	private String resultCode;
	private String resultDesc;
	private String templateId;
	private String templateName;
	private String headerMsg;	
	private String detail1;
	private String detail2;
	private String detail3;
	private String postScript1;
	private String postScript2;
	private String remark1;
	private ArrayList<String> attachments;
	private String bodyMsg;
	private String bodyMsgTh;
	private String bodyMsgEn;
	private String alertMessageTh;
	private String alertMessageEn;
	
	private TemplateVariableDataM templateVariable;
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getHeaderMsg() {
		return headerMsg;
	}
	public void setHeaderMsg(String headerMsg) {
		this.headerMsg = headerMsg;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getDetail1() {
		return detail1;
	}
	public void setDetail1(String detail1) {
		this.detail1 = detail1;
	}
	public String getDetail2() {
		return detail2;
	}
	public void setDetail2(String detail2) {
		this.detail2 = detail2;
	}
	public String getDetail3() {
		return detail3;
	}
	public void setDetail3(String detail3) {
		this.detail3 = detail3;
	}
	public String getPostScript1() {
		return postScript1;
	}
	public void setPostScript1(String postScript1) {
		this.postScript1 = postScript1;
	}
	public String getPostScript2() {
		return postScript2;
	}
	public void setPostScript2(String postScript2) {
		this.postScript2 = postScript2;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public TemplateVariableDataM getTemplateVariable() {
		return templateVariable;
	}
	public void setTemplateVariable(TemplateVariableDataM templateVariable) {
		this.templateVariable = templateVariable;
	}
	public ArrayList<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(ArrayList<String> attachments) {
		this.attachments = attachments;
	}
	public String getBodyMsgTh() {
		return bodyMsgTh;
	}
	public void setBodyMsgTh(String bodyMsgTh) {
		this.bodyMsgTh = bodyMsgTh;
	}
	public String getBodyMsgEn() {
		return bodyMsgEn;
	}
	public void setBodyMsgEn(String bodyMsgEn) {
		this.bodyMsgEn = bodyMsgEn;
	}
	public String getBodyMsg() {
		return bodyMsg;
	}
	public void setBodyMsg(String bodyMsg) {
		this.bodyMsg = bodyMsg;
	}
	public String getAlertMessageTh() {
		return alertMessageTh;
	}
	public void setAlertMessageTh(String alertMessageTh) {
		this.alertMessageTh = alertMessageTh;
	}
	public String getAlertMessageEn() {
		return alertMessageEn;
	}
	public void setAlertMessageEn(String alertMessageEn) {
		this.alertMessageEn = alertMessageEn;
	}	
	
}
