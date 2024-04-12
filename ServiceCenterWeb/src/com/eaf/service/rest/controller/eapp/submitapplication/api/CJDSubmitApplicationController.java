package com.eaf.service.rest.controller.eapp.submitapplication.api;

import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.jms.DeliveryMode;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ava.flp.eapp.iib.deserializer.DateDeserializer;
import com.ava.flp.eapp.iib.serializer.DateSerializer;
import com.ava.flp.eapp.submitapplication.model.ApplicationGroup;
import com.ava.flp.eapp.submitapplication.model.ESubmitApplicationObject;
import com.ava.flp.eapp.submitapplication.model.KBankHeader;
import com.ava.flp.eapp.submitapplication.model.SubmitApplication;
import com.avalant.wm.model.WorkManagerRequest;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.postapproval.CJDCardLinkAction;
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
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.eapp.mapper.EAppModelMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.model.ServiceReqRespDataM;
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

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;

//@Controller
@RestController
@RequestMapping("/service/FLS-SubmitApplication")
public class CJDSubmitApplicationController implements SubmitApi {
	private static Logger logger = Logger.getLogger(CJDSubmitApplicationController.class);
	
	@RequestMapping(value="/submitCJD",method=RequestMethod.POST)
    public ResponseEntity<SubmitApplication> submitPost(@ApiParam(value = ""  )  @Valid @RequestBody SubmitApplication submitApplicationRequest) {
		String errorMsg = "";
		KBankHeader responseKbankHeader = new KBankHeader();
		SubmitApplication submitApplicationResponse = new SubmitApplication();
		KBankHeader requestKBankHeader = submitApplicationRequest.getKbankHeader();
		if(null == requestKBankHeader){
			requestKBankHeader = new KBankHeader();
		}
		String userId = requestKBankHeader.getUserId();
		if(Util.empty(userId)){
			userId = SystemConstant.getConstant("SYSTEM_USER");
		}
		logger.debug("userId >> "+userId);
		
		ApplicationGroup applicationGroup = submitApplicationRequest.getApplicationGroup();
		
		String applicationGroupNo = applicationGroup.getApplicationGroupNo();
		String callAction = applicationGroup.getCallAction();
		
		logger.debug("applicationGroupNo : "+applicationGroupNo);
		logger.debug("callAction : "+callAction);
		
		Gson logGson = new Gson();
		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		ServiceReqRespDataM serviceLogRequest = new ServiceReqRespDataM();
			serviceLogRequest.setReqRespId(serviceReqRespId);
			serviceLogRequest.setRefCode(applicationGroupNo);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setContentMsg(logGson.toJson(submitApplicationRequest));
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.CJDSubmitApplication);
			serviceLogRequest.setCreateBy(userId);
		CJDCardLinkAction.createServiceReqRespLog(serviceLogRequest);
		
		if(null != requestKBankHeader){
			responseKbankHeader.setRqUID(requestKBankHeader.getRqUID());
			responseKbankHeader.setFuncNm(requestKBankHeader.getFuncNm());
		}
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		responseKbankHeader.setRsAppId(RqAppId);
		responseKbankHeader.setRsUID(serviceReqRespId);
		responseKbankHeader.setRsDt(EAppUtil.convertXmlGregorianCalendarToJodaDateTime(ServiceUtil.getXMLGregorianCalendar()));
		responseKbankHeader.setCorrID(serviceReqRespId);
		
