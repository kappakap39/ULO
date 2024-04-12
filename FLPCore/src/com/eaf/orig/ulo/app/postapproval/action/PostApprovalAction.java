package com.eaf.orig.ulo.app.postapproval.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.PostApprovalProcessInf;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;

public class PostApprovalAction {
	private static transient Logger logger = Logger.getLogger(PostApprovalAction.class);
	private static final String POST_APPROVAL_PROCESS = "POST_APPROVAL_PROCESS";	
	private static final String APPLICATION_LEVEL = "APPLICATION";	
	private static final String CIS_LEVEL = "CIS";	
	private static final String DECISION_FINAL_DECISION_APPROVE = SystemConstant.getConstant("DECISION_FINAL_DECISION_APPROVE");
	private static final String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	private static final String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	
	public PostApprovalAction(ApplicationGroupDataM applicationGroup, ApplicationDataM application,PersonalInfoDataM personalInfo) {
		super();
		this.applicationGroup = applicationGroup;
		this.application = application;
		this.personalInfo = personalInfo;
	}
	private ApplicationDataM application;
	private ApplicationGroupDataM applicationGroup;
	private PersonalInfoDataM personalInfo;

	public ApplicationGroupDataM getApplicationGroup() {
		return applicationGroup;
	}

	public void setApplicationGroup(ApplicationGroupDataM applicationGroup) {
		this.applicationGroup = applicationGroup;
	}

	public ApplicationDataM getApplication() {
		return application;
	}

