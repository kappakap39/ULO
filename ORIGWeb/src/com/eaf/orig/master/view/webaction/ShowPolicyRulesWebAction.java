/*
 * Created on Nov 18, 2007
 * Created by Prawit Limwattanachai
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
package com.eaf.orig.master.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.PolicyRulesDataM;
import com.eaf.orig.shared.control.event.PolicyRulesEvent;

/**
 * @author Administrator
 *
 * Type: ShowRunningParamWebAction
 */
public class ShowPolicyRulesWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(ShowUserDetailWebAction.class);
	PolicyRulesDataM policyRulesM = new PolicyRulesDataM();

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
	    PolicyRulesEvent  policyRuleEvent = new PolicyRulesEvent();
		
	    policyRuleEvent.setEventType(PolicyRulesEvent.POLICY_RULES_SELECT);
		
		log.debug("PolicyRulesEvent.POLICY_RULES_SELECT=" + PolicyRulesEvent.POLICY_RULES_SELECT);
		
		policyRuleEvent.setObject(policyRulesM);
		
		log.debug("policyRulesM = " + policyRuleEvent);
		log.debug("policyRulesM.getPolicyCode() = " + policyRulesM.getPolicyCode());
		log.debug("policyRulesM.getParamType() = " + policyRulesM.getPolicyType());
		log.debug("policyRulesM.getDesciption() = " + policyRulesM.getDesciption());
		log.debug("policyRuleEvent=" + policyRuleEvent);
		
		return policyRuleEvent;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {

		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		
		return defaultProcessResponse(response);
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		String shwAddFrm = getRequest().getParameter("shwAddFrm");
		
		// show edit form
		log.debug("///ShowPolicyRulesWebAction/// shwAddFrm = " + shwAddFrm);
		if("edit".equals(shwAddFrm)){
			String policyCodeEdit = getRequest().getParameter("policyCodeEdit");
			//String policyRuleType = getRequest().getParameter("policyRuleTypeEdit");			
			String policyRuleDesc = getRequest().getParameter("policyRuleDescEdit");
			policyRulesM.setPolicyCode(policyCodeEdit);
			policyRulesM.setPolicyType(policyRuleDesc);			
			return true;
		}
		// just show add form & clear session
		if(!"cancelEdit".equals(shwAddFrm)){
			getRequest().getSession().removeAttribute("FIRST_SEARCH_policyCode");
			getRequest().getSession().removeAttribute("FIRST_SEARCH_policyDesc");
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {

		return 0;
	}
	protected void doFail(EventResponse arg0) {
		log.debug("sorry i'm in do fail.!!!" );
	}
	protected void doSuccess(EventResponse arg0) {
		log.debug("//from Action//PolicyRulesDataM = " + (PolicyRulesDataM)arg0.getEncapData());
		getRequest().getSession().setAttribute("EDIT_POLICY_RULES_DATAM", (PolicyRulesDataM)arg0.getEncapData());
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
