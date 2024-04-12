package com.eaf.service.rest.controller.calloper;

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

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.calloper.dao.UpdateCallOperatorDAO;
import com.eaf.orig.ulo.calloper.dao.UpdateCallOperatorFactory;
import com.eaf.orig.ulo.model.calloper.UpdateCallOperatorRequest;
import com.eaf.orig.ulo.model.calloper.UpdateCallOperatorResponse;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.proxy.ServiceCenterController;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.model.KbankError;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

@RestController
@RequestMapping("/service/UpdateCallOperator")
public class UpdateCallOperatorController {
	private static transient Logger logger = Logger.getLogger(UpdateCallOperatorController.class);	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> updateCallOperator(@RequestBody UpdateCallOperatorRequest updateCallOperatorRequest
			, @RequestHeader HttpHeaders requestHeaders){
		if(null == updateCallOperatorRequest){
			updateCallOperatorRequest = new UpdateCallOperatorRequest();
		}
		updateCallOperatorRequest.setFuncNm(requestHeaders.getFirst("KBank-FuncNm"));
		updateCallOperatorRequest.setRqUID(requestHeaders.getFirst("KBank-RqUID"));
		updateCallOperatorRequest.setRqDt(requestHeaders.getFirst("KBank-RqDt"));
		updateCallOperatorRequest.setRqAppId(requestHeaders.getFirst("KBank-RqAppId"));
		updateCallOperatorRequest.setUserId(requestHeaders.getFirst("KBank-UserId"));
		updateCallOperatorRequest.setTerminalId(requestHeaders.getFirst("KBank-TerminalId"));
		updateCallOperatorRequest.setUserLangPref(requestHeaders.getFirst("KBank-UserLangPref"));
		updateCallOperatorRequest.setCorrID(requestHeaders.getFirst("KBank-CorrID"));		
		logger.debug("updateCallOperatorRequest : "+updateCallOperatorRequest);
		
		String caseId = updateCallOperatorRequest.getCaseID();
		String applicationGroupId = getApplicationGroupId(caseId);
		String userId = requestHeaders.getFirst("KBank-UserId");
		HttpStatus httpStatus = HttpStatus.OK;
		if(Util.empty(userId)){
			userId = SystemConstant.getConstant("SYSTEM_USER");
		}
		
		logger.debug("caseId >> "+caseId);
		logger.debug("applicationGroupId >> "+applicationGroupId);
		
		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		ServiceCenterController serviceController = new ServiceCenterController();
		
		ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
			serviceLogRequest.setServiceReqRespId(serviceReqRespId);
			serviceLogRequest.setRefCode(caseId);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceDataObject(updateCallOperatorRequest);
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.UpdateCallOperator);
			serviceLogRequest.setUniqueId(applicationGroupId);
			serviceLogRequest.setUserId(userId);
		serviceController.createLog(serviceLogRequest);
		
		String RsAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		UpdateCallOperatorResponse updateCallOperatorResponse = new UpdateCallOperatorResponse();	
			updateCallOperatorResponse.setFuncNm(ServiceConstant.ServiceId.UpdateCallOperator);		
			updateCallOperatorResponse.setRqUID(updateCallOperatorRequest.getRqUID());		
			updateCallOperatorResponse.setRsAppId(RsAppId);
			updateCallOperatorResponse.setRsUID(serviceReqRespId);
			updateCallOperatorResponse.setRsDt(ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		String errorMsg = "";
		try{			
			String CallOperator = updateCallOperatorRequest.getCallOperator();
			logger.debug("CallOperator : "+CallOperator);
			if(!Util.empty(applicationGroupId)){
				UpdateCallOperatorDAO UpdateCallOperatorDAO = UpdateCallOperatorFactory.getCallOperatorDAO();
					UpdateCallOperatorDAO.updateCallOperator(applicationGroupId,CallOperator);			
				updateCallOperatorResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
			}else{
				updateCallOperatorResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			errorMsg = e.getLocalizedMessage();
			updateCallOperatorResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			KbankError kbankError = new KbankError();
			kbankError.setErrorAppId(RsAppId);
			kbankError.setErrorCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			kbankError.setErrorDesc(errorMsg);
			kbankError.setErrorSeverity(ServiceConstant.ErrorSeverityCode.ERROR);			
			updateCallOperatorResponse.error(kbankError);
		}
		HttpHeaders httpHeaderResp = new HttpHeaders();
			httpHeaderResp.set("KBank-FuncNm", ServiceConstant.ServiceId.UpdateCallOperator);
			httpHeaderResp.set("KBank-RqUID", ServiceUtil.displayText(requestHeaders.getFirst("KBank-RqUID")));
			httpHeaderResp.set("KBank-RsAppId", ServiceUtil.displayText(RsAppId));
			httpHeaderResp.set("KBank-RsUID", ServiceUtil.displayText(serviceReqRespId));
			httpHeaderResp.set("KBank-RsDt", ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		
		KbankError kbankError = getFirstError(updateCallOperatorResponse.getError());			
		httpHeaderResp.set("KBank-StatusCode", ServiceUtil.displayText(updateCallOperatorResponse.getStatusCode()));
		if(null != kbankError){
			httpHeaderResp.set("KBank-ErrorAppId", ServiceUtil.displayText(kbankError.getErrorAppId()));
			httpHeaderResp.set("KBank-ErrorAppAbbrv", ServiceUtil.displayText(kbankError.getErrorAppAbbrv()));
			httpHeaderResp.set("KBank-ErrorCode", ServiceUtil.displayText(kbankError.getErrorCode()));
			httpHeaderResp.set("KBank-ErrorDesc", ServiceUtil.displayText(kbankError.getErrorDesc()));
			httpHeaderResp.set("KBank-ErrorSeverity", ServiceUtil.displayText(kbankError.getErrorSeverity()));			
		}
				
		ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
			serviceLogResponse.setServiceReqRespId(serviceReqRespId);
			serviceLogResponse.setRefCode(caseId);
			serviceLogResponse.setActivityType(ServiceConstant.OUT);
			serviceLogResponse.setServiceDataObject(updateCallOperatorResponse);
			serviceLogResponse.setServiceId(ServiceConstant.ServiceId.UpdateCallOperator);
			serviceLogResponse.setUniqueId(applicationGroupId);
			serviceLogResponse.setUserId(userId);
			serviceLogResponse.setRespCode(updateCallOperatorResponse.getStatusCode());
			try{
				Gson gson = new Gson();
				if(null != updateCallOperatorResponse.getError()){
					serviceLogResponse.setRespDesc(gson.toJson(updateCallOperatorResponse.getError()));
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
		serviceLogResponse.setErrorMessage(errorMsg);
		serviceController.createLog(serviceLogResponse);
		Gson gson = new Gson();
		return new ResponseEntity<String>(gson.toJson(updateCallOperatorResponse),httpHeaderResp,httpStatus);
	}	
	private String getApplicationGroupId(String setId){
		try{
			return ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupIdByQr2(setId);
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
}
