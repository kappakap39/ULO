package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGMandatoryErrorUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

public class MandatoryPLoanPopupServlet extends HttpServlet implements Servlet {
	Logger logger = Logger.getLogger(MandatoryPLoanPopupServlet.class);
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public MandatoryPLoanPopupServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
	    doPost(arg0,arg1);
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("<<<<<<< Start MandatoryPLoanPopupServlet >>>>>>>");
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGMandatoryErrorUtil errorUtil = new ORIGMandatoryErrorUtil();
        ORIGUtility utility = new ORIGUtility();
        
        String userRole = (String) userM.getRoles().elementAt(0);
        boolean isError = false;
        
        isError = errorUtil.getMandateErrorPLoanForCalulate(request);
        
        logger.debug("isError = "+isError);
        StringBuffer sb = new StringBuffer("");
        if (isError) {
			sb = errorUtil.getDisplayError(formHandler);
		}
        
        //Clear Error data
		formHandler.setFormErrors(new Vector());
		formHandler.setErrorFields(new Vector());
		formHandler.setNotErrorFields(new Vector());
		String returnValue = sb.toString();
		
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