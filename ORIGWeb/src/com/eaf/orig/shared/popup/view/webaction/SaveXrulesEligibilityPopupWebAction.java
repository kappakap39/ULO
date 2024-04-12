/*
 * Created on Mar 17, 2010
 * Created by wichaya
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

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author wichaya
 *
 * Type: SaveXrulesEligibilityWebPopupAction
 */
public class SaveXrulesEligibilityPopupWebAction extends WebActionHelper implements WebAction {
    private static Logger log = (Logger) Logger.getLogger(SaveXrulesEligibilityPopupWebAction.class);
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        log.debug("SaveXrulesEligibilityWebPopupAction-->preModelRequest");
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
        if (applicationDataM == null) {
            applicationDataM = new ApplicationDataM();
        }
        ORIGUtility utility = new ORIGUtility();
        String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
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
        }
        
        XRulesVerificationResultDataM xRulesVerification = personalInfoDataM.getXrulesVerification();
        
        String eligibilityResult = getRequest().getParameter("eligibilityResult");
        log.debug("Execute eligibilityResult Xrules Reuslt ="+eligibilityResult);
        xRulesVerification.setEligibilityResult(eligibilityResult);
        
        getRequest().getSession().setAttribute("execResult",eligibilityResult);
        getRequest().getSession().setAttribute("xrulseExecuteService",String.valueOf(XRulesConstant.ServiceID.ELIGIBILITY));
        getRequest().getSession().setAttribute("openPopup", "N");
        
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
