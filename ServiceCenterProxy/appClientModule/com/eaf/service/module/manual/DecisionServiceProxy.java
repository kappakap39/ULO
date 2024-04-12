package com.eaf.service.module.manual;

import iib.ava.com.decisionservice.DecisionServiceHttpPortProxy;

import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

import decisionservice_iib.ApplicationDataM;
import decisionservice_iib.ApplicationGroupDataM;

public class DecisionServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(DecisionServiceProxy.class);
	public final static String serviceId = "DecisionService";	
	private String DECISION_ERROR = ServiceCache.getConstant("DECISION_ERROR");
	private String requestDecision = null;
	@Override
	public ServiceRequestTransaction requestTransaction() throws Exception {
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		DecisionServiceRequestDataM  applicationRequest = (DecisionServiceRequestDataM)serviceRequest.getObjectData();
		String decisionPoint =applicationRequest.getDecisionPoint();//DecisionServiceUtil.getAction(applicationRequest);
		logger.debug("decisionPoint : "+decisionPoint);
		logger.debug("reCalculateActionFlag : "+applicationRequest.getApplicationGroup().getReCalculateActionFlag());
		requestDecision = decisionPoint;
		ApplicationGroupDataM  requestData = DecisionServiceUtil.getIIBRequestMapperClass(decisionPoint)
				.getDecsionServiceMapper(applicationRequest.getApplicationGroup(), decisionPoint);		
		decisionservice_iib.KBankHeader  KBankHeader = new decisionservice_iib.KBankHeader();
		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		KBankHeader.setFuncNm(serviceId);
		KBankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
		KBankHeader.setRqDt(DecisionServiceUtil.toString(DecisionServiceUtil.getApplicationDate()));
		KBankHeader.setRqAppId(RqAppId);
		if(null!= serviceRequest.getUserId() && !"".equals(serviceRequest.getUserId())){
			KBankHeader.setUserId(serviceRequest.getUserId());
		}else{
			KBankHeader.setUserId(applicationRequest.getUserId());		
		}		
		KBankHeader.setUserLangPref(DecisionServiceUtil.TH);
		KBankHeader.setCorrID(serviceRequest.getServiceReqResId());
		KBankHeader.setTransactionId( serviceRequest.getTransactionId());
		KBankHeader.setTerminalId(ServiceCache.getGeneralParam("DEFAULT_TERMINAL_ID"));
		requestData.setKBankHeader(KBankHeader);
		
		serviceRequest.setServiceData(decisionPoint);
		logger.debug("requestData>>>"+ new Gson().toJson(requestData));
		requestTransaction.serviceInfo(ServiceConstant.OUT, requestData, serviceRequest);
		return requestTransaction;
	}

	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			String URL = getEndpointUrl();
			logger.debug("URL : "+URL);
			ApplicationGroupDataM requestData = (ApplicationGroupDataM)requestServiceObject;
			ApplicationGroupDataM responseData = decisionService(URL, requestData);
			serviceTransaction.setServiceTransactionObject(responseData);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceTransaction;
	}
	private ApplicationGroupDataM decisionService(String URL,ApplicationGroupDataM requestData){
		DecisionServiceHttpPortProxy proxy = new DecisionServiceHttpPortProxy();
		proxy._getDescriptor().setEndpoint(URL);
		ApplicationGroupDataM responseData = proxy.decisionService(requestData);
		return responseData;
	}
	@Override
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		logger.debug("response transaction");
		ServiceResponseTransaction respTran = new ServiceResponseTransaction();
		DecisionServiceRequestDataM  requestData = (DecisionServiceRequestDataM)serviceRequest.getObjectData();
		DecisionServiceResponseDataM iibApplicationRes= new DecisionServiceResponseDataM();
		try {
			if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode())){
				ApplicationGroupDataM responseApplicationGroup = (ApplicationGroupDataM)serviceTransaction.getServiceTransactionObject();
				if(null==responseApplicationGroup){
					logger.debug(" response object data is null!!");
				}
				decisionservice_iib.KBankHeader  kBankHeader  = responseApplicationGroup.getKBankHeader();
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
						String reCalculateActionFlag = responseApplicationGroup.getReCalculateActionFlag();
						logger.debug("response decisionpoint>>"+decisionpoint);
						if(null==decisionpoint ||"".equals(decisionpoint)){
							decisionpoint = requestData.getDecisionPoint();
						}
						logger.debug("response ReCalculateActionFlag>>"+reCalculateActionFlag);
						if(null==reCalculateActionFlag ||"".equals(reCalculateActionFlag)){
							reCalculateActionFlag = requestData.getReCalculateActionFlag();
						}
						if("Y".equals(reCalculateActionFlag)){
							decisionpoint = decisionpoint+"_RECAL";
						}
						List<ApplicationDataM> iibApplications = responseApplicationGroup.getApplications();
						if(!ServiceUtil.empty(iibApplications)){
							for(ApplicationDataM iibApplication : iibApplications){
								if(DECISION_ERROR.equals(iibApplication.getRecommendDecision())){
									serviceResponse.setStatusCode(DecisionServiceResponseDataM.Result.BUSINESS_EXCEPTION);
									serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SERVICE_RESPONSE,iibApplication.getErrorMessage()));
									break;
								}
							}
						}
						if(ServiceResponse.Status.SUCCESS.equals(serviceResponse.getStatusCode())){
							DecisionServiceResponseMapper responseMapper = DecisionServiceUtil.getIIBResponseMapperClass(decisionpoint);	
							responseApplicationGroup.setExecuteAction(requestDecision);
							responseMapper.applicationGroupMapper(requestData.getApplicationGroup(), responseApplicationGroup);
						}
						
						logger.debug("decision mapping ulo from response Data>>>"+ new Gson().toJson(requestData.getApplicationGroup()));
					}else{
						List<decisionservice_iib.Error> errorMessages =kBankHeader.getErrors();
						serviceResponse.setStatusCode(DecisionServiceResponseDataM.Result.BUSINESS_EXCEPTION);
						serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SERVICE_RESPONSE,errorMessages));
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
