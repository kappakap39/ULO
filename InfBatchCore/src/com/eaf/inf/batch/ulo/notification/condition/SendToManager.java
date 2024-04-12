package com.eaf.inf.batch.ulo.notification.condition;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.notify.model.SMSRecipientDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;

public class SendToManager extends NotificationActionHelper  implements NotificationActionInf  {
	private static transient Logger logger = Logger.getLogger(SendToManager.class);	
	String SEND_TO_TYPE_MANAGER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_MANAGER");
	String NOTIFICATION_SEND_TO_PRIORITY=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_PRIORITY");
	@Override
	public Object notificationProcessCondition(Object... obj) throws Exception {
		NotificationInfoDataM notificationInfo  = (NotificationInfoDataM)obj[0];
		RecipientTypeDataM recipientType = (RecipientTypeDataM)obj[1];	
		String EMAIL = TemplateBuilderConstant.TemplateType.EMAIL;
		String SMS = TemplateBuilderConstant.TemplateType.SMS;
		String saleID = notificationInfo.getSaleId();
		HashMap<String, ArrayList<JobCodeDataM>>  hJobCodes = notificationInfo.getJobCodes();
		ArrayList<JobCodeDataM>  smsManagerJobCodes =  hJobCodes.get(SMS+"_"+SEND_TO_TYPE_MANAGER);
		ArrayList<JobCodeDataM>  emailManagerJobCodes =  hJobCodes.get(EMAIL+"_"+SEND_TO_TYPE_MANAGER);
		//get app and applicant info
		NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
		ArrayList<RecipientInfoDataM> recipientInfos = notificationDAO.loadRecipient(notificationInfo);
		if(!InfBatchUtil.empty(smsManagerJobCodes) && smsManagerJobCodes.size()>0){
			this.setElementData(SMS, saleID, smsManagerJobCodes, recipientType,recipientInfos);
		}
		if(!InfBatchUtil.empty(emailManagerJobCodes) && emailManagerJobCodes.size()>0){
			this.setElementData(EMAIL, saleID, emailManagerJobCodes, recipientType,recipientInfos);
		}
		return null;
	}
	
	private void setElementData(String notificationType,String saleId,ArrayList<JobCodeDataM> jobCodeList,RecipientTypeDataM recipientTypeDataM,ArrayList<RecipientInfoDataM> recipientInfos) throws Exception{
		if(!InfBatchUtil.empty(saleId) && NOTIFICATION_SEND_TO_PRIORITY.equals(jobCodeList.get(0).getPriority())){
			NotificationCondition notificationCondition = new NotificationCondition();
			ArrayList<VCEmpInfoDataM> vcEmpInfos = notificationCondition.getSendToVCEmpManagers(saleId, jobCodeList);
			if(!InfBatchUtil.empty(vcEmpInfos)){
				for(VCEmpInfoDataM vcEmpInfo : vcEmpInfos){
					if(TemplateBuilderConstant.TemplateType.SMS.equals(notificationType) && !InfBatchUtil.empty(vcEmpInfo.getMobilePhone())){
						SMSRecipientDataM  smsRecipient = new SMSRecipientDataM();
						smsRecipient.setMobileNo(vcEmpInfo.getMobilePhone());
						smsRecipient.setRecipientType(SEND_TO_TYPE_MANAGER);
						smsRecipient.setRecipientObject(recipientInfos);
						recipientTypeDataM.put(smsRecipient);
					}else if(TemplateBuilderConstant.TemplateType.EMAIL.equals(notificationType) &&  !InfBatchUtil.empty(vcEmpInfo.getEmail())){
						EmailRecipientDataM  emailRecipient = new EmailRecipientDataM();
						emailRecipient.setEmail(vcEmpInfo.getEmail());
						emailRecipient.setRecipientType(SEND_TO_TYPE_MANAGER);
						emailRecipient.setRecipientObject(recipientInfos);
						recipientTypeDataM.put(emailRecipient);
					}
				}
			}
		}
	}
}
