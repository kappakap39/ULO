/*
 * Created on Nov 27, 2007
 * Created by Weeraya
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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.InstallmentDataM;
import com.eaf.orig.shared.model.LoanDataM;

/**
 * @author Weeraya
 * 
 * Type: CalculateLoanUtil
 */
public class CalculateLoanUtil {
    Logger logger = Logger.getLogger(CalculateLoanUtil.class);

    BigDecimal car_price_total = new BigDecimal(0);

    BigDecimal car_price_cost = new BigDecimal(0);

    BigDecimal car_price_vat = new BigDecimal(0);

    BigDecimal down_payment_total = new BigDecimal(0);

    BigDecimal down_payment_cost = new BigDecimal(0);

    BigDecimal down_payment_vat = new BigDecimal(0);

    BigDecimal finance_cost = new BigDecimal(0);

    BigDecimal finance_vat = new BigDecimal(0);

    BigDecimal finance_total = new BigDecimal(0);

    BigDecimal balloon_amt_percent = new BigDecimal(0);

    BigDecimal balloon_amt_term = new BigDecimal(0);

    BigDecimal balloon_vat = new BigDecimal(0);

    BigDecimal balloon_total = new BigDecimal(0);

    BigDecimal balloon_cost = new BigDecimal(0);

    BigDecimal pv_ball_amtcost = new BigDecimal(0);

    BigDecimal pv_ball_amtvat = new BigDecimal(0);

    BigDecimal pv_ball_amttotal = new BigDecimal(0);

    BigDecimal pv_cost = new BigDecimal(0);

    BigDecimal pv_vat = new BigDecimal(0);

    BigDecimal pv_total = new BigDecimal(0);

    BigDecimal rv = new BigDecimal(0);

    BigDecimal rv_cost = new BigDecimal(0);

    BigDecimal rv_vat = new BigDecimal(0);

    BigDecimal rv_total = new BigDecimal(0);

    BigDecimal installment1 = new BigDecimal(0);

    BigDecimal installment1_cost = new BigDecimal(0);

    BigDecimal installment1_vat = new BigDecimal(0);

    BigDecimal rate1 = new BigDecimal(0);

    BigDecimal installment1_total = new BigDecimal(0);

    BigDecimal hire_purchase_vat = new BigDecimal(0);

    BigDecimal hire_purchase_total = new BigDecimal(0);

    BigDecimal hire_purchase_cost = new BigDecimal(0);

    BigDecimal rate1_total = new BigDecimal(0);

    BigDecimal rate1_cost = new BigDecimal(0);

    BigDecimal rate1_vat = new BigDecimal(0);

    BigDecimal downPayment = new BigDecimal(0);

    BigDecimal net_rate = new BigDecimal(0);

    BigDecimal subsidies_amount = new BigDecimal(0);

    BigDecimal firstCashFlows;

    BigDecimal lastCashFlows;

    BigDecimal pmt = new BigDecimal(0);

    //BigDecimal loan_rate = new BigDecimal(0);
    BigDecimal irr_rate = new BigDecimal(0);

    BigDecimal effective_rate = new BigDecimal(0);

    double[] cashFlows;

