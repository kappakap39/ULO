package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ava.flp.cjd.model.CJDResponseServiceProxyRequest;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.notify.cont.NotifyConstant;
import com.eaf.core.ulo.service.notify.controller.NotifyController;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoRequestDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.CJDResponseUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.orig.ulo.model.notification.NotificationRequest;
import com.eaf.orig.ulo.model.notification.NotificationResponse;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.ibm.websphere.ce.cm.DuplicateKeyException;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowAction;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class DecisionEDV2 extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionEDV2.class);
	private String DECISION_ACTION_CANCEL =SystemConstant.getConstant("DECISION_ACTION_CANCEL");
	private String RECOMMEND_DECISION_CANCEL =SystemConstant.getConstant("RECOMMEND_DECISION_CANCEL");
	private String VALIDATION_STATUS_REQUIRED = SystemConstant.getConstant("VALIDATION_STATUS_REQUIRED");
	private String FIELD_ID_DATA_VALIDATION_STATUS =SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS");
	private	String CONTACT_RESULT_CANCEL = SystemConstant.getConstant("CONTACT_RESULT_CANCEL");
	private	String VALIDATION_STATUS_COMPLETED_NOT_FRAUD = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED_NOT_FRAUD");
	private	String DOCUMENT_OVER_SLA_BY_CUSTOMER = SystemConstant.getConstant("DOCUMENT_OVER_SLA_BY_CUSTOMER");
	private	String DOCUMENT_OVER_SLA_BY_HR = SystemConstant.getConstant("DOCUMENT_OVER_SLA_BY_HR");
	private String FINAL_APP_DECISION_APPROVE = SystemConstant.getConstant("FINAL_APP_DECISION_APPROVE");
	private String ROLE_DV = SystemConstant.getConstant("ROLE_DV");
	public static String PRODUCT_CODE_KEC = SystemConstant.getConstant("PRODUCT_CODE_KEC");
	
	private String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private int SUBMIT_IA_BLOCK_TIME = SystemConstant.getIntConstant("SUBMIT_IA_BLOCK_TIME");
	
	private ArrayList<String> WIP_JOBSTATE_DE2 = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_DE2");
	private ArrayList<String> JOB_STATE_DE2_APPROVE_SUBMIT = SystemConfig.getArrayListGeneralParam("JOB_STATE_DE2_APPROVE_SUBMIT");
	private ArrayList<String> JOB_STATE_EAPP_SYSTEM = SystemConfig.getArrayListGeneralParam("JOB_STATE_EAPP_SYSTEM");
	private ArrayList<String> JOB_STATE_FULL_FRAUD = SystemConfig.getArrayListGeneralParam("JOB_STATE_FULL_FRAUD");
	private ArrayList<String> WIP_JOBSTATE_CA = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_CA");
	private ArrayList<String> WIP_JOBSTATE_VT = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_VT");
	private ArrayList<String> JOB_STATE_DV_SAVING_PLUS = SystemConfig.getArrayListGeneralParam("JOB_STATE_DV_SAVING_PLUS");
	private ArrayList<String> WIP_JOBSTATE_FRAUD = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_FRAUD");
	private ArrayList<String> JOB_STATE_KEC_LOWINCOME = SystemConfig.getArrayListGeneralParam("JOB_STATE_KEC_LOWINCOME");
	private String CJD_SOURCE = SystemConfig.getGeneralParam("CJD_SOURCE");
	
	private String ERROR_SUBMIT_CLOSE_INTERVAL_1 = MessageErrorUtil.getText("ERROR_SUBMIT_CLOSE_INTERVAL_1");
	private String ERROR_SUBMIT_CLOSE_INTERVAL_2 = MessageErrorUtil.getText("ERROR_SUBMIT_CLOSE_INTERVAL_2");
	private String ERROR_SUBMIT_APP_WAIT_FOR_APPROVE = MessageErrorUtil.getText("ERROR_SUBMIT_APP_WAIT_FOR_APPROVE");
	private String DECISION_SERVICE_POINT_DV2 =SystemConstant.getConstant("DECISION_SERVICE_POINT_DV2");
	
	public Object processAction() {
		logger.debug("DefaultDecisionDV2.processAction()..");
		DecisionApplication decisionApplication = new DecisionApplication();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		decisionApplication.setDiffRequestFlag(MConstant.FLAG_N);
		decisionApplication.setLowIncomeFlag(MConstant.FLAG_N);
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		DecisionServiceRequestDataM decisionRequest = new DecisionServiceRequestDataM();
		decisionRequest.setApplicationGroup(applicationGroup);
		decisionRequest.setUserId(userM.getUserName());
		decisionRequest.setRoleId(FormControl.getFormRoleId(request));
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		decisionRequest.setTransactionId(transactionId);
		String decisionAction = applicationGroup.getDecisionAction();
		String formAction = applicationGroup.getFormAction();
		String jobstate = applicationGroup.getJobState();
		logger.debug("decisionAction : "+decisionAction);
		logger.debug("formAction : "+formAction);
		logger.debug("jobstate : "+jobstate);
		if(DECISION_ACTION_CANCEL.equals(decisionAction)||WorkflowAction.CANCEL_APPLICATION.equals(formAction)){
			return decisionApplication;
		}
		try{
			String buttonAction = request.getParameter("buttonAction");
			if(BUTTON_ACTION_SUBMIT.equals(buttonAction)) {
				String appGroupNo = applicationGroup.getApplicationGroupNo();
				String idNo = "";
				ArrayList<String> products = applicationGroup.getProducts();
				if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
				} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
				}
				
				logger.debug("appGroupNo = " + appGroupNo);
				logger.debug("idNo = " + idNo);
				logger.debug("products = " + products.toString());
				
				UtilityDAO utilDao = new UtilityDAOImpl();
				utilDao.deleteOldSubmitIATimestamp(appGroupNo, idNo, SUBMIT_IA_BLOCK_TIME);
				try {	
					utilDao.insertSubmitIATimestamp(appGroupNo, idNo);

					ArrayList<String> jobStates = new ArrayList<>();
					jobStates.addAll(WIP_JOBSTATE_DE2);
					jobStates.addAll(JOB_STATE_DE2_APPROVE_SUBMIT);
					jobStates.addAll(JOB_STATE_EAPP_SYSTEM);
					jobStates.addAll(JOB_STATE_FULL_FRAUD);
					jobStates.addAll(WIP_JOBSTATE_CA);
					jobStates.addAll(WIP_JOBSTATE_VT);
					jobStates.addAll(JOB_STATE_DV_SAVING_PLUS);
					jobStates.addAll(WIP_JOBSTATE_FRAUD);
					jobStates.addAll(JOB_STATE_KEC_LOWINCOME);
					if(utilDao.isAppWaitForApprove(idNo, appGroupNo, jobStates, products, CJD_SOURCE)) {
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
						decisionApplication.setResultDesc(ERROR_SUBMIT_APP_WAIT_FOR_APPROVE);
						DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
						return decisionApplication;
					}
				} catch(Exception e) {
					if(e instanceof DuplicateKeyException) {
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
						decisionApplication.setResultDesc(ERROR_SUBMIT_CLOSE_INTERVAL_1 + SUBMIT_IA_BLOCK_TIME + ERROR_SUBMIT_CLOSE_INTERVAL_2);
						return decisionApplication;
					} else {
						throw e;
					}
				}
			}
			
			Map<String, String> verifyDV = DecisionApplicationUtil.verifyDV(applicationGroup,userM);
			
			String overSLAFlag = verifyDV.get("OVER_SLA");
			String cusVerCounter = verifyDV.get("CUS_VER_COUNTER");
			String waitStatus = DecisionApplicationUtil.requestWaitStatus(applicationGroup.getPersonalInfos());// Verify Wait
			//String overSLAFlag = DecisionApplicationUtil.verifyDVOverSLA(applicationGroup,userM);
			String incomeFlowAction = WorkflowAction.getIncomeFlowAction(applicationGroup);
			boolean isKPL = KPLUtil.isKPL(applicationGroup);
			boolean isKEC = DecisionApplicationUtil.isKEC(applicationGroup);
			boolean isLowIncome = DecisionApplicationUtil.isLowIncome(applicationGroup);
			boolean isCancelAppLowIncome = DecisionApplicationUtil.isCancelAppLowIncome(applicationGroup);
			logger.debug("waitStatus : " + waitStatus);
			logger.debug("overSLAFlag : " + overSLAFlag);
			logger.debug("incomeFlowAction : " + incomeFlowAction);	
			logger.debug("cusVerCounter : " + cusVerCounter);
			logger.debug("isKEC : " + isKEC);
			logger.debug("isLowIncome : " + isLowIncome);
			logger.debug("isLowIncome : " + (!SystemConstant.lookup("JOB_STATE_EXCEPTION_VERIFY_INCOME",jobstate)
					||!ApplicationGroupDataM.DecisionLog.REQUIRE_VERIFY_INCOME.equals(incomeFlowAction)));
			if(Util.empty(waitStatus)&&Util.empty(overSLAFlag)
					&&(!SystemConstant.lookup("JOB_STATE_EXCEPTION_VERIFY_INCOME",jobstate)
							||!ApplicationGroupDataM.DecisionLog.REQUIRE_VERIFY_INCOME.equals(incomeFlowAction)))
			{
					String responseResult = "";
					boolean skipCallDecision = (isSavingPlus(applicationGroup) ||  isLowIncome);
					if(skipCallDecision)
					{
						logger.debug("Skip calling ODM in case CA approve and sent back to DV for input savingPlus account no.");
						responseResult = DecisionApplicationUtil.ResultCode.SUCCESS;
					}
					else
					{
						DecisionServiceResponse decisionResponse = processDecisionEDV2(decisionRequest);
						responseResult = decisionResponse.getResultCode();
						logger.debug("decisionResponseResult : "+responseResult);
						decisionApplication.setResultCode(responseResult);	
						decisionApplication.setResultDesc(decisionResponse.getResultDesc());
						decisionApplication.setErrorType(decisionResponse.getErrorType());
					}
					if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
						String docCompletedFlag = getDocCompleteFlag(applicationGroup.getPersonalInfos());
						logger.debug("docCompletedFlag : "+docCompletedFlag);
						if(MConstant.FLAG.NO.equals(docCompletedFlag))
						{
							decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
							decisionApplication.setResultDesc(MessageErrorUtil.getText("ERROR_DOCUMENT_COMPLETE_FLAG"));
							decisionApplication.setDocCompleteFlag(MConstant.FLAG.NO);
						}
						else
						{
							if(isKPL)
							{
								//Check DIFF_REQUEST_RESULT if == '03' cancel application
								int cancelAppCount = processCancelApplication(applicationGroup);
								
								if(cancelAppCount == 0) //Do if application DIFF_REQUEST_RESULT != '03'
								{
									boolean diffRequestFlag = false;
									ApplicationDataM kplApp = KPLUtil.getKPLApplication(applicationGroup);
									//Skip diffRequestCheck if not call ODM DV2 beforehand
									if(!skipCallDecision)
									{
										diffRequestFlag = DecisionApplicationUtil.diffRequestFlag(applicationGroup, ROLE_DV);
									}
									
									logger.debug("DecisionDV2 - diffRequestFlag : " + diffRequestFlag);
									if(diffRequestFlag)
									{
										VerifyUtil.setVerificationResult(applicationGroup, VALIDATION_STATUS_REQUIRED);
										VerifyUtil.VerifyResult.setRequiredVerifyCustomer(applicationGroup);								
										
										kplApp.setRecommendDecision(kplApp.getPreviousRecommendDecision());
										kplApp.setFinalAppDecision(DecisionServiceUtil.getFinalAppDiffReq(kplApp.getPreviousRecommendDecision()));
										applicationGroup.setDecisionAction(null);
										
										//Stay at same page - DECISION_DV2AfterDecisionActionJS handle this with diffRequestFlag
										logger.debug("Set DiffRequestFlag of decisionApplication to Y" );
										decisionApplication.setDiffRequestFlag(MConstant.FLAG_Y);
										
										//manual remove table lock submit timestamp
										DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
									}
									//Saving Plus Flag Logic
									else if(kplApp.getRecommendDecision().equals(FINAL_APP_DECISION_APPROVE)
									   && KPLUtil.isSavingPlus(kplApp.getSavingPlusFlag()))
									{	
										if(!KPLUtil.haveKbankSavingDoc(applicationGroup))
										{
											logger.debug("SavingPlusFlag checked + not have KbankSavingDoc = SENT TO FU");
											KPLUtil.addFollowSavingPlusDocReason(applicationGroup); //create SavingPlus follow-up detail
											applicationGroup.setDecisionAction("FUSP");
											decisionApplication.setSavingPlusFlag(MConstant.FLAG.ACTIVE);
										}
										else
										{
											LoanDataM kplLoan = KPLUtil.getKPLLoanDataM(applicationGroup);
											if(kplLoan != null && KPLUtil.isPaymentAccountNoBlank(applicationGroup, kplLoan))
											{
												logger.debug("DV2 - SavingPlusFlag checked + have KbankSavingDoc + Blank account no. = Warn to enter Account No.");
												decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
												decisionApplication.setResultDesc("Please input saving account no.");
											}
										}
									}
									//Save Application
									ORIGServiceProxy.getApplicationManager().saveApplication(applicationGroup, userM);
								}
							}else if(isKEC){
								//Check paymentMethod = '03' cancel application
								if(isCancelAppLowIncome){
									processCancelApplicationLowIncome(applicationGroup, userM);
								}else{
									boolean lowIncomeFlag = false;
									if(!skipCallDecision)
									{
										lowIncomeFlag = DecisionApplicationUtil.foundLowIncomeFlag(applicationGroup);
									}
									
									logger.debug("DecisionEDV2 - lowIncomeFlag : " + lowIncomeFlag);
									if(lowIncomeFlag){
										VerifyUtil.setVerificationResult(applicationGroup, VALIDATION_STATUS_REQUIRED);
										VerifyUtil.VerifyResult.setRequiredVerifyCustomer(applicationGroup);								
	
										decisionApplication.setLowIncomeFlag(MConstant.FLAG_Y);
										
										// update jobstate dv kec lowincome
										processDecisionAction(decisionApplication, applicationGroup);
									
										//Save Application
										ORIGServiceProxy.getApplicationManager().saveApplication(applicationGroup, userM);
										
										//manual remove table lock submit timestamp
										DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
									}
								}
							}
						}
						decisionApplication.setRejectFlag(applicationGroup.getDecisionAction());
						
					} else {
						/* change to use method from DecisionApplicationUtil						
  						String idNo = "";
						if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
							idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
						} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
							idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
						}
						UtilityDAO utilDao = new UtilityDAOImpl();
						utilDao.deleteErrorSubmitIATimestamp(idNo);
						*/
						DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
					}
			}else if(!Util.empty(overSLAFlag))
			{
				defaultOverSLAFlag(applicationGroup, overSLAFlag);
				DecisionServiceResponse decisionResponse = processDecisionEDC(decisionRequest);
				String responseResult = decisionResponse.getResultCode();
				logger.debug("responseResult : "+responseResult);
				decisionApplication.setResultCode(responseResult);	
				decisionApplication.setResultDesc(decisionResponse.getResultDesc());
				decisionApplication.setErrorType(decisionResponse.getErrorType());
			}
			//DecisionApplicationUtil.VerificationType.WAIT_CUSTOMER
			if(!Util.empty(cusVerCounter)&&!Util.empty(waitStatus)){
				if(waitStatus.contains(DecisionApplicationUtil.VerificationType.WAIT_CUSTOMER)){
					Integer cusVerCounterNum=Integer.valueOf(cusVerCounter);
					if(cusVerCounterNum==2){
						NotificationRequest notificationRequest = new NotificationRequest();
						notificationRequest.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						notificationRequest.setSendTime("DV");
						sendSms(notificationRequest);
					}
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		logger.debug("formAction : "+applicationGroup.getFormAction());		
		logger.debug("decisionApplication : "+ decisionApplication.getDiffRequestFlag());
		return decisionApplication;
	}
	
	private boolean isSavingPlus(ApplicationGroupDataM applicationGroup){
		boolean curDecisionAP = DecisionApplicationUtil.isKPLDecisionAP(applicationGroup);
		boolean isKPL = KPLUtil.isKPL(applicationGroup);
		
		if(isKPL && curDecisionAP){
			return true;
		}
		return false;
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
 
	public DecisionServiceResponse  processDecisionEDV2(DecisionServiceRequestDataM decsionRequest){		
		DecisionServiceResponse responseData = new DecisionServiceResponse();
		try {
			decsionRequest.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_EDV2);
			responseData = DecisionApplicationUtil.requestDecisionService(decsionRequest);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			responseData.setResultCode(DecisionServiceResponseDataM.Result.SYSTEM_EXCEPTION);
			responseData.setResultDesc(e.getLocalizedMessage());
		}
		return  responseData;
	}
	public DecisionServiceResponse processDecisionEDC(DecisionServiceRequestDataM decsionRequest){
		DecisionServiceResponse  decisionResponse = new DecisionServiceResponse();
		try {
			decsionRequest.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_EDC);
			decisionResponse = DecisionApplicationUtil.requestDecisionService(decsionRequest);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			decisionResponse.setResultCode(DecisionServiceResponseDataM.Result.SYSTEM_EXCEPTION);
			decisionResponse.setResultDesc(DecisionServiceResponseDataM.ResultDesc.ERROR);
		}
		return decisionResponse;
	}
	
	public int processCancelApplication(ApplicationGroupDataM applicationGroup){
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
		return countAppCancel;
	}

	private String getDocCompleteFlag(ArrayList<PersonalInfoDataM>    uloPersonalInfos){
		if(null!=uloPersonalInfos){
			for(PersonalInfoDataM personalInfo :uloPersonalInfos){
				if(null!=personalInfo.getVerificationResult() && MConstant.FLAG.NO.equals(personalInfo.getVerificationResult().getDocCompletedFlag())){
					return MConstant.FLAG.NO;
				}
			}
		}

		return MConstant.FLAG.YES;
	}
	
	 public NotificationResponse sendSms(NotificationRequest notificationRequest){
			NotificationResponse response = new NotificationResponse(); 
			try{
				logger.debug("applicationGroupId : "+notificationRequest.getApplicationGroupId());
				logger.debug("saleType : "+notificationRequest.getSaleType());
				logger.debug("sendTime : "+notificationRequest.getSendTime());
				logger.debug("status : "+notificationRequest.getStatus());
				NotifyResponse notifyResponse = NotifyController.notify(mappingDVNotifyRequest(notificationRequest), null);	
				logger.debug(notifyResponse);
				if(InfBatchConstant.ResultCode.SUCCESS.equals(notifyResponse.getStatusCode())){
					response.setStatusCode(ServiceResponse.Status.SUCCESS);
				}else if(InfBatchConstant.ResultCode.WARNING.equals(notifyResponse.getStatusCode())){
					response.setStatusCode(ServiceResponse.Status.WARNING);
					response.setStatusDesc(notifyResponse.getStatusDesc());
				}else{
					response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
					response.setStatusDesc(notifyResponse.getStatusDesc());
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
				response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				response.setStatusDesc(e.getLocalizedMessage());
			}			
			logger.debug(response);
			return response;
	}
	
	private NotifyRequest mappingDVNotifyRequest(NotificationRequest notificationRequest) throws Exception{
		NotifyRequest notifyRequest  = new NotifyRequest();
		NotificationInfoRequestDataM notificationInfo = new NotificationInfoRequestDataM();
		notificationInfo.setApplicationGroupId(notificationRequest.getApplicationGroupId());
		notificationInfo.setApplicationStatus(notificationRequest.getStatus());
		notificationInfo.setSendingTime(notificationRequest.getSendTime());
		notificationInfo.setSaleType(notificationRequest.getSaleType());
		notificationInfo.setLifeCycle(ORIGDAOFactory.getApplicationGroupDAO().getLifeCycle(notificationRequest.getApplicationGroupId()));
		notifyRequest.setNotifyId(NotifyConstant.Notify.NOTIFY_NOTIFICATION_DV);
		notifyRequest.setRequestObject(notificationInfo);
		return notifyRequest;
	}
	public int processCancelApplicationLowIncome(ApplicationGroupDataM applicationGroup, UserDetailM userM){
		logger.debug("processCancelApplication..");
		String CANCEL_REASON_CUSTOMER_DENIED_DEPOSIT_ACCOUNT = SystemConstant.getConstant("CANCEL_REASON_CUSTOMER_DENIED_DEPOSIT_ACCOUNT");
		String REASON_TYPE_CANCEL = SystemConstant.getConstant("REASON_TYPE_CANCEL");
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		int countAppCancel = 0;
		if(!Util.empty(applications)){
			for(ApplicationDataM applicationItem:applications){
				logger.debug("applicationItem.getFinalAppDecision() >> "+applicationItem.getFinalAppDecision());
				applicationItem.setFinalAppDecision(RECOMMEND_DECISION_CANCEL);	
				if(RECOMMEND_DECISION_CANCEL.equals(applicationItem.getFinalAppDecision())){
					countAppCancel++;
				}				
			}
		}
		if(countAppCancel == applications.size()){
			applicationGroup.setFormAction(WorkflowAction.CANCEL_APPLICATION);
		}
		
		ReasonDataM reasonM = new ReasonDataM();
		reasonM.setCreateBy(userM.getUserName());
		reasonM.setUpdateBy(userM.getUserName());
		reasonM.setReasonCode(CANCEL_REASON_CUSTOMER_DENIED_DEPOSIT_ACCOUNT);
		reasonM.setReasonOthDesc("");
		reasonM.setReasonType(REASON_TYPE_CANCEL);
		reasonM.setRemark("");
		
		applicationGroup.setReason(reasonM);
		
		return countAppCancel;
	}
	
	private void processDecisionAction(DecisionApplication decisionApplication,ApplicationGroupDataM applicationGroup){
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		decisionApplication.setDecisionId(DECISION_SERVICE_POINT_DV2);	
		
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		logger.debug("BPM_HOST >> "+BPM_HOST);
		logger.debug("BPM_PORT >> "+BPM_PORT);	
		try{
			Integer instanceId = applicationGroup.getInstantId();
			logger.debug("instanceId >> "+instanceId);
			ORIGForm.auditForm(request);
			ORIGServiceProxy.getApplicationManager().saveApplication(applicationGroup, userM);
			WorkflowManager workflow = new WorkflowManager(BPM_HOST,BPM_PORT);
			WorkflowResponseDataM response = workflow.updateDVJobStateCaseKecLowIncome(instanceId, userM.getUserName(), applicationGroup.getApplicationGroupId());
			String workflowResult = response.getResultCode();
			logger.debug("workflowResult >> "+workflowResult);
			if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(workflowResult)){
				String jobState = response.getJobState();
				String status = response.getStatus();
				logger.debug("jobState >> "+jobState);
				logger.debug("status >> "+status);
				applicationGroup.setJobState(jobState);
				applicationGroup.setApplicationStatus(status);
				
			}else{
				decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
	}
}
	
