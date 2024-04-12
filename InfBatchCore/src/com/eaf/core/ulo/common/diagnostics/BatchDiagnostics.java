package com.eaf.core.ulo.common.diagnostics;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.core.diagnostics.model.Diagnostics;
import com.core.diagnostics.model.DiagnosticsHander;
import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.hash.Hasher;
import com.eaf.core.ulo.security.hash.HashingFactory;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequest;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.KmobileServiceProxy;
import com.eaf.service.module.manual.UpdateApprovalStatusServiceProxy;
import com.eaf.service.module.model.KmobileRequestDataM;
import com.google.gson.Gson;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.workflow.model.BPMInbox;
import com.orig.bpm.workflow.model.BPMSearchTask;
import com.orig.bpm.workflow.proxy.BPMMainFlowProxy;

public class BatchDiagnostics extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(BatchDiagnostics.class);
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException{
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		ProcessTaskDataM processTask = new ProcessTaskDataM("DIAGNOSTICS_TASK");
		try{
			ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			ArrayList<TaskExecuteDataM> taskExecutes = processTask.getTaskExecutes();
			DiagnosticsHander diagnosticsHander = new DiagnosticsHander();
			diagnosticsHander.setServerName(getComputerName());
			diagnosticsHander.setContext("Batch");
			try{
				diagnosticsHander.create("JDBC orig/app", "Test Connection JDBC = orig/app");
				InfBatchServiceLocator origService = InfBatchServiceLocator.getInstance();
				Connection conn = origService.getConnection(InfBatchServiceLocator.ORIG_DB);
				if(null!=conn)conn.close();
				diagnosticsHander.success("JDBC orig/app");
			}catch(Exception e){
				logger.fatal("ERROR",e);
				diagnosticsHander.error("JDBC orig/app",e);
			}
			try{
				diagnosticsHander.create("JDBC jdbc/dm", "Test Connection JDBC = jdbc/dm");
				InfBatchServiceLocator origService = InfBatchServiceLocator.getInstance();
				Connection conn = origService.getConnection(InfBatchServiceLocator.DM_DB);
				if(null!=conn)conn.close();
				diagnosticsHander.success("JDBC jdbc/dm");
			}catch(Exception e){
				logger.fatal("ERROR",e);
				diagnosticsHander.error("JDBC jdbc/dm",e);
			}
			try{
				diagnosticsHander.create("JDBC jdbc/dih", "Test Connection JDBC = jdbc/dih");
				InfBatchServiceLocator origService = InfBatchServiceLocator.getInstance();
				Connection conn = origService.getConnection(InfBatchServiceLocator.DIH);
				if(null!=conn)conn.close();
				diagnosticsHander.success("JDBC jdbc/dih");
			}catch(Exception e){
				logger.fatal("ERROR",e);
				diagnosticsHander.error("JDBC jdbc/dih",e);
			}
			try{
				diagnosticsHander.create("JDBC jdbc/ias", "Test Connection JDBC = jdbc/ias");
				InfBatchServiceLocator origService = InfBatchServiceLocator.getInstance();
				Connection conn = origService.getConnection(InfBatchServiceLocator.ORIG_IAS);
				if(null!=conn)conn.close();
				diagnosticsHander.success("JDBC jdbc/ias");
			}catch(Exception e){
				logger.fatal("ERROR",e);
				diagnosticsHander.error("JDBC jdbc/ias",e);
			}
			try{
				diagnosticsHander.create("JDBC jdbc/cvrs", "Test Connection JDBC = jdbc/cvrs");
				InfBatchServiceLocator origService = InfBatchServiceLocator.getInstance();
				Connection conn = origService.getConnection(InfBatchServiceLocator.CVRS_DB);
				if(null!=conn)conn.close();
				diagnosticsHander.success("JDBC jdbc/cvrs");
			}catch(Exception e){
				logger.fatal("ERROR",e);
				diagnosticsHander.error("JDBC jdbc/cvrs",e);
			}
			try{
				diagnosticsHander.create("JDBC jdbc/bpm", "Test Connection JDBC = jdbc/bpm");
				InfBatchServiceLocator origService = InfBatchServiceLocator.getInstance();
				Connection conn = origService.getConnection(OrigServiceLocator.BPM);
				if(null!=conn)conn.close();
				diagnosticsHander.success("JDBC jdbc/bpm");
			}catch(Exception e){
				logger.fatal("ERROR",e);
				diagnosticsHander.error("JDBC jdbc/bpm",e);
			}
			try{
				String BPM_HOST = InfBatchProperty.getInfBatchConfig("BPM_HOST");
				String BPM_PORT = InfBatchProperty.getInfBatchConfig("BPM_PORT");
				String USER_NAME = InfBatchProperty.getInfBatchConfig("BPM_USER_ID");
				String PASSWORD = InfBatchProperty.getInfBatchConfig("BPM_PASSWORD");
				diagnosticsHander.create("BPM", "Test Connection BPM "+BPM_HOST+":"+BPM_PORT);
				try{
					BPMMainFlowProxy proxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),USER_NAME,PASSWORD);
					BPMSearchTask searchTask = new BPMSearchTask();
					searchTask.setUserId(USER_NAME);
					searchTask.setActiveInbox(false);
					BPMInbox bpmInbox = proxy.getInbox(searchTask);
					if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(bpmInbox.getResultCode())){
						diagnosticsHander.success("BPM");
					}else{
						diagnosticsHander.error("BPM",bpmInbox.getResultDesc());
					}
				}catch(Exception e){
					logger.fatal("ERROR",e);
					diagnosticsHander.error("BPM",e);
				}
				diagnosticsHander.success("BPM");
			}catch(Exception e){
				logger.fatal("ERROR",e);
				diagnosticsHander.error("BPM",e);
			}
			try{
				String URL = SystemConfig.getProperty("NOTIFICATION_SEND_KMOBILE_URL");
				diagnosticsHander.create(KmobileServiceProxy.serviceId, "Test Connection "+KmobileServiceProxy.serviceId+" "+URL);
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
					serviceRequest.setServiceId(KmobileServiceProxy.serviceId);
					serviceRequest.setUserId("SYSTEM");
					serviceRequest.setEndpointUrl(URL);
					serviceRequest.setIgnoreServiceLog(true);
				KmobileRequestDataM objectData = new KmobileRequestDataM();
					serviceRequest.setObjectData(objectData);
				ServiceCenterProxy proxy = new ServiceCenterProxy();
				ServiceResponseDataM response = proxy.requestService(serviceRequest);				
				ServiceErrorInfo errorInfo = response.getErrorInfo();
				if (errorInfo != null) {
					String errorType = errorInfo.getErrorType();
					if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
						String errorInfoStr = new Gson().toJson(response.getErrorInfo());
						diagnosticsHander.error(KmobileServiceProxy.serviceId,errorInfoStr);
					} else {
						diagnosticsHander.success(KmobileServiceProxy.serviceId);
					}
				} else {
					diagnosticsHander.success(KmobileServiceProxy.serviceId);
				}						
			}catch(Exception e){
				logger.fatal("ERROR",e);
				diagnosticsHander.error(KmobileServiceProxy.serviceId,e);
			}
