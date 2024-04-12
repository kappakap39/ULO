package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.ulo.app.view.form.cis.compare.CISCompareAction;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.DocumentCheckListUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.FinalVerifyResultDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.manual.DecisionServiceProxy;
import com.eaf.service.module.manual.EDecisionServiceProxy;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class DecisionApplicationUtil {
	private static String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	private static String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	private static String APPLY_TYPE_ERROR_CODE = SystemConstant.getConstant("APPLY_TYPE_ERROR_CODE");
	private static transient Logger logger = Logger.getLogger(DecisionApplicationUtil.class);
	public  class ResultCode{
		public static final String SUCCESS = "00";
		public static final String BUSINESS_EXCEPTION = "10";
		public static final String SYSTEM_EXCEPTION = "20";
		public static final String NO_ACTION = "-1";
	}
	public static class VerificationType{
		public static final String WAIT_CUSTOMER = "CUS";
		public static final String WAIT_HR = "HR";
		public static final String WAIT_WEB_VER = "WEB";
	}
	
	public DecisionApplicationUtil(){
		super();
	}
	
	private static String FINAL_APP_DECISION_APPROVE = SystemConstant.getConstant("FINAL_APP_DECISION_APPROVE");
	private static String FINAL_APP_DECISION_REFER = SystemConstant.getConstant("FINAL_APP_DECISION_REFER");
	private static String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	private static String PRODUCT_K_PERSONAL_LOAN =SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	private static String FINAL_APP_DECISION_PRE_APPROVE = SystemConstant.getConstant("DECISION_SERVICE_PRE_APPPROVE");
	private static String RECOMMEND_DECISION_APPROVED =SystemConstant.getConstant("RECOMMEND_DECISION_APPROVED");
	private static String RECOMMEND_DECISION_PRE_APPROVE = SystemConstant.getConstant("RECOMMEND_DECISION_PRE_APPROVE");
	private static String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	private static String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	public static String PRODUCT_CODE_KEC = SystemConstant.getConstant("PRODUCT_CODE_KEC");
	public static String PAYMENT_METHOD_NOT_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_NOT_DEPOSIT_ACCOUNT");
	private static String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private static String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
	public static  DecisionServiceResponse requestDecisionService(DecisionServiceRequestDataM reqApplication) throws Exception{
		
		DecisionServiceResponse response = new DecisionServiceResponse();
		try{
			String source = reqApplication.getApplicationGroup().getSource();
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
			if(ApplicationUtil.eApp(source) || ApplicationUtil.cjd(source)){
				String URL = SystemConfig.getProperty("CALL_EDECISION_SERVICE_URL");
				preProcessRequestDecisionService(reqApplication.getApplicationGroup(),reqApplication.getRoleId());
				
					serviceRequest.setServiceId(EDecisionServiceProxy.serviceId);
					serviceRequest.setUserId(reqApplication.getUserId());
					serviceRequest.setEndpointUrl(URL);
					serviceRequest.setUniqueId(reqApplication.getApplicationGroup().getApplicationGroupId());
					serviceRequest.setRefId(reqApplication.getApplicationGroup().getApplicationGroupNo());
					serviceRequest.setObjectData(reqApplication);
			}else{
				String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
				preProcessRequestDecisionService(reqApplication.getApplicationGroup(),reqApplication.getRoleId());

					serviceRequest.setServiceId(DecisionServiceProxy.serviceId);
					serviceRequest.setUserId(reqApplication.getUserId());
					serviceRequest.setEndpointUrl(URL);
					serviceRequest.setUniqueId(reqApplication.getApplicationGroup().getApplicationGroupId());
					serviceRequest.setRefId(reqApplication.getApplicationGroup().getApplicationGroupNo());
					serviceRequest.setObjectData(reqApplication);
			}
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest,reqApplication.getTransactionId());		 		
			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
				postProcessRequestDecisionService(reqApplication.getApplicationGroup());
				response.setResultCode(DecisionApplicationUtil.ResultCode.SUCCESS);
			}else{
				response.setResultCode(serivceResponse.getStatusCode());
				if(!Util.empty(serivceResponse.getErrorInfo())){
					response.setResultDesc(serivceResponse.getErrorInfo().getErrorInformation());	
					response.setErrorType(serivceResponse.getErrorInfo().getErrorType());
				}else{
					response.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			response.setResultDesc(e.getLocalizedMessage());
			response.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			response.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
		}
		return response;
	}
	
	public static  DecisionServiceResponse requestEDecisionServiceUncheck(DecisionServiceRequestDataM reqApplication) throws Exception{	
		DecisionServiceResponse response = new DecisionServiceResponse();
		try {
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
			String URL = SystemConfig.getProperty("CALL_EDECISION_SERVICE_URL");
			
			preProcessRequestDecisionService(reqApplication.getApplicationGroup(),reqApplication.getRoleId());
			serviceRequest.setServiceId(EDecisionServiceProxy.serviceId);
			serviceRequest.setUserId(reqApplication.getUserId());
			serviceRequest.setEndpointUrl(URL);
			serviceRequest.setUniqueId(reqApplication.getApplicationGroup().getApplicationGroupId());
			serviceRequest.setRefId(reqApplication.getApplicationGroup().getApplicationGroupNo());
			serviceRequest.setObjectData(reqApplication);
			
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest,reqApplication.getTransactionId());		 		
			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())) {
				postProcessRequestDecisionService(reqApplication.getApplicationGroup());
				response.setResultCode(DecisionApplicationUtil.ResultCode.SUCCESS);
			} else {
				response.setResultCode(serivceResponse.getStatusCode());
				if(!Util.empty(serivceResponse.getErrorInfo())) {
					response.setResultDesc(serivceResponse.getErrorInfo().getErrorInformation());	
					response.setErrorType(serivceResponse.getErrorInfo().getErrorType());
				} else {
					response.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
				}
			}
		} catch(Exception e) {
			logger.fatal("ERROR ",e);
			response.setResultDesc(e.getLocalizedMessage());
			response.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			response.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
		}
		return response;
	}
	
	public static void preProcessRequestDecisionService(ApplicationGroupDataM applicationGroup,String roleId){
		applicationGroup.clearFinalVerify();
		String verifyCustomerResultCode = VerifyUtil.VerifyResult.getVerifyCustomerResult(applicationGroup);
		applicationGroup.createFinalVerifyResult(FinalVerifyResultDataM.VerifyId.VERIFY_CUSTOMER,verifyCustomerResultCode);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		logger.debug("personalInfo.getPersonalId() >> "+personalInfo.getPersonalId());
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(null == verificationResult){
			verificationResult = new VerificationResultDataM();
		}
		applicationGroup.createFinalVerifyResult(FinalVerifyResultDataM.VerifyId.VERIFY_HR,verificationResult.getVerHrResultCode());
		applicationGroup.createFinalVerifyResult(FinalVerifyResultDataM.VerifyId.VERIFY_PROJECT_CODE,verificationResult.getVerPrivilegeResultCode());
		applicationGroup.createFinalVerifyResult(FinalVerifyResultDataM.VerifyId.VERIFY_WEB,verificationResult.getVerWebResultCode());
		String verifyIncomeResultCode = VerifyUtil.VerifyResult.getVerifyIncomeResult(applicationGroup, roleId);
		applicationGroup.createFinalVerifyResult(FinalVerifyResultDataM.VerifyId.VERIFY_INCOME,verifyIncomeResultCode);
		CISCompareAction cisCompareProcessor = new CISCompareAction();			
		HashMap<String,CompareDataM> cisCompareFields = cisCompareProcessor.processAction(applicationGroup,roleId);
		if(!Util.empty(cisCompareFields)){
			ComparisonGroupDataM comparisonGroup = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.CIS);
			if(null == comparisonGroup){
				comparisonGroup = new ComparisonGroupDataM();
				comparisonGroup.setSrcOfData(CompareDataM.SoruceOfData.CIS);
				applicationGroup.addComparisonGroups(comparisonGroup);
				comparisonGroup.setComparisonFields(cisCompareFields);
			}					
		}
	}
	public static void postProcessRequestDecisionService(ApplicationGroupDataM applicationGroup){
		boolean requiredDocFlag = applicationGroup.getRequiredDocFlag();
		logger.debug("requiredDocFlag : "+requiredDocFlag);
		if(!requiredDocFlag){
			applicationGroup.setDocumentCheckLists(new ArrayList<DocumentCheckListDataM>());
		}
		DocumentCheckListUtil.defaultNotReceivedDocumentReason(applicationGroup);
		ApplicationUtil.defaultCardLinkCustId(applicationGroup);
	}
	
	public static String getAppGroupActionDecision(ApplicationGroupDataM applicationGroup) {
		String recommendDecision = WorkflowManager.getAppGroupActionDecision(applicationGroup);
		return recommendDecision;
	}
	
	public static String requestWaitStatus(List<PersonalInfoDataM> uloPeople) throws Exception{
		String waitStatus	="";
		if (uloPeople == null || uloPeople.isEmpty()) {
			return null;
		}

		List<VerificationResultDataM> verList = new ArrayList<VerificationResultDataM>();
		for (PersonalInfoDataM person : uloPeople) {
			verList.add(person.getVerificationResult());
		}
		
		if(verList.isEmpty()){
			return null;
		}
		ArrayList<String>   VERIFICATION_STATUS_WAIT =SystemConstant.getArrayListConstant("VERIFICATION_STATUS_WAIT");
		boolean waitCus = false;
		boolean waitHR = false;
		boolean waitWeb = false;
		for (VerificationResultDataM ver : verList) {
			if(ver == null){
				continue;
			}
			if(VERIFICATION_STATUS_WAIT.contains(ver.getVerCusResultCode())){
				waitCus = true;
				break;
			}
			if(VERIFICATION_STATUS_WAIT.contains(ver.getVerHrResultCode())){
				waitHR = true;
			}
			if(VERIFICATION_STATUS_WAIT.contains(ver.getVerWebResultCode())){
				waitWeb = true;
			}
		}
		
		StringBuilder result = new StringBuilder();
		if(waitCus){
			result.append(DecisionApplicationUtil.VerificationType.WAIT_CUSTOMER+"-");
		}
		if(waitHR){
			result.append(DecisionApplicationUtil.VerificationType.WAIT_HR+"-");
		}
		if(waitWeb){
			result.append(DecisionApplicationUtil.VerificationType.WAIT_WEB_VER);
		}
		waitStatus=result.toString();
		return waitStatus;
	}
	public static boolean isApplyTypeError(String applicationType){
//		ArrayList<String> applyTypeExceptions = new ArrayList<>(Arrays.asList(IA_APPLY_TYPE_ERROR_EXCEPTION));
		logger.debug("applicationType>>"+applicationType);
//		if(!applyTypeExceptions.contains(applicationType)){
//			return true;
//		}
		if(APPLY_TYPE_ERROR_CODE.equals(applicationType)){
			return true;
		}
		return false;
	}
	public static String verifyDVOverSLA(ApplicationGroupDataM applicationGroup,UserDetailM userM) throws Exception {
		WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT);
			WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			workflowRequest.setInstantId(String.valueOf(applicationGroup.getInstantId()));
			workflowRequest.setApplicationGroup(applicationGroup);
			workflowRequest.setUsername(userM.getUserName());
			workflowRequest.setFromRole(userM.getCurrentRole());
		return workflowManager.verifyDVOverSLA(workflowRequest);
	}
	
	public static boolean diffRequestFlag(ApplicationGroupDataM applicationGroup)
	{
		return diffRequestFlag(applicationGroup, null);
	}
	
	public static boolean diffRequestFlag(ApplicationGroupDataM applicationGroup, String roleId)
	{
		String ROLE_DV =SystemConstant.getConstant("ROLE_DV");
		ApplicationDataM applicationItem = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		boolean diffRequestFlag = false;
		if(!Util.empty(applicationItem))
		{
			String diffRequestResult = applicationItem.getDiffRequestResult();
		
			if((FINAL_APP_DECISION_APPROVE.equals(applicationItem.getRecommendDecision()) 
					   || (FINAL_APP_DECISION_REFER.equals(applicationItem.getRecommendDecision()) && (ROLE_DV.equals(roleId) || !isRequireVerify(applicationGroup)))
					   || FINAL_APP_DECISION_PRE_APPROVE.equals(applicationItem.getRecommendDecision())
					   // DF#3263 - Add case reject - [Removed as of PROD Incident 1239600]
					   // || FINAL_APP_DECISION_REJECT.equals(applicationItem.getRecommendDecision())
					   )
			  ){
				 if(ROLE_DV.equals(roleId) && MConstant.FLAG.YES.equals(applicationItem.getDiffRequestFlag()) && Util.empty(diffRequestResult))
				 {
					 logger.debug("Role = DV && diffRequestFlag = Y && diffRequestResult is NULL -> Set diffRequestFlag = true");
					 diffRequestFlag = true;
				 }
				 else
				 {
					LoanDataM loan=	applicationItem.getLoan();
					
					//if diffRequestFlag = Y AND role = DV -> compare recommend with loanAmt / term
					//else compare recommend with request
					boolean isCalcDiff = (ROLE_DV.equals(roleId) && MConstant.FLAG.YES.equals(applicationItem.getDiffRequestFlag()));
					BigDecimal loanAmt = isCalcDiff ? loan.getLoanAmt() : loan.getRequestLoanAmt();
					BigDecimal term = isCalcDiff ? loan.getTerm() : loan.getRequestTerm();
					
					//Defect#2301
					boolean isDiffReqRes = !Util.empty(applicationItem.getDiffRequestResult());
					BigDecimal recLoanAmt = isDiffReqRes ? loan.getLoanAmt() : loan.getRecommendLoanAmt();
					BigDecimal recTerm = isDiffReqRes ? loan.getTerm() : loan.getRecommendTerm();
					
					logger.debug("[Compare Section]");
					logger.debug("loan.getRecommendLoanAmt() = " + recLoanAmt);
					logger.debug("loanAmt = " + loanAmt);
					logger.debug("loan.getRecommendTerm() = " + recTerm);
					logger.debug("term = " + term);
					
					if(
						(!Util.empty(recLoanAmt) && !Util.empty(loanAmt) && (recLoanAmt.compareTo(loanAmt)!=0))
						|| 
						((!Util.empty(recTerm) && !(BigDecimal.ZERO.compareTo(recTerm) == 0)) && !Util.empty(term) && (recTerm.compareTo(term)!=0))
					  )
					  {
						logger.debug("Compare Section Results -> Set diffRequestFlag = true");
						diffRequestFlag =  true;
					  } 
				 }
			}
			//PROD Incident 1239600 - This section prevent diffRequestFlag to disappear for case reject while calcu
			else if(FINAL_APP_DECISION_REJECT.equals(applicationItem.getRecommendDecision()) 
					&& ROLE_DV.equals(roleId) && MConstant.FLAG.YES.equals(applicationItem.getDiffRequestFlag()) 
					&& Util.empty(diffRequestResult))
			{
				     LoanDataM loan = applicationItem.getLoan();
				     loan.setInstallmentAmt(BigDecimal.ZERO);
				     loan.setInterestRate(BigDecimal.ZERO);
					 logger.debug("Reject && Role = DV && diffRequestFlag = Y && diffRequestResult is NULL -> Set diffRequestFlag = true");
					 diffRequestFlag = true;
			}
		}
		
		String diffRequestFlagStr = diffRequestFlag ? MConstant.FLAG.YES : "";
		logger.info("Set diffRequestFlag to ApplicationDataM : " + diffRequestFlagStr);
        applicationItem.setDiffRequestFlag(diffRequestFlagStr);
		
		return diffRequestFlag;
	}
	
	public static boolean isPreviousKPLDecisionAP(ApplicationGroupDataM applicationGroup)
	{
		ApplicationDataM kplApp = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		if(!Util.empty(kplApp) && FINAL_APP_DECISION_APPROVE.equals(kplApp.getPreviousRecommendDecision()))
		{ return true; }
		else
		{ return false; }
	}
	
	public static boolean isKPLDecisionAP(ApplicationGroupDataM applicationGroup)
	{
		ApplicationDataM kplApp = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		if(!Util.empty(kplApp) && ( FINAL_APP_DECISION_APPROVE.equals(kplApp.getRecommendDecision()) 
									|| FINAL_APP_DECISION_PRE_APPROVE.equals(kplApp.geteRecommendDecision())))
		{ return true; }
		else
		{ return false; }
	}
	
	public static boolean isRequireVerify(ApplicationGroupDataM applicationGroup)
	{
		boolean isRequireVerify = false;
		String FLAG_YES = MConstant.FLAG.YES;
		
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(!Util.empty(personalInfo))
		{
            VerificationResultDataM verRes = personalInfo.getVerificationResult();
			if(!Util.empty(verRes))
			{
				if
				(
				  FLAG_YES.equals(verRes.getRequiredVerCustFlag()) ||
				  FLAG_YES.equals(verRes.getRequiredVerHrFlag()) ||
				  FLAG_YES.equals(verRes.getRequiredVerIncomeFlag()) ||
				  FLAG_YES.equals(verRes.getRequiredVerPrivilegeFlag()) ||
				  FLAG_YES.equals(verRes.getRequiredVerWebFlag())
				)
				{isRequireVerify = true;}
			}
		}
		return isRequireVerify;
	}
	
	public static Map<String, String> verifyDV(ApplicationGroupDataM applicationGroup,UserDetailM userM) throws Exception {
		WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT);
			WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			workflowRequest.setInstantId(String.valueOf(applicationGroup.getInstantId()));
			workflowRequest.setApplicationGroup(applicationGroup);
			workflowRequest.setUsername(userM.getUserName());
			workflowRequest.setFromRole(userM.getCurrentRole());
		return workflowManager.verifyDV(workflowRequest);
	}
	public static boolean foundLowIncomeFlag(ApplicationGroupDataM uloAppGroup){
		ArrayList<ApplicationDataM> applications = uloAppGroup.getApplications();
		if(!ServiceUtil.empty(applications)){
			for(ApplicationDataM application:applications) {
				logger.debug("foundLowIncomeFlag application.getLowIncomeFlag(): "+application.getLowIncomeFlag());
				logger.debug("foundLowIncomeFlag application.getFinalAppDecision(): "+application.getFinalAppDecision());
					if(!ServiceUtil.empty(application.getLowIncomeFlag())){
						if("Y".equalsIgnoreCase(application.getLowIncomeFlag()) && PRODUCT_CODE_KEC.equals(application.getProduct()) 
						   && isApprove(application.getFinalAppDecision())) {
							logger.debug("foundLowIncomeFlag e-app : LowIncomeFlag is Y ");
							return true;
						}
					}
			}
		}
		logger.debug("foundLowIncomeFlag :LowIncomeFlag is N ");
		return false;
	}
	public static boolean isApprove(String result){
			if(!ServiceUtil.empty(result)){
				if(RECOMMEND_DECISION_PRE_APPROVE.equals(result) || RECOMMEND_DECISION_APPROVED.equals(result)) {
						logger.debug("isApprove  true ");
						return true;
				}
			}
		logger.debug("isApprove false ");
		return false;
	}
	public static boolean isLowIncome(ApplicationGroupDataM uloAppGroup){
		//ArrayList<ApplicationDataM> applications = uloAppGroup.getApplications();
		ArrayList<ApplicationDataM> applications = uloAppGroup.filterListApplicationProductLifeCycle(PRODUCT_K_EXPRESS_CASH);
		if(!ServiceUtil.empty(applications)){
			for(ApplicationDataM application:applications) {
				logger.debug("foundLowIncomeFlag application.getLowIncomeFlag(): "+application.getLowIncomeFlag());
					if(!ServiceUtil.empty(application.getLowIncomeFlag())){
						if("Y".equalsIgnoreCase(application.getLowIncomeFlag())) {
							logger.debug("isLowIncome : LowIncomeFlag is Y ");
							return true;
						}
					}
			}
		}
		return false;
	}
	public static boolean isCancelAppLowIncome(ApplicationGroupDataM applicationGroup){
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(null != personalInfo){
			String personalId = personalInfo.getPersonalId();
			logger.debug("personalId >> "+personalId);		
			PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_EXPRESS_CASH); 
			logger.debug("paymentMethod >> "+paymentMethod);		
			if(!ServiceUtil.empty(paymentMethod)){
				if(!ServiceUtil.empty(paymentMethod.getPaymentMethod())){
					if(PAYMENT_METHOD_NOT_DEPOSIT_ACCOUNT.equalsIgnoreCase(paymentMethod.getPaymentMethod())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public static boolean isKEC(ApplicationGroupDataM applicationGroup) 
	{
		return (hasKECProduct(applicationGroup));
	}
	
	public static boolean hasKECProduct(ApplicationGroupDataM applicationGroup)
	{
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplicationsProduct(PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(applications))
		{
			for(ApplicationDataM applicationItem : applications)
			{
				return true;
			}
		}
		return false;
	}
	
	public static void deleteLockSubmitIATimestamp(ApplicationGroupDataM applicationGroup) throws Exception{
		String idNo = "";
		if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
			idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
		} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
			idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
		}
		UtilityDAO utilDao = new UtilityDAOImpl();
		utilDao.deleteErrorSubmitIATimestamp(idNo);
	}
}
