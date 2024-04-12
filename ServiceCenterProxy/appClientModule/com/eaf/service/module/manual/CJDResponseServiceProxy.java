package com.eaf.service.module.manual;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.ulo.control.util.CJDResponseUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.ava.flp.cjd.model.CJDRequest;
import com.ava.flp.cjd.model.CJDResponse;
import com.ava.flp.cjd.model.CJDRequestBody;
import com.ava.flp.cjd.model.CJDResponseServiceProxyRequest;
import com.ava.flp.cjd.model.CJDRequestHeader;
import com.ava.flp.cjd.model.CJDResponseHeader;
import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;

import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.util.ServiceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.ws.batch.xJCL.beans.returnCodeExpression;

public class CJDResponseServiceProxy extends ServiceControlHelper implements ServiceControl {

	private static transient Logger logger = Logger.getLogger(CJDResponseServiceProxy.class);
	public static final String serviceId = "CJDResponse";
	public static final String statusFoundDocReason = "99";
	private static String FIELD_ID_DOC_CHECKLIST_REASON = SystemConstant.getConstant("FIELD_ID_DOC_CHECKLIST_REASON");
	public static String[] FILTER_DOC_CJD = SystemConstant.getArrayConstant("FILTER_DOC_CJD");
	static ArrayList<String> filterDocument = new ArrayList<String>(Arrays.asList(FILTER_DOC_CJD));
	private static String CJD_REASON_CODE_SENT_IMAGE_ADMIN = SystemConstant.getConstant("CJD_REASON_CODE_SENT_IMAGE_ADMIN");
	
	public ServiceRequestTransaction requestTransaction() throws Exception {
		logger.debug("Request Transaction");
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		CJDResponseServiceProxyRequest cjdResponseRequest = (CJDResponseServiceProxyRequest) serviceRequest.getObjectData();
		ApplicationGroupDataM applicationGroup = cjdResponseRequest.getApplicationGroup();
		String completeFlag = cjdResponseRequest.getCompleteFlag();
		
		Date appDate = InfBatchProperty.getDate();
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");

		Date messageDt = ApplicationDate.getCalendar().getTime();
		//Date messageDt =ServiceApplicationDate.getCalendar();
		CJDRequest cjdRequestObject = new CJDRequest();
		// Header
		CJDRequestHeader cjdRequestHeader = new CJDRequestHeader();
		//cjdResponseRequestHeader.setStatusCode(foundReasonCodeSentImageAdmin(applicationGroup.getDocumentCheckLists())?ServiceResponse.Status.SUCCESS:statusFoundDocReason);
		cjdRequestHeader.setAppId(RqAppId);
		cjdRequestHeader.setAppPwd(null);
		cjdRequestHeader.setAppUser(null);
		cjdRequestHeader.setMessageDt(messageDt);
		cjdRequestHeader.setMessageUid(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
		// Body
		CJDRequestBody cjdRequestBody = new CJDRequestBody();
		cjdRequestBody.setContractNo( getString( applicationGroup.getApplicationGroupNo(), "") );
		cjdRequestBody.setResult(foundReasonCodeSentImageAdmin(applicationGroup.getDocumentCheckLists())?statusFoundDocReason:ServiceResponse.Status.SUCCESS);
		
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos(SystemConstant.getArrayListConstant("PERSONAL_TYPE_LIST_CONDITION"));
		
		if ( ! ServiceUtil.empty( personalInfos ) ) {
			ArrayList<String> docRemark = new ArrayList<String>();
			for (PersonalInfoDataM personalInfo : personalInfos ) {
				cjdRequestBody.setIncomeAmount(personalInfo.getTotVerifiedIncome());
				
				String personalType = personalInfo.getPersonalType();
				String personalId = personalInfo.getPersonalId();
				int seq = personalInfo.getSeq();
				logger.debug("personalType : "+personalType);
				logger.debug("personalId : "+personalId);
				logger.debug("seq : "+seq);
				String applicantTypeIM = PersonalInfoUtil.getIMPersonalType(personalType, seq);
				logger.debug("applicantTypeIM : "+applicantTypeIM);
				ArrayList<DocumentCheckListDataM> documentCheckLists = applicationGroup.getDocumentCheckListApplicantTypeIM(applicantTypeIM);
				//DocumentSuggestion
				if ( ! ServiceUtil.empty( documentCheckLists) ) {
					String docCode = "";
					String docNameList = "";
					for (DocumentCheckListDataM documentCheckList : documentCheckLists ) {
						docCode= documentCheckList.getDocumentCode();
						docNameList = CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), docCode);
						logger.debug("docCode >> "+docCode);
						logger.debug("docNameList >> "+docNameList);
						logger.debug("MandatoryDoc : " + CJDResponseUtil.mandatoryFlag(personalInfo, docCode));
						//return only mandatory doc
						if(CJDResponseUtil.mandatoryFlag(personalInfo, docCode)){
							if("N".equals(completeFlag)){
								if(filterDocument.contains(docCode)){
									if(foundGenerateFlag(documentCheckList.getDocumentCheckListReasons())){
										docRemark.add(FormatUtil.displayText(getReasonCodeNameList(documentCheckList.getDocumentCheckListReasons(), docNameList)));
									}else{
										docRemark.add(documentCheckList.getRemark());
									}
								}
							}else{
								if(foundGenerateFlag(documentCheckList.getDocumentCheckListReasons())){
									docRemark.add(FormatUtil.displayText(getReasonCodeNameList(documentCheckList.getDocumentCheckListReasons(), docNameList)));
								}else{
									docRemark.add(documentCheckList.getRemark());
								}
							}
						}
					}
				}
			}
			cjdRequestBody.setRemarks(docRemark);
		}
		
