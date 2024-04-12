package com.eaf.orig.rest.controller.diagnostics;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.core.diagnostics.model.DiagnosticsHander;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.hash.Hasher;
import com.eaf.core.ulo.security.hash.HashingFactory;
import com.eaf.ias.shared.model.service.IASServiceRequest;
import com.eaf.ias.shared.model.service.IASServiceResponse;
import com.eaf.im.rest.attach.model.AttachFileRequest;
import com.eaf.im.rest.attach.model.DeleteFileRequest;
import com.eaf.im.rest.attach.model.RetrieveFileRequest;
import com.eaf.im.rest.callIM.model.CallIMRequest;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionApplicationUtil;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionServiceResponse;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.pega.FollowUpRequest;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequest;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.AttachFileControlProxy;
import com.eaf.service.module.manual.CBS1215I01ServiceProxy;
import com.eaf.service.module.manual.CIS0314I01ServiceProxy;
import com.eaf.service.module.manual.CIS0315U01ServiceProxy;
import com.eaf.service.module.manual.CIS1034A01ServiceProxy;
import com.eaf.service.module.manual.CIS1035A01ServiceProxy;
import com.eaf.service.module.manual.CIS1036A01ServiceProxy;
import com.eaf.service.module.manual.CIS1037A01ServiceProxy;
import com.eaf.service.module.manual.CIS1044U01ServiceProxy;
import com.eaf.service.module.manual.CIS1046A01ServiceProxy;
import com.eaf.service.module.manual.CIS1047O01ServiceProxy;
import com.eaf.service.module.manual.CIS1048O01ServiceProxy;
import com.eaf.service.module.manual.CVRSDQ0001ServiceProxy;
import com.eaf.service.module.manual.CallIMControlProxy;
import com.eaf.service.module.manual.CreateKVIAppServiceProxy;
import com.eaf.service.module.manual.DeleteFileControlProxy;
import com.eaf.service.module.manual.EditKVIAppServiceProxy;
import com.eaf.service.module.manual.PegaFollowUpServiceProxy;
import com.eaf.service.module.manual.RetrieveFileControlProxy;
import com.eaf.service.module.manual.UpdateApprovalStatusServiceProxy;
import com.eaf.service.module.model.CBS1215I01RequestDataM;
import com.eaf.service.module.model.CIS0314I01RequestDataM;
import com.eaf.service.module.model.CIS0315U01RequestDataM;
import com.eaf.service.module.model.CIS1034A01RequestDataM;
import com.eaf.service.module.model.CIS1035A01RequestDataM;
import com.eaf.service.module.model.CIS1036A01RequestDataM;
import com.eaf.service.module.model.CIS1037A01RequestDataM;
import com.eaf.service.module.model.CIS1044U01RequestDataM;
import com.eaf.service.module.model.CIS1046A01RequestDataM;
import com.eaf.service.module.model.CIS1047O01RequestDataM;
import com.eaf.service.module.model.CIS1048O01RequestDataM;
import com.eaf.service.module.model.CVRSDQ0001RequestDataM;
import com.eaf.service.module.model.CreateKVIAppRequestDataM;
import com.eaf.service.module.model.EditKVIAppRequestDataM;
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
		diagnosticsHander.setContext("ORIG");
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
			diagnosticsHander.create("JDBC orig/ias", "Test Connection JDBC = orig/ias");
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			Connection conn = origService.getConnection(OrigServiceLocator.ORIG_IAS);
			if(null!=conn)conn.close();
			diagnosticsHander.success("JDBC orig/ias");
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("JDBC orig/ias",e);
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
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
			serviceRequest.setServiceId(AttachFileControlProxy.serviceId);
			String URL = SystemConfig.getProperty("IM_ATTACH_UPLOAD_URL");
			diagnosticsHander.create(AttachFileControlProxy.serviceId, "Test Connection "+AttachFileControlProxy.serviceId+" "+URL);
			AttachFileRequest attachFileRequest = new AttachFileRequest();
			serviceRequest.setObjectData(attachFileRequest);
			serviceRequest.putRawData("ContentFile",null);		
			serviceRequest.setEndpointUrl(URL);
			serviceRequest.setIgnoreServiceLog(true);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(AttachFileControlProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(AttachFileControlProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(AttachFileControlProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(AttachFileControlProxy.serviceId);
			} else {
				diagnosticsHander.error(AttachFileControlProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(AttachFileControlProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("IM_TOKEN_URL");
			diagnosticsHander.create(CallIMControlProxy.serviceId, "Test Connection "+CallIMControlProxy.serviceId+" "+URL);
			CallIMRequest callIMRequest  = new CallIMRequest();
				callIMRequest.setRequestTokenOnly(MConstant.FLAG_N);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CallIMControlProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setObjectData(callIMRequest);
				serviceRequest.setIgnoreServiceLog(true);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CallIMControlProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CallIMControlProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CallIMControlProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CallIMControlProxy.serviceId);
			} else {
				diagnosticsHander.error(CallIMControlProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CallIMControlProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("IAS_SERVICE_AUTHENTICATION_URL");
			diagnosticsHander.create("IAS_SERVICE_AUTHENTICATION", "Test Connection IAS AuthenTication "+URL);
			IASServiceRequest serviceRequest = new IASServiceRequest();
			serviceRequest.setUserName("SYSTEM");
			serviceRequest.setSystemName(OrigConstant.SYSTEM_NAME);				
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
			ResponseEntity<IASServiceResponse> responseEntity = restTemplate.postForEntity(URL,serviceRequest,IASServiceResponse.class);
			IASServiceResponse serviceResponse = responseEntity.getBody();
			logger.debug("serviceResponse : "+new Gson().toJson(serviceResponse));
			if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
				diagnosticsHander.success("IAS_SERVICE_AUTHENTICATION");
			}else{
				diagnosticsHander.error("IAS_SERVICE_AUTHENTICATION",serviceResponse.getResponseDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("IAS_SERVICE_AUTHENTICATION",e);
		}
		try{
			String URL = SystemConfig.getProperty("CBS1215I01_ENDPOINT_URL");
			diagnosticsHander.create(CBS1215I01ServiceProxy.serviceId, "Test Connection "+CBS1215I01ServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CBS1215I01ServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
				CBS1215I01RequestDataM objectData = new CBS1215I01RequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CBS1215I01ServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CBS1215I01ServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CBS1215I01ServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CBS1215I01ServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CBS1215I01ServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CBS1215I01ServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("CIS0314I01_ENDPOINT_URL");
			diagnosticsHander.create(CIS0314I01ServiceProxy.serviceId, "Test Connection "+CIS0314I01ServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CIS0314I01ServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			CIS0314I01RequestDataM objectData = new CIS0314I01RequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CIS0314I01ServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CIS0314I01ServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CIS0314I01ServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CIS0314I01ServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CIS0314I01ServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CIS0314I01ServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("CIS0315U01_ENDPOINT_URL");
			diagnosticsHander.create(CIS0315U01ServiceProxy.serviceId, "Test Connection "+CIS0315U01ServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CIS0315U01ServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			CIS0315U01RequestDataM objectData = new CIS0315U01RequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CIS0315U01ServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CIS0315U01ServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CIS0315U01ServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CIS0315U01ServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CIS0315U01ServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CIS0315U01ServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("CIS1034A01_ENDPOINT_URL");
			diagnosticsHander.create(CIS1034A01ServiceProxy.serviceId, "Test Connection "+CIS1034A01ServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CIS1034A01ServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			CIS1034A01RequestDataM objectData = new CIS1034A01RequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CIS1034A01ServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CIS1034A01ServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CIS1034A01ServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CIS1034A01ServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CIS1034A01ServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CIS1034A01ServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("CIS1035A01_ENDPOINT_URL");
			diagnosticsHander.create(CIS1035A01ServiceProxy.serviceId, "Test Connection "+CIS1035A01ServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CIS1035A01ServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
				CIS1035A01RequestDataM objectData = new CIS1035A01RequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CIS1035A01ServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CIS1035A01ServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CIS1035A01ServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CIS1035A01ServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CIS1035A01ServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CIS1035A01ServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("CIS1036A01_ENDPOINT_URL");
			diagnosticsHander.create(CIS1036A01ServiceProxy.serviceId, "Test Connection "+CIS1036A01ServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CIS1036A01ServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			CIS1036A01RequestDataM objectData = new CIS1036A01RequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CIS1036A01ServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CIS1036A01ServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CIS1036A01ServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CIS1036A01ServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CIS1036A01ServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CIS1036A01ServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("CIS1037A01_ENDPOINT_URL");
			diagnosticsHander.create(CIS1037A01ServiceProxy.serviceId, "Test Connection "+CIS1037A01ServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CIS1037A01ServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
				CIS1037A01RequestDataM objectData = new CIS1037A01RequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CIS1037A01ServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CIS1037A01ServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CIS1037A01ServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CIS1037A01ServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CIS1037A01ServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CIS1037A01ServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("CIS1044U01_ENDPOINT_URL");
			diagnosticsHander.create(CIS1044U01ServiceProxy.serviceId, "Test Connection "+CIS1044U01ServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CIS1044U01ServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			CIS1044U01RequestDataM objectData = new CIS1044U01RequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CIS1044U01ServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CIS1044U01ServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CIS1044U01ServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CIS1044U01ServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CIS1044U01ServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CIS1044U01ServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("CIS1046A01_ENDPOINT_URL");
			diagnosticsHander.create(CIS1046A01ServiceProxy.serviceId, "Test Connection "+CIS1046A01ServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CIS1046A01ServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			CIS1046A01RequestDataM objectData = new CIS1046A01RequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CIS1046A01ServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CIS1046A01ServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CIS1046A01ServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CIS1046A01ServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CIS1046A01ServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CIS1046A01ServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("CIS1047O01_ENDPOINT_URL");
			diagnosticsHander.create(CIS1047O01ServiceProxy.serviceId, "Test Connection "+CIS1047O01ServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CIS1047O01ServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			CIS1047O01RequestDataM objectData = new CIS1047O01RequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CIS1047O01ServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CIS1047O01ServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CIS1047O01ServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CIS1047O01ServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CIS1047O01ServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CIS1047O01ServiceProxy.serviceId,e);
		}

		try{
			String URL = SystemConfig.getProperty("CIS1048O01_ENDPOINT_URL");
			diagnosticsHander.create(CIS1048O01ServiceProxy.serviceId, "Test Connection "+CIS1048O01ServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CIS1048O01ServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			CIS1048O01RequestDataM objectData = new CIS1048O01RequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CIS1048O01ServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CIS1048O01ServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CIS1048O01ServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CIS1048O01ServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CIS1048O01ServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CIS1048O01ServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("CREATE_KVI_ENDPOINT_URL");
			diagnosticsHander.create(CreateKVIAppServiceProxy.serviceId, "Test Connection "+CreateKVIAppServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CreateKVIAppServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			CreateKVIAppRequestDataM objectData = new CreateKVIAppRequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CreateKVIAppServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CreateKVIAppServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CreateKVIAppServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CreateKVIAppServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CreateKVIAppServiceProxy.serviceId,he);
			}	
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CreateKVIAppServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("EDIT_KVI_ENDPOINT_URL");
			diagnosticsHander.create(EditKVIAppServiceProxy.serviceId, "Test Connection "+EditKVIAppServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(EditKVIAppServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			EditKVIAppRequestDataM objectData = new EditKVIAppRequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(EditKVIAppServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(EditKVIAppServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(EditKVIAppServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(EditKVIAppServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(EditKVIAppServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(EditKVIAppServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("CVRSDQ0001_ENDPOINT_URL");
			diagnosticsHander.create(CVRSDQ0001ServiceProxy.serviceId, "Test Connection "+CVRSDQ0001ServiceProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CVRSDQ0001ServiceProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
				CVRSDQ0001RequestDataM objectData = new CVRSDQ0001RequestDataM();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(CVRSDQ0001ServiceProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(CVRSDQ0001ServiceProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(CVRSDQ0001ServiceProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(CVRSDQ0001ServiceProxy.serviceId);
			} else {
				diagnosticsHander.error(CVRSDQ0001ServiceProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(CVRSDQ0001ServiceProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("IM_ATTACH_DELETE_URL");
			diagnosticsHander.create(DeleteFileControlProxy.serviceId, "Test Connection "+DeleteFileControlProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(DeleteFileControlProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
				DeleteFileRequest objectData = new DeleteFileRequest();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(DeleteFileControlProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(DeleteFileControlProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(DeleteFileControlProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(DeleteFileControlProxy.serviceId);
			} else {
				diagnosticsHander.error(DeleteFileControlProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(DeleteFileControlProxy.serviceId,e);
		}
		try{
			String URL = SystemConfig.getProperty("IM_ATTACH_DOWNLOAD_URL");
			diagnosticsHander.create(RetrieveFileControlProxy.serviceId, "Test Connection "+RetrieveFileControlProxy.serviceId+" "+URL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(RetrieveFileControlProxy.serviceId);
				serviceRequest.setUserId("SYSTEM");
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setIgnoreServiceLog(true);
			RetrieveFileRequest objectData = new RetrieveFileRequest();
				serviceRequest.setObjectData(objectData);
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM response = proxy.requestService(serviceRequest);
			ServiceErrorInfo errorInfo = response.getErrorInfo();
			if (errorInfo != null) {
				String errorType = errorInfo.getErrorType();
				if (ErrorData.ErrorType.CONNECTION_ERROR.equals(errorType)) {
					String errorInfoStr = new Gson().toJson(response.getErrorInfo());
					diagnosticsHander.error(RetrieveFileControlProxy.serviceId,errorInfoStr);
				} else {
					diagnosticsHander.success(RetrieveFileControlProxy.serviceId);
				}
			} else {
				diagnosticsHander.success(RetrieveFileControlProxy.serviceId);
			}
		}catch(HttpServerErrorException he){	
			logger.fatal("ERROR",he);
			if (HttpStatus.INTERNAL_SERVER_ERROR == he.getStatusCode()) {
				diagnosticsHander.success(RetrieveFileControlProxy.serviceId);
			} else {
				diagnosticsHander.error(RetrieveFileControlProxy.serviceId,he);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error(RetrieveFileControlProxy.serviceId,e);
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
			diagnosticsHander.create("DIHEncryptor", "Test DIHEncryptor");
			Encryptor enc = EncryptorFactory.getDIHEncryptor();
			String data = "123456789";
			String encrypt = enc.encrypt(data);
			enc.decrypt(encrypt);
			diagnosticsHander.success("DIHEncryptor");
		}catch(Exception e){
			logger.fatal("ERROR",e);
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
		ApplicationGroupDataM applicationGroup = new ApplicationGroupDataM();
		applicationGroup.setApplicationGroupId("999999");
		applicationGroup.setApplicationGroupNo("T"+FormatUtil.token(5));
		applicationGroup.setApplicationDate(ApplicationDate.getDate());
		applicationGroup.setApplyDate(ApplicationDate.getDate());
		applicationGroup.setApplicationTemplate("0004");
		ArrayList<ApplicationDataM> applications = new ArrayList<ApplicationDataM>();
		ApplicationDataM application = new ApplicationDataM();
		application.setApplicationRecordId("999999");
		application.setBusinessClassId("CC_CG_GE");
		ArrayList<LoanDataM> loans = new ArrayList<LoanDataM>();
		LoanDataM loan = new LoanDataM();
		loan.setLoanId("999999");
		loans.add(loan);
		CardDataM card = new CardDataM();
		loan.setCard(card);
		card.setCardType("023");
		application.setLoans(loans);
		applications.add(application);
		ArrayList<PersonalInfoDataM> personalInfos = new ArrayList<PersonalInfoDataM>();
		PersonalInfoDataM personalInfo = new PersonalInfoDataM();
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(null==verificationResult){
			verificationResult = new VerificationResultDataM();
			personalInfo.setVerificationResult(verificationResult);
		}
		personalInfo.setPersonalId("999999");
		personalInfo.setCisNo("999999");
		personalInfo.setIdno("999999");
		personalInfo.setPersonalType("A");
		personalInfo.setBureauRequiredFlag("NCB_NEW");
		ArrayList<PersonalRelationDataM> personalRelations = new ArrayList<PersonalRelationDataM>();
		PersonalRelationDataM personalRelation = new PersonalRelationDataM();
		personalRelation.setPersonalId("999999");
		personalRelation.setRefId("999999");
		personalRelations.add(personalRelation);
		personalInfo.setPersonalRelations(personalRelations);
		personalInfos.add(personalInfo);
		applicationGroup.setPersonalInfos(personalInfos);
		applicationGroup.setApplications(applications);
		try{
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			diagnosticsHander.create("DECISION_POINT_IA", "Test DECISION_POINT_IA "+URL);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_IA);
			requestDecision.setUserId("SYSTEM");
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				diagnosticsHander.success("DECISION_POINT_IA");
			}else{
				diagnosticsHander.error("DECISION_POINT_IA",responseData.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("DECISION_POINT_IA",e);
		}
		try{
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			diagnosticsHander.create("DECISION_POINT_PB1", "Test DECISION_POINT_PB1 "+URL);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_PB1);
			requestDecision.setUserId("SYSTEM");
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				diagnosticsHander.success("DECISION_POINT_PB1");
			}else{
				diagnosticsHander.error("DECISION_POINT_PB1",responseData.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("DECISION_POINT_PB1",e);
		}
		try{
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			diagnosticsHander.create("DECISION_POINT_DE1", "Test DECISION_POINT_DE1 "+URL);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DE1);
			requestDecision.setUserId("SYSTEM");
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				diagnosticsHander.success("DECISION_POINT_DE1");
			}else{
				diagnosticsHander.error("DECISION_POINT_DE1",responseData.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("DECISION_POINT_DE1",e);
		}
		try{
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			diagnosticsHander.create("DECISION_POINT_DE2", "Test DECISION_POINT_DE2 "+URL);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DE2);
			requestDecision.setUserId("SYSTEM");
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				diagnosticsHander.success("DECISION_POINT_DE2");
			}else{
				diagnosticsHander.error("DECISION_POINT_DE2",responseData.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("DECISION_POINT_DE2",e);
		}
		try{
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			diagnosticsHander.create("DECISION_POINT_FI", "Test DECISION_POINT_FI "+URL);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_FI);
			requestDecision.setUserId("SYSTEM");
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				diagnosticsHander.success("DECISION_POINT_FI");
			}else{
				diagnosticsHander.error("DECISION_POINT_FI",responseData.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("DECISION_POINT_FI",e);
		}
		try{
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			diagnosticsHander.create("DECISION_POINT_DV1", "Test DECISION_POINT_DV1 "+URL);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DV1);
			requestDecision.setUserId("SYSTEM");
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				diagnosticsHander.success("DECISION_POINT_DV1");
			}else{
				diagnosticsHander.error("DECISION_POINT_DV1",responseData.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("DECISION_POINT_DV1",e);
		}
		try{
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			diagnosticsHander.create("DECISION_POINT_DV2", "Test DECISION_POINT_DV2 "+URL);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DV2);
			requestDecision.setUserId("SYSTEM");
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				diagnosticsHander.success("DECISION_POINT_DV2");
			}else{
				diagnosticsHander.error("DECISION_POINT_DV2",responseData.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("DECISION_POINT_DV2",e);
		}
		try{
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			diagnosticsHander.create("DECISION_POINT_DC", "Test DECISION_POINT_DC "+URL);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DC);
			requestDecision.setUserId("SYSTEM");
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				diagnosticsHander.success("DECISION_POINT_DC");
			}else{
				diagnosticsHander.error("DECISION_POINT_DC",responseData.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("DECISION_POINT_DC",e);
		}
		try{
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			diagnosticsHander.create("DECISION_POINT_DOC_COMPLETE", "Test DECISION_POINT_DOC_COMPLETE "+URL);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DOC_COMPLETE);
			requestDecision.setUserId("SYSTEM");
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				diagnosticsHander.success("DECISION_POINT_DOC_COMPLETE");
			}else{
				diagnosticsHander.error("DECISION_POINT_DOC_COMPLETE",responseData.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("DECISION_POINT_DOC_COMPLETE",e);
		}
		try{
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			diagnosticsHander.create("DECISION_POINT_BUREAU_DOC", "Test DECISION_POINT_BUREAU_DOC "+URL);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_BUREAU_DOC);
			requestDecision.setUserId("SYSTEM");
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				diagnosticsHander.success("DECISION_POINT_BUREAU_DOC");
			}else{
				diagnosticsHander.error("DECISION_POINT_BUREAU_DOC",responseData.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("DECISION_POINT_BUREAU_DOC",e);
		}
		try{
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			diagnosticsHander.create("DECISION_POINT_INCOME", "Test DECISION_POINT_INCOME "+URL);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME);
			requestDecision.setUserId("SYSTEM");
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				diagnosticsHander.success("DECISION_POINT_INCOME");
			}else{
				diagnosticsHander.error("DECISION_POINT_INCOME",responseData.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("DECISION_POINT_INCOME",e);
		}
		try{
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			diagnosticsHander.create("DECISION_POINT_POST", "Test DECISION_POINT_POST "+URL);
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_POST);
			requestDecision.setUserId("SYSTEM");
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				diagnosticsHander.success("DECISION_POINT_POST");
			}else{
				diagnosticsHander.error("DECISION_POINT_POST",responseData.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			diagnosticsHander.error("DECISION_POINT_POST",e);
		}
		return ResponseEntity.ok(diagnosticsHander);
	}
}
