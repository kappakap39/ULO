package com.eaf.inf.batch.ulo.warehouse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.proxy.ServiceCenterController;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.model.ServiceResponse;

public class CallDmService {
	private static transient Logger logger = Logger.getLogger(CallDmService.class);
	public static final String serviceId = "CreateWarehouse";
	public static void creatDMService(String appGroupId,String appGroupNo,String userId){
		ServiceCenterController serviceController = new ServiceCenterController();
		ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
		ResponseEntity<ServiceResponse> responseEntity = null;
		try{
			if(null!=appGroupId && !"".equals(appGroupId)){
				String URL = SystemConfig.getProperty("CREATE_DM_SERVICE_URL").replace("{appGroupId}", appGroupId);
				logger.debug("URL>>>"+URL);								
				ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
					serviceLogRequest.setServiceReqRespId(ServiceUtil.generateServiceReqResId());
					serviceLogRequest.setRefCode(appGroupNo);
					serviceLogRequest.setActivityType(ServiceConstant.OUT);
					serviceLogRequest.setServiceDataObject("{appGroupId:"+appGroupId+"}");
					serviceLogRequest.setServiceId(serviceId);
					serviceLogRequest.setUniqueId(appGroupId);
					serviceLogRequest.setUserId(userId);
				serviceController.createLog(serviceLogRequest);				
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
					responseEntity = restTemplate.postForEntity(URI.create(URL), null, ServiceResponse.class);						
				 }catch(Exception e){
					serviceLogResponse.setRespCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
					serviceLogResponse.setRespDesc("error call rest Dm:"+e.getLocalizedMessage());
					logger.fatal("ERROR CALL REST DM>>",e);
				 }			
				if(!Util.empty(responseEntity)){
					if(!Util.empty(responseEntity.getBody())){
						if(ServiceResponse.Status.SUCCESS.equals(responseEntity.getBody().getStatusCode())){
							logger.debug("Create DM Success!!");
							serviceLogResponse.setRespCode(ServiceResponse.Status.SUCCESS);
						}else{
							logger.debug("Create DM Fail!!");
							serviceLogResponse.setRespCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
						}
					}else{
						logger.debug("Body  is null...call DM Fail!!");
						serviceLogResponse.setRespCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					}

				}else{
					logger.debug("responseEntity is null....call DM Fail!!");
					serviceLogResponse.setRespCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				}
			}	
		}catch(Exception e){
			serviceLogResponse.setRespCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceLogResponse.setRespDesc(e.getLocalizedMessage());
			logger.fatal("ERROR",e);
		}		
		serviceLogResponse.setServiceReqRespId(ServiceUtil.generateServiceReqResId());
		serviceLogResponse.setRefCode(appGroupNo);
		serviceLogResponse.setActivityType(ServiceConstant.IN);
		serviceLogResponse.setServiceId(serviceId);
		serviceLogResponse.setUniqueId(appGroupId);
		serviceLogResponse.setUserId(userId);
		serviceController.createLog(serviceLogResponse);
	}
}
