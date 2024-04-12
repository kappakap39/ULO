package com.eaf.orig.ulo.model.app;

import java.sql.Timestamp;
import java.util.ArrayList;

public class BankStatementDataM extends IncomeCategoryDataM {
	public BankStatementDataM(){
		super();
	}
//	private int seq;
	private String bankStatementId;	//INC_BANK_STATEMENT.BANK_STATEMENT_ID(VARCHAR2)
//	private String incomeId;	//INC_BANK_STATEMENT.INCOME_ID(VARCHAR2)
	private String additionalCode;	//INC_BANK_STATEMENT.ADDITIONAL_CODE(VARCHAR2)
	private String statementCode;	//INC_BANK_STATEMENT.STATEMENT_CODE(VARCHAR2)
	private String bankCode;	//INC_BANK_STATEMENT.BANK_CODE(VARCHAR2)
	private String createBy;	//INC_BANK_STATEMENT.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_BANK_STATEMENT.CREATE_DATE(DATE)
	private String updateBy;	//INC_BANK_STATEMENT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_BANK_STATEMENT.UPDATE_DATE(DATE)
//	private String compareFlag; //INC_BANK_STATEMENT.COMPARE_FLAG(VARCHAR2)
	
	private ArrayList<BankStatementDetailDataM> bankStatementDetails;
		
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
	public String getBankStatementId() {
		return bankStatementId;
	}
	public void setBankStatementId(String bankStatementId) {
		this.bankStatementId = bankStatementId;
	}	
//	public String getIncomeId() {
//		return incomeId;
//	}
//	public void setIncomeId(String incomeId) {
//		this.incomeId = incomeId;
//	}
	public String getStatementCode() {
		return statementCode;
	}
	public void setStatementCode(String statementCode) {
		this.statementCode = statementCode;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}	
	public String getAdditionalCode() {
		return additionalCode;
	}
	public void setAdditionalCode(String additionalCode) {
		this.additionalCode = additionalCode;
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
	public ArrayList<BankStatementDetailDataM> getBankStatementDetails() {
		return bankStatementDetails;
	}
	public void setBankStatementDetails(ArrayList<BankStatementDetailDataM> bankStatementDetails) {
		this.bankStatementDetails = bankStatementDetails;
	}
	@Override
	public String getId() {
		return getBankStatementId();
	}
	public BankStatementDetailDataM getBankStatementDetailById(
			String bankStatementDetailId) {
		if(bankStatementDetailId != null && bankStatementDetails != null) {
			for(BankStatementDetailDataM statementM: bankStatementDetails){
				if(bankStatementDetailId.equals(statementM.getBankStatementDetailId())) {
					return statementM;
				}
			}
		}
		return null;
	}
}
