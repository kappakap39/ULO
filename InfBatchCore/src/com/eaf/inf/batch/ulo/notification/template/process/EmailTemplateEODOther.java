package com.eaf.inf.batch.ulo.notification.template.process;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;

public class EmailTemplateEODOther extends TemplateBuilderHelper {
private static transient Logger logger = Logger.getLogger(EmailTemplateEODOther.class);
	@Override
	public TemplateVariableDataM getTemplateVariable(){
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		try{
			ArrayList<String> applicationGroupIdList = getApplicationGroupIDs(templateBuilderRequest.getRequestObject());
			if(!InfBatchUtil.empty(applicationGroupIdList)){
				
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return templateVariable;
	}
	private ArrayList<String> getApplicationGroupIDs(Object object){
		String NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_OTHER = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_OTHER");
		ArrayList<String> applicationGroupIdList = new ArrayList<String>();
		try{
			if(object instanceof ArrayList<?> ){
				ArrayList<?> objectArr = (ArrayList<?>)object;
				if(objectArr.get(0) instanceof NotificationEODDataM){
					ArrayList<String> applicationGroupIds = new ArrayList<String>();
					ArrayList<NotificationEODDataM> notificationEODList = (ArrayList<NotificationEODDataM>)object;
					for(NotificationEODDataM notificationEOD :notificationEODList){
						if(!applicationGroupIds.contains(notificationEOD.getApplicationGroupId()) && notificationEOD.getNotificationType().equals(NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_OTHER)){
							applicationGroupIds.add(notificationEOD.getApplicationGroupId());
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		logger.debug("applicationGroupIdList : "+applicationGroupIdList);
		return applicationGroupIdList;
	}

}
