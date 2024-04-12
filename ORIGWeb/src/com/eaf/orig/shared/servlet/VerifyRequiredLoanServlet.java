package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.model.ApplicationDataM;

public class VerifyRequiredLoanServlet extends HttpServlet implements Servlet {
    Logger logger = Logger.getLogger(VerifyRequiredLoanServlet.class);
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public VerifyRequiredLoanServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    doPost(req,resp);
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    ORIGFormHandler ORIGForm = (ORIGFormHandler) req.getSession().getAttribute("ORIGForm");
	    ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	    String returnValue = "";
	    
	    if(applicationDataM!=null && applicationDataM.getLoanVect()!=null && applicationDataM.getLoanVect().size()>0){
	        returnValue = "1";
	    }else{
	        returnValue = "0";
	    }
	    
	    //Create response
		resp.setContentType("text/xml;charset=UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		
		PrintWriter pw = resp.getWriter();	
		logger.debug("returnValue = "+returnValue);
		pw.write(returnValue);
		pw.close();
		logger.debug("==> out doPost");
	    
	}
		

}