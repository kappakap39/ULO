/*
 * Created on Dec 22, 2008
 */
package com.eaf.orig.shared.view.webaction;

import java.math.BigDecimal;
import java.util.Vector;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.PreScoreDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.SerializeUtil;

/**
 * @author Administrator
 */
public class LoadPLoanLoanPopupWebAction extends WebActionHelper implements WebAction {
	private String nextAction;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
        return FrontController.ACTION;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
        ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        Vector loanVect = formHandler.getAppForm().getLoanVect();
    	LoanDataM loanDataM = new LoanDataM();
    	
    	if(loanVect!=null&&loanVect.size() > 0){
    		loanDataM = (LoanDataM) loanVect.elementAt(0);
    	}else{
    		//Sankom set data From PreScore
    		ORIGUtility utility = new ORIGUtility();
    		PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
    	    PreScoreDataM prmPreScoreDataM=personalInfoDataM.getPreScoreDataM(); 
    	    if(prmPreScoreDataM!=null){
    	        loanDataM.setMarketingCode(prmPreScoreDataM.getMarketingCode());
    	        //Term Loan
	        	loanDataM.setInstallment1( new BigDecimal(prmPreScoreDataM.getTermLoan()));      	    	            	        
    	    }
    	}
    	
    	if(loanDataM.getInternalCkecker()==null||"".equals(loanDataM.getInternalCkecker())){
    	    ORIGUtility utility=ORIGUtility.getInstance();
    	    String interanChk = utility.getInternalChecker(userM
					.getUserName());  
    	    if(interanChk!=null &&!"".equals(interanChk)){
    	    loanDataM.setInternalCkecker(interanChk);
    	    }
    	}
        String loanType = getRequest().getParameter("type");
//        if(OrigConstant.LOAN_TYPE_LEASING.equals(loanType)){
//        	nextAction = "page=LOAN_LEASING_POPUP";
//        }else{
//        	nextAction = "page=LOAN_POPUP";
//        }
        nextAction = "page=LOAN_PLOAN_POPUP";
        loanDataM.setLoanType(loanType);
//        loanDataM.setCostOfFinanceTmp(loanDataM.getCostOfFinancialAmt());
        getRequest().getSession().setAttribute("POPUP_DATA", (LoanDataM) SerializeUtil.clone(loanDataM));
        return true;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		return false;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		return false;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		return null;
	}
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return nextAction;
    }
	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
