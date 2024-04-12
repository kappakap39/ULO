package com.eaf.service.module.manual;

import java.util.ArrayList;

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
import com.eaf.service.module.model.CIS0222I01RequestDataM;
import com.eaf.service.module.model.CIS0222I01ResponseDataM;
import com.eaf.service.module.model.CISZipCodeDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.kasikornbank.eai.cis0222i01.CIS0222I01Response;
import com.kasikornbank.eai.cis0222i01.CIS0222I01SoapProxy;
import com.kasikornbank.eai.cis0222i01.CIS0222I01_Type;
import com.kasikornbank.eai.cis0222i01._____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode;
import com.kasikornbank.eai.cis0222i01._____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode;
import com.kasikornbank.eai.cis0222i01.____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect;
import com.kasikornbank.eai.cis0222i01.___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj;
import com.kasikornbank.eai.cis0222i01.___doService_CIS0222I01_CISCustomer_zipCodesObj;
import com.kasikornbank.eai.cis0222i01.__doServiceResponse_CIS0222I01Response_CISCustomer;
import com.kasikornbank.eai.cis0222i01.__doServiceResponse_CIS0222I01Response_EAIHeader;
import com.kasikornbank.eai.cis0222i01.__doService_CIS0222I01_CISCustomer;
import com.kasikornbank.eai.cis0222i01.__doService_CIS0222I01_EAIHeader;

public class CIS0222I01ServiceProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(CIS0222I01ServiceProxy.class);
	public final static String url = "urlWebService";
	public final static String serviceId = "CIS0222I01";
//	public final static String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public final static String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public final static String successCode = ServiceCache.getConstant("SUCCESS_CODE");	
	public static class requestConstants{
		public final static String tumbolName = "tumbolName";
		public final static String amphurName = "amphurName";
		public final static String provinceName = "provinceName";
	}	
	public static class responseConstants{
		public final static String totalResult = "totalResult";
		public class addressInfomationList{
			public final static String amphurDescription = "amphurDescription";
			public final static String lastMaintenanceDate = "lastMaintenanceDate";
			public final static String provinceDescription = "provinceDescription";
			public final static String referenceNumber = "referenceNumber";
			public final static String tumbolDescription = "tumbolDescription";
			public final static String userId = "userId";
			public final static String zipCode = "zipCode";
		}
	}	
	
	@Override
	public ServiceRequestTransaction requestTransaction(){
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();		
		CIS0222I01_Type cisRequestElement = new CIS0222I01_Type();
		__doService_CIS0222I01_EAIHeader EAIHeader = new __doService_CIS0222I01_EAIHeader();
			EAIHeader.setServiceId(serviceId);
			EAIHeader.setTransactionId(serviceRequest.getTransactionId());
			EAIHeader.setSourceTransactionId(null);
			String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			EAIHeader.setSourceSystem(RqAppId);
			EAIHeader.setUserId(ServiceCache.getGeneralParam("KBANK_USER_ID"));
		CIS0222I01RequestDataM CIS0222I01Request = (CIS0222I01RequestDataM)serviceRequest.getObjectData(); 
		_____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode cisZipCode = new _____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode();
			cisZipCode.setTumbolDesc(CIS0222I01Request.getTumbol());
			cisZipCode.setAmphurDesc(CIS0222I01Request.getAmphur());
			cisZipCode.setProvinceDesc(CIS0222I01Request.getProvince());
		__doService_CIS0222I01_CISCustomer CISCustomer = new __doService_CIS0222I01_CISCustomer();
		___doService_CIS0222I01_CISCustomer_zipCodesObj zipCodesObj = new ___doService_CIS0222I01_CISCustomer_zipCodesObj();
		____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect zipCodesVect = new ____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect();			
			zipCodesVect.setCISZipCode(cisZipCode);
			zipCodesObj.setZipCodesVect(zipCodesVect);
			CISCustomer.setZipCodesObj(zipCodesObj);
		cisRequestElement.setCISCustomer(CISCustomer);
		cisRequestElement.setEAIHeader(EAIHeader);	
		requestTransaction.serviceInfo(ServiceConstant.OUT, cisRequestElement, serviceRequest);
		return requestTransaction;
	}	
	
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		CIS0222I01_Type requestObject = (CIS0222I01_Type)requestServiceObject;
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CIS0222I01SoapProxy proxy = new CIS0222I01SoapProxy();
			String endPointUrl = getEndpointUrl();
			logger.debug("endPointUrl >> "+endPointUrl);
				proxy.setEndpoint(endPointUrl);
			CIS0222I01Response responseObject = proxy.doService(requestObject);
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
		logger.debug("response transaction");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		CIS0222I01Response responseObject = (CIS0222I01Response)serviceTransaction.getServiceTransactionObject();
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				__doServiceResponse_CIS0222I01Response_EAIHeader EAIHeader = responseObject.getEAIHeader();
				if(ServiceResponse.Status.EAI_SUCCESS_CODE == EAIHeader.getReasonCode()){
					serviceResponse.setObjectData(getCIS0222I01Response(responseObject));
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
		responseTransaction.serviceInfo(ServiceConstant.IN, responseObject, serviceResponse);
		return responseTransaction;
	}
	
	private CIS0222I01ResponseDataM getCIS0222I01Response(CIS0222I01Response responseObject){
		__doServiceResponse_CIS0222I01Response_CISCustomer CISCustomer = responseObject.getCISCustomer();
		___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj zipCodesObj = CISCustomer.getZipCodesObj();
		_____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode[] zipCodesVects = zipCodesObj.getZipCodesVect();		
		ArrayList<CISZipCodeDataM> CISZipCodeList = new ArrayList<>();
		if(zipCodesVects !=null){
			for(_____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode zipCodeVect : zipCodesVects){
				CISZipCodeDataM CISZipCode = new CISZipCodeDataM();
				CISZipCode.setZipCode(zipCodeVect.getZipCode());
				CISZipCode.setAmphur(zipCodeVect.getAmphurDesc());
				CISZipCode.setProvince(zipCodeVect.getProvinceDesc());
				CISZipCode.setTumbol(zipCodeVect.getTumbolDesc());
				CISZipCodeList.add(CISZipCode);
			}
		}		
		CIS0222I01ResponseDataM CIS0222I01Response = new CIS0222I01ResponseDataM(); 
		CIS0222I01Response.setTotalResult(zipCodesVects.length);
		CIS0222I01Response.setCisZipCode(CISZipCodeList);
		return CIS0222I01Response;		
	}
}
