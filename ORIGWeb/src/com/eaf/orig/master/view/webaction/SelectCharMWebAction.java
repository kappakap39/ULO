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
import com.eaf.orig.master.shared.model.ScoreCharacterM;
//import com.eaf.orig.shared.dao.OrigMasterAppScoreDAO;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: SelectCharMWebAction
 */
public class SelectCharMWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(SelectCharMWebAction.class);
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
		
		//***Remove SESSION
		getRequest().getSession().removeAttribute("DISABLE_FORM");
		//***END Remove
		
		String scoreType = getRequest().getParameter("scoreType");
		String scoreCode="";
		log.debug("scoreType = "+scoreType);
		
		if(scoreType!=null && !"".equalsIgnoreCase(scoreType)){
			String temp[];
			temp = scoreType.split(",");
			scoreCode = temp[0];
		
			String charCode = getRequest().getParameter("charType"); //*** get charCode from SelectTag named charType
			String temp2[];
			temp2 = charCode.split(",");
			charCode = temp2[0];
			
			log.debug("charCode = "+charCode);
			
			if((charCode!=null && !"".equalsIgnoreCase(charCode)) && (scoreCode!=null && !"".equalsIgnoreCase(scoreCode))){
				
				HashMap charTypeHash = (HashMap)getRequest().getSession().getAttribute("HASH_CHAR_TYPE");
				HashMap scoreTypeHash = (HashMap)getRequest().getSession().getAttribute("HASH_SCORE_TYPE");
				Vector charTypeMVect = new Vector();
				boolean hvCharVect=true;
				boolean hvScoreCdeInHash=true;
				
				if(charTypeHash!=null){
					if(charTypeHash.containsKey(charCode)){
						charTypeMVect = (Vector)charTypeHash.get(charCode);
					}else{
						hvCharVect = false;
					}
				}
				if(scoreTypeHash!=null){
					if(!scoreTypeHash.containsKey(scoreCode)){
						hvScoreCdeInHash = false;
					}
				}
				if(charTypeHash == null || !hvCharVect || !hvScoreCdeInHash){
//					OrigMasterAppScoreDAO appScoreDAO = (OrigMasterAppScoreDAO)OrigMasterDAOFactory.getOrigMasterAppScoreDAO();
					charTypeMVect = new Vector();
					
					try {
//						charTypeMVect = appScoreDAO.selectCharM(scoreCode,charCode);
						
						charTypeMVect = PLORIGEJBService.getORIGDAOUtilLocal().selectCharM(scoreCode, charCode);
						
						log.debug("charTypeMVect = "+charTypeMVect);
						log.debug("charTypeMVect.size() = "+charTypeMVect.size());
					} catch (Exception e) {
						log.fatal("",e);
					}
				}
				
				//*** Assign Seq
				int seq=0;
				if(charTypeMVect!=null && charTypeMVect.size()>0){
					ScoreCharacterM characterM; 
					for(int i=0;i<charTypeMVect.size();i++){
						characterM = (ScoreCharacterM)charTypeMVect.get(i);
						characterM.setSeq(++seq);
						
					}
				}
				getRequest().getSession().setAttribute("SEQ",String.valueOf(seq));
				//*** END Assign
				
				getRequest().getSession().setAttribute("SEL_AND_ADD_CHAR_M_VECT",charTypeMVect);
				getRequest().getSession().setAttribute("CHAR_CODE",charCode);
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
