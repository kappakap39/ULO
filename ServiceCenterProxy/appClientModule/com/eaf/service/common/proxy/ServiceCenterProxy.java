package com.eaf.service.common.proxy;

import org.apache.log4j.Logger;

import com.eaf.service.common.api.ServiceHandleManager;
import com.eaf.service.common.master.ServiceLocator;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.util.ServiceUtil;

public class ServiceCenterProxy {
	private static transient Logger logger = Logger.getLogger(ServiceCenterProxy.class);
	public ServiceCenterProxy(int dbType){
		init(dbType);
	}
	public ServiceCenterProxy(){
		init(ServiceLocator.ORIG_DB);
	}
	public void init(int dbType){
		this.transactionId = ServiceUtil.generateTransectionId();
		this.stepId = 0;
		this.dbType = dbType;
		logger.debug("transactionId >> "+transactionId);
		logger.debug("stepId >> "+stepId);
	}
	public ServiceCenterProxy(String transactionId){
		this.transactionId = transactionId;
		this.dbType = ServiceLocator.ORIG_DB;
	}
	public ServiceCenterProxy(int dbType,String transactionId){
		this.transactionId = transactionId;
		this.dbType = dbType;
	}
	private String transactionId;
	private int stepId;	
	private int dbType;
	public ServiceResponseDataM requestService(ServiceRequestDataM serviceRequest) throws Exception{
		return requestService(serviceRequest, "");
	}	
	public ServiceResponseDataM requestService(ServiceRequestDataM serviceRequest,String requestTransactionId) throws Exception{
		stepId++;
		String serviceReqResId = ServiceUtil.generateServiceReqResId();
		logger.debug("transactionId >> "+transactionId);
		logger.debug("requestTransactionId >> "+requestTransactionId);
		logger.debug("serviceReqResId >> "+serviceReqResId);
		logger.debug("stepId >> "+stepId);
		ServiceResponseDataM serviceResponse = new ServiceResponseDataM();
		try{
			serviceRequest.setTransactionId(transactionId);
			serviceRequest.setServiceReqResId(serviceReqResId);
			serviceRequest.setStepId(stepId);
			init(serviceRequest,serviceResponse);
			ServiceHandleManager serviceHandleManager = new ServiceHandleManager(dbType);
				serviceHandleManager.processControl(serviceRequest,serviceResponse,requestTransactionId);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e);
		}
//		logger.debug("serviceResponse >> "+new Gson().toJson(serviceResponse));
		return serviceResponse;
	}	
	private void init(ServiceRequestDataM serviceRequest,ServiceResponseDataM serviceResponse){
		serviceResponse.setTransactionId(serviceRequest.getTransactionId());
		serviceResponse.setServiceReqResId(serviceRequest.getServiceReqResId());
		serviceResponse.setServiceId(serviceRequest.getServiceId());
		serviceResponse.setUserId(serviceRequest.getUserId());
		serviceResponse.setUniqueId(serviceRequest.getUniqueId());
		serviceResponse.setRefId(serviceRequest.getRefId());
		serviceResponse.setIgnoreServiceLog(serviceResponse.isIgnoreServiceLog());
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}	
}
