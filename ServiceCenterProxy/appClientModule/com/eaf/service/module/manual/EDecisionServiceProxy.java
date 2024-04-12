package com.eaf.service.module.manual;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.ava.flp.eapp.iib.model.ApplicationGroup;
import com.ava.flp.eapp.iib.model.Applications;
import com.ava.flp.eapp.iib.model.DecisionService;
import com.ava.flp.eapp.iib.model.DecisionServiceResponseDataEappM;
import com.ava.flp.eapp.iib.model.KBankHeader;
import com.ava.flp.eapp.iib.serializer.DateTimeSerializer;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.iib.eapp.mapper.EDecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM.CallerScreen;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EDecisionServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(EDecisionServiceProxy.class);
	public final static String serviceId = "EDecisionService";	
	private String DECISION_ERROR = ServiceCache.getConstant("DECISION_ERROR");
	private String INCOME_SERVICE = DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME_SERVICE;
	private String INCOME_INQUIRY = DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME_INQUIRY;
	
	private String requestDecision = null;
	@Override
	public ServiceRequestTransaction requestTransaction() throws Exception {
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		DecisionServiceRequestDataM  applicationRequest = (DecisionServiceRequestDataM)serviceRequest.getObjectData();
		String decisionPoint =applicationRequest.getDecisionPoint();//DecisionServiceUtil.getAction(applicationRequest);
		logger.debug("decisionPoint : "+decisionPoint);
		logger.debug("reCalculateActionFlag : "+applicationRequest.getApplicationGroup().getReCalculateActionFlag());
		requestDecision = decisionPoint;
		ApplicationGroup  requestData = DecisionServiceUtil.getIIBRequestMapperClassEApp(decisionPoint)
				.getEDecsionServiceMapper(applicationRequest.getApplicationGroup(), decisionPoint);		
		KBankHeader  KBankHeader = new KBankHeader();
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		KBankHeader.setFuncNm(serviceId);
//		KBankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
		KBankHeader.setRqUID(serviceRequest.getTransactionId());
		KBankHeader.setRqDt(DecisionServiceUtil.getApplicationDateTime());
		KBankHeader.setRqAppId(RqAppId);
		if(null!= serviceRequest.getUserId() && !"".equals(serviceRequest.getUserId())){
			KBankHeader.setUserId(serviceRequest.getUserId());
		}else{
			KBankHeader.setUserId(applicationRequest.getUserId());		
		}		
		KBankHeader.setUserLangPref(DecisionServiceUtil.TH);
		KBankHeader.setCorrID(serviceRequest.getServiceReqResId());
//		KBankHeader.set( serviceRequest.getTransactionId());
		KBankHeader.setTerminalId(ServiceCache.getGeneralParam("DEFAULT_TERMINAL_ID"));
		//requestData.setKBankHeader(KBankHeader);
		
		DecisionService decisionService=new DecisionService();
		
		CallerScreen callerScreen = applicationRequest.getCallerScreen();
		logger.debug("callerScreen : "+callerScreen);
		if(CallerScreen.INCOME_VARIENCE == callerScreen && (INCOME_SERVICE.equals(decisionPoint) ||
				INCOME_INQUIRY.equals(decisionPoint))) {
			requestData.setProcessAction(callerScreen.name());
		}
		
		decisionService.setApplicationGroup(requestData);
		decisionService.setKbankHeader(KBankHeader);
		
		serviceRequest.setServiceData(decisionPoint);
		logger.debug("requestData>>>"+ new Gson().toJson(requestData));
		requestTransaction.serviceInfo(ServiceConstant.OUT, decisionService, serviceRequest);
		return requestTransaction;
	}

	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			String URL = getEndpointUrl();
			logger.debug("URL : "+URL);
			DecisionService requestData = (DecisionService)requestServiceObject;
			DecisionService responseData = decisionService(URL, requestData);
			serviceTransaction.setServiceTransactionObject(responseData);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceTransaction;
	}
	private DecisionService decisionService(String URL,DecisionService requestData){
		
		ResponseEntity<DecisionService> responseEntity = null;
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
	    	
			String endPointUrl = URL;
	        logger.info("endPointUrl : "+endPointUrl);	        
	        HttpHeaders httpHeaderReq = new HttpHeaders();
	        httpHeaderReq.add("Content-Type", "application/json; charset=UTF-8");

	        GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeSerializer());
	        Gson gson=gsonBuilder.create();
	        
	        logger.debug("body->"+gson.toJson(requestData));
	        HttpEntity<String> requestEntity = new HttpEntity<String>(gson.toJson(requestData),httpHeaderReq);
			responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,DecisionService.class);
	    	
	    	logger.debug("response >> "+responseEntity);
	
	    }catch(Exception e){
	    	//errorMsg = e.getLocalizedMessage();
	    	logger.fatal("ERROR",e);
	    }
		DecisionService responseData=responseEntity.getBody();
		return responseData;
	}
	@Override
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		logger.debug("response transaction");
		ServiceResponseTransaction respTran = new ServiceResponseTransaction();
		DecisionServiceRequestDataM  requestData = (DecisionServiceRequestDataM)serviceRequest.getObjectData();
		DecisionServiceResponseDataEappM iibApplicationRes= new DecisionServiceResponseDataEappM();
		try {
			if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode())){
				DecisionService responseDecisionService = (DecisionService)serviceTransaction.getServiceTransactionObject();
				ApplicationGroup responseApplicationGroup = responseDecisionService.getApplicationGroup();
				if(null==responseApplicationGroup){
					logger.debug(" response object data is null!!");
				}
				KBankHeader  kBankHeader  = responseDecisionService.getKbankHeader();
				logger.debug("decision service response Data>>>"+ new Gson().toJson(responseApplicationGroup));
				if(null==kBankHeader){
					serviceResponse.setStatusCode(DecisionServiceResponseDataM.Result.BUSINESS_EXCEPTION);
					logger.debug("kBankHeader is Null");
				}else{
					if(null==kBankHeader.getStatusCode() || "".equals(kBankHeader.getStatusCode())){
						logger.debug("kbank header status code is no value!!");
					}
					
					if(ServiceResponse.Status.SUCCESS.equals(kBankHeader.getStatusCode())){
						serviceResponse.setStatusCode(DecisionServiceResponseDataM.Result.SUCCESS);
						String decisionpoint = responseApplicationGroup.getCallAction();
						//String reCalculateActionFlag = responseApplicationGroup.getReCalculateActionFlag();
						logger.debug("response decisionpoint>>"+decisionpoint);
						if(null==decisionpoint ||"".equals(decisionpoint)){
							decisionpoint = requestData.getDecisionPoint();
						}
//						logger.debug("response ReCalculateActionFlag>>"+reCalculateActionFlag);
//						if(null==reCalculateActionFlag ||"".equals(reCalculateActionFlag)){
//							reCalculateActionFlag = requestData.getReCalculateActionFlag();
//						}
//						if("Y".equals(reCalculateActionFlag)){
//							decisionpoint = decisionpoint+"_RECAL";
//						}
						List<Applications> iibApplications = responseApplicationGroup.getApplications();
						if(!ServiceUtil.empty(iibApplications)){
							for(Applications iibApplication : iibApplications){
								if(DECISION_ERROR.equals(iibApplication.getRecommendDecision())){
									serviceResponse.setStatusCode(DecisionServiceResponseDataM.Result.BUSINESS_EXCEPTION);
									serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SERVICE_RESPONSE,iibApplication.getErrorMessage()));
									break;
								}
							}
						}
						if(ServiceResponse.Status.SUCCESS.equals(serviceResponse.getStatusCode())){
							String fromAction = requestData.getFromAction();
							logger.debug("fromAction : " + fromAction);
							EDecisionServiceResponseMapper responseMapper = null;
							if(!ServiceUtil.empty(fromAction)){
								responseMapper = DecisionServiceUtil.getIIBResponseMapperClassEApp(decisionpoint, fromAction);
							}
							else{
								responseMapper = DecisionServiceUtil.getIIBResponseMapperClassEApp(decisionpoint);	
							}
							responseApplicationGroup.setExecuteAction(requestDecision);
							responseMapper.applicationGroupMapper(requestData.getApplicationGroup(), responseApplicationGroup);
						}
						
						logger.debug("decision mapping ulo from response Data>>>"+ new Gson().toJson(requestData.getApplicationGroup()));
					}else{
						
						List<com.ava.flp.eapp.iib.model.Error> errorMessages =kBankHeader.getErrors();
						serviceResponse.setStatusCode(DecisionServiceResponseDataM.Result.BUSINESS_EXCEPTION);
						serviceResponse.setErrorInfo(errorEapp(ErrorData.ErrorType.SERVICE_RESPONSE,errorMessages));
					}	
				}
				
				iibApplicationRes.setResponseData(responseApplicationGroup);
				
			}else{
				serviceResponse.setStatusCode(serviceTransaction.getStatusCode());
				serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
			}			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		serviceResponse.setServiceData(requestData.getDecisionPoint());
		serviceResponse.setObjectData(iibApplicationRes);
		respTran.serviceInfo(ServiceConstant.IN, iibApplicationRes, serviceResponse);
		return respTran;
	}  
}
