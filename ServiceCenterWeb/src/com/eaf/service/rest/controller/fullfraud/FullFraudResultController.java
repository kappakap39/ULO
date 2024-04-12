package com.eaf.service.rest.controller.fullfraud;

import java.io.StringReader;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.pega.UpdateApprovalStatus;
import com.eaf.inf.batch.ulo.pega.InfBatchUpdateApprovalStatus;
import com.eaf.inf.batch.ulo.pega.model.CSVContentDataM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusData;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequest;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterController;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.manual.UpdateApprovalStatusServiceProxy;
import com.eaf.service.module.model.FullFraudInfoDataM;
import com.eaf.service.module.model.FullFraudResultRequestDataM;
import com.eaf.service.module.model.FullFraudResultResponseDataM;
import com.eaf.service.rest.controller.fullfraud.action.FailFullFraudAction;
import com.eaf.service.rest.controller.fullfraud.action.PassFullFraudAction;
import com.eaf.service.rest.model.KbankError;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

@RestController
@RequestMapping("/service/FullFraudResult")
public class FullFraudResultController {
	private static transient Logger logger = Logger.getLogger(FullFraudResultController.class);	
	
	@RequestMapping(value="/FullFraudResult", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> fullFraudResult(@RequestBody FullFraudResultRequestDataM fullFraudResultRequest
			, @RequestHeader HttpHeaders requestHeaders){
		
		if(null == fullFraudResultRequest){
			fullFraudResultRequest = new FullFraudResultRequestDataM();
		}
		fullFraudResultRequest.setFuncNm(requestHeaders.getFirst("KBank-FuncNm"));
		fullFraudResultRequest.setRqUID(requestHeaders.getFirst("KBank-RqUID"));
		fullFraudResultRequest.setRqDt(requestHeaders.getFirst("KBank-RqDt"));
		fullFraudResultRequest.setRqAppId(requestHeaders.getFirst("KBank-RqAppId"));
		fullFraudResultRequest.setUserId(requestHeaders.getFirst("KBank-UserId"));
		fullFraudResultRequest.setTerminalId(requestHeaders.getFirst("KBank-TerminalId"));
		fullFraudResultRequest.setUserLangPref(requestHeaders.getFirst("KBank-UserLangPref"));
		fullFraudResultRequest.setCorrID(requestHeaders.getFirst("KBank-CorrID"));			
		
		logger.debug("fullFraudResultRequest : "+fullFraudResultRequest);
		
		String applicationGroupNo  = fullFraudResultRequest.getApplicationGroupNo();
		String applicationGroupId = getApplicationGroupId(applicationGroupNo);
		String userId = requestHeaders.getFirst("KBank-UserId");
		HttpStatus httpStatus = HttpStatus.OK;
		if(Util.empty(userId)){
			// TODO: Define system user id for full fraud
			userId = SystemConstant.getConstant("SYSTEM_USER");
		}
		logger.debug("applicationGroupNo >> "+applicationGroupNo);
		logger.debug("applicationGroupId >> "+applicationGroupId);
				
		// Create outbound entry in SERVICE_REQ_RESP
		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		logger.debug("serviceReqRespId >> "+serviceReqRespId);
		// TODO: Do we need the follow code?
//		ServiceCenterController serviceController = new ServiceCenterController();		
//		ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
//			serviceLogRequest.setServiceReqRespId(serviceReqRespId);
//			serviceLogRequest.setRefCode(applicationGroupNo);
//			serviceLogRequest.setActivityType(ServiceConstant.IN);
//			serviceLogRequest.setServiceDataObject(fullFraudResultRequest);
//			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.FullFraudResult);
//			serviceLogRequest.setUniqueId(applicationGroupId);
//			serviceLogRequest.setUserId(userId);
//		serviceController.createLog(serviceLogRequest);
		
		// Prepare response
		String RsAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		FullFraudResultResponseDataM fullFraudResultResponse = new FullFraudResultResponseDataM();
			fullFraudResultResponse.setFuncNm(ServiceConstant.ServiceId.FullFraudResult);		
			fullFraudResultResponse.setRqUID(requestHeaders.getFirst("KBank-RqUID"));		
			fullFraudResultResponse.setRsAppId(RsAppId);
			fullFraudResultResponse.setRsUID(serviceReqRespId);
			fullFraudResultResponse.setRsDt(ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		
		String errorMsg = "";
		String processResultCode;
		ProcessActionResponse processResponse;
		try{
			if(!Util.empty(applicationGroupId)){
				// Create object for passing parameters to action class
				FullFraudInfoDataM fraudInfo = new FullFraudInfoDataM();
				fraudInfo.setApplicationGroupId(applicationGroupId);
				fraudInfo.setApplicationGroupNo(fullFraudResultRequest.getApplicationGroupNo());
				fraudInfo.setProduct(fullFraudResultRequest.getProduct());
				fraudInfo.setAsOfDate(fullFraudResultRequest.getAsOfDate());
				fraudInfo.setResult(fullFraudResultRequest.getResult());
				fraudInfo.setUserId(userId);
				fraudInfo.setRequestId(serviceReqRespId);
				String actionType = "";
				if ("K".equals(fraudInfo.getResult())) {
					actionType = "FailFullFraudAction";
					ProcessActionInf processAction = new FailFullFraudAction();
					processAction.init(fraudInfo);
					processResponse = (ProcessActionResponse)processAction.processAction();
					processResultCode = processResponse.getResultCode();
					logger.debug("applicationGroupId : "+applicationGroupId);
					logger.debug("processResultCode : "+processResultCode);
					fullFraudResultResponse.setStatusCode(processResultCode);
				} else {
					actionType = "PassFullFraudAction";
					ProcessActionInf processAction = new PassFullFraudAction();
					processAction.init(fraudInfo);
					processResponse = (ProcessActionResponse)processAction.processAction();
					processResultCode = processResponse.getResultCode();
					logger.debug("applicationGroupId : "+applicationGroupId);
					logger.debug("processResultCode : "+processResultCode);
					fullFraudResultResponse.setStatusCode(processResultCode);
				}
				if(!ServiceResponse.Status.SUCCESS.equals(processResultCode)){
					errorMsg = processResponse.getErrorMsg();
					logger.debug("ProcessAction " + actionType + " is done with Error : " + errorMsg);
					KbankError kbankError = new KbankError();
						kbankError.setErrorAppId(RsAppId);
						kbankError.setErrorCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
						kbankError.setErrorDesc(errorMsg);
						kbankError.setErrorSeverity(ServiceConstant.ErrorSeverityCode.ERROR);			
					fullFraudResultResponse.error(kbankError);
				}					
			}else{
				errorMsg = "Result from Full Fraud has invalid applicationGroupNo: " + applicationGroupNo + ", applicationGroupId: " + applicationGroupId;
				logger.error(errorMsg);
				fullFraudResultResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				KbankError kbankError = new KbankError();
					kbankError.setErrorAppId(RsAppId);
					kbankError.setErrorCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					kbankError.setErrorDesc(errorMsg);
					kbankError.setErrorSeverity(ServiceConstant.ErrorSeverityCode.ERROR);			
				fullFraudResultResponse.error(kbankError);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			errorMsg = e.getLocalizedMessage();
			fullFraudResultResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			KbankError kbankError = new KbankError();
				kbankError.setErrorAppId(RsAppId);
				kbankError.setErrorCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				kbankError.setErrorDesc(errorMsg);
				kbankError.setErrorSeverity(ServiceConstant.ErrorSeverityCode.ERROR);			
			fullFraudResultResponse.error(kbankError);
		}
		
		HttpHeaders httpHeaderResp = new HttpHeaders();
			httpHeaderResp.set("KBank-FuncNm", ServiceConstant.ServiceId.FollowUpResult);
			httpHeaderResp.set("KBank-RqUID", ServiceUtil.displayText(requestHeaders.getFirst("KBank-RqUID")));
			httpHeaderResp.set("KBank-RsAppId", ServiceUtil.displayText(RsAppId));
			httpHeaderResp.set("KBank-RsUID", ServiceUtil.displayText(serviceReqRespId));
			httpHeaderResp.set("KBank-RsDt", ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		
		KbankError kbankError = getFirstError(fullFraudResultResponse.getError());			
		httpHeaderResp.set("KBank-StatusCode", ServiceUtil.displayText(fullFraudResultResponse.getStatusCode()));
		if(null != kbankError){
			httpHeaderResp.set("KBank-ErrorAppId", ServiceUtil.displayText(kbankError.getErrorAppId()));
			httpHeaderResp.set("KBank-ErrorAppAbbrv", ServiceUtil.displayText(kbankError.getErrorAppAbbrv()));
			httpHeaderResp.set("KBank-ErrorCode", ServiceUtil.displayText(kbankError.getErrorCode()));
			httpHeaderResp.set("KBank-ErrorDesc", ServiceUtil.displayText(kbankError.getErrorDesc()));
			httpHeaderResp.set("KBank-ErrorSeverity", ServiceUtil.displayText(kbankError.getErrorSeverity()));			
		}
		
		// Create inbound entry in SERVICE_REQ_RESP
//		ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
//			serviceLogResponse.setServiceReqRespId(serviceReqRespId);
//			serviceLogResponse.setRefCode(applicationGroupNo);
//			serviceLogResponse.setActivityType(ServiceConstant.OUT);
//			serviceLogResponse.setServiceDataObject(fullFraudResultResponse);
//			serviceLogResponse.setServiceId(ServiceConstant.ServiceId.FullFraudResult);
//			serviceLogResponse.setUniqueId(applicationGroupId);
//			serviceLogResponse.setUserId(userId);
//			serviceLogResponse.setRespCode(fullFraudResultResponse.getStatusCode());
//			try{
//				Gson gson = new Gson();
//				if(null != fullFraudResultResponse.getError()){
//					serviceLogResponse.setRespDesc(gson.toJson(fullFraudResultResponse.getError()));
//				}
//			}catch(Exception e){
//				logger.fatal("ERROR",e);
//			}
//		logger.debug("errorMsg >> "+errorMsg);
//		serviceLogResponse.setErrorMessage(errorMsg);
//		serviceController.createLog(serviceLogResponse);
		Gson gson = new Gson();
		return new ResponseEntity<String>(gson.toJson(fullFraudResultResponse),httpHeaderResp,httpStatus);
	}		
	
	private String getApplicationGroupId(String applicationGroupNo){
		try{
			return ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupIdByQr2(applicationGroupNo);
		}catch(Exception e){
			return null;
		}
	}
	
	public KbankError getFirstError(ArrayList<KbankError> errorList){
		KbankError kbankError = null;
		if(errorList != null && errorList.size() > 0){
			errorList.get(0);
		}
		return kbankError;
	}
	
	public static String UpdateApprovalStatusFullFruad(ApplicationGroupDataM applicationGroup)
	{
		String updateApprovalStatusResult = "";
		String SYSTEM_USER = SystemConstant.getConstant("SYSTEM_USER");
		String isVetoEligible = applicationGroup.getIsVetoEligible();
		if(MConstant.FLAG.YES.equals(isVetoEligible))
		{
			ArrayList<ApplicationGroupDataM> applicationGroups = new ArrayList<ApplicationGroupDataM>();
				applicationGroups.add(applicationGroup);
			ArrayList<CSVContentDataM> csvContents = InfBatchUpdateApprovalStatus.mapCSVContent(applicationGroups,MConstant.FLAG.NO,isVetoEligible);
			UpdateApprovalStatusRequest updateApprovalRequest = new UpdateApprovalStatusRequest();
			updateApprovalRequest.setCSVContent(InfBatchUpdateApprovalStatus.getCSVContent(csvContents));					
			String UPDATE_APPROVAL_STATUS_URL = SystemConfig.getProperty("UPDATE_APPROVAL_STATUS_URL");
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(UpdateApprovalStatusServiceProxy.serviceId);
				serviceRequest.setUserId(SYSTEM_USER);
				serviceRequest.setEndpointUrl(UPDATE_APPROVAL_STATUS_URL);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setObjectData(updateApprovalRequest);
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());					
			try
			{
				ServiceCenterProxy proxy = new ServiceCenterProxy();
				ServiceResponseDataM serviceResponse = proxy.requestService(serviceRequest);
				updateApprovalStatusResult = serviceResponse.getStatusCode();
			}
			catch(Exception e)
			{
				updateApprovalStatusResult = ServiceResponse.Status.SYSTEM_EXCEPTION;
				e.printStackTrace();
			}
			
			logger.debug("updateApprovalStatusResult >> "+updateApprovalStatusResult);
		}
		return updateApprovalStatusResult;
	}
}

 