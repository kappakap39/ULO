package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class OtherOpenedEndFundDetailDataM implements Serializable,Cloneable {
	public OtherOpenedEndFundDetailDataM(){
		super();		
	}
	private String othOpnFundDetailId;	//INC_OTH_OPN_END_FUND_DETAIL.OTH_OPN_FUND_DETAIL_ID(VARCHAR2)
	private String othOpnFundId;	//INC_OTH_OPN_END_FUND_DETAIL.OTH_OPN_FUND_ID(VARCHAR2)
	private BigDecimal unitValue;	//INC_OTH_OPN_END_FUND_DETAIL.UNIT_VALUE(NUMBER)
	private BigDecimal value;	//INC_OTH_OPN_END_FUND_DETAIL.VALUE(NUMBER)
	private BigDecimal unitAmtBuy;	//INC_OTH_OPN_END_FUND_DETAIL.UNIT_AMT_BUY(NUMBER)
	private BigDecimal unitAmtSell;	//INC_OTH_OPN_END_FUND_DETAIL.UNIT_AMT_SELL(NUMBER)
	private Date transactionDate;	//INC_OTH_OPN_END_FUND_DETAIL.TRANSACTION_DATE(DATE)
	private BigDecimal remainingUnit;	//INC_OTH_OPN_END_FUND_DETAIL.REMAINING_UNIT(NUMBER)
	private String createBy;	//INC_OTH_OPN_END_FUND_DETAIL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_OTH_OPN_END_FUND_DETAIL.CREATE_DATE(DATE)
	private String updateBy;	//INC_OTH_OPN_END_FUND_DETAIL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_OTH_OPN_END_FUND_DETAIL.UPDATE_DATE(DATE)
	
	public String getOthOpnFundDetailId() {
		return othOpnFundDetailId;
	}
	public void setOthOpnFundDetailId(String othOpnFundDetailId) {
		this.othOpnFundDetailId = othOpnFundDetailId;
	}
	public String getOthOpnFundId() {
		return othOpnFundId;
	}
	public void setOthOpnFundId(String othOpnFundId) {
		this.othOpnFundId = othOpnFundId;
	}
	public BigDecimal getUnitValue() {
		return unitValue;
	}
	public void setUnitValue(BigDecimal unitValue) {
		this.unitValue = unitValue;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public BigDecimal getUnitAmtBuy() {
		return unitAmtBuy;
	}
	public void setUnitAmtBuy(BigDecimal unitAmtBuy) {
		this.unitAmtBuy = unitAmtBuy;
	}
	public BigDecimal getUnitAmtSell() {
		return unitAmtSell;
	}
	public void setUnitAmtSell(BigDecimal unitAmtSell) {
		this.unitAmtSell = unitAmtSell;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public BigDecimal getRemainingUnit() {
		return remainingUnit;
	}
	public void setRemainingUnit(BigDecimal remainingUnit) {
		this.remainingUnit = remainingUnit;
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