    public LoanDataM calculateLoanTypeBalloon(LoanDataM loanDataM, boolean haveVat, BigDecimal vatTmp, BigDecimal monthInyear, int paymentType) {
        logger.debug("loanType Balloon ");
        car_price_total = loanDataM.getTotalOfCarPrice();
        car_price_cost = loanDataM.getCostOfCarPrice();
        down_payment_total = loanDataM.getTotalOfDownPayment();
        down_payment_cost = loanDataM.getCostOfDownPayment();
        balloon_amt_percent = loanDataM.getBalloonPercent();
        balloon_amt_term = loanDataM.getBalloonTerm();
        installment1 = loanDataM.getInstallment1();
        rate1 = loanDataM.getRate1();
        downPayment = loanDataM.getDownPaymentPercent();
        net_rate = loanDataM.getNetRate();
        subsidies_amount = loanDataM.getSubsidiesAmount();
        balloon_total = loanDataM.getTotalOfBalloonAmt();
        double[] cashFlows;
        logger.debug("balloon_total = " + balloon_total);
        if (haveVat) {
            logger.debug("car_price_total = " + car_price_total);
            car_price_cost = car_price_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            logger.debug("car_price_cost = " + car_price_cost);
            //--------------Set Scale 2-----------
            //car_price_cost=car_price_cost.setScale(2,BigDecimal.ROUND_HALF_UP);

            car_price_vat = car_price_total.subtract(car_price_cost);
            down_payment_cost = down_payment_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            //----------------Set Scale 2-----------------------------
            //down_payment_cost=down_payment_cost.setScale(2,BigDecimal.ROUND_HALF_UP);
            down_payment_vat = down_payment_total.subtract(down_payment_cost);
            finance_total = car_price_total.subtract(down_payment_total);
            //finance_cost = finance_total.divide(vatTmp,10,0);
            finance_cost = car_price_cost.subtract(down_payment_cost);
            finance_vat = finance_total.subtract(finance_cost);
            //balloon_total =
            // car_price_total.multiply(balloon_amt_percent).setScale(0,
            // BigDecimal.ROUND_HALF_UP);
            balloon_cost = balloon_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            logger.debug("balloon_cost = " + balloon_cost);
            balloon_vat = balloon_total.subtract(balloon_cost);
            //pv_ball_amttotal = calculatePV(rate1.divide(monthInyear,10,0),
            // balloon_amt_term, pmt, balloon_total, paymentType);
            pv_ball_amttotal = calculatePV(rate1.divide(monthInyear, 20, BigDecimal.ROUND_HALF_UP), balloon_amt_term, pmt, balloon_total, paymentType);
            //-----------------Round 2----
            pv_ball_amttotal = pv_ball_amttotal.setScale(2, BigDecimal.ROUND_HALF_UP);
            pv_ball_amtcost = pv_ball_amttotal.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            pv_ball_amtvat = pv_ball_amttotal.subtract(pv_ball_amtcost);
            logger.debug("pv_ball_amttotal = " + pv_ball_amttotal);
            //pv_total =
            // car_price_total.subtract(down_payment_total).add(pv_ball_amttotal).setScale(2,BigDecimal.ROUND_HALF_UP);
            pv_total = car_price_total.subtract(down_payment_total).add(pv_ball_amttotal);
            pv_cost = pv_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            pv_vat = pv_total.subtract(pv_cost);
            logger.debug("rate1 = " + rate1);
            logger.debug("balloon_amt_term = " + balloon_amt_term);
            logger.debug("pv_cost = " + pv_cost);
            logger.debug("pv_total = " + pv_total);
            logger.debug("pv_ball_amtcost" + pv_ball_amtcost);
            logger.debug("-------------Before Call get Subsidies------------");
            installment1_total = calculatePMT(rate1.divide(monthInyear, 10, 0), balloon_amt_term.subtract(new BigDecimal(1)), pv_total.negate(),
                    new BigDecimal(0), paymentType);
            installment1_total = installment1_total.setScale(0, BigDecimal.ROUND_HALF_UP);
            installment1_cost = installment1_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            installment1_vat = installment1_total.subtract(installment1_cost);
            //hire_purchase_total =
            // (installment1_total.multiply(balloon_amt_term)).add(balloon_total);
            hire_purchase_total = (installment1_total.multiply(balloon_amt_term.subtract(new BigDecimal(1))).setScale(2, BigDecimal.ROUND_HALF_UP))
                    .add(balloon_total);
            //hire_purchase_cost =
            // hire_purchase_total.divide(vatTmp,2,BigDecimal.ROUND_HALF_UP);
            hire_purchase_cost = (installment1_cost.multiply(balloon_amt_term.subtract(new BigDecimal(1))).setScale(2, BigDecimal.ROUND_HALF_UP))
                    .add(balloon_cost);
            hire_purchase_vat = hire_purchase_total.subtract(hire_purchase_cost);
            logger.debug("hire_purchase_total = " + hire_purchase_total);
            logger.debug("hire_purchase_cost = " + hire_purchase_cost);

            rate1_total = hire_purchase_total.subtract(finance_total);
            //rate1_cost = rate1_total.divide(vatTmp,10,0);
            rate1_cost = hire_purchase_cost.subtract(finance_cost);
            rate1_vat = rate1_total.subtract(rate1_cost);

            LoanDataM loanDataMTmp = new LoanDataM();
            loanDataMTmp.setTotalOfCarPrice(car_price_total);
            loanDataMTmp.setTotalOfDownPayment(down_payment_total);
            loanDataMTmp.setCostOfCarPrice(car_price_cost);
            loanDataMTmp.setCostOfDownPayment(down_payment_cost);
            loanDataMTmp.setRate1(rate1);
            loanDataMTmp.setInstallment1(installment1);
            loanDataMTmp.setBalloonPercent(balloon_amt_percent);
            loanDataMTmp.setBalloonTerm(balloon_amt_term);
            loanDataMTmp.setNetRate(net_rate);
            loanDataMTmp.setSubsidiesAmount(subsidies_amount);
            loanDataMTmp.setRvPercent(rv);
            loanDataMTmp.setTotalOfBalloonAmt(balloon_total);
            //if(net_rate.compareTo(rate1) != 0){
            if (net_rate.compareTo(rate1) == 1) {
                pmt = getSubsidiesForLastBalloon(loanDataMTmp, haveVat, vatTmp, monthInyear, paymentType);
            }
            logger.debug("pmt = " + pmt);
            firstCashFlows = getFirstCashFlowsForLastInstallment(haveVat, paymentType, finance_cost, vatTmp, rate1, balloon_amt_term, monthInyear,
                    balloon_total, installment1_cost, finance_total, pmt, pv_ball_amttotal);
            //cashFlows = getcashFlows(firstCashFlows.setScale(2,0),
            // installment1_cost.setScale(2,0), balloon_cost.setScale(2,0),
            // balloon_amt_term, paymentType);
            cashFlows = getcashFlows(firstCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1_cost.setScale(2, BigDecimal.ROUND_HALF_UP),
                    balloon_cost.setScale(2, BigDecimal.ROUND_HALF_UP), balloon_amt_term, paymentType);
            irr_rate = calculateIRRRate(cashFlows, 0);
            irr_rate = irr_rate.multiply(monthInyear);

            if (paymentType == 0) {
                firstCashFlows = finance_cost.negate();
            } else {
                firstCashFlows = finance_cost.negate().add(installment1_cost);
            }
            //cashFlows = getcashFlows(firstCashFlows.setScale(2,0),
            // installment1_cost.setScale(2,0),
            // balloon_cost.setScale(2,BigDecimal.ROUND_HALF_UP),
            // balloon_amt_term, paymentType);
            cashFlows = getcashFlows(firstCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1_cost.setScale(2, BigDecimal.ROUND_HALF_UP),
                    balloon_cost.setScale(2, BigDecimal.ROUND_HALF_UP), balloon_amt_term, paymentType);
            effective_rate = calculateIRRRate(cashFlows, 0);
            effective_rate = effective_rate.multiply(monthInyear);
            //pv_ball_amttotal = pv_ball_amttotal.setScale(0,
            // BigDecimal.ROUND_HALF_UP);
            pv_ball_amttotal = pv_ball_amttotal.setScale(2, BigDecimal.ROUND_HALF_UP);

        } else {
            //car_price_total = car_price_cost;
            //------------
            car_price_total = car_price_cost.multiply(vatTmp);
            car_price_total = car_price_total.setScale(2, BigDecimal.ROUND_HALF_UP);
            car_price_vat = car_price_total.subtract(car_price_cost);
            //------------------
            //down_payment_total = down_payment_cost;
            down_payment_total = down_payment_cost.multiply(vatTmp);
            down_payment_total = down_payment_total.setScale(2, BigDecimal.ROUND_HALF_UP);
            down_payment_vat = down_payment_total.subtract(down_payment_cost);
            finance_total = car_price_total.subtract(down_payment_total);
            //finance_cost = finance_total;
            finance_cost = car_price_cost.subtract(down_payment_cost);
            finance_vat = car_price_vat.subtract(down_payment_vat);
            balloon_cost = balloon_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            //balloon_total =
            // balloon_cost.divide(vatTmp,2,BigDecimal.ROUND_HALF_UP);
            logger.debug("balloon_cost = " + balloon_cost);
            balloon_vat = balloon_total.subtract(balloon_cost);
            pv_ball_amttotal = calculatePV(rate1.divide(monthInyear, 20, 0), balloon_amt_term, pmt, balloon_total, paymentType);
            pv_ball_amtcost = pv_ball_amttotal.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            pv_ball_amtvat = pv_ball_amttotal.subtract(pv_ball_amtcost);
            //pv_total =
            // (car_price_total.subtract(down_payment_total)).multiply(vatTmp).add(pv_ball_amttotal);
            pv_total = (car_price_total.subtract(down_payment_total)).add(pv_ball_amttotal);
            pv_total = pv_total.setScale(2, BigDecimal.ROUND_HALF_UP);
            pv_cost = pv_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            pv_vat = pv_total.subtract(pv_cost);
            logger.debug("rate1 = " + rate1);
            logger.debug("balloon_amt_term = " + balloon_amt_term);
            logger.debug("pv_total = " + pv_total);
            installment1_total = calculatePMT(rate1.divide(monthInyear, 10, 0), balloon_amt_term.subtract(new BigDecimal(1)), pv_total.negate(),
                    new BigDecimal(0), paymentType);
            installment1_total = installment1_total.setScale(0, BigDecimal.ROUND_HALF_UP);
            installment1_cost = installment1_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            installment1_vat = installment1_total.subtract(installment1_cost);
            //hire_purchase_total =
            // (installment1_total.multiply(balloon_amt_term
            // ).setScale(2,BigDecimal.ROUND_HALF_UP)).add(balloon_total);
            hire_purchase_total = (installment1_total.multiply(balloon_amt_term.subtract(new BigDecimal(1))).setScale(2, BigDecimal.ROUND_HALF_UP))
                    .add(balloon_total);
            ////hire_purchase_cost =
            // hire_purchase_total.divide(vatTmp,2,BigDecimal.ROUND_HALF_UP);
            hire_purchase_cost = (installment1_cost.multiply(balloon_amt_term.subtract(new BigDecimal(1))).setScale(2, BigDecimal.ROUND_HALF_UP))
                    .add(balloon_cost);
            logger.debug("hire_purchase_total = " + hire_purchase_total);
            logger.debug("hire_purchase_cost = " + hire_purchase_cost);
            hire_purchase_vat = hire_purchase_total.subtract(hire_purchase_cost);

            //rate1_total =
            // (hire_purchase_total.subtract(finance_total)).multiply(vatTmp);
            rate1_total = (hire_purchase_total.subtract(finance_total));
            //rate1_total = (hire_purchase_total.subtract(finance_total));
            //rate1_cost = rate1_total.divide(vatTmp,10,0);
            rate1_cost = hire_purchase_cost.subtract(finance_cost);
            rate1_vat = rate1_total.subtract(rate1_cost);

            LoanDataM loanDataMTmp = new LoanDataM();
            loanDataMTmp.setTotalOfCarPrice(car_price_total);
            loanDataMTmp.setTotalOfDownPayment(down_payment_total);
            loanDataMTmp.setCostOfCarPrice(car_price_cost);
            loanDataMTmp.setCostOfDownPayment(down_payment_cost);
            loanDataMTmp.setRate1(rate1);
            loanDataMTmp.setInstallment1(installment1);
            loanDataMTmp.setBalloonPercent(balloon_amt_percent);
            loanDataMTmp.setBalloonTerm(balloon_amt_term);
            loanDataMTmp.setNetRate(net_rate);
            loanDataMTmp.setSubsidiesAmount(subsidies_amount);
            loanDataMTmp.setRvPercent(rv);
            loanDataMTmp.setTotalOfBalloonAmt(balloon_total);
            //if(net_rate.compareTo(rate1) != 0){
            if (net_rate.compareTo(rate1) == 1) {
                pmt = getSubsidiesForLastBalloon(loanDataMTmp, haveVat, vatTmp, monthInyear, paymentType);
            }
            logger.debug("pmt = " + pmt);
            firstCashFlows = getFirstCashFlowsForLastInstallment(haveVat, paymentType, finance_cost, vatTmp, rate1, balloon_amt_term, monthInyear,
                    balloon_total, installment1_cost, finance_total, pmt, pv_ball_amttotal);
            //cashFlows = getcashFlows(firstCashFlows.setScale(2,0),
            // installment1_cost.setScale(2,0), balloon_cost.setScale(2,0),
            // balloon_amt_term, paymentType);
            cashFlows = getcashFlows(firstCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1_cost.setScale(2, BigDecimal.ROUND_HALF_UP),
                    balloon_cost.setScale(2, BigDecimal.ROUND_HALF_UP), balloon_amt_term, paymentType);
            irr_rate = calculateIRRRate(cashFlows, 0);
            irr_rate = irr_rate.multiply(monthInyear);

            if (paymentType == 0) {
                firstCashFlows = finance_cost.negate();
            } else {
                firstCashFlows = finance_cost.negate().add(installment1_cost);
            }
            //cashFlows = getcashFlows(firstCashFlows.setScale(2,0),
            // installment1_cost.setScale(2,0),
            // balloon_cost.setScale(2,BigDecimal.ROUND_HALF_UP),
            // balloon_amt_term, paymentType);
            cashFlows = getcashFlows(firstCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1_cost.setScale(2, BigDecimal.ROUND_HALF_UP),
                    balloon_cost.setScale(2, BigDecimal.ROUND_HALF_UP), balloon_amt_term, paymentType);
            effective_rate = calculateIRRRate(cashFlows, 0);
            effective_rate = effective_rate.multiply(monthInyear);

            //pv_ball_amttotal =
            // pv_ball_amttotal.setScale(0,BigDecimal.ROUND_HALF_UP);
            pv_ball_amttotal = pv_ball_amttotal.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        return setValueToLoanDataM(loanDataM);
    }

    public BigDecimal getSubsidiesForLastBalloon(LoanDataM loanDataM, boolean haveVat, BigDecimal vatTmp_t, BigDecimal monthInyear, int paymentType) {

        BigDecimal car_price_total_t = loanDataM.getTotalOfCarPrice();
        logger.debug("car_price_total_t = " + car_price_total_t);
        BigDecimal car_price_cost_t = loanDataM.getCostOfCarPrice();
        BigDecimal car_price_vat_t = new BigDecimal(0);

        BigDecimal down_payment_total_t = loanDataM.getTotalOfDownPayment();
        BigDecimal down_payment_cost_t = loanDataM.getCostOfDownPayment();
        BigDecimal down_payment_vat_t = new BigDecimal(0);

        BigDecimal finance_cost_t = new BigDecimal(0);
        BigDecimal finance_vat_t = new BigDecimal(0);
        BigDecimal finance_total_t = new BigDecimal(0);

        BigDecimal balloon_vat_t = new BigDecimal(0);
        BigDecimal balloon_total_t = loanDataM.getTotalOfBalloonAmt();
        BigDecimal balloon_cost_t = new BigDecimal(0);

        BigDecimal pv_ball_amtcost_t = new BigDecimal(0);
        BigDecimal pv_ball_amtvat_t = new BigDecimal(0);
        BigDecimal pv_ball_amttotal_t = new BigDecimal(0);

        BigDecimal pv_cost_t = new BigDecimal(0);
        BigDecimal pv_vat_t = new BigDecimal(0);
        BigDecimal pv_total_t = new BigDecimal(0);

        BigDecimal installment1_t = loanDataM.getInstallment1();
        BigDecimal installment1_total_t = new BigDecimal(0);

        BigDecimal rate_t = loanDataM.getNetRate();

        if (haveVat) {
            car_price_cost_t = car_price_total_t.divide(vatTmp_t, 2, BigDecimal.ROUND_HALF_UP);
            car_price_vat_t = car_price_total_t.subtract(car_price_cost_t);
            down_payment_cost_t = down_payment_total_t.divide(vatTmp_t, 2, BigDecimal.ROUND_HALF_UP);
            down_payment_vat_t = down_payment_total_t.subtract(down_payment_cost_t);
            finance_total_t = car_price_total_t.subtract(down_payment_total_t);
            finance_cost_t = finance_total_t.divide(vatTmp_t, 2, BigDecimal.ROUND_HALF_UP);
            finance_vat_t = finance_total_t.subtract(finance_cost_t);
            //balloon_total_t =
            // car_price_total_t.multiply(balloon_amt_percent).setScale(0,
            // BigDecimal.ROUND_HALF_UP);
            balloon_cost_t = balloon_total_t.divide(vatTmp_t, 2, BigDecimal.ROUND_HALF_UP);
            balloon_vat_t = balloon_total_t.subtract(balloon_cost_t);
            logger.debug("balloon_amt_term = " + balloon_amt_term);
            logger.debug("balloon_total_t = " + balloon_total_t);
            //=====================================
            rate1 = loanDataM.getRate1();
            //pv_ball_amttotal_t = calculatePV(rate_t.divide(monthInyear,10,0),
            // balloon_amt_term, new BigDecimal(0), balloon_total_t,
            // paymentType);
            pv_ball_amttotal_t = calculatePV(rate1.divide(monthInyear, 20, 0), balloon_amt_term, new BigDecimal(0), balloon_total_t, paymentType);
            pv_ball_amttotal_t = pv_ball_amttotal_t.setScale(2, BigDecimal.ROUND_HALF_UP);
            logger.debug("pv_ball_amttotal_t -->" + pv_ball_amttotal_t);
            //=================
            //pv_ball_amttotal_t=new BigDecimal(135273.25d);
            pv_ball_amtcost_t = pv_ball_amttotal_t.divide(vatTmp_t, 2, BigDecimal.ROUND_HALF_UP);
            pv_ball_amtvat_t = pv_ball_amttotal_t.subtract(pv_ball_amtcost_t);
            logger.debug("pv_ball_amttotal_t = " + pv_ball_amttotal_t);
            pv_total_t = car_price_total_t.subtract(down_payment_total_t).add(pv_ball_amttotal_t).setScale(2, BigDecimal.ROUND_HALF_UP);
            logger.debug("pv_total_t = " + pv_total_t);
            pv_cost_t = pv_total_t.divide(vatTmp_t, 2, BigDecimal.ROUND_HALF_UP);
            logger.debug("pv_cost_t = " + pv_cost_t);
            pv_vat_t = pv_total_t.subtract(pv_cost_t);
            logger.debug("pv_vat_t = " + pv_vat_t);
            logger.debug("rate_t = " + rate_t);
            installment1_total_t = calculatePMT(rate_t.divide(monthInyear, 10, 0), balloon_amt_term.subtract(new BigDecimal(1)), pv_total_t.negate(),
                    new BigDecimal(0), paymentType);
            installment1_total_t = installment1_total_t.setScale(0, BigDecimal.ROUND_HALF_UP);

        } else {
            //car_price_total_t = car_price_cost_t;
            car_price_total_t = car_price_cost_t.multiply(vatTmp_t);
            car_price_total_t = car_price_total_t.setScale(2, BigDecimal.ROUND_HALF_UP);
            //down_payment_total_t = down_payment_cost_t;
            down_payment_total_t = down_payment_cost_t.multiply(vatTmp_t);
            down_payment_total_t = down_payment_total_t.setScale(2, BigDecimal.ROUND_HALF_UP);
            finance_total_t = car_price_total_t.subtract(down_payment_total_t);
            finance_cost_t = finance_total_t;
            //balloon_total_t =
            // car_price_total_t.multiply(balloon_amt_percent);
            balloon_cost_t = balloon_total_t.divide(vatTmp_t, 2, BigDecimal.ROUND_HALF_UP);
            balloon_vat_t = balloon_total_t.subtract(balloon_cost_t);
            //pv_ball_amttotal_t = calculatePV(rate_t.divide(monthInyear,10,0),
            // balloon_amt_term, new BigDecimal(0), balloon_total_t,
            // paymentType);
            rate1 = loanDataM.getRate1();
            pv_ball_amttotal_t = calculatePV(rate1.divide(monthInyear, 20, 0), balloon_amt_term, new BigDecimal(0), balloon_total_t, paymentType);
            pv_ball_amttotal_t = pv_ball_amttotal_t.setScale(2, BigDecimal.ROUND_HALF_UP);
            logger.debug("pv_ball_amttotal_t = " + pv_ball_amttotal_t);
            pv_ball_amtcost_t = pv_ball_amttotal_t.divide(vatTmp_t, 2, BigDecimal.ROUND_HALF_UP);
            pv_ball_amtvat_t = pv_ball_amttotal_t.subtract(pv_ball_amtcost_t);
            logger.debug("car_price_total_t=" + car_price_total_t);
            logger.debug("down_payment_total_t=" + down_payment_total_t);
            pv_total_t = car_price_total_t.subtract(down_payment_total_t).add(pv_ball_amttotal_t);
            logger.debug("pv_total_t = " + pv_total_t);
            //pv_total_t = pv_total_t.setScale(2,BigDecimal.ROUND_HALF_UP);
            //logger.debug("pv_total_t = "+pv_total_t);
            pv_cost_t = pv_total_t.divide(vatTmp_t, 2, BigDecimal.ROUND_HALF_UP);
            pv_vat_t = pv_total_t.subtract(pv_cost_t);
            //installment1_total_t =
            // calculatePMT(rate_t.divide(monthInyear,10,0),
            // balloon_amt_term.subtract(new BigDecimal(1)),
            // pv_total_t.negate().multiply(vatTmp_t), new
            // BigDecimal(0),paymentType);
            installment1_total_t = calculatePMT(rate_t.divide(monthInyear, 10, 0), balloon_amt_term.subtract(new BigDecimal(1)), pv_total_t.negate(),
                    new BigDecimal(0), paymentType);
            logger.debug("installment1_total_t = " + installment1_total_t);
            installment1_total_t = installment1_total_t.setScale(0, BigDecimal.ROUND_HALF_UP);
            logger.debug("installment1_total_t = " + installment1_total_t);
        }

        return installment1_total_t;
    }

    public LoanDataM calculateLoanTypeBalloonIns(LoanDataM loanDataM, boolean haveVat, BigDecimal vatTmp, BigDecimal monthInyear, int paymentType) {
        logger.debug("loanType Balloon Install ");
        car_price_total = loanDataM.getTotalOfCarPrice();
        car_price_cost = loanDataM.getCostOfCarPrice();
        down_payment_total = loanDataM.getTotalOfDownPayment();
        down_payment_cost = loanDataM.getCostOfDownPayment();
        balloon_amt_percent = loanDataM.getBalloonPercent();
        balloon_amt_term = loanDataM.getBalloonTerm();
        installment1 = loanDataM.getInstallment1();
        rate1 = loanDataM.getRate1();
        downPayment = loanDataM.getDownPaymentPercent();
        net_rate = loanDataM.getNetRate();
        subsidies_amount = loanDataM.getSubsidiesAmount();
        balloon_total = loanDataM.getTotalOfBalloonAmt();

        if (haveVat) {
            car_price_cost = car_price_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            car_price_vat = car_price_total.subtract(car_price_cost);
            down_payment_cost = down_payment_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            down_payment_vat = down_payment_total.subtract(down_payment_cost);
            finance_total = car_price_total.subtract(down_payment_total);
            //finance_cost = finance_total.divide(vatTmp,10,0);
            finance_cost = car_price_cost.subtract(down_payment_cost);
            finance_vat = finance_total.subtract(finance_cost);
            //balloon_total =
            // car_price_total.multiply(balloon_amt_percent).setScale(0,
            // BigDecimal.ROUND_HALF_UP);
            balloon_cost = balloon_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            balloon_vat = balloon_total.subtract(balloon_cost);
            pv_ball_amttotal = calculatePV(rate1.divide(monthInyear, 20, BigDecimal.ROUND_HALF_UP), balloon_amt_term, pmt, balloon_total, paymentType);
            pv_ball_amttotal = pv_ball_amttotal.setScale(2, BigDecimal.ROUND_HALF_UP);
            pv_ball_amtcost = pv_ball_amttotal.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            pv_ball_amtvat = pv_ball_amttotal.subtract(pv_ball_amtcost);
            logger.debug("pv_ball_amttotal = " + pv_ball_amttotal);
            //pv_total =
            // car_price_total.subtract(down_payment_total).add(pv_ball_amttotal).setScale(2,BigDecimal.ROUND_HALF_UP);
            pv_total = car_price_total.subtract(down_payment_total).add(pv_ball_amttotal);
            pv_cost = pv_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            pv_vat = pv_total.subtract(pv_cost);
            installment1_total = calculatePMT(rate1.divide(monthInyear, 10, 0), balloon_amt_term, finance_total.negate(), balloon_total, paymentType);
            installment1_total = installment1_total.setScale(0, BigDecimal.ROUND_HALF_UP);
            installment1_cost = installment1_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            installment1_vat = installment1_total.subtract(installment1_cost);
            hire_purchase_total = (installment1_total.multiply(installment1)).add(balloon_total);
            //hire_purchase_cost =
            // hire_purchase_total.divide(vatTmp,2,BigDecimal.ROUND_HALF_UP);
            hire_purchase_cost = (installment1_cost.multiply(installment1)).add(balloon_cost);
            //hire_purchase_cost=hire_purchase_cost.setScale(0,BigDecimal.ROUND_HALF_UP);
            //hire_purchase_vat = hire_purchase_total.setScale(2,
            // BigDecimal.ROUND_HALF_UP).subtract(hire_purchase_cost.setScale(2,
            // BigDecimal.ROUND_HALF_UP));
            hire_purchase_vat = hire_purchase_total.subtract(hire_purchase_cost);
            rate1_total = hire_purchase_total.subtract(finance_total);
            //rate1_cost = hire_purchase_cost.setScale(2,
            // BigDecimal.ROUND_HALF_UP).subtract(finance_cost.setScale(2,
            // BigDecimal.ROUND_HALF_UP));
            rate1_cost = hire_purchase_cost.subtract(finance_cost);
            rate1_vat = rate1_total.subtract(rate1_cost);
            //if(net_rate.compareTo(rate1) != 0){
            if (net_rate.compareTo(rate1) == 1) {
                subsidies_amount = getSubsidiesForLeasing(paymentType, vatTmp, rate1, balloon_amt_term, monthInyear, net_rate, balloon_total, finance_total);
            } else {
                subsidies_amount = new BigDecimal(0);
            }
            firstCashFlows = getFirstCashFlowsForLeasing(paymentType, finance_cost, vatTmp, rate1, balloon_amt_term, monthInyear, net_rate, balloon_total,
                    installment1_cost, finance_total, subsidies_amount);
            lastCashFlows = getLastCashFlows(installment1_cost, balloon_cost);
            //cashFlows = getcashFlows(firstCashFlows.setScale(2,0),
            // installment1_cost.setScale(2,0), lastCashFlows.setScale(2,0),
            // balloon_amt_term, paymentType);
            cashFlows = getcashFlows(firstCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1_cost.setScale(2, BigDecimal.ROUND_HALF_UP),
                    lastCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), balloon_amt_term, paymentType);
            irr_rate = calculateIRRRate(cashFlows, 0);
            irr_rate = irr_rate.multiply(monthInyear);

            if (paymentType == 0) {
                firstCashFlows = finance_cost.negate();
            } else {
                firstCashFlows = finance_cost.negate().add(installment1_cost);
            }
            //cashFlows = getcashFlows(firstCashFlows.setScale(2,0),
            // installment1_cost.setScale(2,0),
            // lastCashFlows.setScale(2,BigDecimal.ROUND_HALF_UP),
            // balloon_amt_term, paymentType);
            cashFlows = getcashFlows(firstCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1_cost.setScale(2, BigDecimal.ROUND_HALF_UP),
                    lastCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), balloon_amt_term, paymentType);
            effective_rate = calculateIRRRate(cashFlows, 0);
            effective_rate = effective_rate.multiply(monthInyear);
            //pv_ball_amttotal = pv_ball_amttotal.setScale(0,
            // BigDecimal.ROUND_HALF_UP);
            pv_ball_amttotal = pv_ball_amttotal.setScale(2, BigDecimal.ROUND_HALF_UP);

        } else {
            //car_price_total = car_price_cost;
            car_price_total = car_price_cost.multiply(vatTmp);
            car_price_total = car_price_total.setScale(2, BigDecimal.ROUND_HALF_UP);
            car_price_vat = car_price_total.subtract(car_price_cost);
            //down_payment_total = down_payment_cost;
            down_payment_total = down_payment_cost.multiply(vatTmp);
            ;
            down_payment_total = down_payment_total.setScale(2, BigDecimal.ROUND_HALF_UP);
            down_payment_vat = down_payment_total.subtract(down_payment_cost);
            finance_total = car_price_total.subtract(down_payment_total);
            //finance_cost = finance_total;
            //finance_cost =
            // finance_total.divide(vatTmp,10,BigDecimal.ROUND_HALF_UP);
            finance_cost = car_price_cost.subtract(down_payment_cost);
            finance_vat = finance_total.subtract(finance_cost);
            //balloon_total =
            // car_price_total.multiply(balloon_amt_percent);
            balloon_cost = balloon_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            balloon_vat = balloon_total.subtract(balloon_cost);
            pv_ball_amttotal = calculatePV(rate1.divide(monthInyear, 20, 0), balloon_amt_term, pmt, balloon_total, paymentType);
            pv_ball_amttotal = pv_ball_amttotal.setScale(2, BigDecimal.ROUND_HALF_UP);
            logger.debug("pv_ball_amttotal=" + pv_ball_amttotal);
            pv_ball_amtcost = pv_ball_amttotal.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            pv_ball_amtvat = pv_ball_amttotal.subtract(pv_ball_amtcost);
            pv_total = car_price_total.subtract(down_payment_total).add(pv_ball_amttotal);
            //pv_total = pv_total.setScale(2,BigDecimal.ROUND_HALF_UP);
            pv_cost = pv_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            pv_vat = pv_total.subtract(pv_cost);
            //installment1_total =
            // calculatePMT(rate1.divide(monthInyear,10,0),
            // balloon_amt_term, finance_total.negate().multiply(vatTmp),
            // balloon_total,paymentType);
            installment1_total = calculatePMT(rate1.divide(monthInyear, 10, 0), balloon_amt_term, finance_total.negate(), balloon_total, paymentType);
            installment1_total = installment1_total.setScale(0, BigDecimal.ROUND_HALF_UP);
            installment1_cost = installment1_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            installment1_vat = installment1_total.subtract(installment1_cost);
            //hire_purchase_total =
            // (installment1_total.multiply(balloon_amt_term).setScale(2,BigDecimal.ROUND_HALF_UP)).add(balloon_total);
            hire_purchase_total = (installment1_total.multiply(balloon_amt_term)).add(balloon_total);
            //hire_purchase_cost =
            // hire_purchase_total.divide(vatTmp,2,BigDecimal.ROUND_HALF_UP);
            //hire_purchase_cost =
            // (installment1_cost.multiply(balloon_amt_term).setScale(2,BigDecimal.ROUND_HALF_UP)).add(balloon_cost);
            hire_purchase_cost = (installment1_cost.multiply(balloon_amt_term)).add(balloon_cost);
            //hire_purchase_cost.setScale(2,BigDecimal.ROUND_HALF_UP);
            hire_purchase_vat = hire_purchase_total.subtract(hire_purchase_cost);

            rate1_total = (hire_purchase_total.subtract(finance_total));
            //rate1_total =
            // (hire_purchase_total.subtract(finance_total)).multiply(vatTmp);
            rate1_cost = hire_purchase_cost.subtract(finance_cost);
            //rate1_cost = rate1_total.divide(vatTmp,10,0);
            rate1_vat = rate1_total.subtract(rate1_cost);
            //if(net_rate.compareTo(rate1) != 0){
            if (net_rate.compareTo(rate1) == 1) {
                subsidies_amount = getSubsidiesForLeasing(paymentType, vatTmp, rate1, balloon_amt_term, monthInyear, net_rate, balloon_total, finance_total);
            } else {
                subsidies_amount = new BigDecimal(0);
            }
            firstCashFlows = getFirstCashFlowsForLeasing(paymentType, finance_cost, vatTmp, rate1, balloon_amt_term, monthInyear, net_rate, balloon_total,
                    installment1_cost, finance_total.multiply(vatTmp), subsidies_amount);
            lastCashFlows = getLastCashFlows(installment1_cost, balloon_cost);
            //cashFlows = getcashFlows(firstCashFlows.setScale(2,0),
            // installment1_cost.setScale(2,0), lastCashFlows.setScale(2,0),
            // balloon_amt_term, paymentType);
            cashFlows = getcashFlows(firstCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1_cost.setScale(2, BigDecimal.ROUND_HALF_UP),
                    lastCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), balloon_amt_term, paymentType);
            irr_rate = calculateIRRRate(cashFlows, 0);
            irr_rate = irr_rate.multiply(monthInyear);

