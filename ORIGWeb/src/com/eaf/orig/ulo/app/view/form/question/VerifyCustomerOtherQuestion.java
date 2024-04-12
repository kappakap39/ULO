package com.eaf.orig.ulo.app.view.form.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.QuestionObjectDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
//import java.util.Arrays;

public class VerifyCustomerOtherQuestion extends ElementHelper{
	private static transient Logger logger = Logger.getLogger(VerifyCustomerOtherQuestion.class);
	String FLAG_ENABLED = SystemConstant.getConstant("FLAG_ENABLED");
	String[] QUESTION_SET_VERIFY_CUSTOMER_OTHER = SystemConstant.getArrayConstant("QUESTION_SET_VERIFY_CUSTOMER_OTHER");
	public String getElement(HttpServletRequest request, Object objectElement) {
		String personalId = (String)objectElement;
		logger.debug("personalId >> "+personalId);
		return "/orig/ulo/subform/question/VerifyCustomerOtherQuestions.jsp?PERSONAL_ID="+personalId;
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String renderElementFlag(HttpServletRequest request,Object objectElement) {
//		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		String personalId = (String)objectElement;
		logger.debug("personalId>>>"+personalId);
		EntityFormHandler EntityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) EntityForm.getObjectForm();
		PersonalInfoDataM  personalInfo = applicationGroup.getPersonalById(personalId);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
		String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");
		String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
		String ACCT_VALIDATION_PASS = SystemConstant.getConstant("ACCT_VALIDATION_PASS");
		String[] QUESTION_CIS_IMPLEMENT_IDS = SystemConstant.getArrayConstant("VERIFY_CUTOMER_OTHER_QUESTION_CIS_IMPLEMENT_IDS");
	 	String SPECIAL_ADDITIONAL_SERVICE_ATM = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_ATM");
		String []cashTransFerType = ApplicationUtil.getCashTranferTypes();
		String SPECIAL_ADDITIONAL_SERVICE_POSTAL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_POSTAL");
		String SPECIAL_ADDITIONAL_SERVICE_EMAIL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_EMAIL");
		String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		String GEN_PARAM_CC_INFINITE = SystemConstant.getConstant("GEN_PARAM_CC_INFINITE");
		String GEN_PARAM_CC_WISDOM = SystemConstant.getConstant("GEN_PARAM_CC_WISDOM");
		
		
		VerificationResultDataM verificationResult= personalInfo.getVerificationResult();
		ArrayList<IdentifyQuestionSetDataM> identifyQuestionSets = verificationResult.getVerifyCustomerQuestionSets(QUESTION_SET_VERIFY_CUSTOMER_OTHER);
		
		if(!Util.empty(identifyQuestionSets)){
			for(IdentifyQuestionSetDataM identifyQuestionSet : identifyQuestionSets){
				String QuestionSetItem = identifyQuestionSet.getQuestionSetCode();
				ArrayList<HashMap<String,Object>> questions = CacheControl.search("QuestionCacheDataM", "SET_TYPE", QuestionSetItem);
				for(HashMap<String,Object> question : questions){
					String QUESTION_NO = (String)question.get("CODE");
					String ENABLED = (String)question.get("ENABLED");
					boolean isShow = false;
					if(FLAG_ENABLED.equals(ENABLED)){
						isShow = true;
					}
					IdentifyQuestionDataM identifyQuestionsDisplay = verificationResult.getIndentifyQuesitionNo(QUESTION_NO,QuestionSetItem);
					if(!Util.empty(identifyQuestionsDisplay) && isShow){
						return MConstant.FLAG.YES;
			
					}
				}
			}
		}
		
		 //Verify Customer CIS Section
		ArrayList<String> implementIds = new ArrayList<String>(Arrays.asList(QUESTION_CIS_IMPLEMENT_IDS));
		for(String implementId :implementIds){
			ElementInf elementCIS = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_CIS,implementId);
			if(MConstant.FLAG.YES.equals(elementCIS.renderElementFlag(request, applicationGroup))){
				return MConstant.FLAG.YES;
			}
		}
			
