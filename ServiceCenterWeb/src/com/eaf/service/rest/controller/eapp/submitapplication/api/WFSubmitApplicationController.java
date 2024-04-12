package com.eaf.service.rest.controller.eapp.submitapplication.api;

import java.sql.Date;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ava.flp.eapp.iib.deserializer.DateDeserializer;
import com.ava.flp.eapp.iib.serializer.DateSerializer;
import com.ava.flp.eapp.submitapplication.model.ESubmitApplicationObject;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.flp.eapp.util.EAppUtil;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.doc.CreateDocSetRequest;
import com.eaf.orig.ulo.model.eapp.WFSubmitApplicationM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ComplexClassExclusionStrategy;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterController;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.manual.ActiveMQSendQueueProxy;
import com.eaf.service.module.model.ActiveMQSendQueueRequestDataM;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;

@RestController
@RequestMapping("/service/FLS-SubmitApplication")
public class WFSubmitApplicationController {

	private static Logger logger = Logger.getLogger(WFSubmitApplicationController.class);
	private String DECISION_ACTION_IM = SystemConstant.getConstant("DECISION_ACTION_IM");
	private String WM_ACTION_IM = SystemConstant.getConstant("WM_ACTION_IM");
	private String CALL_ACTION_REJECTED = SystemConstant.getConstant("CALL_ACTION_REJECTED");
	private String CALL_ACTION_DV = SystemConstant.getConstant("CALL_ACTION_DV");
	private String CALL_ACTION_FRAUD = SystemConstant.getConstant("CALL_ACTION_FRAUD");
	private String PRIORITY = SystemConstant.getConstant("PRIORITY");
	private String PEGA_EVENT_FLAG = SystemConstant.getConstant("PEGA_EVENT_FLAG");
	private String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
	private String CALL_ACTION_PROCEED = SystemConstant.getConstant("CALL_ACTION_PROCEED");
	private String CALL_ACTION_PRE_APPROVED = SystemConstant.getConstant("CALL_ACTION_PRE_APPROVED");
	
	@RequestMapping(value="/IM", method = {RequestMethod.POST, RequestMethod.PUT})
    public @ResponseBody ResponseEntity<ProcessResponse> submitProceed(@RequestBody WFSubmitApplicationM submitApplicationM){
		ProcessResponse response = new ProcessResponse();
		String errorMsg = "";
		String applicationGroupId = submitApplicationM.getApplicationGroupId();
		String applicationGroupNo = submitApplicationM.getApplicationGroupNo();
		String userId = submitApplicationM.getUserId();
		
		logger.debug("applicationGroupId >> "+applicationGroupId);
		logger.debug("applicationGroupNo >> "+applicationGroupNo);
		
		if(Util.empty(userId)){
			userId = SystemConstant.getConstant("SYSTEM_USER");
		}
		logger.debug("userId >> "+userId);
		
		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		ServiceCenterController serviceController = new ServiceCenterController();
		ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
			serviceLogRequest.setServiceReqRespId(serviceReqRespId);
			serviceLogRequest.setRefCode(applicationGroupNo);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceDataObject(submitApplicationM);
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.ESubmitApplicationIM);
			serviceLogRequest.setUniqueId(applicationGroupId);
			serviceLogRequest.setUserId(userId);
		serviceController.createLog(serviceLogRequest);
		
		ArrayList<String> validJobStateProceed = SystemConstant.getArrayListConstant("VALID_JOBSTATE_PROCEED");

