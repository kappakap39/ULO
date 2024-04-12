package com.eaf.service.rest.resource.sms;

//import javax.ws.rs.Consumes;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//
//import org.apache.log4j.Logger;
//
//import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
//import com.eaf.service.common.api.ServiceCache;
//import com.eaf.service.common.model.ServiceRequestDataM;
//import com.eaf.service.common.model.ServiceResponseDataM;
//import com.eaf.service.common.proxy.ServiceCenterProxy;
//import com.eaf.service.module.manual.SMSServiceProxy;
//import com.eaf.service.module.model.SMSRequestDataM;
//import com.eaf.service.module.model.SMSResponseDataM;
//import com.eaf.service.rest.model.SMSRequest;
//import com.eaf.service.rest.model.SMSResponse;
//import com.eaf.service.rest.model.ServiceResponse;
//import com.google.gson.Gson;

@Deprecated
//@Path("/service")
public class SmsService {
//	private static transient Logger logger = Logger.getLogger(SmsService.class);
//	@PUT
//	@Path("/send")
//	@Consumes(MediaType.APPLICATION_JSON)
//    public Response sendsms(@Context UriInfo uriInfo,SMSRequest smsRequest){
//		logger.debug(smsRequest);	
//		SMSResponse smsResponse = new SMSResponse();
//		try{
//			String applicationGroupId = smsRequest.getUniqueId();
//			logger.debug("applicationGroupId >> "+applicationGroupId);
//			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
//			serviceRequest.setUniqueId(applicationGroupId);
//			serviceRequest.setRefId(ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupNo(applicationGroupId));
//			serviceRequest.setServiceId(SMSServiceProxy.serviceId);
//			String SMS_SERVICE_ENDPOINT_URL = ServiceCache.getProperty("SMS_SERVICE_ENDPOINT_URL");
//			logger.debug("SMS_SERVICE_ENDPOINT_URL >> "+SMS_SERVICE_ENDPOINT_URL);
//			serviceRequest.setEndpointUrl(SMS_SERVICE_ENDPOINT_URL);
//			serviceRequest.setObjectData(getSMSRequest(smsRequest));			
//			ServiceCenterProxy proxy = new ServiceCenterProxy();
//			ServiceResponseDataM serviceResponse = proxy.requestService(serviceRequest);
//			SMSResponseDataM responseObject = (SMSResponseDataM)serviceResponse.getObjectData();
//			logger.debug("responseObject >> "+responseObject);
//			smsResponse.setStatusCode(serviceResponse.getResultCode());
//			smsResponse.setStatusDesc(serviceResponse.getResultDesc());
//		}catch(Exception e){
//			smsResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
//			smsResponse.setResultDesc(e.getLocalizedMessage());
//			logger.fatal("ERROR",e);
//		}
//		logger.debug(smsResponse);
//		Gson gson = new Gson();		
//        return Response.ok().entity(gson.toJson(smsResponse)).build();
//    }
//	
//	private SMSRequestDataM getSMSRequest(SMSRequest smsRequest){
//		SMSRequestDataM smsRequestM = new SMSRequestDataM();
//		smsRequestM.setMobileNoElement(smsRequest.getMobileNoElement());
//		smsRequestM.setMessageType(smsRequestM.getMessageType());
//		smsRequestM.setTemplateId(smsRequest.getTemplateId());
//		smsRequestM.setSmsLanguage(smsRequest.getSmsLanguage());
//		smsRequestM.setDepartmentCode(smsRequest.getDepartmentCode());
//		smsRequestM.setPriority(smsRequest.getPriority());
//		smsRequestM.setMessageType(smsRequest.getMsg());
//		smsRequestM.setClientId(smsRequest.getClientId());
//		return smsRequestM;
//	}
}
