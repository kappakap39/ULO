package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class CardlinkCustomerDataM implements Serializable,Cloneable{
	public CardlinkCustomerDataM(){
		super();
	}
	private String personalId;	//ORIG_CARDLINK_CUSTOMER.PERSONAL_ID(VARCHAR2)
	private String orgId;	//ORIG_CARDLINK_CUSTOMER.ORIG_ID(VARCHAR2)
	private String cardlinkCustId;	//ORIG_CARDLINK_CUSTOMER.CARDLINK_CUST_ID(VARCHAR2)
	private String cardlinkCustNo;	//ORIG_CARDLINK_CUSTOMER.CARDLINK_CUST_NO(VARCHAR2)
	private String createBy;	//ORIG_CARDLINK_CUSTOMER.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_CARDLINK_CUSTOMER.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_CARDLINK_CUSTOMER.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_CARDLINK_CUSTOMER.UPDATE_DATE(DATE)
	private String newCardlinkCustFlag;	//ORIG_CARDLINK_CUSTOMER.NEW_CARDLINK_CUST_FLAG(VARCHAR2)
	private String financialInfoCode;
	private String vatCode;
	
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getCardlinkCustId() {
		return cardlinkCustId;
	}
	public void setCardlinkCustId(String cardlinkCustId) {
		this.cardlinkCustId = cardlinkCustId;
	}
	public String getCardlinkCustNo() {
		return cardlinkCustNo;
	}
	public void setCardlinkCustNo(String cardlinkCustNo) {
		this.cardlinkCustNo = cardlinkCustNo;
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
	public String getNewCardlinkCustFlag() {
		return newCardlinkCustFlag;
	}
	public void setNewCardlinkCustFlag(String newCardlinkCustFlag) {
		this.newCardlinkCustFlag = newCardlinkCustFlag;
	}
	public String getFinancialInfoCode() {
		return financialInfoCode;
	}
	public void setFinancialInfoCode(String financialInfoCode) {
		this.financialInfoCode = financialInfoCode;
	}
	public String getVatCode() {
		return vatCode;
	}
	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}		
}
