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

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Sankom
 * 
 * Type: LoadApprovAuthorWebAction
 */
public class LoadApprovAuthorWebAction extends WebActionHelper implements WebAction {
    private static Logger log = (Logger) Logger.getLogger(LoadApprovAuthorWebAction.class);

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
        String policyVersion = getRequest().getParameter("policyVersion");
        String displayType = getRequest().getParameter("displayType");
        getRequest().getSession().setAttribute("DISPLAY_TYPE",displayType);
        if (displayType == null) {
            displayType = "";
        }
        try {
            if ("new".equals(displayType)){
                OrigPolicyVersionDataM policyVersionDataM = new OrigPolicyVersionDataM();// (OrigPolicyVersionDataM) getRequest().getSession().getAttribute("POLICY_VERSION");
                getRequest().getSession().setAttribute("POLICY_VERSION", policyVersionDataM);
                //Level 
                policyVersionDataM.setVPolicyVersionLevel(new Vector());
                getRequest().getSession().setAttribute("POLICY_VERSION_LEVEL", policyVersionDataM.getVPolicyVersionLevel());  
                //Map code
                policyVersionDataM.setVPolicyMapRules(new Vector());  
                getRequest().getSession().setAttribute("POLICY_VERSION_MAP_RULES", policyVersionDataM.getVPolicyMapRules());                
                //Approval                 
                policyVersionDataM.setVPolicyApprovalDetails(new Vector());
                getRequest().getSession().setAttribute("POLICY_VERSION_APPRV_DETAIL", policyVersionDataM.getVPolicyApprovalDetails());               
                //Group                
                policyVersionDataM.setVPolicyVesionGroup(new Vector());                                
                getRequest().getSession().setAttribute("POLICY_VERSION_GROUPS", policyVersionDataM.getVPolicyVesionGroup());
               //Exception
               policyVersionDataM.setVPolicyRulesException(new Vector());
                 getRequest().getSession().setAttribute("POLICY_RUlES_EXCEPTION",policyVersionDataM.getVPolicyRulesException());                 
                // Group Total                
                 policyVersionDataM.setVPolicyVesionGroupTotal(new Vector());                                
                 getRequest().getSession().setAttribute("POLICY_VERSION_GROUPS_TOTAL", policyVersionDataM.getVPolicyVesionGroupTotal()); 
            
            }else if ("newcopy".equals(displayType)) {
                log.debug("Data From Create New Copy");
                OrigPolicyVersionDataM policyVersionDataM = (OrigPolicyVersionDataM) getRequest().getSession().getAttribute("POLICY_VERSION");
                //Level
                if (policyVersionDataM != null && policyVersionDataM.getVPolicyVersionLevel() != null) {
                    getRequest().getSession().setAttribute("POLICY_VERSION_LEVEL", policyVersionDataM.getVPolicyVersionLevel());
                }
                //Map code
                if (policyVersionDataM != null && policyVersionDataM.getVPolicyMapRules() != null) {
                    getRequest().getSession().setAttribute("POLICY_VERSION_MAP_RULES", policyVersionDataM.getVPolicyMapRules());
                }
                //Approval
                if (policyVersionDataM != null && policyVersionDataM.getVPolicyApprovalDetails() != null) {
                    log.debug("Approval Detail size " + policyVersionDataM.getVPolicyApprovalDetails().size());
                    getRequest().getSession().setAttribute("POLICY_VERSION_APPRV_DETAIL", policyVersionDataM.getVPolicyApprovalDetails());
                }
                //Group                
                if (policyVersionDataM != null && policyVersionDataM.getVPolicyVesionGroup() != null) {
                    getRequest().getSession().setAttribute("POLICY_VERSION_GROUPS", policyVersionDataM.getVPolicyVesionGroup());
                }    
                //Exception
                if (policyVersionDataM != null && policyVersionDataM.getVPolicyRulesException() != null) {
                    getRequest().getSession().setAttribute("POLICY_RUlES_EXCEPTION", policyVersionDataM.getVPolicyRulesException());
                }
                //Group total                
                if (policyVersionDataM != null && policyVersionDataM.getVPolicyVesionGroupTotal() != null) {
                    getRequest().getSession().setAttribute("POLICY_VERSION_GROUPS_TOTAL", policyVersionDataM.getVPolicyVesionGroupTotal());
                } 
                getRequest().getSession().setAttribute("POLICY_VERSION", policyVersionDataM);
            }else if ("edit".equals(displayType)){
//                OrigPolicyVersionDataM policyVersionDataM = ORIGDAOFactory.getOrigPolicyVersionMDAO().loadModelOrigPolicyVersionDataM(policyVersion);
            	
            	OrigPolicyVersionDataM policyVersionDataM = PLORIGEJBService.getORIGDAOUtilLocal().loadModelOrigPolicyVersionDataM(policyVersion);
            	
                getRequest().getSession().setAttribute("POLICY_VERSION", policyVersionDataM);                
                //Level
                if (policyVersionDataM != null && policyVersionDataM.getVPolicyVersionLevel() != null) {
                    getRequest().getSession().setAttribute("POLICY_VERSION_LEVEL", policyVersionDataM.getVPolicyVersionLevel());
                }
                //Map code
                if (policyVersionDataM != null && policyVersionDataM.getVPolicyMapRules() != null) {
                    getRequest().getSession().setAttribute("POLICY_VERSION_MAP_RULES", policyVersionDataM.getVPolicyMapRules());
                }
                //Approval
                if (policyVersionDataM != null && policyVersionDataM.getVPolicyApprovalDetails() != null) {
                    getRequest().getSession().setAttribute("POLICY_VERSION_APPRV_DETAIL", policyVersionDataM.getVPolicyApprovalDetails());
                }
                //Group               
                if (policyVersionDataM != null && policyVersionDataM.getVPolicyVesionGroup() != null) {
                    getRequest().getSession().setAttribute("POLICY_VERSION_GROUPS", policyVersionDataM.getVPolicyVesionGroup());
                }      
                //Exception
                if (policyVersionDataM != null && policyVersionDataM.getVPolicyRulesException() != null) {
                    getRequest().getSession().setAttribute("POLICY_RUlES_EXCEPTION", policyVersionDataM.getVPolicyRulesException());
                }
                //Group total                
                if (policyVersionDataM != null && policyVersionDataM.getVPolicyVesionGroupTotal() != null) {
                    getRequest().getSession().setAttribute("POLICY_VERSION_GROUPS_TOTAL", policyVersionDataM.getVPolicyVesionGroupTotal());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
