/*
 * Created on Sep 25, 2007
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
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.BankDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author weeraya
 *
 * Type: LoadFinancePopupWebaction
 */
public class LoadFinancePopupWebAction extends WebActionHelper implements
        WebAction {
    Logger logger = Logger.getLogger(LoadFinancePopupWebAction.class);
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
        String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
        String personalTypeParam=getRequest().getParameter("appPersonalType");                
        if(personalTypeParam!=null&&!"".equals(personalTypeParam)&&!personalTypeParam.equals(personalType)){
            personalType=personalTypeParam;
            getRequest().getSession().setAttribute("PersonalType",personalTypeParam);
        }
        logger.debug("personalTypeParam="+personalTypeParam);
        logger.debug("PersonalType="+personalType);
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA");
    	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
    	}else{
    		personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
    	}
        int seq = 0;
        if(seqStr != null && !("").equals(seqStr)){
            seq = Integer.parseInt(seqStr);
        }
        
        Vector financeVect = personalInfoDataM.getFinanceVect();
        
        if(financeVect == null){
            financeVect = new Vector();
            personalInfoDataM.setFinanceVect(financeVect);
        }
        
        BankDataM bankDataM;
        if(seq == 0){// Add new finance data
            bankDataM = new BankDataM();
            bankDataM.setSeq(0);
        }else{//Edit finance data
            bankDataM = utility.getFinanceBySeq(financeVect, seq);
        }
        getRequest().getSession().setAttribute("seq", seqStr);
        getRequest().getSession().setAttribute("POPUP_DATA", bankDataM);
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
