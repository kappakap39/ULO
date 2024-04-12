/*
 * Created on Oct 30, 2007
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
package com.eaf.orig.shared.popup.view.webaction;

import java.sql.Timestamp;
import java.util.Date;
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
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesBlacklistDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 * 
 * Type: LoadXrulesBlacklistCustomerPopupWebAction
 */
public class SaveXrulesBlacklistCustomerPopupWebAction extends WebActionHelper
        implements WebAction {
    private static Logger log = (Logger) Logger
            .getLogger(SaveXrulesBlacklistCustomerPopupWebAction.class);

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
        //		get data
        //set to model
        log
                .debug("SaveXrulesBlacklistCustomerPopupWebAction-->preModelRequest");
        log.debug("Execute SaveXrulesBlacklistCustomerPopupWebAction Webation");
        String blacklistFinalResult = getRequest().getParameter(
                "blacklistFinalResult");
        UserDetailM ORIGUser = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession()
                .getAttribute("ORIGForm");
         
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
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
            //personalInfoDataM.setPersonalType(personalType);
           // applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
        }
        XRulesVerificationResultDataM xRulesVerificationDataM = personalInfoDataM
                .getXrulesVerification();
        xRulesVerificationDataM.setBLResult(blacklistFinalResult);
        xRulesVerificationDataM.setBlacklistUpdateBy(ORIGUser.getUserName());
        xRulesVerificationDataM.setBlacklistUpdateDate(new Timestamp(new Date().getTime()) );
        String updateRole="";
        if(ORIGUser!=null &&ORIGUser.getRoles()!=null&& ORIGUser.getRoles().size()>0){
             updateRole=(String)ORIGUser.getRoles().get(0); 
        }
        xRulesVerificationDataM.setBlacklistCustomerUpdateRole(updateRole);
        String xrulesExecuteResult = blacklistFinalResult;
        String strXrulesService = String
                .valueOf(XRulesConstant.ServiceID.BLACKLIST);
        //String txtResultName=getRequest().getParameter("txtResultName");
        //String txtButtonName=getRequest().getParameter("txtButtonName");
        //getRequest().getSession().setAttribute("txtResultName",txtResultName);
        //getRequest().getSession().setAttribute("txtButtonName",txtButtonName);
        Vector vBlacklistCustomer = xRulesVerificationDataM
                .getVXRulesBlacklistDataM();
        if (vBlacklistCustomer != null) {
            for (int i = 0; i < vBlacklistCustomer.size(); i++) {
                XRulesBlacklistDataM  prmXRulesBlacklistingCustomerDataM=(XRulesBlacklistDataM)vBlacklistCustomer.get(i);
                String blFlag=getRequest().getParameter("blCustomer_"+i);
                if(blFlag!=null){
                    prmXRulesBlacklistingCustomerDataM.setBLFlag(blFlag);
                }
                log
                        .debug("SaveXrulesBlacklistCustomerPopupWebAction--> CheckBox blCustomer_"
                                + i
                                + "  Flag ="
                                + prmXRulesBlacklistingCustomerDataM
                                        .getBLFlag());
            }
        }
        getRequest().getSession().setAttribute("execResult",
                xrulesExecuteResult);
        getRequest().getSession().setAttribute("xrulseExecuteService",
                strXrulesService);
        getRequest().getSession().setAttribute("openPopup", "N");
        log
                .debug("SaveXrulesBlacklistCustomerPopupWebAction -->xrulesExecuteResult "
                        + xrulesExecuteResult);
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
