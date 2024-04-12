package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CheckSessionAlive extends HttpServlet implements Servlet {

	Logger log = Logger.getLogger(CheckSessionAlive.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache,no-store");
		response.setDateHeader("Expires", 0);
		
		PrintWriter pw = response.getWriter();	
		String result = "false";
		
		if(request.getSession(false)!=null){
			if(request.getSession(false).getId()!=null && !"".equals(request.getSession(false).getId())){
				log.debug("##### CheckSessionAlive #####");
				result = "true";
			}
		}
		
		pw.write(result);
		pw.close();
	}

}