		/*if ( ! ServiceUtil.empty( applicationGroup.getPersonalInfos() ) ) {
			for (PersonalInfoDataM personalInfo : applicationGroup.getPersonalInfos()  ) {
				CJDReponsePersonalInfoDataM cjdResponsePersonalInfo = new CJDReponsePersonalInfoDataM();
				
				cjdResponsePersonalInfo.setCisNo( getString( personalInfo.getCisNo(), "" ) );
				cjdResponsePersonalInfo.setEnFirstName(getString( personalInfo.getEnFirstName(), "" ));
				cjdResponsePersonalInfo.setEnLastName(getString( personalInfo.getEnLastName(), "" ));
				cjdResponsePersonalInfo.setEnMidName(getString( personalInfo.getEnMidName(), "" ));
				cjdResponsePersonalInfo.setEnTitleCode(getString( personalInfo.getEnTitleDesc(), "" ));
				cjdResponsePersonalInfo.setIdNo(getString( personalInfo.getIdno(), "" ));
				cjdResponsePersonalInfo.setThFirstName(getString( personalInfo.getThFirstName(), "" ));
				cjdResponsePersonalInfo.setThMidName(getString( personalInfo.getThMidName(), "" ));
				cjdResponsePersonalInfo.setThTitleCode(getString( personalInfo.getThTitleDesc(), "" ));
				cjdResponsePersonalInfo.setThLastName(getString( personalInfo.getThLastName(), "" ));		
				cjdResponsePersonalInfo.setVerifiedIncomeSource(getString( personalInfo.getSorceOfIncome(), "" ));
				cjdResponsePersonalInfo.setTotalVerifiedIncome(personalInfo.getTotVerifiedIncome());
				
				VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
				CJDReponseVerificationResultDataM cjdResponseVerResult = new CJDReponseVerificationResultDataM();
				cjdResponseVerResult.setCompletedFlag( getString( completeFlag , "" ));
				cjdResponsePersonalInfo.setVerificationResult(cjdResponseVerResult);
				
					//VerifiedResult
					if ( ! ServiceUtil.empty( personalInfo.getVerificationResult() ) ) {
						
						
						//IncomeInfo
						ArrayList<CJDReponseIncomeInfoDataM> cjdResponseIncomeInfos = new ArrayList<CJDReponseIncomeInfoDataM>();
						if ( ! ServiceUtil.empty( verificationResult.getIncomeCalculations() ) ) {
							
							for (IncomeCalculationDataM incomeCal : verificationResult.getIncomeCalculations() ) {
									CJDReponseIncomeInfoDataM cjdResponseIncomeInfo = new CJDReponseIncomeInfoDataM();
									cjdResponseIncomeInfo.setIncomeSource( getString(incomeCal.getIncomeSourceType(),""));
									cjdResponseIncomeInfo.setVerifiedIncome(incomeCal.getTotalVerifiedIncome());
									cjdResponseIncomeInfos.add(cjdResponseIncomeInfo);
							}
						}
						cjdResponsePersonalInfo.setIncomeInfos(cjdResponseIncomeInfos);
						//DocumentSuggestion
						if ( ! ServiceUtil.empty( applicationGroup.getDocumentCheckLists()) ) {
							ArrayList<CJDReponseDocumentSuggestionDataM> documentSuggestions = new ArrayList<CJDReponseDocumentSuggestionDataM>();
							String docCode = "";
							for (DocumentCheckListDataM documentCheckList : applicationGroup.getDocumentCheckLists() ) {
								CJDReponseDocumentSuggestionDataM documentSuggestion = new CJDReponseDocumentSuggestionDataM();
								docCode= documentCheckList.getDocumentCode();
								if("N".equals(completeFlag)){
									if(filterDocument.contains(docCode)){
										documentSuggestion.setDocumentType(documentCheckList.getDocTypeId());
										documentSuggestion.setRemark(documentCheckList.getRemark());
										documentSuggestion.setReason(FormatUtil.displayText(getReasonCodeNameList(documentCheckList.getDocumentCheckListReasons())));
										documentSuggestions.add(documentSuggestion);
									}
								}else{
									documentSuggestion.setDocumentType(documentCheckList.getDocTypeId());
									documentSuggestion.setRemark(documentCheckList.getRemark());
									documentSuggestion.setReason(FormatUtil.displayText(getReasonCodeNameList(documentCheckList.getDocumentCheckListReasons())));
									documentSuggestions.add(documentSuggestion);
								}
								
							}
							cjdResponsePersonalInfo.setDocumentSuggestions(documentSuggestions);
						}
					}
					
				personalInfos.add(cjdResponsePersonalInfo);
			}
			
		}*/
		//cjdResponseApplicationGroup.setPersonalInfos(personalInfos);
		
