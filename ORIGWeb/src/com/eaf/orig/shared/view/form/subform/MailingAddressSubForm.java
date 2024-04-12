/*
 * Created on Sep 18, 2007
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
package com.eaf.orig.shared.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author weeraya
 *
 * Type: MailingAddressSubForm
 */
public class MailingAddressSubForm extends ORIGSubForm {
    Logger logger = Logger.getLogger(MailingAddressSubForm.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.NaosSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ORIGUtility utility = new ORIGUtility();
        try{
	        String mailing = (String) request.getParameter("mailing_address");
	        String personalType = request.getParameter("appPersonalType");//(String) request.getSession().getAttribute("PersonalType");
	        String personalSeq = request.getParameter("personalSeq");
	        int seq = 0;
	        if(!ORIGUtility.isEmptyString(personalSeq)){
	            seq = Integer.parseInt(personalSeq);
	        }
	        
	        logger.debug("personalType = "+personalType);
	        logger.debug("personalSeq = "+seq);
	        
//	      Get Personal Info
	        ApplicationDataM applicationDataM = formHandler.getAppForm();
	        PersonalInfoDataM personalInfoDataM=null;
	    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");	    		
	    	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");	    		
	    	}else{
	    		personalInfoDataM = utility.getPersonalInfoByType(applicationDataM,OrigConstant.PERSONAL_TYPE_APPLICANT);
	    	}
            if(personalInfoDataM!=null){
	        personalInfoDataM.setMailingAddress(mailing);
            }else{
	    	    logger.error("Persoanl info is null");
	    	}	        
	        request.getSession().setAttribute("MAIN_POPUP_DATA", personalInfoDataM);
	        request.getSession().setAttribute("SUPCARD_POPUP_DATA", personalInfoDataM);
        }catch(Exception e){
            logger.error("Error in MailingAddressSubForm.setProperties() >> ", e);
        }
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.NaosSubForm#validateForm(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public HashMap validateForm(HttpServletRequest request, Object appForm) {
         
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateSubForm(javax.servlet.http.HttpServletRequest)
     */
    public boolean validateSubForm(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return false;
    }

}
