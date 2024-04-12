package com.eaf.orig.ulo.app.view.form.subform.verifycustomer;

//import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
//import com.eaf.core.ulo.common.properties.CacheControl;
//import com.eaf.orig.ulo.constant.MConstant;
//import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
//import com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM;
//import com.eaf.orig.ulo.model.app.PhoneVerificationDataM;

@SuppressWarnings("serial")
public class CustomerVerificationResultSubform extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(CustomerVerificationResultSubform.class);
	String VALIDATION_STATUS_COMPLETED_NOT_PASS = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED_NOT_PASS");
	String VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER = SystemConstant.getConstant("VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER");
	String VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT = SystemConstant.getConstant("VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT");
	String FIELD_ID_DATA_VALIDATION_STATUS = SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS");
	String QUESTION_SET_CACHE_DATAM = SystemConstant.getConstant("QUESTION_SET_CACHE_DATAM");
	String QUESTION_CACHE = SystemConstant.getConstant("QUESTION_CACHE");
	String VALIDATION_STATUS_COMPLETED = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED");
	String VER_CUS_COMPLETE_VERIFY_COMPLETED=SystemConstant.getConstant("VER_CUS_COMPLETE_VERIFY_COMPLETED");
	String VER_CUS_COMPLETE_CANCEL= SystemConstant.getConstant("VER_CUS_COMPLETE_CANCEL");
	String QUESTION_SET_TYPE_VERIRY_CUSTOMER = SystemConstant.getConstant("QUESTION_SET_TYPE_VERIRY_CUSTOMER");
	String QUESTION_SET_VERIFY_CUSTOMER_OTHER = SystemConstant.getConstant("QUESTION_SET_VERIFY_CUSTOMER_OTHER");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm){
		String APPROVAL_RESULT_DECISION = request.getParameter("APPROVAL_RESULT_DECISION");
		logger.debug("APPROVAL_RESULT_DECISION >>> " + APPROVAL_RESULT_DECISION);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) appForm;
		
		//Add supplementary result for verify customer
//		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
//		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
//		if (null == verificationResult) {
//			verificationResult = new VerificationResultDataM();
//			personalInfo.setVerificationResult(verificationResult);
//		}	
//		logger.debug("verificationResult >>> "+verificationResult.getVerCusComplete());
//		verificationResult.setVerCusComplete(APPROVAL_RESULT_DECISION);
//		ArrayList<PhoneVerificationDataM> phoneVerifications =  verificationResult.getPhoneVerifications();
//		if(!Util.empty(phoneVerifications)){
//			if (VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER.equals(APPROVAL_RESULT_DECISION)) {
//				logger.debug("Case Not Contact ... ");
//				String verCustResult = CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS,VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT,"DISPLAY_NAME");
//				verificationResult.setVerCusResult(verCustResult);
//				verificationResult.setVerCusResultCode(VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT);
//			}else if(VER_CUS_COMPLETE_CANCEL.equals(APPROVAL_RESULT_DECISION)){
//				logger.debug("Case Cancel app ..");
//				String verCustResult = CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS,VALIDATION_STATUS_COMPLETED_NOT_PASS,"DISPLAY_NAME");
//				verificationResult.setVerCusResult(verCustResult);
//				verificationResult.setVerCusResultCode(VALIDATION_STATUS_COMPLETED_NOT_PASS);
//			}else if (VER_CUS_COMPLETE_VERIFY_COMPLETED.equals(APPROVAL_RESULT_DECISION)) {
//				logger.debug("VER_CUS_COMPLETE_VERIFY_COMPLETED >> "+APPROVAL_RESULT_DECISION);
//				ArrayList<CustomerVerifyQuestionResult> customerVerifyResults = getCustomerVerifyQuestionResult(verificationResult);	
//				if(validateCustomerVerifyResult(customerVerifyResults)){										
//					verificationResult.setVerCusResultCode(VALIDATION_STATUS_COMPLETED);
//					String verCustResult = CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS, VALIDATION_STATUS_COMPLETED, "DISPLAY_NAME");
//					verificationResult.setVerCusResult(verCustResult);
//				}else{	
//					verificationResult.setVerCusResultCode(VALIDATION_STATUS_COMPLETED_NOT_PASS);
//					String verCustResult = CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS, VALIDATION_STATUS_COMPLETED_NOT_PASS, "DISPLAY_NAME");
//					verificationResult.setVerCusResult(verCustResult);					
//				}					
//			}
//		}
		VerifyUtil.setVerificationResultByDecision(applicationGroup, APPROVAL_RESULT_DECISION);
	}
	
