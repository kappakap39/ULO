/*
 * Created on Nov 27, 2007
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

/**
 * @author Administrator
 *
 * Type: DeleteAppScoreWebAction
 */
public class DeleteAppScoreWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(DeleteAppScoreWebAction.class);

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
		
		String[] charTypeToDelete = getRequest().getParameterValues("charMChk");
		String[] scCde_ChCde_Seq;
		
		if(charTypeToDelete!=null && charTypeToDelete.length>0){
			for(int i = 0; i < charTypeToDelete.length; i++){
				scCde_ChCde_Seq = charTypeToDelete[i].split(",");
				log.debug("scoreCode = "+scCde_ChCde_Seq[0]);
				log.debug("charCode = "+scCde_ChCde_Seq[1]);
				log.debug("seq = "+scCde_ChCde_Seq[2]);
				
				Vector charMVectForSave = (Vector)getRequest().getSession().getAttribute("SEL_AND_ADD_CHAR_M_VECT");
				
				HashMap charTypeHash = (HashMap)getRequest().getSession().getAttribute("HASH_CHAR_TYPE");
				
				if(charMVectForSave!=null && charMVectForSave.size()>0){
					ScoreCharacterM characterM; 
					for(int j=0;j<charMVectForSave.size();j++){
						characterM = (ScoreCharacterM)charMVectForSave.get(j);
						
						if(scCde_ChCde_Seq[0].equals(characterM.getScoreCode()) 
								&& scCde_ChCde_Seq[1].equals(characterM.getCharCode())
										&& Integer.parseInt(scCde_ChCde_Seq[2])==characterM.getSeq()){
							log.debug("i'm removing charTypeM");
							charMVectForSave.remove(j);
						}
					}
					if(charTypeHash!=null && charTypeHash.size()>0){
						charTypeHash.put(scCde_ChCde_Seq[1],charMVectForSave);
						getRequest().getSession().setAttribute("HASH_CHAR_TYPE",charTypeHash);
					}
					getRequest().getSession().setAttribute("SEL_AND_ADD_CHAR_M_VECT",charMVectForSave);
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
	
	protected void doSuccess(EventResponse arg0) {
		
		//***Refresh Cache
    	log.debug("sorry i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshAll();
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
