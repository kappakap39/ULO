package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.QuestionObjectDataM;

public class SubcardPaymentMethod extends VerifyQuestionCustomerMaster{
	private static transient Logger logger = Logger.getLogger(SubcardPaymentMethod.class);
	String SUBCARD_PAYMENT_METHOD_QUESTION = SystemConstant.getConstant("SUBCARD_PAYMENT_METHOD_QUESTION");
	String DISPLAY_MODE = HtmlUtil.EDIT;
	String CUSTOMER_NOT_ANS = SystemConstant.getConstant("CUSTOMER_NOT_ANS");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	String BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SUB_FOMR_ID ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	public String getElement(HttpServletRequest request, Object objectElement) {
		StringBuilder HTML = new StringBuilder();
		FormUtil formUtil = new FormUtil(SUB_FOMR_ID,request);
		IdentifyQuestionDataM identifyQuestion = (IdentifyQuestionDataM)objectElement;
		String QUESTION = CacheControl.getName("QuestionCacheDataM", SUBCARD_PAYMENT_METHOD_QUESTION);		
		QuestionObjectDataM questionObject = (QuestionObjectDataM)getObjectRequest();
		String questionSetCode = questionObject.getQuestionSetCode();
		
		ApplicationGroupDataM applicationGroup = questionObject.getApplicationGroup();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(questionObject.getPersonalId());
		String applicationType = applicationGroup.getApplicationType();
		logger.debug("applicationType >> "+applicationType);
		String personalId = personalInfo.getPersonalId();
		PaymentMethodDataM paymentMethodM = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		StringBuilder Result = new StringBuilder("");
		if(!Util.empty(paymentMethodM)){
			String paymentMethod = CacheControl.getName(SystemConstant.getConstant("FIELD_ID_PAYMENT_METHOD"), paymentMethodM.getPaymentMethod());
			String paymentRatio = CacheControl.getName(SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO"),String.valueOf(paymentMethodM.getPaymentRatio()));
			String accountNo = FormatUtil.getAccountNo(paymentMethodM.getAccountNo());
			Result.append(paymentMethod);
			if(!Util.empty(paymentRatio)){
				Result.append(" / ")
					.append(paymentRatio);
			}
			if(!Util.empty(accountNo)){
				Result.append(" / ")
					.append(accountNo);
			}
		}else{
			Result.append("-");
		}
		HTML.append("<td class='text-left'>"+QUESTION+"</td>")
		.append("<td>"+FormatUtil.display(Result.toString())+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(SUBCARD_PAYMENT_METHOD_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(),"", CUSTOMER_NOT_ANS, HtmlUtil.elementTagId(getQuestionTagId(SUBCARD_PAYMENT_METHOD_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(SUBCARD_PAYMENT_METHOD_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(),"", "Y", HtmlUtil.elementTagId(getQuestionTagId(SUBCARD_PAYMENT_METHOD_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(SUBCARD_PAYMENT_METHOD_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(),"", "N", HtmlUtil.elementTagId(getQuestionTagId(SUBCARD_PAYMENT_METHOD_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			/*.append("<td>"+FormatUtil.display(Result.toString())+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", SUBCARD_PAYMENT_METHOD_QUESTION+"_"+personalId, CUSTOMER_NOT_ANS,CUSTOMER_NOT_ANS, DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+SUBCARD_PAYMENT_METHOD_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", SUBCARD_PAYMENT_METHOD_QUESTION+"_"+personalId, identifyQuestion.getResult(),"Y", DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+SUBCARD_PAYMENT_METHOD_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", SUBCARD_PAYMENT_METHOD_QUESTION+"_"+personalId, identifyQuestion.getResult(),"N", DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+SUBCARD_PAYMENT_METHOD_QUESTION+"_"+personalId), "", "", request)+"</td>")*/
			.append(HtmlUtil.hidden("QUESTION_NO", SUBCARD_PAYMENT_METHOD_QUESTION));
		return HTML.toString();
	}

}