//			try{
//				String URL = SystemConfig.getProperty("SMS_SERVICE_ENDPOINT_URL");
//				diagnosticsHander.create(SMSServiceProxy.serviceId, "Test Connection "+SMSServiceProxy.serviceId+" "+URL);
//				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
//					serviceRequest.setServiceId(SMSServiceProxy.serviceId);
//					serviceRequest.setUserId("SYSTEM");
//					serviceRequest.setEndpointUrl(URL);
//					serviceRequest.setIgnoreServiceLog(true);
//				SMSRequestDataM objectData = new SMSRequestDataM();
//					serviceRequest.setObjectData(objectData);
//				ServiceCenterProxy proxy = new ServiceCenterProxy();
//				ServiceResponseDataM response = proxy.requestService(serviceRequest);
//				if(ServiceResponse.Status.SUCCESS.equals(response.getStatusCode())){
//					diagnosticsHander.success(SMSServiceProxy.serviceId);
//				}else{
//					String errorInfo = new Gson().toJson(response.getErrorInfo());
//					diagnosticsHander.error(SMSServiceProxy.serviceId,errorInfo);
//				}
//			}catch(Exception e){
//				logger.fatal("ERROR",e);
//				diagnosticsHander.error(SMSServiceProxy.serviceId,e);
//			}
			try{
				String URL = SystemConfig.getProperty("UPDATE_APPROVAL_STATUS_URL");
				diagnosticsHander.create(UpdateApprovalStatusServiceProxy.serviceId, "Test Connection "+UpdateApprovalStatusServiceProxy.serviceId+" "+URL);
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
					serviceRequest.setServiceId(UpdateApprovalStatusServiceProxy.serviceId);
					serviceRequest.setUserId("SYSTEM");
					serviceRequest.setEndpointUrl(URL);
					serviceRequest.setIgnoreServiceLog(true);
				UpdateApprovalStatusRequest objectData = new UpdateApprovalStatusRequest();
					serviceRequest.setObjectData(objectData);
				ServiceCenterProxy proxy = new ServiceCenterProxy();
				ServiceResponseDataM response = proxy.requestService(serviceRequest);
				ServiceErrorInfo errorInfo = response.getErrorInfo();
				if (errorInfo != null) {
					String errorType = errorInfo.getErrorType();
					if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
						String errorInfoStr = new Gson().toJson(response.getErrorInfo());
						diagnosticsHander.error(UpdateApprovalStatusServiceProxy.serviceId,errorInfoStr);
					} else {
						diagnosticsHander.success(UpdateApprovalStatusServiceProxy.serviceId);
					}
				} else {
					diagnosticsHander.success(UpdateApprovalStatusServiceProxy.serviceId);
				}	
			}catch(Exception e){
				logger.fatal("ERROR",e);
				diagnosticsHander.error(UpdateApprovalStatusServiceProxy.serviceId,e);
			}
			try{
				diagnosticsHander.create("DIHEncryptor", "Test DIHEncryptor");
				Encryptor enc = EncryptorFactory.getDIHEncryptor();
				String data = "123456789";
				String encrypt = enc.encrypt(data);
				enc.decrypt(encrypt);
				diagnosticsHander.success("DIHEncryptor");
			}catch(Exception e){
				diagnosticsHander.error("DIHEncryptor",e);
			}
			try{
				diagnosticsHander.create("KmAlertEncryptor", "Test KmAlertEncryptor");
				Encryptor enc = EncryptorFactory.getKmAlertEncryptor();
				String data = "123456789";
				String encrypt = enc.encrypt(data);
				enc.decrypt(encrypt);
				diagnosticsHander.success("KmAlertEncryptor");
			}catch(Exception e){
				logger.fatal("ERROR",e);
				diagnosticsHander.error("KmAlertEncryptor",e);
			}
			try{
				diagnosticsHander.create("SHA256Hasher", "Test SHA256Hasher");
				Hasher enc = HashingFactory.getSHA256Hasher();
				String encryptedStr = "123456789";
				enc.getHashCode(encryptedStr);
				diagnosticsHander.success("SHA256Hasher");
			}catch(Exception e){
				logger.fatal("ERROR",e);
				diagnosticsHander.error("SHA256Hasher",e);
			}
			TaskExecuteDataM _taskExecute = new TaskExecuteDataM();
			_taskExecute.setResponseObject(diagnosticsHander);
			taskExecutes.add(_taskExecute);
			logger.info("==================================================================================");
			if(null!=taskExecutes){
				for(TaskExecuteDataM taskExecute:taskExecutes){
					if(InfBatchConstant.ResultCode.FAIL.equals(taskExecute.getResultCode())){
						logger.info("Execute Fail : "+taskExecute.getResultDesc());
					}else{
						DiagnosticsHander responseObject = (DiagnosticsHander)taskExecute.getResponseObject();
						logger.info("ServerName : "+responseObject.getServerName());
						logger.info("Module : "+responseObject.getContext());
						logger.info("==================================================================================");
						List<Diagnostics> diagnostics = responseObject.getDiagnostics();
						if(null!=diagnostics){
							for(Diagnostics diagnostic:diagnostics){
								logger.info("Diagnostics Id : "+diagnostic.getDiagnosticsId());
								logger.info("Diagnostics Info : "+diagnostic.getDiagnosticsInfo());
								logger.info("Status Code : "+diagnostic.getStatusCode());
								if(null!=diagnostic.getStatusDesc()){
									logger.info("Status Desc : "+diagnostic.getStatusDesc());
								}
								if(null!=diagnostic.getSolve()){
									logger.info("Solve : "+diagnostic.getSolve());
								}
								logger.info("-----------------------------------------------------------------------------------");
							}
						}
					}
					logger.info("==================================================================================");
				}
			}
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	private String getComputerName(){
	    Map<String, String> env = System.getenv();
	    if (env.containsKey("COMPUTERNAME"))
	        return env.get("COMPUTERNAME");
	    else if (env.containsKey("HOSTNAME"))
	        return env.get("HOSTNAME");
	    else
	        return "Unknown Computer";
	}
}
