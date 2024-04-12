package com.eaf.orig.ulo.model.pega;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


@SuppressWarnings("serial")
public class FollowUpResponse implements Serializable, Cloneable{
	
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
	private List<ErrorDataM> Error;
	
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
	public List<ErrorDataM> getError() {
		return Error;
	}
	public void setError(List<ErrorDataM> error) {
		Error = error;
	}
	public String getCorrID() {
		return CorrID;
	}
	public void setCorrID(String corrID) {
		CorrID = corrID;
	}
	
}
