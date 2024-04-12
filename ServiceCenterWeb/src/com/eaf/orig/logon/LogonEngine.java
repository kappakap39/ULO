package com.eaf.orig.logon;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.LogonDataM;
import com.eaf.orig.profile.model.UserDetailM;

public class LogonEngine {
	private static transient Logger logger = Logger.getLogger(LogonEngine.class);
	public static HashMap<String,String> logonErrorCode = null;
	public LogonEngine(){
		super();
	}
	public static final String ERROR_INVALID_IP = "ERROR_INVALID_IP";
	public static final String ERROR_ALREADY_LOGON = "ERROR_ALREADY_LOGON";
	public static final String ERROR_INVALID_USERNAME_PASSWORD = "ERROR_INVALID_USERNAME_PASSWORD";
	public static final String ERROR_PLEASE_CONTACT_ADMIN = "ERROR_PLEASE_CONTACT_ADMIN";
	public static final String ERROR_ON_SESSION = "ERROR_ON_SESSION";
	public static final String ERROR_PLEASE_CHANGE_PASSWORD = "ERROR_PLEASE_CHANGE_PASSWORD";
	public static final String ERROR_NO_AUTHORIZATION = "ERROR_NO_AUTHORIZATION";
	public static final String ERROR_LOGON_ANOTHER_COMPUTER = "ERROR_LOGON_ANOTHER_COMPUTER";
	public static final String ERROR_USER_CONFIGURATION_NOT_COMPLETED = "ERROR_USER_CONFIGURATION_NOT_COMPLETED";
	public static final String ERROR_PARAM_ERROR = "ERROR_PARAM_ERROR";
	public static final String ERROR_REQUIRE_USERNAME = "ERROR_REQUIRE_USERNAME";
	public static final String ERROR_REQUIRE_PASSWORD = "ERROR_REQUIRE_PASSWORD";
	public static final String ERROR_INACTIVE_USER = "ERROR_INACTIVE_USER";
	public static final String ERROR_MSG = "ERROR_MSG";	
	public static final String ERROR_PASSWORD_RESET = "ERROR_PASSWORD_RESET";
	public static final String ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND";
	public static final String ERROR_INVALID_CONDENTIAL = "ERROR_INVALID_CONDENTIAL";
	public static final String ERROR_NOT_PERMITTE_TIME = "ERROR_NOT_PERMITTE_TIME";
	public static final String ERROR_NOT_PERMITTE_WORKSTATION = "ERROR_NOT_PERMITTE_WORKSTATION";
	public static final String ERROR_PASSWORD_EXPIRE = "ERROR_PASSWORD_EXPIRE";
	public static final String ERROR_ACCOUNT_DISABLE = "ERROR_ACCOUNT_DISABLE";
	public static final String ERROR_ACCOUNT_EXPIRE = "ERROR_ACCOUNT_EXPIRE";
	public static final String ERROR_USER_RESET_PASSWORD = "ERROR_USER_RESET_PASSWORD";
	public static final String ERROR_USER_ACCOUNT_LOCK = "ERROR_USER_ACCOUNT_LOCK";
	public static final String ERROR_CONNECTION_TIME_OUT = "ERROR_CONNECTION_TIME_OUT";
	public static final String ERROR_UNEXPECTED = "ERROR_UNEXPECTED";
	
