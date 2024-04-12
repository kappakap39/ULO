package com.eaf.service.module.manual;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.EditKVIAppRequestDataM;
import com.eaf.service.module.model.EditKVIAppResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.kvi.editkviapp.proxy.EditKVIApplicationRequest;
import com.kasikornbank.kvi.editkviapp.proxy.EditKVIApplicationResponse;
import com.kasikornbank.kvi.editkviapp.proxy.IEditKVIApplicationProxy;
import com.kasikornbank.kvi.editkviapp.proxy.KBankRequestHeader;
import com.kasikornbank.kvi.editkviapp.proxy.KBankResponseHeader;
import com.kasikornbank.kvi.editkviapp.proxy.RequestData;
import com.kasikornbank.kvi.editkviapp.proxy.ResponseData;

public class EditKVIAppServiceProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(EditKVIAppServiceProxy.class);
	public static final String url = "urlWebService";
	public static final String serviceId = "EditKVIApp";
//	public static final String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public static final String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public static final String successCode = ServiceCache.getConstant("SUCCESS_CODE");	
	public static class requestConstants{
		public static final String fId = "fId";
	}
	public static class responseConstants{
		public static final String fId = "fId";
		public static final String tokenId = "tokenId";
	}
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("request transaction");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();	
		EditKVIApplicationRequest requestObject = new EditKVIApplicationRequest();
		KBankRequestHeader KBankHeader = new KBankRequestHeader();
			KBankHeader.setFuncNm(serviceId);
			KBankHeader.setUserId(serviceRequest.getUserId());
			String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			KBankHeader.setRqAppId(RqAppId);
			KBankHeader.setCorrID(serviceRequest.getServiceReqResId());
			KBankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
			KBankHeader.setRqDt(ServiceApplicationDate.getCalendar().toString());			
		EditKVIAppRequestDataM EditKVIAppRequest = (EditKVIAppRequestDataM)serviceRequest.getObjectData();
			RequestData requestData = new RequestData();
			requestData.setFID(EditKVIAppRequest.getfId());			
		requestObject.setKBankRequestHeader(KBankHeader);
		requestObject.setRequestData(requestData);
		requestTransaction.serviceInfo(ServiceConstant.OUT, requestObject, serviceRequest);
		return requestTransaction;
	}
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			EditKVIApplicationRequest requestTran = (EditKVIApplicationRequest) requestServiceObject;
			IEditKVIApplicationProxy proxy = new IEditKVIApplicationProxy();
			String endPointUrl = getEndpointUrl();
			logger.debug("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endPointUrl);
			EditKVIApplicationResponse responseObject = proxy.serviceRequest(requestTran);
			serviceTransaction.setServiceTransactionObject(responseObject);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		logger.debug("responseTransaction..");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		EditKVIApplicationResponse responseObject = (EditKVIApplicationResponse)serviceTransaction.getServiceTransactionObject();
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				KBankResponseHeader KBankHeader = responseObject.getKBankResponseHeader();
				if(ServiceResponse.Status.SUCCESS.equals(KBankHeader.getStatusCode())){
					serviceResponse.setObjectData(getEditKVIAppResponse(responseObject));
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
				}else{
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
					com.kasikornbank.kvi.editkviapp.proxy.KBankResponseError[] Errors = KBankHeader.getError();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
					errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.KVI);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					if(null != Errors && Errors.length > 0){
						com.kasikornbank.kvi.editkviapp.proxy.KBankResponseError Error = Errors[0];
						errorInfo.setErrorCode(Error.getErrorCode());
						errorInfo.setErrorDesc(Error.getErrorDesc());
						errorInfo.setErrorInformation(new Gson().toJson(Errors));
					}
					serviceResponse.setErrorInfo(errorInfo);
				}
			}catch(Exception e){					
				serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
				serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		responseTransaction.serviceInfo(ServiceConstant.IN, responseObject, serviceResponse);
		return responseTransaction;
	}
	private EditKVIAppResponseDataM getEditKVIAppResponse(EditKVIApplicationResponse responseObject){
		ResponseData responseData = responseObject.getResponseData();				
		EditKVIAppResponseDataM EditKVIAppResponse = new EditKVIAppResponseDataM();
			EditKVIAppResponse.setfId(responseData.getFID());
			EditKVIAppResponse.setTokenId(responseData.getTokenID());	
		return EditKVIAppResponse;
	}
}