		cjdRequestObject.setHeader( cjdRequestHeader );
		cjdRequestObject.setBody( cjdRequestBody );
		System.out.println(" cjdResponseRequestObject : " + new Gson().toJson(cjdRequestObject) );
		requestTransaction.serviceInfo(ServiceConstant.OUT, cjdRequestObject, serviceRequest);
		return requestTransaction;
	}

	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("Service Transaction");
		CJDResponseServiceProxyRequest cjdRequest = (CJDResponseServiceProxyRequest) serviceRequest.getObjectData();
		ApplicationGroupDataM applicationGroup = cjdRequest.getApplicationGroup();
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		CJDRequest cjdResponseRequest = (CJDRequest) requestServiceObject;
		try{
			RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
				@Override
				protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
			        if(connection instanceof HttpsURLConnection ){
			            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
							@Override
							public boolean verify(String arg0, SSLSession arg1) {
								return true;
							}
						});
			        }
					super.prepareConnection(connection, httpMethod);
				}
			});	
			String endPointUrl = serviceRequest.getEndpointUrl();
	        logger.info("endPointUrl : "+endPointUrl);
	        HttpHeaders httpHeaderReq = new HttpHeaders();
	        httpHeaderReq.add("Content-Type", "application/json; charset=UTF-8");
	        httpHeaderReq.set("Authorization","Basic "+SystemConfig.getProperty("CJD_RESPONSE_AUTHORIZATION"));
	        Gson gson = new GsonBuilder().setDateFormat("yyyyMMDDhhmmss").create();			
			HttpEntity<String> requestEntity = new HttpEntity<String>( gson.toJson(cjdResponseRequest), httpHeaderReq );
			ResponseEntity<String> responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,String.class);			
			HttpHeaders httpHeaderResp = responseEntity.getHeaders();
			String responseBody = responseEntity.getBody();
			logger.debug("httpHeaderResp >> "+httpHeaderResp);
			logger.debug("responseBody >> "+responseBody);
			
			CJDResponse cjdResponse = gson.fromJson(responseBody, CJDResponse.class);
			CJDResponseHeader responseHeader = cjdResponse.getHeader();
			String statusCode = responseHeader.getRespCode();
			logger.debug("StatusCode >> "+statusCode);
			/*ErrorDataM kbankError = new ErrorDataM();
				kbankError.setErrorCode(responseHeader.getRespCode());
				kbankError.setErrorDesc(responseHeader.getRespDesc());
			ArrayList<ErrorDataM> errorList = new ArrayList<ErrorDataM>();
				errorList.add(kbankError);*/
			logger.debug("cjdResponse >> "+cjdResponse);
			serviceTransaction.setServiceTransactionObject(cjdResponse);
			serviceTransaction.setStatusCode(statusCode);
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			serviceTransaction.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}


	@Override
	public ServiceResponseTransaction responseTransaction(
			ServiceTransaction serviceTransaction) {
		logger.debug("Response Transaction");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		CJDResponse cjdResponse = (CJDResponse) serviceTransaction.getServiceTransactionObject();
		if(ServiceConstant.Status.SUCCESS.equals(serviceTransaction.getStatusCode())&& null != cjdResponse){
			serviceResponse.setObjectData(cjdResponse);		
			if(ServiceConstant.Status.SUCCESS.equals(cjdResponse.getHeader().getRespCode())){
				serviceResponse.setStatusCode(cjdResponse.getHeader().getRespCode());
			}else{
				serviceResponse.setStatusCode(cjdResponse.getHeader().getRespCode());
				serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SERVICE_RESPONSE,cjdResponse.getHeader().getRespDesc()));
//				ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
//				errorInfo.setServiceId(serviceId);
//				errorInfo.setErrorDesc(cjdResponse.getHeader().getRespDesc());
//				serviceResponse.setErrorInfo(errorInfo); 
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		serviceResponse.setServiceData(serviceRequest.getServiceData());
		responseTransaction.serviceInfo(ServiceConstant.IN, cjdResponse, serviceResponse);
		return responseTransaction;
	}
	
	private String getString(String value, String defaultValue) {
		if (ServiceUtil.empty(value))
			return defaultValue;
		return value;
	}
	public static String getReasonCodeNameList(ArrayList<DocumentCheckListReasonDataM> documemtCheckListReasonList, String docNameList){
		StringBuilder docReasonNames = new StringBuilder("");
		try {
			if(!Util.empty(documemtCheckListReasonList)){
				String SLASH ="";
				for(DocumentCheckListReasonDataM documentCheckListReasonDataM :documemtCheckListReasonList){						
					String docName = docNameList;
					//check doc reason is not DOCUMENT_NOT_CLEAR
					logger.debug("getReasonCodeNameList: "+documentCheckListReasonDataM.getDocReason());
						docName = docName+" "+CacheControl.getName(FIELD_ID_DOC_CHECKLIST_REASON, documentCheckListReasonDataM.getDocReason());
							if("".equals(docReasonNames) || docReasonNames.length() == 0){
								docReasonNames.append(docName);
							}
							else{
								docReasonNames.append(SLASH+docName);
							}
					SLASH="//";
				}				
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return docReasonNames.toString();
	}
	public static boolean foundGenerateFlag(ArrayList<DocumentCheckListReasonDataM> documemtCheckListReasonList){
		try {
			if(!Util.empty(documemtCheckListReasonList)){
				for(DocumentCheckListReasonDataM documentCheckListReasonDataM :documemtCheckListReasonList){						
					if("Y".equals(documentCheckListReasonDataM.getGenerateFlag())){
						return true;
					}
				}				
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return false;
	}
	public static boolean foundReasonCodeSentImageAdmin(ArrayList<DocumentCheckListDataM> documemtCheckLists){
		try {
			if ( ! ServiceUtil.empty( documemtCheckLists) ) {
				String docCode = "";
				for (DocumentCheckListDataM documentCheckList : documemtCheckLists ) {
					if(!Util.empty(documentCheckList.getDocumentCheckListReasons())){
						for(DocumentCheckListReasonDataM documentCheckListReasonDataM :documentCheckList.getDocumentCheckListReasons()){
							docCode= documentCheckList.getDocumentCode();
							logger.debug("foundReasonCodeSentImageAdmin DocReason: "+ documentCheckListReasonDataM.getDocReason());	
							if(filterDocument.contains(docCode)){
								if(CJD_REASON_CODE_SENT_IMAGE_ADMIN.equals(documentCheckListReasonDataM.getDocReason())){
									return true;
								}
							}
						}				
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return false;
	}
}
