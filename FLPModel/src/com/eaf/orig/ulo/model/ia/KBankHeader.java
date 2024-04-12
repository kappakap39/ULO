package com.eaf.orig.ulo.model.ia;

import java.io.Serializable;
import java.util.ArrayList;

public class KBankHeader implements Serializable,Cloneable{
	public KBankHeader(){
		super();
		errorVect = new ArrayList<Error>();
	}
	private String rqUID;
	private String rsAppId;
	private String statusCode;
	private String userId;
	private ArrayList<Error> errorVect;
	public String getRqUID() {
		return rqUID;
	}
	public void setRqUID(String rqUID) {
		this.rqUID = rqUID;
	}
	public String getRsAppId() {
		return rsAppId;
	}
	public void setRsAppId(String rsAppId) {
		this.rsAppId = rsAppId;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ArrayList<Error> getErrorVect() {
		return errorVect;
	}
	public void setErrorVect(ArrayList<Error> errorVect) {
		this.errorVect = errorVect;
	}	
	public void error(String errorCode,String errorDesc){
		Error e = new Error();
		e.setErrorCode(errorCode);
		e.setErrorDesc(errorDesc);
		errorVect.add(e);
	}
}
