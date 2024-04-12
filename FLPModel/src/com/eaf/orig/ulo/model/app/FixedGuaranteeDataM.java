package com.eaf.orig.ulo.model.app;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class FixedGuaranteeDataM extends IncomeCategoryDataM {
	public FixedGuaranteeDataM(){
		super();
	}
//	private int seq;
	private String fixedGuaranteeId;	//INC_FIXED_GUARANTEE.FIXED_GUARANTEE_ID(VARCHAR2)
//	private String incomeId;	//INC_FIXED_GUARANTEE.INCOME_ID(VARCHAR2)
	private String accountNo;	//INC_FIXED_GUARANTEE.ACCOUNT_NO(VARCHAR2)
	private Date retentionDate;	//INC_FIXED_GUARANTEE.RETENTION_DATE(DATE)
	private BigDecimal retentionAmt;	//INC_FIXED_GUARANTEE.RETENTION_AMT(NUMBER)
	private String branchNo;	//INC_FIXED_GUARANTEE.BRANCH_NO(VARCHAR2)
	private String accountName;	//INC_FIXED_GUARANTEE.ACCOUNT_NAME(VARCHAR2)
	private String retentionType;	//INC_FIXED_GUARANTEE.RETENTION_TYPE(VARCHAR2)
	private String retentionTypeDesc;	//INC_FIXED_GUARANTEE.RETENTION_TYPE_DESC(VARCHAR2)
	private String sub;	//INC_FIXED_GUARANTEE.SUB(VARCHAR2)
	private String createBy;	//INC_FIXED_GUARANTEE.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_FIXED_GUARANTEE.CREATE_DATE(DATE)
	private String updateBy;	//INC_FIXED_GUARANTEE.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_FIXED_GUARANTEE.UPDATE_DATE(DATE)
//	private String compareFlag; //INC_FIXED_GUARANTEE.COMPARE_FLAG(VARCHAR2)
		
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
	public String getFixedGuaranteeId() {
		return fixedGuaranteeId;
	}
	public void setFixedGuaranteeId(String fixedGuaranteeId) {
		this.fixedGuaranteeId = fixedGuaranteeId;
	}
//	public String getIncomeId() {
//		return incomeId;
//	}
//	public void setIncomeId(String incomeId) {
//		this.incomeId = incomeId;
//	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public Date getRetentionDate() {
		return retentionDate;
	}
	public void setRetentionDate(Date retentionDate) {
		this.retentionDate = retentionDate;
	}
	public BigDecimal getRetentionAmt() {
		return retentionAmt;
	}
	public void setRetentionAmt(BigDecimal retentionAmt) {
		this.retentionAmt = retentionAmt;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getRetentionType() {
		return retentionType;
	}
	public void setRetentionType(String retentionType) {
		this.retentionType = retentionType;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
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
	public String getRetentionTypeDesc() {
		return retentionTypeDesc;
	}
	public void setRetentionTypeDesc(String retentionTypeDesc) {
		this.retentionTypeDesc = retentionTypeDesc;
	}
	@Override
	public String getId() {
		return getFixedGuaranteeId();
	}
	
}
