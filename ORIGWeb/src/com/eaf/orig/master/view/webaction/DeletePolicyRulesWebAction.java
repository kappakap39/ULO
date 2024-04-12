/*
 * Created on Nov 27, 2007
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

import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.PolicyRulesDataM;
import com.eaf.orig.shared.control.event.PolicyRulesEvent;

/**
 * @author Administrator
 * 
 * Type: DeleteGenParamWebAction
 */
public class DeletePolicyRulesWebAction extends WebActionHelper implements WebAction {

    Logger log = Logger.getLogger(DeletePolicyRulesWebAction.class);

    Vector policyRulesToDelete;

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {

        PolicyRulesEvent policyRulesEvent = new PolicyRulesEvent();
        policyRulesEvent.setEventType(PolicyRulesEvent.POLICY_RULES_DELETE);

        log.debug("PolicyRulesEvent.POLICY_RULES_DELETE=" + PolicyRulesEvent.POLICY_RULES_DELETE);

        policyRulesEvent.setObject(policyRulesToDelete);

        log.debug("policyRulesToDelete = " + policyRulesToDelete);
        log.debug("policyRulesEvent=" + policyRulesEvent);

        return policyRulesEvent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {

        return defaultProcessResponse(response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        String[] strPolicyRulesToDelete = getRequest().getParameterValues("policyRulesChk");

        // *** log genParamToDelete
        if (strPolicyRulesToDelete != null && strPolicyRulesToDelete.length > 0) {
            policyRulesToDelete = new Vector();
            log.debug("////// strPolicyRuleToDelete =" + strPolicyRulesToDelete);
            log.debug("////// strPolicyRuleToDelete.length =" + strPolicyRulesToDelete.length);
            for (int i = 0; i < strPolicyRulesToDelete.length; i++) {
                PolicyRulesDataM policyRulesDataM = new PolicyRulesDataM();
                log.debug("////// PolicyRulesToDelete " + i + " = " + strPolicyRulesToDelete[i]);
                String[] param = strPolicyRulesToDelete[i].split(",");
                if (param != null && param.length >= 2) {
                    policyRulesDataM.setPolicyCode(param[0]);
                    policyRulesDataM.setPolicyType(param[1]);
                    policyRulesToDelete.add(policyRulesDataM);
                }                
            }
        }
        // ****

        if (strPolicyRulesToDelete == null || strPolicyRulesToDelete.length <= 0) {
            log.debug("////// strPolicyRulesToDelete is null //////");
            return false;
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
         
        return FrontController.ACTION;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {

        //	System.out.println("Session FIRST_SEARCH_USER_NAME ->" +
        // getRequest().getSession(true).getAttribute("FIRST_SEARCH_USER_NAME"));

        return "action=SearchPolicyRules";
    }

    protected void doSuccess(EventResponse arg0) {          
        ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
        getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
       // com.eaf.cache.TableLookupCache.refreshCache("RunParamDataM");
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
