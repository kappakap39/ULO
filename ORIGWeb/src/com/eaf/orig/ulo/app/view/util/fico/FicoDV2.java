
package com.eaf.orig.ulo.app.view.util.fico;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.fico.FicoApplication;
//import com.google.gson.Gson;
import com.orig.bpm.workflow.handle.WorkflowAction;


public class FicoDV2 extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(FicoDV2.class);
	private String FICO_DECISION_POINT_DV2 =SystemConstant.getConstant("FICO_DECISION_POINT_DV2");
	private String FICO_DECISION_POINT_DC = SystemConstant.getConstant("FICO_DECISION_POINT_DC");
	private String DECISION_ACTION_CANCEL =SystemConstant.getConstant("DECISION_ACTION_CANCEL");
	private String PRODUCT_K_PERSONAL_LOAN =SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	private String RECOMMEND_DECISION_CANCEL =SystemConstant.getConstant("RECOMMEND_DECISION_CANCEL");
	private String RECOMMEND_DECISION_APPROVED =SystemConstant.getConstant("RECOMMEND_DECISION_APPROVED");
	private String VALIDATION_STATUS_REQUIRED = SystemConstant.getConstant("VALIDATION_STATUS_REQUIRED");
	private String FIELD_ID_DATA_VALIDATION_STATUS =SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS");
	private	String CONTACT_RESULT_CANCEL = SystemConstant.getConstant("CONTACT_RESULT_CANCEL");
	private	String VALIDATION_STATUS_COMPLETED_NOT_FRAUD = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED_NOT_FRAUD");
	private	String DOCUMENT_OVER_SLA_BY_CUSTOMER = SystemConstant.getConstant("DOCUMENT_OVER_SLA_BY_CUSTOMER");
	private	String DOCUMENT_OVER_SLA_BY_HR = SystemConstant.getConstant("DOCUMENT_OVER_SLA_BY_HR");
	public Object processAction() {
		logger.debug("FicoDV2.processAction()..");
		FicoApplication ficoApplication = new FicoApplication();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ficoApplication.setDiffRequestFlag(MConstant.FLAG_N);
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		FicoRequest ficoRequest = new FicoRequest();
		ficoRequest.setApplicationGroup(applicationGroup);
		String decisionAction = applicationGroup.getDecisionAction();
		String formAction = applicationGroup.getFormAction();
		String jobstate = applicationGroup.getJobState();
		logger.debug("decisionAction : "+decisionAction);
		logger.debug("formAction : "+formAction);
		logger.debug("jobstate : "+jobstate);		
		if(DECISION_ACTION_CANCEL.equals(decisionAction)||WorkflowAction.CANCEL_APPLICATION.equals(formAction)){
			return ficoApplication;
		}
		try{
			String waitStatus = FicoApplicationUtil.requestWaitFico(ficoRequest);// Verify Wait
			String overSLAFlag = FicoApplicationUtil.verifyDVOverSLA(applicationGroup);
			logger.debug("waitStatus : " + waitStatus);
			logger.debug("overSLAFlag : " + overSLAFlag);			
			if(Util.empty(waitStatus)&&Util.empty(overSLAFlag)&&!SystemConstant.lookup("JOB_STATE_EXCEPTION_VERIFY_INCOME",jobstate)){
				FicoResponse ficoResponse = processFicoDV2(ficoRequest);
				String ficoResponseResult = ficoResponse.getResultCode();
				logger.debug("ficoResponseResult : "+ficoResponseResult);
				ficoApplication.setResultCode(ficoResponseResult);	
				ficoApplication.setResultDesc(ficoResponse.getResultDesc());
				if(FicoApplicationUtil.ResultCode.SUCCESS.equals(ficoResponseResult)){
					String docCompletedFlag = applicationGroup.getDocCompletedFlag();
					logger.debug("docCompletedFlag : "+docCompletedFlag);
					if(MConstant.FLAG.NO.equals(docCompletedFlag)){
						ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
						ficoApplication.setResultDesc(MessageErrorUtil.getText("ERROR_DOCUMENT_COMPLETE_FLAG"));
						ficoApplication.setDocCompleteFlag(MConstant.FLAG.NO);
					}else{
						boolean diffRequestFlag = diffRequestFlag(applicationGroup);
						logger.debug("diffRequestFlag : "+diffRequestFlag);
						if(diffRequestFlag){
							VerifyUtil.setVerificationResult(applicationGroup, VALIDATION_STATUS_REQUIRED);
							ORIGServiceProxy.getApplicationManager().saveApplication(applicationGroup, userM);
							ficoApplication.setDiffRequestFlag(MConstant.FLAG_Y);
						}else{
							processCancelApplication(applicationGroup);
						}
					}
				}
			}else if(!Util.empty(overSLAFlag)){
				defaultOverSLAFlag(applicationGroup, overSLAFlag);
				boolean diffRequestFlag = diffRequestFlag(applicationGroup);
				logger.debug("diffRequestFlag : "+diffRequestFlag);
				if(diffRequestFlag){
					processCancelApplication(applicationGroup);
				}else{
					FicoResponse ficoResponse = processFicoDC(ficoRequest);
					String ficoResponseResult = ficoResponse.getResultCode();
					logger.debug("ficoResponseResult : "+ficoResponseResult);
					ficoApplication.setResultCode(ficoResponseResult);	
					ficoApplication.setResultDesc(ficoResponse.getResultDesc());
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			ficoApplication.setResultDesc(e.getLocalizedMessage());
		}
		logger.debug("formAction : "+applicationGroup.getFormAction());		
		logger.debug("ficoApplication : "+ ficoApplication.getDiffRequestFlag());
		return ficoApplication;
	}
		
	private void defaultOverSLAFlag(ApplicationGroupDataM applicationGroup, String overSLAFlag) {
		if(DOCUMENT_OVER_SLA_BY_CUSTOMER.equals(overSLAFlag)){
			//Add supplementary result for verify customer
			VerifyUtil.setVerificationResultByOverSLAFlag(applicationGroup, overSLAFlag);
		}else if(DOCUMENT_OVER_SLA_BY_HR.equals(overSLAFlag)){
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			VerificationResultDataM	verificationResult = personalInfo.getVerificationResult();
			String verResultDesc = CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS,VALIDATION_STATUS_COMPLETED_NOT_FRAUD,"DISPLAY_NAME");
			verificationResult.setVerHrResultCode(VALIDATION_STATUS_COMPLETED_NOT_FRAUD);
			verificationResult.setVerHrResult(verResultDesc);
		}
		
	}

	public FicoResponse processFicoDV2(FicoRequest ficoRequest){		
		ficoRequest.setFicoFunction(FICO_DECISION_POINT_DV2);
		ficoRequest.setRoleId(FormControl.getFormRoleId(request));
		return FicoApplicationUtil.requestFico(ficoRequest);
	}
	public FicoResponse processFicoDC(FicoRequest ficoRequest){
		FicoResponse ficoResponse = new FicoResponse();
		ficoRequest.setFicoFunction(FICO_DECISION_POINT_DC);
		ficoRequest.setRoleId(FormControl.getFormRoleId(request));
		ficoResponse =FicoApplicationUtil.requestFico(ficoRequest);
		return ficoResponse;
	}
	public boolean diffRequestFlag(ApplicationGroupDataM applicationGroup){
		ApplicationDataM applicationItem = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		boolean diffRequestFlag = false;
		if(!Util.empty(applicationItem) && RECOMMEND_DECISION_APPROVED.equals(applicationItem.getFinalAppDecision())){
			 String diffRequestResult = applicationItem.getDiffRequestResult();
			 if(!Util.empty(diffRequestResult)){
				 diffRequestFlag = false;
			 }else{
				LoanDataM loan=	applicationItem.getLoan();
				if(!Util.empty(loan.getRecommendLoanAmt()) && !Util.empty(loan.getRequestLoanAmt())
						&&  (loan.getRecommendLoanAmt().compareTo(loan.getRequestLoanAmt())!=0)){
					diffRequestFlag =  true;
				} 
			 }			
		}
		return diffRequestFlag;
	}
	public void processCancelApplication(ApplicationGroupDataM applicationGroup){
		logger.debug("processCancelApplication..");
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		int countAppCancel = 0;
		if(!Util.empty(applications)){
			for(ApplicationDataM applicationItem:applications){
				logger.debug("applicationItem.getFinalAppDecision() >> "+applicationItem.getFinalAppDecision());
				if(CONTACT_RESULT_CANCEL.equals(applicationItem.getDiffRequestResult())){
					applicationItem.setFinalAppDecision(RECOMMEND_DECISION_CANCEL);
				}		
				if(RECOMMEND_DECISION_CANCEL.equals(applicationItem.getFinalAppDecision())){
					countAppCancel++;
				}				
			}
		}
		if(countAppCancel == applications.size()){
			applicationGroup.setFormAction(WorkflowAction.CANCEL_APPLICATION);
		}	
	}
}
