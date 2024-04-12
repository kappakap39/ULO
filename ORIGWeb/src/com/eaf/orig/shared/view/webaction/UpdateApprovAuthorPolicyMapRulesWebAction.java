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
import com.eaf.orig.master.shared.model.OrigPolicyLevelMapDataM;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.master.shared.model.PolicyRulesDataM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Sankom
 *
 * Type: UpdateApprovAuthorPolicyMapRulesWebAction
 */
public class UpdateApprovAuthorPolicyMapRulesWebAction extends WebActionHelper implements WebAction {
    Logger log = Logger.getLogger(UpdateApprovAuthorPolicyMapRulesWebAction.class);
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
        log.debug("UpdateApprovAuthorPolicyMapRulesWebAction");
        String mapAction=getRequest().getParameter("mapAction");
        String policyLevel=(String)getRequest().getSession().getAttribute("LOAD_POLICY_LEVEL");
        log.debug("map Action");
        Vector vPolicyMap=(Vector)getRequest().getSession().getAttribute("CURRENT_POLICY_LEVEL_MAP");  
        if(vPolicyMap==null){vPolicyMap=new Vector();
        getRequest().getSession().setAttribute("CURRENT_POLICY_LEVEL_MAP",vPolicyMap);        
        }
        Vector vpolicyRules=(Vector) getRequest().getSession().getAttribute("POLICY_CODE"); 
        if("add".equals(mapAction)){
            //get Parameter
          String[]  policyCode=getRequest().getParameterValues("selectPolicyCode");
            log.debug("Add "+getRequest().getParameterValues("selectPolicyCode"));
            //log.debug("Remove "+getRequest().getParameterValues("usedPolicyCode"));
            if(policyCode!=null){
              for(int i=0;i<policyCode.length;i++){
                  String  selecPolicyCode=policyCode[i];
                  if(selecPolicyCode==null){selecPolicyCode="";}
                  for(int j=vpolicyRules.size()-1;j>=0;j--){
                      PolicyRulesDataM policyRulesDataM=(PolicyRulesDataM)vpolicyRules.get(j);
                      if(selecPolicyCode.equals(policyRulesDataM.getPolicyCode())){
                          vpolicyRules.remove(j);
                          OrigPolicyLevelMapDataM origPolicyMap=new  OrigPolicyLevelMapDataM();
                          origPolicyMap.setPolicyCode(policyRulesDataM.getPolicyCode());
                          origPolicyMap.setPolicyLevel(policyLevel);
                          vPolicyMap.add(origPolicyMap);
                          break;
                      }
                  }
              }                
            }
                        
        }else if("remove".equals(mapAction)){
            log.debug("Remove "+getRequest().getParameterValues("usedPolicyCode"));
            String[]  policyCode=getRequest().getParameterValues("usedPolicyCode");
            if(policyCode!=null){
                for(int i=0;i<policyCode.length;i++){
                    String  selecPolicyCode=policyCode[i];
                    if(selecPolicyCode==null){selecPolicyCode="";}
                    for(int j=vPolicyMap.size()-1;j>=0;j--){                       
                        OrigPolicyLevelMapDataM origPolicyMap=(OrigPolicyLevelMapDataM)vPolicyMap.get(j);
                        if(selecPolicyCode.equals(origPolicyMap.getPolicyCode())){
                            vPolicyMap.remove(j);
                            PolicyRulesDataM policyRulesDataM=  new PolicyRulesDataM();                           
                            policyRulesDataM.setPolicyCode(origPolicyMap.getPolicyCode());                             
                            vpolicyRules.add(policyRulesDataM);
                            break;
                        }
                    }
                }                
              }
 
        }else if("save".equals(mapAction)){
            
            OrigPolicyVersionDataM  policyVersionDataM=(OrigPolicyVersionDataM)getRequest().getSession().getAttribute("POLICY_VERSION");
            Vector vPolicyMapRules=policyVersionDataM.getVPolicyMapRules();
            for(int i=0;i<vPolicyMap.size();i++){
                OrigPolicyLevelMapDataM origPolicyMapDataM=(OrigPolicyLevelMapDataM)vPolicyMap.get(i);
                boolean found=false;
                for( int j=0;j<vPolicyMapRules.size();j++){
                    OrigPolicyLevelMapDataM prmOrigPolicyMapRules=(OrigPolicyLevelMapDataM)vPolicyMapRules.get(j);
                    if(origPolicyMapDataM.getPolicyCode().equals(prmOrigPolicyMapRules.getPolicyCode())){
                        prmOrigPolicyMapRules.setPolicyLevel(policyLevel);
                        found=true;
                        break;
                    }
                }
                if(!found){
                    vPolicyMapRules.add(origPolicyMapDataM);
                }                
            }          
            //Remove Policy 
           for(int k=0;k< vpolicyRules.size();k++){
               PolicyRulesDataM policyRulesDataM=(PolicyRulesDataM)vpolicyRules.get(k);
               for( int j=0;j<vPolicyMapRules.size();j++){
                   OrigPolicyLevelMapDataM prmOrigPolicyMapRules=(OrigPolicyLevelMapDataM)vPolicyMapRules.get(j);
                   if(policyRulesDataM.getPolicyCode().equals(prmOrigPolicyMapRules.getPolicyCode())){
                       prmOrigPolicyMapRules.setPolicyLevel(null);
                       break;
                   }
               }           
           }      
           ORIGUtility utility=ORIGUtility.getInstance();
           Vector policyRules=(Vector)getRequest().getSession().getAttribute("POLICY_CODE");
           HashMap  hPolicyMapMaster=utility.mapPolicyRules(vPolicyMapRules,policyRules);           
           log.debug("hPolicyMapMaster"+hPolicyMapMaster);            
            getRequest().getSession().setAttribute("MAP_POLICY_LEVEL",hPolicyMapMaster);            
            getRequest().getSession().setAttribute("POLICY_CODE",policyRules);
            log.debug("V policy Map Rules size"+vPolicyMapRules.size());
            policyVersionDataM.setVPolicyMapRules(vPolicyMapRules);
           // getRequest().getSession().setAttribute("CURRENT_POLICY_LEVEL_MAP",vPolicyMap );  
        }
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