//	private ArrayList<CustomerVerifyQuestionResult> getCustomerVerifyQuestionResult(VerificationResultDataM verificationResult){
//		ArrayList<CustomerVerifyQuestionResult> customerVerifyResults = new ArrayList<CustomerVerifyQuestionResult>();
//		ArrayList<IdentifyQuestionSetDataM> questionSets = verificationResult.filterIndentifyQuesitionSets(QUESTION_SET_TYPE_VERIRY_CUSTOMER);
//		if(!Util.empty(questionSets)){
//			for(IdentifyQuestionSetDataM questionSet:questionSets){
//				String questionSetCode = questionSet.getQuestionSetCode();
//				int minAnswer = Integer.parseInt(CacheControl.getName(QUESTION_SET_CACHE_DATAM,"SET_TYPE",questionSetCode,"MIN_ANSWER"));		
//				ArrayList<HashMap<String,Object>> questions = CacheControl.search(QUESTION_CACHE,"SET_TYPE",questionSetCode);
//				int answer = 0;
//				if(!Util.empty(questions)){
//					for(HashMap<String, Object> row : questions){
//						String questionNo = (String)row.get("CODE");
//						IdentifyQuestionDataM identifyQuestion = verificationResult.getIndentifyQuesitionNo(questionNo);
//						if(null != identifyQuestion && !Util.empty(identifyQuestion.getResult()) && MConstant.FLAG_Y.equals(identifyQuestion.getResult())){
//							answer++;
//						}
//					}
//				}
//				CustomerVerifyQuestionResult customerVerifyQuestionResult = new CustomerVerifyQuestionResult();
//				customerVerifyQuestionResult.setCount(answer);
//				customerVerifyQuestionResult.setQuestionSet(questionSetCode);
//				if (answer >= minAnswer){
//					customerVerifyQuestionResult.setResult(SystemConstant.getConstant("DECISION_ACTION_PASS"));
//				}else{
//					customerVerifyQuestionResult.setResult(SystemConstant.getConstant("DECISION_ACTION_FAIL"));
//				}
//				customerVerifyResults.add(customerVerifyQuestionResult);
//			}
//		}
//		return customerVerifyResults;	
//	}
	
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) appForm;
		String APPROVAL_RESULT_DECISION = request.getParameter("APPROVAL_RESULT_DECISION");
		logger.debug("subformId >> "+subFormID);
		formError.mandatory(subFormID,"APPROVAL_RESULT_DECISION",APPROVAL_RESULT_DECISION,request);
//		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
		for(PersonalInfoDataM personalInfo : personalInfos){
			logger.debug("personalInfo >> "+personalInfo.getPersonalId());
			VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
			if(!Util.empty(verificationResult)){		
				if (VER_CUS_COMPLETE_VERIFY_COMPLETED.equals(APPROVAL_RESULT_DECISION)) {
					if(!isAnswerQuestionAll(verificationResult)){
						formError.error(MessageErrorUtil.getText(request,"ERROR_REQUIRED_IDENTIFY_QUESTION_ALL"));
					}else{
						if(!isAnswerQuestion(verificationResult) 
								&& !verificationResult.isContainOnlyConditionQuestionSetCodes(QUESTION_SET_TYPE_VERIRY_CUSTOMER
										,QUESTION_SET_VERIFY_CUSTOMER_OTHER)){						
							formError.error(MessageErrorUtil.getText(request,"ERROR_REQUIRED_QUESTION_RESULT"));
						}
					}
				}
			}
		}
		
		return formError.getFormError();
	}
		
