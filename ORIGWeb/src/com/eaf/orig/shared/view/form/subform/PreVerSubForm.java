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
import com.eaf.orig.shared.model.PreScoreDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author Sankom
 * 
 * Type: OccupationSubForm
 */
public class PreVerSubForm extends ORIGSubForm {
    Logger logger = Logger.getLogger(PreVerSubForm.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.view.form.NaosSubForm#setProperties(javax.servlet.http.HttpServletRequest,
     *      java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ORIGUtility utility = new ORIGUtility();
        logger.debug("PreVerSubForm");
        String occupation = request.getParameter("pre_score_occupation");
        String bus_type = request.getParameter("pre_score_bus_type");
        String bus_size = request.getParameter("pre_score_bus_size");
        String company = request.getParameter("pre_score_company");
        String department = request.getParameter("pre_score_department");
        String position = request.getParameter("pre_score_position");
        String salary = request.getParameter("pre_score_salary");
        String other_income = request.getParameter("pre_score_other_income");
        String position_desc = request.getParameter("pre_score_position_desc");
        String source_other_income = request.getParameter("pre_score_source_other_income");
        String qualification = request.getParameter("pre_score_qualification");
        String year = request.getParameter("pre_score_year");
        String month = request.getParameter("pre_score_month");
        String house_regis_status = request.getParameter("pre_score_house_regis_status");
        String pre_record = request.getParameter("pre_score_pre_record");
        String building_condition = request.getParameter("pre_score_building_condition");
        String location = request.getParameter("pre_score_location");
        String asset_amount = request.getParameter("pre_score_asset_amount");
        String debt_amount = request.getParameter("pre_score_debt_amount");
        String land_ownership = request.getParameter("pre_score_land_ownership");
        String cheque_return = request.getParameter("pre_score_cheque_return");
        String no_of_employee = request.getParameter("pre_score_no_of_employee");
        String time_current_address_year = request.getParameter("pre_score_time_current_address_year");
        String time_current_address_month = request.getParameter("pre_score_time_current_address_month");
        String mkt_code = request.getParameter("pre_score_mkt_code");
        String share_capital = request.getParameter("pre_score_share_capital");
        String over_due60dayIn12Month = request.getParameter("pre_score_overdue_over_60_days");
        String max_over_dueIn6Month = request.getParameter("pre_score_max_time_overdue");
        String no_of_revolving_loan = request.getParameter("pre_score_number_of_revolving_loan");
        String no_of_hire_perchase = request.getParameter("pre_score_number_of_automobile_hire_purchase");
        String npl = request.getParameter("pre_score_NPL_histry_restructure");
        String downPayment = request.getParameter("pre_score_down_payment");
        String termLoan = request.getParameter("pre_score_term_loan");
        String no_of_guarantor = request.getParameter("pre_score_no_of_guarantor");
        String carPrice = request.getParameter("pre_score_car_price");
        //new field
        String installmentAmount = request.getParameter("pre_score_InstallmentAmtVAT");
        String financeAmount = request.getParameter("pre_score_financeAmtVAT");
        String houseId = request.getParameter("pre_score_houseid");
        //      Get Personal Info
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        if (applicationDataM == null) {
            applicationDataM = new ApplicationDataM();
        }
        String personalType = (String) request.getSession().getAttribute("PersonalType");
        String personalSeq = request.getParameter("personalSeq");
        int seq = 0;
        if (!ORIGUtility.isEmptyString(personalSeq)) {
            seq = Integer.parseInt(personalSeq);
        }
        logger.debug("personalType = " + personalType);
        logger.debug("personalSeq = " + seq);
        PersonalInfoDataM personalInfoDataM = null;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        } else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        PreScoreDataM preScoredataM = personalInfoDataM.getPreScoreDataM();
        if (preScoredataM == null) {
            preScoredataM = new PreScoreDataM();
            personalInfoDataM.setPreScoreDataM(preScoredataM);
        }
        if (mkt_code != null) {
            preScoredataM.setMarketingCode(mkt_code);
        }
        if (position != null) {
            preScoredataM.setPosition(position);
        }
        if (bus_size != null) {
            preScoredataM.setBusinessSize(bus_size);
        }
        if (salary != null) {
            preScoredataM.setSalary(utility.stringToBigDecimal(salary));
        }
        if (qualification != null) {
            preScoredataM.setQualification(qualification);
        }
        if (house_regis_status != null) {
            preScoredataM.setHouseRegistStatus(house_regis_status);
        }
        if (land_ownership != null) {
            preScoredataM.setLandOwnerShip(land_ownership);
        }
        if (no_of_employee != null) {
            preScoredataM.setNoOfEmployee(utility.stringToInt(no_of_employee));
        }
        if (occupation != null) {
            preScoredataM.setOccupation(occupation);
        }
        if (bus_type != null) {
            preScoredataM.setBusinessType(bus_type);
        }
        if (share_capital != null) {
            preScoredataM.setShareCapital(utility.stringToBigDecimal(share_capital));
        }
        if (other_income != null) {
            preScoredataM.setOtherIncome(utility.stringToBigDecimal(other_income));
        }
        if (year != null) {
            preScoredataM.setTotalWorkYear(utility.stringToInt(year));
        }
        if (month != null) {
            preScoredataM.setTotalWorkMonth(utility.stringToInt(month));
        }
        if (time_current_address_year != null) {
            preScoredataM.setTimeInCurrentAddressYear(utility.stringToInt(time_current_address_year));
        }
        if (time_current_address_month != null) {
            preScoredataM.setTimeInCurrentAddressMonth(utility.stringToInt(time_current_address_month));
        }
        if (cheque_return != null) {
            preScoredataM.setChequeReturn(utility.stringToBigDecimal(cheque_return));
        }
        if (max_over_dueIn6Month != null) {

            preScoredataM.setMaxOverDue6Month(utility.stringToIntPrescoreData(max_over_dueIn6Month));
        }
        if (over_due60dayIn12Month != null) {

            preScoredataM.setOverDue60dayIn12Month(utility.stringToIntPrescoreData(over_due60dayIn12Month));

        }
        if (no_of_revolving_loan != null) {

            preScoredataM.setNoRevolvingLoan(utility.stringToIntPrescoreData(no_of_revolving_loan));

        }
        if (no_of_hire_perchase != null) {

            preScoredataM.setNoAutoHirePurchase(utility.stringToIntPrescoreData(no_of_hire_perchase));

        }
        if (npl != null) {
            preScoredataM.setNPL(npl);
        }
        if (downPayment != null) {
            preScoredataM.setDownPayment(utility.stringToBigDecimal(downPayment));
        }
        if (termLoan != null) {
            preScoredataM.setTermLoan(utility.stringToInt(termLoan));
        }
        if (no_of_guarantor != null) {
            preScoredataM.setNoOfGuarantor(utility.stringToInt(no_of_guarantor));
        }
        if (carPrice != null) {
            preScoredataM.setCarPrice(utility.stringToBigDecimal(carPrice));
        }
        if (installmentAmount != null) {
            preScoredataM.setInstallmentAmountVAT(utility.stringToBigDecimal(installmentAmount));
        }
        if (financeAmount != null) {
            preScoredataM.setFinanceAmountVAT(utility.stringToBigDecimal(financeAmount));
        }
        if (houseId != null) {
            preScoredataM.setHouseIdno(houseId);
        }
        //request.getSession().setAttribute("MAIN_POPUP_DATA",
        // personalInfoDataM);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.orig.shared.view.form.NaosSubForm#validateForm(javax.servlet.http.HttpServletRequest,
     *      java.lang.Object)
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
