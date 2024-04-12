/*
 * Created on Dec 13, 2007
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
//import java.util.Iterator;
//import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
//import com.eaf.orig.master.shared.model.ScoreCharacterM;
//import com.eaf.orig.master.shared.model.ScoreTypeM;
//import com.eaf.orig.shared.dao.OrigMasterAppScoreDAO;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: SearchAppScorebyCusTypeWebAction
 */
public class SearchAppScorebyCusTypeWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(SearchAppScorebyCusTypeWebAction.class);

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
		getRequest().getSession().removeAttribute("DISABLE_FORM");
		getRequest().getSession().removeAttribute("SC_CONSTANT");
		//***END Remove
		
		String customerType = getRequest().getParameter("customerType");
		Vector scoreTypeVect = new Vector();
		HashMap charTypeHashMap = new HashMap();
		
		getRequest().getSession().setAttribute("CUS_TYPE",customerType);
		
		if(customerType!=null){
//			OrigMasterAppScoreDAO appScoreDAO = (OrigMasterAppScoreDAO)OrigMasterDAOFactory.getOrigMasterAppScoreDAO();
			
			try {
//				scoreTypeVect = appScoreDAO.getScoreTypeVect(customerType);
//				charTypeHashMap = appScoreDAO.getCharTypeHashMapByScoreCode(scoreTypeVect);
				
				scoreTypeVect = PLORIGEJBService.getORIGDAOUtilLocal().getScoreTypeVect(customerType);
				
				charTypeHashMap = PLORIGEJBService.getORIGDAOUtilLocal().getCharTypeHashMapByScoreCode(scoreTypeVect);
				
				log.debug("scoreTypeVect = "+scoreTypeVect);
				log.debug("scoreTypeVect.size() = "+scoreTypeVect.size());
				log.debug("charTypeHashMap = "+charTypeHashMap);
				log.debug("charTypeHashMap.size() = "+charTypeHashMap.size());
			} catch (Exception e) {
				log.fatal("",e);
			}
			getRequest().getSession().setAttribute("SEARCH_SCORE_TYPE_VECT",scoreTypeVect);
			getRequest().getSession().setAttribute("SEARCH_CHAR_TYPE_HASH",charTypeHashMap);
		}
		
/*		if(scoreTypeVect!=null && scoreTypeVect.size()>0){
			ScoreTypeM scoreTypeM;
			for(int i=0;i<scoreTypeVect.size();i++){
				scoreTypeM = (ScoreTypeM)scoreTypeVect.get(i);
				boolean print=true;
				
				if(charTypeHashMap!=null){
					Set chTypekeySet = charTypeHashMap.keySet();
					Iterator chTypekeyIt = chTypekeySet.iterator();
					String chTypekey;
					
					while(chTypekeyIt.hasNext()){
						chTypekey = (String)chTypekeyIt.next();
						if(scoreTypeM.getScoreCode().equals(chTypekey)){
					
							Vector charTypeVect = (Vector)charTypeHashMap.get(chTypekey);
							if(charTypeVect!=null && charTypeVect.size()>0){
								log.debug("<Key = "+chTypekey+">charTypeVect.size() = "+charTypeVect.size());
								ScoreCharacterM characterM;
								for(int j=0;j<charTypeVect.size();j++){
									characterM = (ScoreCharacterM)charTypeVect.get(j);
										
									if(print){
										log.debug("scoreTypeM.getCusType() = "+scoreTypeM.getCusType());
										log.debug("scoreTypeM.getScoreCode() = "+scoreTypeM.getScoreCode());
										log.debug("scoreTypeM.getScoreType() = "+scoreTypeM.getScoreType());
										log.debug("scoreTypeM.getScoreWeight() = "+scoreTypeM.getScoreWeight());
										log.debug("scoreTypeM.getScoreSeq() = "+scoreTypeM.getScoreSeq());
										print = false;
									}
									
									log.debug("characterM.getScoreCode() = "+characterM.getScoreCode());
									log.debug("characterM.getCharCode() = "+characterM.getCharCode());
									log.debug("characterM.getCharDesc() = "+characterM.getCharDesc());
									log.debug("characterM.getMinRange() = "+characterM.getMinRange());
									log.debug("characterM.getMaxRange() = "+characterM.getMaxRange());
									log.debug("characterM.getSpecDesc() = "+characterM.getSpecDesc());
									log.debug("characterM.getScore() = "+characterM.getScore());
									log.debug("characterM.getScoreSeq() = "+characterM.getScoreSeq());
									log.debug("characterM.getSeq() = "+characterM.getSeq());
									
								}
							}
						}
					}
				}
			}
		}*/
		
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
