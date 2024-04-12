package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.QuestionObjectDataM;

public class DateOfBirth extends VerifyQuestionCustomerMaster{
	private static transient Logger logger = Logger.getLogger(DateOfBirth.class);
	String DATE_OF_BIRTH_QUESTION = SystemConstant.getConstant("DATE_OF_BIRTH_QUESTION");
	String ANSWER = SystemConstant.getConstant("RESULT_QUESTION_"+DATE_OF_BIRTH_QUESTION);
	String DISPLAY_MODE = HtmlUtil.EDIT;
	String CUSTOMER_NOT_ANS = SystemConstant.getConstant("CUSTOMER_NOT_ANS");
	String SUB_FOMR_ID ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	
	public String getElement(HttpServletRequest request, Object objectElement) {
		StringBuilder HTML = new StringBuilder();
		FormUtil formUtil = new FormUtil(SUB_FOMR_ID,request);
		QuestionObjectDataM questionObject = (QuestionObjectDataM)getObjectRequest();
		String questionSetCode = questionObject.getQuestionSetCode();
		
		ApplicationGroupDataM applicationGroup = questionObject.getApplicationGroup();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(questionObject.getPersonalId());
		String personalId = personalInfo.getPersonalId();
		IdentifyQuestionDataM identifyQuestion = (IdentifyQuestionDataM)objectElement;
		String QUESTION = CacheControl.getName("QuestionCacheDataM", DATE_OF_BIRTH_QUESTION);
		
		HTML.append("<td class='text-left'>"+QUESTION+"</td>")
			.append("<td>"+HtmlUtil.icon("CusAnswer", DISPLAY_MODE, "btn_document_question", "onclick=popUpResultQuestion('"+ANSWER+"')", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(DATE_OF_BIRTH_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(), "", CUSTOMER_NOT_ANS, HtmlUtil.elementTagId(getQuestionTagId(DATE_OF_BIRTH_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(DATE_OF_BIRTH_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(),"", "Y", HtmlUtil.elementTagId(getQuestionTagId(DATE_OF_BIRTH_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(DATE_OF_BIRTH_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(),"", "N", HtmlUtil.elementTagId(getQuestionTagId(DATE_OF_BIRTH_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			
			/*.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", DATE_OF_BIRTH_QUESTION+"_"+personalId, CUSTOMER_NOT_ANS,CUSTOMER_NOT_ANS, DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+DATE_OF_BIRTH_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", DATE_OF_BIRTH_QUESTION+"_"+personalId, identifyQuestion.getResult(),"Y", DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+DATE_OF_BIRTH_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", DATE_OF_BIRTH_QUESTION+"_"+personalId, identifyQuestion.getResult(),"N", DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+DATE_OF_BIRTH_QUESTION+"_"+personalId), "", "", request)+"</td>")*/
			.append(HtmlUtil.hidden("QUESTION_NO", DATE_OF_BIRTH_QUESTION));
		return HTML.toString();
	}
}
