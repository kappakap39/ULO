package com.eaf.orig.ulo.app.view.form.subform.product.manual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.model.ApplicationResultRequest;
import com.eaf.orig.ulo.control.util.model.ApplicationResultResponse;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;
//import com.eaf.orig.ulo.app.view.form.manual.OrigApplicationForm;

public class AddCardInfo implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(AddCardInfo.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");	
	String DEFAULT_REC_DECISION = SystemConstant.getConstant("DEFAULT_REC_DECISION");
	String ROLE_DE1_1 = SystemConstant.getConstant("ROLE_DE1_1");
	String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	
	ArrayList<String> CARD_TYPE_HAS_MEMBER_SHIP = SystemConstant.getArrayListConstant("CARD_TYPE_HAS_MEMBER_SHIP");
	private String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	private String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	
	private String BENZ_CARD_TYPE = SystemConstant.getConstant("BENZ_CARD_TYPE");
	private String CGA_CARD_TYPE = SystemConstant.getConstant("CGA_CARD_TYPE");
	
	public class functionType{
		public static final String CREATE_PRODUCT_INFO = "CREATE_PRODUCT_INFO";
		public static final String CREATE_PRODUCT_INFO_IA = "CREATE_PRODUCT_INFO_IA";
		public static final String CREATE_CC_SUPCARD_INFO = "CREATE_CC_SUPCARD_INFO";
	}
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_PRODUCT);
		try{
			String functionId = request.getParameter("functionId");
			logger.debug("functionId >> " + functionId);
			if (functionType.CREATE_PRODUCT_INFO.equalsIgnoreCase(functionId)
					|| functionType.CREATE_PRODUCT_INFO_IA.equalsIgnoreCase(functionId)) {
				createProductInfo(functionId,request);
			} else if (functionType.CREATE_CC_SUPCARD_INFO.equalsIgnoreCase(functionId)) {
				logger.debug("Create CC SupCard Info..");
				createCCSupCardInfo(request);
			}
			ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
			ApplicationUtil.clearNotMatchApplicationRelation(applicationGroup);
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
	private void createCCSupCardInfo(HttpServletRequest request) throws Exception {
		logger.debug("Create Supplementary CardInfo >> ");
		String uniqueId = request.getParameter("uniqueId");
		logger.debug("uniqueId >> " + uniqueId);
		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		
		EntityFormHandler entityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
		PersonalApplicationInfoDataM personalAppInfo = (PersonalApplicationInfoDataM) entityForm.getObjectForm();

		ArrayList<ApplicationDataM> applications = personalAppInfo.getApplications();

		String cardCode = (!Util.empty(request.getParameter("CARD_TYPE")) ? request.getParameter("CARD_TYPE").split("\\_")[1] : "");
		String CARD_LEVEL = request.getParameter("CARD_LEVEL");

		HashMap<String, Object> cardInfo = CardInfoUtil.getCardInfo(cardCode, CARD_LEVEL);
		String businessClassId = SQLQueryEngine.display(cardInfo, "BUSINESS_CLASS_ID");
		String cardLevel = SQLQueryEngine.display(cardInfo, "CARD_LEVEL");
		String cardTypeId = SQLQueryEngine.display(cardInfo, "CARD_TYPE_ID");
		logger.debug("cardTypeId >> " + cardTypeId);
		logger.debug("cardCode >> " + cardCode);
		String applicationRecordId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK);
		String loanId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PK);
		String cardId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARD_PK);
		String projectCode = request.getParameter("PROJECT_CODE");

		logger.debug("businessClassId >> " + businessClassId);
		logger.debug("cardLevel >> " + cardLevel);
		logger.debug("applicationRecordId >> " + applicationRecordId);

		PersonalInfoDataM personalInfo = personalAppInfo.getPersonalInfo();
		logger.debug("personalInfo >> " + personalInfo);
		ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
		if (null == personalRelations) {
			personalRelations = new ArrayList<PersonalRelationDataM>();
			personalInfo.setPersonalRelations(personalRelations);
		}
		personalInfo.addPersonalRelation(applicationRecordId, PERSONAL_TYPE_SUPPLEMENTARY, APPLICATION_LEVEL);
		ApplicationDataM application = new ApplicationDataM(applicationRecordId);
		if (null != projectCode) {
			application.setProjectCode(projectCode);
		}
		application.setBusinessClassId(businessClassId);
		LoanDataM loan = new LoanDataM(applicationRecordId, loanId);
		CardDataM card = new CardDataM(loanId, cardId);
		card.setRequestCardType(cardTypeId);
		card.setCardType(cardTypeId);
		card.setCardLevel(cardLevel);
		card.setApplicationType(SUPPLEMENTARY);
		card.setCompleteFlag(COMPLETE_FLAG_N);

		loan.setCard(card);
		logger.debug("MAINCARD_RECORD_ID >>>> " + uniqueId);
		logger.debug("card.Card Type ID : " + card.getCardType());
		logger.debug("card.Card Level : " + card.getCardLevel());
		application.setMaincardRecordId(uniqueId);
		application.addLoan(loan);
		application.setLifeCycle(Math.max(1, applicationGroup.getMaxLifeCycle()));
			PersonalInfoDataM applicantPersonal = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			ArrayList<String> applicantApplicationIds = applicationGroup.getPersonalApplication(applicantPersonal.getPersonalId(),APPLICATION_LEVEL);
			String applicationNo = GenerateUnique.generateApplicationNo(applicationGroup.getApplicationGroupNo(), personalAppInfo.itemLifeCycle(applicationGroup.getMaxLifeCycle())+applicantApplicationIds.size()+1,applicationGroup.getMaxLifeCycle());
		application.setApplicationType(APPLICATION_TYPE_ADD_SUP);
		application.setApplicationNo(applicationNo);
		
		ApplicationDataM applicationReferM = applicationGroup.getApplicationById(uniqueId);
		if(null != applicationReferM){
			application.setProjectCode(applicationReferM.getProjectCode());
//			application.setApplicationType(applicationReferM.getApplicationType());
			application.setRecommendDecision(applicationReferM.getRecommendDecision());
		}else{
//			application.setApplicationType(applicationGroup.getApplicationType());
		}
		if(Util.empty(application.getRecommendDecision())){
			application.setRecommendDecision(DEFAULT_REC_DECISION);
		}
		application.setApplicationDate(applicationGroup.getApplicationDate());
		ApplicationUtil.setPolicyRuleToApplication(applicationGroup, application, FormControl.getFormRoleId(request),personalInfo);
		
		
		
		if(applicationGroup.chkCardDub(application)){
			applications.add(application);
		}
	}

	private void createProductInfo(String functionId,HttpServletRequest request) throws Exception {
		String PRODUCT_TYPE = request.getParameter("PRODUCTS_CARD_TYPE");
		String CARD_TYPE = request.getParameter("CARD_TYPE");
		String CARD_LEVEL = request.getParameter("CARD_LEVEL");
		String APPLICATION_CARD_TYPE = request.getParameter("APPLICATION_CARD_TYPE");
		String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
		String PRODUCT_CODE = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, "CHOICE_NO", PRODUCT_TYPE, "SYSTEM_ID1");
		logger.debug("PRODUCT_TYPE >> " + PRODUCT_TYPE);
		logger.debug("CARD_TYPE >> " + CARD_TYPE);
		logger.debug("CARD_LEVEL >> " + CARD_LEVEL);
		logger.debug("APPLICATION_CARD_TYPE >> " + APPLICATION_CARD_TYPE);
		logger.debug("PRODUCT_CODE >> " + PRODUCT_CODE);
		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		if (PRODUCT_K_PERSONAL_LOAN.equals(PRODUCT_CODE)) {
			createKPLApplicationInfo(functionId,PRODUCT_TYPE,ORIGForm,request);
		} else {
			if (PRODUCT_CRADIT_CARD.equals(PRODUCT_CODE)) {
				HashMap<String, Object> cardInfo = CardInfoUtil.getCardInfo(CARD_TYPE, CARD_LEVEL);
				logger.debug("cardInfo >> " + cardInfo);
				if (null != cardInfo) {
					createCCApplicationInfo(functionId,CARD_TYPE,CARD_LEVEL,APPLICATION_CARD_TYPE,ORIGForm,cardInfo,request);
				}
			} else if (PRODUCT_K_EXPRESS_CASH.equals(PRODUCT_CODE)) {
				HashMap<String, Object> cardInfo = CardInfoUtil.getCardInfoCardType(CARD_TYPE);
				logger.debug("cardInfo >> " + cardInfo);
				if (null != cardInfo) {
					createKECApplicationInfo(functionId,CARD_TYPE, APPLICATION_CARD_TYPE, ORIGForm, cardInfo, request);
				}
			}
		}
	}

	private void createKPLApplicationInfo(String functionId,String PRODUCT_TYPE, ORIGFormHandler ORIGForm, HttpServletRequest request) throws Exception {
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		if(Util.empty(applications)){
			applications = new ArrayList<ApplicationDataM>();
			applicationGroup.setApplications(applications);
		}
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		String applicationRecordId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK);
		String loanId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PK);
		String projectCode = request.getParameter("PROJECT_CODE");
		
		logger.debug("applicationGroupId >> "+applicationGroupId);
		logger.debug("applicationRecordId >> "+applicationRecordId);
		logger.debug("loanId >> "+loanId);
		logger.debug("projectCode >> "+projectCode);
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);		
//		
//		if (null == personalInfo) {
//			personalInfo = new PersonalInfoDataM();
//			personalInfo.setPersonalType(PERSONAL_TYPE_APPLICANT);
//			int personalSeq = OrigApplicationForm.getPersonalSeq(PERSONAL_TYPE_APPLICANT,applicationGroup);
//			String personalId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_INFO_PK);
//			personalInfo.setPersonalId(personalId);
//			personalInfo.setSeq(personalSeq);
//			applicationGroup.getPersonalInfos().add(personalInfo);
//		}
		
		ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
		if (null == personalRelations) {
			personalRelations = new ArrayList<PersonalRelationDataM>();
			personalInfo.setPersonalRelations(personalRelations);
		}
		personalInfo.addPersonalRelation(applicationRecordId, PERSONAL_TYPE_APPLICANT, APPLICATION_LEVEL);
		
		String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
		String businessClassId = CacheControl.getName(CACHE_BUSINESS_CLASS, "PRODUCT_ID", PRODUCT_TYPE, "BUS_CLASS_ID");
		logger.debug("businessClassId >> " + businessClassId);		
		ApplicationDataM application = new ApplicationDataM(applicationRecordId);
		application.setBusinessClassId(businessClassId);
		
		LoanDataM loan = new LoanDataM(applicationRecordId, loanId);		
		if(!Util.empty(projectCode)){
			application.setProjectCode(projectCode);
		}				
		application.addLoan(loan);
		application.setMaincardRecordId(applicationRecordId);
		application.setLifeCycle(Math.max(1, applicationGroup.getMaxLifeCycle()));		
		
		application.setApplicationNo(GenerateUnique.generateApplicationNo(applicationGroup.getApplicationGroupNo()
				, applicationGroup.itemLifeCycle()+1,applicationGroup.getMaxLifeCycle()));
		
		String productId = application.getProduct();	
		int lifeCycle = application.getLifeCycle();
		String applyType = applicationGroup.getApplicationType();

		logger.debug("productId >> "+productId);	
		logger.debug("lifeCycle >> "+lifeCycle);
		logger.debug("applyType >> "+applyType);
		
		ApplicationResultRequest applicationResultRequest = new ApplicationResultRequest();
			applicationResultRequest.setApplicationGroupId(applicationGroupId);
			applicationResultRequest.setProduct(productId);
			applicationResultRequest.setBusinessClassId(businessClassId);
			applicationResultRequest.setLifeCycle(lifeCycle);
			applicationResultRequest.setApplyType(applyType);
		ApplicationResultResponse applicationResultResponse = ApplicationUtil.getApplicationResult(applicationResultRequest);
			application.setApplicationType(applicationResultResponse.getApplyType());		
		if(functionType.CREATE_PRODUCT_INFO.equalsIgnoreCase(functionId)){
			application.setProjectCode(applicationResultResponse.getProjectCode());
			application.setRecommendDecision(applicationResultResponse.getRecommendDecision());
		}
		if(Util.empty(application.getRecommendDecision())){
			application.setRecommendDecision(DEFAULT_REC_DECISION);
		}	
		applications.add(application);
	}

	private void createKECApplicationInfo(String functionId,String CARD_TYPE, String APPLICATION_CARD_TYPE, ORIGFormHandler ORIGForm,
			HashMap<String, Object> cardInfo, HttpServletRequest request) throws Exception {
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		if(Util.empty(applications)){
			applications = new ArrayList<ApplicationDataM>();
			applicationGroup.setApplications(applications);
		}
		String businessClassId = SQLQueryEngine.display(cardInfo, "BUSINESS_CLASS_ID");
		String applicationRecordId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK);
		String loanId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PK);
		String cardId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARD_PK);
		String cardTypeId = SQLQueryEngine.display(cardInfo, "CARD_TYPE_ID");
		String projectCode = request.getParameter("PROJECT_CODE");
		
		logger.debug("applicationGroupId >> "+applicationGroupId);
		logger.debug("businessClassId >> " + businessClassId);
		logger.debug("applicationRecordId >> " + applicationRecordId);
		logger.debug("loanId >> " + loanId);
		logger.debug("cardId >> " + cardId);		
		logger.debug("cardTypeId >> " + cardTypeId);
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);		
//		
//		logger.debug("personalInfo >> " + personalInfo);
//		if (null == personalInfo) {
//			logger.debug("personalInfo is NULL., try to get from Supplementary....");
//			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
//			if (null == personalInfo) {
//				logger.error("Personal Info IS NULL !!!!");
//				return;
//			}
//			logger.debug("Got personalInfo from Supplementary.");
//			personalInfo = new PersonalInfoDataM();
//			personalInfo.setPersonalType(PERSONAL_TYPE_APPLICANT);
//			int personalSeq = OrigApplicationForm.getPersonalSeq(PERSONAL_TYPE_APPLICANT,applicationGroup);
//			String personalId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_INFO_PK);
//			personalInfo.setPersonalId(personalId);
//			personalInfo.setSeq(personalSeq);
//			applicationGroup.getPersonalInfos().add(personalInfo);
//		}		
		ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
		if (null == personalRelations) {
			personalRelations = new ArrayList<PersonalRelationDataM>();
			personalInfo.setPersonalRelations(personalRelations);
		}
		personalInfo.addPersonalRelation(applicationRecordId, PERSONAL_TYPE_APPLICANT, APPLICATION_LEVEL);
		
		ApplicationDataM application = new ApplicationDataM(applicationRecordId);
		if(!Util.empty(projectCode)){
			application.setProjectCode(projectCode);
		}
		application.setBusinessClassId(businessClassId);
		LoanDataM loan = new LoanDataM(applicationRecordId, loanId);
		CardDataM card = new CardDataM(loanId, cardId);
		card.setCardType(cardTypeId);
		card.setRequestCardType(cardTypeId);
		card.setApplicationType(BORROWER);
		//KEC Set Defalut cardComplete Flag
		card.setCompleteFlag(COMPLETE_FLAG_Y);
		loan.setCard(card);
		application.addLoan(loan);
		application.setMaincardRecordId(applicationRecordId);
		application.setLifeCycle(Math.max(1, applicationGroup.getMaxLifeCycle()));
		application.setApplicationNo(GenerateUnique.generateApplicationNo(applicationGroup.getApplicationGroupNo()
				, applicationGroup.itemLifeCycle()+1,applicationGroup.getMaxLifeCycle()));
		
		String productId = application.getProduct();	
		int lifeCycle = application.getLifeCycle();
		String applyType = applicationGroup.getApplicationType();

		logger.debug("productId >> "+productId);	
		logger.debug("lifeCycle >> "+lifeCycle);
		logger.debug("applyType >> "+applyType);
		
		ApplicationResultRequest applicationResultRequest = new ApplicationResultRequest();
			applicationResultRequest.setApplicationGroupId(applicationGroupId);
			applicationResultRequest.setProduct(productId);
			applicationResultRequest.setBusinessClassId(businessClassId);
			applicationResultRequest.setLifeCycle(lifeCycle);
			applicationResultRequest.setApplyType(applyType);
			applicationResultRequest.setCardType(cardTypeId);		
		ApplicationResultResponse applicationResultResponse = ApplicationUtil.getApplicationResult(applicationResultRequest);
			application.setApplicationType(applicationResultResponse.getApplyType());		
		if(functionType.CREATE_PRODUCT_INFO.equalsIgnoreCase(functionId)){
			application.setProjectCode(applicationResultResponse.getProjectCode());
			application.setRecommendDecision(applicationResultResponse.getRecommendDecision());
		}	
		if(Util.empty(application.getRecommendDecision())){
			application.setRecommendDecision(DEFAULT_REC_DECISION);
		}
