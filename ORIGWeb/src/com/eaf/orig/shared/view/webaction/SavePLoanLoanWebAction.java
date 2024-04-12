/*
 * Created on Dec 22, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.view.webaction;

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
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SavePLoanLoanWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(SavePLoanLoanWebAction.class);
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
		        String scheme_code = getRequest().getParameter("scheme_code");
		        String credir_approval = getRequest().getParameter("credir_approval");
		        String bank = getRequest().getParameter("bank");
		        String branch = getRequest().getParameter("branch");
		        String account_no = getRequest().getParameter("account_no");
		        String account_name = getRequest().getParameter("account_name");
		        
		        String purpose_of_loan = getRequest().getParameter("purpose_of_loan");
		        String loan_amount_applied = getRequest().getParameter("loan_amount_applied");
		        String amount_finance = getRequest().getParameter("amount_finance");
		        String amount_finance3 = getRequest().getParameter("amount_finance3");
		        String amount_finance4 = getRequest().getParameter("amount_finance4");
		        
		        String acceptance_fee_percent = getRequest().getParameter("acceptance_fee_percent");
		        String acceptance_fee = getRequest().getParameter("acceptance_fee");
		        String acceptance_fee3 = getRequest().getParameter("acceptance_fee3");
		        String acceptance_fee4 = getRequest().getParameter("acceptance_fee4");
		        
		        String fee_discount_percent = getRequest().getParameter("fee_discount_percent");
		        String fee_discount = getRequest().getParameter("fee_discount");
		        String fee_discount3 = getRequest().getParameter("fee_discount3");
		        String fee_discount4 = getRequest().getParameter("fee_discount4");
		        
		        String monthly_instalment_one = getRequest().getParameter("monthly_instalment_one");
		        String monthly_instalment_two = getRequest().getParameter("monthly_instalment_two");
		        String monthly_instalment_three = getRequest().getParameter("monthly_instalment_three");
		        String monthly_instalment_four = getRequest().getParameter("monthly_instalment_four");
		        
		        String last_instalment_one = getRequest().getParameter("last_instalment_one");
		        String last_instalment_two = getRequest().getParameter("last_instalment_two");
		        String last_instalment_three = getRequest().getParameter("last_instalment_three");
		        String last_instalment_four = getRequest().getParameter("last_instalment_four");
		        
		        String first_interest_rate = getRequest().getParameter("first_interest_rate");
		        
		        String second_interest_rate = getRequest().getParameter("second_interest_rate"); 
		        String third_interest_rate = getRequest().getParameter("third_interest_rate");
		        String four_interest_rate = getRequest().getParameter("forth_interest_rate");
		        
		        String first_tier_term = getRequest().getParameter("first_tier_term");
		        
		        String second_tier_term = getRequest().getParameter("second_tier_term");
		        String third_tier_term = getRequest().getParameter("third_tier_term");
		        String four_tier_term = getRequest().getParameter("forth_tier_term");
		        
		        String serviceFee = getRequest().getParameter("service_fee");
		        String registrationFee = getRequest().getParameter("registration_fee");
		        String transferFee = getRequest().getParameter("transfer_fee");
		        String prepaymentFee = getRequest().getParameter("prepayment_fee");
		        
		        String MRTAFlag =  getRequest().getParameter("MRTA");		        
		        String irr_rate = getRequest().getParameter("irr_rate");
				
		        loanDataM.setLoanType(loan_type);
		        loanDataM.setMarketingCode(mkt_code);
		        loanDataM.setCampaign(campaign);
		        loanDataM.setInternalCkecker(internal);
		        loanDataM.setSchemeCode(scheme_code);
		        loanDataM.setCreditApproval(credir_approval);
		        loanDataM.setBankCode(bank);
		        loanDataM.setBranchCode(branch);
		        loanDataM.setAccountNo(account_no);
		        loanDataM.setAccountName(account_name);

		        loanDataM.setPurposeLoan(purpose_of_loan);
		        loanDataM.setLoanAmt(utility.stringToBigDecimal(loan_amount_applied));
		        loanDataM.setAmountFinance(utility.stringToBigDecimal(amount_finance));
		        
		        loanDataM.setAcceptanceFeePercent(utility.stringToBigDecimal(acceptance_fee_percent));
		        loanDataM.setAcceptanceFee(utility.stringToBigDecimal(acceptance_fee));
		        
		        loanDataM.setFeeDiscountPercent(utility.stringToBigDecimal(fee_discount_percent));
		        loanDataM.setFeeDiscount(utility.stringToBigDecimal(fee_discount));
		        
		        loanDataM.setMonthlyIntalmentAmtOne(utility.stringToBigDecimal(monthly_instalment_one));
		        loanDataM.setMonthlyIntalmentAmtTwo(utility.stringToBigDecimal(monthly_instalment_two));
		        loanDataM.setMonthlyIntalmentAmtThree(utility.stringToBigDecimal(monthly_instalment_three));
		        loanDataM.setMonthlyIntalmentAmtFour(utility.stringToBigDecimal(monthly_instalment_four));
		        
		        loanDataM.setLastIntalmentAmtOne(utility.stringToBigDecimal(last_instalment_one));
		        loanDataM.setLastIntalmentAmtTwo(utility.stringToBigDecimal(last_instalment_two));
		        loanDataM.setLastIntalmentAmtThree(utility.stringToBigDecimal(last_instalment_three));
		        loanDataM.setLastIntalmentAmtFour(utility.stringToBigDecimal(last_instalment_four));
		        
		        loanDataM.setFirstTierRate(utility.stringToBigDecimal(first_interest_rate));
		        loanDataM.setSecondTierRate(utility.stringToBigDecimal(second_interest_rate));
		        loanDataM.setThirdTierRate(utility.stringToBigDecimal(third_interest_rate));
		        loanDataM.setForthTierRate(utility.stringToBigDecimal(four_interest_rate));
		        
		        loanDataM.setFirstTierTerm(Integer.parseInt(first_tier_term));
		        loanDataM.setSecondTierTerm(Integer.parseInt(second_tier_term));
		        loanDataM.setThirdTierTerm(Integer.parseInt(third_tier_term.split("\\.")[0]));
		        loanDataM.setForthTierTerm(Integer.parseInt(four_tier_term.split("\\.")[0]));
		        
		        loanDataM.setIRRRate(utility.stringToBigDecimal(irr_rate));
		        loanDataM.setTotalOfInstallment1(utility.stringToBigDecimal(last_instalment_one).add(utility.stringToBigDecimal(last_instalment_two)));
		        
		        loanDataM.setServiceFee(utility.stringToBigDecimal(serviceFee));
		        loanDataM.setRegistrationFee(utility.stringToBigDecimal(registrationFee));
		        loanDataM.setTransferFee(utility.stringToBigDecimal(transferFee));
		        loanDataM.setPrepaymentFee(utility.stringToBigDecimal(prepaymentFee));
		        
		        loanDataM.setMRTAFlag((!"".equals(MRTAFlag)?"Y":"N"));
		        
		        
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
		        if(ORIGUtility.isEmptyString(loanDataM.getCreateBy())){
		        	loanDataM.setCreateBy(userM.getUserName());
		        }else{
		        	loanDataM.setUpdateBy(userM.getUserName());
		        }
	        }
	        
	        //Rewrite
	        String tableData = utility.getPLoanTable(loanDataM, getRequest());
	        
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
