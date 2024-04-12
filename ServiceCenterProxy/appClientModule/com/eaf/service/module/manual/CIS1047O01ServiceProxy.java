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
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.CIS1047O01RequestDataM;
import com.eaf.service.module.model.CIS1047O01ResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.eai.cis1047o01.CIS1047O01Response;
import com.kasikornbank.eai.cis1047o01.CIS1047O01SoapProxy;
import com.kasikornbank.eai.cis1047o01.CIS1047O01_Type;
import com.kasikornbank.eai.cis1047o01.CISContact;
import com.kasikornbank.eai.cis1047o01.CISHeader;
import com.kasikornbank.eai.cis1047o01.ContactAddrObj;
import com.kasikornbank.eai.cis1047o01.ContactsObj;
import com.kasikornbank.eai.cis1047o01.ContactsVect;
import com.kasikornbank.eai.cis1047o01.__doServiceResponse_CIS1047O01Response_CISCustomer;
import com.kasikornbank.eai.cis1047o01.__doServiceResponse_CIS1047O01Response_KBankHeader;
import com.kasikornbank.eai.cis1047o01.__doService_CIS1047O01_CISCustomer;
import com.kasikornbank.eai.cis1047o01.__doService_CIS1047O01_KBankHeader;

public class CIS1047O01ServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(CIS1047O01ServiceProxy.class);
	public static final String serviceId = "CIS1047O01";
	public static final String url = "urlWebService";
	
	public static class responseConstants{
		public static final String cusId = "customerId";
	}
	
	@Override
	public ServiceRequestTransaction requestTransaction(){
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		CIS1047O01RequestDataM requetData = (CIS1047O01RequestDataM)serviceRequest.getObjectData();		
		CIS1047O01_Type cis1047O01Object  = new CIS1047O01_Type();				
		__doService_CIS1047O01_KBankHeader requestKBankHeader = new __doService_CIS1047O01_KBankHeader();
		requestKBankHeader.setFuncNm(serviceId);
		requestKBankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
		requestKBankHeader.setRqDt(ServiceApplicationDate.getCalendar());
		requestKBankHeader.setRqAppId(RqAppId);		
		requestKBankHeader.setUserId(serviceRequest.getUserId());
		requestKBankHeader.setTerminalId(ServiceCache.getGeneralParam("DEFAULT_TERMINAL_ID"));
		requestKBankHeader.setCorrID(serviceRequest.getServiceReqResId());
		cis1047O01Object.setKBankHeader(requestKBankHeader);
		
		CISHeader requestCISHeader = new CISHeader();
		requestCISHeader.setUserId(CisGeneralUtil.displayCISHeaderUserId(requetData.getUserId()));
		requestCISHeader.setHubNo(requetData.getHubNo());
		requestCISHeader.setBrNo(requetData.getBranchNo());
		cis1047O01Object.setCISHeader(requestCISHeader);
		  
		__doService_CIS1047O01_CISCustomer  requestCISCustomer = new __doService_CIS1047O01_CISCustomer();
		requestCISCustomer.setDataTypeCode(requetData.getDataTypeCode());
		requestCISCustomer.setNum(requetData.getCustomerId());
		
		ContactAddrObj requestContactAddress = new ContactAddrObj();
		requestContactAddress.setNum(requetData.getContactAddressNum());
		requestCISCustomer.setContactAddrObj(requestContactAddress);
		
		ContactsObj requestContactObject = new ContactsObj();
		ContactsVect requestContactVect = new ContactsVect();
		CISContact  requestCISContact = new CISContact();
		requestCISContact.setTypeCode(requetData.getContactTypeCode());
		requestCISContact.setFuncTypeCode(requetData.getContactFunctionTypeCode());
		
		requestContactVect.setCISContact(requestCISContact);
		requestContactObject.setContactsVect(requestContactVect);
		requestCISCustomer.setContactsObj(requestContactObject);
		cis1047O01Object.setCISCustomer(requestCISCustomer);
		
		requestTransaction.serviceInfo(ServiceConstant.OUT, cis1047O01Object, serviceRequest);
		return requestTransaction;
	}

	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CIS1047O01_Type requestObjectData = (CIS1047O01_Type)requestServiceObject;
			CIS1047O01SoapProxy proxy = new CIS1047O01SoapProxy();
			String endPointUrl = serviceRequest.getEndpointUrl();
	        logger.info("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endPointUrl);
			CIS1047O01Response  responseObject = proxy.doService(requestObjectData);
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
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		CISRefDataObject personalRefData = (CISRefDataObject)serviceRequest.getServiceData();
		CIS1047O01Response  responseObject  =(CIS1047O01Response)serviceTransaction.getServiceTransactionObject();
		logger.debug("responseTransaction..");
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				__doServiceResponse_CIS1047O01Response_KBankHeader KBankHeader = responseObject.getKBankHeader();
				if(ServiceResponse.CIS_STATUS.CIS_SUCCESS_CODES.contains(KBankHeader.getStatusCode())){
					serviceResponse.setObjectData(getCIS1047O01Response(responseObject));
					serviceResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
				}else{
					serviceResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					com.kasikornbank.eai.cis1047o01.Error[] Errors = KBankHeader.getErrorVect();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
					errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.CIS);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					if(null != Errors && Errors.length > 0){
						com.kasikornbank.eai.cis1047o01.Error Error = Errors[0];
						errorInfo.setErrorCode(Error.getErrorCode());
						errorInfo.setErrorDesc(Error.getErrorDesc());
						errorInfo.setErrorInformation(new Gson().toJson(Errors));
					}
					serviceResponse.setErrorInfo(errorInfo);
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);					
				serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
				serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.Status.BUSINESS_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		serviceResponse.setServiceData(personalRefData);
		responseTransaction.serviceInfo(ServiceConstant.IN, responseObject, serviceResponse);
		return responseTransaction;
	}
	
	private CIS1047O01ResponseDataM getCIS1047O01Response(CIS1047O01Response  responseObject){
		CIS1047O01ResponseDataM cis1047O01ResponseDataM = new CIS1047O01ResponseDataM();
		if(null!=responseObject && null!=responseObject.getCISCustomer()){
			__doServiceResponse_CIS1047O01Response_KBankHeader KBankHeader = responseObject.getKBankHeader();
			__doServiceResponse_CIS1047O01Response_CISCustomer cisCustomer = responseObject.getCISCustomer();
			cis1047O01ResponseDataM.setCustomerId(cisCustomer.getNum());
			cis1047O01ResponseDataM.setStatusCode(KBankHeader.getStatusCode());
		}else{
			logger.debug("CIS1047O01Response is null!!");
		}	
		return cis1047O01ResponseDataM;
	}
}