		ArrayList<String> products = applicationGroup.filterProductPersonal(personalInfo.getPersonalId(), PERSONAL_RELATION_APPLICATION_LEVEL);		
		if(!Util.empty(products)){					
			for (String product : products) {
				//Verify Customer Payment Section
				ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_PAYMENTS, product);
				if(MConstant.FLAG.YES.equals(element.renderElementFlag(request,personalId))){
					return MConstant.FLAG.YES;
				}
				
				//Verify Customer  Additional Service Email Section
				SpecialAdditionalServiceDataM specialAdditionalServicePostal = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, product, SPECIAL_ADDITIONAL_SERVICE_POSTAL);
				SpecialAdditionalServiceDataM specialAdditionalServiceEmail = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, product, SPECIAL_ADDITIONAL_SERVICE_EMAIL);
				if(SystemConfig.getGeneralParam(GEN_PARAM_CC_INFINITE).contains(applicationGroup.getApplicationTemplate()) ||
						SystemConfig.getGeneralParam(GEN_PARAM_CC_WISDOM).contains(applicationGroup.getApplicationTemplate())){
					if(!Util.empty(personalInfo) && PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
						if(!Util.empty(specialAdditionalServiceEmail) && !Util.empty(personalInfo) && Util.empty(personalInfo.getEmailPrimary())){
							return MConstant.FLAG.YES;
						}
					}
				}else{
					if(!Util.empty(personalInfo) && PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
						if(Util.empty(specialAdditionalServicePostal) && !Util.empty(personalInfo) && Util.empty(personalInfo.getEmailPrimary())){
							return MConstant.FLAG.YES;
						}
					}
				}
				
				//Verify Customer  Additional Service Section
				SpecialAdditionalServiceDataM specialAdditionalServiceProduct = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalInfo.getPersonalId(), product, SPECIAL_ADDITIONAL_SERVICE_ATM);
				if(!Util.empty(specialAdditionalServiceProduct) && !Util.empty(specialAdditionalServiceProduct.getCompleteData())){
					
					if(!specialAdditionalServiceProduct.getCompleteData().equals(ACCT_VALIDATION_PASS)){
						return MConstant.FLAG.YES;
					} 
					
					if(!specialAdditionalServiceProduct.getCompleteDataSaving().equals(ACCT_VALIDATION_PASS)){
						return MConstant.FLAG.YES;
					}
				}
			}
			
		}	
			// Verify Customer Cash Trnsfer			
			ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
			if(!Util.empty(applicationItem)){
				PersonalRelationDataM relationM = applicationGroup.getPersonalRelation(applicationItem.getApplicationRecordId(), personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
				if(!Util.empty(relationM)) {
					CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);
			 	 	if(!Util.empty(cashTransfer) && !Util.empty(cashTransfer.getCompleteData()) ){
				 		if(ACCT_VALIDATION_PASS.equals(cashTransfer.getCompleteData())){
				 			return MConstant.FLAG.YES;
				 		}else if(!ACCT_VALIDATION_PASS.equals(cashTransfer.getCompleteData())){
				 			return MConstant.FLAG.YES;
				 		}
			 	 	}	
			 	 	
			 		CashTransferDataM cashTransferCallForCash = applicationItem.getCashTransfer(CALL_FOR_CASH);
			 		if(!Util.empty(cashTransferCallForCash) && !Util.empty(cashTransferCallForCash.getCompleteData())){
						if(ACCT_VALIDATION_PASS.equals( cashTransferCallForCash.getCompleteData())){
								return MConstant.FLAG.YES;
							}else if(!ACCT_VALIDATION_PASS.equals(cashTransferCallForCash.getCompleteData())){
								return MConstant.FLAG.YES;
							}
			 		}
				}
			}
		return MConstant.FLAG.NO;
	}
	
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		EntityFormHandler EntityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) EntityForm.getObjectForm();
		String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
