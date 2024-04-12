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
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.OrigPolicyLevelGroupDataM;
import com.eaf.orig.master.shared.model.OrigPolicyLevelGroupTotalDataM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Sankom
 *
 * Type: UpdateApprovAuthorPolicyGroupsWebAction
 */
public class UpdateApprovAuthorPolicyGroupsWebAction extends WebActionHelper implements WebAction {
    Logger log = Logger.getLogger(UpdateApprovAuthorPolicyGroupsWebAction.class);
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
       
       //Vector vGroupName=(Vector) getRequest().getSession().getAttribute("GROUP_NAME");
       Vector vSelectGroupName=(Vector) getRequest().getSession().getAttribute("SELECT_GROUP_NAME");
       OrigPolicyLevelGroupTotalDataM  origPolicyLevelGroupTotalDataM=(OrigPolicyLevelGroupTotalDataM)getRequest().getSession().getAttribute("SELECT_GROUP_TOTAL");
       Vector  vPolicyVersionGroupTotal=(Vector)getRequest().getSession().getAttribute("POLICY_VERSION_GROUPS_TOTAL");
       String policyGroup=getRequest().getParameter("policyGroup");
       String groupAction=getRequest().getParameter("groupAction");
       log.debug("Group Action "+groupAction);
       String oldGroupAction=(String)getRequest().getSession().getAttribute("GROUP_ACTION");
       ORIGUtility utility=ORIGUtility.getInstance();
       if("save".equals(groupAction)){
       for(int i=0;i<vSelectGroupName.size();i++){
           OrigPolicyLevelGroupDataM origPolicyLevelGroup=(OrigPolicyLevelGroupDataM)vSelectGroupName.get(i);
           String name=origPolicyLevelGroup.getLevelName();
           String amount=getRequest().getParameter(name+"_amt");
           String description=getRequest().getParameter(name+"_desc");
           origPolicyLevelGroup.setGroupName(policyGroup);
           origPolicyLevelGroup.setLevelAmount(utility.stringToInt(amount));
           origPolicyLevelGroup.setDescription(description);
       }                  
      String  totalPolicyAmt=getRequest().getParameter("total_amt");
      String  totalDesc=getRequest().getParameter("total_desc");
      origPolicyLevelGroupTotalDataM.setToalLevelAmount( utility.stringToInt(totalPolicyAmt));
      origPolicyLevelGroupTotalDataM.setDescription(totalDesc);
       if("add".equals(oldGroupAction)){           
           //============================= 
           for(int i=vPolicyVersionGroupTotal.size()-1;i>=0;i--){
               OrigPolicyLevelGroupTotalDataM   origPolicyLevelGroupTotal=(OrigPolicyLevelGroupTotalDataM)vPolicyVersionGroupTotal.get(i);
               if(policyGroup.equals(origPolicyLevelGroupTotal.getGroupName())){
                   ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
                   String errMsg="Duplicate Group Name";
                   log.debug("Save Fail Error " +errMsg);
                   origMasterForm.getFormErrors().add(errMsg);		
                   return false;
               }              
           }      
           //=====================                                       
           Vector  vPolicyVersionGroup=(Vector)getRequest().getSession().getAttribute("POLICY_VERSION_GROUPS");
           vPolicyVersionGroup.addAll(vSelectGroupName);
           origPolicyLevelGroupTotalDataM.setGroupName(policyGroup);
           vPolicyVersionGroupTotal.add(origPolicyLevelGroupTotalDataM);
           //Vector vUpdateGroupName=this.getGroupName(vPolicyVersionGroup);
           //getRequest().getSession().setAttribute("GROUP_NAME",vUpdateGroupName);
       }
       getRequest().getSession().setAttribute("SELECT_GROUP",policyGroup); 
       getRequest().getSession().setAttribute("GROUP_ACTION","edit");
       }else if("delete".equals(groupAction)){           
           String selectGroup=getRequest().getParameter("selectGroup");
           if(selectGroup==null){selectGroup="";}
           Vector  vPolicyVersionGroup=(Vector)getRequest().getSession().getAttribute("POLICY_VERSION_GROUPS");
           for(int i=vPolicyVersionGroup.size()-1;i>=0;i--){
               OrigPolicyLevelGroupDataM origPolicyLevelGroup=(OrigPolicyLevelGroupDataM)vPolicyVersionGroup.get(i);
               if(selectGroup.equals(origPolicyLevelGroup.getGroupName())){
                   vPolicyVersionGroup.remove(i);    
               }
           }
          for(int i=vPolicyVersionGroupTotal.size()-1;i>=0;i--){
              OrigPolicyLevelGroupTotalDataM   origPolicyLevelGroupTotal=(OrigPolicyLevelGroupTotalDataM)vPolicyVersionGroupTotal.get(i);
              if(selectGroup.equals(origPolicyLevelGroupTotal.getGroupName())){
               vPolicyVersionGroupTotal.remove(i);   
              }              
          }                                 
          // Vector vUpdateGroupName=this.getGroupName(vPolicyVersionGroup);
          // getRequest().getSession().setAttribute("GROUP_NAME",vUpdateGroupName);
           getRequest().getSession().setAttribute("GROUP_ACTION","");
       }
        
        return true;              
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.PAGE;
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
