/*
 * Created on Oct 17, 2007
 * Created by Prawit Limwattanachai
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
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
//import com.eaf.orig.shared.model.ChangeNameDataM;
import com.eaf.orig.shared.model.OtherNameDataM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: LoadOtherNamePopupWebAction
 */
public class LoadOtherNamePopupWebAction extends WebActionHelper implements
		WebAction {
	Logger logger = Logger.getLogger(LoadOtherNamePopupWebAction.class);
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
		 
		ORIGUtility utility = new ORIGUtility();
        ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
       
        String seqStr = (String) getRequest().getParameter("seq");
       // String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
        
        ApplicationDataM applicationDataM = formHandler.getAppForm();
    
   /*     PersonalInfoDataM personalInfoDataM;
    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA");
    	}else{
    		personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
    	} */
                
        int seq = 0;
        if(seqStr != null && !("").equals(seqStr)){
            seq = Integer.parseInt(seqStr);
        }
        
        Vector otherNameVect = applicationDataM.getOtherNameDataM(); 
        
        if(otherNameVect == null){
        	otherNameVect = new Vector();
        	applicationDataM.setOtherNameDataM(otherNameVect);
        }
        logger.debug("seq = "+seq);
        OtherNameDataM otherNameDataM;
        if(seq == 0){// Add new finance data
        	otherNameDataM = new OtherNameDataM();
        	otherNameDataM.setSeq(0);
        }else{//Edit finance data
        	otherNameDataM = utility.getOtherNameBySeq(applicationDataM, seq); 
        }
        logger.debug("otherNameDataM = "+otherNameDataM);
        getRequest().getSession().setAttribute("POPUP_DATA",otherNameDataM); 
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
