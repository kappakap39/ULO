package com.eaf.orig.ulo.dm.dao.exception;

public class DMException extends Exception{
	public DMException() {
		super();
	}
	public DMException(String message) {
		super(message);
	}
	public DMException(String message, Throwable cause) {
		super(message, cause);
	}
	public DMException(Throwable cause) {
		super(cause);
	}
}
