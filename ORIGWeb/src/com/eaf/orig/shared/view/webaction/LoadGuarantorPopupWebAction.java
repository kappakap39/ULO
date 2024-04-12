/*
 * Created on Oct 1, 2007
 * Created by weeraya
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

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author weeraya
 *
 * Type: LoadGuarantorPopupWebAction
 */
public class LoadGuarantorPopupWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(LoadGuarantorPopupWebAction.class); 
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
        logger.debug("+++++LoadApplicationFormWebAction+++++");
		UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null==userM)	userM=new UserDetailM();		
						
		ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		ORIGFormHandler popupForm = (ORIGFormHandler) ORIGForm.getPopupForm();
		if(popupForm == null){
		    popupForm = new ORIGFormHandler();
		    ORIGForm.setPopupForm(popupForm);
		}
		String customerType = (String) getRequest().getParameter("type");
		String coborrwerFlag = (String) getRequest().getParameter("coborrowerFlag");
		Vector userRoles = userM.getRoles();
		String formID = "GUARANTOR_FORM";
		String currentTab = "MAIN_TAB";
		//****************************************
		popupForm.getSubForms().clear();
		popupForm.setIsLoadedSubForms(false);
		//****************************************
		popupForm.loadSubForms(userRoles, formID);
		popupForm.setCurrentTab(currentTab);
		popupForm.setFormID(formID);
		logger.debug("customerType = "+customerType);
		popupForm.setSubForms(ORIGUtility.filterSubformByMainCusType(popupForm.getSubForms(), customerType));
		//*****************************************
		
		//Get Personal
		ORIGUtility utility = new ORIGUtility();
		PersonalInfoDataM personalInfoDataM;
		String seqStr = (String) getRequest().getParameter("seq");
        int seq = 0;
        if(seqStr != null && !("").equals(seqStr)){
            seq = Integer.parseInt(seqStr);
        }
        logger.debug("seq "+seq);
        logger.debug("coborrowerFlag="+coborrwerFlag);
       // if( OrigConstant.COBORROWER_FLAG_ACTIVE.equals(coborrwerFlag)){
		    //get Persoanl applicaint
		//    PersonalInfoDataM  applicaintPersonalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
		//    customerType=applicaintPersonalInfoDataM.getCustomerType();    
		//} 
		if(seq == 0){
		    personalInfoDataM = new PersonalInfoDataM();
		    personalInfoDataM.setCustomerType(customerType);
		    personalInfoDataM.setPersonalType(OrigConstant.PERSONAL_TYPE_GUARANTOR);
		    personalInfoDataM.setCmpCode(utility.getGeneralParamByCode(OrigConstant.ORIG_CMPCDE));
		    personalInfoDataM.setPersonalSeq(seq);
		    personalInfoDataM.setCoborrowerFlag(coborrwerFlag);
		    if("Y".equals(ORIGForm.getAppForm().getDrawDownFlag())){
		    	PersonalInfoDataM personalInfoDataM2 = (PersonalInfoDataM)getRequest().getSession().getAttribute("CUSTOMER_DRAW_DOWN");
		    	logger.debug("Load data first when application is drawdown .... personal drowdown = "+personalInfoDataM2);
		    	if(personalInfoDataM2 != null){
		    		personalInfoDataM.setXrulesVerification((XRulesVerificationResultDataM)SerializeUtil.clone(personalInfoDataM2.getXrulesVerification()));
		    	}
		    }
		    
		}else{
		    personalInfoDataM = utility.getPersonalInfoByTypeAndSeq(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_GUARANTOR, seq);
		}
		personalInfoDataM.setAddressIndex(personalInfoDataM.getAddressVect().size()+1);
		personalInfoDataM.setFinanceIndex(personalInfoDataM.getFinanceVect().size()+1);
		personalInfoDataM.setChangeNameIndex(personalInfoDataM.getChangeNameVect().size()+1);				     		
		getRequest().getSession().setAttribute("ORIGForm",ORIGForm);
		getRequest().getSession().setAttribute("PersonalType",OrigConstant.PERSONAL_TYPE_GUARANTOR);
		getRequest().getSession().setAttribute("MAIN_POPUP_DATA", personalInfoDataM);
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
         
        return 0;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
