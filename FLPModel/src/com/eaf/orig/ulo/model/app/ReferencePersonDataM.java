package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReferencePersonDataM implements Serializable,Cloneable{
	public ReferencePersonDataM(){
		super();
	}
	private String applicationGroupId;	//ORIG_REFERENCE_PERSON.APPLICATION_GROUP_ID(VARCHAR2)
	private int seq;  //ORIG_REFERENCE_PERSON.SEQ(NUMBER)			
	private String thLastName;	//ORIG_REFERENCE_PERSON.TH_LAST_NAME(VARCHAR2)
	private String phone2;	//ORIG_REFERENCE_PERSON.PHONE2(VARCHAR2)
	private String officePhone;	//ORIG_REFERENCE_PERSON.OFFICE_PHONE(VARCHAR2)
	private String thTitleCode;	//ORIG_REFERENCE_PERSON.TH_TITLE_CODE(VARCHAR2)
	private String thFirstName;	//ORIG_REFERENCE_PERSON.TH_FIRST_NAME(VARCHAR2)
	private String ext2;	//ORIG_REFERENCE_PERSON.EXT2(VARCHAR2)
	private String mobile;	//ORIG_REFERENCE_PERSON.MOBILE(VARCHAR2)
	private String relation;	//ORIG_REFERENCE_PERSON.RELATION(VARCHAR2)
	private String ext1;	//ORIG_REFERENCE_PERSON.EXT1(VARCHAR2)
	private String officePhoneExt;	//ORIG_REFERENCE_PERSON.OFFICE_PHONE_EXT(VARCHAR2)
	private String phone1;	//ORIG_REFERENCE_PERSON.PHONE1(VARCHAR2)
	private String createBy;	//ORIG_REFERENCE_PERSON.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_REFERENCE_PERSON.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_REFERENCE_PERSON.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_REFERENCE_PERSON.UPDATE_DATE(DATE)
	private String thTitleDesc; //ORIG_PERSONAL_INFO.TH_TITLE_DESC(VARCHAR2)
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getThLastName() {
		return thLastName;
	}
	public void setThLastName(String thLastName) {
		this.thLastName = thLastName;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
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
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getOfficePhoneExt() {
		return officePhoneExt;
	}
	public void setOfficePhoneExt(String officePhoneExt) {
		this.officePhoneExt = officePhoneExt;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
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
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getThTitleDesc() {
		return thTitleDesc;
	}
	public void setThTitleDesc(String thTitleDesc) {
		this.thTitleDesc = thTitleDesc;
	}	
}	
