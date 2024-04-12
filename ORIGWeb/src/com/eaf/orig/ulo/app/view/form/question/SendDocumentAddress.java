package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SendDocumentAddress extends VerifyQuestionCustomerMaster{
	private static transient Logger logger = Logger.getLogger(SendDocumentAddress.class);
	String SEND_DOCUMENT_ADDRESS_QUESTION = SystemConstant.getConstant("SEND_DOCUMENT_ADDRESS_QUESTION");
	String ANSWER = SystemConstant.getConstant("RESULT_QUESTION_"+SEND_DOCUMENT_ADDRESS_QUESTION);
	String DISPLAY_MODE = HtmlUtil.EDIT;
	String CUSTOMER_NOT_ANS = SystemConstant.getConstant("CUSTOMER_NOT_ANS");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	
	public String getElement(HttpServletRequest request, Object objectElement) {
		StringBuilder HTML = new StringBuilder();
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)getObjectRequest();
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		String personalId = personalInfo.getPersonalId();
		IdentifyQuestionDataM identifyQuestion = (IdentifyQuestionDataM)objectElement;
		String QUESTION = CacheControl.getName("QuestionCacheDataM", SEND_DOCUMENT_ADDRESS_QUESTION);
		
//		HTML.append("<td class='text-left'>"+QUESTION+"</td>")
		HTML.append("<td>"+HtmlUtil.icon("CusAnswer", DISPLAY_MODE, "btn_document_question", "onclick=popUpResultQuestion('"+ANSWER+"')", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", SEND_DOCUMENT_ADDRESS_QUESTION+"_"+personalId, identifyQuestion.getResult(),CUSTOMER_NOT_ANS, DISPLAY_MODE, HtmlUtil.elementTagId("RESULT_CHECK_"+SEND_DOCUMENT_ADDRESS_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", SEND_DOCUMENT_ADDRESS_QUESTION+"_"+personalId, identifyQuestion.getResult(),"Y", DISPLAY_MODE, HtmlUtil.elementTagId("RESULT_CHECK_"+SEND_DOCUMENT_ADDRESS_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", SEND_DOCUMENT_ADDRESS_QUESTION+"_"+personalId, identifyQuestion.getResult(),"N", DISPLAY_MODE, HtmlUtil.elementTagId("RESULT_CHECK_"+SEND_DOCUMENT_ADDRESS_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append(HtmlUtil.hidden("QUESTION_NO", SEND_DOCUMENT_ADDRESS_QUESTION));
		return HTML.toString();
	}

}
