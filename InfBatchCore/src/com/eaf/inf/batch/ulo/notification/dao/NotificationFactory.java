package com.eaf.inf.batch.ulo.notification.dao;


public class NotificationFactory {
	public static NotificationDAO getNotificationDAO(){
		return new NotificationDAOImpl();
	}
	public static TemplateDAO getTemplateDAO(){
		return new TemplateDAOImpl();
	}
	public static MasterNotificationConfigDAO getMasterNotificationConfigDAO(){
		return new MasterNotificationConfigDAOImpl();
	}
	public static ApplicationInfoDAO getApplicationInfoDAO(){
		return new ApplicationInfoDAOImpl();
	}
}
