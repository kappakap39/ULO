package com.eaf.orig.ulo.app.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.model.PersonalRelationDataM;
@SuppressWarnings("serial")
public class PersonalInfoDataM implements Serializable,Cloneable{
	private String applicationGroupId;	//ORIG_PERSONAL_INFO.APPLICATION_GROUP_ID(VARCHAR2)
	private String personalId;	//ORIG_PERSONAL_INFO.PERSONAL_ID(VARCHAR2)
	private String idno;	//ORIG_PERSONAL_INFO.IDNO(VARCHAR2)
	private String nationality;	//ORIG_PERSONAL_INFO.NATIONALITY(VARCHAR2)
	private String thTitleCode;	//ORIG_PERSONAL_INFO.TH_TITLE_CODE(VARCHAR2)
	private String thFirstName;	//ORIG_PERSONAL_INFO.TH_FIRST_NAME(VARCHAR2)
	private String thMidName;	//ORIG_PERSONAL_INFO.TH_MID_NAME(VARCHAR2)
	private String thLastName;	//ORIG_PERSONAL_INFO.TH_LAST_NAME(VARCHAR2)
	private String enTitleCode;	//ORIG_PERSONAL_INFO.EN_TITLE_CODE(VARCHAR2)
	private String enFirstName;	//ORIG_PERSONAL_INFO.EN_FIRST_NAME(VARCHAR2)
	private String enMidName;	//ORIG_PERSONAL_INFO.EN_MID_NAME(VARCHAR2)
	private String enLastName;	//ORIG_PERSONAL_INFO.EN_LAST_NAME(VARCHAR2)
	private String mobileNo;	//ORIG_PERSONAL_INFO.MOBILE_NO(VARCHAR2)
	private String thTitleDesc; //ORIG_PERSONAL_INFO.TH_TITLE_DESC(VARCHAR2)
	private String enTitleDesc; //ORIG_PERSONAL_INFO.EN_TITLE_DESC(VARCHAR2)
	private String mTitleDesc; //ORIG_PERSONAL_INFO.M_TITLE_DESC(VARCHAR2)
	private String personalType; //ORIG_PERSONAL_INFO.PERSONAL_TYPE(VARCHAR2) 
	private String emailPrimary; //ORIG_PERSONAL_INFO.EMAIL_PRIMARY(VARCHAR2)
	private String createBy;	//ORIG_PERSONAL_INFO.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_PERSONAL_INFO.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_PERSONAL_INFO.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_PERSONAL_INFO.UPDATE_DATE(DATE)
	private ArrayList<PersonalRelationDataM> personalRelations;
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getThTitleCode() {
		return thTitleCode;
	}
	public void setThTitleCode(String thTitleCode) {
		this.thTitleCode = thTitleCode;
	}
	public String getThFirstName() {
		return thFirstName;
	}
	public void setThFirstName(String thFirstName) {
		this.thFirstName = thFirstName;
	}
	public String getThMidName() {
		return thMidName;
	}
	public void setThMidName(String thMidName) {
		this.thMidName = thMidName;
	}
	public String getThLastName() {
		return thLastName;
	}
	public void setThLastName(String thLastName) {
		this.thLastName = thLastName;
	}
	public String getEnTitleCode() {
		return enTitleCode;
	}
	public void setEnTitleCode(String enTitleCode) {
		this.enTitleCode = enTitleCode;
	}
	public String getEnFirstName() {
		return enFirstName;
	}
	public void setEnFirstName(String enFirstName) {
		this.enFirstName = enFirstName;
	}
	public String getEnMidName() {
		return enMidName;
	}
	public void setEnMidName(String enMidName) {
		this.enMidName = enMidName;
	}
	public String getEnLastName() {
		return enLastName;
	}
	public void setEnLastName(String enLastName) {
		this.enLastName = enLastName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getThTitleDesc() {
		return thTitleDesc;
	}
	public void setThTitleDesc(String thTitleDesc) {
		this.thTitleDesc = thTitleDesc;
	}
	public String getEnTitleDesc() {
		return enTitleDesc;
	}
	public void setEnTitleDesc(String enTitleDesc) {
		this.enTitleDesc = enTitleDesc;
	}
	public String getmTitleDesc() {
		return mTitleDesc;
	}
	public void setmTitleDesc(String mTitleDesc) {
		this.mTitleDesc = mTitleDesc;
	}
	public String getPersonalType() {
		return personalType;
	}
	public void setPersonalType(String personalType) {
		this.personalType = personalType;
	}
	public String getEmailPrimary() {
		return emailPrimary;
	}
	public void setEmailPrimary(String emailPrimary) {
		this.emailPrimary = emailPrimary;
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
	public ArrayList<PersonalRelationDataM> getPersonalRelations() {
		return personalRelations;
	}
	public void setPersonalRelations(
			ArrayList<PersonalRelationDataM> personalRelations) {
		this.personalRelations = personalRelations;
	}
}