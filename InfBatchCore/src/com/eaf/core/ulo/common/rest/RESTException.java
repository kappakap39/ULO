package com.eaf.core.ulo.common.rest;

public class RESTException extends Exception{
	private static final long serialVersionUID = -2561929678812996056L;
	private String errorDetails;
	private int statusCode;	
	public RESTException(String message){
		super(message);
	}
	public RESTException(String message, String errorDetails, int statusCode){
		super(message);
		this.statusCode = statusCode;
		this.errorDetails = errorDetails;
	}	
	public RESTException(Throwable cause) {
		super(cause);
	}	
	public String getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
