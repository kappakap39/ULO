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
import com.eaf.orig.master.shared.model.UserTeamM;
import com.eaf.orig.master.shared.model.UserTeamMemberM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.UserTeamEvent;

/**
 * @author Administrator
 *
 * Type: UpdateUserTeamWebAction
 */
public class UpdateUserTeamWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(UpdateUserTeamWebAction.class);
	private UserTeamM userTeamM;
	
	public Event toEvent() {
		UserTeamEvent userTeamEvent = new UserTeamEvent();
		
		userTeamEvent.setEventType(UserTeamEvent.USER_TEAM_UPDATE);
		log.debug("UserTeamEvent.USER_TEAM_UPDATE=" + UserTeamEvent.USER_TEAM_UPDATE);
		userTeamEvent.setObject(userTeamM);
		log.debug("userTeamM=" + userTeamM);
		log.debug("userTeamEvent=" + userTeamEvent);
		
		return userTeamEvent;
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
		
		String tmNm = getRequest().getParameter("tmNm");
		String tmDesc = getRequest().getParameter("tmDesc");
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		
		Vector selTeamMemVect = (Vector) getRequest().getSession().getAttribute("SEL_MEMBER_SESSION");
		
		//*** Set LeaderFlag
		String member_leader = getRequest().getParameter("leaderFlag");
		String memLeadFrmSession = (String)getRequest().getSession().getAttribute("member_leader_SESSION");
		
		if(memLeadFrmSession!=null){
			if(!memLeadFrmSession.equals(member_leader)){// *** Chk have new leader
				if(selTeamMemVect!=null && selTeamMemVect.size()>0){
					UserTeamMemberM memberM; 
					//*** Remove Old Leader Flag
					for(int i=0;i<selTeamMemVect.size();i++){
						memberM = (UserTeamMemberM)selTeamMemVect.get(i);
						if(memLeadFrmSession.equals(memberM.getMemberID())){
							memberM.setLeaderFlag("");
							selTeamMemVect.setElementAt(memberM,i);
						}
					}
					//*** Set New Leader Flagmember
					memberM = new UserTeamMemberM();
					for(int i=0;i<selTeamMemVect.size();i++){
						memberM = (UserTeamMemberM)selTeamMemVect.get(i);
						if(memberM.getMemberID().equals(member_leader)){
							memberM.setLeaderFlag("Y");
							selTeamMemVect.setElementAt(memberM,i);
						}
					}
				}
			}
		}else if(member_leader!=null){
			if(selTeamMemVect!=null && selTeamMemVect.size()>0){
				//*** Set New Leader Flagmember
				UserTeamMemberM memberM;
				for(int i=0;i<selTeamMemVect.size();i++){
					memberM = (UserTeamMemberM)selTeamMemVect.get(i);
					if(memberM.getMemberID().equals(member_leader)){
						memberM.setLeaderFlag("Y");
						selTeamMemVect.setElementAt(memberM,i);
					}
				}
			}
		}
		//*** END Set
		
		String teamID = (String) getRequest().getSession().getAttribute("TEAM_ID_EDIT_SESSION");
		 
		 log.debug("teamID ="+teamID);
		 log.debug("tmNm ="+tmNm);
		 log.debug("tmDesc ="+tmDesc);
		 log.debug("userName ="+userName);
		 		 
		 userTeamM = new UserTeamM(); 
		 
		 userTeamM.setTeamId(teamID);
		 userTeamM.setTeamName(tmNm);
		 userTeamM.setTeamDesc(tmDesc);
		 userTeamM.setTeamMemberVect(selTeamMemVect);
		 userTeamM.setCreateBy(userName);
		 userTeamM.setUpdateBy(userName);
		 
		 //*** Get LeaderFlag for checking Mand field
		 boolean hvLeaderFlag=false;
		 boolean hvMember=false;
		 Vector memberVect = userTeamM.getTeamMemberVect();
		 
		 if(memberVect!=null && memberVect.size()>0){
		 	hvMember = true;
		 	UserTeamMemberM memberM;
		 	for(int i=0;i<memberVect.size();i++){
		 		memberM = (UserTeamMemberM)memberVect.get(i);
		 		if("Y".equals(memberM.getLeaderFlag())){
		 			hvLeaderFlag = true;
		 		}
		 	}
		 }
		 //*** END Get

		 ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
		 origMasterForm.setShwAddFrm("edit");	
		 
			origMasterForm.setFormErrors(new Vector());
			if (userTeamM.getTeamName()==null || "".equalsIgnoreCase(userTeamM.getTeamName())){
				origMasterForm.getFormErrors().add("Team Code is required");
			}
			if (userTeamM.getTeamDesc()==null || "".equalsIgnoreCase(userTeamM.getTeamDesc())){
				origMasterForm.getFormErrors().add("Team Description is required");
			}
			if (!hvLeaderFlag){
				origMasterForm.getFormErrors().add("Team Lead is required");
			}
			if (!hvMember){
				origMasterForm.getFormErrors().add("Team Member is required");
			}
			
			if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
				getRequest().getSession().setAttribute("EDIT_USER_TEAM_DATAM",userTeamM);
				return false;
			}else {
				getRequest().getSession().setAttribute("EDIT_USER_TEAM_DATAM",userTeamM);
				return true;
			}
		 
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {

		return FrontController.ACTION;
    }
    
    public String getNextActionParameter() {
        return "action=SearchUserTeam";
    }
    
    protected void doFail(EventResponse arg0) {
		log.debug("sorry i'm in do fail.!!!" );
		ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
        String errMsg = arg0.getMessage();
        origMasterForm.getFormErrors().add(errMsg);
	}
    protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
		getRequest().getSession().removeAttribute("SEL_MEMBER_SESSION");
		 getRequest().getSession().removeAttribute("USERNAME_SESSION");
		 getRequest().getSession().removeAttribute("selMemStrArry_SESSION");
		 getRequest().getSession().removeAttribute("member_leader_SESSION");
		 getRequest().getSession().removeAttribute("ADD_USER_TEAM_DATAM");
		 getRequest().getSession().removeAttribute("EDIT_USER_TEAM_DATAM");
		
		//***Refresh Cache
//		log.debug("sorry i'm refreshing Cache.!!!" );
//		com.eaf.cache.TableLookupCache.refreshAll();
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
