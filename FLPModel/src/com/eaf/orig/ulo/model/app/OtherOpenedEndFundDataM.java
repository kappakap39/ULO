package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OtherOpenedEndFundDataM implements Serializable,Cloneable{
	public OtherOpenedEndFundDataM(){
		super();
	}
	private String incomeId;	//INC_OTH_OPN_END_FUND.INCOME_ID(VARCHAR2)
	private String othOpnFundId;	//INC_OTH_OPN_END_FUND.OTH_OPN_FUND_ID(VARCHAR2)
	private Date openDate;	//INC_OTH_OPN_END_FUND.OPEN_DATE(DATE)
	private String accountName;	//INC_OTH_OPN_END_FUND.ACCOUNT_NAME(VARCHAR2)
	private String bankCode;	//INC_OTH_OPN_END_FUND.BANK_CODE(VARCHAR2)
	private BigDecimal holdingRatio;	//INC_OTH_OPN_END_FUND.HOLDING_RATIO(NUMBER)
	private String fundType;	//INC_OTH_OPN_END_FUND.FUND_TYPE(VARCHAR2)
	private String accountNo;	//INC_OTH_OPN_END_FUND.ACCOUNT_NO(VARCHAR2)
	private String createBy;	//INC_OTH_OPN_END_FUND.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_OTH_OPN_END_FUND.CREATE_DATE(DATE)
	private String updateBy;	//INC_OTH_OPN_END_FUND.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_OTH_OPN_END_FUND.UPDATE_DATE(DATE)
	
	private ArrayList<OtherOpenedEndFundDetailDataM> otherOpenedEndFundDetails;
	
	public String getIncomeId() {
		return incomeId;
	}
	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
	}
	public String getOthOpnFundId() {
		return othOpnFundId;
	}
	public void setOthOpnFundId(String othOpnFundId) {
		this.othOpnFundId = othOpnFundId;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public BigDecimal getHoldingRatio() {
		return holdingRatio;
	}
	public void setHoldingRatio(BigDecimal holdingRatio) {
		this.holdingRatio = holdingRatio;
	}
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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
	public ArrayList<OtherOpenedEndFundDetailDataM> getOtherOpenedEndFundDetails() {
		return otherOpenedEndFundDetails;
	}
	public void setOtherOpenedEndFundDetails(ArrayList<OtherOpenedEndFundDetailDataM> otherOpenedEndFundDetails) {
		this.otherOpenedEndFundDetails = otherOpenedEndFundDetails;
	}
	private OtherOpenedEndFundDetailDataM getFundDetailById(
			String othOpnFundDetailId) {
		if(othOpnFundDetailId != null && otherOpenedEndFundDetails != null) {
			for(OtherOpenedEndFundDetailDataM othFundDetailM: otherOpenedEndFundDetails){
				if(othOpnFundDetailId.equals(othFundDetailM.getOthOpnFundDetailId())) {
					return othFundDetailM;
				}
			}
		}
		return null;
	}
}
