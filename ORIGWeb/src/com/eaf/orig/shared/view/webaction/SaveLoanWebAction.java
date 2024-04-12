/*
 * Created on Sep 27, 2007
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
package com.eaf.orig.shared.view.webaction;

import java.math.BigDecimal;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PopulatePopupM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author weeraya
 *
 * Type: SaveLoanWebAction
 */
public class SaveLoanWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(SaveLoanWebAction.class);
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
         
        return null;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
         
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
         
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        try{
	        ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
	        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
	        String userRole = (String) userM.getRoles().elementAt(0);
	        ORIGUtility utility = new ORIGUtility();
	        String dateFormat = "dd/mm/yyyy";
	        
	        ApplicationDataM applicationDataM = formHandler.getAppForm();
	        Vector loanVect = applicationDataM.getLoanVect();
	    	LoanDataM loanDataM = null;
	    	if(loanVect!=null&&loanVect.size() > 0){
	    		loanDataM = (LoanDataM) loanVect.elementAt(0);
	    	}
	        
	        if(loanDataM == null){
	            loanDataM = new LoanDataM();
	            formHandler.getAppForm().getLoanVect().add(loanDataM);
	        }
	        
	        if(OrigConstant.ROLE_XCMR.equals(userRole)){
	        	String campaign = getRequest().getParameter("campaign");
	        	loanDataM.setCampaign(campaign);
	        	
	        	/*boolean changeData = utility.checkExceptionForXCMR(loanDataM);
		        if(changeData){
		        	applicationDataM.setXcmrOverrideBy(null);
		        }*/
	        }else{
		        String loan_type = getRequest().getParameter("loan_type");
		        String mkt_code = getRequest().getParameter("mkt_code");
		        String campaign = getRequest().getParameter("campaign");
		        String internal = getRequest().getParameter("internal");
		        String reason_camgaign = getRequest().getParameter("reason_camgaign");
		        String external = getRequest().getParameter("external");
		        String override_by = getRequest().getParameter("override_by");
		        String credir_approval = getRequest().getParameter("credir_approval");
		        String override_date = getRequest().getParameter("override_date");
		        String appraisal_price = getRequest().getParameter("appraisal_price");
		        String collection_code = getRequest().getParameter("collection_code");
		        String balloon_flag = getRequest().getParameter("balloon_flag");
		        String balloon = getRequest().getParameter("balloon");
		        String bank = getRequest().getParameter("bank");
		        String branch = getRequest().getParameter("branch");
		        String account_no = getRequest().getParameter("account_no");
		        String account_name = getRequest().getParameter("account_name");
		        String booking_date = getRequest().getParameter("booking_date");
		        String contact_date = getRequest().getParameter("contact_date");
		        String collector_code = getRequest().getParameter("collector_code");
		        String first_due_date = getRequest().getParameter("first_due_date");
		        String end_due_date = getRequest().getParameter("end_due_date");
		        String down_type = getRequest().getParameter("down_type");
		        String down_amount = getRequest().getParameter("down_amount");
		        String payment_type = getRequest().getParameter("payment_type");
		        String first_installment = getRequest().getParameter("first_installment");
		        String net_rate = getRequest().getParameter("net_rate");
		        String special_hire_charge = getRequest().getParameter("special_hire_charge");
		        String document_date = getRequest().getParameter("document_date");
		        String cheque_payment_date = getRequest().getParameter("cheque_payment_date");
		        
		        String vat = getRequest().getParameter("vat");
		        String car_price_cost = getRequest().getParameter("car_price_cost");
		        String car_price_vat = getRequest().getParameter("car_price_vat");
		        String car_price_total = getRequest().getParameter("car_price_total");
		        String down_payment_cost = getRequest().getParameter("down_payment_cost");
		        String down_payment_vat = getRequest().getParameter("down_payment_vat");
		        String down_payment_total = getRequest().getParameter("down_payment_total");
		        String finance_cost = getRequest().getParameter("finance_cost");
		        String finance_vat = getRequest().getParameter("finance_vat");
		        String finance_total = getRequest().getParameter("finance_total");
		        String balloon_cost = getRequest().getParameter("balloon_cost");
		        String balloon_vat = getRequest().getParameter("balloon_vat");
		        String balloon_total = getRequest().getParameter("balloon_total");
		        
//		        String pv_ball_amt = getRequest().getParameter("pv_ball_amt");
//		        String pv_ball_amtcost = getRequest().getParameter("pv_ball_amtcost");
//		        String pv_ball_amtvat = getRequest().getParameter("pv_ball_amtvat");
//		        String pv_ball_amttotal = getRequest().getParameter("pv_ball_amttotal");
//		        String pv = getRequest().getParameter("pv");
//		        String pv_cost = getRequest().getParameter("pv_cost");
//		        String pv_vat = getRequest().getParameter("pv_vat");
//		        String pv_total = getRequest().getParameter("pv_total");
		        
		        String rate1 = getRequest().getParameter("rate1");
		        String rate1_cost = getRequest().getParameter("rate1_cost");
		        String rate1_vat = getRequest().getParameter("rate1_vat");
		        String rate1_total = getRequest().getParameter("rate1_total");
		        String installment1 = getRequest().getParameter("installment1");
		        String installment1_cost = getRequest().getParameter("installment1_cost");
		        String installment1_vat = getRequest().getParameter("installment1_vat");
		        String installment1_total = getRequest().getParameter("installment1_total");
		        
		        
		        String hire_purchase_cost = getRequest().getParameter("hire_purchase_cost");
		        String hire_purchase_vat = getRequest().getParameter("hire_purchase_vat");
		        String hire_purchase_total = getRequest().getParameter("hire_purchase_total");
		        String effective_rate = getRequest().getParameter("effective_rate");
		        String irr_rate = getRequest().getParameter("irr_rate");
		        
		        String down_payment = getRequest().getParameter("down_payment");
		        String financial_amt = getRequest().getParameter("financial_amt");
		        String install_1 = getRequest().getParameter("install_1");
		        String install_2 = getRequest().getParameter("install_2");
		        
		        String deposit_type = getRequest().getParameter("deposit_type");
		        String subsidies_amount = getRequest().getParameter("subsidies_amount");
		        String agreement_date = getRequest().getParameter("agreement_date");
		        String excution_date = getRequest().getParameter("excution_date");
		        String last_due_date = getRequest().getParameter("last_due_date");
		        String delivery_date = getRequest().getParameter("delivery_date");
		        String expiry_date = getRequest().getParameter("expiry_date");
		        String balloon_ins_amount = getRequest().getParameter("balloon_ins_amount");
		        String first_ins_type = getRequest().getParameter("first_ins_type");
		        
		        String first_ins_deduct = getRequest().getParameter("first_ins_deduct");
		        String pay_dealer = getRequest().getParameter("pay_dealer");
		        String rv_percent = getRequest().getParameter("rv_percent");
		        String rv_cost = getRequest().getParameter("rv_cost");
		        String rv_vat = getRequest().getParameter("rv_vat");
		        String rv_total = getRequest().getParameter("rv_total");
		        //String loan_rate = getRequest().getParameter("loan_rate");
		        String penalty_rate = getRequest().getParameter("penalty_rate");
		        String disc_rate = getRequest().getParameter("disc_rate");
		        String down_payment_percent = getRequest().getParameter("down_payment_percent");
		        
		        String have_vat = getRequest().getParameter("have_vat");
		        String balloon_amt_percent = getRequest().getParameter("balloon_amt_percent");
		        //String balloon_amt_term = getRequest().getParameter("balloon_amt_term");
		        //String cheque_date = getRequest().getParameter("cheque_date");
		        //String have_vat = getRequest().getParameter("have_vat");
				BigDecimal actual_car_price = utility.stringToBigDecimal(getRequest().getParameter("actual_car_price"));				
				BigDecimal actual_down = utility.stringToBigDecimal(getRequest().getParameter("actual_down"));
		        logger.debug("actual_car_price = "+actual_car_price);
				logger.debug("actual_down = "+actual_down);
				//20080722 add installmentFlag
				String installmentFlag = getRequest().getParameter("installment_flag");
				
		        loanDataM.setLoanType(loan_type);
		        loanDataM.setMarketingCode(mkt_code);
		        loanDataM.setCampaign(campaign);
		        loanDataM.setInternalCkecker(internal);
		        loanDataM.setReasonCampaign(reason_camgaign);
		        loanDataM.setExternalCkecker(external);
		        loanDataM.setOverrideBy(override_by);
		        
		        loanDataM.setCreditApproval(credir_approval);
		        loanDataM.setOverrideDate(ORIGUtility.parseThaiToEngDate(override_date));
		        loanDataM.setAppraisalPrice(utility.stringToBigDecimal(appraisal_price));
		        loanDataM.setCollectionCode(collection_code);
		        loanDataM.setBalloonFlag(balloon_flag);
		        loanDataM.setBalloonType(balloon);
		        loanDataM.setBankCode(bank);
		        loanDataM.setBranchCode(branch);
		        loanDataM.setAccountNo(account_no);
		        loanDataM.setAccountName(account_name);
		        loanDataM.setBookingDate(ORIGUtility.parseThaiToEngDate(booking_date));
		        loanDataM.setContractDate(ORIGUtility.parseThaiToEngDate(contact_date));
		        loanDataM.setCollectorCode(collector_code);
		        loanDataM.setFirstDueDate(ORIGUtility.parseThaiToEngDate(first_due_date));
		        loanDataM.setEndDueDate(ORIGUtility.parseThaiToEngDate(end_due_date));
		        loanDataM.setDownType(down_type);
		        loanDataM.setDownAmount(utility.stringToBigDecimal(down_amount));
		        loanDataM.setPaymentType(payment_type);
		        loanDataM.setFirstInstallment(utility.stringToBigDecimal(first_installment));
		        loanDataM.setNetRate(utility.stringToBigDecimal(net_rate));
		        loanDataM.setSpecialHireCharge(special_hire_charge);
		        loanDataM.setDocumentDate(ORIGUtility.parseThaiToEngDate(document_date));
		        loanDataM.setChequeDate(ORIGUtility.parseThaiToEngDate(cheque_payment_date));
		        loanDataM.setVAT(vat);
		        loanDataM.setCostOfCarPrice(utility.stringToBigDecimal(car_price_cost));
		        loanDataM.setVATOfCarPrice(utility.stringToBigDecimal(car_price_vat));
		        loanDataM.setTotalOfCarPrice(utility.stringToBigDecimal(car_price_total));
	            loanDataM.setCostOfDownPayment(utility.stringToBigDecimal(down_payment_cost));
	            loanDataM.setVATOfDownPayment(utility.stringToBigDecimal(down_payment_vat));
	            loanDataM.setTotalOfDownPayment(utility.stringToBigDecimal(down_payment_total));
	            loanDataM.setCostOfFinancialAmt(utility.stringToBigDecimal(finance_cost));
	            loanDataM.setVATOfFinancialAmt(utility.stringToBigDecimal(finance_vat));
	            loanDataM.setTotalOfFinancialAmt(utility.stringToBigDecimal(finance_total));
	            loanDataM.setCostOfBalloonAmt(utility.stringToBigDecimal(balloon_cost));
	            loanDataM.setVATOfBalloonAmt(utility.stringToBigDecimal(balloon_vat));
	            loanDataM.setTotalOfBalloonAmt(utility.stringToBigDecimal(balloon_total));
	            loanDataM.setRate1(utility.stringToBigDecimal(rate1));
	            loanDataM.setCostOfRate1(utility.stringToBigDecimal(rate1_cost));
	            loanDataM.setVATOfRate1(utility.stringToBigDecimal(rate1_vat));
	            loanDataM.setTotalOfRate1(utility.stringToBigDecimal(rate1_total));
	            loanDataM.setInstallment1(utility.stringToBigDecimal(installment1));
	            loanDataM.setCostOfInstallment1(utility.stringToBigDecimal(installment1_cost));
		        loanDataM.setVATOfInstallment1(utility.stringToBigDecimal(installment1_vat));
		        loanDataM.setTotalOfInstallment1(utility.stringToBigDecimal(installment1_total));
		        loanDataM.setCostOfHairPurchaseAmt(utility.stringToBigDecimal(hire_purchase_cost));
		        loanDataM.setVATOfHairPurchaseAmt(utility.stringToBigDecimal(hire_purchase_vat));
		        loanDataM.setTotalOfHairPurchaseAmt(utility.stringToBigDecimal(hire_purchase_total));
		        loanDataM.setEffectiveRate(utility.stringToBigDecimal(effective_rate));
		        loanDataM.setIRRRate(utility.stringToBigDecimal(irr_rate));
//		        loanDataM.setPvBalloonAmt(utility.stringToBigDecimal(pv_ball_amt));
//		        loanDataM.setCostOfpvBalloonAmt(utility.stringToBigDecimal(pv_ball_amtcost));
//		        loanDataM.setVATOfpvBalloonAmt(utility.stringToBigDecimal(pv_ball_amtvat));
//		        loanDataM.setTotalOfpvBalloonAmt(utility.stringToBigDecimal(pv_ball_amttotal));
//		        loanDataM.setPv(utility.stringToBigDecimal(pv));
//		        loanDataM.setCostOfpv(utility.stringToBigDecimal(pv_cost));
//		        loanDataM.setVATOfpv(utility.stringToBigDecimal(pv_vat));
//		        loanDataM.setTotalOfpv(utility.stringToBigDecimal(pv_total));
		        loanDataM.setDownPayment(utility.stringToBigDecimal(down_payment));
		        loanDataM.setFinancialAmt(utility.stringToBigDecimal(financial_amt));
		        
		        loanDataM.setDepositType(deposit_type);
		        loanDataM.setSubsidiesAmount(utility.stringToBigDecimal(subsidies_amount));
		        loanDataM.setAgreementDate(ORIGUtility.parseThaiToEngDate(agreement_date));
		        loanDataM.setExcutionDate(ORIGUtility.parseThaiToEngDate(excution_date));
		        loanDataM.setLastDueDate(ORIGUtility.parseThaiToEngDate(last_due_date));
		        loanDataM.setDeliveryDate(ORIGUtility.parseThaiToEngDate(delivery_date));
		        loanDataM.setExpiryDate(ORIGUtility.parseThaiToEngDate(expiry_date));
		        loanDataM.setBalloonInstallmentAmt(utility.stringToBigDecimal(balloon_ins_amount));
		        loanDataM.setFirstInsPayType(first_ins_type);
		        loanDataM.setFirstInsDeduct(first_ins_deduct);
		        loanDataM.setPayDealer(pay_dealer);
		        loanDataM.setRvPercent(utility.stringToBigDecimal(rv_percent));
		        loanDataM.setCostOfRV(utility.stringToBigDecimal(rv_cost));
		        loanDataM.setVATOfRV(utility.stringToBigDecimal(rv_vat));
		        loanDataM.setTotalOfRV(utility.stringToBigDecimal(rv_total));
		        //loanDataM.setLoanRate(utility.stringToBigDecimal(loan_rate));
		        loanDataM.setPenaltyRate(utility.stringToBigDecimal(penalty_rate));
		        loanDataM.setDiscRate(utility.stringToBigDecimal(disc_rate));
		        loanDataM.setBalloonTerm(utility.stringToBigDecimal(installment1));
		        //loanDataM.setChequeDate(ORIGUtility.parseThaiToEngDate(cheque_date));
		        loanDataM.setInstallmentFlag(installmentFlag);
		        BigDecimal installment;
		    	if("Y".equals(loanDataM.getBalloonFlag())){
		    		installment = loanDataM.getBalloonTerm();
		    	}else{
		    		installment = loanDataM.getInstallment1();
		    	}
		        if(loanDataM.getRateTemp() == null){
		        	loanDataM.setRateTemp(utility.stringToBigDecimal(rate1));
		        }
		        
		        if(loanDataM.getTermTemp() == null){
		        	loanDataM.setTermTemp(installment);
		        }
		        
		        if(loanDataM.getCampaignTemp() == null){
		        	loanDataM.setCampaignTemp(campaign);
		        }
		        
		        if(loanDataM.getDownPaymentTemp() == null){
		        	loanDataM.setDownPaymentTemp(utility.stringToBigDecimal(down_payment_cost));
		        }
		        if(OrigConstant.ROLE_UW.equals(userRole)){
			        boolean changeData = utility.checkExceptionForUW(loanDataM);
			        if(changeData){
			        	applicationDataM.setXcmrOverrideBy(null);
			        	applicationDataM.setXcmrDecision(null);			        	
			        }
		        }
		        
		        LoanDataM loanDataMTmp = (LoanDataM) getRequest().getSession().getAttribute("POPUP_DATA");
		        //20080723 Sankom add Step Installment
		        if(OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag())){
		           loanDataM.setStepInstallmentVect(loanDataMTmp.getStepInstallmentVect());
		        }else{
		            loanDataM.setStepInstallmentVect(new Vector());
		        }		        
		        loanDataM.setActualCarPrice(actual_car_price);
		        loanDataM.setActualDown(actual_down);		        
		        if(ORIGUtility.isEmptyString(loanDataM.getCreateBy())){
		        	loanDataM.setCreateBy(userM.getUserName());
		        }else{
		        	loanDataM.setUpdateBy(userM.getUserName());
		        }
	        }
	        
	        //Rewrite
	        String tableData = utility.getLoanTable(loanDataM, getRequest());
	        
	        PopulatePopupM populatePopupM = new PopulatePopupM("Loan",ORIGDisplayFormatUtil.replaceDoubleQuot(tableData));
			Vector popMs = new Vector();
			popMs.add(populatePopupM);
			getRequest().getSession(true).setAttribute("POPULATE_POPUP",popMs);
			popMs = (Vector)getRequest().getSession(true).getAttribute("POPULATE_POPUP");
        }catch(Exception e){
            logger.error("Error in SaveLoanWebAction.preModelRequest() >> ", e);
        }
        
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.FORWARD;
    }
    /* (non-Javadoc)
	 * @see com.bara.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
	 */
	public String getNextActionParameter() {
		return "orig/appform/filterMainScreen.jsp";
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
