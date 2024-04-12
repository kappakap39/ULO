/*
 * Created on Dec 12, 2007
 * Created by Sankom Sanpunya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
//import com.eaf.xrules.shared.factory.XRulesEJBFactory;

/**
 * @author Sankom
 * 
 * Type: SearchVerificationListReportWebaction
 */
public class SearchVerificationListReportWebaction extends WebActionHelper implements WebAction {
    Logger log = Logger.getLogger(SearchVerificationListReportWebaction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
         
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
         
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
         
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        //load model to session
        String errMsg = "";
        ApplicationDataM applicationDataM = null;
        String appRecordID = "";
        String applicatinNo = getRequest().getParameter("rpt_ApplicationNo");
        getRequest().getSession().removeAttribute("applicationVerification");
        getRequest().getSession().removeAttribute("ncbUserLog");
        if (applicatinNo != null) {
            try {
                //Load application by application record id
                //if(!ORIGUtility.isEmptyString(appRecordID)){
//                appRecordID = ORIGDAOFactory.getUtilityDAO().getMainApplicatinRecordIdByApplicaionNo(applicatinNo);
                
            	appRecordID =PLORIGEJBService.getORIGDAOUtilLocal().getMainApplicatinRecordIdByApplicaionNo(applicatinNo);
            	
                if (appRecordID != null && !"".equals(appRecordID)) {
                    String providerUrlEXT = null;
                    String jndiEXT = null;
                    ORIGApplicationManager applicationManager = ORIGEJBService.getApplicationManager();
                    applicationDataM = applicationManager.loadApplicationDataM(applicationDataM, appRecordID, providerUrlEXT, jndiEXT);
                    //applicationDataM.setAiid(aiid);
                    //applicationDataM.setClaimDate(new
                    // Timestamp(System.currentTimeMillis()));
                    /*
                     * PersonalInfoDataM personalInfoDataM = null; if
                     * (applicationDataM != null) { personalInfoDataM =
                     * utility.getPersonalInfoByType( applicationDataM,
                     * OrigConstant.PERSONAL_TYPE_APPLICANT); } else {
                     * log.debug("Cant' find applcation"); }
                     * getRequest().getSession(true)
                     * .setAttribute("personalApplicatinVerification",
                     * personalInfoDataM);
                     */
                    getRequest().getSession(true).setAttribute("applicationVerification", applicationDataM);
                    //Get NCB Report
//                    Vector ncbUserLogResult = XRulesEJBFactory.getNCBServiceManager().getNCBReqRespConsent(applicatinNo);
//                    getRequest().getSession(true).setAttribute("ncbUserLog", ncbUserLogResult);
                }
                //}
            } catch (Exception e) {
                log.error("load application exception", e);
                errMsg = "Load application error : " + e.getMessage();
            }
            getRequest().getSession().setAttribute("formSearh", "Y");
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
         
        return FrontController.PAGE;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
