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
import com.eaf.xrules.shared.model.XRulesBlacklistVehicleDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 * 
 * Type: LoadXrulesBlacklistCustomerPopupWebAction
 */
public class SaveXrulesBlacklistVehiclePopupWebAction extends WebActionHelper
        implements WebAction {
    private static Logger log = (Logger) Logger
            .getLogger(SaveXrulesBlacklistVehiclePopupWebAction.class);

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
        log.debug("SaveXrulesBlacklistVehiclePopupWebAction-->preModelRequest");
        log.debug("Execute SaveXrulesVehicleCustomerPopupWebAction Webation");
        String blacklistFinalResult = getRequest().getParameter(
                "blacklistVehicleFinalResult");
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession()
                .getAttribute("ORIGForm");
        UserDetailM ORIGUser = (UserDetailM) getRequest().getSession()
                .getAttribute("ORIGUser");
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
           // personalInfoDataM.setPersonalType(personalType);
           // applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
        }
        XRulesVerificationResultDataM xRulesVerificationDataM = personalInfoDataM
                .getXrulesVerification();
        xRulesVerificationDataM.setBLVehicleResult(blacklistFinalResult);
        String xrulesExecuteResult = blacklistFinalResult;
        String strXrulesService = String
                .valueOf(XRulesConstant.ServiceID.BLACKLIST_VEHICLE);
        xRulesVerificationDataM.setBlacklistVehicleUpdateBy(ORIGUser.getUserName());
        xRulesVerificationDataM.setBlacklistVehcieUpdateDate(new Timestamp(new Date().getTime()) );
        String updateRole="";
        if(ORIGUser!=null &&ORIGUser.getRoles()!=null&& ORIGUser.getRoles().size()>0){
             updateRole=(String)ORIGUser.getRoles().get(0); 
        }
        xRulesVerificationDataM.setBlacklistVehicleUpdateRole(updateRole);     
        Vector vBlacklistVehicle = xRulesVerificationDataM
                .getVXRulesBlacklistVehicleDataM();
        if (vBlacklistVehicle != null) {
            for (int i = 0; i < vBlacklistVehicle.size(); i++) {
                XRulesBlacklistVehicleDataM prmXRulesBlacklistingVehicleDataM = (XRulesBlacklistVehicleDataM) vBlacklistVehicle
                        .get(i);
                String blFlag = getRequest().getParameter("blVehicle_" + i);
                if (blFlag != null) {
                    prmXRulesBlacklistingVehicleDataM.setBLFlag(blFlag);
                }
                log
                        .debug("SaveXrulesBlacklistVechiclePopupWebAction--> CheckBox blCustomer_"
                                + i
                                + "  Flag ="
                                + prmXRulesBlacklistingVehicleDataM.getBLFlag());
            }
        }

        //String txtResultName=getRequest().getParameter("txtResultName");
        //String txtButtonName=getRequest().getParameter("txtButtonName");
        // getRequest().getSession().setAttribute("txtResultName",txtResultName);
        //getRequest().getSession().setAttribute("txtButtonName",txtButtonName);
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
