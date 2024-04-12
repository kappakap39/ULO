package com.eaf.service.rest.controller.postapproval;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.postapproval.CardLinkAction;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.pega.UpdateApprovalStatus;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.flp.eapp.util.CardLinkAddressUtil;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoRequestDataM;
import com.eaf.inf.batch.ulo.pega.InfBatchUpdateApprovalStatus;
import com.eaf.inf.batch.ulo.pega.model.CSVContentDataM;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAOImpl;
import com.eaf.orig.ulo.app.dao.OrigComparisionDataDAO;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.postapproval.action.PostApprovalAction;
import com.eaf.orig.ulo.app.postapproval.action.PostApprovalAction.PostApprovalActionResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusData;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequest;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterController;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.manual.NotifyFLPFinalApproveResultServiceProxy;
import com.eaf.service.module.manual.UpdateApprovalStatusServiceProxy;
import com.eaf.service.module.model.NotifyFLPFinalApproveResultResponseM;
import com.eaf.service.module.model.NotifyFLPFinalApproveServiceProxyRequest;
import com.eaf.service.module.model.PostApprovalDataM;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;

@RestController
@RequestMapping("/service/PostApproval")
public class PostApprovalController {
	
	private static transient Logger logger = Logger.getLogger(PostApprovalController.class);
	private String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	private String DECISION_ACTION_APPROVE = SystemConstant.getConstant("DECISION_ACTION_APPROVE");
	private String DECISION_ACTION_REJECT = SystemConstant.getConstant("DECISION_ACTION_REJECT");
	private String NOTI_SEND_TIME_DE2 = SystemConstant.getConstant("NOTI_SEND_TIME_DE2");
	private String NOTI_SEND_TIME_FRAUDSYS = SystemConstant.getConstant("NOTI_SEND_TIME_FRAUDSYS");
	private String NOTI_STATUS_APPROVE = SystemConstant.getConstant("NOTI_STATUS_APPROVE");
	private String NOTI_STATUS_REJECT = SystemConstant.getConstant("NOTI_STATUS_REJECT");
	private String NOTI_SALE_TYPE = SystemConstant.getConstant("NOTI_SALE_TYPE");
	private String FINAL_APP_DECISION_APPROVE = SystemConstant.getConstant("FINAL_APP_DECISION_APPROVE");
	private String DECISION_ACTION_DE2 = SystemConstant.getConstant("DECISION_ACTION_DE2");
	private ArrayList<String> E_APP_KPL = SystemConstant.getArrayListConstant("E_APP_KPL");
	private String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	private String RECOMMEND_DECISION_APPROVED = SystemConstant.getConstant("RECOMMEND_DECISION_APPROVED");
	
	@RequestMapping(value = "/submit", method = {RequestMethod.POST, RequestMethod.PUT})
    public @ResponseBody ResponseEntity<ProcessResponse> send(@RequestBody PostApprovalDataM postApprovalRequest) {
		logger.debug("========== START ==========");
		String errorMsg = "";
		ProcessResponse response = new ProcessResponse();
		response.setStatusCode(ServiceResponse.Status.SUCCESS);
		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		ServiceCenterController serviceController = new ServiceCenterController();
		
		String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
		
		String applicationGroupNo = postApprovalRequest.getApplicationGroupNo();
		String recommendDecision = postApprovalRequest.getRecommendDecision();
		String userId = SystemConstant.getConstant("SYSTEM_USER");
		
		logger.debug("applicationGroupNo = " + applicationGroupNo);
		logger.debug("recommendDecision = " + recommendDecision);
			
		OrigApplicationGroupDAOImpl appGroupDAO = new OrigApplicationGroupDAOImpl();
		String applicationGroupId = appGroupDAO.getApplicationGroupIdByApplicationGroupNo(applicationGroupNo);
		
		logger.debug("applicationGroupId = " + applicationGroupId);
		
		ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
			serviceLogRequest.setServiceReqRespId(serviceReqRespId);
			serviceLogRequest.setRefCode(applicationGroupNo);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceDataObject(applicationGroupId);
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.PostApproval);
			serviceLogRequest.setUniqueId(applicationGroupId);
			serviceLogRequest.setUserId(userId);
		serviceController.createLog(serviceLogRequest);
		
