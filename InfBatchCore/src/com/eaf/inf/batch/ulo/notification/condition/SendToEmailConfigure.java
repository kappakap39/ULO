package com.eaf.inf.batch.ulo.notification.condition;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;

public class SendToEmailConfigure extends NotificationActionHelper  implements NotificationActionInf{
	private static transient Logger logger = Logger.getLogger(SendToEmailConfigure.class);	
	String SEND_TO_TYPE_FIX=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_FIX");
	String NOTIFICATION_SEND_TO_PRIORITY=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_PRIORITY");
	@Override
	public Object notificationProcessCondition(Object... obj) throws Exception {
		NotificationInfoDataM notificationInfo  = (NotificationInfoDataM)obj[0];		
		RecipientTypeDataM recipientType = (RecipientTypeDataM)obj[1];
		logger.debug("send to fix email...");
		HashMap<String, ArrayList<JobCodeDataM>>  hJobCodes = notificationInfo.getJobCodes();
		String EMAIL_KEY_SELLER = TemplateBuilderConstant.TemplateType.EMAIL+"_"+SEND_TO_TYPE_FIX;
		ArrayList<JobCodeDataM>  emailFixJobCodes =  hJobCodes.get(EMAIL_KEY_SELLER);
		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
		if(!InfBatchUtil.empty(emailFixJobCodes)){
			//set applicant for find template data
			ArrayList<RecipientInfoDataM> sendTocustomers = notificationDAO.loadRecipient(notificationInfo);	
			if(!Util.empty(sendTocustomers)){
				for(RecipientInfoDataM recipientInfo : sendTocustomers){
					if(!InfBatchUtil.empty(recipientInfo.getCashTranferType())){
						for(JobCodeDataM  jobCode :emailFixJobCodes ){
							if(!InfBatchUtil.empty(jobCode.getFixEmail()) && NOTIFICATION_SEND_TO_PRIORITY.equals(jobCode.getPriority())){
								String generalParam = jobCode.getFixEmail();
								logger.debug("generalParam>>"+generalParam);
								String email = notificationDAO.getGeneralParamValue(generalParam);
								logger.debug("EMAIL FIX "+email);
								if(!InfBatchUtil.empty(email)){
									String[] emailList = email.split(",");
									for(String emailconfix : emailList){
										EmailRecipientDataM  emailRecipient  = new EmailRecipientDataM();
										emailRecipient.setEmail(emailconfix);
										emailRecipient.setRecipientType(SEND_TO_TYPE_FIX);
										emailRecipient.setRecipientObject(sendTocustomers);
										recipientType.put(emailRecipient);
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
}
