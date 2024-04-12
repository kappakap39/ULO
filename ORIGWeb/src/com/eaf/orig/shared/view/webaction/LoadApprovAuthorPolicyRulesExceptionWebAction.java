/*
 * Created on Nov 10, 2008
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

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.OrigPolicyRulesExceptionDataM;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.master.shared.model.PolicyRulesDataM;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterPolicyRulesDAO;
//import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Sankom
 * 
 * Type: LoadApprovAuthorPolicyRulesExceptionWebAction
 */
public class LoadApprovAuthorPolicyRulesExceptionWebAction extends WebActionHelper implements WebAction {
    Logger log = Logger.getLogger(LoadApprovAuthorPolicyRulesExceptionWebAction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        OrigPolicyVersionDataM policyVersionDataM = (OrigPolicyVersionDataM) getRequest().getSession().getAttribute("POLICY_VERSION");
         
        Vector policyRules = null;
        ORIGUtility utility = new ORIGUtility();

//        OrigMasterPolicyRulesDAO origMasterDao=OrigMasterDAOFactory.getOrigMasterPolicyRulesDAO();           
        try {
//            policyRules= origMasterDao.loadPolicyRules();
        	
        	policyRules = PLORIGEJBService.getORIGDAOUtilLocal().loadPolicyRules();
        	
        } catch (Exception e) {                
            log.error("Error",e);
        }
        
       Vector  vPolicyRulesException=policyVersionDataM.getVPolicyRulesException();
       if(vPolicyRulesException==null){vPolicyRulesException=new Vector();}
       if(policyRules==null){policyRules=new Vector();}
       for(int i=0;i<vPolicyRulesException.size();i++){
         OrigPolicyRulesExceptionDataM   prmOrigPolicyRulesExceptionDataM=(OrigPolicyRulesExceptionDataM)vPolicyRulesException.get(i);
          for(int j=policyRules.size()-1;j>=0;j--){
          PolicyRulesDataM policyRulesDataM=(PolicyRulesDataM)policyRules.get(j);
          if(policyRulesDataM.getPolicyCode().equals(prmOrigPolicyRulesExceptionDataM.getPolicyCode())){
              policyRules.remove(j);
              break;
            }
          }
       }
                
        getRequest().getSession().setAttribute("CURRENT_POLICY_RULES_EXCEPTION",vPolicyRulesException );   
        getRequest().getSession().setAttribute("CURRENT_POLICY_RULES",policyRules );

        return true;
    }

    /*
     * (non-Javadoc)
     * 
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
