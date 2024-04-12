package com.eaf.orig.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.eaf.orig.logon.LdapErrorUtility;
import com.eaf.orig.logon.LogonEngine;

@WebFilter(filterName="/LoginErrorFilter",urlPatterns="/j_security_check")
public class LoginErrorFilter implements Filter {
	private static Logger log = Logger.getLogger(LoginErrorFilter.class);
	protected FilterConfig filterConfig;	
	public LoginErrorFilter(){
		super();
	}
	public void destroy(){
		
	}
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException{
		String username = request.getParameter("j_username");		
		log.info("USERNAME >>"+username);
		LastAuthenticationErrorHelper errorHelper = null;
		try{
			chain.doFilter(request, response);
		}catch(Exception e){			
			log.fatal("Exception ", e);		
		}
		if(errorHelper == null){
			errorHelper = new LastAuthenticationErrorHelper();
		}
		ServletResponseWrapper responseWrapper = new ServletResponseWrapper(response);
		log.info("LoginErrorFilter: j_security_check : "+responseWrapper.getResponse().toString());
		String ERROR_CODE = "";
		if (errorHelper !=null && errorHelper.wasLoginFailure()) {		
			Throwable rootCause = errorHelper.getRootCause();
			String exception = errorHelper.getException();
			if(rootCause!=null){
				log.debug("getRootCause Message = "+rootCause.getCause().getMessage());
				log.debug("getRootCause toString = "+rootCause.getCause().toString());
				log.debug("getRootCause ClassName = "+rootCause.getClass().getName());
				rootCause.getCause().printStackTrace();
				ERROR_CODE = LdapErrorUtility.getError(rootCause.getCause().toString(),exception);		
			}
		}
		log.debug("ERROR_CODE"+ERROR_CODE);
		HttpSession s = ((HttpServletRequest)request).getSession();
		s.setAttribute("ERROR_CODE",LogonEngine.getCode(ERROR_CODE));
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig = fConfig;
	}

}
