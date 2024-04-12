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
import com.eaf.service.module.model.CreateKVIAppRequestDataM;
import com.eaf.service.module.model.CreateKVIAppResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationRequest;
import com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationResponse;
import com.kasikornbank.kvi.createkviapp.proxy.ICreateKVIApplicationProxy;
import com.kasikornbank.kvi.createkviapp.proxy.KBankRequestHeader;
import com.kasikornbank.kvi.createkviapp.proxy.KBankResponseError;
import com.kasikornbank.kvi.createkviapp.proxy.KBankResponseHeader;
import com.kasikornbank.kvi.createkviapp.proxy.RequestData;
import com.kasikornbank.kvi.createkviapp.proxy.ResponseData;

public class CreateKVIAppServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(CreateKVIAppServiceProxy.class);	
	public static final String url = "urlWebService";
	public static final String serviceId = "CreateKVIApp";	
//	public static final String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public static final String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public static final String successCode = ServiceCache.getConstant("SUCCESS_CODE");	
	public static class requestConstants{
		public static final String fgAppNo = "fgAppNo";
		public static final String fcDept = "fcDept";
		public static final String fcInputId = "fcInputId";
		public static final String fgRequestor = "fgRequestor";
		public static final String fgRequestorL = "fgRequestorL";
		public static final String fgType = "fgType";
		public static final String fgId = "fgId";
		public static final String fgCisNo = "fgCisNo";
		public static final String fgRequestor1 = "fgRequestor1";
		public static final String fgRequestorL1 = "fgRequestorL1";
		public static final String fgType1 = "fgType1";
		public static final String fgId1 = "fgId1";
		public static final String fgCisNo1 = "fgCisNo1";
		public static final String fgRequestor2 = "fgRequestor2";
		public static final String fgRequestorL2 = "fgRequestorL2";
		public static final String fgType2 = "fgType2";
		public static final String fgId2 = "fgId2";
		public static final String fgCisNo2 = "fgCisNo2";
		public static final String fcBusiness = "fcBusiness";
		public static final String fcBusinessDesc = "fcBusinessDesc";
	}
	public static class responseConstants{
		public static final String fId = "fId";
		public static final String tokenId = "tokenId";
		public static final String fgAppNo = "fgAppNo";
	}
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();	
		CreateKVIApplicationRequest requestObject = new CreateKVIApplicationRequest();
		KBankRequestHeader KBankHeader = new KBankRequestHeader();
			KBankHeader.setFuncNm(serviceId);
			KBankHeader.setUserId(serviceRequest.getUserId());
			String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			KBankHeader.setTerminalId(ServiceCache.getGeneralParam("DEFAULT_TERMINAL_ID"));
			KBankHeader.setUserLangPref(ServiceCache.getGeneralParam("DEFAULT_LANGUAGE"));
			KBankHeader.setRqAppId(RqAppId);
			KBankHeader.setCorrID(serviceRequest.getServiceReqResId());
			KBankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
			KBankHeader.setRqDt(ServiceApplicationDate.getCalendar().toString());			
		CreateKVIAppRequestDataM CreateKVIAppRequest = (CreateKVIAppRequestDataM)serviceRequest.getObjectData();  
			RequestData requestData = new RequestData();
			requestData.setFGAppNo(CreateKVIAppRequest.getFgAppNo());
			requestData.setFCDept(CreateKVIAppRequest.getFcDept());
			requestData.setFCInputID(CreateKVIAppRequest.getFcInputId());
			requestData.setFGRequestor(CreateKVIAppRequest.getFgRequestor());
			requestData.setFGRequestorL(CreateKVIAppRequest.getFgRequestorL());
			requestData.setFGType(CreateKVIAppRequest.getFgType());
			requestData.setFGID(CreateKVIAppRequest.getFgId());
			requestData.setFGCisNo(CreateKVIAppRequest.getFgCisNo());
			requestData.setFGRequestor1(CreateKVIAppRequest.getFgRequestor1());
			requestData.setFGRequestorL1(CreateKVIAppRequest.getFgRequestorL1());
			requestData.setFGType1(CreateKVIAppRequest.getFgType1());
			requestData.setFGID1(CreateKVIAppRequest.getFgId1());
			requestData.setFGCisNo1(CreateKVIAppRequest.getFgCisNo1());
			requestData.setFGRequestor2(CreateKVIAppRequest.getFgRequestor2());
			requestData.setFGType2(CreateKVIAppRequest.getFgType2());
			requestData.setFGID2(CreateKVIAppRequest.getFgId2());
			requestData.setFGCisNo2(CreateKVIAppRequest.getFgCisNo2());
			requestData.setFCBusiness(CreateKVIAppRequest.getFcBusiness());
			requestData.setFCBusinessDesc(CreateKVIAppRequest.getFcBusinessDesc());
			requestData.setPercentShareHolder(CreateKVIAppRequest.getPercentShareHolder());
		requestObject.setRequestData(requestData);
		requestObject.setKBankRequestHeader(KBankHeader);
		requestTransaction.serviceInfo(ServiceConstant.OUT, requestObject, serviceRequest);
		return requestTransaction;
	}

	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CreateKVIApplicationRequest requestTran = (CreateKVIApplicationRequest)requestServiceObject;
			ICreateKVIApplicationProxy proxy = new ICreateKVIApplicationProxy();
			String endPointUrl = getEndpointUrl();
			logger.debug("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endPointUrl);
			CreateKVIApplicationResponse responseObject = proxy.serviceRequest(requestTran);
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
		CreateKVIApplicationResponse responseObject = (CreateKVIApplicationResponse)serviceTransaction.getServiceTransactionObject();
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				KBankResponseHeader KBankHeader = responseObject.getKBankResponseHeader();				
				KBankHeader.setCorrID(serviceResponse.getServiceReqResId());
				if(ServiceResponse.Status.SUCCESS.equals(KBankHeader.getStatusCode())){
					serviceResponse.setObjectData(getCreateKVIAppResponse(responseObject));
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
				}else{
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
					KBankResponseError[] Errors = KBankHeader.getError();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();		
						errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.KVI);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					if(null != Errors && Errors.length > 0){
						KBankResponseError Error = Errors[0];
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
	private CreateKVIAppResponseDataM getCreateKVIAppResponse(CreateKVIApplicationResponse responseObject){
		ResponseData responseData = responseObject.getResponseData();
		CreateKVIAppResponseDataM CreateKVIAppResponse = new CreateKVIAppResponseDataM();
		CreateKVIAppResponse.setfId(responseData.getFID());
//		CreateKVIAppResponse.setFgAppNo(responseData.getFGAppNo());
 		CreateKVIAppResponse.setTokenId(responseData.getTokenID());	
		return CreateKVIAppResponse;
	}
}
