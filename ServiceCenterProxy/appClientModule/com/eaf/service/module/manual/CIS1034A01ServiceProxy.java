package com.eaf.service.module.manual;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
import com.eaf.service.module.model.CIS1034A01RequestDataM;
import com.eaf.service.module.model.CIS1034A01ResponseDataM;
import com.eaf.service.module.model.CISDocInfoDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.eai.cis1034a01.CIS1034A01Response;
import com.kasikornbank.eai.cis1034a01.CIS1034A01SoapProxy;
import com.kasikornbank.eai.cis1034a01.CIS1034A01_Type;
import com.kasikornbank.eai.cis1034a01.CISHeader;
import com.kasikornbank.eai.cis1034a01._____doServiceResponse_CIS1034A01Response_CISCustomer_docsObj_docsVect_CISDocInfo;
import com.kasikornbank.eai.cis1034a01._____doService_CIS1034A01_CISCustomer_docsObj_docsVect_CISDocInfo;
import com.kasikornbank.eai.cis1034a01.___doServiceResponse_CIS1034A01Response_CISCustomer_docsObj;
import com.kasikornbank.eai.cis1034a01.___doService_CIS1034A01_CISCustomer_docsObj;
import com.kasikornbank.eai.cis1034a01.__doServiceResponse_CIS1034A01Response_CISCustomer;
import com.kasikornbank.eai.cis1034a01.__doServiceResponse_CIS1034A01Response_KBankHeader;
import com.kasikornbank.eai.cis1034a01.__doService_CIS1034A01_CISCustomer;
import com.kasikornbank.eai.cis1034a01.__doService_CIS1034A01_KBankHeader;

public class CIS1034A01ServiceProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(CIS1034A01ServiceProxy.class);
	public static final String url = "urlWebService";
	public static final String serviceId = "CIS1034A01";
