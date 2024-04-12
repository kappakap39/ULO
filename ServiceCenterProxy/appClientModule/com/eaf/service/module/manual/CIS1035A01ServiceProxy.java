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
import com.eaf.service.module.model.CIS1035A01RequestDataM;
import com.eaf.service.module.model.CIS1035A01ResponseDataM;
import com.eaf.service.module.model.CISContactDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.eai.cis1035a01.CIS1035A01Response;
import com.kasikornbank.eai.cis1035a01.CIS1035A01SoapProxy;
import com.kasikornbank.eai.cis1035a01.CIS1035A01_Type;
import com.kasikornbank.eai.cis1035a01.CISHeader;
import com.kasikornbank.eai.cis1035a01._____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact;
import com.kasikornbank.eai.cis1035a01._____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact;
import com.kasikornbank.eai.cis1035a01.___doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj;
import com.kasikornbank.eai.cis1035a01.___doService_CIS1035A01_CISCustomer_contactsObj;
import com.kasikornbank.eai.cis1035a01.__doServiceResponse_CIS1035A01Response_CISCustomer;
import com.kasikornbank.eai.cis1035a01.__doServiceResponse_CIS1035A01Response_KBankHeader;
import com.kasikornbank.eai.cis1035a01.__doService_CIS1035A01_CISCustomer;
import com.kasikornbank.eai.cis1035a01.__doService_CIS1035A01_KBankHeader;

public class CIS1035A01ServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(CIS1035A01ServiceProxy.class);
	public static final String url = "urlWebService";
	public static final String serviceId = "CIS1035A01";
