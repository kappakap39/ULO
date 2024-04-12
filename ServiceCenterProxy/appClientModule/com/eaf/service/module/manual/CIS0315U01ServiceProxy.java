package com.eaf.service.module.manual;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.CISRefDataObject;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.util.CisGeneralUtil;
import com.eaf.service.module.model.CIS0315U01RequestDataM;
import com.eaf.service.module.model.CIS0315U01ResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.kasikornbank.eai.cis0315u01.CIS0315U01Response;
import com.kasikornbank.eai.cis0315u01.CIS0315U01SoapProxy;
import com.kasikornbank.eai.cis0315u01.CIS0315U01_Type;
import com.kasikornbank.eai.cis0315u01.KYCObj;
import com.kasikornbank.eai.cis0315u01.__doServiceResponse_CIS0315U01Response_CISCustomer;
import com.kasikornbank.eai.cis0315u01.__doServiceResponse_CIS0315U01Response_EAIHeader;
import com.kasikornbank.eai.cis0315u01.__doService_CIS0315U01_CISCustomer;
import com.kasikornbank.eai.cis0315u01.__doService_CIS0315U01_EAIHeader;

public class CIS0315U01ServiceProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(CIS0315U01ServiceProxy.class);
	public static final String url = "urlWebService";
	public static final String serviceId = "CIS0315U01";
//	public static final String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public static final String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public static final String successCode = ServiceCache.getConstant("SUCCESS_CODE");	
	public static class requestConstants{
		public static final String cusId = "customerId";
		public static final String srcAsstCode = "soucreAssetCode";
		public static final String srcAsstOthDesc = "soucreAssetOtherDescription";
		public static final String policalPosDesc = "policalPositionDescription";
		public static final String valAsstAmt = "assetValueAmount";
		public static final String valAsstCode = "assetValueCode";
	}
	
	public static class responseConstants{
		public static final String cusId = "customerId";
	}	
	
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		CIS0315U01_Type requestObject = new CIS0315U01_Type();
		__doService_CIS0315U01_EAIHeader EAIHeader = new __doService_CIS0315U01_EAIHeader();
			EAIHeader.setServiceId(serviceId);
			EAIHeader.setTransactionId(serviceRequest.getTransactionId());
			EAIHeader.setSourceTransactionId(null);
			String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			EAIHeader.setSourceSystem(RqAppId);
			EAIHeader.setUserId(CisGeneralUtil.displayCISHeaderUserId(serviceRequest.getUserId()));			
		CIS0315U01RequestDataM CIS0315U01Request = (CIS0315U01RequestDataM)serviceRequest.getObjectData();
		__doService_CIS0315U01_CISCustomer CISCustomer = new __doService_CIS0315U01_CISCustomer();
		CISCustomer.setNum(CIS0315U01Request.getCustomerId());
		KYCObj KYCObj = new KYCObj();
			KYCObj.setSrcAsstCode(CIS0315U01Request.getSoucreAssetCode());
			KYCObj.setSrcAsstOthDesc(CIS0315U01Request.getSoucreAssetOtherDescription());
			KYCObj.setPolitcnPosiDesc(CIS0315U01Request.getPolicalPositionDescription());
			KYCObj.setValAsstAmt(CIS0315U01Request.getAssetValueAmount());
			KYCObj.setValAsstCode(CIS0315U01Request.getAssetValueCode());
		CISCustomer.setKYCObj(KYCObj);			
		requestObject.setEAIHeader(EAIHeader);
		requestObject.setCISCustomer(CISCustomer);	
		requestTransaction.serviceInfo(ServiceConstant.OUT,requestObject,serviceRequest);
		return requestTransaction;
	}
	
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		CIS0315U01_Type requestObject = (CIS0315U01_Type)requestServiceObject;
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CIS0315U01SoapProxy proxy = new CIS0315U01SoapProxy();
			String endPointUrl = getEndpointUrl();
			logger.debug("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endPointUrl);
			CIS0315U01Response responseObject = proxy.doService(requestObject);
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
		CIS0315U01Response responseObject = (CIS0315U01Response)serviceTransaction.getServiceTransactionObject();
		CISRefDataObject personalRefData = (CISRefDataObject)serviceRequest.getServiceData();
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				__doServiceResponse_CIS0315U01Response_EAIHeader EAIHeader = responseObject.getEAIHeader();
				if(ServiceResponse.Status.EAI_SUCCESS_CODE == EAIHeader.getReasonCode()){
					serviceResponse.setObjectData(getCIS0315U01Response(responseObject));
					serviceResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
				}else{
					serviceResponse.setStatusCode(String.valueOf(EAIHeader.getReasonCode()));
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();		
						errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.CIS);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
						errorInfo.setErrorCode(String.valueOf(EAIHeader.getReasonCode()));
						errorInfo.setErrorDesc(EAIHeader.getReasonDesc());
					serviceResponse.setErrorInfo(errorInfo);
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
				serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
				serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		serviceResponse.setServiceData(personalRefData);
		responseTransaction.serviceInfo(ServiceConstant.IN, responseObject, serviceResponse);
		return responseTransaction;
	}
	
	private CIS0315U01ResponseDataM getCIS0315U01Response(CIS0315U01Response responseObject){
		__doServiceResponse_CIS0315U01Response_CISCustomer CISCustomer = responseObject.getCISCustomer();
		CIS0315U01ResponseDataM CIS0315U01Response = new CIS0315U01ResponseDataM();
		CIS0315U01Response.setCustomerId(CISCustomer.getNum());
		return CIS0315U01Response;
	}
}
