package com.eaf.orig.ulo.service.process.response.model;

import java.io.Serializable;

import com.eaf.core.ulo.common.model.ErrorData;

@SuppressWarnings("serial")
public class ProcessResponse implements Serializable,Cloneable{
	public static class SubmitApplicationErrorCode{
		public static final String SUBMIT_APPLICATION_ERROR_NOT_FOUND_APPLICATION_VETO = "00";
		public static final String SUBMIT_APPLICATION_ERROR_NOT_FOUND_APPLICATION_FOLLOW = "01";
	}
	private String statusCode;
	private ErrorData errorData;
	private String data;
	
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProcessResponse [statusCode=").append(statusCode)
				.append(", errorData=").append(errorData).append(", data=")
				.append(data).append("]");
		return builder.toString();
	}	
}
