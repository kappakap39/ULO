package com.eaf.service.module.manual;

import java.rmi.RemoteException;

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
import com.eaf.service.module.model.CIS0314I01RequestDataM;
import com.eaf.service.module.model.CIS0314I01ResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.kasikornbank.eai.cis0314i01.CIS0314I01Response;
import com.kasikornbank.eai.cis0314i01.CIS0314I01SoapProxy;
import com.kasikornbank.eai.cis0314i01.CIS0314I01_Type;
import com.kasikornbank.eai.cis0314i01.KYCObj;
import com.kasikornbank.eai.cis0314i01.__doServiceResponse_CIS0314I01Response_CISCustomer;
import com.kasikornbank.eai.cis0314i01.__doServiceResponse_CIS0314I01Response_EAIHeader;
import com.kasikornbank.eai.cis0314i01.__doService_CIS0314I01_CISCustomer;
import com.kasikornbank.eai.cis0314i01.__doService_CIS0314I01_EAIHeader;

public class CIS0314I01ServiceProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(CIS0314I01ServiceProxy.class);
	public final static String url = "urlWebService";
	public final static String serviceId = "CIS0314I01";	
//	public final static String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public final static String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public final static String successCode = ServiceCache.getConstant("SUCCESS_CODE");	
	public static class requestConstant{
		public final static String customerType = "customerType";
		public final static String customerId = "customerId";
	}	
	
	public static class responseContant{
		public final static String customerId = "customerId";
		public final static String customerType = "customerType";
		public final static String thTitleName = "thaiTitleName";
		public final static String thName = "thaiName";
		public final static String thSurname = "thaiSurname";
		public final static String engTitleName = "englishTitleName";
		public final static String engName = "englishName";
		public final static String engSurname = "englishSurname";
		public final static String srcAstCode = "soucreAssetCode";
		public final static String srcAstOthDesc = "soucreAssetOtherDescription";
		public final static String policalPosDesc = "policalPostionDescription";
		public final static String astValAmt = "assetValueAmount";
		public final static String riskReaCode = "riskReasonCode";
		public final static String astValCode = "assetValueCode";
		public final static String astValDesc = "assetValueDescription";
		public final static String riskLevelCode = "riskLevelCode";
	}	
	
	public ServiceRequestTransaction requestTransaction(){
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();	
		CIS0314I01_Type requestObject = new CIS0314I01_Type();
		__doService_CIS0314I01_EAIHeader EAIHeader = new __doService_CIS0314I01_EAIHeader();
			EAIHeader.setServiceId(serviceId);
			EAIHeader.setTransactionId(serviceRequest.getTransactionId());
			EAIHeader.setSourceTransactionId(null);
			String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			EAIHeader.setSourceSystem(RqAppId);
			EAIHeader.setUserId(CisGeneralUtil.displayCISHeaderUserId(serviceRequest.getUserId()));			
		__doService_CIS0314I01_CISCustomer CISCustomer = new __doService_CIS0314I01_CISCustomer();
		CIS0314I01RequestDataM CIS0314I01Request = (CIS0314I01RequestDataM)serviceRequest.getObjectData(); 
			CISCustomer.setTypeCode(CIS0314I01Request.getCustomerType());
			CISCustomer.setNum(CIS0314I01Request.getCustomerId());			
		requestObject.setEAIHeader(EAIHeader);
		requestObject.setCISCustomer(CISCustomer);
		requestTransaction.serviceInfo(ServiceConstant.OUT,requestObject,serviceRequest);
		return requestTransaction;
	}	
	
	public ServiceTransaction serviceTransaction(Object requestServiceObject){
		logger.debug("serviceTransaction..");
		CIS0314I01_Type requestObject = (CIS0314I01_Type)requestServiceObject;
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CIS0314I01SoapProxy proxy = new CIS0314I01SoapProxy();
			String endPointUrl = getEndpointUrl();
			logger.debug("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endPointUrl);
			CIS0314I01Response responseObject = proxy.doService(requestObject);
			serviceTransaction.setServiceTransactionObject(responseObject);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(RemoteException e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}	
	
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction){
		logger.debug("responseTransaction..");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		CIS0314I01Response responseObject = (CIS0314I01Response)serviceTransaction.getServiceTransactionObject();
		CISRefDataObject serviceCISCustomer = (CISRefDataObject)serviceRequest.getServiceData();
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				__doServiceResponse_CIS0314I01Response_EAIHeader EAIHeader = responseObject.getEAIHeader();
				if(ServiceResponse.Status.EAI_SUCCESS_CODE == EAIHeader.getReasonCode()){
					serviceResponse.setObjectData(getCIS0314I01Response(responseObject));
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
		serviceResponse.setServiceData(serviceCISCustomer);
		responseTransaction.serviceInfo(ServiceConstant.IN, responseObject, serviceResponse);
		return responseTransaction;
	}	
	
	private CIS0314I01ResponseDataM getCIS0314I01Response(CIS0314I01Response responseObject){
		CIS0314I01ResponseDataM CIS0314I01Response = new CIS0314I01ResponseDataM();
		__doServiceResponse_CIS0314I01Response_CISCustomer CISCustomer = responseObject.getCISCustomer();
		if(CISCustomer!=null){
			KYCObj KYCObj = CISCustomer.getKYCObj();
			if(null==KYCObj) {
				KYCObj = new KYCObj();
			}
			CIS0314I01Response.setCustomerId(CISCustomer.getNum());
			CIS0314I01Response.setCustomerType(CISCustomer.getTypeCode());
			CIS0314I01Response.setThaiTitleName(CISCustomer.getThTitle());
			CIS0314I01Response.setThaiName(CISCustomer.getThFstName());
			CIS0314I01Response.setThaiSurname(CISCustomer.getThLstName());
			CIS0314I01Response.setEnglishTitleName(CISCustomer.getEngTitle());
			CIS0314I01Response.setEnglishName(CISCustomer.getEngFstName());
			CIS0314I01Response.setEnglishSurname(CISCustomer.getEngLstName());
			CIS0314I01Response.setSoucreAssetCode(KYCObj.getSrcAsstCode());
			CIS0314I01Response.setSoucreAssetOtherDescription(KYCObj.getSrcAsstOthDesc());
			CIS0314I01Response.setPolicalPostionDescription(KYCObj.getPolitcnPosiDesc());
			CIS0314I01Response.setAssetValueAmount(KYCObj.getValAsstAmt());
			CIS0314I01Response.setRiskReasonCode(KYCObj.getRiskResnCode());
			CIS0314I01Response.setAssetValueCode(KYCObj.getValAsstCode());
			CIS0314I01Response.setAssetValueDescription(KYCObj.getValAsstDesc());
			CIS0314I01Response.setRiskLevelCode(KYCObj.getRiskLevCode());			
		}	
		return CIS0314I01Response;
	}	
}
