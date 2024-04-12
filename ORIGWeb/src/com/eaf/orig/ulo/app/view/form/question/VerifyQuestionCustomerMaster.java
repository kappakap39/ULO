package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.QuestionObjectDataM;

public class VerifyQuestionCustomerMaster extends ElementHelper{
	private static transient Logger logger = Logger.getLogger(VerifyQuestionCustomerMaster.class);
	private String CUSTOMER_NOT_ANS = SystemConstant.getConstant("CUSTOMER_NOT_ANS");
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		QuestionObjectDataM questionObject = (QuestionObjectDataM)getObjectRequest();
		String questionSetCode = questionObject.getQuestionSetCode();
		
		ApplicationGroupDataM applicationGroup = questionObject.getApplicationGroup();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(questionObject.getPersonalId());
		String personalId = personalInfo.getPersonalId();
		
		IdentifyQuestionDataM identifyQuestion = (IdentifyQuestionDataM)objectElement;
		String questionNo = identifyQuestion.getQuestionNo();
		
		String tag = getQuestionTagId(questionNo, questionSetCode, personalId);
		logger.debug("tag >> "+tag);
		String RESULT_CHECK = request.getParameter(tag);
		logger.debug("identifyQuestion.getIdentifyQuestionId() >> "+identifyQuestion.getIdentifyQuestionId());
		logger.debug("identifyQuestion.getQuestionNo() >> "+identifyQuestion.getQuestionNo());
		logger.debug("RESULT_CHECK >> "+RESULT_CHECK);
		//#Fix can set value x in Question SET
//		if(!Util.empty(RESULT_CHECK) && !CUSTOMER_NOT_ANS.equals(RESULT_CHECK)){
		if(!Util.empty(RESULT_CHECK)){
			identifyQuestion.setResult(RESULT_CHECK);
		}else{
			identifyQuestion.setResult(null);
		}
		logger.debug("identifyQuestion.getResult() >> "+identifyQuestion.getResult());
		return null;
	}
	
	public String getQuestionTagId(String questionNo, String questionSetCode, String personalId){
		return "RESULT_CHECK_"+questionNo+"_"+questionSetCode+"_"+personalId;
	}
}
