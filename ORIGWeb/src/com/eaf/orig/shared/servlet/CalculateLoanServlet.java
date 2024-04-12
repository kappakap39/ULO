/*
 * Created on Oct 30, 2007
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
package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.utility.CalculateLoanUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Weeraya
 * 
 * Type: CalculateLoanServlet
 */
public class CalculateLoanServlet extends HttpServlet implements Servlet {
    Logger logger = Logger.getLogger(CalculateLoanServlet.class);

    public CalculateLoanServlet() {
        super();
    }

    /*
     * (non-Java-doc)
     * 
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0,
     *      HttpServletResponse arg1)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ORIGUtility utility = new ORIGUtility();
        try {
            ORIGCacheUtil cacheUtil = new ORIGCacheUtil();

            String vatStr = request.getParameter("vat");
            String have_vat;
            BigDecimal vat = cacheUtil.getVatByCode(vatStr);
            if (vat.compareTo(new BigDecimal(0)) == 0) {
                vat = utility.stringToBigDecimal(cacheUtil.getORIGMasterDisplayNameDataM("VATRate", OrigConstant.HAVE_VAT));
                have_vat = "N";
            } else {
                have_vat = "Y";
            }
            logger.debug("vat" + vat);
            logger.debug("vatStr" + vatStr);
            BigDecimal vatTmp = (vat.divide((new BigDecimal(100)), 2, 0)).add(new BigDecimal(1));
            BigDecimal monthInyear = new BigDecimal(12);
            String balloon_flag = request.getParameter("balloon_flag");
            String balloon = request.getParameter("balloon");
            String installment_flag=request.getParameter("installment_flag"); 
            BigDecimal car_price_cost = utility.stringToBigDecimal(request.getParameter("car_price_cost"));
            BigDecimal car_price_total = utility.stringToBigDecimal(request.getParameter("car_price_total"));

            BigDecimal down_payment_cost = utility.stringToBigDecimal(request.getParameter("down_payment_cost"));
            BigDecimal down_payment_total = utility.stringToBigDecimal(request.getParameter("down_payment_total"));

            BigDecimal balloon_amt_percentTmp = utility.stringToBigDecimal(request.getParameter("balloon_amt_percent"));
            BigDecimal balloon_amt_percent = balloon_amt_percentTmp.divide((new BigDecimal(100)), 2, 0);
            BigDecimal hire_purchase_cost=utility.stringToBigDecimal(request.getParameter("hire_purchase_cost"));
            BigDecimal hire_purchase_total=utility.stringToBigDecimal(request.getParameter("hire_purchase_total"));
            //BigDecimal balloon_amt_term =
            // utility.stringToBigDecimal(request.getParameter("balloon_amt_term"));
            BigDecimal balloon_total = utility.stringToBigDecimal(request.getParameter("balloon_total"));

            BigDecimal installment1 = utility.stringToBigDecimal(request.getParameter("installment1"));

            BigDecimal rate1 = utility.stringToBigDecimal(request.getParameter("rate1"));
            logger.debug("rate1 = " + rate1);
            rate1 = rate1.divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP);

            String first_due_date = request.getParameter("first_due_date");
            String excution_date = request.getParameter("excution_date");
            logger.debug("car_price_cost = " + car_price_cost);
            logger.debug("down_payment_cost = " + down_payment_cost);
            logger.debug("down_payment_total = " + down_payment_total);
            logger.debug("have_vat = " + have_vat);
            logger.debug("balloon_flag = " + balloon_flag);
            logger.debug("balloon = " + balloon);
            logger.debug("rate1 = " + rate1);
            logger.debug("installment1 = " + installment1);
            logger.debug("vat = " + vat);
            logger.debug("vatTmp = " + vatTmp);
            logger.debug("balloon_amt_percent = " + balloon_amt_percent);
            //logger.debug("balloon_amt_term = "+balloon_amt_term);
            logger.debug("balloon_total = " + balloon_total);
            logger.debug("Installment Flag "+installment_flag);
            logger.debug("hire_purchase_cost "+hire_purchase_cost);
            logger.debug("hire_purchase_total "+hire_purchase_total);
            BigDecimal rv = utility.stringToBigDecimal(request.getParameter("rv_percent"));
            rv = rv.divide(new BigDecimal(100), 10, 0);

            String payment_type = request.getParameter("payment_type");
            BigDecimal net_rate = utility.stringToBigDecimal(request.getParameter("net_rate"));
            net_rate = net_rate.divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP);
            BigDecimal subsidies_amount = utility.stringToBigDecimal(request.getParameter("subsidies_amount"));

            int paymentType = 0; //Ending
            if (OrigConstant.PAYMENT_BEGINING.equals(payment_type)) {
                paymentType = 1; //Begining
            }
            logger.debug("payment_type = " + payment_type);
            logger.debug("paymentType = " + paymentType);
            logger.debug("rv %= " + rv);

            LoanDataM loanDataM = new LoanDataM();
            loanDataM.setTotalOfCarPrice(car_price_total);
            loanDataM.setTotalOfDownPayment(down_payment_total);
            loanDataM.setCostOfCarPrice(car_price_cost);
            loanDataM.setCostOfDownPayment(down_payment_cost);
            loanDataM.setRate1(rate1);
            loanDataM.setInstallment1(installment1);
            loanDataM.setBalloonPercent(balloon_amt_percent);
            loanDataM.setBalloonTerm(installment1);
            loanDataM.setNetRate(net_rate);
            loanDataM.setSubsidiesAmount(subsidies_amount);
            loanDataM.setRvPercent(rv);
            loanDataM.setTotalOfBalloonAmt(balloon_total);
            loanDataM.setInstallmentFlag(installment_flag);

            CalculateLoanUtil calculateLoanUtil = new CalculateLoanUtil();

            logger.debug("have_vat = " + have_vat);
            logger.debug("balloon_flag = " + balloon_flag);
            logger.debug("balloon = " + balloon);

            String lastDueDate = "";
            
            //get Step Installment Data
            LoanDataM sessionLoanDataM = (LoanDataM) request.getSession().getAttribute("POPUP_DATA");
            if(sessionLoanDataM!=null){                
                loanDataM.setStepInstallmentVect(sessionLoanDataM.getStepInstallmentVect());
            }            
            //Date firstDueDate =
            // ORIGUtility.parseThaiToEngDate(first_due_date);
            //Date excutionDate =
            // ORIGUtility.parseThaiToEngDate(excution_date);
            if (OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag())) {
                logger.debug("Step Installment ***");
                if (("N").equals(have_vat)) {
                    loanDataM.setCostOfHairPurchaseAmt(hire_purchase_cost);
                    loanDataM = calculateLoanUtil.calculateStepInstallment(loanDataM, false, vatTmp, monthInyear, paymentType);
                } else {
                    loanDataM.setTotalOfHairPurchaseAmt(hire_purchase_total);
                    loanDataM = calculateLoanUtil.calculateStepInstallment(loanDataM, true, vatTmp, monthInyear, paymentType);
                }
            } else {
                if (("N").equals(have_vat)) {
                    if (!OrigConstant.LOAN_TYPE_LEASING.equals(formHandler.getAppForm().getLoanType())) {
                        if (("Y").equals(balloon_flag)) {
                            if (OrigConstant.BALLOON_TYPE_INSTALLMENT.equals(balloon)) {
                                loanDataM = calculateLoanUtil.calculateLoanTypeBalloonIns(loanDataM, false, vatTmp, monthInyear, paymentType);
                            } else {
                                loanDataM = calculateLoanUtil.calculateLoanTypeBalloon(loanDataM, false, vatTmp, monthInyear, paymentType);
                            }
                        } else {
                            loanDataM = calculateLoanUtil.calculateLoanTypeFlatRate(loanDataM, false, vatTmp, monthInyear, paymentType);
                        }
                        //calculate last due date
                        lastDueDate = calculateLoanUtil.getLastDueDate(first_due_date, installment1.intValue());
                    } else {
                        loanDataM = calculateLoanUtil.calculateLoanTypeLeasing(loanDataM, false, vatTmp, monthInyear, paymentType);
                        //calculate last due date
                        lastDueDate = calculateLoanUtil.getLastDueDate(excution_date, installment1.intValue());
                    }
                } else {
                    if (!OrigConstant.LOAN_TYPE_LEASING.equals(formHandler.getAppForm().getLoanType())) {
                        if (("Y").equals(balloon_flag)) {
                            if (OrigConstant.BALLOON_TYPE_INSTALLMENT.equals(balloon)) {
                                loanDataM = calculateLoanUtil.calculateLoanTypeBalloonIns(loanDataM, true, vatTmp, monthInyear, paymentType);
                            } else {
                                loanDataM = calculateLoanUtil.calculateLoanTypeBalloon(loanDataM, true, vatTmp, monthInyear, paymentType);
                            }
                        } else {
                            loanDataM = calculateLoanUtil.calculateLoanTypeFlatRate(loanDataM, true, vatTmp, monthInyear, paymentType);
                        }
                        //calculate last due date
                        lastDueDate = calculateLoanUtil.getLastDueDate(first_due_date, installment1.intValue());
                    } else {
                        loanDataM = calculateLoanUtil.calculateLoanTypeLeasing(loanDataM, true, vatTmp, monthInyear, paymentType);
                        //calculate last due date
                        lastDueDate = calculateLoanUtil.getLastDueDate(excution_date, installment1.intValue());
                    }
                }
            }
            logger.debug("lastDueDate = " + lastDueDate);
            //Rewrite
            StringBuffer sb = new StringBuffer("");
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            sb.append("<list>");
            sb.append("<field name=\"car_price_cost\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfCarPrice().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"car_price_vat\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfCarPrice().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"car_price_total\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfCarPrice().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"down_payment_cost\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfDownPayment().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"down_payment_vat\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfDownPayment().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"down_payment_total\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfDownPayment().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"finance_cost\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfFinancialAmt().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"finance_vat\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfFinancialAmt().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"finance_total\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfFinancialAmt().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"balloon_cost\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfBalloonAmt().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"balloon_vat\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfBalloonAmt().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"balloon_total\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfBalloonAmt().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            //sb.append("<field
            // name=\"pv_ball_amtcost\">"+ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfpvBalloonAmt().setScale(2,BigDecimal.ROUND_HALF_UP))+"</field>");
            //sb.append("<field
            // name=\"pv_ball_amtvat\">"+ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfpvBalloonAmt().setScale(2,BigDecimal.ROUND_HALF_UP))+"</field>");
            //sb.append("<field
            // name=\"pv_ball_amttotal\">"+ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfpvBalloonAmt().setScale(2,BigDecimal.ROUND_HALF_UP))+"</field>");
            //sb.append("<field
            // name=\"pv_cost\">"+ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfpv().setScale(2,BigDecimal.ROUND_HALF_UP))+"</field>");
            //sb.append("<field
            // name=\"pv_vat\">"+ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfpv().setScale(2,BigDecimal.ROUND_HALF_UP))+"</field>");
            //sb.append("<field
            // name=\"pv_total\">"+ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfpv().setScale(2,BigDecimal.ROUND_HALF_UP))+"</field>");
            sb.append("<field name=\"rate1_cost\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfRate1().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"rate1_vat\">" + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfRate1().setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");
            sb.append("<field name=\"rate1_total\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfRate1().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"installment1_cost\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfInstallment1().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            BigDecimal valueVat = (loanDataM.getTotalOfInstallment1().setScale(2, BigDecimal.ROUND_HALF_UP).subtract(loanDataM.getCostOfInstallment1()
                    .setScale(2, BigDecimal.ROUND_HALF_UP)));
            sb.append("<field name=\"installment1_vat\">" + ORIGDisplayFormatUtil.displayCommaNumber(valueVat) + "</field>");
            sb.append("<field name=\"installment1_total\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfInstallment1().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"hire_purchase_cost\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfHairPurchaseAmt().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            valueVat = (loanDataM.getTotalOfHairPurchaseAmt().setScale(2, BigDecimal.ROUND_HALF_UP).subtract(loanDataM.getCostOfHairPurchaseAmt().setScale(2,
                    BigDecimal.ROUND_HALF_UP)));
            sb.append("<field name=\"hire_purchase_vat\">" + ORIGDisplayFormatUtil.displayCommaNumber(valueVat) + "</field>");
            sb.append("<field name=\"hire_purchase_total\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfHairPurchaseAmt().setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"effective_rate\">" + loanDataM.getEffectiveRate().setScale(9, BigDecimal.ROUND_HALF_UP) + "</field>");
            sb.append("<field name=\"irr_rate\">" + loanDataM.getIRRRate().setScale(9, BigDecimal.ROUND_HALF_UP) + "</field>");
            sb.append("<field name=\"rv_cost\">" + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfRV().setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");
            sb.append("<field name=\"rv_vat\">" + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfRV().setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");
            sb.append("<field name=\"rv_total\">" + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfRV().setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");
            sb.append("<field name=\"loan_rate\">" + loanDataM.getEffectiveRate().setScale(7, BigDecimal.ROUND_HALF_UP) + "</field>");
            sb.append("<field name=\"subsidies_amount\">" + loanDataM.getSubsidiesAmount().abs().setScale(2, BigDecimal.ROUND_HALF_UP) + "</field>");
            sb.append("<field name=\"down_amount\">" + loanDataM.getCostOfDownPayment().setScale(2, BigDecimal.ROUND_HALF_UP) + "</field>");
            sb.append("<field name=\"special_hire_charge\">" + loanDataM.getSubsidiesAmount().abs().setScale(2, BigDecimal.ROUND_HALF_UP) + "</field>");
            if (lastDueDate != null) {
                sb.append("<field name=\"end_due_date\">" + ORIGUtility.displayEngToThaiDate(utility.stringToDate(lastDueDate)) + "</field>");
                sb.append("<field name=\"last_due_date\">" + ORIGUtility.displayEngToThaiDate(utility.stringToDate(lastDueDate)) + "</field>");
            }
            sb.append("</list>");

            response.setContentType("text/xml;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache,no-store");
            response.setDateHeader("Expires", 0);
            PrintWriter pw = response.getWriter();
            pw.write(sb.toString());
            pw.close();
        } catch (Exception e) {
            logger.error("Error in CalculateLoanServlet >> ", e);
        }
    }
}
