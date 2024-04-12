package com.eaf.orig.ulo.app.view.form.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.QuestionObjectDataM;

public class ApplyForSubcardToOther extends VerifyQuestionCustomerMaster{
	private static transient Logger logger = Logger.getLogger(ApplyForSubcardToOther.class);
	String APPLY_FOR_SUBCARD_TO_OTHER_QUESTION = SystemConstant.getConstant("APPLY_FOR_SUBCARD_TO_OTHER_QUESTION");
	String DISPLAY_MODE = HtmlUtil.EDIT;
	String CUSTOMER_NOT_ANS = SystemConstant.getConstant("CUSTOMER_NOT_ANS");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String SUB_FOMR_ID ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	public String getElement(HttpServletRequest request, Object objectElement) {
		StringBuilder HTML = new StringBuilder();
		FormUtil formUtil = new FormUtil(SUB_FOMR_ID,request);
		QuestionObjectDataM questionObject = (QuestionObjectDataM)getObjectRequest();
		String questionSetCode = questionObject.getQuestionSetCode();
		
		ApplicationGroupDataM applicationGroup = questionObject.getApplicationGroup();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(questionObject.getPersonalId());
		String personalId = personalInfo.getPersonalId();
		String name = personalInfo.getThFirstName();
		String surname = personalInfo.getThLastName(); 
		
		IdentifyQuestionDataM identifyQuestion = (IdentifyQuestionDataM)objectElement;
		String question = CacheControl.getName("QuestionCacheDataM", APPLY_FOR_SUBCARD_TO_OTHER_QUESTION);
		
		String cardInfos = "-";
		List<String> results = new ArrayList<String>();

		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationCardTypeLifeCycle(APPLICATION_CARD_TYPE_SUPPLEMENTARY);
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				CardDataM card = application.getCard();
				if(!Util.empty(card)){
					HashMap<String,Object> supplemantaryCardInfo = CardInfoUtil.getCardInfo(card.getCardType());
		 	 		String supplemantaryCardCode = SQLQueryEngine.display(supplemantaryCardInfo,"CARD_CODE");
					String supplemantaryCardLevel = SQLQueryEngine.display(supplemantaryCardInfo,"CARD_LEVEL");
					String cardType = CacheControl.getName(FIELD_ID_CARD_TYPE, supplemantaryCardCode,"DISPLAY_NAME");
					String cardLevel = CacheControl.getName(FIELD_ID_CARD_LEVEL, supplemantaryCardLevel,"DISPLAY_NAME");
					StringBuilder cardInfo = new StringBuilder();
						cardInfo.append(cardType);
						if(!Util.empty(cardLevel)){
							cardInfo.append("/")
								.append(cardLevel);
						}
					results.add(cardInfo.toString());
				}
			}
			
			cardInfos = StringUtils.join(results, ',');
			question = StringUtils.replace(question, "[CARD_TYPE],[CARD_LEVEL]", cardInfos);
			question = StringUtils.replace(question, "[NAME]", name);
			question = StringUtils.replace(question, "[SURNAME]", surname);
	 	}
		
		HTML.append("<td class='text-left'>"+question+"</td>")
			.append("<td>"+FormatUtil.display(cardInfos)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(APPLY_FOR_SUBCARD_TO_OTHER_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(), "", CUSTOMER_NOT_ANS, HtmlUtil.elementTagId(getQuestionTagId(APPLY_FOR_SUBCARD_TO_OTHER_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(APPLY_FOR_SUBCARD_TO_OTHER_QUESTION, questionSetCode, personalId), "", "", identifyQuestion.getResult(),"", "Y", HtmlUtil.elementTagId(getQuestionTagId(APPLY_FOR_SUBCARD_TO_OTHER_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(APPLY_FOR_SUBCARD_TO_OTHER_QUESTION, questionSetCode, personalId), "", "", identifyQuestion.getResult(),"", "N", HtmlUtil.elementTagId(getQuestionTagId(APPLY_FOR_SUBCARD_TO_OTHER_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
/*			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", APPLY_FOR_SUBCARD_TO_OTHER_QUESTION+"_"+personalId, CUSTOMER_NOT_ANS,CUSTOMER_NOT_ANS, DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+APPLY_FOR_SUBCARD_TO_OTHER_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", APPLY_FOR_SUBCARD_TO_OTHER_QUESTION+"_"+personalId, identifyQuestion.getResult(),"Y", DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+APPLY_FOR_SUBCARD_TO_OTHER_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", APPLY_FOR_SUBCARD_TO_OTHER_QUESTION+"_"+personalId, identifyQuestion.getResult(),"N", DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+APPLY_FOR_SUBCARD_TO_OTHER_QUESTION+"_"+personalId), "", "", request)+"</td>")
*/			.append(HtmlUtil.hidden("QUESTION_NO", APPLY_FOR_SUBCARD_TO_OTHER_QUESTION));
		return HTML.toString();
	}

}