//		application.setApplicationType(applicationGroup.getApplicationType());
		application.setApplicationDate(applicationGroup.getApplicationDate());
		ApplicationUtil.setPolicyRuleToApplication(applicationGroup, application, FormControl.getFormRoleId(request),personalInfo);
		if(applicationGroup.chkCardDub(application)){
			applications.add(application);
		}
	}
	public String getProduct(String businessClassId){
		return (null != businessClassId)?businessClassId.split("\\_")[0]:null;
	}
	public ApplicationDataM findLastApplication(String productId,String cardTypeId,ApplicationGroupDataM lastObjectForm){
		List<ApplicationDataM> applications = lastObjectForm.filterApplicationLifeCycle();
		if(null!=applications){
			for(ApplicationDataM application:applications){
				if (PRODUCT_CRADIT_CARD.equals(productId)&&productId.equals(application.getProduct())&&cardTypeId.equals(application.getCard().getCardType())){
					return application;
				}else if(PRODUCT_K_EXPRESS_CASH.equals(productId)&&productId.equals(application.getProduct())) {
					return application;
				}
			}
		}
		return null;
	}
	public ApplicationDataM findLastApplication(String productId,String cardTypeId,ApplicationGroupDataM lastObjectForm,List<ApplicationDataM> newApplications){
		List<ApplicationDataM> applications = lastObjectForm.filterApplicationLifeCycle();
		if(null!=applications){
			for(ApplicationDataM application:applications){
					if (compareApplicationSeq(applications,newApplications,productId,cardTypeId)&&PRODUCT_CRADIT_CARD.equals(productId)&&productId.equals(application.getProduct())&&cardTypeId.equals(application.getCard().getCardType())){
						return application;
					}else if(compareApplicationSeq(applications,newApplications,productId,cardTypeId)&&PRODUCT_K_EXPRESS_CASH.equals(productId)&&productId.equals(application.getProduct())) {
						return application;
					}
			}
		}
		return null;
	}
	public boolean compareApplicationSeq(List<ApplicationDataM> lastApplications,List<ApplicationDataM> newApplications,String productId,String cardTypeId){
		boolean result = false;
		if(null!=lastApplications&&newApplications.size()!=0){
			for(ApplicationDataM lastApplication:lastApplications){
				for(ApplicationDataM newApplication:newApplications){
					if(newApplications.indexOf(newApplication) == lastApplications.indexOf(lastApplication)){
						if(newApplication.getApplicationRecordId().equals(lastApplication.getApplicationRecordId())){
							result = true;
						}else{ result = false; break; }
					}
					
				}
			}
		}else{
			ApplicationDataM lastApplication = lastApplications.get(0);
			if(productId.equals(lastApplication.getProduct())&&cardTypeId.equals(lastApplication.getCard().getCardType())){
				result = true;
			}else{ result = false; }
		}
		return result;
	}
	private void createCCApplicationInfo(String functionId,String CARD_TYPE, String CARD_LEVEL, String APPLICATION_CARD_TYPE, ORIGFormHandler ORIGForm,
			HashMap<String, Object> cardInfo, HttpServletRequest request) throws Exception {
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String objectRoleId = flowControl.getRole();
		logger.debug("createCCApplicationInfo()..");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		if(Util.empty(applications)){
			applications = new ArrayList<ApplicationDataM>();
			applicationGroup.setApplications(applications);
		}
		String businessClassId = SQLQueryEngine.display(cardInfo, "BUSINESS_CLASS_ID");
		String cardTypeId = SQLQueryEngine.display(cardInfo, "CARD_TYPE_ID");
		String requestProductId = getProduct(businessClassId);
		ApplicationDataM lastApplication = findLastApplication(requestProductId,cardTypeId,ORIGForm.getLastObjectForm(),applications);
		String applicationRecordId = (null!=lastApplication)?lastApplication.getApplicationRecordId():null;
		String loanId = (null!=lastApplication)?lastApplication.getLoan().getLoanId():null;
		String cardId = (null!=lastApplication)?lastApplication.getCard().getCardId():null;
		if(Util.empty(applicationRecordId)){
			applicationRecordId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK);
		}
		if(Util.empty(loanId)){
			loanId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PK);
		}
		if(Util.empty(cardId)){
			cardId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARD_PK);
		}
		String projectCode = request.getParameter("PROJECT_CODE");
		
		logger.debug("applicationGroupId >> "+applicationGroupId);
		logger.debug("businessClassId >> " + businessClassId);
		logger.debug("applicationRecordId >> " + applicationRecordId);
		logger.debug("loanId >> " + loanId);
		logger.debug("cardId >> " + cardId);
		logger.debug("cardTypeId >> " + cardTypeId);
		logger.debug("APPLICATION_CARD_TYPE >> " + APPLICATION_CARD_TYPE);
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);		
//		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfos().get(0);
//		logger.debug("personalInfo >> " + personalInfo);
//		if (null == personalInfo) {
//			personalInfo = new PersonalInfoDataM();
//			personalInfo.setPersonalType(PERSONAL_TYPE_APPLICANT);
//			applicationGroup.getPersonalInfos().add(personalInfo);
//		}
		ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
		if (null == personalRelations) {
			personalRelations = new ArrayList<PersonalRelationDataM>();
			personalInfo.setPersonalRelations(personalRelations);
		}
