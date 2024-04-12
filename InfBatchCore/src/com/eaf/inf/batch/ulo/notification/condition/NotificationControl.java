package com.eaf.inf.batch.ulo.notification.condition;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;


public class NotificationControl {
	private static transient Logger logger = Logger.getLogger(NotificationControl.class);
	public static String getNotificationActionClass(String configName){
		return InfBatchProperty.getInfBatchConfig(configName);
	}
	
	public static NotificationActionInf getNotification(String configName){
		NotificationActionInf notificationActioninf  =null;
		try{
			String PROCESS_CLASS_NAME = getNotificationActionClass(configName);
			logger.debug("configName >> "+configName);	
			logger.debug("PROCESS_CLASS_NAME >> "+PROCESS_CLASS_NAME);	
			if(!InfBatchUtil.empty(PROCESS_CLASS_NAME)){
				notificationActioninf =(NotificationActionInf)Class.forName(PROCESS_CLASS_NAME).newInstance();
			}		
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return notificationActioninf;
	}
}