//	public static final String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public static final String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public static final String successCode = ServiceCache.getConstant("SUCCESS_CODE");	
	public static class requestConstants{
		public static final String userId = "userId";
		public static final String hubNo = "hubNo";
		public static final String brNo = "branchNo";
		public static final String confirmFlag = "confirmFlag";
		public static final String valFlag = "validateFlag";
		public static final String totalRecord = "totalRecord";
		public static class CISContact{
			public static final String typeCode = "typeCode";
			public static final String locatCode = "locationCode";
			public static final String contactNo = "contactNo";
			public static final String contactExtNo = "contactExtNo";
			public static final String contactAvaTime = "contactAvailabilityTime";
			public static final String contactName = "contactName";
		}
		public static final String cusType = "customerType";
		public static final String cusId = "customerId";
	}
	
	public static class responseConstants{
		public static final String cusId = "customerId";
		public static class CISContactList{
			public static final String contactId = "contactId";
			public static final String contactTp = "contactTypeCode";
			public static final String contactLoc = "contactLocation";
			public static final String contactNo = "contactNo";
			public static final String contactEx = "contactExtension";
			public static final String contactSeq = "contactSequence";
			public static final String successFlag = "successFlag";
			public static final String errDesc = "errorDescription";
		}
	}	
	
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		CIS1035A01_Type requestObject = new CIS1035A01_Type();
		__doService_CIS1035A01_KBankHeader KBankHeader = new __doService_CIS1035A01_KBankHeader();
			KBankHeader.setFuncNm(serviceId);
			KBankHeader.setUserId(serviceRequest.getUserId());
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			KBankHeader.setRqAppId(RqAppId);
			KBankHeader.setCorrID(serviceRequest.getServiceReqResId());
			KBankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
			KBankHeader.setRqDt(ServiceApplicationDate.getCalendar());		
		CIS1035A01RequestDataM CIS1035A01Request = (CIS1035A01RequestDataM)serviceRequest.getObjectData();
			CISHeader CISHeader = new CISHeader();
			CISHeader.setUserId(CisGeneralUtil.displayCISHeaderUserId(CIS1035A01Request.getUserId()));
			CISHeader.setHubNo(CIS1035A01Request.getHubNo());
			CISHeader.setBrNo(CIS1035A01Request.getBranchNo());
			CISHeader.setConfirmFlag(CIS1035A01Request.getConfirmFlag());		
		__doService_CIS1035A01_CISCustomer CISCustomer = new __doService_CIS1035A01_CISCustomer();
		CISCustomer.setValidationFlag(CIS1035A01Request.getValidateFlag());		
		if(null != CIS1035A01Request.getCisContact()){
			___doService_CIS1035A01_CISCustomer_contactsObj contactsObj = new ___doService_CIS1035A01_CISCustomer_contactsObj();
				contactsObj.setTotCntctCnt(CIS1035A01Request.getTotalRecord());
					int data = CIS1035A01Request.getCisContact().size();
					_____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact[] contactsVect 
					= new _____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact[data];
					ArrayList<CISContactDataM> cisContacts = CIS1035A01Request.getCisContact();
					if(null != cisContacts){
						int i = 0;
						for(CISContactDataM cisContact : cisContacts){
							contactsVect[i] = new _____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact();
							contactsVect[i].setTypeCode(cisContact.getTypeCode());
							contactsVect[i].setLocationCode(cisContact.getLocationCode());
							contactsVect[i].setPhNum(cisContact.getContactNo());
							contactsVect[i].setPhExtNum(cisContact.getContactExtNo());
							contactsVect[i].setPhAvailTimeCde(cisContact.getContactAvailabilityTime());
							contactsVect[i].setName(cisContact.getContactName());
							i++;
						}
					}
				contactsObj.setContactsVect(contactsVect);
			CISCustomer.setContactsObj(contactsObj);
		}
		CISCustomer.setTypeCode(CIS1035A01Request.getCustomerType());
		CISCustomer.setNum(CIS1035A01Request.getCustomerId());		
		requestObject.setKBankHeader(KBankHeader);
		requestObject.setCISHeader(CISHeader);
		requestObject.setCISCustomer(CISCustomer);
		requestTransaction.serviceInfo(ServiceConstant.OUT, requestObject, serviceRequest);
		return requestTransaction;
	}
	
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CIS1035A01_Type requestObject = (CIS1035A01_Type)requestServiceObject;
			CIS1035A01SoapProxy proxy = new CIS1035A01SoapProxy();
			String endPointUrl = getEndpointUrl();
			logger.debug("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endPointUrl);
			CIS1035A01Response responseObject = proxy.doService(requestObject);
			serviceTransaction.setServiceTransactionObject(responseObject);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}
	
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction){
		logger.debug("responseTransaction..");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		CIS1035A01Response responseObject = (CIS1035A01Response)serviceTransaction.getServiceTransactionObject();
		CISRefDataObject personalRefData = (CISRefDataObject)serviceRequest.getServiceData();
		if(null==personalRefData){
			personalRefData = new CISRefDataObject();
		}
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				__doServiceResponse_CIS1035A01Response_KBankHeader KBankHeader = responseObject.getKBankHeader();
				if(ServiceResponse.Status.SUCCESS.equals(KBankHeader.getStatusCode())){	
					serviceResponse.setObjectData(getCIS1035A01Response(responseObject));
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
				}else{
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
					com.kasikornbank.eai.cis1035a01.Error[] Errors = KBankHeader.getErrorVect();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
					errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.CIS);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					if(null != Errors && Errors.length > 0){
						com.kasikornbank.eai.cis1035a01.Error Error = Errors[0];
						errorInfo.setErrorCode(Error.getErrorCode());
						errorInfo.setErrorDesc(Error.getErrorDesc());
						errorInfo.setErrorInformation(new Gson().toJson(Errors));
					}
					serviceResponse.setErrorInfo(errorInfo);
				}
			}catch(Exception e){
				logger.debug("ERROR",e);				
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
	
	private CIS1035A01ResponseDataM getCIS1035A01Response(CIS1035A01Response responseObject){
		CIS1035A01ResponseDataM CIS1035A01Response = new CIS1035A01ResponseDataM();	
		if(null != responseObject){
			__doServiceResponse_CIS1035A01Response_CISCustomer CISCustomer = responseObject.getCISCustomer();
			if(null != CISCustomer){
				CIS1035A01Response.setCustomerId(CISCustomer.getNum());
				___doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj contactsObj = CISCustomer.getContactsObj();
				if(null != contactsObj){
					_____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact[] contactsVects = contactsObj.getContactsVect();
					if(null != contactsVects){
						ArrayList<CISContactDataM> cisContacts = new ArrayList<>();
						for(_____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact CISContact : contactsVects){
							CISContactDataM cisContact = new CISContactDataM();
							cisContact.setContactId(CISContact.getNum());
							cisContact.setContactTypeCode(CISContact.getTypeCode());
							cisContact.setContactLocation(CISContact.getLocationCode());
							cisContact.setContactNo(CISContact.getPhNum());
							cisContact.setContactExtNo(CISContact.getPhExtNum());
							cisContact.setContactSequence(CISContact.getSeqNum());
							cisContact.setSuccessFlag(CISContact.getSuccessFlag());
							cisContact.setErrorDescription(CISContact.getErrorDesc());
							cisContacts.add(cisContact);			
						}
						CIS1035A01Response.setCisContact(cisContacts);
					}
				}
			}
		}
		return CIS1035A01Response;
	}
}
