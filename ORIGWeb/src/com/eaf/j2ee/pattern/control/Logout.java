package com.eaf.j2ee.pattern.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.util.LogoutUtil;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static transient Logger logger = Logger.getLogger(Logout.class);
    public Logout() {
        super();
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	if(null !=  request.getSession(false)){
    		String sessionId = request.getSession().getId();
			logger.debug("sessionId >> "+sessionId);
    	}
		String logoutExitPage = request.getParameter("logoutExitPage");
		logger.debug("logoutExitPage >> "+logoutExitPage);		
    	LogoutUtil.logout(request, response);
    	if(!Util.empty(logoutExitPage))
    		response.sendRedirect(logoutExitPage);
    } 
}
