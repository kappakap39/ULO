package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CVRSDQ0001ResponseDataM implements Serializable,Cloneable{
private String responseCode;
private String  responseMsg;
private ArrayList<ResponseValidateResultDataM> responseValidateFields;
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public ArrayList<ResponseValidateResultDataM> getResponseValidateFields() {
		return responseValidateFields;
	}
	public void setResponseValidateFields(
			ArrayList<ResponseValidateResultDataM> responseValidateFields) {
		this.responseValidateFields = responseValidateFields;
	}
}
 