//	private int countAnswerQuestion(VerificationResultDataM verificationResult){
//		int countQuestion = 0;		
//		ArrayList<IdentifyQuestionDataM> identifyQuestions = verificationResult.filterIndentifyQuesitions(QUESTION_SET_TYPE_VERIRY_CUSTOMER);
//		if(!Util.empty(identifyQuestions)){
//			for (IdentifyQuestionDataM identifyQuestion : identifyQuestions) {
//				logger.debug("QuestionSetType >> "+identifyQuestion.getQuestionSetType());
//				logger.debug("QuestionNo >> "+identifyQuestion.getQuestionNo());
//				logger.debug("Result >> "+identifyQuestion.getResult());
//				if (!Util.empty(identifyQuestion.getResult())) {
//					countQuestion++;
//				}
//			}
//		}else{
//			countQuestion = -1;
//		}
//		return countQuestion;
//	}
	
	private boolean isAnswerQuestion(VerificationResultDataM verificationResult){
		boolean isAnswer = true;
		ArrayList<IdentifyQuestionSetDataM> identifyQuestionSets = verificationResult.filterIndentifyQuesitionSetsBySetType(QUESTION_SET_TYPE_VERIRY_CUSTOMER);
		ArrayList<IdentifyQuestionDataM> identifyQuestions = verificationResult.filterIndentifyQuesitions(QUESTION_SET_TYPE_VERIRY_CUSTOMER);
		if(!Util.empty(identifyQuestionSets) && !Util.empty(identifyQuestions)){
			for(IdentifyQuestionSetDataM identifyQuestionSet : identifyQuestionSets){
				if(!QUESTION_SET_VERIFY_CUSTOMER_OTHER.equals(identifyQuestionSet.getQuestionSetCode())){
					logger.debug("identifyQuestionSet >> "+identifyQuestionSet.getQuestionSetCode());
					int countAns = 0;
					for (IdentifyQuestionDataM identifyQuestion : identifyQuestions) {
						logger.debug("identifyQuestion >> "+identifyQuestion.getQuestionNo());
						logger.debug("identifyQuestion >> "+identifyQuestion.getQuestionSetCode());
						logger.debug("identifyQuestion >> "+identifyQuestion.getResult());
						if(identifyQuestionSet.getQuestionSetCode().equals(identifyQuestion.getQuestionSetCode())){
							if (!Util.empty(identifyQuestion.getResult())) {
								countAns++;
							}
						}
					}
					logger.debug("countAns >> "+countAns);
					if(countAns == 0){
						isAnswer = false;
						break;
					}
				}
			}
		}
		logger.debug("isAnswer >> "+isAnswer);
		return isAnswer;
	}
	private boolean isAnswerQuestionAll(VerificationResultDataM verificationResult){
		boolean isAnswerAll = true;
		ArrayList<IdentifyQuestionSetDataM> identifyQuestionSets = verificationResult.filterIndentifyQuesitionSetsBySetType(QUESTION_SET_TYPE_VERIRY_CUSTOMER);
		ArrayList<IdentifyQuestionDataM> identifyQuestions = verificationResult.filterIndentifyQuesitions(QUESTION_SET_TYPE_VERIRY_CUSTOMER);
		if(!Util.empty(identifyQuestionSets) && !Util.empty(identifyQuestions)){
			for(IdentifyQuestionSetDataM identifyQuestionSet : identifyQuestionSets){
				if(!QUESTION_SET_VERIFY_CUSTOMER_OTHER.equals(identifyQuestionSet.getQuestionSetCode())){
					logger.debug("identifyQuestionSet >> "+identifyQuestionSet.getQuestionSetCode());
					int countAns = 0;
					for (IdentifyQuestionDataM identifyQuestion : identifyQuestions) {
						logger.debug("validateIdentifyQuestion identifyQuestion >> "+identifyQuestion.getQuestionNo());
						logger.debug("validateIdentifyQuestion identifyQuestion >> "+identifyQuestion.getQuestionSetCode());
						logger.debug("validateIdentifyQuestion identifyQuestion >> "+identifyQuestion.getResult());
						if(identifyQuestionSet.getQuestionSetCode().equals(identifyQuestion.getQuestionSetCode())){
							if (!Util.empty(identifyQuestion.getResult())) {
								countAns++;
							}else{
								isAnswerAll = false;
							}
						}
					}
					logger.debug("countAns >> "+countAns);
					if(countAns == 0){
						isAnswerAll = false;
						break;
					}
				}
			}
		}
		logger.debug("isAnswerAll >> "+isAnswerAll);
		return isAnswerAll;
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}	
//	private boolean validateCustomerVerifyResult(ArrayList<CustomerVerifyQuestionResult> customerVerifyResults){
//		 if(!Util.empty(customerVerifyResults)){
//			for(CustomerVerifyQuestionResult customerVerifyQuestionResult:customerVerifyResults){
//				if(SystemConstant.getConstant("DECISION_ACTION_FAIL").equals(customerVerifyQuestionResult.getResult())){
//					return false;
//				}
//			} 
//		}
//		return true;
//	}
//	@SuppressWarnings("serial")
//	public class CustomerVerifyQuestionResult implements Serializable,Cloneable{
//		public CustomerVerifyQuestionResult(){
//			super();
//		}
//		private String questionSet;
//		private String result;
//		private int count;
//		public String getQuestionSet() {
//			return questionSet;
//		}
//		public void setQuestionSet(String questionSet) {
//			this.questionSet = questionSet;
//		}
//		public String getResult() {
//			return result;
//		}
//		public void setResult(String result) {
//			this.result = result;
//		}
//		public int getCount() {
//			return count;
//		}
//		public void setCount(int count) {
//			this.count = count;
//		}		
//	}
}
