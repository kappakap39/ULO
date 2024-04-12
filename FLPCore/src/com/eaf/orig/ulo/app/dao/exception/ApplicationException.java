package com.eaf.orig.ulo.app.dao.exception;

public class ApplicationException extends Exception{
	public ApplicationException() {
		super();
	}
	public ApplicationException(String message) {
		super(message);
	}
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
	public ApplicationException(Throwable cause) {
		super(cause);
	}
}
