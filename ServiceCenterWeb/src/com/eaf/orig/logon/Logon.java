package com.eaf.orig.logon;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.LogonDataM;
import com.eaf.orig.ulo.constant.MConstant;

public class Logon extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(Logon.class);
	String IAS_SERVICE_AUTHENTICATION_URL = SystemConfig.getProperty("IAS_SERVICE_AUTHENTICATION_URL");
	String CHECK_LOGON_FLAG = SystemConfig.getProperty("CHECK_LOGON_FLAG");
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");	
		logger.debug("userName >> "+userName);
//		logger.debug("password >> "+password);
		initLogon(userName, password, request);
		ArrayList<String> errorLogon = new ArrayList<String>();
		try
		{	
			//Shiro Authen
			errorLogon = authenShiro(userName, password, request, response);
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			errorLogon.add(LogonEngine.ERROR_PLEASE_CONTACT_ADMIN);
		}
		logger.info("errorLogon >> "+errorLogon);
		if(Util.empty(errorLogon)){		
			request.getSession().setAttribute("FirstLogon",MConstant.FLAG.YES);
			request.getSession().setAttribute("userName",userName);
			request.getSession().setAttribute("password",password);
			logger.debug("redirect to index.jsp");
			response.sendRedirect("jsp/index.jsp");			
		}else{
			String errorCode = errorLogon.get(0);
			logger.error("errorCodeTxt >> "+errorCode);
			logger.error("errorCode >> " + LogonEngine.getCode(errorCode));
			response.sendRedirect("logon.jsp?ERROR_CODE="+LogonEngine.getCode(errorCode));
		}
	}
	private void initLogon(String userName,String password,HttpServletRequest request){
		request.getSession().removeAttribute("userName");
		request.getSession().removeAttribute("password");
		request.getSession().removeAttribute("FirstLogon");
		request.getSession().removeAttribute("ERROR_CODE");
		request.getSession().removeAttribute("ERROR_MSG");
		LogonDataM logon = new LogonDataM();
		logon.setUserName(userName);
		request.getSession().setAttribute("LogonData",logon);
		request.getSession().setAttribute("LogonUserName",userName);
		request.getSession().setAttribute("LogonPassword",password);
	}
	
	private ArrayList<String> authenShiro(String userName,String password, HttpServletRequest request, HttpServletResponse response)
	{
		ArrayList<String> errorLogon = new ArrayList<>();
		
		Subject currentUser = SecurityUtils.getSubject();
		
		if(Util.empty(userName)){
			errorLogon.add(LogonEngine.ERROR_REQUIRE_USERNAME);
			return errorLogon;
		}
		if(Util.empty(password)){
			errorLogon.add(LogonEngine.ERROR_REQUIRE_PASSWORD);
			return errorLogon;
		}
		if(Util.empty(currentUser)){
			errorLogon.add(LogonEngine.ERROR_UNEXPECTED);
			return errorLogon;
		}
		
		if(!Util.empty(currentUser) && !Util.empty(userName) && !Util.empty(password))
		{
			try 
			{              
				if(currentUser.isAuthenticated())
				{
					currentUser.logout();
				}
				UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
				token.setRememberMe(true);
				currentUser.login(token);
			} catch (UnknownAccountException uae)
			{
				logger.error("Username Not Found!", uae);
				errorLogon.add(LogonEngine.ERROR_USER_NOT_FOUND);
			} catch (IncorrectCredentialsException ice)
			{
				logger.error("Invalid Credentials!", ice);
				errorLogon.add(LogonEngine.ERROR_INVALID_USERNAME_PASSWORD);
			} catch (LockedAccountException lae)
			{
				logger.error("Your Account is Locked!", lae);
				errorLogon.add(LogonEngine.ERROR_USER_ACCOUNT_LOCK);
			} catch (Exception ue)
			{
				logger.error("Unexpected Error!", ue);
				errorLogon.add(LogonEngine.ERROR_UNEXPECTED);
			}
		}
		return errorLogon;
	}
	
}
