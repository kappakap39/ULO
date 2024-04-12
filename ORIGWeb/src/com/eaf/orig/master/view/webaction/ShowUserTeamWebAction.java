/*
 * Created on Nov 18, 2007
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
import com.eaf.orig.shared.control.event.UserTeamEvent;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterUserTeamDAO;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: ShowUserTeamWebAction
 */
public class ShowUserTeamWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(ShowUserTeamWebAction.class);
	private String teamIDEdit;
	private UserTeamM userTeamM;
	private UserTeamMemberM memberEditM;
	
	public Event toEvent() {
		UserTeamEvent userTeamEvent = new UserTeamEvent();
		
		userTeamEvent.setEventType(UserTeamEvent.USER_TEAM_SELECT);
		log.debug("UserTeamEvent.USER_TEAM_SELECT=" + UserTeamEvent.USER_TEAM_SELECT);
		userTeamEvent.setObject(teamIDEdit);
		log.debug("teamIDEdit=" + teamIDEdit);
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
		String shwAddFrm = getRequest().getParameter("shwAddFrm");
		
		if("add".equalsIgnoreCase(shwAddFrm)){
			// *** Remove SESSION
			getRequest().getSession().removeAttribute("USERNAME_SESSION");
			getRequest().getSession().removeAttribute("SEL_MEMBER_SESSION");
			getRequest().getSession().removeAttribute("FIRST_SEARCH_teamName");
			getRequest().getSession().removeAttribute("FIRST_SEARCH_teamDesc");
			 getRequest().getSession().removeAttribute("selMemStrArry_SESSION");
			 getRequest().getSession().removeAttribute("member_leader_SESSION");
			 getRequest().getSession().removeAttribute("ADD_USER_TEAM_DATAM");
			 getRequest().getSession().removeAttribute("EDIT_USER_TEAM_DATAM");
			// *** END Remove
		}
		
//		OrigMasterUserTeamDAO userTeamDAO = (OrigMasterUserTeamDAO)OrigMasterDAOFactory.getOrigMasterUserTeamDAO();
		Vector userNameVect = new Vector();
		
			try {
//				userNameVect = userTeamDAO.getAllUserNameCMR();
				userNameVect = PLORIGEJBService.getORIGDAOUtilLocal().getAllUserNameCMR();
				log.debug("userNameVect = "+userNameVect);
				log.debug("userNameVect.size() = "+userNameVect.size());
			} catch (Exception e) {
				log.fatal("",e);
			}
		
		getRequest().getSession().setAttribute("USERNAME_SESSION",userNameVect);
		
		// show edit form
		log.debug("///ShowFieldIDWebAction/// shwAddFrm = " + shwAddFrm);
		if("edit".equalsIgnoreCase(shwAddFrm)){
			teamIDEdit = getRequest().getParameter("teamIDEdit");
			
			log.debug("now im returning true");
			return true;
		}
		
		log.debug("now im returning true");
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {

		return 0;
	}
	protected void doFail(EventResponse arg0) {
		log.debug("sorry i'm in do fail.!!!" );
	}
	protected void doSuccess(EventResponse arg0) {
		log.debug("//from Action//UserTeamM = " + (UserTeamM)arg0.getEncapData());
		getRequest().getSession().setAttribute("EDIT_USER_TEAM_DATAM", (UserTeamM)arg0.getEncapData());
		
		//*** Remove memberSelToEdit From allUserName
		userTeamM = (UserTeamM)arg0.getEncapData();
		Vector memberVect = userTeamM.getTeamMemberVect();
		Vector allUserNameVect = (Vector)getRequest().getSession().getAttribute("USERNAME_SESSION");
		if(allUserNameVect!=null && allUserNameVect.size()>0){
			if(memberVect!=null && memberVect.size()>0){
				for(int i=0;i<memberVect.size();i++){
					memberEditM = new UserTeamMemberM();
					memberEditM = (UserTeamMemberM)memberVect.get(i);
					//*** Set SESSION FOR Show member Leader
					if("Y".equals(memberEditM.getLeaderFlag())){
						getRequest().getSession().setAttribute("member_leader_SESSION",memberEditM.getMemberID());
					}
					//*** END Set
					for(int j=0;j<allUserNameVect.size();j++){
						if(allUserNameVect.get(j).equals(memberEditM.getMemberID())){
							allUserNameVect.remove(j);
						}
					}
				}
			}
			getRequest().getSession().setAttribute("USERNAME_SESSION",allUserNameVect);
		}
		
		//*** END Remove
		getRequest().getSession().setAttribute("SEL_MEMBER_SESSION",userTeamM.getTeamMemberVect());
		getRequest().getSession().setAttribute("TEAM_ID_EDIT_SESSION",userTeamM.getTeamId());
		
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
