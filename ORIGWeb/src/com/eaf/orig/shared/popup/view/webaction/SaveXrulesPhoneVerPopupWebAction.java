/*
 * Created on Nov 13, 2007
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
import java.util.Calendar;
import java.util.Vector;

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
import com.eaf.xrules.shared.model.XRulesPhoneVerificationDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 * 
 * Type: SaveXrulesPhoneVerPopupWebAction
 */
public class SaveXrulesPhoneVerPopupWebAction extends WebActionHelper implements
        WebAction {
    private static Logger log = (Logger) Logger
            .getLogger(SaveXrulesPhoneVerPopupWebAction.class);

    private int nextActivityType;

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
        log.debug("SaveXrulesPhoneVerPopupWebAction -->preModelRequest");
        String phoneVerAction = getRequest().getParameter("phoneVerAction");
        log.debug(" phoneVerAction --> " + phoneVerAction);
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession()
                .getAttribute("ORIGForm");
        UserDetailM ORIGUser = (UserDetailM) getRequest().getSession()
                .getAttribute("ORIGUser");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
        // ApplicationDataM appForm = ORIGForm.getAppForm();
        //get Personal
        if (applicationDataM == null) {
            applicationDataM = new ApplicationDataM();
        }
        ORIGUtility utility = new ORIGUtility();
        String personalType = (String) getRequest().getSession().getAttribute(
                "PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(
                    true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(ORIGForm
                    .getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
           // personalInfoDataM.setPersonalType(personalType);
           // applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
        }
        XRulesVerificationResultDataM xrulesVerificationDataM = personalInfoDataM
                .getXrulesVerification();
        Vector vPhoneVerification = null;
        if (getRequest().getSession().getAttribute("phoneVerItem") == null) {
            vPhoneVerification = xrulesVerificationDataM
                    .getVXRulesPhoneVerificationDataM();
        } else {
            vPhoneVerification = (Vector) getRequest().getSession()
                    .getAttribute("phoneVerItem");
        }
        String updateRole="";
        if(ORIGUser!=null &&ORIGUser.getRoles()!=null&& ORIGUser.getRoles().size()>0){
             updateRole=(String)ORIGUser.getRoles().get(0); 
        }  

        String xrulesExecuteResult = "";
        if ("addPhone".equalsIgnoreCase(phoneVerAction)) {
            String selectPartyType = getRequest()
                    .getParameter("phonePartyType");
            String contactType = getRequest().getParameter("phoneItem");
            //String callType=getRequest().getp;
            String remark = getRequest().getParameter("txtRemark");
            XRulesPhoneVerificationDataM prmXRulesPhoneVerificationDataM = new XRulesPhoneVerificationDataM();
            prmXRulesPhoneVerificationDataM.setCallType(selectPartyType);
            prmXRulesPhoneVerificationDataM.setContactType(contactType);
            prmXRulesPhoneVerificationDataM.setPhoneVerStatus("A");
            prmXRulesPhoneVerificationDataM.setRemark(remark);
            prmXRulesPhoneVerificationDataM.setUpdateDate(new Timestamp(
                    Calendar.getInstance().getTimeInMillis()));
            vPhoneVerification.add(prmXRulesPhoneVerificationDataM);                    
            //new or update phone ver item session
            this.nextActivityType = FrontController.ACTION;
            String nextAction = "action=LoadXrulesPhoneVerPopup";
            /*nextAction = nextAction.concat("&txtResultName="
                    + (String) (getRequest().getSession()
                            .getAttribute("txtResultName")));
            nextAction = nextAction.concat("&txtButtonName="
                    + (String) (getRequest().getSession()
                            .getAttribute("txtButtonName")));*/          
            this.setNextActionParameter(nextAction);
            getRequest().getSession().setAttribute("phoneVerItem",
                    vPhoneVerification);
        } else if ("save".equalsIgnoreCase(phoneVerAction)) {
            //change Action
            //remove phonever item sesion
            String phoneVerificationResult = getRequest().getParameter(
                    "phoneVerificationResult");
            if (phoneVerificationResult != null) {
                xrulesVerificationDataM
                        .setPhoneVerResult(phoneVerificationResult);
            } else {
                phoneVerificationResult = "";
            }

            xrulesExecuteResult = phoneVerificationResult;
            xrulesVerificationDataM
                    .setVXRulesPhoneVerificationDataM(vPhoneVerification);
            xrulesVerificationDataM.setPhoneVerUpdateBy(ORIGUser.getUserName());
            xrulesVerificationDataM.setPhoneVerUpdateDate(new Timestamp(
                    Calendar.getInstance().getTimeInMillis()));
            xrulesVerificationDataM.setPhoneVerUpdateRole(updateRole);
            this.nextActivityType = FrontController.ACTION;
            String nextAction = "action=LoadXrulesPhoneVerPopup";
           /* nextAction = nextAction.concat("&txtResultName="
                    + (String) (getRequest().getSession()
                            .getAttribute("txtResultName")));
            nextAction = nextAction.concat("&txtButtonName="
                    + (String) (getRequest().getSession()
                            .getAttribute("txtButtonName")));*/
            
            this.setNextActionParameter(nextAction);
            getRequest().getSession().setAttribute("phoneVerItem",
                    vPhoneVerification);
        } else if ("saveAndClose".equalsIgnoreCase(phoneVerAction)) {
            String phoneVerificationResult = getRequest().getParameter(
                    "phoneVerificationResult");
            if (phoneVerificationResult != null) {
                xrulesVerificationDataM
                        .setPhoneVerResult(phoneVerificationResult);
            } else {
                phoneVerificationResult = "";
            }
            xrulesExecuteResult = phoneVerificationResult;
            xrulesVerificationDataM
                    .setVXRulesPhoneVerificationDataM(vPhoneVerification);
            xrulesVerificationDataM.setPhoneVerUpdateBy(ORIGUser.getUserName());
            xrulesVerificationDataM.setPhoneVerUpdateDate(new Timestamp(
                    Calendar.getInstance().getTimeInMillis()));
            xrulesVerificationDataM.setPhoneVerUpdateRole(updateRole);
            getRequest().getSession().removeAttribute("phoneVerItem");
            this.nextActivityType = FrontController.PAGE;
        }else if( "Close".equalsIgnoreCase(phoneVerAction)){
            xrulesExecuteResult = xrulesVerificationDataM.getPhoneVerResult();           
            getRequest().getSession().removeAttribute("phoneVerItem");
            this.nextActivityType = FrontController.PAGE;  
        }

        //String txtResultName=getRequest().getParameter("txtResultName");
        //String txtButtonName=getRequest().getParameter("txtButtonName");
        //getRequest().getSession().setAttribute("txtResultName",txtResultName);
        //getRequest().getSession().setAttribute("txtButtonName",txtButtonName);
        getRequest().getSession().setAttribute("execResult",
                xrulesExecuteResult);
        //getRequest().getSession().setAttribute("xrulseExecuteService",strXrulesService);
        //String
        // xrulesNCBColor=personalInfoDataM.getXrulesVerification().getNCBColor();
        log.debug("Execute XruleVerification  Xrules Reuslt ="
                + xrulesExecuteResult);
        //log.debug("Execute XruleVerification NCB Color "+ xrulesNCBColor);
        //getRequest().getSession().setAttribute("xrulesNCBColor",xrulesNCBColor);
        getRequest().getSession().setAttribute("openPopup", "N");
        log.debug("SaveXrulesPhoneVerPopupWebAction -->End preModelRequest");
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return nextActivityType;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
