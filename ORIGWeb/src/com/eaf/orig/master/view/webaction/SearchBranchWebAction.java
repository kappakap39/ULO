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
import com.eaf.orig.master.shared.model.UserBranchM;
import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterUserDetailDAO;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: SearchBranchWebAction
 */
public class SearchBranchWebAction extends WebActionHelper implements WebAction {

	static Logger log = Logger.getLogger(SearchBranchWebAction.class);
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
		
		String searchBranchDesc = getRequest().getParameter("searchBranchDesc");
		if(searchBranchDesc==null){
			searchBranchDesc = (String) getRequest().getSession().getAttribute("BRANCH_DESC_SEARCH");
		}
//		OrigMasterUserDetailDAO userDetailDAO = (OrigMasterUserDetailDAO)OrigMasterDAOFactory.getOrigMasterUserDetailDAO();
		Vector branchSearchVect = new Vector();
		
		Vector selectedBranchVect = (Vector) getRequest().getSession().getAttribute("SELECTED_BRANCH");
		log.debug("selectedBranchVect = "+selectedBranchVect);
		
		if(searchBranchDesc!=null && !"".equals(searchBranchDesc)){
			getRequest().getSession().setAttribute("BRANCH_DESC_SEARCH",searchBranchDesc);
			try {
//				branchSearchVect = userDetailDAO.SearchBranchByDesc(searchBranchDesc);
				
				branchSearchVect = PLORIGEJBService.getORIGDAOUtilLocal().SearchBranchByDesc(searchBranchDesc);
				
				log.debug("branchSearchVect = "+branchSearchVect);
				log.debug("branchSearchVect.size() = "+branchSearchVect.size());
			} catch (Exception e) {
				log.fatal("",e);
			}
			
			// *** remove selbranch from searchbranch ****
			if((selectedBranchVect!=null) && (branchSearchVect!=null)){
				log.debug("now im checking selectedBranchVect && branchSearchVect at if1");
				if((selectedBranchVect.size()>0) && (branchSearchVect.size()>0)){
					log.debug("now im checking selectedBranchVect && branchSearchVect at if2");
					UserBranchM selUserBranchM = null;
					for(int i = 0; i < selectedBranchVect.size(); i++){
						selUserBranchM = (UserBranchM) selectedBranchVect.get(i);
						for(int j = 0; j < branchSearchVect.size(); j++){
							UserBranchM userBranchM = (UserBranchM) branchSearchVect.get(j);
							if( (userBranchM.getArea()).equalsIgnoreCase(selUserBranchM.getArea()) && (userBranchM.getCmpCde()).equalsIgnoreCase(selUserBranchM.getCmpCde()) ){
								branchSearchVect.removeElementAt(j);
							}
						}
					}
				}
			}
		}
		getRequest().getSession().setAttribute("SEARCH_BRANCH",branchSearchVect);
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
