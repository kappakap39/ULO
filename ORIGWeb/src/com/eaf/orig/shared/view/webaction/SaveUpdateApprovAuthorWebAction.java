/*
 * Created on Sep 29, 2008
 * Created by Sankom
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

import java.sql.Date;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.ApprovAuthorEvent;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Sankom
 *
 * Type: SaveUpdateApprovAuthorWebAction
 */
public class SaveUpdateApprovAuthorWebAction extends WebActionHelper implements WebAction {
    Logger log = Logger.getLogger(SaveUpdateApprovAuthorWebAction.class);
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    private OrigPolicyVersionDataM origPolicyVersionDataM;
    public Event toEvent() {
		ApprovAuthorEvent approvAthrEvent = new ApprovAuthorEvent();
		approvAthrEvent.setEventType(ApprovAuthorEvent.POLICY_VERSION_SAVE_UPDATE);
		  UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		 if (null == userM) {
	            userM = new UserDetailM();
	        }
	        String userName = userM.getUserName(); 
		log.debug("ApprovAuthorEvent.POLICY_VERSION_SAVE_UPDATE=" + ApprovAuthorEvent.POLICY_VERSION_SAVE_UPDATE);		
		approvAthrEvent.setObject(origPolicyVersionDataM);
		approvAthrEvent.setUserName(userM.getUserName());		 		
		return approvAthrEvent;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
     
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {     
        	return defaultProcessResponse(response);
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
    	ORIGUtility utility = new ORIGUtility();
		  UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			 if (null == userM) {
		            userM = new UserDetailM();
		        }
		        String userName = userM.getUserName(); 
		String origPolicyVersion=getRequest().getParameter("policyVersion");
		String strEffectiveDate=getRequest().getParameter("effectiveDate");
	    String strExpireDate=getRequest().getParameter("expireDate");	     
	    Date effectiveDate= utility.parseThaiToEngDate(strEffectiveDate);
	    Date expireDate= utility.parseThaiToEngDate(strExpireDate);
		String description=getRequest().getParameter("description");
		String displayType=(String)getRequest().getSession().getAttribute("DISPLAY_TYPE");
		if("new".equals(displayType)||"newcopy".equals(displayType)){
		    try {
//                OrigPolicyVersionDataM policyVersionDataM = ORIGDAOFactory.getOrigPolicyVersionMDAO().loadModelOrigPolicyVersionDataM(origPolicyVersion);
                
		    	 OrigPolicyVersionDataM policyVersionDataM =  PLORIGEJBService.getORIGDAOUtilLocal().loadModelOrigPolicyVersionDataM(origPolicyVersion);
		    	 
		    	if(policyVersionDataM!=null){
                    ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
                    String errMsg="Duplicate PolicyVersion";
                    log.debug("Save Fail Error " +errMsg);
                    origMasterForm.getFormErrors().add(errMsg);
                    return false;
                }
            } catch (Exception e) {                
                log.fatal("Error ",e);
            }		    
		}
		if(effectiveDate.after(expireDate)){	
		    ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
            String errMsg="Expire Date must be more than Effective Date";
            log.debug("Save Fail Error " +errMsg);
            origMasterForm.getFormErrors().add(errMsg);		    
		    return false;
		}		
	    OrigPolicyVersionDataM policyVersionDataM = (OrigPolicyVersionDataM) getRequest().getSession().getAttribute("POLICY_VERSION");
		if(policyVersionDataM==null){policyVersionDataM=new OrigPolicyVersionDataM();}
		policyVersionDataM.setPolicyVersion(origPolicyVersion);
		policyVersionDataM.setEffectiveDate(effectiveDate);
		policyVersionDataM.setExpireDate(expireDate);
		policyVersionDataM.setUpdateBy(userName);
		policyVersionDataM.setDescription(description);
		origPolicyVersionDataM=policyVersionDataM;
		setNextActionParameter("page=MS_APPROV_AUTHOR_SEARCHING_SCREEN");
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.ACTION;
    }
    protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		setNextActionParameter("action=SearchApprovAuthor");
	}
    protected void doFail(EventResponse arg0) {
		
		ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
        String errMsg = arg0.getMessage();
        log.debug("Save Fail Error "+errMsg );
        origMasterForm.getFormErrors().add(errMsg);
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
    
}
