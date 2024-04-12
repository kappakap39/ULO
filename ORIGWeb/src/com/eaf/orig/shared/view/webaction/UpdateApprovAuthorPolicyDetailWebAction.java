/*
 * Created on Sep 29, 2008
 * Created by Sankom
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

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.ApprovAuthorM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Sankom
 *
 * Type: UpdateApprovAuthorPolicyDetailWebAction
 */
public class UpdateApprovAuthorPolicyDetailWebAction extends WebActionHelper implements WebAction {
    Logger log = Logger.getLogger(UpdateApprovAuthorPolicyDetailWebAction.class);
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
       log.debug("UpdateApprovAuthorPolicyDetailWebAction");
       String credit_apprv_pass=getRequest().getParameter("credit_apprv_pass");
       String minimum_down_g_pass=getRequest().getParameter("minimum_down_g_pass");
       String minimum_term_g_pass=getRequest().getParameter("minimum_term_g_pass");
       String maximum_term_g_pass=getRequest().getParameter("maximum_term_g_pass");
       String minimum_down_ng_pass=getRequest().getParameter("minimum_down_ng_pass");
       String minimum_term_ng_pass=getRequest().getParameter("minimum_term_ng_pass");
       String maximum_term_ng_pass=getRequest().getParameter("maximum_term_ng_pass");
       String credit_apprv_fail=getRequest().getParameter("credit_apprv_fail");
       String minimum_down_g_fail=getRequest().getParameter("minimum_down_g_fail");
       String minimum_term_g_fail=getRequest().getParameter("minimum_term_g_fail");
       String maximum_term_g_fail=getRequest().getParameter("maximum_term_g_fail");
       String minimum_down_ng_fail=getRequest().getParameter("minimum_down_ng_fail");
       String minimum_term_ng_fail=getRequest().getParameter("minimum_term_ng_fail");
       String maximum_term_ng_fail=getRequest().getParameter("maximum_term_ng_fail");
       ApprovAuthorM selectApprv=(ApprovAuthorM)getRequest().getSession().getAttribute("SELECT_APPRV_DETAIL");
       log.debug("selectApprv="+selectApprv);
       if(selectApprv==null){selectApprv=new ApprovAuthorM();}
       ORIGUtility utiliy=ORIGUtility.getInstance();
       selectApprv.setCreditApproval(utiliy.stringToBigDecimal(credit_apprv_pass));
       selectApprv.setMinDownGua(utiliy.stringToBigDecimal(minimum_down_g_pass));
       selectApprv.setMinTermGua(utiliy.stringToBigDecimal(minimum_term_g_pass));
       selectApprv.setMaxTermGua(utiliy.stringToBigDecimal(maximum_term_g_pass));
       selectApprv.setMinDownNoGua(utiliy.stringToBigDecimal(minimum_down_ng_pass));
       selectApprv.setMinTermNoGua(utiliy.stringToBigDecimal(minimum_term_ng_pass));
       selectApprv.setMaxTermNoGua(utiliy.stringToBigDecimal(maximum_term_ng_pass));
       selectApprv.setFailPolicyCreditApproval(utiliy.stringToBigDecimal(credit_apprv_fail));
       selectApprv.setFailPolicyMinDownGua(utiliy.stringToBigDecimal(minimum_down_g_fail));
       selectApprv.setFailPolicyMinTermGua(utiliy.stringToBigDecimal(minimum_term_g_fail));
       selectApprv.setFailPolicyMaxTermGua(utiliy.stringToBigDecimal(maximum_term_g_fail));
       selectApprv.setFailPolicyMinDownNoGua(utiliy.stringToBigDecimal(minimum_down_ng_fail));
       selectApprv.setFailPolicyMinTermNoGua(utiliy.stringToBigDecimal(minimum_term_ng_fail));
       selectApprv.setFailPolicyMaxTermNoGua(utiliy.stringToBigDecimal(maximum_term_ng_fail));
       getRequest().getSession().setAttribute("APPRV_ACTION","load"); 
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        // TODO Auto-generated method stub
        return FrontController.PAGE;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
