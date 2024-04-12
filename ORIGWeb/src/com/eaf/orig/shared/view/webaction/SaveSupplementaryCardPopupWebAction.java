/*
 * Created on Dec 15, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.CardInformationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.PopulatePopupM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveSupplementaryCardPopupWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(SaveSupplementaryCardPopupWebAction.class);
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		try{
			ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");
            ORIGUtility utility = new ORIGUtility();
            String card_id = getRequest().getParameter("card_id");
            ORIGForm.setPopupForm(null);
            ApplicationDataM applicationDataM = ORIGForm.getAppForm();
            PersonalInfoDataM personalInfoDataM = (PersonalInfoDataM) getRequest().getSession().getAttribute("SUPCARD_POPUP_DATA");
            logger.debug("personalInfoDataM="+personalInfoDataM);
            UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
	        if(ORIGUtility.isEmptyString(personalInfoDataM.getCreateBy())){
	        	personalInfoDataM.setCreateBy(userM.getUserName());
	        }else{
	        	personalInfoDataM.setUpdateBy(userM.getUserName());
	        }
	        personalInfoDataM.setIdNo(card_id);
            logger.debug("personalInfoDataM.getPersonalSeq() = "+personalInfoDataM.getPersonalSeq());
            logger.debug("personalInfoDataM.getPersonalType() = "+personalInfoDataM.getPersonalType());
            if(personalInfoDataM.getPersonalSeq() == 0){
                personalInfoDataM.setPersonalSeq(utility.getPersonalSizeByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_SUP_CARD)+1);
                personalInfoDataM.setCmpCode(utility.getGeneralParamByCode(OrigConstant.ORIG_CMPCDE));
                ORIGForm.getAppForm().getPersonalInfoVect().add(personalInfoDataM);
                 //Case Add
                applicationDataM.setIsReExcuteAppScoreFlag(true);
                applicationDataM.setScorringResult(null);
                logger.debug("personalInfoDataM Re Excute Appscore Case add");
            }else{
                PersonalInfoDataM personalInfoDataMTmp = utility.getPersonalInfoByTypeAndSeq(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_SUP_CARD, personalInfoDataM.getPersonalSeq());
                personalInfoDataMTmp = personalInfoDataM;                
            }
            
            //**   Save Card Information   **//
            logger.debug("card id : "+card_id);
        	Vector vecCardInformation = applicationDataM.getCardInformation();
        	if (vecCardInformation==null){
            	vecCardInformation = new Vector();
            }
            logger.debug("vecCardInformation size begin : "+vecCardInformation.size());
            boolean isNewCard = true;
            CardInformationDataM cardInformationDataM = new CardInformationDataM();
            for (int i=0;i<vecCardInformation.size();i++){
            	CardInformationDataM temp = (CardInformationDataM)vecCardInformation.get(i);
            	if (OrigConstant.CARD_INFORMATION_APPLICATION_TYPE_SUB.equalsIgnoreCase(temp.getApplicationType()) && card_id.equals(temp.getIdNo())){
            		cardInformationDataM = temp;
            		isNewCard = false;
            		break;
            	}
            }
            cardInformationDataM.setApplication_record_id(applicationDataM.getAppRecordID());
            cardInformationDataM.setApplicationType(OrigConstant.CARD_INFORMATION_APPLICATION_TYPE_SUB);
//            cardInformationDataM.setCardFace(cardFace);
//            cardInformationDataM.setCardSeq(1);
//            cardInformationDataM.setCardType(cardType);
            if (ORIGUtility.isEmptyString(cardInformationDataM.getCreatedBy())){
    	        cardInformationDataM.setCreatedBy(userM.getUserName());
            }
//            cardInformationDataM.setEmbossName(embossName);
            cardInformationDataM.setIdNo(card_id);
            cardInformationDataM.setUpdatedBy(userM.getUserName());
            if (isNewCard){
            	vecCardInformation.add(cardInformationDataM);
            }
            applicationDataM.setCardInformation(vecCardInformation);
            logger.debug("vecCardInformation size end : "+vecCardInformation.size());
            //** End Save Card Information **//
            
            Vector subCardVect = utility.getVectorPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_SUP_CARD);
            logger.debug("subCardVect size = "+subCardVect.size());
            
            //Rewrite
	        String tableData = utility.getSupplementaryCardTable(subCardVect, getRequest(), vecCardInformation);
	        logger.debug("table data = "+tableData);
	        
	        PopulatePopupM populatePopupM = new PopulatePopupM("SupplementaryCard",ORIGDisplayFormatUtil.replaceDoubleQuot(tableData.toString()));
			Vector popMs = new Vector();
			popMs.add(populatePopupM);
			getRequest().getSession(true).setAttribute("POPULATE_POPUP",popMs);
			popMs = (Vector)getRequest().getSession(true).getAttribute("POPULATE_POPUP");
			
            getRequest().getSession().setAttribute("PersonalType",OrigConstant.PERSONAL_TYPE_APPLICANT);
    		getRequest().getSession().removeAttribute("SUPCARD_POPUP_DATA");
    		getRequest().getSession().removeAttribute("MAIN_POPUP_DATA");
		}catch(Exception e){
			logger.error("Error in SaveSupplementaryCardPopupWebAction.preModelRequest()", e);
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		// TODO Auto-generated method stub
		return FrontController.FORWARD;
	}
    /* (non-Javadoc)
	 * @see com.bara.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
	 */
	public String getNextActionParameter() {
		return "orig/appform/filterMainScreen.jsp";
	}
	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
