/*
 * Created on Dec 3, 2007
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

import java.util.HashMap;
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
import com.eaf.xrules.shared.model.XRulesDebtBdDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;
import java.math.BigDecimal;

/**
 * @author Sankom
 *
 * Type: CalculateXrulesDebtBurdenPopupWebAction
 */
public class CalculateXrulesDebtBurdenPopupWebAction extends WebActionHelper
        implements WebAction {
    private static Logger log= (Logger) Logger
    .getLogger(CalculateXrulesDebtBurdenPopupWebAction.class);
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
        BigDecimal debtCalulate=null;
        //get data from form
        log.debug("CalculateXrulesDebtBurdenPopupWebAction-->preModelRequest");        
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		UserDetailM ORIGUser = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
    	if(applicationDataM == null){
    		applicationDataM = new ApplicationDataM();
    	}
    	ORIGUtility utility = new ORIGUtility();
    	String personalType = getRequest().getParameter("appPersonalType");//(String) getRequest().getSession().getAttribute("PersonalType");
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
        //check check box
        //calculate data
        //save data to session
    	XRulesDebtBdDataM  mainAppXruesDebtDataM=xRulesVerification.getXRulesDebtBdDataM();
        HashMap hGuarantorCheckBox=new HashMap();        
        //map checkbox name and flag
        BigDecimal totalIncome=new BigDecimal( mainAppXruesDebtDataM.getIncome().unscaledValue(),mainAppXruesDebtDataM.getIncome().scale());
        BigDecimal totalBurden=new BigDecimal(mainAppXruesDebtDataM.getBurden().unscaledValue(),mainAppXruesDebtDataM.getIncome().scale());
        Vector vPersoanalInfoDataM=applicationDataM.getPersonalInfoVect();
        for(int i=0;i<vPersoanalInfoDataM.size();i++){
            PersonalInfoDataM guarantorPersonalInfo=(PersonalInfoDataM)vPersoanalInfoDataM.get(i);
            if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equalsIgnoreCase(guarantorPersonalInfo.getPersonalType())){
                String checkBoxGaruntor=getRequest().getParameter("chkGuarantorUseFlag_"+i);
                log.debug(" checkebox  chkGuarantorUseFlag_"+i+"  ="+checkBoxGaruntor);                
                if(OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase(checkBoxGaruntor)){
                    
                   XRulesVerificationResultDataM guarantorVerrifiation=guarantorPersonalInfo.getXrulesVerification();                   
                    if(guarantorVerrifiation!=null){
                        XRulesDebtBdDataM  guarantorDebtBdDataM=guarantorVerrifiation.getXRulesDebtBdDataM();
                        if(guarantorDebtBdDataM!=null){
                        totalIncome=totalIncome.add(guarantorDebtBdDataM.getIncome());
                        totalBurden=totalBurden.add(guarantorDebtBdDataM.getBurden());
                        log.debug("total Income-->"+totalIncome);
                        log.debug("total Burden-->"+totalBurden);
                        }
                        //set data to hash map
                       hGuarantorCheckBox.put("chkGuarantorUseFlag_"+i,checkBoxGaruntor);                          
                    }
                }
            }                    
        }
        log.debug("final total Income-->"+totalIncome);
        log.debug("final total Burden-->"+totalBurden);         
        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType()
                )) {
            log.debug("Case Individual totalIncome.intValue()"
                    + totalIncome.intValue());
            if (totalIncome.intValue() > 0) { //check div by zero
                debtCalulate = (totalBurden.divide(totalIncome, 2,
                        BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(
                        100));
            }
        } else if (OrigConstant.CUSTOMER_TYPE_CORPORATE
                .equalsIgnoreCase(personalInfoDataM.getCustomerType())
                || OrigConstant.CUSTOMER_TYPE_FOREIGNER
                        .equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
            log.debug("Case Individual totalIncome.intValue()"
                    + totalIncome.intValue());
           
            if (totalBurden.intValue() > 0) {
                debtCalulate = (totalIncome.divide(totalBurden, 2,
                        BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(
                        100));
            }
        }
        log.debug("calculateXrulesDebtBdDataM debtBurdenScore "
                + debtCalulate);        
        getRequest().getSession().setAttribute("debtCalulateScore",debtCalulate);
        getRequest().getSession().setAttribute("debtTotalIncome",totalIncome);
        getRequest().getSession().setAttribute("debtCalulateItem",hGuarantorCheckBox);
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {        //  
        return FrontController.PAGE;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
