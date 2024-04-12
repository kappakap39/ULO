package com.eaf.service.module.manual;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.tempuri.KBKInstinctFraudCheckSoapProxy;
import org.tempuri.RequestOnline;
import org.tempuri.ResponseOnline;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.orig.ulo.model.common.ResponseData;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.CISRefDataObject;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.CIS1048O01RequestDataM;
import com.eaf.service.module.model.KBKInstinctFraudCheckRequestDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.eai.cbs1215i01.CBS1215I01Response;
import com.kasikornbank.eai.cbs1215i01.__doServiceResponse_CBS1215I01Response_KBankHeader;
import com.kasikornbank.eai.cis1048o01.CIS1048O01Response;
import com.kasikornbank.eai.cis1048o01.CIS1048O01SoapProxy;
import com.kasikornbank.eai.cis1048o01.CIS1048O01_Type;
import com.kasikornbank.eai.cis1048o01.__doServiceResponse_CIS1048O01Response_KBankHeader;

public class FraudonlinecheckProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(FraudonlinecheckProxy.class);
	
	public final static String serviceId = "KBKInstinctFraudCheck";
	
	public static class requestConstant{
		public static String fraudCheckFlag="F";
		public static String legacyName="E-APP";
		
	}
	
	
	public static class responseConstant{
		public static class Status{
			public static String SUCCESS="00";
		}
		
		
	}
	
	@Override
	public ServiceRequestTransaction requestTransaction() {
		
		KBKInstinctFraudCheckRequestDataM requetData = (KBKInstinctFraudCheckRequestDataM)serviceRequest.getObjectData();	
		
//		ServiceUtil.display(ServiceApplicationDate.getTimestamp(),ServiceUtil.EN,"dd-MM-yyyy HH:mm:ss");
//		
//		DateFormat dl = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		RequestOnline request=new RequestOnline();
		request.setInputString(requetData.getInputString());
		request.setApplicationNo(requetData.getApplicationNo());
		request.setFraudCheckFlag(requestConstant.fraudCheckFlag);
		request.setLegacyName(requestConstant.legacyName);
		request.setApplicationType(requetData.getApplicationType());
		request.setRequestDttm(ServiceUtil.display(ServiceApplicationDate.getTimestamp(),ServiceUtil.EN,"dd-MM-yyyy HH:mm:ss"));
		
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		requestTransaction.serviceInfo(ServiceConstant.OUT, request, serviceRequest);
		return requestTransaction;

	}
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			RequestOnline requestObject = (RequestOnline)requestServiceObject;
			KBKInstinctFraudCheckSoapProxy proxy =new KBKInstinctFraudCheckSoapProxy();
			String endPointUrl = serviceRequest.getEndpointUrl();
			proxy._getDescriptor().setEndpoint(endPointUrl);
			ResponseOnline responseObject = proxy.instinctFraudCheck(requestObject);
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
		ResponseOnline responseObject = (ResponseOnline)serviceTransaction.getServiceTransactionObject();
		
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && !ServiceUtil.empty(responseObject)){
			try{
				
				if(responseConstant.Status.SUCCESS.equals(responseObject.getErrorCode())){	
					serviceResponse.setObjectData(responseObject);
					serviceResponse.setStatusCode(responseConstant.Status.SUCCESS);
				}else{
					serviceResponse.setStatusCode(responseConstant.Status.SUCCESS);
					//com.kasikornbank.eai.cbs1215i01.Error[] Errors = KBankHeader.getErrorVect();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();		
						errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(ResponseData.SystemType.FRAUD);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					
						errorInfo.setErrorCode(responseObject.getErrorCode());
						errorInfo.setErrorDesc(responseObject.getErrorDesc());
						errorInfo.setErrorInformation(responseObject.getErrorDesc());
					
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
		responseTransaction.serviceInfo(ServiceConstant.IN, responseObject, serviceResponse);
		return responseTransaction;
	}
}
