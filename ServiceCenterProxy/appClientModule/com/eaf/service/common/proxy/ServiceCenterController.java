package com.eaf.service.common.proxy;

import org.apache.log4j.Logger;

import com.eaf.service.common.api.ComplexClassExclusionStrategy;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.dao.ServiceFactory;
import com.eaf.service.common.master.ServiceLocator;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.model.ServiceReqRespDataM;
import com.eaf.service.common.model.ServiceTypeDataM;
import com.eaf.service.common.util.ServiceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServiceCenterController extends ServiceCenterProxy{
	private static transient Logger logger = Logger.getLogger(ServiceCenterController.class);
	public ServiceCenterController(){
		this.transactionId = ServiceUtil.generateTransectionId();
		this.dbType = ServiceLocator.ORIG_DB;
		logger.debug("transactionId >> "+transactionId);
		logger.debug("dbType >> "+dbType);
	}
	public ServiceCenterController(String transactionId){
		this.dbType = ServiceLocator.ORIG_DB;
		
		logger.debug("transactionId >> "+transactionId);
		logger.debug("dbType >> "+dbType);
	}
	public ServiceCenterController(int dbType){
		this.transactionId = ServiceUtil.generateTransectionId();
		this.dbType = dbType;
		logger.debug("transactionId >> "+transactionId);
		logger.debug("dbType >> "+dbType);
	}
	public String getTransactionId(){
		return transactionId;
	}
	private String transactionId;
	private int dbType;
	public void createLog(ServiceLogDataM serviceLog){
		String serviceId = serviceLog.getServiceId();
		ServiceTypeDataM serviceType = ServiceCache.getServiceType(serviceId);
		if(null==serviceType){
			serviceType = new ServiceTypeDataM();
		}
		String logServiceFlag = serviceType.getLogService();
		String logDataFlag = serviceType.getLogData();
		logger.debug("serviceId >> "+serviceId);
		logger.debug("logServiceFlag >> "+logServiceFlag);
		logger.debug("logDataFlag >> "+logDataFlag);
		if(ServiceConstant.YES.equals(logServiceFlag)){
			String contentMsg = "";
			if(ServiceConstant.YES.equals(logDataFlag)){
				Gson gson = new GsonBuilder().setExclusionStrategies(new ComplexClassExclusionStrategy()).create();
				Object serviceDataObject = serviceLog.getServiceDataObject();
				try{
					contentMsg = gson.toJson(serviceDataObject);
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					if(null != serviceDataObject){
						contentMsg = serviceDataObject.toString();
					}
				}
			}
			ServiceReqRespDataM reqRespM = new ServiceReqRespDataM();
			String reqRespId = serviceLog.getServiceReqRespId();
			if(ServiceUtil.empty(reqRespId)){
				reqRespId = ServiceUtil.generateServiceReqResId();
			}
			reqRespM.setReqRespId(reqRespId);
			reqRespM.setTransId(transactionId);
			reqRespM.setRespCode(serviceLog.getRespCode());
			reqRespM.setRespDesc(serviceLog.getRespDesc());
			reqRespM.setErrorMessage(serviceLog.getErrorMessage());
			reqRespM.setServiceId(serviceLog.getServiceId());
			reqRespM.setActivityType(serviceLog.getActivityType());
			reqRespM.setContentMsg(contentMsg);
			reqRespM.setRefCode(serviceLog.getRefCode());
			reqRespM.setCreateDate(ServiceUtil.getCurrentTimestamp());
			reqRespM.setAppId(serviceLog.getUniqueId());
			reqRespM.setCreateBy(serviceLog.getUserId());
			try{
				ServiceFactory.getServiceDAO(dbType).createLog(reqRespM);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
	}
}
