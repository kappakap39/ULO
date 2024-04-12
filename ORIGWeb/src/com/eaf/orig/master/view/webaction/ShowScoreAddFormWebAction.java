/*
 * Created on Dec 12, 2007
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
//import com.eaf.orig.shared.dao.OrigMasterAppScoreDAO;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
import com.eaf.orig.shared.utility.ORIGMasterUtility;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: ShowScoreAddFormWebAction
 */
public class ShowScoreAddFormWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(ShowScoreAddFormWebAction.class);

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
		//*** Keep Data accept,reject
		String accept = getRequest().getParameter("accept");
		String reject = getRequest().getParameter("reject");
		String scConstant = getRequest().getParameter("scConstant");
		
		getRequest().getSession().setAttribute("SC_CONSTANT",scConstant);
		getRequest().getSession().setAttribute("ACCEPT",accept);
		getRequest().getSession().setAttribute("REJECT",reject);
		//***END Keep
		
		//***Remove SESSION
		getRequest().getSession().removeAttribute("DISABLE_FORM");
		getRequest().getSession().removeAttribute("CHAR_TYPE_M_EDIT");
		//***END Remove
		
		String charCode = getRequest().getParameter("charType");
		String scoreType = getRequest().getParameter("scoreType");
		String scoreCode="";
		if(scoreType!=null && !"".equalsIgnoreCase(scoreType)){
			String temp[];
			temp = scoreType.split(",");
			scoreCode = temp[0];
		}
		if(charCode!=null && !"".equalsIgnoreCase(charCode)){
			String temp[];
			temp = charCode.split(",");
			charCode = temp[0];
		}
		log.debug("charCode = "+charCode);
		log.debug("scoreCode = "+scoreCode);
		
		//*** Get SPECIFIC_LIST_BOX SESSION
//		OrigMasterAppScoreDAO appScoreDAO = (OrigMasterAppScoreDAO)OrigMasterDAOFactory.getOrigMasterAppScoreDAO();
		Vector charTypeSpecificVect = new Vector();
		try {
//			charTypeSpecificVect = appScoreDAO.getCharTypeSpecificVect(scoreCode, charCode);
			charTypeSpecificVect = PLORIGEJBService.getORIGDAOUtilLocal().getCharTypeSpecificVect(scoreCode, charCode);
			log.debug("charTypeSpecificVect = "+charTypeSpecificVect);
		} catch (Exception e) {
			log.fatal("",e);
		}
		getRequest().getSession().setAttribute("SPECIFIC_LIST_BOX",charTypeSpecificVect);
		getRequest().getSession().setAttribute("SPECIFIC_LIST_BOX_MASTER",charTypeSpecificVect);
		
		ORIGMasterUtility masterUtil = new ORIGMasterUtility();
		getRequest().getSession().setAttribute("SPECIFIC_LIST_BOX_MASTER",masterUtil.getSpecificValueList(charCode));
		
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
