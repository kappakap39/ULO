/*
 * Created on Dec 3, 2007
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
import com.eaf.orig.master.shared.model.UserNameM;
import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterUserDetailDAO;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: SearchProfileWebAction
 */
public class SearchUsernameWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(SearchUsernameWebAction.class);
	UserDetailM userDetailM =new UserDetailM(); 

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
		
		// *** for show Data changed

		
		//*** END for show Data changed ***
		
		String searchUserNameDesc = getRequest().getParameter("searchUserNameDesc");
		if(searchUserNameDesc==null){
			searchUserNameDesc = (String) getRequest().getSession().getAttribute("USER_NAME_DESC_SEARCH");
		}
//		OrigMasterUserDetailDAO userDetailDAO = (OrigMasterUserDetailDAO)OrigMasterDAOFactory.getOrigMasterUserDetailDAO();
		Vector usernameSearchVect = new Vector();
		
		Vector selectedUserNameVect = (Vector) getRequest().getSession().getAttribute("SELECTED_USER_NAME");
		log.debug("selectedUserNameVect = "+selectedUserNameVect);
		
		if(searchUserNameDesc!=null && !"".equals(searchUserNameDesc)){
			getRequest().getSession().setAttribute("USER_NAME_DESC_SEARCH",searchUserNameDesc);
			try {
//				usernameSearchVect = userDetailDAO.SearchGroupnameByDesc(searchUserNameDesc);
				
				usernameSearchVect = PLORIGEJBService.getORIGDAOUtilLocal().SearchGroupnameByDesc(searchUserNameDesc);
				
				log.debug("usernameSearchVect = "+usernameSearchVect);
				log.debug("usernameSearchVect.size() = "+usernameSearchVect.size());
			} catch (Exception e) {
				log.fatal("",e);
			}
			
			// *** remove selUserName from searchUserName ****
			if((selectedUserNameVect!=null) && (usernameSearchVect!=null)){
				log.debug("now im checking selectedUserNameVect && usernameSearchVect at if1");
				if((selectedUserNameVect.size()>0) && (usernameSearchVect.size()>0)){
					log.debug("now im checking selectedUserNameVect && usernameSearchVect at if2");
					UserNameM selUserNameM = null;
					for(int i = 0; i < selectedUserNameVect.size(); i++){
						selUserNameM = (UserNameM) selectedUserNameVect.get(i);
						for(int j = 0; j < usernameSearchVect.size(); j++){
							UserNameM userNameM = (UserNameM) usernameSearchVect.get(j);
							if( (userNameM.getGroupName()).equalsIgnoreCase(selUserNameM.getGroupName()) && (userNameM.getLoanType()).equalsIgnoreCase(selUserNameM.getLoanType()) && (userNameM.getCusType()).equalsIgnoreCase(selUserNameM.getCusType())){
								usernameSearchVect.removeElementAt(j);
							}
						}
					}
				}
			}
		}
		getRequest().getSession().setAttribute("SEARCH_USER_NAME",usernameSearchVect);
		log.debug("now im returning true");
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
