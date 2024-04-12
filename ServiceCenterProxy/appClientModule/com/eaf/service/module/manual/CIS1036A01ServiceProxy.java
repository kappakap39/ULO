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
import com.eaf.service.module.model.CIS1036A01RequestDataM;
import com.eaf.service.module.model.CIS1036A01ResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.eai.cis1036a01.CIS1036A01Response;
import com.kasikornbank.eai.cis1036a01.CIS1036A01SoapProxy;
import com.kasikornbank.eai.cis1036a01.CIS1036A01_Type;
import com.kasikornbank.eai.cis1036a01.CISHeader;
import com.kasikornbank.eai.cis1036a01.ObsContactAddrObj;
import com.kasikornbank.eai.cis1036a01.___doServiceResponse_CIS1036A01Response_CISCustomer_contactAddrObj;
import com.kasikornbank.eai.cis1036a01.___doService_CIS1036A01_CISCustomer_contactAddrObj;
import com.kasikornbank.eai.cis1036a01.__doServiceResponse_CIS1036A01Response_CISCustomer;
import com.kasikornbank.eai.cis1036a01.__doServiceResponse_CIS1036A01Response_KBankHeader;
import com.kasikornbank.eai.cis1036a01.__doService_CIS1036A01_CISCustomer;
import com.kasikornbank.eai.cis1036a01.__doService_CIS1036A01_KBankHeader;

public class CIS1036A01ServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(CIS1036A01ServiceProxy.class);
	public static final String url = "urlWebService";
	public static final String serviceId = "CIS1036A01";