		try
		{
			//Map ApplicationGroup to ULO model
			ApplicationGroupDataM uloApplicationGroup = new EAppModelMapper().mapUloModel(null, applicationGroup);
			uloApplicationGroup.setUserId(userId);
			uloApplicationGroup.setSourceUserId(userId);
			uloApplicationGroup.setSource(MConstant.SOURCE_NAME.CJD);
			
			logger.debug("app date " + uloApplicationGroup.getApplicationDate());
			GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
	        Gson gson = gsonBuilder.create();
	        
	        //Create task object
	        String WM_ACTION_INITIAL_CJD = SystemConstant.getConstant("WM_ACTION_INITIAL_CJD");
			WorkManagerRequest wmRequest = new WorkManagerRequest();
			wmRequest.setWmFn(WM_ACTION_INITIAL_CJD);
			wmRequest.setRefId(null);
			wmRequest.setRefCode(applicationGroupNo);
			wmRequest.setTaskData(gson.toJson(uloApplicationGroup));
			
			//Sent task to Work Manager
			ProcessResponse processResponse = EAppAction.AddTaskToWorkManager(wmRequest);
			String processResponseResult = processResponse.getStatusCode();
			logger.debug("processResponseResult >> "+processResponseResult);
			logger.debug(processResponse);
			if(ServiceResponse.Status.SUCCESS.equals(processResponseResult)){
				responseKbankHeader.setStatusCode(ServiceResponse.Status.SUCCESS);
			}else{
				responseKbankHeader.setStatusCode(processResponse.getStatusCode());
				ErrorData errorData = processResponse.getErrorData();
				if(null != errorData){
					responseKbankHeader.error(errorData.getErrorCode(),errorData.getErrorDesc());
					errorMsg = errorData.getErrorInformation();
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			errorMsg = e.getLocalizedMessage();
			responseKbankHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);			
		}
		submitApplicationResponse.setKbankHeader(responseKbankHeader);
		Gson gson = new Gson();
		ServiceReqRespDataM serviceLogResponse = new ServiceReqRespDataM();
			serviceLogResponse.setReqRespId(serviceReqRespId);
			serviceLogResponse.setRefCode(applicationGroupNo);
			serviceLogResponse.setActivityType(ServiceConstant.OUT);
			serviceLogResponse.setContentMsg(gson.toJson(submitApplicationResponse));
			serviceLogResponse.setServiceId(ServiceConstant.ServiceId.CJDSubmitApplication);
			serviceLogResponse.setRespCode(responseKbankHeader.getStatusCode());
			serviceLogResponse.setCreateBy(userId);
			try{
				if(null != responseKbankHeader.getErrors())
				{
					serviceLogResponse.setRespDesc(gson.toJson(responseKbankHeader.getErrors()));
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
				errorMsg = e.getLocalizedMessage();
				responseKbankHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);	
			}
			responseKbankHeader.setErrors(this.getKbankEror(RqAppId,errorMsg));
			serviceLogResponse.setErrorMessage(errorMsg);
			CJDCardLinkAction.createServiceReqRespLog(serviceLogResponse);
		
        return new ResponseEntity<SubmitApplication>(submitApplicationResponse, HttpStatus.OK);
    }
	
