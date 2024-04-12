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
import com.eaf.orig.master.shared.model.OrigPolicyLevelGroupDataM;
import com.eaf.orig.master.shared.model.OrigPolicyLevelGroupTotalDataM;
import com.eaf.orig.master.shared.model.OrigPolicyVersionLevelDataM;

/**
 * @author Sankom
 *
 * Type: LoadApprovAuthorPolicyGroupsWebAction
 */
public class LoadApprovAuthorPolicyGroupsWebAction extends WebActionHelper implements WebAction {
    Logger log = Logger.getLogger(LoadApprovAuthorPolicyGroupsWebAction.class);
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
        /**  1 Get Group form approval */
        //
       Vector  vPolicyVersionGroup=(Vector)getRequest().getSession().getAttribute("POLICY_VERSION_GROUPS");
       Vector  vPolicyVersionGroupTotal=(Vector)getRequest().getSession().getAttribute("POLICY_VERSION_GROUPS_TOTAL");
       //Vector vPolicyVersionApprovalDetail=(Vector)getRequest().getSession().getAttribute("POLICY_VERSION_APPRV_DETAIL");
       Vector vPolicyVersionLevel=(Vector)getRequest().getSession().getAttribute("POLICY_VERSION_LEVEL");
       String selectGroup=getRequest().getParameter("selectGroup");
       String groupAction=getRequest().getParameter("groupAction");
       log.debug("Group Action "+groupAction);
       log.debug("Select Group "+selectGroup);
       Vector vSelectGroup=null;
      // Vector vGroupName=this.getGroupName(vPolicyVersionGroup);
       OrigPolicyLevelGroupTotalDataM  origPolicyLevelGroupTotalDataM=null;
       if("edit".equals(groupAction)){
          vSelectGroup=this.getSelectGroup(vPolicyVersionGroup,selectGroup,vPolicyVersionLevel);
          origPolicyLevelGroupTotalDataM=this.getSelectGroupTotal(vPolicyVersionGroupTotal,selectGroup);
          
       }else if("add".equals(groupAction)){
           selectGroup="";
           vSelectGroup=new Vector();
           for(int i=0;i<vPolicyVersionLevel.size();i++){
             OrigPolicyVersionLevelDataM prmOrigPolicyVersionLevelDataM=(OrigPolicyVersionLevelDataM)vPolicyVersionLevel.get(i);
             OrigPolicyLevelGroupDataM  prmOrigPolicyLevelGroupDataM=new OrigPolicyLevelGroupDataM();
             prmOrigPolicyLevelGroupDataM.setGroupName(selectGroup);
             prmOrigPolicyLevelGroupDataM.setLevelName(prmOrigPolicyVersionLevelDataM.getLevelName());
             vSelectGroup.add(prmOrigPolicyLevelGroupDataM);
           }
           origPolicyLevelGroupTotalDataM=new OrigPolicyLevelGroupTotalDataM();                                   
       }       
       
