package com.eaf.orig.ulo.model.decison;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class DecisionApplication implements Serializable,Cloneable{
	public DecisionApplication(){
		super();
	}
		
	private String resultCode = "-1";
	private String resultDesc;
	private String errorType;
	private String buttonAction;
	private String functionId;
	private String decisionId;
	private String decisionImplementActionId;
	private String decision;
	private String incomeScreenFlag;
	private String docCompleteFlag;	
	private String diffRequestFlag;	
	private String callEscalateFlag;
	private String fraudFlag;
	private String blockedFlag;
	private String rejectFlag;
	//KPL Additional
	private String savingPlusFlag;
	//CR0216
	private BigDecimal debtAmount;
	private BigDecimal debtBurdenCreditLimit;
	private BigDecimal debtRecommend;
	private BigDecimal debtBurden;
	private String lowIncomeFlag;
	
	public String getSavingPlusFlag() {
		return savingPlusFlag;
	}
	public void setSavingPlusFlag(String savingPlusFlag) {
		this.savingPlusFlag = savingPlusFlag;
	}
	public String getRejectFlag() {
		return rejectFlag;
	}
	public void setRejectFlag(String rejectFlag) {
		this.rejectFlag = rejectFlag;
	}
	public String getFraudFlag() {
		return fraudFlag;
	}
	public void setFraudFlag(String fraudFlag) {
		this.fraudFlag = fraudFlag;
	}
	public String getBlockedFlag() {
		return blockedFlag;
	}
	public void setBlockedFlag(String blockedFlag) {
		this.blockedFlag = blockedFlag;
	}
	public String getButtonAction() {
		return buttonAction;
	}
	public void setButtonAction(String buttonAction) {
		this.buttonAction = buttonAction;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	public String getDecisionId() {
		return decisionId;
	}
	public void setDecisionId(String ficoId) {
		this.decisionId = ficoId;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getDecisionImplementActionId() {
		return decisionImplementActionId;
	}
	public void setDecisionImplementActionId(String decisionImplementActionId) {
		this.decisionImplementActionId = decisionImplementActionId;
	}	
	public String getIncomeScreenFlag() {
		return incomeScreenFlag;
	}
	public void setIncomeScreenFlag(String incomeScreenFlag) {
		this.incomeScreenFlag = incomeScreenFlag;
	}	
	public String getDocCompleteFlag() {
		return docCompleteFlag;
	}
	public void setDocCompleteFlag(String docCompleteFlag) {
		this.docCompleteFlag = docCompleteFlag;
	}
	public String getDiffRequestFlag() {
		return diffRequestFlag;
	}
	public void setDiffRequestFlag(String diffRequestFlag) {
		this.diffRequestFlag = diffRequestFlag;
	}	 
	public String getCallEscalateFlag() {
		return callEscalateFlag;
	}
	public void setCallEscalateFlag(String callEscalateFlag) {
		this.callEscalateFlag = callEscalateFlag;
	}	
	
	public BigDecimal getDebtAmount() {
		return debtAmount;
	}
	public void setDebtAmount(BigDecimal debtAmount) {
		this.debtAmount = debtAmount;
	}
	public BigDecimal getDebtBurdenCreditLimit() {
		return debtBurdenCreditLimit;
	}
	public void setDebtBurdenCreditLimit(BigDecimal debtBurdenCreditLimit) {
		this.debtBurdenCreditLimit = debtBurdenCreditLimit;
	}
	public BigDecimal getDebtRecommend() {
		return debtRecommend;
	}
	public void setDebtRecommend(BigDecimal debtRecommend) {
		this.debtRecommend = debtRecommend;
	}
	public BigDecimal getDebtBurden() {
		return debtBurden;
	}
	public void setDebtBurden(BigDecimal debtBurden) {
		this.debtBurden = debtBurden;
	}
	public String getLowIncomeFlag() {
		return lowIncomeFlag;
	}
	public void setLowIncomeFlag(String lowIncomeFlag) {
		this.lowIncomeFlag = lowIncomeFlag;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[resultCode=");
		builder.append(resultCode);
		builder.append(", resultDesc=");
		builder.append(resultDesc);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append(", decisionId=");
		builder.append(decisionId);
		builder.append(", decisionImplementActionId=");
		builder.append(decisionImplementActionId);
		builder.append(", decision=");
		builder.append(decision);
		builder.append(", incomeScreenFlag=");
		builder.append(incomeScreenFlag);
		builder.append(", docCompleteFlag=");
		builder.append(docCompleteFlag);
		builder.append(", diffRequestFlag=");
		builder.append(diffRequestFlag);
		builder.append(", lowIncomeFlag=");
		builder.append(lowIncomeFlag);
		builder.append(", callEscalateFlag=");
		builder.append(callEscalateFlag);
		builder.append(", savingPlusFlag=");
		builder.append(savingPlusFlag);
		builder.append(", debtAmount=");
		builder.append(debtAmount);
		builder.append(", debtBurdenCreditLimit=");
		builder.append(debtBurdenCreditLimit);
		builder.append(", debtRecommend=");
		builder.append(debtRecommend);
		builder.append(", debtBurden=");
		builder.append(debtBurden);
		builder.append("]");
		return builder.toString();
	}
}
