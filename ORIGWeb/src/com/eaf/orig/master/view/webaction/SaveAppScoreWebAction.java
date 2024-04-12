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
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.ScoreCharacterM;
import com.eaf.orig.master.shared.model.ScoreM;
import com.eaf.orig.master.shared.model.ScoreTypeM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.AppScoreEvent;
//import com.eaf.orig.shared.dao.OrigMasterAppScoreDAO;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: SaveAppScoreWebAction
 */
public class SaveAppScoreWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(SaveAppScoreWebAction.class);
	ScoreM scoreM;
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		AppScoreEvent appScoreEvent = new AppScoreEvent();
		
		appScoreEvent.setEventType(AppScoreEvent.APP_SCORE_SAVE);
		log.debug("AppScoreEvent.APP_SCORE_SAVE=" + AppScoreEvent.APP_SCORE_SAVE);
		appScoreEvent.setObject(scoreM);
		log.debug("scoreM=" + scoreM);
		log.debug("appScoreEvent=" + appScoreEvent);
		
		return appScoreEvent;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {

		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		
		return defaultProcessResponse(response);
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		
		String accept = (String)getRequest().getSession().getAttribute("ACCEPT");
		String reject = (String)getRequest().getSession().getAttribute("REJECT");
		String weight = (String)getRequest().getSession().getAttribute("WEIGHT");
		String accept_used = (String)getRequest().getSession().getAttribute("ACCEPT_USED");
		String reject_used = (String)getRequest().getSession().getAttribute("REJECT_USED");
		String scConstant = (String)getRequest().getSession().getAttribute("SC_CONSTANT");
		
		log.debug("accept = "+accept);
		log.debug("reject = "+reject);
		log.debug("accept_used = "+accept_used);
		log.debug("reject_used = "+reject_used);
		log.debug("weight = "+weight);
		log.debug("scConstant = "+scConstant);
		
		ORIGUtility utility = new ORIGUtility();
		
		String custype = (String)getRequest().getSession().getAttribute("CUS_TYPE");
		log.debug("custype = "+custype);
		
		//*** Get Score seq max
//		OrigMasterAppScoreDAO appScoreDAO = (OrigMasterAppScoreDAO)OrigMasterDAOFactory.getOrigMasterAppScoreDAO();
		int maxScoreSeq = -1;
		try {
//			maxScoreSeq = appScoreDAO.getMaxScoreSeq(custype);
			
			maxScoreSeq = PLORIGEJBService.getORIGDAOUtilLocal().getMaxScoreSeq(custype);
			
			log.debug("maxScoreSeq = "+maxScoreSeq);
		} catch (Exception e) {
			log.fatal("",e);
		}
		maxScoreSeq = maxScoreSeq+1;
		//*** END Get
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		
		HashMap scoreTypeHash = (HashMap)getRequest().getSession().getAttribute("HASH_SCORE_TYPE");
		HashMap charTypeHash = (HashMap)getRequest().getSession().getAttribute("HASH_CHAR_TYPE");
		
		if(scoreTypeHash!=null && scoreTypeHash.size()>0 && charTypeHash!=null && charTypeHash.size()>0){
			
			scoreM = new ScoreM();
			
			scoreM.setAcceptScore(utility.stringToDouble(accept));
			scoreM.setRejectScore(utility.stringToDouble(reject));
			scoreM.setAcceptScore_used(utility.stringToDouble(accept_used));
			scoreM.setRejectScore_used(utility.stringToDouble(reject_used));
			scoreM.setScoreConstant(utility.stringToDouble(scConstant));
			scoreM.setCusType(custype);
			scoreM.setCreateBy(userName);
			scoreM.setUpdateBy(userName);
			scoreM.setScoreSeq(maxScoreSeq);
			
			Vector scoreTypeVect = new Vector();
			
			Set scTypekeySet = scoreTypeHash.keySet();
			Iterator scTypekeyIt = scTypekeySet.iterator();
			String scTypekey;
			
			//*** Classify to Vector && Set ScoreSeq and Seq
			while(scTypekeyIt.hasNext()){
				
				scTypekey = (String)scTypekeyIt.next();
				ScoreTypeM scoreTypeM = (ScoreTypeM)scoreTypeHash.get(scTypekey);
				
				scoreTypeM.setScoreSeq(maxScoreSeq);
				scoreTypeM.setCreateBy(userName);
				scoreTypeM.setUpdateBy(userName);
				scoreTypeM.setCusType(custype);
				
				Vector chTypeSameScCdeVect = new Vector();
				
				Set chTypekeySet = charTypeHash.keySet();
				Iterator chTypekeyIt = chTypekeySet.iterator();
				String chTypekey;
				
				while(chTypekeyIt.hasNext()){
					chTypekey = (String)chTypekeyIt.next();
					 Vector charTypeVect = (Vector)charTypeHash.get(chTypekey);
				
					if(charTypeVect!=null && charTypeVect.size()>0){
						
						ScoreCharacterM characterM;
						
						int seq=0;
						for(int i=0;i<charTypeVect.size();i++){
							characterM = (ScoreCharacterM)charTypeVect.get(i);
							
							characterM.setCreateBy(userName);
							characterM.setUpdateBy(userName);
							characterM.setScoreSeq(maxScoreSeq);
							characterM.setSeq(++seq);
							
							if(scTypekey.equals(characterM.getScoreCode())){
								chTypeSameScCdeVect.add(characterM);
								
							}
						}
					}
				}
				
				scoreTypeM.setCharTypeMVect(chTypeSameScCdeVect);
				scoreTypeVect.add(scoreTypeM);
			}
			
			scoreM.setScoreTypeMVect(scoreTypeVect);
			
			//*** print log for check 
/*			ScoreTypeM scoreTypeM2;
			
			if(scoreTypeVect!=null && scoreTypeVect.size()>0){
				log.debug("!!! scoreTypeVect.size() = "+scoreTypeVect.size());
				for(int i=0;i<scoreTypeVect.size();i++){
					scoreTypeM2 = (ScoreTypeM)scoreTypeVect.get(i);
					Vector charTypeMVect2 = scoreTypeM2.getCharTypeMVect();
					
					if(charTypeMVect2!=null && charTypeMVect2.size()>0){
						log.debug("!!! charTypeMVect2.size() = "+charTypeMVect2.size());
						ScoreCharacterM characterM2;
						
						for(int j=0;j<charTypeMVect2.size();j++){
							characterM2 = (ScoreCharacterM)charTypeMVect2.get(j);
							
							log.debug("characterM2.getScoreCode() = "+characterM2.getScoreCode());
							log.debug("characterM2.getCharCode() = "+characterM2.getCharCode());
							log.debug("characterM2.getMinRange() = "+characterM2.getMinRange());
							log.debug("characterM2.getMaxRange() = "+characterM2.getMaxRange());
							log.debug("characterM2.getSpecific() = "+characterM2.getSpecific());
							log.debug("characterM2.getScore() = "+characterM2.getScore());
							log.debug("characterM2.getCharType() = "+characterM2.getCharType());
							log.debug("--------------------------------");
						}
					}
				}
				
			}*/
			//*** End print
			
			return true;
		}
		
		
		return false;
	}

//	public int getNextActivityType() {
//        return FrontController.FORWARD;
//    }
//    /* (non-Javadoc)
//	 * @see com.bara.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
//	 */
//	public String getNextActionParameter() {
//		return "orig/appform/filterMainScreen.jsp";
//	}
	
	public int getNextActivityType() {
        return FrontController.FORWARD;
    }
    
    public String getNextActionParameter() {
        return "orig/master/summitAppScore.jsp";
    }
	
	protected void doFail(EventResponse arg0) {
		log.debug("sorry i'm in do fail.!!!" );
	}
	 protected void doSuccess(EventResponse arg0) {
	 	ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
		log.debug("origMasterForm.getShwAddFrm() = "+origMasterForm.getShwAddFrm());
		log.debug("origMasterForm.getShwAppScore() = "+origMasterForm.getShwAppScore());
		
//		***Remove SESSION
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
		
		//***Refresh Cache
	 	log.debug("i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshCache("Scoring");
		com.eaf.cache.TableLookupCache.refreshCache("ScoringCharecteristic");
		com.eaf.cache.TableLookupCache.refreshCache("ScoringType");
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
