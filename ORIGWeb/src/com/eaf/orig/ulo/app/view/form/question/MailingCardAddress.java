package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.control.address.util.DisplayAddressUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.QuestionObjectDataM;

public class MailingCardAddress extends VerifyQuestionCustomerMaster{
	private static transient Logger logger = Logger.getLogger(MailingCardAddress.class);
	String MAILING_CARD_ADDRESS_QUESTION = SystemConstant.getConstant("MAILING_CARD_ADDRESS_QUESTION");
	String DISPLAY_MODE = HtmlUtil.EDIT;
	String CUSTOMER_NOT_ANS = SystemConstant.getConstant("CUSTOMER_NOT_ANS");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String CACHE_ID_ADDRESS_TYPE = SystemConstant.getConstant("FIELD_ID_ADDRESS_TYPE");
	String ADDRESS_TYPE_BY_APPLICANT = SystemConstant.getConstant("ADDRESS_TYPE_BY_APPLICANT");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String SUB_FOMR_ID ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	public String getElement(HttpServletRequest request, Object objectElement) {
		StringBuilder HTML = new StringBuilder();
		FormUtil formUtil = new FormUtil(SUB_FOMR_ID,request);
		IdentifyQuestionDataM identifyQuestion = (IdentifyQuestionDataM)objectElement;
		String QUESTION = CacheControl.getName("QuestionCacheDataM", MAILING_CARD_ADDRESS_QUESTION);
		
		QuestionObjectDataM questionObject = (QuestionObjectDataM)getObjectRequest();
		String questionSetCode = questionObject.getQuestionSetCode();
		
		ApplicationGroupDataM applicationGroup = questionObject.getApplicationGroup();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(questionObject.getPersonalId());
		String personalId = personalInfo.getPersonalId();
		String mailingAddress = personalInfo.getMailingAddress();
		AddressDataM addressM = personalInfo.getAddress(mailingAddress);
//		QUESTION = QUESTION.replace("[CUSTOMER_ADDRESS]", DisplayAddressUtil.displayAddress(addressM));
		String applicationtype = applicationGroup.getApplicationType();
		logger.debug("mailingAddress : "+mailingAddress);
		if(!Util.empty(mailingAddress) && ADDRESS_TYPE_BY_APPLICANT.equals(mailingAddress) && APPLICATION_TYPE_ADD_SUP.equals(applicationtype)){
			String addressTypeDesc = CacheControl.getName(CACHE_ID_ADDRESS_TYPE,mailingAddress);
			QUESTION = QUESTION.replace("[CUSTOMER_ADDRESS]", addressTypeDesc);
		}else{
			String textAddress = (null!=addressM)?DisplayAddressUtil.displayAddress(addressM):"";
			logger.debug("textAddress : "+textAddress);
			QUESTION = QUESTION.replace("[CUSTOMER_ADDRESS]", textAddress);
		}
		
//		PersonalRelationDataM PersonalRelation = personalInfo.getPersonalRelation(PersonalInfoDataM.PersonalType.SUPPLEMENTARY, PERSONAL_RELATION_APPLICATION_LEVEL);
		String RESULT = "-";
//		if(!Util.empty(PersonalRelation)){
//			String applicationRecordId=PersonalRelation.getRefId();
//		 	ApplicationDataM applicationItem = applicationGroup.getApplicationById(applicationRecordId);
//		 	if(!Util.empty(applicationItem)){
//		 		LoanDataM loan = applicationItem.getLoan();
//		 		if(!Util.empty(loan)){
//		 			RESULT = "-";
//		 		}
//		 	}
//		}
		
		HTML.append("<td class='text-left'>"+QUESTION+"</td>")
			.append("<td>"+FormatUtil.display(RESULT)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(MAILING_CARD_ADDRESS_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(),"", CUSTOMER_NOT_ANS, HtmlUtil.elementTagId(getQuestionTagId(MAILING_CARD_ADDRESS_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(MAILING_CARD_ADDRESS_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(),"", "Y", HtmlUtil.elementTagId(getQuestionTagId(MAILING_CARD_ADDRESS_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(MAILING_CARD_ADDRESS_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(),"", "N", HtmlUtil.elementTagId(getQuestionTagId(MAILING_CARD_ADDRESS_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			/*.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", MAILING_CARD_ADDRESS_QUESTION+"_"+personalId, CUSTOMER_NOT_ANS,CUSTOMER_NOT_ANS, DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+MAILING_CARD_ADDRESS_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", MAILING_CARD_ADDRESS_QUESTION+"_"+personalId, identifyQuestion.getResult(),"Y", DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+MAILING_CARD_ADDRESS_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", MAILING_CARD_ADDRESS_QUESTION+"_"+personalId, identifyQuestion.getResult(),"N", DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+MAILING_CARD_ADDRESS_QUESTION+"_"+personalId), "", "", request)+"</td>")*/
			.append(HtmlUtil.hidden("QUESTION_NO", MAILING_CARD_ADDRESS_QUESTION));
		return HTML.toString();
	}

}
