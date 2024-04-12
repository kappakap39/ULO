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
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.ScoreCharacterM;
import com.eaf.orig.master.shared.model.ScoreTypeM;

/**
 * @author Administrator
 *
 * Type: PopupAppScoreSummaryWebAction
 */
public class PopupAppScoreSummaryWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(PopupAppScoreSummaryWebAction.class);

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
		String accept_used = getRequest().getParameter("accept_used");
		String reject_used = getRequest().getParameter("reject_used");
		String weight = getRequest().getParameter("weight");
		String scConstant = getRequest().getParameter("scConstant");
		
		getRequest().getSession().setAttribute("SC_CONSTANT",scConstant);
		getRequest().getSession().setAttribute("WEIGHT",weight);
		getRequest().getSession().setAttribute("ACCEPT",accept);
		getRequest().getSession().setAttribute("REJECT",reject);
		getRequest().getSession().setAttribute("ACCEPT_USED",accept_used);
		getRequest().getSession().setAttribute("REJECT_USED",reject_used);
		//***END Keep
		
		log.debug("accept = "+accept);
		log.debug("reject = "+reject);
		log.debug("accept_used = "+accept_used);
		log.debug("reject_used = "+reject_used);
		log.debug("weight = "+weight);
		log.debug("scConstant = "+scConstant);
		
		
		//*** Print log for chk
		HashMap charTypeHash = (HashMap)getRequest().getSession().getAttribute("HASH_CHAR_TYPE");
		HashMap scoreTypeHash = (HashMap)getRequest().getSession().getAttribute("HASH_SCORE_TYPE");
		if(charTypeHash==null){
			charTypeHash = new HashMap();
		}
		if(scoreTypeHash==null){
			scoreTypeHash = new HashMap();
		}
		log.debug("charTypeHash.size() = "+charTypeHash.size());
		log.debug("scoreTypeHash.size() = "+scoreTypeHash.size());
		
		Set scTypekeySet = scoreTypeHash.keySet();
		Iterator scTypekeyIt = scTypekeySet.iterator();
		String scTypekey;
		
		while(scTypekeyIt.hasNext()){
			
			scTypekey = (String)scTypekeyIt.next();
			ScoreTypeM scoreTypeM2 = (ScoreTypeM)scoreTypeHash.get(scTypekey);
			boolean print=true;
			
			Set chTypekeySet = charTypeHash.keySet();
			Iterator chTypekeyIt = chTypekeySet.iterator();
			String chTypekey;
			
			while(chTypekeyIt.hasNext()){
				chTypekey = (String)chTypekeyIt.next();
				 Vector charTypeVect = (Vector)charTypeHash.get(chTypekey);
			
				if(charTypeVect!=null && charTypeVect.size()>0){
					
					ScoreCharacterM characterM2;
					
					for(int i=0;i<charTypeVect.size();i++){
						characterM2 = (ScoreCharacterM)charTypeVect.get(i);
						if(scTypekey.equals(characterM2.getScoreCode())){
							if(print){
								log.debug("scoreTypeM2.getScoreWeight() = "+scoreTypeM2.getScoreWeight());
								log.debug("scoreTypeM2.getScoreCode() = "+scoreTypeM2.getScoreCode());
								log.debug("scoreTypeM2.getScoreType() = "+scoreTypeM2.getScoreType());
								log.debug("scoreTypeM2.getCusType() = "+scoreTypeM2.getCusType());
								print = false;
							}
							
							log.debug("characterM2.getScoreCode() = "+characterM2.getScoreCode());
							log.debug("characterM2.getCharCode() = "+characterM2.getCharCode());
							log.debug("characterM2.getCharType() = "+characterM2.getCharType());
							log.debug("characterM2.getMinRange() = "+characterM2.getMinRange());
							log.debug("characterM2.getMaxRange() = "+characterM2.getMaxRange());
							log.debug("characterM2.getSpecDesc() = "+characterM2.getSpecDesc());
							log.debug("characterM2.getScore() = "+characterM2.getScore());
						}
					}
				}
			}
		}
		
		//*** END Print
		
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
