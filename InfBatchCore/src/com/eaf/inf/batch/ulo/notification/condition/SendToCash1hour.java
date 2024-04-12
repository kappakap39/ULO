package com.eaf.inf.batch.ulo.notification.condition;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;

public class SendToCash1hour  extends NotificationActionHelper  implements NotificationActionInf{
	private static transient Logger logger = Logger.getLogger(SendToCash1hour.class);	
	String SEND_TO_TYPE_CASH1HOUR=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_CASH1HOUR");	
	String NOTIFICATION_SEND_TO_PRIORITY=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_PRIORITY");
	@Override
	public Object notificationProcessCondition(Object... obj) throws InfBatchException {
		NotificationInfoDataM notificationInfo  = (NotificationInfoDataM)obj[0];	 
		RecipientTypeDataM recipientType = (RecipientTypeDataM)obj[1];
		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
		HashMap<String,ArrayList<JobCodeDataM>>  hJobCodes = notificationInfo.getJobCodes();
		ArrayList<RecipientInfoDataM> applicantCash1Hour = notificationDAO.loadRecipientCash1Hour(notificationInfo);
		if(null!=applicantCash1Hour  && applicantCash1Hour.size()>0){
			String EMAIL_KEY_CASH1HOUR = TemplateBuilderConstant.TemplateType.EMAIL+"_"+SEND_TO_TYPE_CASH1HOUR;
			ArrayList<JobCodeDataM>  emailFixJobCodes =  hJobCodes.get(EMAIL_KEY_CASH1HOUR);
			if(!InfBatchUtil.empty(emailFixJobCodes)){
				notificationInfo.setSendToCash1hour(true);	
				for(JobCodeDataM  jobCode :emailFixJobCodes ){
					logger.debug("jobCode.getPriority() : "+jobCode.getPriority());
					if(!InfBatchUtil.empty(jobCode.getFixEmail()) &&  NOTIFICATION_SEND_TO_PRIORITY.equals(jobCode.getPriority())){
						String generalParam = jobCode.getFixEmail();
						logger.debug("generalParam : "+generalParam);
						String email = notificationDAO.getGeneralParamValue(generalParam);
						if(!InfBatchUtil.empty(email)){
							String[] emailList = email.split(",");
							for(String emailconfix : emailList){
								EmailRecipientDataM  emailRecipient  = new EmailRecipientDataM();
								emailRecipient.setEmail(emailconfix);
								emailRecipient.setRecipientType(SEND_TO_TYPE_CASH1HOUR);
								emailRecipient.setRecipientObject(applicantCash1Hour);
								recipientType.put(emailRecipient);
							}
						}
					}
				} 
			}
		}
		return null;
	}
}
