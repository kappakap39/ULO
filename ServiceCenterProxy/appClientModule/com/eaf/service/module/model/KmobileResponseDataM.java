package com.eaf.service.module.model;

import java.io.Serializable;

public class KmobileResponseDataM implements Serializable,Cloneable{

	public KmobileResponseDataM(){
		super();
	}
	
	private String responseID;
	private String msgCode;
	private String msgDesc;
	private String status;
	
	
	public String getResponseID() {
		return responseID;
	}
	public void setResponseID(String responseID) {
		this.responseID = responseID;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getMsgDesc() {
		return msgDesc;
	}
	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

	