	public static void create(){
		logonErrorCode = new HashMap<String, String>();
		logonErrorCode.put(ERROR_INVALID_IP,"1001");
		logonErrorCode.put(ERROR_ALREADY_LOGON,"1002");
		logonErrorCode.put(ERROR_INVALID_USERNAME_PASSWORD,"1003");
		logonErrorCode.put(ERROR_PLEASE_CONTACT_ADMIN,"1004");
		logonErrorCode.put(ERROR_ON_SESSION,"1005");
		logonErrorCode.put(ERROR_PLEASE_CHANGE_PASSWORD,"1006");
		logonErrorCode.put(ERROR_NO_AUTHORIZATION,"1007");
		logonErrorCode.put(ERROR_LOGON_ANOTHER_COMPUTER,"1008");
		logonErrorCode.put(ERROR_USER_CONFIGURATION_NOT_COMPLETED,"1009");
		logonErrorCode.put(ERROR_PARAM_ERROR,"1010");
		logonErrorCode.put(ERROR_REQUIRE_USERNAME,"1011");
		logonErrorCode.put(ERROR_REQUIRE_PASSWORD,"1012");
		logonErrorCode.put(ERROR_INACTIVE_USER,"1013");
		logonErrorCode.put(ERROR_MSG,"1014");
		logonErrorCode.put(ERROR_PASSWORD_RESET,"2001");
		logonErrorCode.put(ERROR_USER_NOT_FOUND,"2002");
		logonErrorCode.put(ERROR_INVALID_CONDENTIAL,"2003");
		logonErrorCode.put(ERROR_NOT_PERMITTE_TIME,"2004");
		logonErrorCode.put(ERROR_NOT_PERMITTE_WORKSTATION,"2005");
		logonErrorCode.put(ERROR_PASSWORD_EXPIRE,"2006");
		logonErrorCode.put(ERROR_ACCOUNT_DISABLE,"2007");
		logonErrorCode.put(ERROR_ACCOUNT_EXPIRE,"2008");
		logonErrorCode.put(ERROR_USER_RESET_PASSWORD,"2009");
		logonErrorCode.put(ERROR_USER_ACCOUNT_LOCK,"2010");
		logonErrorCode.put(ERROR_CONNECTION_TIME_OUT,"2011");
		logonErrorCode.put(ERROR_UNEXPECTED,"2012");
	}
	public static String getCode(String Code){
		if(null == logonErrorCode){
			create();
		}
		return logonErrorCode.get(Code);
	}
	public static String getKey(String Code){
		if(null == logonErrorCode){
			create();
		}
		for(Entry<String, String> entry : logonErrorCode.entrySet()){
            if(entry.getValue().equals(Code)){
                return entry.getKey();
            }
	    }
		return "";
	}
	public static void processLogon(HttpServletRequest request,String type){
		LogonDataM logon = (LogonDataM)request.getSession().getAttribute("LogonData");
		if(null != logon){
			logger.debug("processLogon - " + logon.getUserName() + " - " + ApplicationDate.getTimestamp());
		}
	}
	public static void processLogout(HttpSessionEvent request,String type){
		logger.debug("processLogout..start");
		LogonDataM logon = (LogonDataM)request.getSession().getAttribute("LogonData");
		if(null != logon){
			logger.debug("processLogout - " + logon.getUserName() + " - " + ApplicationDate.getTimestamp());
		}
		logger.debug("processLogout..end");
	}
	public static void processLogout(HttpServletRequest request,String type){
		logger.debug("processLogout..start");
		LogonDataM logon = (LogonDataM)request.getSession().getAttribute("LogonData");	
		if(null != logon){
			logger.debug("processLogout - " + logon.getUserName() + " - " + ApplicationDate.getTimestamp());
		}
		logger.debug("processLogout..end");
	}
	public static String getErrorMsg(HttpServletRequest request){
		String errorMsg = "";
		try{
			String errorCode = request.getParameter("ERROR_CODE");
			if("1014".equals(errorCode))
			{
				errorMsg = request.getParameter("ERROR_MSG");
				if(Util.empty(errorMsg))
				{
					errorMsg = (String)request.getSession().getAttribute("ERROR_MSG");
				}			
			}else
			{
				if(!Util.empty(getKey(errorCode)))
				{
					errorMsg = getKey(errorCode).replace("_", " ");
				}
			}
			if(Util.empty(errorMsg)){
				errorMsg = "";
			}											
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return errorMsg;
	}
	public static boolean auth(HttpServletRequest request){
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		return (null != userM && !Util.empty(userM.getUserName()));
	}
	public static void init(HttpServletRequest request,HttpServletResponse response){
		logger.debug("init.LogonPage...");
		try{
			String userName = (String)request.getSession().getAttribute("userName");
			if(SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isAuthenticated())
			{
				logger.debug("User " + userName + " already logged in - redirect to index.jsp");
				response.sendRedirect("jsp/index.jsp");
			}
		}catch(Exception e){	
			logger.fatal("ERROR",e);
		}
	}
	public static String getValue(HttpServletRequest request,String param){
		try{
			String paramValue = (String)request.getSession(false).getAttribute(param);
			if(Util.empty(paramValue)){
				paramValue = "";
			}
			return paramValue;
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return "";
	}
}