		try {
//			String applicationGroupId = ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupIdByApplicationGroupNo(applicationGroupNo);
			ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO().loadOrigApplicationGroupM(applicationGroupId);
			
			if (null != applicationGroup && null != applicationGroup.getJobState() && !Util.empty(validJobStateProceed) && validJobStateProceed.contains(applicationGroup.getJobState()) ) {
				String SetID = applicationGroup.getApplicationGroupNo();
	    		String FormQRCode = CacheControl.getName(CACHE_TEMPLATE, "TEMPLATE_ID", applicationGroup.getApplicationTemplate(), "TEMPLATE_CODE");
	    		String FormatTypeNo = EAppUtil.getFormatType(FormQRCode); //FORMAT_TYPE_NO;
	    		String ITPurpose = EAppUtil.getItPurpose(FormQRCode);//IT_PURPOSE;
	    		String FormLocation = EAppUtil.getFormLocation(FormQRCode);//FORM_LOCATION;
	    		String ProductTypeNo = EAppUtil.getProductTypeNo(FormQRCode);
	    		String FormTypeNo = EAppUtil.getFormTypeNo(FormQRCode);
	    		String FormVersionNo = EAppUtil.getFormVersion(FormQRCode);
	    		String IPAddress = "";
	    		String EventFlag = PEGA_EVENT_FLAG;
	    		String Priority = PRIORITY;
	    		String WebScanUser = applicationGroup.getSourceUserId();
	    		String DocumentChronicleID = applicationGroup.getLhRefId();
	    		String DocumentType = "";
	    		String DocumentName = "";
	    		String DocumentFormat = "";
				
				CreateDocSetRequest createDocSetRequest = new CreateDocSetRequest();
				createDocSetRequest.setSetID(SetID);
				createDocSetRequest.setFormQRCode(FormQRCode);
				createDocSetRequest.setFormatTypeNo(FormatTypeNo);
				createDocSetRequest.setITPurpose(ITPurpose);
				createDocSetRequest.setFormLocation(FormLocation);
				createDocSetRequest.setProductTypeNo(ProductTypeNo);
				createDocSetRequest.setFormTypeNo(ProductTypeNo);
				createDocSetRequest.setFormTypeNo(FormTypeNo);
				createDocSetRequest.setFormVersionNo(FormVersionNo);
				createDocSetRequest.setIPAddress(IPAddress);
				createDocSetRequest.setEventFlag(EventFlag);
				createDocSetRequest.setPriority(Priority);
				createDocSetRequest.setWebScanUser(WebScanUser);
				if(!Util.empty(DocumentChronicleID)){
					createDocSetRequest.setDocumentChronicleID(DocumentChronicleID);
				}
//		    	createDocSetRequest.setDocumentCreationDate(FormatUtil.toString(ApplicationDate.getTimestamp()));
                //change format to time millisecond
                createDocSetRequest.setDocumentCreationDate(String.valueOf(ApplicationDate.getTimestamp().getTime()));
				createDocSetRequest.setDocumentType(DocumentType);
				createDocSetRequest.setDocumentName(DocumentName);
				createDocSetRequest.setDocumentFormat(DocumentFormat);
				
				String contentMsg = "";
				Gson gson = new GsonBuilder().setExclusionStrategies(new ComplexClassExclusionStrategy()).create();
				try{
					contentMsg = gson.toJson(createDocSetRequest);
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					if(null != createDocSetRequest){
						contentMsg = createDocSetRequest.toString();
					}
				}
				
//				WorkManagerRequest wmRequest = new WorkManagerRequest();
//				wmRequest.setWmFn(WM_ACTION_IM);
//				wmRequest.setRefId(applicationGroup.getApplicationGroupId());
//				wmRequest.setRefCode(applicationGroup.getApplicationGroupNo());
//				
//				wmRequest.setTaskData(contentMsg);
//				
//				EAppAction.AddTaskToWorkManager(wmRequest);
				//change method
				ProcessResponse taskResponse = EAppAction.AddTaskToWorkManager(applicationGroup, WM_ACTION_IM, contentMsg);
				if(ServiceConstant.Status.SUCCESS.equals(taskResponse.getStatusCode())){
					//create case
					String result = createCase(applicationGroup, applicationGroup.getSourceUserId());
					logger.debug("Create case result : " + result);
					if(ServiceResponse.Status.SUCCESS.equals(result)){
						applicationGroup.setDecisionAction(DECISION_ACTION_IM);
						applicationGroup.setUserId(userId);
						WorkflowResponseDataM workflowResponse = EAppAction.workflowAction(applicationGroup);
						if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(workflowResponse.getResultCode())){
							response.setStatusCode(ServiceResponse.Status.SUCCESS);
						}else{
							errorMsg = taskResponse.getErrorData().getErrorDesc();
							response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
							response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, workflowResponse.getResultCode(), workflowResponse.getErrorMsg()));
						}
					}else{
						response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
						response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, result, "Call Create Case Error"));
					}
				}
				else{
					errorMsg = taskResponse.getErrorData().getErrorDesc();
					response.setStatusCode(taskResponse.getStatusCode());
					response.setErrorData(taskResponse.getErrorData());
				}
			}
			else{
				errorMsg = "JobState is not ready";
				response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, ServiceResponse.Status.SYSTEM_EXCEPTION, errorMsg));
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			errorMsg = e.getLocalizedMessage();
			response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, ServiceResponse.Status.SYSTEM_EXCEPTION, errorMsg));
		}
		
		ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
		serviceLogResponse.setServiceReqRespId(serviceReqRespId);
		serviceLogResponse.setRefCode(applicationGroupNo);
		serviceLogResponse.setActivityType(ServiceConstant.OUT);
		serviceLogResponse.setServiceDataObject(response);
		serviceLogResponse.setServiceId(ServiceConstant.ServiceId.ESubmitApplicationIM);
		serviceLogResponse.setUniqueId(applicationGroupId);
		serviceLogResponse.setRespCode(response.getStatusCode());
		serviceLogResponse.setUserId(userId);
		serviceLogResponse.setErrorMessage(errorMsg);
		serviceController.createLog(serviceLogResponse);
		
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value="/WF", method = {RequestMethod.POST, RequestMethod.PUT})
    public @ResponseBody ResponseEntity<ProcessResponse> submitPostApproved(@RequestBody WFSubmitApplicationM submitApplicationM){
		ProcessResponse response = new ProcessResponse();
		String errorMsg = "";
		String applicationGroupId = submitApplicationM.getApplicationGroupId();
		String applicationGroupNo = submitApplicationM.getApplicationGroupNo();
		
		String userId = submitApplicationM.getUserId();
		logger.debug("applicationGroupId >> "+applicationGroupId);
		logger.debug("applicationGroupNo >> "+applicationGroupNo);
		
		if(Util.empty(userId)){
			userId = SystemConstant.getConstant("SYSTEM_USER");
		}
		logger.debug("userId >> "+userId);

		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		ServiceCenterController serviceController = new ServiceCenterController();
		ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
			serviceLogRequest.setServiceReqRespId(serviceReqRespId);
			serviceLogRequest.setRefCode(applicationGroupNo);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceDataObject(submitApplicationM);
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.ESubmitApplicationWF);
			serviceLogRequest.setUniqueId(applicationGroupId);
			serviceLogRequest.setUserId(userId);
		serviceController.createLog(serviceLogRequest);
		
		ArrayList<String> validJobStatePreApproved = SystemConstant.getArrayListConstant("VALID_JOBSTATE_PREAPPROVE");
		
		try{
//			String applicationGroupId = ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupIdByApplicationGroupNo(applicationGroupNo);
			ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO().loadOrigApplicationGroupM(applicationGroupId);
			
			if (null != applicationGroup && null != applicationGroup.getJobState() && !Util.empty(validJobStatePreApproved) && validJobStatePreApproved.contains(applicationGroup.getJobState()) ) {
				applicationGroup.setUserId(userId);
				//if case submit from im not set user
				if(CALL_ACTION_PRE_APPROVED.equals(applicationGroup.getSourceAction())){
					applicationGroup.setUserId(applicationGroup.getSourceUserId());
				}
				
				//call verification
				ProcessActionResponse decisionResponse = EAppAction.requestDecision(applicationGroup, DecisionServiceUtil.DECISION_POINT.DECISION_POINT_VERIFICATION, userId);
				String decisionResultCode = decisionResponse.getResultCode();						
				logger.debug("decisionResultCode : "+decisionResultCode);	
				response.setStatusCode(decisionResponse.getResultCode());
				
				if(ServiceResponse.Status.SUCCESS.equals(decisionResultCode)){
					//create case
					String result = ServiceResponse.Status.SUCCESS;
					//check call create case when call action from e-app is Pre-Approved
					if(CALL_ACTION_PRE_APPROVED.equals(applicationGroup.getSourceAction())){
						createCase(applicationGroup, userId);
					}
					logger.debug("Create case result : " + result);
					if(ServiceResponse.Status.SUCCESS.equals(result)){
						String callAction = "";	
						String requireVerify = "";
						
						ArrayList<String> applyTypes = applicationGroup.getApplicationApplyTypes(applicationGroup.getApplicationIds());
						if(applyTypes.contains(DecisionServiceUtil.APPLY_TYPE.ERR)){
							callAction = CALL_ACTION_REJECTED;
						}
						else{
							// check from doc complete flag, ver web require flag, ver flag >>>> if Y set require ver = Y else N
							if(!Util.empty(applicationGroup.getRequireVerify())){
								requireVerify = applicationGroup.getRequireVerify();
							}
							callAction = getVerifyAction(requireVerify);
						}
						
						ProcessActionInf processActionInf = this.actionProcess(callAction);
						if(null!=processActionInf){
							processActionInf.init(applicationGroup);
							ProcessResponse processResponse = (ProcessResponse)processActionInf.processAction();
							String processResponseResult = processResponse.getStatusCode();
							logger.debug("processResponseResult >> "+processResponseResult);
							logger.debug(processResponse);
							if(ServiceResponse.Status.SUCCESS.equals(processResponseResult)){
								response.setStatusCode(ServiceResponse.Status.SUCCESS);
							}else{
								response.setStatusCode(processResponse.getStatusCode());
								ErrorData errorData = processResponse.getErrorData();
								if(null != errorData){
									response.setErrorData(errorData);
									errorMsg = errorData.getErrorInformation();
								}
							}
						}else{
							logger.error("cannot find action process for :" + requireVerify);
							errorMsg= MessageErrorUtil.getText("ERROR_NOT_FOUND_CALL_ACTION");
							response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
							response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, ServiceResponse.Status.SYSTEM_EXCEPTION, errorMsg));
						}
					}else{
						response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
						response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, result, "Call Create Case Error"));
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
		}catch(Exception e){
			logger.fatal("ERROR",e);
			errorMsg = e.getLocalizedMessage();
			response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, ServiceResponse.Status.SYSTEM_EXCEPTION, errorMsg));
		}
		
		ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
		serviceLogResponse.setServiceReqRespId(serviceReqRespId);
		serviceLogResponse.setRefCode(applicationGroupNo);
		serviceLogResponse.setActivityType(ServiceConstant.OUT);
		serviceLogResponse.setServiceDataObject(response);
		serviceLogResponse.setServiceId(ServiceConstant.ServiceId.ESubmitApplicationWF);
		serviceLogResponse.setUniqueId(applicationGroupId);
		serviceLogResponse.setRespCode(response.getStatusCode());
		serviceLogResponse.setUserId(userId);
		serviceLogResponse.setErrorMessage(errorMsg);
		serviceController.createLog(serviceLogResponse);
		
		return ResponseEntity.ok(response);
	}
	
	private ProcessActionInf actionProcess(String action){
		String CACHE_SUMMIT_APPLICATION_FUNCTION = SystemConstant.getConstant("CACHE_WF_SUBMIT_APPLICATION_FUNCTION");
		ProcessActionInf processActionInf = null; 
		logger.debug("action >> "+action);
		try{
			String className = CacheControl.getName(CACHE_SUMMIT_APPLICATION_FUNCTION, action);
			logger.debug("className >> "+className);
			processActionInf = (ProcessActionInf)Class.forName(className).newInstance();
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return processActionInf;
	}
	
	private String getVerifyAction(String requireVerify){
		logger.debug("requireVerify >> "+requireVerify);
		if(MConstant.FLAG_Y.equals(requireVerify)){
			return CALL_ACTION_DV;
		}
		else if(MConstant.FLAG_N.equals(requireVerify)){
			return CALL_ACTION_FRAUD;
		}
		return null;
	}
    
	@RequestMapping(value="/wm", method = {RequestMethod.POST, RequestMethod.PUT})
	public @ResponseBody ResponseEntity<ProcessResponse> wm(@RequestBody WFSubmitApplicationM submitApplicationM){
		ProcessResponse response = new ProcessResponse();

		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value="/init", method = {RequestMethod.POST, RequestMethod.PUT})
	public @ResponseBody ResponseEntity<ProcessResponse> initial(@RequestBody String requestJson){
		ProcessResponse response = new ProcessResponse();
		String errorMsg = "";
		 GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		logger.debug("ApplicationGroup before: " + requestJson);
		ApplicationGroupDataM applicationGroup=gsonBuilder.create().fromJson(requestJson, ApplicationGroupDataM.class);
		
		logger.debug("ApplicationGroup after: " + gsonBuilder.create().toJson(applicationGroup));
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		String applicationGroupNo = applicationGroup.getApplicationGroupNo();
		String callAction = applicationGroup.getSourceAction();
		String userId = applicationGroup.getSourceUserId();

		logger.debug("applicationGroupId >> "+applicationGroupId);
		logger.debug("applicationGroupNo >> "+applicationGroupNo);
		logger.debug("getApplicationDate >> "+applicationGroup.getApplicationDate());
		if(Util.empty(userId)){
			userId = SystemConstant.getConstant("SYSTEM_USER");
		}
		logger.debug("userId >> "+userId);

		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		ServiceCenterController serviceController = new ServiceCenterController();
		ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
			serviceLogRequest.setServiceReqRespId(serviceReqRespId);
			serviceLogRequest.setRefCode(applicationGroupNo);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceDataObject(applicationGroup);
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.InitialApplication);
			serviceLogRequest.setUniqueId(applicationGroupId);
			serviceLogRequest.setUserId(userId);
		serviceController.createLog(serviceLogRequest);
		
		logger.debug("callAction : "+callAction);
		try{
            ProcessActionInf processActionInf = this.findProcessAction(callAction);
            if(null!=processActionInf){
                ESubmitApplicationObject submitEAppObject = new ESubmitApplicationObject();
                submitEAppObject.setApplicationGroup(applicationGroup);
                submitEAppObject.setUserId(userId);
                submitEAppObject.setTransactionId(serviceController.getTransactionId());
                submitEAppObject.setCallAction(callAction);
                
                processActionInf.init(submitEAppObject);
                response = (ProcessResponse)processActionInf.processAction();
                String processResponseResult = response.getStatusCode();
                logger.debug("processResponseResult >> "+processResponseResult);
                logger.debug(response);
            }else{
                logger.error("cannot find action process for : " + callAction);
                errorMsg = "cannot find action process for : " + callAction;
                response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
                response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, ServiceResponse.Status.SYSTEM_EXCEPTION, errorMsg));
            }
		}catch(Exception e){
			logger.fatal("ERROR",e);
			errorMsg = e.getLocalizedMessage();
			response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, ServiceResponse.Status.SYSTEM_EXCEPTION, errorMsg));
		}
		
		ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
		serviceLogResponse.setServiceReqRespId(serviceReqRespId);
		serviceLogResponse.setRefCode(applicationGroupNo);
		serviceLogResponse.setActivityType(ServiceConstant.OUT);
		serviceLogResponse.setServiceDataObject(response);
		serviceLogResponse.setServiceId(ServiceConstant.ServiceId.InitialApplication);
		serviceLogResponse.setUniqueId(applicationGroupId);
		serviceLogResponse.setRespCode(response.getStatusCode());
		serviceLogResponse.setUserId(userId);
		serviceLogResponse.setErrorMessage(errorMsg);
		serviceController.createLog(serviceLogResponse);
		
		return ResponseEntity.ok(response);
	}
	
	private ProcessActionInf findProcessAction(String callAction) throws Exception{
		String CACHE_SUMMIT_APPLICATION_FUNCTION = SystemConstant.getConstant("CACHE_WF_SUBMIT_APPLICATION_FUNCTION");
		logger.debug("callAction >> "+callAction);
		ProcessActionInf processActionInf = null;
		try{
			String className = CacheControl.getName(CACHE_SUMMIT_APPLICATION_FUNCTION, callAction);
			logger.debug("className >> "+className);
			processActionInf = (ProcessActionInf)Class.forName(className).newInstance();
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return processActionInf;
	}
	
	private String createCase(ApplicationGroupDataM applicationGroup, String userId) throws Exception{
		String response = "";
		String JMS_PEGA_URL = SystemConfig.getProperty("JMS_PEGA_URL");
		try {
			ActiveMQSendQueueRequestDataM mqRequest = new ActiveMQSendQueueRequestDataM();
			mqRequest.setApplicationGroup(applicationGroup);
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(ActiveMQSendQueueProxy.serviceId);
				serviceRequest.setUserId(userId);
				serviceRequest.setEndpointUrl(JMS_PEGA_URL);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setObjectData(mqRequest);
				
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
				response =(String)serivceResponse.getObjectData();
			}
			logger.debug("createCase Status code : " + serivceResponse.getStatusCode());
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			throw e;
		}
		
		return response;
	}
}
