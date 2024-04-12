/*
 * Created on Dec 11, 2008
 * Created by wichaya
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
package com.eaf.orig.shared.view.form.subform;

import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.CardInformationDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author wichaya
 *
 * Type: CardInformationSubForm
 */
public class CardInformationSubForm extends ORIGSubForm {
    Logger logger = Logger.getLogger(CardInformationSubForm.class);

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
        // TODO Auto-generated method stub
    	logger.debug("CardInformationSubForm");
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        String cardType = request.getParameter("cardType");
        String cardFace = request.getParameter("cardFace");
        String embossName = request.getParameter("embossName");
        String card_id = request.getParameter("card_id");
        
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        Vector vecCardInformation = applicationDataM.getCardInformation();
        if (vecCardInformation==null){
        	vecCardInformation = new Vector();
        }
        
        /** Main Card Information **/
        ORIGFormHandler popupForm = (ORIGFormHandler) formHandler.getPopupForm();
        CardInformationDataM cardInformationDataM = new CardInformationDataM();
        if (popupForm==null || !"SUPCARD_FORM".equalsIgnoreCase(popupForm.getFormID())){
        	logger.debug("CardInformationSubForm ---> Save main card");
	        boolean isNewMainCard = true;
	        for (int i=0;i<vecCardInformation.size();i++){
	        	CardInformationDataM temp = (CardInformationDataM)vecCardInformation.get(i);
	        	if (OrigConstant.CARD_INFORMATION_APPLICATION_TYPE_MAIN.equalsIgnoreCase(temp.getApplicationType())){
	        		cardInformationDataM = temp;
	        		isNewMainCard = false;
	        		break;
	        	}
	        }
	        cardInformationDataM.setApplication_record_id(applicationDataM.getAppRecordID());
	        cardInformationDataM.setApplicationType(OrigConstant.CARD_INFORMATION_APPLICATION_TYPE_MAIN);
	        cardInformationDataM.setCardFace(cardFace);
	        cardInformationDataM.setCardSeq(1);
	        cardInformationDataM.setCardType(cardType);
	        if (ORIGUtility.isEmptyString(cardInformationDataM.getCreatedBy())){
		        cardInformationDataM.setCreatedBy(userM.getUserName());
	        }
	        cardInformationDataM.setEmbossName(embossName);
	        cardInformationDataM.setIdNo(card_id);
	        cardInformationDataM.setUpdatedBy(userM.getUserName());
	        if (isNewMainCard){
	        	vecCardInformation.add(0, cardInformationDataM);
	        }
        }
        /**     End Main Card     **/
        
        /** Sub Card Information **/
        for (int i=0;i<vecCardInformation.size();i++){
        	CardInformationDataM tempCardInformationDataM = (CardInformationDataM)vecCardInformation.get(i);
	        if (OrigConstant.CARD_INFORMATION_APPLICATION_TYPE_SUB.equals(tempCardInformationDataM.getApplicationType())){
	        	logger.debug("CardInformationSubForm ---> Save supplementary card");
	        	tempCardInformationDataM.setApplication_record_id(applicationDataM.getAppRecordID());
	        	tempCardInformationDataM.setApplicationType(OrigConstant.CARD_INFORMATION_APPLICATION_TYPE_SUB);
	        	tempCardInformationDataM.setCardFace(cardFace);
	        	tempCardInformationDataM.setCardSeq(i+1);
	        	tempCardInformationDataM.setCardType(cardType);
	            if (ORIGUtility.isEmptyString(cardInformationDataM.getCreatedBy())){
	            	tempCardInformationDataM.setCreatedBy(userM.getUserName());
	            }
	            tempCardInformationDataM.setEmbossName(embossName);
//	            tempCardInformationDataM.setIdNo(card_id);
	            tempCardInformationDataM.setUpdatedBy(userM.getUserName());
        	}
        }
        /**     End Main Card    **/
        applicationDataM.setCardInformation(vecCardInformation);
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateForm(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public HashMap validateForm(HttpServletRequest request, Object appForm) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateSubForm(javax.servlet.http.HttpServletRequest)
     */
    public boolean validateSubForm(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return false;
    }
}