	@RequestMapping(value="/initCJD", method = {RequestMethod.POST, RequestMethod.PUT})
	public @ResponseBody ResponseEntity<ProcessResponse> initCJD(@RequestBody String requestJson){
		ProcessResponse response = new ProcessResponse();
		String errorMsg = "";
		
		//Transform String to applicationGroup(ULO model)
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
		
		ServiceCenterController serviceController = new ServiceCenterController();
		
		if(applicationGroup != null)
		{
			logger.debug("callAction : "+callAction);
			try
			{
				//Call CJDProcessActionInitial
	            ProcessActionInf processActionInf = this.findProcessActionCJD("Initial");
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
			}
			catch(Exception e)
			{
				logger.fatal("ERROR",e);
				errorMsg = e.getLocalizedMessage();
				response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, ServiceResponse.Status.SYSTEM_EXCEPTION, errorMsg));
			}
		}
		
		//Call submitCardLinkActionCJD on InitCJD success
		if(ServiceResponse.Status.SUCCESS.equals(response.getStatusCode()))
		{
			ProcessResponse submitCardLinkPR = callSubmitCardLinkActionCJD(applicationGroupId, applicationGroupNo);
		}
		
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value="/submitCardLinkActionCJD",method=RequestMethod.POST)
    public ResponseEntity<SubmitApplication> submitCardLinkActionCJD(@ApiParam(value = ""  )  @Valid @RequestBody WFSubmitApplicationM submitApplicationM) {
		String errorMsg = "";
		KBankHeader responseKbankHeader = new KBankHeader();
		SubmitApplication submitApplicationResponse = new SubmitApplication();
		
		String userId = SystemConstant.getConstant("SYSTEM_USER");
		logger.debug("userId >> "+userId);
		
		String applicationGroupId = submitApplicationM.getApplicationGroupId();
		String applicationGroupNo = submitApplicationM.getApplicationGroupNo();
		
		logger.debug("applicationGroupId : "+applicationGroupId);
		logger.debug("applicationGroupNo : "+applicationGroupNo);
		
		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		responseKbankHeader.setRsAppId(RqAppId);
		responseKbankHeader.setRsUID(serviceReqRespId);
		responseKbankHeader.setRsDt(EAppUtil.convertXmlGregorianCalendarToJodaDateTime(ServiceUtil.getXMLGregorianCalendar()));
		responseKbankHeader.setCorrID(serviceReqRespId);
		responseKbankHeader.setRqUID("");
		responseKbankHeader.setFuncNm("submitCardLinkActionCJD");
		
		try
		{
			String WM_ACTION_CARDLINK_ACTION_CJD = SystemConstant.getConstant("WM_ACTION_CARDLINK_ACTION_CJD");
			WorkManagerRequest wmRequest = new WorkManagerRequest();
			wmRequest.setWmFn(WM_ACTION_CARDLINK_ACTION_CJD);
			wmRequest.setRefId(applicationGroupId);
			wmRequest.setRefCode(applicationGroupNo);
			
			Gson gson = new Gson();
			wmRequest.setTaskData(gson.toJson(submitApplicationM));
			
			ProcessResponse processResponse = EAppAction.AddTaskToWorkManager(wmRequest);
			String processResponseResult = processResponse.getStatusCode();
			logger.debug("processResponseResult >> "+processResponseResult);
			logger.debug(processResponse);
			if(ServiceResponse.Status.SUCCESS.equals(processResponseResult)){
				responseKbankHeader.setStatusCode(ServiceResponse.Status.SUCCESS);
			}else{
				responseKbankHeader.setStatusCode(processResponse.getStatusCode());
				ErrorData errorData = processResponse.getErrorData();
				if(null != errorData){
					responseKbankHeader.error(errorData.getErrorCode(),errorData.getErrorDesc());
					errorMsg = errorData.getErrorInformation();
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			errorMsg = e.getLocalizedMessage();
			responseKbankHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);			
		}
		submitApplicationResponse.setKbankHeader(responseKbankHeader);
		
        return new ResponseEntity<SubmitApplication>(submitApplicationResponse, HttpStatus.OK);
    }
	
	@RequestMapping(value="/cardlinkActionCJD", method = {RequestMethod.POST, RequestMethod.PUT})
	public @ResponseBody ResponseEntity<ProcessResponse> cardLinkActionCJD(@RequestBody WFSubmitApplicationM submitApplicationM){
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
		
		ApplicationGroupDataM appGroup = new ApplicationGroupDataM();
		appGroup.setApplicationGroupId(applicationGroupId);
		appGroup.setApplicationGroupNo(applicationGroupNo);
		
		try{
			//Call CJDProcessActionCardlink
            ProcessActionInf processActionInf = this.findProcessActionCJD("CardLink");
            if(null!=processActionInf)
            {
                processActionInf.init(submitApplicationM);
                response = (ProcessResponse)processActionInf.processAction();
                String processResponseResult = response.getStatusCode();
                logger.debug("processResponseResult >> "+processResponseResult);
                logger.debug(response);
            }else{
                logger.error("cannot find CJD action process for : CardLink");
                errorMsg = "cannot find CJD action process for : CardLink";
                response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
                response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, ServiceResponse.Status.SYSTEM_EXCEPTION, errorMsg));
            }
		}catch(Exception e){
			logger.fatal("ERROR",e);
			errorMsg = e.getLocalizedMessage();
			response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, ServiceResponse.Status.SYSTEM_EXCEPTION, errorMsg));
		}
		
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value="/submitCJDIncome",method=RequestMethod.POST)
    public ResponseEntity<SubmitApplication> submitIncome(@ApiParam(value = ""  )  @Valid @RequestBody SubmitApplication submitApplicationRequest) {
		String errorMsg = "";
		KBankHeader responseKbankHeader = new KBankHeader();
		SubmitApplication submitApplicationResponse = new SubmitApplication();
		KBankHeader requestKBankHeader = submitApplicationRequest.getKbankHeader();
		if(null == requestKBankHeader){
			requestKBankHeader = new KBankHeader();
		}
		String userId = requestKBankHeader.getUserId();
		if(Util.empty(userId)){
			userId = SystemConstant.getConstant("SYSTEM_USER");
		}
		logger.debug("userId >> "+userId);
		String CJD_DEFAULT_WEBSCAN_USER = SystemConstant.getConstant("CJD_DEFAULT_WEBSCAN_USER");
		
		ApplicationGroup applicationGroup = submitApplicationRequest.getApplicationGroup();
		
		String applicationGroupNo = applicationGroup.getApplicationGroupNo();
		String callAction = applicationGroup.getCallAction();
		
		logger.debug("applicationGroupNo : "+applicationGroupNo);
		logger.debug("callAction : "+callAction);
		
		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		ServiceCenterController serviceController = new ServiceCenterController();
		ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
			serviceLogRequest.setServiceReqRespId(serviceReqRespId);
			serviceLogRequest.setRefCode(applicationGroupNo);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceDataObject(applicationGroup);
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.CJDSubmitIncomeAction);
//			serviceLogRequest.setUniqueId(applicationGroup);
			serviceLogRequest.setUserId(userId);
		serviceController.createLog(serviceLogRequest);
		
		if(null != requestKBankHeader){
			responseKbankHeader.setRqUID(requestKBankHeader.getRqUID());
			responseKbankHeader.setFuncNm(requestKBankHeader.getFuncNm());
		}
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		responseKbankHeader.setRsAppId(RqAppId);
		responseKbankHeader.setRsUID(serviceReqRespId);
		responseKbankHeader.setRsDt(EAppUtil.convertXmlGregorianCalendarToJodaDateTime(ServiceUtil.getXMLGregorianCalendar()));
		responseKbankHeader.setCorrID(serviceReqRespId);
		
		try
		{
			//Map ApplicationGroup to ULO model
			ApplicationGroupDataM uloApplicationGroup = new EAppModelMapper().mapUloModel(null, applicationGroup);
			uloApplicationGroup.setUserId(userId);
			uloApplicationGroup.setSourceUserId(CJD_DEFAULT_WEBSCAN_USER);
			uloApplicationGroup.setSource(MConstant.SOURCE_NAME.CJD);
			
			logger.debug("app date " + uloApplicationGroup.getApplicationDate());
			GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
	        Gson gson = gsonBuilder.create();
	        
	        //Create task object
	        String WM_ACTION_INITIAL_CJD_INCOME = SystemConstant.getConstant("WM_ACTION_INITIAL_CJD_INCOME");
			WorkManagerRequest wmRequest = new WorkManagerRequest();
			wmRequest.setWmFn(WM_ACTION_INITIAL_CJD_INCOME);
			wmRequest.setRefId(null);
			wmRequest.setRefCode(applicationGroupNo);
			wmRequest.setTaskData(gson.toJson(uloApplicationGroup));
			
			//Sent task to Work Manager
			ProcessResponse processResponse = EAppAction.AddTaskToWorkManager(wmRequest);
			String processResponseResult = processResponse.getStatusCode();
			logger.debug("processResponseResult >> "+processResponseResult);
			logger.debug(processResponse);
			if(ServiceResponse.Status.SUCCESS.equals(processResponseResult)){
				responseKbankHeader.setStatusCode(ServiceResponse.Status.SUCCESS);
			}else{
				responseKbankHeader.setStatusCode(processResponse.getStatusCode());
				ErrorData errorData = processResponse.getErrorData();
				if(null != errorData){
					responseKbankHeader.error(errorData.getErrorCode(),errorData.getErrorDesc());
					errorMsg = errorData.getErrorInformation();
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			errorMsg = e.getLocalizedMessage();
			responseKbankHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);			
		}
		
		submitApplicationResponse.setKbankHeader(responseKbankHeader);
		ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
			serviceLogResponse.setServiceReqRespId(serviceReqRespId);
			serviceLogResponse.setRefCode(applicationGroupNo);
			serviceLogResponse.setActivityType(ServiceConstant.OUT);
			serviceLogResponse.setServiceDataObject(submitApplicationResponse);
			serviceLogResponse.setServiceId(ServiceConstant.ServiceId.CJDSubmitIncomeAction);
	//		serviceLogResponse.setUniqueId(applicationGroupId);
			serviceLogResponse.setRespCode(responseKbankHeader.getStatusCode());
			serviceLogResponse.setUserId(userId);
			try{
				if(null != responseKbankHeader.getErrors()){
					Gson gson = new Gson();
					serviceLogResponse.setRespDesc(gson.toJson(responseKbankHeader.getErrors()));
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
				errorMsg = e.getLocalizedMessage();
				responseKbankHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);	
			}
			responseKbankHeader.setErrors(this.getKbankEror(RqAppId,errorMsg));
			serviceLogResponse.setErrorMessage(errorMsg);
			serviceController.createLog(serviceLogResponse);
		
        return new ResponseEntity<SubmitApplication>(submitApplicationResponse, HttpStatus.OK);
    }
	
	@RequestMapping(value="/initCJDIncome", method = {RequestMethod.POST, RequestMethod.PUT})
	public @ResponseBody ResponseEntity<ProcessResponse> initialCJDIncome(@RequestBody String requestJson){
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
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.CJDInitialApplication);
			serviceLogRequest.setUniqueId(applicationGroupId);
			serviceLogRequest.setUserId(userId);
		serviceController.createLog(serviceLogRequest);
		
		logger.debug("callAction : "+callAction);
		try{
            ProcessActionInf processActionInf = this.findProcessActionCJD(callAction);
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
		serviceLogResponse.setServiceId(ServiceConstant.ServiceId.CJDInitialApplication);
		serviceLogResponse.setUniqueId(applicationGroupId);
		serviceLogResponse.setRespCode(response.getStatusCode());
		serviceLogResponse.setUserId(userId);
		serviceLogResponse.setErrorMessage(errorMsg);
		serviceController.createLog(serviceLogResponse);
		
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value="/sendIMCJD", method = {RequestMethod.POST, RequestMethod.PUT})
    public @ResponseBody ResponseEntity<ProcessResponse> sendIMCJD(@RequestBody WFSubmitApplicationM submitApplicationM){
		ProcessResponse response = new ProcessResponse();
		String errorMsg = "";
		String WM_ACTION_IM_CJD = SystemConstant.getConstant("WM_ACTION_IM_CJD");
		String DECISION_ACTION_IM = SystemConstant.getConstant("DECISION_ACTION_IM");
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
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.CJDIMAction);
			serviceLogRequest.setUniqueId(applicationGroupId);
			serviceLogRequest.setUserId(userId);
		serviceController.createLog(serviceLogRequest);
		
		ArrayList<String> validJobStateProceed = SystemConstant.getArrayListConstant("VALID_JOBSTATE_PROCEED");

		try {
			ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO().loadOrigApplicationGroupM(applicationGroupId);
			String PRIORITY = SystemConstant.getConstant("PRIORITY");
			String PEGA_EVENT_FLAG = SystemConstant.getConstant("PEGA_EVENT_FLAG");
			String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
			
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
				
				//change method
				ProcessResponse taskResponse = EAppAction.AddTaskToWorkManager(applicationGroup, WM_ACTION_IM_CJD, contentMsg);
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
		serviceLogResponse.setServiceId(ServiceConstant.ServiceId.CJDIMAction);
		serviceLogResponse.setUniqueId(applicationGroupId);
		serviceLogResponse.setRespCode(response.getStatusCode());
		serviceLogResponse.setUserId(userId);
		serviceLogResponse.setErrorMessage(errorMsg);
		serviceController.createLog(serviceLogResponse);
		
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value="/incomeCJD", method = {RequestMethod.POST, RequestMethod.PUT})
    public @ResponseBody ResponseEntity<ProcessResponse> incomeCJD(@RequestBody WFSubmitApplicationM submitApplicationM){
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
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.CJDIncomeAction);
			serviceLogRequest.setUniqueId(applicationGroupId);
			serviceLogRequest.setUserId(userId);
		serviceController.createLog(serviceLogRequest);
		
		ArrayList<String> validJobStatePreApproved = SystemConstant.getArrayListConstant("VALID_JOBSTATE_PREAPPROVE");
		
		try{
			ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO().loadOrigApplicationGroupM(applicationGroupId);
			
			if (null != applicationGroup && null != applicationGroup.getJobState() && !Util.empty(validJobStatePreApproved) && validJobStatePreApproved.contains(applicationGroup.getJobState()) ) {
				applicationGroup.setUserId(userId);
				
				//call verification
				ProcessActionResponse decisionResponse = EAppAction.requestDecision(applicationGroup, DecisionServiceUtil.DECISION_POINT.DECISION_POINT_VERIFICATION, userId);
				String decisionResultCode = decisionResponse.getResultCode();						
				logger.debug("decisionResultCode : "+decisionResultCode);	
				response.setStatusCode(decisionResponse.getResultCode());
				
				if(ServiceResponse.Status.SUCCESS.equals(decisionResultCode)){
					String callAction = SystemConstant.getConstant("CALL_ACTION_DV");	
					
					ProcessActionInf processActionInf = this.findProcessActionCJD(callAction);
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
						logger.error("cannot find action process for :" + callAction);
						errorMsg= MessageErrorUtil.getText("ERROR_NOT_FOUND_CALL_ACTION");
						response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
						response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, ServiceResponse.Status.SYSTEM_EXCEPTION, errorMsg));
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
		serviceLogResponse.setServiceId(ServiceConstant.ServiceId.CJDIncomeAction);
		serviceLogResponse.setUniqueId(applicationGroupId);
		serviceLogResponse.setRespCode(response.getStatusCode());
		serviceLogResponse.setUserId(userId);
		serviceLogResponse.setErrorMessage(errorMsg);
		serviceController.createLog(serviceLogResponse);
		
		return ResponseEntity.ok(response);
	}
	
	private ProcessActionInf findProcessActionCJD(String callAction) throws Exception{
		String CACHE_SUMMIT_APPLICATION_CJD_FUNCTION = SystemConstant.getConstant("CACHE_SUMMIT_APPLICATION_CJD_FUNCTION");
		logger.debug("callAction >> "+callAction);
		ProcessActionInf processActionInf = null;
		try{
			String className = CacheControl.getName(CACHE_SUMMIT_APPLICATION_CJD_FUNCTION, callAction);
			logger.debug("className >> "+className);
			processActionInf = (ProcessActionInf)Class.forName(className).newInstance();
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return processActionInf;
	}
	
	private List<com.ava.flp.eapp.submitapplication.model.Error> getKbankEror(String rsAppId,String errs){
		List<com.ava.flp.eapp.submitapplication.model.Error> kbankErrors = new ArrayList<com.ava.flp.eapp.submitapplication.model.Error>();
		if(!Util.empty(errs)){
			com.ava.flp.eapp.submitapplication.model.Error kbankError = new com.ava.flp.eapp.submitapplication.model.Error();
			kbankError.setErrorAppId(rsAppId);
			kbankError.setErrorCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
			kbankError.setErrorDesc(errs);
			kbankError.setErrorSeverity(ServiceConstant.ErrorSeverityCode.ERROR);	
			kbankErrors.add(kbankError);
		}
		return kbankErrors;
	}
	
	public static ProcessResponse callSubmitCardLinkActionCJD(String applicationGroupId, String applicationGroupNo)
	{
		ProcessResponse response = new ProcessResponse();
		
		//On Success continue with Call submitCardLinkActionCJD
		logger.debug("Call submitCardLinkActionCJD");
		WFSubmitApplicationM wfAppM = new WFSubmitApplicationM();
		wfAppM.setApplicationGroupId(applicationGroupId);
		wfAppM.setApplicationGroupNo(applicationGroupNo);
				
		String endPointUrl = SystemConfig.getProperty("WORK_MANAGER_SUBMIT_CARDLINK_ACTION_CJD_URL");
		logger.debug("endPointUrl : "+endPointUrl);	
				
		HttpHeaders httpHeaderReq = new HttpHeaders();
		httpHeaderReq.add("Content-Type", "application/json; charset=UTF-8");
		HttpEntity<WFSubmitApplicationM> requestEntity = new HttpEntity<WFSubmitApplicationM>(wfAppM, httpHeaderReq);
		RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
		@Override
		protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
			if(connection instanceof HttpsURLConnection ){
				((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
					@Override
					public boolean verify(String arg0, SSLSession arg1) {
						return true;
						}
					});
				}
					super.prepareConnection(connection, httpMethod);
				}
				});	
		ResponseEntity<ProcessResponse> responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,ProcessResponse.class);
		    	
		logger.debug("response >> "+responseEntity);
		response = responseEntity.getBody();
		return response;
	}
	
	@RequestMapping(value="/testCJDQueue",method=RequestMethod.POST)
    public ResponseEntity<String> testCJDQueue(@ApiParam(value = ""  )  @Valid @RequestBody String text) 
    {
		String putQueueConnectionFactory = SystemConstant.getConstant("REQUEST_CJD_QUEUE_CONNECTION_FACTORY_JNDI");
		String putQueueDestination = SystemConstant.getConstant("REQUEST_CJD_QUEUE_JNDI");
		String JMS_IBM_CHARACTER_SET = SystemConstant.getConstant("JMS_IBM_CHARACTER_SET");
		
		QueueConnectionFactory qcf;
		Queue cjdQueue;
		try 
		{
			qcf = (QueueConnectionFactory)InitialContext.doLookup(putQueueConnectionFactory);
			
			QueueConnection queueConnection = qcf.createQueueConnection();
			QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			cjdQueue = (Queue)InitialContext.doLookup(putQueueDestination);
			QueueSender queueSender = queueSession.createSender(cjdQueue);
			
			TextMessage textMessage = queueSession.createTextMessage();
			textMessage.setIntProperty("JMS_IBM_Character_Set", Integer.valueOf(JMS_IBM_CHARACTER_SET));
			textMessage.setText(text);
			textMessage.setJMSType("text/plain");//message type
			textMessage.setJMSExpiration(2*1000);//message expiration
			textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
			
			queueSender.send(textMessage);
			logger.debug("Message Sent : " + text);
			
			queueSession.close();
			queueConnection.close();
		} 
		catch (Exception e) 
		{
			logger.fatal("ERROR", e);
			return new ResponseEntity<String>("ERROR: " + e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
		
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
	
	@RequestMapping(value="/testCJDRespQueue",method=RequestMethod.POST)
    public ResponseEntity<String> testCJDRespQueue(@ApiParam(value = ""  )  @Valid @RequestBody String text) 
    {
		String putQueueConnectionFactory = SystemConstant.getConstant("REQUEST_CJD_QUEUE_CONNECTION_FACTORY_JNDI");
		String putQueueDestination = SystemConstant.getConstant("RESPONSE_CJD_QUEUE_JNDI");
		
		QueueConnectionFactory qcf;
		Queue cjdQueue;
		try 
		{
			qcf = (QueueConnectionFactory)InitialContext.doLookup(putQueueConnectionFactory);
			
			QueueConnection queueConnection = qcf.createQueueConnection();
			QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			cjdQueue = (Queue)InitialContext.doLookup(putQueueDestination);
			QueueSender queueSender = queueSession.createSender(cjdQueue);
			
			TextMessage textMessage = queueSession.createTextMessage(text);
			textMessage.setJMSType("text/plain");//message type
			textMessage.setJMSExpiration(2*1000);//message expiration
			textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
			
			queueSender.send(textMessage);
			logger.debug("Message Sent : " + text);
			
			queueSession.close();
			queueConnection.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<String>("ERROR", HttpStatus.BAD_REQUEST);
		}
		
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
	
	@RequestMapping(value="/testCJDMQQueue",method=RequestMethod.POST)
    public ResponseEntity<String> testCJDMQQueue(@ApiParam(value = ""  )  @Valid @RequestBody String text) 
    {
		String CJD_MQ_HOST = SystemConfig.getProperty("CJD_MQ_HOST");
		String CJD_MQ_CHANNEL = SystemConfig.getProperty("CJD_MQ_CHANNEL");
		int CJD_MQ_PORT = (SystemConfig.getProperty("CJD_MQ_PORT") != null) ? 1414 : Integer.parseInt(SystemConfig.getProperty("CJD_MQ_PORT"));
		String CJD_MQ_QMGR = SystemConfig.getProperty("CJD_MQ_QMGR");
		String CJD_MQ_REQ_QUEUE = SystemConfig.getProperty("CJD_MQ_REQ_QUEUE");
		
		MQQueueManager qMgr = null;
		MQQueue destQueue = null;
		try 
		{
			Hashtable<String,Object> mqht = new Hashtable<String,Object>();
			mqht.put(CMQC.CHANNEL_PROPERTY, CJD_MQ_CHANNEL);
	        mqht.put(CMQC.HOST_NAME_PROPERTY, CJD_MQ_HOST);
	        mqht.put(CMQC.PORT_PROPERTY, CJD_MQ_PORT);
	        //mqht.put(CMQC.USER_ID_PROPERTY, "");
	        //mqht.put(CMQC.PASSWORD_PROPERTY, "");
	        mqht.put(CMQC.TRANSPORT_PROPERTY, CMQC.TRANSPORT_MQSERIES);
	        
	        logger.debug("CJD_MQ_CHANNEL : "+ CJD_MQ_CHANNEL);
	        logger.debug("CJD_MQ_HOST : "+ CJD_MQ_HOST);
	        logger.debug("CJD_MQ_PORT : "+ CJD_MQ_PORT);
	        logger.debug("CJD_MQ_QMGR : "+ CJD_MQ_QMGR);
	        logger.debug("CJD_MQ_REQ_QUEUE : "+ CJD_MQ_REQ_QUEUE);
	        
			int openOptions = MQConstants.MQOO_OUTPUT; //PUT option
			try
			{
				qMgr = new MQQueueManager(CJD_MQ_QMGR, mqht);
			}
			catch(Exception e)
			{
				logger.error("Fail to connect to MQQueueManager...");
				throw e;
			}
	        logger.debug("qMgr.isConnected : " + qMgr.isConnected());
	        
	        destQueue = qMgr.accessQueue(CJD_MQ_REQ_QUEUE, openOptions);
	        MQMessage mqMsg = new MQMessage();
	        mqMsg.writeUTF(text);
	        MQPutMessageOptions pmo = new MQPutMessageOptions();
	        destQueue.put(mqMsg, pmo);
	        destQueue.close();
	        
	        qMgr.commit();
	        logger.debug("Message Sent : " + text);
		} 
		catch (Exception e) 
		{
			logger.error("Fail to put message to MQ queue : " , e);
			try 
			{
				if(qMgr != null)
				{
					qMgr.backout();
				}
			} 
			catch (MQException e1) 
			{
				logger.error("Fail to rollback : " , e);
			}
		}
		finally
		{
			try
			{
				if(qMgr != null)
				{
			        qMgr.disconnect();
					qMgr.close();
				}
			}
			catch (Exception e) 
			{
				logger.error("Fail to close MQ Connection : " , e);
			}
		}
		
        return new ResponseEntity<String>("OK", HttpStatus.OK);
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
