package com.eaf.j2ee.pattern.control.util;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class LogoutUtil {
	private static transient Logger logger = Logger.getLogger(LogoutUtil.class);
	public static void logout(HttpServletRequest request,HttpServletResponse response){
		try{
			if(null !=  request.getSession(false)){
				String sessionId = request.getSession().getId();
    			logger.debug("sessionId >> "+sessionId);     		
    			request.getSession().invalidate();
			}
    		request.logout();
    		ArrayList<String> Cookies = new ArrayList<String>();
    		Cookies.add("LtpaToken2");
    		Cookies.add("JSESSIONID");
    		Cookie[] cookiesRequest =  request.getCookies();
    		if(null != cookiesRequest){
    			for (Cookie cookie : cookiesRequest) {
    				String Name = cookie.getName();
    				if(!Cookies.contains(Name)){
    					Cookies.add(Name);
    				}
				}
    		}
    		logger.debug("Cookies >> "+Cookies);
    		if(null != Cookies){
    			for (String cookiesName : Cookies) {
    				Cookie cookiesItem = new Cookie(cookiesName, null);
    				cookiesItem.setPath("/");
    				cookiesItem.setHttpOnly(true);
    				cookiesItem.setMaxAge(0);
    				response.addCookie(cookiesItem);
				}
    		}
    	}catch(Exception e){
    		logger.fatal("ERROR",e);
    	}
	}
	public static String getUrl(HttpServletRequest request){
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	}
}
