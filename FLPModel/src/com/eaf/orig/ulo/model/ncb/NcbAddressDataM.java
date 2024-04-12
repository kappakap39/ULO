package com.eaf.orig.ulo.model.ncb;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class NcbAddressDataM implements Serializable,Cloneable{
	public NcbAddressDataM(){
		super();
	}
	private String segmentValue;	//NCB_ADDRESS.SEGMENT_VALUE(VARCHAR2)
	private int seq;	//NCB_ADDRESS.SEQ(NUMBER)
	private String trackingCode;	//NCB_ADDRESS.TRACKING_CODE(VARCHAR2)
	private int groupSeq;	//NCB_ADDRESS.GROUP_SEQ(NUMBER)
	private String addressType;	//NCB_ADDRESS.ADDRESS_TYPE(VARCHAR2)
	private String country;	//NCB_ADDRESS.COUNTRY(VARCHAR2)
	private String residentialStatus;	//NCB_ADDRESS.RESIDENTIAL_STATUS(VARCHAR2)
	private String postalCode;	//NCB_ADDRESS.POSTAL_CODE(VARCHAR2)
	private String addressLine3;	//NCB_ADDRESS.ADDRESS_LINE3(VARCHAR2)
	private String subdistrict;	//NCB_ADDRESS.SUBDISTRICT(VARCHAR2)
	private String addressLine1;	//NCB_ADDRESS.ADDRESS_LINE1(VARCHAR2)
	private String telephone;	//NCB_ADDRESS.TELEPHONE(VARCHAR2)
	private Date reportedDate;	//NCB_ADDRESS.REPORTED_DATE(DATE)
	private String telephoneType;	//NCB_ADDRESS.TELEPHONE_TYPE(VARCHAR2)
	private String district;	//NCB_ADDRESS.DISTRICT(VARCHAR2)
	private String province;	//NCB_ADDRESS.PROVINCE(VARCHAR2)
	private String addressLine2;	//NCB_ADDRESS.ADDRESS_LINE2(VARCHAR2)
	private String createBy;	//NCB_ADDRESS.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//NCB_ADDRESS.CREATE_DATE(DATE)
	private String updateBy;	//NCB_ADDRESS.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//NCB_ADDRESS.UPDATE_DATE(DATE)
	
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
	public int getGroupSeq() {
		return groupSeq;
	}
	public void setGroupSeq(int groupSeq) {
		this.groupSeq = groupSeq;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getResidentialStatus() {
		return residentialStatus;
	}
	public void setResidentialStatus(String residentialStatus) {
		this.residentialStatus = residentialStatus;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public String getSubdistrict() {
		return subdistrict;
	}
	public void setSubdistrict(String subdistrict) {
		this.subdistrict = subdistrict;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Date getReportedDate() {
		return reportedDate;
	}
	public void setReportedDate(Date reportedDate) {
		this.reportedDate = reportedDate;
	}
	public String getTelephoneType() {
		return telephoneType;
	}
	public void setTelephoneType(String telephoneType) {
		this.telephoneType = telephoneType;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
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
	
	public String getAddressLine(){
		return this.addressLine1+" "+this.addressLine2+" "+this.addressLine3;
	}
	public String getAddressLineReport(){	
		String lineReport = "";
		lineReport += this.addressLine1==null?"":this.addressLine1+" ";
		lineReport += this.addressLine2==null?"":this.addressLine2+" ";
		lineReport += this.addressLine3==null?"":this.addressLine3+" ";
		lineReport += this.getSubdistrict()==null?"":this.getSubdistrict()+" ";
		lineReport += this.getDistrict()==null?"":this.getDistrict()+" ";
		lineReport += this.getProvince()==null?"":this.getProvince()+" ";
		lineReport += this.getPostalCode()==null?"":this.getPostalCode()+" ";
		return lineReport;
//		return this.addressLine1+" "+this.addressLine2+" "+this.addressLine3+" "+this.getSubdistrict()+" "+this.getDistrict()+" "+this.getProvince()+" "+this.getPostalCode();
	}
}
