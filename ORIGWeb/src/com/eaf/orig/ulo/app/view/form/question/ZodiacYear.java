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
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.QuestionObjectDataM;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;

public class ZodiacYear extends VerifyQuestionCustomerMaster{
	private static transient Logger logger = Logger.getLogger(ZodiacYear.class);
	String ZODIAC_YEAR_QUESTION = SystemConstant.getConstant("ZODIAC_YEAR_QUESTION");
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
		String QUESTION = CacheControl.getName("QuestionCacheDataM", ZODIAC_YEAR_QUESTION);
		
		String RESULT = "";
		if(!Util.empty(personalInfo.getBirthDate())){
	 		String zodiac = DataFormatUtility.dateEnToStringDateEn(personalInfo.getBirthDate(),DataFormatUtility.DateFormatType.FORMAT_CONSTELLATION);
	 		String dayOfWeek = DataFormatUtility.dateEnToStringDateEn(personalInfo.getBirthDate(),DataFormatUtility.DateFormatType.FORMAT_DAY_OF_WEEK);
	 		RESULT = zodiac+","+dayOfWeek;
		}
		
		HTML.append("<td class='text-left'>"+QUESTION+"</td>")
			.append("<td>"+FormatUtil.display(RESULT)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(ZODIAC_YEAR_QUESTION, questionSetCode, personalId), "","","", identifyQuestion.getResult(),"", CUSTOMER_NOT_ANS, HtmlUtil.elementTagId(getQuestionTagId(ZODIAC_YEAR_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(ZODIAC_YEAR_QUESTION, questionSetCode, personalId), "","","", identifyQuestion.getResult(),"", "Y", HtmlUtil.elementTagId(getQuestionTagId(ZODIAC_YEAR_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			.append("<td>"+HtmlUtil.radioInline(getQuestionTagId(ZODIAC_YEAR_QUESTION, questionSetCode, personalId), "","","", identifyQuestion.getResult(),"", "N", HtmlUtil.elementTagId(getQuestionTagId(ZODIAC_YEAR_QUESTION, questionSetCode, personalId)), "", "", formUtil)+"</td>")
			/*.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", ZODIAC_YEAR_QUESTION+"_"+personalId, CUSTOMER_NOT_ANS,CUSTOMER_NOT_ANS, DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+ZODIAC_YEAR_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", ZODIAC_YEAR_QUESTION+"_"+personalId, identifyQuestion.getResult(),"Y", DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+ZODIAC_YEAR_QUESTION+"_"+personalId), "", "", request)+"</td>")
			.append("<td>"+HtmlUtil.radioInline("RESULT_CHECK", ZODIAC_YEAR_QUESTION+"_"+personalId, identifyQuestion.getResult(),"N", DISPLAY_MODE, HtmlUtil.tagId("RESULT_CHECK_"+ZODIAC_YEAR_QUESTION+"_"+personalId), "", "", request)+"</td>")*/
			.append(HtmlUtil.hidden("QUESTION_NO", ZODIAC_YEAR_QUESTION));
		return HTML.toString();
	}

}