//	public static final String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public static final String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public static final String successCode = ServiceCache.getConstant("SUCCESS_CODE");	
	public static class requestConstants{
		public static final String userId = "userId";
		public static final String hubNo = "hubNo";
		public static final String brNo = "branchNo";
		public static final String confirmFlag = "confirmFlag";
		public static final String validateFlag = "validateFlag";
		public static final String totalRecord = "totalRecord";
		public static final String cusType = "customerType";
		public static final String cusId = "customerId";
		public static class docInfoList{
			public static final String docNo = "documentNo";
			public static final String docType = "documentType";
			public static final String placeIssue = "placeIssue";
			public static final String issueDate = "issueDate";
			public static final String expiryDate = "expiryDate";
		}
	}
	
	public static class responseConstants{
		public static final String cusId = "customerId";
		public static class docInfoList{
			public static final String docType = "documentType";
			public static final String docNo = "documentNo";
			public static final String succFlag = "successFlag";
			public static final String errDesc = "errorDescription";
		}
	}	
	
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		CIS1034A01_Type requestObject = new CIS1034A01_Type();
		__doService_CIS1034A01_KBankHeader KBankHeader = new __doService_CIS1034A01_KBankHeader();
			KBankHeader.setFuncNm(serviceId);
			KBankHeader.setUserId(serviceRequest.getUserId());
			String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			KBankHeader.setRqAppId(RqAppId);
			KBankHeader.setCorrID(serviceRequest.getServiceReqResId());
			KBankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
			KBankHeader.setRqDt(ServiceApplicationDate.getCalendar());
		
		CIS1034A01RequestDataM CIS1034A01Request = (CIS1034A01RequestDataM)serviceRequest.getObjectData();
		if(null == CIS1034A01Request){
			CIS1034A01Request = new CIS1034A01RequestDataM();
		}
		CISHeader CISHeader = new CISHeader();
			CISHeader.setUserId(CisGeneralUtil.displayCISHeaderUserId(CIS1034A01Request.getUserId()));
			CISHeader.setHubNo(CIS1034A01Request.getHubNo());
			CISHeader.setBrNo(CIS1034A01Request.getBranchNo());
			CISHeader.setConfirmFlag(CIS1034A01Request.getConfirmFlag());
		
		__doService_CIS1034A01_CISCustomer CISCustomer = new __doService_CIS1034A01_CISCustomer();
		CISCustomer.setValidationFlag(CIS1034A01Request.getValidateFlag());
		CISCustomer.setTypeCode(CIS1034A01Request.getCustomerType());
		CISCustomer.setNum(CIS1034A01Request.getCustomerId());
			___doService_CIS1034A01_CISCustomer_docsObj docsObj = new ___doService_CIS1034A01_CISCustomer_docsObj();
			docsObj.setTotDocCnt(CIS1034A01Request.getTotalRecord());
		if(null != CIS1034A01Request.getCisDocInfo()){
			ArrayList<CISDocInfoDataM> cisDocInfos = CIS1034A01Request.getCisDocInfo();
				int data = CIS1034A01Request.getCisDocInfo().size();
				_____doService_CIS1034A01_CISCustomer_docsObj_docsVect_CISDocInfo[] docsVect = new _____doService_CIS1034A01_CISCustomer_docsObj_docsVect_CISDocInfo[data];
				int i = 0;
				for(CISDocInfoDataM cisDocInfo : cisDocInfos){
					docsVect[i] = new _____doService_CIS1034A01_CISCustomer_docsObj_docsVect_CISDocInfo();
					docsVect[i].setNum(cisDocInfo.getDocumentNo());
					docsVect[i].setTypeCode(cisDocInfo.getDocumentType());
					docsVect[i].setCardIssuePlace(cisDocInfo.getPlaceIssue());
					if(cisDocInfo.getIssueDate()!=null){
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(cisDocInfo.getIssueDate());
						docsVect[i].setCardIssueDate(calendar);
					}
					if(cisDocInfo.getExpiryDate()!=null){
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(cisDocInfo.getExpiryDate());
						docsVect[i].setCardExpDate(calendar);
					}
					i++;
				}
			docsObj.setDocsVect(docsVect);
		}
		CISCustomer.setDocsObj(docsObj);
		requestObject.setKBankHeader(KBankHeader);
		requestObject.setCISHeader(CISHeader);
		requestObject.setCISCustomer(CISCustomer);
		requestTransaction.serviceInfo(ServiceConstant.OUT, requestObject, serviceRequest);
		return requestTransaction;
	}

	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		CIS1034A01_Type requestObject = (CIS1034A01_Type)requestServiceObject;
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CIS1034A01SoapProxy proxy = new CIS1034A01SoapProxy();
			String endPointUrl = getEndpointUrl();
			logger.debug("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endpointUrl);
			CIS1034A01Response responseObject = proxy.doService(requestObject);
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
		CIS1034A01Response responseObject = (CIS1034A01Response)serviceTransaction.getServiceTransactionObject();
		CISRefDataObject personalRefData = (CISRefDataObject)serviceRequest.getServiceData();
		if(null==personalRefData){
			personalRefData = new CISRefDataObject();
		}
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				__doServiceResponse_CIS1034A01Response_KBankHeader KBankHeader = responseObject.getKBankHeader();
				if(ServiceResponse.Status.SUCCESS.equals(KBankHeader.getStatusCode())){	
					serviceResponse.setObjectData(getCIS1034A01Response(responseObject));
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
				}else{
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
					com.kasikornbank.eai.cis1034a01.Error[] Errors = KBankHeader.getErrorVect();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
					errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.CIS);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					if(null != Errors && Errors.length > 0){
						com.kasikornbank.eai.cis1034a01.Error Error = Errors[0];
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
	
	public CIS1034A01ResponseDataM getCIS1034A01Response(CIS1034A01Response responseObject){
		CIS1034A01ResponseDataM CIS1034A01Response = new CIS1034A01ResponseDataM();		
		if(null != responseObject){
			__doServiceResponse_CIS1034A01Response_CISCustomer CISCustomer = responseObject.getCISCustomer();
			if(null != CISCustomer){
				CIS1034A01Response.setCustomerId(CISCustomer.getNum());
				___doServiceResponse_CIS1034A01Response_CISCustomer_docsObj docsObj = CISCustomer.getDocsObj();		
				if(null != docsObj){
					_____doServiceResponse_CIS1034A01Response_CISCustomer_docsObj_docsVect_CISDocInfo[] docsVects = docsObj.getDocsVect();
					ArrayList<CISDocInfoDataM> cisDocInfos = new ArrayList<>();
					if(null != docsVects){
						for(_____doServiceResponse_CIS1034A01Response_CISCustomer_docsObj_docsVect_CISDocInfo CISDocInfo : docsVects){
							CISDocInfoDataM cisDocInfo = new CISDocInfoDataM();
								cisDocInfo.setDocumentType(CISDocInfo.getTypeCode());
								cisDocInfo.setDocumentNo(CISDocInfo.getNum());
								cisDocInfo.setSuccessFlag(CISDocInfo.getSuccessFlg());
								cisDocInfo.setErrorDescription(CISDocInfo.getErrorDesc());
							cisDocInfos.add(cisDocInfo);
						}
					}
					CIS1034A01Response.setCisDocInfo(cisDocInfos);
				}
			}
		}
		return CIS1034A01Response;
	}
}
