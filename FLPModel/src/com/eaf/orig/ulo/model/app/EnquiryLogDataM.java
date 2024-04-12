package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Date;

public class EnquiryLogDataM implements Serializable,Cloneable{
	public EnquiryLogDataM(){
		super();
	}
	private String enquiryLogId;	//ORIG_ENQUIRY_LOG.ENQUIRY_LOG_ID(VARCHAR2) PK
	private String applicationGroupId;	//ORIG_ENQUIRY_LOG.APPLICATION_GROUP_ID(VARCHAR2)
	private Date enquiryDate;	//ORIG_ENQUIRY_LOG.ENQUIRY_DATE(DATE)
	private String username;	//ORIG_ENQUIRY_LOG.USERNAME(VARCHAR2)

	public String getEnquiryLogId() {
		return enquiryLogId;
	}
	public void setEnquiryLogId(String enquiryLogId) {
		this.enquiryLogId = enquiryLogId;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public Date getEnquiryDate() {
		return enquiryDate;
	}
	public void setEnquiryDate(Date enquiryDate) {
		this.enquiryDate = enquiryDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
}
