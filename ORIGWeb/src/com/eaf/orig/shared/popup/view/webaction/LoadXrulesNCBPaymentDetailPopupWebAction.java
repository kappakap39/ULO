/*
 * Created on Nov 8, 2007
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

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.ncb.model.output.TLRespM;
import com.eaf.ncb.service.ejb.NCBServiceManager;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.OrigXRulesUtil;
//import com.eaf.xrules.shared.factory.XRulesEJBFactory;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 *
 * Type: LoadXrulesNCBDetailPopupWebAction
 */
public class LoadXrulesNCBPaymentDetailPopupWebAction extends WebActionHelper
        implements WebAction {
    private static Logger log= (Logger) Logger
    .getLogger(LoadXrulesNCBPaymentDetailPopupWebAction.class);
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
        //Load Data Form NCB
        log.debug("LoadXrulesNCBPaymentDetailPopupWebAction-->preModelRequest");
        log.debug("LoadXrulesNCBPaymentDetailPopupWebAction Webation");
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		UserDetailM ORIGUser = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
    	if(applicationDataM == null){
    		applicationDataM = new ApplicationDataM();
    	}
    	ORIGUtility utility = new ORIGUtility();
    	String personalType =  getRequest().getParameter("appPersonalType");//(String) getRequest().getSession().getAttribute("PersonalType");
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
    	if(xRulesVerification==null){xRulesVerification=new XRulesVerificationResultDataM(); }
    	String accountType=getRequest().getParameter("NCBaccountType");
    	log.debug("accountType"+accountType);
    	String segmentValue=getRequest().getParameter("segmentValue");
    	log.debug("SegmentValue "+segmentValue);
    	String tracingCode=xRulesVerification.getNCBTrackingCode();
    	log.debug("tracingCode "+tracingCode);
    	String strGroupSeq=getRequest().getParameter("groupSeq");
    	log.debug("strGroupSeq "+strGroupSeq);
    	int groupSeq=utility.stringToInt(strGroupSeq);
    	Vector vAccountPaymentHistoryDetail=null;
    	try { 
    	    if( (tracingCode!=null && !"".equals(tracingCode))&& (segmentValue!=null &&!"".equals(segmentValue)) )
    	    {  
//    	        NCBServiceManager ncbService=XRulesEJBFactory.getNCBServiceManager();    	         
//                vAccountPaymentHistoryDetail=ncbService.getNCBAccountPaymentDetail(tracingCode,segmentValue,groupSeq);
    	    }
            //Set Data to Session
        } catch ( Exception e) {          
            e.printStackTrace();
            log.fatal(e.getLocalizedMessage());
        } 
        Vector vAccountDetail=(Vector)getRequest().getSession().getAttribute("NCBAccountDetail");
        OrigXRulesUtil  origXRulesUtil=OrigXRulesUtil.getInstance();
        TLRespM accountM=origXRulesUtil.getNCBAccount(vAccountDetail,accountType,segmentValue,groupSeq);
        getRequest().getSession().setAttribute("NCBAccountPaymentDetail",vAccountPaymentHistoryDetail);
        getRequest().getSession().setAttribute("NCBAccount",accountM);        
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
