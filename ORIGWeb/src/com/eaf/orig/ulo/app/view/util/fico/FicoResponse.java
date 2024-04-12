package com.eaf.orig.ulo.app.view.util.fico;

import java.io.Serializable;

import com.orig.bpm.workflow.decision.mock.model.CreditApplication;

@SuppressWarnings("serial")
public class FicoResponse implements Serializable,Cloneable{
	public FicoResponse(){
		super();
	}
	private String resultCode;
	private String resultDesc;
	private String errorMsg;
	private CreditApplication ficoResponse;
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
	public CreditApplication getFicoResponse() {
		return ficoResponse;
	}
	public void setFicoResponse(CreditApplication ficoResponse) {
		this.ficoResponse = ficoResponse;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}	
}
