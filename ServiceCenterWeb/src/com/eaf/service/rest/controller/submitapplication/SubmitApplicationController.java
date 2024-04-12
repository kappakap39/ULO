package com.eaf.service.rest.controller.submitapplication;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.docset.model.InquireDocSetResponse;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.submitapplication.model.SubmitApplicationObjectDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterController;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.application.model.SubmitApplicationRequest;
import com.eaf.service.rest.application.model.SubmitApplicationResponse;
import com.eaf.service.rest.model.KbankError;
import com.eaf.service.rest.model.RequestKBankHeader;
import com.eaf.service.rest.model.ResponseKBankHeader;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

@RestController
@RequestMapping("/service/SubmitApplication")
public class SubmitApplicationController {
	private static Logger logger = Logger.getLogger(SubmitApplicationController.class);
	@RequestMapping(value="/submit",method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<SubmitApplicationResponse> submitApplication(@RequestBody SubmitApplicationRequest submitApplicationRequest){
		String errorMsg = "";
		ResponseKBankHeader responseKbankHeader = new ResponseKBankHeader();
		SubmitApplicationResponse submitApplicationResponse = new SubmitApplicationResponse();
		RequestKBankHeader requestKBankHeader = submitApplicationRequest.getKBankHeader();
		if(null == requestKBankHeader){
			requestKBankHeader = new RequestKBankHeader();
		}
		String userId = requestKBankHeader.getUserId();
		if(Util.empty(userId)){
			userId = SystemConstant.getConstant("SYSTEM_USER");
		}
		logger.debug("userId >> "+userId);
		String qr1 = submitApplicationRequest.getQr1();
		String qr2 = submitApplicationRequest.getQr2();
		String coverPageType = getCoverPageType(qr1);
		logger.debug("qr1 >> "+qr1);
		logger.debug("qr2 >> "+qr2);
		logger.debug("coverPageType >> "+coverPageType);
		String applicationGroupId = "";
		boolean existApplication = true;
		try{
			applicationGroupId = ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupIdByQr2(qr2);
			if(Util.empty(applicationGroupId)){
				applicationGroupId = GenerateUnique.generate(MConstant.SeqNames.ORIG_APPLICATION_GROUP_PK);
				existApplication = false;
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			logger.error("cannot find process apply type.");
			errorMsg= MessageErrorUtil.getText("PRE_FIX_APPLICATION_ERROR_NOT_FOUND_APPLY_TYPE");
			responseKbankHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			existApplication = false;
		}
		logger.debug("applicationGroupId : "+applicationGroupId);		
		logger.debug("existApplication : "+existApplication);
		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		ServiceCenterController serviceController = new ServiceCenterController();
		submitApplicationRequest.setTransactionId(serviceController.getTransactionId());
		ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
			serviceLogRequest.setServiceReqRespId(serviceReqRespId);
			serviceLogRequest.setRefCode(qr2);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceDataObject(submitApplicationRequest);
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.SubmitApplication);
			serviceLogRequest.setUniqueId(applicationGroupId);
			serviceLogRequest.setUserId(userId);
		serviceController.createLog(serviceLogRequest);
		if(null != requestKBankHeader){
			responseKbankHeader.setRqUID(requestKBankHeader.getRqUID());
		}
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		responseKbankHeader.setRsAppId(RqAppId);

		try{				
			ProcessActionInf processActionInf = this.submitApplicationProcess(coverPageType);
			if(null!=processActionInf){
				SubmitApplication submitApplication = new SubmitApplication();
				ServiceResponseDataM serviceResponse = submitApplication.inquireDocSetService(applicationGroupId,submitApplicationRequest,userId);
				String inquireDocSetResult = serviceResponse.getStatusCode();
				logger.debug("inquireDocSetResult >> "+inquireDocSetResult);
				if(null != serviceResponse && ServiceResponse.Status.SUCCESS.equals(inquireDocSetResult)){
					InquireDocSetResponse inquireDocSetResponse =(InquireDocSetResponse)serviceResponse.getObjectData();
						SubmitApplicationObjectDataM submitApplicationM = new SubmitApplicationObjectDataM();
						submitApplicationM.setTransactionId(serviceController.getTransactionId());
						submitApplicationM.setApplicationGroupId(applicationGroupId);
						submitApplicationM.setInquireDocSetResponse(inquireDocSetResponse);
						submitApplicationM.setSubmitApplicationRequest(submitApplicationRequest);
						submitApplicationM.setUserId(userId);
						submitApplicationM.setCoverPageType(coverPageType);
						submitApplicationM.setExistApplication(existApplication);
					processActionInf.init(submitApplicationM);
					ProcessResponse processResponse = (ProcessResponse)processActionInf.processAction();
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
				}else{
					responseKbankHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);	
					ServiceErrorInfo errorData = serviceResponse.getErrorInfo();
					if(null != errorData){
						errorMsg = errorData.getErrorInformation();
					}
				}
			}else{
				logger.error("cannot find process apply type.");
				errorMsg= MessageErrorUtil.getText("PRE_FIX_APPLICATION_ERROR_NOT_FOUND_APPLY_TYPE");
				responseKbankHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			errorMsg = e.getLocalizedMessage();
			responseKbankHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);			
		}
		submitApplicationResponse.setKBankHeader(responseKbankHeader);
		ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
			serviceLogResponse.setServiceReqRespId(serviceReqRespId);
			serviceLogResponse.setRefCode(qr2);
			serviceLogResponse.setActivityType(ServiceConstant.OUT);
			serviceLogResponse.setServiceDataObject(submitApplicationResponse);
			serviceLogResponse.setServiceId(ServiceConstant.ServiceId.SubmitApplication);
			serviceLogResponse.setUniqueId(applicationGroupId);
			serviceLogResponse.setRespCode(responseKbankHeader.getStatusCode());
			serviceLogResponse.setUserId(userId);
			try{
				if(null != responseKbankHeader.getErrorVect()){
					Gson gson = new Gson();
					serviceLogResponse.setRespDesc(gson.toJson(responseKbankHeader.getErrorVect()));
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
				errorMsg = e.getLocalizedMessage();
				responseKbankHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);	
			}
			responseKbankHeader.setErrorVect(this.getKbankEror(RqAppId,errorMsg));
			serviceLogResponse.setErrorMessage(errorMsg);
			serviceController.createLog(serviceLogResponse);
		return ResponseEntity.ok(submitApplicationResponse);
	}
	private String getCoverPageType(String qr1){
		return (!Util.empty(qr1))?qr1.substring(19,21):"";
	}
	private ProcessActionInf submitApplicationProcess(String coverpageType){
		String COVERPAGE_TYPE_NEW = SystemConstant.getConstant("COVERPAGE_TYPE_NEW");
		String CACHE_SUMMIT_APPLICATION_FUNCTION = SystemConstant.getConstant("CACHE_SUMMIT_APPLICATION_FUNCTION");
		ProcessActionInf processActionInf = null; 
		logger.debug("coverpageType >> "+coverpageType);
		try{
			String className = CacheControl.getName(CACHE_SUMMIT_APPLICATION_FUNCTION,coverpageType);			
			if(Util.empty(className)){
				className = CacheControl.getName(CACHE_SUMMIT_APPLICATION_FUNCTION,COVERPAGE_TYPE_NEW);
			}
			logger.debug("className >> "+className);
			processActionInf = (ProcessActionInf)Class.forName(className).newInstance();
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return processActionInf;
	}
	
 private List<KbankError> getKbankEror(String rsAppId,String errs){
	 List<KbankError> kbankErrors = new ArrayList<KbankError>();
	 if(!Util.empty(errs)){
			KbankError kbankError = new KbankError();
			kbankError.setErrorAppId(rsAppId);
			kbankError.setErrorCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
			kbankError.setErrorDesc(errs);
			kbankError.setErrorSeverity(ServiceConstant.ErrorSeverityCode.ERROR);	
			kbankErrors.add(kbankError);
	 }
	 return kbankErrors;
 }
}
