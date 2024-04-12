package com.eaf.j2ee.pattern.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static transient Logger logger = Logger.getLogger(Logout.class);
    public Logout() {
        super();
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	try{
    		if(null != request.getSession(false)){
    			String sessionId = request.getSession().getId();
    			logger.debug("DMWeb.sessionId >> "+sessionId);
    			request.getSession().invalidate();
    		}
    		request.logout();
    	}catch(Exception e){
    		logger.fatal("ERROR",e);
    	}
    }
}
