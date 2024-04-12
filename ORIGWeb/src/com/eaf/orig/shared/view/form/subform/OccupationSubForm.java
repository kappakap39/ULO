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
 * Type: OccupationSubForm
 */
public class OccupationSubForm extends ORIGSubForm {
    Logger logger = Logger.getLogger(OccupationSubForm.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.NaosSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ORIGUtility utility = new ORIGUtility();
        
        String occupation = request.getParameter("occupation");
        String bus_type = request.getParameter("bus_type");
        String bus_size = request.getParameter("bus_size");
        String company = request.getParameter("company");
        String department = request.getParameter("department");
        String position = request.getParameter("position");
        String salary = request.getParameter("salary");
        String other_income = request.getParameter("other_income");
        String position_desc = request.getParameter("position_desc");
        String source_other_income = request.getParameter("source_other_income");
        String qualification = request.getParameter("qualification");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String house_regis_status = request.getParameter("house_regis_status");
        String pre_record = request.getParameter("pre_record");
        String building_condition = request.getParameter("building_condition");
        String location = request.getParameter("location");
        String asset_amount = request.getParameter("asset_amount");
        String debt_amount = request.getParameter("debt_amount");
        String land_ownership = request.getParameter("land_ownership");
        String cheque_return = request.getParameter("cheque_return");
        String no_of_employee = request.getParameter("no_of_employee");
       // String time_year = request.getParameter("time_year");
        //String time_month = request.getParameter("time_month");
       // logger.debug("time_year = "+time_year);
       // logger.debug("time_month = "+time_month);
        //if(time_month.length() == 1){
        //	time_month = "0"+time_month;
       // }
        //String time_current_address = time_year+"."+time_month;
        String personalType =request.getParameter("appPersonalType");// (String) request.getSession().getAttribute("PersonalType");
        String personalSeq = request.getParameter("personalSeq");
        int seq = 0;
        if(!ORIGUtility.isEmptyString(personalSeq)){
            seq = Integer.parseInt(personalSeq);
        }
        logger.debug("personalType = "+personalType);
        
//      Get Personal Info
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        PersonalInfoDataM personalInfoDataM=null;
    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");	    		
    	}else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
        }else{
    		personalInfoDataM = utility.getPersonalInfoByType(applicationDataM,OrigConstant.PERSONAL_TYPE_APPLICANT);
    	}
        if(personalInfoDataM!=null){
        personalInfoDataM.setOccupation(occupation);
        personalInfoDataM.setBusinessType(bus_type);
        personalInfoDataM.setBusinessSize(bus_size);
        personalInfoDataM.setCompanyName(company);
        personalInfoDataM.setDepartment(department);
        personalInfoDataM.setPosition(position);
        personalInfoDataM.setSalary(utility.stringToBigDecimal(salary));
        personalInfoDataM.setOtherIncome(utility.stringToBigDecimal(other_income));
        personalInfoDataM.setPositionDesc(position_desc);
        personalInfoDataM.setSourceOfOtherIncome(source_other_income);
        personalInfoDataM.setQualification(qualification);
        personalInfoDataM.setYearOfWork(utility.stringToInt(year));
        personalInfoDataM.setMonthOfWork(utility.stringToInt(month));
        personalInfoDataM.setHouseRegisStatus(house_regis_status);
        personalInfoDataM.setPreviousRecord(pre_record);
        personalInfoDataM.setBulidingCondition(building_condition);
        personalInfoDataM.setLocation(location);
        personalInfoDataM.setAssetAmount(utility.stringToBigDecimal(asset_amount));
        personalInfoDataM.setDebitAmount(utility.stringToBigDecimal(debt_amount));
        personalInfoDataM.setLandOwnerShip(land_ownership);
        personalInfoDataM.setChequeReturn(utility.stringToInt(cheque_return));
        personalInfoDataM.setNoOfEmployee(utility.stringToInt(no_of_employee));
        }else{
    	    logger.error("Persoanl info is null");
    	}
        //personalInfoDataM.setTimeInCurrentAddress(utility.stringToDouble(time_current_address));
        
        //request.getSession().setAttribute("MAIN_POPUP_DATA", personalInfoDataM);
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
