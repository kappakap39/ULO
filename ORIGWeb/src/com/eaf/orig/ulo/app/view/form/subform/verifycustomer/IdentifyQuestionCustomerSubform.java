package com.eaf.orig.ulo.app.view.form.subform.verifycustomer;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PhoneVerificationDataM;
import com.eaf.orig.ulo.model.app.QuestionObjectDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class IdentifyQuestionCustomerSubform extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(IdentifyQuestionCustomerSubform.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String[] QUESTION_SET_VERIFY_CUSTOMER = SystemConstant.getArrayConstant("QUESTION_SET_VERIFY_CUSTOMER");
	String QUESTION_SET_TYPE_VERIRY_CUSTOMER = SystemConstant.getConstant("QUESTION_SET_TYPE_VERIRY_CUSTOMER");
	String QUESTION_SET_VERIFY_CUSTOMER_OTHER = SystemConstant.getConstant("QUESTION_SET_VERIFY_CUSTOMER_OTHER");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("IdentifyQuestionCustomerSubform ... setProperties ");
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		PersonalInfoDataM personalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		ArrayList<PersonalInfoDataM> personalSupplementarys = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		
		QuestionObjectDataM questionObject = new QuestionObjectDataM();
			questionObject.setApplicationGroup(applicationGroup);
		if(null != personalApplicant){
			questionObject.setPersonalId(personalApplicant.getPersonalId());
			VerificationResultDataM verificationResult = personalApplicant.getVerificationResult();
			
			ArrayList<IdentifyQuestionSetDataM> identifyQuestionSets = verificationResult.getVerifyCustomerQuestionSets(QUESTION_SET_VERIFY_CUSTOMER);
			ArrayList<IdentifyQuestionDataM> identifyQuestions = verificationResult.getIndentifyQuestions();
			if(!Util.empty(identifyQuestionSets) && !Util.empty(identifyQuestions)){
				for(IdentifyQuestionSetDataM identifyQuestionSet : identifyQuestionSets){
					String questionSetCode = identifyQuestionSet.getQuestionSetCode();
					questionObject.setQuestionSetCode(questionSetCode);
					for(IdentifyQuestionDataM identifyQuestion : identifyQuestions){
						if(identifyQuestionSet.getQuestionSetCode().equals(identifyQuestion.getQuestionSetCode())){
							ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_QUESTION_CUSTOMER, identifyQuestion.getQuestionNo());
							elementInf.setObjectRequest(questionObject);
							elementInf.processElement(request, identifyQuestion);
						}
					}
				}
			}
		}
		
		if(null != personalSupplementarys){
			for(PersonalInfoDataM personalSupplementary : personalSupplementarys){
				questionObject.setPersonalId(personalSupplementary.getPersonalId());
				VerificationResultDataM verificationResult = personalSupplementary.getVerificationResult();
				ArrayList<IdentifyQuestionSetDataM> identifyQuestionSets = verificationResult.getVerifyCustomerQuestionSets(QUESTION_SET_VERIFY_CUSTOMER);
				if(!Util.empty(identifyQuestionSets)){
					for(IdentifyQuestionSetDataM identifyQuestionSet : identifyQuestionSets){
						String QuestionSetItem = identifyQuestionSet.getQuestionSetCode();
						questionObject.setQuestionSetCode(QuestionSetItem);
						ArrayList<HashMap<String,Object>> questions = CacheControl.search("QuestionCacheDataM", "SET_TYPE", QuestionSetItem);
						for(HashMap<String,Object> question : questions){
							String QUESTION_NO = (String)question.get("CODE");
							IdentifyQuestionDataM identifyQuestionsDisplay = verificationResult.getIndentifyQuesitionNo(QUESTION_NO,identifyQuestionSet.getQuestionSetCode());
							if(!Util.empty(identifyQuestionsDisplay)){
								ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_QUESTION_CUSTOMER, QUESTION_NO);
								elementInf.setObjectRequest(questionObject);
								elementInf.processElement(request, identifyQuestionsDisplay);
							}
						}
					}
				}
			}
		}
		
		if(null != personalApplicant){
			ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.QUESTION_FIELDS, "VERIFY_CUSTOMER_OTHER_QUESTION");
			if(MConstant.FLAG.YES.equals(elementInf.renderElementFlag(request, personalApplicant.getPersonalId()))){
				elementInf.processElement(request, personalApplicant);
			}		
		}
		if(!Util.empty(personalSupplementarys)){
			for(PersonalInfoDataM personalSupplementary : personalSupplementarys){
				ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.QUESTION_FIELDS, "VERIFY_CUSTOMER_OTHER_QUESTION");			
				if(MConstant.FLAG.YES.equals(elementInf.renderElementFlag(request, personalSupplementary.getPersonalId()))){
					elementInf.processElement(request, personalSupplementary);
				}
			}
		}
	}
	
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		logger.debug("IdentifyQuestionCustomerSubform ... setProperties ");
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		PersonalInfoDataM personalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		ArrayList<PersonalInfoDataM> personalSupplementarys = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		
		if(null != personalApplicant){
			ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.QUESTION_FIELDS, "VERIFY_CUSTOMER_OTHER_QUESTION");
			if(MConstant.FLAG.YES.equals(elementInf.renderElementFlag(request, personalApplicant.getPersonalId()))){
				formError.addAll(elementInf.validateElement(request,personalApplicant));
			}
			
		}
		if(!Util.empty(personalSupplementarys)){
			for(PersonalInfoDataM personalSupplementary : personalSupplementarys){
				ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.QUESTION_FIELDS, "VERIFY_CUSTOMER_OTHER_QUESTION");
				if(MConstant.FLAG.YES.equals(elementInf.renderElementFlag(request, personalSupplementary.getPersonalId()))){
					formError.addAll(elementInf.validateElement(request,personalSupplementary));
				}		
			}
		}

		return formError.getFormError();
	}
	private HashMap<String,Object> validateQuestionSetVerCustomerOther(HttpServletRequest request,VerificationResultDataM verificationResult,PersonalInfoDataM personalInfo){
		boolean isContainOtherQuestion =verificationResult.isContainConditionQuestionSetCodes(QUESTION_SET_TYPE_VERIRY_CUSTOMER,QUESTION_SET_VERIFY_CUSTOMER_OTHER);
		logger.debug("isContainOtherQuestion>>>"+isContainOtherQuestion);
		logger.debug("personalInfo.getPersonalType()>>>"+personalInfo.getPersonalType());
		if(isContainOtherQuestion){
			 ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.QUESTION_FIELDS, "VERIFY_CUSTOMER_OTHER_QUESTION");	
			return  elementInf.validateElement(request, personalInfo);
		}	
		return null;
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm) 
	{
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		String source = applicationGroup.getSource();
		if(ApplicationUtil.eApp(source) || MConstant.FLAG_Y.equals(applicationGroup.foundLowIncome()) || ApplicationUtil.cjd((source)))
		{
			return MConstant.FLAG_N;
		}		
		return MConstant.FLAG_Y;
	}

}
