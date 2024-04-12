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

import java.math.BigDecimal;
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
import com.eaf.xrules.shared.model.XRulesLPMDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 * 
 * Type: SaveXrulesLPMItemPopupWebAction
 */
public class SaveXrulesLPMItemPopupWebAction extends WebActionHelper implements
        WebAction {
    private static Logger log = (Logger) Logger
            .getLogger(SaveXrulesLPMItemPopupWebAction.class);

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
        //save Input Box
        log.debug("SaveXrulesLPMItemPopupWebAction-->preModelRequest");
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
        XRulesVerificationResultDataM xRulesVerification = personalInfoDataM
                .getXrulesVerification(); 
        String lpmItemIndex=getRequest().getParameter("lpmItemIndex");
        int indexItem = 0;
        try {
            indexItem=Integer.parseInt(lpmItemIndex);
        } catch (NumberFormatException e) {            
            e.printStackTrace();
        }
        if (xRulesVerification != null) {
            Vector vLPMDataM = xRulesVerification.getVXRulesLMPDataM();
            if (vLPMDataM == null) {
                vLPMDataM = new Vector();
                xRulesVerification.setVXRulesLMPDataM(vLPMDataM);
            }
            String loanType=getRequest().getParameter("lpmItemloanType");
            String creditLimit=getRequest().getParameter("lpmItemCreditLimit");
            String outStaingBalance=getRequest().getParameter("lpmItemOutStandingBalane");
            String status=getRequest().getParameter("lpmItemStatus");
            log.debug("SaveXrulesLPMItemPopupWebAction loanType  "+loanType);
            log.debug("SaveXrulesLPMItemPopupWebAction outStaingBalance "+outStaingBalance);
            log.debug("SaveXrulesLPMItemPopupWebAction creditLimit "+creditLimit);
            log.debug("SaveXrulesLPMItemPopupWebAction status "+status);
            
            BigDecimal bCreditLimit;
            BigDecimal bOutstandingBalance;
           
            try {
                bCreditLimit = utility.stringToBigDecimal(  creditLimit);             
                bOutstandingBalance = utility.stringToBigDecimal( outStaingBalance);
            } catch (RuntimeException e1) {
                bCreditLimit=new BigDecimal(0);
                bOutstandingBalance=new BigDecimal(0);
                log.error("Error ",e1);
            }
            
            XRulesLPMDataM prmXRulesLPMDataM=null;
            if (vLPMDataM.size() < indexItem) {
                // update LPM Input
                 prmXRulesLPMDataM = (XRulesLPMDataM) vLPMDataM
                        .get(indexItem - 1);                
            } else {
                //new Lpm Input
                 prmXRulesLPMDataM = new XRulesLPMDataM();
                 vLPMDataM.add(prmXRulesLPMDataM);
            }
            prmXRulesLPMDataM.setLoanType(loanType);                
            prmXRulesLPMDataM.setCreditLimit(bCreditLimit);
            prmXRulesLPMDataM.setOSBalnace(bOutstandingBalance);
            prmXRulesLPMDataM.setStatus(status);
            prmXRulesLPMDataM.setLpmFlag("");
           
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
