/*
 * Created on Dec 6, 2007
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

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.UserTeamM;
import com.eaf.orig.master.shared.model.UserTeamMemberM;

/**
 * @author Administrator
 *
 * Type: AddRemoveUsrTeamMemberWebAction
 */
public class AddRemoveUsrTeamMemberWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(AddRemoveUsrTeamMemberWebAction.class);

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
		//Show Data Changed
		
		String tmNm = getRequest().getParameter("tmNm");
		String tmDesc = getRequest().getParameter("tmDesc");
		
		UserTeamM userTeamM = new UserTeamM(); 
		 
		 userTeamM.setTeamName(tmNm);
		 userTeamM.setTeamDesc(tmDesc);
		 getRequest().getSession().setAttribute("ADD_USER_TEAM_DATAM",userTeamM);
		 getRequest().getSession().setAttribute("EDIT_USER_TEAM_DATAM",userTeamM);
		 
		 //**** END Show
		
		String[]  userNameStrArry = getRequest().getParameterValues("userName");
		String[]  selMemStrArry = getRequest().getParameterValues("memberChk");
		String member_leader = getRequest().getParameter("leaderFlag");
		
		getRequest().getSession().setAttribute("member_leader_SESSION",member_leader);
		getRequest().getSession().setAttribute("selMemStrArry_SESSION",selMemStrArry);
		
		Vector selTeamMemVect = (Vector) getRequest().getSession().getAttribute("SEL_MEMBER_SESSION");
		if(selTeamMemVect==null){
			selTeamMemVect = new Vector();
		}
		Vector userNameVect = (Vector) getRequest().getSession().getAttribute("USERNAME_SESSION");
		if(userNameVect==null){
			userNameVect = new Vector();
		}
		String addOrRemove = getRequest().getParameter("addOrRemove");
		log.debug("------------------> addOrRemove = " + addOrRemove);
		if((addOrRemove != null) && "add".equalsIgnoreCase(addOrRemove)){
			log.debug("///im adding selTeamMemVect");		
				if (userNameStrArry !=null && userNameStrArry.length>0) {
						log.debug("------------------> all userNameStrArry length = " + userNameStrArry.length);
				
					for (int i=0; i<userNameStrArry.length;i++) {
						String member= userNameStrArry[i];
						UserTeamMemberM memberM = new UserTeamMemberM();	
						
						memberM.setMemberID(member);
						if(member_leader!=null){
							if(member_leader.equals(memberM.getMemberID())){
								memberM.setLeaderFlag("Y");
							}
						}
						selTeamMemVect.add(memberM);	
						
						/* remove from userNameVect */
						if(userNameVect!=null && userNameVect.size()>0){
							String userNmToRemove;
							for (int j=0; j<userNameVect.size();j++){
								userNmToRemove = userNameStrArry[i];
								if(userNmToRemove.equals(userNameVect.get(j))){
									userNameVect.removeElementAt(j);
								}
							}
						}
					}
				}	
		}else if((addOrRemove != null) && "remove".equalsIgnoreCase(addOrRemove)){
			log.debug("///im removing selTeamMemVect");		
			if (selMemStrArry !=null && selMemStrArry.length>0) {
					log.debug("------------------> all selMemStrArry length = " + selMemStrArry.length);

				for (int i=0; i<selMemStrArry.length;i++) {
					userNameVect.add(selMemStrArry[i]);	
					
					/* remove from selTeamMemVect */
					if(selTeamMemVect!=null && selTeamMemVect.size()>0){
						UserTeamMemberM memberM = new UserTeamMemberM();
						String memberToRemove = selMemStrArry[i];
						for (int j=0; j<selTeamMemVect.size();j++){
							memberM = (UserTeamMemberM)selTeamMemVect.get(j);
							if(memberToRemove.equals(memberM.getMemberID())){
								selTeamMemVect.removeElementAt(j);
							}
						}
					}
				}			
			}
		
		}	
		if(selMemStrArry!=null && selMemStrArry.length>0){
			for (int i=0; i<selMemStrArry.length;i++) {
				log.debug("selMemStrArry "+i+ " = "+ selMemStrArry[i]);		
			}
		}
		if(selTeamMemVect!=null && selTeamMemVect.size()>0){
			UserTeamMemberM memberM = new UserTeamMemberM();
			for (int j=0; j<selTeamMemVect.size();j++){
				memberM = (UserTeamMemberM)selTeamMemVect.get(j);
				log.debug("memberM.getMemberID "+j+ " = "+ memberM.getMemberID());
				log.debug("memberM.getLeaderFlag "+j+ " = "+ memberM.getLeaderFlag());
			}
		}
		
		getRequest().getSession().setAttribute("USERNAME_SESSION",userNameVect);
		getRequest().getSession().setAttribute("SEL_MEMBER_SESSION",selTeamMemVect);
		
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		 
		return 0;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
