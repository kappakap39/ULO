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
import com.eaf.orig.master.shared.model.OrigPolicyVersionLevelDataM;

/**
 * @author Sankom
 *
 * Type: UpdateApprovAuthorPolicyLevelWebAction
 */
public class UpdateApprovAuthorPolicyLevelWebAction extends WebActionHelper implements WebAction {
    Logger log = Logger.getLogger(UpdateApprovAuthorPolicyLevelWebAction.class);
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
       log.debug("Update Approval Level");
       String mode=getRequest().getParameter("frmMode");
       log.debug("Mode ="+mode);
       getRequest().getSession().setAttribute( "FORM_MODE",mode);
       if("update".equals(mode)){           
           setNextActionParameter("page=MS_APPROV_AUTHOR_POLICY_LEVELS_SCREEN");
           String levelName=getRequest().getParameter("selectLevel");
           Vector vPolicyLevels=(Vector)getRequest().getSession().getAttribute("POLICY_VERSION_LEVEL");
           if(vPolicyLevels==null){vPolicyLevels=new Vector();}
           if(levelName==null){levelName="";}
           OrigPolicyVersionLevelDataM policyVersonLevelDataM=null;
           for(int i=0;i<vPolicyLevels.size();i++){
               OrigPolicyVersionLevelDataM prmOrigPolicyVersionLevelDataM=(OrigPolicyVersionLevelDataM)vPolicyLevels.get(i);
               if(levelName.equals(prmOrigPolicyVersionLevelDataM.getLevelName())){
                   policyVersonLevelDataM=prmOrigPolicyVersionLevelDataM;
                   break;
               }
           }
           if(policyVersonLevelDataM==null){ policyVersonLevelDataM=new OrigPolicyVersionLevelDataM();}
           getRequest().getSession().setAttribute("POLICY_LEVEL_EDIT",policyVersonLevelDataM);
       }else if("add".equals(mode)){
           setNextActionParameter("page=MS_APPROV_AUTHOR_POLICY_LEVELS_SCREEN");
           OrigPolicyVersionLevelDataM  policyVersionLevelDataM=new OrigPolicyVersionLevelDataM();
           getRequest().getSession().setAttribute("POLICY_LEVEL_EDIT",policyVersionLevelDataM);
       }else if("save".equals(mode)){
           setNextActionParameter("page=MS_APPROV_AUTHOR_POLICY_LEVELS_SCREEN");
           String levelName=getRequest().getParameter("policyLevel");
           String desc=getRequest().getParameter("policyDescription");
           OrigPolicyVersionLevelDataM  policyVersionLevelDataM=(OrigPolicyVersionLevelDataM)getRequest().getSession().getAttribute("POLICY_LEVEL_EDIT");
           Vector vPolicyLevels=(Vector)getRequest().getSession().getAttribute("POLICY_VERSION_LEVEL");
           if(vPolicyLevels!=null){
           for(int i=0;i<vPolicyLevels.size();i++){
               OrigPolicyVersionLevelDataM   prmPolicyVersionLevelDataM = (OrigPolicyVersionLevelDataM) vPolicyLevels.get(i);
               if( (policyVersionLevelDataM!=prmPolicyVersionLevelDataM)  &&levelName.equals(prmPolicyVersionLevelDataM.getLevelName())){
                   ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
                   String errMsg="Duplicate Level Name";
                   log.debug("Save Fail Error " +errMsg);
                   origMasterForm.getFormErrors().add(errMsg);		
                   return false;
               }
           }
           }
           if(policyVersionLevelDataM.getLevelName()==null){
              vPolicyLevels.add(policyVersionLevelDataM);               
           }
            policyVersionLevelDataM.setLevelName(levelName);
            policyVersionLevelDataM.setDescription(desc);
            
            
       }     else if("delete".equals(mode)){
           
           Vector vPolicyLevels=(Vector)getRequest().getSession().getAttribute("POLICY_VERSION_LEVEL");
           if(vPolicyLevels==null){vPolicyLevels=new Vector();}
           //if(levelName==null){levelName="";}
           OrigPolicyVersionLevelDataM policyVersonLevelDataM=null;
           for(int i=vPolicyLevels.size()-1;i>=0;i--){
               OrigPolicyVersionLevelDataM prmOrigPolicyVersionLevelDataM=(OrigPolicyVersionLevelDataM)vPolicyLevels.get(i);
               String chkBoxSelect=getRequest().getParameter("chkBox_levels_" +i);
               log.debug(" chk  "+i+" ="+chkBoxSelect);
               if(getRequest().getParameter("chkBox_levels_" +i) !=null){
                    vPolicyLevels.remove(i);                    
               }
           }
           setNextActionParameter("page=MS_APPROV_AUTHOR_POLICY_LEVELS_SCREEN");
       }
       log.debug("next Action="+getNextActionParameter());
       return true;
    }
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {        
        return FrontController.ACTION;
    }
   // public String getNextActionParameter() {
   //     return nextAction;
   // }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

   // public void setNexActivityType(int nextActivityType){
   //   this.nextActivityType=nextActivityType;
    //}
    //public String getNextActionParameter() {
   // 	return  thi
  //  }
}
