package com.eaf.inf.batch.ulo.notification.eod.sendto;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
import com.eaf.inf.batch.ulo.notification.model.EODRecipientRequestDataM;

public class EODSendtoSeller extends EODSendingHelper {
	private static transient Logger logger = Logger.getLogger(EODSendtoSeller.class);
	@Override
	public void processGetRecipient(EODRecipientRequestDataM eodReceipientRequest,RecipientTypeDataM recipient) throws Exception{
		ArrayList<EodEmployee> sellerEmpIds = new ArrayList<EodEmployee>();
		for(NotificationEODDataM notificationEod : eodReceipientRequest.getNotificationEods()){
			String saleId = notificationEod.getSaleId();
//			logger.debug("saleId : "+saleId);
			if(!InfBatchUtil.empty(saleId)&&!foundEmpId(sellerEmpIds, saleId)){
				EodEmployee eodEmployee = new EodEmployee();
				eodEmployee.setEmpId(saleId);
				eodEmployee.setNotificationType(notificationEod.getNotificationType());
				sellerEmpIds.add(eodEmployee);
			}
 		}	 		
		logger.debug("sellerEmpIds : "+sellerEmpIds);
		getEmailEmployeeInfo(sellerEmpIds,recipient,eodReceipientRequest.getEmails());
	}
}
