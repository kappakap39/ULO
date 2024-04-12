/*
 * Created on Nov 14, 2007
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
package com.eaf.orig.shared.popup.view.webaction;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 *
 * Type: SaveXrulesWebverPopupWebAction
 */
public class SaveXrulesWebverPopupWebAction extends WebActionHelper implements
        WebAction {
    private static Logger log= (Logger) Logger
    .getLogger(SaveXrulesWebverPopupWebAction.class);
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
         
        return null;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
         
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
         
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        log.debug("SaveXrulesWebverPopupWebAction-->preModelRequest");
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		UserDetailM ORIGUser = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
    	if(applicationDataM == null){
    		applicationDataM = new ApplicationDataM();
    	}
    	ORIGUtility utility = new ORIGUtility();
    	String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
    	PersonalInfoDataM personalInfoDataM;
    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA");
    	}else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		}else{
    		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
    	}
    	if(personalInfoDataM == null){
    		personalInfoDataM = new PersonalInfoDataM();
    		//personalInfoDataM.setPersonalType(personalType);
    		//applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
    	}
    	XRulesVerificationResultDataM xRulesVerification=personalInfoDataM.getXrulesVerification();
    	
    	
        String xrulesExecuteResult=getRequest().getParameter("webFinalResult");
        String strXrulesService=getRequest().getParameter("serviceId");                
        //String txtResult;Name=getRequest().getParameter("txtResultName");
    	//String txtButtonName=getRequest().getParameter("txtButtonName");
    	String txtWebverComment=getRequest().getParameter("webVerComment");
    	int serviceID=0;
    	try{
        	serviceID=Integer.parseInt(strXrulesService.trim());
        	}catch (Exception ex){
        	 ex.printStackTrace();
        	}
       String userRole=""; 	
       if(ORIGUser.getRoles()!=null &&ORIGUser.getRoles().size()>0){ 	
           userRole=(String)ORIGUser.getRoles().get(0); 	
       }
       Timestamp updateTime = new Timestamp((new Date()).getTime());
        if(serviceID==XRulesConstant.ServiceID.KHONTHAI){
            xRulesVerification.setKhonThaiResult(xrulesExecuteResult) ;
            xRulesVerification.setKhonThaiComment(txtWebverComment);
            xRulesVerification.setKhonthaiUpdateDate(updateTime);
            xRulesVerification.setKhonthaiUpdateBy(ORIGUser.getUserName());
            xRulesVerification.setKhonthaiUpdteRole(userRole);
            
        }else if(serviceID==XRulesConstant.ServiceID.BOL){
            xRulesVerification.setBOLResult(xrulesExecuteResult) ;
            xRulesVerification.setBOLComment(txtWebverComment);
            xRulesVerification.setBolUpdateDate(updateTime);
            xRulesVerification.setBolUpdateBy(ORIGUser.getUserName());
            xRulesVerification.setBolUpdateRole(userRole);
        }else if(serviceID==XRulesConstant.ServiceID.THAIREGITRATION){
            xRulesVerification.setThaiRegistrationResult(xrulesExecuteResult) ;
            xRulesVerification.setThaiRegistrationComment(txtWebverComment);
            xRulesVerification.setThaiRegistrationUpdateDate(updateTime);
            xRulesVerification.setThaiRegistrationUpdateBy(ORIGUser.getUserName());
            xRulesVerification.setThaiRegistrationUpdateRole(userRole);
        }else if(serviceID==XRulesConstant.ServiceID.YELLOWPAGE){
            xRulesVerification.setYellowPageResult(xrulesExecuteResult) ;
            xRulesVerification.setYellowPageComment(txtWebverComment);
            xRulesVerification.setYellowsPagesUpdateDate(updateTime);
            xRulesVerification.setYellowsPageUpdateRole(userRole);
            xRulesVerification.setYellowsPagesUpdateBy(ORIGUser.getUserName());
        }else if(serviceID==XRulesConstant.ServiceID.PHONEBOOK){
            xRulesVerification.setPhoneBookResult(xrulesExecuteResult) ;
            xRulesVerification.setPhoneBookComment(txtWebverComment);
            xRulesVerification.setPhonebookUpdateDate(updateTime);
            xRulesVerification.setPhonebookUpdateBy(ORIGUser.getUserName());
            xRulesVerification.setPhoneBookUpdateRole(userRole);
        }
                
        String txtResultName=getRequest().getParameter("txtResultName");
    	getRequest().getSession().setAttribute("txtResultName",txtResultName);
    	log.debug("SaveXrulesWebverPopupWebAction xrulseExecuteService "+strXrulesService);
        log.debug("SaveXrulesWebverPopupWebAction txtResultName "+txtResultName);
        log.debug("SaveXrulesWebverPopupWebAction xrulesExecuteResult "+xrulesExecuteResult);        
    	//getRequest().getSession().setAttribute("txtButtonName",txtButtonName);    	
    	getRequest().getSession().setAttribute("execResult",xrulesExecuteResult);
    	getRequest().getSession().setAttribute("xrulseExecuteService",strXrulesService);     
        getRequest().getSession().setAttribute("openPopup","N");
        return true;
    }

    /* (non-Javadoc)
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
