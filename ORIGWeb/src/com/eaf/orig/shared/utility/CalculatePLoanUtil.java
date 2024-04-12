/*
 * Created on Dec 23, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.utility;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URL;
import java.util.ArrayList;

import org.apache.log4j.Logger;

//import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
//import com.eaf.orig.profile.model.PersonM;
//import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.OrigLoanMDAO;
//import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.CostFee;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CalculatePLoanUtil {
    Logger logger = Logger.getLogger(CalculatePLoanUtil.class);

    private double amountFinance = 0;

    private double apr1 = 0;

    private double apr2 = 0;

    private double apr3 = 0;

    private double apr4 = 0;

    private double tenor1 = 0;

    private double tenor2 = 0;

    private double tenor3 = 0;

    private double tenor4 = 0;

    private double type = 0;

    private double totalFinanceCharge = 0;

    private double monthlyInstallment1 = 0;

    private double monthlyInstallment2 = 0;

    private double monthlyInstallment3 = 0;

    private double monthlyInstallment4 = 0;

    private double finalPaymentAmt = 0;

    private double finalPaymentAmt2 = 0;

    private double finalPaymentAmt3 = 0;

    private double finalPaymentAmt4 = 0;

    private double totalInterestT1 = 0;

    private double totalInterestT2 = 0;

    private double totalInterestT3 = 0;

    private double totalInterestT4 = 0;

    private double principalBalance = 0;

    private double principalBalance2 = 0;

    private double principalBalance3 = 0;

    private double principalBalance4 = 0;

    //Wiroon 20100316
    private double serviceFee = 0;

    private double registrationFee = 0;

    private double transferFee = 0;

    private double prepaymentFee = 0;


    private String loanType = "";
    
    //Wiroon 20100317 get tier and term
    private String MRTA = "";
    private String accredited ;
    private String LTV = "90";
    private String segment ;
    private String campaign;

    
    public void calculateEMIFormula(String formula) {
        logger.debug("+++++++calculateEMIFormula+++++++++");
        logger.debug("formula = " + formula);
        if (formula.equals("VANI")) {
            calVanilla();
        } else if (formula.equals("BNPL")) {
            calBNPL();
        } else if (formula.equals("STUP") && apr1 == 0) {
            calStepUpZero();
        } else if (formula.equals("STUP") || formula.equals("STDN")) {
            calStepUpDown();
        } else if (formula.equals("PHOL")) {
            calPaymentHoliday();
        } else if (formula.equals("BNPI")) {
            calBNPI();
        }
    }

    /**
     * 1. Single Tier
     *  
     */
    private void calVanilla() {
        logger.debug("calVanilla");
        /*
         * double x = amountFinance * Math.pow((1+apr1/12), tenor1+tenor2);
         * logger.debug("x = "+x); double y =((1 + apr1/12 * type) *
         * (Math.pow((1+apr1/12), tenor1+tenor2) - 1) / (apr1/12));
         * logger.debug("y = "+y);
         * 
         * totalFinanceCharge = ceilPrecision(x / y * (tenor1 + tenor2) -
         * amountFinance,2); logger.debug("totalFinanceCharge =
         * "+totalFinanceCharge); monthlyInstallment1 = x / y;
         * logger.debug("monthlyInstallment1 = "+monthlyInstallment1);
         * finalPaymentAmt = (amountFinance + totalFinanceCharge) -
         * (Math.ceil(monthlyInstallment1) * (tenor1+tenor2 - 1));
         * logger.debug("finalPaymentAmt = "+finalPaymentAmt);
         */
        // apr = Rate
        // Term = tenor
        monthlyInstallment1 = (amountFinance * (apr1 / 100) * (tenor1 / 12) + amountFinance) / tenor1;
        
        //TODO Dao get value from ORIG_COST_FEE
        //Service Fee
        try {
//            OrigLoanMDAO dao = ORIGDAOFactory.getOrigLoanMDAO();
//            ArrayList serviceFeeList = dao.getCostFee("SRV");
        	
        	ArrayList serviceFeeList = PLORIGEJBService.getORIGDAOUtilLocal().getCostFee("SRV");
        	
            for (int i = 0; i < serviceFeeList.size(); i++) {
                CostFee costFee = (CostFee) serviceFeeList.get(i);
                logger.debug("description >>"+costFee.getDescription());
                logger.debug("min loan >>"+costFee.getMinLoanAmt());
                logger.debug("max loan >>"+costFee.getMaxLoanAmt());
                
                if (amountFinance >= costFee.getMinLoanAmt() && amountFinance <= costFee.getMaxLoanAmt()) {
                    logger.debug("Fix value >>"+costFee.getFixValue());
                    serviceFee = costFee.getFixValue();
                    break;
                }
            }
        } catch (Exception ex) {
            logger.error("Error :" + ex, ex);
        }

        //Registration Fee
        if (loanType.equals("HLR") || loanType.equals("HLE")) {
            registrationFee = amountFinance / 100;
            logger.debug("amount >>"+amountFinance * (1 / 100));
            logger.debug("1/100 >>"+(1/100));
            logger.debug("registrationFee HLR HLE>>"+registrationFee);
        } else {
            registrationFee = amountFinance * (0.01 / 100);
            logger.debug("registrationFee >>"+registrationFee);
        }

        //Transfer Fee
        //transferFee = amountFinance * (0.01 / 100);
        try {
//            OrigLoanMDAO dao = ORIGDAOFactory.getOrigLoanMDAO();
//            ArrayList serviceFeeList = dao.getCostFee("TRN");
        	
        	ArrayList serviceFeeList = PLORIGEJBService.getORIGDAOUtilLocal().getCostFee("TRN");
        	
            CostFee costFee = (CostFee) serviceFeeList.get(0);
            transferFee = amountFinance * (costFee.getPercentageValue() / 100);
        } catch (Exception ex) {
            logger.error("Error :" + ex, ex);
        }

        //Prepayment Fee
        //prepaymentFee = amountFinance * (3 / 100);
        try {
//            OrigLoanMDAO dao = ORIGDAOFactory.getOrigLoanMDAO();
//            ArrayList serviceFeeList = dao.getCostFee("PRE");
        	
        	ArrayList serviceFeeList = PLORIGEJBService.getORIGDAOUtilLocal().getCostFee("PRE");
        	
            CostFee costFee = (CostFee) serviceFeeList.get(0);
            prepaymentFee = amountFinance * (costFee.getPercentageValue() / 100);
        } catch (Exception ex) {
            logger.error("Error :" + ex, ex);
        }
        
        logger.debug("finalPaymentAmt = " + finalPaymentAmt);
    }

    /**
     * 2. BNPL
     *  
     */
    private void calBNPL() {
        logger.debug("calBNPL");
        /*
         * totalInterestT1 = ceilPrecision(amountFinance * (apr1/12) *
         * tenor1,2); principalBalance = amountFinance + (amountFinance *
         * apr1/12 * tenor1);
         * 
         * double x = principalBalance * Math.pow((1+apr2/12), tenor2); double y
         * =((1 + apr2/12 * type) * (Math.pow((1+apr2/12), tenor2) - 1) /
         * (apr2/12));
         * 
         * totalFinanceCharge = ceilPrecision(x / y * tenor2 - amountFinance,2);
         * monthlyInstallment1 = 0; monthlyInstallment2= x / y; finalPaymentAmt =
         * (amountFinance + totalFinanceCharge) -
         * (Math.ceil(monthlyInstallment2) * (tenor2 - 1));
         */

      

        //Wiroon 20100316
        double allTerm = (tenor1) + (tenor2) + (tenor3) + (tenor4);
        monthlyInstallment1 = (amountFinance * (apr1 / 100) * (tenor1 / 12) + amountFinance) / allTerm;
        monthlyInstallment2 = (amountFinance * (apr2 / 100) * (tenor2 / 12) + amountFinance) / allTerm;
        monthlyInstallment3 = (amountFinance * (apr3 / 100) * (tenor3 / 12) + amountFinance) / allTerm;
        monthlyInstallment4 = (amountFinance * (apr4 / 100) * (tenor4 / 12) + amountFinance) / allTerm;

        //TODO Dao get value from ORIG_COST_FEE
        //Service Fee
        try {
//            OrigLoanMDAO dao = ORIGDAOFactory.getOrigLoanMDAO();
//            ArrayList serviceFeeList = dao.getCostFee("SRV");
        	
        	ArrayList serviceFeeList = PLORIGEJBService.getORIGDAOUtilLocal().getCostFee("SRV");
        	
        	
            for (int i = 0; i < serviceFeeList.size(); i++) {
                CostFee costFee = (CostFee) serviceFeeList.get(i);
                logger.debug("description >>"+costFee.getDescription());
                logger.debug("min loan >>"+costFee.getMinLoanAmt());
                logger.debug("max loan >>"+costFee.getMaxLoanAmt());
                
                if (amountFinance >= costFee.getMinLoanAmt() && amountFinance <= costFee.getMaxLoanAmt()) {
                    logger.debug("Fix value >>"+costFee.getFixValue());
                    serviceFee = costFee.getFixValue();
                    break;
                }
            }
        } catch (Exception ex) {
            logger.error("Error :" + ex, ex);
        }

        //Registration Fee
        if (loanType.equals("HLR") || loanType.equals("HLE")) {
            registrationFee = amountFinance / 100;
            logger.debug("amount >>"+amountFinance * (1 / 100));
            logger.debug("1/100 >>"+(1/100));
            logger.debug("registrationFee HLR HLE>>"+registrationFee);
        } else {
            registrationFee = amountFinance * (0.01 / 100);
            logger.debug("registrationFee >>"+registrationFee);
        }

        //Transfer Fee
        //transferFee = amountFinance * (0.01 / 100);
        try {
//            OrigLoanMDAO dao = ORIGDAOFactory.getOrigLoanMDAO();
//            ArrayList serviceFeeList = dao.getCostFee("TRN");
        	
        	 ArrayList serviceFeeList = PLORIGEJBService.getORIGDAOUtilLocal().getCostFee("TRN");
            CostFee costFee = (CostFee) serviceFeeList.get(0);
            transferFee = amountFinance * (costFee.getPercentageValue() / 100);
        } catch (Exception ex) {
            logger.error("Error :" + ex, ex);
        }

        //Prepayment Fee
        //prepaymentFee = amountFinance * (3 / 100);
        try {
//            OrigLoanMDAO dao = ORIGDAOFactory.getOrigLoanMDAO();
//            ArrayList serviceFeeList = dao.getCostFee("PRE");
        	 ArrayList serviceFeeList = PLORIGEJBService.getORIGDAOUtilLocal().getCostFee("PRE");
            
            CostFee costFee = (CostFee) serviceFeeList.get(0);
            prepaymentFee = amountFinance * (costFee.getPercentageValue() / 100);
        } catch (Exception ex) {
            logger.error("Error :" + ex, ex);
        }

        logger.debug("finalPaymentAmt = " + finalPaymentAmt);

    }

   

    /**
     * 3. Step Up/Down
     *  
     */
    private void calStepUpDown() {
        logger.debug("calStepUpDown");
        double x = amountFinance * Math.pow((1 + apr1 / 12), tenor1 + tenor2);
        double y = ((1 + apr1 / 12 * type) * (Math.pow((1 + apr1 / 12), tenor1 + tenor2) - 1) / (apr1 / 12));
        monthlyInstallment1 = x / y;

        double sumTerm = 0;
        for (int i = 1; i <= tenor1; i++) {
            sumTerm = sumTerm + ((Math.ceil(monthlyInstallment1) - apr1 / 12 * amountFinance) * Math.pow((1 + apr1 / 12), i - 1));
        }

        totalInterestT1 = ceilPrecision((Math.ceil(monthlyInstallment1) * tenor1) - sumTerm, 2);
        principalBalance = amountFinance - x / y * tenor1 + totalInterestT1;

        double a = principalBalance * Math.pow((1 + apr2 / 12), tenor2);
        double b = ((1 + apr2 / 12 * type) * (Math.pow((1 + apr2 / 12), tenor2) - 1) / (apr2 / 12));
        monthlyInstallment2 = a / b;

        totalFinanceCharge = ceilPrecision(x / y * tenor1 + a / b * tenor2 - amountFinance, 2);
        finalPaymentAmt = (amountFinance + totalFinanceCharge) - (Math.ceil(monthlyInstallment1) * tenor1 + Math.ceil(monthlyInstallment2) * (tenor2 - 1));
    }

    /**
     * 4. Payment Holiday
     *  
     */
    private void calPaymentHoliday() {
        logger.debug("calPaymentHoliday");
        double x = amountFinance * Math.pow((1 + apr1 / 12), tenor1 + tenor2);
        double y = ((1 + apr1 / 12 * type) * (Math.pow((1 + apr1 / 12), tenor1 + tenor2) - 1) / (apr1 / 12));

        monthlyInstallment1 = x / y;
        totalFinanceCharge = ceilPrecision(x / y * (tenor1 + tenor2) - amountFinance, 2);
        finalPaymentAmt = (amountFinance + totalFinanceCharge) - (Math.ceil(monthlyInstallment1) * (tenor1 + tenor2 - 1));
    }

    /**
     * 5. BNPI
     *  
     */
    private void calBNPI() {
        logger.debug("calBNPI");
        totalInterestT1 = ceilPrecision(amountFinance * (apr1 / 12) * tenor1, 2);
        principalBalance = amountFinance;

        double x = principalBalance * Math.pow((1 + apr2 / 12), tenor2);
        double y = ((1 + apr2 / 12 * type) * (Math.pow((1 + apr2 / 12), tenor2) - 1) / (apr2 / 12));

        totalFinanceCharge = ceilPrecision((amountFinance * apr1 / 12 * tenor1) + x / y * tenor2 - amountFinance, 2);
        monthlyInstallment1 = amountFinance * (apr1 / 12);
        monthlyInstallment2 = x / y;
        finalPaymentAmt = (amountFinance + totalFinanceCharge) - (Math.ceil(monthlyInstallment1) * tenor1 + Math.ceil(monthlyInstallment2) * (tenor2 - 1));
    }

    /**
     * 6. Step up for Zero
     *  
     */
    private void calStepUpZero() {
        logger.debug("calStepUpZero");
        monthlyInstallment1 = Math.ceil(amountFinance / (tenor1 + tenor2));
        principalBalance = amountFinance - (monthlyInstallment1 * tenor1);
        monthlyInstallment2 = apr2 == 0 ? 0 : (Math.ceil((principalBalance * (Math.pow(1 + apr2 / 12, tenor2)))
                / ((Math.pow(((1 + apr2 / 12 * type) * (1 + apr2 / 12)), tenor2) - 1) / (apr2 / 12))));
        double tempForCal = 0;
        for (int i = 1; i <= tenor1; i++) {
            tempForCal += (monthlyInstallment1 - apr1 / 12 * amountFinance) * Math.pow(1 + apr1 / 12, i - 1);
        }
        totalInterestT1 = ceilPrecision((monthlyInstallment1 * tenor1) - tempForCal, 2);
        totalFinanceCharge = apr2 > 0 ? ceilPrecision((principalBalance * Math.pow(1 + apr2 / 12, tenor2))
                / ((1 + apr2 / 12 * type) * (Math.pow(1 + apr2 / 12, tenor2) - 1) / (apr2 / 12)) * tenor2 - principalBalance, 2) : ceilPrecision(
                (amountFinance * Math.pow(1 + apr1 / 12, tenor1 + tenor2))
                        / ((1 + apr1 / 12 * type) * (Math.pow(1 + apr1 / 12, tenor1 + tenor2) - 1) / (apr1 / 12)) * tenor1
                        + (principalBalance * Math.pow(1 + apr2 / 12, tenor2)) / ((1 + apr2 / 12 * type) * (Math.pow(1 + apr2 / 12, tenor2) - 1) / (apr2 / 12))
                        * tenor2 - amountFinance, 2);
        finalPaymentAmt = (amountFinance + totalFinanceCharge) - (monthlyInstallment1 * tenor1 + monthlyInstallment2 * (tenor2 - 1));
    }

    public static double ceilPrecision(double value, int precision) {
        double multiply = Math.pow(10, precision);
        return Math.ceil(value * multiply) / multiply;
    }

    /**
     * Returns the amountFinance.
     * 
     * @return double
     */
    public double getAmountFinance() {
        return amountFinance;
    }

    /**
     * Returns the apr1.
     * 
     * @return double
     */
    public double getApr1() {
        return apr1;
    }

    /**
     * Returns the apr2.
     * 
     * @return double
     */
    public double getApr2() {
        return apr2;
    }

    /**
     * Returns the finalPaymentAmt.
     * 
     * @return double
     */
    public double getFinalPaymentAmt() {
        return finalPaymentAmt;
    }

    /**
     * Returns the monthlyInstallment1.
     * 
     * @return double
     */
    public double getMonthlyInstallment1() {
        return monthlyInstallment1;
    }

    /**
     * Returns the monthlyInstallment2.
     * 
     * @return double
     */
    public double getMonthlyInstallment2() {
        return monthlyInstallment2;
    }

    /**
     * Returns the principalBalance.
     * 
     * @return double
     */
    public double getPrincipalBalance() {
        return principalBalance;
    }

    /**
     * Returns the tenor1.
     * 
     * @return double
     */
    public double getTenor1() {
        return tenor1;
    }

    /**
     * Returns the tenor2.
     * 
     * @return double
     */
    public double getTenor2() {
        return tenor2;
    }

    /**
     * Returns the totalFinanceCharge.
     * 
     * @return double
     */
    public double getTotalFinanceCharge() {
        return totalFinanceCharge;
    }

    /**
     * Returns the totalInterestT1.
     * 
     * @return double
     */
    public double getTotalInterestT1() {
        return totalInterestT1;
    }

    /**
     * Returns the type.
     * 
     * @return double
     */
    public double getType() {
        return type;
    }

    /**
     * Sets the amountFinance.
     * 
     * @param amountFinance
     *            The amountFinance to set
     */
    public void setAmountFinance(double amountFinance) {
        this.amountFinance = amountFinance;
    }

    /**
     * Sets the apr1.
     * 
     * @param apr1
     *            The apr1 to set
     */
    public void setApr1(double apr1) {
        this.apr1 = apr1;
    }

    /**
     * Sets the apr2.
     * 
     * @param apr2
     *            The apr2 to set
     */
    public void setApr2(double apr2) {
        this.apr2 = apr2;
    }

    /**
     * Sets the tenor1.
     * 
     * @param tenor1
     *            The tenor1 to set
     */
    public void setTenor1(double tenor1) {
        this.tenor1 = tenor1;
    }

    /**
     * Sets the tenor2.
     * 
     * @param tenor2
     *            The tenor2 to set
     */
    public void setTenor2(double tenor2) {
        this.tenor2 = tenor2;
    }

    /**
     * Sets the type.
     * 
     * @param type
     *            The type to set
     */
    public void setType(double type) {
        this.type = type;
    }

    /**
     * @return Returns the apr3.
     */
    public double getApr3() {
        return apr3;
    }

    /**
     * @param apr3
     *            The apr3 to set.
     */
    public void setApr3(double apr3) {
        this.apr3 = apr3;
    }

    /**
     * @return Returns the apr4.
     */
    public double getApr4() {
        return apr4;
    }

    /**
     * @param apr4
     *            The apr4 to set.
     */
    public void setApr4(double apr4) {
        this.apr4 = apr4;
    }

    /**
     * @return Returns the tenor3.
     */
    public double getTenor3() {
        return tenor3;
    }

    /**
     * @param tenor3
     *            The tenor3 to set.
     */
    public void setTenor3(double tenor3) {
        this.tenor3 = tenor3;
    }

    /**
     * @return Returns the tenor4.
     */
    public double getTenor4() {
        return tenor4;
    }

    /**
     * @param tenor4
     *            The tenor4 to set.
     */
    public void setTenor4(double tenor4) {
        this.tenor4 = tenor4;
    }

    /**
     * @return Returns the monthlyInstallment3.
     */
    public double getMonthlyInstallment3() {
        return monthlyInstallment3;
    }

    /**
     * @return Returns the monthlyInstallment4.
     */
    public double getMonthlyInstallment4() {
        return monthlyInstallment4;
    }

    /**
     * @return Returns the finalPaymentAmt2.
     */
    public double getFinalPaymentAmt2() {
        return finalPaymentAmt2;
    }

    /**
     * @return Returns the finalPaymentAmt3.
     */
    public double getFinalPaymentAmt3() {
        return finalPaymentAmt3;
    }

    /**
     * @return Returns the finalPaymentAmt4.
     */
    public double getFinalPaymentAmt4() {
        return finalPaymentAmt4;
    }

    /**
     * @param finalPaymentAmt
     *            The finalPaymentAmt to set.
     */
    public void setFinalPaymentAmt(double finalPaymentAmt) {
        this.finalPaymentAmt = finalPaymentAmt;
    }

    /**
     * @return Returns the totalInterestT2.
     */
    public double getTotalInterestT2() {
        return totalInterestT2;
    }

    /**
     * @return Returns the totalInterestT3.
     */
    public double getTotalInterestT3() {
        return totalInterestT3;
    }

    /**
     * @return Returns the totalInterestT4.
     */
    public double getTotalInterestT4() {
        return totalInterestT4;
    }

    /**
     * @return Returns the prepaymentFee.
     */
    public double getPrepaymentFee() {
        return prepaymentFee;
    }

    /**
     * @param prepaymentFee
     *            The prepaymentFee to set.
     */
    public void setPrepaymentFee(double prepaymentFee) {
        this.prepaymentFee = prepaymentFee;
    }

    /**
     * @return Returns the registrationFee.
     */
    public double getRegistrationFee() {
        return registrationFee;
    }

    /**
     * @param registrationFee
     *            The registrationFee to set.
     */
    public void setRegistrationFee(double registrationFee) {
        this.registrationFee = registrationFee;
    }

    /**
     * @return Returns the serviceFee.
     */
    public double getServiceFee() {
        return serviceFee;
    }

    /**
     * @param serviceFee
     *            The serviceFee to set.
     */
    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    /**
     * @return Returns the transferFee.
     */
    public double getTransferFee() {
        return transferFee;
    }

    /**
     * @param transferFee
     *            The transferFee to set.
     */
    public void setTransferFee(double transferFee) {
        this.transferFee = transferFee;
    }

    /**
     * @return Returns the loanType.
     */
    public String getLoanType() {
        return loanType;
    }

    /**
     * @param loanType
     *            The loanType to set.
     */
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    /**
     * @return Returns the MRTA.
     */
    public String getMRTA() {
        return MRTA;
    }

    /**
     * @param MRTA
     *            The MRTA to set.
     */
    public void setMRTA(String MRTA) {
        MRTA = MRTA;
    }
    
    
    /**
     * @return Returns the accredited.
     */
    public String getAccredited() {
        return accredited;
    }
    /**
     * @param accredited The accredited to set.
     */
    public void setAccredited(String accredited) {
        this.accredited = accredited;
    }
    /**
     * @return Returns the lTV.
     */
    public String getLTV() {
        return LTV;
    }
    /**
     * @param ltv The lTV to set.
     */
    public void setLTV(String ltv) {
        LTV = ltv;
    }
    /**
     * @return Returns the segment.
     */
    public String getSegment() {
        return segment;
    }
    /**
     * @param segment The segment to set.
     */
    public void setSegment(String segment) {
        this.segment = segment;
    }
    
    
    /**
     * @return Returns the campaign.
     */
    public String getCampaign() {
        return campaign;
    }
    /**
     * @param campaign The campaign to set.
     */
    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }
}
