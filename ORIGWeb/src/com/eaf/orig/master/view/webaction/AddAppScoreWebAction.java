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

import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.ScoreCharacterM;
import com.eaf.orig.master.shared.model.ScoreTypeM;
//import com.eaf.orig.shared.dao.OrigMasterAppScoreDAO;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: AddAppScoreWebAction
 */
public class AddAppScoreWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(AddAppScoreWebAction.class);
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
		
		//Remove SESSION
		getRequest().getSession().removeAttribute("CHAR_TYPE_M_EDIT");
		getRequest().getSession().removeAttribute("SPECIFIC_LIST_BOX");
		//END Remove
		
		ORIGUtility utility = new ORIGUtility();
		
		String charCode = getRequest().getParameter("charType");
		String scoreType = getRequest().getParameter("scoreType");
		String scoreCode="";
		String charDesc="";
		if(scoreType!=null && !"".equalsIgnoreCase(scoreType)){
			String temp[];
			temp = scoreType.split(",");
			scoreCode = temp[0];
			scoreType = temp[1];
		}
		if(charCode!=null && !"".equalsIgnoreCase(charCode)){
			String temp[];
			temp = charCode.split(",");
			charCode = temp[0];
			charDesc = temp[1];
		}
		
		String minRange = getRequest().getParameter("minRange");
		String maxRange = getRequest().getParameter("maxRange");
		String score = getRequest().getParameter("score");
		String specific = getRequest().getParameter("specific");
		
		String seq_session = (String)getRequest().getSession().getAttribute("SEQ");
		int seq_int = Integer.parseInt(seq_session);
		
		log.debug("minRange = "+minRange);
		log.debug("maxRange = "+maxRange);
		log.debug("score = "+score);
		log.debug("specific = "+specific);
		log.debug("scoreCode = "+scoreCode);
		log.debug("charCode = "+charCode);
		log.debug("seq = "+(seq_int+1));
		
		ScoreCharacterM characterM = new ScoreCharacterM();
		characterM.setScoreCode(scoreCode);
		characterM.setCharCode(charCode);
		characterM.setCharDesc(charDesc);
		characterM.setScore(utility.stringToDouble(score));
		characterM.setSeq(++seq_int);
		
		String noDataChk = getRequest().getParameter("noDataChk");
		
		if("checked".equalsIgnoreCase(noDataChk)){
			characterM.setCharType("N");
			characterM.setSpecDesc("");//***For Not show null on screen
		}else if("002".equals(charCode) || "003".equals(charCode) || "004".equals(charCode) || "006".equals(charCode)
				 || "008".equals(charCode) || "012".equals(charCode) || "013".equals(charCode) || "020".equals(charCode)
				 || "023".equals(charCode) || "029".equals(charCode) || "030".equals(charCode) || "036".equals(charCode)){
			
			characterM.setCharType("S");
			characterM.setSpecific(specific);
		}else{
			characterM.setCharType("R");
			characterM.setMinRange(utility.stringToDouble(minRange));
			characterM.setMaxRange(utility.stringToDouble(maxRange));
			characterM.setSpecDesc("");//***For Not show null on screen
		}
		
		getRequest().getSession().setAttribute("SEQ",String.valueOf(seq_int));
		
		//*** get specificDesc
		if(specific!=null && !"".equals(specific)){
//				OrigMasterAppScoreDAO appScoreDAO = (OrigMasterAppScoreDAO)OrigMasterDAOFactory.getOrigMasterAppScoreDAO();
				try {
//					characterM = appScoreDAO.getNewSpecDesc(characterM);
					
					characterM = PLORIGEJBService.getORIGDAOUtilLocal().getNewSpecDesc(characterM);
					
					log.debug("characterM = "+characterM);
				} catch (Exception e) {
					log.fatal("",e);
				}
		}
		
		Vector charMVectForSave = (Vector)getRequest().getSession().getAttribute("SEL_AND_ADD_CHAR_M_VECT");
		if(charMVectForSave!=null){
			charMVectForSave.add(characterM);
			getRequest().getSession().setAttribute("SEL_AND_ADD_CHAR_M_VECT",charMVectForSave);
			
			HashMap charTypeHash = (HashMap)getRequest().getSession().getAttribute("HASH_CHAR_TYPE");
			if(charTypeHash==null){
				charTypeHash = new HashMap();
			}
			charTypeHash.put(charCode,charMVectForSave);
			
			getRequest().getSession().setAttribute("HASH_CHAR_TYPE",charTypeHash);
			
			String cusType = (String)getRequest().getSession().getAttribute("CUS_TYPE");
			
			ScoreTypeM scoreTypeM = new ScoreTypeM();
			scoreTypeM.setScoreWeight(utility.stringToDouble(weight));
			scoreTypeM.setCusType(cusType);
			scoreTypeM.setScoreCode(scoreCode);
			scoreTypeM.setScoreType(scoreType);
			
			HashMap scoreTypeHash = (HashMap)getRequest().getSession().getAttribute("HASH_SCORE_TYPE");
			if(scoreTypeHash==null){
				scoreTypeHash = new HashMap();
			}
			scoreTypeHash.put(scoreCode,scoreTypeM);
			
			getRequest().getSession().setAttribute("HASH_SCORE_TYPE",scoreTypeHash);
			
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
