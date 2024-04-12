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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.ProposalDataM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author weeraya
 *
 * Type: CustomerInfoSubForm
 */
public class CustomerInfoSubForm extends ORIGSubForm {
    Logger logger = Logger.getLogger(CustomerInfoSubForm.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.NaosSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
        try{
	        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
	        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	        ORIGUtility utility = new ORIGUtility();
	        
	        String title_thai = request.getParameter("title_thai");
	        String name_thai = request.getParameter("name_thai");
	        String surname_thai = request.getParameter("surname_thai");
	        String title_eng = request.getParameter("title_eng");
	        String name_eng = request.getParameter("name_eng");
	        String surname_eng = request.getParameter("surname_eng");
	        String birth_date = request.getParameter("birth_date");
	        String age = request.getParameter("age");
	        String race = request.getParameter("race");
	        String gender = request.getParameter("gender");
	        String marital_status = request.getParameter("marital_status");
	        //String customer_type = request.getParameter("customer_type");
	        String card_identity = request.getParameter("card_identity");
	        String nationality = request.getParameter("nationality");
	        String issue_date = request.getParameter("issue_date");
	        String expiry_date = request.getParameter("expiry_date");
	        String tax_id = request.getParameter("tax_id");
	        String client_group = request.getParameter("client_group");
	        String card_id = request.getParameter("card_id");
	        String area_code = request.getParameter("area_code");
	        String issue_by = request.getParameter("issue_by");
	        String source_of_customer = request.getParameter("source_of_customer");
	        String k_consent_flag = request.getParameter("k_consent_flag");
	        String k_consent_date = request.getParameter("k_consent_date");
	        String personalType = request.getParameter("appPersonalType");//(String) request.getSession().getAttribute("PersonalType");
	        String personalSeq = request.getParameter("personalSeq");
	        String custSegment = request.getParameter("customer_segment");
	       
	        //Trim space
	        name_thai=ORIGDisplayFormatUtil.lrtrim(name_thai);
	        surname_thai=ORIGDisplayFormatUtil.lrtrim(surname_thai);
	        name_eng=ORIGDisplayFormatUtil.lrtrim(name_eng);
	        surname_eng=ORIGDisplayFormatUtil.lrtrim(surname_eng);	        	        	        
	        int seq = 0;
	        if(!ORIGUtility.isEmptyString(personalSeq)){
	            seq = Integer.parseInt(personalSeq);
	        }
	        
	        logger.debug("k_consent_flag = "+k_consent_flag);
	        logger.debug("personalType = "+personalType);
	        logger.debug("personalSeq = "+seq);
	       
	        logger.debug("");
	        //Get Personal Info
	        ApplicationDataM applicationDataM = formHandler.getAppForm();
	        PersonalInfoDataM personalInfoDataM=null;
	    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");	    		
	    	}else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)){
	    		personalInfoDataM = utility.getPersonalInfoByType(applicationDataM,OrigConstant.PERSONAL_TYPE_APPLICANT);
	    	}else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	    	}
	    	
	        //Set data
	    	if(personalInfoDataM!=null ){
	    	String dateFormat = "dd/mm/yyyy";
	        personalInfoDataM.setThaiTitleName(title_thai);
	        personalInfoDataM.setThaiFirstName(name_thai);
	        personalInfoDataM.setThaiLastName(surname_thai);
	        personalInfoDataM.setEngTitleName(title_eng);
	        personalInfoDataM.setEngFirstName(name_eng);
	        personalInfoDataM.setEngLastName(surname_eng);
	        personalInfoDataM.setBirthDate(ORIGUtility.parseThaiToEngDate(birth_date));
	        personalInfoDataM.setAge(utility.stringToInt(age));
	        personalInfoDataM.setRace(race);
	        personalInfoDataM.setGender(gender);
	        personalInfoDataM.setMaritalStatus(marital_status);
	        //personalInfoDataM.setCustomerType(customer_type);
	        personalInfoDataM.setCardIdentity(card_identity);
	        personalInfoDataM.setNationality(nationality);
	        personalInfoDataM.setIssueDate(ORIGUtility.parseThaiToEngDate(issue_date));
	        personalInfoDataM.setExpiryDate(ORIGUtility.parseThaiToEngDate(expiry_date));
	        personalInfoDataM.setTaxID(tax_id);
	        personalInfoDataM.setClientGroup(client_group);
	        personalInfoDataM.setCardID(card_id);
	        personalInfoDataM.setIssueBy(issue_by);
	        personalInfoDataM.setCustomerSegment(custSegment);
	        if((!(OrigConstant.ROLE_UW.equals(userM.getRoles().elementAt(0)) || (OrigConstant.ROLE_DE.equals(userM.getRoles().elementAt(0)) && !ORIGUtility.isEmptyString(applicationDataM.getCmrFirstId())))) || personalInfoDataM.getKConsentDate() == null){
		        personalInfoDataM.setKConsentFlag(k_consent_flag);
		        personalInfoDataM.setKConsentDate(ORIGUtility.parseThaiToEngDate(k_consent_date));
	        }
	        if (!OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
	        applicationDataM.setAreaCode(area_code);
	        applicationDataM.setSourceOfCustomer(source_of_customer);
	        }
	        //set proposalM
	        ProposalDataM proposalM = applicationDataM.getProposalDataM();
	        if(proposalM!=null){
		        proposalM.setClientGroup(client_group);
	        }
	     
	        //if(OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag()) ){
	        //    
	       // }()
	        logger.debug("Corborrower Flag"+personalInfoDataM.getCoborrowerFlag());
	        logger.debug("debtIncludeFlag  "+personalInfoDataM.getDebtIncludeFlag());
	        if(ORIGUtility.isEmptyString(personalInfoDataM.getCreateBy())){
	        	personalInfoDataM.setCreateBy(userM.getUserName());
	        }else{
	        	personalInfoDataM.setUpdateBy(userM.getUserName());
	        }
	    	}else{
	    	    logger.error("Personal Type is null");    
	    	}

	        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
	        	request.getSession().setAttribute("MAIN_POPUP_DATA", personalInfoDataM);
	        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
	        	request.getSession().setAttribute("SUPCARD_POPUP_DATA", personalInfoDataM);
	        }
        }catch(Exception e){
            logger.error("Error in CustomerInfoSubForm", e);
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
        boolean result = false;
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ApplicationDataM appForm = formHandler.getAppForm();
        if (appForm == null) {
            appForm = new ApplicationDataM();
        }
        ORIGUtility utility = new ORIGUtility();
        Vector vErrors = formHandler.getFormErrors();
        Vector vErrorFields = formHandler.getErrorFields();
        Vector vNotErrorFields = formHandler.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;
        
        if (!ORIGUtility.isEmptyString(request.getParameter("m_birth_date"))) {
            int age = utility.stringToInt(request.getParameter("m_age"));
            String nullDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(appForm.getNullDate()));
            if ((age <= 0 || age > 150) && !(nullDate.equals(request.getParameter("m_birth_date")))) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
                vErrors.add(errorMsg + "[Marriage]");
                vErrorFields.add("m_birth_date");
                result = true;
            } else {
                vNotErrorFields.add("m_birth_date");
            }
        }
        
        if (!ORIGUtility.isEmptyString(request.getParameter("birth_date"))) {
            int age = utility.stringToInt(request.getParameter("age"));
            PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(appForm,OrigConstant.PERSONAL_TYPE_APPLICANT);

	    	if(personalInfoDataM == null){
	    		personalInfoDataM = new PersonalInfoDataM();		
	    	}
	    	
	    	String customer_type=personalInfoDataM.getCustomerType();
            if (!OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(customer_type)) {
                if (age <= 0 || age > 150) {
                    errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
                    vErrors.add(errorMsg);
                    vErrorFields.add("birth_date");
                    result = true;
                } else {
                    vNotErrorFields.add("birth_date");
                }
            }
            //===============================
            int workingYear = utility.stringToInt(request.getParameter("year"));
            int workingMonth = utility.stringToInt(request.getParameter("month"));
            if (workingMonth > 0) {
                workingYear = workingYear + 1;
            }
            if (workingYear > age) {
                errorMsg = ErrorUtil.getShortErrorMessage(request, "WORKING_YEAR_AGE");
                vErrors.add(errorMsg);
                result = true;
            }
            //================================
            
            boolean addressValid = true;
            String strBirthDate = request.getParameter(request.getParameter("birth_date"));
            Date birthDate = ORIGUtility.parseThaiToEngDate(strBirthDate);
            if (birthDate != null) {
	            ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	            Calendar clNow = Calendar.getInstance();
	            Calendar clBd = Calendar.getInstance();
	            clBd.setTime(birthDate);
	            age = clNow.get(Calendar.YEAR) - clBd.get(Calendar.YEAR);
	            if (clNow.get(Calendar.MONTH) < clBd.get(Calendar.MONTH)
	                    || (clNow.get(Calendar.MONTH) == clBd.get(Calendar.MONTH) && clNow.get(Calendar.DAY_OF_MONTH) < clBd.get(Calendar.DAY_OF_MONTH))) {
	                age = age - 1;
	            }
	            Vector vAddress = personalInfoDataM.getAddressVect();
	            if (vAddress != null) {
	                for (int i = 0; i < vAddress.size(); i++) {
	                    AddressDataM addressDataM = (AddressDataM) vAddress.get(i);
	                    BigDecimal resideYear = addressDataM.getResideYear();
	                    if (resideYear != null) {
	                        int year = resideYear.intValue();
	                        int month = resideYear.movePointLeft(2).intValue() % 100;
	                        if (month > 0) {
	                            year++;
	                        }
	                        if (year != 0 && year > age) {
	                            addressValid = false;
	                            vErrors.add("Age must not less than reside year/month in Address Type "
	                                    + cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addressDataM.getAddressType()));
	                        }
	                    }
	                }
	            }
            }
        }
        
        logger.debug("request.getAttribute(k_consent_flag) = " + request.getParameter("k_consent_flag"));
        if (("Y").equals(request.getParameter("k_consent_flag"))) {
            if (ORIGUtility.isEmptyString((String) request.getParameter("k_consent_date"))) {
                errorMsg = "Application consent date is required.";
                vErrors.add(errorMsg);
                vErrorFields.add("k_consent_date");
                result = true;
            } else {
                vNotErrorFields.add("k_consent_date");
            }
        }
        
        if(!"GUARANTOR_FORM".equals(request.getParameter("FORM_ID"))){//Check if Guarantor form then don't check and area code
	        if (ORIGUtility.isZero(request.getParameter("area_code"))) {
	            errorMsg = "Area Code is required.";
	            vErrors.add(errorMsg);
	            vErrorFields.add("area_code");
	            result = true;
	        } else {
	            vNotErrorFields.add("area_code");
	        }
        }
        
        return result;
    }

}
