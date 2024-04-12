package com.eaf.service.module.model;

import java.io.Serializable;

public class KmobileRequestDataM implements Serializable,Cloneable{

	public KmobileRequestDataM(){
		super();
	}

	private String requestID;
	private String username;
	private String passphrase;
	private String mobileNo;
	private String pageCode;
	private String messageTH;
	private String messageEN;
	private String alertMessageTH;
	private String alertMessageEN;
	private String imageType;
	private byte[] imageByteArrayTH;
	private byte[] imageByteArrayEN;
	private String[] imageNameArrayTH;
	private String[] imageNameArrayEN;
	private String lastSendTime;
	//private int lifeCycle;
	
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassphrase() {
		return passphrase;
	}
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getPageCode() {
		return pageCode;
	}
	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}
	public String getMessageTH() {
		return messageTH;
	}
	public void setMessageTH(String messageTH) {
		this.messageTH = messageTH;
	}
	public String getMessageEN() {
		return messageEN;
	}
	public void setMessageEN(String messageEN) {
		this.messageEN = messageEN;
	}
	public String getAlertMessageTH() {
		return alertMessageTH;
	}
	public void setAlertMessageTH(String alertMessageTH) {
		this.alertMessageTH = alertMessageTH;
	}
	public String getAlertMessageEN() {
		return alertMessageEN;
	}
	public void setAlertMessageEN(String alertMessageEN) {
		this.alertMessageEN = alertMessageEN;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public byte[] getImageByteArrayTH() {
		return imageByteArrayTH;
	}
	public void setImageByteArrayTH(byte[] imageByteArrayTH) {
		this.imageByteArrayTH = imageByteArrayTH;
	}
	public byte[] getImageByteArrayEN() {
		return imageByteArrayEN;
	}
	public void setImageByteArrayEN(byte[] imageByteArrayEN) {
		this.imageByteArrayEN = imageByteArrayEN;
	}
	public String[] getImageNameArrayTH() {
		return imageNameArrayTH;
	}
	public void setImageNameArrayTH(String[] imageNameArrayTH) {
		this.imageNameArrayTH = imageNameArrayTH;
	}
	public String[] getImageNameArrayEN() {
		return imageNameArrayEN;
	}
	public void setImageNameArrayEN(String[] imageNameArrayEN) {
		this.imageNameArrayEN = imageNameArrayEN;
	}
	public String getLastSendTime() {
		return lastSendTime;
	}
	public void setLastSendTime(String lastSendTime) {
		this.lastSendTime = lastSendTime;
	}
//	public int getLifeCycle() {
//		return lifeCycle;
//	}
//	public void setLifeCycle(int lifeCycle) {
//		this.lifeCycle = lifeCycle;
//	}
}
