package com.ava.flp.cjd.model;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

public class CJDResponseHeader implements Serializable,Cloneable{
	public CJDResponseHeader(){
		super();
	}
	private String messageUid;
	private String messageDt;
	private String respCode;
	private String respDesc;
	private String reqMessageUid;
	
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
