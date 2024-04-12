package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
import com.eaf.orig.cache.properties.IntSchemeCacheProperties;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.CalculatePLoanUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

public class CalculatePLoanServlet extends HttpServlet implements Servlet {
    Logger logger = Logger.getLogger(CalculatePLoanServlet.class);

    /*
     * (non-Java-doc)
     * 
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public CalculatePLoanServlet() {
        super();
    }

    /*
     * (non-Java-doc)
     * 
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0,
     *      HttpServletResponse arg1)
     */
    protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        doPost(arg0, arg1);
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
            String scheme_code = (String) request.getParameter("scheme_code");
            BigDecimal loan_amount_applied = utility.stringToBigDecimal(request.getParameter("loan_amount_applied"));
            utility.stringToBigDecimal(request.getParameter("amount_finance"));
            BigDecimal acceptance_fee_percent = utility.stringToBigDecimal(request.getParameter("acceptance_fee_percent"));
            BigDecimal acceptance_fee = new BigDecimal(0);
            BigDecimal acceptance_fee3 = new BigDecimal(0);
            BigDecimal acceptance_fee4 = new BigDecimal(0);

            BigDecimal fee_discount_percent = utility.stringToBigDecimal(request.getParameter("fee_discount_percent"));
            BigDecimal fee_discount = new BigDecimal(0);
            BigDecimal fee_discount3 = new BigDecimal(0);
            BigDecimal fee_discount4 = new BigDecimal(0);

            BigDecimal monthly_instalment_one = new BigDecimal(0);
            BigDecimal monthly_instalment_two = new BigDecimal(0);
            BigDecimal monthly_instalment_three = new BigDecimal(0);
            BigDecimal monthly_instalment_four = new BigDecimal(0);

            BigDecimal last_instalment_one = new BigDecimal(0);
            BigDecimal last_instalment_two = new BigDecimal(0);
            BigDecimal last_instalment_three = new BigDecimal(0);
            BigDecimal last_instalment_four = new BigDecimal(0);

            BigDecimal first_interest_rate = new BigDecimal(0);
            BigDecimal second_interest_rate = new BigDecimal(0);
            BigDecimal third_interest_rate = new BigDecimal(0);
            BigDecimal forth_interest_rate = new BigDecimal(0);

            BigDecimal serviceFee = new BigDecimal(0);
            BigDecimal registrationFee = new BigDecimal(0);
            BigDecimal transferFee = new BigDecimal(0);
            BigDecimal prepaymentFee = new BigDecimal(0);

            int first_tier_term = 0;
            int second_tier_term = 0;
            int third_tier_term = 0;
            int forth_tier_term = 0;

            BigDecimal irr_rate = new BigDecimal(0);

            acceptance_fee = loan_amount_applied.multiply((acceptance_fee_percent.divide(new BigDecimal(100), 2, 0)));
            fee_discount = acceptance_fee.multiply((fee_discount_percent.divide(new BigDecimal(100), 2, 0)));
            BigDecimal amount_finance = (loan_amount_applied.add(acceptance_fee)).subtract(fee_discount);

            acceptance_fee3 = amount_finance.multiply((acceptance_fee_percent.divide(new BigDecimal(100), 2, 0)));
            fee_discount3 = acceptance_fee3.multiply((fee_discount_percent.divide(new BigDecimal(100), 2, 0)));
            BigDecimal amount_finance3 = (amount_finance.add(acceptance_fee3)).subtract(fee_discount3);

            acceptance_fee4 = amount_finance3.multiply((acceptance_fee_percent.divide(new BigDecimal(100), 2, 0)));
            fee_discount4 = acceptance_fee4.multiply((fee_discount_percent.divide(new BigDecimal(100), 2, 0)));
            BigDecimal amount_finance4 = (amount_finance3.add(acceptance_fee4)).subtract(fee_discount4);