	public void setApplication(ApplicationDataM application) {
		this.application = application;
	}	
	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		PostApprovalAction.logger = logger;
	}

	public PersonalInfoDataM getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(PersonalInfoDataM personalInfo) {
		this.personalInfo = personalInfo;
	}

	public PostApprovalActionResult processPostApprvalAction(boolean isPostApprovalCIS) throws Exception {
		if(isPostApprovalCIS){
			return processPostApprvalCISAction();
		}else{
			return processPostApprvalApplicationAction();
		}
	}
	
	public PostApprovalActionResult processPostApprvalCISAction() throws Exception {	
		PostApprovalActionResult postApprovalActionResult = new PostApprovalActionResult();
		try{
			postApprovalActionResult.setStatusCode(ServiceResponse.Status.SUCCESS);						
			boolean isCallCisCondition = isCallCISCondition(applicationGroup, personalInfo);			 
			logger.debug("isCallCisCondition : "+isCallCisCondition);	
			if(isCallCisCondition) {
				ArrayList<String> processActions = getProcessAction("",CIS_LEVEL);
				if(null != processActions){
					for (String processAction : processActions) {
						logger.debug("processAction : "+processAction);
						PostApprovalProcessInf processActionInf = ImplementControl.getPostApprovalProcessInf(POST_APPROVAL_PROCESS, processAction);
						boolean executeProcessFlag = processActionInf.validate(applicationGroup, personalInfo);
						logger.debug("executeProcessFlag : "+executeProcessFlag);
						if(executeProcessFlag){
							ProcessResponse processResponse = processActionInf.execute(applicationGroup, personalInfo);
							String processResult = processResponse.getStatusCode();
							logger.debug("processResult : "+processResult);
							if(!ServiceResponse.Status.SUCCESS.equals(processResult)){
								postApprovalActionResult.setStatusCode(processResult);
								postApprovalActionResult.setErrorData(processResponse.getErrorData());
								break;
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			postApprovalActionResult.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			postApprovalActionResult.setErrorData(ErrorController.error(e));
		}
		logger.debug(postApprovalActionResult);
		return postApprovalActionResult;
	}
	
    public PostApprovalActionResult processPostApprvalApplicationAction() throws Exception {	
		PostApprovalActionResult postApprovalActionResult = new PostApprovalActionResult();
		try{
			postApprovalActionResult.setStatusCode(ServiceResponse.Status.SUCCESS);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoApplication(application.getApplicationRecordId(), PERSONAL_RELATION_APPLICATION_LEVEL);
			if(null==personalInfo){
				personalInfo = new PersonalInfoDataM();
			}
						
			boolean isVerComplete = isVerCusAndVerWebComplete(personalInfo.getVerificationResult(), application.getVerificationResult());
			String finalAppDecision = application.getFinalAppDecision();
			logger.debug("finalAppDecision : "+finalAppDecision);	
			logger.debug("verCus and Ver Web Complete : "+isVerComplete);
			if(DECISION_FINAL_DECISION_APPROVE.equals(finalAppDecision) || (DECISION_FINAL_DECISION_REJECT.equals(finalAppDecision) && isVerComplete)) {
				String product = application.getProduct();
				logger.debug("product : "+product);
				ArrayList<String> processActions = getProcessAction(product,APPLICATION_LEVEL);
				logger.debug("processActions : "+processActions);
				if(null != processActions){
					for (String processAction : processActions) {
						logger.debug("processAction : "+processAction);
						PostApprovalProcessInf processActionInf = ImplementControl.getPostApprovalProcessInf(POST_APPROVAL_PROCESS, processAction);
						boolean executeProcessFlag = processActionInf.validate(applicationGroup, application);
						logger.debug("executeProcessFlag : "+executeProcessFlag);
						if(executeProcessFlag){
							ProcessResponse processResponse = processActionInf.execute(applicationGroup, application);
							String processResult = processResponse.getStatusCode();
							logger.debug("processResult : "+processResult);
							if(!ServiceResponse.Status.SUCCESS.equals(processResult)){
								postApprovalActionResult.setStatusCode(processResult);
								postApprovalActionResult.setErrorData(processResponse.getErrorData());
								break;
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			postApprovalActionResult.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			postApprovalActionResult.setErrorData(ErrorController.error(e));
		}
		logger.debug(postApprovalActionResult);
		return postApprovalActionResult;
	}
	
	@SuppressWarnings("rawtypes")
	private static ArrayList<String> getProcessAction(String product,String decisionLevel){
		String CACHE_POST_APPROVAL_PROCESS = SystemConstant.getConstant("CACHE_POST_APPROVAL_PROCESS");
		ArrayList<String> processActions = new ArrayList<String>();
		ArrayList<HashMap> cacheTypeResult = CacheControl.getCacheList(CACHE_POST_APPROVAL_PROCESS);
		logger.debug("decisionLevel>>"+decisionLevel);
		if(null != cacheTypeResult){
			for(HashMap rowResult : cacheTypeResult) {
				String productCompare = (String)rowResult.get("DECISION01");
				String processLevel = (String)rowResult.get("DECISION02");
				String result = (String)rowResult.get("RESULT");
				logger.debug("processLevel>>"+processLevel);
				logger.debug("result>>"+result);
				
				if(CIS_LEVEL.equals(decisionLevel) && decisionLevel.equals(processLevel) && !processActions.contains(result)){
					processActions.add(result);
				}else{
					if(null != productCompare && productCompare.equals(product) && decisionLevel.equals(processLevel)){					
						processActions.add(result);
					}
				}

			}
		}
		return processActions;
	}
	@SuppressWarnings("serial")
	public class PostApprovalActionResult implements Serializable,Cloneable{
		private String statusCode;
		private ErrorData errorData;
		public String getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}
		public ErrorData getErrorData() {
			return errorData;
		}
		public void setErrorData(ErrorData errorData) {
			this.errorData = errorData;
		}		
	}
	
	private boolean isVerCusAndVerWebComplete(VerificationResultDataM personalVerResult,VerificationResultDataM applicationVerResult){
		if(null==personalVerResult || null==applicationVerResult) return false;
		String POLICY_CODE_VER_WEB_COMPLETE = SystemConstant.getConstant("POLICY_CODE_VER_WEB_COMPLETE");
		String POLICY_VER_RESULT_DECLINE = SystemConstant.getConstant("POLICY_VER_RESULT_DECLINE");
		boolean isOrPolicy = false;
		ArrayList<PolicyRulesDataM>   policyRules = applicationVerResult.getPolicyRules();
		if(!Util.empty(policyRules)){
			for(PolicyRulesDataM policyRule : policyRules){
				if(null==policyRule.getPolicyCode() || null==policyRule.getVerifiedResult()) continue;
				if(POLICY_CODE_VER_WEB_COMPLETE.contains(policyRule.getPolicyCode()) && POLICY_VER_RESULT_DECLINE.equals(policyRule.getVerifiedResult())){
					isOrPolicy = true;
					break;
				}
			}
		}
		logger.debug("isOrPolicy>>"+isOrPolicy);
		 if(VerifyUtil.isCompleteVerCutomer(personalVerResult) && VerifyUtil.isCompleteVerWeb(personalVerResult) && !isOrPolicy){
			 return true;
		 }
		return false;
	}
	
	private boolean isCallCISCondition(ApplicationGroupDataM applicationGroup,PersonalInfoDataM personalInfo){
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications(personalInfo.getPersonalId(),
				personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
		if(!Util.empty(applications)){
			for(ApplicationDataM applicationDataM : applications){
				String finalAppDecision = applicationDataM.getFinalAppDecision();
				String source = applicationGroup.getSource();
				boolean isVerComplete = isVerCusAndVerWebComplete(personalInfo.getVerificationResult(), applicationDataM.getVerificationResult());
				/*
				 * 2019-04-11 
				 * add logic if reject and is eapp not call cis
				 */
				if(DECISION_FINAL_DECISION_APPROVE.equals(finalAppDecision) || (DECISION_FINAL_DECISION_REJECT.equals(finalAppDecision) && isVerComplete && !ApplicationUtil.eApp(source))){
					return true;
				}
			}
		}
		return false;
	}
}
