/*
 * Created on Jul 22, 2008
 * Created by Avalant
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

import java.util.Vector;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Avalant
 *
 * Type: LoadStepInstallmentPopupWebAction
 */
public class LoadStepInstallmentPopupWebAction extends WebActionHelper implements WebAction {

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

    	//if(loanVect!=null&&loanVect.size() > 0){
    	//	loanDataM = (LoanDataM) loanVect.elementAt(0);
    	//}	    
        LoanDataM loanDataM= (LoanDataM) getRequest().getSession().getAttribute("POPUP_DATA");
        if(loanDataM == null){
            loanDataM = new LoanDataM();	            
        }
        try {
            String vat=getRequest().getParameter("vat");
            String term=getRequest().getParameter("term");
            String hire_purchase_total=getRequest().getParameter("hire_purchase_total");
            loanDataM.setVAT(vat);
            ORIGUtility utility=new ORIGUtility();
            loanDataM.setInstallment1(utility.stringToBigDecimal(term));
            loanDataM.setTotalOfHairPurchaseAmt(utility.stringToBigDecimal(hire_purchase_total));
        } catch (RuntimeException e) {         
          
        }        
        Vector vStepInstallment=loanDataM.getStepInstallmentVect();
        if(vStepInstallment==null){
        vStepInstallment=new Vector();    
        }
        getRequest().getSession().setAttribute("STEP_INSTALLMENT",vStepInstallment);
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
         
        return FrontController.PAGE;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