//		String CASH_DAY_1 = SystemConstant.getConstant("CASH_DAY_1");	
//		String CASH_DAY_5 = SystemConstant.getConstant("CASH_DAY_5");
//		String CASH_1_HOUR = SystemConstant.getConstant("CASH_1_HOUR");
		String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");
		String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
		String ACCT_VALIDATION_PASS = SystemConstant.getConstant("ACCT_VALIDATION_PASS");
	 	
//		String []cashTransFerType = {CASH_DAY_1,CASH_DAY_5,CASH_1_HOUR};
		String []cashTransFerType = ApplicationUtil.getCashTranferTypes();
		
		VerificationResultDataM verificationResult= personalInfo.getVerificationResult();
		ArrayList<IdentifyQuestionSetDataM> identifyQuestionSets = verificationResult.getVerifyCustomerQuestionSets(QUESTION_SET_VERIFY_CUSTOMER_OTHER);
		
		if(!Util.empty(identifyQuestionSets)){
			QuestionObjectDataM questionObject = new QuestionObjectDataM();
			questionObject.setPersonalId(personalInfo.getPersonalId());
			questionObject.setApplicationGroup(applicationGroup);
			for(IdentifyQuestionSetDataM identifyQuestionSet : identifyQuestionSets){
				String QuestionSetItem = identifyQuestionSet.getQuestionSetCode();
				questionObject.setQuestionSetCode(QuestionSetItem);
				ArrayList<HashMap<String,Object>> questions = CacheControl.search("QuestionCacheDataM", "SET_TYPE", QuestionSetItem);
				for(HashMap<String,Object> question : questions){
					String QUESTION_NO = (String)question.get("CODE");
					String ENABLED = (String)question.get("ENABLED");
					boolean isShow = false;
					if(FLAG_ENABLED.equals(ENABLED)){
						isShow = true;
					}
					IdentifyQuestionDataM identifyQuestionsDisplay = verificationResult.getIndentifyQuesitionNo(QUESTION_NO,QuestionSetItem);
					if(!Util.empty(identifyQuestionsDisplay) && isShow){
						ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_QUESTION_CUSTOMER, QUESTION_NO);
						elementInf.setObjectRequest(questionObject);
						elementInf.processElement(request, personalInfo);
					}
				}
			}
		}
		
		ElementInf elementEmailStatement = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_EMAIL, "KEMailStatement");
		if(MConstant.FLAG.YES.equals(elementEmailStatement.renderElementFlag(request,personalInfo.getPersonalId()))){
			elementEmailStatement.processElement(request,personalInfo.getPersonalId());
		}
		
		// Verify Customer Payments
		ArrayList<String> products = applicationGroup.filterProductPersonal(personalInfo.getPersonalId(), 
				PERSONAL_RELATION_APPLICATION_LEVEL);
		if(!Util.empty(products)){
			for (String product : products) {
				ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_PAYMENTS,product);
				if(null != element){
					element.processElement(request,personalInfo);
				}
			}
		}
				
		// Cash Transfer
		ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(applicationItem)) {
			PersonalRelationDataM relationM = applicationGroup.getPersonalRelation(applicationItem.getApplicationRecordId(), personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
			if(!Util.empty(relationM)) {
				CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);
				if(!Util.empty(cashTransfer) && !Util.empty(cashTransfer.getCashTransferId()) ){
			 		if(!Util.empty( cashTransfer.getCompleteData()) && cashTransfer.getCompleteData().equals(ACCT_VALIDATION_PASS) ){
						ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_CASH_TRANSFER,"CASH_DAY_ONE");
						if(null != element){
							element.processElement(request,personalInfo);
						}
			 		}
				}
				
				CashTransferDataM cashTransferCallForCash = applicationItem.getCashTransfer(CALL_FOR_CASH);
				if(!Util.empty(cashTransferCallForCash) && !Util.empty( cashTransferCallForCash.getCompleteData())
						&&!Util.empty(cashTransferCallForCash.getCashTransferId()) && cashTransferCallForCash.getCompleteData().equals(ACCT_VALIDATION_PASS)){
					ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_CASH_TRANSFER,"CALL_FOR_CASH");
					if(null != element){
						element.processElement(request,personalInfo);
					}
		 		}
			}
		}
		return null;
	}
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		FormErrorUtil formError = new FormErrorUtil();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		EntityFormHandler EntityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) EntityForm.getObjectForm();
		String ACCT_VALIDATION_PASS = SystemConstant.getConstant("ACCT_VALIDATION_PASS");
	 	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
		String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
