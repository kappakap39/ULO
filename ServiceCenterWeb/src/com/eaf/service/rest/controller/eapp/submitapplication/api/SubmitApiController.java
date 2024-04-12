package com.eaf.service.rest.controller.eapp.submitapplication.api;

import io.swagger.annotations.ApiParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ava.flp.eapp.iib.serializer.DateSerializer;
import com.ava.flp.eapp.submitapplication.model.ApplicationGroup;
import com.ava.flp.eapp.submitapplication.model.KBankHeader;
import com.ava.flp.eapp.submitapplication.model.SubmitApplication;
import com.avalant.wm.model.WorkManagerRequest;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.flp.eapp.util.EAppUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.eapp.mapper.EAppModelMapper;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.proxy.ServiceCenterController;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-12-21T11:36:05.539+07:00")

//@Controller
@RestController
@RequestMapping("/service/FLS-SubmitApplication")
public class SubmitApiController implements SubmitApi {
	private static Logger logger = Logger.getLogger(SubmitApiController.class);
	
	@RequestMapping(value="/submit",method=RequestMethod.POST)
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
		
		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		ServiceCenterController serviceController = new ServiceCenterController();
		ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
			serviceLogRequest.setServiceReqRespId(serviceReqRespId);
			serviceLogRequest.setRefCode(applicationGroupNo);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceDataObject(submitApplicationRequest);
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.ESubmitApplication);
//			serviceLogRequest.setUniqueId(applicationGroupId);
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
		
		try{			
//			ProcessActionInf processActionInf = this.findProcessAction(callAction);
//			if(null!=processActionInf){
//				ESubmitApplicationObject submitEAppObject = new ESubmitApplicationObject();
//				submitEAppObject.setApplicationGroup(applicationGroup);
//				submitEAppObject.setUserId(userId);
//				submitEAppObject.setTransactionId(serviceController.getTransactionId());
//				submitEAppObject.setCallAction(callAction);
//				
//				processActionInf.init(submitEAppObject);
//				ProcessResponse processResponse = (ProcessResponse)processActionInf.processAction();
//				String processResponseResult = processResponse.getStatusCode();
//				logger.debug("processResponseResult >> "+processResponseResult);
//				logger.debug(processResponse);
//				if(ServiceResponse.Status.SUCCESS.equals(processResponseResult)){
//					responseKbankHeader.setStatusCode(ServiceResponse.Status.SUCCESS);
//				}else{
//					responseKbankHeader.setStatusCode(processResponse.getStatusCode());
//					ErrorData errorData = processResponse.getErrorData();
//					if(null != errorData){
//						responseKbankHeader.error(errorData.getErrorCode(),errorData.getErrorDesc());
//						errorMsg = errorData.getErrorInformation();
//					}
//				}
//			}else{
//				logger.error("cannot find action process for : " + callAction);
//				errorMsg= MessageErrorUtil.getText("ERROR_NOT_FOUND_CALL_ACTION");
//				responseKbankHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
//			}
			
			ApplicationGroupDataM uloApplicationGroup = new EAppModelMapper().mapUloModel(null, applicationGroup);
			//set source user id by submit user id
			uloApplicationGroup.setUserId(userId);
			uloApplicationGroup.setSourceUserId(userId);
			uloApplicationGroup.setSource(MConstant.SOURCE_NAME.TABLET);
			
			String WM_ACTION_INITIAL = SystemConstant.getConstant("WM_ACTION_INITIAL");
//			String contentMsg = "";
//			Gson gson = new GsonBuilder().setExclusionStrategies(new ComplexClassExclusionStrategy()).create();
//			try{
//				contentMsg = gson.toJson(uloApplicationGroup);
//			}catch(Exception e){
//				logger.fatal("ERROR ",e);
//				if(null != uloApplicationGroup){
//					contentMsg = uloApplicationGroup.toString();
//				}
//			}
			logger.debug("app date " + uloApplicationGroup.getApplicationDate());
			GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
	        Gson gson = gsonBuilder.create();
	        
			WorkManagerRequest wmRequest = new WorkManagerRequest();
			wmRequest.setWmFn(WM_ACTION_INITIAL);
			wmRequest.setRefId(null);
			wmRequest.setRefCode(uloApplicationGroup.getApplicationGroupNo());
			
			wmRequest.setTaskData(gson.toJson(uloApplicationGroup));
			
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
			serviceLogResponse.setServiceId(ServiceConstant.ServiceId.ESubmitApplication);
//			serviceLogResponse.setUniqueId(applicationGroupId);
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
}