		ArrayList<String> validJobStatePostApproval = SystemConstant.getArrayListConstant("VALID_JOBSTATE_POSTAPPROVE");
		
		try {
			
			ApplicationGroupDataM applicationGroup = appGroupDAO.loadOrigApplicationGroupM(applicationGroupId);
			
			if (null != applicationGroup && null != applicationGroup.getJobState() && !Util.empty(validJobStatePostApproval) && validJobStatePostApproval.contains(applicationGroup.getJobState()) ) {
				EAppAction.loadComparisonData(applicationGroup);
				
				String transactionId = serviceController.getTransactionId();
				applicationGroup.setTransactionId(transactionId);
				
				recommendDecision = applicationGroup.getLastDecision();
				logger.debug("recommendDecision = " + recommendDecision);
//				String decisionResultCode = ServiceResponse.Status.SUCCESS;
				applicationGroup.setUserId(applicationGroup.getSourceUserId());
				ProcessActionResponse decisionResponse = EAppAction.requestDecision(applicationGroup, DecisionServiceUtil.DECISION_POINT.DECISION_POINT_POST_APPROVAL, userId);
				String decisionResultCode = decisionResponse.getResultCode();						
				logger.debug("decisionResultCode : "+decisionResultCode);	
				response.setStatusCode(decisionResponse.getResultCode());
				if(ServiceResponse.Status.SUCCESS.equals(decisionResultCode)){
					ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
					
					logger.debug("transactionId = " + applicationGroup.getTransactionId());
					logger.debug("personalInfosSize = " + personalInfos.size());
					
					ProcessActionResponse processActionResponse = new ProcessActionResponse();
					processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
					
					//CIS
					if(!Util.empty(personalInfos)){
						for(PersonalInfoDataM personalInfo : personalInfos){
							String resultCode = this.postApprovalProcess(null, personalInfo,processActionResponse, applicationGroup,true);
							if(!ServiceResponse.Status.SUCCESS.equals(resultCode)){
								response.setStatusCode(processActionResponse.getResultCode());
								response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, processActionResponse.getResultCode(), processActionResponse.getErrorMsg()));
//								return ResponseEntity.ok(response);
							}
						}
					}
					PersonalInfoDataM applicantSort = new PersonalInfoDataM();
					applicantSort.setSortType(PersonalInfoDataM.SORT_TYPE.ASC);
					if(!Util.empty(personalInfos)){
						Collections.sort(personalInfos,applicantSort);
						for(PersonalInfoDataM personalInfo : personalInfos){
							ArrayList<ApplicationDataM> aplications = applicationGroup.getApplications(personalInfo.getPersonalId(), personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
							if(!Util.empty(aplications)){
								Collections.sort(aplications,new ApplicationDataM());
								for(ApplicationDataM application: aplications){
									String resultCode = this.postApprovalProcess(application,null, processActionResponse, applicationGroup,false);
									if(!ServiceResponse.Status.SUCCESS.equals(resultCode)){
										response.setStatusCode(processActionResponse.getResultCode());
										response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, processActionResponse.getResultCode(), processActionResponse.getErrorMsg()));
//										return ResponseEntity.ok(response);
									}
									if(DECISION_ACTION_APPROVE.equals(recommendDecision)){
										if(null!=application.getLoans()){
											for(LoanDataM loan : application.getLoans()){ 
												if(null!=loan.getCashTransfers()){
													for(CashTransferDataM cashTransfer: loan.getCashTransfers()){
														if(null!=cashTransfer.getPercentTransfer()&&null!=loan.getLoanAmt()){
															BigDecimal firstTransferAmount = BigDecimal.ZERO;
															try{
																firstTransferAmount = loan.getLoanAmt().multiply(cashTransfer.getPercentTransfer().divide(new BigDecimal(100)));
															}catch(Exception e){
															}
															cashTransfer.setFirstTransferAmount(firstTransferAmount);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					
					logger.debug("Checkout point 1 ==============================");
					
					ApplicationUtil.setAdditionalService(applicationGroup);
					
					logger.debug("Checkout point 2 ==============================");
					
					ApplicationUtil.defaultCardLinkCustId(applicationGroup);	
					if(APPLICATION_TYPE_INCREASE.equals(applicationGroup.getApplicationType()) && DECISION_ACTION_REJECT.equals(recommendDecision)){
						PersonalAddressUtil.setAddressCardLinkIncrease(applicationGroup);
					}

					CardLinkAction processCardLink = new CardLinkAction();
					processCardLink.processCardlinkAction(applicationGroup);
					
					String postApprovalResult = processActionResponse.getResultCode();
					
					logger.debug("postApprovalResult : " + postApprovalResult);
					if(ServiceResponse.Status.SUCCESS.equals(postApprovalResult)){			
						UpdateApprovalStatusData updateApprovalStatusData = UpdateApprovalStatus.validateUpdateApprovalStatus(applicationGroup);				
						String isClose = updateApprovalStatusData.getIsClose();
						String isVetoEligible = updateApprovalStatusData.getIsVetoEligible();		
						logger.debug("updateApprovalStatusData : " + updateApprovalStatusData);
						if(MConstant.FLAG.NO.equals(isClose) && MConstant.FLAG.YES.equals(isVetoEligible)){
							//Call pega
							ArrayList<ApplicationGroupDataM> applicationGroups = new ArrayList<ApplicationGroupDataM>();
								applicationGroups.add(applicationGroup);
							ArrayList<CSVContentDataM> csvContents = InfBatchUpdateApprovalStatus.mapCSVContent(applicationGroups,isClose,isVetoEligible);
							UpdateApprovalStatusRequest updateApprovalRequest = new UpdateApprovalStatusRequest();
							updateApprovalRequest.setCSVContent(InfBatchUpdateApprovalStatus.getCSVContent(csvContents));					
							String UPDATE_APPROVAL_STATUS_URL = SystemConfig.getProperty("UPDATE_APPROVAL_STATUS_URL");
							ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
							serviceRequest.setServiceId(UpdateApprovalStatusServiceProxy.serviceId);
							serviceRequest.setUserId(userId);
							serviceRequest.setEndpointUrl(UPDATE_APPROVAL_STATUS_URL);
							serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
							serviceRequest.setObjectData(updateApprovalRequest);
							serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());					
							ServiceCenterProxy proxy = new ServiceCenterProxy();
							ServiceResponseDataM serviceResponse = proxy.requestService(serviceRequest,transactionId);
							String updateApprovalStatusResult = serviceResponse.getStatusCode();
							logger.debug("updateApprovalStatusResult >> "+updateApprovalStatusResult);
							processActionResponse.setResultCode(updateApprovalStatusResult);
							if(!ServiceResponse.Status.SUCCESS.equals(updateApprovalStatusResult)){
								response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
								response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, processActionResponse.getResultCode(), processActionResponse.getErrorMsg()));
							}
						}
						
						applicationGroup.setDecisionAction(applicationGroup.getLastDecision());
						
						//check cardlink address
						if(FINAL_APP_DECISION_APPROVE.equals(applicationGroup.getLastDecision())
								&& !APPLICATION_TYPE_INCREASE.equals(applicationGroup.getApplicationType())){
							//if not match set action to de2
							for(PersonalInfoDataM personal : applicationGroup.getPersonalInfos()){
								if(!CardLinkAddressUtil.checkCardlinkAddress(personal.getAddresses())){
									applicationGroup.setDecisionAction(DECISION_ACTION_DE2);
									break;
								}
							}
						}
						
						//if KPL not to DE2
						if(DECISION_ACTION_DE2.equals(applicationGroup.getDecisionAction()) 
								&& applicationGroup.countApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN) > 0){
							applicationGroup.setDecisionAction(applicationGroup.getLastDecision());
						}
						
						// Check if callAction != DE2
						if(!DECISION_ACTION_DE2.equals(applicationGroup.getDecisionAction())) {
							for (PersonalInfoDataM personalInfo : applicationGroup.getPersonalInfos()) {
								for (AddressDataM address : personalInfo.getAddresses()) {
									address.setEditFlag(MConstant.FLAG_Y);
								}
							}
						}
						
						//remove comparison before save
						applicationGroup.removeComparisonField(CompareDataM.SoruceOfData.CIS);
						
						EAppAction.saveApplication(applicationGroup, userId, "POSTAPPROVAL");
						
						//call notification
						NotifyResponse notifyResponse = sendNotification(applicationGroup);
						logger.debug("notifyResponse result : " + notifyResponse.getStatusCode());
//						if(InfBatchConstant.ResultCode.SUCCESS.equals(notifyResponse.getStatusCode())){
//							response.setStatusCode(ServiceResponse.Status.SUCCESS);
//						}else if(InfBatchConstant.ResultCode.WARNING.equals(notifyResponse.getStatusCode())){
//							response.setStatusCode(ServiceResponse.Status.WARNING);
//							response.setData(notifyResponse.getStatusDesc());
//							errorMsg = notifyResponse.getStatusDesc();
//						}else{
//							response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
//							response.setData(notifyResponse.getStatusDesc());
//							errorMsg = notifyResponse.getStatusDesc();
//						}
						
						//process final approval result
						NotifyFLPFinalApproveServiceProxyRequest notifyRequest = new NotifyFLPFinalApproveServiceProxyRequest();
						notifyRequest.setApplicationGroup(applicationGroup);
						notifyRequest.setNotifyTemplates(notifyResponse.getNotifyTemplateList());
						ProcessActionResponse processFinalApprovalResultResponse = requestFinalApproveResult(notifyRequest, userId);
						logger.debug("processFinalApprovalResultResponse result : " + processFinalApprovalResultResponse.getResultCode());
						if(ServiceResponse.Status.SUCCESS.equals(processFinalApprovalResultResponse.getResultCode())){
							response.setStatusCode(ServiceResponse.Status.SUCCESS);
						}else if(InfBatchConstant.ResultCode.WARNING.equals(processFinalApprovalResultResponse.getResultCode())){
							response.setStatusCode(ServiceResponse.Status.WARNING);
							response.setData(processFinalApprovalResultResponse.getErrorMsg());
							errorMsg = processFinalApprovalResultResponse.getErrorMsg();
						}else{
							response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
							response.setData(processFinalApprovalResultResponse.getErrorMsg());
							errorMsg = processFinalApprovalResultResponse.getErrorMsg();
						}
						
						if(ServiceResponse.Status.SUCCESS.equals(response.getStatusCode())){
							if(!Util.empty(applicationGroup.getInstantId())){
								WorkflowResponseDataM workflowResponse = EAppAction.workflowAction(applicationGroup);
								if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(workflowResponse.getResultCode())){
									response.setStatusCode(ServiceResponse.Status.SUCCESS);
								}else{
									response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
									response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, workflowResponse.getErrorCode(), workflowResponse.getErrorMsg()));
								}
							}
							//case reject from eapp
							else{
								String action = "Submit";
								EAppAction.workflowLog(applicationGroup, applicationGroup.getSourceUserId(), action);
							}
						}
					}
				}
				else{
					response.setStatusCode(decisionResultCode);
					response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, decisionResponse.getResultCode(), decisionResponse.getErrorMsg()));
				}
			}
			else{
				errorMsg = "JobState is not ready";
				response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, ServiceResponse.Status.SYSTEM_EXCEPTION, errorMsg));
			}
		} catch(Exception e) {
			e.printStackTrace();
			response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			response.setErrorData(EAppAction.error(e));
			errorMsg = e.getMessage();
		}
		
		ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
		serviceLogResponse.setServiceReqRespId(serviceReqRespId);
		serviceLogResponse.setRefCode(applicationGroupNo);
		serviceLogResponse.setActivityType(ServiceConstant.OUT);
		serviceLogResponse.setServiceDataObject(response);
		serviceLogResponse.setServiceId(ServiceConstant.ServiceId.PostApproval);
		serviceLogResponse.setUniqueId(applicationGroupId);
		serviceLogResponse.setRespCode(response.getStatusCode());
		serviceLogResponse.setUserId(userId);
		serviceLogResponse.setErrorMessage(errorMsg);
		serviceController.createLog(serviceLogResponse);
		
		return ResponseEntity.ok(response);
	}
	
	private  String  postApprovalProcess(ApplicationDataM  aplicationInfo, PersonalInfoDataM personalInfo,ProcessActionResponse processActionResponse, ApplicationGroupDataM applicationGroup ,boolean isPostApprovalCIS){
		String postApprovalResult="";
		try {
			logger.debug("isPostApprovalCIS : "+isPostApprovalCIS);
			PostApprovalAction approvalProcess = new PostApprovalAction(applicationGroup,aplicationInfo,personalInfo);
			PostApprovalActionResult postApprovalActionResult = approvalProcess.processPostApprvalAction(isPostApprovalCIS);
			postApprovalResult = postApprovalActionResult.getStatusCode();
			logger.debug("postApprovalResult : "+postApprovalResult);
			if(!ServiceResponse.Status.SUCCESS.equals(postApprovalResult)){
				processActionResponse.setResultCode(postApprovalResult);
				ErrorData errorData = postApprovalActionResult.getErrorData();
				if(Util.empty(errorData)){
					errorData = new ErrorData();
				}
				processActionResponse.setResultDesc(errorData.getErrorInformation());
				processActionResponse.setErrorMsg(errorData.getErrorInformation());
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setResultDesc(e.getLocalizedMessage());
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		return  postApprovalResult;
	}
	
	private NotifyResponse sendNotification(ApplicationGroupDataM applicationGroup){
		String decision = applicationGroup.getLastDecision();
		String notiStatus = "";
		String notiSendTime = NOTI_SEND_TIME_DE2;
		if(DECISION_ACTION_APPROVE.equals(decision)){
			notiStatus = NOTI_STATUS_APPROVE;
			//if kpl approve send time will be FRAUDSYS
//			if(E_APP_KPL.contains(applicationGroup.getApplicationTemplate())){
//				notiSendTime = NOTI_SEND_TIME_FRAUDSYS;
//			}
			//change logic check kpl approve
			ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationProductLifeCyclesByFinalAppDecision(PRODUCT_K_PERSONAL_LOAN, RECOMMEND_DECISION_APPROVED);
			if(!Util.empty(applications)){
				notiSendTime = NOTI_SEND_TIME_FRAUDSYS;
			}
		}
		else if(DECISION_ACTION_REJECT.equals(decision)){
			notiStatus = NOTI_STATUS_REJECT;
		}
		
		NotificationInfoRequestDataM notificationInfo = new NotificationInfoRequestDataM();
		notificationInfo.setApplicationGroupId(applicationGroup.getApplicationGroupId());
		notificationInfo.setApplicationStatus(notiStatus);
		notificationInfo.setSendingTime(notiSendTime);
		notificationInfo.setSaleType(NOTI_SALE_TYPE);
		notificationInfo.setLifeCycle(applicationGroup.getLifeCycle());
		
		ArrayList<String> templateList = new ArrayList<String>();
		templateList.add(TemplateBuilderConstant.TemplateType.EMAIL);
		templateList.add(TemplateBuilderConstant.TemplateType.SMS);
		
		NotifyResponse notifyResponse = EAppAction.notificationProcess(notificationInfo, templateList);
		return notifyResponse;
	}
	
	public static ProcessActionResponse requestFinalApproveResult(NotifyFLPFinalApproveServiceProxyRequest notifyRequest, String userId){
		String URL = SystemConfig.getProperty("NOTIFY_FLP_FINAL_APPROVE_RESULT");
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			ApplicationGroupDataM applicationGroup = notifyRequest.getApplicationGroup();
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(NotifyFLPFinalApproveResultServiceProxy.serviceId);
				serviceRequest.setUserId(userId);
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setObjectData(notifyRequest);
				
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
			logger.debug("requestFinalApproveResult response  : " + serivceResponse.getStatusCode());
			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
				NotifyFLPFinalApproveResultResponseM notifyResponse =(NotifyFLPFinalApproveResultResponseM)serivceResponse.getObjectData();
				if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
					processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
				}else{
					processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR+":when  get object data");
				}
			}else{
				processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR);
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
}