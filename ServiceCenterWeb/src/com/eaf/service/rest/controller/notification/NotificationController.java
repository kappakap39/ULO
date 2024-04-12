package com.eaf.service.rest.controller.notification;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.service.notify.cont.NotifyConstant;
import com.eaf.core.ulo.service.notify.controller.NotifyController;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoRequestDataM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.model.notification.NotificationRequest;
import com.eaf.orig.ulo.model.notification.NotificationResponse;
import com.eaf.service.rest.model.ServiceResponse;

@RestController
@RequestMapping("/service/notification")
public class NotificationController {
	private static transient Logger logger = Logger.getLogger(NotificationController.class);
	@RequestMapping(value="/send",method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<NotificationResponse> send(@ModelAttribute NotificationRequest notificationRequest){
		NotificationResponse response = new NotificationResponse(); 
		try{
			logger.debug("applicationGroupId : "+notificationRequest.getApplicationGroupId());
			logger.debug("saleType : "+notificationRequest.getSaleType());
			logger.debug("sendTime : "+notificationRequest.getSendTime());
			logger.debug("status : "+notificationRequest.getStatus());
			NotifyResponse notifyResponse = NotifyController.notify(mappingNotifyRequest(notificationRequest), null);	
			logger.debug(notifyResponse);
			if(InfBatchConstant.ResultCode.SUCCESS.equals(notifyResponse.getStatusCode())){
				response.setStatusCode(ServiceResponse.Status.SUCCESS);
			}else if(InfBatchConstant.ResultCode.WARNING.equals(notifyResponse.getStatusCode())){
				response.setStatusCode(ServiceResponse.Status.WARNING);
				response.setStatusDesc(notifyResponse.getStatusDesc());
			}else{
				response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				response.setStatusDesc(notifyResponse.getStatusDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			response.setStatusDesc(e.getLocalizedMessage());
		}			
		logger.debug(response);
		return ResponseEntity.ok(response);
	}	
	private NotifyRequest mappingNotifyRequest(NotificationRequest notificationRequest) throws Exception{
		NotifyRequest notifyRequest  = new NotifyRequest();
		NotificationInfoRequestDataM notificationInfo = new NotificationInfoRequestDataM();
		notificationInfo.setApplicationGroupId(notificationRequest.getApplicationGroupId());
		notificationInfo.setApplicationStatus(notificationRequest.getStatus());
		notificationInfo.setSendingTime(notificationRequest.getSendTime());
		notificationInfo.setSaleType(notificationRequest.getSaleType());
		notificationInfo.setLifeCycle(ORIGDAOFactory.getApplicationGroupDAO().getLifeCycle(notificationRequest.getApplicationGroupId()));
		notifyRequest.setNotifyId(NotifyConstant.Notify.NOTIFY_NOTIFICATION);
		notifyRequest.setRequestObject(notificationInfo);
		return notifyRequest;
	}
}
