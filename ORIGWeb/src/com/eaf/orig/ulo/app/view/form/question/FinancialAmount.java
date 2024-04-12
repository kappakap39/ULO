package com.eaf.orig.ulo.app.view.form.question;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.QuestionObjectDataM;

public class FinancialAmount extends VerifyQuestionCustomerMaster{
	private static transient Logger logger = Logger.getLogger(FinancialAmount.class);
	String FINANCIAL_AMOUNT_QUESTION = SystemConstant.getConstant("FINANCIAL_AMOUNT_QUESTION");
	String DISPLAY_MODE = HtmlUtil.EDIT;
	String CUSTOMER_NOT_ANS = SystemConstant.getConstant("CUSTOMER_NOT_ANS");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String SUB_FOMR_ID ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	String PERCENT_LIMIT_MAINCARD_MANUAL = SystemConstant.getConstant("PERCENT_LIMIT_MAINCARD_MANUAL");
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	
	public String getElement(HttpServletRequest request, Object objectElement) {
		FormUtil formUtil = new FormUtil(SUB_FOMR_ID,request);
		StringBuilder HTML = new StringBuilder();
		IdentifyQuestionDataM identifyQuestion = (IdentifyQuestionDataM)objectElement;
		String QUESTION = CacheControl.getName("QuestionCacheDataM", FINANCIAL_AMOUNT_QUESTION);
		
		QuestionObjectDataM questionObject = (QuestionObjectDataM)getObjectRequest();
		String questionSetCode = questionObject.getQuestionSetCode();
		ApplicationGroupDataM applicationGroup = questionObject.getApplicationGroup();
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(questionObject.getPersonalId());
		String personalId = personalInfo.getPersonalId();
		
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationCardTypeLifeCycle(APPLICATION_CARD_TYPE_SUPPLEMENTARY);
		String RESULT = "-";
		List<String> results = new ArrayList<String>();
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				if(!Util.empty(application)){
					CardDataM card = application.getCard();
					if(!Util.empty(card)){
						if(PERCENT_LIMIT_MAINCARD_MANUAL.equals(card.getPercentLimitMaincard())){
							LoanDataM loan = application.getLoan();
							if(!Util.empty(loan)){
								results.add(FormatUtil.displayCurrency(loan.getRequestLoanAmt().toString()));
							}else{
								results.add("-");
							}							
						}else{
							if(Util.empty(card.getPercentLimitMaincard())){
								results.add("-");
							}else{
								results.add(card.getPercentLimitMaincard()+" %");
							}
						}
					}			 	
				 }
			}
		}
		
		if(!Util.empty(results)){
			RESULT = StringUtils.join(results,',');
		}
		HTML.append("<td class='text-left'>"+QUESTION+"</td>")
			.append("<td>"+FormatUtil.display(RESULT)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(FINANCIAL_AMOUNT_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(),"", CUSTOMER_NOT_ANS, HtmlUtil.elementTagId(getQuestionTagId(FINANCIAL_AMOUNT_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(FINANCIAL_AMOUNT_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(),"", "Y", HtmlUtil.elementTagId(getQuestionTagId(FINANCIAL_AMOUNT_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(FINANCIAL_AMOUNT_QUESTION, questionSetCode, personalId), "", "", "", identifyQuestion.getResult(),"", "N", HtmlUtil.elementTagId(getQuestionTagId(FINANCIAL_AMOUNT_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			/*.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", FINANCIAL_AMOUNT_QUESTION+"_"+personalId, CUSTOMER_NOT_ANS,CUSTOMER_NOT_ANS, DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+FINANCIAL_AMOUNT_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", FINANCIAL_AMOUNT_QUESTION+"_"+personalId, identifyQuestion.getResult(),"Y", DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+FINANCIAL_AMOUNT_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", FINANCIAL_AMOUNT_QUESTION+"_"+personalId, identifyQuestion.getResult(),"N", DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+FINANCIAL_AMOUNT_QUESTION+"_"+personalId), "", "", request)+"</td>")*/
			.append(HtmlUtil.hidden("QUESTION_NO", FINANCIAL_AMOUNT_QUESTION));
		return HTML.toString();
	}

}
