package com.eaf.orig.ulo.app.dao.exception;

/**
 * @author Anu Elias
 *
 * Type: UniqueIDGeneratorException
 */
public class UniqueIDGeneratorException extends Exception {
	/**
	 * 
	 */
	public UniqueIDGeneratorException() {
		super();
		
	}

	/**
	 * @param message
	 */
	public UniqueIDGeneratorException(String message) {
		super(message);
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UniqueIDGeneratorException(String message, Throwable cause) {
		super(message, cause);
		
	}

	/**
	 * @param cause
	 */
	public UniqueIDGeneratorException(Throwable cause) {
		super(cause);
		
	}
}
