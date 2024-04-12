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
import com.eaf.orig.master.shared.model.UserExceptActM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterUserDetailDAO;

/**
 * @author Administrator
 *
 * Type: SearchExceptActWebAction
 */
public class SearchExceptActWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(SearchExceptActWebAction.class);
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
		
		String searchExceptActDesc = getRequest().getParameter("searchExceptActDesc");
		if(searchExceptActDesc==null){
			searchExceptActDesc = (String) getRequest().getSession().getAttribute("EXCEPT_ACT_DESC_SEARCH");
		}
//		OrigMasterUserDetailDAO userDetailDAO = (OrigMasterUserDetailDAO)OrigMasterDAOFactory.getOrigMasterUserDetailDAO();
		Vector exceptSearchVect = new Vector();
		
		Vector selectedExceptVect = (Vector) getRequest().getSession().getAttribute("SELECTED_EXCEPT_ACT");
		log.debug("selectedExceptVect = "+selectedExceptVect);
		
		if(searchExceptActDesc!=null && !"".equals(searchExceptActDesc)){
			getRequest().getSession().setAttribute("EXCEPT_ACT_DESC_SEARCH",searchExceptActDesc);
			try {
//				exceptSearchVect = userDetailDAO.SearchExceptByDesc(searchExceptActDesc);

				exceptSearchVect = PLORIGEJBService.getORIGDAOUtilLocal().SearchExceptByDesc(searchExceptActDesc);
				
				log.debug("exceptSearchVect = "+exceptSearchVect);
				log.debug("exceptSearchVect.size() = "+exceptSearchVect.size());
			} catch (Exception e) {
				log.fatal("",e);
			}
			
			// *** remove selbranch from searchbranch ****
			if((selectedExceptVect!=null) && (exceptSearchVect!=null)){
				log.debug("now im checking selectedExceptVect && exceptSearchVect at if1");
				if((selectedExceptVect.size()>0) && (exceptSearchVect.size()>0)){
					log.debug("now im checking selectedExceptVect && exceptSearchVect at if2");
					UserExceptActM selExceptActM = null;
					for(int i = 0; i < selectedExceptVect.size(); i++){
						selExceptActM = (UserExceptActM) selectedExceptVect.get(i);
						for(int j = 0; j < exceptSearchVect.size(); j++){
							UserExceptActM exceptActM = (UserExceptActM) exceptSearchVect.get(j);
							if( (exceptActM.getExceptID()).equalsIgnoreCase(selExceptActM.getExceptID())){
								exceptSearchVect.removeElementAt(j);
							}
						}
					}
				}
			}
		}
		getRequest().getSession().setAttribute("SEARCH_EXCEPT",exceptSearchVect);
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