//		personalInfo.addPersonalRelation(applicationRecordId, PERSONAL_TYPE_APPLICANT, APPLICATION_LEVEL);
		personalInfo.addPersonalRelation(applicationRecordId, personalInfo.getPersonalType(), APPLICATION_LEVEL);
		
		ApplicationDataM application = new ApplicationDataM(applicationRecordId);
		if(!Util.empty(projectCode)){
			application.setProjectCode(projectCode);
		}
		application.setBusinessClassId(businessClassId);
		LoanDataM loan = new LoanDataM(applicationRecordId, loanId);
		CardDataM card = new CardDataM(loanId, cardId);
		card.setCardType(cardTypeId);
		card.setRequestCardType(cardTypeId);
		card.setCardLevel(CARD_LEVEL);
		card.setApplicationType(BORROWER);
		if(CARD_TYPE_HAS_MEMBER_SHIP.contains(CARD_TYPE)){
			if(ROLE_DE1_1.equals(objectRoleId)){
				if(CGA_CARD_TYPE.equals(CARD_TYPE) || BENZ_CARD_TYPE.equals(CGA_CARD_TYPE)){
					card.setCompleteFlag(COMPLETE_FLAG_Y);
				}else{
					card.setCompleteFlag(COMPLETE_FLAG_N);
				}
			}else{
				card.setCompleteFlag(COMPLETE_FLAG_N);
			}
		}else{
			card.setCompleteFlag(COMPLETE_FLAG_Y);
		}
		loan.setCard(card);
		application.addLoan(loan);
		application.setMaincardRecordId(applicationRecordId);
		application.setLifeCycle(Math.max(1,applicationGroup.getMaxLifeCycle()));
		application.setApplicationNo(GenerateUnique.generateApplicationNo(applicationGroup.getApplicationGroupNo()
				, applicationGroup.itemLifeCycle()+1,applicationGroup.getMaxLifeCycle()));
