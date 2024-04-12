package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class CardlinkCardDataM implements Serializable,Cloneable{
	public CardlinkCardDataM(){
		super();
	}
	private String personalId;	//ORIG_CARDLINK_CUSTOMER.PERSONAL_ID(VARCHAR2)
	private String cardlinkCardId;	//ORIG_CARDLINK_CUSTOMER.CARDLINK_CUST_ID(VARCHAR2)
	private String cardlinkCustNo;	//ORIG_CARDLINK_CUSTOMER.CARDLINK_CUST_NO(VARCHAR2)
	private String orgId;	//ORIG_CARDLINK_CUSTOMER.ORIG_ID(VARCHAR2)
	private String cardNo;	//ORIG_CARDLINK_CUSTOMER.CARD_NO(VARCHAR2)
	private String blockCode;	//ORIG_CARDLINK_CUSTOMER.BLOCK_CODE(VARCHAR2)
	private Date blockDate;	//ORIG_CARDLINK_CUSTOMER.BLOCK_DATE(VARCHAR2)
	private String projectCode;	//ORIG_CARDLINK_CUSTOMER.PROJECT_CODE(VARCHAR2)
	private String coaProductCode;	//ORIG_CARDLINK_CUSTOMER.COA_PRODUCT_CODE(VARCHAR2)
	private String supCustNo;	//ORIG_CARDLINK_CUSTOMER.SUP_CUST_NO(VARCHAR2)
	private String supOrgId;	//ORIG_CARDLINK_CUSTOMER.SUP_ORG_ID(VARCHAR2)
	private String cardCode;	//ORIG_CARDLINK_CUSTOMER.CARD_CODE(VARCHAR2)
	private String plasticCode;	//ORIG_CARDLINK_CUSTOMER.PLASTIC_CODE(VARCHAR2)
	private String createBy;	//ORIG_CARDLINK_CUSTOMER.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_CARDLINK_CUSTOMER.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_CARDLINK_CUSTOMER.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_CARDLINK_CUSTOMER.UPDATE_DATE(DATE)
	private String paymentCondition; //ORIG_CARDLINK_CUSTOMER.PAYMENT_CONDITION(VARCHAR2)
	
	public String getPaymentCondition() {
		return paymentCondition;
	}
	public void setPaymentCondition(String paymentCondition) {
		this.paymentCondition = paymentCondition;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getCardlinkCardId() {
		return cardlinkCardId;
	}
	public void setCardlinkCardId(String cardlinkCardId) {
		this.cardlinkCardId = cardlinkCardId;
	}
	public String getCardlinkCustNo() {
		return cardlinkCustNo;
	}
	public void setCardlinkCustNo(String cardlinkCustNo) {
		this.cardlinkCustNo = cardlinkCustNo;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getBlockCode() {
		return blockCode;
	}
	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}
	public Date getBlockDate() {
		return blockDate;
	}
	public void setBlockDate(Date blockDate) {
		this.blockDate = blockDate;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getCoaProductCode() {
		return coaProductCode;
	}
	public void setCoaProductCode(String coaProductCode) {
		this.coaProductCode = coaProductCode;
	}
	public String getSupCustNo() {
		return supCustNo;
	}
	public void setSupCustNo(String supCustNo) {
		this.supCustNo = supCustNo;
	}
	public String getSupOrgId() {
		return supOrgId;
	}
	public void setSupOrgId(String supOrgId) {
		this.supOrgId = supOrgId;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getPlasticCode() {
		return plasticCode;
	}
	public void setPlasticCode(String plasticCode) {
		this.plasticCode = plasticCode;
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
