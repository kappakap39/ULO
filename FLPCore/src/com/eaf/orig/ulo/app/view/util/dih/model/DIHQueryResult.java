package com.eaf.orig.ulo.app.view.util.dih.model;

import java.io.Serializable;

import com.eaf.core.ulo.common.model.ErrorData;

@SuppressWarnings("serial")
public class DIHQueryResult<T> implements Serializable,Cloneable{
	private String statusCode;
	private ErrorData errorData;
	private T result;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public ErrorData getErrorData() {
		return errorData;
	}
	public void setErrorData(ErrorData errorData) {
		this.errorData = errorData;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "DIHQueryResult [statusCode=" + statusCode + ", errorData="
				+ errorData + ", result=" + result + "]";
	}
}