//	public static final String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public static final String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public static final String successCode = ServiceCache.getConstant("SUCCESS_CODE");	
	public static class requestConstants{
		public static final String userId = "userId";
		public static final String hubNo = "hubNo";
		public static final String brNo = "branchNo";
		public static final String confirmFlag = "confirmFlag";
		public static final String validateFlag = "validateFlag";
		public static final String cusType = "customerType";
		public static final String cusId = "cusId";
		public static final String cusTypeCode ="customerTypeCode";
		public static class address{
			public static final String id = "addressId";
			public static final String name = "addressName";
			public static final String mailBoxNo = "mailBoxNo";
			public static final String mailNo = "mailNo";
			public static final String moo = "moo";
			public static final String mooban = "mooban";
			public static final String building = "building";
			public static final String room = "room";
			public static final String floor = "floor";
			public static final String soi = "soi";
			public static final String road = "road";
			public static final String tumbol = "tumbol";
			public static final String amphur = "amphur";
			public static final String province = "province";
			public static final String postCode = "postCode";
			public static final String country = "country";
			public static final String line1 = "line1";
			public static final String line2 = "line2";
		}
	}	
	
	public static class responseConstants{
		public static final String cusId = "customerId";
		public static final String addrId = "addressId";
	}
	
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		CIS1036A01_Type requestObject = new CIS1036A01_Type();
		__doService_CIS1036A01_KBankHeader KBankHeader = new __doService_CIS1036A01_KBankHeader();
			KBankHeader.setFuncNm(serviceId);
			KBankHeader.setUserId(serviceRequest.getUserId());
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			KBankHeader.setRqAppId(RqAppId);
			KBankHeader.setCorrID(serviceRequest.getServiceReqResId());
			KBankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
			KBankHeader.setRqDt(ServiceApplicationDate.getCalendar());

		CIS1036A01RequestDataM CIS1036A01Request = (CIS1036A01RequestDataM)serviceRequest.getObjectData();
		if(null == CIS1036A01Request){
			CIS1036A01Request = new CIS1036A01RequestDataM();
		}
		CISHeader CISHeader = new CISHeader();
			CISHeader.setUserId(CisGeneralUtil.displayCISHeaderUserId(CIS1036A01Request.getUserId()));
			CISHeader.setHubNo(CIS1036A01Request.getHubNo());
			CISHeader.setBrNo(CIS1036A01Request.getBranchNo());
			CISHeader.setConfirmFlag(CIS1036A01Request.getConfirmFlag());
		
		__doService_CIS1036A01_CISCustomer CISCustomer = new __doService_CIS1036A01_CISCustomer();
		CISCustomer.setValidationFlag(CIS1036A01Request.getValidateFlag());
		CISCustomer.setTypeCode(CIS1036A01Request.getCustomerType());
		CISCustomer.setNum(CIS1036A01Request.getCustomerId());
		CISCustomer.setCustTypeCode(CIS1036A01Request.getCustomerTypeCode());
			___doService_CIS1036A01_CISCustomer_contactAddrObj contactAddrObj = new ___doService_CIS1036A01_CISCustomer_contactAddrObj();
			contactAddrObj.setNum(CIS1036A01Request.getAddressId());
			contactAddrObj.setName(CIS1036A01Request.getAddressName());
			contactAddrObj.setPoBox(CIS1036A01Request.getMailBoxNo());
			contactAddrObj.setMailNum(CIS1036A01Request.getMailNo());
			contactAddrObj.setMoo(CIS1036A01Request.getMoo());
			contactAddrObj.setVillage(CIS1036A01Request.getMooban());
			contactAddrObj.setBuilding(CIS1036A01Request.getBuilding());
			contactAddrObj.setRoom(CIS1036A01Request.getRoom());
			contactAddrObj.setFloor(CIS1036A01Request.getFloor());
			contactAddrObj.setSoi(CIS1036A01Request.getSoi());
			contactAddrObj.setRoad(CIS1036A01Request.getRoad());
			contactAddrObj.setTumbol(CIS1036A01Request.getTumbol());
			contactAddrObj.setAmphur(CIS1036A01Request.getAmphur());
			contactAddrObj.setProvince(CIS1036A01Request.getProvince());
			contactAddrObj.setPostCode(CIS1036A01Request.getPostCode());
			contactAddrObj.setCntryCode(CIS1036A01Request.getCountry());
		CISCustomer.setContactAddrObj(contactAddrObj);
		
		ObsContactAddrObj obsContactAddrObj = new ObsContactAddrObj();
		obsContactAddrObj.setObsAddr1Txt(CIS1036A01Request.getLine1());
		obsContactAddrObj.setObsAddr2Txt(CIS1036A01Request.getLine2());
		CISCustomer.setObsContactAddrObj(obsContactAddrObj);	
		
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
			CIS1036A01_Type requestObject = (CIS1036A01_Type)requestServiceObject;
			CIS1036A01SoapProxy proxy = new CIS1036A01SoapProxy();
			String endPointUrl = serviceRequest.getEndpointUrl();
	        logger.info("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endPointUrl);
			CIS1036A01Response responseObject = proxy.doService(requestObject);
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
		CIS1036A01Response responseObject = (CIS1036A01Response)serviceTransaction.getServiceTransactionObject();
		CIS1036A01ResponseDataM cis1036a10Response = new CIS1036A01ResponseDataM();
		CISRefDataObject personalRefData = (CISRefDataObject)serviceRequest.getServiceData();
		if(null==personalRefData){
			personalRefData = new CISRefDataObject();
		}
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				__doServiceResponse_CIS1036A01Response_KBankHeader KBankHeader = responseObject.getKBankHeader();
				if(ServiceResponse.Status.SUCCESS.equals(KBankHeader.getStatusCode())){
					cis1036a10Response = getCIS1036A01Response(responseObject);
					personalRefData.setCisAddressId(cis1036a10Response.getAddressId());
					serviceResponse.setObjectData(cis1036a10Response);
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
				}else{
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
					com.kasikornbank.eai.cis1036a01.Error[] Errors = KBankHeader.getErrorVect();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
					errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.CIS);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					if(null != Errors && Errors.length > 0){
						com.kasikornbank.eai.cis1036a01.Error Error = Errors[0];
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
	
	public CIS1036A01ResponseDataM getCIS1036A01Response(CIS1036A01Response responseObject){
		CIS1036A01ResponseDataM CIS1036A01Response = new CIS1036A01ResponseDataM();
		__doServiceResponse_CIS1036A01Response_CISCustomer CISCustomer = responseObject.getCISCustomer();
		if(null == CISCustomer){
			CISCustomer = new __doServiceResponse_CIS1036A01Response_CISCustomer();
		}
		___doServiceResponse_CIS1036A01Response_CISCustomer_contactAddrObj contactAddrObj = CISCustomer.getContactAddrObj();
		if(null == contactAddrObj){
			contactAddrObj = new ___doServiceResponse_CIS1036A01Response_CISCustomer_contactAddrObj();
		}
		CIS1036A01Response.setAddressId(contactAddrObj.getNum());
		CIS1036A01Response.setCustomerId(CISCustomer.getNum());
		return CIS1036A01Response;
	}
}
