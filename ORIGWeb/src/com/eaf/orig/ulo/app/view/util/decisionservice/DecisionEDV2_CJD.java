package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ava.flp.cjd.model.CJDResponseServiceProxyRequest;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.notify.cont.NotifyConstant;
import com.eaf.core.ulo.service.notify.controller.NotifyController;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoRequestDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.CJDResponseUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.orig.ulo.model.notification.NotificationRequest;
import com.eaf.orig.ulo.model.notification.NotificationResponse;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.workflow.handle.WorkflowAction;

public class DecisionEDV2_CJD extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionEDV2.class);
	private String DECISION_ACTION_CANCEL =SystemConstant.getConstant("DECISION_ACTION_CANCEL");
	private String RECOMMEND_DECISION_CANCEL =SystemConstant.getConstant("RECOMMEND_DECISION_CANCEL");
	private	String CONTACT_RESULT_CANCEL = SystemConstant.getConstant("CONTACT_RESULT_CANCEL");
	public static String PRODUCT_CODE_KEC = SystemConstant.getConstant("PRODUCT_CODE_KEC");
	
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
			
			Map<String, String> verifyDV = DecisionApplicationUtil.verifyDV(applicationGroup,userM);
			
			String overSLAFlag = verifyDV.get("OVER_SLA");
			String cusVerCounter = verifyDV.get("CUS_VER_COUNTER");
			String waitStatus = DecisionApplicationUtil.requestWaitStatus(applicationGroup.getPersonalInfos());// Verify Wait
			//String overSLAFlag = DecisionApplicationUtil.verifyDVOverSLA(applicationGroup,userM);
			String incomeFlowAction = WorkflowAction.getIncomeFlowAction(applicationGroup);
			boolean isKEC = DecisionApplicationUtil.isKEC(applicationGroup);
			boolean isLowIncome = DecisionApplicationUtil.isLowIncome(applicationGroup);
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
						DecisionServiceResponse decisionResponse = processDecisionINCOME_CALCULATION(decisionRequest);
						responseResult = decisionResponse.getResultCode();
						logger.debug("decisionResponseResult : "+responseResult);
						decisionApplication.setResultCode(responseResult);	
						decisionApplication.setResultDesc(decisionResponse.getResultDesc());
						decisionApplication.setErrorType(decisionResponse.getErrorType());
					boolean isCJD = ApplicationUtil.cjd(applicationGroup.getSource());
					logger.debug("call cjd : "+responseResult);
					if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
							String docCompletedFlag = getDocCompleteFlag(applicationGroup.getPersonalInfos());
							logger.debug("docCompletedFlag : "+docCompletedFlag);
							if(MConstant.FLAG.NO.equals(docCompletedFlag))
							{
								decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
								decisionApplication.setResultDesc(MessageErrorUtil.getText("ERROR_DOCUMENT_COMPLETE_FLAG"));
								decisionApplication.setDocCompleteFlag(MConstant.FLAG.NO);
							}else{
								if(isCJD){
									//process cjd response
									String userId = SystemConstant.getConstant("SYSTEM_USER");
									CJDResponseServiceProxyRequest cjdRequest = new CJDResponseServiceProxyRequest();
									cjdRequest.setApplicationGroup(applicationGroup);
									cjdRequest.setCompleteFlag("Y");
									
									ProcessActionResponse processCJDResponse = CJDResponseUtil.requestCJDResponse(cjdRequest, userId);
									logger.debug("processFinalApprovalResultResponse result : " + processCJDResponse.getResultCode());
									if(ServiceResponse.Status.SUCCESS.equals(processCJDResponse.getResultCode())){
										decisionApplication.setResultCode(ServiceResponse.Status.SUCCESS);				
									}else{
										decisionApplication.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
									}
								}
							}
							
							decisionApplication.setRejectFlag(applicationGroup.getDecisionAction());
						
					} else {

//						DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
					}
			}
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

	public DecisionServiceResponse  processDecisionINCOME_CALCULATION(DecisionServiceRequestDataM decsionRequest){		
		DecisionServiceResponse responseData = new DecisionServiceResponse();
		try {
			decsionRequest.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME_CALCULATION_SERVICE);
			responseData = DecisionApplicationUtil.requestDecisionService(decsionRequest);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			responseData.setResultCode(DecisionServiceResponseDataM.Result.SYSTEM_EXCEPTION);
			responseData.setResultDesc(e.getLocalizedMessage());
		}
		return  responseData;
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
}
	
