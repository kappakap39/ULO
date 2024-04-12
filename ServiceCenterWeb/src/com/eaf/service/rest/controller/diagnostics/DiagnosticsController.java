package com.eaf.service.rest.controller.diagnostics;

import java.sql.Connection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.core.diagnostics.model.DiagnosticsHander;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.im.rest.docset.model.InquireDocSetRequest;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.model.pega.FollowUpRequest;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequest;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.InquiryDocSetControlProxy;
import com.eaf.service.module.manual.KmobileServiceProxy;
import com.eaf.service.module.manual.PegaFollowUpServiceProxy;
import com.eaf.service.module.manual.SMSServiceProxy;
import com.eaf.service.module.manual.UpdateApprovalStatusServiceProxy;
import com.eaf.service.module.model.KmobileRequestDataM;
import com.eaf.service.module.model.SMSRequestDataM;
import com.google.gson.Gson;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.workflow.model.BPMInbox;
import com.orig.bpm.workflow.model.BPMSearchTask;
import com.orig.bpm.workflow.proxy.BPMMainFlowProxy;

@RestController
@RequestMapping("/service/diagnostics")
public class DiagnosticsController {
	private static transient Logger logger = Logger.getLogger(DiagnosticsController.class);
	private String getComputerName(){
	    Map<String, String> env = System.getenv();
	    if (env.containsKey("COMPUTERNAME"))
	        return env.get("COMPUTERNAME");
	    else if (env.containsKey("HOSTNAME"))
	        return env.get("HOSTNAME");
	    else
	        return "Unknown Computer";
	}
	@RequestMapping(value="/process/{actionName}",method={RequestMethod.POST})
    public @ResponseBody ResponseEntity<DiagnosticsHander> process(@PathVariable("actionName") String actionName){
		logger.info("actionName : "+actionName);
		DiagnosticsHander diagnosticsHander = new DiagnosticsHander();
		diagnosticsHander.setServerName(getComputerName());
		diagnosticsHander.setContext("ServiceCenter");
		try{
			diagnosticsHander.create("JDBC orig/app", "Test Connection JDBC = orig/app");
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			Connection conn = origService.getConnection(OrigServiceLocator.ORIG_DB);
			if(null!=conn)conn.close();
			diagnosticsHander.success("JDBC orig/app");
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("JDBC orig/app",e);
		}
		try{
			diagnosticsHander.create("JDBC orig/dih", "Test Connection JDBC = orig/dih");
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			Connection conn = origService.getConnection(OrigServiceLocator.DIH);
			if(null!=conn)conn.close();
			diagnosticsHander.success("JDBC orig/dih");
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("JDBC orig/dih",e);
		}
		try{
			diagnosticsHander.create("JDBC orig/dm", "Test Connection JDBC = orig/dm");
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			Connection conn = origService.getConnection(OrigServiceLocator.WAREHOUSE_DB);
			if(null!=conn)conn.close();
			diagnosticsHander.success("JDBC orig/dm");
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("JDBC orig/dm",e);
		}
		
		try{
			String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
			String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
			String USER_NAME = SystemConfig.getProperty("BPM_USER_ID");
			String PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
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
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(UpdateApprovalStatusServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(UpdateApprovalStatusServiceProxy.serviceId,he);
			}	
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(UpdateApprovalStatusServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("PEGA_FOLLOWUP_URL");
			diagnosticsHander.create(PegaFollowUpServiceProxy.serviceId, "Test Connection "+PegaFollowUpServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(PegaFollowUpServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			FollowUpRequest objectData = new FollowUpRequest();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);			
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(PegaFollowUpServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(PegaFollowUpServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(PegaFollowUpServiceProxy.serviceId);
			}	
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(PegaFollowUpServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(PegaFollowUpServiceProxy.serviceId,he);
			}	
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(PegaFollowUpServiceProxy.serviceId,e);
		}

		try{
			String URL = SystemConfig.getProperty("IM_INQUIRE_DOC_SET_URL");
			diagnosticsHander.create(InquiryDocSetControlProxy.serviceId, "Test Connection "+InquiryDocSetControlProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(InquiryDocSetControlProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			InquireDocSetRequest objectData = new InquireDocSetRequest();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);			
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(InquiryDocSetControlProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(InquiryDocSetControlProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(InquiryDocSetControlProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(InquiryDocSetControlProxy.serviceId);
			} else {
				diagnosticsHander.error(InquiryDocSetControlProxy.serviceId,he);
			}	
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(InquiryDocSetControlProxy.serviceId,e);
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
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(KmobileServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(KmobileServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(KmobileServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("SMS_SERVICE_ENDPOINT_URL");
			diagnosticsHander.create(SMSServiceProxy.serviceId, "Test Connection "+SMSServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(SMSServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			SMSRequestDataM objectData = new SMSRequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(SMSServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(SMSServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(SMSServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(SMSServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(SMSServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(SMSServiceProxy.serviceId,e);
		}
		return ResponseEntity.ok(diagnosticsHander);
	}
}
