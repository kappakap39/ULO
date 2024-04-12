package com.eaf.core.ulo.flp.security.exception;

@SuppressWarnings("serial")
public class FLPEncryptionException extends Exception {

	public FLPEncryptionException() {
		super();
	}

	public FLPEncryptionException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public FLPEncryptionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FLPEncryptionException(String arg0) {
		super(arg0);
	}

	public FLPEncryptionException(Throwable arg0) {
		super(arg0);
	}

}
