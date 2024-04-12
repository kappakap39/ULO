package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class ApplicationIncreaseDataM implements Serializable,Cloneable{
	public ApplicationIncreaseDataM(String uniqueId){
		super();
		this.applicationRecordId = uniqueId;
	}
	public ApplicationIncreaseDataM(){
		super();
	}
	private String applicationRecordId;	//ORIG_APPLICATION_INCREASE.APPLICATION_RECORD_ID(VARCHAR2)
	private String applicationGroupId;	//ORIG_APPLICATION_INCREASE.APPLICATION_GROUP_ID(VARCHAR2)
	private String cardNoMark; //ORIG_APPLICATION_INCREASE.CARD_NO_MARK(VARCHAR2)
	private String cardNoEncrypted; //ORIG_APPLICATION_INCREASE.CARD_NO(VARCHAR2)
	private String thName; //ORIG_APPLICATION_INCREASE.TH_NAME(VARCHAR2)
	private String cardLinkCustNo; //ORIG_APPLICATION_INCREASE.CARDLINK_CUST_NO(VARCHAR2)
	private String createBy;	//ORIG_APPLICATION_INCREASE.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_APPLICATION_INCREASE.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_APPLICATION_INCREASE.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_APPLICATION_INCREASE.UPDATE_DATE(DATE)
	private String mainCardApplicationRecordId; //ORIG_APPLICATION_INCREASE.ORIG_APPLICATION_INCREASE(VARCHAR2)
	private String mainCardNo; //ORIG_APPLICATION_INCREASE.MAIN_CARD_NO(VARCHAR2)
	public String getMainCardApplicationRecordId() {
		return mainCardApplicationRecordId;
	}
	public void setMainCardApplicationRecordId(String mainCardApplicationRecordId) {
		this.mainCardApplicationRecordId = mainCardApplicationRecordId;
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
	public String getThName() {
		return thName;
	}
	public void setThName(String thName) {
		this.thName = thName;
	}
	public String getCardLinkCustNo() {
		return cardLinkCustNo;
	}
	public void setCardLinkCustNo(String cardLinkCustNo) {
		this.cardLinkCustNo = cardLinkCustNo;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getCardNoMark() {
		return cardNoMark;
	}
	public void setCardNoMark(String cardNoMark) {
		this.cardNoMark = cardNoMark;
	}
	public String getCardNoEncrypted() {
		return cardNoEncrypted;
	}
	public void setCardNoEncrypted(String cardNoEncrypted) {
		this.cardNoEncrypted = cardNoEncrypted;
	}
	public String getMainCardNo() {
		return mainCardNo;
	}
	public void setMainCardNo(String mainCardNo) {
		this.mainCardNo = mainCardNo;
	}
}
