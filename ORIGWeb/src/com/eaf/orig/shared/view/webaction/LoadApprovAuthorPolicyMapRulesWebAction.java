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

import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterPolicyRulesDAO;
//import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Sankom
 *
 * Type: LoadApprovAuthorPolicyMapRulesWebAction
 */
public class LoadApprovAuthorPolicyMapRulesWebAction extends WebActionHelper implements WebAction {
    Logger log = Logger.getLogger(LoadApprovAuthorPolicyMapRulesWebAction.class);
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
        OrigPolicyVersionDataM  policyVersionDataM=(OrigPolicyVersionDataM)getRequest().getSession().getAttribute("POLICY_VERSION");
        String loadPolicy=getRequest().getParameter("loadPolicy");
        String policyLevel=getRequest().getParameter("policyLevel");
        log.debug("loadPolicy="+loadPolicy);
        log.debug("policyLevel="+policyLevel);
        getRequest().getSession().setAttribute("LOAD_POLICY_LEVEL",policyLevel);
        HashMap  hPolicyMap=null;
        Vector policyRules=null;
        // check Policy
        ORIGUtility utility=new ORIGUtility();
        if("loadNew".equals(loadPolicy)){
            log.debug("Load New");
            getRequest().getSession().removeAttribute("CURRENT_POLICY_LEVEL_MAP");
//            OrigMasterPolicyRulesDAO origMasterDao=OrigMasterDAOFactory.getOrigMasterPolicyRulesDAO();           
            try {
//                policyRules= origMasterDao.loadPolicyRules();
            	
            	policyRules = PLORIGEJBService.getORIGDAOUtilLocal().loadPolicyRules();
            	
            } catch (Exception e) {                
                log.error("Error",e);
            }
           HashMap  hPolicyMapMaster=utility.mapPolicyRules(policyVersionDataM.getVPolicyMapRules(),policyRules);
           getRequest().getSession().setAttribute("POLICY_CODE",policyRules);
        }
            
           //Group Map To HashMap;
                    
       // }else{
       //     hPolicyMap= (HashMap)getRequest().getSession().getAttribute("MAP_POLICY_LEVEL");
       //     policyRules=(Vector)getRequest().getSession().getAttribute("POLICY_CODE");
      //  }                         
        //Set to Hash Map;
        //Policy
       
        if("loadPolicy".equals(loadPolicy)){
//            OrigMasterPolicyRulesDAO origMasterDao=OrigMasterDAOFactory.getOrigMasterPolicyRulesDAO();           
            try {
//                policyRules= origMasterDao.loadPolicyRules();
            	
            	policyRules = PLORIGEJBService.getORIGDAOUtilLocal().loadPolicyRules();
            	
            } catch (Exception e) {                
                log.error("Error",e);
            }
           HashMap  hPolicyMapMaster=utility.mapPolicyRules(policyVersionDataM.getVPolicyMapRules(),policyRules);
           log.debug("hPolicyMapMaster"+hPolicyMapMaster);           
           log.debug("hPolicyMap"+hPolicyMapMaster);
           if(hPolicyMapMaster==null){hPolicyMapMaster=new HashMap();}
            getRequest().getSession().setAttribute("MAP_POLICY_LEVEL",hPolicyMapMaster);            
            getRequest().getSession().setAttribute("POLICY_CODE",policyRules);         
            Vector vPolicyMap=null;
            if(hPolicyMapMaster.containsKey(policyLevel) ){
              vPolicyMap=(Vector)hPolicyMapMaster.get(policyLevel);
            }
           //Clone PolicyMap
            log.debug("VpolicyMap = "+vPolicyMap);
            getRequest().getSession().setAttribute("CURRENT_POLICY_LEVEL_MAP",vPolicyMap );            
        }
        
        
        //else{
          //  getRequest().getSession().removeAttribute("CURRENT_POLICY_LEVEL_MAP");
       // }                
      
        
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
