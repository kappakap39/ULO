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
 * Type: EditAppScoreWebAction
 */
public class EditAppScoreWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(EditAppScoreWebAction.class);
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
		
		ORIGUtility utility = new ORIGUtility();
		
		ScoreCharacterM charTypeMEdit;
		Vector charMVectForSave;
		String charType="";
		int scoreSeq=-1;
		int seq=-1;
		String charCodeEdit="";
		String scoreCodeEdit="";
		
		charTypeMEdit = (ScoreCharacterM)getRequest().getSession().getAttribute("CHAR_TYPE_M_EDIT");
		charMVectForSave = (Vector)getRequest().getSession().getAttribute("SEL_AND_ADD_CHAR_M_VECT");
		log.debug("charMVectForSave = "+charMVectForSave);
		
		String minRange = getRequest().getParameter("minRange");
		String maxRange = getRequest().getParameter("maxRange");
		String score = getRequest().getParameter("score");
		String specific = getRequest().getParameter("specific");
		
		log.debug("minRange = "+minRange);
		log.debug("maxRange = "+maxRange);
		log.debug("score = "+score);
		log.debug("specific = "+specific);
		
		if(charTypeMEdit!=null){
			scoreCodeEdit = charTypeMEdit.getScoreCode();
			charCodeEdit = charTypeMEdit.getCharCode();
			scoreSeq = charTypeMEdit.getScoreSeq();
			seq = charTypeMEdit.getSeq();
			charType = charTypeMEdit.getCharType();
			
			log.debug("scoreCodeEdit = "+scoreCodeEdit);
			log.debug("charCodeEdit = "+charCodeEdit);
			log.debug("scoreSeq = "+scoreSeq);
			log.debug("seq = "+seq);
			log.debug("charType = "+charType);
		}
		
		String noDataChk = getRequest().getParameter("noDataChk");
		
		if(charMVectForSave!=null && charMVectForSave.size()>0){
			ScoreCharacterM characterM;
			
			String seq_session = (String)getRequest().getSession().getAttribute("SEQ");
			int seq_int = Integer.parseInt(seq_session);
			
			for(int i=0;i<charMVectForSave.size();i++){
				
				characterM = (ScoreCharacterM)charMVectForSave.get(i);
				
				if(characterM.getScoreCode().equals(scoreCodeEdit) && characterM.getCharCode().equals(charCodeEdit) 
						&& characterM.getScoreSeq()==scoreSeq && characterM.getSeq()==seq){
					
					if("checked".equalsIgnoreCase(noDataChk)){
						characterM.setCharType("N");
						charType = "N";
					}
				
					if("R".equalsIgnoreCase(charType)){
						characterM.setMinRange(utility.stringToDouble(minRange));
						characterM.setMaxRange(utility.stringToDouble(maxRange));
						characterM.setScore(utility.stringToDouble(score));
						characterM.setSeq(++seq_int);
					}else if("S".equalsIgnoreCase(charType)){
						characterM.setScore(utility.stringToDouble(score));
						characterM.setSeq(++seq_int);
					
						//*** get new specificDesc
						if(specific!=null){
							if(!specific.equals(characterM.getSpecific())){
								characterM.setSpecific(specific);
								
//								OrigMasterAppScoreDAO appScoreDAO = (OrigMasterAppScoreDAO)OrigMasterDAOFactory.getOrigMasterAppScoreDAO();
								try {
//									characterM = appScoreDAO.getNewSpecDesc(characterM);
									
									characterM = PLORIGEJBService.getORIGDAOUtilLocal().getNewSpecDesc(characterM);
									
									log.debug("characterM = "+characterM);
								} catch (Exception e) {
									log.fatal("",e);
								}
							}
						}
					}else if("N".equalsIgnoreCase(charType)){
						characterM.setSpecific("");
						characterM.setSpecDesc("");
						characterM.setMinRange(0);
						characterM.setMaxRange(0);
						characterM.setScore(utility.stringToDouble(score));
						characterM.setSeq(++seq_int);
					}
					charMVectForSave.setElementAt(characterM,i);
				}
			}
			
			getRequest().getSession().setAttribute("SEQ",String.valueOf(seq_int));
			getRequest().getSession().setAttribute("SEL_AND_ADD_CHAR_M_VECT",charMVectForSave);
			
			String scoreType = getRequest().getParameter("scoreType");
			String scoreCode="";
			if(scoreType!=null && !"".equalsIgnoreCase(scoreType)){
				String temp[];
				temp = scoreType.split(",");
				scoreCode = temp[0];
				scoreType = temp[1];
			}
			
			HashMap charTypeHash = (HashMap)getRequest().getSession().getAttribute("HASH_CHAR_TYPE");
			if(charTypeHash==null){
				charTypeHash = new HashMap();
			}
			charTypeHash.put(charCodeEdit,charMVectForSave);
			
			getRequest().getSession().setAttribute("HASH_CHAR_TYPE",charTypeHash);
			
			String cusType = (String)getRequest().getSession().getAttribute("CUS_TYPE");
			
			ScoreTypeM scoreTypeM = new ScoreTypeM();
			scoreTypeM.setScoreWeight(utility.stringToDouble(weight));
			scoreTypeM.setCusType(cusType);
			scoreTypeM.setScoreType(scoreType);
			scoreTypeM.setScoreCode(scoreCode);
			
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
