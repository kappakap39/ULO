package com.eaf.orig.ulo.model.fico;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FicoApplication implements Serializable,Cloneable{
	public FicoApplication(){
		super();
	}
		
	private String resultCode = "-1";
	private String resultDesc;
	private String functionId;
	private String ficoId;
	private String ficoImplementActionId;
	private String decision;
	private String incomeScreenFlag;
	private String docCompleteFlag;	
	private String diffRequestFlag;	
	private String callEscalateFlag;
	private String errorMsg;
	
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
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	public String getFicoId() {
		return ficoId;
	}
	public void setFicoId(String ficoId) {
		this.ficoId = ficoId;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getFicoImplementActionId() {
		return ficoImplementActionId;
	}
	public void setFicoImplementActionId(String ficoImplementActionId) {
		this.ficoImplementActionId = ficoImplementActionId;
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
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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
		builder.append(", ficoId=");
		builder.append(ficoId);
		builder.append(", ficoImplementActionId=");
		builder.append(ficoImplementActionId);
		builder.append(", decision=");
		builder.append(decision);
		builder.append(", incomeScreenFlag=");
		builder.append(incomeScreenFlag);
		builder.append(", docCompleteFlag=");
		builder.append(docCompleteFlag);
		builder.append(", diffRequestFlag=");
		builder.append(diffRequestFlag);
		builder.append(", callEscalateFlag=");
		builder.append(callEscalateFlag);
		builder.append("]");
		return builder.toString();
	}
		
}
