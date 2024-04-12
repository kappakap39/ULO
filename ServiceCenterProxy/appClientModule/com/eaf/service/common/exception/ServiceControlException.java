package com.eaf.service.common.exception;

public class ServiceControlException extends Exception{
	public ServiceControlException() {
		super();
	}
	public ServiceControlException(String message) {
		super(message);
	}
	public ServiceControlException(String message, Throwable cause) {
		super(message, cause);
	}
	public ServiceControlException(Throwable cause) {
		super(cause);
	}
}
