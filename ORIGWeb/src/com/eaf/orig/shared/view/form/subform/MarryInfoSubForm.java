/*
 * Created on Sep 21, 2007
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
 * Type: MarryInfoSubForm
 */
public class MarryInfoSubForm extends ORIGSubForm {
    Logger logger = Logger.getLogger(MarryInfoSubForm.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
        try{
	        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
	        ORIGUtility utility = new ORIGUtility();
	        
	        String m_card_type = request.getParameter("m_card_type");
	        String m_id_no = request.getParameter("m_id_no");
	        String m_title_thai = request.getParameter("m_title_thai");
	        String m_name_thai = request.getParameter("m_name_thai");
	        String m_surname_thai = request.getParameter("m_surname_thai");
	        String m_title_eng = request.getParameter("m_title_eng");
	        String m_name_eng = request.getParameter("m_name_eng");
	        String m_surname_eng = request.getParameter("m_surname_eng");
	        String m_birth_date = request.getParameter("m_birth_date");
	        String m_age = request.getParameter("m_age");
	        String m_gender = request.getParameter("m_gender");
	        String m_income = request.getParameter("m_income");
	        String m_occupation = request.getParameter("m_occupation");
	        String m_company = request.getParameter("m_company");
	        String m_position = request.getParameter("m_position");
	        String m_department = request.getParameter("m_department");
	        String personalType = request.getParameter("appPersonalType");//(String) request.getSession().getAttribute("PersonalType");
	        String personalSeq = request.getParameter("personalSeq");
	        int seq = 0;
	        if(!ORIGUtility.isEmptyString(personalSeq)){
	            seq = Integer.parseInt(personalSeq);
	        }
	        
	        logger.debug("personalType = "+personalType);
	        logger.debug("personalSeq = "+seq);
	        
	        //Get Personal Info
	        ApplicationDataM applicationDataM = formHandler.getAppForm();
	        PersonalInfoDataM personalInfoDataM=null;
	    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");	    		
	    	}else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)){
	    		personalInfoDataM = utility.getPersonalInfoByType(applicationDataM,OrigConstant.PERSONAL_TYPE_APPLICANT);
	    	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");	    		
	    	}
	    	String dateFormat = "dd/mm/yyyy";
	    	if(personalInfoDataM!=null){
	        personalInfoDataM.setMCardType(m_card_type);
	        personalInfoDataM.setMIDNo(m_id_no);
	        personalInfoDataM.setMThaiTitleName(m_title_thai);
	        personalInfoDataM.setMThaiFirstName(m_name_thai);
	        personalInfoDataM.setMThaiLastName(m_surname_thai);
	        personalInfoDataM.setMEngTitleName(m_title_eng);
	        personalInfoDataM.setMEngFirstName(m_name_eng);
	        personalInfoDataM.setMEngLastName(m_surname_eng);
	        personalInfoDataM.setMBirthDate(ORIGUtility.parseThaiToEngDate(m_birth_date));
	        personalInfoDataM.setMAge(utility.stringToInt(m_age));
	        personalInfoDataM.setMGender(m_gender);
	        personalInfoDataM.setMIncome(utility.stringToBigDecimal(m_income));
	        personalInfoDataM.setMOccupation(m_occupation);
	        personalInfoDataM.setMPosition(m_position);
	        personalInfoDataM.setMDepartment(m_department);
	        personalInfoDataM.setMCompany(m_company);
	    	}else{
	    	    logger.error("Persoanl info is null");
	    	}
	        
        }catch(Exception e){
            logger.error("Error in CustomerInfoSubForm", e);
        }

    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateForm(javax.servlet.http.HttpServletRequest, java.lang.Object)
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
