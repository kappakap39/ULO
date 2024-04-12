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
import com.eaf.service.module.model.CIS1046A01RequestDataM;
import com.eaf.service.module.model.CIS1046A01ResponseDataM;
import com.eaf.service.module.model.CISAddrRelationDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.eai.cis1046a01.CIS1046A01Response;
import com.kasikornbank.eai.cis1046a01.CIS1046A01SoapProxy;
import com.kasikornbank.eai.cis1046a01.CIS1046A01_Type;
import com.kasikornbank.eai.cis1046a01.CISHeader;
import com.kasikornbank.eai.cis1046a01.______doServiceRespo_750316492_CISAddrRelation;
import com.kasikornbank.eai.cis1046a01.______doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_addrRelsVect_CISAddrRelation;
import com.kasikornbank.eai.cis1046a01.____doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj_addrRelObj;
import com.kasikornbank.eai.cis1046a01.____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj;
import com.kasikornbank.eai.cis1046a01.___doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj;
import com.kasikornbank.eai.cis1046a01.___doService_CIS1046A01_CISCustomer_contactAddrObj;
import com.kasikornbank.eai.cis1046a01.__doServiceResponse_CIS1046A01Response_CISCustomer;
import com.kasikornbank.eai.cis1046a01.__doServiceResponse_CIS1046A01Response_KBankHeader;
import com.kasikornbank.eai.cis1046a01.__doService_CIS1046A01_CISCustomer;
import com.kasikornbank.eai.cis1046a01.__doService_CIS1046A01_KBankHeader;

public class CIS1046A01ServiceProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(CIS1046A01ServiceProxy.class);
	public static final String url = "urlWebService";
	public static final String serviceId = "CIS1046A01";
