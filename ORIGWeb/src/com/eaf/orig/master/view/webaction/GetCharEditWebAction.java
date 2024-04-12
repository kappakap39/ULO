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
import com.eaf.orig.master.shared.model.ScoreCharacterM;
//import com.eaf.orig.shared.dao.OrigMasterAppScoreDAO;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
import com.eaf.orig.shared.utility.ORIGMasterUtility;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: GetCharEditWebAction
 */
public class GetCharEditWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(GetCharEditWebAction.class);
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
		
		String charEdit = getRequest().getParameter("charEdit");
		String temp[];
		String scoreSeq="";
		String seq="";
		String scoreCode="";
		String charCode="";
		String disableForm="";
		
		if(charEdit!=null){
			temp = charEdit.split(",");
			scoreSeq = temp[0];
			seq =	temp[1];
			scoreCode =	temp[2];
			charCode =	temp[3];
			
			log.debug("scoreSeq = "+scoreSeq);
			log.debug("seq = "+seq);
			log.debug("scoreCode = "+scoreCode);
			log.debug("charCode = "+charCode);
			
			if((scoreSeq!=null && !"".equalsIgnoreCase(scoreSeq)) 
					&& (seq!=null && !"".equalsIgnoreCase(seq))
					&& (scoreCode!=null && !"".equalsIgnoreCase(scoreCode))
					&& (charCode!=null && !"".equalsIgnoreCase(charCode))){
				
				Vector selCharMVect = (Vector)getRequest().getSession().getAttribute("SEL_AND_ADD_CHAR_M_VECT");
				ScoreCharacterM charTypeMEdit=null;
				ScoreCharacterM characterM;
				
				if(selCharMVect!=null && selCharMVect.size()>0){
					for(int i=0;i<selCharMVect.size();i++){
						characterM = (ScoreCharacterM)selCharMVect.get(i);
						
						if(characterM.getScoreCode().equals(scoreCode) && characterM.getCharCode().equals(charCode)
								&& characterM.getScoreSeq()==utility.stringToInt(scoreSeq) 
								&&  characterM.getSeq()==utility.stringToInt(seq)){
							
							charTypeMEdit = characterM;
						}
					}
					//***Set DISABLE_FORM
					if("N".equals(charTypeMEdit.getCharType())){
						disableForm = "yes";
					}
					getRequest().getSession().setAttribute("DISABLE_FORM",disableForm);
				}
				
//				OrigMasterAppScoreDAO appScoreDAO = (OrigMasterAppScoreDAO)OrigMasterDAOFactory.getOrigMasterAppScoreDAO();
				Vector charTypeSpecificVect = new Vector();
				try {
//					charTypeSpecificVect = appScoreDAO.getCharTypeSpecificVect(scoreCode, charCode);
					
					charTypeSpecificVect = PLORIGEJBService.getORIGDAOUtilLocal().getCharTypeSpecificVect(scoreCode, charCode);
					
					log.debug("charTypeSpecificVect = "+charTypeSpecificVect);
				} catch (Exception e) {
					log.fatal("",e);
				}
				getRequest().getSession().setAttribute("CHAR_TYPE_M_EDIT",charTypeMEdit);
				getRequest().getSession().setAttribute("SPECIFIC_LIST_BOX",charTypeSpecificVect);
				getRequest().getSession().setAttribute("SPECIFIC_EDIT",charTypeMEdit.getSpecific());
				
				//Wichaya add
				ORIGMasterUtility masterUtil = new ORIGMasterUtility();
				getRequest().getSession().setAttribute("SPECIFIC_LIST_BOX_MASTER",masterUtil.getSpecificValueList(charCode));
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
