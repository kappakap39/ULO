package com.eaf.orig.ulo.model.app;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OpenedEndFundDataM extends IncomeCategoryDataM {
	public OpenedEndFundDataM(){
		super();
	}
//	private int seq;
//	private String incomeId;	//INC_OPN_END_FUND.INCOME_ID(VARCHAR2)
	private String opnEndFundId;	//INC_OPN_END_FUND.OPN_END_FUND_ID(VARCHAR2)
	private Date openDate;	//INC_OPN_END_FUND.OPEN_DATE(DATE)
	private String fundName;	//INC_OPN_END_FUND.FUND_NAME(VARCHAR2)
	private String accountName;	//INC_OPN_END_FUND.ACCOUNT_NAME(VARCHAR2)
	private String bankCode;	//INC_OPN_END_FUND.BANK_CODE(VARCHAR2)
	private String accountNo;	//INC_OPN_END_FUND.ACCOUNT_NO(VARCHAR2)
	private BigDecimal holdingRatio;	//INC_OPN_END_FUND.HOLDING_RATIO(NUMBER)
	private String createBy;	//INC_OPN_END_FUND.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_OPN_END_FUND.CREATE_DATE(DATE)
	private String updateBy;	//INC_OPN_END_FUND.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_OPN_END_FUND.UPDATE_DATE(DATE)
//	private String compareFlag; //INC_OPN_END_FUND.COMPARE_FLAG(VARCHAR2)
	
	private ArrayList<OpenedEndFundDetailDataM> openEndFundDetails;
		
//	public String getCompareFlag() {
//		return compareFlag;
//	}
//	public void setCompareFlag(String compareFlag) {
//		this.compareFlag = compareFlag;
//	}
//	public int getSeq() {
//		return seq;
//	}
//	public void setSeq(int seq) {
//		this.seq = seq;
//	}
//	public String getIncomeId() {
//		return incomeId;
//	}
//	public void setIncomeId(String incomeId) {
//		this.incomeId = incomeId;
//	}
	public String getOpnEndFundId() {
		return opnEndFundId;
	}
	public void setOpnEndFundId(String opnEndFundId) {
		this.opnEndFundId = opnEndFundId;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}	
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
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
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public BigDecimal getHoldingRatio() {
		return holdingRatio;
	}
	public void setHoldingRatio(BigDecimal holdingRatio) {
		this.holdingRatio = holdingRatio;
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
	public ArrayList<OpenedEndFundDetailDataM> getOpenEndFundDetails() {
		return openEndFundDetails;
	}
	public void setOpenEndFundDetails(ArrayList<OpenedEndFundDetailDataM> openEndFundDetails) {
		this.openEndFundDetails = openEndFundDetails;
	}
	public OpenedEndFundDetailDataM getFundDetailById(
			String opnFundDetailId) {
		if(opnFundDetailId != null && openEndFundDetails != null) {
			for(OpenedEndFundDetailDataM opnFundDetailM: openEndFundDetails){
				if(opnFundDetailId.equals(opnFundDetailM.getOpnEndFundDetailId())) {
					return opnFundDetailM;
				}
			}
		}
		return null;
	}
	@Override
	public String getId() {
		return getOpnEndFundId();
	}
}
