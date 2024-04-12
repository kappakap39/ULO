package com.eaf.orig.filter;


public class LastAuthenticationErrorHelper {
	Throwable lastFailure;
	private String exception;
	public LastAuthenticationErrorHelper() {
		final String func = "LastAuthenticationErrorHelper";
		// did login fail?
		lastFailure = com.ibm.websphere.security.auth.WSSubject.getRootLoginException();		
	}
	public LastAuthenticationErrorHelper(Throwable e) {		 
		// did login fail?
		lastFailure = e; 		
	}

	public boolean wasLoginFailure() {
		return (lastFailure != null);
	}

	public Throwable getRootCause() {
		return determineCause(lastFailure);
	}

	private Throwable determineCause(Throwable e) {
		Throwable t = null;
		boolean isWASException = false;
		if (e instanceof com.ibm.websphere.security.auth.WSLoginFailedException) {
			isWASException = true;
			t = ((com.ibm.websphere.security.auth.WSLoginFailedException) e)
					.getCause();
			this.exception="com.ibm.websphere.security.auth.WSLoginFailedException";
		}
		if (e instanceof com.ibm.websphere.security.WSSecurityException) {
			isWASException = true;
			t = ((com.ibm.websphere.security.WSSecurityException) e).getCause();
			this.exception="com.ibm.websphere.security.WSSecurityException";
		}
//		#rawi comment
//		if (e instanceof com.ibm.websphere.wim.exception.WIMSystemException) {
//			isWASException = true;			
//			t = ((com.ibm.websphere.wim.exception.WIMSystemException) e).getCause();
//			this.exception="com.ibm.websphere.wim.exception.WIMSystemException";
//		}
//		if (e instanceof com.ibm.websphere.wim.exception.WIMException) {
//			this.exception="com.ibm.websphere.wim.exception.WIMException";
//			isWASException = true;			
//			t = ((com.ibm.websphere.wim.exception.WIMException) e).getCause();
//		}
//		if (e instanceof com.ibm.websphere.wim.exception.PasswordCheckFailedException ){
//			this.exception="com.ibm.websphere.wim.exception.PasswordCheckFailedException";
//			isWASException = true;
//		}				
		 		
//		System.out.println( "isWASException="+isWASException);
		// is the input a WAS exception? - if so, need to look at t
		if (isWASException) {
			// I hope we found a cause for the WAS exception
			if (t != null) { // good. search deeper
				Throwable  t2= determineCause(t);				 
				if(t2!=null){
//				System.out.println(  t2.getClass()+".getCause()="+t2.getCause());				
					if(t2.getCause()!=null){
					return t2;
					}
				}
				return t;
			} else { // this is bad. There should be a cause.
//				System.out.println("Error Log =null");
				return null;
			}
		} else { // this input must have been a "final" exception
			return e;
		}
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	
}