//		String CASH_DAY_1 = SystemConstant.getConstant("CASH_DAY_1");	
//		String CASH_DAY_5 = SystemConstant.getConstant("CASH_DAY_5");
//		String CASH_1_HOUR = SystemConstant.getConstant("CASH_1_HOUR");
		String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");
//		String []cashTransFerType = {CASH_DAY_1,CASH_DAY_5,CASH_1_HOUR};	
		String []cashTransFerType = ApplicationUtil.getCashTranferTypes();
		
		
		VerificationResultDataM verificationResult= personalInfo.getVerificationResult();
		ArrayList<IdentifyQuestionSetDataM> identifyQuestionSets = verificationResult.getVerifyCustomerQuestionSets(QUESTION_SET_VERIFY_CUSTOMER_OTHER);
		
		if(!Util.empty(identifyQuestionSets)){
			for(IdentifyQuestionSetDataM identifyQuestionSet : identifyQuestionSets){
				String QuestionSetItem = identifyQuestionSet.getQuestionSetCode();
				ArrayList<HashMap<String,Object>> questions = CacheControl.search("QuestionCacheDataM", "SET_TYPE", QuestionSetItem);
				for(HashMap<String,Object> question : questions){
					String QUESTION_NO = (String)question.get("CODE");
					String ENABLED = (String)question.get("ENABLED");
					boolean isShow = false;
					if(FLAG_ENABLED.equals(ENABLED)){
						isShow = true;
					}
					IdentifyQuestionDataM identifyQuestionsDisplay = verificationResult.getIndentifyQuesitionNo(QUESTION_NO,QuestionSetItem);
					if(!Util.empty(identifyQuestionsDisplay) && isShow){
						ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_QUESTION_CUSTOMER, QUESTION_NO);
						formError.addAll(elementInf.validateElement(request,personalInfo));
					}
				}
			}
		}
		
		
		//Verify Customer Payments
		ArrayList<String> products = applicationGroup.filterProductPersonal(personalInfo.getPersonalId(), PERSONAL_RELATION_APPLICATION_LEVEL);
		if(!Util.empty(products)){
			for (String product : products) {
				ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_PAYMENTS,product);
				if(null != element){
					formError.addAll(element.validateElement(request,personalInfo));
					
				}
			}
		}
		
		// Verify Customer Cash Transfer
		ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(applicationItem)) {
			PersonalRelationDataM relationM = applicationGroup.getPersonalRelation(applicationItem.getApplicationRecordId(), personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
			if(!Util.empty(relationM)) {
				CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);
				if(!Util.empty(cashTransfer) && !Util.empty(cashTransfer.getCashTransferId()) ){
			 		if(!Util.empty( cashTransfer.getCompleteData()) && cashTransfer.getCompleteData().equals(ACCT_VALIDATION_PASS) ){
						ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_CASH_TRANSFER,"CASH_DAY_ONE");
						if(null != element){
							formError.addAll(element.validateElement(request,personalInfo));
						}
			 		}
				}
				CashTransferDataM cashTransferCallForCash = applicationItem.getCashTransfer(CALL_FOR_CASH);
				if(!Util.empty(cashTransferCallForCash) && !Util.empty( cashTransferCallForCash.getCompleteData())
						&&!Util.empty(cashTransferCallForCash.getCashTransferId()) && cashTransferCallForCash.getCompleteData().equals(ACCT_VALIDATION_PASS)){
					ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_CASH_TRANSFER,"CALL_FOR_CASH");
					if(null != element){
						formError.addAll(element.validateElement(request,personalInfo));
					}
		 		}
			}
		}
		return formError.getFormError();
	}
}
