package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;
import com.eaf.service.module.model.DuplicateProduct;

import com.eaf.service.rest.model.KbankError;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class CheckProductDupResponseDataM implements Serializable, Cloneable{
	
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
	
	@JsonProperty("duplicateProducts")
	private ArrayList<DuplicateProduct> duplicateProducts = new ArrayList<DuplicateProduct>();
	
	public ArrayList<DuplicateProduct> getDuplicateProducts() {
		return duplicateProducts;
	}
	public void setDuplicateProducts(ArrayList<DuplicateProduct> duplicateProducts) {
		this.duplicateProducts = duplicateProducts;
	}
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
