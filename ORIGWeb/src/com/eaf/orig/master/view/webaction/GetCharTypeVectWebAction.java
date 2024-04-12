/*
 * Created on Dec 11, 2007
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

import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.ScoreTypeM;
//import com.eaf.orig.shared.dao.OrigMasterAppScoreDAO;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: GetCharTypeWebAction
 */
public class GetCharTypeVectWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(GetCharTypeVectWebAction.class);
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
		//*** Remove Attribute
		getRequest().getSession().removeAttribute("SEL_AND_ADD_CHAR_M_VECT");
		getRequest().getSession().removeAttribute("DISABLE_FORM");
		getRequest().getSession().removeAttribute("CHAR_CODE");
//		getRequest().getSession().removeAttribute("WEIGHT");
		
		//*** Keep Data accept,reject
		String accept = getRequest().getParameter("accept");
		String reject = getRequest().getParameter("reject");
		String accept_used = getRequest().getParameter("accept_used");
		String reject_used = getRequest().getParameter("reject_used");
		String scConstant = getRequest().getParameter("scConstant");
		
		getRequest().getSession().setAttribute("SC_CONSTANT",scConstant);
		getRequest().getSession().setAttribute("ACCEPT",accept);
		getRequest().getSession().setAttribute("REJECT",reject);
		getRequest().getSession().setAttribute("ACCEPT_USED",accept_used);
		getRequest().getSession().setAttribute("REJECT_USED",reject_used);
		//***END Keep
		
		String scoreType = getRequest().getParameter("scoreType");
		String scoreCode="";
		if(scoreType!=null && !"".equalsIgnoreCase(scoreType)){
			String temp[];
			temp = scoreType.split(",");
			scoreCode = temp[0];
			scoreType =	temp[1];
		
			if((scoreType!=null && !"".equalsIgnoreCase(scoreType)) && (scoreCode!=null && !"".equalsIgnoreCase(scoreCode))){
				
//				OrigMasterAppScoreDAO appScoreDAO = (OrigMasterAppScoreDAO)OrigMasterDAOFactory.getOrigMasterAppScoreDAO();
				Vector charTypeMVect = new Vector();
				
				try {
//					charTypeMVect = appScoreDAO.getCharTypeVect(scoreCode);
					
					charTypeMVect = PLORIGEJBService.getORIGDAOUtilLocal().getCharTypeVect(scoreCode);
					
					log.debug("charTypeMVect = "+charTypeMVect);
					log.debug("charTypeMVect.size() = "+charTypeMVect.size());
				} catch (Exception e) {
					log.fatal("",e);
				}
				getRequest().getSession().setAttribute("CHAR_TYPE_VECT",charTypeMVect);
				getRequest().getSession().setAttribute("SCORE_TYPE",scoreType);
				
				HashMap scoreTypeHash = (HashMap)getRequest().getSession().getAttribute("HASH_SCORE_TYPE");
				
				if(scoreTypeHash!=null && scoreTypeHash.size()>0){
					ScoreTypeM scoreTypeM = new ScoreTypeM(); 					
					scoreTypeM = (ScoreTypeM)scoreTypeHash.get(scoreCode);
					
					log.debug("scoreTypeM.getScoreWeight() = "+scoreTypeM.getScoreWeight());
					getRequest().getSession().setAttribute("WEIGHT",String.valueOf(scoreTypeM.getScoreWeight()));
				}
			}
		}
		
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
