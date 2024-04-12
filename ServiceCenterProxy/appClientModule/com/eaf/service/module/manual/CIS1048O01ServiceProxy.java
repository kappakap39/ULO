package com.eaf.service.module.manual;

import java.util.ArrayList;

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
import com.eaf.service.module.model.CIS1048O01AddressDataM;
import com.eaf.service.module.model.CIS1048O01RequestDataM;
import com.eaf.service.module.model.CIS1048O01ResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.eai.cis1048o01.AddrRelObj;
import com.kasikornbank.eai.cis1048o01.CIS1048O01Response;
import com.kasikornbank.eai.cis1048o01.CIS1048O01SoapProxy;
import com.kasikornbank.eai.cis1048o01.CIS1048O01_Type;
import com.kasikornbank.eai.cis1048o01.CISAddrRelation;
import com.kasikornbank.eai.cis1048o01.CISHeader;
import com.kasikornbank.eai.cis1048o01.ContactAddrObj;
import com.kasikornbank.eai.cis1048o01.__doServiceResponse_CIS1048O01Response_CISCustomer;
import com.kasikornbank.eai.cis1048o01.__doServiceResponse_CIS1048O01Response_KBankHeader;
import com.kasikornbank.eai.cis1048o01.__doService_CIS1048O01_CISCustomer;
import com.kasikornbank.eai.cis1048o01.__doService_CIS1048O01_KBankHeader;

public class CIS1048O01ServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(CIS1048O01ServiceProxy.class);
	public static final String serviceId = "CIS1048O01";
	public static final String url = "urlWebService";
	
	public static class responseConstants{
		public static final String cusId = "customerId";
	}
	@Override
	public ServiceRequestTransaction requestTransaction() {
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		String terminalId = ServiceCache.getGeneralParam("DEFAULT_TERMINAL_ID");
		CIS1048O01RequestDataM requetData = (CIS1048O01RequestDataM)serviceRequest.getObjectData();	
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		CIS1048O01_Type requestCIS1048O01Object = new CIS1048O01_Type();
		
		__doService_CIS1048O01_KBankHeader requestKBankHeader = new __doService_CIS1048O01_KBankHeader();
		requestKBankHeader.setFuncNm(serviceId);
		requestKBankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
		requestKBankHeader.setRqDt(ServiceApplicationDate.getCalendar());
		requestKBankHeader.setRqAppId(RqAppId);		
		requestKBankHeader.setUserId(serviceRequest.getUserId());
		requestKBankHeader.setTerminalId(terminalId);
		requestKBankHeader.setCorrID(serviceRequest.getServiceReqResId());
		requestCIS1048O01Object.setKBankHeader(requestKBankHeader);
		
		CISHeader requestCISHeader = new CISHeader();
		requestCISHeader.setBrNo(requetData.getBranchNo());
		requestCISHeader.setHubNo(requetData.getHubNo());
		requestCISHeader.setUserId(CisGeneralUtil.displayCISHeaderUserId(requetData.getUserId()));
		requestCIS1048O01Object.setCISHeader(requestCISHeader);
		
		__doService_CIS1048O01_CISCustomer requestCISCustomer = new __doService_CIS1048O01_CISCustomer();
		requestCISCustomer.setTypeCode(requetData.getCustomerType());
		requestCISCustomer.setNum(requetData.getCustomerId());
		
		ContactAddrObj requestAddressObject = new ContactAddrObj();
		requestAddressObject.setNum(requetData.getContactAddressId());
		AddrRelObj requestAddrRelationObject = new AddrRelObj();
		ArrayList<CIS1048O01AddressDataM> addresses =  requetData.getContactAddressTypes();
		if(null!=addresses && addresses.size()>0){
			CISAddrRelation[] addressRelationVect = new CISAddrRelation[addresses.size()];
			for(int i=0;i< addresses.size();i++){
				CIS1048O01AddressDataM address=  addresses.get(i);
				CISAddrRelation requestAddrRelation = new CISAddrRelation(); 
				requestAddrRelation.setTypeCode(address.getAddressType());
				addressRelationVect[i]=requestAddrRelation;
			}
			requestAddrRelationObject.setAddrRelsVect(addressRelationVect);
		}		
		requestAddressObject.setAddrRelObj(requestAddrRelationObject);
		requestCISCustomer.setContactAddrObj(requestAddressObject);
		
		requestCIS1048O01Object.setCISCustomer(requestCISCustomer);		
		requestTransaction.serviceInfo(ServiceConstant.OUT, requestCIS1048O01Object, serviceRequest);
		return requestTransaction;
	}
	
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CIS1048O01_Type requestObject = (CIS1048O01_Type)requestServiceObject;
			CIS1048O01SoapProxy proxy = new CIS1048O01SoapProxy();
			String endPointUrl = serviceRequest.getEndpointUrl();
	        logger.info("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endPointUrl);
			CIS1048O01Response responseObject = proxy.doService(requestObject);
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
		CIS1048O01Response  responseObject  =(CIS1048O01Response)serviceTransaction.getServiceTransactionObject();
		logger.debug("responseTransaction..");
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				__doServiceResponse_CIS1048O01Response_KBankHeader KBankHeader = responseObject.getKBankHeader();
				if(ServiceResponse.CIS_STATUS.CIS_SUCCESS_CODES.contains(KBankHeader.getStatusCode())){
					serviceResponse.setObjectData(getCIS1048O01Response(responseObject));
					serviceResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
				}else{
					serviceResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					com.kasikornbank.eai.cis1048o01.Error[] Errors = KBankHeader.getErrorVect();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
					errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.CIS);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					if(null != Errors && Errors.length > 0){
						com.kasikornbank.eai.cis1048o01.Error Error = Errors[0];
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
	
	private CIS1048O01ResponseDataM getCIS1048O01Response(CIS1048O01Response  responseObject){
		CIS1048O01ResponseDataM cis1048O01ResponseDataM = new CIS1048O01ResponseDataM();
		__doServiceResponse_CIS1048O01Response_KBankHeader KBankHeader = responseObject.getKBankHeader();
		if(null!=responseObject && null!=responseObject.getCISCustomer()){
			__doServiceResponse_CIS1048O01Response_CISCustomer cisCustomer = responseObject.getCISCustomer();
			cis1048O01ResponseDataM.setCustomerId(cisCustomer.getNum());
			cis1048O01ResponseDataM.setStatusCode(KBankHeader.getStatusCode());
		}else{
			logger.debug("CIS1047O01Response is null!!");
		}	
		return cis1048O01ResponseDataM;
	}
}