            HashMap h = TableLookupCache.getCacheStructure();
            Vector dataVect = (Vector) (h.get("IntScheme"));
            IntSchemeCacheProperties data = new IntSchemeCacheProperties();
            if (dataVect != null && dataVect.size() > 0) {
                for (int i = 0; i < dataVect.size(); i++) {
                    IntSchemeCacheProperties temp = (IntSchemeCacheProperties) dataVect.get(i);
                    if (temp != null && temp.getSchemeCode().equalsIgnoreCase(scheme_code)) {
                        data = temp;

                        //TODO
                        if(request.getParameter("first_tier_term").equals("0.00") || request.getParameter("second_tier_term").equals("0.00") || request.getParameter("third_tier_term").equals("0.00") ||request.getParameter("forth_tier_term").equals("0.00")){
	                        first_interest_rate = temp.getIntRate1();
	                        second_interest_rate = temp.getIntRate2();
	                        third_interest_rate = temp.getIntRate3();
	                        forth_interest_rate = temp.getIntRate4();
	                      
	                        first_tier_term = temp.getTerm1();
	                        second_tier_term = temp.getTerm2();
	                        third_tier_term = temp.getTerm3();
	                        forth_tier_term = temp.getTerm4();
                        } else {                        
	                        first_interest_rate = new BigDecimal(request.getParameter("first_interest_rate"));
	                        second_interest_rate = new BigDecimal(request.getParameter("second_interest_rate"));
	                        third_interest_rate = new BigDecimal(request.getParameter("third_interest_rate"));
	                        forth_interest_rate = new BigDecimal(request.getParameter("forth_interest_rate"));                        
	                        
	                        first_tier_term = Integer.parseInt(request.getParameter("first_tier_term"));
	                      	second_tier_term = Integer.parseInt(request.getParameter("second_tier_term"));
	                      	third_tier_term = Integer.parseInt(request.getParameter("third_tier_term"));
	                      	forth_tier_term = Integer.parseInt(request.getParameter("forth_tier_term"));
                        }
                        


                        break;
                    }
                }
                CalculatePLoanUtil calUtil = new CalculatePLoanUtil();
                calUtil.setApr1(first_interest_rate.doubleValue());
                calUtil.setApr2(second_interest_rate.doubleValue());
                calUtil.setApr3(third_interest_rate.doubleValue());
                calUtil.setApr4(forth_interest_rate.doubleValue());

                calUtil.setTenor1(first_tier_term);
                calUtil.setTenor2(second_tier_term);
                calUtil.setTenor3(third_tier_term);
                calUtil.setTenor4(forth_tier_term);

                //TODO
                calUtil.setLoanType(request.getParameter("loan_type"));
                
                ApplicationDataM appForm = formHandler.getAppForm();                
                PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(appForm, OrigConstant.PERSONAL_TYPE_APPLICANT);
                calUtil.setSegment(personalInfoDataM.getCustomerSegment());
                
                calUtil.setAmountFinance(amount_finance.doubleValue());

                //**Default formula for cal**//
                if (second_interest_rate.doubleValue() <= 0 || second_tier_term <= 0) {
                    calUtil.calculateEMIFormula("VANI");
                } else {
                    calUtil.calculateEMIFormula("BNPL");
                }
                //***************************//
                monthly_instalment_one = new BigDecimal(calUtil.getMonthlyInstallment1());
                monthly_instalment_two = new BigDecimal(calUtil.getMonthlyInstallment2());
                monthly_instalment_three = new BigDecimal(calUtil.getMonthlyInstallment3());
                monthly_instalment_four = new BigDecimal(calUtil.getMonthlyInstallment4());

                serviceFee = new BigDecimal(calUtil.getServiceFee());
                registrationFee = new BigDecimal(calUtil.getRegistrationFee());
                transferFee = new BigDecimal(calUtil.getTransferFee());
                prepaymentFee = new BigDecimal(calUtil.getPrepaymentFee());

                last_instalment_one = new BigDecimal(calUtil.getFinalPaymentAmt());
                if (second_interest_rate.doubleValue() > 0 && second_tier_term > 0) {
                    //last_instalment_two = last_instalment_one;
                    //last_instalment_one = monthly_instalment_one;
                }
                //**Cal IRR**//
                irr_rate = new BigDecimal(0);
                //***********//
            }

