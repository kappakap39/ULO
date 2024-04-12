package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class FinancialInstrumentDataM extends IncomeCategoryDataM {
	public FinancialInstrumentDataM(){
		super();
	}
//	private int seq;
	private String finInstrument; //INC_FIN_INSTRUMENT.FIN_INSTRUMENT(VARCHAR2)
//	private String incomeId;	//INC_FIN_INSTRUMENT.INCOME_ID(VARCHAR2)
	private BigDecimal currentBalance;	//INC_FIN_INSTRUMENT.CURRENT_BALANCE(NUMBER)
	private Date expireDate;	//INC_FIN_INSTRUMENT.EXPIRE_DATE(DATE)
	private String instrumentType;	//INC_FIN_INSTRUMENT.INSTRUMENT_TYPE(VARCHAR2)
	private String instrumentTypeDesc;	//INC_FIN_INSTRUMENT.INSTRUMENT_TYPE_DESC(VARCHAR2)
	private String holderName;	//INC_FIN_INSTRUMENT.HOLDER_NAME(VARCHAR2)
	private BigDecimal holdingRatio;	//INC_FIN_INSTRUMENT.HOLDING_RATIO(NUMBER)
	private Date openDate;	//INC_FIN_INSTRUMENT.OPEN_DATE(DATE)
	private String issuerName;	//INC_FIN_INSTRUMENT.ISSUER_NAME(VARCHAR2)
	private String createBy;	//INC_FIN_INSTRUMENT.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_FIN_INSTRUMENT.CREATE_DATE(DATE)
	private String updateBy;	//INC_FIN_INSTRUMENT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_FIN_INSTRUMENT.UPDATE_DATE(DATE)
//	private String compareFlag; //INC_FIN_INSTRUMENT.COMPARE_FLAG(VARCHAR2)
		
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
	public String getFinInstrument() {
		return finInstrument;
	}
	public void setFinInstrument(String finInstrument) {
		this.finInstrument = finInstrument;
	}
//	public String getIncomeId() {
//		return incomeId;
//	}
//	public void setIncomeId(String incomeId) {
//		this.incomeId = incomeId;
//	}
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public String getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}	
	public String getInstrumentTypeDesc() {
		return instrumentTypeDesc;
	}
	public void setInstrumentTypeDesc(String instrumentTypeDesc) {
		this.instrumentTypeDesc = instrumentTypeDesc;
	}
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	public BigDecimal getHoldingRatio() {
		return holdingRatio;
	}
	public void setHoldingRatio(BigDecimal holdingRatio) {
		this.holdingRatio = holdingRatio;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
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
	@Override
	public String getId() {
		return getFinInstrument();
	}
	
}
