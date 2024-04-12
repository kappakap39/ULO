package com.eaf.service.module.model;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class NotifyFLPFinalApproveResultResponseHeaderM implements Serializable, Cloneable {

	String messageUid;
	String messageDt;
	String respCode;
	String respDesc;
	String reqMessageUid;
	
	public String getMessageUid() {
		return messageUid;
	}
	public void setMessageUid(String messageUid) {
		this.messageUid = messageUid;
	}
	public String getMessageDt() {
		return messageDt;
	}
	public void setMessageDt(String messageDt) {
		this.messageDt = messageDt;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getReqMessageUid() {
		return reqMessageUid;
	}
	public void setReqMessageUid(String reqMessageUid) {
		this.reqMessageUid = reqMessageUid;
	}
	
}
