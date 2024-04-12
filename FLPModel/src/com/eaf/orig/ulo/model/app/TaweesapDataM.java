package com.eaf.orig.ulo.model.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TaweesapDataM extends IncomeCategoryDataM {
	public TaweesapDataM(){
		super();
	}
//	private int seq;
	private String tweesapId; //INC_TAWEESAP.TAWEESAP_ID(VARCHAR2)
//	private String incomeId;	//INC_TAWEESAP.INCOME_ID(VARCHAR2)
	private BigDecimal aum;	//INC_TAWEESAP.AUM(NUMBER)
	private String createBy;	//INC_TAWEESAP.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_TAWEESAP.CREATE_DATE(DATE)
	private String updateBy;	//INC_TAWEESAP.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_TAWEESAP.UPDATE_DATE(DATE)
//	private String compareFlag; //INC_TAWEESAP.COMPARE_FLAG(VARCHAR2)
		
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
	public String getTweesapId() {
		return tweesapId;
	}
	public void setTweesapId(String tweesapId) {
		this.tweesapId = tweesapId;
	}
//	public String getIncomeId() {
//		return incomeId;
//	}
//	public void setIncomeId(String incomeId) {
//		this.incomeId = incomeId;
//	}
	public BigDecimal getAum() {
		return aum;
	}
	public void setAum(BigDecimal aum) {
		this.aum = aum;
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
		return getTweesapId();
	}
	
}
