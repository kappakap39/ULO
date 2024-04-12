package com.eaf.orig.logon;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.shared.util.OrigUtil;


public class LogonMessage {
	public LogonMessage(){
		super();
	}
	public static HashMap<String,String> message;
	public static final String MESSAGE_INVALID_IP = "1";
	public static final String MESSAGE_ALREADY_LOGON = "2";
	public static final String MESSAGE_INVALID_USERNAME_PASSWORD = "3";
	public static final String MESSAGE_PLEASE_CONTRACT_ADMIN = "4";
	public static final String MESSAGE_FROM_SESSION_ERRORLOGON = "5";
	public static final String MESSAGE_PLEASE_CHANGE_PASSWORD = "6";
	public static final String MESSAGE_NO_AUTHORIZATION = "7";
	public static final String MESSAGE_LOGON_ANOTHER_COMPUTER = "8";
	public static final String MESSAGE_USER_CONFIGURATION_NOT_COMPLETED = "9";
	public static final String MESSAGE_PARAM_ERRORMSG = "10";
	public static final String MESSAGE_REQUIRE_USERNAME = "11";
	public static final String MESSAGE_REQUIRE_PASSWORD = "12";
	public static final String MESSAGE_INACTIVE_USER = "13";
	
	public static String GetMessage(HttpServletRequest request){	
		init();
		String error = request.getParameter("err");
		if(!OrigUtil.isEmptyString(error)){
			String msg = message.get(error);
			if(!OrigUtil.isEmptyString(msg)){
				return msg;
			}else{
				if(MESSAGE_ALREADY_LOGON.equals(error)){
					return request.getSession().getAttribute("userName")+" Already logon in other screen.";
				}else if(MESSAGE_FROM_SESSION_ERRORLOGON.equals(error)){
					return (String)request.getSession().getAttribute("errorLogon");
				}else if(MESSAGE_PARAM_ERRORMSG.equals(error)){
					return request.getParameter("msgError");
				}
			}
		}
		return "";
	}
	public static void init(){
		if(null == message){
			message = new HashMap<String,String>();
			message.put(MESSAGE_INVALID_IP, "Invalid IP address and user name, Please contact administrator.");
			message.put(MESSAGE_ALREADY_LOGON,"");
			message.put(MESSAGE_INVALID_USERNAME_PASSWORD, "Logon Fail, invalid user name or password.");
			message.put(MESSAGE_PLEASE_CONTRACT_ADMIN, "Logon Fail, Please contact administrator.");
			message.put(MESSAGE_FROM_SESSION_ERRORLOGON, "");
			message.put(MESSAGE_PLEASE_CHANGE_PASSWORD, "You have new password, Please change password before.");
			message.put(MESSAGE_NO_AUTHORIZATION, "No Authorization.");
			message.put(MESSAGE_LOGON_ANOTHER_COMPUTER, "You have logged on to system on another computer, Please logout or contact administrator.");
			message.put(MESSAGE_USER_CONFIGURATION_NOT_COMPLETED, "User configuration is not completed, Please  Contract Administrator.");
			message.put(MESSAGE_PARAM_ERRORMSG, "");
			message.put(MESSAGE_REQUIRE_USERNAME, "Username is required!");
			message.put(MESSAGE_REQUIRE_PASSWORD, "Password is required!");
			message.put(MESSAGE_INACTIVE_USER, "Inactive User!");			
		}
	}
	
}
