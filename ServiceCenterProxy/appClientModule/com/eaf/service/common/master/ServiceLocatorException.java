package com.eaf.service.common.master;

public class ServiceLocatorException extends Exception{
	public ServiceLocatorException() {
		super();		 
	}
	public ServiceLocatorException(String message) {
		super(message);
	 
	}
	public ServiceLocatorException(String message, Throwable cause) {
		super(message, cause);
	 
	}
	public ServiceLocatorException(Throwable cause) {
		super(cause);		
	}
}
