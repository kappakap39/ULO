/*
 * Created on Jan 16, 2008
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.utility;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.ServiceDataM;
import com.eaf.orig.shared.service.ORIGEJBService;


/**
 * @author Avalant
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SendEmailAndSMS extends Thread {
	Logger logger = Logger.getLogger(SendEmailAndSMS.class);
	HttpServletRequest request;
	public SendEmailAndSMS(HttpServletRequest requestIn) {
		super();
		this.request = requestIn;
	}
	
	public void run() {
		ORIGFormHandler formHandler = (ORIGFormHandler) this.request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
    	UserDetailM userM = (UserDetailM) this.request.getSession().getAttribute("ORIGUser");
    	
    	ApplicationDataM applicationDataM = formHandler.getAppForm();
		String decision = applicationDataM.getDeDecision();
		String role = (String) userM.getRoles().elementAt(0);
    	try{
    		
    		ORIGUtility utility = new ORIGUtility();
			ServiceDataM serviceDataM = utility.getServiceDataM();
    		ORIGApplicationManager applicationManager = ORIGEJBService.getApplicationManagerNonJava();
    		applicationManager.sendSMSAndEmail(applicationDataM, serviceDataM, userM);
	    	
    	}catch(Exception e){
    		logger.error("Can not sent SMS/Email >>", e);
    	}
	}
}
