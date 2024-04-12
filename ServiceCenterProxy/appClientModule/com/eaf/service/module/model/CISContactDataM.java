package com.eaf.service.module.model;

import java.io.Serializable;

public class CISContactDataM implements Serializable,Cloneable{
	private String typeCode;
	private String locationCode;
	private String contactNo;
	private String contactExtNo;
	private String contactAvailabilityTime;
	private String contactName;
	private String contactId;
	private String contactTypeCode;
	private String contactLocation;
	private String contactSequence;
	private String successFlag;
	private String errorDescription;
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getContactExtNo() {
		return contactExtNo;
	}
	public void setContactExtNo(String contactExtNo) {
		this.contactExtNo = contactExtNo;
	}
	public String getContactAvailabilityTime() {
		return contactAvailabilityTime;
	}
	public void setContactAvailabilityTime(String contactAvailabilityTime) {
		this.contactAvailabilityTime = contactAvailabilityTime;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getContactTypeCode() {
		return contactTypeCode;
	}
	public void setContactTypeCode(String contactTypeCode) {
		this.contactTypeCode = contactTypeCode;
	}
	public String getContactLocation() {
		return contactLocation;
	}
	public void setContactLocation(String contactLocation) {
		this.contactLocation = contactLocation;
	}
	public String getContactSequence() {
		return contactSequence;
	}
	public void setContactSequence(String contactSequence) {
		this.contactSequence = contactSequence;
	}
	public String getSuccessFlag() {
		return successFlag;
	}
	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
}
