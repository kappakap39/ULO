/*
 * Created on Jan 9, 2008
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
package com.eaf.orig.shared.utility;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.PreScoreDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 * 
 * Type: OrigReExcuteAppScoreUtil
 */
public class OrigReExcuteAppScoreUtil implements Serializable {
    private static OrigReExcuteAppScoreUtil me;

    static Logger log = Logger.getLogger(OrigReExcuteAppScoreUtil.class);

    /**
     *  
     */
    private OrigReExcuteAppScoreUtil() {
        super();
        
    }

    public static OrigReExcuteAppScoreUtil getInstance() {
        if (me == null) {
            me = new OrigReExcuteAppScoreUtil();
        }
        return me;
    }

    public boolean isApplicationReExecuteScorring(ApplicationDataM applicationDataM, HttpServletRequest request) {
        boolean result = false;
        ORIGUtility utility = new ORIGUtility();
        log.debug("isApplicationReExecuteScorring Call");
        PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        } //Loan Personal Info

        ORIGApplicationManager applicationManager = ORIGEJBService.getApplicationManager();
        ApplicationDataM oldApplicationDataM = null;
        if (request != null) {
            oldApplicationDataM = (ApplicationDataM) request.getSession(true).getAttribute("applicationClone");

        }
        log.debug("oldApplicationDataM" + oldApplicationDataM);
        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
            result = isApplicationChangeIndividual(oldApplicationDataM, applicationDataM, request);
        } else if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
            result = isApplicationChangeCorporate(oldApplicationDataM, applicationDataM, request);
        } else if (OrigConstant.CUSTOMER_TYPE_FOREIGNER.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
            result = isApplicationChangeForeigner(oldApplicationDataM, applicationDataM, request);
        }
        return result;
    }

    private boolean isApplicationChangeIndividual(ApplicationDataM oldApplicationDataM, ApplicationDataM newApplicationDataM, HttpServletRequest request) {
        log.debug("isApplicationChangeIndividual");
        //map application to charcode;
        ORIGUtility utility = new ORIGUtility();
        // ===============Old Appplication======================
        PersonalInfoDataM oldPersonalInfoDataM;
        oldPersonalInfoDataM = utility.getPersonalInfoByType(oldApplicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        if (oldPersonalInfoDataM == null) {
            oldPersonalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM oldXRulesVerification = oldPersonalInfoDataM.getXrulesVerification();
        if (oldXRulesVerification == null) {
            oldXRulesVerification = new XRulesVerificationResultDataM();
        }
        AddressDataM oldAddrHome = utility.getAddressByType(oldPersonalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);

        Vector oldVLoan = oldApplicationDataM.getLoanVect();
        VehicleDataM oldVehicleDataM = oldApplicationDataM.getVehicleDataM();
        Vector vOldPersonal = oldApplicationDataM.getPersonalInfoVect();
        PreScoreDataM oldPreScoreDataM = oldPersonalInfoDataM.getPreScoreDataM();
        //==========================New Application===========
        PersonalInfoDataM newPersonalInfoDataM;
        newPersonalInfoDataM = utility.getPersonalInfoByType(newApplicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        if (newPersonalInfoDataM == null) {
            newPersonalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM newXRulesVerification = newPersonalInfoDataM.getXrulesVerification();
        if (newXRulesVerification == null) {
            newXRulesVerification = new XRulesVerificationResultDataM();
        }
        AddressDataM newAddrHome = utility.getAddressByType(newPersonalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        Vector newVLoan = newApplicationDataM.getLoanVect();
        VehicleDataM newVehicleDataM = newApplicationDataM.getVehicleDataM();
        Vector vnewPersonal = oldApplicationDataM.getPersonalInfoVect();
        PreScoreDataM newPreScoreDataM = newPersonalInfoDataM.getPreScoreDataM();
        String paramField = "";
        String value = null;
        //========================================
        //XRulesdxRulesVerification.getXrulesDebtBdDataM();
        //map Data
        //
        //age
        paramField = "age";
        value = request.getParameter("birth_date");
        log.debug("oldPersonalInfoDataM.getBirthDate) " + oldPersonalInfoDataM.getBirthDate());
        //20080424 fix compare date
        log.debug("ORIGUtility.parseThaiToEngDate(value) " + ORIGUtility.parseThaiToEngDate(value));        
        Calendar calendarOld = Calendar.getInstance();
        calendarOld.setTime(oldPersonalInfoDataM.getBirthDate());
        Calendar calendarNew = Calendar.getInstance();
        calendarNew.setTime(ORIGUtility.parseThaiToEngDate(value));
        if (!(calendarOld.get(Calendar.YEAR) == (calendarNew.get(Calendar.YEAR)) && calendarOld.get(Calendar.MONTH) == calendarNew.get(Calendar.MONTH) && calendarOld
                .get(Calendar.DAY_OF_MONTH) == calendarNew.get(Calendar.DAY_OF_MONTH))) {
            log.debug("ReExecute appscore Case  BirthDate");
            return true;
        }
        //material status
        paramField = "marital_status";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getMaritalStatus() != null && !oldPersonalInfoDataM.getMaritalStatus().equalsIgnoreCase(value)) {
            log.debug("ReExecute appscore Case Marial Status");
            return true;
        }
        //Education level
        paramField = "qualification";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getQualification() != null && !oldPersonalInfoDataM.getQualification().equalsIgnoreCase(value)) {
            log.debug("ReExecute appscore Case Qulification");
            return true;
        }
        //Sex
        paramField = "gender";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getGender() != null && !oldPersonalInfoDataM.getGender().equalsIgnoreCase(value)) {
            log.debug("ReExecute appscore Case Gender");
            return true;
        }
        //working year
        paramField = "year";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getYearOfWork() != utility.stringToInt(value)) {
            log.debug("ReExecute appscore Case Working Year");
            return true;
        }
        //      working month
        paramField = "month";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getMonthOfWork() != utility.stringToInt(value)) {
            log.debug("ReExecute appscore Case Working Month");
            return true;
        }

        //Occupation
        paramField = "occupation";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getOccupation() != null && !oldPersonalInfoDataM.getOccupation().equalsIgnoreCase(value)) {
            log.debug("ReExecute appscore Case Occupation");
            return true;
        }
        /*
         * paramField = "pre_score_house_regis_status"; value =
         * request.getParameter(paramField); if (oldPreScoreDataM != null ) {
         * //Accomodation Status if (oldPreScoreDataM.getHouseRegistStatus() !=
         * null && !oldPreScoreDataM.getHouseRegistStatus().equalsIgnoreCase(
         * value)) { log.debug("ReExecute appscore
         * pre_score_house_regis_status"); return true; } }
         */
        //Debt Burden
        if (oldXRulesVerification.getDEBT_BDScore() != null && newXRulesVerification.getDEBT_BDScore() != null
                && (oldXRulesVerification.getDEBT_BDScore().compareTo(newXRulesVerification.getDEBT_BDScore()) != 0)) {
            log.debug("ReExecute appscore Case Debt Burden");
            return true;
        }
        /*
         * paramField = "pre_score_house_regis_status"; value =
         * request.getParameter(paramField); if (oldPreScoreDataM != null ) {
         * //Accomodation Status if (oldPreScoreDataM.getHouseRegistStatus() !=
         * null && !oldPreScoreDataM.getHouseRegistStatus().equalsIgnoreCase(
         * value)) { log.debug("ReExecute appscore Case House resit status");
         * return true; } }
         */
        //Time in Current Address        
        //paramField = "time_year";
        //value = request.getParameter(paramField);
        log.debug("oldAddrHome.getResideYear()="+oldAddrHome.getResideYear());
        log.debug("newAddrHome.getResideYear()="+newAddrHome.getResideYear());
        if ( oldAddrHome.getResideYear()!=null&&oldAddrHome.getResideYear().compareTo(newAddrHome.getResideYear() )!=0 ) {
            log.debug("ReExecute appscore Case Reside Year/month ");
            return true;
        }
        
        log.debug("ooldAddrHome.getStatus()="+oldAddrHome.getStatus());
        log.debug("newAddrHome.getStatus()="+newAddrHome.getStatus());
        if ( oldAddrHome.getStatus()!=null&& !oldAddrHome.getStatus().equals(newAddrHome.getStatus())  ) {
            log.debug("ReExecute appscore Case accommodation status");
            return true;
        }
       // paramField = "time_month";
       // value = request.getParameter(paramField);
       // log.debug(" Old month "+Math.round((oldPersonalInfoDataM.getTimeInCurrentAddress()*100)%100));
       // log.debug(" new month "+utility.stringToInt(value));
       // if (Double.compare( Math.round((oldPersonalInfoDataM.getTimeInCurrentAddress()*100) %100), utility.stringToInt(value)) != 0) {
        //    log.debug("ReExecute appscore Case time in current address month");
         //   return true;
       // }
        

        if (oldVLoan != null && oldVLoan.size() > 0 && newVLoan != null && newVLoan.size() > 0) {
            LoanDataM oldLoanDataM = (LoanDataM) oldVLoan.get(0);
            LoanDataM newLoanDataM = (LoanDataM) newVLoan.get(0);
            paramField = "car";
            value = request.getParameter(paramField);
            if (oldVehicleDataM.getCar() != null && !oldVehicleDataM.getCar().equalsIgnoreCase(value)) {
                log.debug("ReExecute appscore Case Car type");
                return true;
            }
            //individual Down Payment

            paramField = "car";
            value = request.getParameter(paramField);
            if (oldVehicleDataM != null && OrigConstant.CAR_TYPE_USE.equalsIgnoreCase(value)) {

                if (oldLoanDataM.getCostOfCarPrice() != null && (oldLoanDataM.getCostOfCarPrice().compareTo(newLoanDataM.getCostOfCarPrice()) != 0)) {
                    log.debug("ReExecute appscore Case Cost of car price");
                    return true;
                }
                if (oldLoanDataM.getCostOfDownPayment() != null && (oldLoanDataM.getCostOfDownPayment().compareTo(newLoanDataM.getCostOfDownPayment()) != 0)) {
                    log.debug("ReExecute appscore Cost of Down payment");
                    return true;
                }
            } else if (oldVehicleDataM != null && OrigConstant.CAR_TYPE_NEW.equalsIgnoreCase(value)) {

                if (oldLoanDataM.getTotalOfCarPrice() != null && (oldLoanDataM.getTotalOfCarPrice().compareTo(newLoanDataM.getTotalOfCarPrice()) != 0)) {
                    log.debug("ReExecute appscore total of carprice");
                    return true;
                }
                if (oldLoanDataM.getTotalOfDownPayment() != null && (oldLoanDataM.getTotalOfDownPayment().compareTo(newLoanDataM.getTotalOfDownPayment()) != 0)) {
                    return true;
                }

            }

            //individal Term Loan
            if (oldLoanDataM.getBalloonFlag() != null && !oldLoanDataM.getBalloonFlag().equalsIgnoreCase(newLoanDataM.getBalloonFlag())) {
                log.debug("ReExecute appscore Case Balloon flag");
                return true;
            }
            if (OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase(oldLoanDataM.getBalloonFlag())) {
                //              individual loan term
                if (oldLoanDataM.getBalloonTerm() != null && (oldLoanDataM.getBalloonTerm().compareTo(newLoanDataM.getBalloonTerm()) != 0)) {
                    log.debug("ReExecute appscore Case Ballon term");
                    return true;
                }
            } else {
                if (oldLoanDataM.getInstallment1() != null
                        && (oldLoanDataM.getInstallment1().compareTo(newLoanDataM.getInstallment1()) != 0)) {
                    log.debug("ReExecute appscore Case Orig installment term");
                    //individual loan term
                    return true;
                }
            }
            
            
        }

        if (oldVehicleDataM != null) {
            //indivial Car Brand
            paramField = "car_brand";
            value = request.getParameter(paramField);
            if (oldVehicleDataM.getBrand() != null && !oldVehicleDataM.getBrand().equalsIgnoreCase(value)) {
                log.debug("ReExecute appscore Case Car brand");
                return true;
            }
            //car_category
            paramField = "car_category";
            value = request.getParameter(paramField);
            if (oldVehicleDataM.getCategory() != null && !oldVehicleDataM.getCategory().equalsIgnoreCase(newVehicleDataM.getCategory())) {
                log.debug("ReExecute appscore Case car category");
                return true;
            }
            //Car year
            paramField = "car_year";
            value = request.getParameter(paramField);
            if (oldVehicleDataM.getYear() != utility.stringToInt(value)) {
                log.debug("ReExecute appscore Case car year");
                return true;
            }

        }
        // No of Guarantor

        if (vOldPersonal.size() != vnewPersonal.size()) {
            log.debug("ReExecute appscore Case No of guaruntor");
            return true;
        }

        boolean ncbData = false;
        int totalNumberOfOverdue60day = 0;
        int maximumNumberOfOverdue = 0;
        String oldTracingCode = oldXRulesVerification.getNCBTrackingCode();
        String newTracingCode = newXRulesVerification.getNCBTrackingCode();
        if (oldTracingCode != null && !oldTracingCode.equalsIgnoreCase(newTracingCode)) {
            log.debug("ReExecute appscore Case re execute NCB");
            return true;
        }
        String blFlag = null;
        if (oldXRulesVerification.getBLResult() != null && !oldXRulesVerification.getBLResult().equalsIgnoreCase(newXRulesVerification.getBLResult())) {
            log.debug("ReExecute appscore Case Re execute Blacklist");
            return true;
        }
        //20080922  
        //NCB Check ReExcute  NCB
        //Guarantor Change
        log.debug("newApplicationDataM.getIsReExcuteAppScoreFlag() ="+newApplicationDataM.getIsReExcuteAppScoreFlag());
        if(newApplicationDataM.getIsReExcuteAppScoreFlag()){
            log.debug("ReExecute appscore Case Is ReexcuteAppScoreFlag");
            return true;
        }
        return false;
    }

    private boolean isApplicationChangeCorporate(ApplicationDataM oldApplicationDataM, ApplicationDataM newApplicationDataM, HttpServletRequest request) {
        log.debug("isApplicationChangeCorporrate");
        //map application to charcode;
        ORIGUtility utility = new ORIGUtility();
        // ===============Old Appplication======================
        PersonalInfoDataM oldPersonalInfoDataM;
        oldPersonalInfoDataM = utility.getPersonalInfoByType(oldApplicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        if (oldPersonalInfoDataM == null) {
            oldPersonalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM oldXRulesVerification = oldPersonalInfoDataM.getXrulesVerification();
        if (oldXRulesVerification == null) {
            oldXRulesVerification = new XRulesVerificationResultDataM();
        }
        AddressDataM oldAddrHome = utility.getAddressByType(oldPersonalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        Vector oldVLoan = oldApplicationDataM.getLoanVect();
        VehicleDataM oldVehicleDataM = oldApplicationDataM.getVehicleDataM();
        Vector vOldPersonal = oldApplicationDataM.getPersonalInfoVect();
        PreScoreDataM oldPreScoreDataM = oldPersonalInfoDataM.getPreScoreDataM();
        //==========================New Application===========
        PersonalInfoDataM newPersonalInfoDataM;
        newPersonalInfoDataM = utility.getPersonalInfoByType(newApplicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        if (newPersonalInfoDataM == null) {
            newPersonalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM newXRulesVerification = newPersonalInfoDataM.getXrulesVerification();
        if (newXRulesVerification == null) {
            newXRulesVerification = new XRulesVerificationResultDataM();
        }
        AddressDataM newAddrHome = utility.getAddressByType(newPersonalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        Vector newVLoan = newApplicationDataM.getLoanVect();
        VehicleDataM newVehicleDataM = newApplicationDataM.getVehicleDataM();
        Vector vnewPersonal = newApplicationDataM.getPersonalInfoVect();
        PreScoreDataM newPreScoreDataM = newPersonalInfoDataM.getPreScoreDataM();
        //========================================
        String paramField = "";
        String value = null;
        //age
        paramField = "age";
        value = request.getParameter("birth_date");
        log.debug("oldPersonalInfoDataM.getBirthDate) " + oldPersonalInfoDataM.getBirthDate());
        log.debug("ORIGUtility.parseThaiToEngDate(value) " + ORIGUtility.parseThaiToEngDate(value));
        Calendar calendarOld = Calendar.getInstance();
        calendarOld.setTime(oldPersonalInfoDataM.getBirthDate());
        Calendar calendarNew = Calendar.getInstance();
        calendarNew.setTime(ORIGUtility.parseThaiToEngDate(value));
        if (!(calendarOld.get(Calendar.YEAR) == (calendarNew.get(Calendar.YEAR) ) && calendarOld.get(Calendar.MONTH) == calendarNew.get(Calendar.MONTH) && calendarOld
                .get(Calendar.DAY_OF_MONTH) == calendarNew.get(Calendar.DAY_OF_MONTH))) {
            log.debug("ReExecute appscore Case age");
            return true;
        }
        //    total shareholder equity
        //double oldShareHolderEnquity = OrigScoringUtility.getShareholderEquity(oldPersonalInfoDataM.getCorperatedVect());
        //double newShareHolderEnquity = OrigScoringUtility.getShareholderEquity(newPersonalInfoDataM.getCorperatedVect());
       //
       // if (Double.compare(oldShareHolderEnquity, newShareHolderEnquity) != 0) {
       //     return true;
       // }       
        //Shareholder enquiry
        paramField = "pre_score_share_capital";
        value = request.getParameter(paramField);
        log.debug(" old Share holder  "+oldPreScoreDataM.getShareCapital());
        log.debug("Share holder value "+value);
        log.debug("Share Holder  "+utility.stringToBigDecimal(value));
        if(oldPreScoreDataM.getShareCapital()!=null&&(oldPreScoreDataM.getShareCapital().compareTo( utility.stringToBigDecimal(value))!=0)){
            log.debug("Share Holder case");
         return true;
        }
        
        //Land Owership
        paramField = "land_ownership";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getLandOwnerShip() != null && !oldPersonalInfoDataM.getLandOwnerShip().equalsIgnoreCase(value)) {
            log.debug("ReExecute appscore Case Land of owner ship");
            return true;
        }
        //    Cheque Returned
        //      ***
        paramField = "cheque_return";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getChequeReturn() != utility.stringToInt(value)) {
            log.debug("ReExecute appscore Case Cheque return");
            return true;
        }
        //Number of Employee
        // ***
        paramField = "no_of_employee";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getNoOfEmployee() != utility.stringToInt(value)) {
            log.debug("ReExecute appscore Case No of Employee");
            return true;
        }
        //      DSCR
        if (oldXRulesVerification.getDEBT_BDScore() != null && newXRulesVerification.getDEBT_BDScore() != null
                && (oldXRulesVerification.getDEBT_BDScore().compareTo(newXRulesVerification.getDEBT_BDScore()) != 0)) {
            log.debug("ReExecute appscore Case Re execute Debtburden");
            return true;
        }

        if (oldVLoan != null && oldVLoan.size() > 0 && newVLoan != null && newVLoan.size() > 0) {
            LoanDataM oldLoanDataM = (LoanDataM) oldVLoan.get(0);
            LoanDataM newLoanDataM = (LoanDataM) newVLoan.get(0);
            paramField = "car";
            value = request.getParameter(paramField);
            if (oldVehicleDataM.getCar() != null && !oldVehicleDataM.getCar().equalsIgnoreCase(value)) {
                log.debug("ReExecute appscore Case Car type");
                return true;
            }

            //corporate Down Payment
            paramField = "car";
            value = request.getParameter(paramField);
            if (oldVehicleDataM != null && OrigConstant.CAR_TYPE_USE.equalsIgnoreCase(value)) {

                if (oldLoanDataM.getCostOfCarPrice() != null && (oldLoanDataM.getCostOfCarPrice().compareTo(newLoanDataM.getCostOfCarPrice()) != 0)) {
                    log.debug("ReExecute appscore Cost of car price");
                    return true;
                }
                if (oldLoanDataM.getCostOfDownPayment() != null && (oldLoanDataM.getCostOfDownPayment().compareTo(newLoanDataM.getCostOfDownPayment()) != 0)) {
                    log.debug("ReExecute appscore cost of down payment");
                    return true;
                }

            } else if (oldVehicleDataM != null && OrigConstant.CAR_TYPE_NEW.equalsIgnoreCase(value)) {

                if (oldLoanDataM.getTotalOfCarPrice() != null && (oldLoanDataM.getTotalOfCarPrice().compareTo(newLoanDataM.getTotalOfCarPrice()) != 0)) {
                    log.debug("ReExecute appscore Total of car  price");
                    return true;
                }
                if (oldLoanDataM.getTotalOfDownPayment() != null && (oldLoanDataM.getTotalOfDownPayment().compareTo(newLoanDataM.getTotalOfDownPayment()) != 0)) {
                    log.debug("ReExecute appscore total of downpayment ");
                    return true;
                }

            }

            //corporate Term Loan
            if (oldLoanDataM.getBalloonFlag() != null && !oldLoanDataM.getBalloonFlag().equalsIgnoreCase(newLoanDataM.getBalloonFlag())) {
                log.debug("ReExecute appscore balloon flag");
                return true;
            }
            if (OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase(oldLoanDataM.getBalloonFlag())) {
                //              corporate loan term
                if (oldLoanDataM.getBalloonTerm() != null && (oldLoanDataM.getBalloonTerm().compareTo(newLoanDataM.getBalloonTerm()) != 0)) {
                    log.debug("ReExecute appscore Balloon term");
                    return true;
                }
            } else {
                if (oldLoanDataM.getInstallment1() != null
                        && (oldLoanDataM.getInstallment1().compareTo(newLoanDataM.getInstallment1()) != 0)) {
                    log.debug("ReExecute appscore Orig installment term");
                    //corporate loan term
                    return true;
                }
            }
        }
        if (oldVehicleDataM != null) {
            //indivial Car Brand
            paramField = "car_brand";
            value = request.getParameter(paramField);
            if (oldVehicleDataM.getBrand() != null && !oldVehicleDataM.getBrand().equalsIgnoreCase(value)) {
                log.debug("ReExecute appscore Car brand");
                return true;
            }
            //car_category
            paramField = "car_category";
            value = request.getParameter(paramField);
            if (oldVehicleDataM.getCategory() != null && !oldVehicleDataM.getCategory().equalsIgnoreCase(newVehicleDataM.getCategory())) {
                log.debug("ReExecute appscore Car category");
                return true;
            }
            //Car year
            paramField = "car_year";
            value = request.getParameter(paramField);
            if (oldVehicleDataM.getYear() != utility.stringToInt(value)) {
                log.debug("ReExecute appscore Car year");
                return true;
            }

        }

        // Maximum overdue days within 6 months ***
        paramField = "pre_score_overdue_over_60_days";
        value = request.getParameter(paramField);
        if (oldPreScoreDataM.getOverDue60dayIn12Month() != utility.stringToIntPrescoreData(value)) {
            log.debug("ReExecute appscore Pre scrore overdue 60 day in 12 month ");
            return true;
        }
        //    Maximum overdue days within 6 months ***
        paramField = "pre_score_max_time_overdue";
        value = request.getParameter(paramField);
        if (oldPreScoreDataM.getMaxOverDue6Month() != utility.stringToIntPrescoreData(value)) {
            log.debug("ReExecute appscore prescore Max Overdue");
            return true;
        }
        //Utilization of Revolving
        paramField = "pre_score_number_of_revolving_loan";
        value = request.getParameter(paramField);
        if (oldPreScoreDataM.getNoRevolvingLoan() != utility.stringToIntPrescoreData(value)) {
            log.debug("ReExecute appscore prescore  No of revolving");
            return true;
        }
        // NO of Hire Purchase
        paramField = "pre_score_number_of_automobile_hire_purchase";
        value = request.getParameter(paramField);
        if (oldPreScoreDataM.getNoAutoHirePurchase() != utility.stringToIntPrescoreData(value)) {
            log.debug("ReExecute appscore prescore no fo hire purchase");
            return true;
        }
        //NPL/Civil Suit History/Restructure        
            paramField = "pre_score_NPL_histry_restructure";
            value = request.getParameter(paramField);
            if (oldPreScoreDataM.getNPL() != null && !oldPreScoreDataM.getNPL().equalsIgnoreCase(value)) {
                return true;
            }
            log.debug("newApplicationDataM.getIsReExcuteAppScoreFlag() ="+newApplicationDataM.getIsReExcuteAppScoreFlag());
       if(newApplicationDataM.getIsReExcuteAppScoreFlag()){
                log.debug("ReExecute appscore Case Is ReexcuteAppScoreFlag");
                return true;
          }
        return false;
    }

    private boolean isApplicationChangeForeigner(ApplicationDataM oldApplicationDataM, ApplicationDataM newApplicationDataM, HttpServletRequest request) {
        log.debug("isApplicationChangeForeignger");
        //map application to charcode;
        ORIGUtility utility = new ORIGUtility();
        // ===============Old Appplication======================
        PersonalInfoDataM oldPersonalInfoDataM;
        oldPersonalInfoDataM = utility.getPersonalInfoByType(oldApplicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        if (oldPersonalInfoDataM == null) {
            oldPersonalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM oldXRulesVerification = oldPersonalInfoDataM.getXrulesVerification();
        if (oldXRulesVerification == null) {
            oldXRulesVerification = new XRulesVerificationResultDataM();
        }
        AddressDataM oldAddrHome = utility.getAddressByType(oldPersonalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        Vector oldVLoan = oldApplicationDataM.getLoanVect();
        VehicleDataM oldVehicleDataM = oldApplicationDataM.getVehicleDataM();
        Vector vOldPersonal = oldApplicationDataM.getPersonalInfoVect();
        PreScoreDataM oldPreScoreDataM = oldPersonalInfoDataM.getPreScoreDataM();
        //==========================New Application===========
        PersonalInfoDataM newPersonalInfoDataM;
        newPersonalInfoDataM = utility.getPersonalInfoByType(newApplicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        if (newPersonalInfoDataM == null) {
            newPersonalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM newXRulesVerification = newPersonalInfoDataM.getXrulesVerification();
        if (newXRulesVerification == null) {
            newXRulesVerification = new XRulesVerificationResultDataM();
        }
        AddressDataM newAddrHome = utility.getAddressByType(newPersonalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        Vector newVLoan = newApplicationDataM.getLoanVect();
        VehicleDataM newVehicleDataM = newApplicationDataM.getVehicleDataM();
        Vector vnewPersonal = oldApplicationDataM.getPersonalInfoVect();
        PreScoreDataM newPreScoreDataM = newPersonalInfoDataM.getPreScoreDataM();
        //========================================
        String paramField = "";
        String value = null;
        //map Data
        //age
        paramField = "age";
        value = request.getParameter("birth_date");
        log.debug("oldPersonalInfoDataM.getBirthDate) " + oldPersonalInfoDataM.getBirthDate());
        log.debug("utiliy.parseThaiToEngDate(value) " + ORIGUtility.parseThaiToEngDate(value));
        Calendar calendarOld = Calendar.getInstance();
        calendarOld.setTime(oldPersonalInfoDataM.getBirthDate());
        Calendar calendarNew = Calendar.getInstance();
        calendarNew.setTime(ORIGUtility.parseThaiToEngDate(value));
        if (!(calendarOld.get(Calendar.YEAR) == (calendarNew.get(Calendar.YEAR)) && calendarOld.get(Calendar.MONTH) == calendarNew.get(Calendar.MONTH) && calendarOld
                .get(Calendar.DAY_OF_MONTH) == calendarNew.get(Calendar.DAY_OF_MONTH))) {
            log.debug("ReExecute appscore age");
            return true;
        }
        //material status
        paramField = "marital_status";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getMaritalStatus() != null && !oldPersonalInfoDataM.getMaritalStatus().equalsIgnoreCase(value)) {
            log.debug("ReExecute appscore marital status");
            return true;
        }
        //Education level
        paramField = "qualification";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getQualification() != null && !oldPersonalInfoDataM.getQualification().equalsIgnoreCase(value)) {
            log.debug("ReExecute appscore qualification");
            return true;
        }
        //Sex
        paramField = "gender";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getGender() != null && !oldPersonalInfoDataM.getGender().equalsIgnoreCase(value)) {
            log.debug("ReExecute appscore gender");
            return true;
        }
        //working year
        paramField = "year";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getYearOfWork() != utility.stringToInt(value)) {
            log.debug("ReExecute appscore workign year");
            return true;
        }
        //      working month
        paramField = "month";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getMonthOfWork() != utility.stringToInt(value)) {
            log.debug("ReExecute appscore working month");
            return true;
        }

        //Occupation
        paramField = "occupation";
        value = request.getParameter(paramField);
        if (oldPersonalInfoDataM.getOccupation() != null && !oldPersonalInfoDataM.getOccupation().equalsIgnoreCase(value)) {
            log.debug("ReExecute ocupation");
            return true;
        }
        //Debt Burden
        if (oldXRulesVerification.getDEBT_BDScore() != null && newXRulesVerification.getDEBT_BDScore() != null
                && (oldXRulesVerification.getDEBT_BDScore().compareTo(newXRulesVerification.getDEBT_BDScore()) != 0)) {
            log.debug("ReExecute appscore debbd score");
            return true;
        }
        paramField = "pre_score_house_regis_status";
        value = request.getParameter(paramField);
        if (oldPreScoreDataM != null) {
            //Accomodation Status
            if (oldPreScoreDataM.getHouseRegistStatus() != null && !oldPreScoreDataM.getHouseRegistStatus().equalsIgnoreCase(value)) {
                log.debug("ReExecute appscore pre_score_house_regis_status");
                return true;
            }
        }     

        //Time in Current Address
        log.debug("oldAddrHome.getResideYear()="+oldAddrHome.getResideYear());
        log.debug("newAddrHome.getResideYear()="+newAddrHome.getResideYear());
        if ( oldAddrHome.getResideYear()!=null&&oldAddrHome.getResideYear().compareTo(newAddrHome.getResideYear() )!=0 ) {
            log.debug("ReExecute appscore Case Reside Year/month ");
            return true;
        }
        log.debug("oldAddrHome.getStatus()="+oldAddrHome.getStatus());
        log.debug("newAddrHome.getStatus()="+newAddrHome.getStatus());
        if ( oldAddrHome.getStatus()!=null&& !oldAddrHome.getStatus().equals(newAddrHome.getStatus())  ) {
            log.debug("ReExecute appscore Case accommodation status");
            return true;
        }
        if (oldVLoan != null && oldVLoan.size() > 0 && newVLoan != null && newVLoan.size() > 0) {
            LoanDataM oldLoanDataM = (LoanDataM) oldVLoan.get(0);
            LoanDataM newLoanDataM = (LoanDataM) newVLoan.get(0);
            if (oldVehicleDataM.getCar() != null && !oldVehicleDataM.getCar().equalsIgnoreCase(newVehicleDataM.getCar())) {
                log.debug("ReExecute appscore car");
                return true;
            }
            //individual Down Payment

            paramField = "car";
            value = request.getParameter(paramField);
            if (oldVehicleDataM != null && OrigConstant.CAR_TYPE_USE.equalsIgnoreCase(value)) {

                if (oldLoanDataM.getCostOfCarPrice() != null && (oldLoanDataM.getCostOfCarPrice().compareTo(newLoanDataM.getCostOfCarPrice()) != 0)) {
                    log.debug("ReExecute appscore cost of carprice");
                    return true;
                }
                if (oldLoanDataM.getCostOfDownPayment() != null && (oldLoanDataM.getCostOfDownPayment().compareTo(newLoanDataM.getCostOfDownPayment()) != 0)) {
                    log.debug("ReExecute appscore cost of downpayment");
                    return true;
                }
            } else if (oldVehicleDataM != null && OrigConstant.CAR_TYPE_NEW.equalsIgnoreCase(value)) {

                if (oldLoanDataM.getTotalOfCarPrice() != null && (oldLoanDataM.getTotalOfCarPrice().compareTo(newLoanDataM.getTotalOfCarPrice()) != 0)) {
                    log.debug("ReExecute appscore total of carprice");
                    return true;
                }
                if (oldLoanDataM.getTotalOfDownPayment() != null && (oldLoanDataM.getTotalOfDownPayment().compareTo(newLoanDataM.getTotalOfDownPayment()) != 0)) {
                    log.debug("ReExecute appscore total of down payment");
                    return true;
                }

            }
             log.debug(" newLoanDataM.getBalloonFlag() "+newLoanDataM.getBalloonFlag());
             log.debug(" oldLoanDataM.getBalloonTerm() "+oldLoanDataM.getBalloonTerm());
             log.debug(" newLoanDataM.getBalloonTerm() "+newLoanDataM.getBalloonTerm());
             log.debug(" newLoanDataM.getOrigInstallmentTerm() "+newLoanDataM.getBalloonFlag());
            //individal Term Loan
            if (oldLoanDataM.getBalloonFlag() != null && !oldLoanDataM.getBalloonFlag().equalsIgnoreCase(newLoanDataM.getBalloonFlag())) {
                log.debug("ReExecute appscore balloonflag");
                return true;
            }
            if (OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase(oldLoanDataM.getBalloonFlag())) {
                //              individual loan term
                if (oldLoanDataM.getBalloonTerm() != null && (oldLoanDataM.getBalloonTerm().compareTo(newLoanDataM.getBalloonTerm()) != 0)) {
                    log.debug("ReExecute appscore tablloon term");
                    return true;
                }
            } else {
                if (oldLoanDataM.getInstallment1() != null
                        && (oldLoanDataM.getInstallment1().compareTo(newLoanDataM.getInstallment1()) != 0)) {
                    log.debug("ReExecute appscore installment term");
                    //individual loan term
                    return true;
                }
            }
        }
        if (oldVehicleDataM != null) {
            //indivial Car Brand
            paramField = "car_brand";
            value = request.getParameter(paramField);
            if (oldVehicleDataM.getBrand() != null && !oldVehicleDataM.getBrand().equalsIgnoreCase(value)) {
                log.debug("ReExecute appscore car brand");
                return true;
            }
            //car_category
            paramField = "car_category";
            value = request.getParameter(paramField);
            if (oldVehicleDataM.getCategory() != null && !oldVehicleDataM.getCategory().equalsIgnoreCase(newVehicleDataM.getCategory())) {
                log.debug("ReExecute appscore car category");
                return true;
            }
            //Car year
            paramField = "car_year";
            value = request.getParameter(paramField);
            if (oldVehicleDataM.getYear() != utility.stringToInt(value)) {
                log.debug("ReExecute appscore car year");
                return true;
            }

        }
        // No of Guarantor
        if (vOldPersonal.size() != vnewPersonal.size()) {
            log.debug("ReExecute appscore no of guarantor");
            return true;
        }
        //==========================
        // Maximum overdue days within 6 months ***
        paramField = "pre_score_overdue_over_60_days";
        value = request.getParameter(paramField);
        if (oldPreScoreDataM.getOverDue60dayIn12Month() != utility.stringToIntPrescoreData(value)) {
            log.debug("ReExecute appscore pre_score_overdue_over_60_days");
            return true;
        }
        //    Maximum overdue days within 6 months ***
        paramField = "pre_score_max_time_overdue";
        value = request.getParameter(paramField);
        if (oldPreScoreDataM.getMaxOverDue6Month() != utility.stringToIntPrescoreData(value)) {
            log.debug("ReExecute appscore pre_score_max_time_overdue");
            return true;
        }
        //Utilization of Revolving
        paramField = "pre_score_number_of_revolving_loan";
        value = request.getParameter(paramField);
        if (oldPreScoreDataM.getNoRevolvingLoan() != utility.stringToIntPrescoreData(value)) {
            log.debug("ReExecute appscore pre_score_number_of_revolving_loan");
            return true;
        }
        // NO of Hire Purchase
        paramField = "pre_score_number_of_automobile_hire_purchase";
        value = request.getParameter(paramField);
        if (oldPreScoreDataM.getNoAutoHirePurchase() != utility.stringToIntPrescoreData(value)) {
            log.debug("ReExecute appscore pre_score_number_of_automobile_hire_purchase");
            return true;
        }
        //NPL
        String blFlag = null;
        if (oldXRulesVerification.getBLResult() != null && !oldXRulesVerification.getBLResult().equalsIgnoreCase(newXRulesVerification.getBLResult())) {
            log.debug("ReExecute appscore Blacklist");
            return true;
        }
        log.debug("newApplicationDataM.getIsReExcuteAppScoreFlag() ="+newApplicationDataM.getIsReExcuteAppScoreFlag());
        if(newApplicationDataM.getIsReExcuteAppScoreFlag()){
            log.debug("ReExecute appscore Case Is ReexcuteAppScoreFlag");
            return true;
        }
        return false;
    }

}
