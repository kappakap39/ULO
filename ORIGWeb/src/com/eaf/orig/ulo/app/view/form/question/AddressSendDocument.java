package com.eaf.orig.ulo.app.view.form.question;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.QuestionObjectDataM;

public class AddressSendDocument extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(AddressSendDocument.class);
	String SUB_FOMR_ID ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		QuestionObjectDataM questionObject = (QuestionObjectDataM)getObjectRequest();
		ApplicationGroupDataM applicationGroup = questionObject.getApplicationGroup();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(questionObject.getPersonalId());
		String personalId = personalInfo.getPersonalId();
		logger.debug("AddressSendDocument >> "+personalId);
		return "/orig/ulo/subform/question/AddressSendDocument.jsp?PERSONAL_ID="+personalId;
	}

	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request, Object objectElement) {
		logger.debug("processElement >> "+AddressSendDocument.class);
		PersonalInfoDataM personalInfo =null;
		if(objectElement instanceof PersonalInfoDataM){
			personalInfo = (PersonalInfoDataM)objectElement;
		}else if(objectElement instanceof IdentifyQuestionDataM){
			QuestionObjectDataM questionObject = (QuestionObjectDataM)getObjectRequest();
			ApplicationGroupDataM applicationGroup = questionObject.getApplicationGroup();
			personalInfo = applicationGroup.getPersonalById(questionObject.getPersonalId());
		}
		String ADDRESS_SEND_DOCUMENT = request.getParameter("ADDRESS_SEND_DOCUMENT_"+personalInfo.getPersonalId());
		logger.debug("ADDRESS_SEND_DOCUMENT >> "+ADDRESS_SEND_DOCUMENT);
		logger.debug("personalId >> "+personalInfo.getPersonalId());
		
		if(!Util.empty(personalInfo)){
			personalInfo.setPlaceReceiveCard(ADDRESS_SEND_DOCUMENT);
		}
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		String elementId  ="ADDRESS_SEND_DOCUMENT_"+personalInfo.getPersonalId();
		String ADDRESS_SEND_DOCUMENT = request.getParameter(elementId);
		logger.debug("ADDRESS_SEND_DOCUMENT >> "+ADDRESS_SEND_DOCUMENT);
		formError.mandatory(SUB_FOMR_ID,"ADDRESS_SEND_DOCUMENT",elementId,"", ADDRESS_SEND_DOCUMENT,request);	
		return formError.getFormError();
		
	}
}
