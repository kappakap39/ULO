package com.eaf.inf.batch.ulo.card.stack.notification.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProcessResponse implements Serializable, Cloneable {
	private String statusCode;
	private String statusDesc;
	private String responseData;
	private String errorData;

	public ProcessResponse(String resultCode, String resultDesc) {
		this.statusCode = resultCode;
		this.statusDesc = resultDesc;

	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		return statusDesc;
	}

	/**
	 * @param statusDesc the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProcessResponse [statusCode=");
		builder.append(statusCode);
		builder.append(", statusDesc=");
		builder.append(statusDesc);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the responseData
	 */
	public String getResponseData() {
		return responseData;
	}

	/**
	 * @param responseData the responseData to set
	 */
	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	/**
	 * @return the errorData
	 */
	public String getErrorData() {
		return errorData;
	}

	/**
	 * @param errorData the errorData to set
	 */
	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}
}
