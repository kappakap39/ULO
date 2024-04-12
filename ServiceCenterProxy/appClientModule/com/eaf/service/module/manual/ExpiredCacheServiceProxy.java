package com.eaf.service.module.manual;

import iib.ava.com.decisionservice.DecisionServiceHttpPortProxy;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.kasikornbank.expiredcache.ExpiredCache;

public class ExpiredCacheServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(ExpiredCacheServiceProxy.class);
	public final static String serviceId = "ExpiredCacheService";	
	@Override
	public ServiceRequestTransaction requestTransaction() throws Exception {
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		ExpiredCache  requestData = (ExpiredCache)serviceRequest.getObjectData();
		requestTransaction.serviceInfo(ServiceConstant.OUT, requestData, serviceRequest);
		return requestTransaction;
	}

	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			String URL = getEndpointUrl();
			logger.debug("URL : "+URL);
			ExpiredCache requestData = (ExpiredCache)requestServiceObject;
			DecisionServiceHttpPortProxy proxy = new DecisionServiceHttpPortProxy();
			proxy._getDescriptor().setEndpoint(URL);
			ExpiredCache responseData = proxy.expiredCache(requestData);
			serviceTransaction.setServiceTransactionObject(responseData);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceTransaction;
	}
	@Override
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		logger.debug("response transaction");
		ServiceResponseTransaction respTran = new ServiceResponseTransaction();
		ExpiredCache responseExpiredCache = (ExpiredCache)serviceTransaction.getServiceTransactionObject();		
		try {
			logger.debug("response transaction: "+serviceTransaction.getStatusCode());
			if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseExpiredCache){
				serviceResponse.setObjectData(responseExpiredCache);	
				if(ServiceResponse.Status.SUCCESS.equals(responseExpiredCache.getStatusCode())){
					serviceResponse.setStatusCode(DecisionServiceResponseDataM.Result.SUCCESS);
				}else{
					serviceResponse.setStatusCode(responseExpiredCache.getStatusCode());
				}
			}else{
				serviceResponse.setStatusCode(serviceTransaction.getStatusCode());
				serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
			}	
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		respTran.serviceInfo(ServiceConstant.IN, serviceTransaction.getServiceTransactionObject(), serviceResponse);
		return respTran;
	}  
}
