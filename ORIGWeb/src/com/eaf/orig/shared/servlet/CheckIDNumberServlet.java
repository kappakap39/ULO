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
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGUtility;

public class CheckIDNumberServlet extends HttpServlet implements Servlet {
    Logger logger = Logger.getLogger(CheckIDNumberServlet.class);
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CheckIDNumberServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
	    logger.debug("Start CheckIDNumberServlet");
	    String idNo = req.getParameter("idNo");
	    String returnValue = "";

		ORIGUtility utility = new ORIGUtility();
		ORIGFormHandler ORIGForm = new ORIGFormHandler();
		UserDetailM userM = (UserDetailM) req.getSession().getAttribute("ORIGUser");
		
		try {
			PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
			personalInfoDataM.setIdNo(idNo);
			personalInfoDataM.setCmpCode(utility.getGeneralParamByCode(OrigConstant.ORIG_CMPCDE));
			String providerUrlEXT = null;
			String jndiEXT = null;
			
//			ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//			personalInfoDataM = applicationEXTManager.loadModelPersonal(personalInfoDataM);
	        
			//Load Verification List
			ORIGApplicationManager applicationManager = ORIGEJBService.getApplicationManager();
			personalInfoDataM = applicationManager.getVerificationForCreateDrawDown(personalInfoDataM);
			
	        if(personalInfoDataM!=null&&personalInfoDataM.getThaiFirstName()!=null&&!personalInfoDataM.getThaiFirstName().equals("")){
	            	personalInfoDataM.setPersonalType(OrigConstant.PERSONAL_TYPE_APPLICANT);
	            	req.getSession().setAttribute("CUSTOMER_DRAW_DOWN",personalInfoDataM);
				    returnValue = "FOUND";
	        }else{
	        	 returnValue = "NOT_FOUND";
	        }
		}catch(Exception e){
			logger.error("Error >>> ", e);
			if(userM == null){
				returnValue = "This page has been expired. Please close this window and login again on new window.";
			}else{
				returnValue = "Can not retrieve data, please contact admin.";
			}
		}
        resp.setContentType("text/xml;charset=UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		
		PrintWriter pw = resp.getWriter();	
		logger.debug("returnValue = "+returnValue);
		
		pw.write(returnValue);
		pw.close();
	}

}