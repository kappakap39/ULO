package com.eaf.service.module.manual;

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
import com.eaf.service.module.model.CIS1044U01RequestDataM;
import com.eaf.service.module.model.CIS1044U01ResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.eai.cis1044u01.CIS1044U01Response;
import com.kasikornbank.eai.cis1044u01.CIS1044U01SoapProxy;
import com.kasikornbank.eai.cis1044u01.CIS1044U01_Type;
import com.kasikornbank.eai.cis1044u01.CISCustomer;
import com.kasikornbank.eai.cis1044u01.CISHeader;
import com.kasikornbank.eai.cis1044u01.CISIndivCust;
import com.kasikornbank.eai.cis1044u01.ContactAddrObj;
import com.kasikornbank.eai.cis1044u01.KYCObj;
import com.kasikornbank.eai.cis1044u01.OffclAddrObj;
import com.kasikornbank.eai.cis1044u01.ProfileObj;
import com.kasikornbank.eai.cis1044u01.__doServiceResponse_CIS1044U01Response_KBankHeader;
import com.kasikornbank.eai.cis1044u01.__doService_CIS1044U01_KBankHeader;

public class CIS1044U01ServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(CIS1044U01ServiceProxy.class);
	public static final String url = "urlWebService";
	public static final String serviceId = "CIS1044U01";
	public static final String TH = "TH";
	public static final String EN = "EN";	
	public static class requestConstants{}
	
	public static class responseConstants{
		public static final String cusId = "customerId";
		public static final String status = "status";
	}
	
	@Override
	public ServiceRequestTransaction requestTransaction(){
		CIS1044U01RequestDataM requestData = (CIS1044U01RequestDataM)serviceRequest.getObjectData();
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		CIS1044U01_Type requestObject = new CIS1044U01_Type();		
		
		__doService_CIS1044U01_KBankHeader requestKbankHeader = new __doService_CIS1044U01_KBankHeader();
		requestKbankHeader.setCorrID(serviceRequest.getServiceReqResId());
		requestKbankHeader.setFuncNm(serviceId);
		requestKbankHeader.setRqAppId(RqAppId);
		requestKbankHeader.setRqDt(ServiceApplicationDate.getCalendar());
		requestKbankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
		requestKbankHeader.setTerminalId(ServiceCache.getGeneralParam("DEFAULT_TERMINAL_ID"));
		requestKbankHeader.setUserId(serviceRequest.getUserId());
		CISHeader requestCISHeader = new CISHeader();
			requestCISHeader.setBrNo(requestData.getBranchNo());
			requestCISHeader.setHubNo(requestData.getHubNo());
			requestCISHeader.setUserId(CisGeneralUtil.displayCISHeaderUserId(requestData.getUserId()));
		//set indivCust
		CISIndivCust requestCISIndivCust = new CISIndivCust();
		requestCISIndivCust.setAddrOffCntctSameCde(requestData.getAddrOffCntctSameCde());
		if(!ServiceUtil.empty(requestData.getAssetExcludeLand())){
			requestCISIndivCust.setAsstExclLndAmt(requestData.getAssetExcludeLand());
		}	
		if(!ServiceUtil.empty(requestData.getBirthDate())){
			Calendar birthDate = new GregorianCalendar();
			birthDate.setTime(requestData.getBirthDate());
			requestCISIndivCust.setBrthDate(birthDate);
		}
		requestCISIndivCust.setChildCnt(ServiceUtil.empty(requestData.getAmountOfChildren()) ? 0: requestData.getAmountOfChildren());
		requestCISIndivCust.setCnstFlg(requestData.getConsendFlag());
		requestCISIndivCust.setCnstSrcCode(requestData.getSourceOfConsent());
		requestCISIndivCust.setCntctChnlMltCde(requestData.getMultipleContactChannel());  
		requestCISIndivCust.setYoungChildAgeCnt(ServiceUtil.empty(requestData.getAgeOfYoungestChild())|| requestData.getAgeOfYoungestChild() == 0 ? null : requestData.getAgeOfYoungestChild());
		requestCISIndivCust.setOldChildAgeCnt(ServiceUtil.empty(requestData.getAgeOfOldestChild())|| requestData.getAgeOfOldestChild() == 0 ? null : requestData.getAgeOfOldestChild());
		requestCISIndivCust.setFamilyIncCode(requestData.getFamilyIncomeRange());
		requestCISIndivCust.setCustSgmtCode(requestData.getCustomerSegment());
		requestCISIndivCust.setTitleNameCode(requestData.getTitleForcedSaveFlag());		
		requestCISIndivCust.setProspectFlag(requestData.getProspectFlag());
		requestCISIndivCust.setTypeCode(requestData.getCustomerType());
		requestCISIndivCust.setNum(requestData.getCustomerId());
		requestCISIndivCust.setThTitle(requestData.getThTitle());
		requestCISIndivCust.setThFstName(requestData.getThFstName());
		requestCISIndivCust.setThMidName(requestData.getThMidName());
		requestCISIndivCust.setThLstName(requestData.getThLstName());
		requestCISIndivCust.setEngTitle(requestData.getEngTitle());
		requestCISIndivCust.setEngFstName(requestData.getEngFstName());
		requestCISIndivCust.setEngMidName(requestData.getEngMidName());
		requestCISIndivCust.setEngLstName(requestData.getEngLstName());
		requestCISIndivCust.setNmChgResnCode(requestData.getNmChgResnCode());
		requestCISIndivCust.setCustTypeCode(requestData.getCustTypeCode());
		requestCISIndivCust.setSvcBrchCode(requestData.getSvcBrchCode());
		requestCISIndivCust.setDocTypeCode(requestData.getDocTypeCode());
		requestCISIndivCust.setDocNum(requestData.getDocNum());
		requestCISIndivCust.setProfessCode(requestData.getProfessionCode());
		requestCISIndivCust.setOthProfessDesc(ServiceUtil.removeAllSpecialCharactors(requestData.getOthProfessDesc()));
		requestCISIndivCust.setEmpCnt(requestData.getNumberOfEmployee());

		if(null!=requestData.getIdCrdExpDate()){
			Calendar IdCrdExpDate = new GregorianCalendar();
			IdCrdExpDate.setTime(requestData.getIdCrdExpDate());
			requestCISIndivCust.setIdCrdExpDate(IdCrdExpDate);
		}
		if(null!=requestData.getIdCrdIssuDate()){
			Calendar issuDate = new GregorianCalendar();
			issuDate.setTime(requestData.getIdCrdIssuDate());
			requestCISIndivCust.setIdCrdIssuDate(issuDate);
		}
		requestCISIndivCust.setIdCrdIssuPlaceDesc(requestData.getIdCrdIssuPlaceDesc());
		requestCISIndivCust.setIdCrdDesc(requestData.getIdCrdDesc());
		requestCISIndivCust.setTaxNum(requestData.getTaxNum());
		requestCISIndivCust.setSexCode(requestData.getGender());
		requestCISIndivCust.setMarrStatCode(requestData.getMaritalStatus());
		requestCISIndivCust.setReligionCode(requestData.getReligion());
		requestCISIndivCust.setOccCode(requestData.getOccupation());
		requestCISIndivCust.setPositionCode(requestData.getJobPosition()); 
		requestCISIndivCust.setTFBBusTypeCode(requestData.getBusinessTypeCode());
		requestCISIndivCust.setIncomRang(requestData.getSalary());		
		if(null!=requestData.getStartWorkDate()){
			Calendar workDate = new GregorianCalendar();
			workDate.setTime(requestData.getStartWorkDate());
			requestCISIndivCust.setWorkStartDate(workDate);
		}		
		requestCISIndivCust.setEduLevelCode(requestData.getDegree());
		requestCISIndivCust.setNationCode(requestData.getNationality());
		requestCISIndivCust.setRaceCode(requestData.getRace());
		requestCISIndivCust.setNmChgBookNum(requestData.getBookNumberForChangeName());
		requestCISIndivCust.setNmChgSeqNum(requestData.getSequenceNumberForChangeName());
		if(!ServiceUtil.empty(requestData.getTheDateChangeName())){
			Calendar changeNameDate = new GregorianCalendar();
			changeNameDate.setTime(requestData.getTheDateChangeName());
			requestCISIndivCust.setNmChgDate(changeNameDate);
		}

		requestCISIndivCust.setCntctChnlMltCde(requestData.getMultipleContactChannel());
		requestCISIndivCust.setVIPCode(requestData.getVipFlag());
				
		KYCObj kybObject = new KYCObj();
		kybObject.setKYCBrchCode(requestData.getKycBranch());
		kybObject.setCompltDocFlg(requestData.getCompleteDocumentFlag());
		kybObject.setCompltDocKYCFlg(requestData.getCompleteKYCDocumentFlag());
		requestCISIndivCust.setKYCObj(kybObject);
			
		OffclAddrObj offclAddress = new OffclAddrObj();
		offclAddress.setCntryDesc(requestData.getOfficialAddressCountry());
		requestCISIndivCust.setOffclAddrObj(offclAddress);
		
		ContactAddrObj contactAddr = new ContactAddrObj();
		contactAddr.setCntryDesc(requestData.getContactAddressCountry());
		requestCISIndivCust.setContactAddrObj(contactAddr);
		
		ProfileObj profile = new ProfileObj();
		profile.setDeathFlag(requestData.getDeathFlag());
		if(!ServiceUtil.empty(requestData.getDeathDate())){
			Calendar deathDate = new GregorianCalendar();
			deathDate.setTime(requestData.getDeathDate());
			logger.debug("deathDate)>>"+deathDate);
			profile.setDeathDate(deathDate);
		}
		logger.debug("requestData.getDeathDate()>>"+requestData.getDeathDate());
		
		
		requestCISIndivCust.setProfileObj(profile);
		
		requestObject.setKBankHeader(requestKbankHeader);
		requestObject.setCISHeader(requestCISHeader);
		requestObject.setCISIndivCust(requestCISIndivCust);
		requestTransaction.serviceInfo(ServiceConstant.OUT, requestObject, serviceRequest);
	    
		return requestTransaction;
	}
	
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CIS1044U01_Type requestObjectData = (CIS1044U01_Type)requestServiceObject;
			CIS1044U01SoapProxy proxy = new CIS1044U01SoapProxy();
			String endPointUrl = serviceRequest.getEndpointUrl();
	        logger.info("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endPointUrl);
			CIS1044U01Response  responseObject = proxy.doService(requestObjectData);
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
		CIS1044U01Response  responseObject  =(CIS1044U01Response)serviceTransaction.getServiceTransactionObject();
		logger.debug("responseTransaction..");
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				__doServiceResponse_CIS1044U01Response_KBankHeader KBankHeader = responseObject.getKBankHeader();
				String CIS1044U01StatusCode = KBankHeader.getStatusCode();
				logger.debug("CIS1044U01StatusCode>>>"+CIS1044U01StatusCode);
				if(ServiceResponse.CIS_STATUS.CIS_SUCCESS_CODES.contains(CIS1044U01StatusCode)){	
					serviceResponse.setObjectData(getCIS1044U01Response(responseObject));
					serviceResponse.setStatusCode(ServiceConstant.Status.SUCCESS);
				}else{
					serviceResponse.setStatusCode(ServiceConstant.Status.BUSINESS_EXCEPTION);
					com.kasikornbank.eai.cis1044u01.Error[] Errors = KBankHeader.getErrorVect();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
					errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.CIS);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					if(null != Errors && Errors.length > 0){
						com.kasikornbank.eai.cis1044u01.Error Error = Errors[0];
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
	
	private CIS1044U01ResponseDataM getCIS1044U01Response(CIS1044U01Response  responseObject){
		CIS1044U01ResponseDataM cis1044U01Response = new CIS1044U01ResponseDataM();
		if(null!=responseObject && null!=responseObject.getCISCustomer()){
			CISCustomer cisCustomer = responseObject.getCISCustomer();
			cis1044U01Response.setCustomerId(cisCustomer.getNum());
			cis1044U01Response.setStatus(responseObject.getKBankHeader().getStatusCode());
		}else{
			logger.debug("CIS1044U01Response is null!!");
		}	
		return cis1044U01Response;
	}
}