            if (paymentType == 0) {
                firstCashFlows = finance_cost.negate();
            } else {
                firstCashFlows = finance_cost.negate().add(installment1_cost);
            }
            cashFlows = getcashFlows(firstCashFlows, installment1_cost, lastCashFlows, balloon_amt_term, paymentType);
            effective_rate = calculateIRRRate(cashFlows, 0);
            effective_rate = effective_rate.multiply(monthInyear);

            //pv_ball_amttotal =
            // pv_ball_amttotal.setScale(0,BigDecimal.ROUND_HALF_UP);
            pv_ball_amttotal = pv_ball_amttotal.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        return setValueToLoanDataM(loanDataM);
    }

    public LoanDataM calculateLoanTypeLeasing(LoanDataM loanDataM, boolean haveVat, BigDecimal vatTmp, BigDecimal monthInyear, int paymentType) {
        car_price_total = loanDataM.getTotalOfCarPrice();
        car_price_cost = loanDataM.getCostOfCarPrice();
        down_payment_total = loanDataM.getTotalOfDownPayment();
        down_payment_cost = loanDataM.getCostOfDownPayment();
        rv = loanDataM.getRvPercent();
        installment1 = loanDataM.getInstallment1();
        rate1 = loanDataM.getRate1();
        net_rate = loanDataM.getNetRate();

        if (haveVat) {
            //car_price_cost = car_price_total.divide(vatTmp,10,0);
            car_price_cost = car_price_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            car_price_vat = car_price_total.subtract(car_price_cost);
            //down_payment_cost = down_payment_total.divide(vatTmp,10,0);
            down_payment_cost = down_payment_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            down_payment_vat = down_payment_total.subtract(down_payment_cost);
            finance_total = car_price_total.subtract(down_payment_total);
            //finance_cost = finance_total.divide(vatTmp,10,0);
            finance_cost = car_price_cost.subtract(down_payment_cost);
            //finance_vat = finance_total.subtract(finance_cost);
            finance_vat = car_price_vat.subtract(down_payment_vat);
            //rv_total =
            // rv.multiply(car_price_total.setScale(2,BigDecimal.ROUND_HALF_UP));
            rv_total = rv.multiply(car_price_total);
            rv_cost = rv_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            rv_vat = rv_total.subtract(rv_cost);
            //installment1_total = calculatePMT(rate1.divide(monthInyear,10,0),
            // installment1, finance_total.negate(), rv_total, paymentType);
            //fix rv
            installment1_total = calculatePMT(rate1.divide(monthInyear, 10, 0), installment1, finance_total.negate(), rv_total.subtract(down_payment_total),
                    paymentType);
            installment1_total = installment1_total.setScale(0, BigDecimal.ROUND_HALF_UP);
            installment1_cost = installment1_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            installment1_vat = installment1_total.subtract(installment1_cost);
            //hire_purchase_total =
            // installment1_total.multiply(installment1).add(rv_total).setScale(2,BigDecimal.ROUND_HALF_UP);
            hire_purchase_total = (installment1_total.multiply(installment1)).add(rv_total);
            //hire_purchase_cost =
            // hire_purchase_total.divide(vatTmp,2,BigDecimal.ROUND_HALF_UP);
            hire_purchase_cost = (installment1_cost.multiply(installment1)).add(rv_cost);
            //hire_purchase_vat =
            // hire_purchase_total.setScale(2,0).subtract(hire_purchase_cost);
            hire_purchase_vat = hire_purchase_total.subtract(hire_purchase_cost);
            subsidies_amount = getSubsidiesForLeasing(paymentType, vatTmp, rate1, installment1, monthInyear, net_rate, rv_total, finance_total);
            firstCashFlows = getFirstCashFlowsForLeasing(paymentType, finance_cost, vatTmp, rate1, installment1, monthInyear, net_rate, rv_total,
                    installment1_cost, finance_total, subsidies_amount);
            lastCashFlows = getLastCashFlows(installment1_cost, rv_cost);
            //cashFlows = getcashFlows(firstCashFlows.setScale(2,0),
            // installment1_cost.setScale(2,0), lastCashFlows.setScale(2,0),
            // installment1, paymentType);
            cashFlows = getcashFlows(firstCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1_cost.setScale(2, BigDecimal.ROUND_HALF_UP),
                    lastCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1, paymentType);
            irr_rate = calculateIRRRate(cashFlows, 0);
            irr_rate = irr_rate.multiply(monthInyear);
            logger.debug("IRR = " + calculateIRRRate(cashFlows, 0, (cashFlows.length) - 1, 0, paymentType));
            if (paymentType == 0) {
                firstCashFlows = finance_cost.negate();
            } else {
                firstCashFlows = finance_cost.negate().add(installment1_cost);
            }
            //cashFlows = getcashFlows(firstCashFlows.setScale(2,0),
            // installment1_cost.setScale(2,0), lastCashFlows.setScale(2,0),
            // installment1, paymentType);
            cashFlows = getcashFlows(firstCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1_cost.setScale(2, BigDecimal.ROUND_HALF_UP),
                    lastCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1, paymentType);
            effective_rate = calculateIRRRate(cashFlows, 0);
            effective_rate = effective_rate.multiply(monthInyear);

        } else {
            car_price_total = car_price_cost;
            //car_price_total =
            // car_price_cost.multiply(vatTmp).setScale(2,BigDecimal.ROUND_HALF_UP);
            //car_price_vat=car_price_total.subtract(car_price_vat);
            down_payment_total = down_payment_cost;
            //down_payment_total =
            // down_payment_cost.multiply(vatTmp).setScale(2,BigDecimal.ROUND_HALF_UP);
            //down_payment_vat=down_payment_total.subtract(down_payment_cost);
            //finance_total = car_price_total.subtract(down_payment_total);
            finance_total = (car_price_total.subtract(down_payment_total)).multiply(vatTmp);
            //finance_cost = finance_total;
            finance_cost = car_price_cost.subtract(down_payment_cost);
            //finance_vat=finance_total.subtract(finance_cost);
            //rv_total =
            // rv.multiply(car_price_total).setScale(2,BigDecimal.ROUND_HALF_UP);
            //rv_total = rv.multiply(car_price_total);
            rv_total = (rv.multiply(car_price_total)).multiply(vatTmp);
            rv_cost = rv_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            rv_vat = rv_total.subtract(rv_cost);
            //installment1_total = calculatePMT(rate1.divide(monthInyear,10,0),
            // installment1, finance_total.negate().multiply(vatTmp), rv_total,
            // paymentType);
            installment1_total = calculatePMT(rate1.divide(monthInyear, 10, 0), installment1, finance_total.negate(), rv_total.subtract(down_payment_total),
                    paymentType);
            //installment1_total = calculatePMT(rate1.divide(monthInyear,10,0),
            // installment1, finance_total.negate(), rv_total, paymentType);
            installment1_total = installment1_total.setScale(0, BigDecimal.ROUND_HALF_UP);
            installment1_cost = installment1_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            installment1_vat = installment1_total.subtract(installment1_cost);
            //hire_purchase_total =
            // (installment1_total.multiply(installment1)).add(rv_total).setScale(2,BigDecimal.ROUND_HALF_UP);
            hire_purchase_total = (installment1_total.multiply(installment1)).add(rv_total);
            //hire_purchase_cost =
            // hire_purchase_total.divide(vatTmp,2,BigDecimal.ROUND_HALF_UP);
            hire_purchase_cost = (installment1_cost.multiply(installment1)).add(rv_cost);
            hire_purchase_vat = hire_purchase_total.subtract(hire_purchase_cost);
            subsidies_amount = getSubsidiesForLeasing(paymentType, vatTmp, rate1, installment1, monthInyear, net_rate, rv_total, finance_total);
            firstCashFlows = getFirstCashFlowsForLeasing(paymentType, finance_cost, vatTmp, rate1, installment1, monthInyear, net_rate, rv_total,
                    installment1_cost, finance_total.multiply(vatTmp), subsidies_amount);
            lastCashFlows = getLastCashFlows(installment1_cost, rv_cost);
            //cashFlows = getcashFlows(firstCashFlows.setScale(2,0),
            // installment1_cost.setScale(2,0), lastCashFlows.setScale(2,0),
            // installment1, paymentType);
            cashFlows = getcashFlows(firstCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1_cost.setScale(2, BigDecimal.ROUND_HALF_UP),
                    lastCashFlows.setScale(2, BigDecimal.ROUND_HALF_UP), installment1, paymentType);
            irr_rate = calculateIRRRate(cashFlows, 0);
            irr_rate = irr_rate.multiply(monthInyear);

            if (paymentType == 0) {
                firstCashFlows = finance_cost.negate();
            } else {
                firstCashFlows = finance_cost.negate().add(installment1_cost);
            }
            cashFlows = getcashFlows(firstCashFlows, installment1_cost, lastCashFlows, installment1, paymentType);
            effective_rate = calculateIRRRate(cashFlows, 0);
            effective_rate = effective_rate.multiply(monthInyear);

        }

        return setValueToLoanDataM(loanDataM);
    }

    public LoanDataM calculateLoanTypeFlatRate(LoanDataM loanDataM, boolean haveVat, BigDecimal vatTmp, BigDecimal monthInyear, int paymentType) {
        logger.debug("loanType Flat Rate");
        car_price_total = loanDataM.getTotalOfCarPrice();
        car_price_cost = loanDataM.getCostOfCarPrice();
        down_payment_total = loanDataM.getTotalOfDownPayment();
        down_payment_cost = loanDataM.getCostOfDownPayment();
        balloon_amt_percent = loanDataM.getBalloonPercent();
        balloon_amt_term = loanDataM.getBalloonTerm();
        installment1 = loanDataM.getInstallment1();
        rate1 = loanDataM.getRate1();
        downPayment = loanDataM.getDownPaymentPercent();
        net_rate = loanDataM.getNetRate();
        subsidies_amount = loanDataM.getSubsidiesAmount();

        logger.debug("paymentType = " + paymentType);
        logger.debug("haveVat = " + haveVat);

        if (haveVat) {
            car_price_cost = car_price_total.divide(vatTmp, 10, 0);
            car_price_vat = car_price_total.subtract(car_price_cost);
            down_payment_cost = down_payment_total.divide(vatTmp, 10, 0);
            down_payment_vat = down_payment_total.subtract(down_payment_cost);
            finance_total = car_price_total.subtract(down_payment_total);
            finance_cost = car_price_cost.setScale(2, BigDecimal.ROUND_HALF_UP).subtract(down_payment_cost.setScale(2, BigDecimal.ROUND_HALF_UP));
            finance_vat = finance_total.subtract(finance_cost);
            installment1_total = calculateInstallmentNoBalloon(finance_total, installment1, monthInyear, rate1, new BigDecimal(1));
            installment1_total = installment1_total.setScale(0, BigDecimal.ROUND_HALF_UP);
            //installment1_cost = installment1_total.divide(vatTmp,10,0);
            installment1_cost = installment1_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            installment1_vat = installment1_total.subtract(installment1_cost);
            hire_purchase_total = installment1_total.multiply(installment1);
            hire_purchase_total = hire_purchase_total.setScale(2, BigDecimal.ROUND_HALF_UP);
            hire_purchase_cost = installment1_cost.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(installment1);
            //hire_purchase_cost=hire_purchase_cost.setScale(0,BigDecimal.ROUND_HALF_UP);
            hire_purchase_vat = hire_purchase_total.subtract(hire_purchase_cost);
            rate1_total = hire_purchase_total.subtract(finance_total);
            rate1_cost = hire_purchase_cost.setScale(2, BigDecimal.ROUND_HALF_UP).subtract(finance_cost.setScale(2, BigDecimal.ROUND_HALF_UP));
            rate1_vat = rate1_total.subtract(rate1_cost);
            //if(net_rate.compareTo(rate1) != 0){
            if (net_rate.compareTo(rate1) == 1) {
                subsidies_amount = calculateSubsidies(net_rate, installment1, hire_purchase_total, finance_total, monthInyear);
            }
            logger.debug("subsidies_amount = " + subsidies_amount);
            effective_rate = calculateEffectiveRate(installment1, installment1_cost, finance_cost.negate(), new BigDecimal(0), paymentType, 0);
            irr_rate = calculateIRRRate(installment1, installment1_cost, finance_cost.negate(), subsidies_amount, new BigDecimal(0), paymentType, 0);
        } else {
            car_price_total = car_price_cost;
            down_payment_total = down_payment_cost;
            finance_cost = car_price_cost.subtract(down_payment_cost);
            finance_total = car_price_total.subtract(down_payment_total);
            //finance_vat = finance_total.subtract(finance_cost);
            installment1_total = (calculateInstallmentNoBalloon(finance_total, installment1, monthInyear, rate1, vatTmp)).setScale(0, BigDecimal.ROUND_HALF_UP);
            //installment1_total =
            // (installment1_total.multiply(vatTmp)).setScale(2,0);
            //installment1_cost = installment1_total.divide(vatTmp,10,0);
            installment1_cost = installment1_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            installment1_vat = installment1_total.subtract(installment1_cost);
            hire_purchase_total = installment1_total.multiply(installment1);
            hire_purchase_cost = installment1_cost.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(installment1);
            //hire_purchase_cost=hire_purchase_cost.setScale(0,BigDecimal.ROUND_HALF_UP);
            hire_purchase_vat = hire_purchase_total.subtract(hire_purchase_cost);
            rate1_total = (hire_purchase_total.subtract(finance_total));
            rate1_cost = hire_purchase_cost.setScale(2, BigDecimal.ROUND_HALF_UP).subtract(finance_cost.setScale(2, BigDecimal.ROUND_HALF_UP));
            rate1_vat = rate1_total.subtract(rate1_cost);
            //if(net_rate.compareTo(rate1) != 0){
            if (net_rate.compareTo(rate1) == 1) {
                //subsidies_amount = calculateSubsidies(net_rate, installment1,
                // hire_purchase_cost, finance_cost, monthInyear);
                subsidies_amount = calculateSubsidies(net_rate, installment1, hire_purchase_cost, finance_total, monthInyear);
            }
            logger.debug("subsidies_amount = " + subsidies_amount);
            effective_rate =
            //calculateEffectiveRate(installment1, installment1_cost,
            // finance_cost.negate(), new BigDecimal(0), paymentType, 0);
            irr_rate = calculateIRRRate(installment1, installment1_cost, finance_cost.negate(), subsidies_amount, new BigDecimal(0), paymentType, 0);

        }

        return setValueToLoanDataM(loanDataM);
    }

    public LoanDataM calculateStepInstallment(LoanDataM loanDataM, boolean haveVat, BigDecimal vatTmp, BigDecimal monthInyear, int paymentType) {
        car_price_total = loanDataM.getTotalOfCarPrice();
        car_price_cost = loanDataM.getCostOfCarPrice();
        down_payment_total = loanDataM.getTotalOfDownPayment();
        down_payment_cost = loanDataM.getCostOfDownPayment();
        rv = loanDataM.getRvPercent();
        installment1 = loanDataM.getInstallment1();
        rate1 = loanDataM.getRate1();
        net_rate = loanDataM.getNetRate();
        //      Step Installment
        if (haveVat) {
            car_price_cost = car_price_total.divide(vatTmp, 10, 0);
            car_price_vat = car_price_total.subtract(car_price_cost);
            down_payment_cost = down_payment_total.divide(vatTmp, 10, 0);
            down_payment_vat = down_payment_total.subtract(down_payment_cost);
            finance_total = car_price_total.subtract(down_payment_total);
            finance_cost = car_price_cost.setScale(2, BigDecimal.ROUND_HALF_UP).subtract(down_payment_cost.setScale(2, BigDecimal.ROUND_HALF_UP));
            finance_vat = finance_total.subtract(finance_cost);

            //Step Installment get First Installment
            Vector vStepInstallment = loanDataM.getStepInstallmentVect();
            if (vStepInstallment == null) {
                vStepInstallment = new Vector();
            }
            if (vStepInstallment.size() > 1) {
                InstallmentDataM prmInstallmentDataM = (InstallmentDataM) vStepInstallment.get(0);
                installment1_total = prmInstallmentDataM.getInstallmentAmount();
                installment1_cost = prmInstallmentDataM.getAmount();
                installment1_vat = prmInstallmentDataM.getInstallmentVat();
            }
            //installment1_total = calculateInstallmentNoBalloon(finance_total,
            // installment1, monthInyear, rate1, new BigDecimal(1));
            //installment1_total = installment1_total.setScale(0,
            // BigDecimal.ROUND_HALF_UP);
            //installment1_cost = installment1_total.divide(vatTmp,10,0);
            //installment1_cost = installment1_total.divide(vatTmp, 2,
            // BigDecimal.ROUND_HALF_UP);
            //installment1_vat =
            // installment1_total.subtract(installment1_cost);

           // for (int i = 0; i < vStepInstallment.size(); i++) {
                
              //  InstallmentDataM prmInstallmentDataM = (InstallmentDataM) vStepInstallment.get(i);                 
                hire_purchase_total = loanDataM.getTotalOfHairPurchaseAmt();
                hire_purchase_cost = hire_purchase_total.divide(vatTmp, 10, 0);
                hire_purchase_vat = hire_purchase_total.subtract(hire_purchase_cost);
           // }

            //hire_purchase_total = installment1_total.multiply(installment1);
            //hire_purchase_total = hire_purchase_total.setScale(2,
            // BigDecimal.ROUND_HALF_UP);
            // hire_purchase_cost = installment1_cost.setScale(2,
            // BigDecimal.ROUND_HALF_UP).multiply(installment1);
            //hire_purchase_cost=hire_purchase_cost.setScale(0,BigDecimal.ROUND_HALF_UP);
            // hire_purchase_vat =
            // hire_purchase_total.subtract(hire_purchase_cost);
            rv_total = rv.multiply(car_price_total);
            rv_cost = rv_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
            rv_vat = rv_total.subtract(rv_cost);    
            rate1_total = hire_purchase_total.subtract(finance_total);
            rate1_cost = hire_purchase_cost.setScale(2, BigDecimal.ROUND_HALF_UP).subtract(finance_cost.setScale(2, BigDecimal.ROUND_HALF_UP));
            rate1_vat = rate1_total.subtract(rate1_cost);
            //if(net_rate.compareTo(rate1) != 0){
            if (net_rate.compareTo(rate1) == 1) {
                subsidies_amount = calculateSubsidies(net_rate, installment1, hire_purchase_total, finance_total, monthInyear);
            }
            logger.debug("subsidies_amount = " + subsidies_amount);
            // effective_rate = calculateEffectiveRate(installment1,
            // installment1_cost, finance_cost.negate(), new BigDecimal(0),
            // paymentType, 0);
            // effective_rate = calculateIRRRate(installment1,
            // installment1_cost, finance_cost.negate(), new BigDecimal(0), new
            // BigDecimal(0), paymentType, 0);             
            if(vStepInstallment.size()>0){
            cashFlows = getCacheFlowStepInstallment(vStepInstallment, new BigDecimal(0),finance_cost);
            logger.debug("cashFlow="+cashFlows.length);
            logger.debug("hire_purchase_total"+hire_purchase_total);
            effective_rate = calculateIRRRate(cashFlows, 0);
            effective_rate = effective_rate.multiply(monthInyear);
            cashFlows = getCacheFlowStepInstallment(vStepInstallment,subsidies_amount,finance_cost);
            irr_rate = calculateIRRRate(cashFlows, 0);
            irr_rate=irr_rate.multiply(monthInyear);
            }
           // irr_rate = calculateIRRRate(installment1, installment1_cost, finance_cost.negate(), subsidies_amount, new BigDecimal(0), paymentType, 0);
        } else {
            car_price_total = car_price_cost;
            down_payment_total = down_payment_cost;
            finance_cost = car_price_cost.subtract(down_payment_cost);
            finance_total = car_price_total.subtract(down_payment_total);
            //finance_vat = finance_total.subtract(finance_cost);

            //installment1_total =
            // (calculateInstallmentNoBalloon(finance_total, installment1,
            // monthInyear, rate1, vatTmp)).setScale(0,
            // BigDecimal.ROUND_HALF_UP);
            //installment1_total =
            // (installment1_total.multiply(vatTmp)).setScale(2,0);
            //installment1_cost = installment1_total.divide(vatTmp,10,0);
            // installment1_cost = installment1_total.divide(vatTmp, 2,
            // BigDecimal.ROUND_HALF_UP);
            //installment1_vat =
            // installment1_total.subtract(installment1_cost);
            Vector vStepInstallment = loanDataM.getStepInstallmentVect();
            if (vStepInstallment == null) {
                vStepInstallment = new Vector();
            }
            if (vStepInstallment.size() > 1) {
                InstallmentDataM prmInstallmentDataM = (InstallmentDataM) vStepInstallment.get(0);
                installment1_total = prmInstallmentDataM.getInstallmentAmount();
                installment1_cost = prmInstallmentDataM.getAmount();
                installment1_vat = prmInstallmentDataM.getInstallmentVat();
            }
           // for (int i = 0; i < vStepInstallment.size(); i++) {
            //    InstallmentDataM prmInstallmentDataM = (InstallmentDataM) vStepInstallment.get(i);
                hire_purchase_total =loanDataM.getCostOfHairPurchaseAmt();
                hire_purchase_cost = hire_purchase_total;
                hire_purchase_vat =  hire_purchase_total.subtract(hire_purchase_cost);
            //}
            // hire_purchase_total = installment1_total.multiply(installment1);
            // hire_purchase_cost = installment1_cost.setScale(2,
            // BigDecimal.ROUND_HALF_UP).multiply(installment1);
            //hire_purchase_cost=hire_purchase_cost.setScale(0,BigDecimal.ROUND_HALF_UP);
            //hire_purchase_vat =
            // hire_purchase_total.subtract(hire_purchase_cost);
           rv_total = (rv.multiply(car_price_total)).multiply(vatTmp);
           rv_cost = rv_total.divide(vatTmp, 2, BigDecimal.ROUND_HALF_UP);
           rv_vat = rv_total.subtract(rv_cost);
            rate1_total = (hire_purchase_total.subtract(finance_total));
            rate1_cost = hire_purchase_cost.setScale(2, BigDecimal.ROUND_HALF_UP).subtract(finance_cost.setScale(2, BigDecimal.ROUND_HALF_UP));
            rate1_vat = rate1_total.subtract(rate1_cost);
            //if(net_rate.compareTo(rate1) != 0){
            if (net_rate.compareTo(rate1) == 1) {
                //subsidies_amount = calculateSubsidies(net_rate, installment1,
                // hire_purchase_cost, finance_cost, monthInyear);
                subsidies_amount = calculateSubsidies(net_rate, installment1, hire_purchase_cost, finance_total, monthInyear);
            }
            logger.debug("subsidies_amount = " + subsidies_amount);
            //effective_rate = calculateEffectiveRate(installment1,
            // installment1_cost, finance_cost.negate(), new BigDecimal(0),
            // paymentType, 0);
            //effective_rate = calculateIRRRate(installment1,
            // installment1_cost, finance_cost.negate(), new BigDecimal(0), new
            // BigDecimal(0), paymentType, 0);
            if(vStepInstallment.size()>0){
            cashFlows = getCacheFlowStepInstallment(vStepInstallment, new BigDecimal(0),finance_cost);
            logger.debug("cashFlow="+cashFlows.length);
            logger.debug("hire_purchase_total"+hire_purchase_total);
            effective_rate = calculateIRRRate(cashFlows, 0);
            effective_rate = effective_rate.multiply(monthInyear);
            cashFlows = getCacheFlowStepInstallment(vStepInstallment,subsidies_amount,finance_cost);
            irr_rate = calculateIRRRate(cashFlows, 0);
            irr_rate=irr_rate.multiply(monthInyear);
            }
            //calculateIRRRate(installment1, installment1_cost, finance_cost.negate(), subsidies_amount, new BigDecimal(0), paymentType, 0);
        }
        return setValueToLoanDataM(loanDataM);
    }

    private BigDecimal round(BigDecimal p_n, double nd) {
        double n = p_n.doubleValue();
        if (isFinite(n) && isFinite(nd)) {
            double sign_n = (n < 0) ? -1 : 1;
            double abs_n = Math.abs(n);
            double factor = Math.pow(10, nd);
            return new BigDecimal(sign_n * Math.round(abs_n * factor) / factor);
        } else {
            logger.error("Value is NaN");
            return new BigDecimal(0);
        }
    }

    private static final boolean isFinite(double x) {
        return !(Double.isInfinite(x) || Double.isNaN(x));
    }

    /**
     * Calculating a Loan Payment
     * 
     * @param pv :
     *            The present value of an investment or loan
     * @param ir :
     *            The interest rate for an investment or loan
     * @param np :
     *            The number of periods in an investment or loan
     * @return
     */
    public BigDecimal calculatePMT(BigDecimal pv, BigDecimal rate, BigDecimal installmentTerm, BigDecimal monthInyear) {
        //var PMT = (PV * IR) / (1 - Math.pow(1 + IR, -NP))
        logger.debug("pv = " + pv);
        logger.debug("rate = " + rate);
        logger.debug("installmentTerm = " + installmentTerm);
        BigDecimal result = new BigDecimal(Math.pow(((new BigDecimal(1).add(rate.divide(monthInyear, 10, 0))).doubleValue()), installmentTerm.negate()
                .doubleValue()));
        logger.debug("result = " + result);
        result = (pv.multiply(rate.divide(monthInyear, 10, 0))).divide((new BigDecimal(1)).subtract(result), 2, 0);
        logger.debug("result = " + result);
        //result = result.multiply(new BigDecimal(100));
        return result;
    }

    /**
     * Calculate internal rate of return (IRR) using cash flows that occur at
     * regular intervals, such as monthly or annually. The internal rate of
     * return is the interest rate received for an investment consisting of
     * payments and receipts that occur at regular intervals.
     * <p>
     * Method: Newton-Raphson technique. Formula: sum(cashFlow(i) / (1 + IRR)^i)
     * 
     * @param cashFlows
     *            Cash flow values. Must contain at least one negative value
     *            (cash paid) and one positive value (cash received).
     * @param estimatedResult
     *            Optional guess as start value (default (if value is
     *            Double.NaN): 0.1 = 10%; if value is negative: 0.5). As the
     *            formula to calculate IRRs can have multiple solutions, an
     *            estimated result (guess) can help find the result we are
     *            looking for.
     * @return Internal rate of return (0.25 = 25%) or Double.NaN if IRR not
     *         computable.
     * @see <a
     *      href="http://forum.java.sun.com/thread.jspa?forumID=1&threadID=219441">IRR
     *      financial function in Java </a>
     * @see <a
     *      href="http://de.wikipedia.org/wiki/Interner_Zinsfu%C3%9F">Interner
     *      Zinsfuﬂ </a>
     */
    public BigDecimal calculateIRRRate(final double[] cashFlows, final double estimatedResult) {
        double result = Double.NaN;

        if (cashFlows != null && cashFlows.length > 0) {
            //check if business startup costs is not zero:
            if (cashFlows[0] != 0.0) {
                final double noOfCashFlows = cashFlows.length;

                double sumCashFlows = 0.0;
                //check if at least 1 positive and 1 negative cash flow exists:
                int noOfNegativeCashFlows = 0;
                int noOfPositiveCashFlows = 0;
                for (int i = 0; i < noOfCashFlows; i++) {
                    sumCashFlows += cashFlows[i];
                    if (cashFlows[i] > 0) {
                        noOfPositiveCashFlows++;
                    } else if (cashFlows[i] < 0) {
                        noOfNegativeCashFlows++;
                    }
                }

                if (noOfNegativeCashFlows > 0 && noOfPositiveCashFlows > 0) { //at
                    // least
                    // 1
                    // negative
                    // and
                    // 1
                    // positive
                    // cash
                    // flow
                    // available?
                    //set estimated result:
                    double irrGuess = 0.1; //default: 10%
                    if (estimatedResult != Double.NaN) {
                        irrGuess = estimatedResult;
                        if (irrGuess <= 0.0)
                            irrGuess = 0.5;
                    }

                    //initialize first IRR with estimated result:
                    double irr = 0.0;
                    if (sumCashFlows < 0) { //sum of cash flows negative?
                        irr = -irrGuess;
                    } else { //sum of cash flows not negative
                        irr = irrGuess;
                    }

                    //iteration:
                    final double minDistance = .0000001; //the smaller the
                    // distance, the
                    // smaller the
                    // interpolation error
                    final double cashFlowStart = cashFlows[0]; //business
                    // startup costs
                    final int maxIteration = 50;
                    boolean wasHi = false;
                    double cashValue = 0.0; //cash value (Kapitalwert)
                    for (int i = 0; i <= maxIteration; i++) { //for each
                        // iteration
                        //calculate cash value with current irr:
                        cashValue = cashFlowStart; //init with startup costs
                        for (int j = 1; j < noOfCashFlows; j++) { //for each
                            // cash floe
                            cashValue += cashFlows[j] / Math.pow(1.0 + irr, j);
                        }//next cash flow

                        if (Math.abs(cashValue) < 0.01) { //cash value is
                            // nearly zero
                            result = irr;
                            break;
                        }

                        //adjust irr for next iteration:
                        if (cashValue > 0.0) { //cash value > 0 => next irr >
                            // current irr
                            if (wasHi) {
                                irrGuess /= 2;
                            }

                            irr += irrGuess;

                            if (wasHi) {
                                irrGuess -= minDistance;
                                wasHi = false;
                            }
                        } else { //cash value < 0 => next irr < current irr
                            irrGuess /= 2;
                            irr -= irrGuess;
                            wasHi = true;
                        }

                        if (irrGuess <= minDistance) { //estimated result too
                            // small to continue =>
                            // end calculation
                            result = irr;
                            break;
                        }
                    }//next iteration
                }//else: noOfNegativeCashFlows == 0 || noOfPositiveCashFlows ==
                // 0
            }//else: first cash flow is 0
        }//else: cashFlows unavailable
        if(result==Double.NaN){result=0;}
        return new BigDecimal(result);
    }//getIRR()

    public double[] getcashFlows(BigDecimal firstCashFlows, BigDecimal installmentTotal, BigDecimal lastCashFlows, BigDecimal term, int type) {
        if (type == 1) { //Beginning
            term = term.subtract(new BigDecimal(1));
        }
        int cashSize = term.intValue();
        double[] result = new double[(cashSize + 1)];
        result[0] = firstCashFlows.doubleValue();
        logger.debug("cashFlows 0 = " + result[0]);
        for (int i = 1; i < (term.intValue()); i++) {
            result[i] = installmentTotal.doubleValue();
            logger.debug("cashFlows " + i + " = " + result[i]);
        }
        result[cashSize] = lastCashFlows.doubleValue();
        logger.debug("cashFlows last = " + result[cashSize]);
        return result;
    }

    public BigDecimal calculatePV(BigDecimal balloon_total, BigDecimal rate1, BigDecimal monthInyear, BigDecimal term, String balloonType) {
        if (OrigConstant.BALLOON_TYPE_INSTALLMENT.equals(balloonType)) {
            term = term.add(new BigDecimal(1));
        }
        logger.debug("balloon_total = " + balloon_total);
        logger.debug("rate1 = " + rate1);
        logger.debug("monthInyear = " + monthInyear);
        logger.debug("term = " + term);
        BigDecimal pv_ball_amttotal = ((new BigDecimal(1)).add((rate1.divide(monthInyear, 10, 0))));
        logger.debug("pv_ball_amttotal1 = " + pv_ball_amttotal);
        pv_ball_amttotal = new BigDecimal(Math.pow(pv_ball_amttotal.doubleValue(), term.doubleValue()));
        logger.debug("pv_ball_amttotal1 = " + pv_ball_amttotal);
        pv_ball_amttotal = balloon_total.divide(pv_ball_amttotal, 0, 0);
        logger.debug("pv_ball_amttotal1 = " + pv_ball_amttotal);
        return pv_ball_amttotal;
    }

    public BigDecimal calculateInstallmentNoBalloon(BigDecimal finance_total, BigDecimal installment1, BigDecimal monthInyear, BigDecimal rate1, BigDecimal vat) {
        logger.debug("finance_total = " + finance_total);
        logger.debug("installment1 = " + installment1);
        logger.debug("rate1 = " + rate1);
        BigDecimal installment = finance_total.multiply(rate1);
        //Modify Round By sankom 20080430
        //installment =
        // installment.multiply(installment1.divide(monthInyear,10,0));
        installment = installment.multiply(installment1.divide(monthInyear, 10, BigDecimal.ROUND_HALF_UP));
        logger.debug("installment = " + installment.add(finance_total));
        //installment =
        // (installment.add(finance_total)).divide(installment1,10,0).multiply(vat);
        installment = (installment.add(finance_total)).divide(installment1, 10, BigDecimal.ROUND_HALF_UP).multiply(vat);
        logger.debug("installment = " + installment);
        installment = installment.setScale(2, BigDecimal.ROUND_HALF_UP);
        logger.debug("installmentRound = " + installment);
        return installment;
    }

    public BigDecimal calculateEffectiveRate(BigDecimal installmentTerm, BigDecimal installment, BigDecimal financeAmount, BigDecimal p_fv, int type, int guess) {
        double fv = p_fv.doubleValue();
        double pv = financeAmount.doubleValue();
        double pmt = installment.doubleValue();
        double nper = installmentTerm.doubleValue();
        double type2 = (type != 0) ? 1 : 0;

        double wanted_precision = 0.00000001;

        double current_diff = Double.MAX_VALUE;
        double rate;
        double next_rate;
        double y;
        double z;
        if (guess == -1.0)
            rate = 0.1;
        else
            rate = guess;
        int max_iterations = 20;
        int iterations_done = 0;
        while (current_diff > wanted_precision && iterations_done < max_iterations) {
            if (rate == 0)
                next_rate = rate - (pv + pmt * nper + fv) / (pv * nper + pmt * (nper * (nper - 1) + 2 * type2 * nper) / 2);
            else {
                y = Math.pow(1 + rate, nper - 1);
                z = y * (1 + rate);
                next_rate = rate
                        * (1 - (rate * pv * z + pmt * (1 + rate * type2) * (z - 1) + rate * fv)
                                / (rate * rate * nper * pv * y - pmt * (z - 1) + rate * pmt * (1 + rate * type2) * nper * y));
            }
            iterations_done++;
            current_diff = Math.abs(next_rate - rate);
            rate = next_rate;
        }
        if (guess == 0 && Math.abs(rate) < wanted_precision)
            rate = 0;
        if (current_diff >= wanted_precision) {
            return new BigDecimal(0);
        } else {
            rate = rate * 12;
            return new BigDecimal(rate);
        }
    }

    private BigDecimal calculateIRRRate(double[] n0_vect, int n0_from, int n0_to, double guess, int paymentType) {
        if (paymentType == 1) {//Beginning
            n0_to -= 1;
        }
        if (!isFinite(guess)) {
            logger.error("Value is NaN");
            return new BigDecimal(0);
        }

        double used_guess = guess;
        double x;
        double next_x;
        if (used_guess == -1.0)
            x = 0.1;
        else
            x = used_guess;

        int max_iterations = 20;
        int iterations_done = 0;

        double wanted_precision = 0.00000001;
        double current_diff = Double.MAX_VALUE;

        double current;
        double above;
        double below;
        int index;
        while (current_diff > wanted_precision && iterations_done < max_iterations) {
            index = 0;
            above = 0.0;
            below = 0.0;
            for (int ii = n0_from; ii <= n0_to; ii++) {
                current = n0_vect[ii];
                above += current / Math.pow(1.0 + x, index);
                below += -index * current / Math.pow(1.0 + x, index + 1.0);
                index++;
            }
            next_x = x - above / below;
            iterations_done++;
            current_diff = Math.abs(next_x - x);
            x = next_x;
        }
        ;

        if (used_guess == 0.0 && Math.abs(x) < wanted_precision)
            x = 0.0;

        if (current_diff < wanted_precision)
            return new BigDecimal(x);
        else
            logger.debug("0000000000");
        return new BigDecimal(0);
    }

    public BigDecimal calculateIRRRate(BigDecimal installmentTerm, BigDecimal installment, BigDecimal financeAmount, BigDecimal subsidies, BigDecimal p_fv,
            int type, int guess) {
        double fv = p_fv.doubleValue();
        double pv = financeAmount.add(subsidies).doubleValue();
        double pmt = installment.doubleValue();
        double nper = installmentTerm.doubleValue();
        double type2 = (type != 0) ? 1 : 0;

        double wanted_precision = 0.00000001;

        double current_diff = Double.MAX_VALUE;
        double rate;
        double next_rate;
        double y;
        double z;
        if (guess == -1.0)
            rate = 0.1;
        else
            rate = guess;
        int max_iterations = 20;
        int iterations_done = 0;
        while (current_diff > wanted_precision && iterations_done < max_iterations) {
            if (rate == 0)
                next_rate = rate - (pv + pmt * nper + fv) / (pv * nper + pmt * (nper * (nper - 1) + 2 * type2 * nper) / 2);
            else {
                y = Math.pow(1 + rate, nper - 1);
                z = y * (1 + rate);
                next_rate = rate
                        * (1 - (rate * pv * z + pmt * (1 + rate * type2) * (z - 1) + rate * fv)
                                / (rate * rate * nper * pv * y - pmt * (z - 1) + rate * pmt * (1 + rate * type2) * nper * y));
            }
            iterations_done++;
            current_diff = Math.abs(next_rate - rate);
            rate = next_rate;
        }
        if (guess == 0 && Math.abs(rate) < wanted_precision)
            rate = 0;
        if (current_diff >= wanted_precision) {
            return new BigDecimal(0);
        } else {
            rate = rate * 12;
            return new BigDecimal(rate);
        }
    }

    public BigDecimal calculateSubsidies(BigDecimal netRate, BigDecimal installmentTerm, BigDecimal hirePurchase, BigDecimal financeAmountTotal,
            BigDecimal monthInyear) {
        BigDecimal subsidies = new BigDecimal(1).add(netRate.multiply(installmentTerm.divide(monthInyear, 10, 0)));
        logger.debug("subsidies 1 = " + subsidies);
        subsidies = hirePurchase.divide(subsidies, 2, BigDecimal.ROUND_HALF_UP);
        logger.debug("subsidies 2 = " + subsidies);
        subsidies = financeAmountTotal.subtract(subsidies);
        logger.debug("subsidies 3 = " + subsidies);
        return subsidies;
    }

    public BigDecimal calculateRV(BigDecimal equipment, BigDecimal rvPercent, BigDecimal vat) {
        BigDecimal rv = equipment.multiply(rvPercent).divide(new BigDecimal(100), 10, 0).multiply(vat);
        return rv;
    }

    public BigDecimal calculatePMT(BigDecimal p_rate, BigDecimal installmentTerm, BigDecimal p_pv, BigDecimal p_fv, int type) {
        double rate = p_rate.doubleValue();
        double pv = p_pv.doubleValue();
        double fv = p_fv.doubleValue();
        double nper = installmentTerm.doubleValue();
        if (rate == 0) {
            return new BigDecimal(-pv / nper);
        } else {
            double pvif = Math.pow(1 + rate, nper);
            double fvifa = (Math.pow(1 + rate, nper) - 1) / rate;

            double type1 = (type != 0) ? 1 : 0;

            return new BigDecimal((-pv * pvif - fv) / ((1 + rate * type1) * fvifa));
        }
    }

    public BigDecimal calculatePV(BigDecimal p_rate, BigDecimal installmentTerm, BigDecimal p_pmt, BigDecimal p_fv, int type) {
        /*
         * double rate = p_rate.doubleValue(); double fv = p_fv.doubleValue();
         * double pmt = p_pmt.doubleValue(); double nper =
         * installmentTerm.doubleValue(); if (rate == 0) return new
         * BigDecimal(-(fv + nper * pmt));
         * 
         * double type1 = (type != 0) ? 1 : 0;
         * 
         * double pvif = Math.pow(1 + rate, nper); double fvifa = (Math.pow(1 +
         * rate, nper) - 1) / rate;
         * 
         * if (pvif==0){ logger.error("Error in calculatePV >> Value is NaN");
         * return new BigDecimal(0); }
         * 
         * return new BigDecimal((-fv - pmt * (1 + rate * type) * fvifa) /
         * pvif);
         */
        logger.debug("====PV param====");
        logger.debug("p_rate" + p_rate);
        logger.debug("installmentTerm=" + installmentTerm);
        logger.debug("p_pmt=" + p_pmt);
        logger.debug("p_fv" + p_fv);
        logger.debug("type=" + type);
        logger.debug("====End PV param====");
        BigDecimal rate = p_rate;
        BigDecimal fv = p_fv;
        BigDecimal pmt = p_pmt;
        BigDecimal nper = installmentTerm;
        if (new BigDecimal(0).compareTo(rate) == 0) {
            return (fv.add(nper.multiply(pmt))).negate();
        }
//        double type1 = (type != 0) ? 1 : 0;
        BigDecimal pvif = this.bigDecimalPow(rate.add(new BigDecimal(1)), nper);
        BigDecimal fvifa = (this.bigDecimalPow(rate.add(new BigDecimal(1)), nper).subtract(new BigDecimal(1))).divide(rate, 10, BigDecimal.ROUND_HALF_UP);
        if (new BigDecimal(0).compareTo(pvif) == 0) {
            logger.error("Error in calculatePV >> Value is NaN");
            return new BigDecimal(0);
        }
        return (fv.negate().subtract(pmt.multiply(new BigDecimal(1).add(rate.multiply(new BigDecimal(type)))).multiply(fvifa))).divide(pvif, 10,
                BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getFirstCashFlows(int type, BigDecimal financeCost, BigDecimal vatTmp, BigDecimal rate, BigDecimal installment, BigDecimal monthInYear,
            BigDecimal netRate, BigDecimal rv, BigDecimal installmentCost, BigDecimal financeTotal) {
        BigDecimal first = new BigDecimal(0);
        BigDecimal pmt = calculatePMT(netRate.divide(monthInYear, 10, 0), installment, financeTotal.negate(), rv, type);
        pmt = round(pmt, 0.5);
        BigDecimal pv = calculatePV(rate.divide(monthInYear, 20, 0), installment, pmt, rv, type);
        BigDecimal subsidies = pv.add(financeTotal).divide(vatTmp, 10, 0);
        if (type == 1) { // Beginning
            first = financeCost.negate().subtract(subsidies).add(installmentCost);
        } else {
            first = financeCost.negate().subtract(subsidies);
        }
        return first;
    }

    public BigDecimal getFirstCashFlowsForLeasing(int type, BigDecimal financeCost, BigDecimal vatTmp, BigDecimal rate, BigDecimal installment,
            BigDecimal monthInYear, BigDecimal netRate, BigDecimal rv, BigDecimal installmentCost, BigDecimal financeTotal, BigDecimal subsidies) {
        logger.debug("netRate = " + netRate);
        logger.debug("installment = " + installment);
        logger.debug("financeTotal = " + financeTotal);
        logger.debug("rv = " + rv);
        BigDecimal first = new BigDecimal(0);
        logger.debug("subsidies = " + subsidies);
        if (type == 1) { // Beginning
            first = financeCost.negate().add(installmentCost).subtract(subsidies);
        } else {
            first = financeCost.negate().subtract(subsidies);
        }
        return first;
    }

    public BigDecimal getSubsidiesForLeasing(int type, BigDecimal vatTmp, BigDecimal rate, BigDecimal installment, BigDecimal monthInYear, BigDecimal netRate,
            BigDecimal rv, BigDecimal financeTotal) {
        BigDecimal subsidies = new BigDecimal(0);
        logger.debug("netRate = " + netRate);
        logger.debug("installment = " + installment);
        logger.debug("financeTotal = " + financeTotal);
        logger.debug("rv = " + rv);
        logger.debug("type = " + type);
        //if(net_rate.compareTo(rate1) != 0){
        if (net_rate.compareTo(rate1) == 1) {
            BigDecimal pmt = calculatePMT(netRate.divide(monthInYear, 10, 0), installment, financeTotal.negate(), rv, type);
            pmt = pmt.setScale(0, BigDecimal.ROUND_HALF_UP);
            logger.debug("pmt = " + pmt);
            BigDecimal pv = calculatePV(rate.divide(monthInYear, 20, BigDecimal.ROUND_HALF_UP), installment, pmt, rv, type);
            logger.debug("pv = " + pv);
            subsidies = pv.add(financeTotal).divide(vatTmp, 10, 0);
        }
        return subsidies;
    }

    public BigDecimal getFirstCashFlowsForLastInstallment(boolean haveVat, int type, BigDecimal financeCost, BigDecimal vatTmp, BigDecimal rate,
            BigDecimal installment, BigDecimal monthInYear, BigDecimal rv, BigDecimal installmentCost, BigDecimal financeTotal, BigDecimal pmt,
            BigDecimal pv_ball_amttotal) {
        logger.debug("installment = " + installment);
        logger.debug("financeTotal = " + financeTotal);
        logger.debug("rv = " + rv);
        BigDecimal first = new BigDecimal(0);
        BigDecimal subsidies = new BigDecimal(0);
        BigDecimal pv = new BigDecimal(0);
        //if(net_rate.compareTo(rate1) != 0){
        if (net_rate.compareTo(rate1) == 1) {
            logger.debug("pmt = " + pmt);
            //pv = calculatePV(rate.divide(monthInYear,10,0),
            // installment.subtract(new BigDecimal(1)), pmt, rv, 0);
            //pv = calculatePV(rate.divide(monthInYear,10,0),
            // installment.subtract(new BigDecimal(1)), pmt, new BigDecimal(0),
            // 0);
            pv = calculatePV(rate.divide(monthInYear, 20, 0), installment.subtract(new BigDecimal(1)), pmt, new BigDecimal(0), type);
            pv = pv.add(pv_ball_amttotal);
            logger.debug("pv = " + pv);
            if (haveVat) {
                subsidies = pv.add(financeTotal).divide(vatTmp, 10, 0);
            } else {
                //subsidies =
                // pv.add(financeTotal.multiply(vatTmp)).divide(vatTmp,10,0);
                subsidies = pv.add(financeTotal).divide(vatTmp, 10, 0);
            }
            //set subsidies
            subsidies_amount = subsidies;
        }
        if (haveVat) {
            //subsidies = pv.add(financeTotal).divide(vatTmp,10,0);
            logger.debug("subsidies = " + subsidies.abs());
            if (type == 1) { // Beginning
                //first =
                // financeCost.negate().add(installmentCost).subtract(subsidies);
                first = financeCost.negate().add(installmentCost).add(subsidies.abs());
            } else {
                //first = financeCost.negate().subtract(subsidies);
                first = financeCost.negate().add(subsidies.abs());
            }
        } else {
            //subsidies =
            // pv.add(financeTotal.multiply(vatTmp)).divide(vatTmp,10,0);
            logger.debug("subsidies = " + subsidies.abs());
            if (type == 1) { // Beginning
                first = financeCost.negate().add(installmentCost).add(subsidies.abs());
            } else {
                first = financeCost.negate().add(subsidies.abs());
            }
        }
        return first;
    }

    public BigDecimal getLastCashFlows(BigDecimal installmentAmount, BigDecimal rvAmount) {
        BigDecimal last = rvAmount.add(installmentAmount);
        return last;
    }

    public LoanDataM setValueToLoanDataM(LoanDataM loanDataM) {
        logger.debug("car_price_cost = " + car_price_cost);
        loanDataM.setCostOfCarPrice(car_price_cost);
        loanDataM.setVATOfCarPrice(car_price_vat);
        loanDataM.setTotalOfCarPrice(car_price_total);
        loanDataM.setCostOfDownPayment(down_payment_cost);
        loanDataM.setVATOfDownPayment(down_payment_vat);
        loanDataM.setTotalOfDownPayment(down_payment_total);
        loanDataM.setCostOfFinancialAmt(finance_cost);
        loanDataM.setVATOfFinancialAmt(finance_vat);
        loanDataM.setTotalOfFinancialAmt(finance_total);
        loanDataM.setCostOfBalloonAmt(balloon_cost);
        loanDataM.setVATOfBalloonAmt(balloon_vat);
        loanDataM.setTotalOfBalloonAmt(balloon_total);
        loanDataM.setCostOfRate1(rate1_cost);
        loanDataM.setVATOfRate1(rate1_vat);
        loanDataM.setTotalOfRate1(rate1_total);
        loanDataM.setCostOfInstallment1(installment1_cost);
        loanDataM.setVATOfInstallment1(installment1_vat);
        loanDataM.setTotalOfInstallment1(installment1_total);
        loanDataM.setCostOfHairPurchaseAmt(hire_purchase_cost);
        loanDataM.setVATOfHairPurchaseAmt(hire_purchase_vat);
        loanDataM.setTotalOfHairPurchaseAmt(hire_purchase_total);
        loanDataM.setEffectiveRate(effective_rate.multiply(new BigDecimal(100)));
        loanDataM.setIRRRate(irr_rate.multiply(new BigDecimal(100)));
        loanDataM.setCostOfpvBalloonAmt(pv_ball_amtcost);
        loanDataM.setVATOfpvBalloonAmt(pv_ball_amtvat);
        loanDataM.setTotalOfpvBalloonAmt(pv_ball_amttotal);
        loanDataM.setCostOfpv(pv_cost);
        loanDataM.setVATOfpv(pv_vat);
        loanDataM.setTotalOfpv(pv_total);
        loanDataM.setCostOfRV(rv_cost);
        loanDataM.setVATOfRV(rv_vat);
        loanDataM.setTotalOfRV(rv_total);
        loanDataM.setSubsidiesAmount(subsidies_amount);
        //loanDataM.setLoanRate(loan_rate.multiply(new BigDecimal(100)));

        return loanDataM;
    }

    public String getLastDueDate(String fDueDate, int term) {
        if (!ORIGUtility.isEmptyString(fDueDate)) {
//            String dateResult = "";
            String[] fArray = fDueDate.split("/");
            int fDate = Integer.parseInt(fArray[0]);
            int fMonth = Integer.parseInt(fArray[1]);
            int fYear = Integer.parseInt(fArray[2]);

            Calendar current = Calendar.getInstance();
            current.set(Calendar.DATE, fDate);
            current.set(Calendar.MONTH, fMonth - 1 + term - 1);
            current.set(Calendar.YEAR, fYear - 543);

            Calendar lastDueDate = Calendar.getInstance();
            lastDueDate.set(Calendar.DATE, fDate);
            lastDueDate.set(Calendar.MONTH, fMonth - 1 + term - 1);
            lastDueDate.set(Calendar.YEAR, fYear - 543);

            System.out.println(">> fDate " + fDate);
            if (fArray[0] != null && fArray[1] != null) {
                current.set(Calendar.MONTH, current.get(Calendar.MONTH) - 1);
                if (fDate > current.getActualMaximum(Calendar.DATE)) {
                    System.out.println(">> current.getActualMaximum(Calendar.DATE) " + current.getActualMaximum(Calendar.DATE));
                    lastDueDate.set(Calendar.DATE, current.getActualMaximum(Calendar.DATE));
                }
            }

            System.out.println(lastDueDate.get(Calendar.DATE) + "/" + (lastDueDate.get(Calendar.MONTH) + 1) + "/" + lastDueDate.get(Calendar.YEAR));
            System.out.println(lastDueDate.getActualMaximum(Calendar.DATE));

            return lastDueDate.get(Calendar.DATE) + "/" + (lastDueDate.get(Calendar.MONTH) + 1) + "/" + lastDueDate.get(Calendar.YEAR);
        } else {
            return null;
        }
    }

    public BigDecimal bigDecimalPow(BigDecimal base, BigDecimal power) {
        int ipow = power.intValue();
        BigDecimal result = new BigDecimal(1);
        for (int i = 0; i < ipow; i++) {
            result = result.multiply(base);
        }
        return result;
    }

    private double[] getCacheFlowStepInstallment(Vector vInstallment, BigDecimal subsidie,BigDecimal finance_cost) {
        double[] result=null;
        try {   
            int totalTerm=0;
            for (int i = 0; i < vInstallment.size(); i++) {
                InstallmentDataM prmInstallmentDataM=(InstallmentDataM)vInstallment.get(i);
               totalTerm+=prmInstallmentDataM.getTermDuration();
            }
              result=new double[totalTerm+1];
              result[0]=(finance_cost.negate().subtract(subsidie)).doubleValue();
           int termCount=0;                 
              for (int i = 0; i < vInstallment.size(); i++) {
                  InstallmentDataM prmInstallmentDataM=(InstallmentDataM)vInstallment.get(i);
                  for(int j=0;j<prmInstallmentDataM.getTermDuration();j++){
                  result[++termCount]=prmInstallmentDataM.getAmount().doubleValue();
                  }
              }
           
        } catch (RuntimeException e) {
            logger.error("Error get CachFlow:",e);
            result=null;             
        }
        return result;
    }

}
