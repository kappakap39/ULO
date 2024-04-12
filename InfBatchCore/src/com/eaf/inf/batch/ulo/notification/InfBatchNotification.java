package com.eaf.inf.batch.ulo.notification;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.service.notify.cont.NotifyConstant;
import com.eaf.core.ulo.service.notify.controller.NotifyController;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoRequestDataM;

public class InfBatchNotification extends InfBatchHelper {
	private static transient Logger logger = Logger.getLogger(InfBatchNotification.class);
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {	
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		 try{
			NotifyRequest notifiRequest  = new NotifyRequest();
			NotificationInfoRequestDataM notificationInfo = new NotificationInfoRequestDataM();
			notifiRequest.setNotifyId(NotifyConstant.Notify.NOTIFY_NOTIFICATION);
			notifiRequest.setRequestObject(notificationInfo);
			NotifyResponse notifyResponse = NotifyController.notify(notifiRequest, null);	
			logger.debug("notifyResponse>>"+notifyResponse);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	
}
