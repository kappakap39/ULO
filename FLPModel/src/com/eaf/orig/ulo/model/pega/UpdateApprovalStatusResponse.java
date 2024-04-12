package com.eaf.orig.ulo.model.pega;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class UpdateApprovalStatusResponse implements Serializable, Cloneable{
	
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<NotifyPegaResponse> <FuncNm>");
		builder.append(FuncNm);
		builder.append(" </FuncNm><RqUID>");
		builder.append(RqUID);
		builder.append(" </RqUID><RsAppId>");
		builder.append(RsAppId);
		builder.append(" </RsAppId><RsUID>");
		builder.append(RsUID);
		builder.append(" </RsUID><RsDt>");
		builder.append(RsDt);
		builder.append(" </RsDt><StatusCode>");
		builder.append(StatusCode);
		builder.append(" </StatusCode><Error>");
		builder.append(Error);
		builder.append(" </Error><CorrID>");
		builder.append(CorrID);
		builder.append(" </CorrID> </NotifyPegaResponse>");
		return builder.toString();
	}	
}
