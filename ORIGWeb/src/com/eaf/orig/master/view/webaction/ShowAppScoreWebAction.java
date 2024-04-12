/*
 * Created on Nov 19, 2007
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
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.ScoreM;
//import com.eaf.orig.shared.dao.OrigMasterAppScoreDAO;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: ShowAppScoreWebAction
 */
public class ShowAppScoreWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(ShowAppScoreWebAction.class);
	
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
		//***Remove SESSION
		getRequest().getSession().removeAttribute("CUS_TYPE");
		getRequest().getSession().removeAttribute("CUS_THAI_DESC");
		getRequest().getSession().removeAttribute("SCORE_TYPE_VECT");
		getRequest().getSession().removeAttribute("SCORE_TYPE");
		getRequest().getSession().removeAttribute("CHAR_CODE");
		getRequest().getSession().removeAttribute("SPECIFIC_EDIT");
		getRequest().getSession().removeAttribute("WEIGHT");
		getRequest().getSession().removeAttribute("CHAR_TYPE_M_EDIT");
		getRequest().getSession().removeAttribute("SEARCH_SCORE_TYPE_VECT");
		getRequest().getSession().removeAttribute("SEARCH_CHAR_TYPE_HASH");
		getRequest().getSession().removeAttribute("CHAR_TYPE_VECT");
		getRequest().getSession().removeAttribute("SEL_AND_ADD_CHAR_M_VECT");
		getRequest().getSession().removeAttribute("SPECIFIC_LIST_BOX");
		getRequest().getSession().removeAttribute("ACCEPT");
		getRequest().getSession().removeAttribute("REJECT");
		getRequest().getSession().removeAttribute("ACCEPT_USED");
		getRequest().getSession().removeAttribute("REJECT_USED");
		getRequest().getSession().removeAttribute("DISABLE_FORM");
		getRequest().getSession().removeAttribute("SC_CONSTANT");
		//***END Remove
		
		String customerType = getRequest().getParameter("customerType");
		String cusThaiDesc="";
		
		if(customerType!=null){
			getRequest().getSession().setAttribute("CUS_TYPE",customerType);
//			OrigMasterAppScoreDAO appScoreDAO = (OrigMasterAppScoreDAO)OrigMasterDAOFactory.getOrigMasterAppScoreDAO();
			Vector scoreTypeMVect = new Vector();
			
			try {
//				scoreTypeMVect = appScoreDAO.getScoreFacterType(customerType);
				scoreTypeMVect = PLORIGEJBService.getORIGDAOUtilLocal().getScoreFacterType(customerType);
				
//				cusThaiDesc = appScoreDAO.getCusThaiDesc(customerType);
				cusThaiDesc = PLORIGEJBService.getORIGDAOUtilLocal().getCusThaiDesc(customerType);
				log.debug("scoreTypeMVect = "+scoreTypeMVect);
				log.debug("scoreTypeMVect.size() = "+scoreTypeMVect.size());
			} catch (Exception e) {
				log.fatal("",e);
			}
			getRequest().getSession().setAttribute("CUS_THAI_DESC",cusThaiDesc);
			getRequest().getSession().setAttribute("SCORE_TYPE_VECT",scoreTypeMVect);
		}
		
		//*** Get All Old AppScore Data And Assign into Hash
		HashMap charTypeHash = new HashMap();
		HashMap scoreTypeHash = new HashMap();
		ScoreM scoreM = new ScoreM();
		
//		OrigMasterAppScoreDAO appScoreDAO = (OrigMasterAppScoreDAO)OrigMasterDAOFactory.getOrigMasterAppScoreDAO();
		try {
//			scoreM = appScoreDAO.getOriginalScoreM(customerType);
			
			scoreM = PLORIGEJBService.getORIGDAOUtilLocal().getOriginalScoreM(customerType);
			
			getRequest().getSession().setAttribute("SC_CONSTANT",String.valueOf(scoreM.getScoreConstant()));
			getRequest().getSession().setAttribute("ACCEPT",String.valueOf(scoreM.getAcceptScore()));
			getRequest().getSession().setAttribute("REJECT",String.valueOf(scoreM.getRejectScore()));
			getRequest().getSession().setAttribute("ACCEPT_USED",String.valueOf(scoreM.getAcceptScore_used()));
			getRequest().getSession().setAttribute("REJECT_USED",String.valueOf(scoreM.getRejectScore_used()));
			
//			scoreTypeHash = appScoreDAO.getOriginalScoreTypeHash(customerType);
			
			scoreTypeHash = PLORIGEJBService.getORIGDAOUtilLocal().getOriginalScoreTypeHash(customerType);
			
			log.debug(">>>>>scoreTypeHash.size() = "+scoreTypeHash.size());
			
			//*** Get All ScoreCode For Getting charTypeHash
			Vector scoreCodeVect = new Vector();
			
			if(scoreTypeHash!=null && scoreTypeHash.size()>0){
				Set scTypekeySet = scoreTypeHash.keySet();
				Iterator scTypekeyIt = scTypekeySet.iterator();
				String scTypekey;
				
				while(scTypekeyIt.hasNext()){
					scTypekey = (String)scTypekeyIt.next();
					scoreCodeVect.add(scTypekey);
				}
				
				log.debug(">>>>>scoreCodeVect.size() = "+scoreCodeVect.size());
			}
			//*** END Get
			
			if(scoreCodeVect!=null && scoreCodeVect.size()>0){
//				charTypeHash = appScoreDAO.getOriginalCharTypeHash(scoreCodeVect);
				
				charTypeHash = PLORIGEJBService.getORIGDAOUtilLocal().getOriginalCharTypeHash(scoreCodeVect);
				
				if(charTypeHash!=null && charTypeHash.size()>0){
					log.debug(">>>>>charTypeHash.size() = "+charTypeHash.size());
				}
			}
			
			getRequest().getSession().setAttribute("HASH_CHAR_TYPE",charTypeHash);
			getRequest().getSession().setAttribute("HASH_SCORE_TYPE",scoreTypeHash);
		} catch (Exception e) {
			log.fatal("",e);
		}
		//***END
		
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
