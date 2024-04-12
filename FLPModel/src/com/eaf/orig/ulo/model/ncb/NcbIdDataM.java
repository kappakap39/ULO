package com.eaf.orig.ulo.model.ncb;

import java.io.Serializable;
import java.sql.Timestamp;

public class NcbIdDataM implements Serializable,Cloneable{
	public NcbIdDataM(){
		super();
	}
	private String segmentValue;	//NCB_ID.SEGMENT_VALUE(VARCHAR2)
	private int seq;	//NCB_ID.SEQ(NUMBER)
	private String trackingCode;	//NCB_ID.TRACKING_CODE(VARCHAR2)
	private String idIssueCountry;	//NCB_ID.ID_ISSUE_COUNTRY(VARCHAR2)
	private String idType;	//NCB_ID.ID_TYPE(VARCHAR2)
	private int groupSeq;	//NCB_ID.GROUP_SEQ(NUMBER)
	private String idNumber;	//NCB_ID.ID_NUMBER(VARCHAR2)
	private String createBy;	//NCB_ID.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//NCB_ID.CREATE_DATE(DATE)
	private String updateBy;	//NCB_ID.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//NCB_ID.UPDATE_DATE(DATE)
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
	public String getIdIssueCountry() {
		return idIssueCountry;
	}
	public void setIdIssueCountry(String idIssueCountry) {
		this.idIssueCountry = idIssueCountry;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public int getGroupSeq() {
		return groupSeq;
	}
	public void setGroupSeq(int groupSeq) {
		this.groupSeq = groupSeq;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
