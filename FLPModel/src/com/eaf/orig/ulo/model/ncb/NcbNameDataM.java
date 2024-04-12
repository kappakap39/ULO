package com.eaf.orig.ulo.model.ncb;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class NcbNameDataM implements Serializable,Cloneable{
	public NcbNameDataM(){
		super();
	}
	private String segmentValue;	//NCB_NAME.SEGMENT_VALUE(VARCHAR2)
	private int seq;	//NCB_NAME.SEQ(NUMBER)
	private String trackingCode;	//NCB_NAME.TRACKING_CODE(VARCHAR2)
	private int numberOfChildren;	//NCB_NAME.NUMBER_OF_CHILDREN(NUMBER)
	private String familyName2;	//NCB_NAME.FAMILY_NAME2(VARCHAR2)
	private String title;	//NCB_NAME.TITLE(VARCHAR2)
	private Date dateOfBirth;	//NCB_NAME.DATE_OF_BIRTH(DATE)
	private String middleName;	//NCB_NAME.MIDDLE_NAME(VARCHAR2)
	private String familyName1;	//NCB_NAME.FAMILY_NAME1(VARCHAR2)
	private String spouseName;	//NCB_NAME.SPOUSE_NAME(VARCHAR2)
	private String firstName;	//NCB_NAME.FIRST_NAME(VARCHAR2)
	private String maritalStatus;	//NCB_NAME.MARITAL_STATUS(VARCHAR2)
	private int gender;	//NCB_NAME.GENDER(NUMBER)
	private int groupSeq;	//NCB_NAME.GROUP_SEQ(NUMBER)
	private String consentToEnquire;	//NCB_NAME.CONSENT_TO_ENQUIRE(VARCHAR2)
	private String occupation;	//NCB_NAME.OCCUPATION(VARCHAR2)
	private String nationality;	//NCB_NAME.NATIONALITY(VARCHAR2)
	private String createBy;	//NCB_NAME.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//NCB_NAME.CREATE_DATE(DATE)
	private String updateBy;	//NCB_NAME.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//NCB_NAME.UPDATE_DATE(DATE)
	
	public String getSegmentValue() {
		return segmentValue;
	}
	public void setSegmentValue(String segmentValue) {
		this.segmentValue = segmentValue;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTrackingCode() {
		return trackingCode;
	}
	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}
	public int getNumberOfChildren() {
		return numberOfChildren;
	}
	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	public String getFamilyName2() {
		return familyName2;
	}
	public void setFamilyName2(String familyName2) {
		this.familyName2 = familyName2;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getFamilyName1() {
		return familyName1;
	}
	public void setFamilyName1(String familyName1) {
		this.familyName1 = familyName1;
	}
	public String getSpouseName() {
		return spouseName;
	}
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getGroupSeq() {
		return groupSeq;
	}
	public void setGroupSeq(int groupSeq) {
		this.groupSeq = groupSeq;
	}
	public String getConsentToEnquire() {
		return consentToEnquire;
	}
	public void setConsentToEnquire(String consentToEnquire) {
		this.consentToEnquire = consentToEnquire;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
}
