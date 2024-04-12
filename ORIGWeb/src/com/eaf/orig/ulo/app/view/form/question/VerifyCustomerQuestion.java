package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;

public class VerifyCustomerQuestion extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(VerifyCustomerQuestion.class);
	private String CUSTOMER_NOT_ANS = SystemConstant.getConstant("CUSTOMER_NOT_ANS");
	public String getElement(HttpServletRequest request, Object objectElement) {
		String personalId = (String)objectElement;
		logger.debug("personalId >> "+personalId);
		return "/orig/ulo/subform/question/VerifyCustomerQuestion.jsp?PERSONAL_ID="+personalId;
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

//	@Override
//	public String processElement(HttpServletRequest request,Object objectElement) {
//		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
//		String QUESTION_NO = request.getParameter("QUESTION_NO");
//		
//		PersonalInfoDataM personalinfo=	(PersonalInfoDataM)objectElement;
//		VerificationResultDataM verificationResult =personalinfo.getVerificationResult();
//		logger.debug("personalinfo >>>>> "+personalinfo.getPersonalId());
//		if(Util.empty(verificationResult)){
//			verificationResult = new VerificationResultDataM();
//		}	
//		ArrayList<IdentifyQuestionDataM>	 IdentifyQuestions =	 verificationResult.getIndentifyQuestions();
//		if(!Util.empty(IdentifyQuestions)){
//			for(IdentifyQuestionDataM IdentifyQuestion:IdentifyQuestions){
//				String tag = IdentifyQuestion.getQuestionNo()+"_"+personalinfo.getPersonalId();
//				String RESULT_CHECK = request.getParameter("RESULT_CHECK_"+tag);
//				logger.debug("personalinfo ID "+personalinfo.getPersonalId());
//				logger.debug("IdentifyQuestion.getQuestionNo() >> "+IdentifyQuestion.getQuestionNo());
//				logger.debug("RESULT_CHECK >> "+RESULT_CHECK);
//
//				if(!Util.empty(RESULT_CHECK) && !CUSTOMER_NOT_ANS.equals(RESULT_CHECK)){
//					IdentifyQuestion.setResult(RESULT_CHECK);
//				}
//			}
//			
//		}
//		
//		return null;
//	}
}
