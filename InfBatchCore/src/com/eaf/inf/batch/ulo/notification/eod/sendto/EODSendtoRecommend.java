package com.eaf.inf.batch.ulo.notification.eod.sendto;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.inf.batch.ulo.notification.model.EODRecipientRequestDataM;

public class EODSendtoRecommend extends EODSendingHelper {
	private static transient Logger logger = Logger.getLogger(EODSendtoRecommend.class);
	@Override
	public void processGetRecipient(EODRecipientRequestDataM eodReceipientRequest,RecipientTypeDataM recipient) throws Exception {
//		#rawi comment for eod not send recommend
//		ArrayList<EodEmployee> recommendEmpIds = new ArrayList<EodEmployee>();
//		for(NotificationEODDataM notificationEod: eodReceipientRequest.getNotificationEods()){
//			String emdId = notificationEod.getRecommend();
//			if(!InfBatchUtil.empty(emdId) &&!foundEmpId(recommendEmpIds,emdId)){
//				EodEmployee eodEmployee = new EodEmployee();
//				eodEmployee.setEmpId(emdId);
//				eodEmployee.setNotificationType(notificationEod.getNotificationType());
//				recommendEmpIds.add(eodEmployee);
//			}
// 		}	 
//		getEmailEmployeeInfo(recommendEmpIds,recipient,eodReceipientRequest.getEmails());
	}
}
