package com.eaf.service.common.api;

import org.apache.log4j.Logger;

import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.dao.ServiceFactory;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.master.ServiceLocator;
import com.eaf.service.common.model.ServiceDataM;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceReqRespDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.model.ServiceTypeDataM;
import com.eaf.service.common.performance.TraceController;
import com.eaf.service.common.util.ServiceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServiceHandleManager {
	public ServiceHandleManager(int dbType){
		this.dbType = dbType;
	}
	private int dbType = ServiceLocator.ORIG_DB;
	private static transient Logger logger = Logger.getLogger(ServiceHandleManager.class);
	public void processControl(ServiceRequestDataM serviceRequest,ServiceResponseDataM serviceResponse,String requestTransactionId) throws Exception{
		String serviceId = serviceRequest.getServiceId();
		TraceController trace = new TraceController(serviceId,requestTransactionId);
		ServiceTypeDataM serviceType = ServiceCache.getServiceType(serviceId);
		String className = serviceType.getClassName();
		boolean ignoreServiceLog = serviceRequest.isIgnoreServiceLog();
		
		logger.debug("serviceId >> "+serviceId);
		logger.debug("requestTransactionId >> "+requestTransactionId);
		logger.debug("className >> "+className);
		logger.debug("ignoreServiceLog >> "+ignoreServiceLog);
		
		ServiceControl serviceControl = null;
		try{
			serviceControl = (ServiceControl)Class.forName(className).newInstance();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		if(null != serviceControl){
			ServiceRequestTransaction requestTransaction = null;
			ServiceResponseTransaction responseTransaction = null;
			serviceControl.setEndpointUrl(serviceRequest.getEndpointUrl());
			serviceControl.perServiceTransaction(serviceRequest, serviceResponse);
			try{
				trace.create("requestTransaction");
				requestTransaction = serviceControl.requestTransaction();
				trace.end("requestTransaction");
				trace.create("requestLog");
				if(!ignoreServiceLog){
					createLog(requestTransaction);		
				}
				trace.end("requestLog");
				Object serviceRequestObject = requestTransaction.getServiceDataObject();
				logger.debug("serviceRequestObject >> "+serviceRequestObject);		
				logger.debug("EndpointUrl >> "+serviceControl.getEndpointUrl());
				trace.create("serviceTransaction");
				ServiceTransaction serviceTransaction = serviceControl.serviceTransaction(serviceRequestObject);
				trace.end("serviceTransaction");
				trace.create("responseTransaction");
				responseTransaction =  serviceControl.responseTransaction(serviceTransaction);
				trace.end("responseTransaction");
				trace.create("responseLog");
				if(!ignoreServiceLog){
					createLog(responseTransaction);
				}
				trace.end("responseLog");
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new Exception(e);
			}
			trace.trace();
			serviceControl.postServiceTransaction(requestTransaction,responseTransaction);
		}
	}	
	private void createLog(Object requestTransaction){
		ServiceDataM serviceTransaction = (ServiceDataM)requestTransaction;	
		String serviceId = serviceTransaction.getServiceId();
		ServiceTypeDataM serviceType = ServiceCache.getServiceType(serviceId);
		String logServiceFlag = serviceType.getLogService();
		String logDataFlag = serviceType.getLogData();
		logger.debug("serviceId >> "+serviceId);
		logger.debug("logServiceFlag >> "+logServiceFlag);
		logger.debug("logDataFlag >> "+logDataFlag);
		if(ServiceConstant.YES.equals(logServiceFlag)){
			String activityType = serviceTransaction.getActivityType();	
			String contentMsg = "";
			if(ServiceConstant.YES.equals(logDataFlag)){
				Object serviceDataObject = serviceTransaction.getServiceDataObject();
				Gson gson = new GsonBuilder().setExclusionStrategies(new ComplexClassExclusionStrategy()).create();
				try{
					contentMsg = gson.toJson(serviceDataObject);
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					if(null != serviceDataObject){
						contentMsg = serviceDataObject.toString();
					}
				}
			}
			logger.debug("createLog.UserId >> "+serviceTransaction.getuserId());
			ServiceReqRespDataM reqRespM = new ServiceReqRespDataM();
			reqRespM.setReqRespId(serviceTransaction.getServiceReqResId());
			reqRespM.setTransId(serviceTransaction.getTransactionId());
			reqRespM.setServiceId(serviceTransaction.getServiceId());
			reqRespM.setActivityType(activityType);
			reqRespM.setRefCode(serviceTransaction.getRefId());
			reqRespM.setRespCode(serviceTransaction.getStatusCode());			
			ServiceErrorInfo errorInfo = serviceTransaction.getErrorInfo();		
			if(null != errorInfo){
				reqRespM.setRespDesc(errorInfo.getErrorDesc());
				reqRespM.setErrorMessage(errorInfo.getErrorInformation());
			}			
			reqRespM.setContentMsg(contentMsg);
			reqRespM.setCreateBy(serviceTransaction.getuserId());
			reqRespM.setCreateDate(ServiceUtil.getCurrentTimestamp());
			reqRespM.setAppId(serviceTransaction.getUniqueId());
			reqRespM.setServiceData(serviceTransaction.getServiceData());
			try{
				ServiceFactory.getServiceDAO(dbType).createLog(reqRespM);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
	}
}
