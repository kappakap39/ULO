package com.eaf.orig.ulo.app.view.util.fico;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.cis.compare.CISCompareAction;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.DocumentCheckListUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.FinalVerifyResultDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM.CallerScreen;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class FicoApplicationUtil {
	private static transient Logger logger = Logger.getLogger(FicoApplicationUtil.class);
	private static String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	private static String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	public class ResultCode{
		public static final String SUCCESS = "00";
		public static final String BUSINESS_EXCEPTION = "10";
		public static final String SYSTEM_EXCEPTION = "20";
		public static final String NO_ACTION = "-1";
	}
	public FicoApplicationUtil(){
		super();
	}
	public static FicoResponse requestFico(FicoRequest request){
		return requestFico(request,null);		
	}
	public static FicoResponse requestFico(FicoRequest request,CallerScreen callerScreen){
		FicoResponse response = new FicoResponse();
		try{	
			logger.debug("BPM_HOST >> "+BPM_HOST);
			logger.debug("BPM_PORT >> "+BPM_PORT);		
			ApplicationGroupDataM applicationGroup = request.getApplicationGroup();
			preProcessRequestFico(applicationGroup,request.getRoleId());
			WorkflowRequestDataM workflowRequest  = new WorkflowRequestDataM();
				workflowRequest.setApplicationGroup(applicationGroup);
				workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				workflowRequest.setFormAction(applicationGroup.getFormAction());
				workflowRequest.setDecisionAction(applicationGroup.getDecisionAction());
				workflowRequest.setInstantId(String.valueOf(applicationGroup.getInstantId()));	
				workflowRequest.setDecisionStation(request.getFicoFunction());
				if(null != callerScreen){
					workflowRequest.setCallerScreen(callerScreen);
				}
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT);
			WorkflowResponseDataM workflowResponse = workflowManager.callBPMDecisionService(workflowRequest);
			String resultCode = workflowResponse.getResultCode();
			logger.debug("resultCode >> "+resultCode);
			if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(resultCode)){
				response.setResultCode(FicoApplicationUtil.ResultCode.SUCCESS);
				postProcessRequestFico(applicationGroup);
			}else{
				if(BpmProxyConstant.RestAPIResult.FICO_ERROR.equals(resultCode)){
					response.setResultCode(FicoApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
					String errorMsg = "";
					String comma = "";
					if(!Util.empty(workflowResponse.getErrorMsg())){
						errorMsg = workflowResponse.getErrorMsg();
						comma = ",";
					}
					if(!Util.empty(workflowResponse.getResultDesc())){
						errorMsg += comma+workflowResponse.getResultDesc();
						comma = ",";
					}
					String ficoErrorMsg = "";			
					if(!Util.empty(workflowResponse.getErrorCode())){
						ficoErrorMsg = workflowResponse.getErrorCode();
					}
					if(!Util.empty(errorMsg)){
						ficoErrorMsg += ((!Util.empty(workflowResponse.getErrorCode()))?" : ":"")+errorMsg;
					}
					logger.debug("ficoErrorMsg : "+ficoErrorMsg);
					if(Util.empty(ficoErrorMsg)){
						response.setResultCode(FicoApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
					}else{
						response.setResultDesc(ficoErrorMsg);
					}
				}else{
					response.setResultCode(FicoApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
					response.setResultDesc(workflowResponse.getResultDesc());
				}
			}
//			response.setFicoResponse(workflowResponse.getFicoApplication());
		}catch(Exception e){
			logger.fatal("ERROR",e);
			response.setResultCode(FicoApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			response.setResultDesc(e.getLocalizedMessage());
		}
		return response;
	}
	public static void preProcessRequestFico(ApplicationGroupDataM applicationGroup,String roleId){
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
	public static void postProcessRequestFico(ApplicationGroupDataM applicationGroup){
		boolean requiredDocFlag = applicationGroup.getRequiredDocFlag();
		logger.debug("requiredDocFlag : "+requiredDocFlag);
		if(!requiredDocFlag){
			applicationGroup.setDocumentCheckLists(new ArrayList<DocumentCheckListDataM>());
		}
		DocumentCheckListUtil.defaultNotReceivedDocumentReason(applicationGroup);
		ApplicationUtil.defaultCardLinkCustId(applicationGroup);
	}

	public static String requestWaitFico(FicoRequest request) throws Exception{
		logger.debug("<<< requestWaitFico >>> ");
		String waitStatus	="";
		ApplicationGroupDataM applicationGroup = request.getApplicationGroup();			
		ArrayList<PersonalInfoDataM>  personals = applicationGroup.getPersonalInfos();
		WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT);
		waitStatus	 =	workflowManager.getVerificationType(personals);
		logger.debug("waitStatus >>> "+waitStatus);
		return waitStatus;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean requestOverSLAFico(FicoRequest request){
		logger.debug("<<< requestOverSLAFico >>> ");
		boolean OverSLAFlag = false;
		try{
			ApplicationGroupDataM applicationGroup = request.getApplicationGroup();
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT);
			WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			workflowRequest.setInstantId(String.valueOf(applicationGroup.getInstantId()));
			workflowRequest.setApplicationGroup(request.getApplicationGroup());
			OverSLAFlag = workflowManager.isOverSLA(workflowRequest);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return OverSLAFlag;
	}
	public static String getAppGroupActionDecision(ApplicationGroupDataM applicationGroup) {
		String recommendDecision = WorkflowManager.getAppGroupActionDecision(applicationGroup);
		return recommendDecision;
	}
	public static String verifyDVOverSLA(ApplicationGroupDataM applicationGroup) throws Exception {
		WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT);
			WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			workflowRequest.setInstantId(String.valueOf(applicationGroup.getInstantId()));
			workflowRequest.setApplicationGroup(applicationGroup);
		return workflowManager.verifyDVOverSLA(workflowRequest);
	}
}
