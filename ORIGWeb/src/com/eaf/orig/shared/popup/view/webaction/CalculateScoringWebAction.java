/*
 * Created on Dec 4, 2007
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
import com.eaf.orig.shared.utility.OrigScoringUtility;
import com.eaf.orig.shared.utility.SerializeUtil;

/**
 * @author Sankom
 *
 * Type: CalculateScoringWebAction
 */
public class CalculateScoringWebAction extends WebActionHelper implements
        WebAction {
    private static Logger log= (Logger) Logger
    .getLogger(CalculateScoringWebAction.class);
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
 
        
        
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        log.debug("CalculateScoringWebAction-->preModelRequest");         
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		UserDetailM ORIGUser = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
    	if(applicationDataM == null){
    		applicationDataM = new ApplicationDataM();
    	}
    	ORIGUtility utility = new ORIGUtility();
    	String personalType =getRequest().getParameter("appPersonalType");// (String) getRequest().getSession().getAttribute("PersonalType");
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
    	getRequest().getSession().setAttribute("scoringType",OrigConstant.Scoring.SCORING_TYPE_APPSCORE);
    	OrigScoringUtility origScoring=new OrigScoringUtility();
    	String scoringResult=origScoring.calcuateApplicationScoreing(applicationDataM,ORIGUser);    
    	applicationDataM.setScorringResult(scoringResult);
    	applicationDataM.setIsReExcuteAppScoreFlag(false);
    	getRequest().getSession().setAttribute("scoringResult",scoringResult);
    	ApplicationDataM applicationDataMClone=(ApplicationDataM)SerializeUtil.clone(applicationDataM);
    	getRequest().getSession(true).setAttribute("applicationClone",applicationDataMClone);    	
        return FrontController.PAGE;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
