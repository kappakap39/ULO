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
/**
import java.math.BigDecimal;
import java.util.Vector;

import org.apache.log4j.Logger;

import ScoringFactorsRuleApp.Customer;
import ScoringFactorsRuleApp.ScoringFactorsWSRunnerProxy;
import ScoringFactorsRuleApp.holders.CustomerHolder;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.ncb.model.output.TLRespM;
import com.eaf.ncb.service.ejb.NCBServiceManager;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.OrigXRulesUtil;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.factory.XRulesEJBFactory;
import com.eaf.xrules.shared.model.XRulesNCBDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;
*/
/**
 * @author Sankom
 *
 * Type: CalculateScoringWebAction
 * @deprecated 
 * @author septemwi
 * @see CalculateILOGScoringWebAction
 * undefined
 */
@Deprecated
public class CalculateILOGScoringWebAction {
/**extends WebActionHelper implements
        WebAction {
    private static Logger log= (Logger) Logger
    .getLogger(CalculateILOGScoringWebAction.class);
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
//    public Event toEvent() {
//         
//        return null;
//    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
//    public boolean requiredModelRequest() {
//         
//        return false;
//    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
//    public boolean processEventResponse(EventResponse response) {
//         
//        return false;
//    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
//    public boolean preModelRequest() {
// 
//        
//        
//        return true;
//    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
//    public int getNextActivityType() {
//    		log.debug("CalculateScoringWebAction-->preModelRequest");         
//        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
//		UserDetailM ORIGUser = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
//        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
//    	if(applicationDataM == null){
//    		applicationDataM = new ApplicationDataM();
//    	}
//    	ORIGUtility utility = new ORIGUtility();
//    	String personalType =getRequest().getParameter("appPersonalType");// (String) getRequest().getSession().getAttribute("PersonalType");
//    	PersonalInfoDataM personalInfoDataM;
//    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
//    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA");
//    	}else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
//    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
//		}else{
//    		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
//    	}
//    	if(personalInfoDataM == null){
//    		personalInfoDataM = new PersonalInfoDataM();
//    		//personalInfoDataM.setPersonalType(personalType);
//    		//applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
//    	}
//    	LoanDataM loanDataM=(LoanDataM)applicationDataM.getLoanVect().get(0);
//    	XRulesVerificationResultDataM xRulesVer=personalInfoDataM.getXrulesVerification();
//        VehicleDataM  origVehicle=applicationDataM.getVehicleDataM();
//        AddressDataM addrHome = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
//    	//getRequest().getSession().setAttribute("scoringType",OrigConstant.Scoring.SCORING_TYPE_APPSCORE);
//    	//OrigScoringUtility origScoring=new OrigScoringUtility();
//    	//String scoringResult=origScoring.calcuateApplicationScoreing(applicationDataM,ORIGUser);    
//    	//applicationDataM.setScorringResult(scoringResult);
//    	//applicationDataM.setIsReExcuteAppScoreFlag(false);
//    	//======================================================
//    	ScoringFactorsWSRunnerProxy scoring=new ScoringFactorsWSRunnerProxy();
//    	Customer customer = new Customer();
//		customer.setAge(personalInfoDataM.getAge());  //
//		customer.setMaritalStatus("Married");
//		
//		customer.setEducationLevel("Bachelor");
//		
//		customer.setSex("Male");
//		
//		customer.setWorkingYear(personalInfoDataM.getYearOfWork()); 
//		customer.setOccupation("Employee");		
//		
//		//get Percendebt	 
//		if(xRulesVer.getDEBT_BDScore()!=null){
//		customer.setDebtBurden(xRulesVer.getDEBT_BDScore().intValue());
//		}else{
//			customer.setDebtBurden(0);
//		}
//		
//		customer.setAccomodationStatus("Owner");
//		//
//		customer.setTimeInCurrentAddress(addrHome.getResideYear().intValue());	
//		 BigDecimal percentOfDownPayment = null;
//         if (origVehicle != null && OrigConstant.CAR_TYPE_USE.equalsIgnoreCase(origVehicle.getCar())) {
//             try { 
//                 percentOfDownPayment = utility.calculatePercentDownPayment(loanDataM.getCostOfCarPrice(), loanDataM.getCostOfDownPayment());
//             } catch (Exception e1) {
//                 log.debug("Error cal down payment", e1);
//             }
//
//         } else if (origVehicle != null && OrigConstant.CAR_TYPE_NEW.equalsIgnoreCase(origVehicle.getCar())) {
//             try {
//                 percentOfDownPayment = utility.calculatePercentDownPayment(loanDataM.getTotalOfCarPrice(), loanDataM.getTotalOfDownPayment());
//             } catch (Exception e1) {
//                 log.debug("Error cal down payment", e1);
//             }
//         }
//		customer.setDownPayment(percentOfDownPayment.intValue());
//		customer.setTermLoan(loanDataM.getInstallment1().intValue());
//		//String carBrandDesc=origVehicle.getBrand();//Get Desc
//		customer.setBrandOfCar("BMW");
//		//get category
//		customer.setCategoryOfCar("Passenger Car");
//		//		
//		customer.setAgeOfCar(origVehicle.getYear());
//		Vector vPersonal = applicationDataM.getPersonalInfoVect();
//		customer.setNumberOfGuarantor(vPersonal.size()-1);		
//		 boolean ncbData = false;
//	        int totalNumberOfOverdue60day = 0;
//	        int maximumNumberOfOverdue = 0;
//	        String tracingCode = xRulesVer.getNCBTrackingCode();
//	        Vector vXRulesNCBDataM = xRulesVer.getVXRulesNCBDataM();
//	        log.debug("tracingCode " + tracingCode);
//	        if (OrigConstant.NCBcolor.BLACK.equalsIgnoreCase(xRulesVer.getNCBColor())) {
//	            log.debug("NCB No result");	             
//	        } else {
//	            Vector vAccountDetail = null;
//	            int hirePurchaseCount = 0;
//	            double percentOfRevolving = 0;
//	            try {
//	                if ((tracingCode != null && !"".equals(tracingCode) && vXRulesNCBDataM != null)) {
//
//	                    for (int i = 0; i < vXRulesNCBDataM.size(); i++) {
//	                        XRulesNCBDataM xRulesNCBDataM = (XRulesNCBDataM) vXRulesNCBDataM.get(i);
//	                        String accountType = xRulesNCBDataM.getLoanType();
//	                        int groupSeq=xRulesNCBDataM.getGroupSeq();
//	                        NCBServiceManager ncbService = XRulesEJBFactory.getNCBServiceManager();
//	                        vAccountDetail = ncbService.getNCBAccountDetail(tracingCode, accountType,groupSeq);
//
//	                        if (accountType.equalsIgnoreCase(OrigConstant.AccountType.AUTOMOBILE_HIRE_PURCHASE)
//	                                || accountType.equalsIgnoreCase(OrigConstant.AccountType.OTHER_HIRE_PURCHASE)) {
//	                            if (vAccountDetail != null) {
//	                                for (int j = 0; j < vAccountDetail.size(); j++) {
//	                                    TLRespM account = (TLRespM) vAccountDetail.get(j);
//	                                    String accountStatus = (String) XRulesConstant.hAccountStatusDebtAmt.get(account.getAccountStatus());
//	                                    if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountStatus)) {
//	                                        hirePurchaseCount++;
//	                                    }
//	                                }
//	                            }
//
//	                        } else if (accountType.equalsIgnoreCase(OrigConstant.AccountType.Revolving)) {
//	                            if (vAccountDetail != null) {
//	                                percentOfRevolving = OrigXRulesUtil.getInstance().getPercentOfRevolving(vAccountDetail);
//
//	                            }
//
//	                        }
//
//	                        int maxPayment = this.getMaximumPayment(vAccountDetail, 6);
//	                        if (maxPayment > maximumNumberOfOverdue) {
//	                            maximumNumberOfOverdue = maxPayment;
//	                        }
//	                        log.debug("maximumNumberOfOverdue " + maximumNumberOfOverdue);
//	                        totalNumberOfOverdue60day += this.getOverDueMorethan60day(vAccountDetail);
//	                        log.debug("totalNumberOfOverdue60day " + totalNumberOfOverdue60day);
//	                    }
//	                }
//	                //Set Data to Session
//	            } catch (Exception e) {
//	                e.printStackTrace();
//	                log.fatal(e.getLocalizedMessage());
//	            }
//	        	customer.setNumberOfOverduePaymentsWithin12Months(totalNumberOfOverdue60day);
//	    		customer.setDurationOfOverduePayment(maximumNumberOfOverdue);
//	    		customer.setUtilizationOfRevolving( (float)percentOfRevolving);
//	    		customer.setTotalNumberOfHirePurchase(hirePurchaseCount);	            	            
//	        } 			
//		customer.setNplHistory("No");
//		CustomerHolder customerh = new CustomerHolder(customer);
//		try {
//			String result=scoring.scoringFactorsRuleProject( customerh);
//			Customer outputCustomer = customerh.value;
//			int score=outputCustomer.getScoreValue();    	
//			String scoringResult=outputCustomer.getScoreResult();
//			//=====================================================
//			log.debug("ILOG Score="+score);
//			log.debug("ILOG Result=");
//			getRequest().getSession().setAttribute("scoringILOGResult",scoringResult);
//		} catch ( Exception e) {
//			 log.error("Error:",e);
//		}
//    	//ApplicationDataM applicationDataMClone=(ApplicationDataM)SerializeUtil.clone(applicationDataM);
//    	//getRequest().getSession(true).setAttribute("applicationClone",applicationDataMClone);    	
//        return FrontController.PAGE;
//    }
//	        private int getMaximumPayment(Vector vAccountDetail, int month) {
//	            int maxPayment = 0;
//	            for (int i = 0; i < vAccountDetail.size(); i++) {
//	                TLRespM accountRespM = (TLRespM) vAccountDetail.get(i);
//	                String paymentHistory = accountRespM.getPaymentHist1();
//	                String accountType = accountRespM.getAccountType();
//	                String accountFlag = (String) XRulesConstant.hAccountStatusDebtAmt.get(accountRespM.getAccountType());
//	                if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag)
//	                        || (XRulesConstant.ACCOUNT_FLAG_C.equalsIgnoreCase(accountFlag) && !(OrigConstant.AccountType.CreditCard.equalsIgnoreCase(accountType)
//	                                || OrigConstant.AccountType.OverDraft.equalsIgnoreCase(accountType) || OrigConstant.AccountType.Revolving
//	                                .equalsIgnoreCase(accountType)))) {
//	                    StringBuffer paymentHistoryBuff = new StringBuffer(paymentHistory);
//	                    if (paymentHistoryBuff.length() > (3 * month)) {
//	                        paymentHistoryBuff = new StringBuffer(paymentHistoryBuff.substring(0, (3 * month)));
//	                    }
//	                    if (OrigConstant.AccountType.OverDraft.equalsIgnoreCase(accountType)) {
//	                        int payment = 0;
//
//	                        for (int j = 0; j < paymentHistoryBuff.length()-3; j = j + 3) {
//	                            String monthPaymentCode = paymentHistoryBuff.substring(j , j+3);
//	                            if ("  Y".equalsIgnoreCase(monthPaymentCode)) {
//	                                payment += 30;
//	                            } else {
//	                                payment = 0;
//	                            }
//	                            if (payment > maxPayment) {
//	                                maxPayment = payment;
//	                            }
//	                        }
//	                    } else {
//	                        boolean fistCount = true;
//	                        int payment = 0;
//	                        for (int j = 0; j < paymentHistoryBuff.length()-3; j = j + 3) {
//	                            String monthPaymentCode = paymentHistoryBuff.substring(j , j+3);
//	                            if (fistCount) {
//	                                if ("000".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment = 0;
//	                                    fistCount = false;
//	                                } else if ("001".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment += 30;
//	                                } else if ("002".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment += 30;
//	                                } else if ("003".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment += 30;
//	                                } else if ("004".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment += 30;
//	                                } else if ("005".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment += 30;
//	                                } else if ("006".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment += 30;
//	                                } else if ("007".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment += 30;
//	                                } else if ("008".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment += 30;
//	                                } else if ("009".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment += 30;
//	                                } else if ("  F".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment += 30;
//	                                } else {
//	                                    payment = 0;
//	                                    fistCount = false;
//	                                }
//	                            } else {
//	                                if ("000".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment = 0;
//	                                } else if ("001".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment = 60;
//	                                } else if ("002".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment = 90;
//	                                } else if ("003".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment = 120;
//	                                } else if ("004".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment = 150;
//	                                } else if ("005".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment = 180;
//	                                } else if ("006".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment = 210;
//	                                } else if ("007".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment = 240;
//	                                } else if ("008".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment = 270;
//	                                } else if ("009".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment = 300;
//	                                } else if ("  F".equalsIgnoreCase(monthPaymentCode)) {
//	                                    payment = 302;
//	                                }
//	                            }
//	                            if (payment > maxPayment) {
//	                                maxPayment = payment;
//	                            }
//	                        }
//	                    }
//	                }//if account flag
//	            }//for accountDetail
//	            return maxPayment;
//	        }
//	        private int getOverDueMorethan60day(Vector vAccountDetail) {
//	            int overDue60Count = 0;
//	            for (int i = 0; i < vAccountDetail.size(); i++) {
//	                TLRespM accountRespM = (TLRespM) vAccountDetail.get(i);
//	                String paymentHistory = accountRespM.getPaymentHist1();
//
//	                String accountType = accountRespM.getAccountType();
//	                String accountFlag = (String) XRulesConstant.hAccountStatusDebtAmt.get(accountRespM.getAccountType());
//	                if (XRulesConstant.ACCOUNT_FLAG_Y.equalsIgnoreCase(accountFlag)
//	                        || (XRulesConstant.ACCOUNT_FLAG_C.equalsIgnoreCase(accountFlag) && !(OrigConstant.AccountType.CreditCard.equalsIgnoreCase(accountType)
//	                                || OrigConstant.AccountType.OverDraft.equalsIgnoreCase(accountType) || OrigConstant.AccountType.Revolving
//	                                .equalsIgnoreCase(accountType)))) {
//
//	                    StringBuffer paymentHistoryBuff = new StringBuffer(paymentHistory);
//	                    if (paymentHistoryBuff.length() > 36) {
//	                        paymentHistoryBuff = new StringBuffer(paymentHistoryBuff.substring(0, 36));
//	                    }
//	                    if (OrigConstant.AccountType.OverDraft.equalsIgnoreCase(accountType)) {
//
//	                        int odOverDueMonthcount = 0;
//	                        int overDueCount = 0;
//	                        for (int j = 0; j < paymentHistoryBuff.length()-3; j = j + 3) {
//	                            String monthPaymentCode = paymentHistoryBuff.substring(j  , j+3);
//	                            if ("  Y".equalsIgnoreCase(monthPaymentCode)) {
//	                                odOverDueMonthcount++;
//	                            } else {
//	                                odOverDueMonthcount = 0;
//	                            }
//	                            if (odOverDueMonthcount >= 3) {
//	                                overDue60Count++;
//	                            }
//	                        }
//
//	                    } else {//not acccount type od
//	                        int monthCount = 0;
//	                        boolean fistCount = true;
//	                        for (int j = 0; j < paymentHistoryBuff.length()-3; j = j + 3) {
//	                            String monthPaymentCode = paymentHistoryBuff.substring(j, j+3);
//	                            if (fistCount) {
//	                                if ("002".equalsIgnoreCase(monthPaymentCode)) {
//	                                    monthCount++;
//	                                } else if ("003".equalsIgnoreCase(monthPaymentCode)) {
//	                                    monthCount++;
//	                                } else if ("004".equalsIgnoreCase(monthPaymentCode)) {
//	                                    monthCount++;
//	                                } else if ("005".equalsIgnoreCase(monthPaymentCode)) {
//	                                    monthCount++;
//	                                } else if ("006".equalsIgnoreCase(monthPaymentCode)) {
//	                                    monthCount++;
//	                                } else if ("007".equalsIgnoreCase(monthPaymentCode)) {
//	                                    monthCount++;
//	                                } else if ("008".equalsIgnoreCase(monthPaymentCode)) {
//	                                    monthCount++;
//	                                } else if ("009".equalsIgnoreCase(monthPaymentCode)) {
//	                                    monthCount++;
//	                                } else if ("  F".equalsIgnoreCase(monthPaymentCode)) {
//	                                    monthCount++;
//	                                } else {
//	                                    fistCount = false;
//	                                    continue;
//	                                }
//	                                if (monthCount >= 3) {
//	                                    overDue60Count++;
//	                                }
//	                            } else {//not firstcount
//	                                if ("002".equalsIgnoreCase(monthPaymentCode)) {
//	                                    overDue60Count++;
//	                                } else if ("003".equalsIgnoreCase(monthPaymentCode)) {
//	                                    overDue60Count++;
//	                                } else if ("004".equalsIgnoreCase(monthPaymentCode)) {
//	                                    overDue60Count++;
//	                                } else if ("005".equalsIgnoreCase(monthPaymentCode)) {
//	                                    overDue60Count++;
//	                                } else if ("006".equalsIgnoreCase(monthPaymentCode)) {
//	                                    overDue60Count++;
//	                                } else if ("007".equalsIgnoreCase(monthPaymentCode)) {
//	                                    overDue60Count++;
//	                                } else if ("008".equalsIgnoreCase(monthPaymentCode)) {
//	                                    overDue60Count++;
//	                                } else if ("009".equalsIgnoreCase(monthPaymentCode)) {
//	                                    overDue60Count++;
//	                                } else if ("  F".equalsIgnoreCase(monthPaymentCode)) {
//	                                    overDue60Count++;
//	                                }
//	                            }//else
//	                        }//for
//
//	                    }//not od
//	                }//account Flag
//	            } //for accountDetail
//	            return overDue60Count;
//	        }
//} 
}