//	public static final String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public static final String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public static final String successCode = ServiceCache.getConstant("SUCCESS_CODE");	
	public static class requestConstants{
		public static final String userId = "userId";
		public static final String hubNo = "hubNumber";
		public static final String brNo = "branchNo";
		public static final String confirmFlag = "confirmFlag";
		public static final String validateFlag = "validateFlag";
		public static final String cusType = "customerType";
		public static final String cusId = "customerId";
		public static final String addrId = "addressId";
		public static class CISAddrRelation{
			public static final String addrType = "addressType";
		}
		public static final String accNum = "accountId";
		public static final String accRefNum = "accountReferenceId";
		public static final String accLv = "accountLevel";
	}
	
	public static class responseConstants{
		public static final String cusId = "customerId";
		public static final String addrId = "addressId";
		public static class CISAddrRelation{
			public static final String addrType = "addressType";
			public static final String succFlag = "successFlag";
			public static final String errorDesc = "errorDescription";
		}
		public static final String accRefId = "accountReferenceId";
	}
	
	public ServiceRequestTransaction requestTransaction(){
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		CIS1046A01_Type requestObject = new CIS1046A01_Type();
		__doService_CIS1046A01_KBankHeader KBankHeader = new __doService_CIS1046A01_KBankHeader();
			KBankHeader.setFuncNm(serviceId);
			String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			KBankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
			KBankHeader.setRqDt(ServiceApplicationDate.getCalendar());
			KBankHeader.setRqAppId(RqAppId);
			KBankHeader.setUserId(serviceRequest.getUserId());
			KBankHeader.setCorrID(serviceRequest.getServiceReqResId());
		
		CIS1046A01RequestDataM CIS1046A01Request = (CIS1046A01RequestDataM)serviceRequest.getObjectData();
		if(null == CIS1046A01Request){
			CIS1046A01Request = new CIS1046A01RequestDataM();
		}
		CISHeader CISHeader = new CISHeader();
			CISHeader.setUserId(CisGeneralUtil.displayCISHeaderUserId(CIS1046A01Request.getUserId()));
			CISHeader.setHubNo(CIS1046A01Request.getHubNumber());
			CISHeader.setBrNo(CIS1046A01Request.getBranchNo());
			CISHeader.setConfirmFlag(CIS1046A01Request.getConfirmFlag());
		
		__doService_CIS1046A01_CISCustomer CISCustomer = new __doService_CIS1046A01_CISCustomer();
			CISCustomer.setValidationFlag(CIS1046A01Request.getValidateFlag());
			CISCustomer.setTypeCode(CIS1046A01Request.getCustomerType());
			CISCustomer.setNum(CIS1046A01Request.getCustomerId());
				___doService_CIS1046A01_CISCustomer_contactAddrObj contactAddrObj = new ___doService_CIS1046A01_CISCustomer_contactAddrObj();
				contactAddrObj.setNum(CIS1046A01Request.getAddressId());
				ArrayList<CISAddrRelationDataM> CISAddrRelations = CIS1046A01Request.getCisAddrRelations();
				if(null == CISAddrRelations){
					CISAddrRelations = new ArrayList<CISAddrRelationDataM>();
				}
				int data = CISAddrRelations.size();
				____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj addrRelObj = new ____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj();
					______doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_addrRelsVect_CISAddrRelation[] addrRelsVects = new ______doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_addrRelsVect_CISAddrRelation[data];
					int i = 0;
					for(CISAddrRelationDataM CISAddrRelation : CISAddrRelations){
						addrRelsVects[i] = new ______doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_addrRelsVect_CISAddrRelation();
						addrRelsVects[i].setTypeCode(CISAddrRelation.getAddressType());
						i++;
					}
					addrRelObj.setAddrRelsVect(addrRelsVects);
				contactAddrObj.setAddrRelObj(addrRelObj);
			CISCustomer.setContactAddrObj(contactAddrObj);
			CISCustomer.setProspectFlag(CIS1046A01Request.getProspectFlag());
		requestObject.setKBankHeader(KBankHeader);
		requestObject.setCISHeader(CISHeader);
		requestObject.setCISCustomer(CISCustomer);
		requestTransaction.serviceInfo(ServiceConstant.OUT, requestObject, serviceRequest);
		return requestTransaction;
	}
	
	public ServiceTransaction serviceTransaction(Object requestTransaction){
		logger.debug("serviceTransaction..");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CIS1046A01_Type requestObject = (CIS1046A01_Type)requestTransaction;
			CIS1046A01SoapProxy proxy = new CIS1046A01SoapProxy();
			String endPointUrl = getEndpointUrl();
			logger.debug("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endPointUrl);
			CIS1046A01Response responseObject = proxy.doService(requestObject);
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
		CIS1046A01Response responseObject = (CIS1046A01Response)serviceTransaction.getServiceTransactionObject();
		CISRefDataObject personalRefData = (CISRefDataObject)serviceRequest.getServiceData();
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				__doServiceResponse_CIS1046A01Response_KBankHeader KBankHeader = responseObject.getKBankHeader();
				if(ServiceResponse.Status.SUCCESS.equals(KBankHeader.getStatusCode())){
					serviceResponse.setObjectData(getCIS1046A01Response(responseObject));
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
				}else{
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
					com.kasikornbank.eai.cis1046a01.Error[] Errors = KBankHeader.getErrorVect();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
					errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.CIS);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					if(null != Errors && Errors.length > 0){
						com.kasikornbank.eai.cis1046a01.Error Error = Errors[0];
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
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		serviceResponse.setServiceData(personalRefData);
		responseTransaction.serviceInfo(ServiceConstant.IN, responseObject, serviceResponse);
		return responseTransaction;
	}
	
	private CIS1046A01ResponseDataM getCIS1046A01Response(CIS1046A01Response responseObject){
		CIS1046A01ResponseDataM CIS1046A01Response = new CIS1046A01ResponseDataM();
		__doServiceResponse_CIS1046A01Response_CISCustomer CISCustomer = responseObject.getCISCustomer();
		if(null == CISCustomer){
			CISCustomer = new __doServiceResponse_CIS1046A01Response_CISCustomer();
		}
		___doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj contactAddrObj = CISCustomer.getContactAddrObj();
		if(null == contactAddrObj){
			contactAddrObj = new ___doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj();
		}
		____doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj_addrRelObj addrRelObj = contactAddrObj.getAddrRelObj();
		if(null == addrRelObj){
			addrRelObj = new ____doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj_addrRelObj();
		}
		______doServiceRespo_750316492_CISAddrRelation[] CISAddrRelations = addrRelObj.getAddrRelsVect();
		
		ArrayList<CISAddrRelationDataM> CISAddrRelationList = new ArrayList<>();
		if(null != CISAddrRelations){
			for(______doServiceRespo_750316492_CISAddrRelation CISAddrRelation : CISAddrRelations){
				CISAddrRelationDataM CISAddrRelationM = new CISAddrRelationDataM();
				CISAddrRelationM.setAddressType(CISAddrRelation.getTypeCode());
				CISAddrRelationM.setSuccessFlag(CISAddrRelation.getSuccessFlag());
				CISAddrRelationM.setErrorDescription(CISAddrRelation.getErrorDesc());
				CISAddrRelationList.add(CISAddrRelationM);
			}
		}
		CIS1046A01Response.setCustomerId(CISCustomer.getNum());
		CIS1046A01Response.setAddressId(contactAddrObj.getNum());
		CIS1046A01Response.setCISAddrRelations(CISAddrRelationList);
		CIS1046A01Response.setAccountReferenceId(CISCustomer.getAcctRefNum());
		return CIS1046A01Response;
	}
}
