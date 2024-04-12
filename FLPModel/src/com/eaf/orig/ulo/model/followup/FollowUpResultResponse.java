package com.eaf.orig.ulo.model.followup;

import java.io.Serializable;
import java.util.ArrayList;

import com.eaf.service.rest.model.KbankError;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class FollowUpResultResponse implements Serializable, Cloneable{
	
	@JsonProperty("FuncNm")
	private String FuncNm;
	
	@JsonProperty("RqUID")
	private String RqUID;
	
	@JsonProperty("RsAppId")
	private String RsAppId;
	
	@JsonProperty("RsUID")
	private String RsUID;
	
	@JsonProperty("RsDt")
	private String RsDt;
	
	@JsonProperty("StatusCode")
	private String StatusCode;
	
	@JsonProperty("Error")
	private ArrayList<KbankError> Error = new ArrayList<KbankError>();
	
	@JsonProperty("CorrID")
	private String CorrID;
	
	public String getFuncNm() {
		return FuncNm;
	}
	public void setFuncNm(String funcNm) {
		FuncNm = funcNm;
	}
	public String getRqUID() {
		return RqUID;
	}
	public void setRqUID(String rqUID) {
		RqUID = rqUID;
	}
	public String getRsAppId() {
		return RsAppId;
	}
	public void setRsAppId(String rsAppId) {
		RsAppId = rsAppId;
	}
	public String getRsUID() {
		return RsUID;
	}
	public void setRsUID(String rsUID) {
		RsUID = rsUID;
	}
	public String getRsDt() {
		return RsDt;
	}
	public void setRsDt(String rsDt) {
		RsDt = rsDt;
	}
	public String getStatusCode() {
		return StatusCode;
	}
	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}
	public ArrayList<KbankError> getError() {
		return Error;
	}
	public void setError(ArrayList<KbankError> error) {
		Error = error;
	}
	public String getCorrID() {
		return CorrID;
	}
	public void setCorrID(String corrID) {
		CorrID = corrID;
	}
	public void error(KbankError error){
		Error.add(error);
	}
}
