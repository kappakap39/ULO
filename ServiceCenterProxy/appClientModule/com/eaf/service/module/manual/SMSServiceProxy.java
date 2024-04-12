package com.eaf.service.module.manual;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
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
import com.eaf.service.module.model.SMSRequestDataM;
import com.eaf.service.module.model.SMSResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.kbank.sms.smsservice.CardLinkElement;
import com.kbank.sms.smsservice.SMSRequestElement;
import com.kbank.sms.smsservice.SMSResponseElement;
import com.kbank.sms.smsservice.SMSServiceStdWSProxy;

public class SMSServiceProxy extends ServiceControlHelper implements ServiceControl  {
	private static transient Logger logger = Logger.getLogger(SMSServiceProxy.class);
	public final static String serviceId = "SMSServiceWS";
	public final static String url = "urlWebService";
//	public final static String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public final static String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public final static String successCode = ServiceCache.getConstant("SUCCESS_CODE");	
	public static class requestConstants{
		public final static String mobileNoElement = "mobileNoElement";
		public final static String msg = "msg";
		public final static String templateId = "templateId";
		public final static String smsLanguage = "smsLanguage";
		public final static String departmentCode = "departmentCode";
		public final static String priority = "priority";
		public final static String messageType = "messageType";
		public final static String clientId = "clientId";
	}	
	public static class responseConstants{
		public final static String responseCode = "responseCode";
		public final static String responseDetail = "responseDetail";
	}
	@Override
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		SMSRequestElement smsRequestElement = new SMSRequestElement();
		SMSRequestDataM SMSRequest = (SMSRequestDataM)serviceRequest.getObjectData();
		if(!ServiceUtil.empty(SMSRequest.getMobileNoElement())){
			smsRequestElement.getMobileNoElement().addAll(SMSRequest.getMobileNoElement());	
		}
		smsRequestElement.setPriority(SMSRequest.getPriority());
		smsRequestElement.setClientId(ServiceCache.getProperty("SMS_SERVICE_CLIENT_ID"));			
		CardLinkElement cardLink = new CardLinkElement();
			cardLink.setMsg(SMSRequest.getMsg());
			cardLink.setTemplateId(Integer.parseInt(ServiceCache.getProperty("SMS_SERVICE_TEMPLATE_ID")));
			cardLink.setSmsLanguage(SMSRequest.getSmsLanguage());
			cardLink.setDepartmentCode(ServiceCache.getProperty("SMS_SERVICE_DEPARTMENTCODE"));
//			cardLink.setMessageType(SMSRequest.getMessageType());
		smsRequestElement.setCardLinkElement(cardLink);	
		requestTransaction.serviceInfo(ServiceConstant.OUT,smsRequestElement,serviceRequest);
		return requestTransaction;
	}
	
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			SMSRequestElement smsRequestElement =(SMSRequestElement)requestServiceObject;
			SMSServiceStdWSProxy smsServiceProxy = new SMSServiceStdWSProxy();
			String endPointUrl = serviceRequest.getEndpointUrl();
		    logger.info("endPointUrl >> "+endPointUrl);
			smsServiceProxy._getDescriptor().setEndpoint(endPointUrl);		
			SMSResponseElement responseObject = smsServiceProxy.sendSMS(smsRequestElement);
			serviceTransaction.setServiceTransactionObject(responseObject);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}
	
	@Override
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction){
		logger.debug("responseTransaction..");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		SMSResponseElement responseObject = (SMSResponseElement)serviceTransaction.getServiceTransactionObject();
		if(ServiceConstant.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				if(ServiceConstant.Status.SMS_SUCCESS_CODE == responseObject.getResponseCode()){
					SMSResponseDataM SMSResponse = new SMSResponseDataM();
						SMSResponse.setResponseCode(responseObject.getResponseCode());
						SMSResponse.setResponseDetail(responseObject.getResponseDetail());
					serviceResponse.setObjectData(SMSResponse);
					serviceResponse.setStatusCode(ServiceConstant.Status.SUCCESS);
				}else{
					SMSResponseDataM SMSResponse = new SMSResponseDataM();
						SMSResponse.setResponseCode(responseObject.getResponseCode());
						SMSResponse.setResponseDetail(responseObject.getResponseDetail());
					serviceResponse.setObjectData(SMSResponse);
					serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();		
					errorInfo.setServiceId(serviceId);
					errorInfo.setErrorSystem(ResponseData.SystemType.SMS);
					errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
					errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
						errorInfo.setErrorCode(String.valueOf(responseObject.getResponseCode()));
						errorInfo.setErrorDesc(responseObject.getResponseDetail());
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
		responseTransaction.serviceInfo(ServiceConstant.IN,responseObject,serviceResponse);
		return responseTransaction;
	}
}
