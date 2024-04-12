/*
 * Created on Nov 30, 2007
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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
import com.eaf.ncb.model.output.TLRespM;
//import com.eaf.ncb.service.ejb.NCBServiceManager;
import com.eaf.orig.cache.properties.ScoringCharecteristicProperties;
import com.eaf.orig.cache.properties.ScoringProperties;
import com.eaf.orig.cache.properties.ScoringTypeProperties;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.CorperatedDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.PreScoreDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
//import com.eaf.orig.shared.xrules.dao.XRulesScoringLogDAO;
import com.eaf.orig.wf.shared.ORIGWFConstant;
import com.eaf.xrules.shared.constant.XRulesConstant;
//import com.eaf.xrules.shared.factory.XRulesEJBFactory;
import com.eaf.xrules.shared.model.XRulesNCBDataM;
import com.eaf.xrules.shared.model.XRulesScoringLogDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 * 
 * Type: OrigScoringUtility
 */
public class OrigScoringUtility implements Serializable {
    static Logger log = Logger.getLogger(OrigScoringUtility.class);

    /**
     *  
     */
    public OrigScoringUtility() {
        super();
    }

    public String calcuateApplicationScoreing(ApplicationDataM applicationDataM, UserDetailM userM) {
        ORIGUtility utility = new ORIGUtility();
        PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        //get Main Personal Type
        int applicationScore = 0;
        applicationScore = this.calulateScoring(applicationDataM);
        String scoringResult = "N/A";
        String customerType = "";
        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())
                || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equals(personalInfoDataM.getCustomerType())) {
            customerType = OrigConstant.CUSTOMER_TYPE_INDIVIDUAL;
        } else if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(personalInfoDataM.getCustomerType())) {
            customerType = OrigConstant.CUSTOMER_TYPE_CORPORATE;
        }
        ScoringProperties scoring = this.getScoring(customerType);
        if (scoring != null) {
            log.debug("OrigScoringUtility-->caluateApplicationScoreing  scoring " + scoring);

            log.debug("OrigScoringUtility-->caluateApplicationScoreing  Customer Type " + personalInfoDataM.getCustomerType());
            if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())) {
                log.debug("OrigScoringUtility-->caluateApplicationScoreing  Customer Type Individual + score " + scoring.getScoringConstant());
                applicationScore += scoring.getScoringConstant();
            }
            log.debug("OrigScoringUtility-->caluateApplicationScoreing  applicationScore " + applicationScore);
            VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
            if (vehicleDataM == null) {
                vehicleDataM = new VehicleDataM();
            }
            if (OrigConstant.CAR_TYPE_USE.equalsIgnoreCase(vehicleDataM.getCar())) {
                log.debug("OrigScoringUtility-->caluateApplicationScoreing Car Type Used");
                log.debug("OrigScoringUtility-->caluateApplicationScoreing  Accept Score Car Used " + scoring.getAcceptScoreUsedCar());
                log.debug("OrigScoringUtility-->caluateApplicationScoreing  Reject Score Car Used " + scoring.getRejectScoreUsedCar());
                if (applicationScore >= scoring.getAcceptScoreUsedCar()) {
                    scoringResult = OrigConstant.Scoring.SCORING_ACCEPT;
                } else if (applicationScore > scoring.getRejectScoreUsedCar()) {
                    scoringResult = OrigConstant.Scoring.SCORING_REFER;
                } else {
                    scoringResult = OrigConstant.Scoring.SCORING_REJECT;
                }
            } else if (ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equalsIgnoreCase(applicationDataM.getJobState())
                    || ORIGWFConstant.JobState.UW_PROPOSAL_PENDING_STATE.equalsIgnoreCase(applicationDataM.getJobState())
                    || OrigConstant.CAR_TYPE_NEW.equalsIgnoreCase(vehicleDataM.getCar())) {
                log.debug("OrigScoringUtility-->caluateApplicationScoreing Car Type New");
                log.debug("OrigScoringUtility-->caluateApplicationScoreing  Accept Score " + scoring.getAcceptScore());
                log.debug("OrigScoringUtility-->caluateApplicationScoreing  Reject Score " + scoring.getRejectScore());
                if (applicationScore >= scoring.getAcceptScore()) {
                    scoringResult = OrigConstant.Scoring.SCORING_ACCEPT;
                } else if (applicationScore > scoring.getRejectScore()) {
                    scoringResult = OrigConstant.Scoring.SCORING_REFER;
                } else {
                    scoringResult = OrigConstant.Scoring.SCORING_REJECT;
                }
            } else {
                log.fatal("Unknow Car Type");
                //mock up score
                if (applicationScore >= scoring.getAcceptScore()) {
                    scoringResult = OrigConstant.Scoring.SCORING_ACCEPT;
                } else if (applicationScore > scoring.getRejectScore()) {
                    scoringResult = OrigConstant.Scoring.SCORING_REFER;
                } else {
                    scoringResult = OrigConstant.Scoring.SCORING_REJECT;
                }
            }

            XRulesVerificationResultDataM xrulesVerification = personalInfoDataM.getXrulesVerification();
            Timestamp updateTime = new Timestamp((new Date()).getTime());
            //if (xrulesVerification != null) {
            // xrulesVerification.setScoringUpdateBy(userM.getUserName());
            //  xrulesVerification.setScoringUpdateDate(updateTime);
            //}
            //Save Scoring log
            try {
//                XRulesScoringLogDAO xRulesScoringLogDAO = ORIGDAOFactory.getXRulesScoringLogDAO();
                XRulesScoringLogDataM xRulesScoringLogDataM = new XRulesScoringLogDataM();
                xRulesScoringLogDataM.setApplicationRecordId(applicationDataM.getAppRecordID());
//                int seq = xRulesScoringLogDAO.getMaxScoringSequence(xRulesScoringLogDataM);
                
                int seq = PLORIGEJBService.getORIGDAOUtilLocal().getMaxScoringSequence(xRulesScoringLogDataM);
                
                seq++;
                xRulesScoringLogDataM.setScoring(applicationScore);
                xRulesScoringLogDataM.setScoringResult(scoringResult);
                xRulesScoringLogDataM.setSeq(seq);
                xRulesScoringLogDataM.setUpdateBy(userM.getUserName());
                xRulesScoringLogDataM.setUpdateDate(updateTime);
                
//                xRulesScoringLogDAO.createXRulesScoringLogM(xRulesScoringLogDataM);
                
                PLORIGEJBService.getORIGDAOUtilLocal().createXRulesScoringLogM(xRulesScoringLogDataM);
                
            } catch (Exception e) {
                log.debug(e.getMessage());
                e.printStackTrace();
            }
        }
        log.debug("OrigScoringUtility-->caluateApplicationScoreing  result " + scoringResult);
        return scoringResult;
    }

    private int calulateScoring(ApplicationDataM applicationDataM) {
        //get Scoring Type
        //applicationDataM
        ORIGUtility utility = new ORIGUtility();
        PersonalInfoDataM personalInfoDataM;
        personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        HashMap mapData = this.mapData(applicationDataM);
        int totalScore = 0;
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        String customerType = "";
        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())
                || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equals(personalInfoDataM.getCustomerType())) {
            customerType = OrigConstant.CUSTOMER_TYPE_INDIVIDUAL;
        } else if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(personalInfoDataM.getCustomerType())) {
            customerType = OrigConstant.CUSTOMER_TYPE_CORPORATE;
        }
        Vector vScoringType = this.getScoringTypeByCustomerType(customerType);
        int scoring = 0;
        for (int i = 0; i < vScoringType.size(); i++) {
            ScoringTypeProperties scoringType = (ScoringTypeProperties) vScoringType.get(i);
            if (scoringType != null) {
                Vector vScoringCharecteristic = this.getScoringCharecteristicByCustomerType(scoringType.getScorignCode());
                scoring = this.calculateCharacteristicScoreing(mapData, vScoringCharecteristic);
               // log.debug("Score  of  " + scoringType.getScorignCode() + "  = " + scoring);
            }
            totalScore = totalScore + scoring;
            //log.debug("Total Score " + totalScore);
        }
       // log.debug("Total Final Score " + totalScore);
        return totalScore;

    }

    /*
     * private int calulateScoringCoperate(ApplicationDataM applicationDataM){
     * HashMap mapData=this.mapData(applicationDataM); return 0; } private int
     * calulateScoringForeigner(ApplicationDataM applicationDataM){ HashMap
     * mapData=this.mapData(applicationDataM); return 0; }
     */

    private ScoringProperties getScoring(String customerType) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector v = (Vector) (h.get("Scoring"));
        if (v != null && v.size() > 0) {
            for (int i = 0; i < v.size(); i++) {
                ScoringProperties scoring = (ScoringProperties) v.get(i);
                if (customerType.equalsIgnoreCase(scoring.getCustomerType())) {
                    return scoring;
                }
            }
        }
        return null;
    }

    private Vector getScoringTypeByCustomerType(String customerType) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector v = (Vector) (h.get("ScoringType"));
        Vector result = new Vector();
        if (v != null && v.size() > 0) {
            for (int i = 0; i < v.size(); i++) {
                ScoringTypeProperties scoringType = (ScoringTypeProperties) v.get(i);
                if (customerType.equalsIgnoreCase(scoringType.getCustomerType())) {
                    result.add(scoringType);
                }
            }

        }
        return result;
    }

    private Vector getScoringCharecteristicByCustomerType(String scoringCode) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector v = (Vector) (h.get("ScoringCharecteristic"));
        Vector result = new Vector();
        if (v != null && v.size() > 0) {
            for (int i = 0; i < v.size(); i++) {
                ScoringCharecteristicProperties scoringCharecteristic = (ScoringCharecteristicProperties) v.get(i);
                if (scoringCode.equalsIgnoreCase(scoringCharecteristic.getScoreCode())) {
                    result.add(scoringCharecteristic);
                }
            }

        }
        return result;
    }

    private int calculateCharacteristicScoreing(HashMap mapData, Vector vScoringCharecteristic) {
        int totalOfScore = 0;
        int charScoreing = 0;
        for (int j = 0; j < vScoringCharecteristic.size(); j++) {
            ScoringCharecteristicProperties scoringCharecteristic = (ScoringCharecteristicProperties) vScoringCharecteristic.get(j);
            Object dataCompare = mapData.get(scoringCharecteristic.getCharCode());
            //log.debug("char code " + scoringCharecteristic.getCharCode() + " Descitpon " + scoringCharecteristic.getCharDescription() + " dataCompare "
            //        + dataCompare + " Char Type " + scoringCharecteristic.getCharType() + " Specific " + scoringCharecteristic.getSpecific() + " MinRank "
            //        + scoringCharecteristic.getMinRange() + " MaxRank " + scoringCharecteristic.getMaxRange());

            if (dataCompare != null && OrigConstant.Scoring.CHAR_TYPE_SPECIFIC.equalsIgnoreCase(scoringCharecteristic.getCharType())) {
                if (scoringCharecteristic.getSpecific().equals((String) dataCompare)) {
                    charScoreing = scoringCharecteristic.getScore();
                    totalOfScore += charScoreing;
                  //  log.debug("***char code " + scoringCharecteristic.getCharCode() + " Seq " + scoringCharecteristic.getSeq() + " HIT Score "
                   //         + scoringCharecteristic.getScore());
                    // return charScoreing;
                }
            } else if (dataCompare != null && OrigConstant.Scoring.CHAR_TYPE_RANGE.equalsIgnoreCase(scoringCharecteristic.getCharType())) {
                try {
                    Double doubleDataCompare = (Double) dataCompare;

                    if ((doubleDataCompare.compareTo(new Double(scoringCharecteristic.getMinRange())) >= 0)
                            && ((doubleDataCompare.compareTo(new Double(scoringCharecteristic.getMaxRange())) <= 0) || (Double.compare(scoringCharecteristic
                                    .getMaxRange(), -1d) == 0))) {
                        charScoreing = scoringCharecteristic.getScore();
                        totalOfScore += charScoreing;
                     //   log.debug("***Rank Hit  code " + scoringCharecteristic.getCharCode() + "  data " + doubleDataCompare + " Score=" + charScoreing);
                        //return charScoreing;
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

            } else if (dataCompare == null && OrigConstant.Scoring.CHAR_TYPE_NULL.equalsIgnoreCase(scoringCharecteristic.getCharType())) {
                charScoreing = scoringCharecteristic.getScore();
                totalOfScore += charScoreing;
               // log.debug("***char code " + scoringCharecteristic.getCharCode() + "  Seq " + scoringCharecteristic.getSeq() + "  Score "
                //        + scoringCharecteristic.getScore() + " Hit null Score ");
                // return charScoreing;

            }
        }//loop

        return totalOfScore;
    }

    private HashMap mapData(ApplicationDataM applicationDataM) {
        //map application to charcode;
        ORIGUtility utility = new ORIGUtility();
        PersonalInfoDataM personalInfoDataM;
        personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);

        HashMap dataMap = new HashMap();
        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
            dataMap = this.mapDataIndividual(applicationDataM);
        } else if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
            dataMap = this.mapDataCorporate(applicationDataM);
        } else if (OrigConstant.CUSTOMER_TYPE_FOREIGNER.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
            //dataMap = this.mapDataIndividual(applicationDataM);
            dataMap = this.mapDataForeigner(applicationDataM);
        }
        return dataMap;
    }

    private HashMap mapDataIndividual(ApplicationDataM applicationDataM) {

        //map application to charcode;
        ORIGUtility utility = new ORIGUtility();
        PersonalInfoDataM personalInfoDataM;
        personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);

        HashMap dataMap = new HashMap();

        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xRulesVerification = personalInfoDataM.getXrulesVerification();
        if (xRulesVerification == null) {
            xRulesVerification = new XRulesVerificationResultDataM();
        }
        AddressDataM addrHome = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        OrigXRulesUtil origXrulesUtil=OrigXRulesUtil.getInstance();
        //XRulesdxRulesVerification.getXrulesDebtBdDataM();
        //map Data
        //age                 
        dataMap.put("001", new Double(origXrulesUtil.getAge(personalInfoDataM.getBirthDate())));
        //material status
        dataMap.put("002", personalInfoDataM.getMaritalStatus());
        //Education level
        dataMap.put("003", personalInfoDataM.getQualification());
        //Sex
        dataMap.put("004", personalInfoDataM.getGender());
        //working year
        dataMap.put("005", new Double(personalInfoDataM.getYearOfWork()));
        //Occupation
        dataMap.put("006", personalInfoDataM.getOccupation());
        //Debt Burden
        if (xRulesVerification.getDEBT_BDScore() != null) {
            dataMap.put("007", new Double(xRulesVerification.getDEBT_BDScore().doubleValue()));
        }
        if (addrHome != null) {
            //Accomodation Status
             
            dataMap.put("008", addrHome.getStatus());
            //dataMap.put("008", personalInfoDataM.getHouseRegisStatus());
            //Time in Current Address
            if(addrHome.getResideYear()!=null){
            dataMap.put("009", new Double(addrHome.getResideYear().intValue()));
            }
        }        
        Vector vLoan = applicationDataM.getLoanVect();
        VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
        if (vLoan != null && vLoan.size() > 0) {
            LoanDataM loanDataM = (LoanDataM) vLoan.get(0);
            //individual Down Payment
            BigDecimal percentOfDownPayment = null;
            if (vehicleDataM != null && OrigConstant.CAR_TYPE_USE.equalsIgnoreCase(vehicleDataM.getCar())) {
                try { 
                    percentOfDownPayment = utility.calculatePercentDownPayment(loanDataM.getCostOfCarPrice(), loanDataM.getCostOfDownPayment());
                } catch (Exception e1) {
                    log.debug("Error cal down payment", e1);
                }

            } else if (vehicleDataM != null && OrigConstant.CAR_TYPE_NEW.equalsIgnoreCase(vehicleDataM.getCar())) {
                try {
                    percentOfDownPayment = utility.calculatePercentDownPayment(loanDataM.getTotalOfCarPrice(), loanDataM.getTotalOfDownPayment());
                } catch (Exception e1) {
                    log.debug("Error cal down payment", e1);
                }
            }
            if (percentOfDownPayment != null) {
                dataMap.put("010", new Double(percentOfDownPayment.doubleValue()));
            }

            //individal Term Loan
            if (OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase(loanDataM.getBalloonFlag())) {
                if (loanDataM.getBalloonTerm() != null) {
                    //individual loan term
                    dataMap.put("011", new Double(loanDataM.getBalloonTerm().doubleValue()));
                }
            } else {
                if (loanDataM.getInstallment1() != null) {
                    //individual loan term
                    dataMap.put("011", new Double(loanDataM.getInstallment1().doubleValue()));
                }
            }
        }

        if (vehicleDataM != null) {
            //indivial Car Brand
            dataMap.put("012", vehicleDataM.getBrand());
            //Individual Car Type
            dataMap.put("013", vehicleDataM.getCategory());
            //Car Age of Car
            Calendar clNow = Calendar.getInstance();
            int carAgeYear = clNow.get(Calendar.YEAR) - vehicleDataM.getYear();
            dataMap.put("014", new Double(carAgeYear));
            // }
        }
        // No of Guarantor
        Vector vPersonal = applicationDataM.getPersonalInfoVect();
        dataMap.put("015", new Double(vPersonal.size() - 1));
        boolean ncbData = false;
        int totalNumberOfOverdue60day = 0;
        int maximumNumberOfOverdue = 0;
        String tracingCode = xRulesVerification.getNCBTrackingCode();
        Vector vXRulesNCBDataM = xRulesVerification.getVXRulesNCBDataM();
        log.debug("tracingCode " + tracingCode);
        if (OrigConstant.NCBcolor.BLACK.equalsIgnoreCase(xRulesVerification.getNCBColor())) {
            log.debug("NCB No result");
            dataMap.put("016", null);
            dataMap.put("017", null);
            dataMap.put("019", null);
            dataMap.put("018", null);
        } else {
            Vector vAccountDetail = null;
            int hirePurchaseCount = 0;
            double percentOfRevolving = 0;
            try {
                if ((tracingCode != null && !"".equals(tracingCode) && vXRulesNCBDataM != null)) {

                    for (int i = 0; i < vXRulesNCBDataM.size(); i++) {
                        XRulesNCBDataM xRulesNCBDataM = (XRulesNCBDataM) vXRulesNCBDataM.get(i);
                        String accountType = xRulesNCBDataM.getLoanType();
                        int groupSeq=xRulesNCBDataM.getGroupSeq();
//                        NCBServiceManager ncbService = XRulesEJBFactory.getNCBServiceManager();
//                        vAccountDetail = ncbService.getNCBAccountDetail(tracingCode, accountType,groupSeq);

                        if (accountType.equalsIgnoreCase(OrigConstant.AccountType.AUTOMOBILE_HIRE_PURCHASE)
                                || accountType.equalsIgnoreCase(OrigConstant.AccountType.OTHER_HIRE_PURCHASE)) {
                            if (vAccountDetail != null) {
                                for (int j = 0; j < vAccountDetail.size(); j++) {
                                    TLRespM account = (TLRespM) vAccountDetail.get(j);
                                    String accountStatus = (String) XRulesConstant.hAccountStatusDebtAmt.get(account.getAccountStatus());
                                    if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountStatus)) {
                                        hirePurchaseCount++;
                                    }
                                }
                            }

                        } else if (accountType.equalsIgnoreCase(OrigConstant.AccountType.Revolving)) {
                            if (vAccountDetail != null) {
                                percentOfRevolving = OrigXRulesUtil.getInstance().getPercentOfRevolving(vAccountDetail);

                            }

                        }

                        int maxPayment = this.getMaximumPayment(vAccountDetail, 6);
                        if (maxPayment > maximumNumberOfOverdue) {
                            maximumNumberOfOverdue = maxPayment;
                        }
                        log.debug("maximumNumberOfOverdue " + maximumNumberOfOverdue);
                        totalNumberOfOverdue60day += this.getOverDueMorethan60day(vAccountDetail);
                        log.debug("totalNumberOfOverdue60day " + totalNumberOfOverdue60day);
                    }
                }
                //Set Data to Session
            } catch (Exception e) {
                e.printStackTrace();
                log.fatal(e.getLocalizedMessage());
            }

            dataMap.put("016", new Double(totalNumberOfOverdue60day));
            dataMap.put("017", new Double(maximumNumberOfOverdue));
            // Utilization of Revolving
            dataMap.put("018", new Double(percentOfRevolving));
            //individual no of hire purchase
            dataMap.put("019", new Double(hirePurchaseCount));
        }
        String blFlag = null;
        if (XRulesConstant.ExecutionResultString.RESULT_PASS.equalsIgnoreCase(xRulesVerification.getBLResult())) {
            blFlag = OrigConstant.ORIG_FLAG_N;
        } else if (XRulesConstant.ExecutionResultString.RESULT_FAIL.equalsIgnoreCase(xRulesVerification.getBLResult())) {
            blFlag = OrigConstant.ORIG_FLAG_Y;
        }
        dataMap.put("020", blFlag);
        return dataMap;
    }

    private HashMap mapDataCorporate(ApplicationDataM applicationDataM) {
        //map application to charcode;
        ORIGUtility utility = new ORIGUtility();
        PersonalInfoDataM personalInfoDataM;
        personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        HashMap dataMap = new HashMap();
        XRulesVerificationResultDataM xRulesVerification = personalInfoDataM.getXrulesVerification();
        if (xRulesVerification == null) {
            xRulesVerification = new XRulesVerificationResultDataM();
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        PreScoreDataM preScoreDataM = personalInfoDataM.getPreScoreDataM();
        if (preScoreDataM == null) {
            preScoreDataM = new PreScoreDataM();
        }
        AddressDataM addrHome = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        OrigXRulesUtil origXrulesUtil=OrigXRulesUtil.getInstance();
        //business age
        dataMap.put("021", new Double(origXrulesUtil.getAge(personalInfoDataM.getBirthDate())));
        //--------------
        //    total shareholder equity
       /* if (personalInfoDataM.getCorperatedVect() != null) {
            double shareHolderEnquity = getShareholderEquity(personalInfoDataM.getCorperatedVect());
            dataMap.put("022", new Double(shareHolderEnquity));
        }*/
        if (preScoreDataM.getShareCapital() != null) {
            dataMap.put("022", new Double(preScoreDataM.getShareCapital().intValue()));
        }
        //Land Owership
        dataMap.put("023", personalInfoDataM.getLandOwnerShip());
        //    Cheque Returned
        //      ***
        dataMap.put("024", new Double(personalInfoDataM.getChequeReturn()));
        //Number of Employee
        // ***
        dataMap.put("025", new Double(personalInfoDataM.getNoOfEmployee()));
        //      DSCR
        if (xRulesVerification.getDEBT_BDScore() != null) {
            dataMap.put("026", new Double(xRulesVerification.getDEBT_BDScore().doubleValue()));
        }
        Vector vLoan = applicationDataM.getLoanVect();
        VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
        if (vLoan != null && vLoan.size() > 0) {
            LoanDataM loanDataM = (LoanDataM) vLoan.get(0);

            //coporate Down Payment
            BigDecimal percentOfDownPayment = null;
            if (vehicleDataM != null && OrigConstant.CAR_TYPE_USE.equalsIgnoreCase(vehicleDataM.getCar())) {
                try {
                    percentOfDownPayment = utility.calculatePercentDownPayment(loanDataM.getCostOfCarPrice(), loanDataM.getCostOfDownPayment());
                } catch (Exception e1) {
                    log.debug("Error cal down payment", e1);
                }

            } else if (vehicleDataM != null && OrigConstant.CAR_TYPE_NEW.equalsIgnoreCase(vehicleDataM.getCar())) {
                try {
                    percentOfDownPayment = utility.calculatePercentDownPayment(loanDataM.getTotalOfCarPrice(), loanDataM.getTotalOfDownPayment());
                } catch (Exception e1) {
                    log.debug("Error cal down payment", e1);
                }
            }
            if (percentOfDownPayment != null) {
                dataMap.put("027", new Double(percentOfDownPayment.doubleValue()));
            }

            //copoerate Term Loan
            if (OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase(loanDataM.getBalloonFlag())) {
                if (loanDataM.getBalloonTerm() != null) {
                    dataMap.put("028", String.valueOf(loanDataM.getBalloonTerm()));
                }
            } else {
                if (loanDataM.getInstallment1() != null) {
                    // corporate Request Term of loan
                    dataMap.put("028", new Double(loanDataM.getInstallment1().doubleValue()));
                }
            }
        }
        if (vehicleDataM != null) {
            //indivial Car Brand
            dataMap.put("029", vehicleDataM.getBrand());
            //corporate Car Type
            dataMap.put("030", vehicleDataM.getCategory());
            //Car Age of Car
            Calendar clNow = Calendar.getInstance();
            int carAgeYear = clNow.get(Calendar.YEAR) - vehicleDataM.getYear();
            dataMap.put("031", new Double(carAgeYear));
        }
        
        // Maximum overdue days within 6 months ***
        if (preScoreDataM.getOverDue60dayIn12Month() != -1) {
            dataMap.put("032", new Double(preScoreDataM.getOverDue60dayIn12Month()));
        }
        //Maximum overdue days within 6 months ***
        if (preScoreDataM.getMaxOverDue6Month() != -1) {
            dataMap.put("033", new Double(preScoreDataM.getMaxOverDue6Month()));
        }
        //Utilization of Revolving
        if (preScoreDataM.getNoRevolvingLoan() != -1) {
            dataMap.put("034", new Double(preScoreDataM.getNoRevolvingLoan()));
        }
        //NO of Hire Purchase
        if (preScoreDataM.getNoAutoHirePurchase() != -1) {
            dataMap.put("035", new Double(preScoreDataM.getNoAutoHirePurchase()));
        }
        //NPL/Civil Suit History/Restructure
        dataMap.put("036", preScoreDataM.getNPL());
        return dataMap;
    }

    private HashMap mapDataForeigner(ApplicationDataM applicationDataM) {
        //map application to charcode;
        ORIGUtility utility = new ORIGUtility();
        PersonalInfoDataM personalInfoDataM;
        personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);

        HashMap dataMap = new HashMap();

        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xRulesVerification = personalInfoDataM.getXrulesVerification();
        if (xRulesVerification == null) {
            xRulesVerification = new XRulesVerificationResultDataM();
        }
        AddressDataM addrHome = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        OrigXRulesUtil origXrulesUtil=OrigXRulesUtil.getInstance();
        //XRulesdxRulesVerification.getXrulesDebtBdDataM();
        //map Data
        //age
        dataMap.put("001", new Double(origXrulesUtil.getAge(personalInfoDataM.getBirthDate())));
        //material status
        dataMap.put("002", personalInfoDataM.getMaritalStatus());
        //Education level
        dataMap.put("003", personalInfoDataM.getQualification());
        //Sex
        dataMap.put("004", personalInfoDataM.getGender());
        //working year
        dataMap.put("005", new Double(personalInfoDataM.getYearOfWork()));
        //Occupation
        dataMap.put("006", personalInfoDataM.getOccupation());
        //Debt Burden
        if (xRulesVerification.getDEBT_BDScore() != null) {
            dataMap.put("007", new Double(xRulesVerification.getDEBT_BDScore().doubleValue()));
        }
        if (addrHome != null) {
            //Accomodation Status
             dataMap.put("008", addrHome.getStatus());
            //dataMap.put("008", personalInfoDataM.getHouseRegisStatus());
            //Time in Current Address
             if(addrHome.getResideYear()!=null){
            dataMap.put("009", new Double(addrHome.getResideYear().intValue()));
             }
        }
      
        Vector vLoan = applicationDataM.getLoanVect();
        VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
        if (vLoan != null && vLoan.size() > 0) {
            LoanDataM loanDataM = (LoanDataM) vLoan.get(0);

            //individual Down Payment
            BigDecimal percentOfDownPayment = null;
            if (vehicleDataM != null && OrigConstant.CAR_TYPE_USE.equalsIgnoreCase(vehicleDataM.getCar())) {
                try {
                    percentOfDownPayment = utility.calculatePercentDownPayment(loanDataM.getCostOfCarPrice(), loanDataM.getCostOfDownPayment());
                } catch (Exception e1) {
                    log.debug("Error cal down payment", e1);
                }

            } else if (vehicleDataM != null && OrigConstant.CAR_TYPE_NEW.equalsIgnoreCase(vehicleDataM.getCar())) {
                try {
                    percentOfDownPayment = utility.calculatePercentDownPayment(loanDataM.getTotalOfCarPrice(), loanDataM.getTotalOfDownPayment());
                } catch (Exception e1) {
                    log.debug("Error cal down payment", e1);
                }
            }
            if (percentOfDownPayment != null) {
                dataMap.put("010", new Double(percentOfDownPayment.doubleValue()));
            }
            //individal Term Loan
            if (OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase(loanDataM.getBalloonFlag())) {
                if (loanDataM.getBalloonTerm() != null) {
                    //individual loan term
                    dataMap.put("011", new Double(loanDataM.getBalloonTerm().doubleValue()));
                }
            } else {
                if (loanDataM.getInstallment1() != null) {
                    //individual loan term
                    dataMap.put("011", new Double(loanDataM.getInstallment1().doubleValue()));
                }
            }
        }

        if (vehicleDataM != null) {
            //indivial Car Brand
            dataMap.put("012", vehicleDataM.getBrand());
            //Individual Car Type
            dataMap.put("013", vehicleDataM.getCategory());
            //corporate Car Type
            //Car Age of Car
            /*
             * Calendar clRegister = Calendar.getInstance(); if
             * (vehicleDataM.getRegisterDate() != null) {
             * clRegister.setTime(vehicleDataM.getRegisterDate()); Calendar
             * clNow = Calendar.getInstance(); int carAgeYear =
             * clNow.get(Calendar.YEAR) - clNow.get(Calendar.YEAR); if
             * (clNow.get(Calendar.MONTH) < clNow.get(Calendar.MONTH)) {
             * carAgeYear--; }
             * 
             * dataMap.put("014", new Double(carAgeYear)); }
             */
            Calendar clNow = Calendar.getInstance();
            int carAgeYear = clNow.get(Calendar.YEAR) - vehicleDataM.getYear();
            dataMap.put("014", new Double(carAgeYear));

        }
        // No of Guarantor
        Vector vPersonal = applicationDataM.getPersonalInfoVect();
        dataMap.put("015", new Double(vPersonal.size() - 1));
        PreScoreDataM preScoreDataM = personalInfoDataM.getPreScoreDataM();
        if (preScoreDataM == null) {
            preScoreDataM = new PreScoreDataM();
        }
        //total number of OverDue 60 day in 12 month
        if (preScoreDataM.getOverDue60dayIn12Month() != -1) {
            dataMap.put("016", new Double(preScoreDataM.getOverDue60dayIn12Month()));
        }
        if (preScoreDataM.getMaxOverDue6Month() != -1) {
            dataMap.put("017", new Double(preScoreDataM.getMaxOverDue6Month()));
        }
        // Revolving loan count
        if (preScoreDataM.getNoRevolvingLoan() != -1) {
            dataMap.put("018", new Double(preScoreDataM.getNoRevolvingLoan()));
        }
        // individual no of hire purchase
        if (preScoreDataM.getNoAutoHirePurchase() != -1) {
            dataMap.put("019", new Double(preScoreDataM.getNoAutoHirePurchase()));
        }
        String blFlag = null;
        if (XRulesConstant.ExecutionResultString.RESULT_PASS.equalsIgnoreCase(xRulesVerification.getBLResult())) {
            blFlag = OrigConstant.ORIG_FLAG_N;
        } else if (XRulesConstant.ExecutionResultString.RESULT_FAIL.equalsIgnoreCase(xRulesVerification.getBLResult())) {
            blFlag = OrigConstant.ORIG_FLAG_Y;
        }
        dataMap.put("020", blFlag);
        return dataMap;
    }

    private int getMaximumPayment(Vector vAccountDetail, int month) {
        int maxPayment = 0;
        for (int i = 0; i < vAccountDetail.size(); i++) {
            TLRespM accountRespM = (TLRespM) vAccountDetail.get(i);
            String paymentHistory = accountRespM.getPaymentHist1();
            String accountType = accountRespM.getAccountType();
            String accountFlag = (String) XRulesConstant.hAccountStatusDebtAmt.get(accountRespM.getAccountType());
            if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag)
                    || (XRulesConstant.ACCOUNT_FLAG_C.equalsIgnoreCase(accountFlag) && !(OrigConstant.AccountType.CreditCard.equalsIgnoreCase(accountType)
                            || OrigConstant.AccountType.OverDraft.equalsIgnoreCase(accountType) || OrigConstant.AccountType.Revolving
                            .equalsIgnoreCase(accountType)))) {
                StringBuffer paymentHistoryBuff = new StringBuffer(paymentHistory);
                if (paymentHistoryBuff.length() > (3 * month)) {
                    paymentHistoryBuff = new StringBuffer(paymentHistoryBuff.substring(0, (3 * month)));
                }
                if (OrigConstant.AccountType.OverDraft.equalsIgnoreCase(accountType)) {
                    int payment = 0;

                    for (int j = 0; j < paymentHistoryBuff.length()-3; j = j + 3) {
                        String monthPaymentCode = paymentHistoryBuff.substring(j , j+3);
                        if ("  Y".equalsIgnoreCase(monthPaymentCode)) {
                            payment += 30;
                        } else {
                            payment = 0;
                        }
                        if (payment > maxPayment) {
                            maxPayment = payment;
                        }
                    }
                } else {
                    boolean fistCount = true;
                    int payment = 0;
                    for (int j = 0; j < paymentHistoryBuff.length()-3; j = j + 3) {
                        String monthPaymentCode = paymentHistoryBuff.substring(j , j+3);
                        if (fistCount) {
                            if ("000".equalsIgnoreCase(monthPaymentCode)) {
                                payment = 0;
                                fistCount = false;
                            } else if ("001".equalsIgnoreCase(monthPaymentCode)) {
                                payment += 30;
                            } else if ("002".equalsIgnoreCase(monthPaymentCode)) {
                                payment += 30;
                            } else if ("003".equalsIgnoreCase(monthPaymentCode)) {
                                payment += 30;
                            } else if ("004".equalsIgnoreCase(monthPaymentCode)) {
                                payment += 30;
                            } else if ("005".equalsIgnoreCase(monthPaymentCode)) {
                                payment += 30;
                            } else if ("006".equalsIgnoreCase(monthPaymentCode)) {
                                payment += 30;
                            } else if ("007".equalsIgnoreCase(monthPaymentCode)) {
                                payment += 30;
                            } else if ("008".equalsIgnoreCase(monthPaymentCode)) {
                                payment += 30;
                            } else if ("009".equalsIgnoreCase(monthPaymentCode)) {
                                payment += 30;
                            } else if ("  F".equalsIgnoreCase(monthPaymentCode)) {
                                payment += 30;
                            } else {
                                payment = 0;
                                fistCount = false;
                            }
                        } else {
                            if ("000".equalsIgnoreCase(monthPaymentCode)) {
                                payment = 0;
                            } else if ("001".equalsIgnoreCase(monthPaymentCode)) {
                                payment = 60;
                            } else if ("002".equalsIgnoreCase(monthPaymentCode)) {
                                payment = 90;
                            } else if ("003".equalsIgnoreCase(monthPaymentCode)) {
                                payment = 120;
                            } else if ("004".equalsIgnoreCase(monthPaymentCode)) {
                                payment = 150;
                            } else if ("005".equalsIgnoreCase(monthPaymentCode)) {
                                payment = 180;
                            } else if ("006".equalsIgnoreCase(monthPaymentCode)) {
                                payment = 210;
                            } else if ("007".equalsIgnoreCase(monthPaymentCode)) {
                                payment = 240;
                            } else if ("008".equalsIgnoreCase(monthPaymentCode)) {
                                payment = 270;
                            } else if ("009".equalsIgnoreCase(monthPaymentCode)) {
                                payment = 300;
                            } else if ("  F".equalsIgnoreCase(monthPaymentCode)) {
                                payment = 302;
                            }
                        }
                        if (payment > maxPayment) {
                            maxPayment = payment;
                        }
                    }
                }
            }//if account flag
        }//for accountDetail
        return maxPayment;
    }

    private int getOverDueMorethan60day(Vector vAccountDetail) {
        int overDue60Count = 0;
        for (int i = 0; i < vAccountDetail.size(); i++) {
            TLRespM accountRespM = (TLRespM) vAccountDetail.get(i);
            String paymentHistory = accountRespM.getPaymentHist1();

            String accountType = accountRespM.getAccountType();
            String accountFlag = (String) XRulesConstant.hAccountStatusDebtAmt.get(accountRespM.getAccountType());
            if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag)
                    || (XRulesConstant.ACCOUNT_FLAG_C.equalsIgnoreCase(accountFlag) && !(OrigConstant.AccountType.CreditCard.equalsIgnoreCase(accountType)
                            || OrigConstant.AccountType.OverDraft.equalsIgnoreCase(accountType) || OrigConstant.AccountType.Revolving
                            .equalsIgnoreCase(accountType)))) {

                StringBuffer paymentHistoryBuff = new StringBuffer(paymentHistory);
                if (paymentHistoryBuff.length() > 36) {
                    paymentHistoryBuff = new StringBuffer(paymentHistoryBuff.substring(0, 36));
                }
                if (OrigConstant.AccountType.OverDraft.equalsIgnoreCase(accountType)) {

                    int odOverDueMonthcount = 0;
                    int overDueCount = 0;
                    for (int j = 0; j < paymentHistoryBuff.length()-3; j = j + 3) {
                        String monthPaymentCode = paymentHistoryBuff.substring(j  , j+3);
                        if ("  Y".equalsIgnoreCase(monthPaymentCode)) {
                            odOverDueMonthcount++;
                        } else {
                            odOverDueMonthcount = 0;
                        }
                        if (odOverDueMonthcount >= 3) {
                            overDue60Count++;
                        }
                    }

                } else {//not acccount type od
                    int monthCount = 0;
                    boolean fistCount = true;
                    for (int j = 0; j < paymentHistoryBuff.length()-3; j = j + 3) {
                        String monthPaymentCode = paymentHistoryBuff.substring(j, j+3);
                        if (fistCount) {
                            if ("002".equalsIgnoreCase(monthPaymentCode)) {
                                monthCount++;
                            } else if ("003".equalsIgnoreCase(monthPaymentCode)) {
                                monthCount++;
                            } else if ("004".equalsIgnoreCase(monthPaymentCode)) {
                                monthCount++;
                            } else if ("005".equalsIgnoreCase(monthPaymentCode)) {
                                monthCount++;
                            } else if ("006".equalsIgnoreCase(monthPaymentCode)) {
                                monthCount++;
                            } else if ("007".equalsIgnoreCase(monthPaymentCode)) {
                                monthCount++;
                            } else if ("008".equalsIgnoreCase(monthPaymentCode)) {
                                monthCount++;
                            } else if ("009".equalsIgnoreCase(monthPaymentCode)) {
                                monthCount++;
                            } else if ("  F".equalsIgnoreCase(monthPaymentCode)) {
                                monthCount++;
                            } else {
                                fistCount = false;
                                continue;
                            }
                            if (monthCount >= 3) {
                                overDue60Count++;
                            }
                        } else {//not firstcount
                            if ("002".equalsIgnoreCase(monthPaymentCode)) {
                                overDue60Count++;
                            } else if ("003".equalsIgnoreCase(monthPaymentCode)) {
                                overDue60Count++;
                            } else if ("004".equalsIgnoreCase(monthPaymentCode)) {
                                overDue60Count++;
                            } else if ("005".equalsIgnoreCase(monthPaymentCode)) {
                                overDue60Count++;
                            } else if ("006".equalsIgnoreCase(monthPaymentCode)) {
                                overDue60Count++;
                            } else if ("007".equalsIgnoreCase(monthPaymentCode)) {
                                overDue60Count++;
                            } else if ("008".equalsIgnoreCase(monthPaymentCode)) {
                                overDue60Count++;
                            } else if ("009".equalsIgnoreCase(monthPaymentCode)) {
                                overDue60Count++;
                            } else if ("  F".equalsIgnoreCase(monthPaymentCode)) {
                                overDue60Count++;
                            }
                        }//else
                    }//for

                }//not od
            }//account Flag
        } //for accountDetail
        return overDue60Count;
    }

    public String calcuateApplicationPreScoreing(ApplicationDataM applicationDataM, UserDetailM userM) {
        ORIGUtility utility = new ORIGUtility();

        //get Main Personal Type
        //get CustoerType
        int applicationScore = 0;
        applicationScore = this.calculatePreScoring(applicationDataM);
        String scoringResult = "N/A";
        String customerType = "";
        PersonalInfoDataM personalInfoDataM;
        personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())
                || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equals(personalInfoDataM.getCustomerType())) {
            customerType = OrigConstant.CUSTOMER_TYPE_INDIVIDUAL;
        } else if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(personalInfoDataM.getCustomerType())) {
            customerType = OrigConstant.CUSTOMER_TYPE_CORPORATE;
        }
        ScoringProperties scoring = this.getScoring(customerType);
        if (scoring != null) {

            log.debug("OrigScoringUtility-->caluateApplicationPreScoreing  scoring " + scoring);

            log.debug("OrigScoringUtility-->caluateApplicationPreScoreing  Customer Type " + personalInfoDataM.getCustomerType());
            if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())) {
                log.debug("OrigScoringUtility-->caluateApplicationPreScoreing  Customer Type Individual + score " + scoring.getScoringConstant());
                applicationScore += scoring.getScoringConstant();
            }
            log.debug("OrigScoringUtility-->caluateApplicationPreScoreing  applicationScore " + applicationScore);
            VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
            if (vehicleDataM == null) {
                vehicleDataM = new VehicleDataM();
            }
            if (OrigConstant.CAR_TYPE_USE.equalsIgnoreCase(vehicleDataM.getCar())) {
                log.debug("OrigScoringUtility-->caluateApplicationPreScore Car Type Used");
                log.debug("OrigScoringUtility-->caluateApplicationPreScore  Accept Score Car Used " + scoring.getAcceptScoreUsedCar());
                log.debug("OrigScoringUtility-->caluateApplicationPreScore  Reject Score Car Used " + scoring.getRejectScoreUsedCar());
                if (applicationScore >= scoring.getAcceptScoreUsedCar()) {
                    scoringResult = OrigConstant.Scoring.SCORING_ACCEPT;
                } else if (applicationScore > scoring.getRejectScoreUsedCar()) {
                    scoringResult = OrigConstant.Scoring.SCORING_REFER;
                } else {
                    scoringResult = OrigConstant.Scoring.SCORING_REJECT;
                }
            } else if (OrigConstant.CAR_TYPE_NEW.equalsIgnoreCase(vehicleDataM.getCar())) {
                log.debug("OrigScoringUtility-->caluateApplicationPreScore Car Type New");
                log.debug("OrigScoringUtility-->caluateApplicationPreScore  Accept Score " + scoring.getAcceptScore());
                log.debug("OrigScoringUtility-->caluateApplicationPreScore  Reject Score " + scoring.getRejectScore());
                if (applicationScore >= scoring.getAcceptScore()) {
                    scoringResult = OrigConstant.Scoring.SCORING_ACCEPT;
                } else if (applicationScore > scoring.getRejectScore()) {
                    scoringResult = OrigConstant.Scoring.SCORING_REFER;
                } else {
                    scoringResult = OrigConstant.Scoring.SCORING_REJECT;
                }
            } else {
                log.fatal("Unknow Car Type");
            }

        }
        log.debug("OrigScoringUtility-->caluateApplicationPreScoreing  result " + scoringResult);
        PreScoreDataM preScoreDataM = personalInfoDataM.getPreScoreDataM();
        if (preScoreDataM == null) {
            preScoreDataM = new PreScoreDataM();
            personalInfoDataM.setPreScoreDataM(preScoreDataM);
        }
        preScoreDataM.setResult(scoringResult);
        preScoreDataM.setScore(applicationScore);
        return scoringResult;
    }

    private int calculatePreScoring(ApplicationDataM applicationDataM) {
        //get Scoring Type
        //applicationDataM
        ORIGUtility utility = new ORIGUtility();
        PersonalInfoDataM personalInfoDataM;
        personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        HashMap mapData = this.mapPreScoreData(applicationDataM);
        //BigDecimal totalScore = new BigDecimal(0);
        int totalScore = 0;
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        String customerType = "";
        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())
                || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equals(personalInfoDataM.getCustomerType())) {
            customerType = OrigConstant.CUSTOMER_TYPE_INDIVIDUAL;
        } else if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(personalInfoDataM.getCustomerType())) {
            customerType = OrigConstant.CUSTOMER_TYPE_CORPORATE;
        }
        Vector vScoringType = this.getScoringTypeByCustomerType(customerType);

        int scoring = 0;
        for (int i = 0; i < vScoringType.size(); i++) {
            ScoringTypeProperties scoringType = (ScoringTypeProperties) vScoringType.get(i);
            if (scoringType != null) {
                Vector vScoringCharecteristic = this.getScoringCharecteristicByCustomerType(scoringType.getScorignCode());
                scoring = this.calculateCharacteristicScoreing(mapData, vScoringCharecteristic);
              //  log.debug("Score  of  " + scoringType.getScorignCode() + "  = " + scoring);
            }
            totalScore += scoring;
        }
        //log.debug("Total Score " + totalScore);
        return totalScore;//totalScore.intValueExact();//totalScore.multiply(new
        // BigDecimal(100)).intValue();
    }

    private HashMap mapPreScoreData(ApplicationDataM applicationDataM) {
        //map application to charcode;
        ORIGUtility utility = new ORIGUtility();
        PersonalInfoDataM personalInfoDataM;
        personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        HashMap dataMap = new HashMap();
        if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
            dataMap = this.mapPreScoreDataIndividual(applicationDataM);
        } else if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
            dataMap = this.mapPreScoreDataCorporate(applicationDataM);
        } else if (OrigConstant.CUSTOMER_TYPE_FOREIGNER.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
            //dataMap =this.mapPreScoreDataIndividual(applicationDataM);
            dataMap=this.mapPreScoreDataForeigner(applicationDataM);
        }

        return dataMap;
    }

    private HashMap mapPreScoreDataIndividual(ApplicationDataM applicationDataM) {
        ORIGUtility utility = new ORIGUtility();
        PersonalInfoDataM personalInfoDataM;
        personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        HashMap dataMap = new HashMap();
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xRulesVerification = personalInfoDataM.getXrulesVerification();
        if (xRulesVerification == null) {
            xRulesVerification = new XRulesVerificationResultDataM();
        }
        PreScoreDataM preScoreDataM = personalInfoDataM.getPreScoreDataM();
        if (preScoreDataM == null) {
            preScoreDataM = new PreScoreDataM();
        }
        log.debug(" personalInfoDataM " + personalInfoDataM);
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        AddressDataM addrHome = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        OrigXRulesUtil origXrulesUtil=OrigXRulesUtil.getInstance();
        //XRulesdxRulesVerification.getXrulesDebtBdDataM();
        //map Data
        log.debug(" Age " + origXrulesUtil.getAge(personalInfoDataM.getBirthDate()));
        //age
        dataMap.put("001", new Double(origXrulesUtil.getAge(personalInfoDataM.getBirthDate())));
        //material status
        dataMap.put("002", personalInfoDataM.getMaritalStatus());
        //Education level
        dataMap.put("003", preScoreDataM.getQualification());
        //Sex
        dataMap.put("004", personalInfoDataM.getGender());
        //working year
        dataMap.put("005", new Double(preScoreDataM.getTotalWorkYear()));
        //Occupation
        dataMap.put("006", preScoreDataM.getOccupation());
        //Debt Burden
        if (xRulesVerification.getDEBT_BDScore() != null) {
            dataMap.put("007", new Double(xRulesVerification.getDEBT_BDScore().intValue()));
        }
        //Accomodation Status
        dataMap.put("008", preScoreDataM.getHouseRegistStatus());
        //Time in Current Address
        dataMap.put("009", new Double(preScoreDataM.getTimeInCurrentAddressYear()));

        //***************************************
        //individual Down Payment
        if (preScoreDataM.getDownPayment() != null && preScoreDataM.getCarPrice() != null) {
            BigDecimal percentOfDownPayment = null;
            try {
                percentOfDownPayment = utility.calculatePercentDownPayment(preScoreDataM.getCarPrice(), preScoreDataM.getDownPayment());
            } catch (RuntimeException e1) {
                log.error("Down payment", e1);
            }
            if (percentOfDownPayment != null) {
                dataMap.put("010", new Double(percentOfDownPayment.doubleValue()));
            }
        }
        //individal Term Loan
        //individual loan term
        dataMap.put("011", new Double(preScoreDataM.getTermLoan()));
        //corporate Request Term of loan
        //******************************************8
        VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
        if (vehicleDataM != null) {
            //indivial Car Brand
            dataMap.put("012", vehicleDataM.getBrand());
            //Individual Car Type
            dataMap.put("013", vehicleDataM.getCategory());
            //Car Age of Car
            Calendar clRegister = Calendar.getInstance();
            Calendar clNow = Calendar.getInstance();
            int carAgeYear = clNow.get(Calendar.YEAR) - vehicleDataM.getYear();
            dataMap.put("014", new Double(carAgeYear));
        }
        // No of Guarantor
        //Vector vPersonal = applicationDataM.getPersonalInfoVect();
        dataMap.put("015", new Double(preScoreDataM.getNoOfGuarantor()));
        boolean ncbData = false;
        int totalNumberOfOverdue60day = 0;
        int maximumNumberOfOverdue = 0;
        String tracingCode = xRulesVerification.getNCBTrackingCode();
        Vector vXRulesNCBDataM = xRulesVerification.getVXRulesNCBDataM();
        log.debug("tracingCode " + tracingCode);
        log.debug("tracingCode " + tracingCode);
        if (OrigConstant.NCBcolor.BLACK.equalsIgnoreCase(xRulesVerification.getNCBColor())) {
            log.debug("NCB No result");
            dataMap.put("016", null);
            dataMap.put("017", null);
            dataMap.put("019", null);
            dataMap.put("018", null);
        } else {
            Vector vAccountDetail = null;
            int hirePurchaseCount = 0;
            double percentOfRevolving = 0;
            try {
                if ((tracingCode != null && !"".equals(tracingCode) && vXRulesNCBDataM != null)) {

                    for (int i = 0; i < vXRulesNCBDataM.size(); i++) {
                        XRulesNCBDataM xRulesNCBDataM = (XRulesNCBDataM) vXRulesNCBDataM.get(i);
                        String accountType = xRulesNCBDataM.getLoanType();
                        int groupSeq=xRulesNCBDataM.getGroupSeq();
//                        NCBServiceManager ncbService = XRulesEJBFactory.getNCBServiceManager();
//                        vAccountDetail = ncbService.getNCBAccountDetail(tracingCode, accountType,groupSeq);

                        if (accountType.equalsIgnoreCase(OrigConstant.AccountType.AUTOMOBILE_HIRE_PURCHASE)
                                || accountType.equalsIgnoreCase(OrigConstant.AccountType.OTHER_HIRE_PURCHASE)) {
                            if (vAccountDetail != null) {
                                for (int j = 0; j < vAccountDetail.size(); j++) {
                                    TLRespM account = (TLRespM) vAccountDetail.get(j);
                                    String accountStatus = (String) XRulesConstant.hAccountStatusDebtAmt.get(account.getAccountStatus());
                                    if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountStatus)) {
                                        hirePurchaseCount++;
                                    }
                                }
                            }

                        } else if (accountType.equalsIgnoreCase(OrigConstant.AccountType.Revolving)) {
                            if (vAccountDetail != null) {
                                percentOfRevolving = OrigXRulesUtil.getInstance().getPercentOfRevolving(vAccountDetail);

                            }

                        }

                        int maxPayment = this.getMaximumPayment(vAccountDetail, 6);
                        if (maxPayment > maximumNumberOfOverdue) {
                            maximumNumberOfOverdue = maxPayment;
                        }
                        log.debug("maximumNumberOfOverdue " + maximumNumberOfOverdue);
                        totalNumberOfOverdue60day += this.getOverDueMorethan60day(vAccountDetail);
                        log.debug("totalNumberOfOverdue60day " + totalNumberOfOverdue60day);
                    }
                }
                //Set Data to Session
            } catch (Exception e) {
                e.printStackTrace();
                log.fatal(e.getLocalizedMessage());
            }
            dataMap.put("016", new Double(totalNumberOfOverdue60day));
            dataMap.put("017", new Double(maximumNumberOfOverdue));
            // Utilization of Revolving
            dataMap.put("018", new Double(percentOfRevolving));
            //individual no of hire purchase
            dataMap.put("019", new Double(hirePurchaseCount));
        }
        String blFlag = null;
        if (XRulesConstant.ExecutionResultString.RESULT_PASS.equalsIgnoreCase(xRulesVerification.getBLResult())) {
            blFlag = OrigConstant.ORIG_FLAG_N;
        } else if (XRulesConstant.ExecutionResultString.RESULT_FAIL.equalsIgnoreCase(xRulesVerification.getBLResult())) {
            blFlag = OrigConstant.ORIG_FLAG_Y;
        }
        dataMap.put("020", blFlag);
        return dataMap;
    }

    private HashMap mapPreScoreDataCorporate(ApplicationDataM applicationDataM) {
        ORIGUtility utility = new ORIGUtility();
        PersonalInfoDataM personalInfoDataM;
        personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        HashMap dataMap = new HashMap();
        XRulesVerificationResultDataM xRulesVerification = personalInfoDataM.getXrulesVerification();
        if (xRulesVerification == null) {
            xRulesVerification = new XRulesVerificationResultDataM();
        }
        PreScoreDataM preScoreDataM = personalInfoDataM.getPreScoreDataM();
        if (preScoreDataM == null) {
            preScoreDataM = new PreScoreDataM();
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        OrigXRulesUtil origXrulesUtil=OrigXRulesUtil.getInstance();
        AddressDataM addrHome = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        //map Data
        //business age
        dataMap.put("021", new Double(origXrulesUtil.getAge(personalInfoDataM.getBirthDate())));
        //get
        if (preScoreDataM.getShareCapital() != null) {
            dataMap.put("022", new Double(preScoreDataM.getShareCapital().intValue()));
        }
        //Land Of Owner Ship
        dataMap.put("023", preScoreDataM.getLandOwnerShip());
        //Cheque Returned
        dataMap.put("024", new Double(personalInfoDataM.getChequeReturn()));
        // Number of Employee
        dataMap.put("025", new Double(preScoreDataM.getNoOfEmployee()));
        //DSCR deburdent of corporate
        if (xRulesVerification.getDEBT_BDScore() != null) {
            dataMap.put("026", new Double(xRulesVerification.getDEBT_BDScore().doubleValue()));
        }
        //***************************************
        //corporate down payment
        if (preScoreDataM.getDownPayment() != null && preScoreDataM.getCarPrice() != null) {

            BigDecimal percentOfDownPayment = null;
            try {
                percentOfDownPayment = utility.calculatePercentDownPayment(preScoreDataM.getCarPrice(), preScoreDataM.getDownPayment());
            } catch (RuntimeException e1) {
                log.error("Down payment", e1);
            }
            if (percentOfDownPayment != null) {
                dataMap.put("027", new Double(percentOfDownPayment.doubleValue()));
            }

        }
        //corporate Request Term of loan
        dataMap.put("028", new Double(preScoreDataM.getTermLoan()));
        //******************************************

        VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
        if (vehicleDataM != null) {
            //corporate Car Brand
            dataMap.put("029", vehicleDataM.getBrand());
            //corporate Car Type
            dataMap.put("030", vehicleDataM.getCategory());
            //Car Age of Car
            Calendar clRegister = Calendar.getInstance();
            Calendar clNow = Calendar.getInstance();
            int carAgeYear = clNow.get(Calendar.YEAR) - vehicleDataM.getYear();
            dataMap.put("031", new Double(carAgeYear));
        }
        // Maximum overdue days within 6 months ***
        if (preScoreDataM.getOverDue60dayIn12Month() != -1) {
            dataMap.put("032", new Double(preScoreDataM.getOverDue60dayIn12Month()));
        }
        //Maximum overdue days within 6 months ***
        if (preScoreDataM.getMaxOverDue6Month() != -1) {
            dataMap.put("033", new Double(preScoreDataM.getMaxOverDue6Month()));
        }
        //Utilization of Revolving
        if (preScoreDataM.getNoRevolvingLoan() != -1) {
            dataMap.put("034", new Double(preScoreDataM.getNoRevolvingLoan()));
        }
        //NO of Hire Purchase
        if (preScoreDataM.getNoAutoHirePurchase() != -1) {
            dataMap.put("035", new Double(preScoreDataM.getNoAutoHirePurchase()));
        }
        //NPL/Civil Suit History/Restructure
        dataMap.put("036", preScoreDataM.getNPL());
        return dataMap;
    }

    private HashMap mapPreScoreDataForeigner(ApplicationDataM applicationDataM) {
        ORIGUtility utility = new ORIGUtility();
        PersonalInfoDataM personalInfoDataM;
        personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        HashMap dataMap = new HashMap();
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM xRulesVerification = personalInfoDataM.getXrulesVerification();
        if (xRulesVerification == null) {
            xRulesVerification = new XRulesVerificationResultDataM();
        }
        PreScoreDataM preScoreDataM = personalInfoDataM.getPreScoreDataM();
        if (preScoreDataM == null) {
            preScoreDataM = new PreScoreDataM();
        }
        log.debug(" personalInfoDataM " + personalInfoDataM);
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        AddressDataM addrHome = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
        OrigXRulesUtil origXrulesUtil=OrigXRulesUtil.getInstance();
        //map Data
        log.debug(" Age " +origXrulesUtil.getAge( personalInfoDataM.getBirthDate()));
        //age
        dataMap.put("001", new Double(origXrulesUtil.getAge(personalInfoDataM.getBirthDate())));
        //material status
        dataMap.put("002", personalInfoDataM.getMaritalStatus());
        //Education level
        dataMap.put("003", preScoreDataM.getQualification());
        //Sex
        dataMap.put("004", personalInfoDataM.getGender());
        //working year
        dataMap.put("005", new Double(preScoreDataM.getTotalWorkYear()));
        //Occupation
        dataMap.put("006", preScoreDataM.getOccupation());
        //Debt Burden
        if (xRulesVerification.getDEBT_BDScore() != null) {
            dataMap.put("007", new Double(xRulesVerification.getDEBT_BDScore().intValue()));
        }
        //Accomodation Status
        dataMap.put("008", preScoreDataM.getHouseRegistStatus());
        //Time in Current Address
        dataMap.put("009", new Double(preScoreDataM.getTimeInCurrentAddressYear()));

        //***************************************
        //individual Down Payment
        if (preScoreDataM.getDownPayment() != null && preScoreDataM.getCarPrice() != null) {

            BigDecimal percentOfDownPayment = null;
            try {
                percentOfDownPayment = utility.calculatePercentDownPayment(preScoreDataM.getCarPrice(), preScoreDataM.getDownPayment());
            } catch (RuntimeException e1) {
                log.error("Down payment", e1);
            }
            if (percentOfDownPayment != null) {
                dataMap.put("010", new Double(percentOfDownPayment.doubleValue()));
            }
        }
        //individual loan term
        dataMap.put("011", new Double(preScoreDataM.getTermLoan()));
        //corporate Request Term of loan
        //******************************************8
        VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
        if (vehicleDataM != null) {
            //indivial Car Brand
            dataMap.put("012", vehicleDataM.getBrand());
            //Individual Car Type
            dataMap.put("013", vehicleDataM.getCategory());
            //Car Age of Car
            Calendar clRegister = Calendar.getInstance();
            Calendar clNow = Calendar.getInstance();
            int carAgeYear = clNow.get(Calendar.YEAR) - vehicleDataM.getYear();
            dataMap.put("014", new Double(carAgeYear));
        }
        //Vector vPersonal = applicationDataM.getPersonalInfoVect();
        dataMap.put("015", new Double(preScoreDataM.getNoOfGuarantor()));
        //total number of OverDue 60 day in 12 month
        if (preScoreDataM.getOverDue60dayIn12Month() != -1) {
            dataMap.put("016", new Double(preScoreDataM.getOverDue60dayIn12Month()));
        }
        if (preScoreDataM.getMaxOverDue6Month() != -1) {
            dataMap.put("017", new Double(preScoreDataM.getMaxOverDue6Month()));
        }
        // Revolving loan count
        if (preScoreDataM.getNoRevolvingLoan() != -1) {
            dataMap.put("018", new Double(preScoreDataM.getNoRevolvingLoan()));
        }
        // individual no of hire purchase
        if (preScoreDataM.getNoAutoHirePurchase() != -1) {
            dataMap.put("019", new Double(preScoreDataM.getNoAutoHirePurchase()));
        }
        String blFlag = null;
        if (XRulesConstant.ExecutionResultString.RESULT_PASS.equalsIgnoreCase(xRulesVerification.getBLResult())) {
            blFlag = OrigConstant.ORIG_FLAG_N;
        } else if (XRulesConstant.ExecutionResultString.RESULT_FAIL.equalsIgnoreCase(xRulesVerification.getBLResult())) {
            blFlag = OrigConstant.ORIG_FLAG_Y;
        }
        dataMap.put("020", blFlag);
        return dataMap;
    }

    public static double getShareholderEquity(Vector corporateDataMs) {
        double shareholderEnquity = 0;
        ORIGUtility utility = ORIGUtility.getInstance();
        if (corporateDataMs != null && corporateDataMs.size() > 0) {
            CorperatedDataM maxYearCoporateDataM = (CorperatedDataM) corporateDataMs.get(0);
            for (int i = 1; i < corporateDataMs.size(); i++) {
                int maxCorpYear = utility.stringToInt(maxYearCoporateDataM.getYear());
                CorperatedDataM corportateDataM = (CorperatedDataM) corporateDataMs.get(i);
                int corpYear = utility.stringToInt(corportateDataM.getYear());
                if (corpYear > maxCorpYear) {
                    maxYearCoporateDataM = corportateDataM;
                }
            }
            shareholderEnquity = maxYearCoporateDataM.getShdEqShareCapital().doubleValue();
        }
        return shareholderEnquity;
    }

}