            //Rewrite
            StringBuffer sb = new StringBuffer("");
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            sb.append("<list>");
            sb.append("<field name=\"loan_amount_applied\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(loan_amount_applied.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"amount_finance\">" + ORIGDisplayFormatUtil.displayCommaNumber(amount_finance.setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");
            sb.append("<field name=\"amount_finance3\">" + ORIGDisplayFormatUtil.displayCommaNumber(amount_finance3.setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");
            sb.append("<field name=\"amount_finance4\">" + ORIGDisplayFormatUtil.displayCommaNumber(amount_finance4.setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");

            sb.append("<field name=\"acceptance_fee_percent\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(acceptance_fee_percent.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"acceptance_fee\">" + ORIGDisplayFormatUtil.displayCommaNumber(acceptance_fee.setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");

            sb.append("<field name=\"acceptance_fee3\">" + ORIGDisplayFormatUtil.displayCommaNumber(acceptance_fee3.setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");
            sb.append("<field name=\"acceptance_fee4\">" + ORIGDisplayFormatUtil.displayCommaNumber(acceptance_fee4.setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");

            sb.append("<field name=\"fee_discount_percent\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(fee_discount_percent.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"fee_discount\">" + ORIGDisplayFormatUtil.displayCommaNumber(fee_discount.setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");
            sb.append("<field name=\"fee_discount3\">" + ORIGDisplayFormatUtil.displayCommaNumber(fee_discount3.setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");
            sb.append("<field name=\"fee_discount4\">" + ORIGDisplayFormatUtil.displayCommaNumber(fee_discount4.setScale(2, BigDecimal.ROUND_HALF_UP))
                    + "</field>");

            sb.append("<field name=\"monthly_instalment_one\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(monthly_instalment_one.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"monthly_instalment_two\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(monthly_instalment_two.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"monthly_instalment_three\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(monthly_instalment_three.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"monthly_instalment_four\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(monthly_instalment_four.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");

            sb.append("<field name=\"last_instalment_one\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(last_instalment_one.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"last_instalment_two\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(last_instalment_two.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"last_instalment_three\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(last_instalment_three.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"last_instalment_four\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(last_instalment_four.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");

            sb.append("<field name=\"first_interest_rate\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(first_interest_rate.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"second_interest_rate\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(second_interest_rate.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"third_interest_rate\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(third_interest_rate.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("<field name=\"forth_interest_rate\">"
                    + ORIGDisplayFormatUtil.displayCommaNumber(forth_interest_rate.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");

            sb.append("<field name=\"first_tier_term\">" + ORIGDisplayFormatUtil.formatNumber("0", first_tier_term) + "</field>");
            sb.append("<field name=\"second_tier_term\">" + ORIGDisplayFormatUtil.formatNumber("0", second_tier_term) + "</field>");
            sb.append("<field name=\"third_tier_term\">" + ORIGDisplayFormatUtil.formatNumber("0", third_tier_term) + "</field>");
            sb.append("<field name=\"forth_tier_term\">" + ORIGDisplayFormatUtil.formatNumber("0", forth_tier_term) + "</field>");

            //TODO
            sb.append("<field name=\"service_fee\">" + ORIGDisplayFormatUtil.formatNumber("0", serviceFee) + "</field>");
            sb.append("<field name=\"registration_fee\">" + ORIGDisplayFormatUtil.formatNumber("0", registrationFee) + "</field>");
            sb.append("<field name=\"transfer_fee\">" + ORIGDisplayFormatUtil.formatNumber("0", transferFee) + "</field>");
            sb.append("<field name=\"prepayment_fee\">" + ORIGDisplayFormatUtil.formatNumber("0", prepaymentFee) + "</field>");

            sb.append("<field name=\"irr_rate\">" + ORIGDisplayFormatUtil.displayCommaNumber(irr_rate.setScale(2, BigDecimal.ROUND_HALF_UP)) + "</field>");
            sb.append("</list>");

            response.setContentType("text/xml;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache,no-store");
            response.setDateHeader("Expires", 0);
            PrintWriter pw = response.getWriter();
            pw.write(sb.toString());
            pw.close();
        } catch (Exception e) {
            logger.error("Error in CalculatePLoanServlet >> ", e);
        }
    }

}