      // getRequest().getSession().setAttribute("GROUP_NAME",vGroupName);
       getRequest().getSession().setAttribute("SELECT_GROUP_NAME",vSelectGroup);
       getRequest().getSession().setAttribute("SELECT_GROUP_TOTAL",origPolicyLevelGroupTotalDataM);
       getRequest().getSession().setAttribute("SELECT_GROUP",selectGroup); 
       getRequest().getSession().setAttribute("GROUP_ACTION",groupAction);
        return true;
    }

    /**
     * @param policyVersionGroupTotal
     * @param selectGroup
     * @return
     */
    private OrigPolicyLevelGroupTotalDataM getSelectGroupTotal(Vector vPolicyVersionGroupTotal, String selectGroup) {
        OrigPolicyLevelGroupTotalDataM result=null;
        for(int i=0;i<vPolicyVersionGroupTotal.size();i++){
            OrigPolicyLevelGroupTotalDataM prmOrigPolicyLevelGroupTotalDataM=(OrigPolicyLevelGroupTotalDataM)vPolicyVersionGroupTotal.get(i);
            if(selectGroup.equals(prmOrigPolicyLevelGroupTotalDataM.getGroupName())){
                result=prmOrigPolicyLevelGroupTotalDataM;
            }
        }
        
        return result;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        // TODO Auto-generated method stub
        return FrontController.PAGE;
    }
  /* public HashMap mapGroup(Vector vPolicyGroups){
       HashMap hResult=new HashMap();
       for(int i=0;i<vPolicyGroups.size();i++){
           OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM=(OrigPolicyLevelGroupDataM)vPolicyGroups.get(i);
           if(hResult.containsKey(prmOrigPolicyLevelGroupDataM.getGroupName())){
               Vector vResult=(Vector)hResult.get(prmOrigPolicyLevelGroupDataM.getGroupName());
               vResult.add(prmOrigPolicyLevelGroupDataM);                
           }else{
               Vector vResult=new Vector();
               vResult.add(prmOrigPolicyLevelGroupDataM);
               hResult.put(prmOrigPolicyLevelGroupDataM.getGroupName(),vResult);
           }
       }
       return hResult;
   }*/
   /* public Vector getPolicyGroupName(Vector vPolicyGroups){
       Vector vResult=new Vector();
       for(int i=0;i<vPolicyGroups.size();i++){
           OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM=(OrigPolicyLevelGroupDataM)vPolicyGroups.get(i);
           if(vResult.contains(prmOrigPolicyLevelGroupDataM.getGroupName())){
               //Vector vResult=(Vector)hResult.get(prmOrigPolicyLevelGroupDataM.getGroupName());
              // vResult.add(prmOrigPolicyLevelGroupDataM);                
           }else{
               //Vector vResult=new Vector();
               vResult.add(prmOrigPolicyLevelGroupDataM.getGroupName());
               //hResult.put(prmOrigPolicyLevelGroupDataM.getGroupName(),vResult);
           }
       }
     return vResult;
   }*/
   public Vector getSelectGroup(Vector vPolicyVersionGroup,String  groupName,Vector vPolicyVersionLevel){
       Vector vResult=new Vector();
       for(int i=0;i<vPolicyVersionGroup.size();i++){
           OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM=(OrigPolicyLevelGroupDataM)vPolicyVersionGroup.get(i);
           if(groupName.equals(prmOrigPolicyLevelGroupDataM.getGroupName() )){
              vResult.add(prmOrigPolicyLevelGroupDataM);  
           }           
       }
       
       for(int i=0;i<vPolicyVersionLevel.size();i++){
           OrigPolicyVersionLevelDataM prmOrigPolicyVersionLevelDataM=(OrigPolicyVersionLevelDataM)vPolicyVersionLevel.get(i);
           boolean found=false;
           for(int j=0;j<vResult.size();j++){
               OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM=(OrigPolicyLevelGroupDataM)vResult.get(j);
               if(prmOrigPolicyVersionLevelDataM.getLevelName().equals(prmOrigPolicyLevelGroupDataM.getLevelName())){
                   found=true;
                  break;    
               }
           }                      
           if(!found){
           OrigPolicyLevelGroupDataM  prmOrigPolicyLevelGroupDataM=new OrigPolicyLevelGroupDataM();
           prmOrigPolicyLevelGroupDataM.setGroupName(groupName);
           prmOrigPolicyLevelGroupDataM.setLevelName(prmOrigPolicyVersionLevelDataM.getLevelName());
           vResult.add(prmOrigPolicyLevelGroupDataM);
           }           
         }
      return vResult;
   }
   public Vector getGroupName(Vector vPolicyVersionGroup){
     Vector result=new Vector();
         for(int i=0;i<vPolicyVersionGroup.size();i++){
            OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM=(OrigPolicyLevelGroupDataM)vPolicyVersionGroup.get(i);
            if(!result.contains(prmOrigPolicyLevelGroupDataM.getGroupName())){
                result.add(prmOrigPolicyLevelGroupDataM.getGroupName());
            }
         }
     return result;
   }

@Override
public boolean getCSRFToken() {
	// TODO Auto-generated method stub
	return false;
}  
}   