//		application.setApplicationType(applicationGroup.getApplicationType());
		String productId = application.getProduct();	
		int lifeCycle = application.getLifeCycle();
//		String applyType = applicationGroup.getApplicationType();

		logger.debug("productId >> "+productId);	
		logger.debug("lifeCycle >> "+lifeCycle);
//		logger.debug("applyType >> "+applyType);
		
		ApplicationResultRequest applicationResultRequest = new ApplicationResultRequest();
			applicationResultRequest.setApplicationGroupId(applicationGroupId);
			applicationResultRequest.setProduct(productId);
			applicationResultRequest.setBusinessClassId(businessClassId);
			applicationResultRequest.setLifeCycle(lifeCycle);
//			applicationResultRequest.setApplyType(applyType);
			applicationResultRequest.setCardType(cardTypeId);	
			applicationResultRequest.setCardLevel(CARD_LEVEL);
		ApplicationResultResponse applicationResultResponse = ApplicationUtil.getApplicationResult(applicationResultRequest);
			application.setApplicationType(applicationResultResponse.getApplyType());		
		if(functionType.CREATE_PRODUCT_INFO.equalsIgnoreCase(functionId)){
			application.setProjectCode(applicationResultResponse.getProjectCode());
			application.setRecommendDecision(applicationResultResponse.getRecommendDecision());
		}
		if(Util.empty(application.getRecommendDecision())){
			application.setRecommendDecision(DEFAULT_REC_DECISION);
		}
		application.setApplicationDate(applicationGroup.getApplicationDate());
		ApplicationUtil.setPolicyRuleToApplication(applicationGroup, application, FormControl.getFormRoleId(request),personalInfo);
		
		//Thread.sleep(5000);
		if(applicationGroup.chkCardDub(application)){
			applications.add(application);
		}
	}
//	#rawi comment change to GenerateUnique
//	public static String generateApplicationNo(ApplicationGroupDataM applicationGroup){
//		int lifeCycle = Math.max(1,applicationGroup.getMaxLifeCycle())-1;
//		String applicationGroupId = applicationGroup.getApplicationGroupNo();
//		int count = applicationGroup.sizeApplication()+1;
//		return String.format("%s/%02d/%02d",applicationGroupId,count,lifeCycle);
//	}
}
