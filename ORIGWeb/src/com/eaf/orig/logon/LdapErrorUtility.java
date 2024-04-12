package com.eaf.orig.logon;

import org.apache.log4j.Logger;


public class LdapErrorUtility {
	private static transient Logger logger = Logger.getLogger(LdapErrorUtility.class);	
	public static String getError(String errorMessage, String exception) {
		logger.debug("ErrorMessage >> " + errorMessage);
		logger.debug("exception >> " + exception);
		String resErrorMessage = "";
		if (errorMessage == null || "null".equals(errorMessage)) {
			resErrorMessage = LogonEngine.ERROR_UNEXPECTED;
		}else if("com.ibm.websphere.wim.exception.PasswordCheckFailedException".equals(exception)) {
			resErrorMessage = LogonEngine.ERROR_INVALID_CONDENTIAL;
		}else if(errorMessage.indexOf("[Root exception is java.net.ConnectException: Connection timed out: connect]") != -1) {
			resErrorMessage = LogonEngine.ERROR_CONNECTION_TIME_OUT;
		}
		if(errorMessage.indexOf("[LDAP: error code 49 - Invalid Credentials]") != -1) {
			resErrorMessage = LogonEngine.ERROR_INVALID_CONDENTIAL;
		}else if(errorMessage.indexOf("[LDAP: error code 53 - Error, Account is locked]") != -1) {
			resErrorMessage = LogonEngine.ERROR_USER_ACCOUNT_LOCK;
		}else if(errorMessage.indexOf("[LDAP: error code 49 - Error, Password has expired]") != -1) {
			resErrorMessage = LogonEngine.ERROR_PASSWORD_EXPIRE;
		}else if(errorMessage.indexOf("javax.naming.CommunicationException") != -1) {
			resErrorMessage = LogonEngine.ERROR_CONNECTION_TIME_OUT;
		}else if(errorMessage == null || "".equals(errorMessage)) {
			resErrorMessage = LogonEngine.ERROR_UNEXPECTED;
		}else{
			resErrorMessage = LogonEngine.ERROR_PLEASE_CONTRACT_ADMIN;
		}	
		return resErrorMessage;
	}

}
